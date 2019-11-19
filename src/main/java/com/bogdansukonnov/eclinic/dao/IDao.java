package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.service.OrderType;

import java.util.List;

public interface IDao<T> {

    void setClazz(Class<T> clazzToSet);

    T findOne(long id);

    List<T> getAll(OrderType orderType);

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(long entityId);
}
