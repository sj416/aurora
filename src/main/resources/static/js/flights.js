$(document).ready(function () {
    $("#search-btn").click(function () {
        searchFlights();
    });


});

// ✅ 공항 이름 ↔ 공항 코드 매핑 객체
const airportCodes = {
    "인천": "ICN",
    "김포": "GMP",
    "제주": "CJU",
    "도쿄": "NRT",
    "삿포로": "CTS",
    "오사카": "KIX",
    "후쿠오카": "FUK"
};

function searchFlights() {
    var departureName = $("#departure").val();
    var arrivalName = $("#arrival").val();
    var date = $("#date").val();

    // ✅ 선택된 한글 공항 이름을 공항 코드로 변환
    var departureCode = airportCodes[departureName];
    var arrivalCode = airportCodes[arrivalName];

    console.log("변환된 요청 데이터:", { departureCode, arrivalCode, date }); // 디버깅 로그

    $.ajax({
        url: "/flights/list",
        type: "GET",
        data: {
            departure: departureCode,
            arrival: arrivalCode,
            date: date
        },
        dataType: "json",
        success: function (flights) {
            console.log("응답 데이터:", flights); // 응답 확인
            $("#flight-results").empty();

            // flights가 비어 있거나 null/undefined일 경우를 체크
            if (!flights || flights.length === 0) {
                $("#flight-results").append("<p>검색된 항공편이 없습니다.</p>");
                return;
            }

            var table = "<table border='1'>";
            table += "<thead><tr><th>항공사</th><th>출발지</th><th>도착지</th><th>출발일</th><th>출발 시간</th><th>도착 시간</th><th>소요 시간</th><th>가격</th><th>예약</th></tr></thead><tbody>";

            flights.forEach(function (flight) {
                table += "<tr>";
                table += "<td>" + flight.airlineCode + "</td>";
                table += "<td>" + flight.departureName + "</td>";
                table += "<td>" + flight.arrivalName + "</td>";
                table += "<td>" + flight.departureDate + "</td>";
                table += "<td>" + flight.departureTime + "</td>";
                table += "<td>" + flight.arrivalTime + "</td>";
                table += "<td>" + flight.duration + "</td>";
                table += "<td>" + "₩" +  flight.price  + "</td>";
                table += "<td><button class='reserve-btn' data-flight='" + JSON.stringify(flight) + "'>예약</button></td>";
                table += "</tr>";
            });

            table += "</tbody></table>";
            $("#flight-results").append(table);
        },
        error: function (xhr, status, error) {
            console.error("에러 발생:", xhr.responseText);
            alert("항공편 정보를 불러오는 데 실패했습니다.");
        }
    });
}

$(document).ready(function () {
    $("#search-btn").click(function () {
        searchFlights();
    });

    // ✅ 동적으로 생성된 예약 버튼 이벤트 위임
    $(document).on("click", ".reserve-btn", function () {
        var flightData = $(this).data("flight"); // 버튼의 data-flight 값 가져오기
        var flightNo = flightData.flightNo; // flightNo 추출

        console.log("예약할 항공편:", flightNo); // 디버깅 로그

        // ✅ AJAX 요청 (user_no는 백엔드에서 세션에서 가져옴)
        $.ajax({
            url: "/booking/reserve",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({ flightNo: flightNo }),
            success: function (response) {
                alert("항공편이 성공적으로 예약되었습니다!");
            },
            error: function (xhr, status, error) {
                console.error("예약 오류:", xhr.responseText);
                alert("예약에 실패했습니다.");
            }
        });
    });
});

