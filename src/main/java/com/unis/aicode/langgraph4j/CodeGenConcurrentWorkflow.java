package com.unis.aicode.langgraph4j;

import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.unis.aicode.exception.BusinessException;
import com.unis.aicode.exception.ErrorCode;
import com.unis.aicode.langgraph4j.model.QualityResult;
import com.unis.aicode.langgraph4j.node.*;
import com.unis.aicode.langgraph4j.node.concurrent.*;
import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.model.enums.CodeGenTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.*;
import org.bsc.langgraph4j.prebuilt.MessagesState;
import org.bsc.langgraph4j.prebuilt.MessagesStateGraph;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import static org.bsc.langgraph4j.StateGraph.END;
import static org.bsc.langgraph4j.StateGraph.START;
import static org.bsc.langgraph4j.action.AsyncEdgeAction.edge_async;

/**
 * 并发执行的代码生成工作流
 * 该工作流结合了图像收集
 */
@Slf4j
public class CodeGenConcurrentWorkflow {

    /**
     * 创建并发工作流
     * 工作流包含以下主要功能模块：
     * 1. 图像收集模块：收集内容图片、插图、图表和Logo
     * 2. 代码生成模块：提示词增强、路由决策、代码生成、质量检查和项目构建
     * 
     * @return 编译后的工作流图
     */
    public CompiledGraph<MessagesState<String>> createWorkflow() {
        try {
            return new MessagesStateGraph<String>()
                    // 添加节点
                    .addNode("image_plan", ImagePlanNode.create()) // 图像收集计划节点
                    .addNode("prompt_enhancer", PromptEnhancerNode.create()) // 提示词增强节点
                    .addNode("router", RouterNode.create()) // 路由决策节点
                    .addNode("code_generator", CodeGeneratorNode.create()) // 代码生成节点
                    .addNode("code_quality_check", CodeQualityCheckNode.create()) // 代码质量检查节点
                    .addNode("project_builder", ProjectBuilderNode.create()) // 项目构建节点

                    // 添加并发图片收集节点
                    .addNode("content_image_collector", ContentImageCollectorNode.create()) // 内容图片收集节点
                    .addNode("illustration_collector", IllustrationCollectorNode.create()) // 插图收集节点
                    .addNode("diagram_collector", DiagramCollectorNode.create()) // 图表收集节点
                    .addNode("logo_collector", LogoCollectorNode.create()) // Logo收集节点
                    .addNode("image_aggregator", ImageAggregatorNode.create()) // 图像聚合节点

                    // 添加边
                    .addEdge(START, "image_plan") // 起始节点连接到图像计划节点

                    // 并发分支：从计划节点分发到各个收集节点
                    .addEdge("image_plan", "content_image_collector") // 图像计划分发到内容图片收集
                    .addEdge("image_plan", "illustration_collector") // 图像计划分发到插图收集
                    .addEdge("image_plan", "diagram_collector") // 图像计划分发到图表收集
                    .addEdge("image_plan", "logo_collector") // 图像计划分发到Logo收集

                    // 汇聚：所有收集节点都汇聚到聚合器
                    .addEdge("content_image_collector", "image_aggregator") // 内容图片收集结果汇聚到图像聚合器
                    .addEdge("illustration_collector", "image_aggregator") // 插图收集结果汇聚到图像聚合器
                    .addEdge("diagram_collector", "image_aggregator") // 图表收集结果汇聚到图像聚合器
                    .addEdge("logo_collector", "image_aggregator") // Logo收集结果汇聚到图像聚合器

                    // 继续串行流程
                    .addEdge("image_aggregator", "prompt_enhancer") // 图像聚合后进行提示词增强
                    .addEdge("prompt_enhancer", "router") // 提示词增强后进行路由决策
                    .addEdge("router", "code_generator") // 路由决策后进行代码生成
                    .addEdge("code_generator", "code_quality_check") // 代码生成后进行质量检查

                    // 质检条件边
                    .addConditionalEdges("code_quality_check",
                            edge_async(this::routeAfterQualityCheck),
                            Map.of(
                                    "build", "project_builder", // 质检通过且为Vue项目时构建项目
                                    "skip_build", END, // 质检通过但非Vue项目时直接结束
                                    "fail", "code_generator" // 质检失败时重新生成代码
                            ))
                    
                    .addEdge("project_builder", END) // 项目构建完成后结束
                    .compile();
        } catch (GraphStateException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "并发工作流创建失败");
        }
    }

    /**
     * 执行并发工作流
     * 
     * @param originalPrompt 用户输入的原始提示词
     * @return 工作流执行完成后的上下文状态
     */
    public WorkflowContext executeWorkflow(String originalPrompt) {
        CompiledGraph<MessagesState<String>> workflow = createWorkflow();
        WorkflowContext initialContext = WorkflowContext.builder()
                .originalPrompt(originalPrompt)
                .currentStep("初始化")
                .build();
        GraphRepresentation graph = workflow.getGraph(GraphRepresentation.Type.MERMAID);
        log.info("并发工作流图:\n{}", graph.content());
        log.info("开始执行并发代码生成工作流");
        WorkflowContext finalContext = null;
        int stepCounter = 1;
        // 配置并发执行
        ExecutorService pool = ExecutorBuilder.create()
                .setCorePoolSize(10)
                .setMaxPoolSize(20)
                .setWorkQueue(new LinkedBlockingQueue<>(100))
                .setThreadFactory(ThreadFactoryBuilder.create().setNamePrefix("Parallel-Image-Collect").build())
                .build();
        RunnableConfig runnableConfig = RunnableConfig.builder()
                .addParallelNodeExecutor("image_plan", pool)
                .build();
        for (NodeOutput<MessagesState<String>> step : workflow.stream(
                Map.of(WorkflowContext.WORKFLOW_CONTEXT_KEY, initialContext),
                runnableConfig)) {
            log.info("--- 第 {} 步完成 ---", stepCounter);
            WorkflowContext currentContext = WorkflowContext.getContext(step.state());
            if (currentContext != null) {
                finalContext = currentContext;
                log.info("当前步骤上下文: {}", currentContext);
            }
            stepCounter++;
        }
        log.info("并发代码生成工作流执行完成！");
        return finalContext;
    }

    /**
     * 路由函数：根据质检结果决定下一步
     * 
     * @param state 当前状态
     * @return 下一步要执行的节点名称
     */
    private String routeAfterQualityCheck(MessagesState<String> state) {
        WorkflowContext context = WorkflowContext.getContext(state);
        QualityResult qualityResult = context.getQualityResult();

        if (qualityResult == null || !qualityResult.getIsValid()) {
            log.error("代码质检失败，需要重新生成代码");
            return "fail";
        }
        log.info("代码质检通过，继续后续流程");
        CodeGenTypeEnum generationType = context.getGenerationType();
        if (generationType == CodeGenTypeEnum.VUE_PROJECT) {
            return "build";
        } else {
            return "skip_build";
        }
    }
} 