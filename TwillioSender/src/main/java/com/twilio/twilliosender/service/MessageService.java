package com.twilio.twilliosender.service;

import com.twilio.twilliosender.dto.MessageDTO;
import com.twilio.twilliosender.entity.Message;
import com.twilio.twilliosender.entity.MessageDetail;
import com.twilio.twilliosender.repository.MessageDetailRepository;
import com.twilio.twilliosender.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

// MessageService.java
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageDetailRepository messageDetailRepository;
    private final TwilioService twilioService;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          MessageDetailRepository messageDetailRepository,
                          TwilioService twilioService) {
        this.messageRepository = messageRepository;
        this.messageDetailRepository = messageDetailRepository;
        this.twilioService = twilioService;
    }

    @Transactional
    public String sendMessage(MessageDTO messageDTO) {
        try {
            // Save message
            Message message = new Message();
            message.setToField(messageDTO.getTo());
            message.setMessage(messageDTO.getMessage());
            messageRepository.save(message);

            // Send message and get confirmation code
            String confirmationCode = twilioService.sendMessage(messageDTO.getTo(), messageDTO.getMessage());

            // Save message detail
            MessageDetail messageDetail = new MessageDetail();
            messageDetail.setMessage(message);
            messageDetail.setConfirmationCode(confirmationCode);
            messageDetailRepository.save(messageDetail);

            return confirmationCode;
        } catch (Exception e) {
            // Handle exception
            throw new RuntimeException("Failed to send message: " + e.getMessage(), e);
        }
    }

    public List<Message> getMessages(LocalDateTime startDate, LocalDateTime endDate, String orderBy, String toNumber) {
        List<Message> messages;

        if (!StringUtils.hasText(toNumber)) {
            toNumber = null;
        }

        if (startDate != null && endDate != null) {
            if (endDate.isBefore(startDate)) {
                // Swap startDate and endDate
                LocalDateTime temp = startDate;
                startDate = endDate;
                endDate = temp;
            }
        }

        if (toNumber != null) {
            if (startDate != null && endDate != null) {
                messages = messageRepository.findByToFieldAndCreatedAtBetween(toNumber, startDate, endDate);
            } else {
                messages = messageRepository.findByToField(toNumber);
            }
        } else {
            if (startDate != null && endDate != null) {
                messages = messageRepository.findByCreatedAtBetween(startDate, endDate);
            } else {
                messages = messageRepository.findAll();
            }
        }

        if ("asc".equalsIgnoreCase(orderBy)) {
            messages.sort(Comparator.comparing(Message::getCustomCreatedAt));
        } else {
            messages.sort(Comparator.comparing(Message::getCustomCreatedAt).reversed());
        }
        return messages;
    }
}
