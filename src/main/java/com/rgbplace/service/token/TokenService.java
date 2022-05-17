package com.rgbplace.service.token;

import com.auth0.jwt.interfaces.Claim;

import java.util.Date;
import java.util.Map;

public interface TokenService {
    Map<String, Claim> checkToken(String authorizationHeader);
    String generateToken(String userId, String requestURI, Date date);
    String getUserIdInToken(String authorizationHeader);
    String getSecret();
}
