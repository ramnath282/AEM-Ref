(function ($, $document,dialog) {
    "use strict";
    $(document).on("click", ".cq-dialog-submit", function (e) {
        var current=$('.cq-dialog-dropdown-showhide').val();
        $('.cardtFixedColumn > .coral-FixedColumn-column > .list-option-listfrom-showhide-target').each(function(){
            var field= $(this);
            var currentValue=field.attr('data-showhidetargetvalue');
            if(current != currentValue){
                field.find('.coral-Form-field').each(function(){
                    var myvar = $(this);
                    myvar[0].value = '';
                });

                field.find('.js-coral-pathbrowser-input').each(function(){
                    var myvar = $(this);
                    myvar[0].value = '';
                });
            }
        });
        //$(window).adaptTo("foundation-ui").alert("Close", "Dialog closed, event [dialog-closed]");
    });
})($, $(document));