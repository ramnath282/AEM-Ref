/**
* retailer-popup.js
* Version 1.0
*/
(function (global, CRMAEM) {
    var retailer_popup = {
        el: '.gem-retalier-logo',
        lastClickedLogo: "",
        bindingEventsConfig: function () {
            var events = {
                "click .gem-retalier-logo ul li a": "showPopup",
                "click .cancel_btn": "analyticsCancelClick",
                "click .close": "analyticsCloseClick",
                "click .retailer-yes-btn": "hidePopup"
            }
            return events;
        },
        showPopup: function (ele, eve) {
    		if($('.retailer-yes-btn').attr('data-url')){
    			$('.retailer-yes-btn').attr('data-url', $(ele).attr('href'));
    		}
    		else{
    			$('.retailer-yes-btn').attr('href', $(ele).attr('href'));
    		} 

                                                this.lastClickedLogo = $(ele).find("img").attr("analyticsKeyword");             
                 populateRetailersData(this.lastClickedLogo,"");
            eve.preventDefault();

        },
        
         analyticsCancelClick: function (ele, eve) {
            populateRetailersData(this.lastClickedLogo,"Cancel");
        },
        analyticsCloseClick: function (ele, eve) {
            populateRetailersData(this.lastClickedLogo,"Close");
        },
		hidePopup: function(ele, eve){
            $(ele).parents('.modal').modal('hide');
            populateRetailersData(this.lastClickedLogo,"Yes");
        },
        init: function () {
            if (!CRMAEM.isDependencyLoaded || !$(this.el).length || CRMAEM.retailer_popup) return;
            CRMAEM.bindLooping(this.bindingEventsConfig(), this);
        }
    }
    retailer_popup.init();
    CRMAEM.retailer_popup = retailer_popup;
    document.addEventListener('DOMContentLoaded', function () {
        if (!CRMAEM.isDependencyLoaded) {
            retailer_popup.init();
        }
    }, false);
}(window, CRMAEM));
