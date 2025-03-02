package cn.jzyunqi.common.third.dify.api.model.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @since 2025/1/16
 */
@Getter
@Setter
@ToString
public class RetrieverData {
    private String position;
    private String datasetId;
    private String datasetName;
    private String documentId;
    private String documentName;
    private String segmentId;
    private String score;
    private String content;
}
