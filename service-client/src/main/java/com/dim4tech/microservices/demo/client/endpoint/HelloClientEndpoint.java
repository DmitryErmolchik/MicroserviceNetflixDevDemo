package com.dim4tech.microservices.demo.client.endpoint;

import com.dim4tech.microservices.demo.client.dto.MessageDto;
import com.dim4tech.microservices.demo.client.remote.version_1_0.HelloServerClient;
import com.dim4tech.microservices.demo.client.configuration.ApplicationConfigurationBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.InetAddress;

@RestController
@RequestMapping(path = "/api/v${microservice.apiVersion}/hello")
public class HelloClientEndpoint {
    private final HelloServerClient client;
    private final ApplicationConfigurationBean applicationConfigurationBean;

    @Autowired
    public HelloClientEndpoint(final HelloServerClient client,
                               final ApplicationConfigurationBean applicationConfigurationBean) {
        this.client = client;
        this.applicationConfigurationBean = applicationConfigurationBean;
    }

    @GetMapping
    public Mono<MessageDto> getHelloFromServer() {
        return Mono.fromCallable(() ->
                client.getHelloMessage(InetAddress.getLocalHost().getHostName()))
                .onErrorResume((throwable ->
                        Mono.fromCallable(() -> client.getHelloMessage("localhost")))
                )
                .map(message -> new MessageDto(message.getMessage() + " (This message from client service from environment " +
                        applicationConfigurationBean.getEnvironment()+ ")"));
    }
}
