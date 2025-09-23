package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: DeletePersonsTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class DeletePersonsTool {

    /**
     * 华智删除BSS用户
     *
     * @param userIds 用户ID列表，多个ID用逗号分隔
     */
    @Tool("华智删除BSS用户")
    public BaseResponse<String> deletePersons(String userIds) {
        // 模拟删除用户逻辑
        log.info("用户已删除，用户ID: {}", userIds);
        return ResultUtils.success("用户已删除");
    }
}