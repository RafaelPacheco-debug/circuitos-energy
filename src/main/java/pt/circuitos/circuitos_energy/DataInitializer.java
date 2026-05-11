package pt.circuitos.circuitos_energy;

import java.math.BigDecimal;
import java.text.Normalizer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import pt.circuitos.circuitos_energy.model.Servico;
import pt.circuitos.circuitos_energy.repository.ServicoRepository;
import pt.circuitos.circuitos_energy.service.AppUserService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ServicoRepository servicoRepo;
    private final AppUserService userService;
    private final boolean seedDefaultUsers;

    public DataInitializer(
            ServicoRepository servicoRepo,
            AppUserService userService,
            @Value("${app.seed-default-users:false}") boolean seedDefaultUsers) {
        this.servicoRepo = servicoRepo;
        this.userService = userService;
        this.seedDefaultUsers = seedDefaultUsers;
    }

    @Override
    public void run(String... args) {
        createServicoIfMissing(
                "Agendamento de Manutencao",
                "Agende uma visita para manutencao preventiva ou corretiva dos seus sistemas eletricos e solares.",
                new BigDecimal("89.90"));

        createServicoIfMissing(
                "Agendamento de Reuniao",
                "Marque uma reuniao para planear o seu projeto, esclarecer duvidas ou apresentar propostas tecnicas.",
                new BigDecimal("39.90"));

        createServicoIfMissing(
                "Agendamento de Instalacao",
                "Solicite a instalacao de paineis solares, carregadores eletricos ou outros equipamentos de eficiencia energetica.",
                new BigDecimal("149.90"));

        if (seedDefaultUsers) {
            createDefaultUsers();
        }
    }

    private void createServicoIfMissing(String titulo, String descricao, BigDecimal preco) {
        boolean exists = servicoRepo.findAll().stream()
                .anyMatch(s -> normalize(s.getTitulo()).equals(normalize(titulo)));
        if (!exists) {
            Servico servico = new Servico();
            servico.setTitulo(titulo);
            servico.setDescricao(descricao);
            servico.setPreco(preco);
            servico.setAtivo(true);
            servicoRepo.save(servico);
        }
    }

    private String normalize(String value) {
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
    }

    private void createDefaultUsers() {
        userService.findByUsername("Rafael").orElseGet(
                () -> userService.register("Rafael", "1234", "Administrador", "admin@circuitos.pt", "ADMIN"));
        userService.findByUsername("cliente")
                .orElseGet(() -> userService.register("cliente", "1234", "Cliente", "cliente@circuitos.pt", "USER"));
    }
}
