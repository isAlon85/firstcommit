package com.ialonso.firstcommit.security.service;


import com.ialonso.firstcommit.entities.User;
import com.ialonso.firstcommit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Autentica un usuario de la base de datos
 *
 * Authentication Manager llama al método loadUserByUsername de esta clase
 * para obtener los detalles del usuario de la base de datos cuando
 * se intente autenticar un usuario
 */
@Service(value = "userDetails")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),getAuthority(user));
    }

    // Meter roles en el JWT
    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }
}
