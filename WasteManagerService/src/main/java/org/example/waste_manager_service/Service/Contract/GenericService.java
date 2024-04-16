package org.example.waste_manager_service.Service.Contract;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface GenericService <E>{
    List<E> findAll();
    E save(E entity);
    List<E> saveAll(List<E> entity);
    E findById(long id);
    void deleteById(long id);
    void deleteAll();
    void deleteAll(List<E> entities);
    void deleteAllByIds(List<Long> ids);
}
