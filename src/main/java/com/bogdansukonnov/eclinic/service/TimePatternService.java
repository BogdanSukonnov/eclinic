package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.converter.SelectorDataConverter;
import com.bogdansukonnov.eclinic.dao.SortBy;
import com.bogdansukonnov.eclinic.dao.TimePatternDAO;
import com.bogdansukonnov.eclinic.dto.SelectorDataDTO;
import com.bogdansukonnov.eclinic.dto.TimePatternDTO;
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
    public List<TimePatternDTO> getAll(SortBy sortBy) {
        return timePatternDAO.getAll(sortBy).stream()
                .map(timePattern -> modelMapper.map(timePattern, TimePatternDTO.class))
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
        return modelMapper.map(timePattern, TimePatternDTO.class);
    }

    @Transactional(readOnly = true)
    public SelectorDataDTO getAll(String search) {
        return selectorDataConverter.toDTO(timePatternDAO.getAll(search).stream()
                .map(t -> (SelectorData) t)
                .collect(Collectors.toList()));
    }

}
