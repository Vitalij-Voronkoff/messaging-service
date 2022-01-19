package com.messagingservice.message.persistence.repository;

import com.messagingservice.message.persistence.entity.Message;
import com.messagingservice.user.persistence.entity.User;
import com.messagingservice.user.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindAllMessagesByRecipient() {
        var sender = createAndSaveUser("sender");
        var recipient = createAndSaveUser("recipient");

        createAndSaveMessage(sender, recipient);
        createAndSaveMessage(sender, recipient);

        assertEquals(2, messageRepository.findAllByRecipientId(recipient.getId()).size());
    }

    @Test
    public void shouldFindAllMessagesSentBySender() {
        var sender = createAndSaveUser("sender");
        var recipient = createAndSaveUser("recipient");

        createAndSaveMessage(sender, recipient);
        createAndSaveMessage(sender, recipient);

        assertEquals(2, messageRepository.findAllBySenderId(sender.getId()).size());
    }

    @Test
    public void shouldFindAllMessagesByRecipientSentBySender() {
        var sender = createAndSaveUser("sender");
        var recipient = createAndSaveUser("recipient");

        createAndSaveMessage(sender, recipient);
        createAndSaveMessage(sender, recipient);
        createAndSaveMessage(recipient, sender);

        assertEquals(2, messageRepository.findAllByRecipientIdAndSenderId(recipient.getId(), sender.getId()).size());
    }

    private User createAndSaveUser(String nickname) {
        var user = new User();
        user.setNickname(nickname);

        return userRepository.save(user);
    }

    private void createAndSaveMessage(User sender, User recipient) {
        var message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setMessage("Hello World!");

        messageRepository.save(message);
    }
}