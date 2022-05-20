package com.rgbplace.service.user;

import com.rgbplace.Application;
import com.rgbplace.common.constant.StatusMessage;
import com.rgbplace.domain.user.User;
import com.rgbplace.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest(classes= Application.class)
class UserCheckTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("Optional check")
    public void optional_check() {
        User user = new User();
        user.setUid("test");
        user.setName("test");
        user.setPw("test");
        user.setEmail("test@test.com");

        User checkUser = null;

        // null 일때, true
        Assertions.assertTrue(Optional.ofNullable(checkUser).map(User::getAccessToken).isEmpty());

        // user not null
        // token null
        checkUser = userRepository.save(user);
        Assertions.assertTrue(Optional.ofNullable(checkUser).map(User::getAccessToken).isEmpty());

        // user not null
        // token not null
        User hasTokenUser = userService.updateToken(checkUser, "test");
        Assertions.assertFalse(Optional.ofNullable(hasTokenUser).map(User::getAccessToken).isEmpty());

        // user not null
        // token null
        User nullTokenUser = userService.updateToken(hasTokenUser, null);
        Assertions.assertTrue(Optional.ofNullable(nullTokenUser).map(User::getAccessToken).isEmpty());
    }
}
