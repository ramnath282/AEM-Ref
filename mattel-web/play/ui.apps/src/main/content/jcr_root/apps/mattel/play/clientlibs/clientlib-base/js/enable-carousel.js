/**
 * carousel.js
 * Version 1.0
 */
(function (global, PLAYAEM, ooyalaPlayer) {
    var self;
    var carousel = {
        el: '.enable-carousel',
        isManuallyPaused: [],
        bindingEventsConfig: function () {
            var events = {
                "click .enable-carousel .play": "playPauseSlider",
                "click .enable-carousel .slick-prev , .enable-carousel .slick-next": "prevNextBtn",
                "click .enable-carousel .slick-dots li button": "carouselBtnPromCr"
            }
            return events;
        },
        prevNextBtn: function (el, evt) {
            //Analytics fixes 
            var prevNextVal;
            if ($(el).attr('aria-label') == "Previous") {
                prevNextVal = "Left";
            } else if ($(el).attr('aria-label') == "Next") {
                prevNextVal = "Right";
            }
            var trackingVal = 'Navigating Arrows|' + prevNextVal + '||Promo Carousel';
            PLAYAEM.getTrackingValues(el, evt, trackingVal);
        },
        playPauseSlider: function (el, evt) {
            var self = PLAYAEM.carousel;
            evt.preventDefault();
            var $el = $(el),
                $currentSlider = $el.closest(self.el).find(".carousel-slides");
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
        carouselBtnPromCr: function (el, evt) {
            var carlBtnValue = $(el).text();
            var alwaysEnglishPromCr = $('#alwaysEnglishPromCr').val();
            var trackingVal = 'Navigating Dots|' + alwaysEnglishPromCr + '| Pagination Dots:' + carlBtnValue + '|Promo Carousel Section';
            PLAYAEM.getTrackingValues(el, evt, trackingVal);

        },
        applyCarousel: function (el, config) {
          var slider = el.slick({
                autoplay: config.autoplay === 'true' ? true : $(el).closest(el).find('.play').hide() && false,
                slidesToShow: parseInt(config.slideShow) || 1,
                slidesToScroll: parseInt(config.slideScroll) || 1,
                dots: config.dots == 'true' ? true : false,
                arrows: config.arrows == 'false' ? false : true,
                autoplaySpeed: parseInt(config.speed) || 2000,
                infinite: true,
                useTransform : false,
                responsive: config.slideShow > 1 ? this.responsiveSlider(parseInt(config.slideShow), config) : true,
                accessibility: true,
                pauseOnFocus: false,
                pauseOnHover: true,
                pauseOnDotsHover: false
            });
            var prevBtnVal = $('.carousel-slides').attr('data-prev-id');
            var nextBtnVal = $('.carousel-slides').attr('data-next-id');
            $('.slick-prev').attr('data-tracking-id', prevBtnVal);
            $('.slick-next').attr('data-tracking-id', nextBtnVal);

            if ($(el).find(' li') > 1) {
                $(el).parents(".cda-banner").find('.play').show();
            }
            if ($(el).hasClass("has-video-carousel")) {
                this.videoPlayerInit(slider);
            }
            return slider;
        },
        videoPlayerInit: function (slider) {
            var $videoEle = $(".video-player-banner");
            var $isFirstVideo = $(slider).find("li[data-slick-index='0'] .video-player-banner");
            if (!ooyalaPlayer) {
                console.log("OOyala Init JS file not included here for CDA banner section..")
                return;
            }
            if (!$videoEle.length) {
                console.log("Banner, video player class name not found..");
                return;
            }
            self.videoPlayerConfig(slider);
            if ($isFirstVideo.length) {
                self.checkFirstVideoInBanner($isFirstVideo);
                self.sliderPause();
            }
            self.VideoWithImageMaxHeight();
            _.each($videoEle, function (item, indx) {
                item.setAttribute('id', 'video-container-' + (indx + 1))
                item.setAttribute('data-video-index', indx);
                ooyalaPlayer.init('video-container-' + (indx + 1), function (plyr) {
                    self.bannerPlayers[indx] = plyr;
                    self.playerAction(self.bannerPlayers[indx]);
                });
            });
            slider.on('beforeChange', function (event, slick, currentSlide, nextSlide) {
                if (currentSlide === 0 && nextSlide === slick.$slides.length - 1) {
                    self.onSliderAction(slick, currentSlide, nextSlide, this, 'prev');
                } else if (nextSlide > currentSlide || (currentSlide === (slick.$slides.length - 1) && nextSlide === 0)) {
                    self.onSliderAction(slick, currentSlide, nextSlide, this, 'next');
                } else {
                    self.onSliderAction(slick, currentSlide, nextSlide, this, 'prev');
                }
            });
            $(window).resize(function () {
                setTimeout(function () {
                    self.VideoWithImageMaxHeight();
                }, 500)

            });
        },
        maxHeightPromoImg: function () {
            var self = this;
            var $imgContainer = $(".has-video-carousel .slick-slide:not(.video-player-container)");
            if (!$(".has-video-carousel").length) return;
            if (!$imgContainer.length) {
                $(".has-video-carousel").addClass("only-video-slider");
                return;
            }
            
         $(".has-video-carousel ").find(".video-player-container").css("height", 'auto');
            var max = -1,
                height;
            _.each($imgContainer, function (el) {
                height = $(el).height();
                max = height > max ? height : max;
             });
          $(".has-video-carousel ").find(".video-player-container").css("height", max + 'px');
            self.videoSlider.slick('resize')
     
            return max;
        },
        
     VideoWithImageMaxHeight:function(){
            var self = this;
            var max = -1,
                height;
            var $videoContainer = $(".has-video-carousel");
            var $imgContainer = $(".has-video-carousel li:not(.video-player-container)");
            if (!$(".has-video-carousel").length) return;
            if (!$imgContainer.length) {
                $(".has-video-carousel").addClass("only-video-slider");
                return;
            }
            _.each($videoContainer, function (el) {
             var imageList =  $(el).find('li:not(.video-player-container)');
             var videoList =  $(imageList).closest('.has-video-carousel').find('.video-player-container');
             height = imageList.height();
             max = height > max ? height : max;
             videoList.height(max)
             });
             self.videoSlider.slick('resize')
            return max
           },
        checkFirstVideoInBanner: function (videoElem) {
            var player = self.bannerPlayers[videoElem.data("videoIndex")];
            window.setTimeout(function () {
                if (player && player.getState() != "loading") {
                    self.playVideo(self.bannerPlayers[videoElem.data("videoIndex")]);
                } else {
                    self.checkFirstVideoInBanner(videoElem);
                }
            }, 500)
        },
        videoPlayerConfig: function (slider) {
            var $sliderOpts = $(slider).parents('.enable-carousel');
            self.bannerPlayers = [];
            self.videoSlider = slider;
            self.sliderAutoplay = $sliderOpts.data('autoplay') || false;
            self.nextVideoAutoPlay = $sliderOpts.data("nextVideoAutoplay") || false;
        },
        playerAction: function (player) {
            var videoIndx,videoElem;
            player.mb.subscribe(OO.EVENTS.PLAYING, 'Playing', function (event) {
                videoElem = $('.video-player-container:not(.slick-cloned) .video-player-banner[data-video-id='+this.mb._interceptArgs.setEmbedCode[0]+']');
                videoIndx = videoElem.data("videoIndex");
                if(videoIndx>=0 && self.isManuallyPaused[videoIndx]==true && (self.isSliderPlaying==undefined || !self.isSliderPlaying)) {
                    console.log("video paused");
                    self.isManuallyPaused[videoIndx] = false;
                } else{
                    self.isSliderPlaying= false;
                }
                self.sliderPause();
            });
            player.mb.subscribe(OO.EVENTS.PAUSED, 'Paused', function (event) {
                console.log("Paused video");
                videoElem = $('.video-player-container:not(.slick-cloned) .video-player-banner[data-video-id='+this.mb._interceptArgs.setEmbedCode[0]+']');
                videoIndx = videoElem.data("videoIndex");
                self.sliderPlay();
                if(videoIndx>=0 && (self.isSliderPlaying==undefined || !self.isSliderPlaying)) {
                    console.log("video paused");
                    self.isManuallyPaused[videoIndx] = true;
                } else{
                    self.isSliderPlaying= false;
                }
            });
            player.mb.subscribe(OO.EVENTS.PLAYED, 'completed', function (event) {
                self.videoSlider && self.nextVideoAutoPlay && self.videoSlider.slick('slickNext');
            });
        },
        onSliderAction: function (slick, currentSlide, nextSlide, curEle) {
            var $currentEle = $(curEle).find('li[data-slick-index=' + currentSlide + '] .video-player-banner');
            var $targetEle = $(curEle).find('li[data-slick-index=' + nextSlide + '] .video-player-banner');
            if ($currentEle.length) {
                self.pauseVideo(self.bannerPlayers[$currentEle.data("videoIndex")])
            }
            if ($targetEle.length) {
                self.playVideo(self.bannerPlayers[$targetEle.data("videoIndex")])
            }
        },
        playVideo: function (player) {
            var videoIndx;
            if (!self.nextVideoAutoPlay) {
                console.log("True to be set for data-next-video-autoplay attribute for enabling next video autoplay feature.. ");
                return;
            }
            var playerContainer = document.getElementById(player.getElementId());
            videoIndx = playerContainer.dataset.videoIndex;
            if(videoIndx==undefined || (self.isManuallyPaused.length && self.isManuallyPaused[videoIndx])){
                return;
            }
            player && (player.getState() == "ready" || player.getState() == "paused") && ooyalaPlayer.isScrolledIntoView(playerContainer) > 0 && player.play();
        },
        pauseVideo: function (player) {
            //var playerContainer = document.getElementById(player.getElementId());
            if(player && player.isPlaying()) {
                player.pause();
                self.isSliderPlaying= true;
            }
        },
        sliderPlay: function () {
            self.videoSlider && self.sliderAutoplay && self.videoSlider.slick('slickPlay');
        },
        sliderPause: function () {
            self.videoSlider && self.sliderAutoplay && self.videoSlider.slick('slickPause');
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
                        slidesToScroll: 1
                    }
                }
            ]
        },
        
        triggerSlider:function(){
            var $sliderAction = $(this.el).find('.carousel-slides');
            $sliderAction.addClass('slider-trigger');
        },
        
        render: function () {
            var self = this,
                $carouselEle = $(this.el),
                $carouselSlides = $(this.el).find('.carousel-slides'),
                $curSlide;
             $('.pause').attr('aria-label', 'pause');
            _.each($carouselEle, function (item,index) {
                if ($carouselSlides.length) {
                    $carouselSlides.imagesLoaded(function () {
                        $curSlide = $($carouselEle[index]);
                        self.applyCarousel($carouselSlides, $curSlide[0].dataset);
                        if(!$curSlide.find(".carousel-slides .banner-copy").length){
                            $curSlide.addClass("cda-image-slider");
                        }
                    })
                }
            });
        },
        init: function () {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.carousel) return;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            self = this;
            self.render();
            self.triggerSlider();
          }
    }
    carousel.init();
    PLAYAEM.carousel = carousel;
    document.addEventListener('DOMContentLoaded', function () {
        if (!PLAYAEM.isDependencyLoaded) {
            carousel.init();
        }
        carousel.render();
        var $playerContainer = $(".has-video-carousel");
        var isPlayerElementLen = $playerContainer.length && $playerContainer.find(".video-player-banner").length;
        var videoIndx,
            player;
        $(window).scroll(function () {
            if (isPlayerElementLen) {
                videoIndx = $(".video-player-container.slick-current .video-player-banner").data("videoIndex");
                player = carousel.bannerPlayers && carousel.bannerPlayers[videoIndx];
                if (player) {
                    if (!carousel.isManuallyPaused[videoIndx] && ooyalaPlayer.isScrolledIntoView($playerContainer[0]) > 0) {
                        if (!player.isPlaying() && ((player.getState() == "ready") || player.getState() == "paused")) player.play();
                    } else {
                        if (player.isPlaying()) {
                            player.pause();
                            setTimeout(function () {
                                carousel.sliderPause();
                                carousel.isManuallyPaused[videoIndx] = false;
                            }, 100);
                        }
                    }
                }
            }
        });
    }, false);
}(window, PLAYAEM, PLAYAEM.ooyalaPlayer || undefined));
