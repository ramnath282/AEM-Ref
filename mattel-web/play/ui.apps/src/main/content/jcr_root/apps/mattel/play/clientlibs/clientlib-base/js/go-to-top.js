function _gototop() {
	
    var winObj = $(window),
        screenHeight = winObj.height(),
        fold = 1,
        setVisibleAt = (screenHeight * fold)/2.5,
        fadeAnimValue = 500,
        scrollAnimValue = 500;

    // $('body').append('<a href="#" class="play-backtotop">Back to Top</a>');
    function hambugerNavScrollOut(){
		if($(".hamburger").hasClass("open")){
			$(".hamburger").removeClass("open");
			$('.primary-nav').fadeOut(500);                
		}
	}
    winObj.scroll(function () {
        if (winObj.scrollTop() >= setVisibleAt) {
            $('.play-backtotop').fadeIn(fadeAnimValue);
            hambugerNavScrollOut();
        } else {
            $('.play-backtotop').fadeOut(fadeAnimValue);
        }
    });

    $('.play-backtotop').on('click', function (e) {
        e.preventDefault();
        $('html,body').animate({
            scrollTop: 0
        }, scrollAnimValue);
    });
    /*$('.play-backtotop').on(keydown, function (event) {
        if (event.keyCode === 13) {
          $('.skip-main').focus();
        }
    });*/

}
//_gototop();	
	
