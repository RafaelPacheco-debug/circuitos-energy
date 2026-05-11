package pt.circuitos.circuitos_energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.CalculoSolarSubmissao;

public interface CalculoSolarSubmissaoRepository extends JpaRepository<CalculoSolarSubmissao, Long> {
    List<CalculoSolarSubmissao> findAllByOrderByCriadoEmDesc();
}
