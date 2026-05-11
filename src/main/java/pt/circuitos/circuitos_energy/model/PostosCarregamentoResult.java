package pt.circuitos.circuitos_energy.model;

public class PostosCarregamentoResult {

    private final double custoTotalEstimado;
    private final double custoPorUnidade;
    private final double instalacao;
    private final double desconto;

    public PostosCarregamentoResult(double custoTotalEstimado, double custoPorUnidade, double instalacao,
            double desconto) {
        this.custoTotalEstimado = custoTotalEstimado;
        this.custoPorUnidade = custoPorUnidade;
        this.instalacao = instalacao;
        this.desconto = desconto;
    }

    public double getCustoTotalEstimado() {
        return custoTotalEstimado;
    }

    public double getCustoPorUnidade() {
        return custoPorUnidade;
    }

    public double getInstalacao() {
        return instalacao;
    }

    public double getDesconto() {
        return desconto;
    }
}
