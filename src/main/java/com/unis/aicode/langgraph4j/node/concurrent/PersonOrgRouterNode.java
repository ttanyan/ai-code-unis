package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 人员组织管理路由决策工作节点
 * 专门用于根据增强后的提示词决定执行哪种人员组织管理操作
 */
@Slf4j
public class PersonOrgRouterNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 人员组织管理路由决策");

            // 获取增强后的提示词
            String enhancedPrompt = context.getEnhancedPrompt();
            
            // 根据提示词内容决定路由方向
            String routeDecision;
            if (enhancedPrompt.contains("查询") || enhancedPrompt.contains("获取") || enhancedPrompt.contains("查看")) {
                routeDecision = "query";
            } else if (enhancedPrompt.contains("添加") || enhancedPrompt.contains("创建")) {
                routeDecision = "add";
            } else if (enhancedPrompt.contains("更新") || enhancedPrompt.contains("修改")) {
                routeDecision = "update";
            } else if (enhancedPrompt.contains("删除") || enhancedPrompt.contains("移除")) {
                routeDecision = "delete";
            } else if (enhancedPrompt.contains("同步")) {
                routeDecision = "sync";
            } else {
                // 默认执行查询操作
                routeDecision = "query";
            }
            
            log.info("人员组织管理路由决策完成，选择路径: {}", routeDecision);

            // 更新状态
            context.setCurrentStep("人员组织管理路由决策");
            // 可以将路由决策存储在上下文中，供后续节点使用
            // 这里简单处理，实际项目中可能需要更复杂的路由逻辑
            return WorkflowContext.saveContext(context);
        });
    }
}