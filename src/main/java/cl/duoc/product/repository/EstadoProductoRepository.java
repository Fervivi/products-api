/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.repository;

import cl.duoc.product.model.EstadoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoProductoRepository extends JpaRepository<EstadoProducto, Long> {}
