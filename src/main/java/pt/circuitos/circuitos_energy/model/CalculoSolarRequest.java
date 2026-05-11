package pt.circuitos.circuitos_energy.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CalculoSolarRequest {

    @NotNull
    @DecimalMin("50.0")
    private Double consumoMensalKwh;

    @NotNull
    @DecimalMin("0.05")
    private Double tarifaKwh;

    @NotNull
    @DecimalMin("1.0")
    private Double horasUsoDiario;

    @NotNull
    @DecimalMin("0.0")
    private Double investimento;

    @Size(max = 80)
    private String installationType;

    @Size(max = 20)
    private String ownership;

    @Size(max = 20)
    private String construction;

    @Size(max = 20)
    private String gridConnection;

    @Size(max = 180)
    private String address;

    @Size(max = 80)
    private String city;

    @Size(max = 20)
    private String postalCode;

    @Size(max = 80)
    private String name;

    @Size(max = 80)
    private String surname;

    @Pattern(regexp = "^$|^\\+?[0-9\\s\\-()]{6,20}$", message = "Telefone inválido")
    private String phone;

    @Email(message = "Email inválido")
    private String email;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
}
