package cn.jzyunqi.common.third.dify.api.model.doc;

import cn.jzyunqi.common.third.dify.api.enums.DocForm;
import cn.jzyunqi.common.third.dify.api.enums.DocLanguage;
import cn.jzyunqi.common.third.dify.api.enums.DocType;
import cn.jzyunqi.common.third.dify.api.enums.PreRuleId;
import cn.jzyunqi.common.third.dify.api.enums.ProcessMode;
import cn.jzyunqi.common.third.dify.api.enums.SearchMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class DocParam {
    private String originalDocumentId;
    private String name;
    private String text;
    private DocType docType;
    private DocMetadataData docMetadata;
    private String indexingTechnique;
    private DocForm docForm;
    private DocLanguage docLanguage;
    private ProcessRule processRule;
    private RetrievalModelData retrievalModel;
    private String embeddingModel;
    private String embeddingModelProvider;

    @Getter
    @Setter
    @ToString
    public static class ProcessRule {
        private ProcessMode mode;
        private Rules rules;
    }

    @Getter
    @Setter
    @ToString
    public static class Rules {
        private List<PreRule> PreProcessingRules;
        private Segmentation segmentation;
        private String parentMode;
        @JsonProperty("subchunk_segmentation")
        private Segmentation subChunkSegmentation;

    }

    @Getter
    @Setter
    @ToString
    public static class PreRule {
        private PreRuleId id;
        private Boolean enabled;
    }

    @Getter
    @Setter
    @ToString
    public static class Segmentation {
        private String separator;
        private Integer maxTokens;
        private Boolean chunkOverlap;
    }

    @Getter
    @Setter
    @ToString
    public static class RerankingModel {
        private String rerankingProviderName;
        private String rerankingModelName;
    }
}
