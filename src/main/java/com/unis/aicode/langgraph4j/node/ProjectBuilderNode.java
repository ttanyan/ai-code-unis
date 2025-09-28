package com.unis.aicode.langgraph4j.node;

import com.unis.aicode.core.builder.JavaProjectBuilder;
import com.unis.aicode.core.builder.VueProjectBuilder;
import com.unis.aicode.exception.BusinessException;
import com.unis.aicode.exception.ErrorCode;
import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.model.enums.CodeGenTypeEnum;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.io.File;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

/**
 * 项目构建节点
 */
@Slf4j
public class ProjectBuilderNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            log.info("执行节点: 项目构建");

            // 获取必要的参数
            String generatedCodeDir = context.getGeneratedCodeDir();
            CodeGenTypeEnum generationType = context.getGenerationType();
            String buildResultDir;
            
            try {
                // 根据不同的代码生成类型使用相应的构建器
                switch (generationType) {
                    case VUE_PROJECT:
                        VueProjectBuilder vueBuilder = SpringContextUtil.getBean(VueProjectBuilder.class);
                        // 执行 Vue 项目构建（npm install + npm run build）
                        boolean vueBuildSuccess = vueBuilder.buildProject(generatedCodeDir);
                        if (vueBuildSuccess) {
                            // 构建成功，返回 dist 目录路径
                            buildResultDir = generatedCodeDir + File.separator + "dist";
                            log.info("Vue 项目构建成功，dist 目录: {}", buildResultDir);
                        } else {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Vue 项目构建失败");
                        }
                        break;
                        
                    case JAVA_PROJECT:
                        JavaProjectBuilder javaBuilder = SpringContextUtil.getBean(JavaProjectBuilder.class);
                        // 执行 Java 项目构建
                        boolean javaBuildSuccess = javaBuilder.buildProject(generatedCodeDir);
                        if (javaBuildSuccess) {
                            // 构建成功，返回 dist 目录路径
                            buildResultDir = generatedCodeDir + File.separator + "dist";
                            log.info("Java 项目构建成功，dist 目录: {}", buildResultDir);
                        } else {
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "Java 项目构建失败");
                        }
                        break;
                        
                    default:
                        // 对于 HTML 和 MULTI_FILE 类型，直接返回生成目录
                        buildResultDir = generatedCodeDir;
                        log.info("非工程类型项目，无需构建，直接使用生成目录: {}", buildResultDir);
                        break;
                }
            } catch (Exception e) {
                log.error("项目构建异常: {}", e.getMessage(), e);
                buildResultDir = generatedCodeDir; // 异常时返回原路径
            }
            
            // 更新状态
            context.setCurrentStep("项目构建");
            context.setBuildResultDir(buildResultDir);
            log.info("项目构建节点完成，最终目录: {}", buildResultDir);
            return WorkflowContext.saveContext(context);
        });
    }
}