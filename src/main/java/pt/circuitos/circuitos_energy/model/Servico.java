package pt.circuitos.circuitos_energy.model;

import java.text.Normalizer;
import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 800)
    @Column(nullable = false, length = 800)
    private String descricao;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(length = 255)
    private String imagem;

    @DecimalMin(value = "0.0", inclusive = true, message = "Preco invalido")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco = BigDecimal.ZERO;

    // -------- getters / setters --------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    /**
     * A simple stable slug for use in anchors/URLs (e.g.
     * "agendamento-de-manutencao").
     * This is not persisted, but is useful for building predictable fragment links.
     */
    public String getSlug() {
        if (titulo == null) {
            return "";
        }

        String normalized = Normalizer.normalize(titulo, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        return withoutAccents.toLowerCase()
                .replaceAll("[\\s]+", "-")
                .replaceAll("[^a-z0-9-]", "");
    }
}
