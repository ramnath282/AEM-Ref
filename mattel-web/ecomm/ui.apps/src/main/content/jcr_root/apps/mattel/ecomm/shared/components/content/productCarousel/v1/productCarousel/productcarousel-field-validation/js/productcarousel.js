(function(document, $) {
    "use strict";

    $(document).on("dialog-ready", function(e) {
    	 if ($(".product-carousel-editor").length > 0){
 			var enableContentSwitch = $(".product-carousel-editor coral-switch[name='./enableContentBlock']").prop('checked');
 			showHide(enableContentSwitch);
         }
    });

    $(document).on("change", ".switcher-enable-content-block", function(e) {
        var formChange = $("coral-switch[name='./enableContentBlock']").prop('checked');
        showHide(formChange);
    });

    function showHide(element) {
        if(element===true){
            $('.product-carousel-tabs coral-tab')[1].show();
        }else{
            $('.product-carousel-tabs coral-tab')[1].hide();
        }
    }

})(document, Granite.$);