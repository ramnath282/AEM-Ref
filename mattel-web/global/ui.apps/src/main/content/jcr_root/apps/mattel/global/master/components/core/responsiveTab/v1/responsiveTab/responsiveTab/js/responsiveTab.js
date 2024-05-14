(function (document, $)
{
    "use strict";
    $(document).on("foundation-contentloaded", function (e)
    {
        if ($("coral-switch[name='./showMoreFeature']").prop('checked') === false && $("coral-switch[name='./showMoreRepeat']").prop('checked') === false)
        {
            $("coral-switch[name='./showMoreRepeat']").parent().hide();
            $("coral-numberinput[name='./ctaRepeat']").parent().hide();
        }
        if($("coral-switch[name='./showMoreRepeat']").prop('checked') === false) {
            $("coral-numberinput[name='./ctaRepeat']").parent().hide();
        }
        $(".ctaMasterTab-showMoreOn").hide();
        checkboxShowHideHandler($(".cq-dialog-checkbox-showhide-tab-options", e.target));
    });

    $(document).on("change", ".cq-dialog-checkbox-showhide-tab-options", function (e)
    {
        checkboxShowHideHandler($(this));
    });

    function checkboxShowHideHandler(el)
    {
        el.each(function (i, element)
        {
            if ($(element).is("coral-switch"))
            {
                Coral.commons.ready(element, function (component)
                {
                    showHide(component, element);
                    component.on("change", function ()
                    {
                        if ($(element).attr("name") == "./showMoreFeature" && $("coral-switch[name='./showMoreFeature']").prop('checked') === true)
                        {
                            $("coral-switch[name='./showMoreRepeat']").parent().show();
                        }
                        if ($(element).attr("name") == "./showMoreRepeat" && $("coral-switch[name='./showMoreRepeat']").prop('checked') === true)
                        {
                            $("coral-numberinput[name='./ctaRepeat']").parent().show();
                        }
                        if ($(element).attr("name") == "./showMoreFeature" && $("coral-switch[name='./showMoreFeature']").prop('checked') === false)
                        {
                            $("coral-switch[name='./showMoreRepeat']").prop('checked', false);
                            $("coral-numberinput[name='./ctaRepeat']").parent().hide();
                            $("coral-switch[name='./showMoreRepeat']").parent().hide();
                               $( "coral-tab-label" ).each(function( index ) {
                                if($(this).text()=="Show More Rows"){
                                $(this).parent().hide();
                                }
                            });
                        }
                        if ($(element).attr("name") == "./showMoreRepeat" && $("coral-switch[name='./showMoreRepeat']").prop('checked') === false)
                        {
                            $("coral-numberinput[name='./ctaRepeat']").parent().hide();
                        }
                    });
                });
            }
            else
            {
                var component = $(element).data("checkbox");
                if (component)
                {
                    showHide(component, element);
                }
            }
        });
    }

    function showHide(component, element)
    {
        var target = $(element).data("cqdialogcheckboxshowhidetarget");
		$(target).each(function( index )
		{
			  if($(this).hasClass("showMoreOff"))
				{
					$(this).hide(); 
					$(this).siblings().hide();
					if (!component.checked)
					{
						$(this).show();
					} 
					else 
					{
						$(this).siblings(".ctaMasterTab-showMoreOn").show();
					}
				} 
				else if ($(this).parent(".coral-Form-fieldwrapper").length)
        {
					var $this = $(this).parents(".coral-Form-fieldwrapper");
					$this.hide();
            if (component.checked)
            {
						$this.show();
            }
        }
				else if ($(this).parents(".coral3-Panel").length)
            {
                var id = $(this).parents(':eq(1)').attr("aria-labelledby");
                var $tabTarget = $("#" + id);
                $tabTarget.hide();
                $(this).hide();
                if (component.checked)
                {
                    $(this).show();
                    $tabTarget.show();
                }

        }
        });
    }
})(document, Granite.$);