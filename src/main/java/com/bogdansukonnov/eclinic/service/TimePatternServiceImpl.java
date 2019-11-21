package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.SelectorDataConverter;
import com.bogdansukonnov.eclinic.converter.TimePatternConverter;
import com.bogdansukonnov.eclinic.dao.TimePatternDao;
import com.bogdansukonnov.eclinic.dto.*;
import com.bogdansukonnov.eclinic.entity.SelectorData;
import com.bogdansukonnov.eclinic.entity.TimePattern;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimePatternServiceImpl implements TimePatternService {

    private TimePatternDao timePatternDao;
    private ModelMapper modelMapper;
    private SelectorDataConverter selectorDataConverter;
    private TimePatternConverter timePatternConverter;

    @Override
    @Transactional(readOnly = true)
    public List<TimePatternDto> getAll(OrderType orderType) {
        return timePatternDao.getAll(orderType).stream()
                .map(timePattern -> modelMapper.map(timePattern, TimePatternDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public IdDto addNew(TimePatternDto dto) {
        TimePattern timePattern = timePatternConverter.toEntity(dto);
        return new IdDto(timePatternDao.create(timePattern).getId());
    }

    @Override
    @Transactional(readOnly = true)
    public TimePatternDto getOne(Long id) {
        return timePatternConverter.toDto(timePatternDao.findOne(id));
    }

    @Override
    @Transactional(readOnly = true)
    public SelectorDataDto getAll(String search) {
        return selectorDataConverter.toDto(timePatternDao.getAll(search).stream()
                .map(t -> (SelectorData) t)
                .collect(Collectors.toList()));
    }

    @Override
    @Transactional(readOnly = true)
    public TableDataDto getTimePatternTable(RequestTableDto data) {

        List<TimePattern> patterns = timePatternDao.getAll("name", data.getSearch(),
                data.getOffset(), data.getLimit(), null);

        Long totalFiltered = timePatternDao.getTotalFiltered(data.getSearch(), null);

        List<TimePatternDto> list = patterns.stream()
                .map(timePattern -> modelMapper.map(timePattern, TimePatternDto.class))
                .collect(Collectors.toList());

        return new TableDataDto<>(list, data.getDraw(), totalFiltered, totalFiltered);
    }

}
