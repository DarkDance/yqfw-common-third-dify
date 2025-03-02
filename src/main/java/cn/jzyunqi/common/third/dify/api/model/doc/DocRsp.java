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
public class DocRsp extends DifyRspV1 {
    private DocData document;
    private String batch;
}
