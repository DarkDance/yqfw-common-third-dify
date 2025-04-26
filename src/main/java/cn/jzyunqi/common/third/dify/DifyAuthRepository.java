package cn.jzyunqi.common.third.dify;

import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
public abstract class DifyAuthRepository implements InitializingBean {

    private final Map<String, DifyAuth> authMap = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        java.util.List<DifyAuth> difyAuthList = initDifyAuthList();
        for (DifyAuth difyAuth : difyAuthList) {
            authMap.put(difyAuth.getId(), difyAuth);
        }
    }

    public DifyAuth choosDifyAuth(String difyAuthId) {
        return authMap.get(difyAuthId);
    }

    public void addDifyAuth(DifyAuth difyAuth) {
        authMap.put(difyAuth.getId(), difyAuth);
    }

    public void removeDifyAuth(String difyAuthId) {
        authMap.remove(difyAuthId);
    }

    public List<DifyAuth> getDifyAuthList() {
        return new ArrayList<>(authMap.values());
    }

    public abstract List<DifyAuth> initDifyAuthList();
}
