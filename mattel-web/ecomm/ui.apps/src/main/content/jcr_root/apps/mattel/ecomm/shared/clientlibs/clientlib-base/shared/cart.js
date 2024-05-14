import ajaxRequest from '../shared/ajaxbinding';
import apiConfig from '../shared/apiConfig';
import {storeAndGetPayloadData} from '../shared/productSupportiveFunctions';
let self,
    quickViewWraper = "#productViewModal",
    pdpViewWraper = ".pdpproduct",
    $rootEle = $(".root"),
    $addToBagBtnEle = $("#addToBagBtn"),
    $addonAddToBagBtnEle = $("#addProductAddOn"),
    miniAndAddonModal = '#productAddOnModal',
    productInfoEle = ".product-info-wrapper",
    inventoryEle = ".inventory-status-details",
    trackProductFindingAttr,
    trackProductBuyingAttr,
    trackProductListingAttr,
    uniqueId;

export default class CartFunctionality {
    constructor() {
        self = this;
        this.eventBinding();
        this.getTrackingAttr();
    }
    eventBinding() {
        window.global.eventBindingInst.bindLooping({
            "click  #addToBagBtn": "addToCart",
            "click  #addProductAddOn": "addAddOnsToCart",
            "click .close-product-addOns,#addons-modal-close": "closeModal",
            "click .product-addOn-wrapper .show-details,.product-addOn-wrapper .hide-details": "showHideAddOnDetails"
        }, self);
        this.bindAddonModalEvents();
    }
   getTargettrackingAttr(ele){
       let $body = $('body')
        if($body.hasClass("ecomm-plp-page")){
            trackProductBuyingAttr="personalization:recs";
        }
        else if($body.hasClass("ecomm-pdp-page")){
            trackProductBuyingAttr="pdp:targetrecs";
        }
        else if($body.hasClass("ecomm")){
            trackProductBuyingAttr="personalization:justforyou:recs";
        }
    }
    getTrackingAttr() {
        let referringURL = document.referrer,
            currentURL = window.location.href;

        if (referringURL == undefined) return;
        trackProductFindingAttr = "Page navigation";
        if(referringURL.indexOf("americangirl.com") != -1){
            trackProductListingAttr = referringURL;
        }
        if (referringURL.indexOf('SearchDisplay') >= 0) {
            trackProductFindingAttr = "Onsite search";
        }
        if(currentURL.indexOf('?icid=') >= 0){
            trackProductFindingAttr = "Internal promotion banner";
        }
        else if(currentURL.indexOf('?cid=') >= 0){
            trackProductFindingAttr = "External promotion";
        }
        else if(currentURL.indexOf('rp_reflink=') >= 0){
            trackProductFindingAttr = "Recommendation";
        }
        else if(currentURL.indexOf('SearchDisplay') >= 0){
            trackProductFindingAttr = "Onsite search";
        }
    }
    getProductTypeUseCases(productType, wrprEle) {
        productType = productType && productType.toLowerCase();
        return {
            dynamicKit:  productType == "dynamickitbean" && $('.bitty-twins-section').length && true,
            bundle: productType == "bundlebean" && true,
            colorSwatches: $(`${wrprEle} .size-selection-preference .color_category`).length && true,
            sizeSwatches: $(`${wrprEle} .size-selection-preference .size_category`).length && true,
            onlyProduct: productType == "itembean" && true, // itembean
        };
    }
    addAddOnsToCart(ele, evt) {
        $addonAddToBagBtnEle = $(ele);
        if($rootEle.hasClass("request-pending")){
            evt.preventDefault();
            return false;
        } 
        const $ele = $(ele);
        if (!$ele.hasClass("btn-add-to-bag")) {
            $(miniAndAddonModal).modal("hide");
            return;
        }
        let addOnVariantids = [];
        const $addonProductEle = $(".product-addOn-wrapper .addon-option");
        const hasOnlySingleProduct = !$addonProductEle.find("input").length;
        if (hasOnlySingleProduct) {
            if ($addonProductEle.find(".addon-option-wrapper").length) return;
            addOnVariantids.push(cartAPI.getVariantIdOfSKU($addonProductEle, $addonProductEle.data("addonpartnumber")));
        } else {
            let checkedItems = false;
            _.each($addonProductEle, item => {
                _.each($(item).find("input"), item => {
                    if($(item).is(":checked")){
                        checkedItems = true;
                    }
                })
            });
            if(checkedItems){
                // single (item bean)
                _.each($addonProductEle.find(".addon-item input:checked"), item => {
                    addOnVariantids.push(cartAPI.getVariantIdOfSKU(item, $(item).data("addonpartnumber")));
                });
                // grouping (product bean)
                let $normalCheckbox =  $addonProductEle.find(".addon-option-container input"),
                    $checkedCheckbox =  $addonProductEle.find(".addon-option-container input:checked");
                    if($normalCheckbox.length == $checkedCheckbox.length){
                        addOnVariantids.push(cartAPI.getVariantIdOfSKU($(".both-sku-span"), $(".both-sku-span").data("bothsku")));
                    }
                    else{
                        _.each($addonProductEle.find(".addon-option-container input:checked"), (item,index) => {

                            addOnVariantids.push(cartAPI.getVariantIdOfSKU(item, $(item).data("addonpartnumber")));
                        });
                    }
                addOnVariantids = _.unique(addOnVariantids);
            }
            else{
                $(".product-addOn-wrapper .inline-message").removeClass("hide");
                return;
            }
        }
        $rootEle.addClass("request-pending");
        $addonAddToBagBtnEle.attr("disabled", true);
        const parentIdisObj = typeof self.addonParentId == "object";
        self.callAddToCartAPI(addOnVariantids, {
            parentId: parentIdisObj ? self.addonParentId[0] : self.addonParentId,
            type: "child"
        }, (data) => {
            if (!data) {
                console.log("Addtocart API failed");
                $rootEle.removeClass("request-pending");
                $addonAddToBagBtnEle.attr("disabled", false);
                return;
            }
            const mergedVariantIds = parentIdisObj ? self.addonParentId.concat(addOnVariantids) : addOnVariantids.push(self.addonParentId || null) && addOnVariantids;
            self.callMinicartModal(data, {}, mergedVariantIds);
            self.addonParentId = "";
            uniqueId = "";
        });
        evt.preventDefault();
    }
    addToCart(ele, evt) {
        let { hasaddon, producttype, quickview, parentpartnumber, variantId, parentvariantid} = ele.dataset;
        trackProductBuyingAttr = undefined;
        if (quickview == "true" && self.quickViewPreValidation(ele)) return;
        else if (quickview == "false" && self.pdpPrevalidation(producttype)) return; 
        const wrprClassName = quickview == "true" ? quickViewWraper : pdpViewWraper;
        let variations = _.findKey(self.getProductTypeUseCases(producttype, wrprClassName), (val) => { return val == true;});
        if (variations == undefined) {
            if(producttype != "PackageBean" || producttype != "ProductBean"){
                variations = "onlyProduct";
            } else{
                console.log("Product type Variations not handled in the current implementation..");
                return;
            }
        }
        $rootEle.addClass("request-pending");
        $addToBagBtnEle.attr("disabled", true);
        let variantIds,
            quickSellIds = self.getQuickSellIds(quickview == "true");
        switch (variations) {
            case "bundle":
                let { ids, checkIfBundleHasAddon, addonParentNumber, addonParentVariantid } = self.getBundleVaraintIds(wrprClassName);
                variantIds = ids;
                if (checkIfBundleHasAddon) {
                    hasaddon = "true";
                    parentpartnumber = addonParentNumber;
                    parentvariantid = addonParentVariantid;
                }
                break;
            case "colorSwatches":
                variantIds = self.getColorVaraintId(wrprClassName);
                break;
            case "sizeSwatches":
                variantIds = self.getSizeVaraintId(wrprClassName);
                break;
            case "onlyProduct":
                variantIds = cartAPI.getVariantIdOfSKU(ele, parentpartnumber);
                break;
            case "dynamicKit":
                variantIds = self.getDynamicKitPayload(variantId,producttype,parentpartnumber);
                break;
            default:
                break;
        };
        if (quickSellIds.length)
            variantIds = typeof variantIds == "object" ? variantIds.concat(quickSellIds) : quickSellIds.push(variantIds) && quickSellIds;
        let isGetCallAnonymously = (hasaddon == "true" || producttype == "PackageBean" ) && {
            type: "parent",
            productType: producttype,
            hasaddon,
            parentvariantid
        };
        self.callAddToCartAPI(variantIds, isGetCallAnonymously, (data) => {
            if (!data) {
                console.log("Addtocart API failed");
                $rootEle.removeClass("request-pending");
                $addToBagBtnEle.attr("disabled", false);
                return;
            }
            if (hasaddon == "true")
                self.callAddonModal(parentpartnumber, variantIds);
            else{
                self.callMinicartModal(data, ele.dataset, variations == "dynamicKit" ? variantId :variantIds );
            }
        },variations == "dynamicKit" && "dynamicKit");
        return;
    }
    getColorVaraintId(wrprClassName) {
        let $colorSelectionEle = $(wrprClassName).find(".size-selection-preference .color_category .innerCont.active"),
            swatchPartNo = $colorSelectionEle.data("partnoswatch");
        return $colorSelectionEle.length && cartAPI.getVariantIdOfSKU($colorSelectionEle, swatchPartNo);
    }
    getSizeVaraintId(wrprClassName) {
        let $sizeSelectionEle = $(wrprClassName).find(".size-selection-preference .size_category .innerCont.active"),
            swatchPartNo = $sizeSelectionEle.data("partnoswatch");
        return $sizeSelectionEle.length && cartAPI.getVariantIdOfSKU($sizeSelectionEle, swatchPartNo);
    }
    getBundleVaraintIds(wrprClassName) {
        let $bundlewrappr = $(`${wrprClassName} .product-bundle-component`),
            checkIfBundleHasAddon = false,
            addonParentNumber,
            $bundleCheckboxEle,
            hasQuickSellExist,
            addonParentVariantid,
            ids = [],
            $item,
            id;
        _.each($bundlewrappr, item => {
            $item = $(item);
            $bundleCheckboxEle = $item.find(".bundle-check-item input");
            hasQuickSellExist = $item.find(".addon-option input:checked");
            if (!$bundleCheckboxEle.is(":checked")){
                hasQuickSellExist.length && ids.push(cartAPI.getVariantIdOfSKU(hasQuickSellExist, hasQuickSellExist.data("partnumber")));
                return;
            }
            if ($item.find(".size_category").length) {
                id = self.getSizeVaraintId($item);
            } else if ($item.find(".color_category").length) {
                id = self.getColorVaraintId($item);
            } else {
                id = cartAPI.getVariantIdOfSKU($bundleCheckboxEle, $bundleCheckboxEle.data("partnumber"));
                if ($item.find(".bundle-check-item input:checked").data("hasaddon")) {
                    checkIfBundleHasAddon = true;
                    addonParentNumber = $bundleCheckboxEle.data("partnumber");
                    addonParentVariantid = $bundleCheckboxEle.data("variantId");
                }
            }
            if(hasQuickSellExist.length){ // quick sell
                _.each(hasQuickSellExist, item => {
                    ids.push(cartAPI.getVariantIdOfSKU($(item), $(item).data("partnumber")));
                });
            }
            id && ids.push(id);
        });
        return {
            ids,
            checkIfBundleHasAddon,
            addonParentNumber,
            addonParentVariantid
        };
    }
    getDynamicKitPayload(variantId,producttype,parentpartnumber){
        let compArr=[],
            parentObj = null,
            dollChoiceSection = $(".doll-choice-section"),
            refNo = variantId+Math.random().toString(16).slice(2),
            invStatus = $('.inventory-status').find('span.inventory-status-message').text(),
            invDate = $('.inventory-status').find('span.back-oderable-date').data('org-date');

        _.each([1,2], (item)=>{
            compArr.push({
                "quantity":  $("#bittykitcomponent-"+item).data("partnumber") == "HCH49-F01A" ? 2 : 1,
                "id": $("#bittykitcomponent-"+item).data("variant-id"), 
                "properties": {
                    "sku":$("#bittykitcomponent-"+item).data("partnumber"),
                    "reference": refNo,
                    "type": "child",
                    "productType":$("#bittykitcomponent-"+item).data("product-type"),
                    "association": $("#bittykitcomponent-"+item).data("association-type"), 
                    "variant_inventorystatus": $("#bittykitcomponent-"+item).data("inventory-status"),
                    "variant_backorderdate": ($("#bittykitcomponent-"+item).data("backordered-date")==undefined)?"":$("#bittykitcomponent-"+item).data("backordered-date"),
                    "variant_itematpreceiptid": ($("#bittykitcomponent-"+item).data("receipt-id")==undefined)?"":$("#bittykitcomponent-"+item).data("receipt-id")
                }
            });
        });
        _.each(dollChoiceSection, (item)=>{
            compArr.push({
                "quantity": 1,
                "id": $('ul li.active', item).data('doll-variant'), 
                "properties": {
                    "sku":$('ul li.active', item).data("doll-partnumber"),
                    "reference": refNo,
                    "type": "child",
                    "productType": $('ul li.active', item).data('doll-product-type'),
                    "association": $('ul li.active', item).data('doll-association-type'), 
                    "variant_inventorystatus": $('ul li.active', item).data('inventory-status'),
                    "variant_backorderdate": ($('ul li.active', item).data('backordered-date')==undefined)?"":$('ul li.active', item).data('backordered-date'),
                    "variant_itematpreceiptid": ($('ul li.active', item).data('receipt-id')==undefined)?"":$('ul li.active', item).data('receipt-id'),
                }
            });
        });
        parentObj = {
            "quantity": 1,
            "id":variantId,
            "properties":{
                "reference": refNo,
                "type": "parent",
                "productType":producttype,
                "configurationId":parentpartnumber,
                "ImageURL": $('.pdp-img-viewer-holder aside ul li:first img').attr('src'),
                "FromGT": "false",
                "RetailStoreId": "",
                "variant_inventorystatus": (invStatus=="")?"Available":invStatus,
                "variant_backorderdate": (invDate==undefined)?"":invDate,
                "variant_itematpreceiptid": ($(".product-info-wrapper.parent[data-partnumber="+parentpartnumber+"]").data("itematpreceiptid")==undefined)?"":$(".product-info-wrapper.parent[data-partnumber="+parentpartnumber+"]").data("itematpreceiptid"),
                "components":compArr
            }
        };
        //compArr.unshift(parentObj);
        return {
            "items": [parentObj]
        }
    }
    getQuickSellIds(isQuickView, productType) {
        let hasQuickSell = $(`${productInfoEle} ${isQuickView ? '.marketing-addons' : '.associations-addons'}`);
        if(productType == "BundleBean" && !hasQuickSell.length){
            hasQuickSell = $(`${productInfoEle} .addon-option.addon-box`);
        }
        const quickSellVariantId = [];
        if (!hasQuickSell.length) return quickSellVariantId;
        _.each(hasQuickSell.find("input:checked"), item => {
            quickSellVariantId.push(cartAPI.getVariantIdOfSKU(item, $(item).data('partnumber')));
        });
        return quickSellVariantId;
    }
    callAddonModal(partNumber, variantIds) {
        request.ajaxCall({
            url:`//${window.location.host}/bin/requesthandler.web.productaddon.json?partnumber=${partNumber}&storeId=ag_en&domainId=ag_en`,
            type: 'get',
            accept: 'application/json',
            contentType: 'application/json'
        }).then(data => {
            let addons = data.product.associations;
            self.addonModalCB(addons);
        }).catch(error => {
            console.log(error);
            $rootEle.removeClass("request-pending");
        });
    }
    closeModal(ele, evt){
        if($(ele).hasClass('shopping-bag')){
            evt.preventDefault();
            $(miniAndAddonModal).modal("hide");
            return;
        }
        self.callMinicartModal(self.getCartObj || {} , {}, self.addonParentId);
        if($(ele).is("#addons-modal-close") || $(ele).is("#closeProductAddOn")){
            self.addonParentId = "";
        }
    }
    bindAddonModalEvents(){
        $(miniAndAddonModal).on('hidden.bs.modal', function () {
            // only trigger when click on outside of the modal
            setTimeout(()=>{ if(self.addonParentId) self.addonParentId = "";},1000);
        });
    }
    callMinicartModal(data, dataset, variantIds) {
        const productDatas = self.getProductDetailsForMinicart(data, dataset, variantIds);
        handleBarTemplateInst.loadTemplate("#productTmpl", `#addOnsModal`, productDatas, "replace");
        $rootEle.removeClass("request-pending");
        $addToBagBtnEle.attr("disabled", false);
        $addonAddToBagBtnEle.attr("disabled", false);
        $(miniAndAddonModal).modal("show");
    }
    addonModalCB(addons) {
        self.addonAttrs = {};
        if (addons.length == 1) {
            let { product_type } = addons[0].product.core;
            let { variants } = addons[0].product;
            if (product_type == "ItemBean") {
                addons[0].singleNochild = true;
                let pricing = cartAPI.getActivePriceData(variants[0].pricing);
                variants[0].pricing.price = pricing.salePrice;
                variants[0].pricing.strikePrice = parseInt(pricing.salePrice || 0) < parseInt(pricing.listPrice || 0) ? pricing.listPrice : false; 
            } else if (product_type == "ProductBean" || product_type == "PackageBean") {
                addons[0].singleWithchild = true;
                if (addons[0].product.variants) {
                    addons[0].product.variants = self.getChildProductsForAddon(variants);
                }
            }
            self.addonAttrs[variants[0].id] = {
                'productType' : product_type,
                'associations' : addons[0].association_type,
                'inventoryStatus': variants[0].core.variant_inventorystatus || '',
                'backorderdate': variants[0].core.variant_backorderdate || '',
                'recieptId': variants[0].core.variant_itematpreceiptid || '',
                'pricing':variants[0].pricing
            };
        } else {
            _.map(addons, data => {
                data.hasMultiItems = true;
                if(data.product.variants.length > 1){
                    data.mutipleVarientItem = true;
                }
                if (data.product.variants && data.product.variants.length == 1) {
                    data.product.variants[0].singleEntry = true;
                    data.product.variants = self.getChildProductsForAddon(data.product.variants);
                } else if (data.product.variants) {
                    data.product.variants = data.product.variants.sort(function (a, b) {
                        return a.sequence - b.sequence;
                    });
                    data.product.variants = self.getChildProductsForAddon(data.product.variants);
                }
                _.each(data.product.variants,(variant)=>{
                    self.addonAttrs[variant.id] = {
                        'productType' : data.product.core.product_type,
                        'associations' : data.association_type,
                        'inventoryStatus': variant.core.variant_inventorystatus || '',
                        'backorderdate': variant.core.variant_backorderdate || '',
                        'recieptId': variant.core.variant_itematpreceiptid || '',
                        'pricing':variant.pricing
                    };

                });
            });
        }
        handleBarTemplateInst.loadTemplate("#addOnsTmpl", "#addOnsModal", addons, "replace");
        $(".inline-message").addClass("hide");
        $rootEle.removeClass("request-pending");
        $addToBagBtnEle.attr("disabled", false);
        $addonAddToBagBtnEle.attr("disabled", false);
        $(miniAndAddonModal).modal("show");
    }
    getChildProductsForAddon(childProducts) {
        let bothSku,bothVariantId = "", pricing;
        childProducts.forEach(function(data) {
            if (data.core.variant_definingAttributes && data.core.variant_definingAttributes.Location && "both" == data.core.variant_definingAttributes.Location.toLowerCase()) {
                data.notBothSku = false;
                bothSku = data.core.sku;
                bothVariantId = data.id;
            } else {
                data.notBothSku = true;
                data.loc = data.core.variant_definingAttributes ? data.core.variant_definingAttributes.Location.toLowerCase() : '';
            }
        });
        childProducts.forEach(function(data) {
            pricing = cartAPI.getActivePriceData(data.pricing);
            data.bothSku = bothSku;
            data.bothVariantId= bothVariantId;
            data.pricing.price = pricing.salePrice;
            data.pricing.strikePrice = parseInt(pricing.salePrice || 0) < parseInt(pricing.listPrice || 0) ? pricing.listPrice : false; 
        });
        return childProducts;
    }
    getFinalCartCount(data){
        let childQnty=0;
        _.filter(data.items, item => {
            if(item.properties && (item.properties.type == "child")){
                childQnty+=_.isNumber(item.quantity) ? item.quantity : 0;
            }
            return childQnty;
        });
        return parseInt((data.item_count - childQnty) || 0);
    }
    getProductDetailsForMinicart(getCartResponse, dataset, variantIds) {
        if(_.isEmpty(dataset))
            dataset = $(".product-wrapper #addToBagBtn")[0].dataset;
        let { hasaddon, producttype, quickview, parentpartnumber } = dataset;
        const { items, total_price } = getCartResponse;
        const finalCartCount = self.getFinalCartCount(getCartResponse);
        let productArr = [], newaddonData = [], product,listPrice, salePrice, discountPrice,
            pageReferal = window.location.href,quickSellIds, isParentType,
            count=0,
            isDynamicKit = producttype == "DynamicKitBean" && $('.bitty-twins-section').length;
        // move the quick sell id to last position
        if((quickview == "true" || $(".addon-option.addon-box .custom-checkbox").is(":checked")) && typeof variantIds == "object") {
            quickSellIds = self.getQuickSellIds(quickview == "true", producttype);
            _.each(quickSellIds, item => variantIds.push(variantIds.splice(variantIds.indexOf(item), 1).pop()));
        }
        _.each(typeof variantIds == "object" ? variantIds : variantIds.split(","), variantId => {
            product = _.find(items, function (item) {
                return item.id == variantId;
            });
            if (!product) return;
            listPrice = product.properties.list_price ? product.properties.list_price : cartAPI.formatWithDelimiters(product.original_price,2);
            salePrice = cartAPI.formatWithDelimiters(product.discounted_price,2);
            discountPrice = cartAPI.formatWithDelimiters(product.total_discount,2);
            if(quickSellIds && quickSellIds.indexOf(variantId)!=-1){
                productArr.push({
                    "salePrice":salePrice,
                    "listPrice": parseInt(salePrice || 0) < parseInt(listPrice || 0) ? listPrice : "",
                    "discountPrice": parseInt(discountPrice) ? discountPrice : '',
                    "productName": product.product_title,
                    "imageLink": product.featured_image.url,
                })
                count++;
            }
            else if(product.properties && product.properties.type == "child"){
                if(producttype != "PackageBean" || hasaddon == "true"){
                    listPrice = product.properties.list_price ? product.properties.list_price : cartAPI.formatWithDelimiters(product.original_price,2);
                    salePrice = cartAPI.formatWithDelimiters(product.discounted_price,2);
                    hasaddon = "true";
                    newaddonData.push({
                        "name": product.title,
                        "viewprice": salePrice,
                        "strikePrice": parseInt(salePrice || 0) < parseInt(listPrice || 0) ? listPrice : false
                    });
                }
            } else {
                let parentObj,age;
                if(producttype == "BundleBean"){
                    if(product.product_type == "ProductBean" || product.product_type == "PackageBean")
                        product.sku = product.sku.split(".")[0];
                    parentObj = $(".product-info-wrapper.child-product[data-partnumber='" + product.sku + "']");
                    if(!parentObj.length && product.product_type == "ProductBean"){ // swatches
                        parentObj = $(".product-info-wrapper.child-product [data-partnoswatch='" + product.sku + "']").closest(".child-product");
                    }
                } else {
                    parentObj = $(".product-info-wrapper.parent[data-partnumber='" + parentpartnumber + "']");
                    age = parentObj.length && (parentObj.find(".age-specification span").text() || parentObj.find(".age-specification").text());
                    if (age && age.trim().indexOf("Age") != -1) {
                        age = age.split(":")[1];
                    }
                }
                if(product.product_type == "ProductBean" && $(`${quickview == "true" ? quickViewWraper : pdpViewWraper} .size-selection-preference .color_category`).length){
                    let checkImageLinkForSwatches = storeAndGetPayloadData("get", undefined, variantId);
                    if(typeof checkImageLinkForSwatches == "object")  product.featured_image.url = checkImageLinkForSwatches.imageLink
                }
                isParentType = !isParentType && (producttype != "PackageBean" || hasaddon == "true") && product.properties && product.properties.type == "parent"
                productArr.push({
                    "isParentData": isParentType,
                    "salePrice":salePrice,
                    "listPrice": parseInt(salePrice || 0) < parseInt(listPrice || 0) ? listPrice : "",
                    "discountPrice": parseInt(discountPrice) ? discountPrice : '',
                    "productName": product.product_title,
                    "inventoryStatus": (parentObj.find(".inventory-status-message").text() || "").trim(),
                    "backorderdate": (parentObj.find(".back-oderable-date").text() || "").trim(),
                    "isAddOn": hasaddon == "true",
                    "size": (parentObj.find(".innerCont.active a span").text() || "").trim(),
                    "color": isDynamicKit ? "Customized" : (parentObj.find(".product-value").text() || "").trim(),
                    "ages": (age || '').trim(),
                    "imageLink": isDynamicKit ? product.properties.ImageURL : (product.featured_image.url || (`//mattel.scene7.com/is/image/Mattel/${$('.gt-image:first').data("thumbnail")}?$ossmall$`)),
                });
                count++
            }
        });
        // const childItems = _.countBy(items, item => (item.properties && item.properties.type) == "child").true || 0;
        return {
            'newproductData': productArr,
            'newaddonData': newaddonData,
            'totaladdedproduct': count,
            'pageReferal': pageReferal.indexOf('#') > -1 ? pageReferal.replace("#", "") : pageReferal,
            'newminiCartData': Object.assign(
                self.getShippingInfo(parseInt(cartAPI.formatWithDelimiters(total_price,2).replace(/,/g, ''))), {
                    cartQuantity: parseInt(finalCartCount || 0),
                    orderTotal: cartAPI.formatWithDelimiters(total_price,2)
                })
        }
    }
    getUniqueid(){
        return Math.random().toString(16).slice(2);
    }
    getShippingInfo(totAmt) {
        let freeShippingLimit = parseInt($("#freeShippingLimit").val()) || 125;
        let isShippingFree = totAmt < freeShippingLimit;
        return {
            freeshipping: isShippingFree,
            freeshippingAmount: isShippingFree && parseFloat((freeShippingLimit - totAmt)).toFixed(2)
        }
    }
    callAddToCartAPI(variantIds, addOnObj, cb, isDynamicKit) {
        if (!variantIds || _.isEmpty(variantIds)) {
            cb(false);
            return;
        }
        if(self.addonParentId == undefined || self.addonParentId == ""){
            uniqueId = (typeof variantIds == "object" ? variantIds[0] : variantIds)+self.getUniqueid();
        }
        let isGetCallAnonymously = false;
        self.getCartObj = undefined;
        const config = apiConfigInst.getApiConfig("addToBag").addProductToBag;
        config.data = isDynamicKit ? variantIds : self.getPayload(variantIds, {
            reference: uniqueId,
            type: addOnObj.type || null,
            productType: addOnObj.productType,
            addonParentVariantid:  addOnObj.parentvariantid || undefined
        });
        cartAPI.callPostCartAPI(config, (data)=>{
            if(!data){
                cb(false);
                return;
            }
            $('#productViewModal').modal("hide");
            if (addOnObj && addOnObj.type == "parent" && addOnObj.hasaddon == "true"){
                self.addonParentId = variantIds;
                isGetCallAnonymously = true;
                cb(true);
            }
            cartAPI.callGetCartAPI(apiConfigInst.getApiConfig("getMiniCart"),data =>{
                if(!isGetCallAnonymously){
                    cb(data);
                }
                self.getCartObj = data;
            });
        })
    }
    getPackageBeanPayload(parentObj, reference) {
        let childIds, attrsObj;
        parentObj.items.map((items, index)=> {
            if(items.properties.type == "parent"){
                childIds = storeAndGetPayloadData("get", undefined, items.id);
                if(!childIds)
                    return parentObj;
                let componentArr = [];
                _.each(childIds['associationIds'], id => {
                    attrsObj = storeAndGetPayloadData("get", undefined, id);
                    if(attrsObj){
                        componentArr.push({
                            "id": `${id}`,
                            "quantity": 1,
                            "properties": {
                                sku:attrsObj['partnumber'],
                                reference,
                                type: "child",
                                variant_inventorystatus: attrsObj['inventoryStatus'] || '',
                                variant_backorderdate: attrsObj['backorderdate'] || '',
                                variant_itematpreceiptid: attrsObj['recieptid'] || '',
                                productType: attrsObj['productType'],
                                association: attrsObj['associationType'],
                                product_finding_method : trackProductFindingAttr,
                                product_buying_method : trackProductBuyingAttr,
                                product_listing_page : trackProductListingAttr
                            }
                        })
                    }
                });
                items.properties.components = componentArr.length ? componentArr : undefined;
            }
        })
        return parentObj;
    }
    getPayload(ids, addOnObj) {
        let {type, reference, productType, addonParentVariantid} = addOnObj || {};
        let obj = { "items": [] },
            attrsObj,subproductuniqueval;
        _.each(ids.toString().split(","), id => {
            subproductuniqueval = id+self.getUniqueid();
            if(addonParentVariantid == id) {
                uniqueId = subproductuniqueval;
            }
            attrsObj = type == "child" ? self.addonAttrs[id] : storeAndGetPayloadData("get", undefined, id)
            obj.items.push({
                "id": id,
                "quantity": 1,
                "properties": {
                    reference: (attrsObj['type'] == "Quick" || attrsObj['type'] == "Component") ? subproductuniqueval  : reference ,
                    type: attrsObj['type'] != "Quick" ? type : undefined,
                    variant_inventorystatus: attrsObj['inventoryStatus'] || '',
                    variant_backorderdate: attrsObj['backorderdate'] || '',
                    variant_itematpreceiptid: attrsObj['recieptid'] || '',
                    productType: attrsObj['productType'],
                    association: type == "child" ? attrsObj['associations'] : undefined,
                    list_price:attrsObj['pricing'].compare_at_price || '',
                    product_finding_method : trackProductFindingAttr,
                    product_buying_method : trackProductBuyingAttr,
                    product_listing_page : trackProductListingAttr
                }
            })
        });
        return productType == "PackageBean" ? self.getPackageBeanPayload(obj, reference) : obj;
    }
    isAnyErrorOnPdp() {
        let errorEle = [".childErrMsg", ".errMsg"],
            res = false;
        $.each(errorEle, function (k, v) {
            $(v).each(function () {
                if ($(this).is(":visible")) {
                    res = true;
                }
            });
        });
        return res;
    }
    quickViewPreValidation(ele) {
        let $errorMsgEle = $(`${quickViewWraper} .select-size-wrapper .inline-message`),
            $swatchParentEle = $(`${quickViewWraper} .size-selection-preference`),
            noGo;
        $(ele).hasClass("target-comp-btn") && self.getTargettrackingAttr();
        if ($(`${quickViewWraper} .size-selection-preference .innerCont`).length) {
            if ($swatchParentEle.find(".on").length > 0) {
                if ($swatchParentEle.find(".innerCont").hasClass("on") && $swatchParentEle.find(".on").hasClass("unavailable-select")) {
                    noGo = true;
                } else {
                    $errorMsgEle.hide();
                    $(quickViewWraper).modal("hide");
                    return false;
                }
            } else {
                noGo = true;
            }
        }
        if(noGo){
            $errorMsgEle.show();
            return true;
        }
    }
    pdpPrevalidation(productType){
        let isNotSelected = $(".product-info-wrapper.child-product").find(".bundle-check-item .custom-checkbox:not(:checked)").length,
            totalItems = $(".product-info-wrapper.child-product").length;
        $(productInfoEle).each((i, mainCont) => {
            let isSwatchAvailable = $(mainCont).hasClass("swatch-available");
            if(productType == "BundleBean" && $(".addon-option .custom-checkbox:checked").length == 0){
                if(isSwatchAvailable && $(mainCont).find(".bundle-check-item .custom-checkbox").is(":checked")){
                    self.childValidationChecker(mainCont, true);
                    if(($(window).width() < 768) && $('.childErrMsg').is(':visible')){
                        $('html, body').animate({
                            scrollTop: $(".size-header-wrap").offset().top
                        }, 1000);
                    }
                } else{
                    if(parseInt(totalItems) == parseInt(isNotSelected)){
                        self.childValidationChecker(mainCont, false);
                        if(($(window).width() < 768) && $('.childErrMsg').is(':visible')){
                        $('html, body').animate({
                            scrollTop: $(".product-bundle-component").offset().top
                        }, 1000);
                        }
                    }else{
                        $(mainCont).find(".childErrMsg").hide();
                    }
                }
            } else if($(".addon-option .custom-checkbox:checked").length == 0){
                self.childValidationChecker(mainCont, false);
                if(($(window).width() < 768) && $('.childErrMsg').is(':visible')){
                    $('html, body').animate({
                        scrollTop: $(".size-header-wrap").offset().top
                    }, 1000);
                }
            } else if(productType == "BundleBean" && $(".addon-option .custom-checkbox:checked").length > 0){
                if(isSwatchAvailable && $(mainCont).find(".bundle-check-item .custom-checkbox").is(":checked")){
                    self.childValidationChecker(mainCont, true);
                    if(($(window).width() < 768) && $('.childErrMsg').is(':visible')){
                        $('html, body').animate({
                            scrollTop: $(".size-header-wrap").offset().top
                        }, 1000);
                    }
                } else {
                    $(mainCont).find(".childErrMsg").hide();
                    $(mainCont).find(".checkErrMsg").hide();
                }
            } else{
                $(mainCont).find(".childErrMsg").hide();
                $(mainCont).find(".checkErrMsg").hide();
            }
        });
        return self.isAnyErrorOnPdp();
    }
    childValidationChecker(mainCont, isChecked) {
        let isActive = 0;
        let isSwatchAvailable = $(mainCont).hasClass("swatch-available");
        let $currentCont = $(mainCont);
        if (isSwatchAvailable) {
          $currentCont.find(".filter_list_cont li").each((j, item) => {
            if ($(item).hasClass("active")) {
              isActive++;
            }
            if (isActive > 0) {
              $currentCont.find(".childErrMsg").hide();
            } else {
              if (isChecked) {
                $currentCont.find(".childErrMsg").show();
                $currentCont.find(".checkErrMsg").hide();
              } else {
                if ($(".pdpproduct .product-info-wrapper").data("producttype") == "BundleBean") {
                  $currentCont.find(".checkErrMsg").show();
                } else {
                  $currentCont.find(".childErrMsg").show();
                }
              }
            }
          });
        } else {
          $currentCont.find(".checkErrMsg").show();
        }
    }
    checkAuthenticationCookie(cb) {
        const isUnknownUser = document.cookie.indexOf("WC_AUTHENTICATION_-1002") != -1 || document.cookie.indexOf("WC_AUTHENTICATION_") == -1;
        if (isUnknownUser) {
            request.ajaxCall(apiConfigInst.getApiConfig("getGuestIdentity"))
            .then(data => {
                    cb(true);
                })
                .catch(error => {
                    cb(false);
                });
        } else {
            cb(true);
        }
    }
    showHideAddOnDetails(ele, evt) {
        evt.preventDefault();
        const $ele = $(ele);
        $ele.addClass("hide");
        if ($ele.hasClass("show-details")) {
            $ele.parent().next(".details-container").removeClass("hide");
            $ele.next(".hide-details").removeClass("hide").focus();
            $ele.attr("aria-expanded", "true");
            $ele.next(".hide-details").attr("aria-expanded", "true");
        } else {
            $ele.parent().next(".details-container").addClass("hide");
            $ele.attr("aria-expanded", "false");
            $ele.prev(".show-details").attr("aria-expanded", "false");
            $ele.prev(".show-details").removeClass("hide").focus();
        }
    }
}
const request = new ajaxRequest();
const { handleBarTemplateInst, cartAPI } = window.global;
const apiConfigInst = new apiConfig();
if(!window.global.CartFunctionality){
    window.global.CartFunctionality = new CartFunctionality();
}
