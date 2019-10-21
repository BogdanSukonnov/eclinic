
function treatmentType() {
    return document.querySelector('input[name="treatmentType"]:checked').value;
}

function onTreatmentTypeChange() {
    const treatmentType_ = treatmentType();
    document.querySelector("#procedureGroup").hidden = treatmentType_ !== 'Procedure';
    document.querySelector("#medicineGroup").hidden = treatmentType_ !== 'Medicine';
    document.querySelector("#dosageGroup").hidden = treatmentType_ !== 'Medicine';
}

let els = document.querySelectorAll('input[name="treatmentType"]');
els.forEach(function(elem) {elem.addEventListener("click", onTreatmentTypeChange)});

onTreatmentTypeChange();

