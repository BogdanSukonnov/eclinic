var calendar = null;
var nameEl = $('#name');
var calendarEl = document.getElementById('calendar');
var everyDaysEl = $('#everyDays');
var saveNewTimePatternBtn = $('#saveNewTimePatternBtn');
var table = null;
var addItemBtn = $('#addItemBtn');
var newDayTimeEl = $('#newDayTime');
var newDayNumberEl = $('#newDayNumber');
var newTimeErrorEl = $('#newTimeError');
var customLengthEl = $('#customLength');

document.addEventListener('DOMContentLoaded', function () {
    onPeriodTypeChange();
    $('input[name="periodType"]').click(function () {
        onPeriodTypeChange()
    });
    everyDaysEl.change(function () {
        constructName();
    });
    addItemBtn.click(function () {
        addItem();
    });
    newDayNumberEl.change(function () {
        onNewDayChange();
    });
    newDayTimeEl.change(function () {
        onNewDayChange();
    });
    nameEl.on('input', function () {
        onPatternChange();
    });
    customLengthEl.change(function () {
        onCustomLengthChange();
    });
    onNewDayChange();
    saveNewTimePatternBtn.click(function () {
        onSave();
    });
});

function getCustomLengthNumber() {
    let customLength = customLengthEl.val();
    if ((customLength) === 'Week') {
        customLength = 7;
    }
    return customLength;
}

function onCustomLengthChange() {
    let customLength = getCustomLengthNumber();
    // adjust new day number
    if (getNewDayNumber() > customLength) {
        newDayNumberEl.val(customLength);
    }
    let oldVal = newDayNumberEl.val();
    newDayNumberEl.empty();
    for (let i = 1; i <= customLength; i++) {
        newDayNumberEl.append('<option value=' + i + '>' + i + '</option>');
    }
    newDayNumberEl.val(oldVal);
    // delete all inappropriate data in table
    table
        .rows(function (idx, data, node) {
            return data.dayNumber > customLength;
        })
        .remove()
        .draw();
    // fire other events
    onPatternChange();
}

function onNewDayChange() {
    let canAdd = newTimeIsNotEmpty() && newTimeIsUnique();
    addItemBtn.attr('disabled', !canAdd);
    newTimeErrorEl.attr('hidden', canAdd);
    if (!canAdd) {
        if (!newTimeIsNotEmpty()) {
            newTimeErrorEl.text('please enter day number and time to add');
        } else if (!newTimeIsUnique()) {
            newTimeErrorEl.text('please enter unique pair of day number and time to add');
        }
    }
    onPatternChange();
}

function newTimeIsUnique() {
    let isUnique = true;
    let newDayNumber = getNewDayNumber();
    let newDayTime = getNewDayTime();
    table.rows().every(function (rowIdx, tableLoop, rowLoop) {
        let rowData = this.data();
        if (rowData.dayNumber === newDayNumber && rowData.time === newDayTime) {
            isUnique = false;
        }
    });
    return isUnique;
}

function newTimeIsNotEmpty() {
    return getNewDayNumber() > 0 && getNewDayTime() !== ''
}

function getNewDayNumber() {
    return newDayNumberEl.val();
}

function getNewDayTime() {
    return newDayTimeEl.val();
}

function addItem() {
    if (!newTimeIsNotEmpty() || !newTimeIsUnique()) {
        return
    }
    // actually add row
    table.row.add({
        dayNumber: getNewDayNumber(),
        time: getNewDayTime()
    }).draw();
    // sort table
    table.sort(function compare(a, b) {
        if (a.dayNumber < b.dayNumber) {
            return -1;
        }
        if (a.dayNumber > b.dayNumber) {
            return 1;
        }
        if (a.timeStr < b.timeStr) {
            return -1;
        }
        if (a.timeStr > b.timeStr) {
            return 1;
        }
        return 0;
    }).draw();
    onNewDayChange();
}

function onPeriodTypeChange() {
    const periodType = $('input[name="periodType"]:checked').val();
    $('.everyNDaysGroup').attr('hidden', periodType !== 'Every');
    $('.customPatternGroup').attr('hidden', periodType !== 'Custom');
    if (periodType === 'Daily') {
        calendarInit('timeGridDay', false);
    } else if (periodType === 'Weekly') {
        calendarInit('timeGridWeek', true);
    } else if (periodType === 'Every') {
        calendarInit('timeGridDay', false);
    } else if (periodType === 'Custom') {
        if (calendar !== null) {
            calendar.destroy();
        }
        if (table === null) {
            table = $('#newPatternTable').DataTable({
                dom: "<'tableHeaderRow'>" +
                    "<'row'<'col-sm-12'tr>>",
                lengthChange: false,
                paging: false,
                ordering: true,
                data: [],
                columns: [
                    {
                        name: 'dayNumber',
                        data: 'dayNumber'
                    },
                    {
                        name: 'time',
                        data: 'time'
                    }
                ]
            });
        }
        nameEl.val('');
    }
    onPatternChange();
}

function calendarInit(defaultViewName, showColumnHeader) {

    if (calendar !== null) {
        calendar.destroy();
    }

    calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: ['timeGrid', 'bootstrap', 'interaction'],
        defaultView: defaultViewName,
        allDaySlot: false,
        minTime: "09:00:00",
        maxTime: "22:00:00",
        eventOverlap: false,
        columnHeader: showColumnHeader,
        columnHeaderFormat: {weekday: 'short'},
        themeSystem: 'bootstrap',
        slotEventOverlap: false,
        scrollTime: "09:00:00",
        height: "auto",
        weekends: true,
        slotLabelFormat: {
            hour: '2-digit',
            minute: '2-digit',
            omitZeroMinute: false,
            hour12: false
        },
        header: {
            left: '',
            center: '',
            right: ''
        },
        eventTimeFormat: {
            hour: '2-digit',
            minute: '2-digit',
            omitZeroMinute: false,
            hour12: false
        },
        dateClick: function (info) {
            calendar.addEvent(
                {
                    start: info.date,
                    allDay: false,
                    editable: true
                }
            );
            onPatternChange();
        },
        eventClick: function (info) {
            info.event.remove();
            onPatternChange();
        }
    });

    calendar.render();
}

function onPatternChange() {
    constructName();
    saveBtnAppearance();
}

function saveBtnAppearance() {
    if (getPeriodType() === 'Custom') {
        saveNewTimePatternBtn.attr('disabled', table.rows().data().length === 0
            || nameEl.val() === '');
    } else {
        saveNewTimePatternBtn.attr('disabled', calendar.getEvents().length === 0
            || nameEl.val() === '');
    }
}

function getPeriodType() {
    return $('input[name="periodType"]:checked').val();
}

function constructName() {
    const periodType = getPeriodType();
    let nameStr = periodType + ' ';
    let formatStr = "HH:mm";
    if (periodType === 'Custom') {
        return;
    } else {
        if (periodType === 'Every') {
            nameStr = 'Every ' + everyDaysEl.val() + ' days ';
        } else if (periodType === 'Weekly') {
            nameStr = '';
            formatStr = 'ddd-HH:mm';
        }
        let times = calendar.getEvents().length;
        if (times > 1) {
            nameStr += times + ' times ';
        }
        if (periodType === 'Weekly') {
            nameStr += times > 1 ? ' a week:' : '';
        } else {
            nameStr += 'at';
        }
        for (let i = 0; i < times; ++i) {
            let separator = ', ';
            if (i === 0) {
                separator = ' ';
            } else if (i === times - 1) {
                separator = ' and ';
            }
            nameStr += separator + moment(calendar.getEvents()[i].start).format(formatStr);
        }
    }
    nameEl.val(nameStr);
}

function onSave() {
    let cycleLength = 1;
    let isWeekCycle = false;
    let items = [];

    if (getPeriodType() === 'Custom') {
        cycleLength = customLengthEl.val() === 'Week' ? 7 : customLengthEl.val();
        isWeekCycle = customLengthEl.val() === 'Week';
        table.rows().every(function (rowIdx, tableLoop, rowLoop) {
            let rowData = this.data();
            items.push({dayOfCycle: rowData.dayNumber - 1, time: rowData.time});
        });
    } else {
        if (getPeriodType() === 'Every') {
            cycleLength = everyDaysEl.val();
        } else if (getPeriodType() === 'Weekly') {
            cycleLength = 7;
            isWeekCycle = true;
        }
        for (let event of calendar.getEvents()) {
            items.push({
                time: event.start.toLocaleTimeString('en-US', {hour12: false, hour: '2-digit', minute: '2-digit'}),
                dayOfCycle: getPeriodType() === 'Weekly' ? event.start.getDay() : 0
            });
        }
    }

    let dto = {
        name: nameEl.val(),
        cycleLength: cycleLength,
        isWeekCycle: isWeekCycle,
        items: items
    };

    $.ajax({
        method: "PUT",
        contentType: "application/json",
        url: "/doctor/new-time-pattern",
        data: JSON.stringify(dto)
    })
        .done(function (msg) {
            window.location.assign('/doctor/time-pattern?id=' + msg.id);
        })
        .fail(function (msg) {
            console.log("fail");
        });

}