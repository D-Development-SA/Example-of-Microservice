package org.example.wastemanageraddressservice.Service.Contract;

import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntity;
import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntityDto;

import java.util.List;

public interface WasteManagerAddressService{
    List<WasteManagerAddressEntity> findAll();
    WasteManagerAddressEntity save(WasteManagerAddressEntity entity);
    List<WasteManagerAddressEntity> saveAll(List<WasteManagerAddressEntity> entity);
    WasteManagerAddressEntity findById(long id);
    void deleteById(long id);
    void deleteAll();
    void deleteAll(List<WasteManagerAddressEntity> entities);
    void deleteAllByIds(List<Long> ids);
}
