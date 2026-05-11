package pt.circuitos.circuitos_energy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
