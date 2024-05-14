(function (document, $) {
    "use strict";

        $(document).on("dialog-ready", function() {
        $(".global-video-gallery-v1 .ctaMasterTab-showMoreOn").attr("style","display: block;")
    });

    $(document).on("foundation-contentloaded", function (e) {
        checkboxShowHidePlayerHandler($(".cq-dialog-checkbox-showhide-player", e.target));
    });

    $(document).on("change", ".cq-dialog-checkbox-showhide-player", function (e) {
        checkboxShowHidePlayerHandler($(this));
    });

    function checkboxShowHidePlayerHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-checkbox")) {
                Coral.commons.ready(element, function (component) {
                    showHidePlayer(component, element);
                    component.on("change", function () {
                        showHidePlayer(component, element);
                    });
                });
            } else {
                var component = $(element).data("checkbox");
                if (component) {
                    showHidePlayer(component, element);
                }
            }
        })
    }

    function showHidePlayer(component, element) {
        var targetElement = $(element).data("cqDialogCheckboxShowhidePlayerTarget");
        var $targetElement = $(targetElement);
        if (targetElement) {
            $targetElement.addClass("hide");
            if (component.checked) {
                $targetElement.removeClass("hide");
            }
        }
    }
})(document, Granite.$);