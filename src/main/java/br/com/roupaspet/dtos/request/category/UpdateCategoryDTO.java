package br.com.roupaspet.dtos.request.category;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryDTO(@NotBlank String name, boolean isActive) {

}
