let blac = document.querySelector("#blac");

let scrollWhere = window.addEventListener('scroll', function(){
      let value = this.window.scrollY;
      // console.log("scrollY", value);
      // 866
});


const scrolldown = document.querySelector('#scrollDown')

scrolldown.addEventListener('click',function(){
	//window.scrollTo(0, 100) //or
	window.scrollTo({left:0, top:866, behavior:"smooth"})
})