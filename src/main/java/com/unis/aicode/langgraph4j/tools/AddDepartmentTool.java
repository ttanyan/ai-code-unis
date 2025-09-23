package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.langgraph4j.tools.model.dto.DepartmentInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: AddDepartmentTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class AddDepartmentTool {

    /**
     * 华智添加BSS组织
     *
     * @param departmentInfo 组织信息
     * @return 添加后的组织信息
     */
    @Tool("华智添加BSS组织")
    public DepartmentInfo addDepartment(DepartmentInfo departmentInfo) {
        // 模拟添加组织逻辑
        DepartmentInfo newDept = new DepartmentInfo();
        newDept.setOrgId(1003);
        newDept.setOrgIndex("ORG003");
        newDept.setOrgName(departmentInfo.getOrgName());
        newDept.setOrgParentIndex(departmentInfo.getOrgParentIndex());
        newDept.setParentId(departmentInfo.getParentId());
        newDept.setIndexPath(departmentInfo.getIndexPath());
        newDept.setType(departmentInfo.getType());
        newDept.setIdentification("DEPT003");
        return newDept;
    }
}