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
public class WorkflowRunData {
    private String id;
    private String version;
    private String workflowId;
    private WorkflowStatus status;
    private Map<String, Object> outputs;
    private String error;
    private Float elapsedTime;
    private Integer totalTokens;
    private Integer totalSteps;
    private String createdAt;
    private String finishedAt;
}
