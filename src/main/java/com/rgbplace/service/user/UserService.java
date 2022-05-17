package com.rgbplace.service.user;

import com.rgbplace.domain.user.User;

public interface UserService {
    public User saveUser(User user);
    public User updateToken(User user, String accessToken);
    public User getUser(String userId);
    public User checkUser(String userId, String userPassword);
}
