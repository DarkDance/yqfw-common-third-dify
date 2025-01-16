package cn.jzyunqi.common.third.dify.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author wiiyaya
 * @since 2025/1/16
 */
@Getter
@Setter
@ToString
public class FileUploadData {
    private String id;
    private String name;
    private Long size;
    private String extension;
    private String mimeType;
    private String createdBy;
    private String createdAt;
}
