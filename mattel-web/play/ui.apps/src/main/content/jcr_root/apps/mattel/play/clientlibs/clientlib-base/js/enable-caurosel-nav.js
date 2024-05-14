/**
	In Character Client libs

 * enable-caurosel-nav.js
 * Version 1.0
 */
(function(global, PLAYAEM) {
    var charfilter = {
        el: '.enable-carousel-nav',
        bindingEventsConfig: function() {
            var events = {
                "click .enable-carousel-nav .play": "playPauseSlider",
                "click .category-drpdown-display": "openCatDropdownM"
            }
            return events;
        },
        openCatDropdownM: function() {
            $('.nav-categorylist').toggle();
	    $('.category-drpdown-display').toggleClass('open');
            var filterData = $('.nav-carousel-slides li a.active').text();
            $('.category-drpdown-display').empty().append(filterData + '<span></span>');
        },
        playPauseSlider: function(el, evt) {
            var self = PLAYAEM.charfilter;
            evt.preventDefault();
            var $el = $(el),
                $currentSlider = $el.closest(self.el).find(
                    ".nav-carousel-slides");
            if (!$currentSlider.length) {
                console
                    .log("PlayPauseSlider : Not Able to find current slider element..");
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
        applycarousel: function(el, config) {
            if (el.hasClass("slick-initialized") || el[0].tagName == "UL" && !el.find('li').length || el.hasClass("owl-carousel")) {
                return;
            }
            if ((el.is("#filter-category-items") || el.is("#category-filter-carousel")) && typeof $.fn.owlCarousel == "function") {
                el.addClass("owl-carousel");
                this.applyNonGridCarousel(el, config);
                return;
            }
            var self = this;
            var isCharDetailPage = $(el).parents(".play-category-details-component").length;
            el.on('init', function(event, slick) {
                console.log("initialized");
                if (isCharDetailPage) {
                    self.syncLabelHeight($(el).find('.slick-slide img+span'));
                }
            });
            var slider = el.slick({
                slidesToShow: parseInt(config.slideShow) || 1,
                slidesToScroll: parseInt(config.slideScroll) || 1,
                autoplay: config.autoplay == 'true' ? true : $(this.el)
                    .closest(this.el).find('.play').hide() && false,
                dots: config.dots == 'true' ? true : false,
                arrows: config.arrows == 'false' ? false : true,
                autoplaySpeed: parseInt(config.speed) || 2000,
                infinite: config.rewind == 'true' ? true : false,
                responsive: config.slideShow > 1 ? this.responsiveSlider(
                    parseInt(config.slideShow), config) : true,
                accessibility: true,
            });
            var prevBtnVal = $('.prev-tracking-id').val(),
                nextBtnVal = $(
                    '.next-tracking-id').val();
            $('.slick-prev').attr('data-tracking-id', prevBtnVal);
            $('.slick-next').attr('data-tracking-id', nextBtnVal);
            return slider;
        },
        syncLabelHeight: function(el) {
            var $elem = $(el);
            if (!$elem.length) return;
            var max = -1,
                $heightElem = $elem,
                height;
            $heightElem.css('height', 'auto');
            $elem.find('img').imagesLoaded(function() { // image ready
                _.each($heightElem, function(el) {
                    height = $(el).innerHeight();
                    max = height > max ? height : max;
                });
                $heightElem.css('height', max + 'px');
            });
            return;
        },
        applyNonGridCarousel: function(el, config) {
            var self = this,
                slider,
                options;
            if (typeof $.fn.owlCarousel == "function") {
                options = {
                    loop: config.rewind == 'true' ? true : false,
                    autoWidth: true,
                    slideBy: 1,
                    dots: config.dots == 'true' ? true : false,
                    nav: config.arrows == 'false' ? false : true,
                    navText: [],
                    margin: 15,
                    onRefreshed: function(el) {
                        console.log("refreshing");
                        if(!self.sliderRefreshed){
                            self.sliderRefreshed= true;
                            setTimeout(function(){
                                slider.trigger('refresh.owl.carousel');
                                self.checkNavDisabled(el, slider);
                            },3000);
                        }
                    },
                    onResized: function(el) {
                        self.checkNavDisabled(el, slider);
                    }
                };
                slider = el.owlCarousel(options);
                // $(window).on('load',function(){
                //     slider.trigger("refresh.owl.carousel");
                //     self.checkNavDisabled(slider);
                // });
                // window.onload = slider.trigger('refresh.owl.carousel');
            } else {
                self.applycarousel(el, config);
            }

            return slider;
        },
        checkNavDisabled: function(el) {
            var $el = $(el.currentTarget || el);
            setTimeout(function() {
                if (!$el.find(".owl-prev.disabled+.owl-next.disabled").length) {
                    $el.addClass('slider-nav-loaded');
                } else {
                    $el.removeClass('slider-nav-loaded');
                }
            }, 100)
        },
        // this is only used for cat-nav change --starts--
        responsiveSlider: function(config) {
            var isMobileSlideOverwrite = $("#businessSiteName").val() == "thomas-and-friends"
            if ($(".nav-carousel-slides").hasClass("char-tiles")) {
                return [{
                    breakpoint: 1024,
                    settings: {
                        slidesToShow: 4,
                        slidesToScroll: 3,
                        dots: false,
                        arrows: true
                    }
                }, {
                    breakpoint: 480,
                    settings: {
                        slidesToShow: isMobileSlideOverwrite ? 2.3 : 2.5,
                        slidesToScroll: 3,
                        dots: false,
                        arrows: false
                    }
                }]

            } else {
                return [{
                    breakpoint: 1280,
                    settings: {
                        slidesToShow: 7,
                        slidesToScroll: 1,
                        infinite: true,
                        dots: false,
                        arrows: true
                    }
                }, {
                    breakpoint: 992,
                    settings: {
                        slidesToShow: 5,
                        slidesToScroll: 1,
                    }
                }, {
                    breakpoint: 767,
                    settings: {
                        slidesToShow: 4,
                        slidesToScroll: 1,
                    }
                }, {
                    breakpoint: 600,
                    settings: {
                        slidesToShow: 3,
                        slidesToScroll: 1
                    }
                }, {
                    breakpoint: 480,
                    settings: {
                        slidesToShow: 2,
                        slidesToScroll: 1,
                    }
                }]
            }
        },
        //--char-nav ends--

        render: function() {
            var self = this,
                $carouselEle = $(this.el),
                $carouselSlides;
            if ($('.nav-categorylist').length == 1) {
                if (PLAYAEM.isMobile)
                    return;
            }
            _.each($carouselEle, function(item) {
                $carouselSlides = $(item).find(".nav-carousel-slides");
                if ($carouselSlides.length) {
                    $carouselSlides.imagesLoaded(function() {
                        self.applycarousel($carouselSlides,
                            $carouselEle[0].dataset);
                    })
                }
            });
        },
        updateFilterDropdown: function() {
            var $activeDropdown = $(".nav-carousel-slides .active:first"),
                $filterDesTitle = $(".filtered-heading-name h2"),
                $filterMobTitle = $(".category-drpdown-display"),
                activeTxt;
            if ($activeDropdown.length) {
                activeTxt = $activeDropdown.find('a').length ? $activeDropdown.find('a').text() : $activeDropdown.text();
                $filterMobTitle.length && $filterMobTitle.empty().append(activeTxt + "<span></span>")
                $filterDesTitle.length && $filterDesTitle.html(activeTxt);
            }
        },
        resizeBind: function() {
            var self = this;
            var isCharDetailPage = $(".play-category-details-component");
            window.onresize = function(event) {
                // if (!PLAYAEM.isMobile) {
                setTimeout(function() {
                    if (isCharDetailPage.length) {
                        self.syncLabelHeight($(isCharDetailPage).find('.slick-slide img+span'));
                    }
                }, 200);
                // }
            };
        },
        init: function() {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length ||
                PLAYAEM.charfilter)
                return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.render();
            this.updateFilterDropdown();
            this.resizeBind();
        }
    }
    charfilter.init();
    PLAYAEM.charfilter = charfilter;
    document.addEventListener('DOMContentLoaded', function() {
    $('.category-drpdown-display').removeClass('open'); // on page load removing the open class name
        if (!PLAYAEM.isDependencyLoaded) {
            charfilter.init();
        }
    }, false);
}(window, PLAYAEM));
