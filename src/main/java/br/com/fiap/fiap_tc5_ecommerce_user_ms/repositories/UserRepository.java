package br.com.fiap.fiap_tc5_ecommerce_user_ms.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.entities.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail(String email);
}
