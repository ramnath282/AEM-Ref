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
  let setting = {
    dots: config.dots || false,
    arrows: config.arrows || false,
    infinite: config.centerMode || config.rewind || false,
    speed: config.speed || 300,
    slidesToShow: config.slideToShow || 1,
    slidesToScroll: config.slideToScroll || 1,
    centerMode: config.centerMode || false,
    adaptiveHeight: false,
    autoplay: config.autoPlay || false,
    autoplaySpeed: config.autoPlaySpeed || 2000,
    variableWidth: config.autoWidth || false,
    responsive: [
      {
        breakpoint: 1024,
        settings: {
          slidesToShow: config.slideToShowMd || (config.slideToShow == 1 ? 1 : 2 || 1),
          slidesToScroll: config.slideToScrollMd || config.slideToScroll || 1,
          infinite:
            config.rewindMd || config.centerMode || config.rewind || false,
          dots: config.dotsMd || config.dots || false
        }
      },
      {
        breakpoint: 600,
        settings: {
          slidesToShow:
            config.slideToShowSm || 1 ||
            (config.slideToShow > 1
              ? config.slideToShow - 1
              : config.slideToShow) ||
            1,
          slidesToScroll:
            config.slideToScrollSm || 1 ||
            (config.slideToScroll > 1
              ? config.slideToScroll - 1
              : config.slideToScroll) ||
            1
        }
      },
      {
        breakpoint: 480,
        settings: {
          slidesToShow:
            config.slideToShowXs || 1 ||
            (config.slideToShow > 2
              ? config.slideToShow - 2
              : config.slideToShow) ||
            1,
          slidesToScroll:
            config.slideToScrollXs ||
            (config.slideToScroll > 2
              ? config.slideToScroll - 2
              : config.slideToScroll) ||
            1
        }
      }
    ]
  };
  $ele.addClass("slider-loaded").slick(setting);
};
