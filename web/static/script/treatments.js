let length = 8;
let treatmentsTableId = '#treatmentsTable';

$(document).ready(function() {
    treatmentsTableInit();
    $('#newTreatmentModal input').on('input', function () {
        onInputChange();
    });
} );

function buildAjaxObject(data) {
    return {
        search: data.search.value,
        draw: data.draw,
        offset: data.start,
        limit: data.length
    }
}

function treatmentsTableInit() {
    const table = $(treatmentsTableId).DataTable({
        dom: "<'tableHeaderRow'Bfl>" +
            "<'row'<'col-sm-12'tr>>" +
            "<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7'p>>",
        processing: true,
        serverSide: true,
        lengthChange: true,
        paging: true,
        ordering: false,
        pageLength: length,
        lengthMenu: [length, 25, 50, 100],
        stateSave: true,
        ajax: {
            url: '/doctor/treatments-table',
            type: 'POST',
            data: function(data) {return buildAjaxObject(data)}
        },
        rowId: 'id',
        columns: [
            {data: 'type'},
            {data: 'name'}
        ],
        buttons: [
            {
                text: 'New treatment',
                className: 'newTreatmentBtn'
            }
        ]
    });

    table.on( 'draw', function () {
        newTreatmentBtnInit();
    } );

}

function newTreatmentBtnInit() {
    $('.newTreatmentBtn').attr('data-toggle', 'modal')
        .attr('data-target', '#newTreatmentModal');
}

function onInputChange() {
    a = $("#newTreatmentName").val() === '';
    b = !($("input[name='treatmentType']:checked").length > 0);
    $("#saveTreatmentBtn").prop('disabled', a || b);
}
