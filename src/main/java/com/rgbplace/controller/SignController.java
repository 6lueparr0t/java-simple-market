package com.rgbplace.controller;

import com.rgbplace.domain.user.User;
import com.rgbplace.service.sign.SignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
@Slf4j
public class SignController {

    private static final String AUTHORIZATION = "Authorization";
    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final SignService signService;

    @PostMapping("/up")
    public ResponseEntity<Map<String, Object>> signUpUser(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          @RequestBody User user) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).signUpUser(request, response, user)).toUri();

        Map<String, Object> data = signService.signUp(user, request);

        response.setContentType(APPLICATION_JSON_VALUE);
        return ResponseEntity.created(uri).body(data);
    }

    @GetMapping("/out")
    public ResponseEntity<Map<String, String>> signOutUser(HttpServletRequest request,
                                                           HttpServletResponse response) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).signOutUser(request, response)).toUri();

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        Map<String, String> data = signService.signOut(authorizationHeader);

        response.setContentType(APPLICATION_JSON_VALUE);
        return ResponseEntity.created(uri).body(data);
    }
}
