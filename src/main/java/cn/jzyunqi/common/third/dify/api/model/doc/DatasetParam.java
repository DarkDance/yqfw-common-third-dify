package cn.jzyunqi.common.third.dify.api.model.doc;

import cn.jzyunqi.common.third.dify.api.enums.IndexingTechnique;
import cn.jzyunqi.common.third.dify.api.enums.Permission;
import cn.jzyunqi.common.third.dify.api.enums.Provider;
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
public class DatasetParam {
    private String name;
    private String description;
    private IndexingTechnique indexingTechnique;
    private Permission permission;
    private Provider provider;
    private String externalKnowledgeApiId;
    private String externalKnowledgeId;
}
