package com.messagingservice.message.service.validation;

import com.messagingservice.exception.ValidationException;
import com.messagingservice.message.model.SendMessageRequest;
import com.messagingservice.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageValidator messageValidator;

    @Test
    @DisplayName("Should throw validation exception when sender and recipient the same user")
    public void shouldThrowValidationExceptionWhenSenderAndRecipientAreEqual() {
        var sendMessageRequest = new SendMessageRequest(1l, 1l, "Hello World!");

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> messageValidator.validateSender(sendMessageRequest));

        assertEquals("400 BAD_REQUEST \"Sender and recipient must be different.\"", validationException.getMessage());
    }

    @Test
    @DisplayName("Should throw validation exception when user does not exist")
    public void shouldThrowValidationExceptionWhenUserNotExists() {

        when(userRepository.existsById(anyLong())).thenReturn(false);

        ValidationException validationException = assertThrows(ValidationException.class,
                () -> messageValidator.validateIsUserExists(1l));

        assertEquals("400 BAD_REQUEST \"User with id 1 does not exist.\"", validationException.getMessage());
    }

}