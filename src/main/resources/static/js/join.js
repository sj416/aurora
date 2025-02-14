window.addEventListener("DOMContentLoaded",function () {
    $("#userid").keyup(function () {
        var value = $(event.target).val();
        var num = value.search(/[0-9]/g);
        var eng = value.search(/[a-z]/ig);
        if (value.length < 5){
            $("#alertid").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertid").text("아이디는 5자리 이상 이여야 합니다.")
        }
        else if(value.replace(/\s| /gi, "").length === 0){
            $("#alertid").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertid").text("아이디에 공백은 사용 불가합니다.")
        }
        else if(num<0||eng<0){
            $("#alertid").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"

            });
            $("#alertid").text("아이디는 영어+숫자로 이루어저야 합니다.")
        }
        else {
            $.ajax({
                url: "/user/idChk",
                type: "POST",
                dataType: "json",
                data: { "userid": $("#userid").val() },
                success: function(data) {
                    if (data === 1) {
                        $("#alertid").css({
                            "color": "red",
                            "font-size": "15px",
                            "text-align": "center"
                        });
                        $("#alertid").text("중복된 아이디입니다.");
                    } else if (data === 0) {
                        $("#alertid").css({
                            "color": "black",
                            "text-align": "center"
                        });
                        $("#alertid").text("사용가능한 아이디입니다.");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("아이디 체크 실패:", status, error);
                    // 에러가 발생한 경우의 처리 (필요시 추가)
                }
            });
        }
    });
    $("#userpw").keyup(function () {
        var val = $(event.target).val();
        var num = val.search(/[0-9]/g);
        var eng = val.search(/[a-z]/ig);
        var spe = val.search(/['~!@#$%^&*|\\\'\";\/?]/gi);
        if(val.length <8){
            $("#alertpw").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertpw").text("비밀번호는 8자리 이상이여야 합니다")
        }
        else if (val.replace(/\s| /gi,"").length == 0){
            $("#alertpw").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertpw").text("비밀번호에 공백은 사용할 수 없습니다.")
        }
        else if(num<0||eng>0||spe<0){
            $("#alertpw").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertpw").text("비밀번호는 영어+숫자+특수문자로 이루어저야 합니다.")
        }
        else{
            $("#alertpw").css({
                "color": "black",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertpw").text("사용가능한 비밀번호 입니다")
        }
    })
    $("#userpwchk").keyup(function () {
        var val = $("#userpwchk").val()
        if(val != $("#userpw").val())
        {
            $("#alertpw2").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertpw2").text("비밀번호가 일치하지 않습니다.")
            return;
        }
        $("#alertpw2").css({
            "color": "black",
            "font-size": "15px",
            "text-align": "center"
        });
        $("#alertpw2").text("비밀번호가 일치합니다.")
    });
    $("#email").keyup(function () {
        var regex = new RegExp("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[0-9a-zA-Z]{2,3}$");
        if(!regex.test($("#email").val())){
            $("#alertemail").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertemail").text("이메일 형식이 맞지 않습니다.")
        }else {
            $("#alertemail").text("")
            $("#checkmail").attr("disabled",false);
        }
    })



    $("#join").click(function () {
        if ($("#alertid").text() != "사용가능한 아이디입니다."){
            alert("아이디 중복 확인을 해주세요")
            $("#userid").focus()
            return;
        }
        if($("#alertpw2").text() != "비밀번호가 일치합니다."){
            alert("비밀번호를 확인 해주세요")
            $("#userpw").focus()
            return;;
        }

        $("#userform").submit();
    })

    $(document).ready(function() {
        $("#join").click(function() {
            var userDto = {
                userId: $("#userid").val(),
                userPw: $("#userpw").val(),
                username: $("#name").val(),
                email: $("#email").val(),
                phone: $("#phone").val(),
                gender: $("input[name='gender']:checked").val()
            };
            console.log("UserDto", userDto);

            $.ajax({
                url: "/user/insert",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(userDto),
                processData: false,
                success: function(response) {
                    window.location.href = "/main";
                },
                error: function(xhr) {
                    alert("회원가입 실패: " + xhr.responseText);
                }
            });
        });
    });


});