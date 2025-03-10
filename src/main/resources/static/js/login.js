$(document).ready(function () {
    // 로그인 폼 제출 시
    $("#loginForm").submit(function (event) {
        event.preventDefault();  // 폼 기본 제출 방지

        let userId = $("#userId").val();
        let userPw = $("#userPw").val();

        // 로그인 데이터 준비 (JSON 형식)
        let loginData = {
            userId: userId,
            userPw: userPw
        };

        // 로그인 요청
        $.ajax({
            type: "POST",
            url: "/loginProc",  // Spring Boot 로그인 엔드포인트
            contentType: "application/json",
            data: JSON.stringify(loginData),
            success: function (response) {
                // 로그인 성공 시 /flights/search 페이지로 이동
                if (response.status === "success") {
                    window.location.href = "/flights/search";
                } else {
                    // 실패한 메시지 표시
                    $("#message").text(response.message).css("color", "red");
                }
            },
            error: function (xhr) {
                // 로그인 실패 시, 실패 메시지 표시
                let errorMessage = xhr.responseJSON && xhr.responseJSON.message ? xhr.responseJSON.message : "login failure";
                $("#message").text(errorMessage).css("color", "red");

                // 로그인 실패 시 /main 페이지로 이동
                window.location.href = "/";
            }
        });
    });

    // 회원가입 버튼 클릭 시 회원가입 페이지로 이동
    document.getElementById("signupBtn").addEventListener("click", function() {
        window.location.href = "/user/join";  // 회원가입 페이지로 이동
    });
});
