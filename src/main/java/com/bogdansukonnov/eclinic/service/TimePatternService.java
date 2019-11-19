package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.SelectorDataConverter;
import com.bogdansukonnov.eclinic.dao.TimePatternDAO;
import com.bogdansukonnov.eclinic.dto.RequestTableDto;
import com.bogdansukonnov.eclinic.dto.SelectorDataDto;
import com.bogdansukonnov.eclinic.dto.TableDataDto;
import com.bogdansukonnov.eclinic.dto.TimePatternDto;
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
public class TimePatternService {

    private TimePatternDAO timePatternDAO;
    private ModelMapper modelMapper;
    private SelectorDataConverter selectorDataConverter;

    @Transactional(readOnly = true)
    public List<TimePatternDto> getAll(OrderType orderType) {
        return timePatternDAO.getAll(orderType).stream()
                .map(timePattern -> modelMapper.map(timePattern, TimePatternDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNew() {
        TimePattern timePattern = new TimePattern();
        timePatternDAO.create(timePattern);
    }

    @Transactional(readOnly = true)
    public TimePattern getOne(Long id) {
        return timePatternDAO.findOne(id);
    }

    @Transactional(readOnly = true)
    public SelectorDataDto getAll(String search) {
        return selectorDataConverter.toDto(timePatternDAO.getAll(search).stream()
                .map(t -> (SelectorData) t)
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    public TableDataDto getTable(RequestTableDto data) {

        List<TimePattern> patterns = timePatternDAO.getAll("name", data.getSearch(),
                data.getOffset(), data.getLimit(), null);

        Long totalFiltered = timePatternDAO.getTotalFiltered(data.getSearch(), null);

        List<TimePatternDto> list = patterns.stream()
                .map(timePattern -> modelMapper.map(timePattern, TimePatternDto.class))
                .collect(Collectors.toList());

        return new TableDataDto<>(list, data.getDraw(), totalFiltered, totalFiltered);
    }

}
