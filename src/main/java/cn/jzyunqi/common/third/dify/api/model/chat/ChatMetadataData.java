package cn.jzyunqi.common.third.dify.api.model.chat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
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
    private List<RetrieverData> retrieverResources;

    @Getter
    @Setter
    @ToString
    public static class Usage {
        private Integer promptTokens;
        private BigDecimal promptUnitPrice;
        private BigDecimal promptPriceUnit;
        private BigDecimal promptPrice;
        private Integer completionTokens;
        private BigDecimal completionUnitPrice;
        private BigDecimal completionPriceUnit;
        private BigDecimal completionPrice;
        private Integer totalTokens;
        private BigDecimal totalPrice;
        private String currency;
        private String latency;
    }
}
