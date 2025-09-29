package com.unis.aicode.langgraph4j;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CodeGenConcurrentWorkflowTest{


    @Test
    void testConcurrentWorkflow() {
        WorkflowContext result = new CodeGenConcurrentWorkflow().executeWorkflow("创建一个技术博客网站，需要展示编程教程和系统架构");
        Assertions.assertNotNull(result);
        System.out.println("生成类型: " + result.getGenerationType());
        System.out.println("生成的代码目录: " + result.getGeneratedCodeDir());
        System.out.println("构建结果目录: " + result.getBuildResultDir());
        System.out.println("收集的图片数量: " + (result.getImageList() != null ? result.getImageList().size() : 0));
    }

    @Test
    void testEcommerceWorkflow() {
        WorkflowContext result = new CodeGenConcurrentWorkflow().executeWorkflow("创建一个电子商务网站，需要商品展示、购物车和支付功能");
        Assertions.assertNotNull(result);
        System.out.println("生成类型: " + result.getGenerationType());
        System.out.println("生成的代码目录: " + result.getGeneratedCodeDir());
        System.out.println("收集的图片数量: " + (result.getImageList() != null ? result.getImageList().size() : 0));
    }


    @Test
    void testPersonWorkflow() {
        WorkflowContext result = new PersonOrganizationWorkflow().executeWorkflow("查看当前所有用户，然后添加一个新用户张三，最后同步数据");
        Assertions.assertNotNull(result);
        System.out.println("当前步骤: " + result.getCurrentStep());
        System.out.println("增强后的提示词: " + result.getEnhancedPrompt());
        System.out.println("用户列表数量: " + (result.getUserList() != null ? result.getUserList().size() : 0));
    }
}