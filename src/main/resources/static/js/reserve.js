    $(document).ready(function () {
    // ✅ 1. 사용자 정보 세션에서 가져오기
    $.ajax({
        url: "/booking/info",
        type: "GET",
        dataType: "json",
        success: function (user) {
            $("#user-name").text(user.name);
            $("#user-email").text(user.email);
            $("#user-gender").text(user.gender);
            $("#user-phone").text(user.phone)
        },
        error: function (xhr, status, error) {
            alert("로그인이 필요합니다.");
            window.location.href = "/main"; // 로그인 페이지로 이동
        }
    });

    // ✅ 2. 로컬스토리지에서 항공편 정보 가져오기
    var flight = JSON.parse(localStorage.getItem("selectedFlight"));
    if (flight) {
    $("#flight-airline").text(flight.airlineCode);
    $("#flight-departure").text(flight.departureName);
    $("#flight-arrival").text(flight.arrivalName);
    $("#flight-departure-date").text(flight.departureDate);
    $("#flight-departure-time").text(flight.departureTime);
    $("#flight-arrival-time").text(flight.arrivalTime);
    $("#flight-duration").text(flight.duration);
    $("#flight-price").text("₩" + flight.price);
} else {
    alert("항공편 정보가 없습니다.");
    window.location.href = "/main"; // 메인 페이지로 이동
}
});
