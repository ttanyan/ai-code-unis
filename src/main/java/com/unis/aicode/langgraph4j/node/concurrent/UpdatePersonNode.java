package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.UpdatePersonTool;
import com.unis.aicode.langgraph4j.tools.model.dto.UserInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class UpdatePersonNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                UpdatePersonTool updatePersonTool = SpringContextUtil.getBean(UpdatePersonTool.class);
                log.info("UpdatePersonTool Node executed successfully");
                // 这里应该从上下文中获取要更新的用户信息
                // 由于这是一个示例，我们创建一个模拟的用户对象
                UserInfo mockUserInfo = new UserInfo();
                mockUserInfo.setUserId("1");
                mockUserInfo.setUserName("更新后的用户");
                
                // 调用工具方法更新用户
                updatePersonTool.updatePerson(mockUserInfo);
                context.setCurrentStep("更新用户");
                log.info("成功更新用户ID: {}", mockUserInfo.getUserId());
            } catch (Exception e) {
                log.error("UpdatePersonTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("更新用户失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}