/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package com.duoc.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfigProduct {

    @Bean
    public WebClient WebClientProduct() {
        return WebClient.builder()
                .baseUrl("http://localhost:8082")
                .defaultHeader("Content-Type", "aplication/json")
                .build();
    }
}
