package br.com.roupaspet.dtos.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductDTO(
    @NotBlank String name,
    @NotBlank String description,
    @NotNull @PositiveOrZero Double price,
    @NotBlank String image,
    @NotNull @PositiveOrZero Long stock,
    @NotBlank Long categoryID,
    @NotNull Boolean isActive) {
}
