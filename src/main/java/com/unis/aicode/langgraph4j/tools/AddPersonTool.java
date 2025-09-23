package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.langgraph4j.tools.model.dto.UserInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: AddPersonTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class AddPersonTool {

    /**
     * 华智添加BSS用户
     *
     * @param userInfo 用户信息
     * @return 添加后的用户信息
     */
    @Tool("华智添加BSS用户")
    public UserInfo addPerson(UserInfo userInfo) {
        // 模拟添加用户逻辑
        UserInfo newUser = new UserInfo();
        newUser.setUserId("3");
        newUser.setUserCode(userInfo.getUserCode());
        newUser.setUserName(userInfo.getUserName());
        newUser.setGender(userInfo.getGender());
        newUser.setBirthday(userInfo.getBirthday());
        newUser.setOrgIndex(userInfo.getOrgIndex());
        newUser.setOrgId(userInfo.getOrgId());
        newUser.setIndexPath(userInfo.getIndexPath());
        newUser.setUserType(userInfo.getUserType());
        newUser.setOrgParentIndex(userInfo.getOrgParentIndex());
        newUser.setParentId(userInfo.getParentId());
        return newUser;
    }
}