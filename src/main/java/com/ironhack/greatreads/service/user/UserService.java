package com.ironhack.greatreads.service.user;

import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() { return userRepository.findAll(); }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User addNewUser(User user) { return userRepository.save(user); }

    public User updateUser(int id, User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (user.getName() != null) existingUser.setName(user.getName());
        if (user.getUserName() != null) existingUser.setUserName(user.getUserName());
        if (user.getEmail() != null) existingUser.setEmail(user.getEmail());

        userRepository.save(existingUser);

        return existingUser;
    }

    public void deleteUser(int id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.delete(existingUser);
    }
}
