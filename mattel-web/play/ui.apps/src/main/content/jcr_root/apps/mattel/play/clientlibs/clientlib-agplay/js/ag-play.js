var PLAYAEM;
(function(global) {
    var self,
        count = 0,
        cnt = 0,
        indx = 0,
        pluginLoaded = false,
        pluginCheckCnt = 0,
        isIOSDevice = /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream,
        agPlay = {
            applyCarousel: function(config, el) {
                if ($(el).length) {
                    var setting = {
                        dots: config.dots || false,
                        arrows: config.arrows || false,
                        infinite: false,
                        speed: 800,
                        slidesToShow: config.slideToShow || 3,
                        slidesToScroll: config.slideToScroll || 1,
                        centerMode: config.centerMode || false,
                        adaptiveHeight: false,
                        autoplay: config.autoPlay || false,
                        autoplaySpeed: 4000,
                        variableWidth: config.variableWidth || false,
                        centerPadding: '15px',
                        // fade: true,
                    };
                    if (config.responsive) {
                        setting.responsive = config.responsive;
                    }
                    $(el).addClass("slider-loaded").slick(setting);
                } else {
                    if (cnt > 3) return;
                    setTimeout(function() {
                        self.applyCarousel(config, el);
                        cnt++;
                    }, 500);
                }
                // return slider;
            },
            getCarouselConfig: function(compName) {
                var config;
                switch (compName) {
                    case "characterComp":
                        config = {
                            slideToShow: 5,
                            arrows: true,
                            responsive: [{
                                    breakpoint: 920,
                                    settings: {
                                        slidesToShow: 4
                                    }
                                }, {
                                    breakpoint: 767,
                                    settings: {
                                        slidesToShow: 2.5
                                    }
                                }, {
                                    breakpoint: 480,
                                    settings: {
                                        slidesToShow: 1.2
                                    }
                                }]
                                // variableWidth : true
                        }
                        break;
                    default:
                        config = {};
                }
                return config;
            },
            updatePromoBannerEffect: function() {
                var $ele = $(".image-text-container .col-2 .enable-carousel .slick-slider");
                if ($ele.length) {
                    _.each($ele, function(item) {
                        $(item).slick('slickSetOption', { fade: true, cssEase: 'linear' }).slick("refresh");
                    });
                } else {
                    if (count > 3) return;
                    setTimeout(function() {
                        self.updatePromoBannerEffect();
                        count++;
                    }, 500);
                }
            },
            updateHoverImageSrc: function(elem) {
                var $ele = $(elem);
                var hoverSrc;
                if ($ele.length) {
                    _.each($ele, function(item) {
                        hoverSrc = $(item).attr('data-hover');
                        if (hoverSrc.indexOf('undefined') != -1) {
                            $(item).attr('data-hover', $(item).attr('data-src'));
                        }
                    });
                } else {
                    if (count > 3) return;
                    setTimeout(function() {
                        self.updateHoverImageSrc(elem);
                        count++;
                    }, 500);
                }
            },
            menuNavUpdate: function() {
                var $navEle = $(".navigationHeader header .primary-nav ul");
                var $homeMenu = $(".brand-logo:last");
                if (!$navEle.length || !$homeMenu.length) {
                    return;
                }
                $navEle.prepend('<li class="home-menu">' + $homeMenu.parent().html() + '</li>');
            },
            pageNameActions: function() {
                var pageName = $('body').hasClass('character-page') ? 'characters' : ($("#pageNamesForAnalytics").val() || "");
                var pageNameInFormatted = pageName.toLowerCase();
                var $bodyTag = $('body');
                switch (pageNameInFormatted) {
                    case "home":
                    case "home1":
                        this.applyCarousel(this.getCarouselConfig('characterComp'), ".play-characters .gallery-tile ul");
                        this.updatePromoBannerEffect();
                        // alert("Home Page");
                        break;
                    case "games":
                        this.updateHoverImageSrc(".play-character-landing-block .gallery-tile ul>li a .gallery-image img[data-hover]");
                        break;
                    case "courtney":
					case pageNameInFormatted.toLowerCase().startsWith("goty") && pageNameInFormatted:
                    case pageNameInFormatted.endsWith("-stretched") && pageNameInFormatted:
                        if(window.innerWidth <= 1200){
                            this.applyIframeHeight();
                            window.addEventListener('resize', this.applyIframeHeight)
                        }
                        break;
                    case "characters":
                        this.OOReady();
                        this.initiateVideoPlayer();
                        break;
                    case "videos":
                        break;
                    case "products":
                        break;
                    default:
                        console.log("unknown page");
                        break;
                }
                $bodyTag.addClass('ag-play-' + pageNameInFormatted + '-page');
            },
            initiateVideoPlayer: function() {
                var $el = $("#video-gallery-player-component");
                var $gamesEle = $(".play-tiles-gallery.play-games");
                if (!$gamesEle.find('.gallery-tile>ul li').length) {
                    $gamesEle.hide();
                }
                if (!$el.length) return;
                var $playlist = $el.find(".play-list li");
                if (!$playlist.length) {
                    $el.hide();
                    return;
                }
                this.waitForElement(function() {
                    if ($playlist.length) {
                        if (!pluginLoaded) {
                            console.log("ooyala plugins not loaded..");
                            return;
                        }
                        PLAYAEM.bindLooping(self.videoPlayerEvents(), self);
                        //var videoId = $playlist.data('videoId');
                        $el.addClass($playlist.length == 1 ? "single-video-player" : 'playlist-video-player');
                        $el.find(".play-list li:eq(0)")[0].click();
                    }
                });
            },
            videoPlayerEvents: function() {
                var events = {
                    "click .play-list li": "thumbnailAction",
                    "click #play-list-datas li": "thumbnailAction",
                    "click .video-player-container .slide-btn": "videoSlide"
                };
                return events;
            },
            thumbnailAction: function(ele, evt) {
                evt.preventDefault();
                var $ele = $(ele);
                var $parentContainer = $(".video-player-container");
                if ($ele.hasClass("active")) {
                    return false;
                }
                var videoId = $ele.data('videoId'),
                    videoTitle = $ele.find(".tile-content").text(),
                    videoDesc = $ele.find(".tile-desc").text(),
                    $parentUL = $ele.closest('ul'),
                    index = $(ele).data("index");
                if (!self.player) {
                    self.player = OO.Player.create("main-player-container", videoId, PLAYAEM.ooParams);
                    setTimeout(function() {
                        $("#main-player-container").data('autoplay') == "true" && !isIOSDevice && self.player.play();
                    }, 10);
                } else {
                    self.player.setEmbedCode(videoId);
                    setTimeout(function() {
                        !isIOSDevice && self.player.play();
                    }, 10);
                }
                $parentContainer.find("#video-player-title").html(videoTitle);
                $parentContainer.find("#video-player-desc").html(videoDesc);
                $parentUL.find('li').removeClass("active");
                $parentUL.find("li[data-index=" + index + "]").addClass("active");
            },
            videoSlide: function(ele, evt) {
                evt.preventDefault();
                var actionName = $(ele).data("action");
                if (actionName == "prev") {
                    if (indx == 0) {
                        indx = $('#videos-gallery .play-list li').length;
                    }
                    indx = indx - 1;
                } else if (actionName == "next") {
                    indx = indx + 1;
                    if (indx > $('#videos-gallery .play-list li').length - 1) {
                        indx = 0;
                    }
                }
                //$(".play-list li[data-index=" + indx + "]")[0].click();
                $(ele).parents(".video-component").find(".play-list li[data-index=" + indx + "]")[0].click();
            },
            OOReady: function() {
                if (typeof OO === "undefined") return;
                OO.ready(function() {
                    //player ready
                    pluginLoaded = true;
                });
            },
            waitForElement: function(callBack) {
                var self = this;
                window.setTimeout(function() {
                    if (pluginLoaded) {
                        callBack(true);
                    } else {
                        pluginCheckCnt++;
                        if (pluginCheckCnt > 15) {
                            return;
                        }
                        self.waitForElement(callBack);
                    }
                }, 1000)
            },
            applyIframeHeight: function() {
                var $ele = $(".game-player-area-container");
                if(!$ele.length) return;
                var offsetTop = ($(".page-view").offset().top + parseInt($(".game-player-area-content").css('padding') || 0)) || 0;
                $ele.css('--app-height', window.innerHeight - offsetTop+'px');
            },
            init: function() {
                if (PLAYAEM.agPlay) return;
                self = this;
                this.pageNameActions();
            }
        }
        // agPlay.init();
    document.addEventListener('DOMContentLoaded', function() {
        agPlay.init();
        //window.innerWidth < 767 && agPlay.menuNavUpdate();
        PLAYAEM.agPlay = agPlay;
    }, false);
}(window));
