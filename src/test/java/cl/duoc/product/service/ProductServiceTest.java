/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.duoc.product.dto.request.ProductRequestDto;
import cl.duoc.product.dto.response.ProductResponseDto;
import cl.duoc.product.exception.ResourceNotFoundException;
import cl.duoc.product.model.Categoria;
import cl.duoc.product.model.EstadoProducto;
import cl.duoc.product.model.ProductModel;
import cl.duoc.product.repository.CategoriaRepository;
import cl.duoc.product.repository.EstadoProductoRepository;
import cl.duoc.product.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private EstadoProductoRepository estadoProductoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ProductService productService;

    private Categoria categoria;
    private EstadoProducto estadoActivo;
    private EstadoProducto estadoInactivo;
    private ProductModel product;

    @BeforeEach
    void setUp() {
        categoria = new Categoria(1L, "Alimentos", "Alimentos", "🍖", "Alimentos para mascotas");
        estadoActivo = new EstadoProducto(1L, "activo", "Activo", "Disponible para la venta");
        estadoInactivo = new EstadoProducto(2L, "inactivo", "Inactivo", "No disponible para la venta");
        product = new ProductModel(
                1L,
                "AL-101",
                "Alimento Perro Adulto",
                "Alimento seco balanceado",
                "Formato seco para perros adultos",
                "/img/comida-perro.png",
                new BigDecimal("29990.00"),
                20,
                categoria,
                estadoActivo,
                true);
    }

    private ProductRequestDto createRequest(Long categoriaId, Long estadoId) {
        return new ProductRequestDto(
                "Alimento Perro Adulto",
                "AL-101",
                "Alimento seco balanceado",
                "Formato seco para perros adultos",
                "/img/comida-perro.png",
                new BigDecimal("29990.00"),
                20,
                categoriaId,
                estadoId);
    }

    @Test
    void createProduct_shouldSaveActiveProductWithCategoryAndState() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(estadoProductoRepository.findById(1L)).thenReturn(Optional.of(estadoActivo));
        when(productRepository.save(any(ProductModel.class))).thenAnswer(inv -> {
            ProductModel saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        ProductResponseDto result = productService.createProduct(createRequest(1L, 1L));

        ArgumentCaptor<ProductModel> captor = ArgumentCaptor.forClass(ProductModel.class);
        verify(productRepository).save(captor.capture());
        ProductModel savedProduct = captor.getValue();
        assertThat(savedProduct.getActivo()).isTrue();
        assertThat(savedProduct.getCategoria()).isSameAs(categoria);
        assertThat(savedProduct.getEstado()).isSameAs(estadoActivo);
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getImagen()).isEqualTo("/img/comida-perro.png");
        assertThat(result.getCategoriaId()).isEqualTo(1L);
        assertThat(result.getCategoria()).isEqualTo("Alimentos");
        assertThat(result.getEstadoId()).isEqualTo(1L);
        assertThat(result.getEstado()).isEqualTo("Activo");
    }

    @Test
    void createProduct_withNonActiveState_shouldNotBeActive() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(estadoProductoRepository.findById(2L)).thenReturn(Optional.of(estadoInactivo));
        when(productRepository.save(any(ProductModel.class))).thenAnswer(inv -> inv.getArgument(0));

        ProductResponseDto result = productService.createProduct(createRequest(1L, 2L));

        assertThat(result.getActivo()).isFalse();
        assertThat(result.getEstado()).isEqualTo("Inactivo");
    }

    @Test
    void createProduct_shouldRejectUnknownCategory() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(createRequest(99L, 1L)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Categoría no encontrada con id: 99");
        verify(productRepository, never()).save(any(ProductModel.class));
    }

    @Test
    void createProduct_shouldRejectUnknownState() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(estadoProductoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(createRequest(1L, 99L)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Estado de producto no encontrado con id: 99");
        verify(productRepository, never()).save(any(ProductModel.class));
    }

    @Test
    void getAllProducts_shouldMapRepositoryResults() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDto> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        ProductResponseDto response = result.getFirst();
        assertThat(response.getNombre()).isEqualTo("Alimento Perro Adulto");
        assertThat(response.getPrecio()).isEqualByComparingTo("29990");
        assertThat(response.getCategoria()).isEqualTo("Alimentos");
        assertThat(response.getEstado()).isEqualTo("Activo");
    }

    @Test
    void getActiveProducts_shouldUseActiveProductsQuery() {
        when(productRepository.findByActivoTrue()).thenReturn(List.of(product));

        List<ProductResponseDto> result = productService.getActiveProducts();

        assertThat(result).hasSize(1).allMatch(ProductResponseDto::getActivo);
        verify(productRepository).findByActivoTrue();
    }

    @Test
    void getProductsByCategoria_shouldReturnMatchingActiveProducts() {
        when(productRepository.findByCategoriaNombreCategoriaAndActivoTrue("Alimentos"))
                .thenReturn(List.of(product));

        List<ProductResponseDto> result = productService.getProductsByCategoria("Alimentos");

        assertThat(result).hasSize(1);
        verify(productRepository).findByCategoriaNombreCategoriaAndActivoTrue("Alimentos");
    }

    @Test
    void getProductById_shouldReturnProductWhenItExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDto result = productService.getProductById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getSku()).isEqualTo("AL-101");
        assertThat(result.getCaracteristicas()).isEqualTo("Formato seco para perros adultos");
    }

    @Test
    void getProductById_shouldThrowWhenProductDoesNotExist() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Producto no encontrado con id: 99");
    }

    @Test
    void updateProduct_shouldModifyProductAndSyncActiveFlagWithState() {
        Categoria nuevaCategoria = new Categoria(2L, "Juguetes", "Juguetes", "🧸", "Juguetes para mascotas");
        ProductRequestDto request = new ProductRequestDto(
                "Alimento actualizado",
                "AL-101",
                "Nueva descripción",
                "Nuevas características",
                "/img/comida-perro.png",
                new BigDecimal("27990"),
                15,
                2L,
                2L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(nuevaCategoria));
        when(estadoProductoRepository.findById(2L)).thenReturn(Optional.of(estadoInactivo));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponseDto result = productService.updateProduct(1L, request);

        assertThat(result.getNombre()).isEqualTo("Alimento actualizado");
        assertThat(result.getStock()).isEqualTo(15);
        assertThat(result.getCategoriaId()).isEqualTo(2L);
        assertThat(result.getCategoria()).isEqualTo("Juguetes");
        assertThat(result.getEstadoId()).isEqualTo(2L);
        assertThat(result.getEstado()).isEqualTo("Inactivo");
        assertThat(result.getActivo()).isFalse();
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_shouldNotSaveWhenProductDoesNotExist() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(99L, createRequest(1L, 1L)))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Producto no encontrado con id: 99");
        verify(productRepository, never()).save(any(ProductModel.class));
    }

    @Test
    void deactivateProduct_shouldMarkProductAsInactiveAndChangeState() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(estadoProductoRepository.findByNombre("inactivo")).thenReturn(Optional.of(estadoInactivo));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponseDto result = productService.deactivateProduct(1L);

        assertThat(result.getActivo()).isFalse();
        assertThat(result.getEstadoId()).isEqualTo(2L);
        assertThat(result.getEstado()).isEqualTo("Inactivo");
        assertThat(product.getEstado()).isSameAs(estadoInactivo);
        verify(productRepository).save(product);
    }

    @Test
    void deactivateProduct_shouldThrowWhenProductDoesNotExist() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deactivateProduct(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Producto no encontrado con id: 99");
        verify(productRepository, never()).save(any(ProductModel.class));
    }
}
