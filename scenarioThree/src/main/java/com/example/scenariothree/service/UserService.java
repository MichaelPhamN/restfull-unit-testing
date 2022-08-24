package com.example.scenariothree.service;


import com.example.scenariothree.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    int addUser(User User) throws SQLException;
    int editUser(User User) throws SQLException;
    User findUserById(Integer id) throws SQLException;
    List<User> findUsers() throws SQLException;
    int deleteUser(Integer id) throws SQLException;
    boolean isUserAdmin(Integer id) throws SQLException;
}
