package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.GetDepartmentListTool;
import com.unis.aicode.langgraph4j.tools.model.dto.DepartmentInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.util.List;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class GetDepartmentListNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                GetDepartmentListTool getDepartmentListTool = SpringContextUtil.getBean(GetDepartmentListTool.class);
                log.info("GetDepartmentListTool Node executed successfully");
                // 调用工具方法并存储结果到上下文
                List<DepartmentInfo> departmentList = getDepartmentListTool.getDepartmentList();
                context.setDepartmentList(departmentList);
                context.setCurrentStep("获取部门列表");
                log.info("成功获取部门列表，共{}个部门", departmentList.size());
            } catch (Exception e) {
                log.error("GetDepartmentListTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("获取部门列表失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}