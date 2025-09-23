package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.DeletePersonsTool;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class DeletePersonsNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                DeletePersonsTool deletePersonsTool = SpringContextUtil.getBean(DeletePersonsTool.class);
                log.info("DeletePersonsTool Node executed successfully");
                // 这里应该从上下文中获取要删除的用户ID列表
                // 由于这是一个示例，我们使用模拟的用户ID
                String mockUserIds = "1,2,3";
                
                // 调用工具方法删除用户
                deletePersonsTool.deletePersons(mockUserIds);
                context.setCurrentStep("删除用户");
                log.info("成功删除用户IDs: {}", mockUserIds);
            } catch (Exception e) {
                log.error("DeletePersonsTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("删除用户失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}