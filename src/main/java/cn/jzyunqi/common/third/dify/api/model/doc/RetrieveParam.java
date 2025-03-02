package cn.jzyunqi.common.third.dify.api.model.doc;

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
public class RetrieveParam {
    private String query;
    private RetrievalModelData retrievalModel;
    private Object externalRetrievalModel;
}
