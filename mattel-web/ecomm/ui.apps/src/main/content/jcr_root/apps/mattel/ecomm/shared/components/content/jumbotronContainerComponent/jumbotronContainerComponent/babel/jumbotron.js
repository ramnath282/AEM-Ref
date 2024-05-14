((() => {
    // defining configurations
    const config = {
        el: $("#anchor-indicators a").filter("[href]"),
        // $navGoNext: $(".jump-next-container"),
        $headerHeight: $("header").height() || 0,
        scrollScene: [],
        isTouchDevice: 'ontouchstart' in window,
        vh: window.innerHeight * 0.01,
        deviceName: /iPhone/.test(navigator.userAgent) && !window.MSStream && 'ontouchstart' in window ? 'ios-device-iphone' : (/(android)/i.test(navigator.userAgent) && 'ontouchstart' in window ? 'android-device' : '')
    };

    let controller;

    // initializing scroll
    const initScroll = () => {
        $('#anchor-indicators').css('visibility', 'hidden').addClass('active');
        controller = new ScrollMagic.Controller({
            globalSceneOptions: {
                triggerHook: 0.025,
                reverse: true
            }
        });
    };

    const activateScroll = elem => {
        if (!elem.length) return;
        let idName = elem.attr("id");
        let $indicatorParent = $("#anchor-indicators");
        let $parentElem = elem.closest(".jumbotronContainerComponent");
        let navEle = $(`.banner-menu-scroll a[href="#${idName}"]`);
        let $nextNaveEleState = navEle
            .closest("li")
            .next("li")
            .find("a");
        let $prevNaveEleState = navEle
            .closest("li")
            .prev("li")
            .find("a");

        config.scrollScene[idName] = new ScrollMagic.Scene({
                duration: elem.height(),
                triggerHook: "onLeave",
                offset: -(navEle[0].getBoundingClientRect().top),
                triggerElement: `#${idName}`
            })
            .setClassToggle(`#${navEle.attr("id")}`, "active")
            .on("start", e => {
                applyThemeToIndicator($parentElem.length ? $parentElem : elem);
                $nextNaveEleState.toggleClass("inactive");
            })
            .on("end", (e, t) => {
                applyThemeToIndicator($parentElem.length ? $parentElem : elem);
                $nextNaveEleState.toggleClass("inactive");
            })
            .on("enter", (evt) => {
                $(`#${idName}`).addClass("animating");
                if ($prevNaveEleState.length || $nextNaveEleState.length) {
                    $indicatorParent.addClass('active');
                } else {
                    $indicatorParent.removeClass('active');
                }
            })
            .on("leave", (evt) => {
                if (!($parentElem.length ? $parentElem : elem).length) {
                    $("#anchor-indicators").removeClass("active");
                }
                if (!$nextNaveEleState.length && evt.scrollDirection == "FORWARD") {
                    $indicatorParent.removeClass('active');
                } else {
                    $indicatorParent.addClass('active');
                }
            });
        controller.addScene(config.scrollScene[idName]);
    };

    const applyThemeToIndicator = elem => {
        let $indicatorEle = $("#anchor-indicators");
        if (elem.hasClass("text-white")) {
            $indicatorEle.toggleClass("text-white");
        } else if (elem.hasClass("text-black")) {
            $indicatorEle.toggleClass("text-black");
        }
    };
    const initAnimateScroll = () => {
        // let $secondaryHeaderHeight = ($(".promoPencilContainer").height() || 0) + ($(".pencil-drawer").height() || 0) + ($(".header-promo-container").height() || 0);
        // const pencilDrawerHeight = $secondaryHeaderHeight ? $secondaryHeaderHeight+ (window.innerWidth >= 480 ? 0 : 0) : 0;
        controller.scrollTo(target => {
            TweenMax.to(window, 0.75, {
                scrollTo: {
                    // (window.innerWidth < 1200 || $(".jumbotron-fixed-off").length) ? 0 : 0
                    // y: target - ($("#media-banner-0").offset().top == target ? pencilDrawerHeight : 0),
                    y: target,
                    force3D: true,
                    autoKill: false // Allow scroll position to change outside itself
                },
                ease: Power3.easeInOut
            });
        });

        $(document).on("click", "a[href^=\\#], .cta-link-component .internal", function(e) {
            if($(this).is(".rewards-btn, .apply-promo-code")) return;
            const id = $(this).attr("href");
            if ($(id).length > 0) {
                e.preventDefault();
                updatePromoDrawerAndScroll(id);

            }
        });
    };
    const injectScroll = () => {
        let compId;
        const $ele = $(config.el);
        if (!$ele.length) {
            console.log("media type attribute or hero banner element not found..");
            return;
        }
        initAnimateScroll();
        _.each($ele, item => {
            compId = item.getAttribute("href");
            activateScroll($(compId));
        });
        config.el.length > 1 && $('#anchor-indicators').css('visibility', 'hidden');
    };
    const addChildrenClassNames = ele => {
        const $ele = $(ele);
        let $ctaLinks = $ele.find(".cta-link-component");
        $ele.find(".jump-next-container").length && $ele.addClass("carat-arrow-on");
        if ($ctaLinks.length) {
            $ele.addClass("cta-links-on");
            $ctaLinks.addClass(`cta-links-column-${$ctaLinks.find('a').length}`)
        }
        $ele.find(".cardsContainer").length && !$ele.find(".cardsContainer .flexible-grid-component > .cta-button").length && $ele.addClass("no-inner-button");
        if ($ele.find(".cardsContainer").length || $ele.find(".carouselContainer").length) {
            $ele.find(".player-container").length && $ele.addClass("background-video-on");
        } else if ($ele.css('background-image') && $ele.css('background-image').split(/"/)[1] && $ele.css('background-image').split(/"/)[1].match(/\.(jpeg|jpg|gif|png)$/)) {
            $ele.addClass("background-image-on");
        }
    };
    const checkMediaType = () => {
        const $ele = $(config.el);
        if (!$ele.length) {
            console.log("media type attribute or hero banner element not found..");
            return;
        }
        initScroll();
        let compId;
        let $compElem;
        _.each($ele, item => {
            compId = item.getAttribute("href");
            $compElem = $(compId);
            if (!$compElem.length || $compElem.hasClass('loaded')) {
                console.log(`${compId} element not found..`);
                return;
            }
            $compElem.addClass('loaded');
            addChildrenClassNames($(compId));
            if (/MSIE \d|Trident.*rv:/.test(navigator.userAgent) && window.innerWidth > 1024) {
                updateImageZoomEffect($compElem);
            }
        });
    };

    const updateImageZoomEffect = elem => {
        let $imgElem = $(elem).find('[data-cmp-lazy]').length ? $(elem).find('[data-cmp-lazy]') : $(elem).find('.cmp-image img');
        if (!$imgElem.length) {
            return;
        }
        let t = $imgElem,
            src = $imgElem.attr('src') || $imgElem.attr("data-asset"),
            s = 'url(' + src + ')',
            p = t.parent(),
            d = $('<div></div>');
        t.css('visibility', 'hidden');
        p.append(d);
        d.css({
            'background-size': 'cover',
            'background-repeat': 'no-repeat',
            'background-origin': 'content-box',
            'background-image': s,
            'position': 'absolute',
            'top': 0,
            'bottom': 0,
            'width': '100%'
        });
    };
    const applyVHForResponsive = () => {
        if (config.deviceName == "android-device") {
            document.documentElement.style.setProperty('--vh', `${config.vh}px`);
        }
    }
    const updatePromoDrawerAndScroll = (id) => {
        let $promoBannerEle = $(".promo-banner-on .promo-header-dropdown");
        if ($promoBannerEle.hasClass("in")) {
            $promoBannerEle.collapse("hide");
            $promoBannerEle.on('hidden.bs.collapse', function() {
                controller.scrollTo(id);
            });
        } else {
            controller.scrollTo(id);
        }
    }
    const bindMobileNavbarIdentifier = () => {
        if (config.deviceName != "ios-device-iphone") {
            return;
        }
        let min_inner_height = false;
        let max_inner_height = false;

        let passiveIfSupported = false;
        try {
            window.addEventListener("test", null, Object.defineProperty({}, "passive", {
                get: function() { passiveIfSupported = { passive: true }; }
            }));
        } catch (err) {}
        document.addEventListener('scroll', function(e) {
            let win_inner_h = window.innerHeight;
            if (/iPad|iPhone|iPod/.test(navigator.userAgent)) {
                if (min_inner_height === false || win_inner_h < min_inner_height) {
                    min_inner_height = win_inner_h;
                }
                if ((max_inner_height === false || win_inner_h > max_inner_height) && win_inner_h > min_inner_height) {
                    max_inner_height = win_inner_h;
                }
                if (max_inner_height !== false && max_inner_height == win_inner_h) {
                    $('body').addClass('safari-toolbars-hidden');
                } else {
                    $('body').removeClass('safari-toolbars-hidden');
                }
            }
        }, passiveIfSupported);
    }
    const initTouchActions = () => {
        if (config.isTouchDevice) {
            bindMobileNavbarIdentifier();
        }
    }
    const popupVideoEvents = () => {
        let $closestEle;
        $("#VRModal").on('show.bs.modal', function() {
            $closestEle = $(this).closest(".content");
            $closestEle.length && $closestEle.css('z-index', '111111');
        }).on('hidden.bs.modal', function() {
            $closestEle = $(this).closest(".content");
            $closestEle.length && $closestEle.css('z-index', '1');
        });
    }
    const init = () => {
        // bindingEvents();
        if (config.deviceName) {
            $('body').addClass(config.deviceName)
        }
        checkMediaType();
        config.isTouchDevice && applyVHForResponsive();
        document.addEventListener("DOMContentLoaded", function(event) {
            setTimeout(() => {
                checkMediaType();
                injectScroll();
                initTouchActions();
                popupVideoEvents();
                $("#anchor-indicators li").length > 1 &&
                    $("#anchor-indicators").removeClass("hide");
            }, 1000);
        });
    };
    init();
})());
