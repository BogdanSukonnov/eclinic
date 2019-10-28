
let treatmentSelect = $('#treatment');
let patternSelect = $('#pattern');

$(document).ready(function() {
    dosageVisibility();
    treatmentSelectInit();
    $('input[name="treatmentType"]').click(function() {onTreatmentTypeChange()});
    patternSelectInit();
});

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
