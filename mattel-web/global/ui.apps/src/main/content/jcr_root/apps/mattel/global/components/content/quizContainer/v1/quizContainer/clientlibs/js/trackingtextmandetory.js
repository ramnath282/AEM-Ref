(function(document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function(e) {
		var recommendationTrackingSwitch = $('.quizcontainer-recommendation-cta-switch');
		var recommendationTrackingTxt = $('.quizcontainer-recommendation-tracking-text');
		showhide(recommendationTrackingSwitch,recommendationTrackingTxt);
		
		var nextBtnTrackingSwitch = $('.quizcontainer-cta-nexttracking-switch');
		var nextBtnTrackingTxt = $('.quizcontainer-cta-nextbutton-tracking-text');
		showhide(nextBtnTrackingSwitch,nextBtnTrackingTxt);
		
		var submitBtnTrackingSwitch = $('.quizcontainer-cta-submittracking-switch');
		var submitBtnTrackingTxt = $('.quizcontainer-cta-submitbutton-tracking-text');
		showhide(submitBtnTrackingSwitch,submitBtnTrackingTxt);
    });

	$(document).on("change", ".quizcontainer-recommendation-cta-switch", function(e) {
		var recommendationTrackingSwitch = $('.quizcontainer-recommendation-cta-switch');
		var recommendationTrackingTxt = $('.quizcontainer-recommendation-tracking-text');
		showhide(recommendationTrackingSwitch,recommendationTrackingTxt);
    });
	
	$(document).on("change", ".quizcontainer-cta-nexttracking-switch", function(e) {
		var nextBtnTrackingSwitch = $('.quizcontainer-cta-nexttracking-switch');
		var nextBtnTrackingTxt = $('.quizcontainer-cta-nextbutton-tracking-text');
		showhide(nextBtnTrackingSwitch,nextBtnTrackingTxt);
    });
	
	$(document).on("change", ".quizcontainer-cta-submittracking-switch", function(e) {
		var submitBtnTrackingSwitch = $('.quizcontainer-cta-submittracking-switch');
		var submitBtnTrackingTxt = $('.quizcontainer-cta-submitbutton-tracking-text');
		showhide(submitBtnTrackingSwitch,submitBtnTrackingTxt);
    });
	
    function showhide(trackingSwitch, trackingTxt) {
		var checkStatus = trackingSwitch.prop('checked');
        if(checkStatus){
			trackingTxt.attr('aria-required','true');
        }else{
			trackingTxt.attr('aria-required','false');
        }
    }
})(document, Granite.$);