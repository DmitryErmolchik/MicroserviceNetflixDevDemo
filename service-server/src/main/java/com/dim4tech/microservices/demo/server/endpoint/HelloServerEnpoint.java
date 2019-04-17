package com.dim4tech.microservices.demo.server.endpoint;

import com.dim4tech.microservices.demo.server.configuration.ApplicationConfigurationBean;
import com.dim4tech.microservices.demo.server.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = "/api/v${microservice.apiVersion}/hello")
public class HelloServerEnpoint {
    private final ApplicationConfigurationBean applicationConfigurationBean;

    @Autowired
    public HelloServerEnpoint(final ApplicationConfigurationBean applicationConfigurationBean) {
        this.applicationConfigurationBean = applicationConfigurationBean;
    }

    @GetMapping
    Mono<MessageDto> getHello(String clientName) {
        return Mono.just(new MessageDto("Hello " + clientName + "! (This message from " +
                applicationConfigurationBean.getEnvironment() + " environment)"));
    }
}
