var foundationReg = $(window).adaptTo("foundation-registry");
foundationReg.register("foundation.validation.validator", {
    selector: "[data-foundation-validation = 'article.tag.max.count.validation.secondary']",
    validate: function(e1) {
        if (e1) {
            var $e1 = $(e1);
            var errorMsg = "Max tag count exceeded";
            var tagList = $e1.find('coral-tagList');


                if (tagList != null && tagList != "" && tagList[0].items.length > 3) {
                    return errorMsg;
                } else {

                    return null;

                }



        }

    }

});