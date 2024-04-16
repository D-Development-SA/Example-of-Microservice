package org.example.waste_manager_service.Service.Implements;

import org.example.waste_manager_service.Controller.Exception.BDExcepcion.NotExistException;
import org.example.waste_manager_service.Service.Contract.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public class GenericImpl<E, D extends CrudRepository<E, Long>> implements GenericService<E> {
    protected final D dao;

    public GenericImpl(D dao) {
        this.dao = dao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<E> findAll() {
        return (List<E>) dao.findAll();
    }

    @Override
    @Transactional
    public E save(E entity) {
        return dao.save(entity);
    }

    @Override
    @Transactional
    public List<E> saveAll(List<E> entity) {
        return (List<E>) dao.saveAll(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public E findById(long id) {
        if(id == 0) throw new NotExistException(id, String.valueOf(id));

        E e = dao.findById(id).orElse(null);

        if(e == null) throw new NotExistException(id, String.valueOf(id));

        return e;
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
    public void deleteAll(List<E> entities) {
        dao.deleteAll(entities);
    }

    @Override
    @Transactional
    public void deleteAllByIds(List<Long> ids) {
        dao.deleteAllById(ids);
    }
}
