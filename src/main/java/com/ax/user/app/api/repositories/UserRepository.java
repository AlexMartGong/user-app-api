package com.ax.user.app.api.repositories;

import com.ax.user.app.api.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the found User, or empty if no user was found
     */
    Optional<User> findByUsername(String username);
}
