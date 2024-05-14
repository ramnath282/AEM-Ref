import { dateFormat } from './dateFormat';
import {manageAffirm} from './affirmkey';
import { getStorage, setStorage } from './sessionStorage';
import  apiConfig  from './apiConfig';
import  ajaxRequest  from './ajaxbinding';

export const productSupportObj = {

};

productSupportObj.checkProductCallOut = (productDetails, releaseDate, productType, isQuickView) => {
    let parentObj = isQuickView ? $("#quickViewModal .product-wrapper") : $(".product-info-wrapper:first");
    let newOverrideFlag = isQuickView ? (productDetails.NewOverrideFlag ? productDetails.NewOverrideFlag : "") : parentObj.attr("data-newOverrideFlag");
    let releaseDateWeb = isQuickView ? releaseDate : parentObj.attr("data-releasedateweb");
    if(releaseDateWeb != ""){
        let compareMonth = dateFormatInst.comparePastMonths(releaseDateWeb, 6);
        let productCalloutAttr = parentObj.find(".product-callout-attribute").text().trim();
        let itemListPrice = parentObj.find(".offer_price").text().trim();
        let itemOfferPrice = parentObj.find(".current_price").text().trim();
        if(productCalloutAttr != ""){
            if(compareMonth && productCalloutAttr.indexOf("New") == -1 && newOverrideFlag != "Y"){
                parentObj.find(".product-callout-attribute").text("New - "+productCalloutAttr);
            }else{
                parentObj.find(".product-callout-attribute").text(productCalloutAttr);
            }
        }else{
            if(compareMonth && newOverrideFlag != "Y"){
                parentObj.find(".product-callout-attribute").text("New");
            }else{
                if(productType != "BundleBean" && parseFloat(itemListPrice) > parseFloat(itemOfferPrice)){
                    parentObj.find(".product-callout-attribute").text("Sale");
                }        
            }
        }
    }
}

productSupportObj.compareInventoryStatus = (swatchInventoryStatus, swatchBackOrderDate, isQuickView) => {
    let dateFlag;
    let parentObj = isQuickView ? $("#productViewModal") : $(".product-info-wrapper.parent");
    let $parentInvObj = parentObj.find(".inventory-status-message");
    let parentInventoryStatus = $parentInvObj.attr("data-invstatus").trim(),
        parentBackorderDate = $parentInvObj.attr("data-backorderdate") && $parentInvObj.attr("data-backorderdate").length > 0 ? $parentInvObj.attr("data-backorderdate").trim() : "";
    let inventoryStatusObj = {
      inventoryStatus : "",
      backOrderDate : ""
    };
    if(!(parentBackorderDate == "" || parentBackorderDate == undefined) && !(swatchBackOrderDate == "" || swatchBackOrderDate == undefined)){
      dateFlag = dateFormatInst.compareTwoDates(parentBackorderDate, swatchBackOrderDate);
    }

    switch (parentInventoryStatus) {
      case 'Available':
          if(parentInventoryStatus == swatchInventoryStatus){
            inventoryStatusObj.inventoryStatus = parentInventoryStatus;
          }else if(swatchInventoryStatus == "Backordered" || swatchInventoryStatus == "Backorderable" || swatchInventoryStatus == "PreOrderable"){
            inventoryStatusObj.inventoryStatus = swatchInventoryStatus;
            inventoryStatusObj.backOrderDate = swatchBackOrderDate;
          }else if(swatchInventoryStatus == "Limited"){
            inventoryStatusObj.inventoryStatus = swatchInventoryStatus;
          }else{
            inventoryStatusObj.inventoryStatus = "noLongerAvailable";
          }
          break;
      case 'Backorderable':
      case 'Backordered':
          if(parentInventoryStatus == swatchInventoryStatus){
            inventoryStatusObj.inventoryStatus = parentInventoryStatus;
            inventoryStatusObj.backOrderDate = dateFlag == 1 ? parentBackorderDate : swatchBackOrderDate;
          }else if(swatchInventoryStatus == "Available" || swatchInventoryStatus == "Limited"){
            inventoryStatusObj.inventoryStatus = parentInventoryStatus;
            inventoryStatusObj.backOrderDate = parentBackorderDate;
          }else if(swatchInventoryStatus == "No longer available" || swatchInventoryStatus == "noLongerAvailable"){
            inventoryStatusObj.inventoryStatus = swatchInventoryStatus;
          }else if(swatchInventoryStatus == "PreOrderable"){
            inventoryStatusObj.inventoryStatus = dateFlag == 1 ? parentInventoryStatus : swatchInventoryStatus;
            inventoryStatusObj.backOrderDate = dateFlag == 1 ? parentBackorderDate : swatchBackOrderDate;
          }else{
            inventoryStatusObj.inventoryStatus = "noLongerAvailable";
          }
          break;
      case 'Limited':
          if(parentInventoryStatus == swatchInventoryStatus || swatchInventoryStatus == "Available"){
            inventoryStatusObj.inventoryStatus = parentInventoryStatus;
          }else if(swatchInventoryStatus == "Backordered" || swatchInventoryStatus == "Backorderable" || swatchInventoryStatus == "PreOrderable"){
            inventoryStatusObj.inventoryStatus = swatchInventoryStatus;
            inventoryStatusObj.backOrderDate = swatchBackOrderDate;
          }else if(swatchInventoryStatus == "No longer available" || swatchInventoryStatus == "noLongerAvailable"){
            inventoryStatusObj.inventoryStatus = swatchInventoryStatus;
          }else{
            inventoryStatusObj.inventoryStatus = "noLongerAvailable";
          }
          break;
      case 'No longer available':
      case 'noLongerAvailable':
          inventoryStatusObj.inventoryStatus = "noLongerAvailable";
          break;
      case 'PreOrderable':
          if(parentInventoryStatus == swatchInventoryStatus){
            inventoryStatusObj.inventoryStatus = parentInventoryStatus;
            inventoryStatusObj.backOrderDate = dateFlag == 1 ? parentBackorderDate : swatchBackOrderDate;
           }else if(swatchInventoryStatus == "Available" || swatchInventoryStatus == "Limited"){
            inventoryStatusObj.inventoryStatus = parentInventoryStatus;
            inventoryStatusObj.backOrderDate = parentBackorderDate;
          }else if(swatchInventoryStatus == "Backordered" || swatchInventoryStatus == "Backorderable"){
            inventoryStatusObj.inventoryStatus = dateFlag == 1 ? parentInventoryStatus : swatchInventoryStatus;
            inventoryStatusObj.backOrderDate = dateFlag == 1 ? parentBackorderDate : swatchBackOrderDate;
          }else if(swatchInventoryStatus == "No longer available" || swatchInventoryStatus == "noLongerAvailable"){
            inventoryStatusObj.inventoryStatus = swatchInventoryStatus;
          }else{
            inventoryStatusObj.inventoryStatus = "noLongerAvailable";
          }
          break;
      default:
          inventoryStatusObj.inventoryStatus = "noLongerAvailable";
          break;
    }
    return inventoryStatusObj;
}


productSupportObj.roundUpBazzarVoiceRating = (averageOverallRating) => {
    let ratingFrac, ratingInt,
        ratingResult;
    if(averageOverallRating && averageOverallRating.toString().indexOf(".") != -1){
      ratingInt = averageOverallRating.toString().split(".")[0];
      ratingFrac = averageOverallRating.toString().split(".")[1];
      ratingFrac = ratingFrac.charAt(0);
      if(ratingFrac > 5){
        ratingInt++;
        ratingFrac = 0;
      }else if(ratingFrac < 5 && ratingFrac != 0){
        ratingFrac = 5;
      }
      ratingResult = ratingInt+"."+ratingFrac;
    }else{
      ratingResult = averageOverallRating;
    }
    return ratingResult;
}

productSupportObj.productPriceShow = (productType, affirmIneligible) => {
    let assocChkdObj = $("#productViewModal .addon-option .custom-checkbox:checked");
    let offerPrice = $("#productViewModal .product-price .offer_price").text().trim() == "" ? 0 : $("#productViewModal .product-price .offer_price").text().trim();
    let priceSaved = $("#productViewModal .discount_price").eq(0).text().trim() == "" ? 0 : $("#productViewModal .discount_price").eq(0).text().trim();
    let totalPrice = parseFloat(offerPrice) - parseFloat(priceSaved);
    assocChkdObj.each((index, addOnObj) => {
    let addOnPrice = $(addOnObj).parent().find(".addon-price").text().substring(1).trim() == "" ? 0 : $(addOnObj).parent().find(".addon-price").text().substring(1).trim();
    totalPrice = parseFloat(totalPrice) + parseFloat(addOnPrice);

    });
    $("#productViewModal .product-price .current_price").eq(0).text(parseFloat(totalPrice).toFixed(2));
    if(productType.toLowerCase()=="itembean"){
        productSupportObj.affirmCheck(affirmIneligible);
    }
}

productSupportObj.affirmCheck = (affirmIneligible, inventoryStatus, price) => {
    inventoryStatus = inventoryStatus || $("#productViewModal .inventory-status-message").text().trim();
    price = price || $("#productViewModal .current_price").text();
    if(affirmIneligible != "Y" && inventoryStatus.indexOf("Backordered") == -1 && inventoryStatus.indexOf("Preorder") == -1 && inventoryStatus != "No longer available" && inventoryStatus != "noLongerAvailable" && inventoryStatus != "Unavailable") {
        manageAffirm(parseFloat(price || 0));
    }
}

productSupportObj.showHideQuickSell = (quickSellAssociationArr) => {
    if(quickSellAssociationArr && quickSellAssociationArr.length > 0){
        $.each(quickSellAssociationArr, (k,v) => {
            let quickSellPartNumber = v.partNumber,
                quickSellInvStatus = v.inventoryStatus,
                $quickSellObj = $("input[data-partnumber="+quickSellPartNumber+"]");
            if(quickSellInvStatus == "Available"){
                $quickSellObj.parents(".addon-option, .associations-addons").removeClass("hide");
            }
        });
    }
}

productSupportObj.renderAssociatePrice = (obj, partNumberToPrices) => {
    obj.price = partNumberToPrices[obj.partNumber];
    return obj;
}

productSupportObj.showInventoryStatus = (data, productStatus, productType, productInventoryStatusObj, headerInventoryStatus) => {
    if(productStatus=="Available") {
        $("#productViewModal .inventory-status-message").addClass("available-status").html("");                
    } else {
        $("#productViewModal .inventory-status-message").removeClass("available-status").html(productInventoryStatusObj[productStatus]);
        $("#productViewModal .btn-add-to-bag").attr("disabled",true);
    }
    $("#productViewModal .inventory-status-message").attr("data-invstatus", productStatus);
    if(productStatus =="Available" || productStatus =="Backordered" || productStatus =="Backorderable" || productStatus =="Limited" || productStatus =="PreOrderable") {
        
        if(productStatus=="Backorderable" || productStatus=="Backordered" || productStatus=="PreOrderable") {
            let inventoryBackDate = _.map(data.variants, item => item.core.variant_inventorystatus == productStatus && item.core.variant_backorderdate).filter(Boolean)[0];
            if((headerInventoryStatus == "Backordered" || headerInventoryStatus == "Backorderable")&& data.variants.length > 1){
              let headerBackorderDateArr = [];
              _.each(data.variants, item =>{
                headerBackorderDateArr.push(new Date(item.core.variant_backorderdate));
              });
              inventoryBackDate = dateFormatInst.getLatestDate(headerBackorderDateArr);
            }
            let headerInvBackDate = "";
            $("#productViewModal .inventory-status-message").attr("data-backorderdate", inventoryBackDate);
            if(productType.toLowerCase() != "itembean"){
                headerInvBackDate = inventoryBackDate || "";
                $("#productViewModal .inventory-status-message").attr("data-backorderdate", headerInvBackDate);
                headerInvBackDate = headerInvBackDate != "" ? dateFormatInst.formatToNewDate(new Date(headerInvBackDate.replace(/ /g, "T")), headerInvBackDate) : "";
            }
            inventoryBackDate = (inventoryBackDate != "" || inventoryBackDate != undefined) ? dateFormatInst.formatToNewDate(new Date(inventoryBackDate.replace(/ /g, "T")), inventoryBackDate) : "";
            let backOrderDate = productType.toLowerCase()=="itembean" ? inventoryBackDate : headerInvBackDate;
            if(productStatus=="PreOrderable"){
                $("#productViewModal .back-oderable-date").html(" - available " + backOrderDate);
            }else{
                $("#productViewModal .back-oderable-date").html(" until " + backOrderDate);
            }
        }else{
            $("#productViewModal .back-oderable-date").html("");
        }
        $("#productViewModal .btn-add-to-bag").removeAttr("disabled");
    } 
}

productSupportObj.strikeThroughPrice = (inventoryResponse) => {
    $.each(inventoryResponse, (k,v)=> {
        if(v.inventoryStatus.toLowerCase()=="unavailable") {
            $('[data-partnoswatch="'+v.partNumber+'"]').addClass("out-of-stock");
        } else if(v.inventoryStatus=="No longer available" || v.inventoryStatus=="noLongerAvailable") {
            $('[data-partnoswatch="'+v.partNumber+'"]').addClass("unavailable");
        }
    });
}

// Related to Bazzar Voice load
productSupportObj.waitForBVLoad = (cb) => {
    let timer;
    if (checkingBVLoadCnt >= 15) {
      window.clearTimeout(timer);
      cb(false);
      console.log("$BV plugin not found..");
      return;
    }
    if (typeof $BV == "undefined") {
      timer = setTimeout(a => {
        checkingBVLoadCnt++;
        productSupportObj.waitForBVLoad(cb);
      }, 1000);
      return false;
    }
    window.clearTimeout(timer);
    cb(true);
}

// productSupportObj.getProductInventoryStatusMsg = () => {
//     let ajaxOption = apiConfigInst.getApiConfig("productInventoryStatus");
//     const sessionData = getStorage("productStorage");
//     productSupportObj.inventryMessages = sessionData;

//     if (!sessionData) {
//       ajaxOption.async = false;
//       request
//         .ajaxCall(ajaxOption)
//         .then(data => {
//           productSupportObj.inventryMessages = data;
//           setStorage("productStorage", data);
//         })
//         .catch(error => {
//           console.log(error);
//         });
//     }     
// }
productSupportObj.getBazzarVoiceRating = (ele) => {
    let parentObj = $(ele);
    let partVal= parentObj.hasClass("giftCard-container") ? parentObj.find('#giftPartNumber').val() : parentObj.attr("data-partnumber");
    let passkey = $("#bazarVoicePassKey").val();
    let readReviewObj = parentObj.hasClass("giftCard-container") ? parentObj.find(".read-reviews span span") : parentObj.find(".read-reviews span");
    $.getJSON(`//api.bazaarvoice.com/data/statistics.json?apiversion=5.4&passkey=${passkey}&Filter=ProductId:${partVal}&Include=Products&stats=Reviews&callback=?`, function (res) {
        if(res.Results && res.Results.length > 0){
            let nativeReviewStatistics = res.Results[0].ProductStatistics.ReviewStatistics;
            let averageOverallRating = nativeReviewStatistics.AverageOverallRating == null || nativeReviewStatistics.AverageOverallRating == undefined ? 0 : nativeReviewStatistics.AverageOverallRating;
            let starRatingResult = productSupportObj.roundUpBazzarVoiceRating(averageOverallRating);
            let avgRating = starRatingResult.toString().indexOf(".") != -1 ? starRatingResult.toString().replace(".","-") : (starRatingResult == 0 ? starRatingResult.toString() : starRatingResult.toString() + "-0");
            let ratingClass = `rating-${avgRating}`;
            parentObj.find(".rating-section").addClass(ratingClass);
            parentObj.find(".rating-section span").html(`${avgRating.replace("-",".")} Stars`);
            let reviewCount = nativeReviewStatistics.TotalReviewCount;
            if(reviewCount == ''){
                readReviewObj.html('0');
            }else{
                readReviewObj.html(reviewCount);
            }
        }else{
            parentObj.find(".rating-section").addClass("rating-0");
            parentObj.find(".rating-section span").text("0 Stars");
            readReviewObj.text("0");
        }
    }).fail(function(error){
        console.log(error);
    });
}
export const storeAndGetPayloadData = (type, data, id) =>{
  if(!window.global.payloadData){ window.global.payloadData = {};}
  if(type == "get"){
    return window.global.payloadData[id] || {};
  } else if (type == "set"){
    if(data.type == "Swatches" && data.headerInventory){
      _.each(data.headerInventory, (swatch) => {
        window.global.payloadData[`${swatch.id}`] = {
          "inventoryStatus" : swatch.core.variant_inventorystatus,
          "recieptid": swatch.core.variant_itematpreceiptid,
          "backorderdate" : swatch.core.variant_backorderdate,
          "productType": data.productType,
          "imageLink" : swatch.core.variant_imageLink,
          "pricing":data.pricing
        };
      });
    } else {
      window.global.payloadData[`${data.id || id}`] = data;
    }
    if(data.productType == "PackageBean" && data.associations){
      let variantsAttrs;
      _.each(data.associations, (association) => {
        variantsAttrs = association.product.variants[0];
        if(!variantsAttrs) return;
        window.global.payloadData[`${variantsAttrs.id}`] = {
          "inventoryStatus" : variantsAttrs.core.variant_inventorystatus,
          "recieptid": variantsAttrs.core.variant_itematpreceiptid,
          "backorderdate" : variantsAttrs.core.variant_backorderdate,
          "productType": association.product.core.product_type,
          "associationType": association.association_type,
          "partnumber": variantsAttrs.core.sku,
          "pricing":data.pricing
        };
      });
    }
    if(data.type == "Component"){
      _.each(data.variants, (swatch) => {
        window.global.payloadData[`${swatch.id}`] = {
          "inventoryStatus" : swatch.core.variant_inventorystatus,
          "recieptid": swatch.core.variant_itematpreceiptid,
          "backorderdate" : swatch.core.variant_backorderdate,
          "productType": data.productType,
          "associations":data.associations,
          "type" : data.type,
          "imageLink" : swatch.core.variant_imageLink,
          "pricing":data.pricing
        };
      });
  }
  }
};
let checkingBVLoadCnt = 0,
    dateFormatInst = new dateFormat(),
    apiConfigInst = new apiConfig(),
    request = new ajaxRequest();   
