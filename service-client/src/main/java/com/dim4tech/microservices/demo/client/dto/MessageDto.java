package com.dim4tech.microservices.demo.client.dto;

public class MessageDto {
    private final String message;

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
