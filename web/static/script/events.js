let table;
var showCompleted = false;
const tableElement = $('#eventsTable');

$(document).ready(function() {
    eventsTableInit();
} );

function eventsTableInit() {
    table = tableElement.DataTable({
        dom: "<'tableHeaderRow'<'#eventsTableHeader'><'#showCompletedContainer'>l>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        processing: true,
        serverSide: true,
        searching: false,
        ordering: false,
        // scrollY: '300px',
        lengthChange: true,
        pageLength: 9,
        lengthMenu: [9, 25, 50, 100],
        paging: true,
        ajax: {
            url: '/nurse/events-table',
            type: 'POST',
            data: {
                "showCompleted": showCompleted
            }
        },
        rowId: 'id',
        columns: [
            {data: 'eventStatus'},
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

    table.on( 'draw', function () {
        const headerDiv = $('#eventsTableHeader');
        const showCompletedContainer = $('#showCompletedContainer');
        headerDiv.empty();
        headerDiv.append('<h1>Events</h1>');
        showCompletedContainer.empty();
        showCompletedContainer.append('' +
            '<div class="form-group"><input id="showCompleted" type="checkbox" class="form-check-input">' +
            '<label for="showCompleted" class="form-check-label" >' +
            'Show completed' +
            '</label></div>');
        const showCompletedCheckbox = $('#showCompleted');
        showCompletedCheckbox.prop('checked', showCompleted);
        showCompletedCheckbox.change(function () {
            showCompleted = showCompletedCheckbox.is(':checked');
            table.destroy();
            eventsTableInit();
        });
    } );
}

function openEvent(id) {
    window.location.assign('/nurse/event?id=' + id);
}
