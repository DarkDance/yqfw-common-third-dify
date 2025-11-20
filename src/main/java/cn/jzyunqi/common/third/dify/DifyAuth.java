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
     * dify机器人的baseUrl
     */
    private String baseUrl;

    /**
     * dify机器人的apiKey
     */
    private String apiKey;
}
