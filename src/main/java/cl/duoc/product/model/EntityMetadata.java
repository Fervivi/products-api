/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entities_metadata")
public class EntityMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name", nullable = false, length = 64)
    private String tableName;

    @Column(name = "entity_key", nullable = false, length = 64)
    private String entityKey;

    @Column(name = "single_label", nullable = false, length = 100)
    private String singleLabel;

    @Column(name = "plural_label", nullable = false, length = 100)
    private String pluralLabel;

    @Column(name = "category_field", nullable = false, length = 64)
    private String categoryField;

    @Column(name = "category_label", nullable = false, length = 100)
    private String categoryLabel;

    @Column(name = "category_add_title", nullable = false, length = 100)
    private String categoryAddTitle;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "category_icons", nullable = false)
    private List<String> categoryIcons;

    @Column(name = "delete_name_key", nullable = false, length = 64)
    private String deleteNameKey;

    @Column(name = "delete_detail_key", nullable = false, length = 64)
    private String deleteDetailKey;

    @Column(name = "delete_detail_label", nullable = false, length = 100)
    private String deleteDetailLabel;

    @Column(name = "action_key", nullable = false, length = 64)
    private String actionKey;

    @Column(name = "action_label", nullable = false, length = 100)
    private String actionLabel;
}
