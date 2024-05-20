package br.com.roupaspet.dtos.request.category;

import jakarta.validation.constraints.NotBlank;

public record SaveCategoryDTO(@NotBlank String name) {
}
