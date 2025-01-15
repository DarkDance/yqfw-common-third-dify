package cn.jzyunqi.common.third.dify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author wiiyaya
 * @since 2025/1/15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DifyAuth {

    /**
     * 自定义的id，用于区分不同应用
     */
    private String id;

    /**
     * dify机器人的baseUrl
     */
    private String baseUrl;

    /**
     * dify机器人的apiKey
     */
    private String apiKey;
}
