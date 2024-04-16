package org.example.wastemanageraddressservice.Repository;

import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteManagerAddressRepository extends CrudRepository<WasteManagerAddressEntity, Long> {
}
