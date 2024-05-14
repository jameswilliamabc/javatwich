package com.twilio.twilliosender.entity;

import jakarta.persistence.GenerationType;
import lombok.Data;
//import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import com.twilio.twilliosender.entity.MessageDetail;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class) // Enable JPA Auditing
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    private String toField;
    private String message;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL)
    private MessageDetail messageDetails;

    // Custom method to get the creation date
    public LocalDateTime getCustomCreatedAt() {
        return this.createdAt;
    }
}
