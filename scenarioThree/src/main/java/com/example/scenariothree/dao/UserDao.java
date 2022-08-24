package com.example.scenariothree.dao;


import com.example.scenariothree.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    public int addUser(User User) throws SQLException;
    public int editUser(User User) throws SQLException;
    public User findUserById(Integer id) throws SQLException;
    public List<User> findUsers() throws SQLException;
    public int deleteUser(Integer id) throws SQLException;

    public boolean isUserAdmin(Integer id) throws SQLException;
}
