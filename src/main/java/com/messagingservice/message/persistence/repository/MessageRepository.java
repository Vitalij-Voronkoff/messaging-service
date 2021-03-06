package com.messagingservice.message.persistence.repository;

import com.messagingservice.message.persistence.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByRecipientId(long recipientId);

    List<Message> findAllBySenderId(long senderId);

    List<Message> findAllByRecipientIdAndSenderId(long recipientId, long senderId);
}
