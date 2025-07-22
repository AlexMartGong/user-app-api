package com.ax.user.app.api.services;

import com.ax.user.app.api.dto.user.UserCreateDTO;
import com.ax.user.app.api.dto.user.UserDTO;
import com.ax.user.app.api.dto.user.UserUpdateDTO;
import com.ax.user.app.api.entities.Role;
import com.ax.user.app.api.entities.User;
import com.ax.user.app.api.mapper.UserMapper;
import com.ax.user.app.api.repositories.RoleRepository;
import com.ax.user.app.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.toDTO(user);
    }

    @Override
    @Transactional
    public UserDTO createUser(UserCreateDTO userCreate) {
        User user = userMapper.toEntity(userCreate);

        Optional<Role> role = roleRepository.findByName("ROLE_USER");
        role.ifPresent(value -> user.setRoles(List.of(value)));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    @Override
    @Transactional
    public UserDTO update(Long id, UserUpdateDTO userUpdate) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return null;
        }

        userMapper.updateEntity(userUpdate, existingUser);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDTO updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null || !passwordEncoder.matches(oldPassword, user.getPassword())) {
            return null;
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        User updateUser = userRepository.save(user);
        return userMapper.toDTO(updateUser);
    }
}
