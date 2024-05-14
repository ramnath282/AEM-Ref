class imageCompViewer {
  constructor() {
    s7sdk.Util.lib.include("s7sdk.common.Container");
    s7sdk.Util.lib.include("s7sdk.image.ZoomView");
    s7sdk.Util.lib.include("s7sdk.set.MediaSet");
    s7sdk.Util.lib.include("s7sdk.set.Swatches");
	selfIV = this;
  }
  
  init(pn) {
    var mediaSet, container, zoomView, swatches, localImageSet;
    s7sdk.Util.init();
    var params = new s7sdk.ParameterManager();
	var partNo = pn || $(".product-info-wrapper:first").attr("data-partnumber");
	var imageParam = $(".product-wrapper").attr("data-fullimage") || partNo+"_Viewer";
    const initViewer = () => {
      params.push(
        "asset",
        "Mattel/" +imageParam
      );
      params.push(
        "serverurl",
        $("#scenesevenUrls").attr("data-scenesevenServerurl")
      );
      params.push(
        "contenturl",
        $("#scenesevenUrls").attr("data-scenesevenContenturl")
      );
      params.push(
        "videoserverurl",
        $("#scenesevenUrls").attr("data-scenesevenVideoserverurl")
      );
      params.push("Swatches.scrolltransition", "1.5");
      params.push("Swatches.scrollstep", "2,2");
      params.push("ZoomView.frametransition", "slide");
      if ($(window).width() > 991) {
        params.push("Swatches.tmblayout", "1,0");
        params.push("Swatches.orientation", "1");
      } else if ($(window).width() < 992) {
        params.push("Swatches.tmblayout", "0,1");
        params.push("Swatches.orientation", "0");
        if ($(window).width() < 768) {
          params.push("Swatches.align", "center,top");
        } else {
          params.push("Swatches.align", "center,bottom");
        }
      }
      container = new s7sdk.common.Container(
        "s7viewerCont",
        params,
        "s7viewer"
      );
      container.addEventListener(
        s7sdk.event.ResizeEvent.COMPONENT_RESIZE,
        containerResize,
        false
      );
      //container.addEventListener(s7sdk.event.ResizeEvent.WINDOW_RESIZE, windowResize, false);
      zoomView = new s7sdk.image.ZoomView("s7viewer", params, "myZoomView");
      mediaSet = new s7sdk.set.MediaSet(null, params, "mediaSet");
      mediaSet.addEventListener(
        s7sdk.event.AssetEvent.NOTF_SET_PARSED,
        onSetParsed,
        false
      );
      swatches = new s7sdk.set.Swatches("s7viewer", params, "mySwatches");
      swatches.addEventListener(
        s7sdk.event.AssetEvent.SWATCH_SELECTED_EVENT,
        swatchSelected,
        false
      );
      zoomView.addEventListener(
        s7sdk.event.AssetEvent.ASSET_CHANGED,
        largeImageSelected,
        false
      );
	  container.addEventListener(s7sdk.event.StatusEvent.NOTF_VIEW_READY,function(e){self.setPdpColHeight(true)},false);
      resizeViewer(container.getWidth(), container.getHeight());
	  self.setPdpColHeight(true);
    };

    const onSetParsed = e => {
      var mediasetDesc = e.s7event.asset;
      swatches.setMediaSet(mediasetDesc);
      swatches.selectSwatch(0, true);
      if (e.s7event.asset.items.length == 1) {
        $(".s7swatches")
          .hide()
          .prev()
          .css("left", 0);
        $("#s7viewer")
          .css("margin", "0 auto !important"); 
        $(".socialShareForProducts")
          .css("margin-left", "75px");         
      }
      else {
        $("#s7viewer")
          .css("margin", "");
        $(".socialShareForProducts")
          .css("margin-left", ""); 
      }
    };

    const swatchSelected = event => {
      localImageSet = event.s7event.asset.parent.items;
      zoomView.setItem(event.s7event.asset);
      $("#s7viewerCont").attr("data-selected-image", "https://s7d9.scene7.com/is/image/" + event.s7event.asset.name);
      var socialImagePath = $("#s7viewerCont").attr("data-selected-image");
      $(".social_pinterest_image").attr("src", socialImagePath);
      if($(".product-wrapper .size-selection-preference .color_category .innerCont.active").length > 0){
        $('.gt-image:first').html(socialImagePath);
      }
    };

    params.addEventListener(s7sdk.Event.SDK_READY, initViewer, false);

    const containerResize = event => {
      // resizeViewer(event.s7event.w, event.s7event.h);
      // if(pn) {
      //   $('#mySwatches').remove();
      // }
    };
    const largeImageSelected = event => {
      swatches.selectSwatch(event.s7event.frame, true);
    };
    const resizeViewer = (width, height) => {
      zoomView.resize(width, height);
      if ($(window).width() > 992) {
        swatches.resize(swatches.getWidth(), height);
      } else if ($(window).width() < 992) {
        swatches.resize(width, swatches.getHeight());
      }
    };
    $("body").on("click", ".product-wrapper .size-selection-preference .color_category .innerCont", function(){
      let imgIdx = $('img',this).attr('src'),
          counter = 1;
      imgIdx = imgIdx.substring(imgIdx.lastIndexOf('_')+1, imgIdx.length);
      $.each(localImageSet, function(idx,obx){
        let thisObx = obx.name;
        thisObx =thisObx.substring(thisObx.lastIndexOf('_')+1, thisObx.length);
          if(thisObx == imgIdx) {
            swatches.selectSwatch(idx, true);
            return false;
          }else if(isNaN(imgIdx)){
            swatches.selectSwatch(0, true);
          }
          else if(counter==localImageSet.length) {
            swatches.selectSwatch(0, true);
          }
          counter ++;
      });
    });
    params.init();

  }

}

let selfIV, s7Image = new imageCompViewer();
$(document).ready(function(){
s7Image.init();
});


class affixScroll {
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
        let productSliderHeight = $(".product-slider").parents(".col-first").outerHeight();
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
$(function() {
  const affixInit = new affixScroll();
  affixInit.init();
});