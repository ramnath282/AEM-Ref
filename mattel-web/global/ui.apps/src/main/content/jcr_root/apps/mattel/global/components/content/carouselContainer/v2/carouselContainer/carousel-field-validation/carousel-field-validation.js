(function(document, $) {
    "use strict";

    $(document).on("dialog-ready", function(e) {
        var freeformLoad = $("coral-switch[name='./freeform']").prop('checked');
        showHide("freeform",freeformLoad);

         var freeFormCarouselLoad = $("coral-switch[name='./freeFormCarousel']").prop('checked');
         showHide("freeFormCarousel",freeFormCarouselLoad);


        checkboxShowHideHandler($(".switcher-showhide-items-options", e.target));
    });
    $(document).on("change", ".switcher-showhide-items-options", function(e) {
        var formChange = $("coral-switch[name='./freeform']").prop('checked');
        showHide("freeform",formChange);

        var freeFormCarouselChange = $("coral-switch[name='./freeFormCarousel']").prop('checked');
        showHide("freeFormCarousel",freeFormCarouselChange);

        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el) {
        el.each(function(i, element) {
            if ($(element).is("coral-switch")) {
                Coral.commons.ready(element, function(component) {
                    component.on("change", function() {
                        if($(element).attr("name") == "./freeform" && $("coral-switch[name='./freeform']").prop('checked')){
                            $("coral-switch[name='./freeFormCarousel']").prop('checked', false);
                            showHide("freeform",true);
                        }
                        else if ($(element).attr("name") == "./freeFormCarousel" && $("coral-switch[name='./freeFormCarousel']").prop('checked')){
                            $("coral-switch[name='./freeform']").prop('checked', false);
                            showHide("freeFormCarousel",true);
                        }
                    });
                });
            }
        });
    }

    function showHide(component,element) {
        if(component === "freeform" && element === true) {
                $("coral-numberinput[name='./slidetoscroll']").parent().show();
                $("coral-numberinput[name='./slideToShow']").parent().hide();
                $("coral-numberinput[name='./freeFormTab']").parent().show();
                $("coral-numberinput[name='./freeFormMob']").parent().show();
        }
        else if (component === "freeFormCarousel" && element === true){
                $("coral-numberinput[name='./slideToShow']").parent().hide();
                $("coral-numberinput[name='./freeFormTab']").parent().hide();
                $("coral-numberinput[name='./freeFormMob']").parent().hide();
                $("coral-numberinput[name='./slidetoscroll']").parent().hide();
        }
        else if(!$("coral-switch[name='./freeFormCarousel']").prop('checked') && !$("coral-switch[name='./freeform']").prop('checked')) {
            $("coral-numberinput[name='./slideToShow']").parent().show();
            $("coral-numberinput[name='./freeFormTab']").parent().hide();
            $("coral-numberinput[name='./freeFormMob']").parent().hide();
            $("coral-numberinput[name='./slidetoscroll']").parent().show();
        }
    }
})(document, Granite.$);