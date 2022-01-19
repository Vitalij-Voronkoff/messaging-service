package com.messagingservice.message.api.v1;

import com.messagingservice.message.model.GetMessageResponse;
import com.messagingservice.message.model.SendMessageRequest;
import com.messagingservice.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@RequestBody @Validated SendMessageRequest sendMessageRequest) {

        log.info("Sending message from sender {} to recipient {}", sendMessageRequest.getSenderId(), sendMessageRequest.getRecipientId());

        messageService.sendMessage(sendMessageRequest);
    }

    @GetMapping("/recipients/{id}")
    public List<GetMessageResponse> getAllReceivedMessages(@PathVariable("id") long recipientId,
                                                           @RequestParam(value = "senderId", required = false) Long senderId) {
        if (senderId == null) {
            log.info("Getting all messages for recipient {}", recipientId);

            return messageService.findAllReceivedMessages(recipientId);
        } else {
            log.info("Getting all messages for recipient {} sent by sender {}", recipientId, senderId);

            return messageService.findAllReceivedFromSenderMessages(senderId, recipientId);
        }
    }

    @GetMapping("/senders/{id}")
    public List<GetMessageResponse> getAllSentMessages(@PathVariable("id") long senderId) {

        log.info("Getting all messages sent by sender {}", senderId);

        return messageService.findAllSentMessages(senderId);
    }

}
