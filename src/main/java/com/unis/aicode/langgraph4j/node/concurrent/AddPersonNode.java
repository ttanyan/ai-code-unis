package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.AddPersonTool;
import com.unis.aicode.langgraph4j.tools.model.dto.UserInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 添加用户节点
 * 该节点用于执行添加BSS用户的操作
 */
@Slf4j
public class AddPersonNode {

    /**
     * 创建添加用户节点的异步操作
     * 
     * @return 异步节点操作
     */
    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                // 从Spring容器中获取工具实例
                AddPersonTool addPersonTool = SpringContextUtil.getBean(AddPersonTool.class);
                log.info("AddPersonTool Node executed successfully");
                // 这里应该从上下文中获取要添加的用户信息
                // 由于这是一个示例，我们创建一个模拟的用户对象
                UserInfo mockUserInfo = new UserInfo();
                mockUserInfo.setUserCode("MOCK001");
                mockUserInfo.setUserName("模拟用户");
                mockUserInfo.setGender(1);
                
                // 调用工具方法添加用户
                UserInfo addedUser = addPersonTool.addPerson(mockUserInfo);
                context.setCurrentStep("添加用户");
                log.info("成功添加用户: {}", addedUser.getUserName());
            } catch (Exception e) {
                log.error("AddPersonTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("添加用户失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}