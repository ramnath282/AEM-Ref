/**
 * Header.js
 * Version 1.0
 */
(function (global, PLAYAEM) {
    var self;
    var header = {
        el: 'header',
        headerScrollStart: 100,
        bindingEventsConfig: function () {
            var events = {
                "click .hamburger": "showNavigation",
		"click .primary-nav ul li a": "closeMenu",
                "click .play-backtotop": "backToTopAction",
                "click .show-sub-nav": "showSublevel"
            }
            return events;
        },
        showNavigation: function (el, evt) {
            $('.primary-nav').fadeToggle(500, function () {
                ($(this).is(':visible')) ? $('.hamburger').addClass('open'): $('.hamburger').removeClass('open');
                ($(this).is(':visible')) ? $('body').addClass('hamburger-open'): $('body').removeClass('hamburger-open');
                $('header').find('.show-sub-nav').removeClass("show-sub-nav-true").text("+").siblings('ul').hide();
            });
        },
    	closeMenu: function (el, evt) {
            if($('.hamburger').hasClass('open') && !$(el).hasClass("show-sub-nav")){
               $('.hamburger')[0].click();
            }
            
        },
        backToTopAction: function (ele, evt) {
            return $("body,html").animate({
                scrollTop: 0
            }, 800);
        },
        showSublevel: function (ele, evt) {
            $('header').find('.show-sub-nav').not(ele).each(function(index,item){
                $(item).removeClass("show-sub-nav-true").text("+").siblings('ul').hide();
            });
            $(ele).siblings('ul').slideToggle(100, 'linear', function () {
                if ($(ele).siblings('ul').is(':visible')) {
                    $(ele).text('-').addClass("show-sub-nav-true").attr("aria-expanded","true");
                } else {
                    $(ele).text('+').removeClass("show-sub-nav-true").attr("aria-expanded","false");
                }
            });
        },
        windowResize: function () {
            $(window).resize(function () {
                //console.log('resizing'+$(window).width());
                if ($(window).width() >= 1025) {
                    $('.primary-nav>ul>li').each(function (idx, obj) {
                        if ($(obj).children().length === 3) {
                            $(obj).find('ul').removeAttr('style');
                        }
                    });
                }
            });
        },
        scrollHeader: function () {
            $(window).scroll(function () {
                var sticky = $('header'),
                    scroll = $(window).scrollTop();
                if (scroll >= ($(".global-header-wrapper").innerHeight() || 0)) sticky.addClass("scroll-sticky");
                else sticky.removeClass("scroll-sticky");
            });
        },
        pageLoadActions: function () {
            var topHeight;
            $('.primary-nav>ul>li>a').each(function (idx, obj) {
                if ($('[data-link-attribute="' + $(obj).data('linkName') + '"]').length && !$(obj).hasClass("active-loaded")) {
                    $(obj).addClass("active-loaded");
                    $(obj).attr('href', '#' + $(obj).data('linkName')).on('click', function () {
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
        setHashScroll: function(){
            var hashVal = window.location.hash;
            if(!hashVal || !$(hashVal).length || self.hashCheckCnt>1) return;
            self.hashCheckCnt++;
            $('html, body').animate({
                scrollTop: ($(hashVal).offset().top) - ($('header').height() || 0)
            }, 500);
            return; 
        },
        init: function () {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.header) return;
            self=this;
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            this.hashCheckCnt = 0;
            this.pageLoadActions();
            $('.primary-nav>ul>li').each(function (idx, obj) {
                if ($(obj).children(':not(input)').length === 2) {
                    $(obj).prepend('<a href="javascript:void(0)" class="show-sub-nav hidden-lg" aria-label="submenu" role="button" aria-expanded="false">+</a>');
                }
            });
            this.windowResize();
            this.scrollHeader();
        }
    }
    header.init();
    PLAYAEM.header = header;
    document.addEventListener('DOMContentLoaded', function () {
        if (!PLAYAEM.isDependencyLoaded) {
            header.init();
        }
        header.pageLoadActions();
        header.setHashScroll();
        // window.onscroll = header.setHashScroll;
    }, false);
}(window, PLAYAEM));
