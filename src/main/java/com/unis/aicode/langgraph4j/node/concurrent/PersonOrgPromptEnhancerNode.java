package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 人员组织管理提示词增强工作节点
 * 专门用于增强与人员组织管理相关的提示词
 */
@Slf4j
public class PersonOrgPromptEnhancerNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 人员组织管理提示词增强");
            
            // 获取原始提示词
            String originalPrompt = context.getOriginalPrompt();
            
            // 构建增强后的提示词
            StringBuilder enhancedPromptBuilder = new StringBuilder();
            enhancedPromptBuilder.append("人员组织管理任务: ").append(originalPrompt);
            enhancedPromptBuilder.append("\n\n请根据以上要求执行相应的人员组织管理操作。");
            enhancedPromptBuilder.append("\n可执行的操作包括：");
            enhancedPromptBuilder.append("\n1. 查询用户、部门、MG人员列表");
            enhancedPromptBuilder.append("\n2. 添加用户、部门、MG人员");
            enhancedPromptBuilder.append("\n3. 更新用户、部门、MG人员信息");
            enhancedPromptBuilder.append("\n4. 删除用户、部门、MG人员");
            enhancedPromptBuilder.append("\n5. 同步人员和组织信息");
            
            String enhancedPrompt = enhancedPromptBuilder.toString();
            
            // 更新状态
            context.setCurrentStep("人员组织管理提示词增强");
            context.setEnhancedPrompt(enhancedPrompt);
            log.info("人员组织管理提示词增强完成，增强后长度: {} 字符", enhancedPrompt.length());
            return WorkflowContext.saveContext(context);
        });
    }
}