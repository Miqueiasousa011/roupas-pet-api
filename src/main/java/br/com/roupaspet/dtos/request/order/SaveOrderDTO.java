package br.com.roupaspet.dtos.request.order;

import java.util.List;

import br.com.roupaspet.models.Order.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record SaveOrderDTO(
    List<OrderItemDTO> items,
    PaymentMethod paymentMethod,
    @NotNull @PositiveOrZero Double shippingCost //
) {
}
