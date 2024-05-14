import { sliderInit } from "../../../clientlibs/common-dependency/shared/slider-init.js"
const config = {
  ele: ".carouselContainer.carousel .flexible-carousel-component"
};

const applySlider = () => {
  let $elem = $(config.ele).not(".slider-in");
  let dataSets,
    $curEle;
  if (!$elem.length) {
    console.log("Slider class name not found..");
    return;
  }
  _.each($elem, function (item) {
    $(item).addClass("slider-in");
    dataSets = {
      "centerMode": $(item).data("centerMode"),
      "autoPlay": $(item).data("autoPlay"),
      "arrows": $(item).data("arrows"),
      "dots": $(item).data("dots"),
      "slideToShow": $(item).data("slideToShow"),
      "slideToScroll": $(item).data("slideToScroll"),
      "rewind": $(item).data("rewind"),
      "autoPlaySpeed": $(item).data("rotationSpeed")
    }
    $curEle = $(item).find(".slider-content");
    let isFeaturedCardExist = $(item).find(".flexible-grid-component .content");
    if (isFeaturedCardExist.length > 1) {
      _.each(isFeaturedCardExist, (item) => { !$(item).text().trim().length && $(item).addClass('hide'); });
    } else if (isFeaturedCardExist.length) {
      !isFeaturedCardExist.text().trim().length && isFeaturedCardExist.addClass('hide');
    }
    if (($(item).closest(".promoPencilHeader").length || dataSets.autoPlay === true) && dataSets.rewind === true) { // for promo pencil drawer
      dataSets.infinite = true;
    }
    sliderInit($curEle, dataSets || {});
    if (dataSets.slideToShow == 1) {
      //console.log("Single Stretched SLider..");
      let $imgElem = $curEle.find('li:not(".slick-cloned") .image');
      let imageLoaded = $imgElem.find('noscript').length ? $imgElem.find('noscript') : $imgElem.find('.cmp-image__image--is-loading');
      if (imageLoaded.length) {
        checkDynamicImageLoaded($curEle, (res, elem) => {
          res == true && applyHeight(elem);
        });
      } else {
        applyHeight($curEle);
      }
    }
  });
};

const applyHeight = ele => {
  if (ele.find(".slick-list").height() == Math.max.apply(null, ele.find(".slick-slide .thumbnail-image img,.slick-slide .product-image,.slick-slide .image img").map(function () {
    return $(this).height();
  }).get())) {
    console.log("....");
    (ele.closest(".jumbotron-banner-component").length ? ele.closest(".jumbotron-banner-component") : ele.closest(".carouselContainer")).addClass("inner-dots");
  }
};

const checkDynamicImageLoaded = (ele, cb) => {
  const hasDynamicImg = ele.find('li:not(".slick-cloned") .image');
  if (!hasDynamicImg.length) {
    cb(false, ele);
    return;
  }
  waitForScene7ImageToLoad(hasDynamicImg, (bool) => {
    setTimeout(() => {
      cb(bool, ele);
    }, 100);
  });

};

let dynamicImgCheckCnt = 0;
const waitForScene7ImageToLoad = (ele, callBack) => {
  let $imgEle;
  window.setTimeout(() => {
    $imgEle = $(ele).find('img:not(.cmp-image__image--is-loading)');
    if (ele.length == $imgEle.length) {
      callBack(true);
    } else {
      dynamicImgCheckCnt++;
      if (dynamicImgCheckCnt > 20) {
        console.log("%c Scene7 images not loaded/failed.", "color:red");
        callBack(false);
        return;
      }
      waitForScene7ImageToLoad(ele, callBack);
    }
  }, 500);
};

applySlider();
document.addEventListener("DOMContentLoaded", event => {
  applySlider();
});
