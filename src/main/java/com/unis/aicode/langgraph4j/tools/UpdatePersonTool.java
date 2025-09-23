package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import com.unis.aicode.langgraph4j.tools.model.dto.UserInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: UpdatePersonTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class UpdatePersonTool {

    /**
     * 华智更新BSS用户
     *
     * @param userInfo 用户信息
     */
    @Tool("华智更新BSS用户")
    public BaseResponse<String> updatePerson(UserInfo userInfo) {
        // 模拟更新用户逻辑
        new UserInfo().setUserName("阿里云");
        log.info("用户信息已更新: {}", userInfo.getUserName());
        return ResultUtils.success("用户信息已更新");
    }
}