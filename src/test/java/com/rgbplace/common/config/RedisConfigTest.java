package com.rgbplace.common.config;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void redisTest() throws JSONException {
        //Given
        String key = "foo";
        String before = "bar";

        // redis에 set
        redisTemplate.opsForValue().set(key, before);

        // redis에서 get
        String after = (String) redisTemplate.opsForValue().get(key);

        // redis에서 data delete
        redisTemplate.delete(key);

        // redis에 해당 key를 가지고 있는지 확인
        if (Boolean.FALSE.equals(redisTemplate.hasKey("999"))) {
            System.out.printf("%s : %s 미존재\n", key, after);
        }

        assertThat(after).isEqualTo(key);
    }
}