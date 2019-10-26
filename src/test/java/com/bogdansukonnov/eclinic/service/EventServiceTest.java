package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.dao.EventDAO;
import com.bogdansukonnov.eclinic.entity.TimePatternItem;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class EventServiceTest {

    @Test
    void patternDates() {

        EventService service = new EventService(new EventDAO(), new ModelMapper());

        List<ItemData> dataList = new ArrayList<>();
        dataList.add(new ItemData(1, 9, 0));
        dataList.add(new ItemData(1, 17, 0));
        List<TimePatternItem> items = items(dataList);

        LocalDate periodStart = LocalDate.of(2019, 10, 26);
        LocalDateTime endDate = LocalDateTime.of(2019, 10, 30, 0, 0);
        LocalDateTime now = LocalDateTime.of(2019, 10, 26, 10, 0);

        List<LocalDateTime> dates = service.patternDates(items, periodStart, endDate, (short) 1, false, now);

        System.out.println(dates);
    }

    private List<TimePatternItem> items(List<ItemData> dataList) {
        List<TimePatternItem> items = new ArrayList<>();
        for (ItemData data : dataList) {
            TimePatternItem item = new TimePatternItem();
            item.setTime(LocalTime.of(data.hour, data.minute));
            item.setDayOfCycle((short) data.dayOfCycle);
            items.add(item);
        }
        return items;
    }

    @AllArgsConstructor
    private  class ItemData {
        int dayOfCycle;
        int hour;
        int minute;
    }

}
