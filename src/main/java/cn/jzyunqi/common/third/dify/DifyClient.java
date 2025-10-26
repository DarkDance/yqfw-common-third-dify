package cn.jzyunqi.common.third.dify;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.DatasetApiProxy;
import cn.jzyunqi.common.third.dify.api.DifyApiProxy;
import cn.jzyunqi.common.third.dify.api.DifyStreamApiProxy;
import cn.jzyunqi.common.third.dify.api.enums.Rating;
import cn.jzyunqi.common.third.dify.api.enums.ResponseMode;
import cn.jzyunqi.common.third.dify.api.enums.WorkflowStatus;
import cn.jzyunqi.common.third.dify.api.model.chat.AppConfigInfoData;
import cn.jzyunqi.common.third.dify.api.model.chat.AppInfoData;
import cn.jzyunqi.common.third.dify.api.model.chat.AudioToTextData;
import cn.jzyunqi.common.third.dify.api.model.chat.BlockingChatData;
import cn.jzyunqi.common.third.dify.api.model.chat.BlockingWorkflowData;
import cn.jzyunqi.common.third.dify.api.model.chat.ChatMsgParam;
import cn.jzyunqi.common.third.dify.api.model.chat.ConversationData;
import cn.jzyunqi.common.third.dify.api.model.chat.ConversationParam;
import cn.jzyunqi.common.third.dify.api.model.chat.FeedbackParam;
import cn.jzyunqi.common.third.dify.api.model.chat.FileUploadData;
import cn.jzyunqi.common.third.dify.api.model.chat.MessageData;
import cn.jzyunqi.common.third.dify.api.model.chat.StreamingData;
import cn.jzyunqi.common.third.dify.api.model.doc.DatasetData;
import cn.jzyunqi.common.third.dify.api.model.doc.DatasetParam;
import cn.jzyunqi.common.third.dify.api.model.doc.DocFileData;
import cn.jzyunqi.common.third.dify.api.model.doc.DocParam;
import cn.jzyunqi.common.third.dify.api.model.doc.DocRsp;
import cn.jzyunqi.common.third.dify.api.model.doc.RetrieveParam;
import cn.jzyunqi.common.third.dify.api.model.doc.RetrieveRsp;
import cn.jzyunqi.common.third.dify.api.model.doc.SegmentCreateReq;
import cn.jzyunqi.common.third.dify.api.model.doc.SegmentRsp;
import cn.jzyunqi.common.third.dify.common.model.DifyPageRsp;
import cn.jzyunqi.common.utils.StringUtilPlus;
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
    private DifyAuthHelper difyAuthRepository;

    @Resource
    private DifyApiProxy difyApiProxy;

    @Resource
    private DifyStreamApiProxy difyStreamApiProxy;

    @Resource
    private DatasetApiProxy datasetApiProxy;

    public final Chat chat = new Chat();
    public final Workflow workflow = new Workflow();
    public final Msg msg = new Msg();
    public final Conv conv = new Conv();
    public final Tools tools = new Tools();
    public final App app = new App();

    public final Dataset dataset = new Dataset();
    public final Doc doc = new Doc();
    public final Segment segment = new Segment();

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

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.blockingChat(chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
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

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyStreamApiProxy.streamingChat(chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void streamingStop(String difyAuthId, String userId, String taskId) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setUser(userId);

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyStreamApiProxy.streamingChatStop(taskId, chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Workflow {
        public BlockingWorkflowData blocking(String difyAuthId, String userId, Map<String, Object> customParams, List<ChatMsgParam.FileInfo> files) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setInputs(customParams);
            chatMsgParam.setResponseMode(ResponseMode.blocking);
            chatMsgParam.setUser(userId);
            chatMsgParam.setFiles(files);

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.blockingWorkflowRun(chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public Flux<StreamingData> streaming(String difyAuthId, String userId, Map<String, Object> customParams, List<ChatMsgParam.FileInfo> files) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setInputs(customParams);
            chatMsgParam.setResponseMode(ResponseMode.streaming);
            chatMsgParam.setUser(userId);
            chatMsgParam.setFiles(files);

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyStreamApiProxy.streamingWorkflowRun(chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void streamingStop(String difyAuthId, String userId, String taskId) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setUser(userId);

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyStreamApiProxy.streamingChatStop(taskId, chatMsgParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void logList(String difyAuthId, String keyword, WorkflowStatus status, Integer page, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyApiProxy.workflowsLogList(keyword, status, page, limit, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Msg {
        public void feedback(String difyAuthId, String userId, String messageId, Rating rating, String message) throws BusinessException {
            FeedbackParam feedbackParam = new FeedbackParam();
            feedbackParam.setUser(userId);
            feedbackParam.setRating(rating);
            feedbackParam.setContent(message);

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyApiProxy.messageFeedback(messageId, feedbackParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void nextSuggest(String difyAuthId, String userId, String messageId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyApiProxy.messageSuggest(userId, messageId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public DifyPageRsp<MessageData> list(String difyAuthId, String userId, String conversationId, String firstMessageId, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.messageList(userId, conversationId, firstMessageId, limit, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Conv {
        public DifyPageRsp<ConversationData> list(String difyAuthId, String userId, String lastConversationId, Integer limit, String sortBy) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.conversationList(userId, lastConversationId, limit, sortBy, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void delete(String difyAuthId, String userId, String conversationId) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            difyApiProxy.conversationDelete(conversationId, conversationParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public ConversationData autoRename(String difyAuthId, String userId, String conversationId) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);
            conversationParam.setAutoGenerate(Boolean.TRUE);

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.conversationRename(conversationId, conversationParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public ConversationData rename(String difyAuthId, String userId, String conversationId, String conversationName) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);
            conversationParam.setName(conversationName);
            conversationParam.setAutoGenerate(Boolean.FALSE);

            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.conversationRename(conversationId, conversationParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Tools {

        public FileUploadData uploadFile(String difyAuthId, String userId, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.fileUpload(userId, file, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public AudioToTextData audioToText(String difyAuthId, String userId, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.audioToText(userId, file, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public org.springframework.core.io.Resource messageToAudio(String difyAuthId, String userId, String messageId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.textToAudio(userId, messageId, null, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public org.springframework.core.io.Resource textToAudio(String difyAuthId, String userId, String text) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.textToAudio(userId, null, text, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

    }

    public class App {
        public AppInfoData baseInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.appInfo(userId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public AppConfigInfoData configInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.appConfigInfo(userId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public String metaInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return difyApiProxy.appMetaInfo(userId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Dataset {
        public DatasetData createEmptyDataset(String difyAuthId, DatasetParam data) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.createEmptyDataset(data, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public DifyPageRsp<DatasetData> getDatasetList(String difyAuthId, Integer page, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.getDatasetList(page, limit, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void deleteDataset(String difyAuthId, String datasetId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            datasetApiProxy.deleteDataset(datasetId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public RetrieveRsp retrieve(String difyAuthId, String datasetId, RetrieveParam retrieveParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.retrieve(datasetId, retrieveParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Doc {
        public DocRsp createDocByText(String difyAuthId, String datasetId, DocParam docParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.createDocByText(datasetId, docParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public DocRsp updateDocByText(String difyAuthId, String datasetId, String documentId, DocParam docParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.updateDocByText(datasetId, documentId, docParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public DocRsp createDocByFile(String difyAuthId, String datasetId, DocParam data, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.createDocByFile(datasetId, data, file, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public DocFileData getDocFile(String difyAuthId, String datasetId, String documentId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.getDocFile(datasetId, documentId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public DocRsp updateDocByFile(String difyAuthId, String datasetId, String documentId, DocParam data, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.updateDocByFile(datasetId, documentId, data, file, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public DocRsp getDocIndexingStatus(String difyAuthId, String datasetId, String batch) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.getDocIndexingStatus(datasetId, batch, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void deleteDoc(String difyAuthId, String datasetId, String documentId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            datasetApiProxy.deleteDoc(datasetId, documentId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void getDocList(String difyAuthId, String datasetId, String keyword, Integer page, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            datasetApiProxy.getDocList(datasetId, keyword, page, limit, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Segment {
        public SegmentRsp createDocSegment(String difyAuthId, String datasetId, String documentId, SegmentCreateReq segmentParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.createDocSegment(datasetId, documentId, segmentParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public SegmentRsp updateDocSegment(String difyAuthId, String datasetId, String documentId, String segmentId, SegmentCreateReq segmentParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.updateDocSegment(datasetId, documentId, segmentId, segmentParam, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public SegmentRsp getDocSegmentList(String difyAuthId, String datasetId, String documentId, String keyword, String status) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            return datasetApiProxy.getDocSegmentList(datasetId, documentId, keyword, status, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }

        public void deleteDocSegment(String difyAuthId, String datasetId, String documentId, String segmentId) throws BusinessException {
            DifyAuth difyAuth = difyAuthRepository.choosDifyAuth(difyAuthId);
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(difyAuth.getBaseUrl()).build();
            datasetApiProxy.deleteDocSegment(datasetId, documentId, segmentId, uriComponents.getScheme(), uriComponents.getHost(), defaultPort(uriComponents), findContextPath(uriComponents.getPath()), "Bearer " + difyAuth.getApiKey());
        }
    }

    private int defaultPort(UriComponents uriComponents) {
        int port = uriComponents.getPort();
        if (port == -1) {
            return StringUtilPlus.equalsIgnoreCase(uriComponents.getScheme(), "http") ? 80 : 443;
        } else {
            return port;
        }
    }

    private static String findContextPath(String path) {
        return StringUtilPlus.defaultString(StringUtilPlus.splitGetFirst(path, "/"));
    }

}
