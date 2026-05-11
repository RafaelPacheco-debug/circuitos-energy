package pt.circuitos.circuitos_energy.model;

import java.time.LocalDateTime;

public record FerramentaUtilizadorItem(
                String tipoFerramenta,
                Long id,
                String nome,
                String email,
                String telefone,
                String resumo,
                String localizacao,
                String estado,
                LocalDateTime criadoEm) {
}
