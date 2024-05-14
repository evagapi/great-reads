package com.ironhack.greatreads.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ironhack.greatreads.model.library.Library;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "You must provide a name")
    private String name;

    @NotEmpty(message = "You must provide a username")
    private String username;

    @NotEmpty(message = "You must provide an email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "You must provide a password")
    private String password;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "library_id")
    @ManyToMany(fetch = LAZY)
    private Library library;

    public User(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
