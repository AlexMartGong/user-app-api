package com.ax.user.app.api.repositories;

import com.ax.user.app.api.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {
}
