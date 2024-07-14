package br.com.fiap.fiap_tc5_ecommerce_user_ms.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.enums.UserRoleEnum;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.UserDto;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.entities.User;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.repositories.UserRepository;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.services.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userServiceImpl;

    @BeforeEach
    public void setUp() {
        userServiceImpl = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void devePermitirCriarUmUsuario() {
        UserDto request = new UserDto("teste", "test@test.com.br", "Teste@123", LocalDateTime.now(),
                UserRoleEnum.ROLE_USER);

        Optional<User> response = Optional.empty();

        when(userRepository.findByEmail(anyString()))
                .thenReturn(response);

        when(userRepository.save(any(User.class))).thenReturn(new User(UUID.randomUUID(), "", "", "", true,
                LocalDateTime.now(), UserRoleEnum.ROLE_USER));

        userServiceImpl.create(request);

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save((any(User.class)));
    }

    @Test
    void naoDevePermitirCriarUmUsuarioExistente() {
        UserDto request = new UserDto("teste", "test@test.com.br", "Teste@123", LocalDateTime.now(),
                UserRoleEnum.ROLE_USER);

        Optional<User> response = Optional.of(
                new User(UUID.randomUUID(), "teste", "test@test.com.br", "Teste@123", true, LocalDateTime.now(),
                        UserRoleEnum.ROLE_USER));

        when(userRepository.findByEmail(anyString()))
                .thenReturn(response);

        assertThrows(RuntimeException.class, () -> {
            userServiceImpl.create(request);
        });
    }

    @Test
    void devePermitirBuscarUsuarioPorEmail() {

        Optional<User> response = Optional.of(
                new User(UUID.randomUUID(), "teste", "test@test.com.br", "Teste@123", true, LocalDateTime.now(),
                        UserRoleEnum.ROLE_USER));

        when(userRepository.findByEmail(anyString()))
                .thenReturn(response);

        userServiceImpl.findByEmail("teste@email.com");

        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void deveRetornarErroParaUmUsuarioInexistente() {

        Optional<User> response = Optional.empty();

        when(userRepository.findByEmail(anyString()))
                .thenReturn(response);

        assertThrows(RuntimeException.class, () -> {
            userServiceImpl.findByEmail("test@email.com");
        });

    }
}
