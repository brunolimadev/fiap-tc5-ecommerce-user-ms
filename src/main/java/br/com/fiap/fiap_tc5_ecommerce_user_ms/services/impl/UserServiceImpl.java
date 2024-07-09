package br.com.fiap.fiap_tc5_ecommerce_user_ms.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.enums.UserRoleEnum;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.GetUserByEmailDto;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.UserDto;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.entities.User;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.repositories.UserRepository;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.services.UserService;
import jakarta.validation.Valid;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método responsável por criar um usuário
     *
     * @param userDto
     */
    @Override
    public void create(UserDto userDto) {

        Optional<User> userFounded = this.userRepository.findByEmail(userDto.email());

        if (userFounded.isPresent()) {
            throw new RuntimeException("Erro ao criar o usuário! Por favor, tente novamente.");
        }

        String encrypedPassoword = this.passwordEncoder.encode(userDto.password());

        User user = this.convertToEntity(userDto);

        user.setPassword(encrypedPassoword);

        this.userRepository.save(user);
    }

    @Override
    public GetUserByEmailDto findByEmail(@Valid String email) {

        Optional<User> userFounded = this.userRepository.findByEmail(email);

        if (!userFounded.isPresent()) {
            throw new RuntimeException("Usuário não encontrado!");
        }

        User user = userFounded.get();

        return new GetUserByEmailDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                user.getCreatedAt(),
                user.getRole());

    }

    /**
     * Método responsável por converter um UserDto para um User
     *
     * @param userDto
     * @return
     */
    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setActive(true);
        user.setName(userDto.name());
        user.setCreatedAt(LocalDateTime.now().atOffset(ZoneOffset.of("-03:00")).toLocalDateTime());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        user.setRole(userDto.role() != null ? userDto.role() : UserRoleEnum.ROLE_USER);

        return user;
    }

}
