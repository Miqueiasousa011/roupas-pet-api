package br.com.roupaspet.dtos.response.order;

import java.util.List;

import br.com.roupaspet.models.Order;

public record OrderResponseDTO(
        Long id,
        String paymentMethod,
        List<OrderItemResponseDTO> items,
        Double total,
        String status //
) {
    public OrderResponseDTO(Order order) {
        this(
                order.getId(),
                order.getPaymentMethod().name(),
                order.getItems().stream().map(OrderItemResponseDTO::new).toList(),
                order.getTotal(),
                order.getStatus().name() //
        );
    }
}
