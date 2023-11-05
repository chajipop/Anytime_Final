$(document).ready(function () {
    $(".del").click(function() {
        var confirmation = confirm("내용을 삭제하시겠습니까?");
        if (confirmation) {
            var post_id = $(this).data('post_id');
            var board_id = $(this).data('board_id');
            $.ajax({
                type: "GET",
                url: "delete",
                data: { post_id: post_id },
                success: function(response) {
                    alert(response);
                    location.href = "list?board_id=" + board_id;
                },
                error: function() {
                    alert("삭제 요청 실패했습니다.");
                }
            });
        }
    });
});
