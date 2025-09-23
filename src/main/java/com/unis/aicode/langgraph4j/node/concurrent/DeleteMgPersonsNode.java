package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.DeleteMgPersonsTool;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.util.Arrays;
import java.util.List;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class DeleteMgPersonsNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                DeleteMgPersonsTool deleteMgPersonsTool = SpringContextUtil.getBean(DeleteMgPersonsTool.class);
                log.info("DeleteMgPersonsTool Node executed successfully");
                // 这里应该从上下文中获取要删除的MG人员ID列表
                // 由于这是一个示例，我们使用模拟的MG人员ID列表
                List<String> mockUserIds = Arrays.asList("1", "2", "3");
                
                // 调用工具方法删除MG人员
                deleteMgPersonsTool.deleteMgPersons(mockUserIds);
                context.setCurrentStep("删除MG人员");
                log.info("成功删除MG人员IDs: {}", mockUserIds);
            } catch (Exception e) {
                log.error("DeleteMgPersonsTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("删除MG人员失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}