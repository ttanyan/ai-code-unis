package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import com.unis.aicode.langgraph4j.tools.model.dto.DepartmentInfo;
import com.unis.aicode.langgraph4j.tools.model.dto.PersonInfo;
import com.unis.aicode.langgraph4j.tools.model.dto.UserInfo;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tan Lianwang
 * @title: DepartmentPersonTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class DepartmentPersonTool {

    /**
     * 获取所有BSS用户列表
     *
     * @return 用户列表
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

    /**
     * 添加BSS用户
     *
     * @param userInfo 用户信息
     * @return 添加后的用户信息
     */
    @Tool("添加BSS用户")
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

    /**
     * 更新BSS用户
     *
     * @param userInfo 用户信息
     */
    @Tool("更新BSS用户")
    public BaseResponse<String> updatePerson(UserInfo userInfo) {
        // 模拟更新用户逻辑
        new UserInfo().setUserName("阿里云");
        log.info("用户信息已更新: {}", userInfo.getUserName());
        return ResultUtils.success("用户信息已更新");
    }

    /**
     * 删除BSS用户
     *
     * @param userIds 用户ID列表，多个ID用逗号分隔
     */
    @Tool("删除BSS用户")
    public BaseResponse<String> deletePersons(String userIds) {
        // 模拟删除用户逻辑
        log.info("用户已删除，用户ID: {}", userIds);
        return ResultUtils.success("用户已删除");
    }

    /**
     * 获取所有BSS组织列表
     *
     * @return 组织列表
     */
    @Tool("获取所有BSS组织列表")
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

    /**
     * 添加BSS组织
     *
     * @param departmentInfo 组织信息
     * @return 添加后的组织信息
     */
    @Tool("添加BSS组织")
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

    /**
     * 更新BSS组织
     *
     * @param departmentInfo 组织信息
     * @return 更新后的组织信息
     */
    @Tool("更新BSS组织")
    public DepartmentInfo updateDepartment(DepartmentInfo departmentInfo) {
        // 模拟更新组织逻辑
        log.info("组织信息已更新: {}", departmentInfo.getOrgName());
        return departmentInfo;
    }

    /**
     * 删除BSS组织
     *
     * @param orgIndexs 组织索引列表，多个索引用逗号分隔
     */
    @Tool("删除BSS组织")
    public BaseResponse<String> deleteDepartment(String orgIndexs) {
        // 模拟删除组织逻辑
        log.info("组织已删除，组织索引: {}", orgIndexs);
        return ResultUtils.success("组织已删除，组织索引"+orgIndexs);
    }

    /**
     * 获取所有MG人员列表
     *
     * @return MG人员列表
     */
    @Tool("获取所有MG人员列表")
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

    /**
     * 添加MG人员
     *
     * @param personInfo 人员信息
     */
    @Tool("添加MG人员")
    public BaseResponse<String> addMgPerson(PersonInfo personInfo) {
        // 模拟添加MG人员逻辑
        log.info("MG人员已添加: {}", personInfo.toString());
        return ResultUtils.success("MG人员已添加");
        
    }

    /**
     * 更新MG人员
     *
     * @param personInfo 人员信息
     */
    @Tool("更新MG人员")
    public BaseResponse<String> updateMgPerson(PersonInfo personInfo) {
        // 模拟更新MG人员逻辑
        log.info("MG人员信息已更新: {}", personInfo.toString());
        return ResultUtils.success("MG人员信息已更新");
    }

    /**
     * 删除MG人员
     *
     * @param userIds 人员ID列表
     */
    @Tool("删除MG人员")
    public BaseResponse<String> deleteMgPersons(List<String> userIds) {
        // 模拟删除MG人员逻辑
        log.info("MG人员已删除，用户ID: {}", userIds);
        return ResultUtils.success("MG人员已删除，用户ID:"+ userIds);
    }

    /**
     * 同步人员和组织信息
     */
    @Tool("同步人员和组织信息")
    public BaseResponse<String> syncPersonAndOrganization() {
        // 模拟同步逻辑
        log.info("人员和组织信息同步完成");
       return ResultUtils.success("人员和组织信息同步完成");
    }

    /**
     * 手动触发同步
     */
    @Tool("手动触发同步")
    public BaseResponse<String> triggerSync() {
        // 模拟触发同步逻辑
        log.info("手动同步已触发");
        return ResultUtils.success("手动同步已触发");
    }
}