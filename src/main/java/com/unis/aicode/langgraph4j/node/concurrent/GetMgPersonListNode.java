package com.unis.aicode.langgraph4j.node.concurrent;

import com.unis.aicode.langgraph4j.state.WorkflowContext;
import com.unis.aicode.langgraph4j.tools.GetMgPersonListTool;
import com.unis.aicode.langgraph4j.tools.model.dto.PersonInfo;
import com.unis.aicode.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.bsc.langgraph4j.action.AsyncNodeAction;
import org.bsc.langgraph4j.prebuilt.MessagesState;

import java.util.List;

import static org.bsc.langgraph4j.action.AsyncNodeAction.node_async;

@Slf4j
public class GetMgPersonListNode {

    public static AsyncNodeAction<MessagesState<String>> create() {
        return node_async(state -> {
            WorkflowContext context = WorkflowContext.getContext(state);
            try {
                GetMgPersonListTool getMgPersonListTool = SpringContextUtil.getBean(GetMgPersonListTool.class);
                log.info("GetMgPersonListTool Node executed successfully");
                // 调用工具方法并存储结果到上下文
                List<PersonInfo> mgPersonList = getMgPersonListTool.getMgPersonList();
                context.setMgPersonList(mgPersonList);
                context.setCurrentStep("获取MG人员列表");
                log.info("成功获取MG人员列表，共{}个MG人员", mgPersonList.size());
            } catch (Exception e) {
                log.error("GetMgPersonListTool Node execution failed: {}", e.getMessage(), e);
                context.setErrorMessage("获取MG人员列表失败: " + e.getMessage());
            }
            return WorkflowContext.saveContext(context);
        });
    }
}