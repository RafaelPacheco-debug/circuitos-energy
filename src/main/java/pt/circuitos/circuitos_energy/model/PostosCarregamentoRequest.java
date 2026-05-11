package pt.circuitos.circuitos_energy.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PostosCarregamentoRequest {

    @NotBlank(message = "O nome completo e obrigatorio")
    private String nomeCompleto;

    @NotBlank(message = "O email e obrigatorio")
    private String email;

    private String telefone;

    @NotBlank(message = "Selecione o tipo de localizacao")
    private String tipoLocalizacao;

    @NotBlank(message = "Selecione o tipo de carregador")
    private String tipoCarregador;

    @NotNull(message = "A quantidade e obrigatoria")
    @Min(value = 1, message = "A quantidade deve ser pelo menos 1")
    private Integer quantidade;

    @NotBlank(message = "Selecione o tipo de instalacao")
    private String tipoInstalacao;

    private String wizardInstallationType;
    private String wizardInstallationPlace;
    private String wizardHasEv;
    private String wizardChargingType;
    private String wizardGridAvailable;
    private String wizardAddress;
    private String wizardCity;
    private String wizardPostalCode;
    private String wizardName;
    private String wizardSurname;
    private String wizardPhone;
    private String wizardEmail;

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

    public String getTipoLocalizacao() {
        return tipoLocalizacao;
    }

    public void setTipoLocalizacao(String tipoLocalizacao) {
        this.tipoLocalizacao = tipoLocalizacao;
    }

    public String getTipoCarregador() {
        return tipoCarregador;
    }

    public void setTipoCarregador(String tipoCarregador) {
        this.tipoCarregador = tipoCarregador;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipoInstalacao() {
        return tipoInstalacao;
    }

    public void setTipoInstalacao(String tipoInstalacao) {
        this.tipoInstalacao = tipoInstalacao;
    }

    public String getWizardInstallationType() {
        return wizardInstallationType;
    }

    public void setWizardInstallationType(String wizardInstallationType) {
        this.wizardInstallationType = wizardInstallationType;
    }

    public String getWizardInstallationPlace() {
        return wizardInstallationPlace;
    }

    public void setWizardInstallationPlace(String wizardInstallationPlace) {
        this.wizardInstallationPlace = wizardInstallationPlace;
    }

    public String getWizardHasEv() {
        return wizardHasEv;
    }

    public void setWizardHasEv(String wizardHasEv) {
        this.wizardHasEv = wizardHasEv;
    }

    public String getWizardChargingType() {
        return wizardChargingType;
    }

    public void setWizardChargingType(String wizardChargingType) {
        this.wizardChargingType = wizardChargingType;
    }

    public String getWizardGridAvailable() {
        return wizardGridAvailable;
    }

    public void setWizardGridAvailable(String wizardGridAvailable) {
        this.wizardGridAvailable = wizardGridAvailable;
    }

    public String getWizardAddress() {
        return wizardAddress;
    }

    public void setWizardAddress(String wizardAddress) {
        this.wizardAddress = wizardAddress;
    }

    public String getWizardCity() {
        return wizardCity;
    }

    public void setWizardCity(String wizardCity) {
        this.wizardCity = wizardCity;
    }

    public String getWizardPostalCode() {
        return wizardPostalCode;
    }

    public void setWizardPostalCode(String wizardPostalCode) {
        this.wizardPostalCode = wizardPostalCode;
    }

    public String getWizardName() {
        return wizardName;
    }

    public void setWizardName(String wizardName) {
        this.wizardName = wizardName;
    }

    public String getWizardSurname() {
        return wizardSurname;
    }

    public void setWizardSurname(String wizardSurname) {
        this.wizardSurname = wizardSurname;
    }

    public String getWizardPhone() {
        return wizardPhone;
    }

    public void setWizardPhone(String wizardPhone) {
        this.wizardPhone = wizardPhone;
    }

    public String getWizardEmail() {
        return wizardEmail;
    }

    public void setWizardEmail(String wizardEmail) {
        this.wizardEmail = wizardEmail;
    }
}
