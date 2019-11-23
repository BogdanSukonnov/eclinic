package com.bogdansukonnov.eclinic.service;

import com.bogdansukonnov.eclinic.entity.TimePatternItem;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class EventServiceImplPatternDatesTest {

    private EventService service;

    @BeforeEach
    void init() {
        service = new EventServiceImpl();
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

        List<LocalDateTime> rightDates = Arrays.asList(
                LocalDateTime.parse("2019-10-02T18:00"),
                LocalDateTime.parse("2019-10-03T09:00"),
                LocalDateTime.parse("2019-10-03T18:00"));

        assertEquals(dates, rightDates);
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

        List<LocalDateTime> rightDates = Arrays.asList(
                LocalDateTime.parse("2019-10-08T15:00"),
                LocalDateTime.parse("2019-10-10T15:00"),
                LocalDateTime.parse("2019-10-15T15:00"),
                LocalDateTime.parse("2019-10-17T15:00"));

        assertEquals(dates, rightDates);

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
