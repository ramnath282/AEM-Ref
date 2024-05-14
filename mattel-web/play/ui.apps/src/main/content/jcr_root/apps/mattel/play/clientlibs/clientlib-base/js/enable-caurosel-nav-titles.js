(function (global, PLAYAEM, tileCarosuel) {
    var checkCnt= 0,
        charTiles = {
        el: '.enable-carousel-chartiles',
        bindingEventsConfig: function () {
            var events = {
                "click .enable-carousel-chartiles .play": "playPauseSlider",
            }
            return events;
        },
        playPauseSlider: function(el, evt) {
            tileCarosuel.controlSlider(el,evt)
        },
        render: function () {
            //var self = this,
            var $carouselEle = $(this.el),
                $carouselSlides;
            _.each($carouselEle, function (item) {
                $carouselSlides = $(item).find(".nav-carousel-slides");
                if ($carouselSlides.length) {
                    $carouselSlides.imagesLoaded(function () {
                        tileCarosuel.applycarousel($carouselSlides, $carouselEle[0].dataset);
                    })
                }
            });
        },
        gotoActiveSlide: function(el, slideElem){
            var self= this;
            if($(el).hasClass('slick-initialized')){
                var slideno = $(slideElem).data('slickIndex');
                if(!slideno){
                    console.log('slide index must be a required param to goto active slide. ');
                    return;
                }
                $(el).slick('slickGoTo', slideno, true);
            } else{
                if(checkCnt>20){
                    console.log('slick slider not initialized for '+ el+ ' element');
                    return;
                }
                setTimeout(function(){
                    self.gotoActiveSlide(el, slideElem);
                    checkCnt++;
                },500);
            }
        },
        init: function () {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.charTiles) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
        }
    }
    charTiles.init();
    PLAYAEM.charTiles = charTiles;
    document.addEventListener('DOMContentLoaded', function () {
        if (!PLAYAEM.isDependencyLoaded) {
            charTiles.init();
        }
        $('.char-tiles li a').each(function() {
                //if(location.pathname == this.pathname) {
				if(location.pathname.indexOf(this.pathname)!=-1) {
                    $(this).addClass('active');
                    charTiles.gotoActiveSlide(".char-tiles", $(this).closest('li'));
                }
       });
        if(location.hash == '#detail') {
			$('.char-detials-comp').attr("tabindex","0").focus();
        }
    }, false);
}(window, PLAYAEM, PLAYAEM.charfilter));
