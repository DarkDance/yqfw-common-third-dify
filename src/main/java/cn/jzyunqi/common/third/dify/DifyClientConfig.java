package cn.jzyunqi.common.third.dify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
public interface DifyClientConfig {

    /**
     * 鉴权key
     */
    List<AuthInfo> getAuthList();

    /**
     * 域名地址
     */
    String getDomain();

    default String getApiKey(String apiId) {
        return getAuthList().stream().filter(authInfo -> authInfo.getApiId().equals(apiId)).findFirst().orElse(new AuthInfo()).getApiKey();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class AuthInfo{
        /**
         * 自定义的id，用于区分不同应用
         */
        private String apiId;

        /**
         * dify机器人的apiKey
         */
        private String apiKey;
    }

}
