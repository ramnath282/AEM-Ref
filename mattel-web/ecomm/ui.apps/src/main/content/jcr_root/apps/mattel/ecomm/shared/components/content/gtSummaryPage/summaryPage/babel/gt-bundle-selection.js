import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import Constants from "../shared/constant";
import {
    dateFormat
} from '../shared/dateFormat';
import {
    getCookie
} from '../shared/browserCookie';
import {
    handleBarTemplate
} from '../shared/templateSetter';
import {
    handleBarsHelper
} from "../shared/handleBarsHelper";
class gtProduct {
    constructor() {
        self = this;
        this.element = ".gt-container>.row div.gt-bundle-selection-wrapper div.option-details";
        this.defaultDisplay = $('div.gt-bundle-selection-wrapper');
        this.categorybundleDetailsTemplate = "#categorybundleDetailsTemplate";
        this.categoryTemplateContainer = ".categoryBundleList";
        this.priceObjName = self.getActivePriceName();
        this.initialCategoryBundleList1="";
        this.initialCategoryBundleList2="";
        this.selectedElementCached="";
        this.nextIndex="";
        this.playBackElementCached="";
        this.nextPlayPackIndex="";
		this.bindingHelperFn();
		this.largeTrunkattrSku = $("#largeTrunk").attr('data-sku-id');
		this.smallTrunkattrSku = $("#smallTrunk").attr('data-sku-id');
		this.earskuId =  $('#earPiercing').attr('data-sku-id');
		this.hearingAidskuId =  $('#hearingAid').attr('data-sku-id');
		this.smalltrunkinventory = {};
		this.largetrunkinventory = {};
		this.noItemSamllTruk='';
		this.noItemLargeTruk='';
		this.dollSelectId= $('.choiceSelected .gt-product-doll-name');
		this.dates = [];
		this.ajaxFlag = 'true';
		this.smalltrunkpriceObj= {};
		this.largetrunkpriceObj= {};
		this.smalltrunkAvaiArray= [];
		this.largetrunkAvaiArray= [];
        //this.footerFlag;
		this.backorder_date = getCookie('gt-product-doll-backorder-date');
        this.includeExtrasContainer=$('.include-extrasContainer');
        this.compositeImageBaseUrl = $('#gtCompositeImageBaseUrl').val() || 'https://mattel.scene7.com/is/image/AmericanGirlBrands/GiftTrunkTest?';
        this.layerComp=$('#gtCompositeImageCompUrl').val() || '&layer=comp&fmt=jpeg&qlt=85,0&resMode=sharp2&op_usm=0.9,1.0,8,0'
        this.layer1largeTrunkUrl=$('#gtTrunkLayer0').val() || '&layer=1&src=AmericanGirlBrands/GT-LARGE-T&size=3475,1917&pos=1867,1195&hide=0';
        this.layer1smallTrunkUrl=$('#gtTrunkLayer1').val() || '&layer=1&src=AmericanGirlBrands/GT-SMALL-T&size=2691,1040&pos=2168,1525&hide=0';
        this.layer2Bundle1Url=$('#gtlayer2Bundle1Url').val() || '&layer=2&src=AmericanGirlBrands/GT-SS1-SKU_ID&size=446,583&pos=2593,1439&hide=0';
        this.layer2Bundle2Url=$('#gtlayer2Bundle2Url').val() || '&layer=3&src=AmericanGirlBrands/GT-SS2-SKU_ID&size=427,556&pos=2586,671&hide=0';
        this.layer4DollUrl=$('#gtlayer4DollUrl').val() || '&layer=4&src=AmericanGirlBrands/GT-SKU_ID&size=1146,1494&pos=1778,1212&hide=0&wid=500&scl=3&hei=550';
        this.thumbnailLink=$('#mattelImageLink').val() || 'https://mattel.scene7.com/is/image/Mattel';
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.recipient = getCookie('gt-quiz-display-recipient');
		this.dollId = getCookie('gt-product-doll-id');
		this.dollName= localStorage.getItem('gt-product-doll-name');
		this.isGTFlowBroke=false;
        this.jumpToExtras=false;
        this.adderPriceText=$('#gt-bundle-selection-wrapper').attr('data-adderPriceText') || "";
		this.deductPriceText=$('#gt-bundle-selection-wrapper').attr('data-deductPriceText') || "";
		this.doll_price = getCookie('gt-product-doll-pricing');
		/* Dynamic pricing */
			this.lowestPlayPack1=0;
			this.lowestPlayPack2=0;
			this.pricingAnimation=$('#pricingAnimation').val() || 'noAnimate';
		/* Dynamic pricing */
		this.optionClick=false;
		this.prevChangedPriceStack=[];
    }
    init() {
        if (this.element.length) {
			this.getSnPResponse();
        }
         var temp = "";
		 var msgText = $('.messageContent:first p');
		 $(msgText).each(function (index) {
				temp += $(this).text() + '\n';
		 });
		 $('#messageBox').val(temp);
		 if (window.matchMedia('(min-width: 992px)').matches)
		 {
			 $('.gt-product-doll-image, .gt-ltr-left').width($('.doll-img').width());
			 $('.gt-ltr-left').css('max-width', $('.doll-img').width() - 10 + 'px');
			 $('.gt-ltr-left').css('min-width', $('.doll-img').width() - 10 + 'px');
			 $('.gt-ltr-left').css('min-height', $('.doll-img').width() + 40 + 'px');
		 }

		 self.handleImageCompositeImage();
	     self.handleCompositeTrunkThumbnail();
    }
    bindingHelperFn() {
        handleBarsHelperInst.checkIFConditions("ifEquals");
        handleBarsHelperInst.checkIFConditions("ifNotEquals");
        handleBarsHelperInst.callRegisterHelper("getMMDDYYYY");
        handleBarsHelperInst.checkIFConditions("checkIndexExist");
    }
    bindingEventsConfig() {
        let eventsArr = {
            'click .gt-container > .row div.gt-bundle-selection-wrapper div.option-details .view-all-products': 'productViewAll',
            'click .gt-container > .row div.gt-bundle-selection-wrapper div.option-details .show-less-products': 'productViewAll',
            'click .gt-container > .row div.gt-bundle-selection-wrapper div.option div.selected .change span': 'changeOption',
            'click .gt-container > .row div.gt-bundle-selection-wrapper div.trunk div.offerSize #smallTrunk': 'smallTrunkChange',
            'click .gt-container > .row div.gt-bundle-selection-wrapper div.trunk div.offerSize #largeTrunk': 'largeTrunkChange',
            'click .gt-container > .row div.gt-bundle-selection-wrapper div.option div.selected .extras-toggle span': 'extrasToggle',
			'click .bundleList .choice': 'getVariantId',
			'click #earPiercing .choice': 'earInventoryStatus'

        };
        return eventsArr;
    }

	trunkApi(query) {
		let gruntapiOptions = jQuery.extend({},apiConfigInst.getApiConfig('giftTrunk').getGiftTrunk(query));
		return request.ajaxCall(gruntapiOptions);
	}

	getVariantId(ele){
		var $self = $(ele),
			getSku = $self.attr('data-sku-id');
			$("#addToBagbutton").addClass("inventory-disabled");
			$("#buyNowbutton").addClass("inventory-disabled");
		self.trunkApi(getSku).then(function(data) {
			const variandId = data.product.variants[0].id,
				  itematpreceiptid = data.product.variants[0].core.variant_itematpreceiptid,
				  variantbackOrderdate = data.product.variants[0].core.variant_backorderdate,
				  variantInventory = data.product.variants[0].core.variant_inventorystatus ? data.product.variants[0].core.variant_inventorystatus :"";
			let setAttr = {
				"data-variantid": variandId,
				"data-backorderdate":variantbackOrderdate,
				'data-itematpreceiptid':itematpreceiptid,
				"data-variantinventry":variantInventory,
				'data-association':"COMPONENT",
			}
			$self.parents('.bundleList').find('.choice').removeAttr('data-variantid data-itematpreceiptid data-backorderdate')
			$self.attr(setAttr);
			$self.parents('.bundleList').attr(setAttr);
			self.checkInventoryattrFlag($self)

		})
	   }

	    getDollselectId() {
			let skuid = getCookie('gt-product-doll-id');
			self.trunkApi(skuid).then(function(data) {
			let $item = data.product.variants[0];
			const setAttr =  {
				"data-variantid": $item.id,
				"data-backorderdate":$item.core.variant_backorderdate ? $item.core.variant_backorderdate : '',
				"data-product-type":data.product.core.product_type ? data.product.core.product_type : '',
				'data-itematpreceiptid':$item.core.variant_itematpreceiptid ? $item.core.variant_itematpreceiptid : '',
				'data-sku-id': data.product.partnumber,
				"data-variantinventry":$item.core.variant_inventorystatus ? $item.core.variant_inventorystatus: '',
				'data-association':"COMPONENT",
				"data-price": parseFloat($item.pricing.price).toFixed(2),
			}
			 self.dollSelectId.attr(setAttr);
			})
		}
    /* Pricing Footer - Start */
		    addToBagFun(){
		            let retailSelection = localStorage.getItem("RetailSelected");
			        if(retailSelection == "Instore"){
			        	$(".buyNow").removeClass('hide');
			            $(".addToBag").addClass('hide');
			        }
			        else{
			            $(".buyNow").addClass('hide');
			            $(".addToBag").removeClass('hide');
			        }
				}
		    calculatePrice(ele) {
		        self.addToBagFun();
		        const $ele = $(ele);
		        $ele.addClass('selectSum');
		        $ele.addClass('backOrderSel');
		        $ele.addClass('footerAvailable');
				self.sum = parseFloat($(self.defaultDisplay).parent().find(".total-price").text());

                let safMessage = localStorage.getItem('gt-product-doll-safetyMsg');
                if (safMessage == "") {

                     if($ele.parent().find('.selectSum p').hasClass('safetyMessage')){
                         $ele.addClass('safetyFlag');
                      }
		           $ele.parents('.trunk').find('.selectSum p').each(function() {
		           if($ele.parents().find('.safetyFlag p').hasClass('safetyMessage')){
		               var safetymessageText = $ele.parents('.trunk').find('.safetyFlag p.safetyMessage').html();
		                    if ($(self.defaultDisplay).parent().find(".warning").html() == ''){
		                        $(self.defaultDisplay).parent().find(".warning").prepend(safetymessageText);
		                    }
		            }else{
		                   $(self.defaultDisplay).parent().find(".warning").html("");
		             }
		           });
		         }

		        $ele.parents().find('.selectSum span.stock-price').each(function() {
		            if ($(this).text() !== "") {
		                let priceParse = $(this).text().replace(/[$,]+/g, "");
		                self.sum += parseFloat(isNaN(priceParse) ? 0 : priceParse);
		                $ele.removeClass('selectSum');
		                $ele.addClass('priceSelect');
		            }
		        });

	        /* Dynamic Pricing Change */
		        self.animate();
		        $(self.defaultDisplay).parent().find(".total-price").text(self.sum.toFixed(2));
		        let dt;
		        $ele.parents().find('.backOrderSel span.backorder-date').each(function() {
		            if ($(this).text() != "" && typeof($(this).text()) != 'undefined') {
		                dt = $(this).text();
		                self.dates.push(new Date(dt));
		                $ele.removeClass('backOrderSel');
		                $ele.addClass('backOrderSelect');
		            }
		        });
		        if (self.backorder_date != "" && typeof(self.backorder_date) != 'undefined') {
		            let back_cookie = self.backorder_date;
		            self.dates.push(new Date(back_cookie));
		        }

		        let maxDate;

		        let sorted = self.dates.sort(self.sortDates);
		        maxDate = sorted[sorted.length - 1];
		        if (maxDate != "" && typeof(maxDate) != 'undefined') {
		            $(self.defaultDisplay).parent().find(".status-date").text(formatDate.formatToNewDate(maxDate));
		        } else {
		            $(self.defaultDisplay).parent().find(".status-date").text("");
		        }
		        if (maxDate == "" || typeof(maxDate) == 'undefined') {
		            $(self.defaultDisplay).parent().find(".bundle-footer span.status-detail").hide();
		        } else {
		            $(self.defaultDisplay).parent().find(".bundle-footer span.status-detail").show();
		        }
		    }



		    sortDates(a, b){
		    	return a.getTime() - b.getTime();
			}

		    safetyMessageFunct(){

		        let $safetMsg = localStorage.getItem('gt-product-doll-safetyMsg');
		        let $safetyMessage="";
		        if(typeof $safetMsg != "undefined" && $safetMsg != null && $safetMsg != "")
		        {
		        	 $safetyMessage = $safetMsg.replace(/"/g," ");
		        }
                if ($(self.defaultDisplay).parent().find(".warning").html() == ''){
                    $(self.defaultDisplay).parent().find(".warning").prepend($safetyMessage);
                }
		    }

    /*Pricing Footer - End*/


   /** Logic for view all and show less products Starts **/
	    productViewAll(ele) {
			const $ele = $(ele);
			const $toggleProducts = $ele.closest(".toggle-products");
	        const $optiondetails = $ele.closest(".option-details");
	        let numberOfProductsToShow = parseInt($ele.parents('.option-details').find('.categoryBundleList').attr('data-intial-productcount'));
	        $toggleProducts.find(".show-less-products").toggleClass("hide");
	        $toggleProducts.find(".view-all-products").toggleClass("hide");
	        $optiondetails.find(".choice:gt(" + (numberOfProductsToShow - 1) + ")").toggleClass("hide");
	    }

	    showHideElements(ele, categoryBundleListLoadCount) {
	        let $ele = $(ele);
	        let numberOfProductsToShow = parseInt($ele.find('.categoryBundleList').attr('data-intial-productcount'));
	        if ($ele.find(".choice").length > numberOfProductsToShow) {
	            $ele.find(".show-less-products").addClass("hide");
	            $ele.find(".view-all-products").removeClass("hide");
	            $ele.find(".choice:gt(" + (categoryBundleListLoadCount - 1) + ")").addClass("hide");
	        } else {
	            $ele.find(".view-all-products").addClass("hide");
	            $ele.find(".show-less-products").removeClass("hide");
	            $ele.find(".choice:gt(" + (categoryBundleListLoadCount - 1) + ")").removeClass("hide");
	        }

	    }

		hideViewAll($ele)
		{
			let intialProductcount=parseInt($ele.find('.categoryBundleList').attr('data-intial-productcount'));
			let choiceCount=parseInt($ele.find('.choice').length);
			if( choiceCount <= intialProductcount)
				$ele.find('.toggle-products').hide();
			else
			   $ele.find('.toggle-products').show();
		}

	    setCategoryBundleProductLoad() {
	        this.categoryBundleList1LoadCount = parseInt($('#categoryBundleList1').attr('data-intial-productCount'));
	        this.categoryBundleList2LoadCount = parseInt($('#categoryBundleList2').attr('data-intial-productCount'));
	        this.categoryBundleList1 = $('#categoryBundleList1').parents('.option');
	        this.categoryBundleList2 = $('#categoryBundleList2').parents('.option');
	        self.showHideElements(this.categoryBundleList1, this.categoryBundleList1LoadCount);
	        self.showHideElements(this.categoryBundleList2, this.categoryBundleList2LoadCount);
	    }

    /** Logic for view all and show less products Ends **/

    /** Logic for supress B1 selections in B2 vice versa Starts **/
	    adjustBundleLoad($ele) {
	    	//Reset if there is previously cached item
	        if (typeof this.selectedElementCached != "undefined" && this.selectedElementCached.length > 0) {
	            let loadNextBundleList = $ele.parents('.option').next();
	            self.resetChoice(loadNextBundleList, this.selectedElementCached, this.nextIndex);
	        }
	        //Remove newly selected item
	        let selectedSkuId = $ele.attr('data-sku-id');
	        let selectedElement = $ele.parents('.option').next().find("[data-sku-id='" + selectedSkuId + "']");
	        this.nextIndex = self.getChoiceNextIndex($(selectedElement));
	        this.selectedElementCached = selectedElement.detach();

	        //Clear hide class before attach
	        this.selectedElementCached=this.selectedElementCached.removeClass('hide');
	        //Handle if intial count is less than expected
	        if (this.categoryBundleList2.find(".choice").not('.hide').length < this.categoryBundleList2LoadCount) {
	            this.categoryBundleList2.find(".choice.hide:first").removeClass('hide');
	        }
	    }

	    getChoiceNextIndex($ele) {
	        let nextElementIndex = -1;
	        if ($ele.next().length > 0)
	            nextElementIndex = $ele.next().attr('data-choice-index');
	        return nextElementIndex;

	    }

	    resetChoice($ele, cachedElement, resetIndex) {
	        if (resetIndex != -1) {
	            let targetElement = $ele.find("[data-choice-index='" + resetIndex + "']");
	            $(cachedElement).insertBefore(targetElement)
	        } else {
	            $(cachedElement).insertAfter($ele.find('.choice:last'));
	        }
	    }

	    //Supress B2 selection in B2
	    handleBundleChangeEvent($optionSelectionElement) {
	        let nextBundleListElement = $optionSelectionElement.next();
	        if (nextBundleListElement.length > 0 && nextBundleListElement.hasClass('bundleList')) {
	            let bundle2SelectedItem = nextBundleListElement.attr('data-sku-id');
	            self.adjustBundleChangeLoad($optionSelectionElement, bundle2SelectedItem);
	        }
	    }
	    adjustBundleChangeLoad($ele, bundle2SelectedItem) {
	    	//Restore Previously remove item if there
	        if (typeof this.playBackElementCached != "undefined" && this.playBackElementCached.length > 0) {
	            self.resetChoice($ele, this.playBackElementCached, this.nextPlayPackIndex)
	        }
	        //Detach newly selected item
	        let selectedElement = $ele.find("[data-sku-id='" + bundle2SelectedItem + "']");
	        this.nextPlayPackIndex = self.getChoiceNextIndex($(selectedElement));
	        this.playBackElementCached = selectedElement.detach();
	        //Clear hide class before cache
	        this.playBackElementCached =this.playBackElementCached.removeClass('hide');
	        //Check if initial load is less than expected
	        if (this.categoryBundleList1.find(".choice").not('.hide').length < this.categoryBundleList1LoadCount) {
	            this.categoryBundleList1.find(".choice.hide:first").removeClass('hide');
	        }
	    }
	    //Restore View all on change event
	    restoreViewAllToggleOnChange($ele)
	    {
	    	let toggleProductOption=$ele.find('.toggle-products');
	    	if(!toggleProductOption.hasClass('hide'))
	    	{
	    		let initialProductLoad=$ele.find('.categoryBundleList').attr('data-intial-productcount');
	    		if(typeof initialProductLoad != "undefined" && initialProductLoad !=null && initialProductLoad!=""){
	    		$ele.find(".choice").removeClass('hide');
	    		$ele.find(".choice:gt(" + (initialProductLoad - 1) + ")").addClass("hide");
	    		$ele.find(".show-less-products").addClass("hide");
	    		$ele.find(".view-all-products").removeClass("hide");
	    		}
	    	}
	    }
	    //Reset all bundle list if trunk change happens
	    resetBundleListOnTrunkChange($ele){
	    	this.selectedElementCached="";
	    	this.nextIndex="";
	    	this.playBackElementCached="";
	    	this.nextPlayPackIndex="";
	    	$('.categoryBundleList').empty();
	    	$(this.initialCategoryBundleList1).removeClass("hide");
	    	$(this.initialCategoryBundleList2).removeClass("hide");
	    	$('#categoryBundleList1').append(this.initialCategoryBundleList1);
	    	$('#categoryBundleList2').append(this.initialCategoryBundleList2);
	    	self.setCategoryBundleProductLoad();
	    	$(".gt-container > .row div.gt-bundle-selection-wrapper div.trunk div.option.bundleList div.option-details div.categoryBundleList .choice").unbind("click").bind("click", function(e) {
	            self.optionSelection(e.target);
	        });

	    }

    /** Logic for supress B1 selections in B2 vice versa Starts **/

    /** Logic for getting and displaying SnP data  Starts **/
	    getInitialBundleList()
	    {
	    	let renderedSnPProducts1=$('#categoryBundleList1').find(".choice").clone();
	    	let renderedSnPProducts2=$('#categoryBundleList2').find(".choice").clone();
        	if(typeof renderedSnPProducts1 != "undefined" && renderedSnPProducts1!= null && renderedSnPProducts1 != "")
        	{
        		this.initialCategoryBundleList1=renderedSnPProducts1.removeClass("hide");

        	}
        	if(typeof renderedSnPProducts2 != "undefined" && renderedSnPProducts2!= null && renderedSnPProducts2 != "")
        	{
        		this.initialCategoryBundleList2=renderedSnPProducts2.removeClass("hide");

        	}
	    }
	    getQueryString() {
	        let queryString = $('#categoryBundleList1').attr("data-snpparams");
	        let gtQuizParam = self.getCookiesForSnp();
	        if(typeof queryString != "undefined" && queryString != null && queryString!="")
	        	queryString = queryString + gtQuizParam;
	        else
	        	queryString=gtQuizParam;
	        return queryString;
	    }
	    getCookiesForSnp() {
	        var cookieParamString = "";
	        let quizAttrValue = getCookie('gt-quiz-display-attr-value');
	        let quizAspireValue = getCookie('gt-quiz-display-aspire-value');
	        let characterName = getCookie('gt-product-doll-character');
	        if (typeof(characterName) != "undefined" && characterName !== "") {
	            cookieParamString = cookieParamString + '&character=' + characterName;
	        }
	        if (typeof(quizAttrValue) != "undefined" && quizAttrValue !== "") {
	            cookieParamString = cookieParamString + '&TrunkTrait=';
	            var quizAttrArr = quizAttrValue.split(',');

	            for (let i = 0; i < quizAttrArr.length; i++) {
	                if (i < quizAttrArr.length - 1) {
	                    cookieParamString = cookieParamString + quizAttrArr[i] + '+';
	                } else {
	                    cookieParamString = cookieParamString + quizAttrArr[i];
	                }
	            }
	        }
	        if (typeof(quizAspireValue) != "undefined" && quizAspireValue !== "") {
	            cookieParamString = cookieParamString + '&TrunkAspiration=';
	            var quizAspireArr = quizAspireValue.split(',');

	            for (let i = 0; i < quizAspireArr.length; i++) {
	                if (i < quizAspireArr.length - 1) {
	                    cookieParamString = cookieParamString + quizAspireArr[i] + '+';
	                } else {
	                    cookieParamString = cookieParamString + quizAspireArr[i];
	                }
	            }
	        }

	        return cookieParamString;
	    }
	    getSnPResponse() {
	        let errorMessage;
	        let queryString = self.getQueryString();
			let storeSelected = localStorage.getItem("storeSelected");
	        const payload = apiData("products")["getSAndP"].apply({
	            queryString: queryString //'?category=Clothing'//'?q1=|Playpack;x1=AGCategory'
	        });
	        request.ajaxCall(payload)
	            .then(data => {
	                try {
	                    const response = typeof data == "string" ? JSON.parse(data) : data;
	                    let products = response.resultsets[0];

	                    if(typeof(storeSelected) != "undefined" && storeSelected !== "" && storeSelected != null) {
	                   	 self.checkAvailability(products, storeSelected);
	                    }
	                    else
	                    {
	                    	let availableProducts=[];
		                    $.each(products.results, (k, v) => {

		                    	if(v.availability != "noLongerAvailable" && v.availability != "Sold Out" && v.availability != "Unavailable"){
		                    		v.layerCompParams='?'+self.layerComp;
		                    		if(v.imageLink){
				                        let imageLink = v.imageLink
				                        let scene7Url = "";
				                        if (imageLink != undefined && imageLink != "") {
				                            scene7Url = imageLink.substring(0, imageLink.lastIndexOf("/") + 1);
				                            v.scene7Url = scene7Url;
				                        }
		                    		}
		                    		else
		                    		{
		                    			v.scene7Url=this.thumbnailLink+'/';
		                    		}
			                        let targetPricing=self.priceObjName;
			                        if(v.availability == "Backorderable" || v.availability == "Backordered") {
			     		      			let dateStr=v.backorder_date;
			     		      			let a=dateStr.split(" ");
			     		      			let d=a[0].split("-");
			     		      			let t=a[1].split(":");
			     		      			let date = new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
			     		      			v.backorder_date = date;
			     		      		}
			                        $.each(v.prices, (index, value) => {
			                         let priceObject=value;
			                         if(targetPricing in priceObject )
			                         {
			                        	 /* Dynamic Pricing */
											v.targetPricing=parseFloat(priceObject[targetPricing][0].price);
										  /* Dynamic Pricing */
			                         }

			                        });
			                        availableProducts.push(v);
		                    	}
		                    });
		                    /* Dynamic Pricing */
								if(availableProducts.length <= 2)
								{
									self.findCheapestPlayPacks(JSON.parse(JSON.stringify(availableProducts)));
									let bundleList = JSON.parse(JSON.stringify(availableProducts));

									$.each(bundleList, (k, v) => {
										v.adderPriceLargeTrunk=0;
										v.adderDisplayPriceLargeTrunk=self.adderPriceText+Math.abs(v.adderPriceLargeTrunk);

										v.adderPriceSmallTrunk=v.targetPricing-parseFloat(isNaN(self.lowestPlayPack1) ? 0 : self.lowestPlayPack1);
										if(v.adderPriceSmallTrunk >= 0)
										{
										   v.adderDisplayPriceSmallTrunk=self.adderPriceText+Math.abs(v.adderPriceSmallTrunk);
										}
										else{
											v.adderDisplayPriceSmallTrunk=self.deductPriceText+Math.abs(v.adderPriceSmallTrunk);
										}
									});
									handleBarTemplateInst.loadTemplate(this.categorybundleDetailsTemplate,this.categoryTemplateContainer, bundleList, 'replace');
								}
								else{
										self.findCheapestPlayPacks(JSON.parse(JSON.stringify(availableProducts)));
										let bundleList1 = JSON.parse(JSON.stringify(availableProducts));
										let bundleList2 = JSON.parse(JSON.stringify(availableProducts));
										$.each(bundleList1, (k, v) => {
											v.adderPriceLargeTrunk=v.adderPriceSmallTrunk=v.adderPrice=v.targetPricing-self.lowestPlayPack1;
											if(v.adderPrice >= 0)
											{
												v.adderDisplayPriceLargeTrunk=v.adderDisplayPriceSmallTrunk=v.adderDisplayPrice=self.adderPriceText+Math.abs(v.adderPrice);
											}
											else{
												v.adderDisplayPriceLargeTrunk=v.adderDisplayPriceSmallTrunk=v.adderDisplayPrice=self.deductPriceText+Math.abs(v.adderPrice);
											}
										});
										$.each(bundleList2, (k, v) => {
											v.adderPriceLargeTrunk=v.adderPriceSmallTrunk=v.adderPrice=v.targetPricing-self.lowestPlayPack2;
											if(v.adderPrice >= 0)
											{
												v.adderDisplayPriceLargeTrunk=v.adderDisplayPriceSmallTrunk=v.adderDisplayPrice=self.adderPriceText+Math.abs(v.adderPrice);
											}
											else{
												v.adderDisplayPriceLargeTrunk=v.adderDisplayPriceSmallTrunk=v.adderDisplayPrice=self.deductPriceText+Math.abs(v.adderPrice);
											}
										});
										handleBarTemplateInst.loadTemplate(this.categorybundleDetailsTemplate,"#categoryBundleList1", bundleList1, 'replace');
										handleBarTemplateInst.loadTemplate(this.categorybundleDetailsTemplate,"#categoryBundleList2", bundleList2, 'replace');
								}
							/* Dynamic Pricing */
		                    self.setCategoryBundleProductLoad();
							self.safetyMessageFunct();
							self.getInitialBundleList();
							$.when(self.calculateTrunkPricing()).done(function(data){
								if(data){
									$(".gt-container > .row div.gt-bundle-selection-wrapper div.option-details .choice").bind("click", function(e) {
										self.optionSelection(e.target);
									});
									self.handleTrunkAvailability(availableProducts);
								}
							});
	                    }

	                } catch (e) {
	                    errorMessage = e.message;
	                    console.log(`S&P JSON Format error: ${errorMessage}`);
	                }
	            })
	            .catch(error => {
					console.log(`S&P JSON Format error: ${error}`);
	            });
	    }
    /* Logic for getting and displaying SnP data  Ends */
    /* Handle accordion selection and change events Starts */

	  //Hanlde accordion option selection
	  optionSelection(ele) {
	        const $ele = $(ele).closest('.choice');
	        const $optionElement = $ele.parents('.option');
	        let choiceValue = $(ele).attr('data-choice') || $ele.attr('data-choice');
	        let selectedSkuId = $(ele).attr('data-sku-id') || $ele.attr('data-sku-id');


	        $('#submitLetter').unbind().click(function() {

	        	/* Dynamic Pricing */

			          if (!$(this).parents(".option").data("run-once")) {
						 $("#addToBagbutton").removeClass("disabled");
						 $("#buyNowbutton").removeClass("disabled");
			                $(this).parents(".option").data("run-once", true); // Remember that we ran already on this element
			            }

	        	/* Dynamic Pricing */
	            $('#submitLetter').parents('.letter').find('.selected').css('display','table');
	            $('#submitLetter').parents('.letter').find('.option-details').removeClass('open').addClass('close');
	            $('.gt-ltr-left').hide();
				$('.gt-product-doll-image').show();
				$('#submitLetter').parents('.option').removeClass("gtFlowIndex");
				$('#submitLetter').parents('.option').addClass("choiceSelected");

				self.optionClick=true;

	        	 $("#addToBagbutton").removeClass("disabled");
				 $("#buyNowbutton").removeClass("disabled");

	        });
	        /* Dynamic Pricing */

	        $('#acceptBtn,#acceptBtn-xs').unbind().click(function() {
		         self.optionClick=true;
	        });

	        /* Dynamic Pricing */
	        /* Bind click event only to not interested for addons */
		        if ($ele.hasClass('notInterestedAddon')) {
					if(ele.tagName.toLowerCase() !== 'span') {
						return;
					}
					else{
						  $ele.siblings(".choice").find(".hearAidRadio").removeClass("activeRadio");
					}
				}

	        /* Bind click event only to child products if it has*/
		        if ($ele.find('form').length > 0) {
		            if (!($(ele).hasClass("formInputType"))) {
		                return;
		            }
		        }
	        $ele.parents('.option').find('.selected .name').html(choiceValue);
	        $ele.parents('.option').attr('data-sku-id', selectedSkuId);


	        if(self.isGTFlowBroke){
	        	//Find current broken flow element
	        	let targetFlowElement=$(".option.gtFlowIndex");
	        	targetFlowElement.removeClass('disabled');
	        	targetFlowElement.find('.option-details').addClass('open');
	        	targetFlowElement.find('.selected').hide();
	        	self.isGTFlowBroke=false;

	        	if (window.matchMedia('(min-width: 992px)').matches && targetFlowElement.hasClass('letter')) {
		            $('.gt-ltr-left').show().insertAfter('.gt-product-doll-image');
		            $('.gt-product-doll-image').hide();
		        }
		        if (window.matchMedia('(min-width: 992px)').matches && !targetFlowElement.hasClass('letter')) {
		            $('.gt-ltr-left').hide();
		            $('.gt-product-doll-image').show();
		        }
	        }
	        else if(self.jumpToExtras)
	        {
	        	if($optionElement.hasClass('gtFlowIndex'))
	        	{
	        		let nextDisabledElement = $optionElement.nextAll('div.option.disabled:first');
			        nextDisabledElement.removeClass('disabled');
			        nextDisabledElement.find('.option-details').addClass('open');
			        nextDisabledElement.find('.selected').hide();
			        $optionElement.removeClass("gtFlowIndex");
			        nextDisabledElement.addClass("gtFlowIndex");
			        self.jumpToExtras=false;
			        self.isGTFlowBroke=false;

			        if (window.matchMedia('(min-width: 992px)').matches && nextDisabledElement.hasClass('letter')) {
			            $('.gt-ltr-left').show().insertAfter('.gt-product-doll-image');
			            $('.gt-product-doll-image').hide();
			        }
			        if (window.matchMedia('(min-width: 992px)').matches && !nextDisabledElement.hasClass('letter')) {
			            $('.gt-ltr-left').hide();
			            $('.gt-product-doll-image').show();
			        }
	        	}
	        	else
	        	{
	        		let targetFlowElement=$(".option.gtFlowIndex");
		        	targetFlowElement.removeClass('disabled');
		        	targetFlowElement.find('.option-details').addClass('open');
		        	targetFlowElement.find('.selected').hide();
		        	self.jumpToExtras=false;
			        self.isGTFlowBroke=false;

			        if (window.matchMedia('(min-width: 992px)').matches && targetFlowElement.hasClass('letter')) {
			            $('.gt-ltr-left').show().insertAfter('.gt-product-doll-image');
			            $('.gt-product-doll-image').hide();
			        }
			        if (window.matchMedia('(min-width: 992px)').matches && !targetFlowElement.hasClass('letter')) {
			            $('.gt-ltr-left').hide();
			            $('.gt-product-doll-image').show();
			        }
	        	}

	        }
	        else{
		        //Enable next option-details
	        	let nextDisabledElement = $optionElement.nextAll('div.option.disabled:first');
		        nextDisabledElement.removeClass('disabled');
		        nextDisabledElement.find('.option-details').addClass('open');
		        nextDisabledElement.find('.selected').hide();
		        $optionElement.removeClass("gtFlowIndex");
		        nextDisabledElement.addClass("gtFlowIndex");

		        if (window.matchMedia('(min-width: 992px)').matches && nextDisabledElement.hasClass('letter')) {
		            $('.gt-ltr-left').show().insertAfter('.gt-product-doll-image');
		            $('.gt-product-doll-image').hide();
		        }
		        if (window.matchMedia('(min-width: 992px)').matches && !nextDisabledElement.hasClass('letter')) {
		            $('.gt-ltr-left').hide();
		            $('.gt-product-doll-image').show();
		        }
	        }

	        /* Handle option selection operations for different accordions */

		        if ($optionElement.hasClass('offerSize')) {
		            let playPackCount = parseInt($ele.attr("data-playpack-count"));
		            $(self.defaultDisplay).siblings('.bundle-footer').css('display', 'block');
		            if (playPackCount == 1) {
		                $optionElement.nextAll(".bundleList:gt(" + (playPackCount - 1) + ")").removeClass('disabled').hide();
		            } else {
		                $optionElement.nextAll(".bundleList").addClass('disabled').show();
		                $optionElement.next().removeClass('disabled');

		            }
		        }
		        else if ($optionElement.hasClass('bundleList')) {
		            if ($optionElement.next().hasClass('bundleList')) {
		                self.adjustBundleLoad($ele);
		            }
		        }
		        else {
		            $optionElement.next().removeClass('disabled');
		        }

	        //Close selected option-details
		        $ele.parents('.option-details').removeClass('open').addClass('close');
		        $optionElement.find('.selected').css('display', 'table');
		        self.setSelectedItems($ele);
		        self.moveToTop($optionElement);
	        /*pricing footer -End*/

		    // Hide view all if number of products is less than initial count
		        self.hideViewAll($optionElement.next());
	        /* Call composite Image formation */
	        	self.handleImageCompositeImage($ele);
	        	self.calculatePrice($ele);
	        	self.optionClick=true;
	        	if($('#letterContainer').hasClass('choiceSelected')){
	        		 $("#addToBagbutton").removeClass("disabled");
	         		 $("#buyNowbutton").removeClass("disabled");
	        	}
	        	$optionElement.addClass('choiceSelected');
	    }

	    //Handle accordion change option
	    changeOption(ele) {
	        const $ele = $(ele);

	        let $optionSelectionElement = $ele.parents('.option');
	        let $eleParentSiblings = $optionSelectionElement.parents('.trunk').siblings('.include-extrasContainer').find('.include-extras');

	        if (window.matchMedia('(min-width: 992px)').matches && $optionSelectionElement.hasClass('letter')) {
	            $('.gt-ltr-left').show().insertAfter('.gt-product-doll-image');
	            $('.gt-product-doll-image').hide();
	        }

	        if (window.matchMedia('(min-width: 992px)').matches && !$optionSelectionElement.hasClass('letter')) {
	            $('.gt-ltr-left').hide();
	            $('.gt-product-doll-image').show();
	        }
	        $("#addToBagbutton").addClass("disabled");
    		$("#buyNowbutton").addClass("disabled");
	        //Clear all selection on trunk change
	        if ($optionSelectionElement.hasClass('offerSize')) {
	        	self.jumpToExtras=false;
		        self.gtFlowBroke=false;
		        self.isGTFlowBroke=false;
		        $optionSelectionElement.addClass('gtFlowIndex');
		        $optionSelectionElement.nextAll('.option').removeClass('gtFlowIndex');
				$optionSelectionElement.removeClass('choiceSelected');
				$optionSelectionElement.removeClass('priceSelect');
				$optionSelectionElement.nextAll('.option').removeClass('choiceSelected');
				$optionSelectionElement.nextAll('.option').removeClass('priceSelect');
	            $optionSelectionElement.nextAll('.option').addClass('disabled');
	            $optionSelectionElement.nextAll('.option').removeAttr('data-sku-id');
	            $optionSelectionElement.nextAll('.option').find('.selected').css('display', 'table');
	            $optionSelectionElement.nextAll('.option').find('.option-details').removeClass('open').addClass('close');
	            let nextAllArray = $optionSelectionElement.nextAll('.option');
	            $.each(nextAllArray, function(index, element) {
	                let nextEach = nextAllArray[index];
	                let h2Name = $(nextEach).find('.option-details h2').html();
	                $(nextEach).find('.selected .name').html(h2Name);
	            });
	            $("#categoryBundle2").nextAll().remove();
				self.sortHandleBarTemplate(self.largeTrunkData);
	            $optionSelectionElement.nextAll(".bundleList").addClass('disabled').show();
	            self.resetBundleListOnTrunkChange($optionSelectionElement);
	            //Hide or show view all in next option
	            self.hideViewAll($optionSelectionElement.next());
	            self.includeExtrasContainer.find('div[data-include-extra-bind]').hide();
	            self.includeExtrasContainer.find("[data-include-extra-bind='" + self.largeTrunkData.partNumber + "']").show();
	        }
	        else {
	            $optionSelectionElement.siblings('.option').find('.selected').css('display', 'table');
	        }

	        //Re-enable option-details
	        $ele.parents('.option').find('.selected').hide();
	        $optionSelectionElement.find('.option-details').removeClass('close').addClass('open');

	        //Close if any open accordions
	        self.closeAllItems($eleParentSiblings);
	        $optionSelectionElement.siblings('.option').find('.selected').css('display', 'table');
	        $optionSelectionElement.siblings('.option').find('.option-details').removeClass('open').addClass('close');
	        self.moveToTop($optionSelectionElement);

	        //Handle if there is bundle 2 selection in b1
	        if ($optionSelectionElement.hasClass('bundleList')) {
	            self.handleBundleChangeEvent($optionSelectionElement);
	            self.restoreViewAllToggleOnChange($optionSelectionElement);
	        }

	        /*Pricing Footer code - START*/
	        if(self.prevChangedPriceStack.length >-1)
	        {
	        	self.prevChangedPriceStack = self.prevChangedPriceStack.splice(self.prevChangedPriceStack.length - 1,1);
	        	self.prevChangedPriceStack.push($ele.parents('.option').find('.option-details .priceSelect'));
	        }


	        let $totalPrice = $(self.defaultDisplay).parent().find(".total-price").text();


	                $ele.parents().nextAll('.option-details').find('.safetyFlag').each(function() {
	                    $(this).removeClass('safetyFlag');
	                });
	                /* Dynamic Pricing */
	                if($ele.parents('.option').hasClass('offerSize')){
	                	  $ele.parents('.gt-bundle-selection-wrapper').siblings('.bundle-footer').css('display', 'none');
				          $totalPrice = 0;
				          self.animate();
				          $(self.defaultDisplay).parent().find(".total-price").text($totalPrice.toFixed(2));
				          self.dates = [];
					      self.optionClick=false;
					      self.prevChangedPriceStack=[];
				          $ele.parents('.option').find(".choice").removeClass("priceSelect");
				          $ele.parents('.option').nextAll(".option").find(".choice").removeClass("priceSelect");
	                }
	                else{
	                	let currentPrice=$ele.parents('.option').find('.option-details .priceSelect span.stock-price');
	                	 if(self.optionClick == true){
		                		self.tempTotalPrice=parseFloat($totalPrice);
		     	        		self.optionClick=false;
						        let finalPrice=self.getTotalPrice(currentPrice,$totalPrice);
						        $ele.parents('.option').find(".choice").removeClass('priceSelect');
					          	if(finalPrice){
					          		self.animate();
					          		$(self.defaultDisplay).parent().find(".total-price").text(finalPrice.toFixed(2));
					          		}
					          }
	                	 else{
		                		 self.optionClick=false;
		                		 self.resetPriceSelect();
		                		 let finalPrice=self.getTotalPrice(currentPrice,self.tempTotalPrice);
		                		 $ele.parents('.option').find(".choice").removeClass('priceSelect');
		                		 if(finalPrice)
		                		 {
		                			 self.animate();
		                			 $(self.defaultDisplay).parent().find(".total-price").text(finalPrice.toFixed(2));
		                		 }
	                	 	}
	                	}

			        /* Dynamic Pricing */
		        let removeDt;
		        $ele.parents().nextAll('.option-details').find('.backOrderSelect span.backorder-date').each(function() {
		            if ($(this).text() != "" && typeof($(this).text()) != 'undefined') {
		                removeDt = $(this).text();
		                $(this).parents('.choice').removeClass('backOrderSelect');
		            }
		        });
		        if (removeDt != "" && typeof(removeDt) != 'undefined') {
		            var formatToRemoveDt = new Date(removeDt);
		            var RemoveDtToString = formatToRemoveDt.toString();
		            var objectDate = self.dates.toString();
		            var stringToArray = [];
		            stringToArray = objectDate.split(",");
		            let index = stringToArray.indexOf(RemoveDtToString);
		            if (index !== -1) self.dates.splice(index, 1);

		        }
	        /*pricing Footer code - END*/

		    //Handle clear composite image on change
	        self.clearImageCompositeOnChange($ele);
			if(!$optionSelectionElement.hasClass("offerSize") && $optionSelectionElement.siblings('.option').not('.choiceSelected').length>0 && !self.jumpToExtras){
	        		self.isGTFlowBroke=true;
	        }
	    }
	   //Handle static accordion
	    extrasToggle(ele) {
		   	 const $ele = $(ele).closest(".extras-toggle");
		   	 let $eleParent = $ele.parents('.option');
		   	 let $eleParentSiblings = $eleParent.parents('.include-extrasContainer').siblings('.trunk');
		   	 let gtFlowIndexItem=$eleParentSiblings.find(".option.gtFlowIndex");

		   	$eleParent.parent().siblings('.gt-accordion-block').find('.option-details').removeClass('open').addClass('close');
	   		$eleParent.parent().siblings('.gt-accordion-block').find('.extras-close').css('display', 'none');
	   		$eleParent.parent().siblings('.gt-accordion-block').find('.extras-open').css('display', 'table-cell');
	   	    self.closeAllItems($eleParentSiblings);

		   	if($eleParent.find('.option-details').hasClass('open'))
		   		{


		   			$eleParent.find('.option-details').removeClass('open').addClass('close');
		   			$ele.find('.extras-close').css('display', 'none');
		   			$ele.find('.extras-open').css('display', 'table-cell');

					gtFlowIndexItem.removeClass('disabled');
		   			gtFlowIndexItem.find('.option-details').addClass('open');
		   			gtFlowIndexItem.find('.selected').hide();

		   			if (window.matchMedia('(min-width: 992px)').matches && gtFlowIndexItem.hasClass('letter')) {
			            $('.gt-ltr-left').show().insertAfter('.gt-product-doll-image');
			            $('.gt-product-doll-image').hide();
			        }
			        if (window.matchMedia('(min-width: 992px)').matches && !gtFlowIndexItem.hasClass('letter')) {
			            $('.gt-ltr-left').hide();
			            $('.gt-product-doll-image').show();
			        }
			   		self.moveToTop(gtFlowIndexItem);

		   		}
		   		else {


		   			$eleParent.find('.option-details').removeClass('close').addClass('open');
		   			$ele.find('.extras-open').css('display', 'none');
		   			$ele.find('.extras-close').css('display', 'table-cell');

			        if (window.matchMedia('(min-width: 992px)').matches) {
			            $('.gt-ltr-left').hide();
			            $('.gt-product-doll-image').show();
			        }

		   			self.moveToTop($eleParent);

		   		}

		   		if(gtFlowIndexItem.length>0)
		   		{
		   			self.jumpToExtras=true;
		   		}

	   	}
	    //Logic for one accordion open at time
	   	closeAllItems($eleParentSiblings) {
	   	 $eleParentSiblings.find('.option').find('.selected').css('display', 'table');
	   	 $eleParentSiblings.find('.option').find('.option-details').removeClass('open').addClass('close');
	   	 $eleParentSiblings.find('.option').find('.extras-close').css('display', 'none');
	   	 $eleParentSiblings.find('.option').find('.extras-open').css('display', 'table-cell');
	   	}
	   	//Move focus to current open accordion
		moveToTop($eleParent) {
		if(typeof $eleParent != "undefined" && $eleParent != null && $eleParent!="" && $eleParent.length>0){
			 let offsetValue = $eleParent.offset().top - 60;
			 $('html, body').animate({
			  scrollTop: offsetValue
			 }, 600);
			}
		}

	/* Handle accordion selection and change events Ends */
    /* Trunk releated events and function starts */

	    //Load trunk data from html

	    //Get small trunk data
	    getSmallTrunkDetails() {
	        let smallTrunkData = "";
	        let smallTrunkJson = $("#smallTrunkData").html();
	        if (typeof smallTrunkJson != "undefined" && smallTrunkJson != null && smallTrunkJson != "") {
	            smallTrunkData = JSON.parse(smallTrunkJson);

	        }
	        return smallTrunkData;
	    }
	  //Get Large trunk data
	    getLargeTrunkDetails() {
	        let largeTrunkData = "";
	        let largeTrunkJson = $("#largeTrunkData").html();
	        if (typeof largeTrunkJson != "undefined" && largeTrunkJson != null && largeTrunkJson != "") {
	            largeTrunkData = JSON.parse(largeTrunkJson);
	        }
	        return largeTrunkData;
	    };
	    //Load trunk data into global variable for reuse
	    loadTrunkJsonData() {
     	    this.smallTrunkData = self.getSmallTrunkDetails();
			this.largeTrunkData = self.getLargeTrunkDetails();
			//self.renderTrunkList();
			self.getDollselectId();

	    }
	    //Render Trunk data
	    renderTrunkList() {
	        if (typeof self.largeTrunkData != "undefined" && self.largeTrunkData != null && self.largeTrunkData != "") {
	            self.sortHandleBarTemplate(self.largeTrunkData);
	            self.checkInventoryStatus();
	            self.includeExtrasContainer.find('div[data-include-extra-bind]').hide();
	            self.includeExtrasContainer.find("[data-include-extra-bind='" + self.largeTrunkData.partNumber + "']").show();
	        } else if (typeof self.smallTrunkData != "undefined" && self.smallTrunkData != null && self.smallTrunkData != "") {
	            self.sortHandleBarTemplate(self.smallTrunkData);
	            self.checkInventoryStatus();
	            self.includeExtrasContainer.find('div[data-include-extra-bind]').hide();
	            self.includeExtrasContainer.find("[data-include-extra-bind='" + self.smallTrunkData.partNumber + "']").show();
	        } else {
	            return false;
	        }
	    }
	    //Handle trunk change event
	    smallTrunkChange(ele) {
			let trunkItemsPrice=$(ele).attr('data-displayable-pricing');
		    let smallTrankObj = {
			  	    "skuid": $(ele).attr('data-sku-id'),
					"variantid": $(ele).attr('data-variant-id'),
					"productType": $(ele).attr('data-product-type'),
					"variantinventry": $(ele).attr('data-variantinventry'),
					"backorderdate": $(ele).attr('data-backorderdate'),
					'itematpreceiptid':$(ele).attr('data-itematpreceiptid'),
			 }
	    	self.clearAllSessionItemsOnTrunkChange();
	        $("#categoryBundle2").nextAll().remove();
			self.sortHandleBarTemplate(self.smallTrunkData);
			if(typeof trunkItemsPrice != "undefined" && trunkItemsPrice != null && trunkItemsPrice != ""){
				self.populatePricingValue(JSON.parse(trunkItemsPrice));
			}
	        self.includeExtrasContainer.find('div[data-include-extra-bind]').hide();
			self.includeExtrasContainer.find("[data-include-extra-bind='" + self.smallTrunkData.partNumber + "']").show();
	        self.setNonDisplayableItemsSession(self.smallTrunkData)
			self.setPlayPackPricing(ele);
			self.setLocalStorageItem('giftTrank',JSON.stringify(smallTrankObj))
			self.smallAndLargeinventorySet('#earPiercing .choice',self.smalltrunkinventory)
			self.filternoItemProduct(self.noItemSamllTruk);
	    }

	    largeTrunkChange(ele) {
			let trunkItemsPrice=$(ele).attr('data-displayable-pricing');
			let largeTrankObj = {
				    "skuid": $(ele).attr('data-sku-id'),
				    "variantid": $(ele).attr('data-variant-id'),
					"productType": $(ele).attr('data-product-type'),
					"variantinventry": $(ele).attr('data-variantinventry'),
					"backorderdate": $(ele).attr('data-backorderdate'),
					'itematpreceiptid':$(ele).attr('data-itematpreceiptid'),
			}
	    	self.clearAllSessionItemsOnTrunkChange();
	        $("#categoryBundle2").nextAll().remove();
			self.sortHandleBarTemplate(self.largeTrunkData);
			if(typeof trunkItemsPrice != "undefined" && trunkItemsPrice != null && trunkItemsPrice != ""){
				self.populatePricingValue(JSON.parse(trunkItemsPrice));
			}
	        self.includeExtrasContainer.find('div[data-include-extra-bind]').hide();
	        self.includeExtrasContainer.find("[data-include-extra-bind='" + self.largeTrunkData.partNumber + "']").show();
	        self.setNonDisplayableItemsSession(self.largeTrunkData);
			self.setPlayPackPricing(ele);
			self.setLocalStorageItem('giftTrank', JSON.stringify(largeTrankObj));
			self.smallAndLargeinventorySet('#earPiercing .choice',self.largetrunkinventory);
			self.filternoItemProduct(self.noItemLargeTruk);
		}

		filternoItemProduct(arr) {
			const getNoitemPartNumber = $.map($('.nonDisplayableItems'), function(item) {
			      return $(item).attr('data-sku-id')
			})
			const filterList =  arr.filter(item => {
				return getNoitemPartNumber.includes(item.product.partnumber)
			});
			let noItem = $('.nonDisplayableItemsContainer .nonDisplayableItems');
			noItem.each(function(i) {
			  let getKeyvalue = filterList[i].product.variants[0];
				 $(this).attr({
				"data-variantid":getKeyvalue.id,
				"data-product-type": filterList[i].product.core.product_type,
				"data-association":filterList[i].association_type,
				"data-variantinventry": getKeyvalue.core.variant_inventorystatus ? getKeyvalue.core.variant_inventorystatus : '',
				"data-backorderdate": getKeyvalue.core.variant_backorderdate ? getKeyvalue.core.variant_backorderdate: '',
				'data-itematpreceiptid': getKeyvalue.core.variant_itematpreceiptid ? getKeyvalue.core.variant_itematpreceiptid: '',
				'data-price': getKeyvalue.pricing.price,

			})
			})
		}
		setLocalStorageItem(key,data) {
			if(key) {
			 localStorage.removeItem(key);
			 localStorage.setItem(key,data)
			}
		 }

	    //Display all addons based on the squence from WCS
	    sortHandleBarTemplate(data) {
	    	let gtDisplayableItems=data.gtDisplayableItems;
	    	if(gtDisplayableItems != null && gtDisplayableItems.length>0){
	    		gtDisplayableItems.forEach(function(item, index) {
	    			item.index=index;
	    			if (item.KitOption != null && item.KitOption == "KitLetter")
	    			{
	    				if(typeof self.gtLetterDetailsTemplate != "undefined" && self.gtLetterDetailsTemplate != "" && self.gtLetterDetailsTemplate != null) {
	    					self.gtLetterDetailsTemplate.attr('data-gtLetterDetails',JSON.stringify(item));
	    					$(".trunk").append(self.gtLetterDetailsTemplate);
		        		}
		        		else
		        		{
		        			if($('#letterContainer').length > 0)
		                    {

		                		self.gtLetterDetailsTemplate = $('#letterContainer').detach();
		                		self.gtLetterDetailsTemplate.attr('data-gtLetterDetails',JSON.stringify(item));
								$(".trunk").append(self.gtLetterDetailsTemplate);
								let letterJson = JSON.parse($('#letterContainer').attr('data-gtletterdetails'));

								$('#letterContainer').attr({
								'data-sku-id': letterJson.partNumber,
								'data-kitoption': letterJson.KitOption,
								'data-kitdisplayable':letterJson.KitDisplayable,
								'data-product-type': letterJson.product_type,
								'data-variantid': letterJson.variantId,
								'data-association': letterJson.associationType });
					       		$('.gt-ltr-left').css('max-width', $('.doll-img').width() - 10 + 'px');
		                		$('.gt-ltr-left').css('min-width', $('.doll-img').width() - 10 + 'px');
		                		$('.gt-ltr-left').css('min-height', $('.doll-img').width() + 40 + 'px');

		                    }
		        		}
	    			}
	    		   else
	    		   {
	    			   if(item.product_type !=null && item.product_type == "ItemBean"){
	    				   let itemBeanTemplate = self.executeHandleBar("#earPiercingTemplate", item);
	   	                   $(".trunk").append(itemBeanTemplate.templateData);
	    			   }
	    			   else if(item.product_type !=null && item.product_type == "ProductBean")
	    			   {
	    				   let productBeantemplate = self.executeHandleBar("#hearingAidTemplate", item);
		   	               $(".trunk").append(productBeantemplate.templateData);

	    			   }
	    		   }
		        });
	    	}
	    	let gtNonDisplayableItems=data.gtNonDisplayableItems;

	    	if(gtNonDisplayableItems != null && gtNonDisplayableItems.length>0){
	    		$(".nonDisplayableItemsContainer").empty();
	    		gtNonDisplayableItems.forEach(function(item, index) {
	    			let nonDisplayableItemTemplate = self.executeHandleBar("#nonDisplayableItemTemplate", item);
	    			$(".nonDisplayableItemsContainer").append(nonDisplayableItemTemplate.templateData);
	    		});
	    	}
	    }
	    executeHandleBar(template, data) {
	        var templateObj = {};
	        if (typeof data != "undefined" && data != null) {

	            var source = $(template).html();
	            var templatesource = Handlebars.compile(source);
	            var templateData = templatesource(data);
	            templateObj["skuId"] = data.partNumber;
	            templateObj["templateData"] = templateData;
	        }
	        return templateObj;
	    }

    /* Trunk releated events and function Ends */


	/** Logic for pricing for addons starts **/
	    getActivePriceName() {
	        let loyalityPriceName = getCookie(
	          Constants.miniCartCookieName || "MATTEL_CUSTOMER_SEGMENT"
	        );
	        let employeePrice = getCookie(
	          Constants.EmployeePriceCookieName || "EmployeeSegment"
	        );
	        if (employeePrice == true || employeePrice == "true") {
	          if (loyalityPriceName) {
	            return "employee_loyalty_price";
	          }
	          return "employee_price";
	        } else if (
	          loyalityPriceName == "SILVER" ||
	          loyalityPriceName == "GOLD" ||
	          loyalityPriceName == "BERRY"
	        ) {
	          return "loyalty_price";
	        } else {
	          return "sale_price";
	        }
	      }
		productOfferprice(partNumbersArr) {
			let deferred = $.Deferred();
			let partNumbers =partNumbersArr.toString();
	  		let ajaxOptionsLarge = jQuery.extend({}, apiConfigInst.getApiConfig("giftTrunk").getGiftTrunk(self.largeTrunkattrSku));
			let ajaxOptionsSmall = jQuery.extend({}, apiConfigInst.getApiConfig("giftTrunk").getGiftTrunk(self.smallTrunkattrSku));
			ajaxOptionsLarge.data = JSON.stringify({
				flag: self.ajaxFlag });
			request.ajaxCall(ajaxOptionsLarge).then(data => {
				let largeTrunkSkus=self.getSkuIdsForAvailabilityCheck(self.largeTrunkData);
				let inventoryStatus = data.product.variants[0].core.variant_inventorystatus ? data.product.variants[0].core.variant_inventorystatus : '',
					  backorderDate = data.product.variants[0].core.variant_backorderdate,
					  itematpreceiptid = data.product.variants[0].core.variant_itematpreceiptid,
				      productType = data.product.core.product_type;
				var responseData = data.product.associations;
				self.noItemLargeTruk = data.product.associations;
				 self.productSkuidForInventoryCheck(responseData, largeTrunkSkus, self.largetrunkAvaiArray)
     			for(var i = 0; i < responseData.length; i++) {
					let filteravailArray = responseData[i].product;
					let availArray = responseData[i].product.variants[0];
					self.largetrunkpriceObj[responseData[i].product.partnumber] = availArray.pricing.price;

					setTimeout(function(){
						self.setHearingAndheariaidInventory(filteravailArray,self.largetrunkinventory)
					   },800);

				}
	 		$('#largeTrunk').attr({
					'data-variantinventry':inventoryStatus,
					'data-product-type':productType,
					'data-backorderdate': backorderDate ? backorderDate : '',
					'data-itematpreceiptid': itematpreceiptid ? itematpreceiptid: ''
				});
				self.responseNextLoad()
			});

			request.ajaxCall(ajaxOptionsSmall).then(data => {
				let smallTrunkSkus=self.getSkuIdsForAvailabilityCheck(self.smallTrunkData);
                 let inventoryStatus = data.product.variants[0].core.variant_inventorystatus ? data.product.variants[0].core.variant_inventorystatus : '',
					backorderDate = data.product.variants[0].core.variant_backorderdate,
					itematpreceiptid = data.product.variants[0].core.variant_itematpreceiptid,
				    productType = data.product.core.product_type;
				var responseData = data.product.associations;
                self.noItemSamllTruk = data.product.associations;
                self.productSkuidForInventoryCheck(responseData, smallTrunkSkus, self.smalltrunkAvaiArray)
				for(var i = 0; i < responseData.length; i++) {
					let filteravailArray = responseData[i].product;
					let availArray = responseData[i].product.variants[0];
					self.smalltrunkpriceObj[responseData[i].product.partnumber] = availArray.pricing.price;

					setTimeout(function(){
						self.setHearingAndheariaidInventory(filteravailArray,self.smalltrunkinventory);
						$('.gt-bundle-selection-wrapper .trunk').removeClass('container-prevent')
					},800);
 			}
			$('#smallTrunk').attr({
				'data-variantinventry':inventoryStatus,
				'data-product-type':productType,
				'data-backorderdate': backorderDate ? backorderDate : '',
				'data-itematpreceiptid': itematpreceiptid ? itematpreceiptid: ''
			});
 			self.responseNextLoad()
			});
		 deferred.resolve(true);
		 return deferred.promise();
	    }

         responseNextLoad() {
			let combineObj = $.extend({},self.largetrunkpriceObj,self.smalltrunkpriceObj);
			self.loadTrunksWithPricingData(combineObj);
			self.renderTrunkList();
		 }

		 checkInventoryattrFlag(ele) {
			let selfFlag = true;
			if(ele.attr('data-variantinventry') == '' || $('.select-gt-product').attr('data-variantinventry') == '' || $('#letterContainer').attr('data-variantinventry') == '') {
 			       selfFlag = false
				} else {
					selfFlag = true
				}
			 if($('.categoryBundleList .priceSelect').length) {
				$('.categoryBundleList .priceSelect').each(function(){
					 if($(this).attr('data-variantinventry') == "") {
						 selfFlag = false
						  return false
				   } else {
					     selfFlag = true
				    }
				  })

				  if(!selfFlag) {
						$("#addToBagbutton").addClass("inventory-disabled");
						$("#buyNowbutton").addClass("inventory-disabled");
				  } else {
						$("#addToBagbutton").removeClass("inventory-disabled");
						$("#buyNowbutton").removeClass("inventory-disabled");
				  }
			 }
		  }
		 earInventoryStatus(ele) {
			self.checkInventoryattrFlag($(ele))
		 }

         productSkuidForInventoryCheck(responseData,skuId,pushArray)
		 {
			const filterListArray =  responseData.filter(item => skuId.includes(item.product.variants[0].core.sku));
			for(var j = 0; j < filterListArray.length; j++) {
				let inventoryArray = filterListArray[j].product.variants[0];
				pushArray.push({
					  itemCode:inventoryArray.core.sku,
					 inventoryStatus: inventoryArray.core.variant_inventorystatus ? inventoryArray.core.variant_inventorystatus: '',
					 backOrderDate: inventoryArray.core.variant_backorderdate ? inventoryArray.core.variant_backorderdate: ''
					 });
			 }


		 }

		setHearingAndheariaidInventory(data,arr) {
			var earskuId = $('#earPiercing').attr('data-sku-id');
			var hearingAid = $('#hearingAid').attr('data-sku-id');

		 if(data.partnumber == earskuId) {
			arr.inventoryStatus = data.variants[0].core.variant_inventorystatus ? data.variants[0].core.variant_inventorystatus : '';
			arr.itematpreceiptid = data.variants[0].core.variant_itematpreceiptid ? data.variants[0].core.variant_itematpreceiptid : '';
			arr.backorderdate = data.variants[0].core.variant_backorderdate ? data.variants[0].core.variant_backorderdate : '';
			 arr.price = data.variants[0].pricing.price ? data.variants[0].pricing.price : '';
			arr.kitoption = data.attributes.KitOption ? data.attributes.KitOption : '';
			arr.kitdisplayable = data.attributes.KitDisplayable ? data.attributes.KitDisplayable : '';

		}
		if(data.partnumber == hearingAid) {
			arr.hearing_aid = data.variants;
		}
		if(data.attributes.KitOption == "KitEnvelope"){
			arr.envelope = data.variants;
		}
	  }

	  smallAndLargeinventorySet(id,data) {
		let hearAid = $('#hearingAid .hearAidRadio')
		$(id).attr({
			"data-variantinventry": data.inventoryStatus,
			"data-itematpreceiptid":data.itematpreceiptid,
			"data-backorderdate": data.backorderdate,
			"data-price": parseFloat(data.price).toFixed(2),
			"data-kitoption": data.kitoption,
			"data-kitdisplayable":  data.kitdisplayable,
			})

			hearAid.each(function(i){
			$(this).attr({
					  "data-variantinventry": data.hearing_aid[i].core.variant_inventorystatus ? data.hearing_aid[i].core.variant_inventorystatus: '',
					   "data-itematpreceiptid":data.hearing_aid[i].core.variant_itematpreceiptid ? data.hearing_aid[i].core.variant_itematpreceiptid : '',
					   "data-backorderdate": data.hearing_aid[i].core.variant_backorderdate ? data.hearing_aid[i].core.variant_backorderdate : '',
					   "data-price": parseFloat(data.hearing_aid[i].pricing.price).toFixed(2)
					  })
			})

			$('#letterContainer').attr({
				"data-variantinventry": data.envelope[0].core.variant_inventorystatus ? data.envelope[0].core.variant_inventorystatus: '',
				"data-itematpreceiptid":data.envelope[0].core.variant_itematpreceiptid ? data.envelope[0].core.variant_itematpreceiptid : '',
				"data-backorderdate": data.envelope[0].core.variant_backorderdate ? data.envelope[0].core.variant_backorderdate : '',
				"data-price": parseFloat(data.envelope[0].pricing.price).toFixed(2)

			})
	  }

	    populatePricingValue(data) {
		    if(data){
						for (let key in data) {
							if (data.hasOwnProperty(key)) {
								let partnumber = key;
								let price = data[key];
								let productElement = $("#gt-bundle-selection-wrapper").find('[data-sku-id=' + partnumber + ']');
								if (typeof productElement != 'undefined' && productElement != null) {
									if (price > 0) {
										let priceString = '$' + price;
										$(productElement).find('.gt-price').text(priceString);
									} else {
										$(productElement).find('.gt-price').text('free');
									}
								}
							}
						}
				}
				$(".gt-container > .row div.gt-bundle-selection-wrapper div.trunk div.option.addon div.option-details .choice").unbind("click").bind("click", function(e) {
					self.optionSelection(e.target);
				});
		}
		formatPricing(data) {
			let priceSkuMap=new Object();
		    if(data && data.priceDetails){

			        	let priceList = data.priceDetails.product_priceList;
				        for (var i = 0; i < priceList.length; i++) {
				            let partnumber = priceList[i].partNumber;
				            let price = priceList[i].unitPrice[0].price.value;
				            priceSkuMap[partnumber]=price;
				        }

				}
			return priceSkuMap;
	    }
	/** Logic for pricing for addons Ends **/

	/** Logic for Storing and  clearing Selection Data for Add to Bag Starts **/
		setNonDisplayableItemsSession(trunkData){
			let gtLetterDetails=$("#letterContainer").attr("data-gtletterdetails");
			if(typeof gtLetterDetails != "undefined" && gtLetterDetails != null && gtLetterDetails != "")
			{
				localStorage.setItem("gtLetterDetails",gtLetterDetails);
			}
			if(typeof trunkData != "undefined" && trunkData != null && trunkData != "")
			{
				let gtNonDisplayableItems=trunkData.gtNonDisplayableItems;
				if(typeof gtNonDisplayableItems != "undefined" && gtNonDisplayableItems != null && gtNonDisplayableItems != "")
				{
					localStorage.setItem("gtNonDisplayableItems",JSON.stringify(trunkData.gtNonDisplayableItems));
				}
			}
		}
		setSelectedItems($choiceElement)
		{
			let filterElements=[".trunk-heading",".product-age-range",".safetyMessage",".gt-price",".product-status",".trunk-pricerange"];

			let $parent=$choiceElement.parents('.option');

			let selectionDetails={};

			let data_sku_id =$parent.attr('data-sku-id');
			let data_variant_id =$parent.attr('data-variantid');
			let data_choice=$choiceElement.attr('data-choice');
			let data_product_type =$choiceElement.attr('data-product-type');
			let data_inventory_status =$choiceElement.attr('data-variantinventry');

			let gt_price=$choiceElement.find('.gt-price').text();
			let product_age_range=$choiceElement.find('.product-age-range').text();
			let product_status=$choiceElement.find('.product-status').text();
			let safetyMessage=$choiceElement.find('.safetyMessage').text();
			let img=$choiceElement.find('.img-responsive').next().attr('src');
			let data_selection_id=$choiceElement.parents('.option').attr('data-selection-id');
			let trunkDesc=$choiceElement.find('.trunk-desc').clone();
			let trunk_pricerange=trunkDesc.find(".trunk-pricerange").text();
			let isInterestedItem=$choiceElement.hasClass("notInterestedAddon");
			for(let i=0;i<filterElements.length;i++)
			{
				trunkDesc.find(filterElements[i]).remove();
			}

			if(typeof data_selection_id != "undefined" && data_selection_id !=null && data_selection_id!="")
			{
				selectionDetails["selectionId"]=data_selection_id;
				if(typeof isInterestedItem == "undefined" || isInterestedItem == null || !isInterestedItem){
						if(typeof data_sku_id != "undefined" && data_sku_id !=null && data_sku_id!="")
						{
							selectionDetails["skuId"]=data_sku_id;
						}
						if(typeof data_variant_id != "undefined" && data_variant_id !=null && data_variant_id!="")
						{
							selectionDetails["variantId"]=data_variant_id;
						}
						if(typeof data_product_type != "undefined" && data_product_type !=null && data_product_type!="")
						{
							selectionDetails["productType"]=data_product_type;
						}
						if(typeof data_inventory_status != "undefined" && data_inventory_status !=null && data_inventory_status!="")
						{
							selectionDetails["inventoryStatus"]= data_inventory_status;
						}
						if(typeof data_choice != "undefined" && data_choice !=null && data_choice!="")
						{
							selectionDetails["title"]=data_choice;
						}
						if(typeof gt_price != "undefined" && gt_price !=null && gt_price!="")
						{
							selectionDetails["price"]=gt_price;
						}
						if(typeof product_age_range != "undefined" && product_age_range !=null && product_age_range!="")
						{
							selectionDetails["ageRange"]=product_age_range;
						}
						if(typeof product_status != "undefined" && product_status !=null && product_status!="")
						{
							selectionDetails["productStatus"]=product_status.trim();
						}
						if(typeof safetyMessage != "undefined" && safetyMessage !=null && safetyMessage!="")
						{
							selectionDetails["safetyMessage"]=safetyMessage;
						}
						if(typeof img != "undefined" && img !=null && img!="")
						{
							selectionDetails["img"]=img;
						}
						if(trunkDesc)
						{
							if(typeof trunkDesc.text() != "undefined" && trunkDesc.text() !=null && trunkDesc.text()!="")
							{
								let description=trunkDesc.text().trim();
								selectionDetails["description"]=description;
							}
						}
						if(typeof trunk_pricerange != "undefined" && trunk_pricerange !=null && trunk_pricerange!="")
						{
							selectionDetails["trunkPricerange"]=trunk_pricerange;
						}
						localStorage.setItem(data_selection_id,JSON.stringify(selectionDetails));
				}
				else
				{
					localStorage.setItem(data_selection_id,"");
				}

			}


		}

		clearAllSessionItemsOnTrunkChange()
		{
			var n = localStorage.length;
			while(n--) {
			  var key = localStorage.key(n);
			  if(/addon/.test(key)) {
			    localStorage.removeItem(key);
			  }
			}
			localStorage.removeItem("bundlelist0");
			localStorage.removeItem("bundlelist1");
			localStorage.removeItem("gtLetterDetails");
			localStorage.removeItem("gtNonDisplayableItems");
		}

	/** Logic for Storing and  clearing Selection Data for Add to Bag Ends **/

	/** Logic for ATP checking inventory status for SnP data Starts **/
		checkAvailability(products, storeSelectedValue) {
			  let catVal = $("#catalogId").val();
			  let ajaxOptions = jQuery.extend({},apiConfigInst.getApiConfig("storeAvailability").update);
				 ajaxOptions.data = {
						"catalogId": catVal ? catVal : "",
						"storeSelected": storeSelectedValue ? storeSelectedValue : "",
						"productsList":[]
				};
				$.each(products.results, (k, v) => {
					ajaxOptions.data.productsList.push(v.PartNumber.toString());

				});
				ajaxOptions.data = JSON.stringify(ajaxOptions.data);
				request.ajaxCall(ajaxOptions).then(data => {
					   try {
						   const responseATP = typeof data == "string" ? JSON.parse(data) : data;
						   let availableResult = [];
						      $.each(products.results, (k, v) => {
									for(var i=0;i<responseATP.itemAvailabilityDetailsList.length;i++) {
										let itemCode = responseATP.itemAvailabilityDetailsList[i].itemCode;
										let availabilityStatus = responseATP.itemAvailabilityDetailsList[i].availabilityStatus;
										let partNumber = v.PartNumber;
										if(partNumber === itemCode && availabilityStatus != "T" && availabilityStatus != "S") {
											if(availabilityStatus == "A" || availabilityStatus == "N" || availabilityStatus == "M") {
                                                v.availability = "Available";
                                                availableResult.push(v);
                                          }
                                          else {
                                                v.availability = "Unavailable";
                                          }
										}
										else {
											if(partNumber === itemCode) {
											v.availability = "Unavailable";
											}
										}
									}
								});
						      $.each(availableResult, (k, v) => {
                                  if(v.availability != "noLongerAvailable" && v.availability != "Sold Out" && v.availability != "Unavailable"){
			       			             v.layerCompParams='?'+self.layerComp;
			                    		if(v.imageLink){
					                        let imageLink = v.imageLink
					                        let scene7Url = "";
					                        if (imageLink != undefined && imageLink != "") {
					                            scene7Url = imageLink.substring(0, imageLink.lastIndexOf("/") + 1);
					                            v.scene7Url = scene7Url;
					                        }
			                    		}
			                    		else
			                    		{
			                    			v.scene7Url=this.thumbnailLink+'/';
			                    		}
				                        let targetPricing=self.priceObjName;
				                        if(v.availability == "Backorderable" || v.availability == "Backordered") {
				     		      			let dateStr=v.backorder_date;
				     		      			let a=dateStr.split(" ");
				     		      			let d=a[0].split("-");
				     		      			let t=a[1].split(":");
				     		      			let date = new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
				     		      			v.backorder_date = date;
				     		      		}
				                        $.each(v.prices, (index, value) => {
				                         let priceObject=value;
				                         if(targetPricing in priceObject )
				                         {
				                        	v.targetPricing=parseFloat(priceObject[targetPricing][0].price);
				                         }

				                        });
			                    	}
			                    });
						      /* Dynamic Pricing */
								if(availableResult.length <= 2)
								{
									self.findCheapestPlayPacks(JSON.parse(JSON.stringify(availableResult)));
									let bundleList = JSON.parse(JSON.stringify(availableResult));

									$.each(bundleList, (k, v) => {
										v.adderPriceLargeTrunk=0;
										v.adderDisplayPriceLargeTrunk=self.adderPriceText+Math.abs(v.adderPriceLargeTrunk);
										v.adderPriceSmallTrunk=v.targetPricing-parseFloat(isNaN(self.lowestPlayPack1) ? 0 : self.lowestPlayPack1);
										if(v.adderPriceSmallTrunk >= 0)
										{
										   v.adderDisplayPriceSmallTrunk=self.adderPriceText+Math.abs(v.adderPriceSmallTrunk);
										}
										else{
											v.adderDisplayPriceSmallTrunk=self.deductPriceText+Math.abs(v.adderPriceSmallTrunk);
										}
									});
									handleBarTemplateInst.loadTemplate(this.categorybundleDetailsTemplate,this.categoryTemplateContainer, bundleList, 'replace');
								}
								else{
										self.findCheapestPlayPacks(JSON.parse(JSON.stringify(availableResult)));
										let bundleList1 = JSON.parse(JSON.stringify(availableResult));
										let bundleList2 = JSON.parse(JSON.stringify(availableResult));
										$.each(bundleList1, (k, v) => {
											v.adderPriceLargeTrunk=v.adderPriceSmallTrunk=v.adderPrice=v.targetPricing-self.lowestPlayPack1;
											if(v.adderPrice >= 0)
											{
												v.adderDisplayPriceLargeTrunk=v.adderDisplayPriceSmallTrunk=v.adderDisplayPrice=self.adderPriceText+Math.abs(v.adderPrice);
											}
											else{
												v.adderDisplayPriceLargeTrunk=v.adderDisplayPriceSmallTrunk=v.adderDisplayPrice=self.deductPriceText+Math.abs(v.adderPrice);
											}
										});
										$.each(bundleList2, (k, v) => {
											v.adderPriceLargeTrunk=v.adderPriceSmallTrunk=v.adderPrice=v.targetPricing-self.lowestPlayPack2;
											if(v.adderPrice >= 0)
											{
												v.adderDisplayPriceLargeTrunk=v.adderDisplayPriceSmallTrunk=v.adderDisplayPrice=self.adderPriceText+Math.abs(v.adderPrice);
											}
											else{
												v.adderDisplayPriceLargeTrunk=v.adderDisplayPriceSmallTrunk=v.adderDisplayPrice=self.deductPriceText+Math.abs(v.adderPrice);
											}
										});
										handleBarTemplateInst.loadTemplate(this.categorybundleDetailsTemplate,"#categoryBundleList1", bundleList1, 'replace');
										handleBarTemplateInst.loadTemplate(this.categorybundleDetailsTemplate,"#categoryBundleList2", bundleList2, 'replace');
								}
							/* Dynamic Pricing */
							  self.getInitialBundleList();
						      self.setCategoryBundleProductLoad();
							  self.safetyMessageFunct();
							  $.when(self.calculateTrunkPricing()).done(function(data){
								if(data){
									$(".gt-container > .row div.gt-bundle-selection-wrapper div.option-details .choice").bind("click", function(e) {
										self.optionSelection(e.target);
									});
									self.handleTrunkAvailability(availableResult);
								}
							});
					   } catch (e) {
			                 //exceptionHandler("error", "Failed to load ATP products");
			               }
					    });
			}
	/** Logic for ATP checking inventory status for SnP data Ends **/

	/** Logic for checking inventory status for Trunk data Starts **/
		handleTrunkAvailability(availableProducts){
			let trunks=$('.offerSize').find('.option-details').children('.choice');
            $.each(trunks,function(index,element){
            	let noOfPlayback=parseInt($(element).attr('data-playpack-count'));
                    if(noOfPlayback > availableProducts.length)
                	{
                    	$(element).unbind('click');
                		let $statusLine=$(element).find('.product-status');
                		$statusLine.removeClass('hide');
                		let unavailableText=$('.offerSize').attr('data-unavailableText');
                		if(typeof unavailableText != "undefined" && unavailableText != null && unavailableText!="")
                		{
							let statusText="";
                			if(unavailableText != "false")
							{
								statusText=$statusLine.find('.status-detail').text(unavailableText);
                			}
							$statusLine.empty();
                			$statusLine.append(statusText);
                		}
					}
					else if($(element).find('.product-status').hasClass("backordered"))
					{
						$(element).find('.product-status').removeClass("hide");
					}
					else if($(element).find('.product-status').hasClass("unavailable"))
					{
						$(element).unbind('click');
                		let $statusLine=$(element).find('.product-status');
                		$statusLine.removeClass('hide');
                		let unavailableText=$('.offerSize').attr('data-unavailableText');
                		if(typeof unavailableText != "undefined" && unavailableText != null && unavailableText!="")
                		{
							let statusText="";
                			if(unavailableText != "false")
							{
								statusText=$statusLine.find('.status-detail').text(unavailableText);
                			}
							$statusLine.empty();
                			$statusLine.append(statusText);
						}
						$(element).find('.trunkNAPricingText').removeClass('hide');
						$(element).find('.trunkPricingText').addClass('hide');
						$(element).find('.stock-price').addClass('hide');
					}
            	});
		}
		getSkuIdsForAvailabilityCheck(trunkData)
		{
			let skuIdSet=[];
			if(typeof trunkData != "undefined" && trunkData != null && trunkData != "")
			{
		    	let gtDisplayableItems=trunkData.gtDisplayableItems;
				$.each(gtDisplayableItems, function(k,v){
					if(v.KitOption == "KitLetter")
						skuIdSet.push(v.partNumber);
				});
				let gtNonDisplayableItems=trunkData.gtNonDisplayableItems;
				$.each(gtNonDisplayableItems, function(k,v){
					skuIdSet.push(v.partNumber);
				});
			}
			return skuIdSet;
		}

		checkInventoryStatus()
		{
			let catVal = $("#catalogId").val();
            let storeSelected = localStorage.getItem("storeSelected");
			let smallTrunkSkus=self.getSkuIdsForAvailabilityCheck(self.smallTrunkData);
			let largeTrunkSkus=self.getSkuIdsForAvailabilityCheck(self.largeTrunkData);
			let partNumberArr=smallTrunkSkus.concat(self.getSkuIdsForAvailabilityCheck(self.largeTrunkData));

            if(typeof(storeSelected) != "undefined" && storeSelected !== "" && storeSelected != null) {
                	let ajaxOptions = jQuery.extend({},apiConfigInst.getApiConfig("storeAvailability").update);
	                ajaxOptions.data = JSON.stringify({
	                            "catalogId": catVal ? catVal : "",
	                            "storeSelected": storeSelected ? storeSelected : "",
	                            "productsList":partNumberArr
	                });
	                 request.ajaxCall(ajaxOptions).then(data => {
	                      let response = data.itemAvailabilityDetailsList;
	                      self.handleATPStoreAvailability(response,smallTrunkSkus,largeTrunkSkus);
	                });
                }
            else {
				      let smallTrunkStatusArray=self.smalltrunkAvaiArray.splice(0,smallTrunkSkus.length);
					  let largeTrunkStatusArray=self.largetrunkAvaiArray.splice(0,largeTrunkSkus.length);
						  self.handleInventoryStatus(self.smallTrunkData.partNumber,smallTrunkStatusArray)
					      self.handleInventoryStatus(self.largeTrunkData.partNumber,largeTrunkStatusArray)
				}
		  }

		 handleATPStoreAvailability(response,smallTrunkSkusFormed,largeTrunkSkusFormed) {
	            let smallTrunkATPArray = [];
	            let largeTrunkATPArray = [];
	            let filteredResponse = self.removeDuplicate(response);
	            $.each(filteredResponse, (k, v) => {
                    if(smallTrunkSkusFormed.indexOf(v.itemCode) != -1) {
	                        smallTrunkATPArray.push(v);
	                  }
                    if(largeTrunkSkusFormed.indexOf(v.itemCode) != -1) {
	                        largeTrunkATPArray.push(v);
	                  }
	            });
	            if(smallTrunkATPArray.length === smallTrunkSkusFormed.length) {
	                  self.displayATPStatus(self.smallTrunkData.partNumber,smallTrunkATPArray)
	            }
	            else {
	                  let trunkElement= $(document).find("div[data-sku-id='" + self.smallTrunkData.partNumber +"']");
	                  self.displayUnavailableMessage(trunkElement);
	            }
	            if(largeTrunkATPArray.length === largeTrunkSkusFormed.length) {
	                  self.displayATPStatus(self.largeTrunkData.partNumber,largeTrunkATPArray);
	            }
	            else {
	                  let trunkElement= $(document).find("div[data-sku-id='" + self.largeTrunkData.partNumber +"']");
	                  self.displayUnavailableMessage(trunkElement);
	            }
	      }

	      removeDuplicate(arr) {
	                let cleaned = [];
	                arr.forEach(function(itm) {
	                    let unique = true;
	                    cleaned.forEach(function(itm2) {
	                        if (_.isEqual(itm, itm2)) unique = false;
	                    });
	                    if (unique)  cleaned.push(itm);
	                });
	                return cleaned;
	      }

	      displayATPStatus(trunkSku, trunkATPResponse) {
	            let productUnavailable = [];
	            $.each(trunkATPResponse, (k, v) => {
	                  if(v.availabilityStatus == "T" || v.availabilityStatus == "S" || v.availabilityStatus == "B") {
	                        productUnavailable.push("false");
	                  }
	            });
	            if(productUnavailable.indexOf("false") > -1) {
	                  let trunkElement= $(document).find("div[data-sku-id='" + trunkSku +"']");
	                  self.displayUnavailableMessage(trunkElement);
	            }
	      }

	      displayUnavailableMessage (trunkElement) {
	            $(trunkElement).css("pointer-events","none");
	            let $statusLine=$(trunkElement).find('.product-status');
	            $statusLine.addClass('unavailable')

	      }
		handleInventoryStatus(trunkSkuId,dependentProductInventoryResponseArray)
		{
			let backorderDates=[];
			let backorderFlag=false;
			if(dependentProductInventoryResponseArray.length) {
			 $.each(dependentProductInventoryResponseArray, (k, v) => {
				  let inventoryStatusObject=v;
		    	  if(inventoryStatusObject.inventoryStatus == 'Backorderable' || inventoryStatusObject.inventoryStatus == 'Backordered')
		    	  {
		    		backorderFlag=true;
					let dateStr=inventoryStatusObject.backOrderDate;
					let a=dateStr.split(" ");
					let d=a[0].split("-");
					// let t=a[1].split(":");
					// let date = new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
				    let date = new Date(d[0],(d[1]-1),d[2]);
		    		backorderDates.push(date);
		    	  }
		    	  else if(inventoryStatusObject.inventoryStatus=='Sold Out' || inventoryStatusObject.inventoryStatus=='Unavailable' || inventoryStatusObject.inventoryStatus=='noLongerAvailable' )
		    	 {
		    		  backorderFlag=false;
		    	 }
		      });
		      if(backorderFlag)
		      {
		    	  backorderDates.sort(self.sortDates);
				  let maxDate = backorderDates[backorderDates.length-1];
		    	  let trunkElement= $(document).find("div[data-sku-id='" + trunkSkuId +"']");
		    	  $(trunkElement).find('.product-status').removeClass('hide').addClass('backordered');
		    	  $(trunkElement).find('.trunkBackorderedDate').text(formatDate.formatToNewDate(maxDate));
		    	  //Handle back order logic
		      }

			}
		}
	/** Logic for checking inventory status for Trunk data Ends **/

	/** Logic for handling composite image Starts**/
		handleCompositeTrunkThumbnail(){

			let trunkElements=$(".offerSize .choice");
			let compositeBaseImage=self.parseWidScaleProperties(self.compositeImageBaseUrl);
			if(trunkElements.length > 0){
				$(trunkElements).each(function(index,element){
					let imgElement=$(element).find("img");
					let compositeTrunkBaseImage="";
					if($(element).attr('id') == 'smallTrunk')
					{
						compositeTrunkBaseImage=compositeBaseImage+self.layer1smallTrunkUrl;
					}
					if($(element).attr('id') == 'largeTrunk')
					{
						compositeTrunkBaseImage=compositeBaseImage+self.layer1largeTrunkUrl;
					}
					$(imgElement).attr("src",compositeTrunkBaseImage+self.layerComp);

				});
			}
		}
		handleImageCompositeImage($choiceElement)
		{

			if(typeof $choiceElement == "undefined" || $choiceElement == null || $choiceElement == ""){
				this.compositeImageBaseUrl=this.compositeImageBaseUrl+self.parseSKUIDProperty(this.layer4DollUrl,this.dollId);

			}
			if(typeof $choiceElement != "undefined" && $choiceElement != null && $choiceElement != ""){
				let parentElement=$choiceElement.parents('.option');
				if($choiceElement.parents('.option').hasClass('offerSize'))
				{

					this.compositeImageBaseUrl=self.parseWidScaleProperties(this.compositeImageBaseUrl);

				}
				if($choiceElement.attr('id') == 'smallTrunk')
				{
					this.compositeImageBaseUrl=this.compositeImageBaseUrl+this.layer1smallTrunkUrl;
				}
				if($choiceElement.attr('id') == 'largeTrunk')
				{
					this.compositeImageBaseUrl=this.compositeImageBaseUrl+this.layer1largeTrunkUrl;
				}
				if(parentElement.attr('data-selection-id') == 'bundlelist0')
				{
					let skuId=parentElement.attr('data-sku-id');
					this.layer2Bundle1Url=self.parseSKUIDProperty(this.layer2Bundle1Url,skuId)
					this.compositeImageBaseUrl=this.compositeImageBaseUrl+this.layer2Bundle1Url;
				}
				if(parentElement.attr('data-selection-id') == 'bundlelist1')
				{
					let skuId=parentElement.attr('data-sku-id');
					this.layer2Bundle2Url=self.parseSKUIDProperty(this.layer2Bundle2Url,skuId)
					this.compositeImageBaseUrl=this.compositeImageBaseUrl+this.layer2Bundle2Url;
				}
			}

			if(window.matchMedia('(max-width: 767px)').matches){
				self.defaultDisplay.find('.gt-product-doll-image img').attr("src", this.compositeImageBaseUrl+self.layerComp+"&wid=600");
			}else{
				self.defaultDisplay.find('.gt-product-doll-image img').attr("src", this.compositeImageBaseUrl+self.layerComp);
			}

		}
		clearImageCompositeOnChange($changeElement)
		{
			if(typeof $changeElement != "undefined" && $changeElement != null && $changeElement != ""){
				let $parentElement=$changeElement.parents('.option')
				let selectionType=$parentElement.attr('data-selection-id');
				let selectedSkuId=$parentElement.attr('data-sku-id');
				if(selectionType == 'offerSize')
				{
					this.compositeImageBaseUrl=this.compositeImageBaseUrl.split('?')[0];
					this.compositeImageBaseUrl=this.compositeImageBaseUrl+'?'+self.parseSKUIDProperty(this.layer4DollUrl,this.dollId);
					self.defaultDisplay.find('.gt-product-doll-image img').attr("src", this.compositeImageBaseUrl+self.layerComp);
				}
				if(selectionType == 'bundlelist0')
				{

					this.compositeImageBaseUrl=this.compositeImageBaseUrl.replace(this.layer2Bundle1Url,'');
					this.layer2Bundle1Url=this.layer2Bundle1Url.replace(selectedSkuId,'SKU_ID');
					self.defaultDisplay.find('.gt-product-doll-image img').attr("src", this.compositeImageBaseUrl+self.layerComp);
				}
				if(selectionType == 'bundlelist1')
				{
					this.compositeImageBaseUrl=this.compositeImageBaseUrl.replace(this.layer2Bundle2Url,'');
					this.layer2Bundle2Url=this.layer2Bundle2Url.replace(selectedSkuId,'SKU_ID');
					self.defaultDisplay.find('.gt-product-doll-image img').attr("src", this.compositeImageBaseUrl+self.layerComp);
				}

			}

		}
		parseWidScaleProperties(compositeImageDollBaseUrl)
		{
			let parameters=["wid","scl","hei"];
			let compositeImageDollUrl=compositeImageDollBaseUrl;
			if(typeof compositeImageDollUrl != "undefined" && compositeImageDollUrl != null && compositeImageDollUrl !="")
			{
				parameters.forEach(function(value){
					compositeImageDollUrl=compositeImageDollUrl
						.replace(new RegExp('[?&]' + value + '=[^&#]*(#.*)?$'), '$1')
						.replace(new RegExp('([?&])' + value + '=[^&]*&'), '$1');
				});
			}
			return compositeImageDollUrl;
		}

	/** Logic for handling composite image Ends**/


	parseSKUIDProperty(layerParam,skuid)
	{

		if(typeof layerParam != "undefined" && layerParam != null && layerParam != ""){
			return layerParam.replace(/SKU_ID/g,skuid)
		}
		else
		{
			return "";
		}
	}
	/** Logic for Dynamic Pricing Starts */
		calculateTrunkPricing(){
			let deferred = $.Deferred();
			let combinedTrunkSkus=self.getAllTrunkCombinedPricingSkus();
			deferred.resolve(self.productOfferprice(combinedTrunkSkus));
			return deferred.promise();
		}
		loadTrunksWithPricingData(formatedPricing){
			$(".trunkData").each(function(index,item){
				let trunkData=JSON.parse($(item).html());
				let trunkSku=trunkData.partNumber;
				let displayableItemsPricing=new Object();
				let nonDisplayableItemsPrice=0.0;
				let trunkPrice;
				let trunkElement=$("#gt-bundle-selection-wrapper").find('[data-sku-id=' + trunkSku + ']');
				$.each(trunkData.gtDisplayableItems, function(index,value) {
					if(formatedPricing.hasOwnProperty(value.partNumber))
					{
						displayableItemsPricing[value.partNumber]=formatedPricing[value.partNumber];
					}
				});
				$.each(trunkData.gtNonDisplayableItems, function(index,value) {
					if(formatedPricing.hasOwnProperty(value.partNumber))
					{
						nonDisplayableItemsPrice=nonDisplayableItemsPrice+parseFloat(formatedPricing[value.partNumber]);
					}
				});
				if(trunkData.attributes.PlaypackCount == 1)
				{
					if(self.lowestPlayPack1 == "NA")
					{
						trunkElement.find('.trunkNAPricingText').removeClass('hide');
					}
					else{
						trunkPrice= parseFloat(self.doll_price)+parseFloat(self.lowestPlayPack1)+nonDisplayableItemsPrice;
						trunkElement.attr('data-displayable-pricing',JSON.stringify(displayableItemsPricing));
				    	trunkElement.find('.stock-price').text(trunkPrice);
						trunkElement.find('.trunkPricingText').removeClass('hide');
					}
				}
				if(trunkData.attributes.PlaypackCount == 2)
				{
					if(self.lowestPlayPack2 == "NA")
					{
						trunkElement.find('.trunkNAPricingText').removeClass('hide');
					}
					else{
						trunkPrice= parseFloat(self.doll_price)+parseFloat(self.lowestPlayPack1)+parseFloat(self.lowestPlayPack2)+nonDisplayableItemsPrice;
						trunkElement.attr('data-displayable-pricing',JSON.stringify(displayableItemsPricing));
			    		trunkElement.find('.stock-price').text(trunkPrice);
						trunkElement.find('.trunkPricingText').removeClass('hide');
					}
				}

			});
		}
		getAllTrunkCombinedPricingSkus(){
			let combinedTrunkSkus=[];
			$(".trunkData").each(function(index,item){
				let trunkData=JSON.parse($(item).html());
				let trunkItems=[];
				$.each(trunkData.gtDisplayableItems, function(index,value) {
					trunkItems.push(value.partNumber)
				});
				$.each(trunkData.gtNonDisplayableItems, function(index,value) {
					trunkItems.push(value.partNumber)
				});
				combinedTrunkSkus.push.apply(combinedTrunkSkus, trunkItems);
			});
			return combinedTrunkSkus;
		}
		findCheapestPlayPacks(products){
			if(products.length >=2){
				let priceSortedProducts=products.sort(self.sortByProperty('targetPricing'));
				self.lowestPlayPack1=priceSortedProducts[0].targetPricing;
				self.lowestPlayPack2=priceSortedProducts[1].targetPricing;
			}
			else if(products.length == 1)
			{
				self.lowestPlayPack1=products[0].targetPricing;
				self.lowestPlayPack2="NA";
			}
			else
			{
				self.lowestPlayPack1=self.lowestPlayPack2="NA";
			}

		}
		sortByProperty (property) {
			return function (x, y) {
				return ((x[property] === y[property]) ? 0 : ((x[property] > y[property]) ? 1 : -1));
			};
		}
		setPlayPackPricing(ele){
			let $ele=$(ele).closest(".choice ");
			if($ele.attr('id') == "largeTrunk")
				{
					let choiceElements1=$("#categoryBundle1").find(".choice");
					choiceElements1.each(function (index,element) {
						let largeTrunkAdderDisplayPrice=$(element).find(".largeTrunkAdderDisplayPrice").text();
						$(element).find(".displayPrice").text(largeTrunkAdderDisplayPrice);
						let largeTrunkAdderPrice=$(element).find(".largeTrunkAdderPrice").text();
						$(element).find(".stock-price").text(largeTrunkAdderPrice);
					 });
					let choiceElements2=$("#categoryBundle2").find(".choice");
					choiceElements2.each(function (index,element) {
						let largeTrunkAdderDisplayPrice=$(element).find(".largeTrunkAdderDisplayPrice").text();
						$(element).find(".displayPrice").text(largeTrunkAdderDisplayPrice);
						let largeTrunkAdderPrice=$(element).find(".largeTrunkAdderPrice").text();
						$(element).find(".stock-price").text(largeTrunkAdderPrice);
					 });
				}
			if($ele.attr('id') == "smallTrunk")
				{
				let choiceElements1=$("#categoryBundle1").find(".choice");
				choiceElements1.each(function (index,element) {
					let smallTrunkAdderDisplayPrice=$(element).find(".smallTrunkAdderDisplayPrice").text();
					$(element).find(".displayPrice").text(smallTrunkAdderDisplayPrice);
					let smallTrunkAdderPrice=$(element).find(".smallTrunkAdderPrice").text();
					$(element).find(".stock-price").text(smallTrunkAdderPrice);
				 });
				let choiceElements2=$("#categoryBundle2").find(".choice");
				choiceElements2.each(function (index,element) {
					let smallTrunkAdderDisplayPrice=$(element).find(".smallTrunkAdderDisplayPrice").text();
					$(element).find(".displayPrice").text(smallTrunkAdderDisplayPrice);
					let smallTrunkAdderPrice=$(element).find(".smallTrunkAdderPrice").text();
					$(element).find(".stock-price").text(smallTrunkAdderPrice);
				 });
				}
		}
		getTotalPrice(currentPrice,totalPrice)
		 {
			if (currentPrice.length > 0)
			{
	            let priceSubParse = currentPrice.text().replace(/[$,]+/g, "");
	            let total = parseFloat(isNaN(priceSubParse) ? 0 : priceSubParse);
	            totalPrice -= total.toFixed(2);
	            return parseFloat(totalPrice);
			}
			else
				return parseFloat(totalPrice);
		}
		resetPriceSelect(){
			let prevSelectedChoice=self.prevChangedPriceStack[0];
			let prevSelectedSku=$(prevSelectedChoice).attr("data-sku-id");
			$(prevSelectedChoice).parents(".option").find('[data-sku-id='+prevSelectedSku+']').addClass('priceSelect');

		}
	    animate() {
		    $('#animatePricing').removeClass().addClass(self.pricingAnimation + ' animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
		    	$('#animatePricing').removeClass();
		    });
		  };
	/** Logic for Dynamic Pricing Starts */

}
window.onbeforeunload = function() {
sessionStorage.setItem("pdp","false");
};
let self;
const request = new ajaxRequest();
const evtBinding = new eventBinding();
const apiConfigInst = new apiConfig();
const formatDate = new dateFormat();
const handleBarsHelperInst = new handleBarsHelper();
const handleBarTemplateInst = new handleBarTemplate();
const apiData = new apiConfig().getApiConfig;
const gtProductInstance = new gtProduct();
gtProductInstance.init();


$(document).ready(function() {
	gtProductInstance.loadTrunkJsonData();
	$('.gt-bundle-selection-outer-wrapper').find('span.gt-product-doll-name').attr("id", getCookie('gt-product-doll-id')).html(localStorage.getItem('gt-product-doll-name'));
	$('.gt-bundle-selection-outer-wrapper').find(".total-price").text(0);
    $('#gt-bundle-selection-wrapper').find('span.gt-quiz-display-recipient').html(getCookie('gt-quiz-display-recipient'));
    var footerTop = $('.xf-content-height .footer').offset().top;
    var wrapperTop = $('.gt-bundle-selection-wrapper').offset().top;
    let dollHeight = $('.gt-product-doll-image').outerHeight();
    let scrollFlags = 0,
        loadFlag = 1;

    var tops = footerTop - (dollHeight + wrapperTop);

    $('.gt-bundle-selection-wrapper span.change, .gt-bundle-selection-wrapper, .gt-accordion-block').click(function() {
        scrollFlags = 1;
    });
    $('.gt-bundle-selection-wrapper span.extras-close').click(function () {
        if (window.matchMedia('(min-width: 992px)').matches) {
                        $('.gt-product-doll-image img').animate({
                                        'margin-top': '-155px'
                        });
        }
    });

    $('.gt-bundle-selection-wrapper span.extras-close').click(function() {
              if (window.matchMedia('(min-width: 992px)').matches) {
                  $('.gt-product-doll-image img').animate({
                 'margin-top': '-155px'
              });
              }
	});

	$(document).on('click','.hearAidRadio', function () {
		const setAttr = {
			'data-variantid':$(this).attr('data-variantid'),
			'data-price':  $(this).attr('data-price'),
			'data-variantinventry' : $(this).attr('data-variantinventry'),
			'data-itematpreceiptid': $(this).attr('data-itematpreceiptid'),
			'data-backorderdate':  $(this).attr('data-backorderdate'),
		}
		let $parentEle = $(this).parents('.choice');
		let $parentElemts = $(this).parents('#hearingAid');
		$parentEle.attr(setAttr);
		$parentElemts.attr(setAttr)
		$(this).siblings().removeClass('activeRadio');
		$(this).addClass('activeRadio');
		$(this).find('input[type=radio]').prop('checked', true);
		self.checkInventoryattrFlag($(this))
	});


    $(window).scroll(function() {

		scrollImage();
    });

    function scrollImage() {
        if (window.matchMedia('(min-width: 992px)').matches) {
        	 let letterConTop = $('.doll-img').offset().top;
            if (scrollFlags == 1 || loadFlag == 1) {
                footerTop = $('.xf-content-height .footer').offset().top;


                if ($('.gt-product-doll-image').css('display') == 'none') {
                    dollHeight = $('.gt-ltr-left').outerHeight();
                } else {
                    dollHeight = $('.gt-product-doll-image').outerHeight();
                }
                tops = footerTop - (dollHeight + wrapperTop + 20);
            }

            var windowTop = $(document).scrollTop();
            var scrollPage = windowTop + dollHeight + wrapperTop + 10;

            if (scrollPage >= footerTop) {
                $('.gt-product-doll-image, .gt-ltr-left').css({
                    position: 'absolute',
                    'margin-top': tops
                });
            } else {
                $('.gt-product-doll-image').css({
                    position: 'fixed',
                    'margin-top': 'auto'
                });
				if ( windowTop + letterConTop <= letterConTop + 50 ) {
					$('.gt-ltr-left').css({
									position: 'fixed',
									'margin-top': 0
					});
					$('.gt-product-doll-image img').css({
									'margin-top': 0
					});
				} else if (windowTop > letterConTop) {
					$('.gt-ltr-left').css({
									position: 'fixed',
									'margin-top': '-100px'
					});
					$('.gt-product-doll-image img').css({
									'margin-top': 0
					});
				}


            }
        }
    }
});
