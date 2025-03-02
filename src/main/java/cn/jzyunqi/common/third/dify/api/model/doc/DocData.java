package cn.jzyunqi.common.third.dify.api.model.doc;

import cn.jzyunqi.common.third.dify.api.enums.DocForm;
import cn.jzyunqi.common.third.dify.api.enums.DocType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @since 2025/3/2
 */
@Getter
@Setter
@ToString
public class DocData {
    private String id;
    private DocType docType;
    private Integer position;
    private String dataSourceType;
    private FileInfo dataSourceInfo;
    private String datasetProcessRuleId;
    private String name;
    private String createdFrom;
    private String createdBy;
    private String createdAt;
    private Integer tokens;
    private String indexingStatus;
    private String error;
    private Boolean enabled;
    private String disabledAt;
    private String disabledBy;
    private Boolean archived;
    private String displayStatus;
    private Integer wordCount;
    private Integer hitCount;
    private DocForm docForm;

    @Getter
    @Setter
    @ToString
    public static class FileInfo {
        private String uploadFileId;
    }
}
