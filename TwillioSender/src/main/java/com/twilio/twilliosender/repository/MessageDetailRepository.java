package com.twilio.twilliosender.repository;

import com.twilio.twilliosender.entity.MessageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MessageDetailRepository extends JpaRepository<MessageDetail, Long> {
}