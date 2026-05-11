package pt.circuitos.circuitos_energy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.AgendamentoInstalacao;
import pt.circuitos.circuitos_energy.model.AgendamentoInstalacaoRequest;
import pt.circuitos.circuitos_energy.repository.AgendamentoInstalacaoRepository;

@Service
public class AgendamentoInstalacaoService {

    private final AgendamentoInstalacaoRepository agendamentoInstalacaoRepository;

    public AgendamentoInstalacaoService(AgendamentoInstalacaoRepository agendamentoInstalacaoRepository) {
        this.agendamentoInstalacaoRepository = agendamentoInstalacaoRepository;
    }

    public AgendamentoInstalacao guardar(AgendamentoInstalacaoRequest request) {
        return agendamentoInstalacaoRepository.save(AgendamentoInstalacao.fromRequest(request));
    }

    public List<AgendamentoInstalacao> listarRecentes() {
        return agendamentoInstalacaoRepository.findAllByOrderByCriadoEmDesc();
    }
}
