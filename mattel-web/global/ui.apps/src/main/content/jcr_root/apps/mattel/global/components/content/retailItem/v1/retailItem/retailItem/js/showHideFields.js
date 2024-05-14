/* This listener will work for retail item as well as app item.
*  Please note that updating this listener might affect app item behavior.
*  In case modifications are needed in js please copy this js in app item and then modify this js
*/
(function (document, $) {
    "use strict";
    
    $(document).on("foundation-contentloaded", function (e) { 
       checkboxShowHideHandler($(".cq-dialog-checkbox-showhide-items-options", e.target));
    });
    $(document).on("change", ".cq-dialog-checkbox-showhide-items-options", function (e) {
        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-switch")) {
                Coral.commons.ready(element, function (component) {
                    showHide(component, element);
                });
            }
        });
    }
    function showHide(component, element) {
        var target = $(element).data("cqdialogcheckboxshowhidetarget");
        var $target;
        if($(target).parent( ".coral-Form-fieldwrapper").length){
            $target = $(target).parents( ".coral-Form-fieldwrapper");
            $target.hide();
            if (component.checked) {
                $target.show();
            }
        }
    }
})(document, Granite.$);