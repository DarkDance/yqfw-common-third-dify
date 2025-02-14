package cn.jzyunqi.common.third.dify.api.model;

import cn.jzyunqi.common.third.dify.api.enums.WorkflowStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author wiiyaya
 * @since 2025/2/14
 */
@Getter
@Setter
@ToString
public class BlockingWorkflowData {
    private String workflowRunId;
    private String taskId;
    private WorkflowRunData data;
}
