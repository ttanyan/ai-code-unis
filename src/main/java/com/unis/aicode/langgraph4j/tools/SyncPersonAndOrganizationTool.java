package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: SyncPersonAndOrganizationTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class SyncPersonAndOrganizationTool {

    /**
     * 华智同步人员和组织信息
     */
    @Tool("华智同步人员和组织信息")
    public BaseResponse<String> syncPersonAndOrganization() {
        // 模拟同步逻辑
        log.info("人员和组织信息同步完成");
       return ResultUtils.success("人员和组织信息同步完成");
    }
}