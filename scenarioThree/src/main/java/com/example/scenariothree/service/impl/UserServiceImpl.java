package com.example.scenariothree.service.impl;

import com.example.scenariothree.dao.impl.UserDaoImpl;
import com.example.scenariothree.model.User;
import com.example.scenariothree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDaoImpl UserDao;

    @Override
    public int addUser(User user) throws SQLException {
        return UserDao.addUser(user);
    }

    @Override
    public int editUser(User user) throws SQLException {
        return UserDao.editUser(user);
    }

    @Override
    public User findUserById(Integer id) throws SQLException {
        return UserDao.findUserById(id);
    }

    @Override
    public List<User> findUsers() throws SQLException {
        return UserDao.findUsers();
    }

    @Override
    public int deleteUser(Integer id) throws SQLException {
        return UserDao.deleteUser(id);
    }

    @Override
    public boolean isUserAdmin(Integer id) throws SQLException {return UserDao.isUserAdmin(id);}
}
