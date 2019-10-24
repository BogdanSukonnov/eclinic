package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.EventDAO;
import com.bogdansukonnov.eclinic.dto.EventDTO;
import com.bogdansukonnov.eclinic.entity.Prescription;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {

    private EventDAO eventDAO;

    private ModelMapper modelMapper;

    public List<EventDTO> getAll(Prescription prescription) {
        return eventDAO.getAll(prescription).stream()
                .map(event -> modelMapper.map(event, EventDTO.class))
                .collect(Collectors.toList());
    }

}
