export class affixScroll {
  constructor() {
    self = this;
    this.ele = $(
      "#floating-product-info,  #floating-bundle-info, .logo-giftCard-wrapper"
    );
  }
  calcStopAffix() {
    let headerWrapper = Math.floor($("header").outerHeight(true) || 0),
      productInfo = Math.floor(
        $(self.ele)
          .parents(".image-text-container")
          .outerHeight(true) || 0
      ),
      documentHeight = Math.floor($(document).innerHeight()),
      stopAffix = documentHeight - (headerWrapper + productInfo) + 60;

    return stopAffix;
  }
  resetStopAffix() {
    if (self.ele.data("bs.affix") != undefined) {
      self.ele.data("bs.affix").options.offset.bottom = self.calcStopAffix();
      self.ele.affix("checkPosition");
    }
  }
  initAffix() {
    let $ele = self.ele;
    if ($ele.data("affixed")) {
      self.resetStopAffix();
      return;
    }
    let stopAffix = self.calcStopAffix();
    $ele.affix({
      offset: {
        top: Math.floor($ele.position().top - 1),
        bottom: function() {
          return (this.bottom = stopAffix);
        }
      }
    });
    $ele.width($ele.parent().width());
    $ele.attr("data-affixed", "true");
  }
  mutationHandler (mutationRecords) {
		mutationRecords.forEach ( function (mutation) {
			if(mutation.type=="childList"){
				self.setPdpColHeight();
			}
		});
	}
  setPdpColHeight(showAccordion) {
	  if (window.innerWidth >= 1024) {
        $(".accordion").css("z-index", 1);
        let productSliderHeight = $(".pdp-img-viewer-holder").parents(".col-first").outerHeight();
        let accordionHeight = $(".accordion .accordion-comp").outerHeight();
        let socialShareHeight = $(".at-svc-facebook").height() ? 0 : 50;
		productSliderHeight = productSliderHeight + socialShareHeight;
        let colControl = $(".pdp-col-container").prevAll();
		let colControlHeight = 0;
        let pdpproductHeight = productSliderHeight + accordionHeight + 30;
        $(".pdpproduct,.giftcard").css("min-height", pdpproductHeight);
		for(let i = 0; i< colControl.length; i++) {
			colControlHeight += $(colControl[i]).outerHeight();
		}

        if(showAccordion) {
			$(".accordion").css({ "opacity": 1 });
		}
		if( parseInt(pdpproductHeight-140)> parseInt($(self.ele).outerHeight()+40)){
			self.resetStopAffix();
			self.initAffix();
			$(self.ele).removeClass("removeAffix");
		} else {
			$(self.ele).addClass("removeAffix");
		}
		$(".accordion").css({ top: (productSliderHeight+colControlHeight)> 500 ? productSliderHeight+colControlHeight : 500 });
		var removeAffix = $(self.ele).hasClass("affix-bottom") && ( $(".affix-bottom").offset().top <= $(".pdpproduct, .giftcard").offset().top );
	    if(removeAffix) {
	      $(self.ele).addClass("change-affix-bottom");
	    } else {
	      $(self.ele).removeClass("change-affix-bottom");
	    }

		let stickyHeaderHt = $('#header-wrapper').length ? $('#header-wrapper').outerHeight() : 0;

        if($(window).scrollTop() < ($(".pdpproduct,.giftcard").offset().top + stickyHeaderHt)) {
            $(self.ele).css("top", $(".pdpproduct,.giftcard").offset().top - $(window).scrollTop() );
        }

	      if($(window).scrollTop() == 0) {
	        $(self.ele).addClass("removeAffix");
	      }
      }
  }
  init() {
    if (window.innerWidth < 1024 || !self.ele.length) return;
    scrollTo(0, pageYOffset - 1);
    self.ele.on("affixed.bs.affix", function() {
      self.resetStopAffix();
    });

    let ele = $(self.ele);
    $(window).on("resize scroll load orientationchange", function(e) {
      self.setPdpColHeight();
      if(e.type == "resize") {
        ele.width(ele.parent().width());
      }
    });
    if(!self.mutationObjInit) {
      self.mutationObjInit = true;
      var targetNodes         = $(self.ele)[0];
      var MutationObserver    = window.MutationObserver || window.WebKitMutationObserver;
      var myObserver          = new MutationObserver (self.mutationHandler);
      var obsConfig           = { childList: true, characterData: true, attributes: true, subtree: true };
      myObserver.observe (targetNodes, obsConfig);
    }
  }
}
let self;
  $(document).ready(function(){
    const affixInit = new affixScroll();
    affixInit.init();
  });
