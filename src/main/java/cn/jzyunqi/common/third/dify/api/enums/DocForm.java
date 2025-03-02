package cn.jzyunqi.common.third.dify.api.enums;

/**
 * @author wiiyaya
 * @since 2025/3/2
 */
public enum DocForm {
    text_model, //text 文档直接 embedding，经济模式默认为该模式
    hierarchical_model, //parent-child 模式
    qa_model //Q&A 模式：为分片文档生成 Q&A 对，然后对问题进行 embedding
}
