package com.unis.aicode.ai.tools;

import cn.hutool.json.JSONObject;
import com.unis.aicode.constant.AppConstant;
import com.unis.aicode.model.enums.CodeGenTypeEnum;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件读取工具
 * 支持 AI 通过工具调用的方式读取文件内容
 */
@Slf4j
@Component
public class FileReadTool extends BaseTool {

    @Tool("读取指定路径的文件内容")
    public String readFile(
            @P("文件的相对路径")
            String relativeFilePath,
            @P("代码生成类型，可选值：html, multi_file, vue_project, java_project")
            String codeGenType,
            @ToolMemoryId Long appId
    ) {
        try {
            Path path = Paths.get(relativeFilePath);
            if (!path.isAbsolute()) {
                // 根据代码生成类型确定项目目录名称
                String projectDirName = getProjectDirName(codeGenType, appId);
                Path projectRoot = Paths.get(projectDirName);
                path = projectRoot.resolve(relativeFilePath);
            }
            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                return "错误：文件不存在或不是文件 - " + relativeFilePath;
            }
            return Files.readString(path);
        } catch (IOException e) {
            String errorMessage = "读取文件失败: " + relativeFilePath + ", 错误: " + e.getMessage();
            log.error(errorMessage, e);
            return errorMessage;
        }
    }

    /**
     * 根据代码生成类型和应用ID获取项目目录名称
     *
     * @param codeGenType 代码生成类型
     * @param appId       应用ID
     * @return 项目目录名称
     */
    private String getProjectDirName(String codeGenType, Long appId) {
        CodeGenTypeEnum typeEnum = CodeGenTypeEnum.getEnumByValue(codeGenType);
        String baseDir = AppConstant.CODE_OUTPUT_ROOT_DIR;
        
        if (typeEnum == null) {
            // 默认使用Vue项目目录
            return baseDir + AppConstant.VUE_PROJECT_DIR + appId;
        }
        
        switch (typeEnum) {
            case HTML:
            case MULTI_FILE:
                return baseDir + AppConstant.HTML_DIR + appId;
            case JAVA_PROJECT:
                return baseDir + AppConstant.JAVA_PROJECT_DIR + appId;
            case VUE_PROJECT:
            default:
                return baseDir + AppConstant.VUE_PROJECT_DIR + appId;
        }
    }

    @Override
    public String getToolName() {
        return "readFile";
    }

    @Override
    public String getDisplayName() {
        return "读取文件";
    }

    @Override
    public String generateToolExecutedResult(JSONObject arguments) {
        String relativeFilePath = arguments.getStr("relativeFilePath");
        return String.format("[工具调用] %s %s", getDisplayName(), relativeFilePath);
    }
}