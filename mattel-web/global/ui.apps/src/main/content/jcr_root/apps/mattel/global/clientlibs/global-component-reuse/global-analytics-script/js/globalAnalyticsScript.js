/* list component analytics - start */
function showMoreLessClick(_this){
	var containerTitle = $(_this).prev().find('.main-title').text()
    var showMoreActive = $(_this).find('.showMoreBtn a').hasClass('show-more-active');
    if(showMoreActive){
        listAnalytics(containerTitle, "show more");
    }else{
        listAnalytics(containerTitle, "show less");
    }
}

$("body").on("click", ".list-wrapper .list-content .list-inner a", function() {
    var trackList = $(this).attr("data-track-listing").split("|");
    var itmclicked ='';
    if(trackList.length >=1){
        if(trackList[1].includes(",")) {
        var itemClickedList = trackList[1].split(",");
            itmclicked = itemClickedList.length >= 1 ? itemClickedList[0] : '';
        }
        else {
             itmclicked = trackList[1]
        }
    }
    var itemSubCategory = trackList[0];
    var listObject = $(this).closest('.list');
     if (listObject != undefined) {
         listTracking(itmclicked,itemSubCategory);
     }
});

function listTracking(itmclicked,subCategory){
  if (typeof digitalData != "undefined") {
	  digitalData.eventInfo = {
		   event_name: "listing module",
		   event_type: "click",
		   item_clicked: itmclicked,
		   item_subcategory: subCategory,
		   location_name: itmclicked
	  };
	  _satellite.track("event");
  }
}

$("body").on("click", ".list-wrapper .viewAllBtn .cta-item", function() {
	 var listObject = $(this).closest('.list');
	 if (listObject != undefined) {
	  listAnalytics($(listObject).find('.main-title').text(), "view all");
	 }
	})

function listAnalytics(itemClicked, itemSubcategory){
  if (typeof digitalData != "undefined") {
	  digitalData.eventInfo = {
		   event_name: "listing module click",
		   event_type: "click",
		   item_clicked: itemClicked,
		   item_subcategory: itemSubcategory,
		   location_name: "listing module"
	  };
	  _satellite.track("event");
  }
}
/* list component analytics - end */
/* card component analytics - start */

function cardComponentClick(_this,eventName){
	var exclusionSelectorList = [".mq-thanks-txt-msg"];
	var notExcluded = true;
    var itemClicked = $(_this).find('.text-container .main-title').text();
    var itemSubCategory = $(_this).find('.ctaItem .btn-cta-item').first().text();
    var trackingID = $(_this).find('.btn-first-cta-margin .btn-cta-item').attr('data-price-spider') || $(_this).find('.btn-first-cta-margin .btn-cta-item').attr('data-tracking-cta-id');
    if(itemClicked == "" || itemClicked == undefined){
        itemClicked = "no-value";
    }
    if(itemSubCategory == "" || itemSubCategory == undefined){
        itemSubCategory = "no-value";
    }

    exclusionSelectorList.forEach(function(value, idx) {
    	if ($(_this).parents(value).length > 0) {
    		notExcluded = false;
    	}
    });

    if (typeof digitalData != "undefined" && notExcluded) {
        digitalData.eventInfo = {
            event_name: eventName,
            event_type: "click",
            item_clicked: trackingID == '' || trackingID ==  undefined ? itemClicked.trim() : trackingID.trim(),
            item_subcategory: itemSubCategory.trim(),
            location_name: trackingID == '' || trackingID ==  undefined ? eventName : trackingID.trim(),
        };
        _satellite.track("event");
    }
}

function cardComponentCTAClick(ctaClicked,_this,eventName, itemSubCategory){
    var exclusionSelectorList = [".mq-thanks-txt-msg"];
    var notExcluded = true;
    var itemClicked = $(_this).find('.text-container .main-title').text();
    var itemSubCategory = itemSubCategory;
    var trackingID = $(ctaClicked).attr('data-price-spider') || $(_this).find('.btn-first-cta-margin .btn-cta-item').attr('data-tracking-cta-id');
    if(itemClicked == "" || itemClicked == undefined){
        itemClicked = "no-value";
    }
    if(itemSubCategory == "" || itemSubCategory == undefined){
        itemSubCategory = "no-value";
    }

    exclusionSelectorList.forEach(function(value, idx) {
        if ($(_this).parents(value).length > 0) {
            notExcluded = false;
        }
    });

    if (typeof digitalData != "undefined" && notExcluded) {
        digitalData.eventInfo = {
            event_name: eventName,
            event_type: "click",
            item_clicked: trackingID == '' || trackingID ==  undefined ? itemClicked.trim() : trackingID.trim(),
            item_subcategory: itemSubCategory.trim(),
            location_name: trackingID == '' || trackingID ==  undefined ? eventName : trackingID.trim(),
        };
        _satellite.track("event");
    }
}


$("body").on("click", ".card .link-element", function(e) {
    if(!$(e.target).hasClass('btn-cta-item') && $(this).parents(".carouselContainer").length == 0 ) {
    var cardWrapper = $(this);
    cardComponentClick(cardWrapper,"Featured Card");
    }
});

$("body").on("click", ".card-component .ctaItem .btn-cta-item", function() {
    var itemCategory=$(this).text();
    if($(this).parents(".carouselContainer").length==0){
    var cardWrapper = $(this).closest('.card-component');
               cardComponentCTAClick($(this),cardWrapper,"CTA", itemCategory);
    }
});
/* card component analytics - end */

/* retail item component analytics - start */
$(".cta-item .cta_useInterstitial_tracking").click(function() {
  digitalData.eventInfo = {
   event_name: "interstitial model click",
   event_type: "click",
   item_clicked: $(this).attr("data-price-spider") || $(this).attr("data-tracking-cta-id"),
   item_subcategory: $(this).text().trim(),
   location_name: "interstitial section"
  };
  _satellite.track("event");
});
$("body").on("click", ".interstitial-modal .retailer-list .btn-app-retail-item", function() {
	interstitialButtonClick("interstitial retailer section","Retailer Section", this);
});

$("body").on("click", ".interstitial-modal .app-list .btn-app-retail-item", function() {
	interstitialButtonClick("interstitial mobile app","mobile app section", this);
});

function interstitialButtonClick(iterstitialType, itemclicked, _this){
	var dataTrackingId = "";

	if(typeof  $(_this).attr('data-tracking-cta-id') != 'undefined'){
        dataTrackingId = $(_this).attr('data-tracking-cta-id');
    }
	if (typeof digitalData != "undefined") {
		  digitalData.eventInfo = {
			  event_name:"interstitial section",
			  event_type:"click",
			  item_clicked:itemclicked,
			  item_subcategory:dataTrackingId,
			  location_name:iterstitialType
		  };
		  _satellite.track("event");
	}
}

$("body").on("click", ".interstitial-modal .modal-header button.close", function() {
	var interstetialType = $(this).closest('.interstitial-modal').attr('id');
	if(interstetialType == "interstitialModal"){
		interstitialCloseEvent("interstitial Leaving the Site","interstitial Leaving the Site");
	}else if(interstetialType == "interstitialRetailerModal"){
		interstitialCloseEvent("retailer section","interstitial retailer section");
	}else if(interstetialType == "interstitialGameRetailerModal"){
		interstitialCloseEvent("mobile app section","interstitial mobile app");
	}
});

function interstitialCloseEvent(iterstitialType,locType){
	if (typeof digitalData != "undefined") {
		  digitalData.eventInfo = {
			event_name: "interstitial section",
			event_type: "click",
			item_clicked: iterstitialType,
			item_subcategory: "model close",
			location_name: locType
		  };
		  _satellite.track("event");
	}
}

$("body").on("click", ".interstitial-modal .modal-body .continue-btn.btn-cta-item", function() {
	var interstetialType = $(this).closest('.interstitial-modal').attr('id');
	if(interstetialType == "interstitialModal"){
		leavingInterstitialCtaClick("continue","interstitial leaving the site", "interstitial leaving the site");
	}else if(interstetialType == "interstitialRetailerModal"){
		leavingInterstitialCtaClick("continue", "retailer section","interstitial retailer section");
	}else if(interstetialType == "interstitialGameRetailerModal"){
		leavingInterstitialCtaClick("continue", "mobile app section","interstitial mobile app");
	}
});

$("body").on("click", ".interstitial-modal .modal-body .go-back-btn.btn-cta-item", function() {
	var interstetialType = $(this).closest('.interstitial-modal').attr('id');
	if(interstetialType == "interstitialModal"){
		leavingInterstitialCtaClick("cancel","interstitial leaving the site", "interstitial leaving the site");
	}else if(interstetialType == "interstitialRetailerModal"){
		leavingInterstitialCtaClick("go back", "retailer section","interstitial retailer section");
	}else if(interstetialType == "interstitialGameRetailerModal"){
		leavingInterstitialCtaClick("cancel", "mobile app section","interstitial mobile app");
	}
});

function leavingInterstitialCtaClick(subcategory, itemClicked,locType){
	if (typeof digitalData != "undefined") {
	  digitalData.eventInfo = {
	   event_name: "interstitial section",
	   event_type: "click",
	   item_clicked: itemClicked,
	   item_subcategory: subcategory,
	   location_name: locType
	  };
	  _satellite.track("event");
	}
}
/* retail item component analytics - end */


/* grid component analytics - start */


$(".gridItemContainer .btn-cta-item").click(function() {
 if (typeof digitalData != "undefined") {
  digitalData.eventInfo = {
   event_name: "grid click",
   event_type: "click",
   item_clicked: $(this).attr("data-price-spider") || $(this).attr("data-tracking-cta-id"),
   item_subcategory: $(this).text(),
   location_name: "Grid Container"
  };
  _satellite.track("event");
 }
});

$(".grid-container .show-more-active").click(function() {
 if (typeof digitalData != "undefined") {
  digitalData.eventInfo = {
   event_name: "grid click",
   event_type: "click",
   item_clicked: $(this).attr("data-tracking-cta-id"),
   item_subcategory: $(this).text(),
   location_name: "Grid Container"
  };
  _satellite.track("event");
 }
});

$(".grid-container .show-less-active").click(function() {
 if (typeof digitalData != "undefined") {
  digitalData.eventInfo = {

   event_name: "grid click",
   event_type: "click",
   item_clicked: $(this).attr("data-tracking-cta-id"),
   item_subcategory: $(this).text(),
   location_name: "Grid Container"
  };
  _satellite.track("event");
 }
});

$("body").on("click", ".accordion .accordion-grid .arrowUp", function() {
 if (typeof digitalData != "undefined") {
  digitalData.eventInfo = {
   event_name: "accordion section",
   event_type: "click",
   item_clicked: $(this).parents('.accordion-title').contents().not($('a')).text().trim(),
   item_subcategory: "collapse",
   location_name: "Accordion Container"
  };
  _satellite.track("event");
 }
});

$("body").on("click", ".accordion .accordion-grid .arrowDown", function() {
 if (typeof digitalData != "undefined") {
  digitalData.eventInfo = {
   event_name: "accordion section",
   event_type: "click",
   item_clicked: $(this).parents('.accordion-title').contents().not($('a')).text().trim(),
   item_subcategory:"expand",
   location_name: "Accordion Container"
  };
  _satellite.track("event");
 }
});

/*  Text Overlay Component - Start */
$("body").on("click", ".textOverlayComponent .cta-button-container .btn-cta-item", function() {
	if($(this).parents(".carouselContainer").length){
        var itmClicked = $(this).data('trackingCtaId');
        var itemSubCategory = $(this).text().trim() || '';
		var currentIndex = $(this).closest('.slick-active').index();
		promocarouselTracking(itmClicked,currentIndex,itemSubCategory);
	}
	else {
		var textOverlayComponent = "";
		var dataTrackingId = "";
		if(typeof $(this).closest('.textOverlayComponent').find('.main-title') != 'undefined'){
			textOverlayComponent = $(this).closest('.textOverlayComponent').find('.main-title').text().trim();
		}
		dataTrackingId=$(this).text();
		if(typeof dataTrackingId != 'undefined'){
			dataTrackingId=dataTrackingId.trim();
		}
		textOverlayCtaClick(textOverlayComponent,dataTrackingId);
	}
});

function textOverlayCtaClick(textOverlayComponent, dataTrackingId){
	if (typeof digitalData != "undefined") {
		digitalData.eventInfo = {
			event_name: "text overlay",
			event_type: "click",
			item_clicked: textOverlayComponent,
			item_subcategory: dataTrackingId,
			location_name: "text overlay container"
		};
		_satellite.track("event");
	}
}
/* Text Overlay Component - End */

/* Entire Component clickable tracking - Start */
$("body").on("click", ".link-element", function(e) {
	if((!$(e.target).hasClass('btn-cta-item')) && $(this).parents(".carouselContainer").length && !$(this).hasClass('card-component')){
		var itmClicked = $(this).find(".main-title").text().trim();
		var itemSubcategory = '';
		var currentIndex = $(this).closest('.slick-active').index()
		promocarouselTracking(itmClicked,currentIndex,itemSubcategory);
	}
});
/* Entire Component clickable tracking - End */


/* Article - Carousel container tracking - Start*/
$("body").on("click", ".carouselContainer .slick-slide .card-component", function(e) {
	var carouseltitle = $(this).parents('.carouselContainer').find('.carousel-text-container > .main-title').text().trim();
	var cardtitle = $(this).find('.text-container .main-title').text().trim();
	if (typeof digitalData != "undefined") {
		digitalData.eventInfo = {
			event_name: "Card",
			event_type: "click",
			item_clicked: carouseltitle,
			item_subcategory: cardtitle,
			location_name: carouseltitle,
			container_type: "Carousel Container",
			component_name: carouseltitle
		};
		_satellite.track("event");
	}
});
/* Article - Carousel container tracking - End*/

/* Carousel component analytics - start */
$("body").on("click", ".carouselContainer .slider-content .slick-prev", function() {
	carouselArrowClickEvent("Left");
});

$("body").on("click", ".carouselContainer .slider-content .slick-next", function() {
	carouselArrowClickEvent("Right");
});

function carouselArrowClickEvent(arrowClicked){
	if (typeof digitalData != "undefined") {
		digitalData.eventInfo = {
			event_name: "Navigating-Arrows",
			event_type: "click",
			item_clicked: arrowClicked,
			item_subcategory: "",
			location_name: "Promo Carousel"
		};
		_satellite.track("event");
	}
}

function promocarouselTracking(itmClicked,currentIndex,itemSubcategory){
	if (typeof digitalData != "undefined") {
            digitalData.eventInfo = {
                event_name: "Promo Carousel",
                event_type: "click",
                item_clicked: itmClicked.trim(),
                item_subcategory: itemSubcategory.trim(),
                location_name: "P"+currentIndex+":"+itmClicked.trim()
            };
            _satellite.track("event");
	}
}

$("body").on("click", ".carouselContainer .slider-content .slick-dots button", function() {
	var clickedDotId = $(this).text();
	if (typeof digitalData != "undefined") {
		digitalData.eventInfo = {
			event_name: "Navigating-Dots",
			event_type: "click",
			item_clicked: "Promo Carousel",
			item_subcategory: "Pagination Dots:"+clickedDotId,
			location_name: "Promo Carousel"
		};
		_satellite.track("event");
	}
});


/* Carousel component analytics - end */

/* CTA Container Component - Start */

$(".ctaContainer .cta_button_tracking").click(function() {
 if ((typeof digitalData != "undefined") && ($(this).parent().hasClass("cta-item")) ) {
  digitalData.eventInfo = {
   event_name: "CTA Container click",
   event_type: "click",
   item_clicked: $(this).attr("data-price-spider") || $(this).attr("data-tracking-cta-id"),
   item_subcategory: $(this).text(),
   location_name: "CTA Container"
  };
  _satellite.track("event");
 }
});
/* CTA Container Component - End */

/* Article grid analytics - start */
function articleAnalytics(_this){
  if (typeof digitalData != "undefined") {
      var containerTitle=$(".most-recent-article").text().trim();
       var articleTitle=$(_this).parents(".articlecomponent-info").find(".articlecomponent-headline h3").text().trim();
       var articleCategory=$(_this).parents(".articlecomponent-info").find(".articlecomponent-category").text().trim();

      if(containerTitle == "" || containerTitle == undefined){
        containerTitle = "no-value";
      }
      if(articleTitle == "" || articleTitle == undefined){
        articleTitle = "no-value";
      }


   digitalData.eventInfo = {
		event_name: "recommended articles click",
		event_type: "click",
		item_clicked: containerTitle,
		item_subcategory: articleTitle,
		location_name: containerTitle,
		article_category: articleCategory,
		container_type: "",
		component_name: containerTitle
 	};
  _satellite.track("recommendedArticles");
 }
}

$("body").on("click", ".articlecomponent-container .total-content", function() {
articleAnalytics(this);

});
$("body").on("click", ".articlecomponent-container .cta-item .btn-cta-item", function() {
  if (typeof digitalData != "undefined") {
      var containerTitle=$(".most-recent-article").text().trim();
       var ctaTitle=$(this).text().trim();
      if(containerTitle == "" || containerTitle == undefined){
        containerTitle = "no-value";
      }

   digitalData.eventInfo = {
		event_name: "link click",
		event_type: "click",
		item_clicked: containerTitle,
		item_subcategory: ctaTitle,
		location_name: "link",
		container_type: "CTA",
		component_name: ctaTitle
 	};
  _satellite.track("event");
 }
});

/* Article grid analytics - end */

/* Anchor Navigation - start */
$("body").on("click", "#navbar > ul > li > a", function() {
    if (typeof digitalData != "undefined") {
        digitalData.eventInfo = {
            event_name: "header-click",
            event_type: "click",
            item_clicked: $(this).attr('data-tracking-anchor-id') || $(this).text().trim(),
            item_subcategory: "",
            location_name: "Navigation Header"
        };
        _satellite.track("event");
    }
});
/* Anchor Navigation - end */

$("body").on("click", ".listingComponent .listingComponent-cta .learn-more", function() {
    if (typeof digitalData != "undefined") {
        digitalData.eventInfo = {
            event_name: "link-click",
            event_type: "click",
            item_clicked: $(this).closest(".listingComponent-info").find(".listingComponent-headline").text().trim(),
            item_subcategory: "see more",
            location_name: "link"
        };
        _satellite.track("event");
    }
});


$("body").on("click", ".listingComponent .view-all-btn", function() {
    if (typeof digitalData != "undefined") {
        digitalData.eventInfo = {
            event_name: "link-click",
            event_type: "click",
            item_clicked: "news - articles",
            item_subcategory: "see more",
            location_name: "link"
        };
        _satellite.track("event");
    }
});

$("body").on("click", "a", function() {
    if ($(this).attr("data-tracking-id")) {
       var trackingVal = $(this).data("trackingId");
       var valArr = trackingVal.split('|');
       var obj = {
           event_name: valArr[0] || '',
           event_type: 'click',
           item_clicked: valArr[1] || '',
           item_subcategory: valArr[2] || '',
           location_name: valArr[3] || '',
           video_discovery: valArr[4] || ''
       }
       var camelCaseName = camelize(valArr[0]);
       var evtName = camelCaseName.replace(/-/g, "");
       sendToAnalytics(obj,evtName);
    }
});

function sendToAnalytics(params, evtName){
  digitalData.eventInfo= params;
  _satellite.track("event");
}

function camelize(str) {
    return str.replace(/(?:^\w|[A-Z]|\b\w|\s+)/g, function(match, index) {
        if (+match === 0) return ""; // or if (/\s+/.test(match)) for white spaces
        return index == 0 ? match.toLowerCase() : match.toUpperCase();
    });
}

/* Hero Banner analytics - start*/
$("body").on("click", ".hero-image-banner .ctaItem", function() {
	heroBannerAnalytics(this);
});

function heroBannerAnalytics(_this){
	var sectionTitle = $(_this).parents('.hero-container').find('.textOverlayComponent .main-title').text().trim();
    var sectionSubTitle = $(_this).parents('.hero-container').find('.textOverlayComponent .sub-title').text().trim();
	var buttonName = $(_this).find('.cta-item a').text().trim();
	if (typeof digitalData != "undefined") {
	  digitalData.eventInfo = {
		   event_name: "hero image banner click",
		   event_type: "click",
           item_clicked: sectionTitle + ':' + sectionSubTitle,
		   item_subcategory: buttonName,
		   location_name: "hero image banner"
	  };
	  _satellite.track("event");
	}
}

$("body").on("click", ".hero-image-banner .image-container", function() {
	bannerImageClick(this);
});

function bannerImageClick(_this){
	var sectionTitle = $(_this).parents('.hero-container').find('.textOverlayComponent .main-title').text().trim();
    var sectionSubTitle = $(_this).parents('.hero-container').find('.textOverlayComponent .sub-title').text().trim();
	var imageName = $(_this).parents('.card-component').data('tracking-text').trim();
	if (typeof digitalData != "undefined") {
	  digitalData.eventInfo = {
		   event_name: "hero image banner click",
		   event_type: "click",
		   item_clicked: sectionTitle + ':' + sectionSubTitle,
		   item_subcategory: imageName,
		   location_name: "hero image banner"
	  };
	  _satellite.track("event");
	}
}
/* Hero Banner analytics - end*/

function getContainerType ($this) {
    var containerType = "page";
    if (($this).parents(".modal-component").length > 0){
        containerType = "modal";
    }
    else if (($this).parents(".slidein").length > 0){
         containerType = "slider";
     }
    
     return containerType;
}

function getButtonContainerType ($this) {
    var containerType = "CTA";
    if (($this).parents(".card-component").length > 0){
        containerType = "cards";
    }
    
     return containerType;
}


function checkRecommendationType($this) {
    var selectedAnswers = [];
    var recommendationType = "recommended products";

    try {
        ($this).parents(".quizContainer").find(".quizQuestion").each((idz, ques) => {
            $('ul li', ques).each((ansIdx, ansObj) => {
                if ($(ansObj).find('[type="' + $(ansObj).parents('section').data('input-type') + '"]').is(':checked')) {
                    var txt = $(ansObj).children('label').text().trim();

                    if (txt != "") {
                        selectedAnswers.push(txt);
                    }
                }
            });
        });

        if (selectedAnswers.length == 0) {
            recommendationType = "popular products";
        }
    } catch (err) {
        console.error(err);
    }

    return recommendationType;
}

/* Mini-Quiz analytics - Start */
$(".mqbtnnext").click(function() {
    // Analytics for next and submit buttons
    if (typeof digitalData != "undefined") {
        var parentSection = $(this).parents("section");
        var questionCount = $(this).parents(".mq-wrapper").find('.quizQuestion').length;
        var currentQuestionCount = parentSection.data('screen-index');
        var defaultQuizName = "miniquiz";
        var bannerElement = parentSection.find('.mq-banner-copy');
        var questionText = parentSection.find('.mq-question-counter').attr('data-question-text');
        var answer = "response not selected";
        var selectedAnswers = [];

        if (bannerElement.length > 0 && bannerElement.text().trim() != "") {
            defaultQuizName = bannerElement.text().trim();
        }

        if (!questionText) {
            questionText = parentSection.find('.mq-question-info').text().replace(parentSection.find('.mq-question-counter').text(), '').trim();
        }

        questionText = questionText.replace( /(<([^>]+)>)/ig, '');
        
        $('ul li', $(this).parents(".quizQuestion")).each((ansIdx, ansObj) => {
            if ($(ansObj).find('[type="' + $(ansObj).parents('section').data('input-type') + '"]').is(':checked')) {
                var txt = $(ansObj).children('label').text().trim();

                if (txt != "") {
                    selectedAnswers.push(txt);
                }
            }
        });

        if (selectedAnswers.length > 0) {
            answer = selectedAnswers.join(", ");
        }

        var eventName = currentQuestionCount < questionCount ? "quiz-next" : "quiz-submit";

        digitalData.eventInfo = {
            event_name: eventName,
            event_type: "click",
            quiz_detail: defaultQuizName + '_q' + currentQuestionCount,
            quiz_question: questionText.trim(),
            quiz_answer: answer,
            component_name: "mini quiz",
            container_type: getContainerType($(this))
        };
        _satellite.track("quizFlow");
    }
});

function addAnalyticsToMiniQuizRecommendations() {
	$(".mq-recomm-block").find("a.data-track-miniquiz").click(function() {
		if (typeof digitalData != "undefined") {
			var $this= $(this);
			var itemCategory = $this.attr("data-prod-category") || "";
			var productName = $this.attr("data-prod-name") || $this.children("img").attr("alt");
			var sections = $this.parents(".quizContainer").find("section") || [];
			var quizName = sections.length > 0 ? $(sections[0]).find('.mq-banner-copy').text() : "miniquiz";
			var partNumber = $this.attr("data-prod-sku") || "";
			var prodPrice = $this.attr("data-prod-price") || "";
			var sectionTitle = checkRecommendationType($this);
			
	        digitalData.eventInfo = {
	                event_name: "recommended products click",
	                event_type: "click",
	                item_clicked: sectionTitle,
	                item_subcategory : productName,
	                location_name: "recommended products",
	                quiz_name : quizName,
	                product_info: itemCategory + "|" + partNumber,
	                component_name: "recommended products",
	                container_type: getContainerType($this),
	                product_sku:partNumber.toLowerCase(),
                    Product_price:prodPrice.trim()
	            };
	        _satellite.track("recommendedProductsFP");
		}
	});
}

$(".mq-recomm-cta").find("a").click(function() {
    if (typeof digitalData != "undefined") {
    	 var ctaButton = $(this);
    	 var ctaName = ctaButton.text().trim();
    	 var sectionTitle = checkRecommendationType($(this));
  	 
    	 digitalData.eventInfo = {
    			 event_name: "CTA",
    			 event_type: "click",
    			 item_clicked: sectionTitle,
    			 item_subcategory : ctaName,
    			 location_name: "recommended products",
    			 component_name: "CTA",
    			 container_type: getContainerType($(this))
    	 };
    	 _satellite.track("quizCTA");
    }
});

$(".mq-thanks-txt-msg").find("a").click(function() {
	if (typeof digitalData != "undefined") {
   	 	var ctaButton = $(this);
   	 	var ctaName = ctaButton.text().trim();
   	 	var sectionTitle = "thankyou";
   	 	
   	 	try {
   	 		sectionTitle = ctaButton.parents(".mq-thanks-txt-msg").find(".main-title").text().trim();
   	 	} catch (err) {
   	 		sectionTitle = "thankyou";
   	 		console.error(err);
   	 	}

	   	 digitalData.eventInfo = {
				 event_name: getButtonContainerType($(this)),
				 event_type: "click",
				 item_clicked: "quiz " + sectionTitle,
				 item_subcategory : ctaName,
				 location_name: "quiz thankyou",
				 component_name: "CTA",
				 container_type: getContainerType($(this))
		 };
		 _satellite.track("event");
	}
});
/* Mini-Quiz analytics - End */



/* video YouTube analytics starts */
var playerYouTube;
function onYouTubeIframeAPIReady() {
  playerYouTube = new YT.Player('youtube_player', {        
    events: {			
      'onStateChange': onPlayerStateChange
    }
  });
}	  
function onPlayerStateChange(event) {
	 var playerState = playerYouTube.getPlayerState();
	 if(playerState == 1 || playerState == 2){
	   digitalData.eventInfo.event_name = "Video Clicks"
       digitalData.eventInfo.event_type = "click"
       digitalData.eventInfo.item_clicked = playerYouTube.getVideoData().title
       digitalData.eventInfo.item_subcategory = playerState == 1 ? "play":"pause"
       digitalData.eventInfo.location_name = "video player"
       digitalData.eventInfo.video_discovery = "video player"
       digitalData.eventInfo.video_length = playerYouTube.getDuration()+"s"
       digitalData.eventInfo.video_id = "YouTube-"+ playerYouTube.getVideoData()['video_id']
       _satellite.track("event");
	 }
}

/* video YouTube analytics ends */