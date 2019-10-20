package com.bogdansukonnov.eclinic.dao;

import com.bogdansukonnov.eclinic.entity.TimePattern;
import org.springframework.stereotype.Repository;

@Repository
public class TimePatternDAO extends AbstractDAO<TimePattern> {

    public TimePatternDAO() {
        setClazz(TimePattern.class);
    }

}
