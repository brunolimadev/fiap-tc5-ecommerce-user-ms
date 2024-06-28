package br.com.fiap.fiap_tc5_ecommerce_user_ms.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.repositories.UserRepository;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Erro ao obter usuário."));
    }

}
