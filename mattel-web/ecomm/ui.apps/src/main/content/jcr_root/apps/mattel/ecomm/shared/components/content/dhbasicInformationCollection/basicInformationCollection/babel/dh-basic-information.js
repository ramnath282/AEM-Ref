import fieldValidation from '../shared/inputvalidation';
import constant from '../shared/constant';
import {
    crossWordCheck,
    inputTextValidation,
    inputRadioValidation,
    inputCheckboxValidation,
    updateHashStringParameter,
    bindDollName,
    removeHashStringParameter,
    DHFormSession,
    getHashValue,
    helperTextEmpty
} from '../shared/dh-form-validation';
import {deleteStorage } from "../shared/sessionStorage";
((() => {
    // defining configurations
    const config = {
        el: '.dh-basicinfo-block .form-section',
        isBackButtonClicked: window.performance && window.performance.navigation.type == 2,
        isTouchDevice: 'ontouchstart' in window,
        inputDataStorageName: 'DHFormData',
        dollDataStorageName: 'dollName',
        checkHistoryAPISupport: window.history && window.history.pushState,
        formObjForSession: {},
        retailFlowObj:{},
        deviceName: /iPhone/.test(navigator.userAgent) && !window.MSStream && 'ontouchstart' in window ? 'ios-device-iphone' : (/(android)/i.test(navigator.userAgent) && 'ontouchstart' in window ? 'android-device' : ''),
        priceSku: "",
        wellNessFlag: "",
        wellNessTreatmentData: "",
        wnTreatmentSkuId: "",
        legalAge : "",
        additionalDetails : "",
        wnTreatmentSkuName: ""
    };
    class dhOnlineForm {
        constructor() {
            self = this;
            window.dh.eventBinding.bindLooping(self.bindingEventsConfig(), self);
            this.urlPara = self.urlParam('DH_User');
            if (self.urlPara == "wellness") {
                sessionStorage.setItem("wellNessFlag", "true");
                config.wellNessTreatmentData = JSON.parse(document.getElementById("wellnessTreatmentData").text);
                
           }
        }
        postLoadAction() {
            self.checkAbandonedScreenExist();
            self.initBrowserEvents();
            helperTextEmpty();
        }
        bindingEventsConfig() {
            let eventsArr = {
                'keyup .dh-basicinfo-input-holder .doll-name': 'validateInputField',
                'change [data-control-type="radio"] input[type="radio"],[data-control-type="checkbox"] input[type="checkbox"]': 'validateInputField',
                'click .dh-basicinfo-block .dh-back-btn': 'prevSlide',
                'click .dh-basicinfo-block .dh-next-btn': 'nextSlide'
            };
            return eventsArr;
        }
        checkAbandonedScreenExist() {
            self.updateScreenPageAttrs();
            let defaultPage = 0; // welcome page
            const getPageNoFromURL = parseInt(getHashValue('page') || 0);
            let resetForm = false;
            let activeScreenIndx = $(".form-section.active").data('screenIndex');
            if (getPageNoFromURL) {
                self.sessionData = DHFormSession('quizData', '', 'get');
                let retrieveLastPageData = _.keys(self.sessionData),
                    indx;
                if ((isTriggeredFromHash && (getPageNoFromURL < activeScreenIndx))) {
                    indx = getPageNoFromURL;
                } else if (getPageNoFromURL == 1) {
                	if(sessionStorage.getItem("DHLandingPageFlag") != "true"){
                		removeHashStringParameter('page');
                		indx = defaultPage;
                		} else{
                		indx = getPageNoFromURL;
                		resetForm = true;
                		} 
                } else if (retrieveLastPageData.length) {
                    indx = getPageNoFromURL;
                    self.mappingStoredData(self.sessionData);
                    // resetForm = true;
                    DHFormSession('quizData', '', 'delete');
                } else {
                    removeHashStringParameter('page');
                    indx = defaultPage;
                }
                self.activeCurrentSlide((indx || defaultPage), resetForm);
            } else {
                self.activeCurrentSlide(defaultPage, resetForm);
            }
        }
        updateScreenPageAttrs() {
            let $screenEle = $(config.el);
            _.each($screenEle, (item, indx) => {
                $(item).attr('data-screen-index', indx);
            });
        }
        validateInputField(ele, evt) {
            evt.preventDefault();
            const $ele = $(ele);
            self.checkInputType($ele);
        }
        checkInputType($ele) {
            let type = $ele.attr('type');
            switch (type) {
                case 'text':
                    inputTextValidation($ele, [
                        'regexPattern',
                        'capitalize',
                        'checkMaxLength'
                    ]);
                    break;
                case 'radio':
                    inputRadioValidation($ele);
                    break;

                case 'checkbox':
                    inputCheckboxValidation($ele);
                    break;
            }
        }
        hasHrefLink($formElement) {
            let hrefLink = $formElement.attr('action') || $formElement.find('.dh-next-btn').attr('href');
            if (hrefLink) {
                self.initRefreshButtonAction();
                window.location.href = hrefLink;
                return true;
            }
            return false;
        }
        beforeSubmit(target, cb) {
            let $formElement = $(target);
            if (self.hasHrefLink($formElement)) return;
            $formElement.addClass('data-loading');
            window.dh.inputValidation.checkAllFields($formElement, function(output, formFields) {
                if (!output) {
                    $formElement.removeClass('data-loading');
                    return;
                }
                setTimeout(() => {
                    $formElement.removeClass('data-loading');
                    cb(output, target)
                }, 200)
            })
        }
        prevSlide(ele, evt) {
            evt.preventDefault();
            const $formEle = $(ele.form);
            let currentPage = $formEle.closest(".form-section").data('screenIndex');
            currentPage--;
            config.wellNessFlag = sessionStorage.getItem("wellNessFlag");
            if (config.wellNessFlag == "true" && currentPage == 2) currentPage--;
            const $inputEle = $(config.el).filter(`[data-screen-index=${currentPage}]`).find('input:not([type="hidden"])');
            self.activeCurrentSlide(currentPage);
            noCallback = true;
            if (currentPage == 0 && !$inputEle.length) {
                // welcome screen
                removeHashStringParameter('page');
            }
        }
        
        getPartIdByDollTypeOfferType() {
            if (config.wellNessTreatmentData) {
            	let $inputEle = $(".dh-basicinfo-block"),
                dollType_data = $inputEle.find('.wellnessUserTag').data('dolltype'),
                productType_data = $inputEle.find('.wellnessUserTag').data('offertype');
                _.each(config.wellNessTreatmentData, (item) => {
                    let dollType = item.attributes.DHDollType;
                    let productType = item.attributes.DHOfferType;
                    for (let k = 0; k < dollType.length; k++) {
                    	if (dollType[k].value == dollType_data && productType[k].value == productType_data) {
                            config.wnTreatmentSkuId = item.partNumber;
                            config.wnTreatmentSkuName = item.name;
                            config.legalAge= item.attributes.LegalAge[k].value;
                            config.additionalDetails = item.attributes.AdditionalDetails[k].value;
                            break;
                        }
                    }
                });
            }
        }
        
        getTreatmentsPricing() {
            let deferred = $.Deferred();
            let treatmentSkusArray = [];
            treatmentSkusArray.push(config.wnTreatmentSkuId);
            if (treatmentSkusArray.length) {
                request.ajaxCall({
                    url:`//${window.location.host}/bin/requesthandler.web.productavailability.json?partnumber=${treatmentSkusArray}&storeId=ag_en&domainId=ag_en&partial=variants`,
                    type:"GET",
                    accept:'application/json'
                }).then(data => {
                    deferred.resolve(data);
                });
                return deferred.promise();
            }

        }
        
        formatTreatmentPrice(data){
        	let price;
            let{variants} = data.product;
		    if(variants){   	
				   price = variants[0].pricing.price;       
			}
		  return price;
        }

        storingWellTreatmentData() {
            let treatmentObj = {};
            $.when(self.getTreatmentsPricing()).done(function (data) {
                if (data) config.priceSku = self.formatTreatmentPrice(data);

            });
            treatmentObj = {
                skuId: config.wnTreatmentSkuId,
                skuName: config.wnTreatmentSkuName,
                skuPrice: config.priceSku,
                legalAge : config.legalAge,
                additionalDetails : config.additionalDetails
                
            }
            return treatmentObj;
        }

        nextSlide(ele, evt) {
            evt.preventDefault();
            const $formEle = $(ele.form);
            const $nextEle = $formEle.find(".dh-next-btn");
            const $ele = $formEle.find('input:not([type="hidden"])');
            let currentPage = $formEle.closest(".form-section").data('screenIndex'),
            isValid = true;
	    let treatmentDHSession = DHFormSession('treatment', '', 'get');
            config.wellNessFlag = sessionStorage.getItem("wellNessFlag");
            if (config.wellNessFlag == "true" && currentPage == 4) {
                let $next_url = $nextEle.data('wellness-url');
                $nextEle.attr('href', $next_url);
            }
            if (config.wellNessFlag == "true" && currentPage != 0) {
	               let displayObj = self.storingWellTreatmentData(ele);
	               DHFormSession('treatment', displayObj, 'set');
	               let specialExtrasDHSession = DHFormSession('specialExtrasData', '', 'get');
	               if(currentPage == 4 && specialExtrasDHSession){
	            	   DHFormSession('specialExtrasData', '', 'delete');
	               }
             }
            if(config.wellNessFlag == "false" && currentPage == 4 && treatmentDHSession){
            	DHFormSession('treatment', '', 'delete');
            }
            
            if (currentPage == 0 && !$ele.length) {
                // welcome screen
            	sessionStorage.setItem("DHLandingPageFlag", "true");
                currentPage++;
                self.activeCurrentSlide(currentPage, true);
                deleteStorage('DHFormData');
                deleteStorage('pdpProductStorage');
                deleteStorage('specialInstruction');
                self.getPartIdByDollTypeOfferType();
                if (self.urlPara != "wellness") {
                	sessionStorage.setItem("wellNessFlag", "false");
                }
                self.startRetailFlow();
                return;
            }
            
            let $inputEle = $formEle.find('input[type="text"]');
            if ($inputEle.data('mandate') == true && $inputEle.length ) {
                let inputVal = $inputEle.val();
                crossWordCheck(inputVal, (msg) => {
                    if (msg == "fail") {
                        $formEle.removeClass('data-loading');
                        let $errorEle = $formEle.find('.err-msg');
                        $errorEle.removeClass('in');
                        $errorEle.filter('.restrict-message').addClass('in');
                        $nextEle.addClass('disabled').attr('aria-disabled',true);
                        return;
                    }
                    $formEle.find('.err-msg').each((i, elt) => {
                        if ($(elt).hasClass('in')) {
                            isValid = false;
                        }
                    })
                    if (isValid && $inputEle.val().trim().length) {
                        $nextEle.removeClass('disabled').removeAttr('aria-disabled');
                        $formEle.addClass('data-loading');
                        self.proceedToNext($formEle, currentPage);
                    }
                });
            } else {
                if (!$nextEle.hasClass('disabled')) {
                    self.proceedToNext($formEle, currentPage);
                }
            }
            
        }
        
        urlParam(name) {
            var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
            if (results) return results[1] || 0;
        }
        
        proceedToNext($formEle, currentPage) {
            let resetForm = true;
            self.beforeSubmit($formEle, function(res, formFields) {
                currentPage++;
                if (config.wellNessFlag == "true" && currentPage == 2) {
                	currentPage++;
                }
                self.activeCurrentSlide(currentPage, resetForm);
                noCallback = true;
            });
        }
        
        storingEnteredData() {
            let $inputEle = $(".dh-basicinfo-block section.form-section");
            if (!$inputEle.length) {
                console.log("input tags not found..");
                return;
            }
            _.every($inputEle, (item) => {
                let $ele = $(item),
                    $inputEle = $ele.find('input:not([type="hidden"])'),
                    pageNo = $ele.data('screenIndex'),
                    inputName = $inputEle.attr('name'),
                    inputVal = $inputEle.val(),
                    inputTitle = $inputEle.val(),
					inputType = $inputEle.attr('type'),
                    treatmentHelper="",
                	treatmentPopover="",
                    treatmentVideo="",
                    videoType="";
                    
                if ($inputEle.attr("type") == "radio" && $inputEle.is(":checked")) {
                    inputVal = $inputEle.filter(":checked").val();
                    if($inputEle.closest('div').hasClass('dh-dollType-option-group')){
                    	let checkedlabel= $inputEle.filter(":checked").next('label');
                        inputTitle = checkedlabel.find('span:eq(1)').text().trim();
                        treatmentHelper = checkedlabel.data('helper');
                        treatmentPopover = checkedlabel.data('pop');
                        treatmentVideo = checkedlabel.data('video');
                        videoType = checkedlabel.data('videoType');
                    }else{
                        inputTitle = $inputEle.filter(":checked").next('label').text().trim();
                    }  
                }
                if (inputVal != '' && pageNo) {
                	if (config.wellNessFlag == "true" && pageNo == 2) {
                        inputTitle = $ele.parents().find('.wellnessUserTag').data('dolltype');
                        if(inputTitle == "18"){
                            inputTitle = '18" Doll';
                        }
                		inputName = "dollType";
                		inputVal = inputTitle;
                		inputType = "radio";
                	}
                    config.formObjForSession[`page${pageNo}`] = {
                        inputName: inputName,
                        inputVal: inputVal,
                        inputTitle: inputTitle,
                        type: inputType,
						treatmentHelper: treatmentHelper,
						treatmentPopover: treatmentPopover,
                        treatmentVideo: treatmentVideo,
                        treatmentVideoType: videoType
                    }
                } else if (pageNo) {
                    return;
                }
                return !$ele.hasClass('active');
            });
        }
        mappingStoredData(obj) {
            let $formEle = $(".dh-basicinfo-block section.form-section"),
                objKeys = _.keys(obj),
                data, pageNo, $inputEle;
            if (objKeys.length < 2 || !$formEle.length) {
                return;
            }
            _.each(objKeys, (item) => {
                data = obj[item];
                pageNo = item.replace('page', '');
                $inputEle = $formEle.filter(`[data-screen-index="${pageNo}"]`).find('input:not([type="hidden"])');
                if (data.type == "radio") {
                    $formEle.find(`[value='${data.inputVal}']`).prop("checked", true);
                } else {
                    $formEle.find(`[name='${data.inputName}']`).attr("value", data.inputVal);
                }
                self.checkInputType($inputEle);
            });
        }
        scrollToFormElement($form, cb) {
            let offTop = $form.offset().top - 43;
            let $checkInputField = $form.find('input[type="text"]:eq(0)');
            $(window).scrollTop(offTop);
            cb($checkInputField);

        }
        activeCurrentSlide(currentPage, isResetForm) {
            let $currentFormEle = $(config.el).removeClass('active').filter(`[data-screen-index=${currentPage}]`);
            bindDollName($currentFormEle);
            self.scrollToFormElement($currentFormEle, ($inputEle) => {
                $currentFormEle.addClass('active');
                currentPage && updateHashStringParameter('page', currentPage);
                if ((isResetForm && $currentFormEle.length)) {
                    let $formEle = $currentFormEle.find('form');
                    $formEle[0].reset();
                    if ($formEle.find('input[type="text"]').length) $inputEle[0].value = "";
                    $formEle.find('.dh-next-btn').addClass('disabled').attr('aria-disabled',true);
                }
                //$inputEle.length && $inputEle.focus();
            });
        }
        initRefreshButtonAction() {
            self.storingEnteredData();
            if (_.keys(config.formObjForSession).length) {
                DHFormSession('quizData', config.formObjForSession, 'set');
                DHFormSession('dollName', $("input.doll-name").val() || false, 'set');
            }
        }
        initBrowserEvents() {
            window.onpagehide = function(e) {
                self.initRefreshButtonAction();
                return null;
            }
            $(window).on('hashchange', function() {
                if (noCallback) {
                    noCallback = false;
                    return;
                }
                if (!isTriggeredFromHash) isTriggeredFromHash = true;
                self.checkAbandonedScreenExist();
            });
        }
        
        startRetailFlow(){
	   		 let  parameter, params, query;
	   	     let  uri = window.location.search;
	   	      if (uri.indexOf("?") != -1) {
	   	    	  query = uri.slice(1);
	   	    	  params = query.split("&");
	   	          let i = 0;
		   	      while (i < params.length) {
		   	    	  parameter = params[i].split("=");
		   	    	  if(parameter[0] == "storeEmail" || parameter[0] == "printerEmail" || parameter[0] == "storeNumber") {  
		   	    		  config.retailFlowObj['retailFlow']=true;
		   	    		  config.retailFlowObj[parameter[0]]=parameter[1].replace(/%20/g, " ");
		   	    	  } 
		   	    	  i++;
		   	      }
		   	    DHFormSession('retailFlowData', config.retailFlowObj, 'set');
	   	      }
	   	  
        }
    }
    let self;
    let noCallback = false;
    let isTriggeredFromHash = false;
    window.dh.inputValidation = new fieldValidation();
    const request = window.dh.ajaxRequest;
    const dhOnlineFormInst = new dhOnlineForm();
    document.addEventListener("DOMContentLoaded", function() {
        dhOnlineFormInst.postLoadAction();
    });
    $(document).ready(function(){
        equalContainerHeight();
    });

    function equalContainerHeight() {
        var maxHeight = 0;
        $('.rdDollTypeContainer').each(function() {
            maxHeight = Math.max(maxHeight, $(this).outerHeight());
        });
        $('.rdDollTypeContainer').css({ height: maxHeight + 'px' });
    }

})());
