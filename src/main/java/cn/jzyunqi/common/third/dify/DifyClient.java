package cn.jzyunqi.common.third.dify;

import cn.jzyunqi.common.exception.BusinessException;
import cn.jzyunqi.common.third.dify.api.DifyDatasetApiProxy;
import cn.jzyunqi.common.third.dify.api.DifyBlockApiProxy;
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
import cn.jzyunqi.common.model.UriDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
@Slf4j
public class DifyClient {
    
    private static final String API_VERSION = "v1";

    @Resource
    private DifyAuthHelper difyAuthHelper;

    @Resource
    private DifyBlockApiProxy difyBlockApiProxy;

    @Resource
    private DifyStreamApiProxy difyStreamApiProxy;

    @Resource
    private DifyDatasetApiProxy difyDatasetApiProxy;

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

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.blockingChat(chatMsgParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey(), MDC.get("traceId"));
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

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyStreamApiProxy.streamingChat(chatMsgParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey(), MDC.get("traceId"));
        }

        public void streamingStop(String difyAuthId, String userId, String taskId) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setUser(userId);

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyStreamApiProxy.streamingChatStop(taskId, chatMsgParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Workflow {
        public BlockingWorkflowData blocking(String difyAuthId, String userId, Map<String, Object> customParams, List<ChatMsgParam.FileInfo> files) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setInputs(customParams);
            chatMsgParam.setResponseMode(ResponseMode.blocking);
            chatMsgParam.setUser(userId);
            chatMsgParam.setFiles(files);

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.blockingWorkflowRun(chatMsgParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey(), MDC.get("traceId"));
        }

        public Flux<StreamingData> streaming(String difyAuthId, String userId, Map<String, Object> customParams, List<ChatMsgParam.FileInfo> files) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setInputs(customParams);
            chatMsgParam.setResponseMode(ResponseMode.streaming);
            chatMsgParam.setUser(userId);
            chatMsgParam.setFiles(files);

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyStreamApiProxy.streamingWorkflowRun(chatMsgParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey(), MDC.get("traceId"));
        }

        public void streamingStop(String difyAuthId, String userId, String taskId) throws BusinessException {
            ChatMsgParam chatMsgParam = new ChatMsgParam();
            chatMsgParam.setUser(userId);

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyStreamApiProxy.streamingChatStop(taskId, chatMsgParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public void logList(String difyAuthId, String keyword, WorkflowStatus status, Integer page, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyBlockApiProxy.workflowsLogList(keyword, status, page, limit, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Msg {
        public void feedback(String difyAuthId, String userId, String messageId, Rating rating, String message) throws BusinessException {
            FeedbackParam feedbackParam = new FeedbackParam();
            feedbackParam.setUser(userId);
            feedbackParam.setRating(rating);
            feedbackParam.setContent(message);

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyBlockApiProxy.messageFeedback(messageId, feedbackParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public void nextSuggest(String difyAuthId, String userId, String messageId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyBlockApiProxy.messageSuggest(userId, messageId, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public DifyPageRsp<MessageData> list(String difyAuthId, String userId, String conversationId, String firstMessageId, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.messageList(userId, conversationId, firstMessageId, limit, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Conv {
        public DifyPageRsp<ConversationData> list(String difyAuthId, String userId, String lastConversationId, Integer limit, String sortBy) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.conversationList(userId, lastConversationId, limit, sortBy, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public void delete(String difyAuthId, String userId, String conversationId) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyBlockApiProxy.conversationDelete(conversationId, conversationParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public ConversationData autoRename(String difyAuthId, String userId, String conversationId) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);
            conversationParam.setAutoGenerate(Boolean.TRUE);

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.conversationRename(conversationId, conversationParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public ConversationData rename(String difyAuthId, String userId, String conversationId, String conversationName) throws BusinessException {
            ConversationParam conversationParam = new ConversationParam();
            conversationParam.setUser(userId);
            conversationParam.setName(conversationName);
            conversationParam.setAutoGenerate(Boolean.FALSE);

            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.conversationRename(conversationId, conversationParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Tools {

        public FileUploadData uploadFile(String difyAuthId, String userId, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.fileUpload(userId, file, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public AudioToTextData audioToText(String difyAuthId, String userId, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.audioToText(userId, file, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public org.springframework.core.io.Resource messageToAudio(String difyAuthId, String userId, String messageId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.textToAudio(userId, messageId, null, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public org.springframework.core.io.Resource textToAudio(String difyAuthId, String userId, String text) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.textToAudio(userId, null, text, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

    }

    public class App {
        public AppInfoData baseInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.appInfo(userId, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public AppConfigInfoData configInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.appConfigInfo(userId, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public String metaInfo(String difyAuthId, String userId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyBlockApiProxy.appMetaInfo(userId, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Dataset {
        public DatasetData createEmptyDataset(String difyAuthId, DatasetParam data) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.createEmptyDataset(data, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public DifyPageRsp<DatasetData> getDatasetList(String difyAuthId, Integer page, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.getDatasetList(page, limit, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public void deleteDataset(String difyAuthId, String datasetId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyDatasetApiProxy.deleteDataset(datasetId, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public RetrieveRsp retrieve(String difyAuthId, String datasetId, RetrieveParam retrieveParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.retrieve(datasetId, retrieveParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Doc {
        public DocRsp createDocByText(String difyAuthId, String datasetId, DocParam docParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.createDocByText(datasetId, docParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public DocRsp updateDocByText(String difyAuthId, String datasetId, String documentId, DocParam docParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.updateDocByText(datasetId, documentId, docParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public DocRsp createDocByFile(String difyAuthId, String datasetId, DocParam data, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.createDocByFile(datasetId, data, file, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public DocFileData getDocFile(String difyAuthId, String datasetId, String documentId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.getDocFile(datasetId, documentId, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public DocRsp updateDocByFile(String difyAuthId, String datasetId, String documentId, DocParam data, org.springframework.core.io.Resource file) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.updateDocByFile(datasetId, documentId, data, file, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public DocRsp getDocIndexingStatus(String difyAuthId, String datasetId, String batch) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.getDocIndexingStatus(datasetId, batch, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public void deleteDoc(String difyAuthId, String datasetId, String documentId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyDatasetApiProxy.deleteDoc(datasetId, documentId, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public void getDocList(String difyAuthId, String datasetId, String keyword, Integer page, Integer limit) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyDatasetApiProxy.getDocList(datasetId, keyword, page, limit, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }
    }

    public class Segment {
        public SegmentRsp createDocSegment(String difyAuthId, String datasetId, String documentId, SegmentCreateReq segmentParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.createDocSegment(datasetId, documentId, segmentParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public SegmentRsp updateDocSegment(String difyAuthId, String datasetId, String documentId, String segmentId, SegmentCreateReq segmentParam) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.updateDocSegment(datasetId, documentId, segmentId, segmentParam, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public SegmentRsp getDocSegmentList(String difyAuthId, String datasetId, String documentId, String keyword, String status) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            return difyDatasetApiProxy.getDocSegmentList(datasetId, documentId, keyword, status, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }

        public void deleteDocSegment(String difyAuthId, String datasetId, String documentId, String segmentId) throws BusinessException {
            DifyAuth difyAuth = difyAuthHelper.choosDifyAuth(difyAuthId);
            UriDto uriDto = new UriDto(difyAuth.getBaseUrl(), API_VERSION);
            difyDatasetApiProxy.deleteDocSegment(datasetId, documentId, segmentId, uriDto.getScheme(), uriDto.getHost(), uriDto.getPort(), uriDto.getVersion(), "Bearer " + difyAuth.getApiKey());
        }
    }

}
