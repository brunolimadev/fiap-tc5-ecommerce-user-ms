package br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos;

public record SignInResponseDto(
        boolean isAuthenticated,
        String name) {
}
