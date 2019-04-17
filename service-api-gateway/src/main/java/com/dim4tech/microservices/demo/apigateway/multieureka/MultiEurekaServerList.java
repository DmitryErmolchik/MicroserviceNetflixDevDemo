package com.dim4tech.microservices.demo.apigateway.multieureka;

import com.netflix.client.config.IClientConfig;
import com.netflix.discovery.EurekaClient;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiEurekaServerList extends AbstractServerList<DiscoveryEnabledServer> {
    private static final Logger LOG = LoggerFactory.getLogger(MultiEurekaServerList.class);
    private final List<EurekaClient> eurekaClients;
    private final List<DiscoveryEnabledNIWSServerList> discoveryEnabledNIWSServerLists = new ArrayList<>();

    public MultiEurekaServerList(IClientConfig clientConfig, List<EurekaClient> eurekaClients) {
        this.eurekaClients = eurekaClients;
        initWithNiwsConfig(clientConfig);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        for (final EurekaClient eurekaClient : eurekaClients) {
            discoveryEnabledNIWSServerLists.add(new DiscoveryEnabledNIWSServerList(clientConfig, () -> eurekaClient));
        }
    }

    @Override
    public List<DiscoveryEnabledServer> getInitialListOfServers() {
        return getDiscoveryEnabledServers();
    }

    @Override
    public List<DiscoveryEnabledServer> getUpdatedListOfServers() {
        return getDiscoveryEnabledServers();
    }

    private List<DiscoveryEnabledServer> getDiscoveryEnabledServers() {
        for (DiscoveryEnabledNIWSServerList discoveryEnabledNIWSServerList : discoveryEnabledNIWSServerLists) {
            List<DiscoveryEnabledServer> servers = discoveryEnabledNIWSServerList.getUpdatedListOfServers();
            if (!CollectionUtils.isEmpty(servers)) {
                return servers;
            }
        }
        return Collections.emptyList();
    }
}
