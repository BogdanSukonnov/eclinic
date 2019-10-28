
$(document).ready(function() {

    var table = $('#prescriptionsTable').DataTable( {
        processing: true,
        serverSide: true,
        lengthChange: false,
        paging: false,
        ajax: {
            url: '/doctor/prescriptionsTable',
            type: 'POST'
        },
        rowId: 'id',
        columns: [
            { data: 'dateTimeFormatted' },
            { data: 'patient.fullName' },
            { data: 'treatment.type' },
            { data: 'treatment.name' },
            { data: 'dosage' },
            { data: 'timePattern.name' },
            { data: 'duration' },
            { data: 'doctorFullName' }
        ]
    } );

    $('#prescriptionsTable tbody').on('click', 'tr', function () {
        var data = table.row( this ).data();
        openPrescription(data.id);
    } );

} );

function openPrescription(id) {
    window.location.assign('/doctor/prescription?id=' + id);
}
