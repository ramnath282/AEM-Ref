$("document").ready(function(){
    $(".fadein-image-cmp").each(function(i){
		$(this).find(".section-C .track-historical-character-link").attr("data-target","#character"+i);
        $(this).find(".modal").attr({"id":"character"+i, "aria-labelledby":"character"+i});
    });
});