package cn.jzyunqi.common.third.dify.api.model.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @since 2025/1/11
 */
@Getter
@Setter
@ToString
public class BlockingChatData {

    private String messageId;
    private String conversationId;
    private String mode;
    private String answer;
    private String createdAt;
    private ChatMetadataData metadata;
}
