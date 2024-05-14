/**
* Header.js
* Version 1.0
*/
(function (global, PLAYAEM) {
    var self;
    var NewsLetter = {
        el: '.newsletter-sign-up',
        bindingEventsConfig: function () {
            var events = {
                "click .signup-close" : "newsLetter"
            }
            return events;
        },
        createCookie: function (name,value,days) {
            var expires;
            if (days) {
            var date = new Date();
                date.setTime(date.getTime()+(days*24*60*60*1000));
                expires = "; expires="+date.toGMTString();
            }
            else expires = "";
            document.cookie = name+"="+value+expires+"; path=/";
            },
        readCookie: function(name) {
            var nameEQ = name + "=";
            var ca = document.cookie.split(';');
            for(var i=0;i < ca.length;i++) {
                var c = ca[i];
                while (c.charAt(0)==' ') c = c.substring(1,c.length);
                if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
            }
            return null;
            },
        eraseCookie: function(name) {
            this.createCookie(name,"",-1);
            },
        headerHeight:function(){
            var newsletter_height = $('.newsletter-sign-up').outerHeight();
            var header_Height = $('header').outerHeight();
            var page_view_top = newsletter_height + header_Height;
            if($('.newsletter-sign-up').is(':visible')){
                $('header').css("top",newsletter_height);
                $('.page-view').css('margin-top',page_view_top);
            }
            else {
                $('header').css("top",'0');
                $('.page-view').css('margin-top',header_Height);
            }
        },
        newsLetter: function() {
            var header_Height = $('header').outerHeight();
            $('.newsletter-sign-up').hide();
            $('header').css("top",'0');
            $('.page-view').css('margin-top', header_Height);
            self.createCookie('hide', true, 1)
            return false;
        },
         windowResizeFunc: function () {
              $(window).resize(function () {
					self.headerHeight();
			 });
         },
        init: function () {
            if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.NewsLetter) return;
            self=this;
            if (!self.readCookie('hide')) {
                $('.newsletter-sign-up').show();
            }
            PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
            if ($(window).width() > 1024) {
                this.headerHeight();
           }
            this.eraseCookie();
            this.windowResizeFunc();
        }
    }
    NewsLetter.init();
    PLAYAEM.NewsLetter = NewsLetter;
    document.addEventListener('DOMContentLoaded', function () {
        if (!PLAYAEM.isDependencyLoaded) {
            NewsLetter.init();
        }
    }, false);
}(window, PLAYAEM));