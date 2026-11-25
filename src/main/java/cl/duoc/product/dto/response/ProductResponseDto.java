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

    @Schema(description = "Nombre del producto", example = "Alimento Dog Chow Adulto 15kg")
    private String nombre;

    @Schema(description = "Código SKU del producto", example = "PD-101")
    private String sku;

    @Schema(
            description = "Descripción comercial del producto",
            example = "Alimento seco para perros adultos de raza mediana y grande")
    private String descripcion;

    @Schema(
            description = "Características o especificaciones técnicas del producto",
            example =
                    "Formato seco de 15 kg con proteínas de alta calidad, vitaminas y minerales para perros adultos de raza mediana y grande.")
    private String caracteristicas;

    @Schema(description = "URL de la imagen del producto", example = "https://placehold.co/600x400?text=PD-101")
    private String imagen;

    @Schema(description = "Precio unitario del producto", example = "32990.00")
    private BigDecimal precio;

    @Schema(description = "Cantidad disponible en inventario", example = "20")
    private Integer stock;

    @Schema(description = "Identificador de la categoría del producto", example = "1")
    private Long categoriaId;

    @Schema(description = "Nombre de la categoría para mostrar en la tienda", example = "Alimentos")
    private String categoria;

    @Schema(description = "Identificador del estado del producto", example = "1")
    private Long estadoId;

    @Schema(description = "Nombre legible del estado del producto", example = "Activo")
    private String estado;

    @Deprecated
    @Schema(description = "(DEPRECATED) Indica si el producto está activo", example = "true", deprecated = true)
    private Boolean activo;
}
