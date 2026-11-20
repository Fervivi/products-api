/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.service;

import cl.duoc.product.dto.request.CategoriaRequestDto;
import cl.duoc.product.dto.response.CategoriaListadoResponseDto;
import cl.duoc.product.dto.response.CategoriaResponseDto;
import cl.duoc.product.exception.ResourceNotFoundException;
import cl.duoc.product.model.Categoria;
import cl.duoc.product.repository.CategoriaRepository;
import cl.duoc.product.repository.ProductRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProductRepository productRepository;

    private CategoriaResponseDto mapCategoriaToResponse(Categoria categoria) {
        return CategoriaResponseDto.builder()
                .id(categoria.getId())
                .nombreCategoria(categoria.getNombreCategoria())
                .nombreMostrar(categoria.getNombreMostrar())
                .icon(categoria.getIcon())
                .descripcion(categoria.getDescripcion())
                .build();
    }

    @Transactional(readOnly = true)
    public List<CategoriaListadoResponseDto> getCategories() {
        List<CategoriaListadoResponseDto> categories = new ArrayList<>();

        long totalProducts = productRepository.count();
        categories.add(
                new CategoriaListadoResponseDto(0L, "🐾", "Todos los productos", "todos", totalProducts, Boolean.TRUE));

        Map<Long, Long> amountsByCategory = new HashMap<>();
        for (var count : productRepository.countGroupByCategoria()) {
            amountsByCategory.put(count.getCategoriaId(), count.getTotal());
        }

        List<Categoria> storedCategories = categoriaRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (Categoria categoria : storedCategories) {
            categories.add(new CategoriaListadoResponseDto(
                    categoria.getId(),
                    categoria.getIcon(),
                    categoria.getNombreMostrar(),
                    categoria.getNombreCategoria(),
                    amountsByCategory.getOrDefault(categoria.getId(), 0L),
                    null));
        }

        return categories;
    }

    @Transactional
    public CategoriaResponseDto createCategory(CategoriaRequestDto request) {
        log.info("Creando categoría: {}", request.getNombreCategoria());

        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(request.getNombreCategoria());
        categoria.setNombreMostrar(request.getNombreMostrar());
        categoria.setIcon(request.getIcon());
        categoria.setDescripcion(request.getDescripcion());

        return mapCategoriaToResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public CategoriaResponseDto updateCategory(Long id, CategoriaRequestDto request) {
        Categoria categoria = categoriaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La categoría con ID " + id + " no existe"));

        categoria.setNombreCategoria(request.getNombreCategoria());
        categoria.setNombreMostrar(request.getNombreMostrar());
        categoria.setIcon(request.getIcon());
        categoria.setDescripcion(request.getDescripcion());

        return mapCategoriaToResponse(categoriaRepository.save(categoria));
    }

    @Transactional
    public void deleteCategory(Long id) {
        Categoria categoria = categoriaRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede eliminar categoría con id: " + id));

        categoriaRepository.delete(categoria);
    }
}
