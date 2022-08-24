package com.example.scenariothree.dao.impl;

import com.example.scenariothree.config.DBDefaultConfig;
import com.example.scenariothree.model.User;
import com.github.javafaker.Faker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.*;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {
    @InjectMocks
    private UserDaoImpl userDao;
    @Mock
    private DBDefaultConfig config;
    @Mock
    private Connection conn;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private User user;

    private static final String ADDED_QUERY = "INSERT INTO Account(email, password, isAdmin) VALUES (?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE Account as acc SET acc.email = ?, acc.password = ?, acc.isAdmin = ? WHERE acc.id = ?";
    private static final String SELECT_USER_BY_ID_QUERY = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc WHERE acc.id = ?";
    private static final String SELECT_USER_QUERY = "SELECT acc.id, acc.email, acc.password, acc.isAdmin FROM Account as acc";

    @Before
    public void setUp() throws SQLException {
        Faker faker = new Faker();
        user = new User();
        user.setId(faker.random().nextInt(100));
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setIsAdmin(faker.random().nextBoolean());
        Assert.assertNotNull(config);
        when(config.getConnection()).thenReturn(conn);
    }

    @Test
    public void nullCreateThrowsException() throws SQLException {
        when(conn.prepareStatement(ADDED_QUERY)).thenReturn(preparedStatement);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDao.addUser(null);
        });
        String expectedException = "User object is not null";
        assertEquals(expectedException, exception.getMessage());
    }


    @Test
    public void sqlCreateThrowsException() throws SQLException {
        when(conn.prepareStatement(ADDED_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
            userDao.addUser(user)
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
        verify(conn).rollback();
    }

    @Test
    public void addUser() throws SQLException {
        when(conn.prepareStatement(ADDED_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        int expectedResponse = 1;
        Assert.assertEquals(expectedResponse, userDao.addUser(user));
        verify(conn, times(1)).setAutoCommit(false);
        verify(conn, times(1)).prepareStatement(ADDED_QUERY);
        verify(preparedStatement, times(1)).setString(eq(1), anyString());
        verify(preparedStatement, times(1)).setString(eq(2), anyString());
        verify(preparedStatement, times(1)).setBoolean(eq(3), anyBoolean());
        verify(conn).commit();
        verify(preparedStatement).close();
    }

    @Test
    public void nullEditThrowsException() throws SQLException {
        when(conn.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatement);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDao.editUser(null);
        });
        String expectedException = "User object is not null";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void sqlEditThrowsException() throws SQLException {
        when(conn.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.editUser(user)
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
        verify(conn).rollback();
    }

    @Test
    public void editUser() throws SQLException {
        when(conn.prepareStatement(UPDATE_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        int expectedResponse = 1;
        Assert.assertEquals(expectedResponse, userDao.editUser(user));
        verify(conn, times(1)).prepareStatement(UPDATE_QUERY);
        verify(preparedStatement, times(1)).setString(eq(1), anyString());
        verify(preparedStatement, times(1)).setString(eq(2), anyString());
        verify(preparedStatement, times(1)).setBoolean(eq(3), anyBoolean());
        verify(preparedStatement, times(1)).setInt(eq(4), anyInt());
        verify(conn).commit();
        verify(preparedStatement).close();
    }

    @Test
    public void nullFindUserByIdThrowsException() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_BY_ID_QUERY)).thenReturn(preparedStatement);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userDao.findUserById(null);
        });
        String expectedException = "User id is not null";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void sqlFindUserByIdThrowsException() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_BY_ID_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.findUserById(user.getId())
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void findUserById() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_BY_ID_QUERY)).thenReturn(preparedStatement);
        preparedStatement.setInt(eq(1), anyInt());
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);;
        when(resultSet.getInt("id")).thenReturn(user.getId());
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getBoolean("isAdmin")).thenReturn(user.getIsAdmin());

        User returnedUser = userDao.findUserById(user.getId());
        assertNotNull(returnedUser);
        assertEquals(user.getId(), returnedUser.getId());
        assertEquals(user.getEmail(), returnedUser.getEmail());
        assertEquals(user.getPassword(), returnedUser.getPassword());
        assertEquals(user.getIsAdmin(), returnedUser.getIsAdmin());
        verify(conn, times(1)).prepareStatement(SELECT_USER_BY_ID_QUERY);
        verify(preparedStatement, times(1)).setInt(eq(1), anyInt());
        verify(preparedStatement).close();
    }

    @Test
    public void sqlFindUsersThrowsException() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);
        Exception exception = assertThrows(SQLException.class, () ->
                userDao.findUsers()
        );
        String expectedException = "Internal exception has been occurred";
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    public void findUsers() throws SQLException {
        when(conn.prepareStatement(SELECT_USER_QUERY)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);;
        when(resultSet.getInt("id")).thenReturn(user.getId());
        when(resultSet.getString("email")).thenReturn(user.getEmail());
        when(resultSet.getString("password")).thenReturn(user.getPassword());
        when(resultSet.getBoolean("isAdmin")).thenReturn(user.getIsAdmin());

        List<User> returnedUser = userDao.findUsers();
        assertNotNull(returnedUser);
        assertEquals(1, returnedUser.size());
        verify(conn, times(1)).prepareStatement(SELECT_USER_QUERY);
        verify(preparedStatement).close();
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void isUserAdmin() {
    }
}
