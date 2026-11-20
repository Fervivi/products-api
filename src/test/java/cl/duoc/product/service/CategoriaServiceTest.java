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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.duoc.product.dto.request.CategoriaRequestDto;
import cl.duoc.product.dto.response.CategoriaListadoResponseDto;
import cl.duoc.product.dto.response.CategoriaResponseDto;
import cl.duoc.product.exception.ResourceNotFoundException;
import cl.duoc.product.model.Categoria;
import cl.duoc.product.repository.CategoriaCountProjection;
import cl.duoc.product.repository.CategoriaRepository;
import cl.duoc.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria(1L, "alimentos", "Alimentos para perros", "🐕", "Alimentos balanceados");
    }

    private CategoriaRequestDto createRequest() {
        return new CategoriaRequestDto("alimentos", "Alimentos para perros", "🐕", "Alimentos balanceados");
    }

    @Test
    void getCategories_shouldAddDefaultCategoryAndProductCounts() {
        CategoriaCountProjection count = mock(CategoriaCountProjection.class);
        when(count.getCategoriaId()).thenReturn(1L);
        when(count.getTotal()).thenReturn(3L);
        when(productRepository.count()).thenReturn(5L);
        when(productRepository.countGroupByCategoria()).thenReturn(List.of(count));
        when(categoriaRepository.findAll(any(Sort.class))).thenReturn(List.of(categoria));

        List<CategoriaListadoResponseDto> result = categoriaService.getCategories();

        assertThat(result).hasSize(2);
        CategoriaListadoResponseDto defaultCategory = result.getFirst();
        assertThat(defaultCategory.getId()).isZero();
        assertThat(defaultCategory.getName()).isEqualTo("Todos los productos");
        assertThat(defaultCategory.getValue()).isEqualTo("todos");
        assertThat(defaultCategory.getAmount()).isEqualTo(5L);
        assertThat(defaultCategory.getIsDefault()).isTrue();
        CategoriaListadoResponseDto storedCategory = result.get(1);
        assertThat(storedCategory.getId()).isEqualTo(1L);
        assertThat(storedCategory.getName()).isEqualTo("Alimentos para perros");
        assertThat(storedCategory.getValue()).isEqualTo("alimentos");
        assertThat(storedCategory.getAmount()).isEqualTo(3L);
        assertThat(storedCategory.getIsDefault()).isNull();
    }

    @Test
    void createCategory_shouldSaveAndMapCategory() {
        when(categoriaRepository.save(any(Categoria.class))).thenAnswer(invocation -> {
            Categoria savedCategory = invocation.getArgument(0);
            savedCategory.setId(1L);
            return savedCategory;
        });

        CategoriaResponseDto result = categoriaService.createCategory(createRequest());

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNombreCategoria()).isEqualTo("alimentos");
        assertThat(result.getNombreMostrar()).isEqualTo("Alimentos para perros");
        assertThat(result.getIcon()).isEqualTo("🐕");
        assertThat(result.getDescripcion()).isEqualTo("Alimentos balanceados");
    }

    @Test
    void updateCategory_shouldModifyExistingCategory() {
        CategoriaRequestDto request = new CategoriaRequestDto("juguetes", "Juguetes", "🧸", "Juguetes para mascotas");
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        CategoriaResponseDto result = categoriaService.updateCategory(1L, request);

        assertThat(result.getNombreCategoria()).isEqualTo("juguetes");
        assertThat(result.getNombreMostrar()).isEqualTo("Juguetes");
        assertThat(result.getIcon()).isEqualTo("🧸");
        assertThat(result.getDescripcion()).isEqualTo("Juguetes para mascotas");
        verify(categoriaRepository).save(categoria);
    }

    @Test
    void updateCategory_shouldThrowWhenCategoryDoesNotExist() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.updateCategory(99L, createRequest()))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("La categoría con ID 99 no existe");
    }

    @Test
    void deleteCategory_shouldDeleteExistingCategory() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        categoriaService.deleteCategory(1L);

        verify(categoriaRepository).delete(categoria);
    }

    @Test
    void deleteCategory_shouldThrowWhenCategoryDoesNotExist() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoriaService.deleteCategory(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("No se puede eliminar categoría con id: 99");
    }
}
