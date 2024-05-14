package com.twilio.twilliosender.repository;

import com.twilio.twilliosender.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// MessageRepository.java
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByToFieldAndCreatedAtBetween(String toNumber, LocalDateTime startDate, LocalDateTime endDate);

    List<Message> findByToField(String toNumber);

    List<Message> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}