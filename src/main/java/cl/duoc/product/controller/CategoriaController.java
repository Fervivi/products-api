/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.controller;

import cl.duoc.product.dto.request.CategoriaRequestDto;
import cl.duoc.product.dto.response.CategoriaListadoResponseDto;
import cl.duoc.product.dto.response.CategoriaResponseDto;
import cl.duoc.product.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products/categories")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Operaciones CRUD del dominio categorías.")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    @Operation(
            summary = "Listar categorías",
            description = "Retorna todas las categorías registradas con su cantidad de productos.")
    public ResponseEntity<List<CategoriaListadoResponseDto>> getCategories() {
        return ResponseEntity.ok(categoriaService.getCategories());
    }

    @PostMapping
    @Operation(summary = "Crear categoría", description = "Registra una nueva categoría en el sistema.")
    public ResponseEntity<CategoriaResponseDto> createCategory(
            @Valid @RequestBody CategoriaRequestDto categoriaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.createCategory(categoriaRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Actualiza los datos de una categoría existente.")
    public ResponseEntity<CategoriaResponseDto> updateCategory(
            @PathVariable Long id, @Valid @RequestBody CategoriaRequestDto categoriaRequest) {
        return ResponseEntity.ok(categoriaService.updateCategory(id, categoriaRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría por ID", description = "Elimina una categoría por su identificador.")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoriaService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
