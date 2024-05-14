export const sliderInit = (ele, config) => {
  let $ele = $(ele);
  if (!$ele.length) {
      console.log("Slider Element Not found..");
      return;
  }
  if (typeof $.fn.slick != "function") {
      console.log("Slick Slider plugin not found..");
      return;
  }
  if (config.centerMode) {
      $ele.addClass("slider-in-center");
  }

  if (config.slideToShow == '1') {
      $ele.addClass("grid-column-one");
  } else {
      $ele.addClass("grid-column-extra");
  }
  if (config.autoWidth) {
      $ele.addClass("carousal-autoWidth").removeClass("grid-column-one");
  }

  let setting = {
      dots: config.dots || false,
      arrows: config.arrows || false,
      infinite: config.centerMode || config.infinite || false,
      autoplaySpeed: config.autoPlaySpeed ? config.autoPlaySpeed : (config.speed ? Math.floor(config.speed * 1000) : 300),
      slidesToShow: (config.autoWidth == true ? config.slideToScroll : config.slideToShow) || 1,
      slidesToScroll: config.slideToScroll || 1,
      centerMode: config.centerMode || false,
      adaptiveHeight: false,
      autoplay: config.autoPlay || false,
      variableWidth: config.autoWidth || false,
      easing: 'linear',
      responsive: [{
              breakpoint: 1024,
              settings: {
                  slidesToShow: config.autoWidth == true ? config.freeFormTab : config.slideToShow && config.autoWidth != true && config.slideToShow == 4 ? 2 : config.slideToShow || 1,
                  infinite: config.rewindMd || config.centerMode || config.infinite || false,
                  slidesToScroll: config.autoWidth == true ? config.freeFormTab : config.slideToScroll && config.slideToShow == 4 ? 2 : config.slideToScroll || 1,
                  dots: config.dotsMd || config.dots || false,

              }
          },
          {
              breakpoint: 480,
              settings: {
                  slidesToShow: config.autoWidth == true ? config.freeFormMob : config.slideToShow && config.slideToShow == 1 ? 1 : 2 || 1,
                  slidesToScroll: config.autoWidth == true ? config.freeFormMob : config.slideToScroll && config.slideToShow == 1 ? 1 : 2 || 1,
                  speed: 1000
              }
          }
      ]
  };
  $ele.addClass("slider-loaded").slick(setting);
  if (config.initialSlide) {
      $ele.slick('slickGoTo', config.initialSlide, true);
  }
  if (!setting.infinite && setting.autoplay) {
      $ele.on("afterChange", function(e, slick, cur) {
          if (cur === slick.$slides.length - 1) {
              slick.setOption("autoplay", false, true);
          }
      });
  }
};
