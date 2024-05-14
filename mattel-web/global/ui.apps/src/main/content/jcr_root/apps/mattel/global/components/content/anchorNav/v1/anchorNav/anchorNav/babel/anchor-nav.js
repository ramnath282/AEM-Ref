/**
 * Anchor Navigation.js
 * Version 1.0
 * 
 */

const config = {
    ele: ".anchorNav",
    mobEle:".navbar-header",
    onlyDesktop: window.innerWidth > 1024,
    isMobile: window.innerWidth <= 900,
    tickerSet:'.navbar-fixed-top',
    tickerEle:".tickerModule",
    tickerHeadHeight: $(".tickerModule").length ? $(".tickerModule").height() : 0,
    mobtickerEle: $(".navbar-header").length ? $(".navbar-header").outerHeight() : 0,
   }
   
    
   class anchorNav {
       constructor() {
           self = this;
   
       }
       init() {
           window.global.eventBindingInst.bindLooping({
               "click #scrollnav a[data-scroll]": "scrollToSection",
               "click .ticker-content .ticker-close":"navSetPosition",
   
           }, self);
          
           setTimeout(() => {
           self.setPositionToNav();
            },300)             
       }
    
       setPositionToNav(){
           let $ele = $(config.ele),
           $tickerHead = $(config.tickerEle);
            if($tickerHead.hasClass('ticker-available') && $tickerHead.hasClass('sticky') && !(config.isMobile && $tickerHead.hasClass('no-mobile'))){
                    $ele.css("top",config.tickerHeadHeight);
                    $ele.addClass('navbar-fixed-top');
            }
       }
       navSetPosition(ele){
           $(config.ele).css("top","0")  
       }
       
       scrollToSection(ele, evt) {
           let headerEle = {
               getHeight:  $(config.ele).outerHeight() + $(config.ele).offset().top,
               mobGetHeight:  $(config.ele).offset().top + $(config.mobEle).outerHeight(),
           }
           let recall = 0;
           if( !$(".anchorNav.navbar-fixed-top").length) {    
                recall = $(".recall-component").length ? $(".recall-component").outerHeight() : 0;           
                headerEle = {
                    getHeight: $(config.ele).offset().top,
                    mobGetHeight: $(config.ele).offset().top
                }
            };
            
           let $el = $(ele);   
           const $section = $($(ele).attr('href'));
          $('.navbar-nav li a').removeClass('active');
           $el.addClass('active');
           $el.parents('.anchor-nav').find('.navbar-toggle').addClass('collapsed');
           $el.parents('.anchor-nav').find('.navbar-collapse').removeClass('in');
           if(config.onlyDesktop) {
               $('html, body').animate({
                   scrollTop: $section.offset().top - headerEle.getHeight + recall
               }, 500);
           } else{
               $('html, body').animate({
                   scrollTop: $section.offset().top - headerEle.mobGetHeight + recall
               }, 500);
           }
          
       }
   
   }
   
   let self;
   window.global.anchorNavBar = window.global.anchorNavBar || new anchorNav();
   window.global.anchorNavBar.init();