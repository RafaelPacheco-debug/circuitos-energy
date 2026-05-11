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
@Table(name = "agendamentos_reuniao")
public class AgendamentoReuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String nomeCompleto;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Column(nullable = false, length = 120)
    private String assunto;

    @Column(nullable = false)
    private LocalDate dataPreferida;

    @Column(nullable = false, length = 40)
    private String horarioPreferido;

    @Column(length = 800)
    private String observacoes;

    @Column(nullable = false, length = 20)
    private String estado = "Novo";

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public static AgendamentoReuniao fromRequest(AgendamentoReuniaoRequest request) {
        AgendamentoReuniao entity = new AgendamentoReuniao();
        entity.setNomeCompleto(request.getNomeCompleto());
        entity.setEmail(request.getEmail());
        entity.setTelefone(request.getTelefone());
        entity.setAssunto(request.getAssunto());
        entity.setDataPreferida(request.getDataPreferida());
        entity.setHorarioPreferido(request.getHorarioPreferido());
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

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public LocalDate getDataPreferida() {
        return dataPreferida;
    }

    public void setDataPreferida(LocalDate dataPreferida) {
        this.dataPreferida = dataPreferida;
    }

    public String getHorarioPreferido() {
        return horarioPreferido;
    }

    public void setHorarioPreferido(String horarioPreferido) {
        this.horarioPreferido = horarioPreferido;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
