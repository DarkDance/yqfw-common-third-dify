package cn.jzyunqi.common.third.dify.api.model;

import cn.jzyunqi.common.third.dify.api.enums.BelongsTo;
import cn.jzyunqi.common.third.dify.api.enums.FileType;
import cn.jzyunqi.common.third.dify.api.enums.Rating;
import cn.jzyunqi.common.third.dify.api.enums.TransferMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @since 2025/1/16
 */
@Getter
@Setter
@ToString
public class MessageData {
    private String id;
    private String conversationId;
    private Map<String, Object> inputs;
    private String query;
    private List<FileInfo> messageFiles;
    private String answer;
    private String createdAt;
    private Feedback feedback;
    private List<RetrieverData> retrieverResources;

    @Getter
    @Setter
    @ToString
    public static class FileInfo {
        private String id;
        private FileType type;
        private String url;
        private BelongsTo belongsTo;
        private List<AgentThought> agentThoughts;
    }

    @Getter
    @Setter
    @ToString
    public static class AgentThought {
        private String id;
        private String messageId;
        private String conversationId;
        private Integer position;
        private String thought;
        private String observation;
        private String tool;
        private String tool_input;
        private String created_at;
        private List<String> message_files;
    }

    @Getter
    @Setter
    @ToString
    public static class Feedback {
        private Rating rating;
    }
}
