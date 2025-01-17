package cn.jzyunqi.common.third.dify.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @since 2025/1/14
 */
@Getter
@Setter
@ToString
public class DifyRspV1 {
    private String code;
    private String message;
    private String status;
}
