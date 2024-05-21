package br.com.roupaspet.dtos.request.order;

import br.com.roupaspet.models.Order.OrderStatus;

public record UpdateOrderDTO(OrderStatus status) {

}
