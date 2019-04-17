package com.dim4tech.microservices.demo.client.remote.version_1_0;

public class HelloServerMessageDto {
    private String message;

    public HelloServerMessageDto() {
    }

    public HelloServerMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
