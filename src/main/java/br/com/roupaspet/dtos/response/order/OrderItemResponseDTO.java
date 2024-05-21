package br.com.roupaspet.dtos.response.order;

import br.com.roupaspet.models.OrderItem;

public record OrderItemResponseDTO(
    Long productId,
    String productName,
    Integer quantity,
    Double unitPrice,
    Double subTotal //
) {

  public OrderItemResponseDTO(OrderItem orderItem) {
    this(
        orderItem.getId().getProduct().getId(),
        orderItem.getId().getProduct().getName(),
        orderItem.getQuantity(),
        orderItem.getId().getProduct().getPrice(),
        orderItem.getPrice() //
    );
  }

}
