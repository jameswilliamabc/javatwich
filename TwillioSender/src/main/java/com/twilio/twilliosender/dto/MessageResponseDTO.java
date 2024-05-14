package com.twilio.twilliosender.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private String toField;
    private String message;
    private LocalDateTime sentAt;
    private String confirmationCode;
}

