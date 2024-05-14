
/**
 * parallax-banner.js
 * Version 1.0
 */
(function (global, AGAEM) {
    var parallaxBanner = {
        el: '.parallax-banner',
        init: function () {
            var self =this;
            if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.parallaxBanner) return;            
            
            $(self.el).each(function(index){
                var bgProperty = $(this).find(".parallax-img-container").attr("data-mobileimage");
                if(AGAEM.isMobile && bgProperty != "" && bgProperty) {
                    $(self.el).eq(index).find(".parallax-img-container").css("background-image",'url(' + bgProperty + ')');       
                }
                if(!AGAEM.isMobile) {
                    var speed = parseInt($(self.el).eq(index).attr("data-speed"));
                    speed = speed ? speed/1000 : 0.5;
                    jarallax($('.jarallax').eq(index), {
                        speed: speed
                    });
                }
            });
            
        }
    }
    parallaxBanner.init();
    AGAEM.parallaxBanner = parallaxBanner;
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            parallaxBanner.init();
        }
    }, false);
}(window, AGAEM));