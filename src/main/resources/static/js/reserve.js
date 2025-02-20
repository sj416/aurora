$(document).ready(function() {
    $(".cancel-btn").click(function() {
        let bookingNo = $(this).attr("data-booking-id");

        if (confirm("정말 예약을 취소하시겠습니까?")) {
            $.post("/booking/cancel", { bookingNo: bookingNo }, function(response) {
                alert(response);
                location.reload(); // 성공 시 페이지 새로고침
            }).fail(function() {
                alert("예약 취소에 실패했습니다.");
            });
        }
    });
});