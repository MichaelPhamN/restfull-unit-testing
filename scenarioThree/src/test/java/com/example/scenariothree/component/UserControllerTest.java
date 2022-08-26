package com.example.scenariothree.component;

import com.example.scenariothree.ScenarioThreeApplication;
import com.example.scenariothree.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@SpringBootTest(classes={ ScenarioThreeApplication.class })
@WebAppConfiguration
public class UserControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Mock
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUsers_HappyPath() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String expected = "[{\"id\":1,\"email\":\"admin@example.com\",\"password\":\"admin\",\"isAdmin\":true}," +
                "{\"id\":2,\"email\":\"user@example.com\",\"password\":\"user\",\"isAdmin\":false}," +
                "{\"id\":3,\"email\":\"test@example.com\",\"password\":\"test\",\"isAdmin\":false}]";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findUsers_NoContent_HappyPath() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
        String expected = "";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUserById_HappyPath() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/{id}", 1)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String expected = "{\"id\":1,\"email\":\"admin@example.com\",\"password\":\"admin\",\"isAdmin\":true}";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    //TODO: RUN TEST METHOD INDIVIDUALLY IS OKAY, BUT RUN ALL METHODS WILL BE STUCKED AT findUserById_HappyPath AND isUserAdmin_HappyPath

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void isUserAdmin_HappyPath() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/isAdmin/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String expected = "true";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteUser_HappyPath() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/user/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn();

        String expected = "Delete account successful";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUser_HappyPath() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\":\"sample@example.com\",\"password\":\"sample\",\"isAdmin\":false}")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();

        String expected = "Add account successful";
        assertEquals(expected, result.getResponse().getContentAsString());
    }

    @Test
    @Sql(scripts= "/scripts/insert.sql", executionPhase= Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts= "/scripts/clean.sql", executionPhase= Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editUser_HappyPath() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"id\":1,\"email\":\"test1@example.com\",\"password\":\"test1@example.com\",\"isAdmin\":true}")
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String expected = "Edit account successful";
        assertEquals(expected, result.getResponse().getContentAsString());
    }
}
//https://github.com/briansjavablog/rest-controller-testing-mock-mvc