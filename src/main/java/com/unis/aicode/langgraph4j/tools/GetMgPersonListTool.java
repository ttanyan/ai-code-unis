package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.langgraph4j.tools.model.dto.PersonInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tan Lianwang
 * @title: GetMgPersonListTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class GetMgPersonListTool {

    /**
     * 华智获取所有MG人员列表
     *
     * @return MG人员列表
     */
    @Tool("华智获取所有MG人员列表")
    public List<PersonInfo> getMgPersonList() {
        // 模拟MG人员列表数据
        List<PersonInfo> mockMgPersonList = new ArrayList<>();

        // 创建模拟MG人员1
        PersonInfo person1 = new PersonInfo();
        person1.setUserCode("MG001");
        person1.setUserName("王五");
        person1.setGender(1);
        person1.setOrgIndex("ORG001");
        person1.setOrgId(1001);
        person1.setIndexPath("ROOT/ORG001");

        // 创建模拟MG人员2
        PersonInfo person2 = new PersonInfo();
        person2.setUserCode("MG002");
        person2.setUserName("赵六");
        person2.setGender(2);
        person2.setOrgIndex("ORG002");
        person2.setOrgId(1002);
        person2.setIndexPath("ROOT/ORG001/ORG002");

        // 添加到列表
        mockMgPersonList.add(person1);
        mockMgPersonList.add(person2);

        return mockMgPersonList;
    }
}