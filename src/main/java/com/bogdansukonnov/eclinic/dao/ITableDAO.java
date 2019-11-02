package com.bogdansukonnov.eclinic.dao;

import java.util.List;

public interface ITableDAO<T> extends IDAO<T> {

    String getQueryConditions(String search);

    public List<T> getAll(String orderField, String search, int offset, int limit);

    public Long getCount(String search);

}
