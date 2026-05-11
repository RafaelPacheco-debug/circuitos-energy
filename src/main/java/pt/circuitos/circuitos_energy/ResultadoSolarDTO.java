package pt.circuitos.circuitos_energy;

public class ResultadoSolarDTO {

    private double potenciaNecessariaKw;
    private double consumoAnualKwh;

    private double custoAnualAtual;
    private double economiaAnualEstimada;
    private double economiaPercent;

    private double paybackAnos;
    private double economiaTotal10Anos;

    public double getPotenciaNecessariaKw() {
        return potenciaNecessariaKw;
    }

    public void setPotenciaNecessariaKw(double potenciaNecessariaKw) {
        this.potenciaNecessariaKw = potenciaNecessariaKw;
    }

    public double getConsumoAnualKwh() {
        return consumoAnualKwh;
    }

    public void setConsumoAnualKwh(double consumoAnualKwh) {
        this.consumoAnualKwh = consumoAnualKwh;
    }

    public double getCustoAnualAtual() {
        return custoAnualAtual;
    }

    public void setCustoAnualAtual(double custoAnualAtual) {
        this.custoAnualAtual = custoAnualAtual;
    }

    public double getEconomiaAnualEstimada() {
        return economiaAnualEstimada;
    }

    public void setEconomiaAnualEstimada(double economiaAnualEstimada) {
        this.economiaAnualEstimada = economiaAnualEstimada;
    }

    public double getEconomiaPercent() {
        return economiaPercent;
    }

    public void setEconomiaPercent(double economiaPercent) {
        this.economiaPercent = economiaPercent;
    }

    public double getPaybackAnos() {
        return paybackAnos;
    }

    public void setPaybackAnos(double paybackAnos) {
        this.paybackAnos = paybackAnos;
    }

    public double getEconomiaTotal10Anos() {
        return economiaTotal10Anos;
    }

    public void setEconomiaTotal10Anos(double economiaTotal10Anos) {
        this.economiaTotal10Anos = economiaTotal10Anos;
    }
}