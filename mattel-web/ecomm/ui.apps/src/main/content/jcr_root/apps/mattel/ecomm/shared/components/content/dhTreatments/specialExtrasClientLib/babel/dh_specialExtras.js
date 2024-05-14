import {
	DHFormSession,
	helperTextEmpty
} from '../shared/dh-form-validation';
import {
	handleBarTemplate
} from '../shared/templateSetter';
import {setStorage } from "../shared/sessionStorage";
((() => {
	// defining configurations
	const config = {
		el: '.dh-wrapper .dh-container .dh-specialExtra-block',
		pdpCallIndex:0,
		specialExtraArray: [],
		specialAssociations: [],
		displayObj: {},
		storeName: document.getElementById('siteKey') != null ? document.getElementById('siteKey').value : 'ag_en',
		domainName: document.getElementById('siteKey') != null ? document.getElementById('siteKey').value : 'ag_en',
        pdpPartNumber: "",
        pdpProductStorage: "",
        ajaxOptions: "",
        treatmentStorage: "",
        wellNessFlag: "",
	};
	class dhExtraSpecial {
		constructor() {
			self = this;
			window.dh.eventBinding.bindLooping(this.bindingEventsConfig(), this);
            config.pdpProductStorage = JSON.parse(sessionStorage.getItem('pdpProductStorage'));
            config.treatmentStorage = DHFormSession('treatment', '', 'get');
            config.wellNessFlag = sessionStorage.getItem("wellNessFlag");
			helperTextEmpty();
        }
        checkAbandonedScreenExist() {
		   DHFormSession('actionType', 'back', 'set');
           self.sessionData = DHFormSession('specialExtrasData', '', 'get');
           if(self.sessionData) self.mappingStoredData(self.sessionData);
        }
        mappingStoredData(obj) {
            let $formEle = $(".dh-specialExtra-block section");
            _.each(obj, (item) => {
                if (item.type == "checkbox") {
                    $formEle.find(`[value="${item.parentSku}"]`).parents('.sku-card').addClass('active');
                    $formEle.find(`[value="${item.parentSku}"]`).prop("checked", true);
                } else if (item.type == "radio"){
                	$formEle.find(`[value="${item.childSkuDetails.childSku}"]`).parents('.sku-card').addClass('active');
                    $formEle.find(`[value="${item.childSkuDetails.childSku}"]`).prop("checked", true);
                }
            });
        }
		init() {
			if(JSON.parse(sessionStorage.getItem('DHFormData'))){
				if (config.pdpProductStorage == "" || config.pdpProductStorage == null) {
					config.pdpPartNumber = config.treatmentStorage.skuId;
					$.when(self.getSpecialExtraPdpData()).done(function (data) {
						if (data) setStorage("pdpProductStorage", data);
						config.pdpProductStorage = JSON.parse(sessionStorage.getItem('pdpProductStorage'));
						self.initSpecialExtraCall();
					});
				} else {
					self.initSpecialExtraCall();
				} 
			} 
			else{
				let pagePath =  $('.landingPage').data('landingpagepath');
	    		if(pagePath){
	    			window.location.href = pagePath;
	    		}
			}
       }
        initSpecialExtraCall() {
			let productObj = config.pdpProductStorage.product;
            config.specialExtraArray = productObj.attributes.DHSpecialExtra ? (typeof(productObj.attributes.DHSpecialExtra) == "string" ? [productObj.attributes.DHSpecialExtra] : productObj.attributes.DHSpecialExtra):[];
            self.populateSpecialExtraTemplate(productObj);
        }
		bindingEventsConfig() {
			let eventsArr = {
                'click .dh-specialExtra-block .dh-back-btn': 'prevSlide',
                'change .dh-specialExtra-block .sku-card input[type="checkbox"]': 'cardActivation',
				 'click .dh-specialExtra-block .sku-card input[type="radio"]': 'childCardActivation',
				 'mouseover .dh-specialExtra-block .sku-card fieldset label': 'mouseOver',
                 'mouseout .dh-specialExtra-block .sku-card fieldset label': 'mouseOut',
                'click .dh-specialExtra-block .dh-next-btn': 'nextSlide'
			};
			return eventsArr;
		}
		saveSession(ele) {
			DHFormSession('dollName', config.displayObj, 'set');
		}
		childCardActivation(ele){
            $("input[name='"+$(ele).attr("name")+"']:radio").not(ele).removeData("chk");
            $(ele).data("chk",!$(ele).data("chk"));
            $(ele).prop("checked",$(ele).data("chk"));
            if ( $(ele).is(':checked')) {
                 $(ele).parents('.sku-card').addClass('active');
            } else{
                 $(ele).parents('.sku-card').removeClass('active');
            }
        }
		childCardActivationHover(){
            let ele = ".dh-specialExtra-block .sku-card input[type='radio']";
            $(ele).parents('.sku-card').addClass('not-hover');
        }
        mouseOver(ele){
                $(ele).parents('.sku-card').removeClass('not-hover');
        }
        mouseOut(ele){
                $(ele).parents('.sku-card').addClass('not-hover');
        }
		cardActivation(ele) {
			const $ele = $(ele);
			if ($ele.is(':checked')) {
				$ele.parents('.sku-card').addClass('active');
			} else {
				$ele.parents('.sku-card').removeClass('active');
			}
		}
        prevSlide(ele) {
        	 const $ele = $(ele);
        	 if (config.wellNessFlag == "true") {
                 let $back_url = $ele.data('wellness-url');  
                 $ele.parent('a').attr('href',$back_url+"?DH_User=wellness#page4");     
              }
        	let DHSubTreatmentData = DHFormSession('skippedSubTreatment', '', 'get');
			if(DHSubTreatmentData.isSkipped && DHSubTreatmentData.prePageURL){
				let $back_url = DHSubTreatmentData.prePageURL;  
                $ele.parent('a').attr('href',$back_url); 
			}
            if ($ele.length) {
                DHFormSession('specialExtrasData', '', 'delete');
            }
         }
		nextSlide(ele) {
            let displayObj = self.storingEnteredData(ele);
            DHFormSession('specialExtrasData', displayObj, 'set');
        }
		storingEnteredData(ele) {
            let $inputEle = $(".dh-specialExtra-block form"),
                subObj = {};
            if (!$inputEle.length) {
                console.log("input tags not found..");
                return;
            }
            const $ele = $(ele);
            $inputEle = $inputEle.find('input:not([type="hidden"])');
            let filterChk = $inputEle.filter(":checked"),
                inputType = $inputEle.attr("type"),
                subObjarr = [];
            _.each(filterChk, (item, index) => {
                let $eles = $(item),
                    inputTitle = $eles.next('label').text(),
                    inputPrice = $eles.parents('.sku-card').find('.item-price').contents().not($('.item-price span')).text(),
                    smallPartWar = $eles.parents('.sku-container').attr('safety-msg');
                	inputType = $eles.attr("type");
                if (inputType == "checkbox") {
                    if (filterChk.is(':checked')) {
                        subObj = {
                            parentSku: item.value,
                            parentSkuName: inputTitle,
                            price: inputPrice,
							smallPartsWarning: smallPartWar,
                            type: inputType
                        }
                        subObjarr.push(subObj);
                    }
                }
                else if (inputType == "radio") {
                    let inputParTitle = $eles.parent('fieldset').parent().find('h3').text(),
					inputParSku = $eles.parent('fieldset').parent().find('h3').attr("value");
                    subObj = {
                        parentSku: inputParSku,
                        parentSkuName: inputParTitle,
                        type: inputType,
                        childSkuDetails: {
                            childSku: item.value,
                            skuName: inputTitle,
                            smallPartsWarning: smallPartWar,
                            price: inputPrice
                        }
                    }
                    subObjarr.push(subObj);
                }
                return !$ele.hasClass('active');
            });
            return subObjarr;
        }
		getPdpdata(index, arrLen, filteredSpecialExtraService) {
			let spclExtraCurrentData = {};
			$.when(self.getSpecialExtraPdpData()).done(function (data) {
				if (data) {
					let filteredOnInvData ={};
					config.pdpCallIndex = config.pdpCallIndex+1;
					let {variants, attributes} = data.product;
					let childProductArray = [];
					if (variants.length > 1) {
						_.each(variants, (item, index) => {
							let childDetails = {};
							childDetails.childPartNumber=item.core.sku;
							childDetails.childSkuName = item.core.title;
							childDetails.variantID = item.core.variant_id;
							childDetails.inventoryStatus = item.core.variant_inventorystatus;
							childDetails.productType = data.product.core.product_type;
							childProductArray.push(childDetails);
						});
						spclExtraCurrentData.childProduct = childProductArray;
					}
					spclExtraCurrentData.imageLink = variants[0].core.variant_imageLink || "https://mattel.scene7.com/is/image/Mattel/";
					spclExtraCurrentData.skuName =  data.product.core.title;
					spclExtraCurrentData.description = attributes.MarketingDescription != null ? attributes.MarketingDescription:"";
					//spclExtraCurrentData.imageAltText = productResponse.seo_imageAltText;
					spclExtraCurrentData.safetyMessage = attributes.SafetyMessage != null ? attributes.SafetyMessage:"";
					spclExtraCurrentData.LOVLabel =  attributes.LOVLabel != null ? attributes.LOVLabel:"";
					spclExtraCurrentData.partNumber = variants[0].core.variant_parentpartnumber;
					spclExtraCurrentData.price = cartAPI.getActivePriceData(variants[0].pricing);
					spclExtraCurrentData.inventoryStatus= variants[0].core.variant_inventorystatus;
					if (parseInt(config.pdpCallIndex) == parseInt(arrLen)) {
						_.each(filteredSpecialExtraService, (item, indx) => {
							if(item.inventoryStatus=="Available"){
								filteredOnInvData[indx]=item;
							}									
						});
						if(Object.keys(filteredOnInvData).length === 0 && filteredOnInvData.constructor === Object){
							self.specialExtrapageSkipAction();
						}
						else{
							console.log(filteredOnInvData);
							handleBarTemplateInst.loadTemplate("#specialExtra-dynamic", ".dynamic-data-load", filteredOnInvData, 'append');
							self.equalContainerHeight();
							$('.main-content-section').removeClass('data-loading');
							self.checkAbandonedScreenExist();
							self.childCardActivationHover();
						}
					}
				}
			});
			return spclExtraCurrentData;
		}
		equalContainerHeight() {
			if (window.matchMedia('(min-width: 768px)').matches) {
				var maxHeight = 0;
				$('.sku-container').each(function () {
					maxHeight = Math.max(maxHeight, $(this).outerHeight());
				});
				$('.sku-container').css({
					height: maxHeight + 'px'
				});
			}
		}
		populateSpecialExtraTemplate(productObj) {
			let filteredSpecialExtraService = [];
			if (config.specialExtraArray.length) {
				let SkippedSpclExtra = DHFormSession('skippedSpclExtra', '', 'get');
				if(SkippedSpclExtra){
					DHFormSession('skippedSpclExtra', '', 'delete');
				}
				$.each(config.specialExtraArray, (k) => {
						config.pdpPartNumber = config.specialExtraArray[k];
						let specialExtraPdpData = self.getPdpdata(k, config.specialExtraArray.length,filteredSpecialExtraService);
						filteredSpecialExtraService.push(specialExtraPdpData);
				});
			}
			else{
				self.specialExtrapageSkipAction();
			}
		}
		specialExtrapageSkipAction(){
			let sessionObj = {};
			const ReturnedFromSmry = DHFormSession('bckfromSummary', '', 'get');
			sessionObj.isSkipped = true;
			sessionObj.prePageURL = window.location.origin+$('.dh-back-btn').parent('a').attr('href');
            DHFormSession('skippedSpclExtra', sessionObj, 'set');
            if(ReturnedFromSmry == "yes"){
            	window.location.href = window.location.origin+$('.dh-back-btn').parent('a').attr('href');
            	DHFormSession('bckfromSummary', '', 'delete');
            }
            else{
            	window.location.href = window.location.origin+$('.dh-next-btn').parent('a').attr('href');
            }
		}
		getSpecialExtraPdpData() {
			let deferred = $.Deferred();
            request.ajaxCall({
				url:`//${window.location.host}/bin/requesthandler.web.productavailability.json?partnumber=${config.pdpPartNumber}&storeId=ag_en&domainId=ag_en`,
				type:'get',
				accept:'application/json',
			}).then(data => {
					deferred.resolve(data);
			}).catch(error => {
				window.global.errorHandling.PDPAPI(error);
			});
				return deferred.promise();
		}
	}
	let self;
	const apiConfig = window.dh.apiConfig;
	const request = window.dh.ajaxRequest;
	const handleBarTemplateInst = new handleBarTemplate();
	const {cartAPI} = window.global;
	const dhExtraSpecialInstance = new dhExtraSpecial();
	dhExtraSpecialInstance.init();
    $(window).on({
        popstate:function(e){
        DHFormSession('specialExtrasData', '', 'delete');       
        }
  })
})());