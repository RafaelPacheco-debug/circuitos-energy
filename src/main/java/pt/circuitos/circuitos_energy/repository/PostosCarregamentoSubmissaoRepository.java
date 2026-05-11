package pt.circuitos.circuitos_energy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.circuitos.circuitos_energy.model.PostosCarregamentoSubmissao;

public interface PostosCarregamentoSubmissaoRepository extends JpaRepository<PostosCarregamentoSubmissao, Long> {
    List<PostosCarregamentoSubmissao> findAllByOrderByCriadoEmDesc();
}
