package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.TimePattern;

import java.util.List;

public interface TimePatternDao extends TableDao<TimePattern> {
    List<TimePattern> getAll(String search);

    @Override
    String getQueryConditions(String search, Long parentId);
}
