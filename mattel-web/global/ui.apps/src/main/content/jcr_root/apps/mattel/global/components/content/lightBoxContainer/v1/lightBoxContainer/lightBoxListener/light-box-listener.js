(function (document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function (e) {
		if ($(".cq-dialog-lightbox-container").length > 0){
			checkboxShowHideHandler($(".cq-dialog-checkbox-showhide-lightbox", e.target));
			if ($("coral-switch[name='./enableCookies']").prop('checked') === false){
				$(".lightbox-enablecookie-cookie-label").parent().hide();
				$(".lightbox-enablecookie-cookie-expiry").parent().hide();
			} else {
				$(".lightbox-enablecookie-cookie-label").parent().show();
				$(".lightbox-enablecookie-cookie-expiry").parent().show();
			}
			
			$(document).on("change", ".lightbox-enablecookie-options", function (e){
				var filterSelectOption = $("coral-switch[name='./enableCookies']").prop('checked');
				if(filterSelectOption){
					$(".lightbox-enablecookie-cookie-label").parent().show();
					$(".lightbox-enablecookie-cookie-expiry").parent().show();
				}else{
					$(".lightbox-enablecookie-cookie-label").parent().hide();
					$(".lightbox-enablecookie-cookie-expiry").parent().hide();
				}
			});
			
		} 
    });

    $(document).on("change", ".cq-dialog-checkbox-showhide-lightbox", function (e) {
        checkboxShowHideHandler($(this));
    });
	
	

    function checkboxShowHideHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-radio")) {
                Coral.commons.ready(element, function (component) {
                    showHide(component, element);
                    component.on("change", function () {
                        showHide(component, element);
                    });
                });
            } else {
                var component = $(element).data("radio");
                if (component) {
                    showHide(component, element);
                }
            }
        })
    }

    function showHide(component, element) {
        var target = $(element).data("cqDialogCheckboxShowhideLightboxTarget");
        var $target = $(target);
        if (target) {
            $target.addClass("hide");
            if (component.checked && component.value == 'modal') {
                $target.removeClass("hide");
            }
        }
    }
})(document, Granite.$);