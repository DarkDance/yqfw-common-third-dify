package cn.jzyunqi.common.third.dify;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.DifyStreamApiProxy;
import cn.jzyunqi.common.third.dify.api.enums.ResponseMode;
import cn.jzyunqi.common.third.dify.api.model.BlockingChatData;
import cn.jzyunqi.common.third.dify.api.model.ChatMsgParam;
import cn.jzyunqi.common.third.dify.api.DifyApiProxy;
import cn.jzyunqi.common.third.dify.api.model.StreamingChatData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
@Slf4j
public class DifyClient {

    @Resource
    private DifyApiProxy difyApiProxy;

    @Resource
    private DifyStreamApiProxy difyStreamApiProxy;

    @Resource
    private DifyClientConfig difyClientConfig;

    public BlockingChatData blockingChat(String difyId, String userId, Map<String, Object> customParams, String message) throws BusinessException {
        ChatMsgParam chatMsgParam = new ChatMsgParam();
        chatMsgParam.setUser(userId);
        chatMsgParam.setInputs(customParams);
        chatMsgParam.setQuery(message);
        chatMsgParam.setResponseMode(ResponseMode.blocking);
        chatMsgParam.setAutoGenerateName(Boolean.TRUE);
        chatMsgParam.setConversationId(null);
        chatMsgParam.setFiles(null);

        DifyAuth difyAuth = difyClientConfig.getDifyAuth(difyId);
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
        return difyApiProxy.blockingChat(uriComponents.getScheme(), uriComponents.getHost(), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey(), chatMsgParam);
    }

    public Flux<StreamingChatData> streamingChat(String difyId, String userId, Map<String, Object> customParams, String message) throws BusinessException {
        ChatMsgParam chatMsgParam = new ChatMsgParam();
        chatMsgParam.setUser(userId);
        chatMsgParam.setInputs(customParams);
        chatMsgParam.setQuery(message);
        chatMsgParam.setResponseMode(ResponseMode.blocking);
        chatMsgParam.setAutoGenerateName(Boolean.TRUE);
        chatMsgParam.setConversationId(null);
        chatMsgParam.setFiles(null);
        DifyAuth difyAuth = difyClientConfig.getDifyAuth(difyId);
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
        return difyStreamApiProxy.streamingChat(uriComponents.getScheme(), uriComponents.getHost(), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey(), chatMsgParam);
    }
}
