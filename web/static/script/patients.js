let length = 8;
let patientsTableId = '#patientsTable';

$(document).ready(function() {
    patientsTableInit();
} );

function patientsTableInit() {
    const table = $(patientsTableId).DataTable({
        dom: "<'tableHeaderRow'Bfl>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        processing: true,
        serverSide: true,
        lengthChange: true,
        paging: true,
        ordering: true,
        order: [[4, 'desc']],
        pageLength: length,
        lengthMenu: [length, 25, 50, 100],
        paging: true,
        stateSave: true,
        ajax: {
            url: '/doctor/patients-table',
            type: 'POST',
            data: function ( d ) {
                d.orderColumn = d.columns[d.order[0].column].name;
                d.orderDirection = d.order[0].dir;
            }
        },
        rowId: 'id',
        columns: [
            {data: 'fullName', orderable: true, name: 'fullName'},
            {data: 'patientStatus', orderable: false},
            {data: 'diagnosis', orderable: false},
            {data: 'insuranceNumber', orderable: false},
            {data: 'formattedDate', orderable: true, name: 'createdDateTime'}
        ],
        buttons: [
            {
                text: 'New patient',
                className: 'newPatientBtn'
            }
        ]
    });

    table.on( 'draw', function () {
        newPatientBtnInit();
    } );

    $(patientsTableId + ' tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openPatient(data.id);
    });
}

function newPatientBtnInit() {
    $('.newPatientBtn').attr('data-toggle', 'modal')
        .attr('data-target', '#newPatientModal');
}

function openPatient(id) {
    window.location.assign('/doctor/patient?id=' + id);
}
