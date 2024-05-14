(function (document, $) {
    "use strict";

    $(document).on("foundation-contentloaded", function (e) {
		
        var entCompClickable = $(".tickerModule-dialog .ctaMasterTab-entrCompClick");
        if(entCompClickable.parent( ".coral-Form-fieldwrapper").length){
            entCompClickable = entCompClickable.parents( ".coral-Form-fieldwrapper");
            entCompClickable.hide();
        }

        limitItemsInCta();

    });


    $(document).on("change", ".cq-dialog-switch-showHide-viewAll", function (e) {
        limitItemsInCta();
    });


    function limitItemsInCta() {
        var target = $(".tickerModule-dialog .cmp-childreneditor_cta");
        var $target = $(target); 
        let multiFieldSize = $target.children(".coral3-Multifield-item").size();
        if(multiFieldSize>0) {
            $target.children(".coral3-Button--secondary").hide();
        } else {
            $target.children(".coral3-Button--secondary").show();
        }
    }


})(document, Granite.$);
