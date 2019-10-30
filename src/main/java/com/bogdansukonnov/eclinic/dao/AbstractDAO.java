package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.TableData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AbstractDAO<T>  {

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

    public TableData<T> getTableData(SortBy sortBy, Integer start, Integer length) {
        // count rows
        String countQueryStr = "Select count (t.id) from " + clazz.getName() + " t";
        Query countQuery = getCurrentSession().createQuery(countQueryStr);
        Long countResults = (Long) countQuery.uniqueResult();

        // get sort column
        String orderField;
        if (sortBy == SortBy.NAME) {
            orderField = getOrderField();
        }
        else {
            orderField = "createdDateTime desc";
        }

        // do query
        String queryStr = "from " + clazz.getName() + " order by " + orderField;
        Query query = getCurrentSession().createQuery(queryStr)
                .setFirstResult(start)
                .setMaxResults(length);
        List<T> data = query.list();

        // to table data
        TableData<T> tableData = new TableData<>(data, 0, countResults, countResults);

        return tableData;
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
