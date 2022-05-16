package com.rgbplace.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpiration {

    // JWT 만료 시간 / 1시간
    ACCESS_TOKEN_EXPIRATION_TIME("access_token_expiration_time", 1000L * 60 * 60),
    // Refresh 토큰 만료 시간 / 7일
    REFRESH_TOKEN_EXPIRATION_TIME("refresh_token_expiration_time", 1000L * 60 * 60 * 24 * 7);

    private final String name;
    private final Long value;
}
