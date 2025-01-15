package cn.jzyunqi.common.third.dify.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
public class DifyFormatUtils {

    public static final ObjectMapper OBJECT_MAPPER;

    static {
        //JavaTimeModule javaTimeModule = new JavaTimeModule();
        //ZonedDateTimeSerializer zonedDateTimeSerializer = new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX"));
        //javaTimeModule.addSerializer(zonedDateTimeSerializer);

        OBJECT_MAPPER = JsonMapper.builder()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE) // 驼峰转下划线
                //.addModule(javaTimeModule) // 时间序列化
                .serializationInclusion(JsonInclude.Include.NON_NULL) // 序列化时不输出null属性
                .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY) // 排序属性
                //.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // 禁用时间戳
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // 反序列化时忽略未知属性
                .build();
    }

    public static void jackson2Config(ClientCodecConfigurer codecConfig) {
        codecConfig.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(DifyFormatUtils.OBJECT_MAPPER));
        codecConfig.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(DifyFormatUtils.OBJECT_MAPPER));
    }
}
