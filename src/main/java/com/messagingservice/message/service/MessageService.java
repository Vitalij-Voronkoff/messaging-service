package com.messagingservice.message.service;

import com.messagingservice.message.mapper.MessageMapper;
import com.messagingservice.message.model.GetMessageResponse;
import com.messagingservice.message.model.SendMessageRequest;
import com.messagingservice.message.persistence.repository.MessageRepository;
import com.messagingservice.message.producer.MessageProducer;
import com.messagingservice.message.service.validation.MessageValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final MessageProducer messageProducer;
    private final MessageValidator messageValidator;

    /**
     * Send a request from the sender to the recipient.
     *
     * @param sendMessageRequest
     */
    public void sendMessage(SendMessageRequest sendMessageRequest) {

        messageValidator.validateSendRequest(sendMessageRequest);

        messageProducer.produce(sendMessageRequest);
    }

    /**
     * Find all messages received by recipient id.
     *
     * @param recipientId
     * @return
     */
    public List<GetMessageResponse> findAllReceivedMessages(long recipientId) {
        var receivedMessages = messageRepository.findAllByRecipientId(recipientId);

        return messageMapper.toGetAllMessageResponse(receivedMessages);
    }

    /**
     * Find all messages sent by sender id.
     *
     * @param senderId
     * @return
     */
    public List<GetMessageResponse> findAllSentMessages(long senderId) {
        var receivedMessages = messageRepository.findAllBySenderId(senderId);

        return messageMapper.toGetAllMessageResponse(receivedMessages);
    }

    /**
     * Find all received messages from particular sender.
     *
     * @param senderId
     * @param recipientId
     * @return
     */
    public List<GetMessageResponse> findAllReceivedFromSenderMessages(long senderId, long recipientId) {
        var receivedMessages = messageRepository.findAllByRecipientIdAndSenderId(recipientId, senderId);

        return messageMapper.toGetAllMessageResponse(receivedMessages);
    }

}
