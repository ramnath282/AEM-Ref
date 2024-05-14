(function(document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function(e) {
            showhide($(this));
    });

	$(document).on("change", ".lastscreenoptions", function(e) {
        showhide($(this));
    });

    function showhide(el) {
		var tabStatus = false;
        var $targetTab;
        var $thankyouContent;
        var radioValue = $('input[name="./displayOnLastScreen"]:checked').val();
		$targetTab = $('.recommendations');
        $thankyouContent = $('.thankyou-content');
        if (radioValue == 'recommendedProducts') {
            tabStatus = true;
        }
        if ($targetTab != null && $targetTab != undefined) {
            var $parent = $targetTab.parents().eq(1);
            var id = $parent.attr("aria-labelledby");
            var $targetPanel = $("#" + id);
            if (tabStatus) {
                $targetPanel.show();
                $thankyouContent.hide();
            } else {
                $targetPanel.hide();
				$thankyouContent.show();
            }
        }

    }
})(document, Granite.$);