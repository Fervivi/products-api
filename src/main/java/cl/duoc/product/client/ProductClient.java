/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.client;

import cl.duoc.product.dto.response.ProductResponseDto;
import cl.duoc.product.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final WebClient webClientProduct;

    public ProductResponseDto getProductById(Long id) {
        return webClientProduct
                .get()
                .uri("/api/v1/products/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        respose -> Mono.error(new ResourceNotFoundException("Producto no encontrado con id: " + id)))
                .bodyToMono(ProductResponseDto.class)
                .block();
    }
}
