package pt.circuitos.circuitos_energy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.AgendamentoReuniao;
import pt.circuitos.circuitos_energy.model.AgendamentoReuniaoRequest;
import pt.circuitos.circuitos_energy.repository.AgendamentoReuniaoRepository;

@Service
public class AgendamentoReuniaoService {

    private final AgendamentoReuniaoRepository agendamentoReuniaoRepository;

    public AgendamentoReuniaoService(AgendamentoReuniaoRepository agendamentoReuniaoRepository) {
        this.agendamentoReuniaoRepository = agendamentoReuniaoRepository;
    }

    public AgendamentoReuniao guardar(AgendamentoReuniaoRequest request) {
        return agendamentoReuniaoRepository.save(AgendamentoReuniao.fromRequest(request));
    }

    public List<AgendamentoReuniao> listarRecentes() {
        return agendamentoReuniaoRepository.findAllByOrderByCriadoEmDesc();
    }
}
