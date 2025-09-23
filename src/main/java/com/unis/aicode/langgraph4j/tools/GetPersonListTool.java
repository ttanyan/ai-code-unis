package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.langgraph4j.tools.model.dto.UserInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户列表工具类
 * 该工具类提供获取所有BSS用户列表的功能，供AI模型调用
 * 
 * @author Tan Lianwang
 * @title: GetPersonListTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class GetPersonListTool {

    /**
     * 华智获取所有BSS用户列表
     * 华智该方法用于获取系统中的所有用户信息列表
     * 
     * @return 用户信息列表
     */
    @Tool("获取所有BSS用户列表")
    public List<UserInfo> getPersonList() {
        // 模拟用户列表数据
        List<UserInfo> mockUserList = new ArrayList<>();
        
        // 创建模拟用户1
        UserInfo user1 = new UserInfo();
        user1.setUserId("1");
        user1.setUserCode("USER001");
        user1.setUserName("张三");
        user1.setGender(1);
        user1.setBirthday("1990-01-01");
        user1.setOrgIndex("ORG001");
        user1.setOrgId(1001);
        user1.setIndexPath("ROOT/ORG001");
        user1.setUserType("EMPLOYEE");
        user1.setOrgParentIndex("ROOT");
        user1.setParentId(0);
        
        // 创建模拟用户2
        UserInfo user2 = new UserInfo();
        user2.setUserId("2");
        user2.setUserCode("USER002");
        user2.setUserName("李四");
        user2.setGender(2);
        user2.setBirthday("1992-05-15");
        user2.setOrgIndex("ORG002");
        user2.setOrgId(1002);
        user2.setIndexPath("ROOT/ORG001/ORG002");
        user2.setUserType("MANAGER");
        user2.setOrgParentIndex("ORG001");
        user2.setParentId(1001);
        
        // 添加到列表
        mockUserList.add(user1);
        mockUserList.add(user2);
        
        return mockUserList;
    }
}