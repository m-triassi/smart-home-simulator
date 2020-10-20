package com.soen343.smarthomesimulator;

import com.soen343.smarthomesimulator.controllers.HomeController;
import com.soen343.smarthomesimulator.controllers.PageController;

import static org.assertj.core.api.Assertions.assertThat;

import com.soen343.smarthomesimulator.controllers.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(UserController.class)
public class UserTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void homePageLoadsWithLoginButton() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class)).contains("Login");
    }

    @Test
    public void signupPageLoads() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .contains("Sign Up");
    }

//    @Test
//    public void userCanCreateAnAccount() throws Exception {
//        post("/user/store?name=TestUser&email=test@test.com&password=password");
//    }

    @Test
    public void loginPageLoads() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/login", String.class))
                .contains("Please sign in");
    }
}
