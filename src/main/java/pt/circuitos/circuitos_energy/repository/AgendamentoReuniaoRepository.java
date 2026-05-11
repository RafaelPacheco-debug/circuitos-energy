package pt.circuitos.circuitos_energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.AgendamentoReuniao;

public interface AgendamentoReuniaoRepository extends JpaRepository<AgendamentoReuniao, Long> {
    List<AgendamentoReuniao> findAllByOrderByCriadoEmDesc();
}
