package cn.jzyunqi.common.third.dify.common.model;

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
@ToString
public class DifyPageRsp<T> extends DifyRspV1 {
    private Integer page;
    private Integer limit;
    private Integer total;
    private Boolean hasMore;
    private List<T> data;
}
