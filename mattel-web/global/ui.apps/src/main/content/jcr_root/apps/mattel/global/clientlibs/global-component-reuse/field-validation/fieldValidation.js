(function($, $document) {
    $document.on("dialog-ready", function() {
        var hiddenTrackingID = $("input[name='./trackingText']");
        var hiddenTextValue = hiddenTrackingID.prop('value');
        var hiddenTrackingCTA = $("coral-switch[name='./trackThisCTA']").prop('checked');
        if ((typeof hiddenTextValue != "undefined" && hiddenTextValue.trim().length === 0) && hiddenTrackingCTA === true) {
            $("coral-switch[name='./trackThisCTA']").prop('checked', false);
        }

    });

    $(document).on("click", ".cq-dialog-submit", function(e) {
        var trackingId = $("input[name='./trackingText']");
        var textVal = trackingId.prop('value');
        var trackCTA = $("coral-switch[name='./trackThisCTA']").prop('checked');
        if ((typeof textVal != "undefined" && textVal.trim().length === 0) && trackCTA === false) {
            $(trackingId).removeClass("is-invalid");
            $(trackingId).prop('aria-required', "false");
            $(trackingId).removeAttr("aria-required");
            return true;
        }

    });
    
    $(document).on("foundation-contentloaded", function(e) {
        checkboxShowHideHandler($(".cq-dialog-checkbox-showhide-items-options", e.target));
    });
    $(document).on("change", ".cq-dialog-checkbox-showhide-items-options", function(e) {
        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el) {
        el.each(function(i, element) {
            if ($(element).is("coral-switch")) {
                Coral.commons.ready(element, function(component) {
                    showHide(component, element);
                });
            }
        });
    }

    function showHide(component, element) {
        var target = $(element).data("cqdialogcheckboxshowhidetarget");
        var $target;
        if ($(target).parent(".coral-Form-fieldwrapper").length) {
            $target = $(target).parents(".coral-Form-fieldwrapper");
            $target.hide();
            $(target).attr('aria-required', 'false');
            if (component.checked) {
                $target.show();
                $(target).attr('aria-required', 'true');
            }
        }
    }

})(jQuery, jQuery(document));