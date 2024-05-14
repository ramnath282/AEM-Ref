/**
* accordion.js
* Version 1.0
*
*/

const config = {
    accMain :".accordion-item",
    accComponent : ".accordion-content",
    arrowUp : 'arrowUp',
    accordionCom:'.accordion',
    arrowDown:'arrowDown',
    activeToggle:"active",
    arrowSlide:".arrow",
    headerEle : $('header'),
    locationUrl : window.location.href,
    getIdfromUrl: window.location.href.split("#")[1],
    accordionOpen:$('.arrow').attr('data-accordion-open'),
    accordionClose:$('.arrow').attr('data-accordion-close'),
}

const headerSec = {
    headerContainer:config.headerEle.length? (config.headerEle.height() + config.headerEle.position().top): 0,
}
class accordion {
   constructor() {
       self = this;
       this.element = config.accMain;

     }

   init() {
       window.global.eventBindingInst.bindLooping({
           "click .arrow": "expandAccordionContainer",
       },self);

     if(config.getIdfromUrl) {
       const setUrl = config.locationUrl.substring(0, config.locationUrl.indexOf('#'));
       document.getElementById("canonicalurl").setAttribute("href",setUrl);
       document.getElementById("hrefLang").setAttribute("href",setUrl);
    }
}

expandAccordionContainer(ele,evt){
       let thisComp = $(ele).parents(self.element),
       $ele = $(ele),
       $accordionContent = thisComp.find(config.accComponent);
       let headerEle = $('header');
       const headerHeight = headerEle.length ? (headerEle.height() + headerEle.position().top): 0;
       $accordionContent.removeClass(config.activeToggle);
       $ele.parent().next().addClass(config.activeToggle).slideToggle('fast');
       setTimeout(() => { $('html,body').animate({scrollTop: $(ele).offset().top - headerSec.headerContainer})},300);

       if ($ele.hasClass(config.arrowUp)) {
           $ele.removeClass(config.arrowUp).addClass(config.arrowDown);
           $ele.html(config.accordionClose);
           $ele.attr("aria-expanded", "false");

       } else {
           thisComp.find(config.arrowSlide).removeClass(config.arrowUp).addClass(config.arrowDown);
           thisComp.find(config.arrowSlide).attr("aria-expanded", "false");
           $ele.removeClass(config.arrowDown).addClass(config.arrowUp);
           $ele.attr("aria-expanded", "true");
           $accordionContent.not('.active').slideUp('fast');

       }
       $('.arrowUp').html(config.accordionClose);
       $('.arrowDown').html(config.accordionOpen);
   }

}

const setToggle = () => {
  let  $accondainer = $(config.accMain);
  let  $findIdContainer = $(config.accMain).find('.accordionItem');

  if (!$accondainer.length) {
    console.log("Accordion class name not found..");
    return;
  }
     _.each($accondainer, function (item) {
            let $item = $(item);
            const $accordItem = $(item).parents('.accordionContainer').find('.open-firstItem');
            const $accordItemOff = $(item).parents('.accordionContainer').find('.autoclose-on');
            const $containerItem = $(item).find(config.accComponent);
  
            if($accordItem.length) {
                $item.find(".accordion-content").first().show('fast');
                $item.find(".arrow").first().removeClass(config.arrowDown).addClass(config.arrowUp).attr("aria-expanded", "true");
                $item.find('.arrowUp').html(config.accordionClose);
                $item.find('.arrowDown').html(config.accordionOpen);

            } 
            
            if($accordItemOff.length) {
                $containerItem.removeClass(config.activeToggle);
                $containerItem.show('fast');
                $item.find(config.arrowSlide).removeClass(config.arrowDown).addClass(config.arrowUp).attr("aria-expanded", "true");
                $item.find('.arrowUp').html(config.accordionClose);
                $item.find('.arrowDown').html(config.accordionOpen);
            }
       });

            if (config.getIdfromUrl && $(`#${config.getIdfromUrl}`)) {
                const $idFinder =  $('.accordionContainer .accordion-title[id='+config.getIdfromUrl+']');
                $idFinder.next().show('fast');
                $idFinder.find(".arrow").first().removeClass(config.arrowDown).addClass(config.arrowUp).attr("aria-expanded", "true");
                $idFinder.find('.arrowUp').html(config.accordionClose);
                $idFinder.find('.arrowDown').html(config.accordionOpen);
                setTimeout(() => { $('html,body').animate({scrollTop: $(`#${config.getIdfromUrl}`).offset().top - headerSec.headerContainer})},300);
               return false;
         }
}
    setTimeout(() => {
        setToggle();
    },500)
    
let self;
window.global.accordTab = window.global.accordTab || new accordion();
window.global.accordTab.init();
