package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.dto.TableDataDTO;
import com.bogdansukonnov.eclinic.entity.Patient;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Map;

public interface ITableDAO<T> extends IDAO<T> {

    String getQueryConditions(String search);

    public List<T> getAll(String orderField, String search, int offset, int limit);

    public Long getCount(String search);

}
