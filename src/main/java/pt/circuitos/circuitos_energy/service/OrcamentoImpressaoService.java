package pt.circuitos.circuitos_energy.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.OrcamentoImpressao;
import pt.circuitos.circuitos_energy.repository.OrcamentoImpressaoRepository;

@Service
public class OrcamentoImpressaoService {

    private static final String ORIGEM_PAGINA_IMPRIMIVEL = "pagina-imprimivel";

    private final OrcamentoImpressaoRepository repository;

    public OrcamentoImpressaoService(OrcamentoImpressaoRepository repository) {
        this.repository = repository;
    }

    public OrcamentoImpressao registarAbertura(
            HttpSession session,
            String tipoOrcamento,
            String nome,
            String email,
            String telefone,
            String resumo,
            String valorPrincipal,
            Long referenciaSubmissaoId) {

        String sessionIdHash = hashSessionId(session.getId());
        String resumoSeguro = truncate(resumo, 500);
        String valorSeguro = truncate(valorPrincipal, 120);

        boolean jaExiste = repository.existsBySessionIdHashAndTipoOrcamentoAndResumoAndValorPrincipal(
                sessionIdHash,
                tipoOrcamento,
                resumoSeguro,
                valorSeguro);

        if (jaExiste) {
            return null;
        }

        OrcamentoImpressao registo = new OrcamentoImpressao();
        registo.setTipoOrcamento(tipoOrcamento);
        registo.setOrigem(ORIGEM_PAGINA_IMPRIMIVEL);
        registo.setNome(truncate(nome, 120));
        registo.setEmail(truncate(email, 120));
        registo.setTelefone(truncate(telefone, 30));
        registo.setResumo(resumoSeguro);
        registo.setValorPrincipal(valorSeguro);
        registo.setReferenciaSubmissaoId(referenciaSubmissaoId);
        registo.setSessionIdHash(sessionIdHash);

        return repository.save(registo);
    }

    public List<OrcamentoImpressao> listarRecentes() {
        return repository.findAllByOrderByCriadoEmDesc();
    }

    private String hashSessionId(String sessionId) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(sessionId.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Nao foi possivel gerar identificador seguro da sessao.", ex);
        }
    }

    private String truncate(String value, int maxLength) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        if (trimmed.length() <= maxLength) {
            return trimmed;
        }

        return trimmed.substring(0, maxLength);
    }
}
