package cn.jzyunqi.common.third.dify.api.enums;

/**
 * @author wiiyaya
 * @since 2025/1/14
 */
public enum EventType {
    message,//文本块
    message_file,//文件
    message_end,//消息结束
    tts_message,//语音消息
    tts_message_end,//语音消息结束
    message_replace,//消息替换
    workflow_started,//工作流开始
    node_started,//节点开始
    node_finished,//节点结束
    workflow_finished,//工作流结束
    error, //错误
    ping//心跳
}
