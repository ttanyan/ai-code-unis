package com.unis.aicode.langgraph4j.tools.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * 人员信息DTO
 */
@Data
public class PersonInfo implements Serializable {
    private static final long serialVersionUID = 1057664453409156825L;
    /**
     * 人员ID
     */
    private String id;

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
     * 身份证号
     */
    private String identityNo;

    /**
     * 身份证类型
     */
    private String identityType;

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
     * 用户对象类型
     */
    private String userObjectType;

    /**
     * 电话号码
     */
    private String cellPhone;

    /**
     * 用户图像
     */
    private String userImage;
}