package com.ironhack.greatreads.controller.user;

import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() { return userService.getAllUsers(); }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable int id) { return userService.getUserById(id); }

    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) { return userService.addNewUser(user); }

    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable int id) { userService.deleteUser(id); }

}
