package com.messagingservice.user.service.validation;

import com.messagingservice.exception.ValidationException;
import com.messagingservice.user.model.CreateUserRequest;
import com.messagingservice.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    @DisplayName("Should throw a validation exception if a user with the following nickname already exists.")
    public void shouldThrowValidationException() {
        when(userRepository.existsByNickname(any())).thenReturn(true);

        ValidationException validationException =
                assertThrows(ValidationException.class,
                        () -> userValidator.validateCreate(new CreateUserRequest("user 1")));

        assertEquals("400 BAD_REQUEST \"User with nickname user 1 already exist\"", validationException.getMessage());
    }

}