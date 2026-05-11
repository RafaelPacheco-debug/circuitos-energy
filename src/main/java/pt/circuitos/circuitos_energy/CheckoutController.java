package pt.circuitos.circuitos_energy;

import java.security.Principal;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.circuitos.circuitos_energy.model.AppUser;
import pt.circuitos.circuitos_energy.model.Cart;
import pt.circuitos.circuitos_energy.model.CheckoutRequest;
import pt.circuitos.circuitos_energy.model.Encomenda;
import pt.circuitos.circuitos_energy.service.AppUserService;
import pt.circuitos.circuitos_energy.service.EncomendaService;

@Controller
public class CheckoutController {

    private final EncomendaService encomendaService;
    private final AppUserService appUserService;

    public CheckoutController(EncomendaService encomendaService, AppUserService appUserService) {
        this.encomendaService = encomendaService;
        this.appUserService = appUserService;
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Principal principal, Model model) {
        Cart cart = getCart(session);
        if (cart == null || cart.isEmpty()) {
            return "redirect:/carrinho";
        }

        if (!model.containsAttribute("checkout")) {
            CheckoutRequest request = new CheckoutRequest();
            AppUser user = getCurrentUser(principal);
            if (user != null) {
                request.setNomeCompleto(user.getFullName());
                request.setEmail(user.getEmail());
            }
            request.setPais("Portugal");
            model.addAttribute("checkout", request);
        }

        model.addAttribute("cart", cart);
        model.addAttribute("metodosPagamento", getMetodosPagamento());
        return "checkout";
    }

    @PostMapping("/checkout")
    public String finalizarCompra(
            @Valid @ModelAttribute("checkout") CheckoutRequest checkout,
            BindingResult result,
            HttpSession session,
            Principal principal,
            Model model) {

        Cart cart = getCart(session);
        if (cart == null || cart.isEmpty()) {
            return "redirect:/carrinho";
        }

        if (result.hasErrors()) {
            model.addAttribute("cart", cart);
            model.addAttribute("metodosPagamento", getMetodosPagamento());
            model.addAttribute("erroCheckout", "Verifique os dados do checkout e tente novamente.");
            return "checkout";
        }

        encomendaService.criarEncomenda(cart, checkout, getCurrentUser(principal));
        cart.clear();
        session.setAttribute(CartController.SESSION_CART, cart);

        return "redirect:/checkout/sucesso";
    }

    @GetMapping("/checkout/sucesso")
    public String checkoutSucesso() {
        return "checkout-sucesso";
    }

    @GetMapping("/admin/encomendas")
    public String adminEncomendas(
            @RequestParam(value = "estado", defaultValue = "todas") String estado,
            Model model) {

        List<Encomenda> encomendas = encomendaService.listarRecentes().stream()
                .filter(encomenda -> "todas".equals(estado) || encomenda.getEstado().equalsIgnoreCase(estado))
                .toList();

        model.addAttribute("encomendas", encomendas);
        model.addAttribute("filtroEstado", estado);
        model.addAttribute("totalEncomendas", encomendaService.listarRecentes().size());
        model.addAttribute("activeSection", "encomendas");
        model.addAttribute("conteudo", "admin-encomendas :: conteudo");
        return "admin-layout";
    }

    private Cart getCart(HttpSession session) {
        return (Cart) session.getAttribute(CartController.SESSION_CART);
    }

    private AppUser getCurrentUser(Principal principal) {
        if (principal == null) {
            return null;
        }
        return appUserService.findByUsername(principal.getName()).orElse(null);
    }

    private List<String> getMetodosPagamento() {
        return List.of(
                "Cartao",
                "MB Way",
                "Transferencia bancaria",
                "Pagamento a entrega");
    }
}
