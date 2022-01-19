package com.messagingservice.user.service;

import com.messagingservice.user.mapper.UserMapper;
import com.messagingservice.user.model.CreateUserRequest;
import com.messagingservice.user.model.CreateUserResponse;
import com.messagingservice.user.persistence.repository.UserRepository;
import com.messagingservice.user.service.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {

        userValidator.validateCreate(createUserRequest);

        var user = userRepository.save(userMapper.fromCreateRequest(createUserRequest));

        return userMapper.toCreateResponse(user);
    }
}
