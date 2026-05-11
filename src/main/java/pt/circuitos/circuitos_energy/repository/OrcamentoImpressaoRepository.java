package pt.circuitos.circuitos_energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.OrcamentoImpressao;

public interface OrcamentoImpressaoRepository extends JpaRepository<OrcamentoImpressao, Long> {
    List<OrcamentoImpressao> findAllByOrderByCriadoEmDesc();

    boolean existsBySessionIdHashAndTipoOrcamentoAndResumoAndValorPrincipal(
            String sessionIdHash,
            String tipoOrcamento,
            String resumo,
            String valorPrincipal);
}
