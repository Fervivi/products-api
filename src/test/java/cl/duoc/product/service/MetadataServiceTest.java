/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import cl.duoc.product.dto.response.MetadataDto;
import cl.duoc.product.model.ColumnMetadata;
import cl.duoc.product.model.EntityMetadata;
import cl.duoc.product.model.EstadoProducto;
import cl.duoc.product.repository.ColumnMetadataRepository;
import cl.duoc.product.repository.EntityMetadataRepository;
import cl.duoc.product.repository.EstadoProductoRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class MetadataServiceTest {

    @Mock
    private EntityMetadataRepository entityMetadataRepository;

    @Mock
    private EstadoProductoRepository estadoProductoRepository;

    @Mock
    private ColumnMetadataRepository columnMetadataRepository;

    @InjectMocks
    private MetadataService metadataService;

    private EntityMetadata entityMetadata;
    private ColumnMetadata categoriaColumn;
    private ColumnMetadata nombreColumn;

    @BeforeEach
    void setUp() {
        entityMetadata = new EntityMetadata();
        entityMetadata.setTableName("productos");
        entityMetadata.setEntityKey("producto");
        entityMetadata.setSingleLabel("Producto");
        entityMetadata.setPluralLabel("Productos");
        entityMetadata.setCategoryField("categoria");
        entityMetadata.setCategoryLabel("Categoría");
        entityMetadata.setCategoryAddTitle("Nueva Categoría");
        entityMetadata.setCategoryIcons(List.of("🐾", "🐕"));
        entityMetadata.setDeleteNameKey("nombre");
        entityMetadata.setDeleteDetailKey("sku");
        entityMetadata.setDeleteDetailLabel("SKU");
        entityMetadata.setActionKey("accion");
        entityMetadata.setActionLabel("Acciones");

        categoriaColumn = new ColumnMetadata();
        categoriaColumn.setId(5L);
        categoriaColumn.setTableName("productos");
        categoriaColumn.setColumnName("categoria_id");
        categoriaColumn.setApiKey("categoria");
        categoriaColumn.setLabel("Categoría");
        categoriaColumn.setType("string");
        categoriaColumn.setSortOrder(5);
        categoriaColumn.setShowInCreate(true);
        categoriaColumn.setShowInEdit(true);
        categoriaColumn.setRequired(true);
        categoriaColumn.setSearchable(true);

        nombreColumn = new ColumnMetadata();
        nombreColumn.setId(2L);
        nombreColumn.setTableName("productos");
        nombreColumn.setColumnName("nombre");
        nombreColumn.setApiKey("nombre");
        nombreColumn.setLabel("Producto");
        nombreColumn.setType("string");
        nombreColumn.setSortOrder(2);
        nombreColumn.setShowInCreate(true);
        nombreColumn.setShowInEdit(true);
        nombreColumn.setRequired(true);
        nombreColumn.setSearchable(true);
        nombreColumn.setHighlight(true);
    }

    @Test
    void getMetadata_shouldMapEntityStatesAndColumns() {
        EstadoProducto estado = new EstadoProducto(1L, "activo", "Activo", "Disponible para la venta");
        when(entityMetadataRepository.findByTableName("productos")).thenReturn(Optional.of(entityMetadata));
        when(estadoProductoRepository.findAll(any(Sort.class))).thenReturn(List.of(estado));
        when(columnMetadataRepository.findAllByTableNameOrderBySortOrderAscIdAsc("productos"))
                .thenReturn(List.of(categoriaColumn, nombreColumn));

        MetadataDto metadata = metadataService.getMetadata();

        assertThat(metadata.getEntity().getKey()).isEqualTo("producto");
        assertThat(metadata.getCategory().getField()).isEqualTo("categoria");
        assertThat(metadata.getDelete().getDetailLabel()).isEqualTo("SKU");
        assertThat(metadata.getAction().getLabel()).isEqualTo("Acciones");
        assertThat(metadata.getStates()).hasSize(2);
        assertThat(metadata.getStates().getFirst().getKey()).isEqualTo("todos");
        assertThat(metadata.getStates().getFirst().getIsDefault()).isTrue();
        assertThat(metadata.getStates().get(1).getId()).isEqualTo(1L);
        assertThat(metadata.getStates().get(1).getLabel()).isEqualTo("Activo");
        assertThat(metadata.getColumns()).hasSize(2);
        assertThat(metadata.getColumns().getFirst().getKey()).isEqualTo("categoria");
    }

    private MetadataDto.ColumnMetadata categoriaColumn() {
        return new MetadataDto.ColumnMetadata(
                categoriaColumn.getApiKey(),
                categoriaColumn.getLabel(),
                categoriaColumn.getType(),
                categoriaColumn.isShowInCreate(),
                categoriaColumn.isShowInEdit(),
                categoriaColumn.isRequired(),
                categoriaColumn.isReadOnly(),
                categoriaColumn.isSearchable(),
                categoriaColumn.isHidden(),
                categoriaColumn.isHighlight(),
                categoriaColumn.isState());
    }

    private MetadataDto.ColumnMetadata nombreColumn() {
        return new MetadataDto.ColumnMetadata(
                nombreColumn.getApiKey(),
                nombreColumn.getLabel(),
                nombreColumn.getType(),
                nombreColumn.isShowInCreate(),
                nombreColumn.isShowInEdit(),
                nombreColumn.isRequired(),
                nombreColumn.isReadOnly(),
                nombreColumn.isSearchable(),
                nombreColumn.isHidden(),
                nombreColumn.isHighlight(),
                nombreColumn.isState());
    }
}
