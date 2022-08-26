package com.example.scenariothree.service.impl;

import com.example.scenariothree.dao.impl.UserDaoImpl;
import com.example.scenariothree.model.User;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDaoImpl userDao;
    private List<User> users;
    @Before
    public void setUp() {
        Faker faker = new Faker();
        users = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(faker.random().nextInt(100));
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setIsAdmin(faker.random().nextBoolean());
            users.add(user);
        }
    }

    @Test
    public void addUser() throws SQLException {
        when(userDao.addUser(users.get(0))).thenReturn(1);
        int expectedResponse = 1;
        assertEquals(expectedResponse, userService.addUser(users.get(0)));
    }

    @Test
    public void editUser() throws SQLException {
        when(userDao.editUser(users.get(0))).thenReturn(1);
        int expectedResponse = 1;
        assertEquals(expectedResponse, userService.editUser(users.get(0)));
    }

    @Test
    public void findUserById() throws SQLException {
        when(userDao.findUserById(users.get(0).getId())).thenReturn(users.get(0));
        assertEquals(users.get(0).getId(), userService.findUserById(users.get(0).getId()).getId());
        assertEquals(users.get(0).getEmail(), userService.findUserById(users.get(0).getId()).getEmail());
        assertEquals(users.get(0).getPassword(), userService.findUserById(users.get(0).getId()).getPassword());
        assertEquals(users.get(0).getIsAdmin(), userService.findUserById(users.get(0).getId()).getIsAdmin());
    }

    @Test
    public void findUsers() throws SQLException {
        when(userDao.findUsers()).thenReturn(users);
        int expectedResponse = 10;
        assertEquals(expectedResponse, userService.findUsers().size());
    }

    @Test
    public void deleteUser() throws SQLException {
        when(userDao.deleteUser(users.get(0).getId())).thenReturn(1);
        int expectedResponse = 1;
        assertEquals(expectedResponse, userService.deleteUser(users.get(0).getId()));
    }

    @Test
    public void isUserAdmin() throws SQLException {
        when(userDao.isUserAdmin(users.get(0).getId())).thenReturn(true);
        boolean expectedResponse = true;
        assertEquals(expectedResponse, userService.isUserAdmin(users.get(0).getId()));
    }
}