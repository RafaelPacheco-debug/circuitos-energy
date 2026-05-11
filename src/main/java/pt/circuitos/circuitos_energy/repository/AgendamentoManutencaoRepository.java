package pt.circuitos.circuitos_energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.AgendamentoManutencao;

public interface AgendamentoManutencaoRepository extends JpaRepository<AgendamentoManutencao, Long> {
    List<AgendamentoManutencao> findAllByOrderByCriadoEmDesc();
}
