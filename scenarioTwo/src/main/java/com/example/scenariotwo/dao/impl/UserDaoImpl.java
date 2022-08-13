package com.example.scenariotwo.dao.impl;

import com.example.scenariotwo.dao.UserDao;
import com.example.scenariotwo.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "user1", "123456","user1@example.com"));
        users.add(new User(2, "user2", "123456","user2@example.com"));
        users.add(new User(3, "user3", "123456","user3@example.com"));
        users.add(new User(4, "user4", "123456","user4@example.com"));
        return users;
    }
}
