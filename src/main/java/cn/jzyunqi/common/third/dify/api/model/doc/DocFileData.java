package cn.jzyunqi.common.third.dify.api.model.doc;

import cn.jzyunqi.common.third.dify.common.model.DifyRspV1;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @since 2025/3/2
 */
@Getter
@Setter
@ToString
public class DocFileData extends DifyRspV1 {
    private String id;
    private String name;
    private Integer size;
    private String extension;
    private String url;
    private String downloadUrl;
    private String mimeType;
    private String createdBy;
    private Long createdAt;
}
