(function (document, $) {
    "use strict";
    $(document).on("foundation-contentloaded", function (e) {
        var items = $('[data-rowresume="true"]');
        let self = $(this);
        $(items).each(function(i) {
            self.closest('.coral-Form-fieldwrapper').addClass("coral-Form-fieldwrapper--rowresume");
        });
        var whitebg = $('[data-whitebg="true"]');
         $(whitebg).each(function(item) {
            self.addClass("white-background");
        });
        var $target = $(".togglefield");
        $target.each(function(){
            var id = self.parents(':eq(1)').attr("aria-labelledby");
            var $tabTarget=$("#"+id);
            $tabTarget.show();
        });
        checkboxShowHideHandler($(".cq-dialog-checkbox-showhide-image-options", e.target));
    });

    $(document).on("change", ".cq-dialog-checkbox-showhide-image-options", function (e) {
        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el) {
        el.each(function (i, element) {
            if($(element).is("coral-switch")) {
                Coral.commons.ready(element, function (component) {
                    showHide(component, element);
                    component.on("change", function () {
                        if($(element).attr("name") === "./customMobile"){
                            $( "coral-tab-label" ).each(function( index ) {
                                if($(this).text()==="Dynamic Media"){
                                $(this).parent().show();
                                }
                            });
                            $("coral-switch[name='./tileImage']").prop('checked', false);
                            var tileSwitch =   $("coral-switch[name='./tileImage']");
                            showHide(tileSwitch, tileSwitch);
                        }
                        else if ($(element).attr("name") === "./tileImage"){
                            // logic to hide  dynamic media tab
                            $( "coral-tab-label" ).each(function( index ) {
                                if($(this).text()==="Dynamic Media"){
                                $(this).parent().hide();
                                }
                            });

                            $( "coral-switch[name='./customMobile']").prop('checked', false);
                            var customMobileSwitch =   $("coral-switch[name='./customMobile']");
                            showHide(customMobileSwitch, customMobileSwitch);
                        }
                    });
                });
            } else {
                var component = $(element).data("checkbox");
                if (component) {
                    showHide(component, element);
                }
            }
        });
    }

    function showHide(component, element) {
        var target = $(element).data("cqdialogcheckboxshowhidetarget");
        var $target;
        if($(target).parent( ".coral-Form-fieldwrapper").length){
            $target = $(target).parents( ".coral-Form-fieldwrapper");
            $target.hide();
            if (component.checked) {
                $target.show();
            }
        }
        else if($(target).parents(".coral3-Panel").length){
            $target = $(target);
            $target.each(function(){
                var id = $(this).parents(':eq(1)').attr("aria-labelledby");
                var $tabTarget=$("#"+id);
                $tabTarget.hide();
                $(this).hide();
                if (component.checked) {
                    $(this).show();
                    $tabTarget.show();
                }
            });
        }
    }
})(document, Granite.$);
