package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.TriggerSyncTool;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class TriggerSyncNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                TriggerSyncTool triggerSyncTool = SpringContextUtil.getBean(TriggerSyncTool.class);
                log.info("TriggerSyncTool Node executed successfully");
                
                // 调用工具方法手动触发同步
                triggerSyncTool.triggerSync();
                context.setCurrentStep("手动触发同步");
                log.info("成功手动触发同步");
            } catch (Exception e) {
                log.error("TriggerSyncTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("手动触发同步失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}