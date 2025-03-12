$(document).ready(function () {
    $("#search-btn").click(function () {
        searchFlights();
    });

    $("#my-bookings-btn").click(function(){
       window.location.href = "/booking/reserve/details";
    });

});

function formatCurrency(price) {
    var userLang = navigator.language || navigator.userLanguage; // 브라우저 언어 확인
    var exchangeRate = 0.097; // 예제 환율 (1 KRW = 0.09 JPY) - 실제 값은 백엔드에서 받아오는 게 좋음.

    if (userLang.startsWith("ja")) {
        var priceInJPY = Math.round(price * exchangeRate); // 원을 엔화로 변환
        return priceInJPY.toLocaleString("ja-JP") + "¥";
    } else {
        return price.toLocaleString("ko-KR") + "원";
    }
}

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
            $("#flight-results").empty();

            if (!flights || flights.length === 0) {
                $("#flight-results").append("<p>검색된 항공편이 없습니다.</p>");
                return;
            }

            var table = "<table border='1'>";
            table += "<thead><tr><th>airline</th><th>departure</th><th>arrival</th><th>departureDate</th><th>departureTime</th><th>arrivalTime</th><th>duration</th><th>price</th><th>seats</th><th>reserve</th></tr></thead><tbody>";

            flights.forEach(function (flight) {
                table += "<tr>";
                table += "<td>" + flight.airlineCode + "</td>";
                table += "<td>" + flight.departureName + "</td>";
                table += "<td>" + flight.arrivalName + "</td>";
                table += "<td>" + flight.departureDate + "</td>";
                table += "<td>" + flight.departureTime + "</td>";
                table += "<td>" + flight.arrivalTime + "</td>";
                table += "<td>" + flight.duration + "</td>";
                table += "<td>"  + formatCurrency(flight.price) + "</td>";
                table += "<td>" + flight.seats + "</td>";
                table += "<td><button class='reserve-btn' data-flight='" + JSON.stringify(flight) + "'>reserve</button></td>";
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

$(document).on("click", ".reserve-btn", function () {
    var flightData = $(this).data("flight"); // 항공편 정보 가져오기
    var flightNo = flightData.flightNo;
    var amount = parseInt(flightData.price, 10); // 가격을 정수로 변환

    console.log("예약할 항공편:", flightNo, "금액:", amount);

    // 결제 요청
    IMP.init("imp14150415"); // 여기에 본인의 가맹점 키 입력

    IMP.request_pay({
        pg: "tosspayments",  // 결제 수단
        pay_method: "card",  // 결제 방식 (카드)
        merchant_uid: "order_" + new Date().getTime(),  // 고유 주문 번호 생성
        name: "항공권 예약",
        amount: amount,  // 결제 금액
        m_redirect_url: window.location.href  // 현재 페이지 URL로 리디렉션

    }, function(rsp) {
        if (rsp) {
            // 결제 성공 후 예약 API 호출
            console.log("결제 성공, imp_uid:", rsp.imp_uid);

            // 결제 결과 서버에서 다시 확인하기
            $.ajax({
                url: "/booking/payment/verify",  // 결제 검증 API 호출
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    paymentKey: rsp.imp_uid,  // imp_uid
                    orderId: rsp.merchant_uid,  // merchant_uid
                    amount: amount  // 결제 금액
                }),
                success: function (verifyResponse) {
                    if (verifyResponse.success) {
                        // 결제 검증이 성공하면 예약 처리
                        $.ajax({
                            url: "/booking/reserve",  // 예약 API
                            type: "POST",
                            contentType: "application/json",
                            data: JSON.stringify({
                                flightNo: flightNo,
                                amount: amount,
                                paymentKey: rsp.imp_uid,  // 결제 ID
                                orderId: rsp.merchant_uid  // 주문 ID
                            }),
                            success: function (response) {
                                alert("항공편이 성공적으로 예약되었습니다!");
                            },
                            error: function (xhr, status, error) {
                                console.error("예약 오류:", xhr.responseText);
                                alert("예약에 실패했습니다.");
                            }
                        });
                    } else {
                        // 결제 검증 실패 시
                        alert("결제 검증 실패. 다시 시도해주세요.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("결제 검증 오류:", xhr.responseText);
                    alert("결제 검증 중 오류가 발생했습니다.");
                }
            });
        } else {
            // 결제 실패 시
            console.error("결제 실패:", rsp.error_msg);
            alert("결제에 실패했습니다. 다시 시도해주세요.");
        }
    });
});



