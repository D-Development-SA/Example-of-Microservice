package org.example.waste_manager_service.Entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link WasteManagerEntity}
 */
public record WasteManagerEntityDto(@NotNull @NotEmpty String name, @NotNull @NotEmpty String nif,
                                    @PositiveOrZero long idWasteManagerAddressEntity, boolean isEnabled,
                                    @PositiveOrZero long version,
                                    WasteManagerAddressEntity wasteManagerAddressEntity,
                                    @NotNull Set<WasteCenterAuthorizationEntity> wasteCenterAuthorizationEntities) implements Serializable {
}