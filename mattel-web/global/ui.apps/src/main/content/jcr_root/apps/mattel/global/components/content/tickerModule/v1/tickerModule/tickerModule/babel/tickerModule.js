/**
 * Ticker
 * Version 1.0
 * 
 */

const config = {
    body : 'body',
    headerContainer: "header",
    tickerModal : '.tickerModule',
    recallContainer: ".recall-component",
    tickerHeadHeight: $(".tickerModule").length ? $(".tickerModule").height() : 0,
    onlyDesktop: window.innerWidth > 1024,
    isMobile: window.innerWidth <= 767,
    flag:true
}
class ticker {
    constructor() {
        self = this;
        window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
    }
    bindingEventsConfig() {
        let eventsArr = {
            'click .ticker-close': 'tickercloseModal',
        };
        return eventsArr;
    }

    init() {
        $(document).ready(function () {
            self.checkCookie();
            if($('.tickerModule').length){
                self.setPositionToNav();
            }
            if($('.tickerModule.mobile-modal.ticker-available').length && config.isMobile){
                $('body').addClass('body-fixed');
            }
            // window.addEventListener('resize', function (e) {
            //     self.checkCookie();
            // });
        });
    }
    tickercloseModal(){
        window.global.browserCookie.setCookie('tickerModalClose', true, 1);
        self.checkCookie();
        let $ele = $(config.headerContainer), headerHeight=  $ele.outerHeight();
        $ele.css("top",'0');
        $(config.headerContainer).css("top",'0');
        $(config.recallContainer).css('margin-top',headerHeight);
        if($('.tickerModule.mobile-modal').length && config.isMobile){
            $('body').removeClass('body-fixed');
        }
        config.flag = false;
      }
      setPositionToNav(){
        let $ele = $(config.headerContainer), $headerHeight = $ele.outerHeight(),
        tickerModal = $(config.tickerModal);
        let headertoalHeight =  config.tickerHeadHeight+$headerHeight;
         if(tickerModal.hasClass('ticker-available') && tickerModal.hasClass('sticky') && !(config.isMobile && tickerModal.hasClass('no-mobile'))){
            $ele.attr('style', `top: ${config.tickerHeadHeight}px !important`);
            $(config.recallContainer).css('margin-top',headertoalHeight);
         }
         else if(tickerModal.hasClass('ticker-available') && !tickerModal.hasClass('sticky') && !(config.isMobile && tickerModal.hasClass('no-mobile'))){
            $ele.attr('style', `top: ${config.tickerHeadHeight}px !important`);
            $(config.recallContainer).css('margin-top',$headerHeight);
            $(window).on('scroll', function() {
                if(config.flag == true){
                    if(window.outerWidth < 768){
                        self.mobilescrollFunction();
                    }
                    else{
                        self.desktopscrollFunction();
                    }
                }
            });
         }
         else{
            $(config.recallContainer).css('margin-top',$headerHeight);
         }
         if($('.tickerModule.mobile-modal').length && config.isMobile){
            $ele.css('top',0);
            $(config.recallContainer).css('margin-top',$headerHeight);
         }
      }
    desktopscrollFunction() {
        if($(window).scrollTop()  >  20) {
            $(config.headerContainer).css('top','0')
       }
       else{
        $(config.headerContainer).attr('style', `top: ${config.tickerHeadHeight}px !important`);
       }
    }
    mobilescrollFunction() {
        if($(window).scrollTop()  > 20 ) {
           $(config.tickerModal).css({'opacity':'0','height':'0'});
           $(config.headerContainer).css('top','0')
       }
       else{
        $(config.tickerModal).removeAttr('style');;
        $(config.headerContainer).attr('style', `top: ${config.tickerHeadHeight}px !important`);
       }
    }
    closeModal() {
        $(config.tickerModal).removeClass('ticker-available');
        $(config.body).removeClass('ticker-available');
    }
    openModal() {
        $(config.tickerModal).addClass('ticker-available');
        $(config.body).addClass('ticker-available');
    }
    checkCookie() {
        const tickerCookie = (window.global.browserCookie.getCookie('tickerModalClose')).split(',');
        const cookiSet = tickerCookie[0];
        if (!cookiSet) {
            self.openModal();
        } else {
            self.closeModal();
        }
    }
}
let self;
window.global.tickerModal = window.global.tickerModal || new ticker();
window.global.tickerModal.init();