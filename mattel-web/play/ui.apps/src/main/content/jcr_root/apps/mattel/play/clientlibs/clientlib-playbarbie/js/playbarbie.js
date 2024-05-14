$(document).ready(function () {
    $(window).resize(function () {
        mobileNavigation();
    });
    mobileNavigation();
    var imageActiveText = $('#primary-nav a.active').text().trim().toLowerCase();
    if ($("#primary-nav").find(".active").attr('data-link-name') == imageActiveText) {
        $("#primary-nav").find(".active img").attr("src", "/content/dam/play-barbie/kids-site/navigation/nav_" + imageActiveText + "_hover.png");
    }

    var imageHoverActiveText = $('#primary-nav li a.active').text().trim().toLowerCase();
    $("#primary-nav li a").hover(function () {
        if ($("#primary-nav").find(".active")) {
            imageHoverActiveText = $(this).text().trim().toLowerCase();
            $(this).css("background-color", "#FDEDF6");
            $(this).css("color", "#de1c85");
            $(this).find("img").attr("src", "/content/dam/play-barbie/kids-site/navigation/nav_" + imageHoverActiveText + "_hover.png");
        }
    }, function () {
        if ($(this).hasClass("active")) {
            return;
        } else {
            imageHoverActiveText = $(this).text().trim().toLowerCase();
            $(this).css("background-color", "#de1c85");
            $(this).css("color", "#fff");
            $(this).find("img").attr("src", "/content/dam/play-barbie/kids-site/navigation/nav_" + imageHoverActiveText + ".png");
        }
    });
});

$(document).on('click', '.enable-carousel-nav .nav-carousel-slides.nav-categorylist li a', function () {
    if (window.innerWidth <= 768) {
        $('.nav-categorylist').css('display', 'none');
    } else {
        $('.nav-categorylist').css('display', 'block');
    }
});

function mobileNavigation() {
    if (window.innerWidth <= 768) {
        if ($("#primary-nav").find("ul li:nth-child(1) > a").attr('data-link-name') == "games") {
            $("#primary-nav").find("ul li:nth-child(1) > a > img").attr("src", "/content/dam/play-barbie/kids-site/navigation/mobile-images/nav-mob-open_icon-games2x.png");
        }
        if ($("#primary-nav").find("ul li:nth-child(2) > a").attr('data-link-name') == "toys") {
            $("#primary-nav").find("ul li:nth-child(2) > a > img").attr("src", "/content/dam/play-barbie/kids-site/navigation/mobile-images/nav-open-mob_icon-toys2x.png");
        }
    }
}