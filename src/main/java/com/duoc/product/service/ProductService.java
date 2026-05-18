/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package com.duoc.product.service;

import com.duoc.product.dto.request.ProductRequestDto;
import com.duoc.product.dto.response.ProductResponseDto;
import com.duoc.product.exception.ResourceNotFoundException;
import com.duoc.product.model.ProductModel;
import com.duoc.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto mapToResponseDto(ProductModel product) {

        ProductResponseDto response = new ProductResponseDto();

        response.setId(product.getId());
        response.setNombre(product.getNombre());
        response.setDescripcion(product.getDescripcion());
        response.setPrecio(product.getPrecio());
        response.setStock(product.getStock());
        response.setCategoria(product.getCategoria());
        response.setActivo(product.getActivo());

        return response;
    }

    public ProductResponseDto createProduct(ProductRequestDto request) {

        ProductModel product = new ProductModel();

        product.setNombre(request.getNombre());
        product.setDescripcion(request.getDescripcion());
        product.setPrecio(request.getPrecio());
        product.setStock(request.getStock());
        product.setCategoria(request.getCategoria());
        product.setActivo(true);

        ProductModel savedProduct = productRepository.save(product);

        return mapToResponseDto(savedProduct);
    }

    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToResponseDto).toList();
    }

    public ProductResponseDto getProductById(Long id) {
        ProductModel model = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        return mapToResponseDto(model);
    }
}
