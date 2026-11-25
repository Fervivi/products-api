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
import cl.duoc.product.model.Categoria;
import cl.duoc.product.model.EstadoProducto;
import cl.duoc.product.model.ProductModel;
import cl.duoc.product.repository.CategoriaRepository;
import cl.duoc.product.repository.EstadoProductoRepository;
import cl.duoc.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final EstadoProductoRepository estadoProductoRepository;
    private final CategoriaRepository categoriaRepository;

    /** Nombre (columna estados_producto.nombre) del estado que permite vender un producto. */
    static final String ESTADO_ACTIVO = "activo";

    /** Estado que se asigna al desactivar (eliminación lógica) un producto. */
    static final String ESTADO_INACTIVO = "inactivo";

    public ProductResponseDto mapToResponseDto(ProductModel product) {

        ProductResponseDto response = new ProductResponseDto();

        response.setId(product.getId());
        response.setNombre(product.getNombre());
        response.setSku(product.getSku());
        response.setDescripcion(product.getDescripcion());
        response.setCaracteristicas(product.getCaracteristicas());
        response.setImagen(product.getImagen());
        response.setPrecio(product.getPrecio());
        response.setStock(product.getStock());
        response.setCategoriaId(product.getCategoria().getId());
        response.setCategoria(product.getCategoria().getNombreMostrar());
        response.setEstadoId(product.getEstado().getId());
        response.setEstado(product.getEstado().getLabel());
        response.setActivo(product.getActivo());

        return response;
    }

    public ProductResponseDto createProduct(ProductRequestDto request) {

        ProductModel product = new ProductModel();

        product.setNombre(request.getNombre());
        product.setSku(request.getSku());
        product.setDescripcion(request.getDescripcion());
        product.setCaracteristicas(request.getCaracteristicas());
        product.setImagen(request.getImagen());
        product.setPrecio(request.getPrecio());
        product.setStock(request.getStock());
        product.setCategoria(getCategoria(request.getCategoriaId()));
        applyEstado(product, getEstado(request.getEstadoId()));

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
        return productRepository.findByCategoriaNombreCategoriaAndActivoTrue(categoria).stream()
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
        product.setSku(request.getSku());
        product.setDescripcion(request.getDescripcion());
        product.setCaracteristicas(request.getCaracteristicas());
        product.setImagen(request.getImagen());
        product.setPrecio(request.getPrecio());
        product.setStock(request.getStock());
        product.setCategoria(getCategoria(request.getCategoriaId()));
        applyEstado(product, getEstado(request.getEstadoId()));

        return mapToResponseDto(productRepository.save(product));
    }

    public ProductResponseDto deactivateProduct(Long id) {
        ProductModel product = productRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        EstadoProducto inactivo = estadoProductoRepository
                .findByNombre(ESTADO_INACTIVO)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Estado de producto no encontrado: " + ESTADO_INACTIVO));
        applyEstado(product, inactivo);

        return mapToResponseDto(productRepository.save(product));
    }

    /**
     * Asigna el estado y mantiene sincronizado el flag {@code activo}: un producto solo se considera activo (visible en
     * la tienda) cuando su estado es "activo".
     */
    private void applyEstado(ProductModel product, EstadoProducto estado) {
        product.setEstado(estado);
        product.setActivo(ESTADO_ACTIVO.equals(estado.getNombre()));
    }

    private Categoria getCategoria(Long id) {
        return categoriaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
    }

    private EstadoProducto getEstado(Long id) {
        return estadoProductoRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de producto no encontrado con id: " + id));
    }
}
