/**
 * heroimagebanner.js
 * Version 1.0
 * 
 */

const config = {
  heroImageBannerComponent :".card-component"
 }
class heroImageBanner {
  constructor() {
    self = this;
    this.element = config.heroImageBannerComponent;
  }
  init() {
    self.hoverImage(); 
  }
  
  hoverImage() {
    $(".card-component").each(function() {
      var heroImageContainer = $(this).find(".image-container")
      var isHeroHoverImageEnabled = heroImageContainer.data("hover-enabled");
      var isHeroCustomMobileEnabled  = heroImageContainer.data("custom-enabled");
      if(isHeroHoverImageEnabled) {
        heroImageContainer.mouseout(function() {
            $(this).find(".mainImage").removeClass("hide");
            $(this).find(".hoverImage").addClass("hide");
            $(this).find(".mobileImage").addClass("hide");
            $(this).find(".mobileHoverImage").addClass("hide");
        });
        heroImageContainer.mouseover(function() {
            $(this).find(".mainImage").addClass("hide");
            $(this).find(".hoverImage").removeClass("hide");
            $(this).find(".mobileImage").addClass("hide");
            $(this).find(".mobileHoverImage").addClass("hide");
        });
      }

      if((isHeroCustomMobileEnabled) && (window.matchMedia("(max-width: 767px)").matches)){
          $(this).find(".mainImage").addClass("hide");
          $(this).find(".hoverImage").addClass("hide");
          $(this).find(".mobileImage").removeClass("hide");
          $(this).find(".mobileHoverImage").addClass("hide");
          if(isHeroHoverImageEnabled){
                heroImageContainer.mouseover(function() {
                  $(this).find(".mainImage").addClass("hide");
                  $(this).find(".hoverImage").addClass("hide");
                  $(this).find(".mobileImage").addClass("hide");
                  $(this).find(".mobileHoverImage").removeClass("hide");
                });
                heroImageContainer.mouseout(function() {
                  $(this).find(".mainImage").addClass("hide");
                  $(this).find(".hoverImage").addClass("hide");
                  $(this).find(".mobileImage").removeClass("hide");
                  $(this).find(".mobileHoverImage").addClass("hide");
                });
          }
      }
    });
  }
}

let self;
window.global.heroImgObj = window.global.heroImgObj || new heroImageBanner();
window.global.heroImgObj.init();
