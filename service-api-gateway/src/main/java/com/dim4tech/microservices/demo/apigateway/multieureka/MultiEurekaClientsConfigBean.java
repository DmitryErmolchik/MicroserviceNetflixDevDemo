package com.dim4tech.microservices.demo.apigateway.multieureka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(MultiEurekaClientsConfigBean.PREFIX)
public class MultiEurekaClientsConfigBean implements Ordered {
    public static final String PREFIX = "multieureka";
    private static final int ORDER = 0;

    private List<EurekaClientConfigBean> clientConfigs;

    public List<EurekaClientConfigBean> getClients() {
        return clientConfigs;
    }

    public void setClients(List<EurekaClientConfigBean> clientConfigs) {
        this.clientConfigs = clientConfigs.stream()
                .sorted(Comparator.comparingInt(EurekaClientConfigBean::getOrder))
                .collect(Collectors.toList());
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
