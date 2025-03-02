package cn.jzyunqi.common.third.dify.api.model.doc;

import cn.jzyunqi.common.third.dify.api.enums.DocForm;
import cn.jzyunqi.common.third.dify.common.model.DifyRspV1;
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
public class SegmentRsp extends DifyRspV1 {
    private List<SegmentData> data;
    private DocForm docForm;
}
