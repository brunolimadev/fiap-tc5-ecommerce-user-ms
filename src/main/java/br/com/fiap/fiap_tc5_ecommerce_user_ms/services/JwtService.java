package br.com.fiap.fiap_tc5_ecommerce_user_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.entities.User;

public interface JwtService {
    public String generateToken(User user) throws Exception;

    public String validateToken(String token);
}
