package org.example.waste_manager_service.Repository;

import org.example.waste_manager_service.Entity.WasteCenterAuthorizationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteCenterAuthorizationRepository extends CrudRepository<WasteCenterAuthorizationEntity, Long> {
}