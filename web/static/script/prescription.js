let treatmentSelect = $('#treatment');
let patternSelect = $('#pattern');
const statusIsPrescribed = $('#status').val() === 'PRESCRIBED';
const periodInput = $('#period');
const savePrescriptionBtn = $('#savePrescriptionBtn');
const savePrescriptionPopoverSpan = $('#savePrescriptionPopoverSpan');
const versionConflictModal = $('#versionConflictModal');
const updateDataBtn = $('#updateDataBtn');
const serverErrorModal = $('#serverErrorModal');
const closeServerErrorModalBtn = $('#closeServerErrorModalBtn');

$(document).ready(function() {
    if (isNew()) {
        changeNewHistory();
    }
    else {
        $('#savePrescriptionPopoverSpan').attr('data-content', 'Nothing changed')
    }
    dosageVisibility();
    eventsTableInit();
    periodInit();
    treatmentSelectInit();
    patternSelectInit();
    if ((isNew() || statusIsPrescribed) && ($('#patientStatus').val() !== 'DISCHARGED')) {
        $('input[name="treatmentType"]').click(function () {
            onTreatmentTypeChange()
        });
        $('#cancelPrescriptionBtn').click(function () {
            cancelPrescription()
        });
        savePrescriptionBtn.click(function (event) {
            savePrescription(event);
        });
    }
    else {
        disableInputs();
    }
    // init popover
    savePrescriptionPopoverSpan.popover();
    $('input').on('input', function () {
        onInputChange();
    });
    $('select').on('change', function () {
        onInputChange();
    });
    versionConflictModal.modal({show: false});
    serverErrorModal.modal({show: false});
});

function changeNewHistory() {
    history.replaceState(null, '', '/doctor/patient?id=' + $('#patientId').val());
}

function disableInputs() {
    $('#prescriptionForm input,select').prop('disabled', true);
    $('.edit-button').prop('disabled', true);
}

function treatmentSelectInit() {
    treatmentSelect
        .select2({
            ajax: {
                url: '/doctor/treatments-selector-data',
                type: 'POST',
                dataType: 'json',
                data: function (params) {
                    return {
                        type: treatmentType(),
                        search: params.term
                    };
                }
            }
        });
}

function dosageVisibility() {
    if (treatmentType() === 'MEDICINE') {
        $("#dosageGroup").show();
    }
    else {
        $("#dosageGroup").hide();
    }
}

function onTreatmentTypeChange() {
    if (treatmentType() !== 'MEDICINE') {
        $("#dosage")
            .val(0)
            .attr('min', 0);
        $("#dosageInfo").val('');
    } else {
        $("#dosage").attr('min', 0.0001);
    }
    dosageVisibility();
    treatmentSelect.empty();
    treatmentSelect.append('<option selected>Select ' + treatmentType().toLowerCase() + '</option>');
    onInputChange();
}

function treatmentType() {
    return $('input[name="treatmentType"]:checked').val();
}

function patternSelectInit() {
    patternSelect
        .select2({
            ajax: {
                url: '/doctor/time-pattern-selector-data',
                type: 'POST',
                dataType: 'json',
                data: function (params) {
                    return {
                        search: params.term
                    };
                }
            }
        });
}

function buildAjaxObject(data) {
    return {
        search: data.search.value,
        draw: data.draw,
        offset: data.start,
        limit: data.length,
        orderField: '',
        orderDirection: '',
        showCompleted: true,
        parentId: prescriptionId()
    }
}

function isNew() {
    return !$('#prescriptionId').val();
}

function eventsTableInit() {
    if (isNew()) {
        return;
    }
    table = $('#prescriptionEventsTable').DataTable( {
        processing: true,
        serverSide: true,
        lengthChange: false,
        paging: false,
        searching: false,
        ordering: false,
        ajax: {
            url: '/doctor/prescription-events-table',
            type: 'POST',
            data: function(data) {return buildAjaxObject(data)}
        },
        rowId: 'id',
        columns: [
            { data: 'eventStatus' },
            { data: 'dateFormatted' },
            { data: 'timeFormatted' },
            { data: 'patientFullName' },
            { data: 'treatmentType' },
            { data: 'treatmentName' },
            { data: 'dosage' }
        ]
    } );
    $('#prescriptionEventsTable tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openEvent(data.id);
    });
}

function prescriptionId() {
    return $('#prescriptionId').val();
}

function cancelPrescription() {
    $.ajax({
        url: '/doctor/cancel-prescription',
        type: 'POST',
        data: {'id': prescriptionId()}
    })
        .done(function () {
            window.history.back();
        });
}

function openEvent(id) {
    window.location.assign('/nurse/event?id=' + id);
}

function periodInit() {

    const defaultPeriod = '2 Weeks';

    period = {
        isCustom: false,
        range: defaultPeriod,
        customStart: moment().add(1, 'day').startOf('day'),
        customEnd: moment().add(2, 'week').endOf('day'),
        ranges: {
            '3 Days': [moment().add(1, 'day').startOf('day'), moment().add(3, 'day').endOf('day')],
            '1 Week': [moment().add(1, 'day').startOf('day'), moment().add(1, 'week').endOf('day')],
            '2 Weeks': [moment().add(1, 'day').startOf('day'), moment().add(2, 'week').endOf('day')],
            '3 Weeks': [moment().add(1, 'day').startOf('day'), moment().add(3, 'week').endOf('day')],
            '4 Weeks': [moment().add(1, 'day').startOf('day'), moment().add(4, 'week').endOf('day')]
        }
    };
    
    if (isNew()) {
        onPeriodChange(null, null, defaultPeriod);
    }
    else {
        // dates from server
        period.isCustom = true;
        period.customStart = moment($('#startDate').val());
        period.customEnd = moment($('#endDate').val());
    }

    periodInput.daterangepicker({
        "showDropdowns": true,
        "timePicker": false,
        "timePicker24Hour": false,
        "timePickerIncrement": 10,
        ranges: period.ranges,
        "linkedCalendars": false,
        "opens": "right",
        "startDate": periodDates().start,
        "endDate": periodDates().end,
        locale: {
            format: 'DD\.MM'
        }
    }, function(start, end, label) {
        onPeriodChange(start.startOf('day'), end.endOf('day'), label);
    });
}

function onPeriodChange(start, end, label) {
    period.isCustom = label === 'Custom Range';
    if (period.isCustom) {
        period.customStart = start;
        period.customEnd = end;
    }
    else {
        period.range = label;
    }
    $('#startDate').val(periodDates().start.format());
    $('#endDate').val(periodDates().end.format());
    onInputChange();
}

function periodDates() {
    if (period.isCustom) {
        return {
            start: period.customStart,
            end: period.customEnd
        }
    }
    else {
        return {
            start: period.ranges[period.range][0],
            end: period.ranges[period.range][1]
        }
    }
}

function onInputChange() {
    let hasEmpty = isNaN($('#treatment').val()) || isNaN($('#pattern').val()) ||
        $("input[name='treatmentType']:checked").val() === 'MEDICINE' && $('#dosage').val() < 0.01;
    if (hasEmpty) {
        savePrescriptionBtn.prop('disabled', true);
        savePrescriptionBtn.css('pointer-events', 'none');
        savePrescriptionPopoverSpan.popover('enable');
    }
    else {
        savePrescriptionBtn.prop('disabled', false);
        savePrescriptionBtn.css('pointer-events', 'auto');
        savePrescriptionPopoverSpan.popover('disable');
    }

}

function getId() {
    return $('#prescriptionId').val()
}

function getPatientId() {
    return $('#patientId').val()
}

function savePrescription(event) {
    event.preventDefault();
    $.ajax({
        url: isNew() ? '/doctor/new-prescription' : '/doctor/prescription',
        type: 'POST',
        data: {
            id: $('#prescriptionId').val(),
            version: $('#version').val(),
            dosage: $('#dosage').val(),
            dosageInfo: $('#dosageInfo').val(),
            patientId: $('#patientId').val(),
            timePatternId: $('#pattern').val(),
            treatmentId: $('#treatment').val(),
            startDate: periodDates().start.format(),
            endDate: periodDates().end.format()
        },
        statusCode: {
            409: function () {
                versionConflictModal.modal('show');
                updateDataBtn.click(function () {
                    window.location.reload();
                });
            },
            500: function () {
                serverErrorModal.modal('show');
                closeServerErrorModalBtn.click(function () {
                    window.location.reload();
                });
            }
        }
    })
        .done(function (msg) {
            let id = isNew() ? msg.id : getId();
            window.location.assign('/doctor/prescription?id=' + id);
        });
}
