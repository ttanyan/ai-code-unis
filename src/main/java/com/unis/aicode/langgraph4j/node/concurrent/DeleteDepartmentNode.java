package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.DeleteDepartmentTool;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class DeleteDepartmentNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                DeleteDepartmentTool deleteDepartmentTool = SpringContextUtil.getBean(DeleteDepartmentTool.class);
                log.info("DeleteDepartmentTool Node executed successfully");
                // 这里应该从上下文中获取要删除的部门索引列表
                // 由于这是一个示例，我们使用模拟的部门索引
                String mockOrgIndexes = "ORG001,ORG002";
                
                // 调用工具方法删除部门
                deleteDepartmentTool.deleteDepartment(mockOrgIndexes);
                context.setCurrentStep("删除部门");
                log.info("成功删除部门索引: {}", mockOrgIndexes);
            } catch (Exception e) {
                log.error("DeleteDepartmentTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("删除部门失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}