/*
 * Copyright © 2026 DuocUC FullStack 1
 * Eduardo Bray
 * Rodrigo Callealta
 * Fernando Villalobos
 */
package cl.duoc.product.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDto {

    private EntityMetadata entity;
    private CategoryMetadata category;
    private DeleteMetadata delete;
    private ActionMetadata action;
    private List<StateMetadata> states;
    private List<ColumnMetadata> columns;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EntityMetadata {
        private String key;
        private String singleLabel;
        private String pluralLabel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryMetadata {
        private String field;
        private String label;
        private String addTitle;
        private List<String> icons;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteMetadata {
        private String nameKey;
        private String detailKey;
        private String detailLabel;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActionMetadata {
        private String key;
        private String label;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StateMetadata {
        private Long id;
        private String key;
        private String label;
        private String description;

        @JsonProperty("isDefault")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean isDefault;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ColumnMetadata {
        private String key;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String valueKey;

        private String label;
        private String type;
        private Boolean showInCreate;
        private Boolean showInEdit;
        private Boolean required;

        @JsonProperty("isReadOnly")
        private Boolean isReadOnly;

        @JsonProperty("isSearchable")
        private Boolean isSearchable;

        @JsonProperty("isHidden")
        private Boolean isHidden;

        private Boolean highlight;

        @JsonProperty("isState")
        private Boolean isState;
    }
}
