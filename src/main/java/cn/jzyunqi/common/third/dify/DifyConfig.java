package cn.jzyunqi.common.third.dify;

import cn.jzyunqi.common.third.dify.api.DifyDatasetApiProxy;
import cn.jzyunqi.common.third.dify.api.DifyBlockApiProxy;
import cn.jzyunqi.common.third.dify.api.DifyStreamApiProxy;
import cn.jzyunqi.common.third.dify.common.DifyHttpExchangeWrapper;
import cn.jzyunqi.common.third.dify.common.utils.DifyFormatUtils;
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
    public DifyHttpExchangeWrapper difyHttpExchangeWrapper() {
        return new DifyHttpExchangeWrapper();
    }

    @Bean
    public DifyClient difyClient() {
        return new DifyClient();
    }

    @Bean
    public DifyBlockApiProxy difyBlockApiProxy(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.clone().codecs(DifyFormatUtils::jackson2Config).build();
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        webClientAdapter.setBlockTimeout(Duration.ofMinutes(5));
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(DifyBlockApiProxy.class);
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
    public DifyDatasetApiProxy difyDatasetApiProxy(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder.clone().codecs(DifyFormatUtils::jackson2Config).build();
        WebClientAdapter webClientAdapter = WebClientAdapter.create(webClient);
        webClientAdapter.setBlockTimeout(Duration.ofSeconds(5));
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(webClientAdapter).build();
        return factory.createClient(DifyDatasetApiProxy.class);
    }
}
