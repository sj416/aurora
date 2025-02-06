// document.getElementById("signup-form").addEventListener("submit", async function(event) {
//     event.preventDefault();
//
//     const username = document.getElementById("signup-username").value;
//     const password = document.getElementById("signup-password").value;
//
//     const response = await fetch("http://localhost:8080/api/auth/signup", {
//         method: "POST",
//         headers: { "Content-Type": "application/json" },
//         body: JSON.stringify({ username, password })
//     });
//
//     const result = await response.json();
//     alert(result.message || "회원가입 성공!");
// });
//
// document.getElementById("login-form").addEventListener("submit", async function(event) {
//     event.preventDefault();
//
//     const username = document.getElementById("login-username").value;
//     const password = document.getElementById("login-password").value;
//
//     const response = await fetch("http://localhost:8080/api/auth/login", {
//         method: "POST",
//         headers: { "Content-Type": "application/json" },
//         body: JSON.stringify({ username, password })
//     });
//
//     const result = await response.json();
//     if (result.token) {
//         localStorage.setItem("token", result.token);
//         alert("로그인 성공!");
//     } else {
//         alert("로그인 실패: " + result.message);
//     }
// });