package pt.circuitos.circuitos_energy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agendamentos_instalacao")
public class AgendamentoInstalacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nomeCompleto;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Column(nullable = false, length = 80)
    private String tipoInstalacao;

    @Column(nullable = false, length = 180)
    private String morada;

    @Column(nullable = false, length = 80)
    private String cidade;

    @Column(nullable = false, length = 8)
    private String codigoPostal;

    @Column(nullable = false, length = 80)
    private String tipoPropriedade;

    @Column(nullable = false)
    private LocalDate dataPreferida;

    @Column(nullable = false, length = 40)
    private String periodoPreferido;

    @Column(length = 800)
    private String observacoes;

    @Column(nullable = false, length = 20)
    private String estado = "Novo";

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public static AgendamentoInstalacao fromRequest(AgendamentoInstalacaoRequest request) {
        AgendamentoInstalacao entity = new AgendamentoInstalacao();
        entity.setNomeCompleto(request.getNomeCompleto());
        entity.setEmail(request.getEmail());
        entity.setTelefone(request.getTelefone());
        entity.setTipoInstalacao(request.getTipoInstalacao());
        entity.setMorada(request.getMorada());
        entity.setCidade(request.getCidade());
        entity.setCodigoPostal(request.getCodigoPostal());
        entity.setTipoPropriedade(request.getTipoPropriedade());
        entity.setDataPreferida(request.getDataPreferida());
        entity.setPeriodoPreferido(request.getPeriodoPreferido());
        entity.setObservacoes(request.getObservacoes());
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
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

    public String getTipoInstalacao() {
        return tipoInstalacao;
    }

    public void setTipoInstalacao(String tipoInstalacao) {
        this.tipoInstalacao = tipoInstalacao;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getTipoPropriedade() {
        return tipoPropriedade;
    }

    public void setTipoPropriedade(String tipoPropriedade) {
        this.tipoPropriedade = tipoPropriedade;
    }

    public LocalDate getDataPreferida() {
        return dataPreferida;
    }

    public void setDataPreferida(LocalDate dataPreferida) {
        this.dataPreferida = dataPreferida;
    }

    public String getPeriodoPreferido() {
        return periodoPreferido;
    }

    public void setPeriodoPreferido(String periodoPreferido) {
        this.periodoPreferido = periodoPreferido;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
