

/**
 * Gift Finder.js
 * Version 1.0
 */

import eventBinding from '../shared/eventBinding';
import fpApiConfig from '../shared/fpApiConfig';
import ajaxRequest from '../shared/ajaxbinding';


 class giftFinderSearch {
   constructor() {
       self = this;
       this.element = ".gift-form-container";
       eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
       self.init()
   }

   bindingEventsConfig(){
    let events = {
      "click .button-container .data-track-gf": "submitFunResult",

       }
    return events;
}
   
   init(){

   }

   submitFunResult(){
    const birthQuery = $('#age_select_box').val(),
          ageQuery = $('#price_select_box').val();
    request.ajaxCall(apiConfigInst.getApiConfig('giftFinderParams').call(this,birthQuery,ageQuery))
    .then(data => {
     })
    .catch(error => {
      console.log(error)
  })

    var actURL = $('.gift_finder_form').attr('action')+"?";
    //self.birthQuery = $('#age_select_box').val();
   //self.ageQuery = $('#price_select_box').val();
    window.location.href = `${actURL}giftAge=${birthQuery}&giftPrice=${ageQuery}&x1=marketingAge&x2=pricerange`;   
     //self.giftFinderResult('productSearchPage');   
     $('.gift_finder_form').submit();
   }

 };

 let self,
    eventBindingInst = new eventBinding(),
    apiConfigInst = new fpApiConfig(),
    request = new ajaxRequest(),
    giftFinderInstance = new giftFinderSearch();
    giftFinderInstance.init();

