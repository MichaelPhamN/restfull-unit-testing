package com.example.scenariothree.component.exception;

import com.example.scenariothree.ScenarioThreeApplication;
import com.example.scenariothree.controller.UserController;
import com.example.scenariothree.exception.DataNotFoundException;
import com.example.scenariothree.model.User;
import com.example.scenariothree.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK, classes={ ScenarioThreeApplication.class })
public class UserControllerExceptionTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Mock
    private UserServiceImpl userService;
    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserById_ReturnFail() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/{id}", 4)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/user/4\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void isUserAdmin_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/isAdmin/{id}", 4)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/user/isAdmin/4\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteUser_ReturnFail() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/user/{id}", 4)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        String expected = "{\"statusCode\":404,\"message\":\"Data not found\",\"description\":\"uri=/user/4\"}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    //TODO: NEED TO CHECK EXCEPTION FOR ADD AND EDIT METHOD IN CASE USER IS NULL
}