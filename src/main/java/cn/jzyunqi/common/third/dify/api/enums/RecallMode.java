package cn.jzyunqi.common.third.dify.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiiyaya
 * @since 2025/3/2
 */
@Getter
@AllArgsConstructor
public enum RecallMode {
    full_doc("full-doc"),
    paragraph("paragraph");

    private final String value;
}
