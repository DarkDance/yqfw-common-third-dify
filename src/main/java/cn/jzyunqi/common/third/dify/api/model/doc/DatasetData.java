package cn.jzyunqi.common.third.dify.api.model.doc;

import cn.jzyunqi.common.third.dify.api.enums.IndexingTechnique;
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
public class DatasetData extends DifyRspV1 {
    private String id;
    private String name;
    private String description;
    private String provider;
    private String permission;
    private String dataSourceType;
    private IndexingTechnique indexingTechnique;
    private Integer appCount;
    private Integer documentCount;
    private Integer wordCount;
    private String createdBy;
    private Long createdAt;
    private String updatedBy;
    private Long updatedAt;
    private String embeddingModel;
    private String embeddingModelProvider;
    private Boolean embeddingAvailable;
}
