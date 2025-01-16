package cn.jzyunqi.common.third.dify.api.model;

import cn.jzyunqi.common.third.dify.api.enums.Rating;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @since 2025/1/16
 */
@Getter
@Setter
@ToString
public class FeedbackParam {
    private Rating rating;
    private String user;
    private String content;
}
