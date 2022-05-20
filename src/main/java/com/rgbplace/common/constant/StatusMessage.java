package com.rgbplace.common.constant;

import lombok.Getter;

@Getter
public enum StatusMessage {
    SUCCESS("success"),
    FAIL("fail");

    final String value;

    StatusMessage(String value) {
        this.value = value;
    }
}
