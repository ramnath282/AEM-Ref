const GemCRMCarousel = function () {
    self = this;
    this.el = '.enable-carousel';
    this.init();
};

GemCRMCarousel.prototype = {
    bindingEventsConfig() {
        const events = {
            "click .enable-carousel .play": "playPauseSlider",
        };
        return events;
    },
    playPauseSlider(el, evt) {
        evt.preventDefault();
        const $el = $(el), $currentSlider = $el.closest(self.el).find(".carousel-slides");
        if (!$currentSlider.length) {
            console.log("PlayPauseSlider : Not Able to find current slider element..");
            return;
        }
        if (!$el.hasClass('pause')) {
            $el.addClass('pause').attr('aria-label', 'pause');
            $currentSlider.slick('slickPlay');
        } else {
            $el.removeClass('pause').attr('aria-label', 'play');
            $currentSlider.slick('slickPause');
        }
    },
    applyCarousel(el, config) {
        const slider = el.slick({
            slidesToShow: parseInt(config.slideShow) || 1,
            slidesToScroll: parseInt(config.slideScroll) || 1,
            autoplay: config.autoplay == 'true' ? true : $(this.el).closest(this.el).find('.play').hide() && false,
            dots: config.dots == 'true' ? true : false,
            arrows: config.arrows == 'false' ? false : true,
            autoplaySpeed: parseInt(config.speed) || 2000,
            //infinite: config.rewind == 'true' ? true : false,
            responsive: config.slideShow > 1 ? this.responsiveSlider(parseInt(config.slideShow), config) : true,
            accessibility: true
        });
        return slider;
    },
    responsiveSlider(showSlideDesktopCnt, config) {
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
                    slidesToScroll: 1
                }
            }
        ]
    },
    render() {
        const self = this;
        const $carouselEle = $(this.el);
        let $carouselSlides;
        _.each($carouselEle, item => {
            $carouselSlides = $(item).find(".carousel-slides");
            if ($carouselSlides.length) {
                $carouselSlides.imagesLoaded(() => {
                    self.applyCarousel($carouselSlides, $carouselEle[0].dataset);
                })
            }
        });
    },
    init() {
        if (!$(this.el).length) return;
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.render();
    }
}

let self;
const evtBinding = window.global.eventBindingInst;
const gemCRMCarouselInit = new GemCRMCarousel();