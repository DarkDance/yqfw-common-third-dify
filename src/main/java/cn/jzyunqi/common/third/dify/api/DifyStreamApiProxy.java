package cn.jzyunqi.common.third.dify.api;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.model.chat.ChatMsgParam;
import cn.jzyunqi.common.third.dify.api.model.chat.StreamingData;
import cn.jzyunqi.common.third.dify.common.model.DifyRspV2;
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
@HttpExchange(url = "{scheme}://{host}:{port}/{path}", contentType = "application/json; charset=utf-8")
public interface DifyStreamApiProxy {

    //发送对话消息
    @PostExchange(url = "/chat-messages", accept = {"text/event-stream"})
    Flux<StreamingData> streamingChat(@RequestBody ChatMsgParam chatMsgParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //停止响应
    @PostExchange(url = "/chat-messages/{taskId}/stop", accept = {"application/json"})
    DifyRspV2 streamingChatStop(@PathVariable String taskId, @RequestBody ChatMsgParam chatMsgParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //执行 workflow
    @PostExchange(url = "/workflows/run")
    Flux<StreamingData> streamingWorkflowRun(@RequestBody ChatMsgParam chatMsgParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

}
