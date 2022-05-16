package com.rgbplace.common.constant;

import java.util.Arrays;

public enum JwtExpiration {

    // JWT 만료 시간 / 1시간
    ACCESS_TOKEN_EXPIRATION_TIME("access_token_expiration_time", 1000L * 60 * 60),
    // Refresh 토큰 만료 시간 / 7일
    REFRESH_TOKEN_EXPIRATION_TIME("refresh_token_expiration_time", 1000L * 60 * 60 * 24 * 7);

    final String name;
    final Long value;

    JwtExpiration(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public JwtExpiration getJwtExpiration(String name) {
        return Arrays.stream(JwtExpiration.values())
                .filter(e -> e.name.equals(name))
                .findAny()
                .orElse(null);
    }
}
