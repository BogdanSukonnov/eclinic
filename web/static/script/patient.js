$(document).ready(function() {
    prescriptionsTableInit();
} );

function prescriptionsTableInit() {
    $('#patientPrescriptionsTable').DataTable( {
        processing: true,
        serverSide: true,
        ajax: {
            url: '/doctor/patientPrescriptions?patient_id=' + $('#patient_id').val(),
            type: 'POST'
        },
        columns: [
            { data: 'dateTimeFormatted' },
            { data: 'treatment.type' },
            { data: 'treatment.name' },
            { data: 'dosage' },
            { data: 'timePattern.name' },
            { data: 'duration' },
            { data: 'doctorFullName' }
        ]
    } );
}
