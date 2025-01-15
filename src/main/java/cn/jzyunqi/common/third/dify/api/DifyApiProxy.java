package cn.jzyunqi.common.third.dify.api;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.model.BlockingChatData;
import cn.jzyunqi.common.third.dify.api.model.ChatMsgParam;
import cn.jzyunqi.common.third.dify.api.model.StreamingChatData;
import cn.jzyunqi.common.third.dify.common.DifyHttpExchange;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
@DifyHttpExchange
@HttpExchange(url = "{scheme}://{host}/v1", contentType = "application/json; charset=utf-8")
public interface DifyApiProxy {

    //发送对话消息
    @PostExchange(url = "/chat-messages", accept = {"application/json"})
    BlockingChatData blockingChat(@PathVariable String scheme, @PathVariable String host, @RequestHeader("Authorization") String authorization, @RequestBody ChatMsgParam chatMsgParam) throws BusinessException;
}
