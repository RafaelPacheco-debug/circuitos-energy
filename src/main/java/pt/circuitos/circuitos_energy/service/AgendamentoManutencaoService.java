package pt.circuitos.circuitos_energy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.AgendamentoManutencao;
import pt.circuitos.circuitos_energy.model.AgendamentoRequest;
import pt.circuitos.circuitos_energy.repository.AgendamentoManutencaoRepository;

@Service
public class AgendamentoManutencaoService {

    private final AgendamentoManutencaoRepository agendamentoManutencaoRepository;

    public AgendamentoManutencaoService(AgendamentoManutencaoRepository agendamentoManutencaoRepository) {
        this.agendamentoManutencaoRepository = agendamentoManutencaoRepository;
    }

    public AgendamentoManutencao guardar(AgendamentoRequest request) {
        return agendamentoManutencaoRepository.save(AgendamentoManutencao.fromRequest(request));
    }

    public List<AgendamentoManutencao> listarRecentes() {
        return agendamentoManutencaoRepository.findAllByOrderByCriadoEmDesc();
    }
}
