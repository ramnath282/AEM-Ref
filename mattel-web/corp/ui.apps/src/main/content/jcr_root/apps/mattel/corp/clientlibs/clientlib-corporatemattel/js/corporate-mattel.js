$( document ).ready(function() {
    $("#pagename-terms-and-conditions .static_ol_number li a").click(function(event){
        event.preventDefault();
        if ($(window).width() > 1024) {
            $("html, body").animate({ scrollTop: $($(this).attr("href")).offset().top - 90 }, 500);
        }else{
            $("html, body").animate({ scrollTop: $($(this).attr("href")).offset().top - 10 }, 500);
        }
    });
    $(".primary-nav>ul>li").hover(function(){
        if ($(window).width() > 1024) {
            $(this).find("a.show-sub-nav").attr("aria-expanded","true");
        }
        }, function(){
        if ($(window).width() > 1024) {
            $(this).find("a.show-sub-nav").attr("aria-expanded","false");
        }
      });
});