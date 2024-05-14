
import {
    handleBarTemplate
} from '../shared/templateSetter';
import apiConfig from "../shared/apiConfig";
import ajaxRequest from "../shared/ajaxbinding";
import { getStorage, setStorage } from "../shared/sessionStorage";
import {
    DHFormSession
} from '../shared/dh-form-validation';
((() => {
    const config = {
        storeName: document.getElementById('siteKey') != null ? document.getElementById('siteKey').value : 'ag_en',
        domainName: document.getElementById('siteKey') != null ? document.getElementById('siteKey').value : 'ag_en',
        pdpPartNumber: "",
        singleSelectionFlag: 0
    };
    class subTreatments {

        constructor() {
            self = this;
            this.pdpStatusObj = {};
            this.JSONObj = {};
        }

        init() {
        	if(!(JSON.parse(sessionStorage.getItem('DHFormData')))){
        		let pagePath =  $('.landingPage').data('landingpagepath');
	    		if(pagePath){
	    			window.location.href = pagePath;
	    		}
        	}
        	DHFormSession('bckfromSummary', '', 'get') ? DHFormSession('bckfromSummary', '', 'delete'):'';
            $(document).ready(function () {
                self.render();
            });
        }
        loadHandlebartmplt(filteredSubTreatmentService, attributesJson) {
            handleBarTemplateInst.loadTemplate("#subtreatment-dynamic", ".dynamic-data-load", attributesJson, 'append');
            self.checkAbandonedScreenExist();
            self.manualPopover();
            self.onLoadVal();
        }
        checkAbandonedScreenExist() {
            self.sessionData = DHFormSession('subTreatmentData', '', 'get');
            if (self.sessionData) self.mappingStoredData(self.sessionData);
        }
        mappingStoredData(obj) {
            let $formEle = $(".dh-sub-treatment-block section");
            if (obj) {
                $formEle.find('button.dh-next-btn').removeClass('disabled');
            }
            _.each(obj, (item) => {
                let selection_attr = $formEle.find(`[value="${item.skuId}"]`);
                if (item.type == "checkbox" || item.type == "radio") {
                    selection_attr.parents('.sku-card').addClass('active');
                    selection_attr.prop("checked", true);
                }
            });
        }
        render() {
            self.getProductFeed();
        }
        onLoadVal() {
            let datasets = $('.dh-sub-treatment-block section form').find('.dh-st-option-group')[0].dataset;
            if (datasets.min == 0) {
                $('.dh-sub-treatment-block section form').find(".dh-next-btn").removeClass('disabled');
            }
            if (window.matchMedia('(min-width: 768px)').matches) {
                $('.dh-sub-treatment-block p.choose-opt').prependTo('.dh-sub-treatment-block .dh-st-option-group');
            }
        }
        manualPopover() {
            let img,
                imgSrc;
            _.each($('[data-toggle="popover"]'), (item) => {
                imgSrc = $(item).data('img');
                if (imgSrc) {
                    img = new Image();
                    img.src = imgSrc;
                    img.className = "img-fluid";
                }
                $(item).popover({
                    html: true,
                    trigger: window.innerWidth <= 766 ? 'click' : 'hover',
                    content: function () {
                        return img;
                    },
                    placement: 'auto' + (window.innerWidth <= 766 ? $(item).data('positionSm') : $(item).data('positionLg')),
                })
            });
        }
        getProductFeed() {
            let filteredSubTreatmentService = [];
            let dhGlobalDataJson = JSON.parse(sessionStorage.getItem('DHFormData'));
            config.pdpPartNumber = (dhGlobalDataJson != null && dhGlobalDataJson.treatment != null) ? dhGlobalDataJson.treatment.skuId : '';
            const sessionData = getStorage("pdpProductStorage");
            self.pdpStatusObj = sessionData;
            request.ajaxCall({
                url:`//${window.location.host}/bin/requesthandler.web.productavailability.json?partnumber=${config.pdpPartNumber}&storeId=ag_en&domainId=ag_en`,
                type:'get',
                accept: 'application/json'
                // data:ajaxOptions
                })
                .then(data => {
                    self.pdpStatusObj = data;
                    setStorage("pdpProductStorage", data);
                    try {
                        let {associations,attributes} = data.product;
                        let subtreatmentArray = attributes.DHSubtreatment ? attributes.DHSubtreatment : [];
                        var attributesJson = {
                            DHQuestion: attributes.DHQuestion != null ? attributes.DHQuestion : "",
                            MaxSubtreatments: attributes.MaxSubtreatments != null ? attributes.MaxSubtreatments : "",
                            MinSubtreatments: attributes.MinSubtreatments != null ? attributes.MinSubtreatments : "",
                            TreatmentInstructions: attributes.TreatmentInstructions != null ? attributes.TreatmentInstructions : "",
                            TreatmentHelperText: attributes.TreatmentHelperText != null ? attributes.TreatmentHelperText : "",
                            TreatmentInfoText: attributes.TreatmentInfoText != null ? attributes.TreatmentInfoText : "",
                            TreatmentTitle: (dhGlobalDataJson != null && dhGlobalDataJson.treatment != null) ? dhGlobalDataJson.treatment.skuName : "",
                            TreatmentDescription: (dhGlobalDataJson != null && dhGlobalDataJson.treatment != null) ? dhGlobalDataJson.treatment.skuDesc : "",
                            Treatmentprice: (dhGlobalDataJson != null && dhGlobalDataJson.treatment != null) ? dhGlobalDataJson.treatment.skuPrice : "",
                            TreatmentImage: (dhGlobalDataJson != null && dhGlobalDataJson.treatment != null) ? dhGlobalDataJson.treatment.skuImage : "",
                            TreatmentSku: (dhGlobalDataJson != null && dhGlobalDataJson.treatment != null) ? dhGlobalDataJson.treatment.skuId : "",
                            TreatmentOfferType: (dhGlobalDataJson != null && dhGlobalDataJson.treatment != null) ? dhGlobalDataJson.treatment.dhOfferType : ""
                        };
                        if (subtreatmentArray.length) {
                            if (attributesJson.TreatmentOfferType == "HearingAid") {
                                config.singleSelectionFlag = 1;
                                let hearingAidPartNumber = subtreatmentArray;
                                        $.each(associations, (k, v) => {
                                            if(hearingAidPartNumber == v.product.variants[0].core.variant_parentpartnumber){
                                                $.each(v.product.variants, (index,item) => {
                                                    filteredSubTreatmentService.push({
                                                        "LOVLabel" : item.core.title,
                                                        "sku" :  item.core.sku,
                                                        "singleSelectionFlag" : config.singleSelectionFlag,
                                                        "isChildSku" : "Y",
                                                    });
                                                });
                                            }
                                        });
									attributesJson.inputData=filteredSubTreatmentService;
                                    self.loadHandlebartmplt(filteredSubTreatmentService, attributesJson);
                                //})
                            }
                            else {
                                if (attributesJson.MaxSubtreatments == 1) {
                                    config.singleSelectionFlag = 1;
                                }
                                $.each(associations, (k, v) => {
                                    if (subtreatmentArray.indexOf(v.product.variants[0].core.sku) >= 0) {
                                        filteredSubTreatmentService.push({
                                        "LOVLabel" : v.product.attributes.LOVLabel != null ? v.product.attributes.LOVLabel : v.product.attributes.optionalName,
                                        "sku" : v.product.variants[0].core.sku != null ? v.product.variants[0].core.sku : "",
                                        "singleSelectionFlag" : config.singleSelectionFlag,
                                        "isChildSku" : "N",
                                        });
                                    }
                                });
								attributesJson.inputData=filteredSubTreatmentService;
                                self.loadHandlebartmplt(filteredSubTreatmentService, attributesJson);
                            }
                            DHFormSession('skippedSubTreatment', '', 'delete');
                        }
                        else {
							DHFormSession('subTreatmentData', '', 'delete');
                            let sessionObj = {};
                            let actionType = DHFormSession('actionType', '', 'get');
							sessionObj.isSkipped = true;
							sessionObj.prePageURL = window.location.origin + $('.prev-page-link').attr('href');
                            DHFormSession('skippedSubTreatment', sessionObj, 'set');
                            if(actionType == "back"){
                                window.location.href = sessionObj.prePageURL;
                                DHFormSession('actionType', '', 'delete');
                            }
                            else{
                                window.location.href = window.location.origin + $('.next-page-link').attr('href');
                            }
                            
                        }

                    } catch (e) {
                        let errorMessage = e.message;
                    }

                })
                .catch(error => {
                    window.global.errorHandling.PDPAPI(error);
                });
        }
    }
    let self,
        apiConfigInst = new apiConfig(),
        request = new ajaxRequest(),
        treatmentsInstance = new subTreatments();
    const handleBarTemplateInst = new handleBarTemplate();
    treatmentsInstance.init();
})());