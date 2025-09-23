package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.AddMgPersonTool;
import com.unis.aicode.langgraph4j.tools.model.dto.PersonInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class AddMgPersonNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                AddMgPersonTool addMgPersonTool = SpringContextUtil.getBean(AddMgPersonTool.class);
                log.info("AddMgPersonTool Node executed successfully");
                // 这里应该从上下文中获取要添加的MG人员信息
                // 由于这是一个示例，我们创建一个模拟的MG人员对象
                PersonInfo mockPersonInfo = new PersonInfo();
                mockPersonInfo.setUserCode("MG001");
                mockPersonInfo.setUserName("模拟MG人员");
                mockPersonInfo.setGender(1);
                
                // 调用工具方法添加MG人员
                addMgPersonTool.addMgPerson(mockPersonInfo);
                context.setCurrentStep("添加MG人员");
                log.info("成功添加MG人员: {}", mockPersonInfo.getUserName());
            } catch (Exception e) {
                log.error("AddMgPersonTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("添加MG人员失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}