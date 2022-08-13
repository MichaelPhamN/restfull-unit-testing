package com.example.scenariotwo.service.impl;

import com.example.scenariotwo.dao.impl.UserDaoImpl;
import com.example.scenariotwo.model.User;
import com.example.scenariotwo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoImpl userDao;
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
