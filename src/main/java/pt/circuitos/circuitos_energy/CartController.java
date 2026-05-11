package pt.circuitos.circuitos_energy;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pt.circuitos.circuitos_energy.model.Cart;
import pt.circuitos.circuitos_energy.model.CartItem;
import pt.circuitos.circuitos_energy.model.Servico;
import pt.circuitos.circuitos_energy.repository.ServicoRepository;

@Controller
@RequestMapping("/carrinho")
public class CartController {

    public static final String SESSION_CART = "CART_SESSION";

    private final ServicoRepository servicoRepo;

    public CartController(ServicoRepository servicoRepo) {
        this.servicoRepo = servicoRepo;
    }

    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(SESSION_CART);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(SESSION_CART, cart);
        }
        return cart;
    }

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = getCart(session);
        model.addAttribute("cart", cart);
        return "carrinho";
    }

    @PostMapping("/adicionar/{id}")
    public String addToCart(@PathVariable("id") Long id, HttpSession session) {
        adicionarServicoAoCarrinho(id, session);
        return "redirect:/carrinho";
    }

    @PostMapping("/adicionar")
    public String addToCartByForm(@RequestParam("servicoId") Long servicoId, HttpSession session) {
        adicionarServicoAoCarrinho(servicoId, session);
        return "redirect:/carrinho";
    }

    private void adicionarServicoAoCarrinho(Long id, HttpSession session) {
        Servico servico = servicoRepo.findById(id).orElse(null);
        if (servico != null) {
            Cart cart = getCart(session);
            cart.addItem(new CartItem(servico.getId(), servico.getTitulo(), 1, servico.getPreco()));
        }
    }

    @PostMapping("/remover/{id}")
    public String removeFromCart(@PathVariable("id") Long id, HttpSession session) {
        Cart cart = getCart(session);
        cart.removeItem(id);
        return "redirect:/carrinho";
    }

    @PostMapping("/limpar")
    public String clearCart(HttpSession session) {
        Cart cart = getCart(session);
        cart.clear();
        return "redirect:/carrinho";
    }
}
