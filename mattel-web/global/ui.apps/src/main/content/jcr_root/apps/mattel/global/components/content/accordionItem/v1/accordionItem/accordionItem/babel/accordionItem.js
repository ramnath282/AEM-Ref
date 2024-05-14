(function (document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function (e) { 
    });
    $(document).on("change", ".trackCtaSwitch ", function (e) {
        var nameAttr =$(this).attr("name");
        var selectionVal = $(this).prop("checked");
        disableTrackingText(nameAttr,selectionVal);
    });
    function disableTrackingText(nameAttr,selectionVal) {
        if(nameAttr=="./trackThisCta"){
           selectionVal ? $("input[name='./trackingText']").prop('disabled', false) : $("input[name='./trackingText']").prop('disabled', true);
        }
        else if(nameAttr=="./trackThisAnchor"){
            selectionVal ? $("input[name='./trackingTextAnchor']").prop('disabled', false) : $("input[name='./trackingTextAnchor']").prop('disabled', true);
        }
    }
})(document, Granite.$);