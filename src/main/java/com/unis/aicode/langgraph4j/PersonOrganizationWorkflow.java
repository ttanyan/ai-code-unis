package com.unis.aicode.langgraph4j;

import com.unis.aicode.exception.BusinessException;
import com.unis.aicode.exception.ErrorCode;
import com.unis.aicode.langgraph4j.node.PromptEnhancerNode;
import com.unis.aicode.langgraph4j.node.RouterNode;
import com.unis.aicode.langgraph4j.node.concurrent.*;
import com.unis.aicode.langgraph4j.state.WorkflowContext;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.GraphRepresentation;
import org.bsc.langgraph4j.GraphStateException;
import org.bsc.langgraph4j.NodeOutput;
import org.bsc.langgraph4j.prebuilt.MessagesState;
import org.bsc.langgraph4j.prebuilt.MessagesStateGraph;

import java.util.Map;

import static org.bsc.langgraph4j.StateGraph.END;
import static org.bsc.langgraph4j.StateGraph.START;

/**
 * 人员组织管理工作流
 * 该工作流专门处理用户、部门和MG人员的增删改查操作
 */
@Slf4j
public class PersonOrganizationWorkflow {

    /**
     * 创建人员组织管理工作流
     * 工作流包含以下主要功能模块：
     * 1. 用户管理：用户列表获取、添加、更新、删除用户
     * 2. 部门管理：部门列表获取、添加、更新、删除部门
     * 3. MG人员管理：MG人员列表获取、添加、更新、删除MG人员
     * 4. 数据同步：同步人员和组织信息、手动触发同步
     *
     * @return 编译后的工作流图
     */
    public StateGraph<MessagesState<String>> createWorkflow() {
        try {
            return new MessagesStateGraph<String>()
                    // 添加人员组织管理节点
                    .addNode("person_org_prompt_enhancer", PersonOrgPromptEnhancerNode.create()) // 提示词增强节点

                    /**
                     * 工作流的形式应该用 MCP 来实现，而不是用tool
                     * 1. 尝试将BSS MG抽成MCP后 让AI自主决定怎么同步
                     * 2. 流程编排.预设几种同步流程后,进行模式选择
                     */


                    //TODO 路由决策是错误的  用 MCP 的核心目标是让 AI 自主决定什么时候使用工具来完成任务
                    .addNode("person_org_router", PersonOrgRouterNode.create()) // 路由决策节点

                    .addNode("person_org_management", GetPersonListNode.create()) // 用户列表获取节点（人员组织管理入口）
                    .addNode("get_department_list", GetDepartmentListNode.create()) // 部门列表获取节点
                    .addNode("get_mg_person_list", GetMgPersonListNode.create()) // MG人员列表获取节点
                    .addNode("add_person", AddPersonNode.create()) // 添加用户节点
                    .addNode("update_person", UpdatePersonNode.create()) // 更新用户节点
                    .addNode("delete_person", DeletePersonsNode.create()) // 删除用户节点
                    .addNode("add_department", AddDepartmentNode.create()) // 添加部门节点
                    .addNode("update_department", UpdateDepartmentNode.create()) // 更新部门节点
                    .addNode("delete_department", DeleteDepartmentNode.create()) // 删除部门节点
                    .addNode("add_mg_person", AddMgPersonNode.create()) // 添加MG人员节点
                    .addNode("update_mg_person", UpdateMgPersonNode.create()) // 更新MG人员节点
                    .addNode("delete_mg_person", DeleteMgPersonsNode.create()) // 删除MG人员节点
                    .addNode("sync_person_org", SyncPersonAndOrganizationNode.create()) // 同步人员和组织节点
                    .addNode("trigger_sync", TriggerSyncNode.create()) // 触发同步节点


                    // 添加边
                    .addEdge(START, "person_org_prompt_enhancer") // 起始节点连接到提示词增强节点
                    .addEdge("person_org_prompt_enhancer", "person_org_router") // 提示词增强后进行路由决策
                    .addEdge("person_org_router", "person_org_management") // 路由决策后获取用户列表

                    // 人员组织管理流程
                    .addEdge("person_org_management", "get_department_list") // 获取用户列表后获取部门列表
                    .addEdge("get_department_list", "get_mg_person_list") // 获取部门列表后获取MG人员列表
                    
                    // 串行执行各个操作流程，避免并行节点问题
                    .addEdge("get_mg_person_list", "add_person") // 先添加用户
                    .addEdge("add_person", "update_person") // 再更新用户
                    .addEdge("update_person", "delete_person") // 再删除用户
                    
                    .addEdge("delete_person", "add_department") // 然后添加部门
                    .addEdge("add_department", "update_department") // 再更新部门
                    .addEdge("update_department", "delete_department") // 再删除部门
                    
                    .addEdge("delete_department", "add_mg_person") // 然后添加MG人员
                    .addEdge("add_mg_person", "update_mg_person") // 再更新MG人员
                    .addEdge("update_mg_person", "delete_mg_person") // 再删除MG人员

                    // 所有操作完成后进行同步
                    .addEdge("delete_mg_person", "sync_person_org") // 删除MG人员后进行同步

                    .addEdge("sync_person_org", "trigger_sync") // 同步人员和组织后可以触发同步
                    .addEdge("trigger_sync", END); // 触发同步后结束
        } catch (GraphStateException e) {
            log.info("人员组织管理工作流创建失败异常",e );
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "人员组织管理工作流创建失败");
        }
    }

    /**
     * 执行人员组织管理工作流
     *
     * @param originalPrompt 用户输入的原始提示词
     * @return 工作流执行完成后的上下文状态
     */
    public WorkflowContext executeWorkflow(String originalPrompt) {
        StateGraph<MessagesState<String>> workflowGraph = createWorkflow();
        CompiledGraph<MessagesState<String>> workflow;
        try {
            workflow = workflowGraph.compile();
        } catch (GraphStateException e) {
            log.info("人员组织管理工作流编译失败异常",e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "人员组织管理工作流编译失败");
        }

        WorkflowContext initialContext = WorkflowContext.builder()
                .originalPrompt(originalPrompt)
                .currentStep("初始化")
                .build();

        GraphRepresentation graph = workflow.getGraph(GraphRepresentation.Type.MERMAID);
        log.info("人员组织管理工作流图:\n{}", graph.content());
        log.info("开始执行人员组织管理工作流");

        WorkflowContext finalContext = null;
        int stepCounter = 1;
        for (NodeOutput<MessagesState<String>> step : workflow.stream(
                Map.of(WorkflowContext.WORKFLOW_CONTEXT_KEY, initialContext))) {
            log.info("--- 第 {} 步完成 ---", stepCounter);
            WorkflowContext currentContext = WorkflowContext.getContext(step.state());
            if (currentContext != null) {
                finalContext = currentContext;
                log.info("当前步骤上下文: {}", currentContext);
            }
            stepCounter++;
        }
        log.info("人员组织管理工作流执行完成！");
        return finalContext;
    }



}