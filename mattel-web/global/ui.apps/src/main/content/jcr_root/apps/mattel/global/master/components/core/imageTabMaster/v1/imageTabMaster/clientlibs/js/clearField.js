(function (document, $) {
    "use strict";
    $(document).on("click", ".altButton", function (e) {
			clearField(e.target);
    });

    function clearField(el){
      var element = $(el).closest(".altTextContainer").find('input[type="text"]').val(''); 
    }

})(document, Granite.$);
