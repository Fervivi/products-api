/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.service;

import cl.duoc.product.dto.request.ProductRequestDto;
import cl.duoc.product.dto.response.ProductResponseDto;
import cl.duoc.product.exception.ResourceNotFoundException;
import cl.duoc.product.model.ProductModel;
import cl.duoc.product.repository.ProductRepository;
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

    public List<ProductResponseDto> getActiveProducts() {
        return productRepository.findByActivoTrue().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public List<ProductResponseDto> getProductsByCategoria(String categoria) {
        return productRepository.findByCategoriaAndActivoTrue(categoria).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public ProductResponseDto getProductById(Long id) {
        ProductModel product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        return mapToResponseDto(product);
    }

    public ProductResponseDto updateProduct(Long id, ProductRequestDto request) {
        ProductModel product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        product.setNombre(request.getNombre());
        product.setDescripcion(request.getDescripcion());
        product.setPrecio(request.getPrecio());
        product.setStock(request.getStock());
        product.setCategoria(request.getCategoria());

        return mapToResponseDto(productRepository.save(product));
    }

    public ProductResponseDto deactivateProduct(Long id) {
        ProductModel product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        product.setActivo(false);

        return mapToResponseDto(productRepository.save(product));
    }
}
