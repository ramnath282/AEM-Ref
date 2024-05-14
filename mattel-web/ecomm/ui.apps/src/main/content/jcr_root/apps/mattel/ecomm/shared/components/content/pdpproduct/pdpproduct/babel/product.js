import eventBinding from "../shared/eventBinding";
import apiConfig from "../shared/apiConfig";
import ajaxRequest from "../shared/ajaxbinding";
import { dateFormat } from "../shared/dateFormat";
import { getStorage, setStorage } from '../shared/sessionStorage';
import { userCategory, isInventoryStatusValid  } from "../shared/addToBagDisableCheck";
import {productSupportObj, storeAndGetPayloadData} from '../shared/productSupportiveFunctions';
class product {
  constructor() {
    self = this;
    this.element = ".product-wrapper";
    this.productInventoryStatusObj = {};
    this.disableButton = false;
    this.productType = $(".product-info-wrapper:first").attr("data-producttype");
    $(".product-wrapper .btn-add-to-bag").attr("aria-disabled", "false");
  }
  init() {
    if (this.element.length) {
      eventBindingInst.bindLooping(
        {
          "click .check-store": "getOptions",
          "click .see-guarantee-details": "getDetailsModal",
          "change .button-wrapper select.selectLocation": "getListValue",
          "change .product-bundle-wrapper select.selectLocation": "getOptValue",
          // "click .btn-add-to-bag": "getAddToBag",
          "click .product-wrapper .dropdown-toggle": "enableScreenReader",
          "keydown .product-wrapper .dropdown-toggle": "enableScreenReader",
          "keydown .product-wrapper .dropdown-menu.inner li": "selectDropdownOption",
          "click .product-wrapper .size-selection-preference .innerCont": "productVariantChange",
          "click .product-wrapper .custom-checkbox": "productPriceShow",
          "click .review-status .write-review": "writeReview",
          "click .read-reviews": "navigateToHrefId",
          "keydown .bootstrap-select .dropdown-toggle":"removeFocus",
          "keydown .product-wrapper .modal-header .close":"closePopup",
          "keydown #addOnsModal .modal-header .close":"closeAddOnsModal",
          "click .at-share-btn-elements a": "pdpSocialShareAction"
        },
        self
      );
    }
    $('.selectpicker').on('hide.bs.select', function () {
      let $curObj = $(this);
      self.showCheckStoreLabel($curObj);
      $('body').hasClass('body-overflow-hidden') ? $('body').removeClass('body-overflow-hidden') : '';
    });

    self.swatchSizeCss();
    if (self.productType == "DynamicKitBean" && $('.bitty-twins-section').length>0) {
      productBittyInstance.initTwins();
    }
    let hashVal = window.location.hash.toLowerCase();
    productSupportObj.waitForBVLoad(function (isLoaded) {
      if (isLoaded) {
        if (hashVal == "#writereview" || hashVal == "#bvoswritereview") {
          $(".write-review")[0].click();
        } else if (hashVal == "#bvrrcontainer" || hashVal == "#bvreviewheading") {
          $(".read-reviews")[0].click();
        }
      }
    });
  }

  render() {
    self.getProductInventoryStatusMsg();
    self.productOfferprice();
    productSupportObj.getBazzarVoiceRating(".product-info-wrapper:first");
    $(".inventory-status").each((index, item) => {
      if ($(item).hasClass("show-inventory")) {
        $(item).removeClass("hide");
      } else {
        $(item).addClass("hide");
      }
    });

  }

  getProductInventoryStatusMsg() {
    let ajaxOption = apiConfigInst.getApiConfig("productInventoryStatus");
    const sessionData = getStorage("productStorage");
    self.productInventoryStatusObj = sessionData;

    if (!sessionData) {
      ajaxOption.async = false;
      request
        .ajaxCall(ajaxOption)
        .then(data => {
          self.productInventoryStatusObj = data;
          setStorage("productStorage", data);
        })
        .catch(error => {
          console.log(error);
        });
    }
  }

  pdpSocialShareAction(){
         $("img").attr("nopin","nopin");
         $(".social_pinterest_image").removeAttr("nopin");
  }

  // Related to ADA functionalities
  removeFocus(el, evt) {
    let keyCode = evt.keyCode;
    if (keyCode == 9) {
      if(!$('.btn-add-to-bag').is(':disabled')){
        $(".btn-add-to-bag").focus();
      }else{
         if($(".button-wrapper .check-store").hasClass("hide")){
          $(".button-wrapper .dropdown-toggle").focus();
         }else{
             $(".button-wrapper .check-store").focus();
         }
      }
    }
    if($(el).parents().hasClass("button-wrapper")){
      if($(el).parent().siblings(".guarantee-message").hasClass("hide")){
        if($(".accordion-section.active a").length==0){
            $(".brand-logo").focus();
        }else{
          $(".accordion-section.active a").focus();
        }
      }else{
        $(".see-guarantee-details").focus();
      }
    }
  }

  // Related to ADA functionalities for pop-up close button
  closePopup(el, evt) {
    let keyCode = evt.keyCode;
    if (keyCode == 13) {
      $(".button-wrapper #guaranteeModal").hide(function(){
        $(".see-guarantee-details").focus();
      });
    }
  }

  // Related to ADA functionalities for pop-up close button of add to bag
  closeAddOnsModal(el, evt) {
    let keyCode = evt.keyCode;
    if (keyCode == 13) {
      $(".product-addOn-wrapper .product-addOn-modal").hide(function(){
        $(".button-wrapper #addToBagBtn").focus();
      });
    }
  }

  // Swatch css for fractional text
  swatchSizeCss() {
    $('.innerCont a span:contains("/")').each((index, item) => {
      $(item).addClass('size-twodigit');
    });
    $('.innerCont a span:contains("-")').each((index, item) => {
      $(item).addClass('size-twodigit');
    });
  }

  // Related to product offer price service call
  productOfferprice() {
    let parentPartNumber = $(".product-info-wrapper:first").attr("data-partnumber");
    let hasquicksell =  $(".product-wrapper #addToBagBtn").data("hasquicksell");
    let ajaxOptions = apiConfigInst.getApiConfig("productAPI").productApiAssociation(parentPartNumber);
    let ajaxUrl = apiConfigInst.getApiConfig("productAPI").productAPICall(parentPartNumber);
     if(self.productType == "BundleBean"){
      request.ajaxCall(ajaxOptions).then(data => {
        let {associations} = data.product;
        let pricingData = [];
        $.each(associations, (index, item) => {
          if(item.association_type.toUpperCase() == "COMPONENT" && item.product.variants.length){
           pricingData.push({ 
              "partnumber" : item.product.partnumber,
              "id": item.product.variants[0].id,
              "pricing" : item.product.variants[0].pricing,
              "type" : "Component",
              "childrenType" : typeof item.product.options == "object" && (item.product.options[0].name == "Clothing Size" || item.product.options[0].name == "Color") && "Swatches",
              "inventoryStatus" : item.product.variants[0].core.variant_inventorystatus,
              "recieptid": item.product.variants[0].core.variant_itematpreceiptid,
              "backorderdate" : item.product.variants[0].core.variant_backorderdate,
              "headerInventory": item.product.variants.length>1 && item.product.variants,
              "associations": item.association_type,
              "productType": item.product.core.product_type,
              "variants" :item.product.variants
             });
          }
          $.each(associations[index].product.associations, (index, item) => {
            if(item.association_type.toUpperCase() == "QUICK" && item.product.variants.length){
             pricingData.push({ 
                 "partnumber" : item.product.partnumber,
                 "id": item.product.variants[0].id,
                 "pricing" : item.product.variants[0].pricing,
                 "type" : "Component",
                "childrenType" : "Quick",
                "inventoryStatus" : item.product.variants[0].core.variant_inventorystatus,
                "recieptid": item.product.variants[0].core.variant_itematpreceiptid,
                "backorderdate" : item.product.variants[0].core.variant_backorderdate,
                "associations": item.association_type,
                "productType": item.product.core.product_type,
                "variants" :item.product.variants
               });
            }
          });
        });
        //removing duplicate objects
        pricingData = pricingData.filter((pricingData, index, self) =>
          index === self.findIndex((key) => (
            key.partnumber === pricingData.partnumber
          ))
        )
        self.getpriceData(pricingData);
      }).catch(error => {
        window.global.errorHandling.PDPAPI(error);
        console.log("error");
      })
    }
    else {
        request.ajaxCall(ajaxUrl).then(data => {
          let {variants, associations} = data.product;
          let pricingData = [];
          let compAssociations = variants.length && data.product.associations && _.filter(data.product.associations, (association) => association.association_type == "COMPONENT"),
          compIdsArr = [];
          compAssociations && compAssociations.forEach((comp)=> comp.product.variants.length && compIdsArr.push(comp.product.variants[0].id));
          if(!hasquicksell){
              if(variants.length){
                pricingData.push({
                    "partnumber" : variants[0].core.variant_parentpartnumber,
                    "id": variants[0].id,
                    "pricing" :  variants[0].pricing,
                    "type" : variants.length > 1 && "Swatches",
                    "inventoryStatus" : variants[0].core.variant_inventorystatus,
                    "recieptid": variants[0].core.variant_itematpreceiptid ? variants[0].core.variant_itematpreceiptid : "",
                    "headerInventory": variants.length > 1 && variants,
                    "backorderdate" : variants[0].core.variant_backorderdate,
                    "productType": data.product.core.product_type,
                    "associations": compAssociations,
                    "associationIds": compIdsArr
                  });
               } else {
                productSupportObj.checkProductCallOut("", "", data.product.core.product_type, false);
               }
          } else {
            if(variants.length){
            pricingData.push({
                "partnumber" : variants[0].core.variant_parentpartnumber,
                "id": variants[0].id,
                "pricing" :  variants[0].pricing,
                "type" : variants.length > 1 && "Swatches",
                "inventoryStatus" : variants[0].core.variant_inventorystatus,
                "recieptid": variants[0].core.variant_itematpreceiptid,
                "backorderdate" : variants[0].core.variant_backorderdate,
                "productType": data.product.core.product_type,
                "associations": compAssociations,
                "associationIds": compIdsArr
              })
            }
            $.each(associations, (index, item) => {
            if(item.association_type.toUpperCase() == "QUICK" && item.product.variants.length){
              pricingData.push({ 
                  "partnumber" : item.product.partnumber,
                  "id": item.product.variants[0].id,
                  "pricing" : item.product.variants[0].pricing,
                  "type" : "Quick",
                  "inventoryStatus" : item.product.variants[0].core.variant_inventorystatus,
                  "recieptid": item.product.variants[0].core.variant_itematpreceiptid,
                  "backorderdate" : item.product.variants[0].core.variant_backorderdate,
                  "productType": item.product.core.product_type
                });
              }
            });
          }
          self.getpriceData(pricingData);
         }).catch(error => {
            window.global.errorHandling.PDPAPI(error);
            console.log("error");
         })
    }
  }

  getpriceData(resObj){
    let partNo,price,$productPriceObj,$addOnPriceObj;
    $.each(resObj, (index, item) => {
      partNo = item.partnumber,
      price =  cartAPI.getActivePriceData(item.pricing),
      $productPriceObj = $(".product-price[data-partnumber=" + partNo + "]"),
      $addOnPriceObj = $(".addon-price[data-partnumber=" + partNo + "]");
      if(partNo == "" || partNo == undefined){
        console.log("data issue - partNumber is undefined");
      }
      else{
        self.getPriceCall(partNo,price,$productPriceObj,$addOnPriceObj);
      }
      storeAndGetPayloadData('set',item);
    });
    if (!(self.productType == "DynamicKitBean" && $('.bitty-twins-section').length>0)) {
      self.productInventoryStatus(resObj);
    }
    if ((self.productType == "DynamicKitBean" && $('.bitty-twins-section').length > 0)) {
        $.each(resObj, (index, item) => {
            partNo = item.partnumber,
            $productPriceObj = $(".product-info-wrapper.parent[data-partnumber=" + partNo + "]");
            $productPriceObj.attr("data-itematpreceiptid", item.recieptid);
        });
    }
  }
  getPriceCall(partNo,priceObject,$productPriceObj,$addOnPriceObj) {
    $(".product-price").each((index, item) => {
      $(item).find(".price-currency").html("$");
    });
    if ($productPriceObj.length > 0) {
      $productPriceObj.find(".offer_price").text(priceObject.listPrice);
      let itemListPrice = priceObject.listPrice;
      let itemSalePrice = priceObject.salePrice;
      if(self.productType == 'ItemBean'){
        productSupportObj.affirmCheck(undefined, undefined, itemSalePrice);
      }
      if (parseInt(itemSalePrice) <= 95 && self.productType != "BundleBean") {
        $(".guarantee-message").addClass("hide");
      }
      if (self.productType == "BundleBean") {
        $('.product-info-wrapper.parent').find('.product-price span').eq(0).removeClass('hide');
      }
      if (parseFloat(itemSalePrice) < parseFloat(itemListPrice)) {
        let priceStrikeMsg = $productPriceObj.find(".price-strike").attr("data-pricestrick-message"),
            youSaveMsg = $productPriceObj.find(".price-saved").attr("data-yousave-message");

        let $priceStrikeHtml = '<span class="sr-only">'+priceStrikeMsg+'</span> $<span class="offer_price"></span>',
            $priceSavedHtml = youSaveMsg +' $<span class="discount_price"></span> (<span class="discount_percentage"></span>%)';

        let priceSaved = priceObject.differenceAmount;
        let savedPercent = priceObject.inPrecentage;
        
        $productPriceObj.find(".price-strike").html($priceStrikeHtml);
        $productPriceObj.find(".price-saved").html($priceSavedHtml);
        $productPriceObj.find(".current_price").text(itemSalePrice).attr("data-price-val", itemSalePrice);
        $productPriceObj.find(".offer_price").text(itemListPrice).attr("data-price-val", itemListPrice);
        $productPriceObj.find(".discount_price").html(priceSaved);
        $productPriceObj.find(".discount_percentage").html(savedPercent);
        $productPriceObj.find(".price-strike, .price-saved").removeClass("hide");
      } else {
        $productPriceObj.find(".current_price").text(itemSalePrice).attr("data-price-val", itemSalePrice);
        $productPriceObj.find(".offer_price").text(itemListPrice).attr("data-price-val", itemListPrice);
        $productPriceObj.find(".price-strike, .price-saved").addClass("hide");
      }
      $productPriceObj.find('span').eq(0).removeClass('hide');
    }
    if ($addOnPriceObj.length > 0) {
      $addOnPriceObj.text("$" + priceObject.listPrice);
    }
    productSupportObj.checkProductCallOut("", "", self.productType, false);
  }

  // Related to product inventory status service call
  productInventoryStatus(resObj) {
    let $parentProductInfoObj = $(".product-info-wrapper:first"),
        parentPartNumber = $parentProductInfoObj.attr("data-partnumber"),
        headerInventoryStatus,
        headerBackorderDate,
        backOrderDate,
        partNumberArr=[];
    partNumberArr.push(parentPartNumber);
    _.each(resObj, (item) =>{
        let {partnumber, inventoryStatus} = item;
        inventoryStatus = cartAPI.readInventoryvalueAPI(inventoryStatus);
        let parentObj = $(".product-info-wrapper[data-partnumber='" + partnumber + "']");
        if(item.type == "Component"){
            if(item.childrenType == "Quick"){
                let $quickSellObj = $("input[data-partnumber="+partnumber+"]");
                if(inventoryStatus == "Available"){
                    $quickSellObj.parents(".addon-option, .associations-addons").removeClass("hide");
                }
            } else if(item.childrenType == "Swatches") {
              let status = _.countBy(_.map(item.headerInventory, item => {
                return cartAPI.readInventoryvalueAPI(item.core.variant_inventorystatus)
              }));
              headerInventoryStatus = status["Available"] ? "Available" : (status["Limited"] ? "Limited" : (
                  status["Backorderable"] ? "Backorderable" : (status["Backordered"] ? "Backordered" : (status["Preorderable"] ? "Preorderable" : ( status["Unavailable"] == item.headerInventory.length ? "Unavailable" :(status["No longer available"] == item.headerInventory.length ? "No longer available" : (status["noLongerAvailable"] == item.headerInventory.length ? "No longer available" : undefined
              )))))));
              let headerBackorderDateArr = [];
              _.each(item.headerInventory, item =>{
                headerBackorderDateArr.push(new Date(item.core.variant_backorderdate));
              });
              headerBackorderDate = changeDateFormat.getLatestDate(headerBackorderDateArr);
              _.each(item.headerInventory, subItem => {
                let $swatchObj = $("a[data-partnumber='" + subItem.core.sku + "']");
                let skuInventoryStatus = isInventoryStatusValid(subItem.core.variant_inventorystatus);
                skuInventoryStatus = cartAPI.readInventoryvalueAPI(skuInventoryStatus);
                if(skuInventoryStatus == "No longer available" || skuInventoryStatus == "noLongerAvailable" || skuInventoryStatus == "Unable to access inventory"){
                    $swatchObj.parent().removeClass("unavailable out-of-stock").addClass("unavailable");
                } else if(skuInventoryStatus == "Unavailable"){
                    $swatchObj.parent().removeClass("unavailable out-of-stock").addClass("out-of-stock");
                }
              });
            } else {
              // other products
            }
        } else if(item.type == "Swatches"){
          if(self.productType == "PackageBean" || self.productType == "ProductBean"){
            let status = _.countBy(_.map(item.headerInventory, item => {
              return cartAPI.readInventoryvalueAPI(item.core.variant_inventorystatus);
            }));
            headerInventoryStatus = status["Available"] ? "Available" : (status["Limited"] ? "Limited" : (
                status["Backorderable"] ? "Backorderable": (status["Backordered"] ? "Backordered" : (status["Preorderable"] ? "Preorderable" : (status["Unavailable"] == item.headerInventory.length ? "Unavailable" :(status["No longer available"] == item.headerInventory.length ? "No longer available" : (status["noLongerAvailable"] == item.headerInventory.length ? "No longer available" : undefined
            )))))))
          }
          _.each(item.headerInventory, item =>{
            let $swatchObj = $("a[data-partnumber='" + item.core.sku + "']");
            inventoryStatus = item.core.variant_inventorystatus;
            inventoryStatus = cartAPI.readInventoryvalueAPI(inventoryStatus);
            if (inventoryStatus == "No longer available"
                || inventoryStatus == "noLongerAvailable"
                || inventoryStatus == "Unable to access inventory") {
              if ($swatchObj.length > 0) {
                $swatchObj.parent().removeClass("unavailable out-of-stock");
                $swatchObj.parent().addClass("unavailable");
              }
            } else if (inventoryStatus == "Unavailable") {
              if ($swatchObj.length > 0) {
                $swatchObj.parent().removeClass("unavailable out-of-stock");
                $swatchObj.parent().addClass("out-of-stock");
              }
            }
          });
          inventoryStatus = headerInventoryStatus;
          if(inventoryStatus == "Backorderable" || inventoryStatus == "Backordered" || inventoryStatus == "Preorderable"){
            let headerBackorderDateArr = [];
            _.each(item.headerInventory, item =>{
              headerBackorderDateArr.push(new Date(item.core.variant_backorderdate));
            });
            headerBackorderDate = changeDateFormat.getLatestDate(headerBackorderDateArr);
          }
        } else if(item.type=="Quick"){
            let $quickSellObj = $("input[data-partnumber="+partnumber+"]");
            if(inventoryStatus == "Available"){
                $quickSellObj.parents(".addon-option, .associations-addons").removeClass("hide");
            }
        }
        if (parentObj.length > 0) {
          parentObj.find(".inventory-status").removeClass("hide");
          let inventoryStatusMsgUI = self.productInventoryStatusObj[inventoryStatus];
         
          if(typeof inventoryStatus == "undefined") {
            parentObj.find(".inventory-status-message").attr("data-noInventory",true);
            console.log(`%c AddtoBag Disabled: Inventory attributes are not available in SDG response.`, "background: red; color:white; font-weight:bold");
          } 
            parentObj.find(".inventory-status-message").attr("data-invstatus", inventoryStatus);
            if (inventoryStatus == "Available") {
              parentObj.find(".inventory-status-message").addClass("available-status").html("");
              parentObj.find(".back-oderable-date").html("");
            } else if ((inventoryStatus == "Backorderable" || inventoryStatus == "Backordered" || inventoryStatus == "PreOrderable")  && item.backorderdate != undefined) {
              backOrderDate = item.backorderdate || "";
              parentObj.find(".inventory-status-message").attr("data-backorderdate", backOrderDate);
              backOrderDate = backOrderDate != "" ? changeDateFormat.formatToNewDate(new Date(backOrderDate.replace(/ /g, "T")), backOrderDate) : "";
              parentObj.find(".inventory-status-message").removeClass("available-status").html(inventoryStatusMsgUI);
              if(inventoryStatus == "PreOrderable"){
                parentObj.find(".back-oderable-date").html(" - available " + backOrderDate);
              }else{
                parentObj.find(".back-oderable-date").html(" until " + backOrderDate);
              }
            } else {
              parentObj.find(".inventory-status-message").removeClass("available-status").html(inventoryStatusMsgUI);
              parentObj.find(".back-oderable-date").html("");
            }
            if (inventoryStatus == "Available" || inventoryStatus == "Backorderable" || inventoryStatus == "Backordered" || inventoryStatus == "PreOrderable" || inventoryStatus == "Limited") {
              $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
            }
            if (parentObj.find(".inventory-status").hasClass("hide-inventory")) {
              parentObj.find(".inventory-status").removeClass("hide-inventory hide");
            }
            if(parentObj.attr("data-producttype") == "BundleBean"){
              $(".product-info-wrapper:first").find(".inventory-status-message").removeClass("available-status").html("");
              $(".product-info-wrapper:first").find(".back-oderable-date").html("");

            }
            if (!(parentObj.attr("data-producttype") == "ItemBean" || parentObj.attr("data-producttype") == "PackageBean" || parentObj.attr("data-producttype") == "BundleBean" )) {
              let headerInventoryStatusMsgUI = self.productInventoryStatusObj[headerInventoryStatus];
              parentObj.find(".inventory-status-message").attr("data-invstatus", headerInventoryStatus);
              if (headerInventoryStatus == "Available"){
                parentObj.find(".inventory-status-message").addClass("available-status").html("");
                parentObj.find(".back-oderable-date").html("");
              }else if((headerInventoryStatus == "Backorderable" || headerInventoryStatus == "Backordered" || headerInventoryStatus == "PreOrderable") && headerBackorderDate != ""){
                let headerBackorderDateUI = headerBackorderDate != null ? headerBackorderDate : "";
                parentObj.find(".inventory-status-message").attr("data-backorderdate", headerBackorderDate);
                headerBackorderDateUI = headerBackorderDateUI != "" ? changeDateFormat.formatToNewDate(new Date(headerBackorderDateUI.replace(/ /g, "T")), headerBackorderDateUI) : "";
                parentObj.find(".inventory-status-message").removeClass("available-status").html(headerInventoryStatusMsgUI);
                if(headerInventoryStatus == "PreOrderable"){
                  parentObj.find(".back-oderable-date").html(" - available " + headerBackorderDateUI);
                }else{
                  parentObj.find(".back-oderable-date").html(" until " + headerBackorderDateUI);
                }
              }else{
                parentObj.find(".inventory-status-message").removeClass("available-status").html(headerInventoryStatusMsgUI);
                parentObj.find(".back-oderable-date").html("");
              }
            }
          }

    });
    self.isGetToAddToBagValid();
    $(".size_category li.innerCont").each((index, item) => {
          let labelText= $(item).find("a").attr("aria-label");
          if ($(item).hasClass("unavailable")) {
            $(item).find("a").attr("aria-label",labelText+ " unavailable")
          } else {
              $(item).find("a").attr("aria-label",labelText+ " available")
          }
    });
    if($("#floating-product-info .non-buyable-product-btn").length){
      $("#floating-product-info .inventory-status-message").addClass("non-buyable-error").html("Currently Unavailable")
    }
}

  // Related to check add to bag button valid on load
  isGetToAddToBagValid() {
    let counter = 0,
        isChecked = 0,
        $parentObj = $(".product-info-wrapper").eq(0);
    if(self.productType == "ItemBean" || self.productType == "ProductBean" || self.productType == "PackageBean"){
      let PriceValue = $parentObj.find(".product-price .current_price").attr("data-price-val"),
      invntoryValue = $parentObj.find(".inventory-status-message").attr("data-invstatus"),
      noInventory = $parentObj.find(".inventory-status-message").attr("data-noInventory");
      invntoryValue = cartAPI.readInventoryvalueAPI(invntoryValue);
      let inventoryStatusMsg = PriceValue || invntoryValue ? invntoryValue : "NotAccessibleData";
      if(inventoryStatusMsg == "No longer available" || inventoryStatusMsg =="noLongerAvailable" || inventoryStatusMsg == "Unavailable" || inventoryStatusMsg == "Unable to access inventory" || inventoryStatusMsg == "NotAccessibleData" || noInventory === "true"){
        counter++;
        $(".product-wrapper .btn-add-to-bag").attr("aria-disabled", "true");
      }
        self.affirmOnload();
    }else if(self.productType == "BundleBean"){
      if(!$(".product-info-wrapper.child-product").length) {
        return;
      }
      $(".product-info-wrapper.child-product").each((index, item) => {
          if($(".product-info-wrapper").eq(0).hasClass("dynamic-kit-product")){
            let isdynamickitprimarysku = $(item).find(".bundle-check-item .custom-checkbox").attr("data-isdynamickitprimarysku");
            if(isdynamickitprimarysku == 'true'){
              $(item).find(".bundle-check-item .custom-checkbox").prop("checked", true);
            }else{
              $(item).find(".bundle-check-item .custom-checkbox").prop("checked", false);
            }
          }
          let PriceValue = $(item).find(".product-price .current_price").attr("data-price-val"),
          invntoryValue = $(item).find(".inventory-status-message").attr("data-invstatus"),
          noInventory = $(item).find(".inventory-status-message").attr("data-noInventory");
          let inventoryStatusMsg = PriceValue || invntoryValue ? invntoryValue : "NotAccessibleData";
          invntoryValue = cartAPI.readInventoryvalueAPI(invntoryValue);
          if(inventoryStatusMsg == "No longer available" || inventoryStatusMsg =="noLongerAvailable" || inventoryStatusMsg == "Unavailable" || inventoryStatusMsg == "Unable to access inventory" || inventoryStatusMsg == "NotAccessibleData" || noInventory === "true"){
            counter++;
            $(item).find(".bundle-check-item .custom-checkbox").prop("checked", false);
            $(".product-wrapper .btn-add-to-bag").attr("aria-disabled", "true");
          }else{
            isChecked++;
          }
          self.productPriceShow("onload");
      });
    }
    $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
    if(counter > 0 && isChecked == 0){
      $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
    }else if(isChecked > 0){
      $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
    }else{
      userCategory();
    }
  }

  writeReview(ele, evt) {
    evt.preventDefault();
    let productId = $(".product-info-wrapper:first").data('partnumber');
    if (!productId) {
      console.log("write review : product id not found..");
      return;
    }
    if (typeof $BV == "undefined") {
      console.log("write review : $BV is not defined..");
      return;
    }
    $BV.ui("rr", "submit_review", { productId: productId });
  }

  navigateToHrefId(ele, evt) {
    let targetID = $(ele).attr("href");
    let headerHeight = 0;
    if (targetID == "#" || !$(targetID).length) {
      console.log("read review: target href id not exist..");
      return;
    }
    if ($("header").hasClass("sticky-header")) {
      headerHeight = $("header.sticky-header").height();
    }
    $("html, body").animate(
      { scrollTop: $(targetID).offset().top - headerHeight },
      1000
    );
    evt.preventDefault();
  }

  // Related to check store label on click function
  getOptions(el, evt) {
    evt.preventDefault();
    evt.stopPropagation();
    let $parentObj = $(el).parents(".product-info-wrapper").length > 0 ? $(el).parents(".product-info-wrapper") : $(el).parents(".button-wrapper");
    $parentObj.find(".dropdown-toggle").trigger("click");
    $(el).addClass("hide");
    $parentObj.find("select.selectLocation").val("");
    $parentObj.find(".selectLocation").removeClass("hide");
    $parentObj.find(".selectLocation").trigger("change");
    $parentObj.find(".btn.dropdown-toggle.bs-placeholder").addClass("hide");
      $parentObj.find(".selectLocation").addClass('open');
    $parentObj.find(".dropdown-menu.inner .active").removeClass(" selected active");
    $parentObj.find(".store-availability-message").addClass("hide");
    if( $parentObj.find(".bootstrap-select.selectLocation").hasClass('open')){
      $('body').addClass('body-overflow-hidden');
    }
  }

  showCheckStoreLabel($curObj){
    let $parentObj = $curObj.parents(".bootstrap-select");
    let $containerObj = $parentObj.parents(".product-info-wrapper").length > 0 ? $parentObj.parents(".product-info-wrapper") : $parentObj.parents(".button-wrapper")
    $containerObj.find(".check-store").removeClass("hide");
    $parentObj.addClass("hide");
    if($parentObj.find(".selectpicker.selectLocation").val() != ""){
      $parentObj.next(".store-availability-message").removeClass("hide");
    }
  }

  // Related to check store availability dropdown on change function
  getListValue(el) {
    $(".selectLocation ul").attr("role", "listbox");
    $(".selectLocation ul li").attr("role", "presentation");
    $(".popover-title .close").attr("aria-label", "close");
    let $selectedOption = $(el).find("option:selected");
    let optionVal = $selectedOption.val();
    let showOptionVal = $selectedOption.text();
    if (optionVal && optionVal != "") {
      let init = showOptionVal.indexOf("(");
      let fin = showOptionVal.indexOf(")");
      let res = showOptionVal.substr(init + 1, fin - init - 1);
      let restStr = showOptionVal.substr(0, init);
      let ajaxOption = apiConfigInst.getApiConfig("storeAvailability").update;
      let catVal = $("#catalogId").val();
      let parentObj = $('.product-info-wrapper.parent');
      let isSwatchAvailable = parentObj.hasClass('swatch-available');
      let isStoreAvailable = isSwatchAvailable ? self.isStoreAvailibilityValid(el) : 1;
      let partVal = isSwatchAvailable
                      ? (parentObj.find('.text-swatches li.active').length > 0 ? parentObj.find('.text-swatches li.active a').attr('data-partnumber')
                      : parentObj.find('.text-swatches li.unavailable-select a').attr('data-partnumber'))
                        : parentObj.attr('data-partnumber');

      $(".button-wrapper .store-location span").html(
        restStr + "<span class='land-mark'>(" + res + ")</span>"
      );

      ajaxOption.data = JSON.stringify({
        catalogId: catVal ? catVal : "",
        partNumber: partVal ? partVal : "",
        storeSelected: optionVal
      });

      if (isStoreAvailable > 0) {
        $(el).parents(".button-wrapper").find(".inline-message").addClass("hide");
        request
          .ajaxCall(ajaxOption)
          .then(data => {
            if (data) {
              let val = data.itemAvailabilityDetailsList
                ? data.itemAvailabilityDetailsList[0].availabilityStatus
                : "";
              let displayContent = "";
              if (val && val != "") {
                displayContent =
                  val == "A" || val == "N"
                    ? "Available"
                    : val == "M"
                    ? "Limited"
                    : val == "B"
                    ? "Backorderable"
                    : val == "T"
                    ? "No longer available"
                    : val == "S"
                    ? "Unavailable"
                    : "";
              }
              $(".store-availability span").html(displayContent);
              if(displayContent == "Available"){
                $(".store-availability span").addClass("store-available-status");
              }else{
                $(".store-availability span").removeClass("store-available-status");
              }
            }
            if ($selectedOption.val() && $selectedOption.val() != "") {
              $(".store-availability-message").removeClass("hide");
              $(el).parents(".button-wrapper").find(".inline-message").addClass("hide");
            } else {
              $(el).parents(".button-wrapper").find(".store-availability-message").addClass("hide");
            }
          })
          .catch(error => {});
      } else {
        $(el).parents(".button-wrapper").find(".inline-message").removeClass("hide");
        setTimeout(() => {
          $(el).parents(".button-wrapper").find(".store-availability-message").addClass("hide");
        }, 200);
      }
    }
  }

  // Related to bundle check store availability dropdown on change function
  getOptValue(el) {
    $(".selectLocation ul").attr("role", "listbox");
    $(".selectLocation ul li").attr("role", "presentation");
    $(".popover-title .close").attr("aria-label", "close");
    let $selectedOption = $(el).find("option:selected");
    let optionVal = $selectedOption.val();
    let showOptionVal = $selectedOption.text();
    if (optionVal && optionVal != "") {
      let init = showOptionVal.indexOf("(");
      let fin = showOptionVal.indexOf(")");
      let res = showOptionVal.substr(init + 1, fin - init - 1);
      let restStr = showOptionVal.substr(0, init);
      let parentObj = $(el).parents(".product-info-wrapper");
      let ajaxOption = apiConfigInst.getApiConfig("storeAvailability").update;
      let catVal = $("#catalogId").val();
      let isSwatchAvailable = parentObj.hasClass('swatch-available');
      let isStoreAvailable = isSwatchAvailable ? self.isStoreAvailibilityValid(el) : 1;
      let partVal = isSwatchAvailable
                    ? (parentObj.find('.text-swatches li.active').length > 0 ? parentObj.find('.text-swatches li.active a').attr('data-partnumber')
                    : parentObj.find('.text-swatches li.unavailable-select a').attr('data-partnumber'))
                      : parentObj.attr('data-partnumber');

      $(".product-bundle-wrapper .store-location span").html(
        restStr + "<span class='land-mark'>(" + res + ")</span>"
      );

      ajaxOption.data = JSON.stringify({
        catalogId: catVal ? catVal : "",
        partNumber: partVal ? partVal : "",
        storeSelected: optionVal
      });

      if (isStoreAvailable > 0) {
        request
          .ajaxCall(ajaxOption)
          .then(data => {
            let val = data.itemAvailabilityDetailsList[0].availabilityStatus;
            let displayContent = "";
            if (val) {
              displayContent =
                val == "A" || val == "N"
                  ? "Available"
                  : val == "M"
                  ? "Limited"
                  : val == "B"
                  ? "Backorderable"
                  : val == "T"
                  ? "No longer available"
                  : val == "S"
                  ? "Unavailable"
                  : "";
            }
            parentObj.find(".store-availability span").html(displayContent);
            if(displayContent == "Available"){
              parentObj.find(".store-availability span").addClass("store-available-status");
            }else{
              parentObj.find(".store-availability span").removeClass("store-available-status");
            }
            if ($selectedOption.val() && $selectedOption.val() != "") {
              parentObj.find(".store-availability-message").removeClass("hide");
            } else {
              parentObj.find(".store-availability-message").addClass("hide");
            }
          })
          .catch(error => {});
      }else{
        setTimeout(() => {
          parentObj.find(".store-availability-message").addClass("hide");
        }, 200);
      }
    }
  }

  // Related to validate check store availability
  isStoreAvailibilityValid(el) {
    let parentObj = self.productType == "BundleBean" ? $(el).parents(".product-info-wrapper") : $(".product-info-wrapper:first");
    let counter = 0;
    let isSwatchAvailable = parentObj.hasClass('swatch-available');

    if(isSwatchAvailable){
      if(parentObj.find(".filter_list_cont li.active").length > 0 || parentObj.find(".filter_list_cont li.unavailable-select").length > 0){
        counter++;
        parentObj.find(".childErrMsg").hide();
      }else{
        parentObj.find(".childErrMsg").show();
        parentObj.find(".checkErrMsg").hide();
      }
    }else{
      counter++;
      parentObj.find(".childErrMsg").hide();
    }

    return counter;
  }

  // Related to open guarantee details modal
  getDetailsModal(el, evt) {
    $('#guaranteeModal').modal('show').css('top','-95%');
    if($(window).width()>992){$('#guaranteeModal').css('top','-55%');}
    $('.modal-backdrop').hide();
    $('body.modal-open').css({'overflow':'scroll','padding-right':'0'});
    $("body").find(".modal-backdrop").addClass("see-details-backdrop");
    evt.preventDefault();
  }

  // Related to ADA functionalities
  enableScreenReader(el, evt) {
    evt.preventDefault();
    let keyCode = evt.keyCode;
    if (keyCode == 13 || keyCode == undefined) {
      $(self.element).find(".inner.open").removeAttr("aria-expanded");
      $(self.element).find(".dropdown-menu.open").find(".inner.open .dropdown-menu.inner li").attr("role", "presentation");
      $(self.element).find(".popover-title .close").attr("aria-label", "close");
      $(self.element).find(".popover-title .close").attr("tabindex", "0");
      $(self.element).find(".dropdown-menu.open").find(".inner.open").removeAttr("role","listbox");
      $(self.element).find(".dropdown-menu.open").find(".inner.open .dropdown-menu.inner").attr("role", "listbox");
    }
  }

  // Related to ADA functionalities
  selectDropdownOption(el, evt) {
    let keyCode = evt.keyCode;
    if (keyCode == 13) {
      $(el).find("a").trigger("click");
        $(self.element).find(".bootstrap-select").removeClass("open");
        $(self.element).find(".dropdown-toggle").attr("aria-expanded", "false");
    }
  }

  // Related to swatch on click functionalities
  productVariantChange(ele, evt) {
    if ($("#productViewModal").hasClass("in")) {
      return;
    }
    evt.preventDefault();
    let listCont = $(ele).parents(".filter_list_cont");
    let partNumber = $(ele).find("a").attr("data-partnumber");
    let parentObj = $(ele).parents(".product-info-wrapper");
    let backOrderDate = "";
    let productVal = $(".product-value").text();
    let $parentEle = parentObj;

    if (!$(ele).hasClass("active") && !$(ele).hasClass("unavailable-select")) {
        $(".product-wrapper .btn-add-to-bag").attr("aria-disabled", "false");
        listCont.find("li").each((index, item) => {
            $(item).removeClass("active");
            $(item).removeClass("unavailable-select");
        });
        $parentEle.addClass("request-pending");
        cartAPI.getSwatchesInventory(apiConfigInst.getApiConfig("quickView").swatchInventory(parentObj.data("partnumber")), partNumber, (filteredData) => {
            if (!filteredData) {
                $parentEle.removeClass("request-pending");
                return;
            };
            let {variant_inventorystatus,variant_backorderdate,product_affirmIneligible} = filteredData;
            let inventoryObj = {
                inventoryStatus: cartAPI.readInventoryvalueAPI(variant_inventorystatus),
                backOrderDate: variant_backorderdate
            };
            let swatchInvStatus = inventoryObj.inventoryStatus;
            let inventoryStatusObj;
            swatchInvStatus = isInventoryStatusValid(swatchInvStatus);
            let inventoryStatus = swatchInvStatus;
            if (self.productType == "PackageBean") {
                inventoryStatusObj = productSupportObj.compareInventoryStatus(inventoryStatus, inventoryObj.backOrderDate, false);
                inventoryStatus = inventoryStatusObj.inventoryStatus;
                inventoryObj.backOrderDate = inventoryStatusObj.backOrderDate;
            }
            let inventoryStatusMsgUI = self.productInventoryStatusObj[inventoryStatus];
            parentObj.find(".inventory-status-message").attr("data-invstatus",inventoryStatus);
            if (inventoryStatus == "Available") {
                parentObj.find(".inventory-status-message").addClass("available-status").html("");
                parentObj.find(".back-oderable-date").html("");
            } else if ((inventoryStatus == "Backorderable" || inventoryStatus == "Backordered" || inventoryStatus == "PreOrderable") && inventoryObj.backOrderDate != undefined) {
                backOrderDate = inventoryObj.backOrderDate != null ? inventoryObj.backOrderDate : "";
                backOrderDate = backOrderDate != "" ? changeDateFormat.formatToNewDate(new Date(backOrderDate.replace(/ /g, "T")),backOrderDate) : "";
                parentObj.find(".inventory-status-message").removeClass("available-status").html(inventoryStatusMsgUI);
                if (inventoryStatus == "PreOrderable") {
                    parentObj.find(".back-oderable-date").html(" - available " + backOrderDate);
                } else {
                    parentObj.find(".back-oderable-date").html(" until " + backOrderDate);
                }
            } else {
                parentObj.find(".inventory-status-message").removeClass("available-status").html(inventoryStatusMsgUI);
                parentObj.find(".back-oderable-date").html("");
            }
            if (inventoryStatus == "Available" || inventoryStatus == "Backorderable" || inventoryStatus == "Backordered" || inventoryStatus == "Limited") {
                parentObj.find(".custom-checkbox").prop("checked", true);
                $(".product-wrapper .btn-add-to-bag").attr("disabled", false);
                self.productPriceShow("onclick");
            }
            if (parentObj.find(".inventory-status").hasClass("hide-inventory")) {
                parentObj.find(".inventory-status").removeClass("hide-inventory hide");
            }
            parentObj.find(".img-swatch, .innerCont").removeClass("active");
            $(ele).addClass("active");
            $(".product-wrapper .size-selection-preference .innerCont a").attr("aria-checked", "false");
            if ($(ele).hasClass("active")) {
                $(ele).find("a").attr("aria-checked", "true");
            }
            if (inventoryStatus == "Unable to access inventory" ||inventoryStatus == "No longer available" || inventoryStatus == "noLongerAvailable" || inventoryStatus == "Unavailable") {
                $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
            }
            if (swatchInvStatus == "Unable to access inventory" || swatchInvStatus == "No longer available" || swatchInvStatus == "noLongerAvailable" || swatchInvStatus == "Unavailable") {
                if ($(ele).hasClass("unavailable-select")) {
                    $(ele).removeClass("unavailable-select");
                } else {
                    $(ele).addClass("unavailable-select");
                }
                $(ele).removeClass("active");
                $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
            }
            if (self.productType == "BundleBean") {
                if (!parentObj.find(".store-availability-message").hasClass("hide")) {
                    self.getOptValue(parentObj.find("select.selectLocation"));
                }
            } else {
                if (!$(".button-wrapper").find(".store-availability-message").hasClass("hide")) {
                    self.getListValue($(".button-wrapper").find("select.selectLocation"));
                }
            }
            if (self.productType == "PackageBean" || self.productType == "ProductBean") {
                let inEligiblekey = $(".affirm_eligible_key").text();
                if ($(ele).hasClass("active") && inEligiblekey != "Y") {
                    if (inventoryStatus != 'PreOrderable' && inventoryStatus != "Backorderable" && inventoryStatus != 'Backordered' && inventoryStatus != 'noLongerAvailable' && inventoryStatus != 'No longer available' && inventoryStatus != 'Unavailable') {
                        let itemPrice = $('.current_price').text();
                        productSupportObj.affirmCheck(inEligiblekey, inventoryStatus, itemPrice);
                      } 
                  } 
              }
            self.onClickBtnValid();
            if(typeof variant_inventorystatus == "undefined"){
              $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
            }
            $parentEle.removeClass("request-pending");
        })
        $(ele).closest(".marketing-savings-cont").find(".childErrMsg").hide();
    } else {
        let swatchNla = 0;
        let totalSwatches = listCont.find("li.innerCont").length;
        listCont.find("li").each((index, item) => {
            $(item).removeClass("active");
            $(item).removeClass("unavailable-select");
            if ($(item).hasClass("unavailable") || $(item).hasClass("out-of-stock")) {
                swatchNla++;
            }
        });
        $(ele).removeClass("active");
        parentObj.find(".inventory-status-message").removeClass("available-status").html("");
        parentObj.find(".back-oderable-date").html("");
        if (self.productType != "BundleBean" && swatchNla == totalSwatches) {
            $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
            $(".product-wrapper .btn-add-to-bag").attr("aria-disabled", "true");
        } else {
            $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
            $(".product-wrapper .btn-add-to-bag").attr("aria-disabled", "false");
        }
    }
    let currentDataId = $(ele).closest("ul").data("id");
    if (currentDataId == "color-selection") {
        let altText = $(ele).find("a img").attr("alt");
        if (altText != productVal) {
            $(".product-value").text(altText);
        } else {
            $(".product-value").text("");
        }
    }
  }
// Product price show on click
  productPriceShow(calledFrom) {
    let totalCheckedPrice = 0.0,
        isUnavailable = 0;
    if (self.productType == "BundleBean") {
      let blankPrice = 0;
      $(".bundle-check-item .custom-checkbox:checked").each((index, item) => {
        let checkedPrice;
        let parentObj = $(item).parents(".product-info-wrapper.child-product");
        let isSwatchAvailable = parentObj.hasClass("swatch-available");
        if(isSwatchAvailable){
          parentObj.find(".size-selection-preference ul li.innerCont").each((index1, liobj) => {
            if($(liobj).hasClass("unavailable") || $(liobj).hasClass("out-of-stock")){
              isUnavailable++;
            }
          });
          if(isUnavailable == parentObj.find(".size-selection-preference ul li.innerCont").length || parentObj.find(".inventory-status-message").data("noinventory") == true){
            checkedPrice = 0;
          }else{
            checkedPrice = parentObj.find(".product-price .current_price").text().trim() == "" ? 0 : parentObj.find(".product-price .current_price").text().trim();
          }
        }else{
          if(parentObj.find(".inventory-status-message").data("noinventory") == true){
            checkedPrice = 0;
          } else{
            checkedPrice = parentObj.find(".product-price .current_price").text().trim() == "" ? 0 : parentObj.find(".product-price .current_price").text().trim();
          }
        }
        if(checkedPrice == 0){
          blankPrice++;
        }

        totalCheckedPrice = parseFloat(checkedPrice) + parseFloat(totalCheckedPrice);
      });

      if($(".addon-option .custom-checkbox:checked").length > 0){
        $(".addon-option .custom-checkbox:checked").each((index2, addOnObj) => {
          let addOnPrice = $(addOnObj).parents(".addon-option").find(".addon-price").text().substring(1).trim() == "" ? 0 : $(addOnObj).parents(".addon-option").find(".addon-price").text().substring(1).trim();
          totalCheckedPrice = parseFloat(addOnPrice) + parseFloat(totalCheckedPrice);
          if(addOnPrice == 0){
            blankPrice++;
          }
        });
      }

      if(blankPrice > 0){
        self.disableButton = true;
      }else{
        self.disableButton = false;
      }

      $(".product-info-wrapper:first")
        .find(".product-price .current_price")
        .eq(0)
        .text(parseFloat(totalCheckedPrice).toFixed(2));
        $(".product-info-wrapper:first")
          .find(".product-price span")
          .eq(0)
          .removeClass("hide");


      if (($(".bundle-check-item .custom-checkbox:checked").length == 0 && $(".addon-option .custom-checkbox:checked").length == 0) || parseFloat(totalCheckedPrice) == 0) {
        $(".product-info-wrapper:first")
          .find(".product-price span")
          .eq(0)
          .addClass("hide");
      }
    }else{
      let assocChkdObj = $(".addon-option .custom-checkbox:checked");
      let currentStaticPrice = $(".product-info-wrapper:first .product-price .current_price").data("priceVal");
      let totalPrice;
      if (currentStaticPrice) {
        totalPrice = parseFloat(currentStaticPrice);
      } else {
        let offerPrice = $(".product-info-wrapper:first").find(".product-price .offer_price").eq(0).text().trim() == "" ? 0 : $(".product-info-wrapper:first").find(".product-price .offer_price").eq(0).text().trim();
        let priceSaved = $(".product-info-wrapper:first").find(".product-price .discount_price").eq(0).text().trim() == "" ? 0 : $(".product-info-wrapper:first").find(".product-price .discount_price").eq(0).text().trim();
        totalPrice = parseFloat(offerPrice) - parseFloat(priceSaved);
      }
      let blankPrice = 0;
      assocChkdObj.each((index, addOnObj) => {
        let addOnPrice = $(addOnObj).parent().find(".addon-price").text().substring(1).trim() == "" ? 0 : $(addOnObj).parent().find(".addon-price").text().substring(1).trim();
        totalPrice = parseFloat(totalPrice) + parseFloat(addOnPrice);
        if(addOnPrice == 0){
          blankPrice++;
        }
      });
      if(blankPrice > 0){
        self.disableButton = true;
      }else{
        self.disableButton = false;
      }
      $(".product-info-wrapper:first").find(".product-price .current_price").eq(0).text(parseFloat(totalPrice).toFixed(2));
      if($(".product-info-wrapper:first .inventory-status-message[data-noinventory='true']").length){
        self.disableButton = true;
      }
    }

    if(calledFrom != "onload"){
      let backorderedCounter = 0;
      let affirmInEligible = 0 ;
      $(".product-info-wrapper").each((index, item) => {
        let isSwatchAvailable = $(item).hasClass("swatch-available");
        if(!$(item).find(".custom-checkbox").is(":checked")){
         if(isSwatchAvailable){
          if($(item).find(".size-selection-preference ul li.active").length > 0){
            $(item).find(".inventory-status-message").removeClass("available-status").html("");
            $(item).find(".size-selection-preference ul li.innerCont").each((liIndex, liItem) => {
              $(liItem).removeClass("active");
            });
          }
        }
      }else {
            let parentCB = $(item).find(".bundle-check-item .custom-checkbox");
            if (parentCB.is(":checked")) {
                let productStatus = $(item).find(".inventory-status-message").attr("data-invstatus");
                if (productStatus.indexOf('Backordered') != -1 || productStatus.indexOf('Backorderable') != -1 || productStatus.indexOf("Preorder") != -1 || productStatus.indexOf("PreOrderable") != -1 || productStatus == "No longer available" || productStatus == "noLongerAvailable" || productStatus == "Unavailable") {
                    backorderedCounter++;
                }
            }
            let inEligiblekey = $(item).attr("data-affirmineligible");
            if (inEligiblekey == 'Y') {
                affirmInEligible++;
            }
        }
      });
      let bundlePartNo = $(".product-info-wrapper.parent").find(".product-price").attr("data-partnumber");
      let itemCurrentPrice = $(".product-price[data-partnumber=" + bundlePartNo + "]")
                             .find(".current_price")
                             .text().trim();

      if (self.productType == "BundleBean") {
          if (parseInt(itemCurrentPrice) <= 95) {
                $(".guarantee-message").addClass("hide");
          }else{
                $(".guarantee-message").removeClass("hide");
          }
          productSupportObj.affirmCheck(undefined, undefined, itemCurrentPrice);
          if (affirmInEligible == 0 && itemCurrentPrice>50 && backorderedCounter == 0 && $(".product-info-wrapper .custom-checkbox:checked").length){
            $('#bagAffirm').removeClass("hide");
          } else{
            $('#bagAffirm').addClass("hide");
          }
      }
      let inventoryStatus = $(".inventory-status-message").attr("data-invstatus");
      let eligiblekey = $(".affirm_eligible_key").text().trim();
      if (self.productType == "ItemBean"){
          if(eligiblekey != "Y"
        && inventoryStatus != "Backorderable"
        && inventoryStatus != "noLongerAvailable"
        && inventoryStatus != "No longer available"
        && inventoryStatus != "Backordered"
        && inventoryStatus != "PreOrderable"
        && inventoryStatus != "Unavailable"
        && inventoryStatus.indexOf("Preorder") == -1
        && inventoryStatus.indexOf("Backordered") == -1){
          productSupportObj.affirmCheck(eligiblekey, inventoryStatus, itemCurrentPrice);
          }
      }
      self.onClickBtnValid();
    }else{
        if(self.productType == 'BundleBean'){
          let bundlePartNo = $(".product-info-wrapper.parent").find(".product-price").attr("data-partnumber");
          let bundleCurrentPrice = $(".product-price[data-partnumber=" + bundlePartNo + "]")
                                    .find(".current_price")
                                     .text().trim();
          if (parseInt(bundleCurrentPrice) <= 95) {
                  $(".guarantee-message").addClass("hide");
           }

           productSupportObj.affirmCheck(undefined, undefined, bundleCurrentPrice);
          let backorderedCounter=0,affirmInEligible=0;
          $(".product-info-wrapper").each((index, item) => {
               if($(item).find(".custom-checkbox").is(":checked")){
                     let productStatus = $(item).find(".inventory-status-message").attr("data-invstatus");
                     if(productStatus.indexOf('Backordered') != -1 || productStatus.indexOf('Backorderable') != -1 || productStatus.indexOf("Preorder") != -1 || productStatus.indexOf("PreOrderable") != -1 || productStatus == "No longer available" || productStatus =="noLongerAvailable"  || productStatus =="Unavailable" ){
                           backorderedCounter++;
                      }
                      let inEligiblekey = $(item).attr("data-affirmineligible");
                      if(inEligiblekey == 'Y'){
                            affirmInEligible++;
                            }
                 }
             });
             if (affirmInEligible == 0 && backorderedCounter == 0 ){
                  $('#bagAffirm').removeClass("hide");
              }else{
                  $('#bagAffirm').addClass("hide");
               }
           }
      }
  }

  //On load Affirm functionality for PackageBean/ProductBean
  affirmOnload() {
        if ((self.productType == 'PackageBean' || self.productType == 'ProductBean') && !($(".product-info-wrapper").hasClass("swatch-available"))) {
            let inEligiblekey = $(".affirm_eligible_key").text().trim();
            let inventoryStatus = $(".product-info-wrapper .inventory-status-message").attr("data-invstatus");
            if (inEligiblekey != "Y" && inventoryStatus.indexOf('Backordered') == -1 && inventoryStatus.indexOf('Backorderable') == -1 && inventoryStatus.indexOf('PreOrderable') == -1 && inventoryStatus.indexOf("Preorder") == -1 && inventoryStatus != "No longer available" && inventoryStatus != "noLongerAvailable" && inventoryStatus != "Unavailable") {
                let itemPrice = $('.current_price').text();
                
                productSupportObj.affirmCheck(inEligiblekey, inventoryStatus, itemPrice);
               } 
        }
    }

  // Add to bag validation on click
  onClickBtnValid(){
    let isSwatchAvailable;
    let counter = 0,
        totalSwatchLis, unavailableSwatches;
    $(".product-info-wrapper").each((index, item) => {
      isSwatchAvailable = $(item).hasClass("swatch-available");
      if((isSwatchAvailable && self.productType != "BundleBean") || (isSwatchAvailable && $(item).find(".bundle-check-item .custom-checkbox").is(":checked"))) {
        let $sizeLiObj = $(item).find(".size-selection-preference");
        let inventoryStatusMsg = $(item).find(".inventory-status-message").attr("data-invstatus");
        totalSwatchLis = $sizeLiObj.find("li.innerCont").length;
        unavailableSwatches = $sizeLiObj.find(".out-of-stock").length + $sizeLiObj.find(".unavailable").length;
        if(parseInt(unavailableSwatches) == parseInt(totalSwatchLis)){
          counter++;
        }else if($sizeLiObj.find(".unavailable-select").length > 0){
          counter++;
        }else if(inventoryStatusMsg == "No longer available"|| inventoryStatusMsg=="noLongerAvailable" || inventoryStatusMsg == "Unavailable" || inventoryStatusMsg == "Unable to access inventory"){
          counter++;
        }
      }else if((isSwatchAvailable && self.productType != "BundleBean") || (!isSwatchAvailable && $(item).find(".bundle-check-item .custom-checkbox").is(":checked")) || (!isSwatchAvailable && self.productType != "BundleBean")) {
        let inventoryStatusMsg = $(item).find(".inventory-status-message").attr("data-invstatus");
        if(inventoryStatusMsg == "No longer available" || inventoryStatusMsg=="noLongerAvailable" || inventoryStatusMsg == "Unavailable" || inventoryStatusMsg == "Unable to access inventory"){
          counter++;
        }
      }
    });

    if(counter > 0 || self.disableButton == true){
      $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
    }else{
      $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
      userCategory();
    }
  }
}

class productBitty {
  constructor() {
    bittySelf = this;
    this.element = ".bitty-twins-section";
    this.dollChoiceSection = ".doll-choice-section";
    this.sleeperChoiceSection = ".sleeper-choice-section";
    this.parentPartNumber = $(".product-info-wrapper:first").attr("data-partnumber");
  }
  initTwins(ele) {
    if (this.element.length) {
      eventBindingInst.bindLooping(
        {
          "click .bitty-twins-section .sleeper-choices li a": "setTwinChoiceOnClick",
          "click .bitty-twins-section .doll-choices li a": "setDollChoiceOnClick"
        },
        bittySelf
      );
    }
    $('.inventory-status').removeClass('hide-inventory hide').css('min-height','18px');
    bittySelf.setTwinChoiceDisplay();
  }

  setTwinChoiceDisplay(){

    let dollChoiceSection = $(this.dollChoiceSection),
        sleeperChoiceSection = $(this.sleeperChoiceSection),
        defaultSequenceArray = [],
        dollPartNumberArray = [],
        displaySleeperType = null;
    
    dollChoiceSection.each((choiceIdx, choiceObj) => {
      dollPartNumberArray = [];
      $('li', choiceObj).each((dollItemIdx, dollItemObj) => { 
        defaultSequenceArray.push($(dollItemObj).data('default-display'));
        dollPartNumberArray.push($(dollItemObj).data('doll-partnumber')); 
        if($(bittySelf.element).hasClass("non-buyable-product")) {
          $(dollItemObj).addClass("unavailable");
        }
      });
      displaySleeperType = $('li[data-default-display='+bittySelf.getRequiredSequenceVal(defaultSequenceArray, choiceIdx)+']', choiceObj).parent('ul').data('sleeper-type');
      if(!$(bittySelf.element).hasClass("non-buyable-product")) {
        $('li[data-default-display='+bittySelf.getRequiredSequenceVal(defaultSequenceArray, choiceIdx)+']', choiceObj).addClass('active').parent().removeClass('hide');
        sleeperChoiceSection.eq(choiceIdx).find('li[data-sleeper-type="'+displaySleeperType+'"]').addClass('active');
        $(choiceObj).prev().children('span').text($('li[data-default-display='+bittySelf.getRequiredSequenceVal(defaultSequenceArray, choiceIdx)+']', choiceObj).data('doll-name'));
      }
      sleeperChoiceSection.eq(choiceIdx).prev().children('span').text(displaySleeperType);
    });
    if($(bittySelf.element).hasClass("non-buyable-product")) {
      $('.inventory-status').find('span.inventory-status-message').html("Currently-Unavailable");
      sleeperChoiceSection.each((choiceIdx, choiceObj) => {
        $('li', choiceObj).each((dollItemIdx, dollItemObj) => { 
          $(dollItemObj).addClass("unavailable");
        });
      });
      return;
    }
    bittySelf.letInventoryWork();
  }

  getRequiredSequenceVal(seqArrObj, choiceIdx){
    if(choiceIdx === 0) return _.min(seqArrObj)
    else return _.min(seqArrObj) + 1;
  }

  setTwinChoiceOnClick(elem){
    let asideElem = $(elem).parents("aside"),
        dollSection = bittySelf.dollChoiceSection,
        sleeperType = $(elem).parent().data('sleeper-type'),
        selectedDollName = $(dollSection, asideElem).find('ul:not(.hide) li.active').data('doll-name');
    
    $(dollSection, asideElem).find('ul:not(.hide) li').removeClass('active');
    $(elem).parent('li').addClass('active').siblings().removeClass('active');
    $(dollSection, asideElem).find('ul[data-sleeper-type="'+sleeperType+'"]').removeClass('hide').siblings().addClass('hide');
    $(dollSection, asideElem).find('ul[data-sleeper-type="'+sleeperType+'"] li[data-doll-name="'+selectedDollName+'"]').addClass('active');
    $(dollSection, asideElem).prev().children('span').text(selectedDollName);
    $(elem).parents('section').prev().children('span').text(sleeperType);

    $(dollSection).each((didx, dobj) => {
      bittySelf.letInventoryWork($('ul li.active', dobj).data('doll-partnumber'));
    });
  }

  setDollChoiceOnClick(elem){
    $(elem).parent('li').addClass('active').siblings().removeClass('active');
    $(elem).parents('section').prev().children('span').text($(elem).parent('li').data('doll-name'));
    bittySelf.letInventoryWork($(elem).parent('li').data('doll-partnumber'));
  }

  setImageOnViewer(twinImageName){
    let thumbHolder = $('.pdp-img-viewer-holder aside ul'),
        largeHolder = $('.pdp-img-viewer-holder section ul'),
        s7ImagePath = 'https://s7d9.scene7.com/is/image/Mattel/BTD01_',
        parentObj = $(this.element).parents(".product-info-wrapper");

      parentObj.addClass("request-pending");
      $('li:first img', thumbHolder).attr('src',s7ImagePath+twinImageName);
      $('li:first img', largeHolder).attr('src',s7ImagePath+twinImageName+'?fit=constrain,1&wid=2000&hei=2000&fmt=jpg').imagesLoaded().done((instance, image) => {
        parentObj.removeClass("request-pending");
        $('li:first img.zoomImg', largeHolder).css({'width':'2000px','height':'2000px'});
        $('li', thumbHolder).removeClass('slick-current');
        $('li:first', thumbHolder).addClass('slick-current').trigger('click');
      });
  }

  letInventoryWork(twinPartNo=null){
    let dollChoiceSection = $(this.dollChoiceSection),
        sleeperChoiceSection = $(this.sleeperChoiceSection),
        twinPartNumber = (twinPartNo!=null)?twinPartNo:bittySelf.parentPartNumber,
        selectedTwinPartNo = [];
    
    dollChoiceSection.each((didx, dobj) => {
      selectedTwinPartNo.push($('ul li.active', dobj).data('doll-partnumber'));
    });
    bittySelf.setImageOnViewer(selectedTwinPartNo.map(element=>element).join("_"));

    request.ajaxCall({
      url :'//'+(window.location.host)+'/bin/requesthandler.web.productavailability.json?partnumber='+twinPartNumber+'&storeId=ag_en&domainId=ag_en'+((twinPartNo==null)?'&partial=associations':''),
      type:"GET",
      dataType:"JSON",
    }).then(data => {
      let dollInventoryStatus = null,
          dollPartNo = null,
          dollBackorderDate = null,
          dollReceiptId = null,
          dollBuyableattr = null;

      if(twinPartNo==null){
        let {associations} = data.product;
        let checkInventory;
        $.each(associations, (idx, obj) => {
          checkInventory = associations[idx].product.variants[0];
          if(associations[idx].association_type.toUpperCase() == "KITCOMPONENT"){
            $("#bittykitcomponent-"+(idx+1)).attr({
              "data-inventory-status":checkInventory && associations[idx].product.variants[0].core.variant_inventorystatus,
              "data-backordered-date":checkInventory && associations[idx].product.variants[0].core.variant_backorderdate,
              "data-receipt-id":checkInventory && associations[idx].product.variants[0].core.variant_itematpreceiptid,
              "data-buyable-attr":associations[idx].product.core.product_buyable
            });
          }
          else if(associations[idx].association_type.toUpperCase() == "BITTY_TWIN_COMP"){
            dollInventoryStatus = checkInventory && associations[idx].product.variants[0].core.variant_inventorystatus;
            dollPartNo = associations[idx].product.partnumber;
            dollBackorderDate = checkInventory && associations[idx].product.variants[0].core.variant_backorderdate;
            dollReceiptId = checkInventory && associations[idx].product.variants[0].core.variant_itematpreceiptid;
            dollBuyableattr = associations[idx].product.core.product_buyable;
            bittySelf.plotInvStatus(dollInventoryStatus,dollPartNo,dollBackorderDate,dollReceiptId,dollBuyableattr);
          }
        });
      } else {
        dollInventoryStatus = data.product.variants[0].core.variant_inventorystatus;
        dollPartNo = data.product.partnumber;
        dollBackorderDate = data.product.variants[0].core.variant_backorderdate;
        dollReceiptId = data.product.variants[0].core.variant_itematpreceiptid;
        bittySelf.plotInvStatus(dollInventoryStatus,dollPartNo,dollBackorderDate,dollReceiptId);
      }
      
      dollChoiceSection.each((didx, dobj) => {
        $('ul',dobj).each((ulidx, ulobj) => {
          if($('li.unavailable',ulobj).length == $('li',ulobj).length){
            sleeperChoiceSection.eq(didx).find('li[data-sleeper-type="'+$(ulobj).data("sleeper-type")+'"]').addClass('unavailable');
          }
        });
      });
      bittySelf.displayInventoryMessage();
    });
  }

  plotInvStatus(dollInventoryStatus, dollPartNo, dollBackorderDate=null,dollReceiptId=null,dollBuyableattr){
    if(dollInventoryStatus=="Unavailable" || dollInventoryStatus=="Sold Out" || dollInventoryStatus=="No longer available" || dollInventoryStatus=="noLongerAvailable" || dollBuyableattr == 0) {
      $('[data-doll-partnumber="'+dollPartNo+'"]').addClass("unavailable");
    }
    $('[data-doll-partnumber="'+dollPartNo+'"]').attr({'data-inventory-status':dollInventoryStatus,'data-receipt-id':dollReceiptId});
    if(dollInventoryStatus=="Backordered" || dollInventoryStatus=="Backorderable" || dollInventoryStatus=="PreOrderable") {
      $('[data-doll-partnumber="'+dollPartNo+'"]').attr('data-backordered-date', dollBackorderDate);
    }
  }

  displayInventoryMessage(){
    let dollChoiceSection = $(this.dollChoiceSection),
        selectedTwinArray = [],
        selectedTwinArrCount = 0,
        checknonBuyable = [],
        checknonBuyableCount = 0;
    dollChoiceSection.each((didx, dobj) => {
      selectedTwinArray.push($('ul li.active', dobj).data('inventory-status'));
      checknonBuyable.push($('ul li.active', dobj).data('buyable-attr'));
    });
    checknonBuyableCount = _.countBy(checknonBuyable);
    if(checknonBuyableCount[0] >= 1){
      $('.inventory-status').find('span.back-oderable-date').text('');
      $('.inventory-status').removeClass('hide-inventory hide').find('span.inventory-status-message').removeClass("available-status").text("Currently Unavailable");
      $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
      return;
    }
    selectedTwinArrCount = _.countBy(selectedTwinArray);
    if(selectedTwinArrCount["Available"] == 2){
        bittySelf.getInvMessage('Available');
        $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
      } else if(selectedTwinArrCount["Unavailable"]){
        bittySelf.getInvMessage('Unavailable');
        $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
      } else if(selectedTwinArrCount["No longer available"]){
        bittySelf.getInvMessage('noLongerAvailable');
        $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
      }else if(selectedTwinArrCount["Sold Out"]){
        bittySelf.getInvMessage('noLongerAvailable');
        $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
      } else if(selectedTwinArrCount["noLongerAvailable"]){
        bittySelf.getInvMessage('noLongerAvailable');
        $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
      } else if(selectedTwinArrCount["Unable to access inventory"]){
        bittySelf.getInvMessage('Unable to access inventory');
        $(".product-wrapper .btn-add-to-bag").attr("disabled", true);
      } else if(selectedTwinArrCount["Available"] && selectedTwinArrCount["Limited Quantities"]){
        bittySelf.getInvMessage('Limited');
        $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
      } else if(selectedTwinArrCount["Backordered"]){
        bittySelf.getInvMessage('Backordered');
        $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
      } else if(selectedTwinArrCount["Backorderable"]){
        bittySelf.getInvMessage('Backordered');
        $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
      } else if(selectedTwinArrCount["PreOrderable"] && selectedTwinArrCount["Available"]){
        bittySelf.getInvMessage('PreOrderable');
        $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
      } else if(selectedTwinArrCount["PreOrderable"] && selectedTwinArrCount["Limited Quantities"]){
        bittySelf.getInvMessage('PreOrderable');
        $(".product-wrapper .btn-add-to-bag").removeAttr("disabled");
      }
      if($('[data-producttype="DynamicKitBean"] .product-price .current_price').html().trim() == ""){
        console.log(`%c PDP API Error:Price Object data not found.`, "background: red; color:white; font-weight:bold");
        return;
      }
  }

  getInvMessage(invKey){
    let getMsg = productInstance.productInventoryStatusObj[invKey],
        dollChoiceSection = $(this.dollChoiceSection);

    $('.inventory-status').find('span.back-oderable-date').text('');
    if(invKey=="Available"){
      $('.inventory-status').find('span.inventory-status-message').addClass("available-status").text('');
    }
    else if(invKey=="PreOrderable"){
      let checkCountPreOrderable = [],
          dateArrayPreOrderable = [];
      $('.inventory-status').removeClass('hide-inventory hide').find('span.inventory-status-message').removeClass("available-status").text(getMsg);
      dollChoiceSection.each((didx, dobj) => {
        if($('ul li.active', dobj).is('[data-backordered-date]')) { checkCountPreOrderable.push(didx); }
      });
      dollChoiceSection.each((didx, dobj) => {
        if($('ul li.active', dobj).is('[data-backordered-date]')) {
          if(checkCountPreOrderable.length == 1) {
          $('.inventory-status').removeClass('hide-inventory hide')
            .find('span.back-oderable-date')
            .text(" - available " + changeDateFormat.formatToNewDate(new Date(($('ul li.active', dobj).data('backordered-date')).replace(/ /g, "T"))))
            .attr('data-org-date',$('ul li.active', dobj).data('backordered-date'));
          }
          else {
            dateArrayPreOrderable.push($('ul li.active', dobj).data('backordered-date'));
            if(didx == 1) {
              $('.inventory-status').removeClass('hide-inventory hide')
                .find('span.back-oderable-date')
                .text(" - available " + changeDateFormat.formatToNewDate(new Date((bittySelf.dateCompare(dateArrayPreOrderable[0],dateArrayPreOrderable[1])).replace(/ /g, "T"))))
                .attr('data-org-date',bittySelf.dateCompare(dateArrayPreOrderable[0],dateArrayPreOrderable[1]));
            }
          }
        }
    });
    }
    else if(invKey=="Backordered"){
      let checkCountBackOrdered = [],
          dateArrayBackOrdered = [];
      $('.inventory-status').removeClass('hide-inventory hide').find('span.inventory-status-message').removeClass("available-status").text(getMsg);
      dollChoiceSection.each((didx, dobj) => {
        if($('ul li.active', dobj).is('[data-backordered-date]')) { checkCountBackOrdered.push(didx); }
      });
      dollChoiceSection.each((didx, dobj) => {
          if($('ul li.active', dobj).is('[data-backordered-date]')) {
            if(checkCountBackOrdered.length == 1) {
            $('.inventory-status').removeClass('hide-inventory hide')
              .find('span.back-oderable-date')
              .text(" until " + changeDateFormat.formatToNewDate(new Date(($('ul li.active', dobj).data('backordered-date')).replace(/ /g, "T"))))
              .attr('data-org-date',$('ul li.active', dobj).data('backordered-date'));
            }
            else {
              dateArrayBackOrdered.push($('ul li.active', dobj).data('backordered-date'));
              if(didx == 1) {
                $('.inventory-status').removeClass('hide-inventory hide')
                  .find('span.back-oderable-date')
                  .text(" until " + changeDateFormat.formatToNewDate(new Date((bittySelf.dateCompare(dateArrayBackOrdered[0],dateArrayBackOrdered[1])).replace(/ /g, "T"))))
                  .attr('data-org-date',bittySelf.dateCompare(dateArrayBackOrdered[0],dateArrayBackOrdered[1]));
              }
            }
          }
      });
    }
    else {
      $('.inventory-status').removeClass('hide-inventory hide').find('span.inventory-status-message').removeClass("available-status").text(getMsg);
    }
  }

  dateCompare(dt1, dt2){
    const date1 = new Date(dt1),
          date2 = new Date(dt2);
    if(date1 > date2){ return dt1 }
    else if(date1 < date2){ return dt2 }
    else { return dt1 }
  }
}

let self,
 bittySelf,
  eventBindingInst = new eventBinding(),
  apiConfigInst = new apiConfig(),
  request = new ajaxRequest(),
  productBittyInstance = new productBitty(),
  productInstance = new product();
const {cartAPI} = window.global;
const changeDateFormat = new dateFormat();
$(document).ready(function() {
  productInstance.init();
  productInstance.render();
});