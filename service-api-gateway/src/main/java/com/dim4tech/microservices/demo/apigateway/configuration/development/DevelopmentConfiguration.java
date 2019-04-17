package com.dim4tech.microservices.demo.apigateway.configuration.development;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!prod")
@Configuration
@RibbonClients(defaultConfiguration = RibbonDevConfiguration.class)
public class DevelopmentConfiguration {
}
