package cn.jzyunqi.common.third.dify.api.model;

import cn.jzyunqi.common.third.dify.api.enums.TransferMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wiiyaya
 * @since 2025/1/16
 */
@Getter
@Setter
@ToString
public class AppConfigInfoData {
    private String openingStatement;//开场白
    private String introduction;//也是开场白
    private List<String> suggestedQuestions;

    private FeatureConfig suggestedQuestionsAfterAnswer;
    private FeatureConfig speechToText;
    private FeatureConfig retrieverResource;
    private FeatureConfig annotationReply;
    private List<FormConfig> userInputForm;
    private FileUploadConfig fileUpload;
    private SystemConfig systemParameters;

    @Getter
    @Setter
    @ToString
    public static class FeatureConfig {
        private Boolean enabled;
    }

    @Getter
    @Setter
    @ToString
    public static class FormConfig {
        private FormFieldConfig textInput;
        private FormFieldConfig paragraph;
        private FormFieldConfig select;
    }

    @Getter
    @Setter
    @ToString
    public static class FormFieldConfig {
        private String label;
        private String variable;
        private String maxLength;
        private Boolean required;
        @JsonProperty("default")
        private String defaultOne;
        private List<String> options;
    }

    @Getter
    @Setter
    @ToString
    public static class FileUploadConfig {
        private ImageConfig image;
    }

    @Getter
    @Setter
    @ToString
    public static class ImageConfig {
        private Boolean enabled;
        private Integer numberLimits;
        private List<TransferMethod> transferMethods;
    }

    @Getter
    @Setter
    @ToString
    public static class SystemConfig {
        private Integer fileSizeLimit;
        private Integer imageFileSizeLimit;
        private Integer audioFileSizeLimit;
        private Integer videoFileSizeLimit;
    }
}
