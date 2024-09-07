package com.specter.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class EmailIntegrationConfig {

    //Определение бил для emailChannel
    @Bean
    public MessageChannel emailChannel() {
        return new DirectChannel();
    }

    //Определяем MailSendingMessageHandler для отправки email
    @Bean
    @ServiceActivator(inputChannel = "emailChannel")
    public MessageHandler mailOutboundAdapter(JavaMailSender mailSender) {
        return new MailSendingMessageHandler(mailSender);
    }
}
