package cn.jzyunqi.common.third.dify;

import java.util.List;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
public interface DifyClientConfig {

    List<DifyAuth> getDifyAuthList();

    default DifyAuth getDifyAuth(String id) {
        return getDifyAuthList().stream().filter(authInfo -> authInfo.getId().equals(id)).findFirst().orElse(new DifyAuth());
    }
}
