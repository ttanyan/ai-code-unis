package com.unis.aicode.langgraph4j.tools.model.dto;

import dev.langchain4j.agent.tool.Tool;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * 部门信息DTO
 */
@Data
public class DepartmentInfo  implements Serializable {
    private static final long serialVersionUID = -3265238966508970689L;
    /**
     * 组织ID
     */
    private Integer orgId;

    /**
     * 组织索引
     */
    private String orgIndex;

    /**
     * 组织名称
     */
    private String orgName;

    /**
     * 父级组织索引
     */
    private String orgParentIndex;

    /**
     * 父级组织ID
     */
    private Integer parentId;

    /**
     * 组织路径
     */
    private String indexPath;

    /**
     * 组织类型
     */
    private Integer type;

    /**
     * 标识
     */
    private String identification;
}