/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos retornados por la API para un producto")
public class ProductResponseDto {

    @Schema(description = "Identificador único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Notebook Lenovo IdeaPad")
    private String nombre;

    @Schema(description = "Código SKU del producto", example = "PD-101")
    private String sku;

    @Schema(description = "Descripción comercial del producto", example = "Notebook de 15 pulgadas con 16 GB RAM")
    private String descripcion;

    @Schema(description = "Precio unitario del producto", example = "549990")
    private BigDecimal precio;

    @Schema(description = "Cantidad disponible en inventario", example = "25")
    private Integer stock;

    @Schema(description = "Categoría a la que pertenece el producto", example = "Alimentos para perros")
    private String categoria;

    @Schema(description = "Identificador de la categoría del producto", example = "1")
    private Long categoriaId;

    @Schema(description = "Identificador del estado del producto", example = "1")
    private Long estadoId;

    @Schema(description = "Indica si el producto está activo", example = "true")
    private Boolean activo;
}
