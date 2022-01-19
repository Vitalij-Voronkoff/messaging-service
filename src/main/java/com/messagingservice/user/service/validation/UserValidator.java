package com.messagingservice.user.service.validation;

import com.messagingservice.exception.ValidationException;
import com.messagingservice.user.model.CreateUserRequest;
import com.messagingservice.user.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateCreate(CreateUserRequest createUserRequest) {

        if (userRepository.existsByNickname(createUserRequest.getNickname())) {
            throw new ValidationException(String.format("User with nickname %s already exist", createUserRequest.getNickname()));
        }
    }
}
