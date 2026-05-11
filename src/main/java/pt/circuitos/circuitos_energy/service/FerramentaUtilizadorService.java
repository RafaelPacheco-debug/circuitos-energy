package pt.circuitos.circuitos_energy.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.CalculoSolarRequest;
import pt.circuitos.circuitos_energy.model.CalculoSolarSubmissao;
import pt.circuitos.circuitos_energy.model.OrcamentoPaineisSolaresRequest;
import pt.circuitos.circuitos_energy.model.OrcamentoPaineisSolaresSubmissao;
import pt.circuitos.circuitos_energy.model.PostosCarregamentoRequest;
import pt.circuitos.circuitos_energy.model.PostosCarregamentoResult;
import pt.circuitos.circuitos_energy.model.PostosCarregamentoSubmissao;
import pt.circuitos.circuitos_energy.repository.CalculoSolarSubmissaoRepository;
import pt.circuitos.circuitos_energy.repository.OrcamentoPaineisSolaresSubmissaoRepository;
import pt.circuitos.circuitos_energy.repository.PostosCarregamentoSubmissaoRepository;

@Service
public class FerramentaUtilizadorService {

    private final CalculoSolarSubmissaoRepository calculoSolarRepository;
    private final OrcamentoPaineisSolaresSubmissaoRepository orcamentoPaineisRepository;
    private final PostosCarregamentoSubmissaoRepository postosRepository;

    public FerramentaUtilizadorService(
            CalculoSolarSubmissaoRepository calculoSolarRepository,
            OrcamentoPaineisSolaresSubmissaoRepository orcamentoPaineisRepository,
            PostosCarregamentoSubmissaoRepository postosRepository) {
        this.calculoSolarRepository = calculoSolarRepository;
        this.orcamentoPaineisRepository = orcamentoPaineisRepository;
        this.postosRepository = postosRepository;
    }

    public CalculoSolarSubmissao guardarCalculoSolar(CalculoSolarRequest request,
            OrcamentoService.ResultadoSolar resultado) {
        return calculoSolarRepository.save(CalculoSolarSubmissao.from(request, resultado));
    }

    public OrcamentoPaineisSolaresSubmissao guardarOrcamentoPaineis(OrcamentoPaineisSolaresRequest request) {
        return orcamentoPaineisRepository.save(OrcamentoPaineisSolaresSubmissao.from(request));
    }

    public PostosCarregamentoSubmissao guardarPostos(PostosCarregamentoRequest request,
            PostosCarregamentoResult result) {
        return postosRepository.save(PostosCarregamentoSubmissao.from(request, result));
    }

    public List<CalculoSolarSubmissao> listarCalculosSolares() {
        return calculoSolarRepository.findAllByOrderByCriadoEmDesc();
    }

    public List<OrcamentoPaineisSolaresSubmissao> listarOrcamentosPaineis() {
        return orcamentoPaineisRepository.findAllByOrderByCriadoEmDesc();
    }

    public List<PostosCarregamentoSubmissao> listarOrcamentosPostos() {
        return postosRepository.findAllByOrderByCriadoEmDesc();
    }

    public CalculoSolarSubmissao obterCalculoSolar(Long id) {
        return calculoSolarRepository.findById(id).orElse(null);
    }

    public OrcamentoPaineisSolaresSubmissao obterOrcamentoPaineis(Long id) {
        return orcamentoPaineisRepository.findById(id).orElse(null);
    }

    public PostosCarregamentoSubmissao obterOrcamentoPostos(Long id) {
        return postosRepository.findById(id).orElse(null);
    }
}
