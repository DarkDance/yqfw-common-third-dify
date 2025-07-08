package cn.jzyunqi.common.third.dify.api.model.chat;

import cn.jzyunqi.common.third.dify.api.enums.BelongsTo;
import cn.jzyunqi.common.third.dify.api.enums.EventType;
import cn.jzyunqi.common.third.dify.api.enums.FileType;
import cn.jzyunqi.common.third.dify.api.enums.NodeStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @since 2025/1/14
 */
@Getter
@Setter
@ToString
public class StreamingData {
    private EventType event;

    //message、agent_message、message_replace
    private String taskId;
    private String messageId;
    private String conversationId;
    private String answer;
    private String createdAt;

    //agent_thought
    private String id;
    //private String taskId;
    //private String messageId;
    //private String conversationId;
    private Integer position;
    private String thought;
    private String observation;
    private String tool;
    private String toolInput;
    //private String createdAt;
    private List<String> messageFiles;

    //message_file
    //private String id;
    private FileType type;
    private BelongsTo belongsTo;
    private String url;
    //private String conversation_id;

    //message_end
    private ChatMetadataData metadata;

    //tts_message
    private String audio;//语音的 Base64 编码

    //workflow_started、node_started、node_finished、workflow_finished
    private String workflowRunId;
    private WorkflowOrNodeData data;

    //error
    private Integer status;
    private String code;
    private String message;

    @Getter
    @Setter
    @ToString
    public static class WorkflowOrNodeData {
        private String id;
        private String workflowId;

        private Integer sequenceNumber;
        private String createdAt;

        private String nodeId;
        private String nodeType;
        private String title;
        private Integer index;
        private String predecessorNodeId;
        private Map<String, Object> inputs;

        private Map<String, Object> processData;
        private Map<String, Object> outputs;
        private NodeStatus status;
        private String error;
        private Float elapsed_time;
        private ChatMetadataData.Usage executionMetadata;

        private String finished_at;
        private Integer totalTokens;
        private Integer totalSteps;
    }
}
