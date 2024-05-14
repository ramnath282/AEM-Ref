(function (document, $) {
    "use strict";

    // when dialog gets injected
    $(document).on("foundation-contentloaded", function (e) {
        // if there is already an inital value make sure the according target element becomes visible
        switchShowHideHandler($(".cq-dialog-switch-showhide", e.target));
    });

    $(document).on("change", ".cq-dialog-switch-showhide", function (e) {
        switchShowHideHandler($(this));
    });

    function switchShowHideHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-switch")) {
                // handle Coral3 base switch
                Coral.commons.ready(element, function (component) {
                    showHide(component, element);
                    component.on("change", function () {
                        showHide(component, element);
                    });
                });
            } else {
                // handle Coral2 based switch
                var component = $(element).data("switch");
                if (component) {
                    showHide(component, element);
                }
            }
        })
    }
    
    function showHide(component, element) {
        // get the selector to find the target elements. its stored as data-.. attribute
        var target = $(element).data("cqDialogSwitchShowhideTarget");
        var $target = $(target);

        if($(target).parent( ".coral-Form-fieldwrapper").length){
            $target = $(target).parents( ".coral-Form-fieldwrapper");
        }
        if($target){
            $target.hide();
            if (component.checked) {
                $target.show();
            }
        }
    }
})(document, Granite.$);
