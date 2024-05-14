import eventBinding from '../shared/eventBinding';
import constant from '../shared/constant';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import { handleBarTemplate } from '../shared/templateSetter';
import fieldValidation from '../shared/inputvalidation';
import { exceptionHandler } from '../shared/flickerMessage';
import { dateFormat } from "../shared/dateFormat";
import { getStorage, setStorage } from '../shared/sessionStorage';
import { userCategory } from "../shared/addToBagDisableCheck";
import { productSupportObj } from '../shared/productSupportiveFunctions';
const config = {
    headerInventoryData: "",
    headerBackorderData: "",
    headerReceiptid:"",
    giftcardAssociation: [],
}
class logoGiftCard {
    constructor() {
        self = this;
        this.element = ".giftCard-container";
        this.productInventoryStatusObj = {};
        this.addToBagBtnEle = $(self.element).find(".btn-add-to-bag");
    }
    init() {
        if (this.element.length) {
            this.render();
            eventBindingInst.bindLooping({
                "keyup .giftCard-container .has-error input": "inputChange",
                "change .giftCard-container input, .giftCard-container .has-error select": "inputChange",
                "change .giftCard-container #messageSelect": "enableMessagBox",
                "click .giftCard-container .dropdown-toggle": "enableScreenReader",
                "keydown .giftCard-container .dropdown-toggle": "enableScreenReader",
                "click .giftCard-container .giftcard-amount-section li.innerCont": "giftcardAmountSelect",
                "click .giftCard-container .add-update-message": "formSubmit",
                "click .close-product-addOns,#addons-modal-close": "closeModal",
                "keydown .giftCard-container .dropdown-menu.inner li": "selectDropdownOption",
                "click .review-status .write-review": "writeReview",
                "click .read-reviews": "navigateToHrefId"
            }, self);
        }
        $(function() {
            let hashVal = window.location.hash.toLowerCase();
            productSupportObj.waitForBVLoad(function(isLoaded) {
                if (isLoaded) {
                    if (hashVal == "#writereview" || hashVal == "#bvoswritereview") {
                        $(".giftCard-container .write-review")[0].click();
                    } else if (hashVal == "#bvrrcontainer" || hashVal == "#bvreviewheading") {
                        $(".giftCard-container .read-reviews")[0].click();
                    }
                }
            });
        });
        if ((window.location.href.indexOf("/shop/p/") > -1 || window.location.href.indexOf(" /shop/ag/") > -1) && $('#pageEditMode').val() != 'author'); {
            $(window).on("resize load orientationchange", function(e) {
                if (window.innerWidth >= 1024) {
                    let giftcard_height = $(".product-slider").parents(".col-first").outerHeight();
                    let accordionHeight = $(".accordion .accordion-comp").outerHeight();
                    let socialShareHeight = $(".at-svc-facebook").outerHeight() || 50;
                    $(".giftcard").css('min-height', giftcard_height + accordionHeight + socialShareHeight);
                } else {
                    $(".giftcard").css("height", "auto");
                }
            });
        }
    }
    render() {
        productSupportObj.getBazzarVoiceRating(".giftCard-container");
        self.getProductInventoryStatusMsg();
        self.renderDropDown();
        self.renderGiftcardVariants();
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
    renderDropDown() {
        $(self.element).find(".message-select").selectpicker({
            dropupAuto: false
        });
        $(self.element).find(".dropdown-menu.open").find(".inner.open .dropdown-menu.inner").attr("role", "listbox");
    }
    renderGiftcardVariants() {
        let partNo = $("#giftPartNumber").val();
        let ajaxOption = apiConfigInst.getApiConfig("logoGiftCard").getProductAPI(partNo);
        request.ajaxCall(ajaxOption).then(data => {
            let { variants, associations } = data.product;
            let childcomponent = [],
                count = 0;
            handleBarTemplateInst.loadTemplate("#giftCardAmountTmpl", "#giftCardAmountSec", variants, "replace");
            $.each(associations, (index, item) => {
                if (item.association_type == "COMPONENT") {
                    childcomponent.push({
                        "partNo": item.product.variants[0].core.sku,
                        "variantID": item.product.variants[0].id,
                        "associationType": item.association_type,
                        "productType": item.product.core.product_type,
                        "inventoryStatus": item.product.variants[0].core.variant_inventorystatus,
                        "backorderDate": item.product.variants[0].core.variant_backorderdate ? item.product.variants[0].core.variant_backorderdate : "",
                        "recieptid" : item.product.variants[0].core.variant_itematpreceiptid ? item.product.variants[0].core.variant_itematpreceiptid : ""
                    });
                    count++
                }
            });
            if (childcomponent.length == count) {
                config.giftcardAssociation = childcomponent;
            }
        }).catch(error => {
            window.global.errorHandling.PDPAPI(error);
            console.log("error");
        });
    }
    enableScreenReader(el, evt) {
        evt.preventDefault();
        let keyCode = evt.keyCode;
        if (keyCode == 13 || keyCode == undefined) {
            $(self.element).find(".inner.open").removeAttr("aria-expanded");
            $(self.element).find(".dropdown-menu.open").find(".inner.open .dropdown-menu.inner li").attr("role", "presentation");
        } else if (evt.shiftKey) {
            if (keyCode == 9) {
                if (!$(self.element).find(".message-select").hasClass("open")) {
                    $(self.element).find("#senderName").focus();
                }
            }
        } else if (keyCode == 9) {
            if (!$(self.element).find(".message-select").hasClass("open")) {
                if ($(self.element).find(".message-box-container").hasClass("hide")) {
                    $(self.element).find(".amount-field").focus();
                } else {
                    $(self.element).find("#messageLine1").focus();
                }
            }
        }
    }
    selectDropdownOption(el, evt) {
        let keyCode = evt.keyCode;
        if (keyCode == 13) {
            $(el).find("a").trigger("click");
            setTimeout(a => {
                $(self.element).find(".bootstrap-select").removeClass("open");
                $(self.element).find(".dropdown-toggle").attr("aria-expanded", "false");
            }, 200);
        }
    }
    inputChange(target) {
        let curVal = $(target).val();
        if (curVal != "") {
            validate.instantValidation(target);
        }
    }
    writeReview(ele, evt) {
        evt.preventDefault();
        let productId = $("#giftPartNumber").val() || false;
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
        $("html, body").animate({ scrollTop: $(targetID).offset().top - headerHeight },
            1000
        );
        evt.preventDefault();
    }
    enableMessagBox(el, evt) {
        evt.preventDefault();
        let messageVal = $.trim($(self.element).find("#messageSelect").val()),
            seqIndex = $(self.element).find("option:selected").attr("seq");
        if (messageVal == "") {
            $(self.element).find(".message-box-container").addClass("hide");
        } else {
            $(self.element).find(".message-box-container").removeClass("hide");
            if (messageVal != "add") {
                $(self.element).find(".message-box-container .input-field").addClass("hide");
                $(self.element).find(".message-box-container .input-field[data-seq=" + seqIndex + "]").removeClass("hide");
            } else {
                $(self.element).find(".message-box-container .input-field").addClass("hide");
                $(self.element).find(".message-box-container .input-field[data-seq='']").removeClass("hide");
            }
        }
    }
    getInputValues(ele, obj, calledFrom) {
        let type = $(ele).attr("type"),inputObj = {};
        let key = calledFrom == 'addGC' ? $(ele).attr("data-add") : $(ele).attr("data-key");
        switch (type) {
            case "text":
                if ($(ele).hasClass("message-box")) {
                    if ($(ele).is(":visible")) {
                        inputObj[key] = $(ele).val();
                    }
                } else {
                    inputObj[key] = $(ele).val();
                }
                break;
            case "number":
                inputObj[key] = $(ele).val();
                break;
            case "checkbox":
                inputObj[key] = $(ele).is(":checked");
                break;
            case "radio":
                if ($(ele).is(":checked")) {
                    inputObj[key] = $(ele).val();
                }
                break;
            default:
                if ($(ele).hasClass("dob-select")) {
                    let dateVal = $(ele).val();
                    let dateStr = dateStr == "" ? dateVal : dateStr + "/" + dateVal;
                    inputObj[key] = dateStr;
                } else {
                    inputObj[key] = $(ele).val();
                }
        }
        return inputObj;
    }
    getAPIPayload(inputNames, formElement, calledFrom) {
        let obj = {},inputArray = [];
        let id = '';
        inputNames.each(index => {
            id = $(inputNames).eq(index).attr("id");
            if (chkGiftCardMsg) {
                if (id != 'amount') {
                    inputArray.push(self.getInputValues(inputNames.eq(index), obj, calledFrom));
                }
            } else {
                inputArray.push(self.getInputValues(inputNames.eq(index), obj, calledFrom));
            }
        });
        let res = inputArray.filter(value => Object.keys(value).length !== 0);
        if (chkGiftCardMsg) {
            obj["validateType"] = "giftMsg";
            res.push(obj);
            let profinityRes = res.reduce(function(result, current) {
                return Object.assign(result, current);
              }, {})
            return profinityRes;
        }
        else{
            return res;
        }
    }
    giftcardAmountSelect(ele, evt) {
        evt.preventDefault();
        let $giftcardEle = ".giftcard-amount-swatches li.innerCont",
            $giftcardSection = ".giftcard-amount-section",
            SwatchpartNum = $(ele).attr("data-partnoswatch"),
            productpartNum = $("#giftPartNumber").val();
        if ($($giftcardEle + ".active").length > 0) {
            $($giftcardEle).removeClass("active");
        }
        if ($($giftcardSection + ".has-error").length > 0) {
            $($giftcardSection).removeClass("has-error");
            $("#amountError").html("")
        }
        $(ele).addClass("active");
        $("#page-content").addClass("request-pending");
        self.giftCardInventoryStatus(SwatchpartNum, productpartNum)
    }
    giftCardInventoryStatus(swatchpartNo, productpartNo) {
        let parentObj = $(".giftCard-container");
        let ajaxOption = apiConfigInst.getApiConfig("logoGiftCard").getInventory(productpartNo);
        request.ajaxCall(ajaxOption).then(data => {
            let { variants } = data.product;
            let headerInventoryStatus, headerInventoryDate, headerReceiptid;
            $.each(variants, (k, v) => {
                if (swatchpartNo == v.core.sku) {
                    headerInventoryStatus = cartAPI.readInventoryvalueAPI(v.core.variant_inventorystatus);
                    headerInventoryDate = v.core.variant_backorderdate ? v.core.variant_backorderdate : "";
                    headerReceiptid = v.core.variant_itematpreceiptid ? v.core.variant_itematpreceiptid : "";
                }
                let backOrderDate = headerInventoryDate ? headerInventoryDate : "";
                backOrderDate = backOrderDate != "" ? changeDateFormat.formatToNewDate(new Date(backOrderDate.replace(/ /g, "T")), backOrderDate) : "";
                let inventoryStatusMsgUI = self.productInventoryStatusObj[headerInventoryStatus];
                config.headerInventoryData = headerInventoryStatus;
                config.headerReceiptid = headerReceiptid;
                config.headerBackorderData = backOrderDate
                if (headerInventoryStatus == "No longer available" ||
                    headerInventoryStatus == "noLongerAvailable" ||
                    headerInventoryStatus == "Unable to access inventory" ||
                    headerInventoryStatus == "Unavailable") {
                    parentObj.find(".inventory-status-message").removeClass("available-status").html(inventoryStatusMsgUI);
                    parentObj.find(".back-oderable-date").html("");
                    parentObj.find(".btn-add-to-bag").attr("disabled", true);
                } else {
                    if (headerInventoryStatus == "Available") {
                        parentObj.find(".inventory-status-message").addClass("available-status").html("");
                        parentObj.find(".back-oderable-date").html("");
                    } else if (headerInventoryStatus == "Backordered" ||
                        headerInventoryStatus == "Backorderable" ||
                        headerInventoryStatus == "PreOrderable") {
                        parentObj.find(".inventory-status-message").removeClass("available-status").html(inventoryStatusMsgUI);
                        if (headerInventoryStatus == "PreOrderable") {
                            parentObj.find(".back-oderable-date").html(" - available " + config.headerBackorderData);
                        } else {
                            parentObj.find(".back-oderable-date").html(" until " + config.headerBackorderData);
                        }
                    } else {
                        parentObj.find(".inventory-status-message").removeClass("available-status").html(inventoryStatusMsgUI);
                        parentObj.find(".back-oderable-date").html("");
                    }
                    if (!headerInventoryStatus) {
                        parentObj.find(".btn-add-to-bag").attr("disabled", true);
                    } else {
                        parentObj.find(".btn-add-to-bag").removeAttr("disabled");
                    }
                }
                $("#page-content").removeClass("request-pending");
            });
        }).catch( error => {
            window.global.errorHandling.PDPAPI(error);
        });
    }
    giftSwatchValidation(ele, cb) {
        let $ele = $(".giftcard-amount-swatches li"),
            activeClsLenth = 0;
        $.each($ele, (index, item) => {
            if ($(item).hasClass("active")) {
                activeClsLenth++;
            }
        });
        if (activeClsLenth == 0) {
            validate.errorToggle(ele, null, 'ERROR_AMOUNTREQUIRED');
            cb(false);
        }
        cb(true);
    }
    formSubmit(target, evt) {
        evt.preventDefault();
        let formElement = target.dataset.formname || target.form;
        let option = "",
            Giftflag = false,
            $giftcardSection = ".giftcard-amount-section";
        if ($(".giftcard-amount-swatches li.active").length > 0) {
            Giftflag = true;
        }
        validate.checkOnlyVisibleFields(formElement, (output) => {
            if (!output || !Giftflag) {
                self.giftSwatchValidation($giftcardSection, (result) => {
                    if (!result) {
                        return;
                    }
                });
                return;
            }
            let inputNames = $(formElement).find("[data-key]");
            const payload = self.getAPIPayload(inputNames, formElement, 'checkGC');
            option = apiConfigInst.getApiConfig("logoGiftCard").check;
            self.addToCart(target, evt, option, payload);
        });
    }
    addToCart(target, evt, option, payload) {
        option.data = JSON.stringify(payload);
        validate.showLoading(".logo-giftCard-container");
        self.addToBagBtnEle.attr("disabled", true);
        request.ajaxCall(option)
            .then(data => {
                chkGiftCardMsg = false;
                validate.hideLoading(".logo-giftCard-container");
                if (data.isNameValid) {
                    self.addGiftCardData(target, evt);
                    chkGiftCardMsg = true;
                } else {
                    exceptionHandler('error', 'Sorry! Your Gift Message contains inappropriate language. Please edit the message before submitting.');
                    chkGiftCardMsg = true;
                    self.addToBagBtnEle.attr("disabled", false);
                }
            })
            .catch(error => {
                console.log(error);
                validate.hideLoading(".logo-giftCard-container");
                self.addToBagBtnEle.attr("disabled", false);
            });
    }
    getAddGiftCardData(payload, formElement) {
        let giftCardVarientID = $(formElement).find("#giftCardAmountSec ul li.innerCont.active").attr("data-variant-id"),
            parentPartNo = $(formElement).closest(".giftCard-container").find('.product-info-wrapper').attr("data-partnumber"),
            parentDatatype = $(formElement).closest(".giftCard-container").find('.product-info-wrapper').attr("data-producttype"),
            refNo = giftCardVarientID + Math.random().toString(16).slice(2);
        let addGCObj = {},
            itemsArr = [];
        itemsArr.push({
            "quantity": 1,
            "id": giftCardVarientID, // Variant ID of the Gift Card Variant
            "properties": {
                "reference": refNo,
                "type": "parent",
                "productType": parentDatatype,
                "variant_inventorystatus": config.headerInventoryData,
                "variant_backorderdate": config.headerBackorderData ? config.headerBackorderData : "",
                "variant_itematpreceiptid":config.headerReceiptid,
                "replacementPartNumber": parentPartNo, //variant_parentpartnumber of price swatch sku
                "gift_message": payload,
                "gift_message_type": payload[2].gift_message_line1,
                "components":self.getChildPayload(itemsArr, refNo)
            }
        })
        //itemsArr[0].properties.gift_message.push(payload);
        //self.getChildPayload(itemsArr, refNo);
        addGCObj.items = itemsArr;
        return addGCObj;
    }
    getChildPayload(itemsArr, refNo) {
        let components = [],
        childItemarry = config.giftcardAssociation;
        $.each(childItemarry, (index, item) => {
            components.push({
                "quantity": 1,
                "id": item.variantID,
                "properties": {
                    "sku" : item.partNo,
                    "reference": refNo,
                    "type": "child",
                    "productType": item.productType,
                    "association": item.associationType,
                    "variant_inventorystatus": item.inventoryStatus,
                    "variant_backorderdate": item.backorderDate,
                    "variant_itematpreceiptid": item.recieptid
                }
            });
        });
        return components;
    }
    addGiftCardData(target, evt) {
        evt.preventDefault();
        let formElement = target.dataset.formname || target.form;
        let option = "",
            variantID;
        let inputNames = $(formElement).find("[data-add]");
        option = apiConfigInst.getApiConfig("addToBag").addProductToBag;
        const payload = self.getAPIPayload(inputNames, formElement, 'addGC');
        const finalPayload = self.getAddGiftCardData(payload, formElement);
        option.data = finalPayload;
        variantID = finalPayload.items[0].id;
        validate.showLoading(".logo-giftCard-container");
        request.ajaxCall(option)
            .then(data => {
                $(window).scrollTop(0);
                chkGiftCardMsg = true;
                let isGetCallAnonymously = true;
                validate.hideLoading(".logo-giftCard-container");
                self.addToBagBtnEle.attr("disabled", false);
                validate.showLoading(".logo-giftCard-container");
                cartAPI.callGetCartAPI(apiConfigInst.getApiConfig("getMiniCart"), data => {
                    if (!isGetCallAnonymously) {
                        cb(data);
                    }
                    self.callMinicartModal(data, variantID)
                });
            })
            .catch(error => {
                window.global.errorHandling.PDPAPI(error);
                validate.hideLoading(".logo-giftCard-container");
                self.addToBagBtnEle.attr("disabled", false);
            })
    }
    closeModal(ele, evt) {
        if ($(ele).hasClass('shopping-bag')) {
            evt.preventDefault();
            $("#productAddOnModal").modal("hide");
            return;
        }
        self.callMinicartModal({}, {});
    }
    callMinicartModal(data, variantIds) {
        const myNewGiftCardData = self.getProductDetailsForMinicart(data, variantIds);
        handleBarTemplateInst.loadTemplate("#giftCardTmpl", "#addOnsModal", myNewGiftCardData, "replace");
        validate.hideLoading(".logo-giftCard-container");
        $('#productAddOnModal').modal("show");
    }
    getProductDetailsForMinicart(getCartResponse, variantIds) {
        const { items, total_price } = getCartResponse;
        const finalCartCount = self.getFinalCartCount(getCartResponse);
        let product, salePrice,
            pageReferal = window.location.href,
            count = 0;
        product = _.find(items, function(item) {
            return item.id == variantIds;
        });
        if (!product) return;
        count++;
        salePrice = cartAPI.formatWithDelimiters(product.price, 2);
        return {
            "salePrice": salePrice,
            "productName": product.product_title,
            "imageLink": product.image,
            'totaladdedproduct': count,
            'pageReferal': pageReferal.indexOf('#') > -1 ? pageReferal.replace("#", "") : pageReferal,
            'newminiCartData': Object.assign(
                self.getShippingInfo(parseInt(cartAPI.formatWithDelimiters(total_price, 2).replace(/,/g, ''))), {
                    cartQuantity: parseInt(finalCartCount || 0),
                    orderTotal: cartAPI.formatWithDelimiters(total_price, 2)
                })
        }
    }
    getFinalCartCount(data) {
        let childQnty = 0;
        _.filter(data.items, item => {
            if (item.properties && (item.properties.type == "child")) {
                childQnty += _.isNumber(item.quantity) ? item.quantity : 0;
            }
            return childQnty;
        });
        return parseInt((data.item_count - childQnty) || 0);
    }
    getShippingInfo(totAmt) {
        let freeShippingLimit = parseInt($("#freeShippingLimit").val()) || 125;
        let isShippingFree = totAmt < freeShippingLimit;
        return {
            freeshipping: isShippingFree,
            freeshippingAmount: isShippingFree && parseFloat((freeShippingLimit - totAmt)).toFixed(2)
        }
    }
}
let self,
    eventBindingInst = new eventBinding(),
    apiConfigInst = new apiConfig(),
    request = new ajaxRequest(),
    handleBarTemplateInst = new handleBarTemplate(),
    logoGiftCardInstance = new logoGiftCard(),
    validate = new fieldValidation(),
    chkGiftCardMsg = true;
const { cartAPI } = window.global;
const changeDateFormat = new dateFormat();
logoGiftCardInstance.init();