// qna 페이지 처리 js
const main = document.querySelector("#mainTest");
const qna = document.querySelector("#qna");
const endPoint = 12;    // 질문 마지막 번호
const result = document.querySelector("#result");
const select = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];      // 설문자가 선택한 답변을 담는 배열
let point = '';

// 사용자 선택지 계산하는 함수
function calResult(){   
      var result = select.indexOf(Math.max(...select));     // (...select) : 전개구문 - 선택한 배열을 펼쳐본다      
      return result;    // 배열의 최대값의 인덱스 반환
}


// 결과페이지 계산
function setResult(){
      point = calResult();
      const resultName = document.querySelector(".resultname");
      resultName.innerHTML = infoList[point].name;

      var resultImg = document.createElement('img');
      const imgDiv = document.querySelector('#resultImg');
      var imgURL = "../assets/images/farmstouch/img/image-" + point + '.png';

      resultImg.src = imgURL;
      resultImg.alt = point;
      resultImg.classList.add('img-fluid');
      imgDiv.appendChild(resultImg);

      const resultDesc = document.querySelector(".resultDesc");
      resultDesc.innerHTML = infoList[point].desc;

}


// 결과페이지 출력
function goResult(){
      qna.style.WebkitAnimation = "fadeOut 1s"; // qna 페이지가 꺼지고
      qna.style.animation = "fadeOut 1s";
      setTimeout(()=> {
            result.style.WebkitAnimation = "fadeIn 1s";     // result 페이지가 켜짐
            result.style.animation = "fadeIn 1s";

            setTimeout(()=>{
                  qna.style.display = "none";
                  result.style.display = "block";
            }, 450);
      });
      setResult();
      goDetail();
}


// Answer box
function addAnswer(answerText, qIdx, idx){
      var a = document.querySelector(".answerBox");
      var answer = document.createElement("button"); // 질문 답변을 버튼식으로 만듬
      answer.classList.add("answerList"); // class="answerList"
      answer.classList.add("my-3");
      answer.classList.add("py-3");
      answer.classList.add('mx-auto');
      answer.classList.add('fadeIn');

      a.appendChild(answer);              // <div id=answerBox><button class="answerList"></button></div>
      answer.innerHTML = answerText;      // goNext에서 끄집어낸 a 리스트 값을 하나씩 넣자

      answer.addEventListener("click", function(){
            // 버튼 선택하면 사라지게
            var children = document.querySelectorAll(".answerList");    // all
            for(let i = 0 ; i < children.length ; i++){
                  children[i].disabled = true;
                  children[i].style.WebkitAnimation = "fadeOut 0.5s";
                  children[i].style.animation = "fadeOut 0.5s";
                  // children[i].style.display = 'none';
            }
            setTimeout(()=>{
                  var target = qnaList[qIdx].a[idx].type;   // qnaList[qIdx] : 몇번째 질문에 해당하는가, a[idx] : 선택한 답

                  for(let j=0 ; j<target.length ; j++){
                        select[target[j]] += 1;
                  }

                  // select[qIdx] = idx;     // 선택지 번호가 담김
                  for(let i=0 ; i<children.length ; i++) {
                        children[i].style.display = 'none';
                  }
                  goNext(++qIdx);
            }, 450)
      }, false);
}

// qustion box
function goNext(qIdx){

      if(qIdx === endPoint){
            goResult();
            return;
      }

      var q = document.querySelector(".qBox");
      q.innerHTML = qnaList[qIdx].q;

      for(let i in qnaList[qIdx].a){
            addAnswer(qnaList[qIdx].a[i].answer, qIdx, i);
      }

      var status = document.querySelector(".statusBar");
      status.style.width = (100/endPoint) * (qIdx+1) + "%";   // 상태바 진행도를 나타내기 위한 수식
}


function begin(){
      // main.style.display="none";
      // qna.style.display="block";

      main.style.WebkitAnimation = "fadeOut 1s";
      main.style.animation = "fadeOut 1s";
      setTimeout(()=> {
            qna.style.WebkitAnimation = "fadeIn 1s";
            qna.style.animation = "fadeIn 1s";

            setTimeout(()=>{
                  main.style.display = "none";
                  qna.style.display = "block";
            }, 450);
            let qIdx = 0;
            goNext(qIdx);
      }, 450);
}

// 결과로 나온 꽃 상세페이지로 이동
function goDetail(){
	var myValue = '';
	if(point === 0){
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=193";
	} else if(point === 1) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=244";
	} else if(point === 2) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=4";
	} else if(point === 3) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=343";
	} else if(point === 4) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=8";
	} else if(point === 5) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=167";
	} else if(point === 6) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=342";
	} else if(point === 7) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=205";
	} else if(point === 8) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=9";
	} else if(point === 9) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=42";
	} else if(point === 10) {
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=184";
	} else if(point === 11){
		myValue = "http://localhost/life/todayFlowerDetail?dataNo=311";
	} else {
		myValue = "http://localhost/life/todayFlowerList";
	}

	var myLink = document.getElementById("myLink"); 
	myLink.setAttribute("href", myValue); 
}