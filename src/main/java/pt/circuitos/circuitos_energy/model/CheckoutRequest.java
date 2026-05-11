package pt.circuitos.circuitos_energy.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CheckoutRequest {

    @NotBlank(message = "Nome completo e obrigatorio")
    @Size(max = 120)
    private String nomeCompleto;

    @NotBlank(message = "Email e obrigatorio")
    @Email(message = "Email invalido")
    @Size(max = 120)
    private String email;

    @NotBlank(message = "Telefone e obrigatorio")
    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{6,20}$", message = "Telefone invalido")
    private String telefone;

    @NotBlank(message = "Morada de envio e obrigatoria")
    @Size(max = 180)
    private String moradaEnvio;

    @NotBlank(message = "Cidade e obrigatoria")
    @Size(max = 80)
    private String cidade;

    @NotBlank(message = "Codigo postal e obrigatorio")
    @Size(max = 20)
    private String codigoPostal;

    @NotBlank(message = "Pais e obrigatorio")
    @Size(max = 80)
    private String pais;

    @NotBlank(message = "Selecione um metodo de pagamento")
    @Size(max = 40)
    private String metodoPagamento;

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

    public String getMoradaEnvio() {
        return moradaEnvio;
    }

    public void setMoradaEnvio(String moradaEnvio) {
        this.moradaEnvio = moradaEnvio;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}
