package br.com.fiap.fiap_tc5_ecommerce_user_ms.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ROLE_ADMIN("ADMIN"),
    ROLE_USER("USER");

    private final String role;

    UserRoleEnum(String role) {
        this.role = role;
    }
}
