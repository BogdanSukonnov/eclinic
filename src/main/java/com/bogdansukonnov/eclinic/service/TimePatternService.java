package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TimePatternService {
    @Transactional(readOnly = true)
    List<TimePatternDto> getAll(OrderType orderType);

    @Transactional
    IdDto addNew(TimePatternDto dto);

    @Transactional(readOnly = true)
    TimePatternDto getOne(Long id);

    @Transactional(readOnly = true)
    SelectorDataDto getAll(String search);

    @Transactional(readOnly = true)
    TableDataDto getTimePatternTable(RequestTableDto data);
}
