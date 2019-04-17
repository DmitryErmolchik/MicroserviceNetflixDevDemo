package com.dim4tech.microservices.demo.apigateway.configuration.production;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod")
@Configuration
@EnableEurekaClient
public class ProductionConfiguration {
}
