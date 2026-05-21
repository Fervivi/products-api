/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.repository;

import cl.duoc.product.model.ProductModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByActivoTrue();

    List<ProductModel> findByCategoriaAndActivoTrue(String categoria);

    Optional<ProductModel> findById(Long id);
}
