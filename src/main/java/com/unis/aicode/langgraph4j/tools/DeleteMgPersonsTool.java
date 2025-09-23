package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tan Lianwang
 * @title: DeleteMgPersonsTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class DeleteMgPersonsTool {

    /**
     * 华智删除MG人员
     *
     * @param userIds 人员ID列表
     */
    @Tool("华智删除MG人员")
    public BaseResponse<String> deleteMgPersons(List<String> userIds) {
        // 模拟删除MG人员逻辑
        log.info("MG人员已删除，用户ID: {}", userIds);
        return ResultUtils.success("MG人员已删除，用户ID:"+ userIds);
    }
}