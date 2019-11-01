$(document).ready(function() {
    prescriptionsTableInit();
} );

function prescriptionsTableInit() {
    const table = $('#prescriptionsTable').DataTable({
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
            {data: 'dateTimeFormatted'},
            {data: 'status'},
            {data: 'patient.fullName'},
            {data: 'treatment.type'},
            {data: 'treatment.name'},
            {data: 'dosage'},
            {data: 'timePattern.name'},
            {data: 'duration'},
            {data: 'doctorFullName'}
        ]
    });

    $('#prescriptionsTable tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openPrescription(data.id);
    });
}

function openPrescription(id) {
    window.location.assign('/doctor/prescription?id=' + id);
}
