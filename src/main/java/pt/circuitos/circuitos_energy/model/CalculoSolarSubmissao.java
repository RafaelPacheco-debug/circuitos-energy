package pt.circuitos.circuitos_energy.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pt.circuitos.circuitos_energy.service.OrcamentoService;

@Entity
@Table(name = "ferramenta_calculo_solar")
public class CalculoSolarSubmissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double consumoMensalKwh;

    @Column(nullable = false)
    private Double tarifaKwh;

    @Column(nullable = false)
    private Double horasUsoDiario;

    @Column(nullable = false)
    private Double investimento;

    @Column(length = 80)
    private String installationType;

    @Column(length = 20)
    private String ownership;

    @Column(length = 20)
    private String construction;

    @Column(length = 20)
    private String gridConnection;

    @Column(length = 180)
    private String address;

    @Column(length = 80)
    private String city;

    @Column(length = 20)
    private String postalCode;

    @Column(length = 80)
    private String firstName;

    @Column(length = 80)
    private String lastName;

    @Column(length = 30)
    private String phone;

    @Column(length = 120)
    private String email;

    @Column(nullable = false)
    private Double potenciaNecessariaKw;

    @Column(nullable = false)
    private Double consumoAnualKwh;

    @Column(nullable = false)
    private Double custoAnualAtual;

    @Column(nullable = false)
    private Double economiaAnualEstimada;

    @Column(nullable = false)
    private Double economiaPercent;

    @Column(nullable = false)
    private Double paybackAnos;

    @Column(nullable = false, length = 20)
    private String estado = "Novo";

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public static CalculoSolarSubmissao from(CalculoSolarRequest request, OrcamentoService.ResultadoSolar resultado) {
        CalculoSolarSubmissao entity = new CalculoSolarSubmissao();
        entity.setConsumoMensalKwh(request.getConsumoMensalKwh());
        entity.setTarifaKwh(request.getTarifaKwh());
        entity.setHorasUsoDiario(request.getHorasUsoDiario());
        entity.setInvestimento(request.getInvestimento());
        entity.setInstallationType(request.getInstallationType());
        entity.setOwnership(request.getOwnership());
        entity.setConstruction(request.getConstruction());
        entity.setGridConnection(request.getGridConnection());
        entity.setAddress(request.getAddress());
        entity.setCity(request.getCity());
        entity.setPostalCode(request.getPostalCode());
        entity.setFirstName(request.getName());
        entity.setLastName(request.getSurname());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setPotenciaNecessariaKw(resultado.getPotenciaNecessariaKw());
        entity.setConsumoAnualKwh(resultado.getConsumoAnualKwh());
        entity.setCustoAnualAtual(resultado.getCustoAnualAtual());
        entity.setEconomiaAnualEstimada(resultado.getEconomiaAnualEstimada());
        entity.setEconomiaPercent(resultado.getEconomiaPercent());
        entity.setPaybackAnos(resultado.getPaybackAnos());
        return entity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getConsumoMensalKwh() {
        return consumoMensalKwh;
    }

    public void setConsumoMensalKwh(Double consumoMensalKwh) {
        this.consumoMensalKwh = consumoMensalKwh;
    }

    public Double getTarifaKwh() {
        return tarifaKwh;
    }

    public void setTarifaKwh(Double tarifaKwh) {
        this.tarifaKwh = tarifaKwh;
    }

    public Double getHorasUsoDiario() {
        return horasUsoDiario;
    }

    public void setHorasUsoDiario(Double horasUsoDiario) {
        this.horasUsoDiario = horasUsoDiario;
    }

    public Double getInvestimento() {
        return investimento;
    }

    public void setInvestimento(Double investimento) {
        this.investimento = investimento;
    }

    public String getInstallationType() {
        return installationType;
    }

    public void setInstallationType(String installationType) {
        this.installationType = installationType;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public String getGridConnection() {
        return gridConnection;
    }

    public void setGridConnection(String gridConnection) {
        this.gridConnection = gridConnection;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getPotenciaNecessariaKw() {
        return potenciaNecessariaKw;
    }

    public void setPotenciaNecessariaKw(Double potenciaNecessariaKw) {
        this.potenciaNecessariaKw = potenciaNecessariaKw;
    }

    public Double getConsumoAnualKwh() {
        return consumoAnualKwh;
    }

    public void setConsumoAnualKwh(Double consumoAnualKwh) {
        this.consumoAnualKwh = consumoAnualKwh;
    }

    public Double getCustoAnualAtual() {
        return custoAnualAtual;
    }

    public void setCustoAnualAtual(Double custoAnualAtual) {
        this.custoAnualAtual = custoAnualAtual;
    }

    public Double getEconomiaAnualEstimada() {
        return economiaAnualEstimada;
    }

    public void setEconomiaAnualEstimada(Double economiaAnualEstimada) {
        this.economiaAnualEstimada = economiaAnualEstimada;
    }

    public Double getEconomiaPercent() {
        return economiaPercent;
    }

    public void setEconomiaPercent(Double economiaPercent) {
        this.economiaPercent = economiaPercent;
    }

    public Double getPaybackAnos() {
        return paybackAnos;
    }

    public void setPaybackAnos(Double paybackAnos) {
        this.paybackAnos = paybackAnos;
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
