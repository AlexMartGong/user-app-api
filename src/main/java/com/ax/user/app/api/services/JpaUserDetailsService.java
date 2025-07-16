package com.ax.user.app.api.services;

import com.ax.user.app.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.ax.user.app.api.entities.User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

            return new User(
                    userOptional.get().getUsername(),
                    userOptional.get().getPassword(),
                    true,
                    true,
                    true,
                    true,
                    grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }

}
