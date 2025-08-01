package com.ax.user.app.api.repositories;

import com.ax.user.app.api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>, PagingAndSortingRepository<User, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return an Optional containing the found User, or empty if no user was found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds all users with pagination support.
     *
     * @param pageable the pagination information
     * @return a page of users
     */
    Page<User> findAll(Pageable pageable);
}
