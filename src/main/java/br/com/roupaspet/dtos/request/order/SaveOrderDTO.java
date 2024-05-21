package br.com.roupaspet.dtos.request.order;

import java.util.List;

import br.com.roupaspet.models.Order.PaymentMethod;

public record SaveOrderDTO(List<OrderItemDTO> items, PaymentMethod paymentMethod) {

}
