package br.com.roupaspet.dtos.response.auth;

public record UserResponseDTO(
                String name,
                String email,
                String token) {
}
