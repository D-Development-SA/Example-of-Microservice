package org.example.waste_manager_service.Service.Implements;

import org.example.waste_manager_service.Entity.WasteManagerEntity;
import org.example.waste_manager_service.Entity.WasteManagerEntityDto;
import org.example.waste_manager_service.Repository.WasteManagerRepository;
import org.example.waste_manager_service.Service.Contract.WasteManagerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WastedManagerImpl extends GenericImpl<WasteManagerEntity, WasteManagerRepository> implements WasteManagerService {

    @Autowired
    public WastedManagerImpl(WasteManagerRepository dao) {
        super(dao);
    }

}
