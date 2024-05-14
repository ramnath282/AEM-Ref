(function (document, $) {
    "use strict";

    // when dialog gets injected
    $(document).on("foundation-contentloaded", function (e) {
        $(".ctaMasterTab-showMoreOn").hide();
        switchshowHideViewAllViewAllHandler($(".cq-dialog-switch-showHide-viewAll", e.target));
    });

    $(document).on("change", ".cq-dialog-switch-showHide-viewAll", function (e) {
        switchshowHideViewAllViewAllHandler($(this));
    });

    function switchshowHideViewAllViewAllHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-switch")) {
                // handle Coral3 base switch
                Coral.commons.ready(element, function (component) {
                    showHideViewAll(component, element);
                    component.on("change", function () {
                        showHideViewAll(component, element);
                    });
                });
            } else {
                // handle Coral2 based switch
                var component = $(element).data("switch");
                if (component) {
                    showHideViewAll(component, element);
                }
            }
        })
    }
    function showHideViewAll(component, element) {
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