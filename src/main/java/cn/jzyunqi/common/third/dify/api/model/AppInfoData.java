package cn.jzyunqi.common.third.dify.api.model;

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
public class AppInfoData {
    private String name;
    private String description;
    private List<String> tags;
}
