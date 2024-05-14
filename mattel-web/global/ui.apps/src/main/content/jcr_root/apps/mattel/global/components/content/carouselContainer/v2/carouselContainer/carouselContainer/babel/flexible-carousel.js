import {
    sliderInit
} from "../../../clientlibs/common-dependency/shared/slider-init.js";
const config = {
    ele: ".carouselContainer:not(.carousel) .flexible-carousel-component",
    desktop : $(window).width() > 1024
};
const freemFormCarousel = () => {
    const carouselEle = $(".flexible-carousel-component[data-free-form=true] .slider-content");
    let preventClick = carouselEle.find(".card-component.link-element").length ? carouselEle.find(".card-component.link-element") : carouselEle.find("a.btn-cta-item");
    let isDown, mousemove = false;
    let startX,scrollLeft,$targetEle;
        carouselEle.on("mousedown", (e) => {
            isDown = true;
            mousemove = false;
            $targetEle = e.target;
            $($targetEle).addClass("active");
            startX = e.pageX - e.currentTarget.offsetLeft;
            scrollLeft =  e.currentTarget.scrollLeft;
        });
        carouselEle.on("mouseleave", (e) => {
            $targetEle = e.target;
            e.preventDefault();
            isDown = false;
            $($targetEle).removeClass("active");
        });
        carouselEle.on("mouseup", (e) => {
            $targetEle = e.target;
            e.preventDefault();
            isDown = false;
            $($targetEle).removeClass("active");
        });
        carouselEle.on("mousemove", (e) => {
           $targetEle = e.target;
            if (!isDown) return;
            mousemove = true;
            e.preventDefault();
            const x = e.pageX -  e.currentTarget.offsetLeft;
            const drag = x - startX;
            e.currentTarget.scrollLeft = scrollLeft - drag;
        });
        preventClick.on("click", (e) => {
            if(mousemove){
                e.preventDefault();
                e.stopImmediatePropagation();
            }
        });
}
const freeFormCarouselTxtWidth = () => {
    let $elem = $(config.ele).find(".slider-content li");
    _.each($elem, function(item, idx) {
        let imageWidth = $(item).closest($elem).find(".image-container > div:not('.hide') img").width();
        $(item).closest($elem).find(".text-container").css("width",imageWidth);
		$(item).attr('data-position', idx);
    });
}
const applySlider = () => {
    let $elem = $(config.ele);
    let dataSets,
        $curEle;
    if (!$elem.length) {
        console.log("Slider class name not found..");
        return;
    }
    _.each($elem, function(item) {
        dataSets = {
            "freeFormMob": $(item).data("freeFormMob"),
            "freeFormTab": $(item).data("freeFormTab"),
            "infinite": $(item).data("infinite"),
            "centerMode": $(item).data("centerMode"),
            "autoPlay": $(item).data("autoPlay"),
            "autoWidth": $(item).data("autoWidth"),
            "arrows": $(item).data("arrows"),
            "dots": $(item).data("dots"),
            "speed": $(item).data("speed"),
            "slideToShow": $(item).data("slideToShow"),
            "slideToScroll": $(item).data("slideToScroll"),
            "rewind": $(item).data("rewind"),
            "isHighightActiveSlide": $(item).closest(".highlight-active-slide").length,
            "autoPlaySpeed": $(item).data("rotationSpeed")
        }

        if (dataSets.isHighightActiveSlide) {
            _.each($(item).find("[data-element-link]"), function(subItem) {
                if ($(subItem).data('elementLink').indexOf(location.pathname) != -1) {
                    $(subItem).closest("li").addClass("active-slide");
                }
            });
            $(".highlight-active-slide").removeClass("invisible");
        }

        $curEle = $(item).find(".slider-content");
        let isFeaturedCardExist = $(item).find(".flexible-grid-component .content");
        if (isFeaturedCardExist.length > 1) {
            _.each(isFeaturedCardExist, (item) => {
                !$(item).text().trim().length && $(item).addClass('hide');
            });
        } else if (isFeaturedCardExist.length) {
            !isFeaturedCardExist.text().trim().length && isFeaturedCardExist.addClass('hide');
        }
        if ($(item).parents(".fullbleed").length) {
            $curEle.on('init', function(event, slick) {
                checkImageHasCropAttr($curEle, (status) => {
                    const playBtnEle = `<span class="play-pause-button" data-action="${dataSets.autoPlay ? 'pause' : 'play'}"><button type="button" role="button"></button></span>`;
                    if (!dataSets.dots) {
                        slick.$dots = $(event.currentTarget).append(`<ul class="slick-dots" role="tablist"></ul>`);
                    }

                    slick.$dots.wrap(`<div class="wrap-dots-section"></div>`);
                    $(item).find(".wrap-dots-section").prepend(playBtnEle);
                });
            });
            carouselAxInit();
        }
        var $activeSlide = $curEle.find(".active-slide");
        if ($activeSlide.length) {
            dataSets.initialSlide = $activeSlide.index();
        }
        sliderInit($curEle, dataSets || {});
        if (dataSets.slideToShow == 1) {
            //console.log("Single Stretched SLider..");
            if ($curEle.find(".slick-list").height() == Math.max.apply(null, $curEle.find(".slick-slide .thumbnail-image img,.slick-slide .inner-container,.slick-slide .product-image,.slick-slide .image img").map(function() {
                    return $(this).height();
                }).get())) {
                console.log("....");
                ($curEle.closest(".jumbotron-banner-component").length ? $curEle.closest(".jumbotron-banner-component") : $curEle.closest(".carouselContainer")).addClass("inner-dots");
            }
        }
    });
    if($elem.parents(".carouselContainer").hasClass("promoPencilHeader")){
        $("body").addClass("promo-pencil-on");
    }
};

const checkImageHasCropAttr = (ele, cb) => {
    const hasCropAttr = ele.find('li:not(".slick-cloned") div[data-mode="smartcrop"]');
    if (!hasCropAttr.length) {
        cb(false);
        return;
    }
    const onlyForDesktop = window.innerWidth >= 1200;
    const $imgContainer = ele.find('.plain-50-50 .image-container');
    onlyForDesktop && $imgContainer.css('height', '459px');
    waitForScene7Image(hasCropAttr, (imgEle) => {
        setTimeout(() => {
            onlyForDesktop && $imgContainer.css('height', 'auto');
            ele.slick('refresh');
            cb(true);
        }, 100);
    });

};

const imageHasCropIdentifier = img => {
    let imgIdentifierExist = true;
    let src;
    _.every(img, (data) => {
        src = data.src.replace(/(^\w+:|^)\/\//, '');
        if (src.indexOf(':') == -1) { imgIdentifierExist = false; return false; }
    });
    return imgIdentifierExist;
}

let pluginCheckCnt = 0;
const waitForScene7Image = (ele, callBack) => {
    let $imgEle;
    window.setTimeout(() => {
        $imgEle = $(ele).find('img');
        if ((ele.length == $imgEle.length) && imageHasCropIdentifier($imgEle)) {
            $imgEle.imagesLoaded().done(() => {
                callBack($imgEle);
            });
        } else {
            pluginCheckCnt++;
            if (pluginCheckCnt > 20) {
                console.log("%c Scene7 images not loaded/failed.", "color:red");
                callBack($imgEle);
                return;
            }
            waitForScene7Image(ele, callBack);
        }
    }, 500);
};

const sliderHeight = () => {
    let $elem = $(config.ele);
    let $sliderHight,
        $curEle;
    _.each($elem, function(item) {
        $curEle = $(item).find(".slider-content");
        $sliderHight = $curEle.find(".slick-list").height();
        $curEle.find(".slick-slide .inner-container").height($sliderHight);
    });
}

const playPauseToggle = ele => {
    const $this = $(ele.currentTarget);
    const actionName = $this.attr('data-action');
    const $targetEle = $this.closest(".slick-slider");
    if (actionName == "play") {
        $targetEle.slick('slickPlay');
        $this.attr('data-action', 'pause');
    } else if (actionName == "pause") {
        $targetEle.slick('slickPause');
        $this.attr('data-action', 'play');
    }
}

const carouselAxInit = () => {
    if (isAxInitialized) return;
    $(document).on('click', '.play-pause-button', playPauseToggle);
    isAxInitialized = true;
	$( "body" ).on( "mouseover mouseout click", ".flexible-carousel-component[data-auto-play=true] .slick-dots li", function(e) {
        let ele = $(this).parents(".wrap-dots-section").find(".play-pause-button");
        switch (e.type) {
          case "mouseover":
            ele.addClass("carousel-dot-hover")
            break;
          case "mouseout":
            ele.removeClass("carousel-dot-hover")
            break;
          case "click":
            ele.attr("data-action","play")
            break;
        }
    });
}

const refreshCarousel = ele => {
    const $this = $(ele.currentTarget);
    const $sliderFinder = $this.parents('.cta-interstitial').find('.slick-slider');
    $sliderFinder.slick('refresh');
}

setTimeout(() => {
    let freeFormCarouselON = $(config.ele).data("freeForm");
    let imgFinder= $(config.ele).find(".slider-content li");
    if(freeFormCarouselON){
        waitForScene7Image(imgFinder, freeFormCarouselTxtWidth);
        if(config.desktop){
            freemFormCarousel();
        }
    } else {
        applySlider();
    }
    $(".carouselContainer:not(.carousel) .link-element").each(function() {
        if ($(this).find(".ctaItem").children("a").length == 0) {
            $(this).find(".ctaItem").remove();
        }
    });
    $(document).on('click', '.cta-interstitial .modal-anchor', refreshCarousel);
    //sliderHeight();
}, 500);

let isAxInitialized = false;