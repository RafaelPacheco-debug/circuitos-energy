package pt.circuitos.circuitos_energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.OrcamentoPaineisSolaresSubmissao;

public interface OrcamentoPaineisSolaresSubmissaoRepository
        extends JpaRepository<OrcamentoPaineisSolaresSubmissao, Long> {
    List<OrcamentoPaineisSolaresSubmissao> findAllByOrderByCriadoEmDesc();
}
