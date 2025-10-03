package com.unis.aicode.ai.tools;

import com.unis.aicode.ai.tools.javatools.JavaTool;
import com.unis.aicode.model.enums.CodeGenTypeEnum;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 工具管理器
 * 统一管理所有工具，提供根据名称获取工具的功能
 */
@Slf4j
@Component
public class ToolManager {

    /**
     * 工具名称到工具实例的映射
     */
    private final Map<String, BaseTool> toolMap = new HashMap<>();

    /**
     * 自动注入所有工具
     */
    @Resource
    private BaseTool[] tools;

    @Resource
    private JavaTool[] javaTools;

    /**
     * 初始化工具映射
     */
    @PostConstruct
    public void initTools() {
        for (BaseTool tool : tools) {
            toolMap.put(tool.getToolName(), tool);
            log.info("注册工具: {} -> {}", tool.getToolName(), tool.getDisplayName());
        }
        for (BaseTool tool : javaTools) {
            toolMap.put(tool.getToolName(), tool);
            log.info("注册Java工具: {} -> {}", tool.getToolName(), tool.getDisplayName());
        }
        log.info("工具管理器初始化完成，共注册 {} 个工具", toolMap.size());
    }


    /**
     * 根据工具名称获取工具实例
     *
     * @param toolName 工具英文名称
     * @return 工具实例
     */
    public BaseTool getTool(String toolName) {
        return toolMap.get(toolName);
    }

    /**
     * 获取已注册的工具集合
     *
     * @return 工具实例集合
     */
    public BaseTool[] getAllTools() {
        return tools;
    }
    
    /**
     * 根据代码生成类型获取相应的工具集合
     * 
     * @param typeEnum 代码生成类型
     * @return 工具实例集合
     */
    public BaseTool[] getToolsByCodeType(CodeGenTypeEnum typeEnum) {
        switch (typeEnum) {
            case CodeGenTypeEnum.VUE_PROJECT:
                return tools;
            case CodeGenTypeEnum.JAVA_PROJECT:
                return javaTools;
            default:
                return getAllTools();
        }
    }
}