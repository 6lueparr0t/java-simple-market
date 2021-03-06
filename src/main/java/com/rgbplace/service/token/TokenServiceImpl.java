package com.rgbplace.service.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Value("${spring.jwt.secret}")
    private String jwtSecretKey;

    @Override
    public String generateToken(String userId, String requestURI, Date date) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey.getBytes());

        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(date)
                .withIssuer(requestURI)
                .withClaim("UserId", userId)
                .sign(algorithm);
    }

    @Override
    public String getUserIdInToken(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getClaim("UserId").toString();
    }

    @Override
    public Map<String, Claim> checkToken(String authorizationHeader) {
        String access_token = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256(jwtSecretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(access_token);

        Map<String, Claim> claimMap = new HashMap<>();

        claimMap.put("UserId", decodedJWT.getClaim("UserId"));

        return claimMap;
    }

    @Override
    public String getSecret() {
        return this.jwtSecretKey;
    }
}
