package com.twilio.twilliosender.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String to;
    private String message;

    // Getters and setters
}
