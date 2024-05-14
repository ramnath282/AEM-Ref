/**
 * Gift Finder.js
 * Version 1.0
 */
(function (global, PLAYAEM) {
    var self;
    var giftFinder = {
      el: '.gift-form-container',
       birthQuery:'',
       ageQuery:'',
       apiConfig: function (name) {
        var obj = {
            "productSearchPage": {
            "url":'https://sp1004f9de.guided.ss-omtrdc.net/?i=1;q1='+ self.birthQuery+';q2='+ self.ageQuery+';x1=marketingAge;x2=pricerange',
            "body": "",
            "type": "POST",
            "contentType": 'application/json',
  
          },
         
        }
        return obj[name];
      },
      bindingEventsConfig: function () {
        var events = {
          //"submit .gift_finder_form": "submitFunResult",
          "click .button-container .data-track-gf": "submitFunResult",

               
        }
        return events;
      },
      ajaxCall: function (obj, cb) {
        $.ajax({
          url: obj.url,
          data: obj.body || '',
          type: obj.type,
          headers: obj.headers,
          beforeSend: function (xhr) {
            if (obj.beforeSend) {
              xhr.setRequestHeader('Authorization', obj.beforeSend);
            }
          },
          success: function (data) {
            if (typeof cb == "function") cb(data);
          },
          error: function (xhr, textStatus, errorThrown) {
            if (typeof cb == "function") cb(false, xhr.responseJSON || errorThrown);
          }
        });
      },
  
  
      submitFunResult:function(el) {
	var actURL = $('.gift_finder_form').attr('action')+"?";
    self.birthQuery = $('#age_select_box').val();
   self.ageQuery = $('#price_select_box').val();
   window.location.href = `${actURL}giftAge=${self.birthQuery}&giftPrice=${self.ageQuery}&x1=marketingAge&x2=pricerange`;
         //  window.location.href = actURL;
        self.giftFinderResult('productSearchPage');
          //giftAge=3+-+8Y&giftPrice=Between+%2410+and+%2420&x1=marketingAge&x2=pricerange
    $('.gift_finder_form').submit();
      },
  
     giftFinderResult: function (apiUrl) {
         self.birthQuery = $('#age_select_box').val();
         self.ageQuery = $('#price_select_box').val();
          var ajaxSettings = self.apiConfig(apiUrl);
          self.ajaxCall(ajaxSettings, function (res, err) {
        });
      },

      render: function () {

      },
      init: function () {
        if (!PLAYAEM.isDependencyLoaded || !$(this.el).length || PLAYAEM.giftFinder) return;
        self = this;
        PLAYAEM.bindLooping(this.bindingEventsConfig(), this);
        this.render();
          }
    }
    giftFinder.init();
    PLAYAEM.giftFinder = giftFinder;
    document.addEventListener('DOMContentLoaded', function () {
      if (!PLAYAEM.isDependencyLoaded) {
        giftFinder.init();
      }
    }, false);
  }(window, PLAYAEM));
 
