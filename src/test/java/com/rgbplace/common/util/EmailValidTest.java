package com.rgbplace.common.util;

import com.rgbplace.Application;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes= Application.class)
public class EmailValidTest {
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    @DisplayName("이메일 체크가 정상작동하는지 확인한다.")
    public void email_valid_check() {
        // 성공 케이스
        List<String> validMailList = new ArrayList<>();
        validMailList.add("user@naver.com");
        validMailList.add("user@naver.co.in");
        validMailList.add("user1@naver.com");
        validMailList.add("user.name@naver.com");
        validMailList.add("user_name@naver.co.in");
        validMailList.add("user-name@naver.co.in");
        validMailList.add("user@navercom");

        for(String email : validMailList){
            Assertions.assertTrue(ValidateUtils.emailCheck(email));
        }

        // 실패 케이스
        String invalidMail = "@naver.com";
        Assertions.assertFalse(ValidateUtils.emailCheck(invalidMail));
    }
}
