package cn.jzyunqi.common.third.dify.api.model.doc;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wiiyaya
 * @since 2025/3/2
 */
@Getter
@Setter
@ToString
public class SegmentData {
    private String id;
    private String position;
    private String documentId;
    private String content;
    private String answer;
    private Integer wordCount;
    private Integer tokens;
    private List<String> keywords;
    private String indexNodeId;
    private String indexNodeHash;
    private Integer hitCount;
    private Boolean enabled;
    private Long disabledAt;
    private String disabledBy;
    private String status;
    private String createdBy;
    private Long createdAt;
    private Long indexingAt;
    private Long completedAt;
    private String error;
    private Long stoppedAt;
    private DocData document;
}
