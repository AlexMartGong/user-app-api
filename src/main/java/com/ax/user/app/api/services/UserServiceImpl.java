package com.ax.user.app.api.services;

import com.ax.user.app.api.dto.PagedResponse;
import com.ax.user.app.api.dto.user.UserCreateDTO;
import com.ax.user.app.api.dto.user.UserDTO;
import com.ax.user.app.api.dto.user.UserUpdateDTO;
import com.ax.user.app.api.entities.Role;
import com.ax.user.app.api.entities.User;
import com.ax.user.app.api.mapper.UserMapper;
import com.ax.user.app.api.repositories.RoleRepository;
import com.ax.user.app.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

        List<Role> userRoles = assignRoles(userCreate.isAdmin());
        user.setRoles(userRoles);

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

        List<Role> userRoles = assignRoles(userUpdate.isAdmin());
        existingUser.setRoles(userRoles);

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

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<UserDTO> findAll(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<UserDTO> userDTOs = userPage.getContent().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());

        return PagedResponse.<UserDTO>builder()
                .content(userDTOs)
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .first(userPage.isFirst())
                .last(userPage.isLast())
                .empty(userPage.isEmpty())
                .build();
    }

    private List<Role> assignRoles(boolean isAdmin) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        userRole.ifPresent(roles::add);
        if (isAdmin) {
            Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            adminRole.ifPresent(roles::add);
        }
        return roles;
    }
}
