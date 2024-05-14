(function (document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function (e) {
        $(".label-spacing").parents(".coral-Form-fieldwrapper").find(".coral-Form-fieldinfo").css({"right" : "-70%","top" : "30px"});
        $(".label-spacing").parents(".coral-Form-fieldwrapper").find("coral-tooltip").css({"right" : "-58%","top" : "30px"});
        checkboxShowHideHandler($(".cq-dialog-checkbox-showhide-dynamic-feature", e.target));
    });

    $(document).on("change", ".cq-dialog-checkbox-showhide-dynamic-feature", function (e) {
        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-switch")) {
                Coral.commons.ready(element, function (component) {
                    showHide(component, element);
                    component.on("change", function () {
                        showHide(component, element);
                    });
                });
            } else {
                var component = $(element).data("checkbox");
                if (component) {
                    showHide(component, element);
                }
            }
        })
    }

    function showHide(component, element) {
        console.log('showing');
        var target = $(element).data("cqdialogcheckboxshowhidedynamicfeaturetarget");
        var $target = $(target).parent(".coral-Form-fieldwrapper");
        if (target) {
            $target.addClass("hide");
            if (component.checked) {
                $target.removeClass("hide");
            }
        }
    }
})(document, Granite.$);