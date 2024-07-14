package br.com.fiap.fiap_tc5_ecommerce_user_ms.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class EcommerceSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http

                // Enable Basic Authentication with Default Configuration
                .httpBasic(Customizer.withDefaults())

                // Disable CSRF and Session Management to use Basic Authentication
                .csrf(csrf -> csrf.disable())

                // Disable Session Management to use Basic Authentication
                .sessionManagement(AbstractHttpConfigurer::disable)

                // Authorize Requests
                .authorizeHttpRequests(
                        authorizize -> {
                            authorizize
                                    .anyRequest().permitAll();
                        })
                .build();

    }

    /**
     * Método responsável por criar o PasswordEncoder
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
