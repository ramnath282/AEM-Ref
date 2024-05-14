/**
 * Global Header.js
 * Version 2.0
 */
let self;
const globalHeader = {
    el: 'header',
    headerScrollStart: 100,
    bindingEventsConfig() {
        const events = {
            "click .hamburger": "showNavigation",
            "click .primary-nav ul li a:not('.submenu-level3')": "closeMenu",
            "click header .overlay": "closeMenu",
            "click .play-backtotop": "backToTopAction",
            "click .show-sub-nav": "showSublevel",
            "click .primary-nav li a span.submenu-level3-arrow": "showSubmenuLevel3"
        };
        return events;
    },
    showNavigation(el, evt) {
        $('.primary-nav').fadeToggle(100, function() {
            ($(this).is(':visible')) ? $('.hamburger').addClass('open'): $('.hamburger').removeClass('open');
            ($(this).is(':visible')) ? $('body').addClass('hamburger-open'): $('body').removeClass('hamburger-open');
            $('header').find('.show-sub-nav').removeClass("show-sub-nav-true").text("+").siblings('ul').hide();
        });
        $(".overlay").removeClass("hide");
    },
    showSubmenuLevel3(el, evt) {
        evt.preventDefault();
        var $ele =  $('.primary-nav li a.submenu-level3');
        if ($ele .is(':visible')) {
            $(".submenu-level3-item").slideUp(100);
            $ele.removeClass('active').find('.submenu-level3-arrow').attr("aria-expanded", "false");
        }
        if ($(el).closest('.submenu-level3').next(".submenu-level3-item").is(':visible')) {
            $(el).closest('.submenu-level3').removeClass('active').next(".submenu-level3-item").slideUp(100);
            $(el).attr("aria-expanded", "false");
        } 
        else {
            $(el).closest('.submenu-level3').addClass('active').next(".submenu-level3-item").slideDown(100);
            $(el).attr("aria-expanded", "true");
        }
    },
    closeMenu(el, evt) {
        if ($('.hamburger').hasClass('open') && !$(el).hasClass("show-sub-nav")) {
            $('.hamburger')[0].click();
            $(".overlay").addClass("hide");
        }
    },
    backToTopAction(ele, evt) {
        $('html,body').animate({
            scrollTop: 0
        }, 'slow');
        $(".navbar-brand").focus();
    },
    showSublevel(ele, evt) {
        $('header').find('.show-sub-nav').not(ele).each((index, item) => {
            $(item).removeClass("show-sub-nav-true").text("+").siblings('ul').hide();
        });
        $(ele).siblings('ul').slideToggle(100, 'linear', () => {
            if ($(ele).siblings('ul').is(':visible')) {
                $(ele).text('-').addClass("show-sub-nav-true").attr("aria-expanded", "true");
            } else {
                $(ele).text('+').removeClass("show-sub-nav-true").attr("aria-expanded", "false");
            }
        });
    },
    windowResize() {
        $(window).resize(() => {
            if ($(window).width() >= 1025) {
                $('.primary-nav>ul>li').each((idx, obj) => {
                    if ($(obj).children().length === 3) {
                        $(obj).find('ul').removeAttr('style');
                    }
                });
            }
        });
    },
    scrollHeader() {
        $(window).scroll(() => {
            const sticky = $('header'),
                scroll = $(window).scrollTop();
            if (scroll >= ($(".global-header-wrapper").innerHeight() || 0)) sticky.addClass("scroll-sticky");
            else sticky.removeClass("scroll-sticky");
        });
    },
    showScrollButton: function($el) {
        var lastScrollTop = 0,
            windowHeight = $(window).height(),
            documentHeight = $(document).height(),
            offset = windowHeight / 2;
        $(window).scroll(function() {
            if ($(this).scrollTop() >= offset) {
                $el.fadeIn(this.fadeAnimValue);
            } else {
                $el.fadeOut(this.fadeAnimValue);
            }
            var st = $(this).scrollTop();
            if (st > lastScrollTop) {
                if (($(this).scrollTop() >= (documentHeight - windowHeight - 10)) || ($(this).scrollTop() <= (documentHeight - offset))) {
                    $el.css({
                        WebkitTransition: 'opacity 1s ease-in-out',
                        MozTransition: 'opacity 1s ease-in-out',
                        MsTransition: 'opacity 1s ease-in-out',
                        OTransition: 'opacity 1s ease-in-out',
                        transition: 'opacity 1s ease-in-out'
                    })
                }
            }
            lastScrollTop = st;
        });
    },
    backToTopInit: function() {
        let $scrollTop = $(".play-backtotop");
        if ($scrollTop.length) {
            this.showScrollButton($scrollTop);
        }
    },
    pageLoadActions() {
        let topHeight;
        $('.primary-nav>ul>li>a').each((idx, obj) => {
            if ($(`[data-link-attribute="${$(obj).data('linkName')}"]`).length && !$(obj).hasClass("active-loaded")) {
                $(obj).addClass("active-loaded");
                $(obj).attr('href', `#${$(obj).data('linkName')}`).on('click', function() {
                    //add/remove active class to nav link
                    $('.primary-nav').find('li>a.active').removeClass('active');
                    $(obj).addClass('active');
                    //menu call page scroll 
                    topHeight = $('header').height();
                    $('html, body').animate({
                        scrollTop: ($($(this).attr('href')).offset().top) - topHeight
                    }, 500);
                });
            }
        });
    },
    setHashScroll() {
        const hashVal = window.location.hash;
        if (!hashVal || !$(hashVal).length || self.hashCheckCnt > 1) return;
        self.hashCheckCnt++;
        $('html, body').animate({
            scrollTop: ($(hashVal).offset().top) - ($('header').height() || 0)
        }, 500);
        return;
    },
    init() {
        if (!$(this.el).length || window.global.globalHeader) return;
        self = this;
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.hashCheckCnt = 0;
        this.pageLoadActions();
        $('.primary-nav>ul>li').each((idx, obj) => {
             if ($(obj).children(':not(input)').length === 2) {
            	var ariaName = $(obj).find('a:not(.show-sub-nav)').attr('data-link-name');
            	$(obj).prepend('<a href="javascript:void(0)" class="show-sub-nav hidden-lg" aria-label="'+ariaName+' submenu" role="button" aria-expanded="false">+</a>');
            }
        });
        $('.primary-nav ul.submenu-level3-item').each((idx, ele) => {
            $(ele).prev('a').addClass('submenu-level3').append('<span class="submenu-level3-arrow" role="button" aria-expanded="false"></span>');
        });
        this.windowResize();
        this.scrollHeader();
    }
};
const evtBinding = window.global.eventBindingInst;
globalHeader.init();
window.global.globalHeader = globalHeader;
document.addEventListener('DOMContentLoaded', () => {
    if (!window.global.isDependencyLoaded) {
        globalHeader.init();
    }
    globalHeader.pageLoadActions();
    globalHeader.backToTopInit();
    globalHeader.setHashScroll();
}, false);