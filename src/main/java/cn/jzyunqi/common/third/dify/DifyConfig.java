package cn.jzyunqi.common.third.dify;

import cn.jzyunqi.common.third.dify.api.DatasetApiProxy;
import cn.jzyunqi.common.third.dify.api.DifyApiProxy;
import cn.jzyunqi.common.third.dify.api.DifyStreamApiProxy;
import cn.jzyunqi.common.third.dify.common.DifyHttpExchangeWrapper;
import cn.jzyunqi.common.third.dify.common.utils.DifyFormatUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

/**
 * @author wiiyaya
 * @since 2025/1/10
 */
@Configuration
public class DifyConfig {

    @Bean
    public DifyHttpExchangeWrapper responseCheckWrapper() {
        return new DifyHttpExchangeWrapper();
    }

    @Bean
    public DifyClient difyClient() {
        return new DifyClient();
    }

    @Bean
    public DifyApiProxy difyApiProxy(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.clone().codecs(DifyFormatUtils::jackson2Config).build();
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        webClientAdapter.setBlockTimeout(Duration.ofMinutes(5));
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(DifyApiProxy.class);
    }

    @Bean
    public DifyStreamApiProxy difyStreamApiProxy(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.clone().codecs(DifyFormatUtils::jackson2Config).build();
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(5));
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(DifyStreamApiProxy.class);
    }

    @Bean
    public DatasetApiProxy datasetApiProxy(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.clone().codecs(DifyFormatUtils::jackson2Config).build();
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(5));
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(DatasetApiProxy.class);
    }
}
