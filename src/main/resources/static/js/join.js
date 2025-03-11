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
            $("#alertid").text("ID must be at least 5 digits long")
        }
        else if(value.replace(/\s| /gi, "").length === 0){
            $("#alertid").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertid").text("Blank spaces are not allowed in ID")
        }
        else if(num<0||eng<0){
            $("#alertid").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"

            });
            $("#alertid").text("The ID must consist of English + numbers")
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
                        $("#alertid").text("Duplicate ID");
                    } else if (data === 0) {
                        $("#alertid").css({
                            "color": "black",
                            "text-align": "center"
                        });
                        $("#alertid").text("available ID");
                    }
                },
                error: function(xhr, status, error) {
                    console.error("ID check failed:", status, error);
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
            $("#alertpw").text("Password must be at least 8 digits")
        }
        else if (val.replace(/\s| /gi,"").length == 0){
            $("#alertpw").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertpw").text("cannot use spaces in your password")
        }
        else if(num<0||eng>0||spe<0){
            $("#alertpw").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertpw").text("Passwords must consist of English + numeric + special characters.")
        }
        else{
            $("#alertpw").css({
                "color": "black",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertpw").text("available password")
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
            $("#alertpw2").text("Password does not match.")
            return;
        }
        $("#alertpw2").css({
            "color": "black",
            "font-size": "15px",
            "text-align": "center"
        });
        $("#alertpw2").text("The password matches")
    });
    $("#email").keyup(function () {
        var regex = new RegExp("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[0-9a-zA-Z]{2,3}$");
        if(!regex.test($("#email").val())){
            $("#alertemail").css({
                "color": "red",
                "font-size": "15px",
                "text-align": "center"
            });
            $("#alertemail").text("The email format is not correct")
        }else {
            $("#alertemail").text("")
            $("#checkmail").attr("disabled",false);
        }
    })



    $(document).ready(function() {
        $("#join").click(function(event) {
            event.preventDefault();  // 기본 폼 제출 방지

            if ($("#alertid").text() !== "available ID") {
                alert("Please double-check your ID");
                $("#userid").focus();
                return;
            }
            if ($("#alertpw2").text() !== "The password matches") {
                alert("Please check the password");
                $("#userpw").focus();
                return;
            }

            let userDto = {
                userId: $("#userid").val(),
                userPw: $("#userpw").val(),
                username: $("#name").val(),
                email: $("#email").val(),
                phone: $("#phone").val(),
                gender: $("input[name='gender']:checked").val()
            };

            $.ajax({
                url: "/user/insert",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(userDto),
                success: function(response) {
                    alert("Successful Joining. Welcome to Aurora");
                    location.replace("/");
                },
                error: function(xhr) {
                    alert("Failed to sign up for membership: " + xhr.responseText);
                }
            });
        });
    });

});

