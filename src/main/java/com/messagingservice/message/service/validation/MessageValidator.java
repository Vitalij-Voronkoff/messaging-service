package com.messagingservice.message.service.validation;

import com.messagingservice.exception.ValidationException;
import com.messagingservice.message.model.SendMessageRequest;
import com.messagingservice.user.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageValidator {

    private final UserRepository userRepository;

    public void validateSendRequest(SendMessageRequest sendMessageRequest) {
        validateSender(sendMessageRequest);
        validateIsUserExists(sendMessageRequest.getSenderId());
        validateIsUserExists(sendMessageRequest.getRecipientId());
    }

    /**
     * Validates that sender and recipient is not the same user.
     *
     * @param sendMessageRequest
     */
    protected void validateSender(SendMessageRequest sendMessageRequest) {
        if (sendMessageRequest.getSenderId() == sendMessageRequest.getRecipientId()) {
            throw new ValidationException("Sender and recipient must be different.");
        }
    }

    /**
     * Validates is user exists in the database.
     *
     * @param userId
     */
    protected void validateIsUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ValidationException(String.format("User with id %d does not exist.", userId));
        }
    }
}
