package pt.circuitos.circuitos_energy.service;

import java.util.Arrays;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.AppUser;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final AppUserService appUserService;

    public DatabaseUserDetailsService(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        String[] roles = user.getRoles() != null ? user.getRoles().split(",") : new String[0];
        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(roles)
                .disabled(!user.isEnabled())
                .build();
    }
}
