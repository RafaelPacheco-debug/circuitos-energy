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
        AppUser user = appUserService.findByUsernameIgnoreCase(username)
                .or(() -> appUserService.findByEmailIgnoreCase(username))
                .orElseThrow(() -> new UsernameNotFoundException("Utilizador nao encontrado: " + username));

        String[] authorities = user.getRoles() == null ? new String[0]
                : Arrays.stream(user.getRoles().split(","))
                        .map(String::trim)
                        .filter(role -> !role.isBlank())
                        .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                        .distinct()
                        .toArray(String[]::new);

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!user.isEnabled())
                .build();
    }
}
