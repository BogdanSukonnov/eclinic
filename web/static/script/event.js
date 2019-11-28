const reasonInput = $('#cancellingReason');
const submitReasonBtn = $('#submitReasonBtn');
const completeEventBtn = $('#completeEventBtn');
const submitReasonPopoverSpan = $('#submitReasonPopoverSpan');
const statusIsScheduled = $('#status').val() === 'SCHEDULED';
const canComplete = statusIsScheduled && $('#canComplete').val() === 'true';

$(document).ready(function() {
    if (statusIsScheduled) {
        completeEventBtn.click(function () {
            completeEvent()
        });
        completeEventBtn.prop('disabled', !canComplete);
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
        data: {
            id: $('#eventId').val(),
            version: $('#version').val()
        }
    })
        .done(function () {
            window.history.back();
        })
        .fail(function ( jqXHR, textStatus, errorThrown) {
            document.write(jqXHR.responseText);
        });
}

function cancelEvent() {
    $.ajax({
        type: "POST",
        url: '/nurse/cancel-event',
        data: {
            id: $('#eventId').val(),
            version: $('#version').val(),
            cancel_reason: reasonInput.val()
        }
    }).done(function () {
        window.history.back();
    })
        .fail(function ( jqXHR, textStatus, errorThrown) {
            document.write(jqXHR.responseText);
        });
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

