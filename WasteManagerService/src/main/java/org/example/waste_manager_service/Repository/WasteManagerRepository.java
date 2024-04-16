package org.example.waste_manager_service.Repository;

import org.example.waste_manager_service.Entity.WasteManagerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteManagerRepository extends CrudRepository<WasteManagerEntity, Long> {
}
