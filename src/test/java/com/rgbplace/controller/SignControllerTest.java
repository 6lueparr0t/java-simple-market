package com.rgbplace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgbplace.Application;
import com.rgbplace.dto.LoginDto;
import com.rgbplace.domain.user.User;
import com.rgbplace.domain.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= Application.class, webEnvironment = RANDOM_PORT)
public class SignControllerTest {
    @LocalServerPort
    private int port;

    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void sign_up_test() throws Exception {
        //given
        String name = "testname";
        String uid = "testid";
        String pw = "testpw";
        String email = "test@naver.com";

        User user = User.builder()
                .name(name)
                .uid(uid)
                .pw(pw)
                .email(email)
                .build();

        String url = "http://localhost:" + port + "/sign/up";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated());

        //then
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(name);
        assertThat(all.get(0).getUid()).isEqualTo(uid);
    }

    @Test
    public void sign_in_test() throws Exception {
        //given
        String name = "testname2";
        String uid = "testid2";
        String pw = "testpw2";

        User user = User.builder()
                .name(name)
                .uid(uid)
                .pw(pw)
                .build();

        String url1 = "http://localhost:" + port + "/sign/up";

        //when
        mvc.perform(post(url1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated());

        LoginDto loginDto = LoginDto.builder()
                .uid(uid)
                .pw(pw)
                .build();

        String url2 = "http://localhost:" + port + "/sign/in";

        //when
        mvc.perform(post(url2)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", equalTo("success")))
                .andExpect(jsonPath("$.token.access").exists());

        //then
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getAccessToken()).isNotEmpty();
    }

    @Test
    public void sign_out_test() throws Exception {
        //given
        String name = "testname4";
        String uid = "testid4";
        String pw = "testpw4";

        User user = User.builder()
                .name(name)
                .uid(uid)
                .pw(pw)
                .build();

        String url1 = "http://localhost:" + port + "/sign/up";

        //when
        mvc.perform(post(url1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated());

        LoginDto loginDto = LoginDto.builder()
                .uid(uid)
                .pw(pw)
                .build();

        String url2 = "http://localhost:" + port + "/sign/in";

        //when
        mvc.perform(post(url2)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(loginDto)))
                .andExpect(status().isOk());

        //then
        List<User> all = userRepository.findAll();
        String accessToken = all.get(0).getAccessToken();

        String url3 = "http://localhost:" + port + "/sign/check";
        mvc.perform(get(url3)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .content(new ObjectMapper().writeValueAsString("")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", equalTo("success")));

        String url4 = "http://localhost:" + port + "/sign/out";
        mvc.perform(get(url4)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .content(new ObjectMapper().writeValueAsString("")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", equalTo("success")));

        List<User> after = userRepository.findAll();
        assertThat(after.get(0).getAccessToken()).isEmpty();
    }
}
