package com.unis.aicode.monitor;

import lombok.extern.slf4j.Slf4j;

/**
 * 监控上下文持有者（同线程内共享）
 */
@Slf4j
public class MonitorContextHolder {

    private static final InheritableThreadLocal<MonitorContext> CONTEXT_HOLDER = new InheritableThreadLocal<MonitorContext>() {
        @Override
        protected MonitorContext childValue(MonitorContext parentValue) {
            // 确保子线程能继承父线程的上下文值
            return parentValue;
        }
        
        @Override
        protected MonitorContext initialValue() {
            // 返回默认值，避免返回null
            return null;
        }
    };

    /**
     * 设置监控上下文
     */
    public static void setContext(MonitorContext context) {
        CONTEXT_HOLDER.set(context);
    }

    /**
     * 获取当前监控上下文
     */
    public static MonitorContext getContext() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 清除监控上下文
     */
    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }
}