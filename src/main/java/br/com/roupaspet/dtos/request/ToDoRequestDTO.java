package br.com.roupaspet.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record ToDoRequestDTO(
        @NotBlank
        String title,
        @NotBlank
        String description
) {
}
