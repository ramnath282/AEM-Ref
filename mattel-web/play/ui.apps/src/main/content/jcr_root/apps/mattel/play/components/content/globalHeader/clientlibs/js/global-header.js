$(document).ready(function(){
    $(".gblhdr-list").click(function(){
        var listItemCount; 
        var maxcount = 5; // maximum items count dropdown
        listItemCount = 2; // count of items to be displayed in the dropdown
        var liHeight = $('.gblhdr-list-item li').height(); 
        var visibleli = liHeight*listItemCount;
        $('.gblhdr-list-item').css('height',visibleli+10);
        if(listItemCount < maxcount){
            $('.gblhdr-list-item').css('overflow-y','scroll');
        }
        else if(listItemCount > maxcount){
            $('.gblhdr-list-item').css('height',liHeight*maxcount+10);
            $('.gblhdr-list-item').css('overflow-y','none');
        }
    });
});
