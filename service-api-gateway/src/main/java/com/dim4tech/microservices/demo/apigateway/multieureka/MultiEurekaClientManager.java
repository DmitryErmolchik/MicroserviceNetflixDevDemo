package com.dim4tech.microservices.demo.apigateway.multieureka;

import com.netflix.discovery.EurekaClient;

import javax.annotation.PreDestroy;
import java.util.List;


public class MultiEurekaClientManager {
    private final List<EurekaClient> eurekaClients;

    public MultiEurekaClientManager(List<EurekaClient> eurekaClients) {
        this.eurekaClients = eurekaClients;
    }

    public List<EurekaClient> getEurekaClients() {
        return eurekaClients;
    }

    @PreDestroy
    public void shutdown() {
        for (EurekaClient eurekaClient : eurekaClients) {
            eurekaClient.shutdown();
        }
    }
}
