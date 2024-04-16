package org.example.waste_manager_service.Entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public record WasteManagerAddressEntity(
        long id,
        String address,
        boolean isEnabled,
        long version,
        Date createdDate,
        Date lastModifiedDate
) {}
