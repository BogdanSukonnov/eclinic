let treatmentSelect = $('#treatment');
let patternSelect = $('#pattern');
const statusIsPrescribed = $('#status').val() === 'PRESCRIBED';
const periodInput = $('#period');

$(document).ready(function() {
    if (isNew()) {
        changeNewHistory();
    }
    dosageVisibility();
    eventsTableInit();
    periodInit();
    if (isNew() || statusIsPrescribed) {
        treatmentSelectInit();
        $('input[name="treatmentType"]').click(function () {
            onTreatmentTypeChange()
        });
        patternSelectInit();
        $('#cancelPrescriptionBtn').click(function () {
            cancelPrescription()
        });
    }
    else {
        disableInputs();
    }
});

function changeNewHistory() {
    history.replaceState(null, '', '/doctor/patient?id=' + $('#patientId').val());
}

function disableInputs() {
    $('#prescriptionForm input,select').prop('disabled', true);
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
        $("#dosage").val('');
    }
    dosageVisibility();
    treatmentSelect.empty();
    treatmentSelect.append('<option selected>Seleect ' + treatmentType().toLowerCase() + '</option>');
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

    period = {
        isCustom: false,
        range: '2 Weeks',
        customStart: moment().add(1, 'day'),
        customEnd: moment().add(2, 'week'),
        ranges: {
            '3 Days': [moment().add(1, 'day'), moment().add(3, 'day')],
            '1 Week': [moment().add(1, 'day'), moment().add(1, 'week')],
            '2 Weeks': [moment().add(1, 'day'), moment().add(2, 'week')],
            '3 Weeks': [moment().add(1, 'day'), moment().add(3, 'week')],
            '4 Weeks': [moment().add(1, 'day'), moment().add(4, 'week')]
        }
    };

    if (!isNew()) {
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
        onPeriodChange(start, end, label);
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