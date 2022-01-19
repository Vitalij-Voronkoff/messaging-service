package com.messagingservice.user.mapper;

import com.messagingservice.user.model.CreateUserRequest;
import com.messagingservice.user.model.CreateUserResponse;
import com.messagingservice.user.persistence.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromCreateRequest(CreateUserRequest createRequest);

    CreateUserResponse toCreateResponse(User user);
}
