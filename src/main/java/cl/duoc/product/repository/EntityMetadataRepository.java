/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.repository;

import cl.duoc.product.model.EntityMetadata;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityMetadataRepository extends JpaRepository<EntityMetadata, Long> {

    Optional<EntityMetadata> findByTableName(String tableName);
}
