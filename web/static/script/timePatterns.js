let length = 8;
let timePatternsTableId = '#timePatternsTable';

$(document).ready(function() {
    length = lengthCalculate();
    timePatternsTableInit();
} );

function buildAjaxObject(data) {
    return {
        search: data.search.value,
        draw: data.draw,
        offset: data.start,
        limit: data.length
    }
}

function timePatternsTableInit() {
    const table = $(timePatternsTableId).DataTable({
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
            url: '/doctor/time-patterns-table',
            type: 'POST',
            data: function(data) {return buildAjaxObject(data)}
        },
        rowId: 'id',
        columns: [
            {data: 'name'},
            {data: 'cycleLength'},
            {data: 'isWeekCycle'}
        ],
        buttons: [
            {
                text: 'New time pattern',
                className: 'newTimePatternBtn'
            }
        ]
    });

    table.on( 'draw', function () {
        newTimePatternBtnInit();
    } );

    $(timePatternsTableId + ' tbody').on('click', 'tr', function () {
        const data = table.row(this).data();
        openTimePattern(data.id);
    });

}

function newTimePatternBtnInit() {
    $('.newTimePatternBtn')
        .click(function () {
            window.location.assign('/doctor/new-time-pattern');
        });
}

function openTimePattern(id) {
    window.location.assign('/doctor/time-pattern?id=' + id);
}