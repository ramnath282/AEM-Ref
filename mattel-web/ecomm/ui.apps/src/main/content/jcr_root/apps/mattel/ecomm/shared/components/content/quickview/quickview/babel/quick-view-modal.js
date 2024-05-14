import ajaxRequest from '../shared/ajaxbinding';
import apiConfig from '../shared/apiConfig';
import {productSupportObj, storeAndGetPayloadData} from '../shared/productSupportiveFunctions';
import { dateFormat } from '../shared/dateFormat';
import {userCategory, isInventoryStatusValid} from '../shared/addToBagDisableCheck';
import  "../shared/cart";

let self,
    ajaxFiredElem = [],
    $rootEle = $(".root");

export default class QuickViewFunctionality {
    constructor() {
        self = this;
        this.eventBinding();
        this.bindingHelperFn();
        this.productInventoryMessages();
    }
    bindingHelperFn() {
        handleBarsHelperInst.checkIFConditions("ifGreater");
        handleBarsHelperInst.callRegisterHelper("index_1");
    }
    eventBinding() {
        window.global.eventBindingInst.bindLooping({
            'click .quick-view' : 'quickViewModalON',
            'click #productViewModal .color-selection .img-swatch' : 'getInventoryStatus',
            'click #productViewModal .size-selection .innerCont a' : 'getInventoryStatus',
            'mouseenter #recommended-products .aem-recommend-item[data-producttype=""]:not(.request-pending-sm)' : 'getProductTypeOnHover',
            'mouseleave #recommended-products .aem-recommend-item.request-pending-sm' : 'hideGifImage',
            'click #productViewModal .product-wrapper .custom-checkbox': 'initiateProductPriceShow'
        }, self);
    }
    getProductTypeOnHover(ele, evt){
        if(window.innerWidth < 991) return;
        let quickViewPartNo;
        (function (ele) {
            let $ele = $(ele);
            quickViewPartNo = $ele.data("partno");
            if (ajaxFiredElem.indexOf(quickViewPartNo) != -1 || quickViewPartNo.indexOf("BUN") != -1) {
                return;
            }
            ajaxFiredElem.push(quickViewPartNo);
            $ele.addClass("request-pending-sm");
            request.ajaxCall(apiConfigInst.getApiConfig("quickView").getProductType(quickViewPartNo))
                .then(data => {
                    let {core} = data;
                    let pType = core.product_type;
                    let isQuickViewEnable = core.product_disableQuickView || "false";
                    if (pType == "BundleBean" || pType == "GiftCard" ||
                        (pType == "PackageBean" && core.itemType == "GIFT_CARD") ||
                        ((isQuickViewEnable == "true" || core.product_disableQuickView) &&
                            (pType == "PackageBean" || pType == "ItemBean" || pType == "ProductBean"))) {
                        $ele.find(".quick-view").addClass("hide");
                    } else {
                        $ele.find(".quick-view").removeClass("hide");
                    }
                    $ele.removeClass("request-pending-sm").addClass("quick-view-enabled").attr("data-producttype", pType);
                })
                .catch(error => {
                    console.log(error);
                    $ele.removeClass("request-pending-sm");
                });
        })(ele);
    }
    productInventoryMessages() {
        let ajaxOption = apiConfigInst.getApiConfig("productInventoryStatus");
        const sessionData = getStorage("productStorage");
        if(!sessionData) {
            request.ajaxCall(ajaxOption)
            .then(data => {
                self.productInventoryStatusObj = data;
                setStorage("productStorage", data);
            })
            .catch(error => {
                console.log(error)
            });
        } else {
             self.productInventoryStatusObj = typeof sessionData == 'string' ? JSON.parse(sessionData) : sessionData;
        }
    }
    getInventoryStatus(ele,evt){
        evt.preventDefault();
        let $ele = $(ele);
        let $parentLiEle =  $ele.parents("li");
        let $btnEle = $("#productViewModal .btn-add-to-bag");
        let $inventoryEle = $("#productViewModal .inventory-status-message");
        let {partnumber,producttype} = $ele.parents(".product-info-wrapper")[0].dataset;
        let selectedSqu = $parentLiEle.attr("data-partnoswatch");
        $(".select-size-wrapper .inline-message").hide();
        $("#productViewModal .img-swatch,#productViewModal .innerCont").removeClass("active unavailable-select");
        $parentLiEle.removeClass("out-of-stock unavailable").addClass("active on");
        if($btnEle.hasClass("non-buyable-product-btn")){
            return;
        }
        $btnEle.attr("disabled",true);
        let swatchColor = $ele.attr("alt");
        cartAPI.getSwatchesInventory(apiConfigInst.getApiConfig("quickView").swatchInventory(partnumber), selectedSqu, (filteredData) =>{
            if(!filteredData) { self.hideGifImage();return; };
            let {variant_inventorystatus, variant_backorderdate, product_affirmIneligible} = filteredData;
            variant_inventorystatus = cartAPI.readInventoryvalueAPI(variant_inventorystatus);
            let inventoryStatus = isInventoryStatusValid(variant_inventorystatus);
            if(inventoryStatus!="Available") {
                $inventoryEle.removeClass("available-status").html(self.productInventoryStatusObj[inventoryStatus]);
            }
            switch (inventoryStatus.toLowerCase()) {
                case "available":
                case "limited":
                    $(".back-oderable-date").html("");
                    inventoryStatus.toLowerCase() == "available" && $inventoryEle.addClass("available-status").html("");
                    $btnEle.removeAttr("disabled");
                    break;
                case "backordered":
                case "backorderable":
                    $(".back-oderable-date").html(" until " + variant_backorderdate != "" ? dateFormatInst.formatToNewDate(new Date(variant_backorderdate.replace(/ /g, "T")), variant_backorderdate) : "");
                    $btnEle.removeAttr("disabled");
                    break;
                case "preorderable":
                    $(".back-oderable-date").html(" - available " + variant_backorderdate != "" ? dateFormatInst.formatToNewDate(new Date(variant_backorderdate.replace(/ /g, "T")), variant_backorderdate) : "");
                    $btnEle.removeAttr("disabled");
                    break;
                case "unavailable":
                    $parentLiEle.addClass("out-of-stock").removeClass("active");
                    if($parentLiEle.hasClass("unavailable-select"))
                        $parentLiEle.removeClass("unavailable-select");
                    else
                        $parentLiEle.addClass("unavailable-select");
                    break;
                case "no longer available":
                case "nolongeravailable":
                    $parentLiEle.addClass("unavailable").removeClass("active");
                    if($parentLiEle.hasClass("unavailable-select"))
                        $parentLiEle.removeClass("unavailable-select");
                    else 
                        $parentLiEle.addClass("unavailable-select");
                    break;
                default:
                    break;
            }
            if(producttype.toLowerCase() == "productbean" || producttype.toLowerCase() =="packagebean"){
                if($(ele).hasClass("img-swatch")) {
                    $("#productViewModal .preview-image").attr("src",$(ele).attr("data-imagepreview"));
                }
                $("#productViewModal .sku-cloth-select .product-value").html(swatchColor);
                if( $parentLiEle.hasClass("active") && product_affirmIneligible != "Y" && inventoryStatus != 'PreOrderable' && inventoryStatus != 'Backordered' && inventoryStatus != 'Backorderable' && inventoryStatus != 'noLongerAvailable' && inventoryStatus != 'No longer available' && inventoryStatus != 'Unavailable'){
                    let price = $("#productViewModal .current_price").text();
                    // let price = $("#productViewModal .current_price").text() * 100;
                    // $("#productViewModal .affirm-as-low-as").removeClass("hide").attr("data-amount", price);
                    // affirmkey(window, {public_api_key:  $(".affirm_key_quick_view").attr("publicKey"),script:  $(".affirm_key_quick_view").attr("affirmUrl")}, "affirm", "checkout", "ui", "script", "ready");
                    productSupportObj.affirmCheck(product_affirmIneligible, inventoryStatus, price);
                } 
                // else{
                //     $("#productViewModal .affirm-as-low-as").addClass("hide");
                // }
            }
            userCategory(true);
        });
    }
    callBVAPI(partNumber,cb){
        let nativeReviewStatistics,starRatingResult;
        request.ajaxCall({
            url:`//api.bazaarvoice.com/data/statistics.json?apiversion=5.4&passkey=${$("#bazarVoicePassKey").val()}&Filter=ProductId:${partNumber}&Include=Products&stats=NativeReviews&callback=?`,
            dataType:'jsonp'
        }).then(res => {
            if(res.Results && res.Results.length > 0){
                nativeReviewStatistics =  res.Results[0].ProductStatistics.NativeReviewStatistics;
                starRatingResult = productSupportObj.roundUpBazzarVoiceRating(nativeReviewStatistics.AverageOverallRating == null || nativeReviewStatistics.AverageOverallRating == undefined ? 0 : nativeReviewStatistics.AverageOverallRating);
            }
            cb({
                product_reviewRating : typeof starRatingResult != "undefined" ? (starRatingResult.toString().indexOf(".")!=-1 ? starRatingResult.toString().replace(".","-") : (starRatingResult == 0 ? starRatingResult.toString() : starRatingResult.toString()+"-0")) : "0",
                product_reviewCount : typeof nativeReviewStatistics != "undefined" ? (nativeReviewStatistics.TotalReviewCount == "" ? 0 : nativeReviewStatistics.TotalReviewCount) : "0"
            })
        }).catch(error => {cb(false)});
    }
    callPromotionBanner(experienceFragmentPath, cb){
        const promoLen = experienceFragmentPath.length;
        if(!promoLen) {cb(false);return false;}
        let counter = 0, promoItem = [];
        for(let index in experienceFragmentPath){
            request.ajaxCall({
                url: `//${window.location.host}${experienceFragmentPath[index]}.plain.html`
                // url: `https://mattel-sites-stage64.adobecqms.net/${experienceFragmentPath[index]}.plain.html`
            }).then(res => {
                promoItem.push(res);
                counter++;
                if(counter == promoLen ){
                    cb(promoItem);
                }
            }).catch(error => {cb(false);});
        }   
    }
    checkAffirmEligible(productType,affirmValue){
        if((productType == "itembean" || productType == "productbean" || productType == "packagebean") && !($("#productViewModal .product-wrapper .select-size-wrapper").length)){
            productSupportObj.affirmCheck(affirmValue);
        }
    }
    checkProductHasQuickSell(associations){
        const hasQuickSellData = associations && _.filter(associations, (item) => {
            return item.association_type == "QUICK" && item.product.variants.length && item.product.variants[0].core.variant_inventorystatus == "Available";
        });
        if(hasQuickSellData.length){
            let inventoryAttrs;
            _.map(hasQuickSellData,item =>{
                inventoryAttrs = item.product.variants[0];
                Object.assign(item.product, {
                    prices : cartAPI.getActivePriceData(inventoryAttrs.pricing),
                    inventoryNA : inventoryAttrs.inventory.inventorystatus != "Available"
                 });
                 storeAndGetPayloadData('set',{
                     "inventoryStatus":inventoryAttrs.core.variant_inventorystatus,
                     "backorderdate": inventoryAttrs.core.variant_backorderdate,
                     "recieptid": inventoryAttrs.core.variant_itematpreceiptid,
                     "productType": item.product.core.product_type,
                     "pricing":inventoryAttrs.pricing,
                     "type": "Quick"
                }, inventoryAttrs.id);
            });
        }
        return hasQuickSellData;
    }
    getProductSwatchesData(options){
        if(!options.length) return 0;
        return ;
    }
    renderTemplate(data, producttype, ele){
        let {core,variants,attributes, displayAssociations} = data;
        let $modal = $("#productViewModal");
        handleBarTemplateInst.loadTemplate("#quickViewModalTemp","#quickViewModal",data ,"replace");
        $modal.find(".product-info-wrapper").attr("data-affirm-eligible", core.product_affirmIneligible);
        let productStatus = isInventoryStatusValid(core.product_type.toLowerCase()=="itembean" ? cartAPI.readInventoryvalueAPI(variants[0].core.variant_inventorystatus) : core.headerInventoryStatus);
        productSupportObj.showInventoryStatus(data, productStatus, core.product_type, self.productInventoryStatusObj, core.headerInventoryStatus);
        if(core.prices.listPrice>=95) $modal.find(".guarantee-message").removeClass("hide");
        productSupportObj.checkProductCallOut(attributes, core.product_releaseDateWeb, core.product_type, true);
        userCategory(true);
        if(core.prices.salePrice == undefined) {
            $modal.find(".product-info-wrapper #addToBagBtn").attr("disabled", "true");
        }
        if(!core.buyProduct){
            let $quicksellele;
            displayAssociations.length && _.each(displayAssociations, quicksellItem => {
                $quicksellele = $modal.find(`.product-info-wrapper .addon-desc[data-partnumber=${quicksellItem.product.partnumber}]`).closest(".addon-box");
                $quicksellele.length && $quicksellele.addClass("non-buyable-product-section");
            });
            $modal.find(".product-info-wrapper #addToBagBtn").addClass("non-buyable-product-btn").attr("disabled", "true");
        }
        if($(ele).parents(".personalization_recommendation_products").length || $(ele).parents('.mboxDefault .recommended-products').length){
            $modal.find(".product-info-wrapper #addToBagBtn").addClass("target-comp-btn");
        }
        $rootEle.removeClass("request-pending");
        if($($modal).find(".non-buyable-product-btn").length){
            $($modal).find(".inventory-status-message").addClass("non-buyable-error").html("Currently Unavailable")
        }
        $modal.modal("show");
        self.checkAffirmEligible(core.product_type.toLowerCase(), core.product_affirmIneligible);
    }
    hideGifImage(error){
        $rootEle.removeClass("request-pending request-pending-sm");
    }
    quickViewModalON(ele, evt){
        $rootEle.addClass("request-pending");
        let $ele = $(ele);
        let {partno, producttype} = $ele.parent().data();
        let itemsToCheck = ['BV','ExperienceFragment'];
        request.ajaxCall(apiConfigInst.getApiConfig("quickView").details(partno))
        .then(data => {
            let {core, associations, variants, options} = data;
            if(!variants.length){
                self.hideGifImage();
                window.global.errorHandling.PDPAPI({status:"showErrorMessage" });
                console.log(`%c PDP API Error:Inventory data not found.`, "background: red; color:white; font-weight:bold");
                return;
            }
            Object.assign(core, { 
                product_pageUrl: $ele.prev().attr("href") || $ele.next().attr("href"),
                product_releaseDateWeb: core.product_releaseDateWeb == undefined ? "" : core.product_releaseDateWeb,
                product_reviewRatingSR: parseInt(core.product_reviewRating),
                prices: cartAPI.getActivePriceData(variants[0].pricing),
                clothingColor: options && (options[0].name == "Clothing Color" || options[0].name == "Color"),
                clothingSize: options && options[0].name == "Clothing Size",
                product_safetyMessage: core.product_safetyMessage && _.unescape(core.product_safetyMessage),
                buyProduct: core.product_buyable == "1"
            });
            if(variants.length > 1 && (producttype == "PackageBean" || producttype == "ProductBean")){
               let status = _.countBy(_.map(variants, item => {
                    let {variant_inventorystatus} = item.core;
                    variant_inventorystatus = cartAPI.readInventoryvalueAPI(variant_inventorystatus);
                    if(typeof variant_inventorystatus == "undefined"){
                        variant_inventorystatus = "";
                    }
                    item.variantClassName = variant_inventorystatus.toLowerCase() == "unavailable" ? "out-of-stock" : (variant_inventorystatus =="No longer available" || variant_inventorystatus =="noLongerAvailable" ? "unavailable" : "may-available");
                    return variant_inventorystatus;
                }));
                core.headerInventoryStatus = status["Available"] ? "Available" : (status["Limited"] ? "Limited" : (
                    status["Backorderable"] ? "Backorderable" : (status["Backordered"] ? "Backordered" : (status["Preorderable"] ? "Preorderable" : (
                    status["Unavailable"] == variants.length ? "Unavailable" :(status["No longer available"] == variants.length ? "No longer available" :(status["noLongerAvailable"] == variants.length ? "noLongerAvailable" : undefined
                )))))))
                data.childProducts = variants;
                if(core.clothingColor) core.selectedClothingColor = typeof options[0].values == "object" ? options[0].values[0] : '';
            } else {
                core.headerInventoryStatus = cartAPI.readInventoryvalueAPI(variants[0].core.variant_inventorystatus);
            }
            let packageBeanAttrs = {};
            if(producttype == "PackageBean"){
                let hasComponent = associations && _.filter(associations, (association) => association.association_type == "COMPONENT"),
                    compIdsArr = [];
                hasComponent.forEach((comp)=> comp.product.variants.length && compIdsArr.push(comp.product.variants[0].id));
                packageBeanAttrs = {
                    "associations": hasComponent.length && hasComponent,
                    "associationIds": compIdsArr
                }
            }

            storeAndGetPayloadData('set',Object.assign({
                "inventoryStatus":variants[0].core.variant_inventorystatus,
                "backorderdate": variants[0].core.variant_backorderdate,
                "recieptid": variants[0].core.variant_itematpreceiptid,
                "productType": producttype,
                "type" : variants.length > 1 && "Swatches",
                "headerInventory": variants.length > 1 && variants,
                'pricing':variants[0].pricing
            }, packageBeanAttrs), variants[0].id);
            data.displayAssociations = self.checkProductHasQuickSell(associations);
            let done = itemsToCheck.length;
            self.callPromotionBanner(data.experienceFragmentPath, res=>{
                data.experienceFragmentPath = res || [];
                done -= 1;
                if(!done) self.renderTemplate(data, producttype, ele);
            });
            self.callBVAPI(partno, res => {
                done -= 1;
                Object.assign(core, res || {});
                if(!done) self.renderTemplate(data, producttype, ele);
            });
        }).catch(error => {
            window.global.errorHandling.PDPAPI(error);
            self.hideGifImage(error);
        });
        typeof quickview_event == "function" && quickview_event(ele);
    }
    // Product price show on click
    initiateProductPriceShow(ele) {
        let {affirmEligible,producttype} = $(ele).parents(".product-info-wrapper")[0].dataset;
        productSupportObj.productPriceShow(producttype, affirmEligible);
    }
}

let dateFormatInst = new dateFormat();
const request = new ajaxRequest();
const { handleBarTemplateInst, cartAPI, getStorage, setStorage, handleBarsHelperInst } = window.global;
const apiConfigInst = new apiConfig();
if(!window.global.QuickView){
    window.global.QuickView = new QuickViewFunctionality();
}
