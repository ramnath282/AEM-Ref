import {
   crossWordCheck,
   inputTextValidation,
   DHFormSession
} from '../shared/dh-form-validation';
import constant from '../shared/constant';
((() => {
    	// defining configurations
	     const config = {
		        el: '.dh-wrapper .dh-container .dh-review-block',
		        DHFormData:'',
		        specialAddonsTemplate : '#specialAddonsTemplate',
		        specialAddonsContainer : '.specialAddonsContainer',
		        emptyContentPlaceHolder : ''
	        };
	
		class dhReviewSummary {
		    constructor() {
				self = this;
				window.dh.eventBinding.bindLooping(this.bindingEventsConfig(), this);
				config.DHFormData=JSON.parse(sessionStorage.getItem('DHFormData')); 
				config.emptyContentPlaceHolder=$('.dh-review-block').attr('data-emptyContentPlaceHolder');
		    }
		    init() {
		    	if(!(config.DHFormData)){
		    		let pagePath =  $('.landingPage').data('landingpagepath');
                	if(pagePath){
                		window.location.href = pagePath;
                	}
		    	}
		    	$('.dh-review-block section').addClass('active');
		    	self.populateSessionStorageItems();
		    	console.log(config.DHFormData);
		    	let specialData=  sessionStorage.getItem("specialInstruction");
		    	$(config.el).find('#special-instructions').val(specialData);
		    	self.setBackURL();
		    }
		  
		    bindingEventsConfig() {
		        let eventsArr = {
		          'click .review-add-to-bag .dh-addToBag': 'addToBag',
		          'keyup #special-instructions': 'textareaLengthVal' 
		        };
		        return eventsArr;
		    }
		    
		    setBackURL(){
	        	let SkippedSubTreatment = DHFormSession('skippedSubTreatment', '', 'get');
	        	let SkippedSpclExtra = DHFormSession('skippedSpclExtra', '', 'get');
				if(SkippedSpclExtra.isSkipped && SkippedSpclExtra.prePageURL){
					let back_url = SkippedSpclExtra.prePageURL;  
	                	if(SkippedSubTreatment.isSkipped && SkippedSubTreatment.prePageURL){
	                		back_url = SkippedSubTreatment.prePageURL;
	                	}
	                	$('.dhSummaryPage .dh-back-btn').attr('href',back_url); 
				}
				DHFormSession('skippedSpclExtra', '', 'delete');
				DHFormSession('bckfromSummary', 'yes', 'set');
		    }
		    
		    populateSessionStorageItems(){
		    	let $reviewBlock= $(".dh-review-block section");
		    	let treatmentData=config.DHFormData.treatment;
		    	let subTreatments=[];
		    	let totalPrice=parseFloat(treatmentData.skuPrice);
		    	let smallPartsWarning='';
		    	/* Populate Quiz Data from session */
		    	_.each(config.DHFormData.quizData, (item) => {
		    			let inputTypeClass='.'+item.inputName;
		    			$reviewBlock.find(inputTypeClass).html(item.inputTitle);
		    		});
		    	/* Populate Treatment Data from session */
		    	$reviewBlock.find('.treatment-type').html(treatmentData.skuName);
		    	$reviewBlock.find('.treatment-price').html(treatmentData.skuPrice);
		    	$reviewBlock.find('.review-treatment-text').html(treatmentData.additionalDetails);
		    	$('.review-add-to-bag').find('.legal-age').html(treatmentData.legalAge);
		    	if(typeof treatmentData.smallPartsWarning != 'undefined'
		    		&& treatmentData.smallPartsWarning != '' 
		    		&& treatmentData.smallPartsWarning != null){
		    		smallPartsWarning=treatmentData.smallPartsWarning;
		    	}
		    	/* Populate subtreatment Data from session */
		    	_.each(config.DHFormData.subTreatmentData, (item) => {
		    		subTreatments.push(item.skuName);
		    		if(typeof item.smallPartsWarning != 'undefined'
			    		&& item.smallPartsWarning != '' 
				    	&& item.smallPartsWarning != null){
			    		smallPartsWarning=item.smallPartsWarning;
			    	}
	    		});
		    	if (subTreatments.length > 0) 
		    	{
		    		$reviewBlock.find('.sub-treatment-type').html(subTreatments.join(", ")); 
		    	}
		    	else
		    	{
		    		$reviewBlock.find('.sub-treatment-type').html(config.emptyContentPlaceHolder); 
		    	}
		    	console.log("SpecialAddons");
		    	console.log(config.DHFormData.specialExtrasData);
		    	/* Populate special extras Data from session */
		    	if(typeof config.DHFormData.specialExtrasData != 'undefined'
		    		&& config.DHFormData.specialExtrasData != null
		    		&& config.DHFormData.specialExtrasData.length > 0)
		    	{
					    	handleBarTemplateInst.loadTemplate(config.specialAddonsTemplate,config.specialAddonsContainer, config.DHFormData.specialExtrasData, 'append');
					    	_.each(config.DHFormData.specialExtrasData, (item) => {
					    		if(typeof item.smallPartsWarning != 'undefined'
						    		&& item.smallPartsWarning != '' 
							    	&& item.smallPartsWarning != null){
					    			
					    			smallPartsWarning=item.smallPartsWarning ;	
						    	}
					    		else if(typeof item.childSkuDetails != 'undefined'
					    				&& typeof item.childSkuDetails.smallPartsWarning != 'undefined'
						    			&& item.childSkuDetails.smallPartsWarning != '' 
								    	&& item.childSkuDetails.smallPartsWarning != null)
					    		{
					    			smallPartsWarning=item.childSkuDetails.smallPartsWarning;
					    		}
					    		
					    		if(item.childSkuDetails ){
					    			totalPrice=totalPrice+parseFloat(item.childSkuDetails.price);
					    		}
					    		else
					    		{
					    			totalPrice=totalPrice+parseFloat(item.price);
					    		}
					    	});
		    	}
		    	else
		    	{
		    		$(config.specialAddonsContainer).addClass('specialAddons-empty-content');
		    		$(config.specialAddonsContainer).html(config.emptyContentPlaceHolder);
		    	}
				
				$('.review-add-to-bag').find('.doll-price').html(totalPrice.toFixed(2));
				if(smallPartsWarning != ''){
					$('.review-add-to-bag').find('.small-Parts-warning img').attr('src',`http://${smallPartsWarning}`);
				}
		    }  
		    addToBag(ele, evt){
                evt.preventDefault();
                const $footerEle = $(ele).parents('.review-add-to-bag');
                const $btnEle =  $footerEle.find(".dh-next-btn");
                let $textareEle = $('.dh-review-block').find('textarea');
                if ($textareEle.length) {
                    let inputVal = $textareEle.val();
                    $btnEle.addClass('disabled');
                    //$formEle.addClass('data-loading');
                    crossWordCheck(inputVal, (msg) => {
                        if (msg == "fail") {
                            //$formEle.removeClass('data-loading');
                            let $errorEle = $('.review-aditional-desc').find('.err-msg');
                            $errorEle.removeClass('in');
                            $errorEle.filter('.restrict-message').addClass('in');
                            $btnEle.addClass('disabled').attr('aria-disabled',true);
                            return;
                        }
						self.getAddtocartPayload();
                    });
                }
				
			
            }
			
			getAddtocartPayload(){		
				$('.dh-btn-span #dh-addToBagbutton').attr("disabled", true);
                let dhGlobalDataJson = JSON.parse(sessionStorage.getItem('DHFormData'));
                let partNumber = (dhGlobalDataJson != null && dhGlobalDataJson.treatment.skuId != null) ? dhGlobalDataJson.treatment.skuId : ""
                let childpartNum = [], childPartNumAddons = [], itemsArr = [], notesArray = [], cartObj = {};
                if (dhGlobalDataJson) {
                    _.each(dhGlobalDataJson.subTreatmentData, (item) => {
                        let subTreatmentSkuId = (item.skuId ? item.skuId : "");
                        if (item.childSku == "Y") {
                            childpartNum.push(subTreatmentSkuId);
                        } else {
                            childPartNumAddons.push(subTreatmentSkuId);
                        }
                    });
                    _.each(dhGlobalDataJson.specialExtrasData, (item) => {
                        if (item.childSkuDetails) {
                            let childSkuId = (item.childSkuDetails.childSku ? item.childSkuDetails.childSku : "");
                            childpartNum.push(childSkuId);
                        } else {
                            let parentSku = (item.parentSku ? item.parentSku : "");
                            childPartNumAddons.push(parentSku);
                        }
                    });
                }
                let retailStoreId = "";
                if (dhGlobalDataJson && dhGlobalDataJson.retailFlowData) {
                    if (dhGlobalDataJson.retailFlowData.retailFlow) {
                        retailStoreId = (dhGlobalDataJson.retailFlowData.storeNumber) ? dhGlobalDataJson.retailFlowData.storeNumber : "";
                    }
                }
                let dollName = (dhGlobalDataJson && dhGlobalDataJson.dollName) ? dhGlobalDataJson.dollName : "",
                    eyeColor = (dhGlobalDataJson && dhGlobalDataJson.quizData.page3 && dhGlobalDataJson.quizData.page3.inputName == "dollEyeColor") ? dhGlobalDataJson.quizData.page3.inputVal : "",
                    hairColor = (dhGlobalDataJson && dhGlobalDataJson.quizData.page4 && dhGlobalDataJson.quizData.page4.inputName == "dollHairColor") ? dhGlobalDataJson.quizData.page4.inputVal : "",
                    specialInst = $.trim($("#special-instructions").val());
                let doll = {
                    "DH_DOLL_NAME": dollName,
                    "SKU": partNumber
                };
                notesArray.push(doll);
                let dollEyeColor = {
                    "DH_EYE_COLOR": eyeColor,
                    "SKU": partNumber
                };
                notesArray.push(dollEyeColor);
                let dollHairColor = {
                    "DH_HAIR_COLOR": hairColor,
                    "SKU": partNumber
                };
                notesArray.push(dollHairColor);
                if (specialInst) {
                    let specialInstruction = {
                        "DH_SPECIAL_INSTRUCTIONS": specialInst,
                        "SKU": partNumber
                    };
                    notesArray.push(specialInstruction);
                }
                if (dhGlobalDataJson != undefined && dhGlobalDataJson.retailFlowData != undefined) {
                    if (dhGlobalDataJson.retailFlowData.retailFlow) {
                        let storeEmail = (dhGlobalDataJson.retailFlowData.storeEmail) ? dhGlobalDataJson.retailFlowData.storeEmail : "";
                        let storeEmiald = {
                            "DH_ADMIN_EMAIL": storeEmail,
                            "SKU": partNumber
                        };
                        notesArray.push(storeEmiald);
                    }
                }
                request.ajaxCall({
                    url:`//${window.location.host}/bin/requesthandler.web.productavailability.json?partnumber=${partNumber}&storeId=ag_en&domainId=ag_en`,
                    type: 'get',
                    accept: 'application/json',
                }).then(data => {
                    let response = data.product;
                    let association = data.product.associations;
                    let refNum = response.variants[0].id+Math.random().toString(16).slice(2);
                    itemsArr.push({
                        quantity: 1,
                        id: response.variants[0].id,
                        properties: {
                            "reference": refNum,
                            "type": "parent",
                            "productType": response.core.product_type,
                            "variant_inventorystatus": response.variants[0].core.variant_inventorystatus,
                            "variant_backorderdate": response.variants[0].core.variant_backorderdate? response.variants[0].core.variant_backorderdate : "",
                            "variant_itematpreceiptid": response.variants[0].core.variant_itematpreceiptid? response.variants[0].core.variant_itematpreceiptid : "",
                            "RetailStoreId": retailStoreId != null ? retailStoreId : "" ,
                            "notes": notesArray
                        }
                    });
                    $.each(childPartNumAddons, (k, v) => {
                        $.each(association, (index, item) => {
                            if (item.product.variants[0].core.sku == childPartNumAddons[k]) {
                                self.getchildPayload(itemsArr, item, "", refNum);
                            }
                        });
                    });
                    $.each(childpartNum, (k, v) => {
                        $.each(association, (index, itemAsc) => {
                            if (itemAsc.product.variants.length > 1) {
                                $.each(itemAsc.product.variants, (index, item) => {
                                    if (item.core.sku == childpartNum[k]) {
                                        self.getchildPayload(itemsArr, item, itemAsc, refNum);
                                    }
                                });
                            }
                        });
                    });
                    cartObj.items = itemsArr;
                    self.addtoBagServiceCall(cartObj);
                }).catch(error => {
                    window.global.errorHandling.PDPAPI(error);
                });
	        }
			addtoBagServiceCall(cartObj) {
                let ajaxOption = window.dh.apiConfig("addToBag").addProductToBag;
                ajaxOption.data = cartObj;
                  let landingPage = $('.dh-btn-span #dh-addToBagbutton').attr('href');
                    request.ajaxCall(ajaxOption).then(data => {
                       try {
                           $('.dh-btn-span #dh-addToBagbutton').attr("disabled", true);
						   window.location.href = landingPage;
                        } catch (e) {
                              $('.dh-btn-span #dh-addToBagbutton').attr("disabled", false).removeClass("disabled");
                              let errorMessage = e.message;
                              window.global.errorHandling.cartAPI(errorMessage);
                              console.log(`Failed to add Product to Cart: ${errorMessage}`);
                            }
                        })
                    .fail(function(err){
                        $('.dh-btn-span #dh-addToBagbutton').attr("disabled", false).removeClass("disabled");
                        window.global.errorHandling.cartAPI(err);
                    })
                   .catch(error => {
                        $('.dh-btn-span #dh-addToBagbutton').attr("disabled", false).removeClass("disabled");
                        window.global.errorHandling.cartAPI(error);
                   });
            }
            getchildPayload(itemarray,res,itemAsc,refNum){
                 if(itemAsc == ""){
                    itemarray.push({
                        quantity: 1,
                        id: res.product.variants[0].id,
                        properties: {
                            "reference": refNum,
                            "type": "child",
                            "productType": res.product.core.product_type,
                            "association":res.association_type,
                            "variant_inventorystatus":res.product.variants[0].core.variant_inventorystatus,
                            "variant_backorderdate":  res.product.variants[0].core.variant_backorderdate ? res.product.variants[0].core.variant_backorderdate : "",
                            "variant_itematpreceiptid": res.product.variants[0].core.variant_itematpreceiptid ? res.product.variants[0].core.variant_itematpreceiptid : "",
                        }
                    });
                 }
                 else {
                    itemarray.push({
                        quantity: 1,
                        id: res.id,
                        properties: {
                            "reference": refNum,
                            "type": "child",
                            "productType": itemAsc.product.core.product_type,
                            "association":itemAsc.association_type,
                            "variant_inventorystatus":res.core.variant_inventorystatus,
                            "variant_backorderdate": res.core.variant_backorderdate ? res.core.variant_backorderdate : "",
                            "variant_itematpreceiptid": res.core.variant_itematpreceiptid ? res.core.variant_itematpreceiptid : "",
                        }
                    });
                 }
            }
            textareaLengthVal(ele){
                let $ele = $(ele);
                inputTextValidation($ele, [
                    'regexPattern',
                    'checkMaxLength'
                ]);
                sessionStorage.setItem("specialInstruction",$(ele).val());
            }
		   
		}
		let self;
		const request = window.dh.ajaxRequest;
		const handleBarTemplateInst= window.dh.handleBarTemplateInst;
		const {cartAPI}= window.global;
		const dhReviewSummaryInst = new dhReviewSummary();
		dhReviewSummaryInst.init();
})());