package com.ax.user.app.api.mapper;

import com.ax.user.app.api.dto.user.UserCreateDTO;
import com.ax.user.app.api.dto.user.UserDTO;
import com.ax.user.app.api.dto.user.UserUpdateDTO;
import com.ax.user.app.api.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User toEntity(UserCreateDTO createDTO) {
        if (createDTO == null) {
            return null;
        }
        return User.builder()
                .username(createDTO.getUsername())
                .email(createDTO.getEmail())
                .password(createDTO.getPassword())
                .build();
    }

    public void updateEntity(UserUpdateDTO updateDTO, User user) {
        if (user == null || updateDTO == null) {
            return;
        }

        if (updateDTO.getUsername() != null) {
            user.setUsername(updateDTO.getUsername());
        }

        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }

    }

    public void updatePasswordEntity(String newPassword, User user) {
        if (user == null || newPassword == null || newPassword.isBlank()) {
            return;
        }
        user.setPassword(newPassword);
    }

}
