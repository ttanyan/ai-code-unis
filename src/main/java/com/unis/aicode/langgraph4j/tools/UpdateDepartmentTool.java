package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.langgraph4j.tools.model.dto.DepartmentInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: UpdateDepartmentTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class UpdateDepartmentTool {

    /**
     * 华智更新BSS组织
     *
     * @param departmentInfo 组织信息
     * @return 更新后的组织信息
     */
    @Tool("华智更新BSS组织")
    public DepartmentInfo updateDepartment(DepartmentInfo departmentInfo) {
        // 模拟更新组织逻辑
        log.info("组织信息已更新: {}", departmentInfo.getOrgName());
        return departmentInfo;
    }
}