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
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {

    List<ProductModel> findByActivoTrue();

    List<ProductModel> findByCategoriaNombreCategoriaAndActivoTrue(String categoria);

    @Query("SELECT p.categoria.id AS categoriaId, COUNT(p) AS total FROM ProductModel p GROUP BY p.categoria.id")
    List<CategoriaCountProjection> countGroupByCategoria();

    Optional<ProductModel> findById(Long id);
}
