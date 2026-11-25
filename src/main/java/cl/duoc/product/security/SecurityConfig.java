/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String[] SWAGGER_PATHS = {"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html"};
    private static final String PRODUCTS_PATHS = "/api/v1/products/**";

    /** Rol (claim "roles" del JWT emitido por auth-api) con permisos de administración. */
    private static final String ADMIN_ROLE = "admin";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${jwt.public}")
    private String[] publicPaths;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // El forward interno a /error debe ser libre; si no, un 403 se convierte en 401.
                        .dispatcherTypeMatchers(DispatcherType.ERROR)
                        .permitAll()
                        .requestMatchers(SWAGGER_PATHS)
                        .permitAll()
                        .requestMatchers(publicPaths)
                        .permitAll()
                        // Catálogo público: la tienda se puede ver sin iniciar sesión.
                        .requestMatchers(HttpMethod.GET, PRODUCTS_PATHS)
                        .permitAll()
                        // Crear, editar o desactivar productos/categorías: solo administradores.
                        .requestMatchers(HttpMethod.POST, PRODUCTS_PATHS)
                        .hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, PRODUCTS_PATHS)
                        .hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PATCH, PRODUCTS_PATHS)
                        .hasAuthority(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, PRODUCTS_PATHS)
                        .hasAuthority(ADMIN_ROLE)
                        .anyRequest()
                        .authenticated())
                // Sin token -> 401 (no autenticado); token sin rol admin -> 403 (prohibido).
                .exceptionHandling(ex -> ex.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
