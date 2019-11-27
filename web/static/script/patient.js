let length = 8;
let prescriptionsTableId = '#patientPrescriptionsTable';
let newPrescriptionBtnClass = 'newPrescriptionBtn';
let backBtnClass = 'backBtn';
let dischargeBtnClass = 'dischargeBtn';
let dischargeConfirmedBtnId = 'dischargeConfirmedBtn';
var calendar = null;
var calendarEl = document.getElementById('calendar');

$(document).ready(function() {
    length = lengthCalculate();
    prescriptionsTableInit();
    calendarInit();
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
            data: function (data) {
                return buildAjaxObject(data)
            }
        },
        rowId: 'id',
        columns: [
            {data: 'status'},
            {data: 'period'},
            {data: 'patient.fullName'},
            {data: 'treatment.type'},
            {data: 'treatmentWithDosage'},
            {data: 'timePattern.name'},
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
            },
            {
                text: 'Discharge',
                className: dischargeBtnClass
            }
        ]
    });

    table.on('draw', function () {
        newPrescriptionBtnInit();
        backBtnInit();
        dischargeBtnInit();
        dischargeConfirmedBtnInit();
        styleButtons();
    });

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

function dischargeBtnInit() {
    $('.' + dischargeBtnClass)
        .prop('disabled', !isStatusPatient())
        .attr('data-toggle', 'modal')
        .attr('data-target', '#dischargeModal');
}

function dischargeConfirmedBtnInit() {
    let dischargeConfirmedBtn = $('#' + dischargeConfirmedBtnId);
    dischargeConfirmedBtn.click(function () {
        $.post('/doctor/discharge-patient', {
            patient_id: $('#patient_id').val(),
            version: $('#version').val()
            },
            function () {
                location.reload();
            })
            .fail(function ( jqXHR, textStatus, errorThrown) {
                document.write(jqXHR.responseText);
            });
    });
    dischargeConfirmedBtn.prop('disabled', !isStatusPatient());
}

function backBtnInit() {
    $('.' + backBtnClass).click(function () {
        history.back();
    });
}

function calendarInit(defaultViewName, showColumnHeader) {

    calendar = new FullCalendar.Calendar(calendarEl, {
        plugins: ['dayGrid', 'bootstrap', 'interaction'],
        defaultView: 'dayGridMonth',
        height: 650,
        eventTimeFormat: { // like '14:30'
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        },
        events: {
            url: '/doctor/patient-events',
            method: 'GET',
            extraParams: {
                patientId: $('#patient_id').val()
            },
            failure: function () {
                console.log('events fetch failure');
            }
            // color: 'yellow',   // a non-ajax option
            // textColor: 'black' // a non-ajax option
        }
    });

    calendar.render();

    // if (calendar !== null) {
    //     calendar.destroy();
    // }
    //
    // calendar = new FullCalendar.Calendar(calendarEl, {
    //     plugins: ['timeGrid', 'bootstrap', 'interaction'],
    //     defaultView: 'timeGridWeek',
    //     allDaySlot: false,
    //     minTime: "09:00:00",
    //     maxTime: "22:00:00",
    //     eventOverlap: false,
    //     columnHeader: true,
    //     // columnHeaderFormat: {weekday: 'short'},
    //     themeSystem: 'bootstrap',
    //     slotEventOverlap: false,
    //     scrollTime: "09:00:00",
    //     height: "auto",
    //     weekends: true,
    //     slotLabelFormat: {
    //         hour: '2-digit',
    //         minute: '2-digit',
    //         omitZeroMinute: false,
    //         hour12: false
    //     },
    //     header: {
    //         left: '',
    //         center: '',
    //         right: ''
    //     },
    //     eventTimeFormat: {
    //         hour: '2-digit',
    //         minute: '2-digit',
    //         omitZeroMinute: false,
    //         hour12: false
    //     },
    //     eventClick: function (info) {
    //         // info.event.remove();
    //     }
    // });
    //
    // calendar.render();
}


