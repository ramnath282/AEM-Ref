/**
 * brandList.js
 * Version 1.0
 */
(function (global, PLAYAEM) {
    // 'use strict';
    var self,
        carouselApplied = false,
        loaded = false,
        isMobile = window.innerWidth < 768,
        brandList = {
            el: '.brand-list-item',
            bindingEventsConfig: function () {
                var events = {
                    "click .more-dropdown-btn.brands-loading": "showMoreBrands",
                    "click .more-dropdown-btn.brands-loaded": "showLessBrands",
                    "click .brand-list-item .slick-prev , .brand-list-item .slick-next": "analyticsTrack",
                    "click .brand-list-item .slick-dots li button": "carouselBtnGh"
                };
                return events;
            },
            brandsOnMobLoad: function () {
                var $parentEle = $('.global-mattel-header #brand-list-dropdown'),
                    onLoadData = $parentEle.data("mobileLoad") || 6,
                    size_li = $parentEle.find("li").length;
                $(self.el + ' li:lt(' + onLoadData + ')').show();
                if (size_li <= onLoadData) {
                    $(".more-dropdown-btn").removeClass("brands-loading brands-loaded");
                } else {
                    $(".more-dropdown-btn").addClass("brands-loading");
                }
            },
            showMoreBrands: function (el, evt) {
                var $parentEle = $('.global-mattel-header #brand-list-dropdown'),
                    onLoadData = $parentEle.data("mobileLoad") || 6,
                    onNextLoad = $parentEle.data("nextLoad") || 'all',
                    size_li = $(self.el).find("li").length;
                onLoadData = (onLoadData + onNextLoad <= size_li && onNextLoad != 'all') ? onNextLoad + onLoadData : size_li;
                $(self.el + ' li:lt(' + onLoadData + ')').show();
                //$(".more-dropdown-btn").removeClass("brands-loading");
                if (onLoadData == size_li) {
                    $(".more-dropdown-btn").removeClass("brands-loading").addClass("brands-loaded");
                }
            },
            showLessBrands: function (el, evt) {
                var $parentEle = $('.global-mattel-header #brand-list-dropdown'),
                    onLoadData = $parentEle.data("mobileLoad") || 6,
                    onNextLoad = $parentEle.data("nextLoad") || 'all';
                onLoadData = (onLoadData - onNextLoad < 0 || onNextLoad == 'all') ? onLoadData : onLoadData - onNextLoad;
                $(self.el + ' li').not(':lt(' + onLoadData + ')').hide();
                $(".more-dropdown-btn").removeClass("brands-loaded").addClass("brands-loading");
                // if(onLoadData == 3){
                // $(".more-dropdown-btn").removeClass("brands-loaded").addClass("brands-loading");
                // }
            },
            initCarousel: function(){
                var ele = $(".global-mattel-header").find(self.el + '[data-maintain-height]');
                if(!ele.length){
                    return;
                }
                if (isMobile) {
                    self.brandsOnMobLoad();
                } else {
                    self.checkCarousel(ele);
                }
                carouselApplied = true;
                $(".global-mattel-header").attr('role', 'banner');
            },
            accordionCB: function () {
                var ele = $(".global-mattel-header").find(self.el + '[data-maintain-height]'),
                    $dropdownEle= $('.global-mattel-header #brand-list-dropdown'),
                    $iconEle = $(".toggle-brands-list .action-btn"),
                    dataMoreAnalytics = $iconEle.data("tracking-more"),
                    dataLessAnalytics = $iconEle.data("tracking-less");
                if (!isMobile && $dropdownEle.length){
                    $dropdownEle.css({'height':'0','display':'block','overflow':'hidden'});
                }
                $dropdownEle.on('show.bs.collapse', function () {
                    if (carouselApplied && !isMobile){
                        ele.length && PLAYAEM.maintainHeight(ele);
                        typeof ele.slick == "function" && ele.slick('setPosition').slick('refresh');
                    }
                    self.hoverLogo();
                    // self.heightcheck(ele);
                    PLAYAEM.getTrackingValues($iconEle, undefined, dataMoreAnalytics);
                }).on('hide.bs.collapse', function () {
                    console.log("hide");
                    PLAYAEM.getTrackingValues($iconEle, undefined, dataLessAnalytics);
                });
                $(window).resize(function () {
                    // self.heightcheck($('.global-mattel-header #brand-list-dropdown .brand-list-item'),true);
                });
            },
            analyticsTrack: function (el, evt) {
                var alwaysEnglishGH = $('#alwaysEnglishGH').val();
                var prevNextVal;
                if ($(el).attr('aria-label') == "Previous") {
                    prevNextVal = "Left";
                } else if ($(el).attr('aria-label') == "Next") {
                    prevNextVal = "Right";
                }
                var trackingVal = 'Navigating Arrows|' + alwaysEnglishGH + '|' + prevNextVal + '|Global Header';
                PLAYAEM.getTrackingValues(el, evt, trackingVal);
            },

            carouselBtnGh: function (el, evt) {
                var carlBtnValue = $(el).text();
                var alwaysEnglishGH = $('#alwaysEnglishGH').val();
                var trackingVal = 'Navigating Arrows|' + alwaysEnglishGH + '| Pagination Dots:' + carlBtnValue + '|Global Header';
                PLAYAEM.getTrackingValues(el, evt, trackingVal);

            },

            checkCarousel: function (ele) {
                var parentHeader = $(ele).closest("#brand-list-dropdown");
                var isParentHeader = parentHeader.length ? 1 : 0;
                if (!isParentHeader) {
                    return;
                }
                if (!$(self.el).hasClass("slick-initialized")) {
                    self.applyCarousel(ele, parentHeader[0].dataset);
                }
            },
            heightcheck: function (ele, resizeTrue) {
                if ((resizeTrue && !$("#brand-list-dropdown.in").length) || !ele) {
                    return;
                }
                var globalHdr = $(".global-header-wrapper").height() || 0,
                    windowHeight = (window.innerHeight - (globalHdr));
                if (ele.innerHeight() > windowHeight) {
                    ele.parents('#brand-list-dropdown').css({
                        'max-height': windowHeight + 'px',
                        'overflow-y': 'scroll'
                    })
                }
            },
            applyCarousel: function (el, config) {
                var slider = el.slick({
                    slidesToShow: parseInt(config.slideShow) || 1,
                    slidesToScroll: parseInt(config.slideScroll) || 1,
                    autoplay: config.autoplay == 'true' ? true : false,
                    dots: config.dots == 'true' ? true : false,
                    arrows: config.arrows == 'false' ? false : true,
                    autoplaySpeed: parseInt(config.speed) || 2000,
                    infinite: config.infinite || false,
                    responsive: [{
                        breakpoint: 1025,
                        settings: {
                            slidesPerRow: 4
                        }
                    }, {
                        breakpoint: 769,
                        settings: {
                            slidesPerRow: 3
                        }
                    }],
                    accessibility: true,
                    pauseOnFocus: false,
                    pauseOnHover: true,
                    slidesPerRow: 5,
                    rows: 2,
                    pauseOnDotsHover: false
                });
                return slider;
            },
            destroyCarousel: function () {
                if ($(self.el).hasClass("slick-initialized")) {
                    $(self.el).slick('unslick');
                }
            },
            render: function () {
                window.onresize = function (event) {
                    if (isMobile) {
                        self.destroyCarousel();
                    }
                };
            },
            setImageMobile: function() {
              if (isMobile) {
                $(".brand-list-item li").each(function(idx,liElem){
                  if($('a img',liElem).data('mobile-src'))
                  {
                    $('a img',liElem).attr('src', $('a img',liElem).data('mobile-src'));
                  }
                });
              }
            },
            hoverLogo: function () {
              var imgElem = null;
              if(!isMobile) {
                $(".brand-list-item li a").mouseover(function () {
                  imgElem = $(this).children('img');

                  if(imgElem.data("hover")){
                    imgElem.attr('src', imgElem.data("hover"));
                  } else
                  {
                    imgElem.attr('src', imgElem.data("src"));
                  }
                }).mouseout(function () {
                  imgElem.attr('src', imgElem.data("src"));
                });
              }
            },
            init: function () {
                self = this;
                if (!PLAYAEM.isDependencyLoaded || !$(self.el).length || loaded) return;
                loaded = true;
                PLAYAEM.bindLooping(self.bindingEventsConfig(), self);
                self.render();
                self.setImageMobile();
                self.hoverLogo();
                self.accordionCB();
                self.initCarousel();
            }
        }
    PLAYAEM.brandList = brandList;
    brandList.init();
    document.addEventListener('DOMContentLoaded', function () {
        if (!PLAYAEM.isDependencyLoaded) {
            brandList.init();
        }
    }, false);
}(window, PLAYAEM));
