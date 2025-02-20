package cn.jzyunqi.common.third.dify.api;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.enums.WorkflowStatus;
import cn.jzyunqi.common.third.dify.api.model.AppConfigInfoData;
import cn.jzyunqi.common.third.dify.api.model.AppInfoData;
import cn.jzyunqi.common.third.dify.api.model.AudioToTextData;
import cn.jzyunqi.common.third.dify.api.model.BlockingChatData;
import cn.jzyunqi.common.third.dify.api.model.BlockingWorkflowData;
import cn.jzyunqi.common.third.dify.api.model.ChatMsgParam;
import cn.jzyunqi.common.third.dify.api.model.ConversationData;
import cn.jzyunqi.common.third.dify.api.model.ConversationParam;
import cn.jzyunqi.common.third.dify.api.model.FeedbackParam;
import cn.jzyunqi.common.third.dify.api.model.FileUploadData;
import cn.jzyunqi.common.third.dify.api.model.MessageData;
import cn.jzyunqi.common.third.dify.api.model.SuggestedRsp;
import cn.jzyunqi.common.third.dify.api.model.WorkflowLog;
import cn.jzyunqi.common.third.dify.common.DifyHttpExchange;
import cn.jzyunqi.common.third.dify.common.model.DifyPageRsp;
import cn.jzyunqi.common.third.dify.common.model.DifyRspV2;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
@DifyHttpExchange
@HttpExchange(url = "{scheme}://{host}:{port}/{path}", contentType = "application/json; charset=utf-8", accept = {"application/json"})
public interface DifyApiProxy {

    //发送对话消息
    @PostExchange(url = "/chat-messages")
    BlockingChatData blockingChat(@RequestBody ChatMsgParam chatMsgParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //上传文件
    @PostExchange(url = "/files/upload", contentType = "multipart/form-data")
    FileUploadData fileUpload(@RequestParam String user, @RequestPart Resource file, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //消息反馈（点赞）
    @PostExchange(url = "/messages/{messageId}/feedbacks")
    DifyRspV2 messageFeedback(@PathVariable String messageId, @RequestBody FeedbackParam feedbackParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取下一轮建议问题列表
    @GetExchange(url = "/messages/{messageId}/suggested")
    SuggestedRsp messageSuggest(@RequestParam String user, @PathVariable String messageId, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取会话历史消息
    @GetExchange(url = "/messages")
    DifyPageRsp<MessageData> messageList(@RequestParam String user, @RequestParam("conversation_id") String conversationId, @RequestParam("first_id") String firstId, @RequestParam Integer limit, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取会话列表
    @GetExchange(url = "/conversations")
    DifyPageRsp<ConversationData> conversationList(@RequestParam String user, @RequestParam("last_id") String lastId, @RequestParam Integer limit, @RequestParam("sort_by") String sortBy, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //删除会话
    @DeleteExchange(url = "/conversations/{conversationId}")
    DifyRspV2 conversationDelete(@PathVariable String conversationId, @RequestBody ConversationParam chatMsgParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //会话重命名
    @PostExchange(url = "/conversations/{conversationId}/name")
    ConversationData conversationRename(@PathVariable String conversationId, @RequestBody ConversationParam chatMsgParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //语音转文字
    @PostExchange(url = "/audio-to-text", contentType = "multipart/form-data")
    AudioToTextData audioToText(@RequestParam String user, @RequestPart Resource file, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //文字转语音
    @PostExchange(url = "/text-to-audio", accept = {"audio/wav"})
    Resource textToAudio(@RequestParam String user, @RequestParam("message_id") String messageId, @RequestParam String text, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取应用基本信息
    @GetExchange(url = "/info")
    AppInfoData appInfo(@RequestParam String user, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取应用参数
    @GetExchange(url = "/parameters")
    AppConfigInfoData appConfigInfo(@RequestParam String user, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取应用Meta信息
    @GetExchange(url = "/meta")
    String appMetaInfo(@RequestParam String user, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //执行 workflow
    @PostExchange(url = "/workflows/run")
    BlockingWorkflowData blockingWorkflowRun(@RequestBody ChatMsgParam chatMsgParam, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

    //获取 workflow 日志
    @PostExchange(url = "/workflows/logs")
    DifyPageRsp<WorkflowLog> workflowsLogList(@RequestParam String keyword, @RequestParam WorkflowStatus status, @RequestParam Integer page, @RequestParam Integer limit, @PathVariable String scheme, @PathVariable String host, @PathVariable int port, @PathVariable String path, @RequestHeader("Authorization") String authorization) throws BusinessException;

}
