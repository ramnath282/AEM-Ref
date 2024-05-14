(function(document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function(e) {
        showHideHandler($(".cq-dialog-dropdown-showhide-multifield", e.target));
    });

    $(document).on("change", ".cq-dialog-dropdown-showhide-multifield", function(e) {
        showHideHandler($(this));
    });

    $(document).on("change", ".treatmentVideoTypeSelection", function() {
        var $this = $(this);
        var $selectedItem = $this.find("coral-selectlist-item:selected");
        var $selectedItemLabel = $selectedItem.text();
        if($selectedItemLabel == "Deluxe") {
            $selectedItem.closest(".coral3-Multifield-item").find(".treatment-video-link").parent().children("label").text("Deluxe Platform ID");
        } else {
            $selectedItem.closest(".coral3-Multifield-item").find(".treatment-video-link").parent().children("label").text("Video Link");
        }
    });

    function showHideHandler(el) {
        el.each(function(i, element) {
            Coral.commons.ready(element, function(component) {
                showHideCustom(component, element);
                component.on("change", function() {
                    showHideCustom(component, element);
                });
            });
        })
    }

    function showHideCustom(component, element) {
	   var selectedVal = element.value;
       var target = $(element).data("cq-dialog-dropdown-showhide-target-multifield");
       var $target = $(target);
       var elementIndex = $(element).closest('coral-multifield-item').index();
       if (target) {
         var value;
         if (component.value) {
           value = component.value;
         } else {
           value = component.getValue();
         }
         $(element).closest("coral-multifield-item").find(target)
         .each(function(index) {
            if(element.value == "pdf") {
                $(this).closest(".coral3-Multifield-item").find(".treatmentVideoTypeSelection").parent().hide();
                $(this).closest(".coral3-Multifield-item").find(".treatment-video-link").parent().children("label").text("PDF Link");
            } else {
                $(this).closest(".coral3-Multifield-item").find(".treatmentVideoTypeSelection").parent().show();
                var videoTypeSelection = $(this).closest(".coral3-Multifield-item").find(".treatmentVideoTypeSelection").find(".coral3-Select-label").text();
                if(videoTypeSelection == 'Deluxe') {
                    $(this).closest(".coral3-Multifield-item").find(".treatment-video-link").parent().children("label").text("Deluxe Platform ID");
                } else {
                $(this).closest(".coral3-Multifield-item").find(".treatment-video-link").parent().children("label").text("Video Link");
                }
            }
            var tarIndex = $(this).closest('coral-multifield-item').index();
            if (elementIndex == tarIndex) {
                $(this).not(".hide").parent().addClass("hide");
                $(this).filter("[data-showhidetargetvalue='" + value + "']")
                .parent().removeClass("hide");
            }
         });
        }
    }

})(document, Granite.$);