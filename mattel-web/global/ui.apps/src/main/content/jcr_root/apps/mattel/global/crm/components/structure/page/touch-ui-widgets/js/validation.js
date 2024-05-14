(function($, $document) {
    "use strict";

    $(document).on('click', '.dialog-analyticsprop-field .coral-Collapsible-header', function(){
        if($(this).attr("aria-expanded") == 'true'){
            $(this).siblings('.coral-Collapsible-content').find('.dialog-analyticsprop-detail .coral3-Multifield-item').each(function() {
                updateDropdown($(this).find('.analytics-attr-key-dropdown'));
            });
        }
    });

    $(document).on('change', '.analytics-attr-key-dropdown', function(){
        updateDropdown($(this));

    });

    setTimeout(function(){
        var numberOfAnalyticsProp = $('.dialog-analyticsprop-field .analytics-attr-key-dropdown').length;
        if(numberOfAnalyticsProp > 0){
            $('.dialog-analyticsprop-field .coral-Collapsible-header').trigger('click');
        }
    },1500);


    $(document).on('click', '.dialog-analyticsprop-detail > .coral3-Button', function(){
        updateDropdown($(this).prev().find(".analytics-attr-key-dropdown"));
    });

    function updateDropdown(dropdownref){
        if (dropdownref.length>0) {
            var dropval = dropdownref.val();
            $.ajax({
                url: "/bin/populateAnalyticsAttrValues",
                type: "GET",
                async: false,
                data: {
                    "fieldType": dropval,
                    "contentPath":(window.location.href).split('?')[1]
                },
                success: function(res) {
                    var obj = JSON.parse(JSON.stringify(res));
                    var items = [];
                    for(var j in obj) {
                        items.push({"value":j, content: {
                            "textContent": obj[j]
                        }
                                   });
                    }
                    dropdownref.parent().parent().find(".analytics-attr-value-dropdown coral-selectlist").empty();
                    dropdownref.parent().parent().find(".analytics-attr-value-dropdown coral-selectlist-item").empty();
                    $(dropdownref.parent().parent().find(".analytics-attr-value-dropdown coral-select-item")).remove();
                    var select = dropdownref.parent().parent().find(".analytics-attr-value-dropdown").get(0);
                    select.set({});
                        items.forEach(function(value, index) {
                            if(index==0){
                                select.set(value);
                            }
                            select.items.add(value);
                        });
                },
                error: function(message) {
                    console.log(message);
                }
            });
        }
    }


})($, $(document));



