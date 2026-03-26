package cn.jzyunqi.common.third.dify.api.enums;

/**
 * @author wiiyaya
 * @since 2025/1/14
 */
public enum NodeStatus {
    //已验证的状态（文档没有，但是实际在chatflow有）
    start, success,
    //未验证状态（文档有，实际在chatflow没有）
    running, succeeded, failed, stopped
}
