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
public class SegmentParam {
    private String content;
    private String answer;
    private List<String> keywords;
    private Boolean enabled;
    private Boolean regenerateChildChunks;
}
