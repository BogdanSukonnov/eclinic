let length = 8;
let prescriptionsTableId = '#prescriptionsTable';

$(document).ready(function() {
    prescriptionsTableInit();
} );

function prescriptionsTableInit() {
    const table = $(prescriptionsTableId).DataTable({
        dom: "<'tableHeaderRow'fl>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        processing: true,
        serverSide: true,
        lengthChange: true,
        paging: true,
        ordering: false,
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

    $(prescriptionsTableId + ' tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openPrescription(data.id);
    });
}

function openPrescription(id) {
    window.location.assign('/doctor/prescription?id=' + id);
}
