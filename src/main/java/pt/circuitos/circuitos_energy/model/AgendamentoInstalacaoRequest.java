package pt.circuitos.circuitos_energy.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AgendamentoInstalacaoRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 80)
    private String nomeCompleto;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 120)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{6,20}$", message = "Telefone inválido")
    private String telefone;

    @NotBlank(message = "Tipo de instalação é obrigatório")
    private String tipoInstalacao;

    @NotBlank(message = "Morada é obrigatória")
    private String morada;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Código postal é obrigatório")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{3}$", message = "Formato de código postal inválido")
    private String codigoPostal;

    @NotBlank(message = "Tipo de propriedade é obrigatório")
    private String tipoPropriedade;

    @NotNull(message = "Data preferida é obrigatória")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataPreferida;

    @NotBlank(message = "Período preferido é obrigatório")
    private String periodoPreferido;

    @Size(max = 800, message = "Observações devem ter até 800 caracteres")
    private String observacoes;

    // Getters / Setters

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
}
