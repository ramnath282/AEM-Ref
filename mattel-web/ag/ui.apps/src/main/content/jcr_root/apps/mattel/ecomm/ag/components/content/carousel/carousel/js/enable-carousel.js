/**
 * carousel.js
 * Version 1.0
 */
(function (global, AGAEM) {
    var carousel = {
        el: '.enable-carousel',
        bindingEventsConfig: function () {
            var events = {
                "click .enable-carousel .play": "playPauseSlider",
            }
            return events;
        },
        playPauseSlider : function(el,evt){
            var self = AGAEM.carousel;
            evt.preventDefault();
            var $el = $(el),
                $currentSlider = $el.closest(self.el).find(".carousel-slides");
            if(!$currentSlider.length) {
                console.log("PlayPauseSlider : Not Able to find current slider element..");
                return;
            }
            if(!$el.hasClass('pause')){
                $el.addClass('pause').attr('aria-label','pause');
                $currentSlider.slick('slickPlay');
            } else{
                $el.removeClass('pause').attr('aria-label','play');
                $currentSlider.slick('slickPause');
            }
        },
        applyCarousel: function (el, config, index) {
            var slickObj = {
                slidesToShow: parseInt(config.slideShow) || 1,
                slidesToScroll: parseInt(config.slideScroll) || 1,
                autoplay: config.autoplay == 'true' ? true : $(this.el).closest(this.el).find('.play').hide() && false,
                dots: config.dots == 'true' ? true : false,
                arrows: config.arrows == 'false' ? false : true,
                autoplaySpeed: parseInt(config.speed) || 2000,
                infinite: true,
                responsive: config.slideShow > 1 ? this.responsiveSlider(parseInt(config.slideShow), config) : true,
                accessibility: true,
                pauseOnFocus: false,
                pauseOnHover: true,
                pauseOnDotsHover: false,
                adaptiveHeight: true,
                centerMode: config.centerMode == "true" ? true : false,
                centerPadding: 0
            };
           if(config.slideShow==1 && config.arrows=="true") {
                var prev = "prev"+index;
                var next = "next"+index;
                $(this.el).eq(index).find('.slick_banner').append('<div class="'+prev+' pp2"><button aria-label="previous" tabindex="0"></button></div><div class="'+next+' nn2"><button aria-label="next" tabindex="0"></button></div>');
                slickObj.nextArrow = "."+next;
                slickObj.prevArrow = "."+prev;
            }

            var slider = $(this.el).eq(index).find(".carousel-slides").slick(slickObj);
            return slider;
        },
        responsiveSlider: function (showSlideDesktopCnt, config) {
            return [{
                    breakpoint: 980,
                    settings: {
                        slidesToShow: (showSlideDesktopCnt == 2 ? showSlideDesktopCnt : showSlideDesktopCnt - 1) || 1,
                        slidesToScroll: 1,
                        infinite: true,
                        dots: true,
                        arrows: config.arrows || 1,
                    }
                },
                {
                    breakpoint: 600,
                    settings: {
                        slidesToShow: (showSlideDesktopCnt - 2) > 0 || 1,
                        slidesToScroll: 2
                    }
                },
                {
                    breakpoint: 480,
                    settings: {
                        slidesToShow: (showSlideDesktopCnt - 3) > 0 || 1,
                        slidesToScroll: 1,
                        dots: true
                    }
                }
            ]
        },
        render: function () {
            var self = this,
                $carouselEle = $(this.el),
                $carouselSlides;
            $('.pause').attr('aria-label','pause');
            
            _.each($carouselEle, function (item,index) {
                $carouselSlides = $(item).find(".carousel-slides");
                
                if ($carouselSlides.length) {
                    $carouselSlides.imagesLoaded(function () {
                        self.applyCarousel($carouselSlides, $carouselEle[index].dataset,index);
                        $(self.el).find(".slick-next").attr("tabindex",0);
                        $(self.el).find(".slick-prev").attr("tabindex",0);
                        if($(".hc-main-container").length){
                            var heightValarr =[];
                            var textHeightarr =[];
                            $(self.el).eq(index).find(".slick_banner").each(function(){
                                heightValarr.push($(this).outerHeight());
                            });
                            $(self.el).eq(index).find(".text-center").each(function(){
                                textHeightarr.push($(this).outerHeight());
                            });
                            heightValarr.sort();
                            textHeightarr.sort();
                            $(self.el).eq(index).find(".text-center").css("min-height",textHeightarr[textHeightarr.length-1]+"px");
                            $(self.el).eq(index).find(".slick-dots").css("top",heightValarr[heightValarr.length-1]+10+"px");
                        }
                    });
                }
            });
            
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.carousel) return;
            AGAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();            
        }
    }
    carousel.init();
    AGAEM.carousel = carousel;
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            carousel.init();
        }
    }, false);
}(window, AGAEM));
