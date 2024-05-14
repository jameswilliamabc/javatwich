package com.twilio.twilliosender.controllers;

import com.twilio.twilliosender.dto.MessageDTO;
import com.twilio.twilliosender.entity.Message;
import com.twilio.twilliosender.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.StringUtils;
import com.twilio.twilliosender.dto.MessageResponseDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO messageDTO) {
        try {
            String confirmationCode = messageService.sendMessage(messageDTO);
            return ResponseEntity.ok("Message sent successfully. Confirmation Code: " + confirmationCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseDTO>> getMessages(
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false, defaultValue = "desc") String orderBy,
            @RequestParam(required = false) String toNumber) {
        try {
            // Your existing logic to fetch messages
            List<Message> messages = messageService.getMessages(startDate, endDate, orderBy, toNumber);

            // Convert Message entities to MessageResponseDTO
            List<MessageResponseDTO> responseDTOs = new ArrayList<>();
            for (Message message : messages) {
                MessageResponseDTO responseDTO = new MessageResponseDTO();
                responseDTO.setId(message.getId());
                responseDTO.setCreatedAt(message.getCreatedAt());
                responseDTO.setToField(message.getToField());
                responseDTO.setMessage(message.getMessage());
                responseDTO.setSentAt(message.getMessageDetails().getSentAt());
                responseDTO.setConfirmationCode(message.getMessageDetails().getConfirmationCode());

                responseDTOs.add(responseDTO);
            }

            return ResponseEntity.ok(responseDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
