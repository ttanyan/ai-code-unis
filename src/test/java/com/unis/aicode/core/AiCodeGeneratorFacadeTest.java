package com.unis.aicode.core;

import com.unis.aicode.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.io.File;
import java.util.List;

@SpringBootTest
class AiCodeGeneratorFacadeTest {

    @Resource
    private AiCodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    void generateAndSaveCode() {
        File file = aiCodeGeneratorFacade.generateAndSaveCode("生成一个登录页面，总共不超过 20 行代码", CodeGenTypeEnum.MULTI_FILE, 1L);
        Assertions.assertNotNull(file);
    }

    @Test
    void generateAndSaveCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateAndSaveCodeStream("生成一个登录页面，总共不超过 20 行代码", CodeGenTypeEnum.HTML, 1L);
        // 阻塞等待所有数据收集完成
        List<String> result = codeStream.collectList().block();
        // 验证结果
        Assertions.assertNotNull(result);
        // 拼接字符串，得到完整内容
        String completeContent = String.join("", result);
        Assertions.assertNotNull(completeContent);
    }

    @Test
    void generateVueProjectCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateAndSaveCodeStream(
                "简单的任务记录网站，总代码量不超过 200 行",
                CodeGenTypeEnum.VUE_PROJECT, 1L);
        // 阻塞等待所有数据收集完成
        List<String> result = codeStream.collectList().block();
        // 验证结果
        Assertions.assertNotNull(result);
        String completeContent = String.join("", result);
        Assertions.assertNotNull(completeContent);
    }


    @Test
    void generateJavaProjectCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateAndSaveCodeStream(
                "获取分发的所有单位信息 https://cloud.moedu.com/openapi/ecp-datacenter-svc/school/getChildByEduOrgId\n" +
                        "根据部门编码获取部门信息 https://cloud.moedu.com/openapi/ecp-datacenter-svc/department/getDetailsById\n" +
                        "根据部门编码获取部门下的用户信息 https://cloud.moedu.com/openapi/ecp-datacenter-svc/department/getUserById\n" +
                        "\n" +
                        "利用上面的接口获取信息同步到华智平台\n" +
                        "获取单位下所有部门信息，整理组织树，同步至华智平台\n" +
                        "根据单位编码获取所有行政组织人员，同步至华智平台对应绝织下根据单位编码获取普通教职工，同步至华智平台对应组织下\n" +
                        "根据单位编码获取外聘服务人员，同步至华智平台对应组织下根据单位编码获取其它人员，同步至华智平台对应组织下",
                CodeGenTypeEnum.JAVA_PROJECT, 1L);
        // 阻塞等待所有数据收集完成
        List<String> result = codeStream.collectList().block();
        // 验证结果
        Assertions.assertNotNull(result);
        String completeContent = String.join("", result);
        Assertions.assertNotNull(completeContent);
    }
}
