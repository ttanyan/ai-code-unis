package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import com.unis.aicode.langgraph4j.tools.model.dto.PersonInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: AddMgPersonTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class AddMgPersonTool {

    /**
     * 华智添加MG人员
     *
     * @param personInfo 人员信息
     */
    @Tool("华智添加MG人员")
    public BaseResponse<String> addMgPerson(PersonInfo personInfo) {
        // 模拟添加MG人员逻辑
        log.info("MG人员已添加: {}", personInfo.toString());
        return ResultUtils.success("MG人员已添加");
    }
}