/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Detalle de error retornado por la API")
public class DtoApiError {

    @Schema(description = "Fecha en que ocurrió el error", example = "2026-06-16")
    private LocalDate timestamp;

    @Schema(description = "Código HTTP del error", example = "404")
    private int status;

    @Schema(description = "Descripción del estado HTTP", example = "Not Found")
    private String error;

    @Schema(description = "Mensaje específico del error", example = "Producto no encontrado")
    private String message;

    @Schema(description = "Ruta donde ocurrió el error", example = "/api/v1/products/99")
    private String path;

    @Schema(description = "Clase de excepción asociada", example = "ResourceNotFoundException")
    private String claseException;
}
