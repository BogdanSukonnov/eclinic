package com.bogdansukonnov.eclinic.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OldAbstractDAO<T>  {

    private Class<T> clazz;

    @Autowired
    SessionFactory sessionFactory;

    public void setClazz(Class< T > clazzToSet){
        this.clazz = clazzToSet;
    }

    public T findOne(long id){
        return (T) getCurrentSession().get(clazz, id);
    }

    public List<T> getAll(SortBy sortBy) {
        String orderField;
        if (sortBy == SortBy.NAME) {
            orderField = getOrderField();
        }
        else {
            orderField = "createdDateTime desc";
        }
        String queryStr = "from " + clazz.getName() + " order by " + orderField;
        return getCurrentSession().createQuery(queryStr).list();
    }

    public T create(T entity) {
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    public T update(T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

    protected String getOrderField() {
        return "id";
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
