package com.dim4tech.microservices.demo.apigateway.configuration.development;

import com.dim4tech.microservices.demo.apigateway.multieureka.MultiEurekaClientManager;
import com.dim4tech.microservices.demo.apigateway.multieureka.MultiEurekaServerList;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ServerList;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonDevConfiguration {
    @RibbonClientName
    private String name = "client";

    @Bean
    public IClientConfig getClientConfig() {
        return IClientConfig.Builder.newBuilder(name)
                .withConnectTimeout(10000)
                .withReadTimeout(10000)
                .withServerListRefreshIntervalMills(1000)
                .withGZIPContentEncodingFilterEnabled(true)
                .withConnectionManagerTimeout(1000)
                .withConnIdleEvictTimeMilliSeconds(1000)
                .withConnectionCleanerRepeatIntervalMills(5000)
                .withConnIdleEvictTimeMilliSeconds(5000)
                .build();
    }

    @Bean
    public ServerList<?> getServerList(IClientConfig clientConfig, MultiEurekaClientManager multiEurekaClientManager) {
        return new MultiEurekaServerList(clientConfig, multiEurekaClientManager.getEurekaClients());
    }
}