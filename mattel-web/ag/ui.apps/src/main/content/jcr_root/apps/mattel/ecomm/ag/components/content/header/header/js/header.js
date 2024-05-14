/**
* Header.js
* Version 1.0
*/
(function (global, AGAEM) {
    var header = {
        el: 'header',
        subNavCategory: '.secondary-nav-container',
        subNavList: '.secondary-nav-list ul',
        root: '.root',
        headerScrollStart: 100,
        bindingEventsConfig: function () {
            var events = {
                "click .hamburger": "showNavigation",
                "click .layout-overlay": "hideNavigation",
                "click #back-top a": "backToTopAction",
                "keydown #primary-nav ul li": "onTabPrimaryNav",
                "click .select-location-menu": "showLocationPopup",
                "click .category-link": "toggleAction",
                "click .subnav-out-link": "subnavFocusout"
           }
            return events;
        },
        subnavFocusout : function(){
            $(".category-link").trigger("click").attr("tabindex",0).focus();
        },
        toggleAction : function(el){
            $(el).attr('aria-expanded', !$(el).hasClass('collapsed'));
        },
        showLocationPopup: function () {
            $(".location-dropdown").trigger("click");
            $(".layout-overlay").trigger("click");
        },
        showNavigation: function (el, evt) {
            $('.inner-wrapper').addClass('open').prepend('<div class="layout-overlay"></div>');
            $(el).attr("aria-expanded", true);
            var searchBoxPos = $(".primary-nav li:last").position();
            $(".brand-search").css("top",searchBoxPos.top + 50);
        },
        hideNavigation: function (el, evt) {
            $('.inner-wrapper').removeClass('open');
            $(".hamburger").attr("aria-expanded", false);
            $('.layout-overlay').remove();
        },
        backToTopAction: function (ele, evt) {
            return $("body,html").animate({
                scrollTop: 0
            }, 800);
        },
        backToTopScroll: function () {
            var $elem = $("#back-top");
            if (!$elem.length) return;
            $(window).scroll(function () {
                $(this).scrollTop() > 100 ? $elem.fadeIn() : $elem.fadeOut();
            })
        },
        scrollHeader: function () {
            var self = this;
            $(window).on("load resize scroll", function (e) {
                if ($(window).scrollTop() >= self.headerScrollStart || window.innerWidth < 1024) {
                    $(self.el).addClass('active');
                } else {
                    $(self.el).removeClass('active');
                    $('.inner-wrapper').removeClass('open');
                    $('.layout-overlay').remove();
                }
                self.setSubnav(e);

                self.adjustContainerFluid();
            });
        },
        scrollBerryHeader: function () {
            var self = this;
            $(window).on("load resize scroll", function (e) {
                self.adjustContainerFluid();
            });
        },
        adjustContainerFluid : function() {
            if($(".article-page").length>0) {
                $(".article-page .full-width-container").css("left", 0);
                var vwidth = $(window).width();
                if(vwidth>1600) {
                    $(".article-page .full-width-container").css({"width":"1600px", "left":"-165px"});
                } else {
                    $(".article-page .full-width-container").css({"width":"100vw"});
                    var pos = $(".full-width-container").position();
                    $(".article-page .full-width-container").css("left", -pos.left);
                }
            }
        },
        onTabPrimaryNav: function (el, evt) {
            if (evt.which == 9 && evt.shiftKey) {
                if ($(el).index() == 0) {
                    $('.inner-wrapper').removeClass('open');
                    $('.layout-overlay').remove();
                }
            } else if (evt.which == 9 && $(window).width() >= 768) {
                let totalItem = $('.sticky-header.active .primary-nav').find('li').length;
                if ((totalItem - 2) == ($(el).index())) {
                    $('.inner-wrapper').removeClass('open');
                    $('.layout-overlay').remove();
                }
            } else if (evt.which == 9 && $(window).width() < 768) {
                let totalItem = $('.sticky-header.active .primary-nav').find('li').length;
                if ((totalItem - 1) == ($(el).index())) {
                    $('.inner-wrapper').removeClass('open');
                    $('.layout-overlay').remove();
                }
            }
        },
        setSubnav: function (e) {
            var self = this;
            var marginTop = parseInt($(self.el).outerHeight()) + parseInt(($(self.subNavCategory).outerHeight() || 0));
            var $navElem = $(this.subNavList).closest(".collapse");
            var navHeight;
            if ($(self.subNavList).length > 0) {
                $(self.subNavCategory).css({"top" : $(self.el).outerHeight()+"px"});
                if (e.type != 'scroll') {
                    $(self.subNavList).css({'height':'auto'});                    
                    if(!$navElem.hasClass('in')){
                        navHeight = $navElem.addClass('in').height() || 0;
                        $navElem.removeClass('in');
                    } else{
                        navHeight = $navElem.height() || 0;
                    }
                    if (navHeight > $(window).height()) {
                        $(self.subNavList).css('height', $(window).height() - $(".category-link").outerHeight() + 'px');                       
                    }
                }
            }
            $(this.root).css("margin-top", marginTop + "px");
        },
        init: function () {
            if (!AGAEM.isDependencyLoaded || !$(this.el).length || AGAEM.header) return;
            AGAEM.bindLooping(this.bindingEventsConfig(), this);
            if ($(this.el).hasClass('sticky-header')) {
                this.scrollHeader();
            } else if ($("#header-wrapper").hasClass("navbar-fixed-top")) {
                this.scrollBerryHeader();
            }
        }
    }
    header.init();
    AGAEM.header = header;
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            header.init();
        }
    }, false);
}(window, AGAEM));
