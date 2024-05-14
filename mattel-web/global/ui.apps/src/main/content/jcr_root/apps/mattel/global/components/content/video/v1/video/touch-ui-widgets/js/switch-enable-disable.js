(function(document, $) {
    "use strict";
    $(document).on("dialog-ready", function(e) {
        var freeformLoad = $("coral-switch[name='./playVidInModal']").prop('checked');
        showHide("playVidInModal",freeformLoad);
        checkboxShowHideHandler($(".cq-dialog-switch-image-field", e.target));
    });
    $(document).on("change", ".cq-dialog-switch-image-field", function(e) {
        var freeformLoad = $("coral-switch[name='./playVidInModal']").prop('checked');
        showHide("playVidInModal",freeformLoad);
        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el) {
        el.each(function(i, element) {
            if ($(element).is("coral-switch")) {
                Coral.commons.ready(element, function(component) {
                    component.on("change", function() {
                        if($(element).attr("name") == "./playVidInModal" && $("coral-switch[name='./playVidInModal']").prop('checked')){
                            showHide("playVidInModal",true);
                        }
                    });
                });
            }
        });
    }

    function showHide(component,element) {
		var labelfield = $("coral-fileupload[name='./thumbnailImage']").prev();
        var mandetoryChar = labelfield.text();
        if(component === "playVidInModal" && element === true) {
            $("coral-fileupload[name='./thumbnailImage']").attr('data-cq-fileupload-required','');
            if (mandetoryChar.indexOf("*") <= 0){
				labelfield.text(mandetoryChar + "*");
            }
        }
        else {
            $("coral-fileupload[name='./thumbnailImage']").removeAttr("data-cq-fileupload-required");
			if (mandetoryChar.indexOf("*") >= 0){
				labelfield.text(mandetoryChar.slice(0, -1));
            }
        }
}
})(document, Granite.$);