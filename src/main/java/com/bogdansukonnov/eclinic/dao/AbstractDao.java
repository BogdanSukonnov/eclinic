package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.service.OrderType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractDao<T> {

    private Class<T> clazz;

    @Autowired
    SessionFactory sessionFactory;

    public void setClazz(Class<T> clazzToSet){
        this.clazz = clazzToSet;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    @Transactional(readOnly = true)
    public T findOne(long id){
        return (T) getCurrentSession().get(clazz, id);
    }

    @Transactional(readOnly = true)
    public List<T> getAll(OrderType orderType) {
        String orderField;
        if (orderType == OrderType.NAME) {
            orderField = getOrderField();
        }
        else {
            orderField = "createdDateTime desc";
        }
        String queryStr = "from " + clazz.getName() + " order by " + orderField;
        return getCurrentSession().createQuery(queryStr).list();
    }

    @Transactional
    public T create(T entity) {
        getCurrentSession().saveOrUpdate(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    @Transactional
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
