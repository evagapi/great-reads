package com.ironhack.greatreads.repository.user;

import com.ironhack.greatreads.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(int id);

    User findByUsername(String username) throws UsernameNotFoundException;

    Optional<User> findByName(String name);

    Optional<User> findByEmail(String email);
}