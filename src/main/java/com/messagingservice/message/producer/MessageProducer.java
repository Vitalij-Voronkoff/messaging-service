package com.messagingservice.message.producer;

import com.messagingservice.message.model.SendMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange-name}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public void produce(SendMessageRequest sendMessageRequest) {

        log.info("Sending message from sender {} to the queue", sendMessageRequest.getSenderId());

        rabbitTemplate.convertAndSend(exchange, routingKey, sendMessageRequest);
    }
}
