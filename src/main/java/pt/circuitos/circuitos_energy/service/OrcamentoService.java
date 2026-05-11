package pt.circuitos.circuitos_energy.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.OrcamentoPaineisSolaresRequest;
import pt.circuitos.circuitos_energy.model.PostosCarregamentoRequest;
import pt.circuitos.circuitos_energy.model.PostosCarregamentoResult;

@Service
public class OrcamentoService {

    public ResultadoSolar calcularOrcamentoSolar(double consumoMensalKwh,
            double tarifaKwh,
            double horasUsoDiario,
            double investimento) {

        double consumoDiarioKwh = consumoMensalKwh / 30.0;
        double consumoAnualKwh = consumoMensalKwh * 12.0;
        double potenciaNecessariaKw = consumoDiarioKwh / horasUsoDiario;

        double custoAnualAtual = consumoAnualKwh * tarifaKwh;

        // Estimativa de economia (usualmente 60-70% com painéis solares)
        double economiaPercentual = 65.0;
        double economiaAnualEstimada = custoAnualAtual * (economiaPercentual / 100.0);

        double poupancaMensal = economiaAnualEstimada / 12.0;
        double poupancaAnual = economiaAnualEstimada;
        double paybackAnos = (economiaAnualEstimada > 0) ? (investimento / economiaAnualEstimada) : 0.0;
        double economiaTotal10Anos = economiaAnualEstimada * 10.0;

        return new ResultadoSolar(
                potenciaNecessariaKw,
                consumoAnualKwh,
                custoAnualAtual,
                economiaAnualEstimada,
                economiaPercentual,
                paybackAnos,
                economiaTotal10Anos,
                poupancaMensal,
                poupancaAnual);
    }

    // RF11: Calculo de paineis solares por area disponivel.
    public Map<String, Object> calcularOrcamentoSolar(double areaDisponivel, double consumoMensalKwh) {
        double potenciaEstimadaKwp = areaDisponivel / 5.0;
        double custoEstimado = potenciaEstimadaKwp * 1500.0;
        double producaoMensalEstimada = potenciaEstimadaKwp * 125;

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("potenciaKwp", Math.round(potenciaEstimadaKwp * 100.0) / 100.0);
        resultado.put("custoTotal", Math.round(custoEstimado * 100.0) / 100.0);
        resultado.put("producaoMensal", Math.round(producaoMensalEstimada));

        double precoKwh = 0.22;
        double poupancaMensal = producaoMensalEstimada * precoKwh;
        double anosPayback = custoEstimado / (poupancaMensal * 12);

        resultado.put("poupancaMensal", Math.round(poupancaMensal * 100.0) / 100.0);
        resultado.put("anosPayback", Math.round(anosPayback * 10.0) / 10.0);
        resultado.put("consumoMensalKwh", consumoMensalKwh);

        return resultado;
    }

    public ResultadoPaineisSolares calcularOrcamentoPaineisSolares(OrcamentoPaineisSolaresRequest request) {
        double area = parseDouble(request.getAreaDisponivel());
        double consumoMensal = parseDouble(request.getConsumoMensal());
        double panelFactor = getPanelFactor(request.getTipoPainel());
        double roofFactor = getRoofFactor(request.getTipoTelhado());

        double potenciaBase = area * 0.2;
        double potenciaEstimadaKwp = potenciaBase * panelFactor * roofFactor;
        int numeroPaineis = Math.max(1, (int) Math.round(area / 2.2));
        double investimentoEstimado = numeroPaineis * 420.0 * panelFactor;
        double consumoAnualKwh = consumoMensal > 0 ? consumoMensal * 12.0 : potenciaEstimadaKwp * 1200.0;
        double economiaAnualEstimada = consumoAnualKwh * 0.21 * 0.78;

        return new ResultadoPaineisSolares(
                round1(potenciaEstimadaKwp),
                numeroPaineis,
                round2(investimentoEstimado),
                round1(consumoAnualKwh),
                round2(economiaAnualEstimada));
    }

    // RF10: Orcamento de posto de carregamento.
    public PostosCarregamentoResult calcularOrcamentoPostos(PostosCarregamentoRequest request) {
        // Base por tipo de carregador
        double basePorCarregador;
        switch (request.getTipoCarregador().toLowerCase()) {
            case "22kw":
                basePorCarregador = 950;
                break;
            case "11kw":
                basePorCarregador = 750;
                break;
            default: // 7kw ou outros
                basePorCarregador = 550;
                break;
        }

        // Ajuste por tipo de instalação
        double instalacaoExtra;
        switch (request.getTipoInstalacao().toLowerCase()) {
            case "externa":
                instalacaoExtra = 180;
                break;
            case "interna":
                instalacaoExtra = 120;
                break;
            default:
                instalacaoExtra = 150;
                break;
        }

        // Ajuste por tipo de localização
        double multiplicadorLocalizacao;
        switch (request.getTipoLocalizacao().toLowerCase()) {
            case "empresa":
                multiplicadorLocalizacao = 1.15;
                break;
            case "condominio":
                multiplicadorLocalizacao = 1.25;
                break;
            default:
                multiplicadorLocalizacao = 1.0;
                break;
        }

        double custoUnidade = (basePorCarregador + instalacaoExtra) * multiplicadorLocalizacao;
        double custoTotal = custoUnidade * request.getQuantidade();

        // Desconto de volume
        double desconto = 0;
        if (request.getQuantidade() >= 5) {
            desconto = custoTotal * 0.08;
        } else if (request.getQuantidade() >= 3) {
            desconto = custoTotal * 0.05;
        }

        double custoComDesconto = custoTotal - desconto;

        return new PostosCarregamentoResult(
                Math.round(custoComDesconto * 100.0) / 100.0,
                Math.round(custoUnidade * 100.0) / 100.0,
                Math.round(instalacaoExtra * 100.0) / 100.0,
                Math.round(desconto * 100.0) / 100.0);
    }

    public static class ResultadoSolar {
        private final double potenciaNecessariaKw;
        private final double consumoAnualKwh;
        private final double custoAnualAtual;
        private final double economiaAnualEstimada;
        private final double economiaPercent;
        private final double paybackAnos;
        private final double economiaTotal10Anos;
        private final double poupancaMensal;
        private final double poupancaAnual;

        public ResultadoSolar(double potenciaNecessariaKw, double consumoAnualKwh,
                double custoAnualAtual, double economiaAnualEstimada, double economiaPercent,
                double paybackAnos, double economiaTotal10Anos, double poupancaMensal,
                double poupancaAnual) {
            this.potenciaNecessariaKw = potenciaNecessariaKw;
            this.consumoAnualKwh = consumoAnualKwh;
            this.custoAnualAtual = custoAnualAtual;
            this.economiaAnualEstimada = economiaAnualEstimada;
            this.economiaPercent = economiaPercent;
            this.paybackAnos = paybackAnos;
            this.economiaTotal10Anos = economiaTotal10Anos;
            this.poupancaMensal = poupancaMensal;
            this.poupancaAnual = poupancaAnual;
        }

        public double getPotenciaNecessariaKw() {
            return potenciaNecessariaKw;
        }

        public double getConsumoAnualKwh() {
            return consumoAnualKwh;
        }

        public double getCustoAnualAtual() {
            return custoAnualAtual;
        }

        public double getEconomiaAnualEstimada() {
            return economiaAnualEstimada;
        }

        public double getEconomiaPercent() {
            return economiaPercent;
        }

        public double getPaybackAnos() {
            return paybackAnos;
        }

        public double getEconomiaTotal10Anos() {
            return economiaTotal10Anos;
        }

        public double getPoupancaMensal() {
            return poupancaMensal;
        }

        public double getPoupancaAnual() {
            return poupancaAnual;
        }
    }

    public static class ResultadoPaineisSolares {
        private final double potenciaEstimadaKwp;
        private final int numeroPaineis;
        private final double investimentoEstimado;
        private final double consumoAnualKwh;
        private final double economiaAnualEstimada;

        public ResultadoPaineisSolares(double potenciaEstimadaKwp, int numeroPaineis,
                double investimentoEstimado, double consumoAnualKwh, double economiaAnualEstimada) {
            this.potenciaEstimadaKwp = potenciaEstimadaKwp;
            this.numeroPaineis = numeroPaineis;
            this.investimentoEstimado = investimentoEstimado;
            this.consumoAnualKwh = consumoAnualKwh;
            this.economiaAnualEstimada = economiaAnualEstimada;
        }

        public double getPotenciaEstimadaKwp() {
            return potenciaEstimadaKwp;
        }

        public int getNumeroPaineis() {
            return numeroPaineis;
        }

        public double getInvestimentoEstimado() {
            return investimentoEstimado;
        }

        public double getConsumoAnualKwh() {
            return consumoAnualKwh;
        }

        public double getEconomiaAnualEstimada() {
            return economiaAnualEstimada;
        }
    }

    private double getPanelFactor(String tipoPainel) {
        if ("Monocristalino".equals(tipoPainel)) {
            return 1.15;
        }
        if ("Alta Eficiência".equals(tipoPainel)) {
            return 1.28;
        }
        if ("Com Bateria".equals(tipoPainel)) {
            return 1.55;
        }
        return 1.0;
    }

    private double getRoofFactor(String tipoTelhado) {
        if ("Metalico".equals(tipoTelhado)) {
            return 0.96;
        }
        if ("Plano".equals(tipoTelhado)) {
            return 1.08;
        }
        if ("Terreno".equals(tipoTelhado)) {
            return 1.12;
        }
        return 1.0;
    }

    private double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return 0.0;
        }

        try {
            return Double.parseDouble(value.replace(',', '.').trim());
        } catch (NumberFormatException ex) {
            return 0.0;
        }
    }

    private double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
