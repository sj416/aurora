document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".price").forEach(function (priceElement) {
        var originalPrice = parseInt(priceElement.getAttribute("data-price"), 10);
        priceElement.querySelector(".formatted-price").textContent = formatCurrency(originalPrice);
    });
});

function formatCurrency(price) {
    var userLang = navigator.language || navigator.userLanguage; // 브라우저 언어 확인
    var exchangeRate = 0.097; // 환율 (1 KRW = 0.097 JPY) - 실제로는 백엔드에서 받아오는 것이 좋음.

    if (userLang.startsWith("ja")) {
        var priceInJPY = Math.round(price * exchangeRate); // 원을 엔화로 변환
        return priceInJPY.toLocaleString("ja-JP") + "¥";
    } else {
        return price.toLocaleString("ko-KR") + "원";
    }
}



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