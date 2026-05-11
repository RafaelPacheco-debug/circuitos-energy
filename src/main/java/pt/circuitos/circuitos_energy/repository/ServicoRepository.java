package pt.circuitos.circuitos_energy.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pt.circuitos.circuitos_energy.model.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findAllByOrderByIdDesc();

}
