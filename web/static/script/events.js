
$(document).ready(function() {

    var table = $('#eventsTable').DataTable( {
        processing: true,
        serverSide: true,
        lengthChange: false,
        paging: false,
        ajax: {
            url: '/nurse/events-table',
            type: 'POST'
        },
        rowId: 'id',
        columns: [
            { data: 'dateFormatted' },
            { data: 'timeFormatted' },
            { data: 'patientFullName' },
            { data: 'treatmentType' },
            { data: 'treatmentName' },
            { data: 'dosage' }
        ]
    } );

    $('#eventsTable tbody').on('click', 'tr', function () {
        var data = table.row( this ).data();
        openEvent(data.id);
    } );

} );

function openEvent(id) {
    window.location.assign('/nurse/event?id=' + id);
}
