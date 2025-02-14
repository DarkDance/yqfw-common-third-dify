package cn.jzyunqi.common.third.dify;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.DifyStreamApiProxy;
import cn.jzyunqi.common.third.dify.api.enums.Rating;
import cn.jzyunqi.common.third.dify.api.enums.ResponseMode;
import cn.jzyunqi.common.third.dify.api.model.AppConfigInfoData;
import cn.jzyunqi.common.third.dify.api.model.AppInfoData;
import cn.jzyunqi.common.third.dify.api.model.AudioToTextData;
import cn.jzyunqi.common.third.dify.api.model.BlockingChatData;
import cn.jzyunqi.common.third.dify.api.model.ChatMsgParam;
import cn.jzyunqi.common.third.dify.api.DifyApiProxy;
import cn.jzyunqi.common.third.dify.api.model.ConversationData;
import cn.jzyunqi.common.third.dify.api.model.ConversationParam;
import cn.jzyunqi.common.third.dify.api.model.FeedbackParam;
import cn.jzyunqi.common.third.dify.api.model.FileUploadData;
import cn.jzyunqi.common.third.dify.api.model.MessageData;
import cn.jzyunqi.common.third.dify.api.model.StreamingData;
import cn.jzyunqi.common.third.dify.common.model.DifyPageRsp;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.util.List;
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
    private DifyAuthRepository difyAuthRepository;

    public final Chat chat = new Chat();
    public final Msg msg = new Msg();
    public final Conv conv = new Conv();
    public final Tools tools = new Tools();
    public final App app = new App();

    public class Chat {
        public BlockingChatData blocking(String difyAuthId, String userId, String conversationId, Map<String, Object> customParams, String message, List<ChatMsgParam.FileInfo> files) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setUser(userId);
            chatMsgParam.setInputs(customParams);
            chatMsgParam.setQuery(message);
            chatMsgParam.setResponseMode(ResponseMode.blocking);
            chatMsgParam.setAutoGenerateName(Boolean.TRUE);
            chatMsgParam.setConversationId(conversationId);
            chatMsgParam.setFiles(files);

            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.blockingChat(chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public Flux<StreamingData> streaming(String difyAuthId, String userId, String conversationId, Map<String, Object> customParams, String message, List<ChatMsgParam.FileInfo> files) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setUser(userId);
            chatMsgParam.setInputs(customParams);
            chatMsgParam.setQuery(message);
            chatMsgParam.setResponseMode(ResponseMode.streaming);
            chatMsgParam.setAutoGenerateName(Boolean.TRUE);
            chatMsgParam.setConversationId(conversationId);
            chatMsgParam.setFiles(files);

            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyStreamApiProxy.streamingChat(chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public void streamingStop(String difyAuthId, String userId, String taskId) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setUser(userId);

            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyStreamApiProxy.streamingChatStop(taskId, chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Msg {
        public void feedback(String difyAuthId, String userId, String messageId, Rating rating, String message) throws BusinessException {
            FeedbackParam feedbackParam = new FeedbackParam();
            feedbackParam.setUser(userId);
            feedbackParam.setRating(rating);
            feedbackParam.setContent(message);

            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyApiProxy.messageFeedback(messageId, feedbackParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public void nextSuggest(String difyAuthId, String userId, String messageId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyApiProxy.messageSuggest(userId, messageId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public DifyPageRsp<MessageData> list(String difyAuthId, String userId, String conversationId, String firstMessageId, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.messageList(userId, conversationId, firstMessageId, limit, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Conv {
        public DifyPageRsp<ConversationData> list(String difyAuthId, String userId, String lastConversationId, Integer limit, String sortBy) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.conversationList(userId, lastConversationId, limit, sortBy, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public void delete(String difyAuthId, String userId, String conversationId) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);

            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyApiProxy.conversationDelete(conversationId, conversationParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public ConversationData autoRename(String difyAuthId, String userId, String conversationId) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);
            conversationParam.setAutoGenerate(Boolean.TRUE);

            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.conversationRename(conversationId, conversationParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public ConversationData rename(String difyAuthId, String userId, String conversationId, String conversationName) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);
            conversationParam.setName(conversationName);
            conversationParam.setAutoGenerate(Boolean.FALSE);

            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.conversationRename(conversationId, conversationParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Tools {

        public FileUploadData uploadFile(String difyAuthId, String userId, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.fileUpload(userId, file, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public AudioToTextData audioToText(String difyAuthId, String userId, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.audioToText(userId, file, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public org.springframework.core.io.Resource messageToAudio(String difyAuthId, String userId, String messageId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.textToAudio(userId, messageId, null, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public org.springframework.core.io.Resource textToAudio(String difyAuthId, String userId, String text) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.textToAudio(userId, null, text, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

    }

    public class App {
        public AppInfoData baseInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.appInfo(userId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public AppConfigInfoData configInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.appConfigInfo(userId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }

        public String metaInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.getDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.appMetaInfo(userId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), uriComponents.getPath(), "Bearer " + difyAuth.getApiKey());
        }
    }

    private int defaultPort(UriComponents uriComponents) {
        int port = uriComponents.getPort();
        if (port == -1) {
            return uriComponents.getScheme().equals("http") ? 80 : 443;
        }else{
            return port;
        }
    }
}
