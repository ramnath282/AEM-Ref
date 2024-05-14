let playCharacterLanding = ".play-character-landing-block .play-list .lazy-load.column-4",
playTilesGallery = ".gallery .play-tiles-gallery .gallery-tile .column-4",
cdaBanner=".cda-banner .crm-container .slick-track .slick-slide";
$(document).ready(function () {
    charcterTradeMark(playCharacterLanding);
    charcterTradeMark(playTilesGallery);
    charcterTradeMark(cdaBanner);
    $(window).scroll(function(){
        charcterTradeMark(playCharacterLanding);
        charcterTradeMark(playTilesGallery);
        charcterTradeMark(cdaBanner);
    });
});
function charcterTradeMark($ele){
    $($ele).each(function(index, value) {
        var innerText=$(value).find("span").text();
        if(innerText.length > 0 && $(value).find(".trade-mark-element").length == 0 && (innerText[innerText.length - 1] == "®" || innerText[innerText.length - 1] == "™")){
            var appendDOM= "<span class='trade-mark-element'>"+ innerText[innerText.length - 1] + "</span>";
            $(value).find("span").text(innerText.substring(0,innerText.length - 1))
             $(value).find("span").append(appendDOM);
        } 
    });
}
$(document).ready(function () {
    charcterTradeMark()
    $(window).scroll(function(){
        charcterTradeMark();
    });
    function charcterTradeMark(){
        $(".downloadImageGallery .play-tiles-gallery .gallery-tile .column-4").each(function(index, value) {
            var innerText=$(value).find("h2").text();
            if(innerText.length > 0 && $(value).find(".trade-mark-element").length == 0 && (innerText[innerText.length - 1] == "®" || innerText[innerText.length - 1] == "™")){
                var appendDOM= "<span class='trade-mark-element'>"+ innerText[innerText.length - 1] + "</span>";
                $(value).find("h2").text(innerText.substring(0,innerText.length - 1))
                 $(value).find("h2").append(appendDOM);
            } 
            if(innerText.length > 0 && $(value).find(".trade-mark-element").length == 0 && (innerText[innerText.length - 2] == "®" || innerText[innerText.length - 1] == "™")){
                var appendDOM= "<span class='trade-mark-element'>"+ innerText[innerText.length - 2] + "</span>";
                $(value).find("h2").text(innerText.substring(0,innerText.length - 2))
                 $(value).find("h2").append(appendDOM);
            } 
        });
    }
});
