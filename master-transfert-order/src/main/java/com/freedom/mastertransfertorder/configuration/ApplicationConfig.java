package com.freedom.mastertransfertorder.configuration;

import com.freedom.mastertransfertorder.configuration.web.CustomRequestInterceptor;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;

@Configuration
@EnableConfigurationProperties
@EnableAsync
public class ApplicationConfig {

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
        result.setMaxTotal(200);
        result.setDefaultMaxPerRoute(50);
        return result;
    }

    @Bean
    public RequestConfig requestConfig() {
        RequestConfig result = RequestConfig.custom()
                                            .setConnectionRequestTimeout(2000)
                                            .setConnectTimeout(2000)
                                            .setSocketTimeout(3000)
                                            .build();
        return result;
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
                                          RequestConfig requestConfig) {
        //If the Keep-Alive header is not present in the response,
        // HttpClient assumes the connection can be kept alive indefinitely
        // That need to implement custom keep alive strategy.
        ConnectionKeepAliveStrategy customKeepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase
                                                      ("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return 3 * 1000;
            }
        };
        CloseableHttpClient result = HttpClientBuilder
                .create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(customKeepAliveStrategy)
                .build();
        return result;
    }

    @Bean("customRestTemplate")
    public RestTemplate customRestTemplate(HttpClient httpClient) {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setInterceptors(Collections.singletonList(new CustomRequestInterceptor()));
        return restTemplate;
    }
}
