package pt.circuitos.circuitos_energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.Encomenda;

public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {

    @EntityGraph(attributePaths = { "items", "items.servico", "utilizador" })
    List<Encomenda> findAllByOrderByCriadoEmDesc();
}
