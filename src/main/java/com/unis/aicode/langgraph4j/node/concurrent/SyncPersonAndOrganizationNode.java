package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.SyncPersonAndOrganizationTool;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class SyncPersonAndOrganizationNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                SyncPersonAndOrganizationTool syncPersonAndOrganizationTool = SpringContextUtil.getBean(SyncPersonAndOrganizationTool.class);
                log.info("SyncPersonAndOrganizationTool Node executed successfully");
                
                // 调用工具方法同步人员和组织信息
                syncPersonAndOrganizationTool.syncPersonAndOrganization();
                context.setCurrentStep("同步人员和组织信息");
                log.info("成功同步人员和组织信息");
            } catch (Exception e) {
                log.error("SyncPersonAndOrganizationTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("同步人员和组织信息失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}