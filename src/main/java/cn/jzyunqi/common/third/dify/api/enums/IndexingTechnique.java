package cn.jzyunqi.common.third.dify.api.enums;

/**
 * @author wiiyaya
 * @since 2025/3/2
 */
public enum IndexingTechnique {
    high_quality,//高质量：使用 embedding 模型进行嵌入，构建为向量数据库索引
    economy//经济：使用 keyword table index 的倒排索引进行构建
}
