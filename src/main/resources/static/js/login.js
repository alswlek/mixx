const account = {
    id: null,
    pw: null
}

/*** SECTION - ID ***/
// ID 입력란과 비밀번호 입력란, 로그인 버튼 요소를 가져옵니다.
const idInputEl = document.getElementById('signin-id');
const pwInputEl = document.getElementById('signin-pw');
const signInBtn = document.getElementById('signin');
// ID 입력란의 값이 변경될 때 발생하는 이벤트 리스너를 추가합니다.
idInputEl.addEventListener('input', () => {
    // 입력된 ID 값을 계정 객체(account)에 저장하고, 계정 객체의 값을 출력합니다.
    account.id = idInputEl.value
    console.log(Object.values(account))
});
// 비밀번호 입력란의 값이 변경될 때 발생하는 이벤트 리스너를 추가합니다.
pwInputEl.addEventListener('input', () => {
    // 입력된 비밀번호 값을 계정 객체(account)에 저장하고, 계정 객체의 값을 출력합니다.
    account.pw = pwInputEl.value
    console.log(Object.values(account))
});
// 로그인 버튼 클릭 시 발생하는 이벤트 리스너를 추가합니다. -> 안먹는거같아,,
signInBtn.addEventListener('click', () => {
    // 랜덤 숫자를 생성합니다.
    const randNum = Math.floor(Math.random() * 10)
    console.log(randNum)
    if(account.id === "" || account.id === null) {
        alert("아이디를 입력해주세요")
    }
    else if (account.pw === "" || account.pw === null) {
        alert("비밀번호를 입력해주세요")
    }
    else {
        if(randNum < 4) {
            alert("존재하지 않는 아이디입니다.")
        }
        else if (randNum < 7) {
            alert("비밀번호를 확인해주세요.")
        }
        else {
            // 로그인이 성공적으로 완료되면 지정된 페이지로 이동합니다.
            location.href = "/index.html"
        }
    }
});
