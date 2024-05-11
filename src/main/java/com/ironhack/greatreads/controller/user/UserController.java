package com.ironhack.greatreads.controller.user;

import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getCurrentUser() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAuthenticatedUser());
        } catch (AuthenticationException auth) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PatchMapping("/me/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> updateCurrentUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateAuthenticatedUser(user));
        } catch (AuthenticationException auth) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {
        try {
            User newUSer = userService.createUser(user, "ROLE_USER");
            return ResponseEntity.status(HttpStatus.CREATED).body(newUSer);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
    }
}
