package com.duoc.product.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precion;
    private Integer stock;
    private String categoria;
    private Boolean actvo;

}
