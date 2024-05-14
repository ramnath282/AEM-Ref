$( document ).ready(function() {
    $( '.fa-info-circle').each(function(index){
        var popoverBtn = $(this).prev();
        $(popoverBtn).append($(this));
    });
});