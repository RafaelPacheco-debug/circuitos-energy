package pt.circuitos.circuitos_energy.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdminAgendamentoItem(
                Long id,
                String tipoPedido,
                String nome,
                String email,
                String telefone,
                LocalDate dataEscolhida,
                String horarioOuPeriodo,
                String localizacao,
                String detalhePedido,
                String observacoes,
                LocalDateTime criadoEm,
                String estado) {
}
