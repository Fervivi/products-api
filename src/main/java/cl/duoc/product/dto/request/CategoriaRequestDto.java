/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDto {

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(min = 1, max = 50, message = "El nombre de la categoría debe tener entre 1 y 50 caracteres")
    private String nombreCategoria;

    @NotBlank(message = "El nombre a mostrar es obligatorio")
    @Size(min = 1, max = 50, message = "El nombre a mostrar debe tener entre 1 y 50 caracteres")
    private String nombreMostrar;

    @NotBlank(message = "El ícono es obligatorio")
    @Size(min = 1, max = 4, message = "El ícono debe tener entre 1 y 4 caracteres")
    private String icon;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 1, max = 255, message = "La descripción debe tener entre 1 y 255 caracteres")
    private String descripcion;
}
