package com.ax.user.app.api.mapper;

import com.ax.user.app.api.dto.role.RoleCreateDTO;
import com.ax.user.app.api.dto.role.RoleDTO;
import com.ax.user.app.api.dto.role.RoleUpdateDTO;
import com.ax.user.app.api.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    /**
     * Convierte una entidad Role a RoleDTO
     */
    public RoleDTO toRoleDTO(Role role) {
        if (role == null) {
            return null;
        }

        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    /**
     * Convierte un RoleCreateDTO a entidad Role
     */
    public Role toRole(RoleCreateDTO roleCreateDTO) {
        if (roleCreateDTO == null) {
            return null;
        }

        return Role.builder()
                .name(roleCreateDTO.getName())
                .build();
    }

    /**
     * Actualiza una entidad Role existente con datos de RoleUpdateDTO
     */
    public Role updateRoleFromDTO(Role existingRole, RoleUpdateDTO roleUpdateDTO) {
        if (existingRole == null || roleUpdateDTO == null) {
            return existingRole;
        }

        existingRole.setName(roleUpdateDTO.getName());
        return existingRole;
    }

    /**
     * Convierte un RoleUpdateDTO a entidad Role (para casos específicos)
     */
    public Role toRole(RoleUpdateDTO roleUpdateDTO) {
        if (roleUpdateDTO == null) {
            return null;
        }

        return Role.builder()
                .name(roleUpdateDTO.getName())
                .build();
    }
}
