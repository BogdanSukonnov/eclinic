package com.bogdansukonnov.eclinic.dao;

import java.util.List;

public interface ITableDAO<T> extends IDAO<T> {

    String getQueryConditions(String search, Long parentId);

    List<T> getAll(String orderField, String search, int offset, int limit, Long parentId);

    Long getTotalFiltered(String search, Long parentId);

}
