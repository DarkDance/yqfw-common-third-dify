package cn.jzyunqi.common.third.dify;

import cn.jzyunqi.common.utils.StringUtilPlus;

import java.util.List;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
public interface DifyAuthRepository {

    List<DifyAuth> getDifyAuthList();

    default DifyAuth getDifyAuth(String id) {
        if (StringUtilPlus.isEmpty(id)) {
            return getDifyAuthList().stream().findFirst().orElse(new DifyAuth());
        } else {
            return getDifyAuthList().stream().filter(authInfo -> authInfo.getId().equals(id)).findFirst().orElse(new DifyAuth());
        }
    }
}
