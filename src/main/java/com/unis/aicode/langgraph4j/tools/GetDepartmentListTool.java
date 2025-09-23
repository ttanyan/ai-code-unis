package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.langgraph4j.tools.model.dto.DepartmentInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tan Lianwang
 * @title: GetDepartmentListTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class GetDepartmentListTool {

    /**
     * 华智获取所有BSS组织列表
     *
     * @return 组织列表
     */
    @Tool("华智获取所有BSS组织列表")
    public List<DepartmentInfo> getDepartmentList() {
        // 模拟组织列表数据
        List<DepartmentInfo> mockDepartmentList = new ArrayList<>();
        
        // 创建模拟组织1
        DepartmentInfo dept1 = new DepartmentInfo();
        dept1.setOrgId(1001);
        dept1.setOrgIndex("ORG001");
        dept1.setOrgName("研发部");
        dept1.setOrgParentIndex("ROOT");
        dept1.setParentId(0);
        dept1.setIndexPath("ROOT/ORG001");
        dept1.setType(1);
        dept1.setIdentification("DEPT001");
        
        // 创建模拟组织2
        DepartmentInfo dept2 = new DepartmentInfo();
        dept2.setOrgId(1002);
        dept2.setOrgIndex("ORG002");
        dept2.setOrgName("测试部");
        dept2.setOrgParentIndex("ORG001");
        dept2.setParentId(1001);
        dept2.setIndexPath("ROOT/ORG001/ORG002");
        dept2.setType(2);
        dept2.setIdentification("DEPT002");
        
        // 添加到列表
        mockDepartmentList.add(dept1);
        mockDepartmentList.add(dept2);
        
        return mockDepartmentList;
    }
}