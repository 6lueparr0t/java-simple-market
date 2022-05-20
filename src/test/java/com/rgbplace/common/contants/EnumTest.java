package com.rgbplace.common.contants;

import com.rgbplace.Application;
import com.rgbplace.common.constant.StatusMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes= Application.class)
public class EnumTest {
    @Test
    @DisplayName("enum 타입에서 String 이 나올까?")
    public void enum_message_check() {
        Assertions.assertEquals("success", StatusMessage.SUCCESS.getValue());
    }
}
