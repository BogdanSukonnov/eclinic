package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dto.*;

import java.util.List;

public interface TimePatternService {

    List<TimePatternDto> getAll(OrderType orderType);

    IdDto addNew(TimePatternDto dto);

    TimePatternDto getOne(Long id);

    SelectorDataDto getAll(String search);

    TableDataDto getTimePatternTable(RequestTableDto data);
}
