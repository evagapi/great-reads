package com.ironhack.greatreads.service.user;

import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.model.user.Role;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.repository.library.LibraryRepository;
import com.ironhack.greatreads.repository.user.RoleRepository;
import com.ironhack.greatreads.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> $user = userRepository.findByUsername(username);

        if ($user.isPresent()) {
            User user = $user.get();

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } else {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
    }

    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }


    public void addRoleToUser(String username, String roleName) throws Exception {
        log.info("Adding role {} to user {}", roleName, username);

        Optional<User> $user = userRepository.findByUsername(username);

        if ($user.isPresent()) {
            User user = $user.get();
            Role role = roleRepository.findByName(roleName);
            if (role == null) throw new Exception("Role doesn't exist in the database");
            user.getRoles().add(role);
            userRepository.save(user);
        }
    }

    public User getUser(String username) throws Exception {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found"));
    }

    public User getUserById(int id) {
        log.info("Fetching user {}", id);
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public String getUsernameByUserId(int id) {
        return getUserById(id).getUsername();
    }

    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getAuthenticatedUser() throws Exception {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) throw new AuthenticationCredentialsNotFoundException("Non authenticated user");
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        if (username != null) {
            return getUser(username);
        }
        return null;
    }

    public User updateAuthenticatedUser(User user) throws Exception {
        User authenticatedUser = getAuthenticatedUser();

        if (user.getName() != null) authenticatedUser.setName(user.getName());
        if (user.getEmail() != null) authenticatedUser.setEmail(user.getEmail());
        if (user.getPassword() != null) authenticatedUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(authenticatedUser);

        return authenticatedUser;
    }

    public User createUser(User user, String role) throws Exception {
       if (userRepository.findByUsername(user.getUsername()).isPresent()) {
           throw new Exception("Username already exists");
       } else {
           User savedUser = saveUser(user);
           addRoleToUser(user.getUsername(), role);
           createLibraryForUser(user);
           return savedUser;
       }
    }

    private void createLibraryForUser(User user) {
        Library library = new Library();
        libraryRepository.save(library);
        user.setLibrary(library);
        userRepository.save(user);
    }

    public void deleteUser(int id) throws Exception {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));

        userRepository.delete(existingUser);
    }
}