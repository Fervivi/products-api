/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos necesarios para crear o actualizar un producto")
public class ProductRequestDto {

    @Schema(description = "Nombre del producto", example = "Alimento Dog Chow Adulto 15kg")
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Schema(description = "Código SKU del producto", example = "PD-101")
    @NotBlank(message = "El SKU es obligatorio")
    private String sku;

    @Schema(description = "Descripción comercial del producto", example = "Alimento seco para perros adultos de raza mediana y grande")
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Schema(description = "Características o especificaciones técnicas del producto", example = "Formato seco de 15 kg con proteínas de alta calidad, vitaminas y minerales para perros adultos de raza mediana y grande.")
    @NotBlank(message = "El texto de características es obligatorio")
    private String caracteristicas;

    @Schema(description = "URL de la imagen del producto", example = "https://placehold.co/600x400?text=PD-101")
    @NotBlank(message = "La imagen del producto es obligatoria")
    private String imagen;

    @Schema(description = "Precio unitario del producto", example = "32990.00")
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @Schema(description = "Cantidad disponible en inventario", example = "20")
    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(description = "Identificador de la categoría del producto", example = "1")
    @NotNull(message = "El id de la categoría es obligatorio")
    private Long categoriaId;

    @Schema(description = "Identificador del estado actual del producto", example = "1")
    @NotNull(message = "El id del estado es obligatorio")
    private Long estadoId;
}
