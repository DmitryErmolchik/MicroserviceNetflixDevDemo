package com.dim4tech.microservices.demo.client.remote.version_1_0;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-api-gateway")
public interface HelloServerClient {
    @GetMapping("/service-server/api/v1.0/hello?clientName={clientName}")
    HelloServerMessageDto getHelloMessage(@PathVariable("clientName") String clientName);
}
