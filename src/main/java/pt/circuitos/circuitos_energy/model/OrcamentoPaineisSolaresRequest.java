package pt.circuitos.circuitos_energy.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrcamentoPaineisSolaresRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 120)
    private String nomeCompleto;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Size(max = 120)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\+?[0-9\\s\\-()]{6,20}$", message = "Telefone inválido")
    private String telefone;

    @NotBlank(message = "Área disponível é obrigatória")
    private String areaDisponivel;

    @NotBlank(message = "Tipo de painel é obrigatório")
    private String tipoPainel;

    @NotBlank(message = "Tipo de telhado é obrigatório")
    private String tipoTelhado;

    private String consumoMensal;
    private String installationType;
    private String propertyType;
    private String ownershipStatus;
    private String constructionStatus;
    private String gridConnection;
    private String installationSurface;
    private String availableAreaBand;
    private String projectAddress;
    private String projectCity;
    private String projectPostalCode;
    private String contactFirstName;
    private String contactLastName;

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

    public String getAreaDisponivel() {
        return areaDisponivel;
    }

    public void setAreaDisponivel(String areaDisponivel) {
        this.areaDisponivel = areaDisponivel;
    }

    public String getTipoPainel() {
        return tipoPainel;
    }

    public void setTipoPainel(String tipoPainel) {
        this.tipoPainel = tipoPainel;
    }

    public String getTipoTelhado() {
        return tipoTelhado;
    }

    public void setTipoTelhado(String tipoTelhado) {
        this.tipoTelhado = tipoTelhado;
    }

    public String getConsumoMensal() {
        return consumoMensal;
    }

    public void setConsumoMensal(String consumoMensal) {
        this.consumoMensal = consumoMensal;
    }

    public String getInstallationType() {
        return installationType;
    }

    public void setInstallationType(String installationType) {
        this.installationType = installationType;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getOwnershipStatus() {
        return ownershipStatus;
    }

    public void setOwnershipStatus(String ownershipStatus) {
        this.ownershipStatus = ownershipStatus;
    }

    public String getConstructionStatus() {
        return constructionStatus;
    }

    public void setConstructionStatus(String constructionStatus) {
        this.constructionStatus = constructionStatus;
    }

    public String getGridConnection() {
        return gridConnection;
    }

    public void setGridConnection(String gridConnection) {
        this.gridConnection = gridConnection;
    }

    public String getInstallationSurface() {
        return installationSurface;
    }

    public void setInstallationSurface(String installationSurface) {
        this.installationSurface = installationSurface;
    }

    public String getAvailableAreaBand() {
        return availableAreaBand;
    }

    public void setAvailableAreaBand(String availableAreaBand) {
        this.availableAreaBand = availableAreaBand;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getProjectCity() {
        return projectCity;
    }

    public void setProjectCity(String projectCity) {
        this.projectCity = projectCity;
    }

    public String getProjectPostalCode() {
        return projectPostalCode;
    }

    public void setProjectPostalCode(String projectPostalCode) {
        this.projectPostalCode = projectPostalCode;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }
}
