package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: TriggerSyncTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class TriggerSyncTool {

    /**
     * 华智手动触发同步
     */
    @Tool("华智手动触发同步")
    public BaseResponse<String> triggerSync() {
        // 模拟触发同步逻辑
        log.info("手动同步已触发");
        return ResultUtils.success("手动同步已触发");
    }
}