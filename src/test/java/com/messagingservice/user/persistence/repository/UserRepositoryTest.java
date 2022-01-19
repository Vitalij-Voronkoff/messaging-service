package com.messagingservice.user.persistence.repository;

import com.messagingservice.user.persistence.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnIsUserExistsByNickname() {
        var user = new User();
        user.setNickname("user 1");

        userRepository.save(user);

        assertTrue(userRepository.existsByNickname(user.getNickname()));
    }

}