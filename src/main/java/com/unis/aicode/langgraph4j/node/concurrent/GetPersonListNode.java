package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.GetPersonListTool;
import com.unis.aicode.langgraph4j.tools.model.dto.UserInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.util.List;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 获取用户列表节点
 * 该节点用于执行获取所有BSS用户列表的操作，并将结果存储在工作流上下文中
 */
@Slf4j
public class GetPersonListNode {

    /**
     * 创建获取用户列表节点的异步操作
     * 
     * @return 异步节点操作
     */
    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                // 从Spring容器中获取工具实例
                GetPersonListTool getPersonListTool = SpringContextUtil.getBean(GetPersonListTool.class);
                log.info("GetPersonListTool Node executed successfully");
                // 调用工具方法获取用户列表并存储结果到上下文
                List<UserInfo> userList = getPersonListTool.getPersonList();
                context.setUserList(userList);
                context.setCurrentStep("获取用户列表");
                log.info("成功获取用户列表，共{}个用户", userList.size());
            } catch (Exception e) {
                log.error("GetPersonListTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("获取用户列表失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}