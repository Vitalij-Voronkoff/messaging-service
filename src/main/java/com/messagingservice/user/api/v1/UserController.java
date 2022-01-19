package com.messagingservice.user.api.v1;

import com.messagingservice.user.model.CreateUserRequest;
import com.messagingservice.user.model.CreateUserResponse;
import com.messagingservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/messages/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserResponse createUser(@RequestBody @Validated CreateUserRequest createUserRequest) {

        log.info("Creating new user with nickname: {}", createUserRequest.getNickname());

        return userService.createUser(createUserRequest);
    }
}
