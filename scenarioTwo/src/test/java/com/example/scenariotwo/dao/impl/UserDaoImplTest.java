package com.example.scenariotwo.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {
    @Spy
    UserDaoImpl userDao;
    @Test
    public void getUsers() {
        int expectedResult = 4;
        Assert.assertEquals(expectedResult, userDao.getUsers().size());
    }
}