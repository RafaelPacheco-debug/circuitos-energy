package pt.circuitos.circuitos_energy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.Valid;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import pt.circuitos.circuitos_energy.model.AgendamentoInstalacaoRequest;
import pt.circuitos.circuitos_energy.model.AdminAgendamentoItem;
import pt.circuitos.circuitos_energy.model.AgendamentoReuniaoRequest;
import pt.circuitos.circuitos_energy.model.AgendamentoRequest;
import pt.circuitos.circuitos_energy.model.CalculoSolarRequest;
import pt.circuitos.circuitos_energy.model.CalculoSolarSubmissao;
import pt.circuitos.circuitos_energy.model.ContactMessage;
import pt.circuitos.circuitos_energy.model.FerramentaUtilizadorItem;
import pt.circuitos.circuitos_energy.model.OrcamentoImpressao;
import pt.circuitos.circuitos_energy.model.OrcamentoPaineisSolaresRequest;
import pt.circuitos.circuitos_energy.model.OrcamentoPaineisSolaresSubmissao;
import pt.circuitos.circuitos_energy.model.PostosCarregamentoRequest;
import pt.circuitos.circuitos_energy.model.PostosCarregamentoResult;
import pt.circuitos.circuitos_energy.model.PostosCarregamentoSubmissao;
import pt.circuitos.circuitos_energy.model.Servico;
import pt.circuitos.circuitos_energy.repository.ContactMessageRepository;
import pt.circuitos.circuitos_energy.repository.ServicoRepository;
import pt.circuitos.circuitos_energy.service.AgendamentoInstalacaoService;
import pt.circuitos.circuitos_energy.service.AgendamentoManutencaoService;
import pt.circuitos.circuitos_energy.service.AgendamentoReuniaoService;
import pt.circuitos.circuitos_energy.service.EncomendaService;
import pt.circuitos.circuitos_energy.service.FerramentaUtilizadorService;
import pt.circuitos.circuitos_energy.service.OrcamentoImpressaoService;
import pt.circuitos.circuitos_energy.service.OrcamentoService;

@Controller
public class HomeController {

    private final ContactMessageRepository contactRepo;
    private final ServicoRepository servicoRepo;
    private final OrcamentoService orcamentoService;
    private final AgendamentoInstalacaoService instalacaoService;
    private final AgendamentoManutencaoService manutencaoService;
    private final AgendamentoReuniaoService reuniaoService;
    private final FerramentaUtilizadorService ferramentaUtilizadorService;
    private final EncomendaService encomendaService;
    private final OrcamentoImpressaoService orcamentoImpressaoService;

    private static final String UPLOAD_DIR = "uploads/";
    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");
    private static final Set<String> ALLOWED_IMAGE_CONTENT_TYPES = Set.of("image/jpeg", "image/png", "image/webp");
    private static final String UPLOAD_ERROR_MESSAGE =
            "Imagem invalida. Use apenas JPG, PNG ou WEBP ate 2MB.";
    private static final String SESSION_SOLAR_ORCAMENTO = "ultimoOrcamentoSolar";
    private static final String SESSION_POSTOS_ORCAMENTO = "ultimoOrcamentoPostos";
    private static final String SESSION_PAINEIS_ORCAMENTO = "ultimoOrcamentoPaineis";

    public HomeController(
            ContactMessageRepository contactRepo,
            ServicoRepository servicoRepo,
            OrcamentoService orcamentoService,
            AgendamentoInstalacaoService instalacaoService,
            AgendamentoManutencaoService manutencaoService,
            AgendamentoReuniaoService reuniaoService,
            FerramentaUtilizadorService ferramentaUtilizadorService,
            EncomendaService encomendaService,
            OrcamentoImpressaoService orcamentoImpressaoService) {
        this.contactRepo = contactRepo;
        this.servicoRepo = servicoRepo;
        this.orcamentoService = orcamentoService;
        this.instalacaoService = instalacaoService;
        this.manutencaoService = manutencaoService;
        this.reuniaoService = reuniaoService;
        this.ferramentaUtilizadorService = ferramentaUtilizadorService;
        this.encomendaService = encomendaService;
        this.orcamentoImpressaoService = orcamentoImpressaoService;
    }

    // -------- PÚBLICO --------

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    @GetMapping("/servicos")
    public String servicos(
            @RequestParam(value = "agendamento", required = false) String agendamento,
            Model model) {

        if ("instalacao".equals(agendamento)) {
            return "redirect:/agendamento/instalacao";
        }
        if ("manutencao".equals(agendamento)) {
            return "redirect:/agendamento/manutencao";
        }
        if ("reuniao".equals(agendamento)) {
            return "redirect:/agendamento/reuniao";
        }

        model.addAttribute("servicos", servicoRepo.findAllByOrderByIdDesc());
        model.addAttribute("agendamento", agendamento);
        model.addAttribute("agendamentoManutencao", new AgendamentoRequest());
        model.addAttribute("agendamentoInstalacao", new AgendamentoInstalacaoRequest());
        model.addAttribute("agendamentoReuniao", new AgendamentoReuniaoRequest());
        return "servicos";
    }

    @GetMapping("/servicos/{id}")
    public String detalheServico(@PathVariable Long id, Model model, HttpSession session) {
        session.getId();
        Optional<Servico> servicoOpt = servicoRepo.findById(id);
        if (servicoOpt.isEmpty()) {
            return "redirect:/servicos";
        }

        Servico servico = servicoOpt.get();
        model.addAttribute("servico", servico);
        model.addAttribute("precoProduto", (servico.getPreco() != null ? servico.getPreco() : "0.00") + " EUR");
        model.addAttribute("notaPreco",
                "Preco real configurado para este servico. Pode finalizar a compra no checkout.");
        model.addAttribute("estadoStock", "Em stock, envio em 24/48h");
        model.addAttribute("categoriaProduto", "servico");
        model.addAttribute("opcoesProduto", List.of(
                Map.of("titulo", "Opções", "valores", List.of("Padrão", "Personalizado"))));
        model.addAttribute("beneficiosProduto", List.of(
                "Solução adaptada à sua necessidade",
                "Atendimento técnico especializado",
                "Instalação confiável e suportada"));
        model.addAttribute("especificacoesProduto", List.of(
                Map.of("nome", "Tipo", "valor", "Serviço profissional"),
                Map.of("nome", "Duração típica", "valor", "1-3 dias úteis"),
                Map.of("nome", "Garantia", "valor", "12 meses")));
        model.addAttribute("utilizacoesProduto", List.of(
                "Residência",
                "Empresa",
                "Condomínio"));
        model.addAttribute("produtosRelacionados",
                servicoRepo.findAllByOrderByIdDesc().stream()
                        .filter(s -> !s.getId().equals(id))
                        .limit(3)
                        .toList());
        return "servico-detalhe";
    }

    @GetMapping("/agendamento/manutencao")
    public String agendarManutencao(Model model) {
        if (!model.containsAttribute("agendamento")) {
            model.addAttribute("agendamento", new AgendamentoRequest());
        }
        populateManutencaoFormOptions(model);
        return "agendamento-manutencao";
    }

    @PostMapping("/agendamento/manutencao")
    public String enviarAgendamentoManutencao(
            @Valid @ModelAttribute("agendamento") AgendamentoRequest agendamento,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            populateManutencaoFormOptions(model);
            model.addAttribute("erroSubmissao", "Verifique os campos assinalados e tente novamente.");
            return "agendamento-manutencao";
        }

        try {
            manutencaoService.guardar(agendamento);
            model.addAttribute("sucesso", true);
            model.addAttribute("agendamento", new AgendamentoRequest());
        } catch (RuntimeException ex) {
            model.addAttribute("erroSubmissao",
                    "Não foi possível confirmar o agendamento neste momento. Tente novamente dentro de instantes.");
            model.addAttribute("agendamento", agendamento);
        }

        populateManutencaoFormOptions(model);
        return "agendamento-manutencao";
    }

    @GetMapping("/agendamento/instalacao")
    public String agendarInstalacao(Model model) {
        if (!model.containsAttribute("agendamento")) {
            model.addAttribute("agendamento", new AgendamentoInstalacaoRequest());
        }
        model.addAttribute("tiposInstalacao",
                List.of("Carregador elétrico", "Painel solar", "Posto de carregamento", "Outro"));
        model.addAttribute("tiposPropriedade", List.of("Residencial", "Comercial", "Industrial", "Outro"));
        model.addAttribute("periodosPreferidos",
                List.of("Manhã (09:00 - 12:00)", "Tarde (13:00 - 17:00)", "Noite (18:00 - 20:00)"));
        return "agendamento-instalacao";
    }

    @PostMapping("/agendamento/instalacao")
    public String enviarAgendamentoInstalacao(
            @Valid @ModelAttribute("agendamento") AgendamentoInstalacaoRequest agendamento,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("tiposInstalacao",
                    List.of("Carregador elétrico", "Painel solar", "Posto de carregamento", "Outro"));
            model.addAttribute("tiposPropriedade", List.of("Residencial", "Comercial", "Industrial", "Outro"));
            model.addAttribute("periodosPreferidos",
                    List.of("Manhã (09:00 - 12:00)", "Tarde (13:00 - 17:00)", "Noite (18:00 - 20:00)"));
            return "agendamento-instalacao";
        }

        instalacaoService.guardar(agendamento);
        model.addAttribute("sucesso", true);
        model.addAttribute("agendamento", new AgendamentoInstalacaoRequest());
        model.addAttribute("tiposInstalacao",
                List.of("Carregador elétrico", "Painel solar", "Posto de carregamento", "Outro"));
        model.addAttribute("tiposPropriedade", List.of("Residencial", "Comercial", "Industrial", "Outro"));
        model.addAttribute("periodosPreferidos",
                List.of("Manhã (09:00 - 12:00)", "Tarde (13:00 - 17:00)", "Noite (18:00 - 20:00)"));
        return "agendamento-instalacao";
    }

    @GetMapping("/agendamento/reuniao")
    public String agendarReuniao(Model model) {
        if (!model.containsAttribute("agendamento")) {
            model.addAttribute("agendamento", new AgendamentoReuniaoRequest());
        }
        return "agendamento-reuniao";
    }

    private void populateManutencaoFormOptions(Model model) {
        model.addAttribute("tiposEquipamento", List.of(
                "Carregador elétrico",
                "Posto de carregamento",
                "Painel solar",
                "Bateria / armazenamento",
                "Outro"));
        model.addAttribute("horariosPreferidos", List.of(
                "08:00 - 10:00",
                "10:00 - 12:00",
                "14:00 - 16:00",
                "16:00 - 18:00"));
        model.addAttribute("tiposServicoManutencao", List.of(
                "Manutenção Preventiva",
                "Manutenção Corretiva",
                "Inspeção Técnica",
                "Diagnóstico / avaria"));
    }

    @PostMapping("/agendamento/reuniao")
    public String enviarAgendamentoReuniao(
            @Valid @ModelAttribute("agendamento") AgendamentoReuniaoRequest agendamento,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "agendamento-reuniao";
        }

        reuniaoService.guardar(agendamento);
        model.addAttribute("sucesso", true);
        model.addAttribute("agendamento", new AgendamentoReuniaoRequest());
        return "agendamento-reuniao";
    }

    @GetMapping("/contactos")
    public String contactos(Model model) {
        if (!model.containsAttribute("msg")) {
            model.addAttribute("msg", new ContactMessage());
        }
        return "contactos";
    }

    @PostMapping("/contactos")
    public String enviarContacto(
            @Valid @ModelAttribute("msg") ContactMessage msg,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "contactos";
        }

        contactRepo.save(msg);
        model.addAttribute("sucesso", true);
        model.addAttribute("msg", new ContactMessage());
        return "contactos";
    }

    @GetMapping("/orcamento/paineis-solares")
    public String orcamentoPaineisSolares(Model model, HttpSession session) {
        session.getId();
        model.addAttribute("orcamentoPaineisRequest", new OrcamentoPaineisSolaresRequest());
        return "orcamento-paineis-solares";
    }

    @PostMapping("/orcamento/paineis-solares/submeter")
    @ResponseBody
    public Map<String, Object> submeterOrcamentoPaineisSolares(
            @Valid @ModelAttribute OrcamentoPaineisSolaresRequest request,
            BindingResult result,
            HttpSession session) {

        if (result.hasErrors()) {
            String mensagem = result.getAllErrors().stream()
                    .findFirst()
                    .map(error -> error.getDefaultMessage())
                    .orElse("Nao foi possivel submeter o orcamento.");

            return Map.of(
                    "success", false,
                    "message", mensagem);
        }

        OrcamentoPaineisSolaresSubmissao submissao = ferramentaUtilizadorService.guardarOrcamentoPaineis(request);
        OrcamentoService.ResultadoPaineisSolares resultado =
                orcamentoService.calcularOrcamentoPaineisSolares(request);
        session.setAttribute(SESSION_PAINEIS_ORCAMENTO,
                new OrcamentoPaineisImpressao(request, resultado, LocalDateTime.now(), submissao.getId()));

        return Map.of(
                "success", true,
                "id", submissao.getId(),
                "message", "Pedido registado com sucesso.",
                "printUrl", "/orcamento/paineis-solares/orcamento");
    }

    @GetMapping("/orcamento/paineis-solares/orcamento")
    public String imprimirOrcamentoPaineisSolares(HttpSession session, Model model) {
        OrcamentoPaineisImpressao orcamento =
                (OrcamentoPaineisImpressao) session.getAttribute(SESSION_PAINEIS_ORCAMENTO);

        if (orcamento == null) {
            return "redirect:/orcamento/paineis-solares";
        }

        registarImpressaoPaineis(session, orcamento);
        model.addAttribute("orcamento", orcamento);
        return "orcamento-paineis-solares-imprimir";
    }

    // -------- FERRAMENTAS / SOLAR --------

    @GetMapping("/ferramentas/solar")
    public String paginaSolar(Model model, HttpSession session) {
        session.getId();
        CalculoSolarRequest request = new CalculoSolarRequest();
        request.setConsumoMensalKwh(300.0);
        request.setTarifaKwh(0.150);
        request.setHorasUsoDiario(8.0);
        request.setInvestimento(5000.0);

        model.addAttribute("calculoSolarRequest", request);
        model.addAttribute("consumoMensalKwh", request.getConsumoMensalKwh());
        model.addAttribute("tarifaKwh", request.getTarifaKwh());
        model.addAttribute("horasUsoDiario", request.getHorasUsoDiario());
        model.addAttribute("investimento", request.getInvestimento());
        model.addAttribute("resultado", null);
        return "ferramenta-solar";
    }

    @PostMapping("/ferramentas/calcular-solar")
    public String processarSolar(
            @Valid @ModelAttribute("calculoSolarRequest") CalculoSolarRequest request,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (result.hasErrors()) {
            model.addAttribute("resultado", null);
            model.addAttribute("consumoMensalKwh", request.getConsumoMensalKwh());
            model.addAttribute("tarifaKwh", request.getTarifaKwh());
            model.addAttribute("horasUsoDiario", request.getHorasUsoDiario());
            model.addAttribute("investimento", request.getInvestimento());
            return "ferramenta-solar";
        }

        OrcamentoService.ResultadoSolar resultado = orcamentoService.calcularOrcamentoSolar(
                request.getConsumoMensalKwh(),
                request.getTarifaKwh(),
                request.getHorasUsoDiario(),
                request.getInvestimento());

        CalculoSolarSubmissao submissao = ferramentaUtilizadorService.guardarCalculoSolar(request, resultado);
        session.setAttribute(SESSION_SOLAR_ORCAMENTO,
                new OrcamentoSolarImpressao(request, resultado, LocalDateTime.now(), submissao.getId()));

        model.addAttribute("resultado", resultado);
        model.addAttribute("consumoMensalKwh", request.getConsumoMensalKwh());
        model.addAttribute("tarifaKwh", request.getTarifaKwh());
        model.addAttribute("horasUsoDiario", request.getHorasUsoDiario());
        model.addAttribute("investimento", request.getInvestimento());

        return "ferramenta-solar";
    }

    @GetMapping("/ferramentas/solar/orcamento")
    public String imprimirOrcamentoSolar(HttpSession session, Model model) {
        OrcamentoSolarImpressao orcamento =
                (OrcamentoSolarImpressao) session.getAttribute(SESSION_SOLAR_ORCAMENTO);

        if (orcamento == null) {
            return "redirect:/ferramentas/solar";
        }

        registarImpressaoSolar(session, orcamento);
        model.addAttribute("orcamento", orcamento);
        return "orcamento-solar-imprimir";
    }

    // -------- FERRAMENTAS / POSTOS DE CARREGAMENTO --------

    @GetMapping("/ferramentas/postos")
    public String paginaPostos(Model model, HttpSession session) {
        session.getId();
        model.addAttribute("postosRequest", new PostosCarregamentoRequest());
        model.addAttribute("resultadoPostos", null);
        return "ferramenta-postos";
    }

    @PostMapping("/ferramentas/calcular-postos")
    public String calcularPostos(
            @Valid @ModelAttribute("postosRequest") PostosCarregamentoRequest request,
            BindingResult result,
            Model model,
            HttpSession session) {

        if (result.hasErrors()) {
            return "ferramenta-postos";
        }

        PostosCarregamentoResult resultCalc = orcamentoService.calcularOrcamentoPostos(request);
        PostosCarregamentoSubmissao submissao = ferramentaUtilizadorService.guardarPostos(request, resultCalc);
        session.setAttribute(SESSION_POSTOS_ORCAMENTO,
                new OrcamentoPostosImpressao(request, resultCalc, LocalDateTime.now(), submissao.getId()));
        model.addAttribute("resultadoPostos", resultCalc);
        return "ferramenta-postos";
    }

    @GetMapping("/ferramentas/postos/orcamento")
    public String imprimirOrcamentoPostos(HttpSession session, Model model) {
        OrcamentoPostosImpressao orcamento =
                (OrcamentoPostosImpressao) session.getAttribute(SESSION_POSTOS_ORCAMENTO);

        if (orcamento == null) {
            return "redirect:/ferramentas/postos";
        }

        registarImpressaoPostos(session, orcamento);
        model.addAttribute("orcamento", orcamento);
        return "orcamento-postos-imprimir";
    }

    // -------- ADMIN GERAL --------

    @GetMapping("/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("totalServicos", servicoRepo.count());
        model.addAttribute("servicosAtivos", servicoRepo.findAllByOrderByIdDesc().size());
        model.addAttribute("totalMensagens", contactRepo.count());
        model.addAttribute("totalAgendamentosInstalacao", instalacaoService.listarRecentes().size());
        model.addAttribute("totalAgendamentosManutencao", manutencaoService.listarRecentes().size());
        model.addAttribute("totalAgendamentosReuniao", reuniaoService.listarRecentes().size());
        model.addAttribute("totalEncomendas", encomendaService.listarRecentes().size());
        model.addAttribute("activeSection", "dashboard");
        model.addAttribute("conteudo", "admin-dashboard :: conteudo");
        return "admin-layout";
    }

    @GetMapping("/admin/orcamentos-impressos")
    public String adminOrcamentosImpressos(Model model) {
        List<OrcamentoImpressao> orcamentos = orcamentoImpressaoService.listarRecentes();

        model.addAttribute("orcamentos", orcamentos);
        model.addAttribute("totalOrcamentosImpressos", orcamentos.size());
        model.addAttribute("activeSection", "orcamentos-impressos");
        model.addAttribute("conteudo", "admin-orcamentos-impressos :: conteudo");
        return "admin-layout";
    }

    @GetMapping("/admin/contactos")
    public String adminContactos(Model model) {
        model.addAttribute("mensagens", contactRepo.findAll());
        model.addAttribute("activeSection", "contactos");
        model.addAttribute("conteudo", "admin-contactos :: conteudo");
        return "admin-layout";
    }

    @GetMapping("/admin/agendamentos")
    public String adminAgendamentos(
            @RequestParam(value = "tipo", defaultValue = "todos") String tipo,
            Model model) {

        List<AdminAgendamentoItem> reunioes = reuniaoService.listarRecentes().stream()
                .map(reuniao -> new AdminAgendamentoItem(
                        reuniao.getId(),
                        "Reunião",
                        reuniao.getNomeCompleto(),
                        reuniao.getEmail(),
                        reuniao.getTelefone(),
                        reuniao.getDataPreferida(),
                        reuniao.getHorarioPreferido(),
                        "Sem localização",
                        reuniao.getAssunto(),
                        reuniao.getObservacoes(),
                        reuniao.getCriadoEm(),
                        reuniao.getEstado()))
                .toList();

        List<AdminAgendamentoItem> manutencoes = manutencaoService.listarRecentes().stream()
                .map(manutencao -> new AdminAgendamentoItem(
                        manutencao.getId(),
                        "Manutenção",
                        manutencao.getNomeCompleto(),
                        manutencao.getEmail(),
                        manutencao.getTelefone(),
                        manutencao.getDataPreferida(),
                        manutencao.getHorarioPreferido(),
                        "Sem localização",
                        manutencao.getTipoEquipamento() + " · " + manutencao.getTipoServico(),
                        manutencao.getObservacoes(),
                        manutencao.getCriadoEm(),
                        manutencao.getEstado()))
                .toList();

        List<AdminAgendamentoItem> instalacoes = instalacaoService.listarRecentes().stream()
                .map(instalacao -> new AdminAgendamentoItem(
                        instalacao.getId(),
                        "Instalação",
                        instalacao.getNomeCompleto(),
                        instalacao.getEmail(),
                        instalacao.getTelefone(),
                        instalacao.getDataPreferida(),
                        instalacao.getPeriodoPreferido(),
                        instalacao.getMorada() + ", " + instalacao.getCidade() + " (" + instalacao.getCodigoPostal()
                                + ")",
                        instalacao.getTipoInstalacao() + " · " + instalacao.getTipoPropriedade(),
                        instalacao.getObservacoes(),
                        instalacao.getCriadoEm(),
                        instalacao.getEstado()))
                .toList();

        List<AdminAgendamentoItem> agendamentos = switch (tipo) {
            case "reuniao" -> reunioes;
            case "manutencao" -> manutencoes;
            case "instalacao" -> instalacoes;
            default -> {
                List<AdminAgendamentoItem> items = new ArrayList<>();
                items.addAll(reunioes);
                items.addAll(manutencoes);
                items.addAll(instalacoes);
                items.sort(Comparator.comparing(AdminAgendamentoItem::criadoEm).reversed());
                yield items;
            }
        };

        model.addAttribute("agendamentos", agendamentos);
        model.addAttribute("filtroTipo", tipo);
        model.addAttribute("totalAgendamentos", reunioes.size() + manutencoes.size() + instalacoes.size());
        model.addAttribute("totalReunioes", reunioes.size());
        model.addAttribute("totalManutencoes", manutencoes.size());
        model.addAttribute("totalInstalacoes", instalacoes.size());
        model.addAttribute("activeSection", "agendamentos");
        model.addAttribute("conteudo", "admin-agendamentos :: conteudo");
        return "admin-layout";
    }

    @GetMapping("/admin/ferramentas-utilizador")
    public String adminFerramentasUtilizador(
            @RequestParam(value = "tipo", defaultValue = "todos") String tipo,
            Model model) {

        List<FerramentaUtilizadorItem> calculosSolares = ferramentaUtilizadorService.listarCalculosSolares().stream()
                .map(item -> new FerramentaUtilizadorItem(
                        "calculo-solar",
                        item.getId(),
                        formatFullName(item.getFirstName(), item.getLastName()),
                        item.getEmail(),
                        item.getPhone(),
                        "Consumo: " + item.getConsumoMensalKwh() + " kWh/mes | Tarifa: " + item.getTarifaKwh()
                                + " EUR/kWh",
                        formatLocation(item.getAddress(), item.getCity(), item.getPostalCode()),
                        item.getEstado(),
                        item.getCriadoEm()))
                .toList();

        List<FerramentaUtilizadorItem> orcamentosPaineis = ferramentaUtilizadorService.listarOrcamentosPaineis()
                .stream()
                .map(item -> new FerramentaUtilizadorItem(
                        "orcamento-paineis",
                        item.getId(),
                        item.getNomeCompleto(),
                        item.getEmail(),
                        item.getTelefone(),
                        "Area: " + item.getAreaDisponivel() + " | Painel: " + item.getTipoPainel() + " | Telhado: "
                                + item.getTipoTelhado(),
                        formatLocation(item.getProjectAddress(), item.getProjectCity(), item.getProjectPostalCode()),
                        item.getEstado(),
                        item.getCriadoEm()))
                .toList();

        List<FerramentaUtilizadorItem> orcamentosPostos = ferramentaUtilizadorService.listarOrcamentosPostos().stream()
                .map(item -> new FerramentaUtilizadorItem(
                        "orcamento-postos",
                        item.getId(),
                        item.getNomeCompleto(),
                        item.getEmail(),
                        item.getTelefone(),
                        "Quantidade: " + item.getQuantidade() + " | Carregador: " + item.getTipoCarregador()
                                + " | Total: " + item.getCustoTotalEstimado() + " EUR",
                        formatLocation(item.getWizardAddress(), item.getWizardCity(), item.getWizardPostalCode()),
                        item.getEstado(),
                        item.getCriadoEm()))
                .toList();

        List<FerramentaUtilizadorItem> registos = switch (tipo) {
            case "calculo-solar" -> calculosSolares;
            case "orcamento-paineis" -> orcamentosPaineis;
            case "orcamento-postos" -> orcamentosPostos;
            default -> {
                List<FerramentaUtilizadorItem> items = new ArrayList<>();
                items.addAll(calculosSolares);
                items.addAll(orcamentosPaineis);
                items.addAll(orcamentosPostos);
                items.sort(Comparator.comparing(FerramentaUtilizadorItem::criadoEm).reversed());
                yield items;
            }
        };

        model.addAttribute("registos", registos);
        model.addAttribute("filtroTipo", tipo);
        model.addAttribute("totalFerramentas",
                calculosSolares.size() + orcamentosPaineis.size() + orcamentosPostos.size());
        model.addAttribute("totalCalculosSolares", calculosSolares.size());
        model.addAttribute("totalOrcamentosPaineis", orcamentosPaineis.size());
        model.addAttribute("totalOrcamentosPostos", orcamentosPostos.size());
        model.addAttribute("activeSection", "ferramentas-utilizador");
        model.addAttribute("conteudo", "admin-ferramentas-utilizador :: conteudo");
        return "admin-layout";
    }

    @GetMapping("/admin/ferramentas-utilizador/{tipo}/{id}")
    public String adminFerramentaUtilizadorDetalhe(
            @PathVariable String tipo,
            @PathVariable Long id,
            Model model) {

        switch (tipo) {
            case "calculo-solar" -> {
                CalculoSolarSubmissao registo = ferramentaUtilizadorService.obterCalculoSolar(id);
                if (registo == null) {
                    return "redirect:/admin/ferramentas-utilizador";
                }
                model.addAttribute("tituloDetalhe", "Detalhe do Calculo Solar");
                model.addAttribute("tipoFerramenta", tipo);
                model.addAttribute("registo", registo);
            }
            case "orcamento-paineis" -> {
                OrcamentoPaineisSolaresSubmissao registo = ferramentaUtilizadorService.obterOrcamentoPaineis(id);
                if (registo == null) {
                    return "redirect:/admin/ferramentas-utilizador";
                }
                model.addAttribute("tituloDetalhe", "Detalhe do Orcamento de Paineis Solares");
                model.addAttribute("tipoFerramenta", tipo);
                model.addAttribute("registo", registo);
            }
            case "orcamento-postos" -> {
                PostosCarregamentoSubmissao registo = ferramentaUtilizadorService.obterOrcamentoPostos(id);
                if (registo == null) {
                    return "redirect:/admin/ferramentas-utilizador";
                }
                model.addAttribute("tituloDetalhe", "Detalhe do Orcamento de Postos");
                model.addAttribute("tipoFerramenta", tipo);
                model.addAttribute("registo", registo);
            }
            default -> {
                return "redirect:/admin/ferramentas-utilizador";
            }
        }

        model.addAttribute("activeSection", "ferramentas-utilizador");
        model.addAttribute("conteudo", "admin-ferramenta-utilizador-detalhe :: conteudo");
        return "admin-layout";
    }

    // -------- ADMIN SERVIÇOS --------

    @GetMapping("/admin/servicos")
    public String adminServicos(Model model) {
        model.addAttribute("servicos", servicoRepo.findAllByOrderByIdDesc());
        model.addAttribute("activeSection", "servicos");
        model.addAttribute("conteudo", "admin-servicos :: conteudo");
        return "admin-layout";
    }

    @GetMapping("/admin/servicos/novo")
    public String novoServico(Model model) {
        model.addAttribute("servico", new Servico());
        model.addAttribute("activeSection", "servicos");
        model.addAttribute("conteudo", "admin-servico-form :: conteudo");
        return "admin-layout";
    }

    @PostMapping("/admin/servicos/novo")
    public String guardarNovoServico(
            @Valid @ModelAttribute("servico") Servico servico,
            BindingResult result,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("activeSection", "servicos");
            model.addAttribute("conteudo", "admin-servico-form :: conteudo");
            return "admin-layout";
        }

        try {
            handleFileUpload(servico, file);
        } catch (UploadValidationException ex) {
            return showServicoFormUploadError(servico, ex.getMessage(), model);
        }

        servicoRepo.save(servico);
        return "redirect:/admin/servicos";
    }

    @GetMapping("/admin/servicos/editar/{id}")
    public String editarServico(@PathVariable Long id, Model model) {
        Optional<Servico> servicoOpt = servicoRepo.findById(id);
        if (servicoOpt.isEmpty()) {
            return "redirect:/admin/servicos";
        }

        model.addAttribute("servico", servicoOpt.get());
        model.addAttribute("activeSection", "servicos");
        model.addAttribute("conteudo", "admin-servico-form :: conteudo");
        return "admin-layout";
    }

    @PostMapping("/admin/servicos/editar/{id}")
    public String guardarEdicaoServico(
            @PathVariable Long id,
            @Valid @ModelAttribute("servico") Servico servico,
            BindingResult result,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("activeSection", "servicos");
            model.addAttribute("conteudo", "admin-servico-form :: conteudo");
            return "admin-layout";
        }

        Servico servicoBase = servicoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        if (file == null || file.isEmpty()) {
            servico.setImagem(servicoBase.getImagem());
        } else {
            try {
                handleFileUpload(servico, file);
            } catch (UploadValidationException ex) {
                servico.setId(id);
                servico.setImagem(servicoBase.getImagem());
                return showServicoFormUploadError(servico, ex.getMessage(), model);
            }
        }

        servico.setId(id);
        servicoRepo.save(servico);
        return "redirect:/admin/servicos";
    }

    @PostMapping("/admin/servicos/apagar/{id}")
    public String apagarServico(@PathVariable Long id) throws IOException {
        Optional<Servico> servicoOpt = servicoRepo.findById(id);
        if (servicoOpt.isPresent()) {
            Servico s = servicoOpt.get();
            if (s.getImagem() != null) {
                deleteUploadedFile(s.getImagem());
            }
            servicoRepo.deleteById(id);
        }
        return "redirect:/admin/servicos";
    }

    @PostMapping("/admin/servicos/toggle/{id}")
    public String toggle(@PathVariable Long id) {
        servicoRepo.findById(id).ifPresent(s -> {
            s.setAtivo(!s.isAtivo());
            servicoRepo.save(s);
        });
        return "redirect:/admin/servicos";
    }

    // -------- AUXILIAR UPLOAD --------

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceeded(Model model) {
        return showServicoFormUploadError(new Servico(), UPLOAD_ERROR_MESSAGE, model);
    }

    private String formatLocation(String address, String city, String postalCode) {
        List<String> parts = new ArrayList<>();

        if (address != null && !address.isBlank()) {
            parts.add(address);
        }
        if (city != null && !city.isBlank()) {
            parts.add(city);
        }
        if (postalCode != null && !postalCode.isBlank()) {
            parts.add(postalCode);
        }

        return parts.isEmpty() ? "Sem localizacao" : String.join(", ", parts);
    }

    private String formatFullName(String firstName, String lastName) {
        List<String> parts = new ArrayList<>();

        if (firstName != null && !firstName.isBlank()) {
            parts.add(firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            parts.add(lastName);
        }

        return parts.isEmpty() ? "Sem nome" : String.join(" ", parts);
    }

    private void handleFileUpload(Servico servico, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return;
        }

        String extension = getAllowedExtension(file.getOriginalFilename());
        validateContentType(file.getContentType());

        Path uploadPath = getUploadPath();
        Files.createDirectories(uploadPath);

        String nomeFinal = UUID.randomUUID() + "." + extension;
        Path path = uploadPath.resolve(nomeFinal).normalize();
        ensureInsideUploadDirectory(uploadPath, path);

        file.transferTo(path.toFile());
        servico.setImagem(nomeFinal);
    }

    private Path getUploadPath() {
        return Paths.get(UPLOAD_DIR).toAbsolutePath().normalize();
    }

    private void ensureInsideUploadDirectory(Path uploadPath, Path targetPath) {
        if (!targetPath.startsWith(uploadPath)) {
            throw new UploadValidationException(UPLOAD_ERROR_MESSAGE);
        }
    }

    private String getAllowedExtension(String originalFilename) {
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new UploadValidationException(UPLOAD_ERROR_MESSAGE);
        }

        String filename = originalFilename.replace('\\', '/');
        int slashIndex = filename.lastIndexOf('/');
        if (slashIndex >= 0) {
            filename = filename.substring(slashIndex + 1);
        }

        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == filename.length() - 1) {
            throw new UploadValidationException(UPLOAD_ERROR_MESSAGE);
        }

        String extension = filename.substring(dotIndex + 1).toLowerCase(Locale.ROOT);
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            throw new UploadValidationException(UPLOAD_ERROR_MESSAGE);
        }

        return extension;
    }

    private void validateContentType(String contentType) {
        if (contentType == null || !ALLOWED_IMAGE_CONTENT_TYPES.contains(contentType.toLowerCase(Locale.ROOT))) {
            throw new UploadValidationException(UPLOAD_ERROR_MESSAGE);
        }
    }

    private void deleteUploadedFile(String imageName) throws IOException {
        if (imageName == null || imageName.isBlank()) {
            return;
        }

        Path uploadPath = getUploadPath();
        Path imagePath = uploadPath.resolve(imageName).normalize();
        if (!imagePath.startsWith(uploadPath)) {
            return;
        }

        Files.deleteIfExists(imagePath);
    }

    private String showServicoFormUploadError(Servico servico, String message, Model model) {
        model.addAttribute("servico", servico);
        model.addAttribute("uploadError", message);
        model.addAttribute("activeSection", "servicos");
        model.addAttribute("conteudo", "admin-servico-form :: conteudo");
        return "admin-layout";
    }

    private void registarImpressaoSolar(HttpSession session, OrcamentoSolarImpressao orcamento) {
        CalculoSolarRequest dados = orcamento.dados();
        OrcamentoService.ResultadoSolar resultado = orcamento.resultado();
        String nome = formatFullName(dados.getName(), dados.getSurname());
        String resumo = "Consumo mensal: " + dados.getConsumoMensalKwh()
                + " kWh | Potencia: " + resultado.getPotenciaNecessariaKw()
                + " kW | Payback: " + resultado.getPaybackAnos() + " anos";
        String valorPrincipal = "Economia anual: " + resultado.getEconomiaAnualEstimada() + " EUR";

        orcamentoImpressaoService.registarAbertura(
                session,
                "calculo-solar",
                nome,
                dados.getEmail(),
                dados.getPhone(),
                resumo,
                valorPrincipal,
                orcamento.referenciaSubmissaoId());
    }

    private void registarImpressaoPostos(HttpSession session, OrcamentoPostosImpressao orcamento) {
        PostosCarregamentoRequest dados = orcamento.dados();
        PostosCarregamentoResult resultado = orcamento.resultado();
        String resumo = "Quantidade: " + dados.getQuantidade()
                + " | Carregador: " + dados.getTipoCarregador()
                + " | Localizacao: " + dados.getTipoLocalizacao();
        String valorPrincipal = "Total estimado: " + resultado.getCustoTotalEstimado() + " EUR";

        orcamentoImpressaoService.registarAbertura(
                session,
                "postos-carregamento",
                dados.getNomeCompleto(),
                dados.getEmail(),
                dados.getTelefone(),
                resumo,
                valorPrincipal,
                orcamento.referenciaSubmissaoId());
    }

    private void registarImpressaoPaineis(HttpSession session, OrcamentoPaineisImpressao orcamento) {
        OrcamentoPaineisSolaresRequest dados = orcamento.dados();
        OrcamentoService.ResultadoPaineisSolares resultado = orcamento.resultado();
        String resumo = "Area: " + dados.getAreaDisponivel()
                + " m2 | Painel: " + dados.getTipoPainel()
                + " | Potencia: " + resultado.getPotenciaEstimadaKwp() + " kWp";
        String valorPrincipal = "Investimento estimado: " + resultado.getInvestimentoEstimado() + " EUR";

        orcamentoImpressaoService.registarAbertura(
                session,
                "paineis-solares",
                dados.getNomeCompleto(),
                dados.getEmail(),
                dados.getTelefone(),
                resumo,
                valorPrincipal,
                orcamento.referenciaSubmissaoId());
    }

    private static class UploadValidationException extends RuntimeException {
        UploadValidationException(String message) {
            super(message);
        }
    }

    public record OrcamentoSolarImpressao(
            CalculoSolarRequest dados,
            OrcamentoService.ResultadoSolar resultado,
            LocalDateTime data,
            Long referenciaSubmissaoId) {
    }

    public record OrcamentoPostosImpressao(
            PostosCarregamentoRequest dados,
            PostosCarregamentoResult resultado,
            LocalDateTime data,
            Long referenciaSubmissaoId) {
    }

    public record OrcamentoPaineisImpressao(
            OrcamentoPaineisSolaresRequest dados,
            OrcamentoService.ResultadoPaineisSolares resultado,
            LocalDateTime data,
            Long referenciaSubmissaoId) {
    }
}
