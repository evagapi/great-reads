package com.ironhack.greatreads.repository.user;

import com.ironhack.greatreads.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(int id);
    /**
     * Method to find a User entity by its username field
     *
     * @param username The username of the User entity to search for
     * @return The found User entity or null if not found
     */

    Optional<User> findByUsername(String username);

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}