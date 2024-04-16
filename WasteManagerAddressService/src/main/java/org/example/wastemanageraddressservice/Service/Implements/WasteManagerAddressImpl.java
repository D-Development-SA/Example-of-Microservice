package org.example.wastemanageraddressservice.Service.Implements;

import org.example.wastemanageraddressservice.Controller.Exception.BDExcepcion.NotExistException;
import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntity;
import org.example.wastemanageraddressservice.Entity.WasteManagerAddressEntityDto;
import org.example.wastemanageraddressservice.Repository.WasteManagerAddressRepository;
import org.example.wastemanageraddressservice.Service.Contract.WasteManagerAddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WasteManagerAddressImpl implements WasteManagerAddressService {
    
    @Autowired
    private WasteManagerAddressRepository dao;

    @Override
    @Transactional(readOnly = true)
    public List<WasteManagerAddressEntity> findAll() {
        return (List<WasteManagerAddressEntity>) dao.findAll();
    }

    @Override
    @Transactional
    public WasteManagerAddressEntity save(WasteManagerAddressEntity entity) {
        return dao.save(entity);
    }

    @Override
    @Transactional
    public List<WasteManagerAddressEntity> saveAll(List<WasteManagerAddressEntity> entity) {
        return (List<WasteManagerAddressEntity>) dao.saveAll(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public WasteManagerAddressEntity findById(long id) {
        if(id == 0) throw new NotExistException(id, String.valueOf(id));

        WasteManagerAddressEntity WasteManagerAddressEntity = dao.findById(id).orElse(null);

        if(WasteManagerAddressEntity == null) throw new NotExistException(id, String.valueOf(id));

        return WasteManagerAddressEntity;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    @Transactional
    public void deleteAll(List<WasteManagerAddressEntity> entities) {
        dao.deleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        dao.deleteAllById(ids);
    }
}
