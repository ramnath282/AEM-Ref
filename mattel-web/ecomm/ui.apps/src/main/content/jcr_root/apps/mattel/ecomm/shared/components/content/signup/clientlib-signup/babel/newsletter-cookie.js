/**
* newsletter-cookie.js
* Version 1.0
*/
var config = {
    newsletterSignUp: ".newsletter-sign-up",
    header: "header"
};

class newsletterCookie { 

    constructor() {
        self = this;
        this.element = ".newsletter-sign-up";
        window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(),self);
        setTimeout(() => {
            self.init();
        },50)
    }

    bindingEventsConfig() {
        const events = {
            "click .signup-close" : "hideNewsletter",
        };
        return events;
    }
    hideNewsletter() {
        $(config.newsletterSignUp).hide();
        window.global.browserCookie.setCookie('hideNewsletter', true, 1)
        return false;
    }
    init() {
        self=this;
        if (!window.global.browserCookie.getCookie('hideNewsletter') && $('.newsletter-sign-up').length && $(window).width() > 1024) {
            $('div:not(".outer-wrapper")').find(".signup,.siteHeader").not($(".footer-wrapper .signup")).wrapAll('<div class="fixed-header-container custom-wrapper-js"></div>');
            $(config.newsletterSignUp).show();
            $("body").addClass("newslettershow");
        }
        else{
            $("body").addClass("newsletterhide");
        }
    }
}

let self,
newsletterComp = new newsletterCookie();
        




