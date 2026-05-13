package pt.circuitos.circuitos_energy;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import pt.circuitos.circuitos_energy.model.AppUser;
import pt.circuitos.circuitos_energy.model.Servico;
import pt.circuitos.circuitos_energy.repository.ServicoRepository;
import pt.circuitos.circuitos_energy.service.AppUserService;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final ServicoRepository servicoRepo;
    private final AppUserService userService;
    private final boolean seedDefaultUsers;
    private final boolean bootstrapAdmin;
    private final String adminUsername;
    private final String adminPassword;
    private final String adminFullName;
    private final String adminEmail;

    public DataInitializer(
            ServicoRepository servicoRepo,
            AppUserService userService,
            @Value("${app.seed-default-users:false}") boolean seedDefaultUsers,
            @Value("${app.admin.bootstrap.enabled:false}") boolean bootstrapAdmin,
            @Value("${app.admin.username:admin}") String adminUsername,
            @Value("${app.admin.password:}") String adminPassword,
            @Value("${app.admin.full-name:Administrador}") String adminFullName,
            @Value("${app.admin.email:admin@circuitos.pt}") String adminEmail) {
        this.servicoRepo = servicoRepo;
        this.userService = userService;
        this.seedDefaultUsers = seedDefaultUsers;
        this.bootstrapAdmin = bootstrapAdmin;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
        this.adminFullName = adminFullName;
        this.adminEmail = adminEmail;
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

        if (bootstrapAdmin) {
            bootstrapAdminUser();
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
        userService.findByUsernameIgnoreCase("Rafael")
                .or(() -> userService.findByEmailIgnoreCase("admin@circuitos.pt"))
                .orElseGet(() -> userService.register("Rafael", "1234", "Administrador",
                        "admin@circuitos.pt", "ADMIN"));
        userService.findByUsernameIgnoreCase("cliente")
                .or(() -> userService.findByEmailIgnoreCase("cliente@circuitos.pt"))
                .orElseGet(() -> userService.register("cliente", "1234", "Cliente", "cliente@circuitos.pt", "USER"));
    }

    private void bootstrapAdminUser() {
        log.info("Admin bootstrap iniciado");

        if (adminPassword == null || adminPassword.isBlank()) {
            log.warn("Admin bootstrap ignorado: password vazia para username={} email={}", adminUsername, adminEmail);
            return;
        }

        Optional<AppUser> userWithAdminEmail = userService.findByEmailIgnoreCase(adminEmail);
        Optional<AppUser> userWithAdminUsername = userService.findByUsernameIgnoreCase(adminUsername);

        userWithAdminEmail.ifPresent(user -> log.info("Admin encontrado por email/username: criterio=email, {}",
                adminSummary(user)));
        userWithAdminUsername.ifPresent(user -> log.info("Admin encontrado por email/username: criterio=username, {}",
                adminSummary(user)));

        if (userWithAdminEmail.isPresent()) {
            AppUser adminUser = userWithAdminEmail.get();
            userWithAdminUsername
                    .filter(user -> !sameUser(adminUser, user))
                    .ifPresent(this::releaseReservedAdminUsername);

            AppUser updatedAdmin = updateAdminUser(adminUser);
            log.info("Admin criado/atualizado: acao=atualizado, {}", adminSummary(updatedAdmin));
            return;
        }

        if (userWithAdminUsername.isPresent()) {
            AppUser updatedAdmin = updateAdminUser(userWithAdminUsername.get());
            log.info("Admin criado/atualizado: acao=atualizado, {}", adminSummary(updatedAdmin));
            return;
        }

        AppUser createdAdmin = userService.register(adminUsername, adminPassword, adminFullName, adminEmail, "ADMIN");
        log.info("Admin criado/atualizado: acao=criado, {}", adminSummary(createdAdmin));
    }

    private AppUser updateAdminUser(AppUser user) {
        user.setUsername(adminUsername);
        user.setEmail(adminEmail);
        user.setFullName(adminFullName);
        user.setPassword(userService.encodePassword(adminPassword));
        user.setRoles("ADMIN");
        user.setEnabled(true);
        return userService.save(user);
    }

    private void releaseReservedAdminUsername(AppUser user) {
        String oldUsername = user.getUsername();
        user.setUsername(oldUsername + "-legacy-" + user.getId());
        AppUser updatedUser = userService.save(user);
        log.warn("Username admin libertado de utilizador antigo: id={}, username_anterior={}, username_atual={}, email={}, role={}, enabled={}",
                updatedUser.getId(), oldUsername, updatedUser.getUsername(), updatedUser.getEmail(),
                updatedUser.getRoles(), updatedUser.isEnabled());
    }

    private boolean sameUser(AppUser first, AppUser second) {
        return first.getId() != null && first.getId().equals(second.getId());
    }

    private String adminSummary(AppUser user) {
        return "id=" + user.getId()
                + ", username=" + user.getUsername()
                + ", email=" + user.getEmail()
                + ", role=" + user.getRoles()
                + ", enabled=" + user.isEnabled();
    }
}
