package cn.jzyunqi.common.third.dify.api;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.model.BlockingChatData;
import cn.jzyunqi.common.third.dify.api.model.ChatMsgParam;
import cn.jzyunqi.common.third.dify.api.model.FileUploadData;
import cn.jzyunqi.common.third.dify.api.model.StreamingChatData;
import cn.jzyunqi.common.third.dify.common.DifyHttpExchange;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Flux;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
@DifyHttpExchange
@HttpExchange(url = "{scheme}://{host}/{path}", accept = {"application/json"})
public interface DifyApiProxy {

    //发送对话消息
    @PostExchange(url = "/chat-messages", contentType = "application/json; charset=utf-8")
    BlockingChatData blockingChat(@RequestBody ChatMsgParam chatMsgParam, @PathVariable String scheme, @PathVariable String host, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //上传文件
    @PostExchange(url = "/files/upload", contentType = "multipart/form-data")
    FileUploadData fileUpload(@RequestParam String user, @RequestPart Resource file, @PathVariable String scheme, @PathVariable String host, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;
}
