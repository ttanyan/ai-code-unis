package com.unis.aicode.ai.tools;

import cn.hutool.core.io.FileUtil;
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
import java.nio.file.StandardOpenOption;

/**
 * 文件写入工具
 * 支持 AI 通过工具调用的方式写入文件
 */
@Slf4j
@Component
public class FileWriteTool extends BaseTool {

    @Tool("写入文件到指定路径")
    public String writeFile(
            @P("文件的相对路径")
            String relativeFilePath,
            @P("要写入文件的内容")
            String content,
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
            // 创建父目录（如果不存在）
            Path parentDir = path.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }
            // 写入文件内容
            Files.write(path, content.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            log.info("成功写入文件: {}", path.toAbsolutePath());
            // 注意要返回相对路径，不能让 AI 把文件绝对路径返回给用户
            return "文件写入成功: " + relativeFilePath;
        } catch (IOException e) {
            String errorMessage = "文件写入失败: " + relativeFilePath + ", 错误: " + e.getMessage();
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
                return baseDir + "/html_" + appId;
            case VUE_PROJECT:
                return baseDir + AppConstant.VUE_PROJECT_DIR + appId;
            case JAVA_PROJECT:
                return baseDir + AppConstant.JAVA_PROJECT_DIR + appId;
            default:
                return baseDir + AppConstant.VUE_PROJECT_DIR + appId;
        }
    }

    @Override
    public String getToolName() {
        return "writeFile";
    }

    @Override
    public String getDisplayName() {
        return "写入文件";
    }

    @Override
    public String generateToolExecutedResult(JSONObject arguments) {
        String relativeFilePath = arguments.getStr("relativeFilePath");
        String suffix = FileUtil.getSuffix(relativeFilePath);
        String content = arguments.getStr("content");
        return String.format("""
                        [工具调用] %s %s
                        ```%s
                        %s
                        ```
                        """, getDisplayName(), relativeFilePath, suffix, content);
    }
}