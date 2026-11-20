/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.service;

import cl.duoc.product.dto.response.MetadataDto;
import cl.duoc.product.model.ColumnMetadata;
import cl.duoc.product.model.EntityMetadata;
import cl.duoc.product.model.EstadoProducto;
import cl.duoc.product.repository.ColumnMetadataRepository;
import cl.duoc.product.repository.EntityMetadataRepository;
import cl.duoc.product.repository.EstadoProductoRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MetadataService {

    private static final String TABLA_PRODUCTOS = "productos";

    private final EntityMetadataRepository entityMetadataRepository;
    private final EstadoProductoRepository estadoProductoRepository;
    private final ColumnMetadataRepository columnMetadataRepository;

    @Transactional(readOnly = true)
    public MetadataDto getMetadata() {
        EntityMetadata entityMetadata = getEntityMetadata();
        MetadataDto metadata = new MetadataDto();

        metadata.setEntity(mapEntity(entityMetadata));
        metadata.setCategory(mapCategory(entityMetadata));
        metadata.setDelete(mapDelete(entityMetadata));
        metadata.setAction(mapAction(entityMetadata));
        metadata.setStates(getStates());
        metadata.setColumns(getColumns());

        return metadata;
    }

    private EntityMetadata getEntityMetadata() {
        return entityMetadataRepository
                .findByTableName(TABLA_PRODUCTOS)
                .orElseThrow(
                        () -> new IllegalStateException("No hay metadata de entidad para la tabla " + TABLA_PRODUCTOS));
    }

    private MetadataDto.EntityMetadata mapEntity(EntityMetadata entityMetadata) {
        return new MetadataDto.EntityMetadata(
                entityMetadata.getEntityKey(), entityMetadata.getSingleLabel(), entityMetadata.getPluralLabel());
    }

    private MetadataDto.CategoryMetadata mapCategory(EntityMetadata entityMetadata) {
        return new MetadataDto.CategoryMetadata(
                entityMetadata.getCategoryField(),
                entityMetadata.getCategoryLabel(),
                entityMetadata.getCategoryAddTitle(),
                entityMetadata.getCategoryIcons());
    }

    private MetadataDto.DeleteMetadata mapDelete(EntityMetadata entityMetadata) {
        return new MetadataDto.DeleteMetadata(
                entityMetadata.getDeleteNameKey(),
                entityMetadata.getDeleteDetailKey(),
                entityMetadata.getDeleteDetailLabel());
    }

    private MetadataDto.ActionMetadata mapAction(EntityMetadata entityMetadata) {
        return new MetadataDto.ActionMetadata(entityMetadata.getActionKey(), entityMetadata.getActionLabel());
    }

    private List<MetadataDto.StateMetadata> getStates() {
        List<MetadataDto.StateMetadata> states = new ArrayList<>();
        states.add(new MetadataDto.StateMetadata(0L, "todos", "Todos los estados", "Todos los estados", Boolean.TRUE));

        List<EstadoProducto> entityStates = estadoProductoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (EstadoProducto entityState : entityStates) {
            states.add(new MetadataDto.StateMetadata(
                    entityState.getId(),
                    entityState.getNombre(),
                    entityState.getLabel(),
                    entityState.getDescripcion(),
                    null));
        }

        return states;
    }

    private List<MetadataDto.ColumnMetadata> getColumns() {
        List<ColumnMetadata> columns =
                columnMetadataRepository.findAllByTableNameOrderBySortOrderAscIdAsc(TABLA_PRODUCTOS);

        if (columns.isEmpty()) {
            log.warn("No hay columnas de metadata registradas para la tabla {}", TABLA_PRODUCTOS);
        }

        return columns.stream()
                .map(column -> new MetadataDto.ColumnMetadata(
                        column.getApiKey(),
                        column.getValueKey(),
                        column.getLabel(),
                        column.getType(),
                        column.isShowInCreate(),
                        column.isShowInEdit(),
                        column.isRequired(),
                        column.isReadOnly(),
                        column.isSearchable(),
                        column.isHidden(),
                        column.isHighlight(),
                        column.isState()))
                .toList();
    }
}
