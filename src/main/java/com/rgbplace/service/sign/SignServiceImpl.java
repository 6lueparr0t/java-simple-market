package com.rgbplace.service.sign;

import com.auth0.jwt.interfaces.Claim;
import com.rgbplace.common.constant.ExceptionMessage;
import com.rgbplace.common.constant.JwtExpiration;
import com.rgbplace.common.constant.StatusMessage;
import com.rgbplace.common.util.DateUtils;
import com.rgbplace.common.util.ValidateUtils;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignServiceImpl implements SignService {
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public Map<String, Object> signUp(User user, HttpServletRequest request) throws IOException {
        try {
            if(!ValidateUtils.emailCheck(user.getEmail()) || !ValidateUtils.passwordCheck(user.getPw())) {
                log.error(ExceptionMessage.INVALID_EMAIL.getValue());
                throw new IOException(ExceptionMessage.INVALID_EMAIL.getValue());
            }

            if(!ValidateUtils.passwordCheck(user.getPw())) {
                log.error(ExceptionMessage.INVALID_PASSWORD.getValue());
                throw new IOException(ExceptionMessage.INVALID_PASSWORD.getValue());
            }

            User createdUser = userService.saveUser(user);
            Map<String, Object> data = new HashMap<>();
            if (Optional.ofNullable(createdUser).isPresent()) {
                data.put("status", StatusMessage.SUCCESS.getValue());

                Date accessTime = new Date(System.currentTimeMillis() + JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getValue());
                String accessToken = tokenService.generateToken(user.getUid(), request.getRequestURI(), accessTime);

                userService.updateToken(createdUser, accessToken);

                Map<String,String> token = new HashMap<>();
                token.put("access", accessToken);
                token.put("expired", DateUtils.getDateString(accessTime));

                data.put("status", StatusMessage.SUCCESS.getValue());
                data.put("token", token);
            } else {
                data.put("status", StatusMessage.FAIL.getValue());
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
        if (Optional.ofNullable(authorizationHeader).isPresent() && authorizationHeader.startsWith("Bearer ")) {
            try {
                Map<String, Claim> claimMap = tokenService.checkToken(authorizationHeader);

                User user = userService.getUser(claimMap.get("UserId").asString());

                if (Optional.ofNullable(user).map(User::getAccessToken).isEmpty()) {
                    throw new RuntimeException(ExceptionMessage.INVALID_TOKEN.getValue());
                } else {
                    userService.updateToken(user, null);
                    data.put("status", StatusMessage.SUCCESS.getValue());
                    data.put("msg", ExceptionMessage.SUCCESS.getValue());
                }
            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                data.put("status", StatusMessage.FAIL.getValue());
                data.put("msg", ExceptionMessage.ERROR.getValue());
            }

            return data;
        } else {
            throw new RuntimeException(ExceptionMessage.INVALID_TOKEN.getValue());
        }
    }
}
