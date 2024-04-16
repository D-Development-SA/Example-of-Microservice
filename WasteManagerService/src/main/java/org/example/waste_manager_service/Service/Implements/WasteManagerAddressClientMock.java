package org.example.waste_manager_service.Service.Implements;

import org.example.waste_manager_service.Entity.WasteManagerAddressEntity;
import org.example.waste_manager_service.Service.Contract.WasteManagerAddressClient;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Profile("test")
@Service
public class WasteManagerAddressClientMock implements WasteManagerAddressClient {
    @Override
    public List<WasteManagerAddressEntity> findAll() {
        return null;
    }

    @Override
    public WasteManagerAddressEntity findById(long id) {
        return new WasteManagerAddressEntity(1L, "address", true, 10L, new Date(), new Date());
    }

    @Override
    public long create(WasteManagerAddressEntity wasteManager) {
        return 0;
    }

    @Override
    public WasteManagerAddressEntity update(WasteManagerAddressEntity managerAddressEntity, long id) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(long id) {
        return null;
    }
}
