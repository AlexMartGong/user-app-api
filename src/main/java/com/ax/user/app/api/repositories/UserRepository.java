package com.ax.user.app.api.repositories;

import com.ax.user.app.api.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
