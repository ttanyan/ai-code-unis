package com.unis.aicode.langgraph4j.tools.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -7604468547838752573L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 组织索引
     */
    private String orgIndex;

    /**
     * 组织ID
     */
    private Integer orgId;

    /**
     * 组织路径
     */
    private String indexPath;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 父级组织索引
     */
    private String orgParentIndex;

    /**
     * 父级组织ID
     */
    private Integer parentId;
}