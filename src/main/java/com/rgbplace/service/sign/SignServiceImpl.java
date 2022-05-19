package com.rgbplace.service.sign;

import com.auth0.jwt.interfaces.Claim;
import com.rgbplace.common.constant.JwtExpiration;
import com.rgbplace.common.util.DateUtils;
import com.rgbplace.domain.user.User;
import com.rgbplace.service.token.TokenService;
import com.rgbplace.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignServiceImpl implements SignService {
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public Map<String, Object> signUp(User user, HttpServletRequest request) throws IOException {
        try {
            User createdUser = userService.saveUser(user);
            Map<String, Object> data = new HashMap<>();
            if (createdUser != null) {
                data.put("status", "success");

                Date accessTime = new Date(System.currentTimeMillis() + JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getValue());
                String accessToken = tokenService.generateToken(user.getUid(), request.getRequestURI(), accessTime);

                userService.updateToken(createdUser, accessToken);

                Map<String,String> token = new HashMap<>();
                token.put("access", accessToken);
                token.put("expired", DateUtils.getDateString(accessTime));

                data.put("status", "success");
                data.put("token", token);
            } else {
                data.put("status", "fail");
            }

            return data;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }
    @Override
    public Map<String, String> signOut(String authorizationHeader) {
        Map<String, String> data = new HashMap<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                Map<String, Claim> claimMap = tokenService.checkToken(authorizationHeader);

                User user = userService.getUser(claimMap.get("UserId").asString());

                if (user.getAccessToken() == null) {
                    throw new RuntimeException("It's not a valid token.");
                } else {
                    userService.updateToken(user, null);
                    data.put("status", "success");
                    data.put("msg", "sign out success!");
                }
            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                data.put("status", "fail");
                data.put("msg", "error occurred!");
            }

            return data;
        } else {
            throw new RuntimeException("It's not a valid token.");
        }
    }
}
