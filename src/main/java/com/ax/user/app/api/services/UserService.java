package com.ax.user.app.api.services;

import com.ax.user.app.api.dto.user.UserCreateDTO;
import com.ax.user.app.api.dto.user.UserDTO;
import com.ax.user.app.api.dto.user.UserUpdateDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll();

    UserDTO findById(Long id);

    UserDTO createUser(UserCreateDTO userCreate);

    UserDTO update(Long id, UserUpdateDTO userUpdate);

    void delete(Long id);
}
