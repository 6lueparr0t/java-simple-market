package com.rgbplace.common.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ExceptionMessage {
    INVALID_EMAIL( "Invalid Email."),
    INVALID_PASSWORD( "Invalid Password."),
    INVALID_TOKEN( "Invalid token."),
    SUCCESS( "request success"),
    ERROR( "error occurred");

    final String value;

    ExceptionMessage(String value) {
        this.value = value;
    }
}
