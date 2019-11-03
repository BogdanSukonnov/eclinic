package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.entity.TimePatternItem;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class EventServiceTest {

    private EventService service;

    @BeforeEach
    void init() {
        service = new EventService();
    }

    @Test
    void twiceADay() {

        List<ItemData> dataList = new ArrayList<>();
        dataList.add(new ItemData(0, 9, 0));
        dataList.add(new ItemData(0, 18, 0));
        List<TimePatternItem> items = items(dataList);

        LocalDateTime periodStart = LocalDateTime.of(2019, 10, 1, 0 , 0);
        LocalDateTime endDate = LocalDateTime.of(2019, 10, 4, 0, 0);
        LocalDateTime notSooner = LocalDateTime.of(2019, 10, 2, 10, 0);

        List<LocalDateTime> dates = service.patternDates(items, periodStart, endDate, (short) 1, false, notSooner);

        assert dates.toString().equals("[2019-10-02T18:00, 2019-10-03T09:00, 2019-10-03T18:00]");

    }

    @Test
    void twiceAWeek() {

        List<ItemData> dataList = new ArrayList<>();
        dataList.add(new ItemData(1, 15, 0)); // tuesday
        dataList.add(new ItemData(3, 15, 0)); // thursday
        List<TimePatternItem> items = items(dataList);

        LocalDateTime periodStart = LocalDateTime.of(2019, 10, 4, 0 , 0);
        LocalDateTime endDate = LocalDateTime.of(2019, 10, 18, 0, 0);
        LocalDateTime notSooner = LocalDateTime.of(2019, 10, 4, 0, 0);

        List<LocalDateTime> dates = service.patternDates(items, periodStart, endDate, (short) 7, true, notSooner);

        assert dates.toString().equals("[2019-10-08T15:00, 2019-10-10T15:00, 2019-10-15T15:00, 2019-10-17T15:00]");

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
    private static class ItemData {
        int dayOfCycle;
        int hour;
        int minute;
    }

}
