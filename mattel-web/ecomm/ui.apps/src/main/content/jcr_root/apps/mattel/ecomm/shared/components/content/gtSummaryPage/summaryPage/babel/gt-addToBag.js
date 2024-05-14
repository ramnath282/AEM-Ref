import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';

import { exceptionHandler } from '../shared/flickerMessage';
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

class gtAddToBag {
    constructor() {
        self = this;
        this.storeIdValue = "";
        this.partNumber = "";
        this.descriptionArray = [];
        this.descriptionData = "";
		this.skuIdArray = [];
		this.itemObj = [];
        this.recipient = getCookie('gt-quiz-display-recipient');
        this.dollSkuId = getCookie('gt-product-doll-id');
        this.dollName = localStorage.getItem('gt-product-doll-name');
		this.envPartNumber= "";
		this.envObject = {};
		this.getStoredollattr = $('.choiceSelected .gt-product-doll-name');
		this.letterPartNumber = "";
		this.letterContent = "";
		this.letterEdited = "";
		this.itemList= $(".priceSelect");
		$('#addToBagbutton').attr("disabled", false);
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
    }

    bindingEventsConfig() {
        let eventsArr = {
			'click #addToBagbutton': 'addToCart'
        };
        return eventsArr;
    }

   bundleAddition() {
	  let keys = Object.keys(localStorage);
	   for(var i=0; i<keys.length; i++) {
	   		if(keys[i].startsWith("bundlelist")) {
	   			let bundlelist = localStorage.getItem(keys[i]);
	   			if (typeof bundlelist != "undefined" && bundlelist != null && bundlelist != "") {
	   			let parsedJSON = JSON.parse(bundlelist);
	   			if (typeof parsedJSON.skuId != "undefined" && parsedJSON.skuId != null && parsedJSON.skuId != "") {
	   				self.skuIdArray.push(parsedJSON.skuId.toString());
	   			}
	   			if (typeof parsedJSON.title != "undefined" && parsedJSON.title != null && parsedJSON.title != "") {
	   				self.descriptionArray.push(parsedJSON.title);
	   			}
	   			}
	   		}
	   	}
   }

   addOnAddition() {
	   let keys = Object.keys(localStorage);
	   for(var i=0; i<keys.length; i++) {
	   		if(keys[i].startsWith("addon")) {
	   			let addon = localStorage.getItem(keys[i]);
	   			if (typeof addon != "undefined" && addon != null && addon != "") {
	   			let parsedJSON = JSON.parse(addon);
	   			if (typeof parsedJSON.skuId != "undefined" && parsedJSON.skuId != null && parsedJSON.skuId != "") {
	   				self.skuIdArray.push(parsedJSON.skuId.toString());
	   			}
	   			if (typeof parsedJSON.title != "undefined" && parsedJSON.title != null && parsedJSON.title != "") {
	   				let titleSplit = parsedJSON.title.split(":")
	   				self.descriptionArray.push(titleSplit[0]);
	   			}
	   			}
	   		}
	   	}
   }

   offerSize() {
	   let offerSize = localStorage.getItem("offerSize");
	   if (typeof offerSize != "undefined" && offerSize != null && offerSize != "") {
	   		let parsedJSON = JSON.parse(offerSize),
	   		    partNumberJSON = parsedJSON.skuId;
	   		if (typeof partNumberJSON != "undefined" && partNumberJSON != null && partNumberJSON != "") {
				self.partNumber = $(`.choice[data-sku-id= ${partNumberJSON}]`).data("variant-id");
	   		}
	   	}
   }

   gtLetterDetails() {
	   let letter = localStorage.getItem("gtLetterDetails");
	   if (typeof letter != "undefined" && letter != null && letter != "") {
	   		let parsedJSON = JSON.parse(letter),
	   		    partNumberJSON = parsedJSON.partNumber;

	   		if (typeof partNumberJSON != "undefined" && partNumberJSON != null && partNumberJSON != "") {
	   			self.letterPartNumber = partNumberJSON.toString();

	   		}

	   	}
   }

   gtNonDisplayableItems() {
	    let nonDisplayable = localStorage.getItem("gtNonDisplayableItems");
	   if (typeof nonDisplayable != "undefined" && nonDisplayable != null && nonDisplayable != "") {
	   		let parsedJSON = JSON.parse(nonDisplayable);
	   		for (var i=0; i < parsedJSON.length; i++) {
			 let getVariendId = parsedJSON[i].variantId,
			     getAssociationType = parsedJSON[i].associationType,
			     getProductType = parsedJSON[i].product_type,
				 getInventory = parsedJSON[i].variant_inventorystatus ? parsedJSON[i].variant_inventorystatus: '',
				 getBackorderdate = parsedJSON[i].variant_backorderdate ? parsedJSON[i].variant_backorderdate : '',
				 getItematpreceiptid = parsedJSON[i].variant_itematpreceiptid ? parsedJSON[i].variant_itematpreceiptid: '';

			if(parsedJSON[i].KitOption == "KitEnvelope") {
				if (typeof getVariendId != "undefined" && getVariendId != null && getVariendId != "") {
					self.envObject.variendId = getVariendId;
					self.envObject.association = getAssociationType;
					self.envObject.productType = getProductType;
					self.envObject.inventorystatus = getInventory;
					self.envObject.backorderdate = getBackorderdate;
					self.envObject.itematpreceiptid = getItematpreceiptid;


				}
			}
			else {
			if (typeof getVariendId != "undefined" && getVariendId != null && getVariendId != "") {
				self.skuIdArray.push(getVariendId);
			  }
			 }
			}
	   	}

   }

	removeDuplicates(arr) {
		var cleaned = [];
		arr.forEach(function(itm) {
			var unique = true;
			cleaned.forEach(function(itm2) {
				if (_.isEqual(itm, itm2)) unique = false;
			});
			if (unique)  cleaned.push(itm);
		});
		return cleaned;
	}

   addingToCart()
   {
	   let landingPage = $('#addToBagbutton').attr('href'),
	       dynamicImageURL = $('.gt-container>.row div.gt-product-doll-image img').attr('src'),
	       getStoreId = localStorage.getItem('storeSelected'),
		   getTrankDetail = JSON.parse(localStorage.getItem("giftTrank")),
		   referenceId = getTrankDetail.variantid+Math.random().toString(16).slice(2),
		   letterValue = localStorage.getItem("letterContent");
	   localStorage.setItem("dynamicImageURL",dynamicImageURL);

	   if (typeof letterValue != "undefined" && letterValue != null && letterValue != "") {
	   self.letterContent = letterValue;
	   }
	   let modified = localStorage.getItem("letterEdited");
	   if (typeof modified != "undefined" && modified != null && modified != "") {
		   self.letterEdited = modified;
	   }
      let ajaxOption = apiConfigInst.getApiConfig("addToBag").addProductToBag;
      ajaxOption.data = {
 		"items": [
		{
			"quantity": 1,
			"id": parseFloat(getTrankDetail.variantid),
			"properties": {
					  "reference": referenceId,
					  "type": "parent",
					  "productType":getTrankDetail.productType,
					  "DescriptionData":self.descriptionData,
					  "configurationId":getTrankDetail.skuid,
					  "ImageURL":dynamicImageURL,
					  "FromGT":"true",
					  "RetailStoreId":self.storeIdValue,
					  "PrintImageURL":'',
					  "variant_inventorystatus": getTrankDetail.variantinventry,
					  "variant_backorderdate": getTrankDetail.backorderdate,
					  "variant_itematpreceiptid": getTrankDetail.itematpreceiptid,
					  "components": []
			}
		  }
       ]
	}


		self.descriptionArray = self.removeDuplicates(self.descriptionArray);
		let descriptionData = "";
		for (var a=0; a < self.descriptionArray.length; a++) {
			if(a < self.descriptionArray.length-1) {
				descriptionData = descriptionData + self.descriptionArray[a] + '|';
			}
			else {
				descriptionData = descriptionData + self.descriptionArray[a];
			}
		}

        ajaxOption.data.items[0].properties.DescriptionData = descriptionData;
		if($('.select-gt-product').length) {
			let $this = $('.select-gt-product'),
			    price = parseFloat($this.data('price')),
			    kitoption = $this.data("kitoption"),
				kitdisplayable = $this.data('kitdisplayable');
			if($this.attr('data-variantid')) {
				let itemObj =  {
					  "quantity":1,
					  "id":  parseFloat($this.data('variantid')),
					  "properties": {
								"reference":referenceId,
								"type": "child",
								"productType": $this.data('product-type'),
								"association": $this.data('association'),
								"sku": $this.data('sku-id'),
								"variant_inventorystatus": $this.data('variantinventry'),
								"variant_backorderdate": $this.data('backorderdate'),
								"variant_itematpreceiptid": $this.data('itematpreceiptid'),

					  }
					}

	   price > 0 || (price <= 0 && kitdisplayable == 'Y' && kitoption == 'KitProduct' ) ?
	   ajaxOption.data.items.push($.extend({},itemObj)) : ajaxOption.data.items[0].properties.components.push($.extend({},itemObj))

	     }
	}

    $('.priceSelect').each(function(){

		if($(this).attr('data-variantid'))  {
	 let price = parseFloat($(this).data('price')),
	     kitoption = $(this).data("kitoption"),
	     kitdisplayable = $(this).data('kitdisplayable');
 	 let itemObj =  {
			"quantity": 1,
			"id":  parseFloat($(this).data('variantid')),
			"properties": {
					  "reference":referenceId,
					  "type": "child",
					  "productType": $(this).data('product-type') ? $(this).data('product-type') : '',
					  "association": $(this).data('association') ? $(this).data('association') : '',
					  "sku": $(this).data('sku-id'),
					  "variant_inventorystatus": $(this).data('variantinventry') ? $(this).data('variantinventry') : '',
					  "variant_backorderdate": $(this).data('backorderdate') ? $(this).data('backorderdate') : '',
					  "variant_itematpreceiptid": $(this).data('itematpreceiptid') ? $(this).data('itematpreceiptid') : '',

			}
		  }

		  price > 0 || (price <= 0 && kitdisplayable == 'Y' && kitoption == 'KitProduct' ) ?
		 ajaxOption.data.items.push($.extend({},itemObj)) : ajaxOption.data.items[0].properties.components.push($.extend({},itemObj));

    	}
	 })

	 if($('.nonDisplayableItemsContainer').length) {
		$('.nonDisplayableItems').each(function(){
			if($(this).attr('data-variantid')) {
				let price = parseFloat($(this).data('price')),
				    kitoption = $(this).data("kitoption"),
				    kitdisplayable = $(this).data('kitdisplayable');
				if($(this).attr('data-variantid') == self.envObject.variendId) {

					var itemObj =  {
						"quantity":1,
						"id": parseFloat($(this).data('variantid')),
						"properties": {
								  "reference":referenceId,
								  "type": "child",
								  "productType": $(this).data('product-type'),
								  "association": $(this).data('association'),
								  "sku":$(this).data('sku-id'),
								  "variant_inventorystatus":$(this).data('variantinventry'),
								  "variant_backorderdate": $(this).data('backorderdate'),
								  "variant_itematpreceiptid": $(this).data('itematpreceiptid'),
								  "notes":{
									"0":{
										"GIFT_TRUNK_RECIPIENT": self.recipient
									}
								}

						}
					  }
				} else {
					var itemObj =  {
						"quantity": 1,
						"id":  parseFloat($(this).data('variantid')),
						"properties": {
								  "reference":referenceId,
								  "type": "child",
								  "productType": $(this).data('product-type'),
								  "association": $(this).data('association'),
								   "sku": $(this).data('sku-id'),
								  "variant_inventorystatus": $(this).data('variantinventry'),
								  "variant_backorderdate": $(this).data('backorderdate'),
								  "variant_itematpreceiptid": $(this).data('itematpreceiptid'),

						}
					  }
				}

		price > 0 || (price <= 0 && kitdisplayable == 'Y' && kitoption == 'KitProduct' ) ?
		ajaxOption.data.items.push($.extend({},itemObj)) : ajaxOption.data.items[0].properties.components.push($.extend({},itemObj));

        	}
		 })
		}

	 if ($('#letterContainer').length) {
		let $this = $('#letterContainer.choiceSelected'),
		    price = parseFloat($this.data('price')),
		    kitoption = $this.data("kitoption"),
		    kitdisplayable = $this.data('kitdisplayable');
		let itemObj =  {
			"quantity": 1,
			"id": parseFloat($this.data('variantid')),
			"properties": {
					  "reference":referenceId,
					  "type": "child",
					  "productType":  $this.data('product-type') ?  $this.data('product-type') : '',
					  "association":  $this.data('association') ?  $this.data('association') : '',
					  "sku": $this.data('sku-id'),
					  "variant_inventorystatus":  $this.data('variantinventry') ?  $this.data('variantinventry') : '',
					  "variant_backorderdate":  $this.data('backorderdate') ? $this.data('backorderdate') : '',
					  "variant_itematpreceiptid":  $this.data('itematpreceiptid') ? $this.data('itematpreceiptid') : '',
					  "notes":{
						"0":{ "GIFT_TRUNK_LETTER_TEXT":self.letterContent},
						"1":{ "GIFT_TRUNK_LETTER_EDITED":self.letterEdited}
					  }

			}
		  }
		  price > 0 ||  (price <= 0 && kitdisplayable == 'Y' && kitoption == 'KitProduct' ) ?
		  ajaxOption.data.items.push($.extend({},itemObj)) : ajaxOption.data.items[0].properties.components.push($.extend({},itemObj));

	}

		request.ajaxCall(ajaxOption).then(data => {
			try {
				   const responseATP = typeof data == "string" ? JSON.parse(data) : data;
				 if(typeof responseATP.items[0].product_id != "undefined" && responseATP.items[0].product_id != null && responseATP.items[0].product_id != "") {
				   window.location.href = landingPage;
				} else if(data.hasOwnProperty("errorCode")) {
					$('#addToBagbutton').attr("disabled", false);
				  let errorMessage = data.errorCode;
				  exceptionHandler("error", "Failed to add Products to Cart");
				  console.log(`Failed to add Product to Cart: ${errorMessage}`);
				}
			} catch (e) {
				  $('#addToBagbutton').attr("disabled", false);
				  let errorMessage = e.message;
				  exceptionHandler("error", "Failed to add Products to Cart");
				  console.log(`Failed to add Product to Cart: ${errorMessage}`);
				}

		})
	 .fail(function(err){
		 $('#addToBagbutton').attr("disabled", false);
		   window.global.errorHandling.cartAPI(err);
			  })
	  .catch(error => {
		  $('#addToBagbutton').attr("disabled", false);
		   window.global.errorHandling.cartAPI(error);
			});
   }


   addToCart() {
	$('#addToBagbutton').attr("disabled", true);
   	self.offerSize();
   	if (typeof self.dollName != "undefined" && self.dollName != null && self.dollName != "") {
   		self.descriptionArray.push(self.dollName);
   	}
   	if (typeof self.dollSkuId != "undefined" && self.dollSkuId != null && self.dollSkuId != "") {
   		self.skuIdArray.push(self.dollSkuId.toString());
   	}
   	self.bundleAddition();
   	self.addOnAddition();
	self.gtLetterDetails();
	self.gtNonDisplayableItems();

   	let storeId = localStorage.getItem("associateCode");
   	if (typeof storeId != "undefined" && storeId != null && storeId != "") {
   		self.storeIdValue = storeId;
   	} else {
   		self.storeIdValue = "";
   	}
	   self.addingToCart();
  }
}

   let self;
   const request = new ajaxRequest();
   const evtBinding = new eventBinding();
   const apiConfigInst = new apiConfig();
   const { setStorage, getStorage, cartAPI} = window.global;
   const formatDate = new dateFormat();
   const handleBarsHelperInst = new handleBarsHelper();
   const handleBarTemplateInst = new handleBarTemplate();
   const apiData = new apiConfig().getApiConfig;
   const gtAddToBagInstance = new gtAddToBag();
