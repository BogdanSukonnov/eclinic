let length = 8;
let prescriptionsTableId = '#patientPrescriptionsTable';
let newPrescriptionBtnClass = 'newPrescriptionBtn';
let backBtnClass = 'backBtn';

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
        dom: "<'tableButtonContainer'B>" + "<'row'<'col-sm-12'tr>>",
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
                    text: 'Back',
                    className: backBtnClass
                },
                {
                    text: 'New prescription',
                    className: newPrescriptionBtnClass
                }
            ]
    });

    table.on( 'draw', function () {
        newPrescriptionBtnInit();
        backBtnInit();
        styleButtons();
    } );

    $(prescriptionsTableId + ' tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openPrescription(data.id);
    });
}

function styleButtons() {
    $('.dt-buttons.btn-group').removeClass('btn-group');

}

function openPrescription(id) {
    window.location.assign('/doctor/prescription?id=' + id);
}

function isStatusPatient() {
    return $('#patient_status').val() === 'PATIENT';
}

function newPrescriptionBtnInit() {
    let newPrescriptionBtn = $('.' + newPrescriptionBtnClass);
    newPrescriptionBtn.click(function () {
        window.location.assign('/doctor/newPrescription?patient_id='
            + $('#patient_id').val() + '&patient_fullName='
            + $('#patient_fullName').val());
    });
    newPrescriptionBtn.prop('disabled', !isStatusPatient());
}

function backBtnInit() {
    $('.' + backBtnClass).click(function () {
        history.back();
    });
}