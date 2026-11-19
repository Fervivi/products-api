/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.controller;

import cl.duoc.product.dto.response.MetadataDto;
import cl.duoc.product.service.MetadataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Metadata", description = "Metadatos de configuración de la interfaz de productos.")
public class MetadataController {

    private final MetadataService metadataService;

    @GetMapping("/metadata")
    @Operation(
            summary = "Obtener metadata de productos",
            description = "Retorna la configuración de metadata (entidad, categoría, estados, columnas, etc.) "
                    + "esperada por el frontend para renderizar el listado de productos.")
    public ResponseEntity<MetadataDto> getMetadata() {
        return ResponseEntity.ok(metadataService.getMetadata());
    }
}
