package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import com.unis.aicode.langgraph4j.tools.model.dto.PersonInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: UpdateMgPersonTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class UpdateMgPersonTool {

    /**
     * 华智更新MG人员
     *
     * @param personInfo 人员信息
     */
    @Tool("华智更新MG人员")
    public BaseResponse<String> updateMgPerson(PersonInfo personInfo) {
        // 模拟更新MG人员逻辑
        log.info("MG人员信息已更新: {}", personInfo.toString());
        return ResultUtils.success("MG人员信息已更新");
    }
}