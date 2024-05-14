$(document).ready(function(){
    checkboxPageload();
    headerHeightTop();
    $("input[type='checkbox']").change(function(){
        $(this).prop("checked") == true ? $(this).attr("aria-checked", "true") : $(this).attr("aria-checked", "false");
    });
});
function checkboxPageload() {
        setTimeout(function(){
            $("input[type='checkbox']:checked").each(function() {
                $(this).attr("aria-checked", "true");
            });
        }, 500);      
    }
function headerHeightTop() {
        var headerHeight = $("header").outerHeight();
        $(".root").css("margin-top", headerHeight);
    }
