package cn.jzyunqi.common.third.dify.api.model;

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
public class WorkflowLog {
    private String id;
    private WorkflowRunData workflowRun;
    private String createdFrom;
    private String createdByRole;
    private String createdByAccount;
    private String createdByEndUser;
    private String createdAt;

    @Getter
    @Setter
    @ToString
    public static class EndUser {
        private String id;
        private String type;
        private Boolean isAnonymous;
        private String sessionId;
    }
}
