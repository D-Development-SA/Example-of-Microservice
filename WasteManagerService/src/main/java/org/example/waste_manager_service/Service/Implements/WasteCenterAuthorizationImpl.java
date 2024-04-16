package org.example.waste_manager_service.Service.Implements;

import org.example.waste_manager_service.Entity.WasteCenterAuthorizationEntity;
import org.example.waste_manager_service.Repository.WasteCenterAuthorizationRepository;
import org.example.waste_manager_service.Service.Contract.WasteCenterAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WasteCenterAuthorizationImpl extends GenericImpl<WasteCenterAuthorizationEntity, WasteCenterAuthorizationRepository> implements WasteCenterAuthorizationService {
    @Autowired
    public WasteCenterAuthorizationImpl(WasteCenterAuthorizationRepository dao) {
        super(dao);
    }
}
