/**
 * card.js
 * Version 1.0
 * 
 */

const config = {
  cardComponent :".card-component"
 }
class card {
  constructor() {
    self = this;
    this.element = config.cardComponent;
  }
  init() {

    window.global.eventBindingInst.bindLooping({
      "click .showMoreBtn a": "showMoreClick",
    },self);  

    $(document).ready( function() {
      $(".grid-container").each(function() {
        $(this).find("li");
        setTimeout(() => {
          heightSync($(this).find("li"));
        },500)
      });
        self.hoverImage();
    });
  }

  showMoreClick(ele,evt){
    heightSync($(ele).closest(".grid-container").find("li"));
  }

  hoverImage() {
    $(".card-component").each(function() {
      var cardImageContainer = $(this).find(".image-container")
      var isHoverImageEnabled = cardImageContainer.data("hover-enabled");
      var isCustomMobileEnabledCard  = cardImageContainer.data("custom-enabled");
      var isCustomTabletEnabledCard  = cardImageContainer.data("custom-tablet-enabled");
      if(isHoverImageEnabled) {
        cardImageContainer.mouseover(function() {
            $(this).find(".mainImage").addClass("hide");
            $(this).find(".hoverImage").removeClass("hide");
            $(this).find(".mobileImage").addClass("hide");
            $(this).find(".mobileHoverImage").addClass("hide");
            $(this).find(".tabletHoverImage").addClass("hide");
            $(this).find(".tabletImage").addClass("hide");
        });
        cardImageContainer.mouseout(function() {
            $(this).find(".mainImage").removeClass("hide");
            $(this).find(".hoverImage").addClass("hide");
            $(this).find(".mobileImage").addClass("hide");
            $(this).find(".mobileHoverImage").addClass("hide");
            $(this).find(".tabletHoverImage").addClass("hide");
            $(this).find(".tabletImage").addClass("hide");
        });
      }
      if((isCustomTabletEnabledCard) && (window.matchMedia("(min-width:767px)").matches) && (window.matchMedia("(max-width: 992px)").matches)){
                $(this).find(".mainImage").addClass("hide");
                $(this).find(".tabletImage").removeClass("hide");
                $(this).find(".mobileImage").addClass("hide");
                $(this).find(".hoverImage").addClass("hide"); 
                $(this).find(".mobileHoverImage").addClass("hide");
                $(this).find(".tabletHoverImage").addClass("hide");
                if(isHoverImageEnabled){
                  cardImageContainer.mouseover(function() {
                    $(this).find(".mainImage").addClass("hide");
                    $(this).find(".mobileImage").addClass("hide");
                    $(this).find(".tabletImage").addClass("hide");
                    $(this).find(".hoverImage").addClass("hide");
                    $(this).find(".mobileHoverImage").addClass("hide");
                    $(this).find(".tabletHoverImage").removeClass("hide");
                });
                cardImageContainer.mouseout(function() {
                    $(this).find(".mainImage").addClass("hide");
                    $(this).find(".mobileImage").addClass("hide");
                    $(this).find(".hoverImage").addClass("hide");
                    $(this).find(".mobileHoverImage").addClass("hide");
                    $(this).find(".tabletHoverImage").addClass("hide");
                    $(this).find(".tabletImage").removeClass("hide");
                });
                }
            }

      if((isCustomMobileEnabledCard) && (window.matchMedia("(max-width: 767px)").matches)){
          $(this).find(".mainImage").addClass("hide");
          $(this).find(".mobileImage").removeClass("hide");
          $(this).find(".hoverImage").addClass("hide");
          $(this).find(".tabletImage").addClass("hide");
          $(this).find(".mobileHoverImage").addClass("hide");
          $(this).find(".tabletHoverImage").addClass("hide");
          if(isHoverImageEnabled){
            cardImageContainer.mouseover(function() {
              $(this).find(".mobileImage").addClass("hide");
              $(this).find(".mainImage").addClass("hide");
              $(this).find(".hoverImage").addClass("hide");
              $(this).find(".mobileHoverImage").removeClass("hide");
              $(this).find(".tabletHoverImage").addClass("hide");
              $(this).find(".tabletImage").addClass("hide");
            });
            cardImageContainer.mouseout(function() {
              $(this).find(".mainImage").addClass("hide");
              $(this).find(".hoverImage").addClass("hide");
              $(this).find(".mobileImage").removeClass("hide");
              $(this).find(".mobileHoverImage").addClass("hide");
              $(this).find(".tabletHoverImage").addClass("hide");
              $(this).find(".tabletImage").addClass("hide");
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
window.global.cardCmp = window.global.cardCmp || new card();
window.global.cardCmp.init();
