let length = 8;
let patientsTableId = '#patientsTable';
let newPatientFullNameInput = $('#newPatientFullName');
let newPatientInsuranceInput = $('#newPatientInsurance');
let nameUniqueSpan = $('#nameUnique');
let insuranceUniqueSpan = $('#insuranceUnique');

$(document).ready(function() {
    length = lengthCalculate();
    patientsTableInit();
    newPatientFullNameInput.on('input', function () {
        onNewPatientFullNameChange();
    });
    newPatientInsuranceInput.on('input', function () {
        onNewPatientInsuranceChange();
    });
    insuranceInit();
} );

function insuranceInit() {
    $('#newPatientInsurance').keyup(function () {
        let val = this.value.replace(/\D/g, '');
        let newVal = '';
        if (val.length > 4) {
            this.value = val;
        }
        if ((val.length > 3) && (val.length < 6)) {
            newVal += val.substr(0, 3) + '-';
            val = val.substr(3);
        }
        if (val.length > 5) {
            newVal += val.substr(0, 3) + '-';
            newVal += val.substr(3, 2) + '-';
            val = val.substr(5);
        }
        newVal += val;
        this.value = newVal.substring(0, 11);
    });
}

function onNewPatientFullNameChange() {
    $.get("patient-name-is-unique",
        {
            fullName: newPatientFullNameInput.val()
        },
        function () {
            nameUniqueSpan.text('   free');
            nameUniqueSpan.css('color', 'green');
            newPatientFullNameInput.prop('pattern', newPatientFullNameInput.val())
        })
        .fail(function () {
            nameUniqueSpan.text('   busy');
            nameUniqueSpan.css('color', 'red');
            newPatientFullNameInput.prop('pattern', 'NO' + newPatientFullNameInput.val())
        });
}

function buildAjaxObject(data) {
    return {
        search: data.search.value,
        draw: data.draw,
        offset: data.start,
        limit: data.length,
        orderField: data.columns[data.order[0].column].name,
        orderDirection: data.order[0].dir
    }
}

function patientsTableInit() {
    const table = $(patientsTableId).DataTable({
        dom: "<'tableHeaderRow'Bfl>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        processing: true,
        serverSide: true,
        lengthChange: true,
        paging: true,
        ordering: true,
        order: [[4, 'desc']],
        pageLength: length,
        lengthMenu: [length, 20, 50, 100],
        stateSave: true,
        ajax: {
            url: 'patients-table',
            type: 'POST',
            data: function(data) {return buildAjaxObject(data)}
        },
        rowId: 'id',
        columns: [
            {data: 'fullName', orderable: true, name: 'fullName'},
            {data: 'patientStatus', orderable: false},
            {data: 'diagnosis', orderable: false},
            {data: 'insuranceNumber', orderable: false},
            {data: 'formattedDate', orderable: true, name: 'createdDateTime'}
        ],
        buttons: [
            {
                text: 'New patient',
                className: 'newPatientBtn'
            }
        ]
    });

    table.on( 'draw', function () {
        newPatientBtnInit();
    } );

    $(patientsTableId + ' tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openPatient(data.id);
    });
}

function newPatientBtnInit() {
    $('.newPatientBtn').attr('data-toggle', 'modal')
        .attr('data-target', '#newPatientModal');
}

function openPatient(id) {
    window.location.assign('patient?id=' + id);
}

function onNewPatientInsuranceChange() {
    if (newPatientInsuranceInput.val().length < 11) {
        insuranceUniqueSpan.text('   incorrect');
        insuranceUniqueSpan.css('color', 'red');
        newPatientInsuranceInput.prop('pattern', "^\d{3}-\d{2}-\d{4}$")
    } else {
        $.get("patient-insurance-is-unique",
            {
                insurance: newPatientInsuranceInput.val()
            },
            function () {
                insuranceUniqueSpan.text('   free');
                insuranceUniqueSpan.css('color', 'green');
                newPatientInsuranceInput.prop('pattern', '*')
            })
            .fail(function () {
                insuranceUniqueSpan.text('   busy');
                insuranceUniqueSpan.css('color', 'red');
                newPatientInsuranceInput.prop('pattern', 'BAD PATTERN')
            });
    }
}
