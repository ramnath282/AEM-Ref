/**
 * product-gallery.js
 * Version 1.0
 */
(function (global, PLAYAEM, ooyalaPlayer) {
    var self,
    	isDestroyed = false,
        modalBinded = false,
        touchtime = 0,
        switchOffZoom = true,
        carouselEnglishTxt,
        productName,
        cloudZoomEnable;
    var productGallery = {
        el: '.product-gallery',
        smallSliderEle: ".small-product-images .carousel-slides",
        largeSliderEle: ".larger-product-images .carousel-slides",
        isTouch: PLAYAEM.isTouchDevicePlay(),
        bindingEventsConfig: function () {
            var events = {
                "click .product-gallery:not(.open-modal) .larger-product-images li img": "showGalleryModal",
                "click .close-gallery-modal": "hideGalleryModal"
            };
            return events;
        },
        doubleTapBind: function (elem, cb) {
            $(document).on("click", elem, function (evt) {
                var curEle = this;
                self.doubleTapCB(function () {
                    self.doubleTapZoom(curEle);
                });
            });
        },
        doubleTapCB: function (cb) {
            console.log("deal with clicks or taps");
            if (touchtime == 0) {
                touchtime = new Date().getTime();
            } else {
                if (((new Date().getTime()) - touchtime) < 800) {
                    //   alert("double clicked");
                    cb(this);
                    touchtime = 0;
                } else {
                    touchtime = 0;
                }
            }
        },
        doubleTapZoom: function (ele, event) {
            console.log("double clicks fires.." + ele);
            if ($(ele).attr('style') && $(ele).attr('style').indexOf('cursor') != -1) {
                self.destroyPanZoom(ele);
            } else {
                $(ele).panzoom({
                    'startTransform': 'scale(2)',
                    panOnlyWhenZoomed: true,
                    focal: '50% 50%',
                    increment: 0.3,
                    minScale: 1,
                    contain: 'invert',
                    "animation": true
                }).panzoom('zoom');
                touchtime = 0;
                $(ele).on('panzoomend', function (e, panzoom, matrix, changed) {
                    var curEle = e.currentTarget;
                    if (changed) {
                        console.log("deal with drags or touch moves");
                    } else {
                        self.doubleTapCB(function () {
                            self.doubleTapZoom(curEle);
                        });
                    }
                    return true;
                });
            }
        },
        destroyPanZoom: function (ele) {
            if ($(ele).panzoom("destroy")) $(ele).removeAttr("style");
        },
        refreshSlider: function (isCloseModal) {
            if($(".product-gallery.open-modal").length || isCloseModal){
                $(self.smallSliderEle)[0].slick && $(self.smallSliderEle).slick('resize');
                $(self.largeSliderEle)[0].slick && $(self.largeSliderEle).slick('reinit');
            }
        },
        showGalleryModal: function (ele, evt) {
            if (!self.isTouch) return;
            var $par = $(ele).parents(".product-gallery");
            $par.toggleClass("open-modal");
            if (!modalBinded && !switchOffZoom) {
                self.doubleTapBind(".open-modal .larger-product-images .slick-active .zoom-image img", self.doubleTapZoom);
                modalBinded = true;
            }
            self.refreshSlider();
        },
        hideGalleryModal: function (ele, evt) {
            self.showGalleryModal(ele, evt);
            self.destroyPanZoom(".larger-product-images .slick-active img");
            self.refreshSlider('hide');
        },
        initZoom: function () {
            var options,
                $img = $('.cloudzoom'),
                i = 0;
            if ($img.length > 0) {
                if (window.innerWidth > 767) {
                    _.each($img, function (item) {
                        ++i;
                        options = "zoomImage: '" + $(item).data('imageZoom') + "',zoomSizeMode: 'image',autoInside: 1023,zoomFlyOut:false"
                        $(item).attr({
                            'data-cloudzoom': options,
                            'id': 'zoom'+i
                        });
                    });
                    CloudZoom.quickStart();
                }
            }
        },

        destroyZoom: function () {
            var $img = $('.cloudzoom');
            _.each($img, function (item) {
                var theImage = new Image();
                theImage.src = $(item).attr('data-image-zoom');
                if (theImage.complete) {
                    if (theImage.width < 1000) {
                        var $myInstance = $(item).data('CloudZoom');
                        $myInstance && $myInstance.destroy();
                    }
                }
            });
        },
        syncThumbnail: function (ele, evt, productEle) {
            // evt.preventDefault();
            var goToSingleSlide = $(ele).data('slick-index');
            var index= goToSingleSlide+1;
            var type = $(ele).hasClass("video-player-tile") ? "video" : "image";
            var trackingId = "Product Carousel|"+carouselEnglishTxt+"|"+carouselEnglishTxt+"|Position"+index+":"+type;
            if (cloudZoomEnable) {
                if ($(ele).hasClass("video-player-tile")) {
                    if (!isDestroyed) {
                        self.destroyZoom();
                        isDestroyed = true;
                    }
                } else if (isDestroyed) {
                    CloudZoom.quickStart();
                    self.playerAction();
                    isDestroyed = false;
                }
            } else if (!$(ele).hasClass("video-player-tile")) {
                self.playerAction();
            }
            $(self.smallSliderEle + ' li').removeClass('slick-current');
            $(self.smallSliderEle + ' li[data-slick-index=' + goToSingleSlide + ']').addClass('slick-current');
            $(productEle).slick('slickGoTo', goToSingleSlide);
            console.log(trackingId);
            PLAYAEM.getTrackingValues(ele, evt, trackingId);
        },
        applyCarousel: function () {
            var productEle = self.largeSliderEle,
                thumbEle = self.smallSliderEle;
            $(productEle).slick({
                slidesToShow: 1,
                slidesToScroll: 1,
                arrows: false,
                fade: true,
                responsive: true,
                autoplay: false
            });
            $(thumbEle).slick({
                slidesToShow: 6,
                slidesToScroll: 1,
                dots: false,
                focusOnSelect: false,
                infinite: false,
                responsive: [{
                    breakpoint: 480,
                    settings: {
                        slidesToShow: 3.5
                    }
                }]
            });
            $(thumbEle).on('beforeChange', function (event, slick, currentSlide, nextSlide) {
                var alwaysEnglishPrd = $('#alwaysEnglishPrd').val();
                var currentIndex= $(this).find(".slick-current").data("slick-index");
                var prevNextVal;
                var trackingVal;
                //tracking
                if (nextSlide > currentSlide || (currentSlide === (slick.$slides.length - 1) && nextSlide === 0)) {
                    prevNextVal = "Right";
                    currentIndex++;
                } else {
                    prevNextVal = "Left";
                    currentIndex--;
                }
                 if(currentIndex>=0) $(productEle).slick('slickGoTo', currentIndex, false);
                trackingVal = 'Navigating Arrows|' + alwaysEnglishPrd + '|' + prevNextVal + '| product carousel';
                console.log(trackingVal);
                PLAYAEM.getTrackingValues(undefined, undefined, trackingVal);
            });
            $(productEle).on('afterChange', function (event, slick, currentSlide, nextSlide) {
                var currentIndex= $(this).find(".slick-current").data("slick-index");
              if(currentIndex>=0) $(productEle).slick('slickGoTo', currentIndex, false);
              $(thumbEle + ' li').removeClass('slick-current');                
              $(thumbEle + ' li[data-slick-index=' + currentSlide + ']').addClass('slick-current');
              var currrentNavSlideElem = thumbEle + ' .slick-slide[data-slick-index="' + currentSlide + '"]';
                             
              $(thumbEle + ' .slick-slide.is-active').removeClass('is-active');
              $(currrentNavSlideElem).addClass('is-active');

              $(thumbEle + ' li').attr({
                  'aria-selected': 'false'
              });
              
              $(currrentNavSlideElem).attr({
                  'aria-selected': 'true'
              });

          });

            // if (self.isTouch) {
                $(thumbEle).on('click', '.slick-slide', function (event) {
                    self.syncThumbnail(this, event, productEle);
                });
            // } else {
            //     $(thumbEle).on('mouseenter', '.slick-slide', function (event) {
            //         self.syncThumbnail(this, event, productEle);
            //     });
            // }
            cloudZoomEnable && self.initZoom();
            self.playerInit();
        },
        playerAction: function () {
            if (!self.player) return;
            if (self.player && self.player.isPlaying()) self.player.pause();
        },
        playerInit: function () {
            var $playerELe = $(".larger-product-images .product-player-container");
            if ($playerELe.length) {
                ooyalaPlayer.init($playerELe.attr('id'), function (plyr) {
                    self.player = plyr;
                });
            }
        },
        resize: function(){
            $(window).on('resize',function(){
                self.refreshSlider();
            });
        },
        init: function () {
            self = this;
            if (!PLAYAEM.isDependencyLoaded || !$(self.el).length || PLAYAEM.productGallery) return;
            PLAYAEM.bindLooping(self.bindingEventsConfig(), self);
            cloudZoomEnable = !switchOffZoom && (self.isTouch ? false : true);
            self.applyCarousel();
            carouselEnglishTxt = $('#alwaysEnglishPC').val() || 'englishTxt not found';
            // self.resize();
        }
    }
    productGallery.init();
    PLAYAEM.productGallery = productGallery;
    document.addEventListener('DOMContentLoaded', function () {
        if (!PLAYAEM.isDependencyLoaded) {
            productGallery.init();
        }
    }, false);
}(window, PLAYAEM, PLAYAEM.ooyalaPlayer));
