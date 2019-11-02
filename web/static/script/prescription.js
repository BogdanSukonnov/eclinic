let treatmentSelect = $('#treatment');
let patternSelect = $('#pattern');
const statusIsActive = $('#status').val() === 'ACTIVE';

$(document).ready(function() {
    dosageVisibility();
    eventsTableInit();
    if (statusIsActive) {
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
    if (treatmentType() === 'Medicine') {
        $("#dosageGroup").show();
    }
    else {
        $("#dosageGroup").hide();
    }
}

function onTreatmentTypeChange() {
    if (treatmentType() !== 'Medicine') {
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

function eventsTableInit() {
    $('#prescriptionEventsTable').DataTable( {
        processing: true,
        serverSide: true,
        lengthChange: false,
        paging: false,
        ajax: {
            url: '/doctor/prescription-events-table',
            type: 'POST',
            data: function(data) {return buildAjaxObject(data)}
        },
        rowId: 'id',
        columns: [
            { data: 'dateFormatted' },
            { data: 'timeFormatted' },
            { data: 'patientFullName' },
            { data: 'treatmentType' },
            { data: 'treatmentName' },
            { data: 'dosage' }
        ]
    } );
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
