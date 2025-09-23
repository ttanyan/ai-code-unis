package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.UpdateDepartmentTool;
import com.unis.aicode.langgraph4j.tools.model.dto.DepartmentInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class UpdateDepartmentNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                UpdateDepartmentTool updateDepartmentTool = SpringContextUtil.getBean(UpdateDepartmentTool.class);
                log.info("UpdateDepartmentTool Node executed successfully");
                // 这里应该从上下文中获取要更新的部门信息
                // 由于这是一个示例，我们创建一个模拟的部门对象
                DepartmentInfo mockDepartmentInfo = new DepartmentInfo();
                mockDepartmentInfo.setOrgId(1001);
                mockDepartmentInfo.setOrgName("更新后的部门");
                
                // 调用工具方法更新部门
                updateDepartmentTool.updateDepartment(mockDepartmentInfo);
                context.setCurrentStep("更新部门");
                log.info("成功更新部门ID: {}", mockDepartmentInfo.getOrgId());
            } catch (Exception e) {
                log.error("UpdateDepartmentTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("更新部门失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}