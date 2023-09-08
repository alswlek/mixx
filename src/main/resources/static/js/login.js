// document.addEventListener('DOMContentLoaded', function () {
//     const account = {
//         id: null,
//         pw: null
//     };
//
//     const idInputEl = document.getElementById('signin-id');
//     const pwInputEl = document.getElementById('signin-pw');
//     const signInBtn = document.getElementById('signin');
//
//     idInputEl.addEventListener('change', function () {
//         account.id = idInputEl.value;
//         console.log(Object.values(account));
//     });
//
//     pwInputEl.addEventListener('change', function () {
//         account.pw = pwInputEl.value;
//         console.log(Object.values(account));
//     });
//
//     // 로그인 버튼 클릭 시 발생하는 이벤트 리스너를 추가합니다.
//     signInBtn.addEventListener('click', function (e) {
//         e.preventDefault(); // 기본 동작 중단
//         //
//         // const randNum = Math.floor(Math.random() * 10);
//         // console.log(randNum);
//
//         if (account.id === "" || account.id === null) {
//             alert("아이디를 입력해주세요");
//         } else if (account.pw === "" || account.pw === null) {
//             alert("비밀번호를 입력해주세요");
//         } else {
//             if (randNum < 4) {
//                 alert("존재하지 않는 아이디입니다.");
//             } else if (randNum < 7) {
//                 alert("비밀번호를 확인해주세요.");
//             } else {
//                 // 로그인이 성공적으로 완료되면 지정된 페이지로 이동합니다.
//                 // 여기서는 로그인 폼을 제출하도록 설정합니다.
//                 const loginForm = document.querySelector('form');
//                 loginForm.submit();
//             }
//         }
//     });
// });
//
//
