import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import  { handleBarTemplate }  from '../shared/templateSetter';
import fieldValidation from '../shared/inputvalidation';
import { handleBarsHelper } from '../shared/handleBarsHelper';
import { JsonArrayToString } from '../shared/JsonArrayToString';
import { cardType } from '../shared/cardType';
import {
    exceptionHandler
} from '../shared/flickerMessage';
import {getCookie} from '../shared/browserCookie';

class paymentInfo {    
    constructor() {
         self = this;
         this.element = ".payment-info-body";
    }
    init() {
        if(this.element.length) {
            this.render();
        }
        eventBindingInst.bindLooping({
            "click .payment-info-body .add-new-card": "addCard",
            "click .payment-info-body .cancel-card": "toggleAddUpdatePaymentDiv",
            "click .payment-info-body .edit-additional-payment": "openAdditionalEditPaymentInfo",
            "click .payment-info-body .edit-default-payment": "openDefaultEditPaymentInfo",
            "keyup .payment-info-body .has-error input": "inputChange",
            "keyup .payment-info-body .card-number": "fetchCardImage",            
            "change .payment-info-body input": "inputChange",
            "change .payment-info-body select": "inputChange",
            "change .payment-info-body .billing-address": "fillBillAddressOnChange",
            "click .payment-info-body .delete-cards": "deleteCardDetails",
            "click .payment-info-body .update-payment-info": "formSubmit"
        },self);
    }
    render() {
        validate.showLoading(".payment-info-body");
        request.ajaxCall(apiConfigInst.getApiConfig("paymentInfo").get)
            .then(data => {
                this.paymentData = data;
                if(this.paymentData.Cards != null && this.paymentData.Cards.length > 0){
                    $.each(this.paymentData.Cards, (key,val)=> { 
                        val.address.adressLineNew = covertToString.toString(val.address.addressLine);
                        val.cardNumEnd = val.maskAccount.slice(-4);
                    });
                }
                this.renderPaymentInfoDefault(this.paymentData);
                this.renderPaymentInfoAdditional(this.paymentData);
                validate.hideLoading(".payment-info-body");
             })
            .catch(error => {
                this.paymentData = null; 
                console.log(error)
                validate.hideLoading(".payment-info-body");
             })
    }
    errorMessage(err, loadingEle) {
        loadingEle && $(loadingEle).removeClass('request-pending');
        $(self.element).removeClass('request-pending');
    }
    fetchAddressList(el, evt, index) {
        validate.showLoading(".user-info-wrapper:first");
        request.ajaxCall(apiConfigInst.getApiConfig("addressBook").get)
            .then(data => {
                self.defaultFilteredData = self.returnDefaultPayment(self.paymentData);
                this.addressList = data;
                let loggedUserName = decodeURIComponent(getCookie("MATTEL_WELCOME_MSG")).replace(/\+/g, " ").split(",");
                this.addressList.addresses = (this.addressList.addresses == null  || this.addressList.addresses.length == 0) ? [] : this.addressList.addresses.filter( item => {
                    return item.firstName.toLowerCase() == loggedUserName[0].toLowerCase() && item.lastName.toLowerCase() == loggedUserName[1].toLowerCase();
                });
                if($(el).hasClass("add-new-card")){          // Add new card block
                    handleBarTemplateInst.loadTemplate("#paymentInfoTemp", "#paymentInfoAddUpdateDiv", self.addressList, "replace");                    
                    $(self.element).find(".submit-button").removeClass("hide");
                    $(self.element).find(".update-button").addClass("hide");
                    self.fillBillAddressOnChange(el, evt, "onClick");
                }else if($(el).hasClass("edit-default-payment")){                // Edit default card block
                    handleBarsHelperInst.callRegisterHelper("addressSelector", self.addressList.addresses, self.defaultFilteredData[0].address);
                    handleBarsHelperInst.callRegisterHelper("monthSelector", [1,2,3,4,5,6,7,8,9,10,11,12], self.defaultFilteredData[0].expMonth);
                    handleBarsHelperInst.callRegisterHelper("yearSelector", [2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,2034,2035,2036,2037,2038,2039], self.defaultFilteredData[0].expYear);
                    handleBarTemplateInst.loadTemplate("#paymentInfoTemp",  "#paymentInfoAddUpdateDiv", self.defaultFilteredData[0], "replace");
                    $(self.element).find(".submit-button").addClass("hide");
                    $(self.element).find(".update-button").removeClass("hide");                    
                    $(self.element).find("#myDefaultCard").prop('checked',true);
                    self.fillBillAddressOnChange(el, evt, "onClick");
                    validate.editFormFields($("#paymentInfoAddUpdateDiv"));
                }else{                    // Edit additional card block
                    handleBarsHelperInst.callRegisterHelper("addressSelector", self.addressList.addresses, self.additionalFilteredData.address);
                    handleBarsHelperInst.callRegisterHelper("monthSelector", [1,2,3,4,5,6,7,8,9,10,11,12], self.additionalFilteredData.expMonth);
                    handleBarsHelperInst.callRegisterHelper("yearSelector", [2019,2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,2034,2035,2036,2037,2038,2039], self.additionalFilteredData.expYear);
                    handleBarTemplateInst.loadTemplate("#paymentInfoTemp",  "#paymentInfoAddUpdateDiv", self.additionalFilteredData, "replace");                    
                    $(self.element).find(".submit-button").addClass("hide");
                    $(self.element).find(".update-button").removeClass("hide");
                    self.fillBillAddressOnChange(el, evt, "onClick");
                    validate.editFormFields($("#paymentInfoAddUpdateDiv"));
                }
                validate.hideLoading(".user-info-wrapper:first");
            })
            .catch(error => { 
                console.log(error)
                validate.hideLoading(".user-info-wrapper:first");
             })
    }
    toggleAddUpdatePaymentDiv(el, evt) {
        evt.preventDefault();
        let $addElem = $(self.element).find(".add-new-card");
        $addElem.toggleClass('transparent-btn-border');
        $addElem.next('div').toggleClass('hide');
        if($(el).hasClass('transparent-btn-border') || $(el).hasClass('cancel-card')){
            $(self.element).find("user-info-editMode").empty();
        }
        let ele = $addElem.attr('data-val');       
        if(ele == 'add'){
            $addElem.attr('value','Cancel');
            $addElem.attr('data-val', 'can');
        } else {
            $addElem.attr('value','Add a new credit/debit card');
            $addElem.attr('data-val', 'add');
        }
        $(self.element).find(".add-new-card").focus();
        let d = new Date();
        let currentYear = d.getFullYear();
        let options = "";
        for(let i=currentYear;i<=currentYear+20;i++) {
            if(options == "") {
                options = `<option value=${i}>${i}</option>`;
            }
            else {
                options =` ${options} <option value=${i}>${i}</option>`;
            }
        }
        setTimeout(() => {
            $("#cardValidYear").append(options);
        }, 1000);
    }
    openAddUpdatePaymentDiv(el, evt){
        evt.preventDefault();
        let $addElem = $(self.element).find(".add-new-card");
        if(!$addElem.hasClass('transparent-btn-border')){
            self.toggleAddUpdatePaymentDiv(el, evt);
        }else{
            $(self.element).find(".add-new-card").focus();
        }
    }
    addCard(el, evt){
        self.fetchAddressList(el, evt);
        self.toggleAddUpdatePaymentDiv(el, evt);
    }
    returnDefaultPayment(data){
        let obj = {},
            defaultArr = [];
        if(data != null){
            if(data.Cards != null && data.Cards.length > 0){
                $.each(data.Cards, (key,val) => {
                    if(val.defaultFlag == "1"){
                        if(val.address.addressLine && val.address.addressLine.length==3) {
                            val.address.addressLine.pop();
                        } 
                        obj = val;
                        defaultArr.push(obj);
                    }
                });
            }
        }
        return defaultArr;
    }
    returnAdditionalPayment(data){
        let paymentArr = [],
            obj = {};
        if(data != null){
            if(data.Cards != null && data.Cards.length > 0){
                $.each(data.Cards, (key,val) => {
                    if(val.defaultFlag != "1"){
                        if(val.address.addressLine && val.address.addressLine.length==3) {
                            val.address.addressLine.pop();
                        } 
                        obj = val;
                        paymentArr.push(obj);
                    }
                });
            }
        }
        return paymentArr;
    }
    renderPaymentInfoDefault(data){
        let defaultPayment = self.returnDefaultPayment(data);
        handleBarTemplateInst.loadTemplate("#defaultPaymentInfoTemp", "#defaultPaymentInfoDiv", {Cards:defaultPayment}, "replace");        
    }
    renderPaymentInfoAdditional(data){
        let additionalPaymentArr = self.returnAdditionalPayment(data);
        handleBarTemplateInst.loadTemplate("#additionalPaymentInfoTemp", "#additionalPaymentInfoDiv", {Cards:additionalPaymentArr}, "replace");
    }
    openDefaultEditPaymentInfo(el, evt){        
        self.fetchAddressList(el, evt);        
        self.openAddUpdatePaymentDiv(el, evt);
    }
    openAdditionalEditPaymentInfo(el, evt){        
        let index = $(self.element).find(".edit-additional-payment").index(el);
        if(self.paymentData != null){
            self.additionalFilteredData = self.returnAdditionalPayment(self.paymentData);
            self.additionalFilteredData = self.paymentData.Cards != null && self.paymentData.Cards.length > 0 ? self.additionalFilteredData[index] : [];
        }else{
            self.additionalFilteredData = [];
        }
        self.additionalFilteredData.index = index;
        self.fetchAddressList(el, evt, index);
        self.openAddUpdatePaymentDiv(el, evt);        
    }    
    inputChange(target) {
        validate.instantValidation(target);
    }
    fetchCardImage(el, evt) {
        let cardType = validate.getCardType($.trim($(el).val()));
        if(cardType != undefined && cardType != ''){
            $(self.element).find(".card-number").attr("class", "form-input medium-input card-number");
            $(self.element).find(".card-number").addClass(cardType);
        }else{
            $(self.element).find(".card-number").attr("class", "form-input medium-input card-number");
        }
    }
    
    fillBillAddressOnChange(el, evt, calledFrom){
        evt.preventDefault();
        let addressId = calledFrom == "onClick" ? (self.addressList.addresses == null || self.addressList.addresses.length == 0 ? '' : self.addressList.addresses[0].addressId) : $(el).val();
        if(self.addressList.addresses.length > 0){
            $.each(self.addressList.addresses, (key,val) => {
                if(val.addressId == addressId){
                    $(self.element).find(".billing-addressLi p:eq(0)").text(val.addressLine[0]+" "+val.addressLine[1]);
                    $(self.element).find(".billing-addressLi p:eq(1)").text(val.city+", "+val.state+" "+val.zipCode);
                    $(self.element).find(".billing-addressLi p:eq(2)").text(val.country);
                }
            });
        }        
    }
    deleteCardDetails(el, evt){        
        let creditCardId = $(el).attr("data-delete");
        const option = apiConfigInst.getApiConfig("paymentInfo").delete;
        evt.preventDefault();
        self.confirmDialog(a => {
            option.data = JSON.stringify({
                "creditCardId": creditCardId,
                "operation": "D",
                "viewName": "restRequest"
            });
            request.ajaxCall(option)
            .then(data => {                
                $(el).parents(".details-list").remove();
                exceptionHandler('success', 'Card Deleted Successfully');
            })
            .catch(error => {
                console.log(error);
                self.errorMessage(error);
            });            
        });
    }
    confirmDialog(onConfirm) {
        const fClose = () => {
            modal.modal("hide");
        };
        let modal = $(self.element).find("#confirmModal");
        modal.modal("show");
        $(self.element).find("#confirmOk").unbind().one('click', onConfirm).one('click', fClose);
        $(self.element).find("#confirmCancel").unbind().one("click", fClose);
    }
    getInputValues(ele,obj) {
        let type = $(ele).attr("type");
        let key = $(ele).attr("data-key");
        switch (type) {
            case "text":
                obj[key] = $(ele).val();
                break; 
            case "number":
                obj[key] = $(ele).val();
                break;
            case "checkbox":
                if(key == "saveDefCard"){
                    obj[key] = $(ele).is(":checked") ? "1" : "0";
                }else{
                    obj[key] = $(ele).is(":checked");
                }                
                break;
            case "radio":
                if($(ele).is(":checked")) {
                    obj[key] = $(ele).val(); 
                }
                break;
            default:
                if($(ele).hasClass("dob-select")) {
                    let dateVal = $(ele).val();
                    let dateStr = dateStr == "" ? dateVal : dateStr+"/"+dateVal;
                    obj[key] = dateStr;
                }else{
                    obj[key] = $(ele).val();
                }                
        }
        return obj;
    }
    getAPIPayload(inputNames, index, isUpdate, cardId) {
        let obj = {},
            operation = "";
        inputNames.each( index => {
            obj = self.getInputValues(inputNames.eq(index), obj);
            operation = isUpdate == true ? "U" : "A";
            obj['operation'] = operation;
            if(isUpdate == true){
                obj['creditCardId'] = cardId;
                obj['defaultChange'] = "";
            }
        });
        return  obj;
    }
    formSubmit(target, evt) {
        evt.preventDefault();        
        let formElement = target.dataset.formname || target.form,
            isUpdate = $(target).hasClass("submit-button") == true ? false : true,
            creditCardId = isUpdate == true ? target.dataset.creditcardid : "";
        validate.checkAllFields(formElement, (output) => {
            if (!output) {
                return;
            }
            let inputNames = $(formElement).find("[data-key]");
            let index = $(target).attr("data-update");
            
            const payload = self.getAPIPayload(inputNames, index, isUpdate, creditCardId);
            const option = apiConfigInst.getApiConfig("paymentInfo").update;
            option.data = JSON.stringify(payload);
            validate.showLoading(formElement);
            request.ajaxCall(option)
            .then(data => {
                validate.hideLoading(formElement);
                self.toggleAddUpdatePaymentDiv(target, evt);
                self.render();
                const errorMessage = data.errorMessage != null && data.errorMessage != undefined ? data.errorMessage : "";
                if(errorMessage == "IO Exception Occured"){
                    exceptionHandler('error', "Invalid card number");
                }else{
                    exceptionHandler('success', 'Card Details Added/Updated Successfully');
                }                
            })
            .catch(error => {
                validate.hideLoading(formElement);
                self.errorMessage(error);
            });
        });        
    };
}

let self;
let eventBindingInst = new eventBinding();
let paymentInfoInstance = new paymentInfo();
let apiConfigInst = new apiConfig();
const config = new apiConfig().getApiConfig;
let request = new ajaxRequest();
let handleBarTemplateInst = new handleBarTemplate();
let validate = new fieldValidation();
let handleBarsHelperInst = new handleBarsHelper();
let covertToString = new JsonArrayToString();
let cardImg = new cardType();
paymentInfoInstance.init();

