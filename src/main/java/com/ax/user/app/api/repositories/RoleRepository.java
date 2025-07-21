package com.ax.user.app.api.repositories;

import com.ax.user.app.api.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, String> {
    Optional<Role> findByName(String name);
}
