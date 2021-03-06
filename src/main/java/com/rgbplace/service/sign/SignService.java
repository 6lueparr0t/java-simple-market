package com.rgbplace.service.sign;

import com.rgbplace.domain.user.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface SignService {
    Map<String, Object> signUp(User user, HttpServletRequest request) throws IOException;
    Map<String, String> signOut(String authorizationHeader) throws IOException;
}
