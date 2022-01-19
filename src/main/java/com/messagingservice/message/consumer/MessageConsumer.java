package com.messagingservice.message.consumer;

import com.messagingservice.message.mapper.MessageMapper;
import com.messagingservice.message.model.SendMessageRequest;
import com.messagingservice.message.persistence.entity.Message;
import com.messagingservice.message.persistence.repository.MessageRepository;
import com.messagingservice.user.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;

    @Transactional
    @RabbitListener(queues = "messages")
    public void consume(SendMessageRequest sendMessageRequest) {

        log.info("Saving message from sender {} to the DB", sendMessageRequest.getSenderId());

        Message message = messageMapper.fromSendRequest(sendMessageRequest);
        message.setRecipient(userRepository.getById(sendMessageRequest.getRecipientId()));
        message.setSender(userRepository.getById(sendMessageRequest.getSenderId()));

        messageRepository.save(message);
    }
}
