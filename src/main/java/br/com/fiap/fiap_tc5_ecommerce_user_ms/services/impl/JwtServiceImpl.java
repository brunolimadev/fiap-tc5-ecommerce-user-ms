package br.com.fiap.fiap_tc5_ecommerce_user_ms.services.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.entities.User;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.services.JwtService;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String ISSUER = "Ecommerce System";

    @Value("${api.security.jwt.secret}")
    private String secret;

    /**
     * Método responsável por gerar um token JWT
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public String generateToken(User user) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId().toString())
                    .withClaim("role", user.getRole().getRole())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token!", e);
        }
    }

    /**
     * Método responsável por validar um token JWT
     *
     * @param token
     * @return
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    /**
     * Método responsável por gerar a data de expiração do token
     *
     * @return
     */
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
