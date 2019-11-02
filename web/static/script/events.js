let table;
let showCompleted = false;
const tableElement = $('#eventsTable');
const eventsDatesInput = $('#eventsDates');
const showCompletedCheckbox = $('#showCompleted');
let search = '';
let length = 8;
let page = 0;
let isPageSet = false;

let period = {
    isCustom: false,
    range: 'Today',
    customStart: moment().startOf('day'),
    customEnd: moment().add(1, 'day').startOf('day'),
    ranges: {
        'Next hour': [moment(), moment().add(1, 'hour')],
        'Today': [moment().startOf('day'), moment().endOf('day')],
        'Tomorrow': [moment().add(1, 'day').startOf('day'), moment().add(1, 'day').endOf('day')],
        'This week': [moment().startOf('week'), moment().endOf('week')]
    }
};

let paramMap = {
    'showCompleted': 'c',
    'period.isCustom': 'cp',
    'period.customStart': 'sd',
    'period.customEnd': 'ed',
    'period.range': 'pd',
    'length': 'l',
    'page': 'p',
    'search': 's'
};

$(document).ready(function() {
    getStateFromURL();
    eventsTableInit();
    setSearch();
    showCompletedCheckboxInit();
    eventDatesInit();
} );

function periodDates() {
    if (period.isCustom) {
        return {
            start: period.customStart,
            end: period.customEnd
        }
    }
    else {
        return {
            start: period.ranges[period.range][0],
            end: period.ranges[period.range][1]
        }
    }
}

function onTableChange ( e, settings, json, xhr ) {
    setFiltersToUrl();
}

function buildAjaxObject(data) {
    return {
        search: data.search.value,
        draw: data.draw,
        offset: data.start,
        limit: data.length,
        showCompleted: showCompleted,
        startDate: periodDates().start.format(),
        endDate: periodDates().end.format()
    }
}

function eventsTableInit() {
    table = tableElement.DataTable({
        dom: "<'tableHeaderRow'fl>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        processing: true,
        serverSide: true,
        searching: true,
        ordering: false,
        // scrollY: '300px',
        lengthChange: true,
        pageLength: length,
        lengthMenu: [8, 25, 50, 100],
        paging: true,
        // stateSave: true,
        ajax: {
            url: '/nurse/events-table',
            type: 'POST',
            data: function(data) {return buildAjaxObject(data)}
        },
        rowId: 'id',
        columns: [
                    {data: 'eventStatus', visible: showCompleted},
                    {data: 'dateFormatted'},
                    {data: 'timeFormatted'},
                    {data: 'patientFullName'},
                    {data: 'treatmentType'},
                    {data: 'treatmentName'},
                    {data: 'dosage'}
                ]
        })
        .on('xhr.dt', function ( e, settings, json, xhr ) {
            onTableChange(e, settings, json, xhr);
        } );

    $('#eventsTable tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openEvent(data.id);
    });
}

function onFiltersChange(reinit = true) {
    setFiltersToUrl();
    if (reinit) {
        eventsTableReInit();
    }
}

function setFiltersToUrl() {
    let paramStr = '?';
    paramStr += paramMap.showCompleted + '=' + showCompleted;
    paramStr += '&' + paramMap["period.isCustom"] +'=' + period.isCustom;
    if (period.isCustom) {
        paramStr += '&' + paramMap["period.customStart"] +'=' + period.customStart.format('YYYYMMDDTHHmmss');
        paramStr += '&' + paramMap["period.customEnd"] +'=' + period.customEnd.format('YYYYMMDDTHHmmss');
    }
    else {
        paramStr += '&' + paramMap["period.range"] +'=' + Object.keys(period.ranges).indexOf(period.range);
    }
    paramStr += '&' + paramMap.length +'=' + table.page.len();
    paramStr += '&' + paramMap.page +'=' + table.page();
    paramStr += '&' + paramMap.search +'=' + table.search();
    history.pushState(null, '', paramStr);
}

function getStateFromURL() {
    const params = new URL(window.location.href).searchParams;
    if (params.has(paramMap.showCompleted)) {
        let param = params.get(paramMap.showCompleted);
        if (param != null) {
            showCompleted = param === 'true';
        }
    }
    if (params.has(paramMap.search)) {
        let param = params.get(paramMap.search);
        if (param != null) {
            search = param;
        }
    }
    if (params.has(paramMap.page)) {
        let param = params.get(paramMap.page);
        if (param != null) {
            page = parseInt(param);
        }
    }
    if (params.has(paramMap.length)) {
        let param = params.get(paramMap.length);
        if (param != null) {
            length = parseInt(param);
        }
    }
    if (params.has(paramMap["period.range"])) {
        let param = params.get(paramMap["period.range"]);
        const rangesKeys = Object.keys(period.ranges);
        if (param != null && rangesKeys.length > parseInt(param)) {
            period.range = rangesKeys[parseInt(param)];
        }
    }
    if (params.has(paramMap["period.customEnd"])) {
        let param = params.get(paramMap["period.customEnd"]);
        if (param != null) {
            period.customEnd = moment(param);
        }
    }
    if (params.has(paramMap["period.customStart"])) {
        let param = params.get(paramMap["period.customStart"]);
        if (param != null) {
            period.customStart = moment(param);
        }
    }
    if (params.has(paramMap["period.isCustom"])) {
        let param = params.get(paramMap["period.isCustom"]);
        if (param != null) {
            period.isCustom = param === 'true';
        }
    }
}

function eventsTableReInit() {
    table.destroy();
    eventsTableInit();
}

function onShowCompletedChange() {
    showCompleted = showCompletedCheckbox.is(':checked');
    onFiltersChange();
}

function showCompletedCheckboxInit() {
    showCompletedCheckbox.prop('checked', showCompleted);
    showCompletedCheckbox.change(function () {
        onShowCompletedChange();
    });
}

function eventDatesInit() {
    eventsDatesInput.daterangepicker({
        "showDropdowns": true,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 10,
        ranges: period.ranges,
        "linkedCalendars": false,
        "opens": "left",
        "startDate": periodDates().start,
        "endDate": periodDates().end,
        locale: {
            format: 'DD\.MM HH:mm'
        }
    }, function(start, end, label) {
        onPeriodChange(start, end, label);
    });
}

function onPeriodChange(start, end, label) {
    period.isCustom = label === 'Custom Range';
    if (period.isCustom) {
        period.customStart = start;
        period.customEnd = end;
    }
    else {
        period.range = label;
    }
    onFiltersChange();
}

function openEvent(id) {
    window.location.assign('/nurse/event?id=' + id);
}

function setSearch() {
    if (search) {
        table.search(search).draw();
    }
    setTimeout(setPage, 200);
}

function setPage() {
    if (!isPageSet && page != null && page > 0) {
        isPageSet = true;
        table.page(page).draw('page');
    }
}