
let treatmentSelect = $('#treatment');

$(document).ready(function() {
    treatmentSelectInit();
    $('input[name="treatmentType"]').click(function() {onTreatmentTypeChange()});
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

function onTreatmentTypeChange() {
    treatmentSelect.empty();
    // treatmentSelect.append('<option selected>Select ' + treatmentType().toLowerCase() + '</option>');
}

function treatmentType() {
    return $('input[name="treatmentType"]:checked').val();
}

//
// function getTreatmentType() {
//     return document.querySelector('input[name="treatmentType"]:checked').value;
// }
//
// function onTreatmentTypeChange() {
//     const treatmentType = getTreatmentType();
//     const treatmentSelector = document.querySelector('#treatment');
//     //remove all treatment options
//     while (treatmentSelector.firstChild) {
//         treatmentSelector.removeChild(treatmentSelector.firstChild);
//     }
//     //add treatment options accordingly to treatmentType
//     for (const allTreatmentsKey in allTreatments) {
//         if (allTreatments.hasOwnProperty(allTreatmentsKey)) {
//             const currTreatment = allTreatments[allTreatmentsKey];
//             if (currTreatment.treatmentType !== treatmentType) {
//                 continue
//             }
//             let option = document.createElement("option");
//             option.value = allTreatmentsKey;
//             option.textContent = currTreatment.name;
//             treatmentSelector.appendChild(option);
//         }
//     }
//     treatmentSelector.value = treatmentId;
//     //show dosage only for medicine
//     document.querySelector("#dosageGroup").hidden = treatmentType !== 'Medicine';
// }
//
// function eventListenersInit() {
//     let els = document.querySelectorAll('input[name="treatmentType"]');
//     els.forEach(function(elem) {elem.addEventListener("click", onTreatmentTypeChange)});
// }
//
// function onStart() {
//     eventListenersInit();
//     onTreatmentTypeChange();
// }
//
// onStart();
//
