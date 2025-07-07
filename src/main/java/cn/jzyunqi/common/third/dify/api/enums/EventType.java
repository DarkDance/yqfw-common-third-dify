package cn.jzyunqi.common.third.dify.api.enums;

/**
 * @author wiiyaya
 * @since 2025/1/14
 */
public enum EventType {
    message,//文本块
    message_end,//消息结束
    message_file,//文件
    tts_message,//语音消息
    tts_message_end,//语音消息结束
    iteration_started,//迭代开始
    iteration_next,//迭代中
    iteration_completed,//迭代完成
    iteration_end,//迭代结束
    parallel_branch_started,//并行分支开始
    parallel_branch_finished,//并行分支结束
    loop_started,//循环开始
    loop_next,//循环中
    loop_completed,//循环完成
    message_replace,//消息替换
    workflow_started,//工作流开始
    node_started,//节点开始
    node_retry,//节点重试
    node_finished,//节点结束
    workflow_finished,//工作流结束
    error, //错误
    ping,//心跳
    agent_thought,//AI思考
    agent_message,//AI消息
    agent_log,//AI日志
    text_chunk,//文本块
    text_replace,//文本替换
}
