package cn.jzyunqi.common.third.dify.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wiiyaya
 * @since 2025/1/14
 */
@Getter
@Setter
@ToString
public class ChatMetadataData {
    private Usage usage;
    private List<Retriever> retrieverResources;

    @Getter
    @Setter
    @ToString
    public static class Usage {
        private String promptTokens;
        private String promptUnitPrice;
        private String promptPriceUnit;
        private String promptPrice;
        private String completionTokens;
        private String completionUnitPrice;
        private String completionPriceUnit;
        private String completionPrice;
        private String totalTokens;
        private String totalPrice;
        private String currency;
        private String latency;
    }

    @Getter
    @Setter
    @ToString
    public static class Retriever {
        private String position;
        private String datasetId;
        private String datasetName;
        private String documentId;
        private String documentName;
        private String segmentId;
        private String score;
        private String content;
    }
}
