package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.AddDepartmentTool;
import com.unis.aicode.langgraph4j.tools.model.dto.DepartmentInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class AddDepartmentNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                AddDepartmentTool addDepartmentTool = SpringContextUtil.getBean(AddDepartmentTool.class);
                log.info("AddDepartmentTool Node executed successfully");
                // 这里应该从上下文中获取要添加的部门信息
                // 由于这是一个示例，我们创建一个模拟的部门对象
                DepartmentInfo mockDepartmentInfo = new DepartmentInfo();
                mockDepartmentInfo.setOrgName("模拟部门");
                mockDepartmentInfo.setOrgIndex("MOCK001");
                
                // 调用工具方法添加部门
                DepartmentInfo addedDepartment = addDepartmentTool.addDepartment(mockDepartmentInfo);
                context.setCurrentStep("添加部门");
                log.info("成功添加部门: {}", addedDepartment.getOrgName());
            } catch (Exception e) {
                log.error("AddDepartmentTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("添加部门失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}