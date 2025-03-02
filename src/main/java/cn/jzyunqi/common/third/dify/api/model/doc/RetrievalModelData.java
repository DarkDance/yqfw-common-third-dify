package cn.jzyunqi.common.third.dify.api.model.doc;

import cn.jzyunqi.common.third.dify.api.enums.SearchMethod;
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
public class RetrievalModelData {
    private SearchMethod searchMethod;
    private Boolean rerankingEnable;
    private DocParam.RerankingModel rerankingModel;
    private Integer weights;
    private Integer topK;
    private Boolean scoreThresholdEnabled;
    private Float scoreThreshold;
}
