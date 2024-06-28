package br.com.fiap.fiap_tc5_ecommerce_user_ms.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.AuthDto;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.SignInResponseDto;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.entities.User;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.services.JwtService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthDto credentials) throws Exception {

        // Cria instancia de autenticação com os dados do usuário
        var userCredentials = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());

        // Autentica o usuário
        var authentication = authenticationManager.authenticate(userCredentials);

        // Gera o token
        var jwt = jwtService.generateToken((User) authentication.getPrincipal());

        // Adiciona o token no header da resposta
        HttpHeaders hearders = new HttpHeaders();

        // Adiciona a header Authorization com o token
        hearders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        // Cria o objeto de respota com o status e o nome do usuário
        SignInResponseDto response = new SignInResponseDto(true, ((User) authentication.getPrincipal()).getName());

        return ResponseEntity.ok().headers(hearders).body(response);
    }
}
