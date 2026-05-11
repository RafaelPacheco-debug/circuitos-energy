package pt.circuitos.circuitos_energy.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AgendamentoReuniaoRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 80)
    private String nomeCompleto;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 120)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{6,20}$", message = "Telefone inválido")
    @Size(max = 30)
    private String telefone;

    @NotBlank(message = "Assunto é obrigatório")
    @Size(max = 120)
    private String assunto;

    @NotNull(message = "Data preferida é obrigatória")
    @FutureOrPresent(message = "A data preferida não pode ser no passado")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataPreferida;

    @NotBlank(message = "Horário preferido é obrigatório")
    @Size(max = 40)
    private String horarioPreferido;

    @Size(max = 800, message = "Observações devem ter até 800 caracteres")
    private String observacoes;

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
}
