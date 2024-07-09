package br.com.fiap.fiap_tc5_ecommerce_user_ms.services;

import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.GetUserByEmailDto;
import br.com.fiap.fiap_tc5_ecommerce_user_ms.models.dtos.UserDto;
import jakarta.validation.Valid;

public interface UserService {

    public void create(UserDto user);

    public GetUserByEmailDto findByEmail(@Valid String email);

}
