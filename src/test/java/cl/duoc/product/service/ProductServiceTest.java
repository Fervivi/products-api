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
    private EstadoProducto estado;
    private ProductModel product;

    @BeforeEach
    void setUp() {
        categoria = new Categoria(1L, "alimentos", "Alimentos para perros", "🐕", "Alimentos balanceados");
        estado = new EstadoProducto(1L, "activo", "Activo", "Disponible para la venta");
        product = new ProductModel(
                1L,
                "PD-101",
                "Alimento premium",
                "Alimento para perro adulto",
                new BigDecimal("24990"),
                20,
                categoria,
                true,
                estado);
    }

    private ProductRequestDto createRequest() {
        return createRequest(1L, "activo");
    }

    private ProductRequestDto createRequest(Long categoriaId, String estadoNombre) {
        return new ProductRequestDto(
                "Alimento premium",
                "PD-101",
                "Alimento para perro adulto",
                new BigDecimal("24990"),
                20,
                categoriaId,
                estadoNombre);
    }

    @Test
    void createProduct_shouldSaveActiveProductWithCategoryAndState() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(estadoProductoRepository.findByNombre("activo")).thenReturn(Optional.of(estado));
        when(productRepository.save(any(ProductModel.class))).thenAnswer(invocation -> {
            ProductModel savedProduct = invocation.getArgument(0);
            savedProduct.setId(1L);
            return savedProduct;
        });

        ProductResponseDto result = productService.createProduct(createRequest());

        ArgumentCaptor<ProductModel> captor = ArgumentCaptor.forClass(ProductModel.class);
        verify(productRepository).save(captor.capture());
        ProductModel savedProduct = captor.getValue();
        assertThat(savedProduct.getActivo()).isTrue();
        assertThat(savedProduct.getCategoria()).isSameAs(categoria);
        assertThat(savedProduct.getEstado()).isSameAs(estado);
        assertThat(savedProduct.getSku()).isEqualTo("PD-101");
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCategoria()).isEqualTo("alimentos");
        assertThat(result.getIdCategoria()).isEqualTo(1L);
        assertThat(result.getEstado()).isEqualTo("Activo");
        assertThat(result.getIdEstado()).isEqualTo(1L);
    }

    @Test
    void createProduct_shouldRejectUnknownCategory() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(createRequest(99L, "activo")))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Categoría no encontrada con id: 99");
        verify(productRepository, never()).save(any(ProductModel.class));
    }

    @Test
    void createProduct_shouldRejectUnknownState() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(estadoProductoRepository.findByNombre("desconocido")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.createProduct(createRequest(1L, "desconocido")))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Estado de producto no encontrado: desconocido");
        verify(productRepository, never()).save(any(ProductModel.class));
    }

    @Test
    void getAllProducts_shouldMapRepositoryResults() {
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDto> result = productService.getAllProducts();

        assertThat(result).hasSize(1);
        ProductResponseDto response = result.getFirst();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getSku()).isEqualTo("PD-101");
        assertThat(response.getCategoria()).isEqualTo("alimentos");
        assertThat(response.getIdCategoria()).isEqualTo(1L);
        assertThat(response.getEstado()).isEqualTo("Activo");
        assertThat(response.getIdEstado()).isEqualTo(1L);
        verify(productRepository).findAll();
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
        when(productRepository.findByCategoriaNombreCategoriaAndActivoTrue("alimentos"))
                .thenReturn(List.of(product));

        List<ProductResponseDto> result = productService.getProductsByCategoria("alimentos");

        assertThat(result).extracting(ProductResponseDto::getCategoria).containsExactly("alimentos");
        verify(productRepository).findByCategoriaNombreCategoriaAndActivoTrue("alimentos");
    }

    @Test
    void getProductById_shouldReturnProductWhenItExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDto result = productService.getProductById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPrecio()).isEqualByComparingTo("24990");
        assertThat(result.getIdEstado()).isEqualTo(1L);
    }

    @Test
    void getProductById_shouldThrowWhenProductDoesNotExist() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Producto no encontrado con id: 99");
    }

    @Test
    void updateProduct_shouldModifyExistingProduct() {
        Categoria nuevaCategoria = new Categoria(2L, "juguetes", "Juguetes", "🧸", "Juguetes para mascotas");
        EstadoProducto nuevoEstado = new EstadoProducto(2L, "inactivo", "Inactivo", "No disponible");
        ProductRequestDto request = new ProductRequestDto(
                "Alimento actualizado", "PD-101", "Nueva descripción", new BigDecimal("27990"), 15, 2L, "inactivo");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(nuevaCategoria));
        when(estadoProductoRepository.findByNombre("inactivo")).thenReturn(Optional.of(nuevoEstado));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponseDto result = productService.updateProduct(1L, request);

        assertThat(result.getNombre()).isEqualTo("Alimento actualizado");
        assertThat(result.getStock()).isEqualTo(15);
        assertThat(result.getCategoria()).isEqualTo("juguetes");
        assertThat(result.getIdCategoria()).isEqualTo(2L);
        assertThat(result.getEstado()).isEqualTo("Inactivo");
        assertThat(result.getIdEstado()).isEqualTo(2L);
        assertThat(result.getActivo()).isTrue();
        verify(productRepository).save(product);
    }

    @Test
    void updateProduct_shouldNotSaveWhenProductDoesNotExist() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateProduct(99L, createRequest()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Producto no encontrado con id: 99");
        verify(productRepository, never()).save(any(ProductModel.class));
    }

    @Test
    void deactivateProduct_shouldMarkProductAsInactive() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        ProductResponseDto result = productService.deactivateProduct(1L);

        assertThat(result.getActivo()).isFalse();
        assertThat(product.getActivo()).isFalse();
        verify(productRepository).save(product);
    }
}
