/**
 * asset.js
 * Version 1.0
 *
 */

const config = {
  assetComponent :".asset-component"
 }
class asset {
  constructor() {
    self = this;
    this.element = config.assetComponent;
  }
  init() {

    window.global.eventBindingInst.bindLooping({
      "click .showMoreBtn a": "showMoreClick",
    },self);

    $(document).ready( function() {
      self.hoverImage();
      $(".asset-listing-container").each(function() {
        $(this).find("li");
        setTimeout(() => {
          heightSync($(this).find("li"));
        },500)
      });
    });
  }

  showMoreClick(ele,evt){
    heightSync($(ele).closest(".asset-listing-container").find("li"));
  }

  hoverImage() {
    $(".asset-component").each(function() {
      var imageContainer = $(this).find(".image-container")
      var isHoverImageEnabled = imageContainer.data("hover-enabled");
      var isCustomMobileEnabled  = imageContainer.data("custom-enabled");
      var isCustomTabletEnabled  = imageContainer.data("custom-tablet-enabled");
      if(isHoverImageEnabled) {
        imageContainer.mouseover(function() {
            $(this).find(".mainImage").addClass("hide");
            $(this).find(".hoverImage").removeClass("hide");
            $(this).find(".mobileImage").addClass("hide");
            $(this).find(".mobileHoverImage").addClass("hide");
            $(this).find(".tabletHoverImage").addClass("hide");
            $(this).find(".tabletImage").addClass("hide");
        });
        imageContainer.mouseout(function() {
            $(this).find(".mainImage").removeClass("hide");
            $(this).find(".hoverImage").addClass("hide");
            $(this).find(".mobileImage").addClass("hide");
            $(this).find(".mobileHoverImage").addClass("hide");
            $(this).find(".tabletHoverImage").addClass("hide");
            $(this).find(".tabletImage").addClass("hide");
        });
      }

      if((isCustomMobileEnabled) && (window.matchMedia("(max-width: 767px)").matches)){
          $(this).find(".mainImage").addClass("hide");
          $(this).find(".hoverImage").addClass("hide");
          $(this).find(".mobileImage").removeClass("hide");
          $(this).find(".mobileHoverImage").addClass("hide");
          $(this).find(".tabletHoverImage").addClass("hide");
          $(this).find(".tabletImage").addClass("hide");
          if(isHoverImageEnabled){
            imageContainer.mouseover(function() {
              $(this).find(".mainImage").addClass("hide");
              $(this).find(".hoverImage").addClass("hide");
              $(this).find(".mobileImage").addClass("hide");
              $(this).find(".mobileHoverImage").removeClass("hide");
              $(this).find(".tabletHoverImage").addClass("hide");
              $(this).find(".tabletImage").addClass("hide");
            });
            imageContainer.mouseout(function() {
              $(this).find(".mainImage").addClass("hide");
              $(this).find(".hoverImage").addClass("hide");
              $(this).find(".mobileImage").removeClass("hide");
              $(this).find(".mobileHoverImage").addClass("hide");
              $(this).find(".tabletHoverImage").addClass("hide");
              $(this).find(".tabletImage").addClass("hide");
            });
          }
      }

      if((isCustomTabletEnabled) && (window.matchMedia("(min-width:767px)").matches) && (window.matchMedia("(max-width: 992px)").matches)){
                $(this).find(".mainImage").addClass("hide");
                $(this).find(".hoverImage").addClass("hide");
                $(this).find(".mobileImage").addClass("hide");
                $(this).find(".tabletImage").removeClass("hide");
                $(this).find(".mobileHoverImage").addClass("hide");
                $(this).find(".tabletHoverImage").addClass("hide");
                if(isHoverImageEnabled){
                    imageContainer.mouseover(function() {
                    $(this).find(".mainImage").addClass("hide");
                    $(this).find(".hoverImage").addClass("hide");
                    $(this).find(".mobileImage").addClass("hide");
                    $(this).find(".mobileHoverImage").addClass("hide");
                    $(this).find(".tabletImage").addClass("hide");
                    $(this).find(".tabletHoverImage").removeClass("hide");
                });
                imageContainer.mouseout(function() {
                    $(this).find(".mainImage").addClass("hide");
                    $(this).find(".hoverImage").addClass("hide");
                    $(this).find(".mobileImage").addClass("hide");
                    $(this).find(".mobileHoverImage").addClass("hide");
                    $(this).find(".tabletHoverImage").addClass("hide");
                    $(this).find(".tabletImage").removeClass("hide");
                });
                }
            }


    });
  }
}
  const heightSync = (elem) => {
    var max = -1;
    var heightElem = $(elem).find(".content");
    heightElem.css('height', 'auto');
    setTimeout(function(){
          heightElem.each(function() {
            var height = $(this).innerHeight();
            max = height > max ? height : max;
          });
          $(heightElem).css('height', max + 'px');
                return;
      },800);
  }

let self;
window.global.assetCmp = window.global.assetCmp || new asset();
window.global.assetCmp.init();
