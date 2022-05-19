package com.rgbplace.service.user;

import com.rgbplace.domain.user.User;

public interface UserService {
    User saveUser(User user);
    User updateToken(User user, String accessToken);
    User getUser(String userId);
    User checkUser(String userId, String userPassword);
}
