package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.UpdateMgPersonTool;
import com.unis.aicode.langgraph4j.tools.model.dto.PersonInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class UpdateMgPersonNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                UpdateMgPersonTool updateMgPersonTool = SpringContextUtil.getBean(UpdateMgPersonTool.class);
                log.info("UpdateMgPersonTool Node executed successfully");
                // 这里应该从上下文中获取要更新的MG人员信息
                // 由于这是一个示例，我们创建一个模拟的MG人员对象
                PersonInfo mockPersonInfo = new PersonInfo();
                mockPersonInfo.setUserCode("MG001");
                mockPersonInfo.setUserName("更新后的MG人员");
                
                // 调用工具方法更新MG人员
                updateMgPersonTool.updateMgPerson(mockPersonInfo);
                context.setCurrentStep("更新MG人员");
                log.info("成功更新MG人员代码: {}", mockPersonInfo.getUserCode());
            } catch (Exception e) {
                log.error("UpdateMgPersonTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("更新MG人员失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}