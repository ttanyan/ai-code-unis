package com.unis.aicode.langgraph4j.state;

import com.unis.aicode.langgraph4j.model.ImageCollectionPlan;
import com.unis.aicode.langgraph4j.model.ImageResource;
import com.unis.aicode.langgraph4j.model.QualityResult;
import com.unis.aicode.langgraph4j.tools.model.dto.DepartmentInfo;
import com.unis.aicode.langgraph4j.tools.model.dto.PersonInfo;
import com.unis.aicode.langgraph4j.tools.model.dto.UserInfo;
import com.unis.aicode.model.enums.CodeGenTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 工作流上下文 - 存储所有状态信息
 * 该类用于在LangGraph4j工作流执行过程中存储和传递状态信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowContext implements Serializable {

    /**
     * WorkflowContext 在 MessagesState 中的存储key
     */
    public static final String WORKFLOW_CONTEXT_KEY = "workflowContext";

    /**
     * 当前执行步骤
     * 记录工作流当前正在执行的步骤名称
     */
    private String currentStep;

    /**
     * 用户原始输入的提示词
     * 存储用户最初输入的需求描述
     */
    private String originalPrompt;

    /**
     * 图片资源字符串
     * 以字符串形式存储图片资源信息
     */
    private String imageListStr;

    /**
     * 图片资源列表
     * 以对象列表形式存储图片资源信息
     */
    private List<ImageResource> imageList;

    /**
     * 增强后的提示词
     * 经过AI增强和优化后的提示词
     */
    private String enhancedPrompt;

    /**
     * 代码生成类型
     * 指定要生成的代码类型（如Vue项目、React组件等）
     */
    private CodeGenTypeEnum generationType;

    /**
     * 生成的代码目录
     * 存储代码生成结果的目录路径
     */
    private String generatedCodeDir;

    /**
     * 构建成功的目录
     * 存储项目构建成功后的目录路径
     */
    private String buildResultDir;

    /**
     * 质量检查结果
     * 存储代码质量检查的结果
     */
    private QualityResult qualityResult;

    /**
     * 错误信息
     * 存储执行过程中发生的错误信息
     */
    private String errorMessage;

    /**
     * 图片收集计划
     * 存储图片收集的计划信息
     */
    private ImageCollectionPlan imageCollectionPlan;

    /**
     * 并发图片收集的中间结果字段
     * 用于存储不同类型图片收集的中间结果
     */
    private List<ImageResource> contentImages;
    private List<ImageResource> illustrations;
    private List<ImageResource> diagrams;
    private List<ImageResource> logos;
    // JAVA 项目
    /**
     * 用户列表
     * 存储从BSS系统获取的用户列表信息
     */
    private List<UserInfo> userList;
    
    /**
     * 部门列表
     * 存储从BSS系统获取的部门列表信息
     */
    private List<DepartmentInfo> departmentList;
    
    /**
     * MG人员列表
     * 存储从MG系统获取的人员列表信息
     */
    private List<PersonInfo> mgPersonList;



    @Serial
    private static final long serialVersionUID = 1L;

    // ========== 上下文操作方法 ==========

    /**
     * 从 MessagesState 中获取 WorkflowContext
     * 
     * @param state 消息状态对象
     * @return 工作流上下文对象
     */
    public static WorkflowContext getContext(MessagesState<String> state) {
        return (WorkflowContext) state.data().get(WORKFLOW_CONTEXT_KEY);
    }

    /**
     * 将 WorkflowContext 保存到 MessagesState 中
     * 
     * @param context 工作流上下文对象
     * @return 包含工作流上下文的映射表
     */
    public static Map<String, Object> saveContext(WorkflowContext context) {
        return Map.of(WORKFLOW_CONTEXT_KEY, context);
    }
}