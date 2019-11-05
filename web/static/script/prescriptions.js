let length = 8;
let prescriptionsTableId = '#prescriptionsTable';

$(document).ready(function() {
    length = lengthCalculate();
    prescriptionsTableInit();
} );

function buildAjaxObject(data) {
    return {
        search: data.search.value,
        draw: data.draw,
        offset: data.start,
        limit: data.length,
        orderField: '',
        orderDirection: ''
    }
}

function prescriptionsTableInit() {
    const table = $(prescriptionsTableId).DataTable({
        dom: "<'tableHeaderRow'fl>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        processing: true,
        serverSide: true,
        lengthChange: true,
        paging: true,
        pageLength: length,
        lengthMenu: [length, 20, 50, 100],
        ordering: false,
        ajax: {
            url: '/doctor/prescriptions-table',
            type: 'POST',
            data: function(data) {return buildAjaxObject(data)}
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
        ]
    });

    $(prescriptionsTableId + ' tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openPrescription(data.id);
    });
}

function openPrescription(id) {
    window.location.assign('/doctor/prescription?id=' + id);
}
