package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.TimePatternConverter;
import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dao.TimePatternDAO;
import com.bogdansukonnov.eclinic.dto.TimePatternDTO;
import com.bogdansukonnov.eclinic.entity.TimePattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimePatternService {

    private TimePatternDAO timePatternDAO;

    private TimePatternConverter converter;

    @Transactional(readOnly = true)
    public List<TimePatternDTO> getAll(SortBy sortBy) {
        return timePatternDAO.getAll(sortBy).stream()
                .map(timePattern -> converter.toDTO(timePattern))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNew() {
        TimePattern timePattern = new TimePattern();
        timePatternDAO.create(timePattern);
    }

    @Transactional(readOnly = true)
    public TimePatternDTO getOne(Long id) {
        TimePattern timePattern = timePatternDAO.findOne(id);
        return converter.toDTO(timePattern);
    }

}
