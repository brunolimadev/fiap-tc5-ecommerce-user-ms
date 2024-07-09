package br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.enums.UserRoleEnum;

public record GetUserByEmailDto(
        UUID id,
        String name,
        String email,
        String password,
        boolean isActive,
        LocalDateTime createdAt,
        UserRoleEnum role) {

}
