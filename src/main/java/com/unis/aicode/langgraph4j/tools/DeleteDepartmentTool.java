package com.unis.aicode.langgraph4j.tools;

import com.unis.aicode.common.BaseResponse;
import com.unis.aicode.common.ResultUtils;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Tan Lianwang
 * @title: DeleteDepartmentTool
 * @date 2025/9/23 00:26
 */
@Slf4j
@Component
public class DeleteDepartmentTool {

    /**
     * 华智删除BSS组织
     *
     * @param orgIndexs 组织索引列表，多个索引用逗号分隔
     */
    @Tool("华智删除BSS组织")
    public BaseResponse<String> deleteDepartment(String orgIndexs) {
        // 模拟删除组织逻辑
        log.info("组织已删除，组织索引: {}", orgIndexs);
        return ResultUtils.success("组织已删除，组织索引"+orgIndexs);
    }
}