package br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos;

import jakarta.validation.constraints.Email;

public record AuthDto(
        @Email(message = "Deve ser um email válido.") String email,
        String password) {
}
