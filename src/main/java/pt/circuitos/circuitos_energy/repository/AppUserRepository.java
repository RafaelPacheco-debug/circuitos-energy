package pt.circuitos.circuitos_energy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByUsernameIgnoreCase(String username);

    Optional<AppUser> findByEmailIgnoreCase(String email);
}
