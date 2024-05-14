package com.twilio.twilliosender.entity;

import lombok.Data;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // Enable JPA Auditing
public class MessageDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime sentAt;

    private String confirmationCode;

    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;
}
