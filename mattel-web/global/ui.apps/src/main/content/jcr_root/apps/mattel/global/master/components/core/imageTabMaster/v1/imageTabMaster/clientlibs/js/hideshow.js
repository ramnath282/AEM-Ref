(function(document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function(e) {
        $(".options").each(function() {
            showhide($(this));
        });
    });

    $(document).on("change", ".options", function(e) {
        showhide($(this));
    });

    function showhide(el) {
        var $targetTab;
        var $mobileImageHovercontainer;
        var $tabletImageHovercontainer;
        var isCustomMobile = false;
        var isCustomTablet = false;
        var tabStatus = el.prop("checked") ? el.val() : false;
        if (el.hasClass("customMobile")) {
            isCustomMobile = true;
            $mobileImageHovercontainer = $('.mobileImageHovercontainer');
            $targetTab = $('.mobileImagecontainer');
        }
        if (el.hasClass("customTablet")) {
            isCustomTablet = true;
            $tabletImageHovercontainer = $('.tabletImageHovercontainer');
            $targetTab = $('.tabletImagecontainer');
        }
        else if (el.hasClass("hoverImage")) {
            $targetTab = $('.hoverImagecontainer');
        }

        if ($targetTab != null && $targetTab != undefined) {
            var $parent = $targetTab.parents().eq(1);
            var id = $parent.attr("aria-labelledby");
            var $targetPanel = $("#" + id);
            if (tabStatus) {
                $targetPanel.show();
                if (isCustomMobile){
                    $mobileImageHovercontainer.show();
                } else if (isCustomTablet){
                    $tabletImageHovercontainer.show();
                }
            } else {
                $targetPanel.hide();
                if (isCustomMobile){
                    $mobileImageHovercontainer.hide();
                } else if (isCustomTablet){
                    $tabletImageHovercontainer.hide();
                }
            }
        }

    }
})(document, Granite.$);