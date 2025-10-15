package com.unis.aicode.ai.tools.javatools;

import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: Test
 * @date 2025/10/14 19:37
 */
@Slf4j
@Component
public class TestJavaTool extends JavaTool{
    @Override
    public String getToolName() {
        return "";
    }

    @Override
    public String getDisplayName() {
        return "";
    }

    @Override
    public String generateToolExecutedResult(JSONObject arguments) {
        return "";
    }
}
