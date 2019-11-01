let length = 8;
let patientsTableId = '#patientsTable';

$(document).ready(function() {
    patientsTableInit();
} );

function patientsTableInit() {
    const table = $(patientsTableId).DataTable({
        processing: true,
        serverSide: true,
        lengthChange: true,
        paging: true,
        ordering: false,
        pageLength: length,
        lengthMenu: [length, 25, 50, 100],
        paging: true,
        stateSave: true,
        ajax: {
            url: '/doctor/patients-table',
            type: 'POST'
        },
        rowId: 'id',
        columns: [
            {data: 'fullName'},
            {data: 'patientStatus'},
            {data: 'diagnosis'},
            {data: 'insuranceNumber'}
        ]
    });

    $(patientsTableId + 'tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openPatient(data.id);
    });
}

function openPatient(id) {
    window.location.assign('/doctor/patient?id=' + id);
}
