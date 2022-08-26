package com.example.scenariothree.controller;

import com.example.scenariothree.exception.DataNotFoundException;
import com.example.scenariothree.model.User;
import com.example.scenariothree.service.impl.UserServiceImpl;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserController userController;

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
    public void findUsers_HappyPath() throws Exception {
        when(userService.findUsers()).thenReturn(users);
        ResponseEntity<List<User>> users = userController.findUsers();
        HttpStatus expectedStatus = HttpStatus.OK;
        int expectedCollectionSize = 10;
        assertEquals(expectedStatus, users.getStatusCode());
        assertEquals(expectedCollectionSize, users.getBody().size());
    }

    @Test
    public void findUsers_NoContent_HappyPath() throws Exception {
        when(userService.findUsers())
                .thenReturn(Collections.EMPTY_LIST);
        ResponseEntity<List<User>> users = userController.findUsers();
        HttpStatus expectedStatus = HttpStatus.NO_CONTENT;
        assertEquals(expectedStatus, users.getStatusCode());
        assertEquals(null, users.getBody());
    }

    @Test
    public void findUserById_ReturnFailure() throws Exception {
        when(userService.findUserById(1000)).thenReturn(null);
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            userController.findUserById(1000);
        });
        String expected = "Data not found";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void findUserById_HappyPath() throws Exception {
        when(userService.findUserById(users.get(0).getId())).thenReturn(users.get(0));
        ResponseEntity<User> user = userController.findUserById(users.get(0).getId());
        HttpStatus expectedStatus = HttpStatus.OK;
        User expected = users.get(0);
        assertEquals(expectedStatus, user.getStatusCode());
        assertEquals(expected.getId(), user.getBody().getId());
        assertEquals(expected.getEmail(), user.getBody().getEmail());
        assertEquals(expected.getPassword(), user.getBody().getPassword());
        assertEquals(expected.getIsAdmin(), user.getBody().getIsAdmin());
    }

    @Test
    public void isUserAdmin_ReturnFailure() throws Exception {
        when(userService.findUserById(1000)).thenReturn(null);
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            userController.isUserAdmin(1000);
        });
        String expected = "Data not found";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void isUserAdmin_HappyPath() throws Exception {
        when(userService.findUserById(users.get(0).getId())).thenReturn(users.get(0));
        when(userService.isUserAdmin(users.get(0).getId())).thenReturn(true);
        ResponseEntity<Boolean> userIsAdmin = userController.isUserAdmin(users.get(0).getId());
        HttpStatus expectedStatus = HttpStatus.OK;
        boolean expected = true;
        assertEquals(expectedStatus, userIsAdmin.getStatusCode());
        assertEquals(expected, userIsAdmin.getBody());
    }

    @Test
    public void deleteUser_ReturnFailure() throws Exception {
        when(userService.findUserById(1000)).thenReturn(null);
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            userController.deleteUser(1000);
        });
        String expected = "Data not found";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void deleteUser_BadRequest() throws Exception{
        when(userService.findUserById(users.get(0).getId())).thenReturn(users.get(0));
        when(userService.deleteUser(users.get(0).getId())).thenReturn(0);
        ResponseEntity<String> deletedUser = userController.deleteUser(users.get(0).getId());
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expected = "Delete account failure";
        assertEquals(expectedStatus, deletedUser.getStatusCode());
        assertEquals(expected, deletedUser.getBody());
    }

    @Test
    public void deleteUser_HappyPath() throws Exception{
        when(userService.findUserById(users.get(0).getId())).thenReturn(users.get(0));
        when(userService.deleteUser(users.get(0).getId())).thenReturn(1);
        ResponseEntity<String> deletedUser = userController.deleteUser(users.get(0).getId());
        HttpStatus expectedStatus = HttpStatus.NO_CONTENT;
        String expected = "Delete account successful";
        assertEquals(expectedStatus, deletedUser.getStatusCode());
        assertEquals(expected, deletedUser.getBody());
    }

    @Test
    public void addUser_ReturnFailure() throws Exception {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.addUser(null);
        });
        String expected = "User data is not null";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void addUser_BadRequest() throws Exception{
        when(userService.addUser(users.get(0))).thenReturn(0);
        ResponseEntity<String> addedUser = userController.addUser(users.get(0));
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expected = "Add account failure";
        assertEquals(expectedStatus, addedUser.getStatusCode());
        assertEquals(expected, addedUser.getBody());
    }

    @Test
    public void addUser_HappyPath() throws Exception{
        when(userService.addUser(users.get(0))).thenReturn(1);
        ResponseEntity<String> addedUser = userController.addUser(users.get(0));
        HttpStatus expectedStatus = HttpStatus.CREATED;
        String expected = "Add account successful";
        assertEquals(expectedStatus, addedUser.getStatusCode());
        assertEquals(expected, addedUser.getBody());
    }

    @Test
    public void editUser_ReturnFailure() throws Exception {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userController.editUser(null);
        });
        String expected = "User data is not null";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void editUser_whenUserNotFound_ReturnFailure() throws Exception {
        when(userService.findUserById(users.get(0).getId())).thenReturn(null);
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            userController.editUser(users.get(0));
        });
        String expected = "Data not found";
        assertEquals(expected, exception.getMessage());
    }

    @Test
    public void editUser_BadRequest() throws Exception{
        when(userService.findUserById(users.get(0).getId())).thenReturn(users.get(0));
        when(userService.editUser(users.get(0))).thenReturn(0);
        ResponseEntity<String> editUser = userController.editUser(users.get(0));
        HttpStatus expectedStatus = HttpStatus.BAD_REQUEST;
        String expected = "Edit account failure";
        assertEquals(expectedStatus, editUser.getStatusCode());
        assertEquals(expected, editUser.getBody());
    }

    @Test
    public void editUser_HappyPath() throws Exception{
        when(userService.findUserById(users.get(0).getId())).thenReturn(users.get(0));
        when(userService.editUser(users.get(0))).thenReturn(1);
        ResponseEntity<String> editUser = userController.editUser(users.get(0));
        HttpStatus expectedStatus = HttpStatus.OK;
        String expected = "Edit account successful";
        assertEquals(expectedStatus, editUser.getStatusCode());
        assertEquals(expected, editUser.getBody());
    }

}
//https://github.com/briansjavablog/rest-controller-testing-mock-mvc