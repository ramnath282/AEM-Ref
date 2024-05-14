(function($, $document) {
    "use strict";
    $document.on("dialog-ready", function() {
		
    if ($(".ag-cmp-video360-editor").length > 0){
	
		if ($(".videoTypeSelection").find(".coral3-Select-label").text() == 'Scene7') {
            $(".video-link").parent().children("label").text("Video URL");	

        } else if ($(".videoTypeSelection").find(".coral3-Select-label").text() == 'Deluxe') {
				$(".video-link").parent().children("label").text("Deluxe Platform ID");
        }

        $(".videoTypeSelection").find(".coral3-SelectList-item").bind('click', function() {
            var mType=$(this).text();
            if (mType == 'Deluxe') {
                $(".video-link").parent().children("label").text("Deluxe Platform ID");
            } else {
                $(".video-link").parent().children("label").text("Video URL");				
            }
        })
	}
    });
})($, $(document));