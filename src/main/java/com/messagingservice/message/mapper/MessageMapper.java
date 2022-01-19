package com.messagingservice.message.mapper;

import com.messagingservice.message.model.GetMessageResponse;
import com.messagingservice.message.model.SendMessageRequest;
import com.messagingservice.message.persistence.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "recipient", ignore = true)
    Message fromSendRequest(SendMessageRequest sendMessageRequest);

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "recipientId", source = "recipient.id")
    GetMessageResponse toGetMessageResponse(Message message);

    List<GetMessageResponse> toGetAllMessageResponse(List<Message> messages);
}
