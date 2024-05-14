package com.twilio.twilliosender.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    private final String accountSid;
    private final String authToken;
    private final String twilioPhoneNumber;

    public TwilioService() {
        Dotenv dotenv = Dotenv.configure().load();
        this.accountSid = dotenv.get("TWILIO_ACCOUNT_SID");
        this.authToken = dotenv.get("TWILIO_AUTH_TOKEN");
        this.twilioPhoneNumber = dotenv.get("TWILIO_PHONE_NUMBER");

        // Initialize Twilio with your Account SID and Auth Token
        Twilio.init(accountSid, authToken);
    }

    public String sendMessage(String to, String messageBody) {
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(twilioPhoneNumber),
                messageBody
        ).create();

        return message.getSid(); // Returning confirmation code
    }
}
