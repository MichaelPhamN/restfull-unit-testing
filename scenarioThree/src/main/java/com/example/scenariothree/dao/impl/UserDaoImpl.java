package com.example.scenariothree.dao.impl;

import com.example.scenariothree.config.DBDefaultConfig;
import com.example.scenariothree.dao.UserDao;
import com.example.scenariothree.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private DBDefaultConfig config;
    @Override
    public int addUser(User user) throws SQLException {
        if(user == null) {
            throw new IllegalArgumentException("User object is not null");
        }
        Connection conn = null;
        int executedRow = 0;
        try {
            conn = config.getConnection();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO Account(email, password, isAdmin) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.getIsAdmin());
            executedRow = preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Internal exception has been occurred");
        }
        return executedRow;
    }

    @Override
    public int editUser(User user) throws SQLException {
        if(user == null) {
            throw new IllegalArgumentException("User object is not null");
        }
        Connection conn = null;
        int executedRow = 0;
        try {
            conn = config.getConnection();
            conn.setAutoCommit(false);
            String sql = "UPDATE Account as acc SET acc.email = ?, acc.password = ?, acc.isAdmin = ? WHERE acc.id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setBoolean(3, user.getIsAdmin());
            preparedStatement.setInt(4, user.getId());
            executedRow = preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Internal exception has been occurred");
        }
        return executedRow;
    }

    @Override
    public User findUserById(Integer id) throws SQLException {
        if(id == null) {
            throw new IllegalArgumentException("User id is not null");
        }
        User user = null;
        try {
            Connection conn = config.getConnection();
            String sql = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc WHERE acc.id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIsAdmin(rs.getBoolean("isAdmin"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new SQLException("Internal exception has been occurred");
        }
        return user;
    }

    @Override
    public List<User> findUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try {
            Connection conn = config.getConnection();
            String sql = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIsAdmin(rs.getBoolean("isAdmin"));
                users.add(user);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new SQLException("Internal exception has been occurred");
        }
        return users;
    }

    @Override
    public int deleteUser(Integer id) throws SQLException {
        if(id == null) {
            throw new IllegalArgumentException("User id is not null");
        }

        Connection conn = null;
        int executedRow = 0;
        try {
            conn = config.getConnection();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM Account as acc WHERE acc.id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            executedRow = preparedStatement.executeUpdate();
            conn.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            conn.rollback();
            throw new SQLException("Internal exception has been occurred");
        }
        return executedRow;
    }

    @Override
    public boolean isUserAdmin(Integer id) throws SQLException {
        if(id == null) {
            throw new IllegalArgumentException("User id is not null");
        }

        User user = null;
        try {
            Connection conn = config.getConnection();
            String sql = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc WHERE acc.id = ? AND acc.isAdmin = 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setIsAdmin(rs.getBoolean("isAdmin"));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new SQLException("Internal exception has been occurred");
        }
        return user != null;
    }
}
