package com.unis.aicode;

import com.unis.aicode.monitor.MonitorContext;
import com.unis.aicode.monitor.MonitorContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Tan Lianwang
 * @title: BaseTest
 * @date 2025/9/23 01:30
 */
@SpringBootTest
public class BaseTest {

    @BeforeEach
    void setUp() {
        MonitorContext monitorContext = MonitorContext.builder()
                .userId("326281077753479168")
                .appId("326306584897363968")
                .build();
        MonitorContextHolder.setContext(monitorContext);
    }

    // 在每个测试方法之后执行
    @AfterEach
    void tearDown() {
        // 清除监控上下文
        MonitorContextHolder.clearContext();
    }
}
