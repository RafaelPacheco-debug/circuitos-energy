package pt.circuitos.circuitos_energy.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ferramenta_orcamento_postos")
public class PostosCarregamentoSubmissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nomeCompleto;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(length = 30)
    private String telefone;

    @Column(nullable = false, length = 40)
    private String tipoLocalizacao;

    @Column(nullable = false, length = 20)
    private String tipoCarregador;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false, length = 30)
    private String tipoInstalacao;

    @Column(length = 80)
    private String wizardInstallationType;

    @Column(length = 80)
    private String wizardInstallationPlace;

    @Column(length = 80)
    private String wizardHasEv;

    @Column(length = 80)
    private String wizardChargingType;

    @Column(length = 80)
    private String wizardGridAvailable;

    @Column(length = 180)
    private String wizardAddress;

    @Column(length = 80)
    private String wizardCity;

    @Column(length = 20)
    private String wizardPostalCode;

    @Column(length = 80)
    private String wizardName;

    @Column(length = 80)
    private String wizardSurname;

    @Column(length = 30)
    private String wizardPhone;

    @Column(length = 120)
    private String wizardEmail;

    @Column(nullable = false)
    private Double custoTotalEstimado;

    @Column(nullable = false)
    private Double custoPorUnidade;

    @Column(nullable = false)
    private Double instalacao;

    @Column(nullable = false)
    private Double desconto;

    @Column(nullable = false, length = 20)
    private String estado = "Novo";

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public static PostosCarregamentoSubmissao from(PostosCarregamentoRequest request, PostosCarregamentoResult result) {
        PostosCarregamentoSubmissao entity = new PostosCarregamentoSubmissao();
        entity.setNomeCompleto(request.getNomeCompleto());
        entity.setEmail(request.getEmail());
        entity.setTelefone(request.getTelefone());
        entity.setTipoLocalizacao(request.getTipoLocalizacao());
        entity.setTipoCarregador(request.getTipoCarregador());
        entity.setQuantidade(request.getQuantidade());
        entity.setTipoInstalacao(request.getTipoInstalacao());
        entity.setWizardInstallationType(request.getWizardInstallationType());
        entity.setWizardInstallationPlace(request.getWizardInstallationPlace());
        entity.setWizardHasEv(request.getWizardHasEv());
        entity.setWizardChargingType(request.getWizardChargingType());
        entity.setWizardGridAvailable(request.getWizardGridAvailable());
        entity.setWizardAddress(request.getWizardAddress());
        entity.setWizardCity(request.getWizardCity());
        entity.setWizardPostalCode(request.getWizardPostalCode());
        entity.setWizardName(request.getWizardName());
        entity.setWizardSurname(request.getWizardSurname());
        entity.setWizardPhone(request.getWizardPhone());
        entity.setWizardEmail(request.getWizardEmail());
        entity.setCustoTotalEstimado(result.getCustoTotalEstimado());
        entity.setCustoPorUnidade(result.getCustoPorUnidade());
        entity.setInstalacao(result.getInstalacao());
        entity.setDesconto(result.getDesconto());
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

    public Double getCustoTotalEstimado() {
        return custoTotalEstimado;
    }

    public void setCustoTotalEstimado(Double custoTotalEstimado) {
        this.custoTotalEstimado = custoTotalEstimado;
    }

    public Double getCustoPorUnidade() {
        return custoPorUnidade;
    }

    public void setCustoPorUnidade(Double custoPorUnidade) {
        this.custoPorUnidade = custoPorUnidade;
    }

    public Double getInstalacao() {
        return instalacao;
    }

    public void setInstalacao(Double instalacao) {
        this.instalacao = instalacao;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
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
