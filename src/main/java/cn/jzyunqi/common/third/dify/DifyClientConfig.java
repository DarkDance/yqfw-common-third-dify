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
    List<DifyAuth> getDifyAuthList();

    default DifyAuth getDifyAuth(String id) {
        return getDifyAuthList().stream().filter(authInfo -> authInfo.getId().equals(id)).findFirst().orElse(new DifyAuth());
    }
}
