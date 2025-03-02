package cn.jzyunqi.common.third.dify.api.model.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author wiiyaya
 * @since 2025/1/16
 */
@Getter
@Setter
@ToString
public class ConversationData {
    private String id;
    private String name;
    private Map<String, Object> inputs;
    private String status;
    private String introduction;
    private String createdAt;
    private String updatedAt;
}
