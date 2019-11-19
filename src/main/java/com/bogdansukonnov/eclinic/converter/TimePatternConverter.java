package com.bogdansukonnov.eclinic.converter;

import com.bogdansukonnov.eclinic.dto.TimePatternDto;
import com.bogdansukonnov.eclinic.entity.TimePattern;
import com.bogdansukonnov.eclinic.entity.TimePatternItem;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

@Component
public class TimePatternConverter {

    public TimePattern toEntity(TimePatternDto dto) {
        TimePattern timePattern = new TimePattern();
        timePattern.setCycleLength(dto.getCycleLength());
        timePattern.setIsWeekCycle(dto.getIsWeekCycle());
        timePattern.setName(dto.getName());
        timePattern.setItems(dto.getItems().stream()
                .map(itemDto -> {
                    TimePatternItem item = new TimePatternItem();
                    item.setDayOfCycle(itemDto.getDayOfCycle());
                    item.setTimePattern(timePattern);
                    item.setTime(LocalDateTime.ofInstant(itemDto.getTime().toInstant(),
                            ZoneId.systemDefault()).toLocalTime());
                    return item;
                })
                .collect(Collectors.toList()));
        return timePattern;
    }

}
