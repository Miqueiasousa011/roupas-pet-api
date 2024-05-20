package br.com.roupaspet.dtos.request.auth;

import jakarta.validation.constraints.Email;

public record SignInRequestDTO(
                @Email(message = "Email inválido") String email,
                String password) {
}
