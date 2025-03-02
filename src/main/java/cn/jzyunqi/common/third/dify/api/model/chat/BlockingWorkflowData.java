package cn.jzyunqi.common.third.dify.api.model.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
