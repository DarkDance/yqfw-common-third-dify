package cn.jzyunqi.common.third.dify.api.model;

import cn.jzyunqi.common.third.dify.api.enums.FileType;
import cn.jzyunqi.common.third.dify.api.enums.ResponseMode;
import cn.jzyunqi.common.third.dify.api.enums.TransferMethod;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author wiiyaya
 * @since 2025/1/11
 */
@Getter
@Setter
@ToString
public class ChatMsgParam {

    private String query;

    // App 定义的各变量值
    private Map<String, Object> inputs;

    //streaming / blocking
    private ResponseMode responseMode;

    //用户唯一标识
    private String user;

    //（选填）会话 ID
    private String conversationId;

    //（选填）自动生成标题
    private Boolean autoGenerateName;

    private List<FileInfo> files;

    @Getter
    @Setter
    @ToString
    public static class FileInfo {
        private FileType type;
        private TransferMethod transferMethod;
        private String url;
        private String uploadFileId;
    }
}
