package br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos;

import java.time.LocalDateTime;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.enums.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserDto(
                String name,
                @Email(message = "Deve ser um email válido.") String email,
                @Size(min = 6, max = 12, message = "Senha de ter no mínimo 6 caracteres.") @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial.") String password,
                LocalDateTime createdAt,
                UserRoleEnum role) {
}
