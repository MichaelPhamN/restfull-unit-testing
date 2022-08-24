package com.example.scenariothree.controller;

import com.example.scenariothree.model.User;
import com.example.scenariothree.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl UserService;
    @GetMapping("")
    public ResponseEntity<List<User>> findUsers() throws SQLException {
        List<User> Users = UserService.findUsers();
        return new ResponseEntity<>(Users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable int id) throws SQLException {
        User User = UserService.findUserById(id);
        return new ResponseEntity<>(User, HttpStatus.OK);
    }

    @GetMapping("/isAdmin/{id}")
    public ResponseEntity<Boolean> isUserAdmin(@PathVariable int id) throws SQLException {
        boolean isAdmin = UserService.isUserAdmin(id);
        return new ResponseEntity<>(isAdmin, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public int deleteUser(@PathVariable int id) throws SQLException {
        return UserService.deleteUser(id);
    }

    @PostMapping("")
    public int addUser(@RequestBody User user) throws SQLException {
        int executedRow = UserService.addUser(user);
        return executedRow;
    }

    @PutMapping("")
    public int editUser(@RequestBody User user) throws SQLException {
        int executedRow = UserService.editUser(user);
        return executedRow;
    }
}
