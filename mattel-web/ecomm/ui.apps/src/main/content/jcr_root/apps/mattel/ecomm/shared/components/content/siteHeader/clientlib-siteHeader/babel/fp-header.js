/**
 * FP Header.js
 * Version 1.0
 */

import eventBinding from '../shared/eventBinding';
import ajaxRequest from '../shared/ajaxbinding';
import fpApiConfig from '../shared/fpApiConfig';


const config = {
    el: '.header',
    navigationContainer: $('#primary-nav-bar'),
    primaryNavSection: $('.primary-nav'),
    navSearchContainer:$('.nav-search'),
    primaryNavID: $('#primary-nav'),
    menuButtonforMob: $('.hamburger-menu'),
    overlayMask: $('.mask'),
    priceSpider: $("#pricespider-div"),
    newsLetterContainer: $(".newsletter-sign-up"),
    headerContainer: $("header"),
    reviewContainer: $('#bvReviewHeading'),
    headerWrapper: $('.sub-header-wrapper'),
    linkButton: $('#CTAlink'),
    hamburgerButton: $('.hamburger'),
    stickBorClass:'sticky-border',
    scrollStickyList:  $('.scroll-sticky'),
    modelCloseButton: $('.close-x'),
    recallContainer: $('.recall-component'),
    productFeatures: $('.productFeatures .product-features'),
    maskLayer : $('.menu-sliding-div-mask'),
    onlyDesktop: window.innerWidth > 1024,
    isMobile: window.innerWidth <= 900,
};

class fpheader {

    constructor() {
        self = this;
        this.element = '.header';
        evtBinding.bindLooping(self.bindingEventsConfig(), self);
        $(document).ready(function(){
            self.init()
        });
        this.navSearchContainer = $('.nav-search');
        if($(window).width() <= 1024){
            $('.search-menu').attr('data-toggle','toggle');
            this.mobnavAnimation();
        }
    }
    
    bindingEventsConfig() {
        let events = {
            "click .hamburger": "showNavigation",
            "click .header-close-menu": "fnMenuClose",
            "click .hamburger-menu": "showNavigationV2",
            "click .primary-nav ul li a": "closeMenu",
            "click .nav-search .input-group": "navSearchBox",
            "keypress  .nav-search .input-group": "navSearchBox",
            "focusout .nav-search .input-group": "navSearchBoxFocusOut",
            "click .close-div": "closeNavSearch",
            "click .close-x": "clearNavSearch",
            "click .play-backtotop": "backToTopAction",
            "click .show-sub-nav": "showSublevel",
            "click .search-menu": "showNavigationSearch",
            'click .menu-sliding-div-mask': 'hideMobileOverlay',
            'mouseover .primary-nav ul li': 'maskLayer',
            'mouseout .primary-nav ul li': 'maskLayerout',
            "click [data-analytics-tracking-id]": "getNavTrackingValues",
            "click .mobarrow-icon": "mobileRecallSlideUp",
            "click .signup-close": "removeRecallMar",
            "click #pricespider-link-button": "buyNowClick",
            "click .readmore": "readMoreClick",
            "click .product-reviews a": "starLinkClick",
            "mouseenter #menuNav0 .sub-nav .img-list li a": "subMenuAnimationON",
            "mouseenter .sub-nav.explore li a": "subMenuAnimationON",
            "mouseleave #primary-category-data>li": "subMenuAnimationOFF",
        }
        return events;
    }
    mobnavAnimation() {
        $('#primary-nav ul').on('shown.bs.collapse', function(e) {
            const clicked = $(e.target).attr('id'),
                clickedid = $('#' + clicked);
            const shopbycategory = $(clickedid).find('.shop-by-category').length,
                shopbybrands = $(clickedid).find('.shop-by-brands').length,
                shopbyage = $(clickedid).find('.explore').length;
            if (shopbycategory == 1) {
                $(clickedid).find('.shop-by-category ul li.column-3').each(function(i) {
                    $(this).delay(200 * i).animate({
                        opacity: 1,
                        bottom: 0
                    }, 500);
                });
            } else if (shopbybrands == 1) {
                $(clickedid).find('.shop-by-brands ul li.column-3').each(function(i) {
                    $(this).delay(200 * i).animate({
                        opacity: 1,
                        bottom: 0
                    }, 500);
                });
            } else if (shopbyage == 1) {
                $(clickedid).find('.explore ul li.column-4').each(function(i) {
                    $(this).delay(200 * i).animate({
                        opacity: 1,
                        bottom: 0
                    }, 500);
                });
            } else {
                $(clickedid).find('li').each(function(i) {
                    $(this).delay(200 * i).animate({
                        opacity: 1,
                        bottom: 0
                    }, 500);
                });
            }
        });
        $('#primary-nav ul').on('hidden.bs.collapse', function(e) {
            const clicked = $(e.target).attr('id'),
                clickedid = $('#' + clicked);
            const shopbycategory = $(clickedid).find('.shop-by-category').length,
                shopbybrands = $(clickedid).find('.shop-by-brands').length,
                shopbyage = $(clickedid).find('.explore').length;
            if (shopbycategory == 1) {
                $(clickedid).find('.shop-by-category ul li.column-3').each(function(i) {
                    $(this).animate({
                        opacity: 0,
                        bottom: -10
                    });
                });
            } else if (shopbybrands == 1) {
                $(clickedid).find('.shop-by-brands ul li.column-3').each(function(i) {
                    $(this).animate({
                        opacity: 0,
                        bottom: -10
                    });
                });
            } else if (shopbyage == 1) {
                $(clickedid).find('.explore ul li.column-4').each(function(i) {
                    $(this).animate({
                        opacity: 0,
                        bottom: -10
                    });
                });
            } else {
                $(clickedid).find('li').each(function(i) {
                    $(this).animate({
                        opacity: 0,
                        bottom: -10
                    });
                });
            }
        });
    }
    maskLayer(ele) {
        if ($(window).width() >= 992) {
           config.overlayMask.css('display', 'block');
            $('.grid-sort.visible-lg').find('.custom-dropdown').addClass('overlap');
        }
    }
    maskLayerout(ele) {
        if ($(window).width() >= 992) {
            config.overlayMask.css('display', 'none');
            $('.grid-sort.visible-lg').find('.custom-dropdown').removeClass('overlap');
        }
    }
    subMenuAnimationON(ele) {
        var $isOverlayChildren = $(ele).find(".overlay-block");
        if (
          $isOverlayChildren.length &&
          $(window).width() >= 1200 &&
          !$isOverlayChildren.hasClass("animation-active")
        ) {
          $isOverlayChildren.addClass("animation-active");
          $isOverlayChildren.show();
        }
      }
      subMenuAnimationOFF(ele, evt) {
        var $isOverlayChildren = $(ele).find(
          ".overlay-block.overlay-image-3.animation-active"
        );
        if ($isOverlayChildren.length) {
          $isOverlayChildren.hide().removeClass("animation-active");
        }
      }
    hideMobileOverlay(ele, evt) {
     config.menuButtonforMob.click();
     config.primaryNavID.removeClass('menu-hide');
    }
    buyNowClick(ele, evt) {
        const mobileView = window.matchMedia("(max-width: 767px)");
        const tabView = window.matchMedia("(min-width: 768px) and (max-width: 1024px)");
        let height;
        if (mobileView.matches) {
            height = config.priceSpider.offset().top - $('.mobile-viewMenu').outerHeight();
        } else if (tabView.matches) {
            height = config.priceSpider.offset().top - config.headerContainer.outerHeight();
        } else {
            if ( config.newsLetterContainer.is(":visible")) {
                height = config.priceSpider.offset().top - config.headerContainer.height() - config.newsLetterContainer.outerHeight();
            } else {
                height = config.priceSpider.offset().top - config.headerContainer.height();
            }
        }
        jQuery(window).scrollTop(height);
    }
    readMoreClick(ele, evt) {
        let height;
        height = config.productFeatures.offset().top - config.headerContainer.height();
        jQuery(window).scrollTop(height);
    }
    starLinkClick() {
        let height;
        if (navigator.userAgent.match(/Android/i) || navigator.userAgent.match(/webOS/i) || navigator.userAgent.match(/iPhone/i) || navigator.userAgent.match(/iPad/i) || navigator.userAgent.match(/Windows Phone/i)) {
            if (config.reviewContainer.is(":visible")) {
                if (config.headerContainer.hasClass(config.stickBorClass)) {
                    height = config.reviewContainer.offset().top - config.headerContainer.height();
                } else {
                    height = config.reviewContainer.offset().top - config.headerContainer.height() - $(".headerAnnouncement").height();
                }
                jQuery(window).scrollTop(height);
            }
        } else {
            if (config.reviewContainer.is(":visible")) {
                const height = config.reviewContainer.offset().top - config.headerContainer.height();
                jQuery(window).scrollTop(height);
            }
        }

    }
    showNavigation(el, evt) {
      config.primaryNavSection.fadeToggle(500, function () {
            ($(this).is(':visible')) ? config.hamburgerButton.addClass('open') : config.hamburgerButton.removeClass('open')

        });
    }
    showNavigationV2(el, evt) {
        const subHeaderContainer = $('.sub-header-content');
        const ariaHidden = 'aria-hidden';
        const closeContainer = $('.close-icon');
        const headercloseMenu = $('.header-close-menu');
        config.primaryNavID.removeClass('menu-hide');
        const selector = $(el).data("target");
        $(config.headerWrapper).animate({
            scrollTop: '0'
        });
        $(selector).toggleClass('in');
        $(el).toggleClass('close-icon');
        if ($(el).hasClass('close-icon')) {
            $('body').css({
                'overflow': 'hidden',
                'position': 'fixed'
            });
        } else {
            $('body').removeAttr("style");
        }

        if (config.headerWrapper.attr('aria-expanded') === 'true') {

            config.headerWrapper.attr(ariaHidden, 'false');
            subHeaderContainer.attr(ariaHidden, 'false');
            closeContainer.attr(ariaHidden, 'false');
            headercloseMenu.attr(ariaHidden, 'false');
        } else {
            config.headerWrapper.attr(ariaHidden, 'true');
            subHeaderContainer.attr(ariaHidden, 'true');
            closeContainer.attr(ariaHidden, 'true');
            headercloseMenu.attr(ariaHidden, 'true');

        }

    }
    showNavigationSearch() {
        $(config.headerWrapper).animate({
            scrollTop: '0'
        });
        config.menuButtonforMob.trigger('click');
    }

    closeMenu(el, evt) {
        if (config.hamburgerButton.hasClass('open')) {
            config.hamburgerButton[0].click();
        }
    }
    fnMenuClose(el, evt) {
        config.navigationContainer.removeClass('active');
        $('body').css({
            'overflow': 'visible',
            'position': 'static'
        });
    }
    backToTopAction(ele, evt) {
        return $("body,html").animate({
            scrollTop: 0
        }, 800), !1
    }
    showSublevel(ele, evt) {
        $(ele).siblings('ul').slideToggle(100, 'linear', function() {
            if ($(ele).siblings('ul').is(':visible')) {
                $(ele).text('-');
            } else {
                $(ele).text('+');
            }
        });
    }
    onChangeInput() {
        $(".input-group input").on('input', function() {
            if ($(this).val()) {
                $('.close-x').addClass("icon-show");
            } else {
                $('.close-x').removeClass("icon-show");
            }
        })
    }
    navSearchBox(ele, evt) {
        config.navSearchContainer.addClass('popup');
        $('.close-x').remove();
        $('.overlay').remove();
        $('.input-group').append('<button class="close-icon close-x" type="button"></button>')
        if ($(window).width() >= 1199) {
            config.navSearchContainer.after('<div class="overlay"> <button type="button" class="close-icon close-div" aria-label="Close"></button></div>');
        }

        $('.nav-search.popup .input-group input').css({
            "border": "1px solid #979797",
            "border-left": "0"
        });
        $('.nav-search.popup .input-group .input-group-addon').css({
            "border": "1px solid #979797",
            "border-right": "0"
        });
        return false;
    }


    navSearchBoxFocusOut(ele, evt) {
        $('.nav-search.popup .input-group input').css({
            "border": "1px solid #BFBFBF",
            "border-left": "0"
        });
        $('.nav-search.popup .input-group .input-group-addon').css({
            "border": "1px solid #BFBFBF",
            "border-right": "0"
        });

    }

    closeNavSearch(ele, evt) {
        $('.close-x').remove();
        $('.overlay').remove();
        config.navSearchContainer.find('input').val('');
        config.navSearchContainer.removeClass('popup');
        return false;
    }
    clearNavSearch(ele, evt) {
        config.modelCloseButton.hide();

        config.navSearchContainer.find('input').val('');
        return false
    }
    windowResize() {
        $(window).resize(
            _.debounce(() => {
                if ($(window).width() >= 1200) {
                    $(".primary-nav>ul>li").each(function(idx, obj) {
                        if ($(obj).children().length === 3) {
                            $(obj)
                                .find("ul")
                                .removeAttr("style");
                        }
                    });
                }
                self.currentDimension = window.innerWidth;
                self.inputWidth();
                if(!$('.tickerModule').length){
                    self.recallMarginSet();
                }
                if (self.currentDimension != window.innerWidth) {
                    self.scrollHeader();
                    self.RenderHeader();
                }
            })
        );

    }

    scrollHeader() {
        let isDesktop = $(window).width() >= 1025;
        let isTab = $(window).width() <= 1024;
        $(window).scroll(function() {
            const brandLogo = $('.brand-logo'),
                navBar = $('#navbar'),
                scroll = $(window).scrollTop();
            if (scroll >= ($(".global-header-wrapper").innerHeight() || 0)) config.headerContainer.addClass("scroll-sticky");
            else config.headerContainer.removeClass("scroll-sticky");
            const stickyHeight = $('.scroll-sticky').outerHeight();
            if ($('#pricespider-div').length >= 1) {
                const elementOffset = $('#pricespider-div').offset().top + 100;

                const newsletter_height = config.newsLetterContainer.outerHeight();
                if ($(this).scrollTop() > (elementOffset - stickyHeight)) {
                    brandLogo.addClass('non-selecter');
                    navBar.show();
                    config.scrollStickyList.addClass(config.stickBorClass);
                    isDesktop && config.navigationContainer.hide();
                    isTab && $('.mobile-viewMenu').hide();
                } else {
                    brandLogo.removeClass('non-selecter');
                    navBar.hide();

                    isDesktop && config.navigationContainer.show();
                    isTab && $('.mobile-viewMenu').show();
                    config.scrollStickyList.removeClass(config.stickBorClass);
                }
            }

        });
    }
    pageLoadActions() {
        let topHeight;
        $('.primary-nav>ul>li>a').each(function(idx, obj) {
            if ($('[data-link-attribute="' + $(obj).data('linkName') + '"]').length && !$(obj).hasClass("active-loaded")) {
                $(obj).addClass("active-loaded");
                $(obj).attr('href', '#' + $(obj).data('linkName')).on('click', function() {
                    config.primaryNavSection.find('li>a.active').removeClass('active');
                    $(obj).addClass('active');
                    topHeight = config.headerContainer.height();
                    $('html, body').animate({
                        scrollTop: ($($(this).attr('href')).offset().top) - topHeight
                    }, 500);
                });
            }
        });
    }
    setHashScroll() {
        let hashVal = window.location.hash;
        if (!hashVal || !$(hashVal).length || self.hashCheckCnt > 1) return;
        self.hashCheckCnt++;
        $('html, body').animate({
            scrollTop: ($(hashVal).offset().top) - (config.headerContainer.height() || 0)
        }, 500);
        return;
    }
    getNavTrackingValues(elem, evt, trackingData) {
        if (trackingData != undefined)
            var trackingVal = trackingData;

        else
            var trackingVal = $(elem).data("analyticsTrackingId");

        if (_.isEmpty(trackingVal)) {
            console.log("Warn: Tracking value should not be empty.. ");
            return;
        }
        const valArr = trackingVal.split('|');
        var obj = {
            event_name: valArr[0] || '',
            event_type: 'click',
            item_category1: valArr[1].toLowerCase() || 'no-value',
            item_category2: valArr[2].toLowerCase() || 'no-value',
            item_category3: valArr[3].toLowerCase() || 'no-value',
            location_name: valArr[4].toLowerCase() || ''
        }
        const camelCaseName = camelize(valArr[0]);
        const evtName = camelCaseName.replace(/-/g, "");

        typeof sendNavToAnalytics == "function" && sendNavToAnalytics(obj, (evtName && evtName.toLowerCase() == "click" && elem.tagName == "A") ? "button" : evtName);

    }

    RenderHeader() {
        var path = '/bin/getNavigation',
            currentPath = $('#headerNodePath').val(),
            deviceType = '';
        if (window.innerWidth > 1199) {
            deviceType = 'desktop';
        } else {
            deviceType = 'mobile';
        }

        request.ajaxCall(apiConfigInst.getApiConfig('headerNavigation').navigationUrl())
            .then(response => {
                if (response != '') {
                    let data = response,
                        elem = $('#primary-category-data'),
                        templateId = _.template(
                            $('#primary-category-template')
                            .html()
                            .trim()
                        ),
                        templateCollection;

                    const newArr = [];
                    let menuName, pageLevelParam;
                    _.map(data.navigationLinks, function(item) {
                        pageLevelParam = item.categoryNavLinks[0];
                        menuName = pageLevelParam.pageName;
                        item.className = pageLevelParam.subLinkClass;
                        newArr[menuName] = item;
                    });

                    templateCollection = templateId({
                        primaryNav: newArr,
                        menuNames: _.keys(newArr),
                        deviceName: deviceType,
                    });
                    elem.empty();
                    elem.append(templateCollection);
                    if (deviceType == 'mobile') {
                        $(".sub-header-content").addClass('mobileSignUp');
                        config.newsLetterContainer.insertAfter('.sub-header-content .nav-search');
                    } else {
                        self.wrapLis();

                    }
                    self.renderLink();
                    config.primaryNavID.removeClass("loader");
                    self.inputWidth();
                }
            })
    }
    truncateBreadCrumb() {
        const isMobile = window.innerWidth <= 767;
        const isdesktop = window.innerWidth >= 767;
        if (isMobile) {
            var $mobEleclass = $(".bc-truncate");
            self.truncateCharcount($mobEleclass);
        }
        if (isdesktop) {
            var $desktoEleclass = $('.breadcrumb li:last-child a');
            self.truncateCharcount($desktoEleclass);
        }
    }
    truncateCharcount(ele) {
        var len = $(ele).text().length;
        if (len > 35) {
            $(ele).text(
                $(ele)
                .text()
                .substr(0, 35) + "..."
            );
        }
    }
    inputWidth() {

        var navWidth = config.primaryNavSection.outerWidth(),
            searchNav = config.navSearchContainer.outerWidth(),
            brandLogo = $('.brand-logo img').outerWidth(),
            navKids = $('.nav-kids').outerWidth(),
            winWidth = $(window).width();
        if (window.innerWidth > 1024 && window.innerWidth <= 1800) {
            if (navWidth >= 640) {
                const total = (navWidth + brandLogo + navKids);
                const resizeValue = ((winWidth - total) - 60);
                config.navSearchContainer.outerWidth(resizeValue + 'px');
            }
        } else if (window.innerWidth > 1800) {
            if (navWidth >= 640) {
                const leftRightGap = $(window).width() - 1800;
                const total = (navWidth + brandLogo + navKids + leftRightGap);
                const resizeValue = ((winWidth - total) - 60);
                config.navSearchContainer.outerWidth(resizeValue + 'px');
            }
        }

    }
    removeRecallMar() {
        const headerHeight = config.headerContainer.outerHeight();
        const recallHeight = config.recallContainer.outerHeight() || 0;
        const marginTopWithoutNewsletter = headerHeight + recallHeight;
        config.recallContainer.css({
            'margin-top': headerHeight
        });
        //$('.outer-wrapper').css({ 'top': headerHeight });
    }
    recallMarginSet() {
        const newsLetterHeight = config.newsLetterContainer.outerHeight();
        const headerHeight = config.headerContainer.outerHeight();
        const marginTop = newsLetterHeight + headerHeight;
        const recallHeight = config.recallContainer.outerHeight() || 0;
        const marginTopWithoutNewsletter = headerHeight + recallHeight;
        const isTab = $(window).width() <= 1024;
        if (isTab) {
            config.recallContainer.css({
                'margin-top': headerHeight
            });
        }
    }
    renderLink() {
        $('.primary-nav ul li  a').each(function() {
            const LinkHref = $(this).attr('href') || $(this).attr('data-url');
            if (LinkHref == '' || LinkHref == '#' || LinkHref == undefined) {
                $(this).css({
                    'cursor': 'default'
                });
            }
        })
    }
    mobileRecallSlideUp(ele) {
        $(ele).toggleClass('arrowDown');
        $('.mobile-container').toggleClass('active');
        if ($(ele).attr('aria-expanded') === 'true') {
            $(ele).attr('aria-expanded', 'false');
        } else {
            $(ele).attr('aria-expanded', 'true');
        }

    }

    wrapLis() {
        const liEle = $(".subMenu-container .shop-by-brands .column-3");
        for (let i = 0; i < liEle.length; i += 6) {
            liEle.slice(i, i + 6).wrapAll("<li class='wrapped-ul'><ul></ul></li>");
        }
    }
    init() {

        self = this;
        self.currentDimension = window.innerWidth;
        this.hashCheckCnt = 0;
        this.pageLoadActions();
        this.RenderHeader();
        if ($('body.article-details-page').length || $('body.category-landing-page').length) {
            this.truncateBreadCrumb();
        }
        $('.primary-nav>ul>li').each(function(idx, obj) {
            if ($(obj).children().length === 2) {}
        });
        $('.grid-sort').removeClass('open');
        if(!$('.tickerModule').length){
            this.recallMarginSet();
        }
        this.windowResize();
        this.scrollHeader();
        this.onChangeInput();
        const superScript = $(".category-container").find('.search-result').text().trim().replace("®", "<sup>&reg;</sup>");
        const titleContent = $(".category-container").find('.search-result').html(superScript);


    }
}

let self,
    evtBinding = new eventBinding(),
    apiConfigInst = new fpApiConfig(),
    //confetti = new confetti(),
    request = new ajaxRequest(),
    fpheaderSection = new fpheader();
//fpheaderSection.init();
fpheaderSection.pageLoadActions();
fpheaderSection.setHashScroll();
