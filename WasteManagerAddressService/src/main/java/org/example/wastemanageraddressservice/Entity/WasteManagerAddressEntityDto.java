package org.example.wastemanageraddressservice.Entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;

/**
 * DTO for {@link WasteManagerAddressEntity}
 */
public record WasteManagerAddressEntityDto(@NotNull @NotEmpty String address, boolean isEnabled,
                                           @Positive long version) implements Serializable {
}