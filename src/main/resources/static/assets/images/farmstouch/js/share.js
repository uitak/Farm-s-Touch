/*const url = 'https://farmstouchlovetype.netlify.app/';*/
const url = 'http://localhost';

function setShare(){
      var resultImg = document.querySelector('#resultImg');
      var resultAlt = resultImg.firstElementChild.alt;
      const shareTitle = '팜스터치 연애 유형 식물 테스트';
      const shareDes = infoList[resultAlt].name;
      /* http://localhost/assets/images/farmstouch/img/image-1.png */
     /* /assets/images/farmstouch/img/image-' + resultAlt + '.png'; */
      const shareImage = 'https://www.iolivegarden.co.kr/_images/main_brandstory.jpg';
      const shareURL = url + '/test/result-' + resultAlt;	/* http://localhost/test/result-0 */

      Kakao.Share.sendDefault({
            objectType: 'feed',
            content: {
                  title: shareTitle,
                  description: shareDes,
                  imageUrl: shareImage,
                  link: {
                        mobileWebUrl: shareURL,
                        webUrl: shareURL,
                  },
            },
            buttons: [
                  {
                        title: '결과 확인하기',
                        link: {
                              mobileWebUrl: shareURL,
                              webUrl: shareURL
                        },
                  },
            ],
      });
}
