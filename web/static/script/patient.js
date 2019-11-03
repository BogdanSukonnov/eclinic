let length = 8;
let prescriptionsTableId = '#patientPrescriptionsTable';
let newPrescriptionBtnClass = 'newPrescriptionBtn';

$(document).ready(function() {
    prescriptionsTableInit();
});

function buildAjaxObject(data) {
    return {
        search: data.search.value,
        draw: data.draw,
        offset: data.start,
        limit: data.length,
        orderField: '',
        orderDirection: '',
        parentId: $('#patient_id').val()
    }
}

function prescriptionsTableInit() {
        const table = $(prescriptionsTableId).DataTable({
        dom: "<B>" + "<'row'<'col-sm-12'tr>>",
        processing: true,
        serverSide: true,
        lengthChange: false,
        paging: false,
        ordering: false,
        ajax: {
            url: '/doctor/prescriptions-table',
            type: 'POST',
            data: function(data) {return buildAjaxObject(data)}
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
        ],
            buttons: [
                {
                    text: 'New prescription',
                    className: newPrescriptionBtnClass
                }
            ]
    });

    table.on( 'draw', function () {
        newPrescriptionBtnInit();
    } );

    $(prescriptionsTableId + ' tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openPrescription(data.id);
    });
}

function openPrescription(id) {
    window.location.assign('/doctor/prescription?id=' + id);
}

function newPrescriptionBtnInit() {
    $('.' + newPrescriptionBtnClass).click(function () {
        window.location.assign('/doctor/newPrescription?patient_id='
            + $('#patient_id').val() + '&patient_fullName='
            + $('#patient_fullName').val());
        // $.ajax({
        //     type: 'GET',
        //     url: '/doctor/newPrescription',
        //     data: {
        //         patient_id: $('#patient_id').val(),
        //         patient_fullName: $('#patient_fullName').val()
        //     }
        // })
    });
}