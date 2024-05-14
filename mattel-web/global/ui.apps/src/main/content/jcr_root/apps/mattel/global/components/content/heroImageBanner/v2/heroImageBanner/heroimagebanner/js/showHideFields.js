(function(document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function(e) {
		var trackingSwitch = $('.heroimagebanner-imagetrackingtext-switch');
		var trackingTxt = $('.heroimagebanner-imagetracking-text');
		showhide(trackingSwitch,trackingTxt);
    });

	$(document).on("change", ".heroimagebanner-imagetrackingtext-switch", function(e) {
		var trackingSwitch = $('.heroimagebanner-imagetrackingtext-switch');
		var trackingTxt = $('.heroimagebanner-imagetracking-text');
		showhide(trackingSwitch,trackingTxt);
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