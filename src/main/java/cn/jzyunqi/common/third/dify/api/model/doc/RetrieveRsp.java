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
public class RetrieveRsp {
    private Query query;
    private List<RetrieveRecord> records;

    @Getter
    @Setter
    @ToString
    public static class Query{
        private String content;
    }

    @Getter
    @Setter
    @ToString
    public static class RetrieveRecord{
        private SegmentData segment;
        private Float score;
        private String tsnePosition;
    }
}
