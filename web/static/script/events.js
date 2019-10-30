let table;
let showCompleted = false;
const tableElement = $('#eventsTable');
const eventsDatesInput = $('#eventsDates');
const showCompletedCheckbox = $('#showCompleted');
let startDate = moment().subtract(3, 'days').startOf('day');
let endDate = moment().add(1, 'day').startOf('day');

$(document).ready(function() {
    eventsTableInit();
    showCompletedCheckboxInit();
    eventDatesInit();
} );

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
        pageLength: 8,
        lengthMenu: [8, 25, 50, 100],
        paging: true,
        ajax: {
            url: '/nurse/events-table',
            type: 'POST',
            data: {
                "showCompleted": showCompleted,
                "startDate": startDate.format('DD-MM-YY HH:mm'),
                "endDate": endDate.format('DD-MM-YY HH:mm')
            }
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
    });

    $('#eventsTable tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openEvent(data.id);
    });
}

function showCompletedCheckboxInit() {
    showCompletedCheckbox.prop('checked', showCompleted);
    showCompletedCheckbox.change(function () {
        showCompleted = showCompletedCheckbox.is(':checked');
        table.destroy();
        eventsTableInit();
    });
}

function eventDatesInit() {
    eventsDatesInput.daterangepicker({
        "showDropdowns": true,
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 10,
        ranges: {
            'Today': [moment(), moment()]
        },
        "linkedCalendars": false,
        "opens": "left",
        "startDate": startDate.startOf('hour'),
        "endDate": endDate.startOf('hour'),
        locale: {
            format: 'DD\.MM HH:mm'
        }
    }, function(start, end, label) {
        startDate = start;
        endDate = end;
    });
}

function openEvent(id) {
    window.location.assign('/nurse/event?id=' + id);
}
