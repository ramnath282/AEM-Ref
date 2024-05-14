(function (document, $) {
    "use strict";

    // when dialog gets injected
    $(document).on("foundation-contentloaded", function (e) {
        // if there is already an inital value make sure the according target element becomes enabled
        switchEnableDisableHandler($(".cq-dialog-switch-enableDisable", e.target));
    });

    $(document).on("change", ".cq-dialog-switch-enableDisable", function (e) {
        switchEnableDisableHandler($(this));
    });

    function switchEnableDisableHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-switch")) {
                // handle Coral3 base switch
                Coral.commons.ready(element, function (component) {
                    enableDisable(component, element);
                    component.on("change", function () {
                        enableDisable(component, element);
                    });
                });
            } else {
                // handle Coral2 based switch
                var component = $(element).data("switch");
                if (component) {
                    enableDisable(component, element);
                }
            }
        })
    }

    function enableDisable(component, element) {
        // get the selector to find the target elements. its stored as data-.. attribute
        var target = $(element).data("cqDialogSwitchShowhideTarget");
        var $target = $(target);
			if($target.length > 0) {
            $target.prop('disabled', true);
            if (component.checked) {
                $target.prop('disabled', false);
            }        
			}     
    }
})(document, Granite.$);
