package com.example.scenariotwo.service.impl;

import com.example.scenariotwo.dao.impl.UserDaoImpl;
import com.example.scenariotwo.model.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserDaoImpl userDao;
    @Test
    public void getUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "user1", "123456","user1@example.com"));
        userList.add(new User(2, "user2", "123456","user2@example.com"));
        when(userDao.getUsers()).thenReturn(userList);
        Assert.assertNotNull(userService.getUsers());
        int exceptedResult = 2;
        Assert.assertEquals(exceptedResult, userService.getUsers().size());
    }
}