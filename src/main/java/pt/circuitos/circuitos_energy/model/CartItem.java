package pt.circuitos.circuitos_energy.model;

import java.math.BigDecimal;

public class CartItem {

    private Long servicoId;
    private String titulo;
    private int quantidade;
    private BigDecimal precoUnitario = BigDecimal.ZERO;

    public CartItem() {
    }

    public CartItem(Long servicoId, String titulo, int quantidade, BigDecimal precoUnitario) {
        this.servicoId = servicoId;
        this.titulo = titulo;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario != null ? precoUnitario : BigDecimal.ZERO;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario != null ? precoUnitario : BigDecimal.ZERO;
    }

    public void incrementar(int delta) {
        this.quantidade += delta;
        if (this.quantidade < 0) {
            this.quantidade = 0;
        }
    }

    public BigDecimal getSubtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
