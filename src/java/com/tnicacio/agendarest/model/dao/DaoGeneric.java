package com.tnicacio.agendarest.model.dao;

import java.util.List;

/**
 *
 * @author tnica
 * @param <T>
 */
public interface DaoGeneric<T> {
    
    void insert(T obj);
    void update(T obj, Long id);
    void delete(Long id);
    T findById(Long id);
    List<T> findAll();
    
}
