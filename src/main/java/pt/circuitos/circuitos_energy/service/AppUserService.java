package pt.circuitos.circuitos_energy.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.AppUser;
import pt.circuitos.circuitos_energy.repository.AppUserRepository;

@Service
public class AppUserService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<AppUser> findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public Optional<AppUser> findByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean emailExists(String email) {
        return userRepository.findAll().stream()
                .anyMatch(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email));
    }

    public long countUsers() {
        return userRepository.count();
    }

    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public AppUser register(String username, String rawPassword, String fullName, String email, String roles) {
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRoles(roles);
        user.setEnabled(true);
        return userRepository.save(user);
    }
}
