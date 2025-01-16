package cn.jzyunqi.common.third.dify.api.model;

import cn.jzyunqi.common.third.dify.common.model.DifyRspV2;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wiiyaya
 * @since 2025/1/16
 */
@Getter
@Setter
@ToString(callSuper = true)
public class SuggestedRsp extends DifyRspV2 {
    private List<String> data;
}
