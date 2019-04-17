package com.dim4tech.microservices.demo.apigateway.configuration.development;

import com.dim4tech.microservices.demo.apigateway.multieureka.MultiEurekaClientManager;
import com.dim4tech.microservices.demo.apigateway.multieureka.MultiEurekaClientsConfigBean;
import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.EurekaInstanceConfig;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.LeaseInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.composite.CompositeDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.netflix.eureka.CloudEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnBean(DevelopmentConfiguration.class)
public class EurekaDiscoveryConfiguration {
    @Bean
    public InetUtils getInetUtils() {
        InetUtilsProperties inetUtilsProperties = new InetUtilsProperties();
        return new InetUtils(inetUtilsProperties);
    }

    @Bean
    public EurekaInstanceConfig getEurekaInstanceConfig(InetUtils inetUtils,
                                                        @Value("${server.port}") int port) {
        EurekaInstanceConfigBean configBean = new EurekaInstanceConfigBean(inetUtils);
        configBean.setNonSecurePort(port);
        return configBean;
    }

    @Bean
    public InstanceInfo getInstanceInfo(EurekaInstanceConfig eurekaInstanceConfig) {
        InstanceInfo instanceInfo = InstanceInfo.Builder.newBuilder()
                .setNamespace("dev")
                .setPort(eurekaInstanceConfig.getNonSecurePort())
                .setInstanceId(eurekaInstanceConfig.getHostName(false) + ":" +
                        eurekaInstanceConfig.getAppname() + ":" +
                        eurekaInstanceConfig.getNonSecurePort())
                .setHostName(eurekaInstanceConfig.getHostName(false))
                .setAppName(eurekaInstanceConfig.getAppname().toUpperCase())
                .setVIPAddress(eurekaInstanceConfig.getAppname())
                .setSecureVIPAddress(eurekaInstanceConfig.getAppname())
                .setDataCenterInfo(eurekaInstanceConfig.getDataCenterInfo())
                .setIPAddr(eurekaInstanceConfig.getIpAddress())
                .setHomePageUrl(null, "${dev.hostname}")
                .setHealthCheckUrls(eurekaInstanceConfig.getHealthCheckUrlPath(), null, null)
                .setStatusPageUrl(eurekaInstanceConfig.getStatusPageUrlPath(), null)
                .setLeaseInfo(LeaseInfo.Builder.newBuilder()
                        .setRenewalTimestamp(eurekaInstanceConfig.getLeaseRenewalIntervalInSeconds())
                        .setDurationInSecs(eurekaInstanceConfig.getLeaseExpirationDurationInSeconds())
                        .build())
                .build();
        return instanceInfo;
    }

    @Bean
    public ApplicationInfoManager getApplicationInfoManager(EurekaInstanceConfig eurekaInstanceConfig, InstanceInfo instanceInfo) {
        return new ApplicationInfoManager(eurekaInstanceConfig, instanceInfo);
    }

    @Bean
    MultiEurekaClientManager getMultiEurekaClientManager(final ApplicationContext applicationContext,
                                                         final ApplicationInfoManager applicationInfoManager,
                                                         final MultiEurekaClientsConfigBean config) {
        List<EurekaClient> eurekaClients = new ArrayList<>(config.getClients().size());
        for (EurekaClientConfigBean clientConfig : config.getClients()) {
            eurekaClients.add(new CloudEurekaClient(applicationInfoManager, clientConfig, applicationContext));
        }
        return new MultiEurekaClientManager(eurekaClients);
    }

    @Bean
    public DiscoveryClient getEurekaDiscoveryClients(final MultiEurekaClientManager multiEurekaClientManager) {
        List<DiscoveryClient> eurekaDiscoveryClients = new ArrayList<>(multiEurekaClientManager.getEurekaClients().size());
        for (EurekaClient eurekaClient : multiEurekaClientManager.getEurekaClients()) {
            eurekaDiscoveryClients.add(new EurekaDiscoveryClient(eurekaClient, eurekaClient.getEurekaClientConfig()));
        }
        return new CompositeDiscoveryClient(eurekaDiscoveryClients);
    }
}
