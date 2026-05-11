package pt.circuitos.circuitos_energy.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import pt.circuitos.circuitos_energy.model.AppUser;
import pt.circuitos.circuitos_energy.model.Cart;
import pt.circuitos.circuitos_energy.model.CartItem;
import pt.circuitos.circuitos_energy.model.CheckoutRequest;
import pt.circuitos.circuitos_energy.model.Encomenda;
import pt.circuitos.circuitos_energy.model.EncomendaItem;
import pt.circuitos.circuitos_energy.repository.EncomendaRepository;
import pt.circuitos.circuitos_energy.repository.ServicoRepository;

@Service
public class EncomendaService {

    private final EncomendaRepository encomendaRepository;
    private final ServicoRepository servicoRepository;

    public EncomendaService(EncomendaRepository encomendaRepository, ServicoRepository servicoRepository) {
        this.encomendaRepository = encomendaRepository;
        this.servicoRepository = servicoRepository;
    }

    public Encomenda criarEncomenda(Cart cart, CheckoutRequest request, AppUser utilizador) {
        if (cart == null || cart.isEmpty()) {
            throw new IllegalStateException("O carrinho esta vazio.");
        }

        Encomenda encomenda = new Encomenda();
        encomenda.setNomeCompleto(request.getNomeCompleto());
        encomenda.setEmail(request.getEmail());
        encomenda.setTelefone(request.getTelefone());
        encomenda.setMoradaEnvio(request.getMoradaEnvio());
        encomenda.setCidade(request.getCidade());
        encomenda.setCodigoPostal(request.getCodigoPostal());
        encomenda.setPais(request.getPais());
        encomenda.setMetodoPagamento(request.getMetodoPagamento());
        encomenda.setEstado("Nova");
        encomenda.setUtilizador(utilizador);

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {
            EncomendaItem item = new EncomendaItem();
            item.setServico(servicoRepository.findById(cartItem.getServicoId()).orElse(null));
            item.setTituloServico(cartItem.getTitulo());
            item.setQuantidade(cartItem.getQuantidade());
            item.setPrecoUnitario(cartItem.getPrecoUnitario());
            item.setSubtotal(cartItem.getSubtotal());
            encomenda.addItem(item);
            total = total.add(cartItem.getSubtotal());
        }

        encomenda.setSubtotal(total);
        encomenda.setTotal(total);

        return encomendaRepository.save(encomenda);
    }

    public List<Encomenda> listarRecentes() {
        return encomendaRepository.findAllByOrderByCriadoEmDesc();
    }
}
