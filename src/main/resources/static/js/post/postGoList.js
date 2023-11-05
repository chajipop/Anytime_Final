$(document).ready(function () {
    $("#goListButton").click(function() {
        var board_id = $(this).data('board_id');
        window.location.href = "list?board_id=" + board_id;
    });
});
