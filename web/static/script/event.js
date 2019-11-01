const reasonInput = $('#cancellingReason');
const submitReasonBtn = $('#submitReasonBtn');
const submitReasonPopoverSpan = $('#submitReasonPopoverSpan');
const statusIsScheduled = $('#status').val() === 'SCHEDULED';

$(document).ready(function() {
    if (statusIsScheduled) {
        $('#completeEventBtn').click(function () {
            completeEvent()
        });
        submitReasonBtn.click(function () {
            cancelEvent()
        });
        reasonInput.on('input', function () {
            onReasonChange()
        });
        // init popover
        submitReasonPopoverSpan.popover();
    }
});

function completeEvent() {
    $.ajax({
        type: "POST",
        url: '/nurse/complete-event',
        data: {id: $('#eventId').val()}
    })
        .done(function () {
            window.history.back();
        })
        .fail(function ( jqXHR, textStatus, errorThrown) {
            console.log(console.log(jqXHR.responseText));
        });
}

function cancelEvent() {
    $.ajax({
        type: "POST",
        url: '/nurse/cancel-event',
        data: { 'id': $('#eventId').val() , 'cancel-reason': reasonInput.val() }
    });
    window.history.back();
}

function onReasonChange() {
    if (isEmpty(reasonInput.val())) {
        submitReasonBtn.prop('disabled', true);
        submitReasonBtn.css('pointer-events', 'none');
        submitReasonPopoverSpan.popover('enable');
    }
    else {
        submitReasonBtn.prop('disabled', false);
        submitReasonBtn.css('pointer-events', 'auto');
        submitReasonPopoverSpan.popover('disable');
    }
}

function isEmpty(str) {
    return (!str || 0 === str.length);
}