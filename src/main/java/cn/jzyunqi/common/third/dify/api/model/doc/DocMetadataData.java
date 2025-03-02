package cn.jzyunqi.common.third.dify.api.model.doc;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class DocMetadataData {
    //see https://github.com/langgenius/dify/blob/main/api/services/dataset_service.py#L475
    private String title;
    private String language;
    private String author;
    private String publisher;
    private String publicationDate;
    private String isbn;
    private String category;

    //private String title;
    private String url;
    //private String language  ;
    private String publishDate;
    @JsonProperty("author/publisher")
    private String authorPublisher;
    @JsonProperty("topic/keywords")
    private String topicKeywords;
    private String description;


}
