package pt.circuitos.circuitos_energy.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orcamentos_impressos")
public class OrcamentoImpressao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String tipoOrcamento;

    @Column(nullable = false, length = 60)
    private String origem;

    @Column(length = 120)
    private String nome;

    @Column(length = 120)
    private String email;

    @Column(length = 30)
    private String telefone;

    @Column(nullable = false, length = 500)
    private String resumo;

    @Column(length = 120)
    private String valorPrincipal;

    private Long referenciaSubmissaoId;

    @Column(nullable = false, length = 64)
    private String sessionIdHash;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoOrcamento() {
        return tipoOrcamento;
    }

    public void setTipoOrcamento(String tipoOrcamento) {
        this.tipoOrcamento = tipoOrcamento;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getValorPrincipal() {
        return valorPrincipal;
    }

    public void setValorPrincipal(String valorPrincipal) {
        this.valorPrincipal = valorPrincipal;
    }

    public Long getReferenciaSubmissaoId() {
        return referenciaSubmissaoId;
    }

    public void setReferenciaSubmissaoId(Long referenciaSubmissaoId) {
        this.referenciaSubmissaoId = referenciaSubmissaoId;
    }

    public String getSessionIdHash() {
        return sessionIdHash;
    }

    public void setSessionIdHash(String sessionIdHash) {
        this.sessionIdHash = sessionIdHash;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
