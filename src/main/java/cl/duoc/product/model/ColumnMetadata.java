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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "columns_metadata")
public class ColumnMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name", nullable = false, length = 64)
    private String tableName;

    @Column(name = "column_name", nullable = false, length = 64)
    private String columnName;

    @Column(name = "api_key", nullable = false, length = 64)
    private String apiKey;

    @Column(name = "value_key", length = 64)
    private String valueKey;

    @Column(nullable = false, length = 100)
    private String label;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "show_in_create", nullable = false)
    private boolean showInCreate;

    @Column(name = "show_in_edit", nullable = false)
    private boolean showInEdit;

    @Column(nullable = false)
    private boolean required;

    @Column(name = "is_read_only", nullable = false)
    private boolean readOnly;

    @Column(name = "is_searchable", nullable = false)
    private boolean searchable;

    @Column(name = "is_hidden", nullable = false)
    private boolean hidden;

    @Column(nullable = false)
    private boolean highlight;

    @Column(name = "is_state", nullable = false)
    private boolean state;
}
