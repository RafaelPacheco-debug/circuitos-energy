package pt.circuitos.circuitos_energy.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ferramenta_orcamento_paineis")
public class OrcamentoPaineisSolaresSubmissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nomeCompleto;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Column(nullable = false, length = 30)
    private String areaDisponivel;

    @Column(nullable = false, length = 60)
    private String tipoPainel;

    @Column(nullable = false, length = 60)
    private String tipoTelhado;

    @Column(length = 30)
    private String consumoMensal;

    @Column(length = 80)
    private String installationType;

    @Column(length = 80)
    private String propertyType;

    @Column(length = 80)
    private String ownershipStatus;

    @Column(length = 80)
    private String constructionStatus;

    @Column(length = 80)
    private String gridConnection;

    @Column(length = 80)
    private String installationSurface;

    @Column(length = 80)
    private String availableAreaBand;

    @Column(length = 180)
    private String projectAddress;

    @Column(length = 80)
    private String projectCity;

    @Column(length = 20)
    private String projectPostalCode;

    @Column(length = 80)
    private String contactFirstName;

    @Column(length = 80)
    private String contactLastName;

    @Column(nullable = false, length = 20)
    private String estado = "Novo";

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public static OrcamentoPaineisSolaresSubmissao from(OrcamentoPaineisSolaresRequest request) {
        OrcamentoPaineisSolaresSubmissao entity = new OrcamentoPaineisSolaresSubmissao();
        entity.setNomeCompleto(request.getNomeCompleto());
        entity.setEmail(request.getEmail());
        entity.setTelefone(request.getTelefone());
        entity.setAreaDisponivel(request.getAreaDisponivel());
        entity.setTipoPainel(request.getTipoPainel());
        entity.setTipoTelhado(request.getTipoTelhado());
        entity.setConsumoMensal(request.getConsumoMensal());
        entity.setInstallationType(request.getInstallationType());
        entity.setPropertyType(request.getPropertyType());
        entity.setOwnershipStatus(request.getOwnershipStatus());
        entity.setConstructionStatus(request.getConstructionStatus());
        entity.setGridConnection(request.getGridConnection());
        entity.setInstallationSurface(request.getInstallationSurface());
        entity.setAvailableAreaBand(request.getAvailableAreaBand());
        entity.setProjectAddress(request.getProjectAddress());
        entity.setProjectCity(request.getProjectCity());
        entity.setProjectPostalCode(request.getProjectPostalCode());
        entity.setContactFirstName(request.getContactFirstName());
        entity.setContactLastName(request.getContactLastName());
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
