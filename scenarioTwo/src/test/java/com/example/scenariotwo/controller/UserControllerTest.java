package com.example.scenariotwo.controller;

import com.example.scenariotwo.model.User;
import com.example.scenariotwo.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void getUsers() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", "123456","user1@example.com"));
        users.add(new User(2, "user2", "123456","user2@example.com"));
        users.add(new User(3, "user3", "123456","user3@example.com"));
        users.add(new User(4, "user4", "123456","user4@example.com"));
        when(userService.getUsers()).thenReturn(users);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                                .get("/users")
                                .accept(MediaType.APPLICATION_JSON);
        String exceptedResult = "[{\"id\":1,\"username\":\"user1\",\"password\":\"123456\",\"email\":\"user1@example.com\"},{\"id\":2,\"username\":\"user2\",\"password\":\"123456\",\"email\":\"user2@example.com\"},{\"id\":3,\"username\":\"user3\",\"password\":\"123456\",\"email\":\"user3@example.com\"},{\"id\":4,\"username\":\"user4\",\"password\":\"123456\",\"email\":\"user4@example.com\"}]";
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                                .andExpect(status().isOk())
                                .andExpect(content().json(exceptedResult))
                                .andReturn();
    }
}