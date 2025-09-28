package com.unis.aicode.langgraph4j.node;

import com.unis.aicode.core.AiCodeGeneratorFacade;
import com.unis.aicode.constant.AppConstant;
import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.model.enums.CodeGenTypeEnum;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;
import reactor.core.publisher.Flux;

import java.time.Duration;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 网站代码生成节点
 */
@Slf4j
public class CodeGeneratorNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 代码生成");
            // 构造用户消息（包含原始提示词和可能的错误修复信息）
            String userMessage = buildUserMessage(context);
            CodeGenTypeEnum generationType = context.getGenerationType();
            // 获取 AI 代码生成外观服务
            AiCodeGeneratorFacade codeGeneratorFacade = SpringContextUtil.getBean(AiCodeGeneratorFacade.class);
            log.info("开始生成代码，类型: {} ({})", generationType.getValue(), generationType.getText());
            // 先使用固定的 appId (后续再整合到业务中)
            Long appId = 0L;
            // 调用流式代码生成
            Flux<String> codeStream = codeGeneratorFacade.generateAndSaveCodeStream(userMessage, generationType, appId);
            // 同步等待流式输出完成
            codeStream.blockLast(Duration.ofMinutes(10)); // 最多等待 10 分钟
            // 根据类型设置生成目录
            String generatedCodeDir = String.format("%s/%s_%s", AppConstant.CODE_OUTPUT_ROOT_DIR, getDirPrefix(generationType), appId);
            log.info("AI 代码生成完成，生成目录: {}", generatedCodeDir);

            // 更新状态
            context.setCurrentStep("代码生成");
            context.setGeneratedCodeDir(generatedCodeDir);
            return WorkflowContext.saveContext(context);
        });
    }

    /**
     * 根据代码生成类型获取目录前缀
     *
     * @param generationType 代码生成类型
     * @return 目录前缀
     */
    private static String getDirPrefix(CodeGenTypeEnum generationType) {
        switch (generationType) {
            case HTML:
            case MULTI_FILE:
                return CodeGenTypeEnum.HTML.getValue();
            case VUE_PROJECT:
                return CodeGenTypeEnum.VUE_PROJECT.getValue();
            case JAVA_PROJECT:
                return CodeGenTypeEnum.JAVA_PROJECT.getValue();
            default:
                return "unknown";
        }
    }

    /**
     * 构建用户消息
     *
     * @param context 工作流上下文
     * @return 用户消息
     */
    private static String buildUserMessage(WorkflowContext context) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(context.getOriginalPrompt());
        if (context.getErrorMessage() != null) {
            messageBuilder.append("\n\n注意：之前的实现存在以下问题，请修复：\n").append(context.getErrorMessage());
        }
        return messageBuilder.toString();
    }
}