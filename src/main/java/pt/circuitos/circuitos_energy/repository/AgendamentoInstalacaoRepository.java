package pt.circuitos.circuitos_energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.AgendamentoInstalacao;

public interface AgendamentoInstalacaoRepository extends JpaRepository<AgendamentoInstalacao, Long> {
    List<AgendamentoInstalacao> findAllByOrderByCriadoEmDesc();
}
