package br.com.roupaspet.dtos.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequestDTO(
                @NotBlank String name,
                @Email String email,
                @NotBlank String street,
                String houseNumber,
                @NotBlank String neighborhood,
                @NotBlank String city,
                @NotBlank String state,
                @NotBlank String zipcode,
                @NotBlank @Min(value = 6, message = "A senha deve ter no mínimo 6 caracteres") String password) {
}
