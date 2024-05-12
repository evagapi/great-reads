package com.ironhack.greatreads.controller.user;

import com.ironhack.greatreads.controller.user.dto.RoleDTO;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
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

    @PostMapping("/users/{id}/promote")
    public ResponseEntity<?> promoteUserToLibrarian(@PathVariable int id, @RequestBody RoleDTO role) {
        try {
            userService.addRoleToUser(userService.getUsernameByUserId(id), role.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.getUserById(id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAuthenticatedUser());
        } catch (AuthenticationCredentialsNotFoundException auth) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> updateCurrentUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.updateAuthenticatedUser(user));
        } catch (AuthenticationException auth) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User newUSer = userService.createUser(user, "ROLE_USER");
            return ResponseEntity.status(HttpStatus.CREATED).body(newUSer);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
        return null;
    }

}
