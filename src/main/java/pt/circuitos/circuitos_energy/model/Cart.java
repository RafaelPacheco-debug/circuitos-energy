package pt.circuitos.circuitos_energy.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {

    private final List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void clear() {
        items.clear();
    }

    public void addItem(CartItem item) {
        if (item == null || item.getServicoId() == null) {
            return;
        }

        CartItem existing = items.stream()
                .filter(i -> i.getServicoId().equals(item.getServicoId()))
                .findFirst()
                .orElse(null);

        if (existing == null) {
            items.add(item);
        } else {
            existing.incrementar(item.getQuantidade());
        }
    }

    public void removeItem(Long servicoId) {
        items.removeIf(i -> i.getServicoId().equals(servicoId));
    }

    public int getTotalItems() {
        return items.stream().mapToInt(CartItem::getQuantidade).sum();
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(CartItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotal() {
        return getSubtotal();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
