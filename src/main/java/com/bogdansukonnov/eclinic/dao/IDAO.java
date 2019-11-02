package com.bogdansukonnov.eclinic.dao;

import java.util.List;

public interface IDAO<T> {

    void setClazz(Class<T> clazzToSet);

    T findOne(long id);

    List<T> getAll(SortBy sortBy);

    T create(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(long entityId);
}
