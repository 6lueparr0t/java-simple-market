package com.rgbplace.service.user;

import com.rgbplace.domain.user.User;
import com.rgbplace.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user {}", user.getUserName());
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateToken(User user, String accessToken) {
        log.info("Update Token user {}", user.getUserName());
        user.setAccessToken(accessToken);
        return userRepository.save(user);
    }

    @Override
    public User getUser(String userId) {
        log.info("Fetching user {}", userId);
        return userRepository.findByUserId(userId);
    }

    @Override
    public User checkUser(String userId, String userPassword) {
        log.info("Fetching user {}", userId);
        log.info("Fetching pw {}", passwordEncoder.encode(userPassword));

        return userRepository.findByUserIdAndUserPassword(userId, passwordEncoder.encode(userPassword));
    }
}
