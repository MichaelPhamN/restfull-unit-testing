package com.example.scenariothree.controller;

import com.example.scenariothree.exception.DataNotFoundException;
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
    private UserServiceImpl userService;
    @GetMapping("")
    public ResponseEntity<List<User>> findUsers() throws SQLException {
        List<User> users = userService.findUsers();
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable int id) throws SQLException {
        User user = userService.findUserById(id);
        if(user == null) {
            throw new DataNotFoundException("Data not found");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/isAdmin/{id}")
    public ResponseEntity<Boolean> isUserAdmin(@PathVariable int id) throws SQLException {
        User user = userService.findUserById(id);
        if(user == null) {
            throw new DataNotFoundException("Data not found");
        }
        boolean isAdmin = userService.isUserAdmin(id);
        return new ResponseEntity<>(isAdmin, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) throws SQLException {
        User user = userService.findUserById(id);
        if(user == null) {
            throw new DataNotFoundException("Data not found");
        }
        int executed = userService.deleteUser(id);
        return executed != 0 ? new ResponseEntity<>("Delete account successful", HttpStatus.NO_CONTENT)
                : new ResponseEntity<>("Delete account failure", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("")
    public ResponseEntity<String> addUser(@RequestBody User user) throws SQLException {
        if(user == null) {
            throw new IllegalArgumentException("User data is not null");
        }
        int executed = userService.addUser(user);
        return executed != 0 ? new ResponseEntity<>("Add account successful", HttpStatus.CREATED)
                : new ResponseEntity<>("Add account failure", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("")
    public ResponseEntity<String> editUser(@RequestBody User user) throws SQLException {
        if(user == null) {
            throw new IllegalArgumentException("User data is not null");
        }

        User getUser = userService.findUserById(user.getId());
        if(getUser == null) {
            throw new DataNotFoundException("Data not found");
        }
        getUser.setId(user.getId());
        getUser.setEmail(user.getEmail());
        getUser.setPassword(user.getPassword());
        getUser.setIsAdmin(user.getIsAdmin());
        int executed = userService.editUser(user);
        return executed != 0 ? new ResponseEntity<>("Edit account successful", HttpStatus.OK)
                : new ResponseEntity<>("Edit account failure", HttpStatus.BAD_REQUEST);
    }
}
