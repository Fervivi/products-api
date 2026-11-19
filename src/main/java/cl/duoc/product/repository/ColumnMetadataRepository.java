/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.repository;

import cl.duoc.product.model.ColumnMetadata;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnMetadataRepository extends JpaRepository<ColumnMetadata, Long> {

    List<ColumnMetadata> findAllByTableNameOrderBySortOrderAscIdAsc(String tableName);
}
