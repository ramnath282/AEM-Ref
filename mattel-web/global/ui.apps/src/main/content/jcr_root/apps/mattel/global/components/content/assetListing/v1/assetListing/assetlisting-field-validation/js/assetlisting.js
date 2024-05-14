(function (document, $)
{
    "use strict";
    $(document).on("foundation-contentloaded", function (e)
    {
        if ($("coral-switch[name='./enableFilter']").prop('checked') === false)
        {
			$(".asset-listing-select-filter").parent().hide();
            $(".asset-listing-filtersection-label").parent().parent().hide();
            hideOtherFields();
            //$(".asset-listing-clearbutton-well").hide();
        }
		//commenting out enable-clearall-switch as it is next phase implementation.
        /*if ($("coral-switch[name='./enableClearAll']").prop('checked') === false)
        {
			$(".asset-listing-clearall-label").parent().parent().hide();
        }*/
    });

    $(document).on("change", ".asset-listing-enablefilter-options", function (e)
    {
        var filterSelectOption = $("coral-switch[name='./enableFilter']").prop('checked');
        if(filterSelectOption){
			$(".asset-listing-select-filter").parent().show();
            $(".asset-listing-filtersection-label").parent().parent().show();
            showOtherFields();
            //$(".asset-listing-clearbutton-well").show();
        }else{
			$(".asset-listing-select-filter").parent().hide();
            $(".asset-listing-filtersection-label").parent().parent().hide();
            hideOtherFields();
            //$(".asset-listing-clearbutton-well").hide();
        }
    });
    
    
    function hideOtherFields() {
    	$("input[name='./isAlphabeticallyGroupSort']").parent().parent().hide();
    	$("input[name='./isAlphabeticallyFacetSort']").parent().parent().hide();
    	$("input[name='./enableFiltersOnFirstLoad']").parent().parent().hide();
    	$(".asset-listing-clearall-label").parent().hide();
    	$(".asset-listing-noresults-label").parent().hide();
    }
    
    function showOtherFields() {
    	$("input[name='./isAlphabeticallyGroupSort']").parent().parent().show();
    	$("input[name='./isAlphabeticallyFacetSort']").parent().parent().show();
    	$("input[name='./enableFiltersOnFirstLoad']").parent().parent().show();
    	$(".asset-listing-clearall-label").parent().show();
    	$(".asset-listing-noresults-label").parent().show();
    }

    /* $(document).on("change", ".asset-listing-enable-clearall-switch", function (e)
    {
        var filterSelectOption = $("coral-switch[name='./enableClearAll']").prop('checked');
        if(filterSelectOption){
			$(".asset-listing-clearall-label").parent().parent().show();
        }else{
			$(".asset-listing-clearall-label").parent().parent().hide();
        }
    });*/

})(document, Granite.$); 