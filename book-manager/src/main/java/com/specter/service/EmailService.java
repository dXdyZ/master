package com.specter.service;

import com.specter.integration.EmailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.mail.MailHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final MessageChannel emailChannel;
    private final EmailProperties emailProperties;

    public void sendEmail(String to, String subject, String body) {
        emailChannel.send(
                MessageBuilder.withPayload(body)
                        .setHeader(MailHeaders.TO, to)
                        .setHeader(MailHeaders.SUBJECT, subject)
                        .setHeader(MailHeaders.FROM, emailProperties.getUsername())
                        .build()
        );
    }
}
