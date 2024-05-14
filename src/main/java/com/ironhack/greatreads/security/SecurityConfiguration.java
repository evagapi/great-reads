package com.ironhack.greatreads.security;

import com.ironhack.greatreads.security.filters.CustomAuthenticationFilter;
import com.ironhack.greatreads.security.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    // UserDetailsService is an interface provided by Spring Security that defines a way to retrieve user information
    @Autowired
    private UserDetailsService userDetailsService;

    // Autowired instance of the AuthenticationManagerBuilder
    @Autowired
    private AuthenticationManagerBuilder authManagerBuilder;
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CustomAuthenticationFilter instance created
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild());
        // set the URL that the filter should process
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        // disable CSRF protection
        http.csrf().disable();
        // set the session creation policy to stateless
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        // set up authorization for different request matchers and user roles
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/v3/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/login/**").permitAll()
                .requestMatchers(GET, "/books").permitAll()
                .requestMatchers(GET, "/books/{id}").permitAll()
                .requestMatchers(GET, "/authors").permitAll()
                .requestMatchers(GET, "/authors/{id}").permitAll()
                .requestMatchers(GET, "/genres").permitAll()
                .requestMatchers(GET, "/genres/{id}").permitAll()
                .requestMatchers(GET, "/translators").permitAll()
                .requestMatchers(GET, "/translators/{id}").permitAll()

                .requestMatchers(POST, "/users").permitAll()

                // Entities management
                .requestMatchers(POST, "/books").hasAnyAuthority("ROLE_LIBRARIAN", "ROLE_ADMIN" )
                .requestMatchers(PATCH, "/books/{id}").hasAnyAuthority("ROLE_LIBRARIAN", "ROLE_ADMIN" )
                .requestMatchers(POST, "/authors").hasAnyAuthority("ROLE_LIBRARIAN", "ROLE_ADMIN" )
                .requestMatchers(PATCH, "/authors/{id}").hasAnyAuthority("ROLE_LIBRARIAN", "ROLE_ADMIN" )
                .requestMatchers(POST, "/translators").hasAnyAuthority("ROLE_LIBRARIAN", "ROLE_ADMIN" )
                .requestMatchers(PATCH, "/translators/{id}").hasAnyAuthority("ROLE_LIBRARIAN", "ROLE_ADMIN" )
                .requestMatchers(PATCH, "/genres/{id}").hasAnyAuthority("ROLE_LIBRARIAN", "ROLE_ADMIN" )
                // Admin domain management
                .requestMatchers(POST, "/genres").hasAnyAuthority( "ROLE_ADMIN" )
                .requestMatchers(DELETE, "/genres/{id}").hasAnyAuthority("ROLE_ADMIN" )
                .requestMatchers(DELETE, "/authors/{id}").hasAnyAuthority("ROLE_ADMIN" )
                .requestMatchers(DELETE, "/translators/{id}").hasAnyAuthority("ROLE_ADMIN" )
                // Admin user management
                .requestMatchers(GET, "/users").hasAnyAuthority( "ROLE_ADMIN")
                .requestMatchers(GET, "/users/{id}").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers(DELETE, "/users/{id}").hasAnyAuthority( "ROLE_ADMIN" )
                .requestMatchers(POST, "/users/{id}/promote").hasAnyAuthority( "ROLE_ADMIN" )
                .anyRequest().authenticated());
        // add the custom authentication filter to the http security object
        http.addFilter(customAuthenticationFilter);
        // Add the custom authorization filter before the standard authentication filter.
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Build the security filter chain to be returned.
        return http.build();
    }
}