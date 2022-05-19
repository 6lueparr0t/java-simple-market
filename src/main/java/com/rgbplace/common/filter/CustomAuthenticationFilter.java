package com.rgbplace.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rgbplace.common.constant.JwtExpiration;
import com.rgbplace.common.util.DateUtils;
import com.rgbplace.dto.LoginDto;
import com.rgbplace.service.token.TokenService;
import com.rgbplace.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        this.authenticationManager = authenticationManager;
        this.tokenService = ctx.getBean(TokenService.class);
        this.userService = ctx.getBean(UserService.class);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        LoginDto loginDto = this.getLoginRequest(request);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUid(), loginDto.getPw());
        return authenticationManager.authenticate(authenticationToken);
    }

    private LoginDto getLoginRequest(HttpServletRequest request) {
        BufferedReader reader = null;
        LoginDto loginDto = null;
        try {
            reader = request.getReader();
            Gson gson = new Gson();
            loginDto = gson.fromJson(reader, LoginDto.class);
        } catch (IOException e) {
            log.debug("{}", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                log.debug("{}", e);
            }
        }

        return loginDto;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        Date accessTime = new Date(System.currentTimeMillis() + JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getValue());
        String accessToken = tokenService.generateToken(user.getUsername(), request.getRequestURI(), accessTime);

        userService.updateToken(userService.getUser(user.getUsername()), accessToken);

        Map<String,String> token = new HashMap<>();
        token.put("access", accessToken);
        token.put("expired", DateUtils.getDateString(accessTime));

        Map<String, Object> data = new HashMap<>();
        data.put("status", "success");
        data.put("token", token);

        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), data);
    }
}
