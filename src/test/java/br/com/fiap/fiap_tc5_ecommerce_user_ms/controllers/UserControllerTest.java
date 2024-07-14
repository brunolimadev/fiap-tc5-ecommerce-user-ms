package br.com.fiap.fiap_tc5_ecommerce_user_ms.controllers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.enums.UserRoleEnum;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.GetUserByEmailDto;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.UserDto;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.services.UserService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void deveRetornarSucessoAoCriarUmUsuario() throws Exception {
        UserDto request = new UserDto(
                "teste",
                "test@test.com.br",
                "Teste@123",
                LocalDateTime.now(),
                UserRoleEnum.ROLE_USER);

        doNothing().when(userService).create(request);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(userService, times(1)).create(request);
    }

    @Test
    void deveRetornarSucessoAoBuscarUmUsuárioPeloEmail() throws Exception {
        GetUserByEmailDto response = new GetUserByEmailDto(
                UUID.randomUUID(),
                "teste",
                "test@test.com.br",
                "Teste@123",
                true,
                LocalDateTime.now(),
                UserRoleEnum.ROLE_USER);

        when(userService.findByEmail(anyString())).thenReturn(response);

        mockMvc.perform(get("/users?byEmail=teste@email.com"))
                .andExpect(status().isOk());

        verify(userService, times(1)).findByEmail(anyString());

    }

}
