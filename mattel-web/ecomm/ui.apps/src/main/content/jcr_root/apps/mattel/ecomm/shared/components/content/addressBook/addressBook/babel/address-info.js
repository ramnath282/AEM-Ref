import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import fieldValidation from '../shared/inputvalidation';
import { handleBarsHelper } from '../shared/handleBarsHelper';
import { handleBarTemplate } from '../shared/templateSetter';
import { countryListFetch } from '../shared/countryListFetch';
import { exceptionHandler } from '../shared/flickerMessage';
import {getCookie} from '../shared/browserCookie';

class addressInfo {
    constructor() {
         self = this;
         this.element = ".address-info-body";
         self.payloadObj = {};         
    }
    init() {
        if(this.element.length) {
            this.render();
        }
        eventBindingInst.bindLooping({
            "click .address-info-body .add-new-address": "showAddressForm",
            "click .address-info-body .details-list-item .edit-address": "showAddressForm",
            "click .address-info-body .cancel-add-info": "collapseFormState",
            "change .address-info-body .select-country select": "onChangeCountry",
            "click .address-info-body #defaultCheck": "toggleDefultAddress",
            "click .address-info-body #addChildCheckbox": "toggleChildBox",
            "keyup .address-info-body .has-error input": "inputChange",
            "change .address-info-body input": "inputChange",
            "click .address-info-body .form-submission": "addressStandaradization",
            "click .address-info-body input[name='addressOption']": "addressType",
            "click .address-info-body .remove-address": "showConfirmModal",
            "click .address-info-body #confirmCancel": "hideConfirmModal",
            "click .address-info-body #confirmOk": "removeAddress",
            "click .address-info-body input[name='shippingOption']": "updateShippingMethod",
            "click .address-link": "submitForm"
        },self);
    }
    render() {
        validate.showLoading(".address-info-body");
        const urlParam = urlName => {
            urlName = urlName.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
            let regexS = "[\\?&]"+urlName+"=([^&#]*)";
            let regex = new RegExp( regexS );
            let results = regex.exec( window.location.href);
            if( results )
                return results[1];
            else
                return results;
        }
        request.ajaxCall(apiConfigInst.getApiConfig("addressBook").get)
        .then(data => {
            this.address = data;
            let shipBillAdd = this.address.addresses.filter(d => d.defaultAddress).filter(d => d.addressType =="ShippingAndBilling");
            if(!shipBillAdd.length) {
                shipBillAdd = this.address.addresses.filter(d => d.defaultAddress).filter(d => d.addressType =="Shipping");
            }
            self.defaultAddData = shipBillAdd.slice();
            if(self.defaultAddData.length){
                self.defaultAddData[0].addressLine.pop();
            }                   
			handleBarsHelperInst.checkIFConditions("ifShareCatalog");
            handleBarTemplateInst.loadTemplate("#defaultAddressTmpl","#defaultAddContainer",self.defaultAddData ,"replace");
            const additonalAddData = this.address.addresses.filter(d => { 
                d.addressLine.pop();                
                return self.defaultAddData.length ?  d.addressId != self.defaultAddData[0].addressId && d.addressType.toLowerCase() !="billing" : d.addressId.toLowerCase() !="billing";
            });
            handleBarTemplateInst.loadTemplate("#additionalAddTmpl","#additionalAddContainer",additonalAddData ,"replace");
            this.countryList = getCountry.fetchCountryList();
            let isUS= true;
            $.each(data.addresses, (key1,val1)=> {
                if(val1.defaultAddress) {
                    if (val1.country!="US"){
                        isUS = false;
                   }
                }
            });
            handleBarsHelperInst.callRegisterHelper("setChecked", {}, {});
            handleBarTemplateInst.loadTemplate("#shippingMethodTmpl","#shippingMethodContainer",{ "shippingMethod" : this.address.defaultShipMethod, "isUS":isUS } ,"replace");
            validate.hideLoading(".address-info-body");
            if(urlParam("defaultOpen")) {
                $("#defaultAddContainer .add-new-address").length ? $("#defaultAddContainer .add-new-address").trigger("click") : $("#defaultAddContainer .edit-address").trigger("click");
            }
        })
        .catch(error => {
            console.log(error);
            validate.hideLoading(".address-info-body");
         })
    }

    updateShippingMethod(el,evt) {
        validate.showLoading(".shipping-method");
        let ajaxOption = apiConfigInst.getApiConfig("shippingMethod").update;
        ajaxOption.data = JSON.stringify({"shipping_modeId": $(el).attr("data-value")});
        request.ajaxCall(ajaxOption)
        .then(data => {
            exceptionHandler("success","Successful!");
            validate.hideLoading(".shipping-method");
            setCommonProp("form-submit", $(".add-add-Btn").val());
        })
        .catch(error => {
            validate.hideLoading(".shipping-method");
            checkForAddressBookError($(".add-add-Btn").val());
         })
    }

    fetchStateList(countryVal){
        let states = [];
        $.each(self.countryList.countries, (key, value) => {
            if(countryVal == value.code){
                states = value.states;
            }
        });
        return states;
    }

    showAddressForm(ele,evt) {
        const addressId = $(ele).attr("data-addressId");
        let defaultCountry = "US";
        let selectedState ="";
        let loadData = [{
            defaultCountrySet: false,
            selectAddressType : false,
            addressLine: ["",""],
            defaultAddress : $(ele).attr("data-default") ? true : false
        }];
        if(addressId) {
            loadData = self.address.addresses.filter(d => {
                return d.addressId == addressId;
            });

            defaultCountry =  loadData[0].country;
            selectedState = loadData[0].state;
            loadData[0].selectAddressType = true;
            loadData[0].addressLine1 = loadData[0].addressLine[0] || "";
            loadData[0].addressLine2 = loadData[0].addressLine[1] || "";
        }
        if(defaultCountry == "US" || defaultCountry == "CA") {
            loadData[0].defaultCountrySet = true;
        }

        let stateList = self.fetchStateList(defaultCountry);
        handleBarsHelperInst.callRegisterHelper("countrySelector",self.countryList.countries, defaultCountry);
        handleBarsHelperInst.callRegisterHelper("stateSelector",stateList, selectedState);
        handleBarsHelperInst.callRegisterHelper("setChecked", {}, {});
        handleBarTemplateInst.loadTemplate("#addressInfoTemp","#updateAddress", loadData[0], "replace");
        if(loadData[0].defaultAddress) {
            let loginUserName = decodeURIComponent(getCookie("MATTEL_WELCOME_MSG")).replace(/\+/g, " ").split(",");
            $(self.element).find("[data-key='firstName']").val(loginUserName[0] || "");
            $(self.element).find("[data-key='lastName']").val(loginUserName[1] || "");
        }
        validate.editFormFields($(self.element).find("#updateAddress"));
        self.expandedFormState(addressId);
        self.addressType("input[name='addressOption']","", loadData[0].organizationUnitName);
        delete loadData[0].defaultCountrySet;
        delete loadData[0].selectAddressType;
        if(loadData[0].defaultAddress) {
            let fname = $(self.element).find("[data-key='firstName']");
            let lname = $(self.element).find("[data-key='lastName']");
            if(fname!=""){
               fname.attr("disabled",true);
            }
            if(lname!=""){
                lname.attr("disabled",true);
            }
        }
        self.payloadObj = loadData[0];
        self.onChangeCountry();
        evt.preventDefault();
    }

    expandedFormState(addressId) {
        if(addressId) {
            $(self.element).find(".add-add-Btn, input.add-new-address").addClass("hide");
            $(self.element).find(".update-add-Btn").removeClass("hide");
        } else {
            $(self.element).find("input.add-new-address, .update-add-Btn").addClass("hide");
        }
        $(self.element).find(".cancel-add-info, #updateAddress").removeClass("hide");
        $(self.element).find(".cancel-add-info").eq(0).focus();
    }

    collapseFormState() {
        $(self.element).find("#updateAddress").empty().addClass("hide");
        $(self.element).find("input.add-new-address").removeClass("hide");
        $(self.element).find(".cancel-add-info").addClass("hide");
    }

    onChangeCountry() {
        const countrySelected = $(self.element).find(".select-country select").val();
        if(countrySelected == "US" || countrySelected == "CA") {
            $(self.element).find(".select-state").parent().addClass("required");
            $(self.element).find(".select-state").removeClass("hide");
            $(self.element).find(".select-state").attr("required", "");
            $(self.element).find(".input-state").addClass("hide");
            $(self.element).find(".input-state").removeAttr("required");
            $(self.element).find("#zipCode").attr("required", "");
            $(self.element).find("#zipCode").parent().addClass("required");

            const stateList = self.fetchStateList(countrySelected);
            $(self.element).find(".select-state option").remove();
            let stateEl=$(self.element).find(".select-state");
            $.each(stateList, (k,v)=>{    
                stateEl
                .append($('<option>', { value : v.code })
                .text(v.displayName));                           
            });        
        } else {
            $(self.element).find(".select-state").parent().removeClass("required has-error");
            $(self.element).find("#defaultStateError").html("");
            $(self.element).find(".select-state").addClass("hide");
            $(self.element).find(".select-state").removeAttr("required");
            $(self.element).find(".input-state").removeClass("hide");
            $(self.element).find(".input-state").removeAttr("required");
            $(self.element).find("#zipCode").removeAttr("required");
            $(self.element).find("#zipCode").parent().removeClass("required has-error");
            $(self.element).find("#zipError").html("");
        }
    }
    toggleDefultAddress(el,evt) {
        $(self.element).find(".defaultCheck-message").toggleClass("hide");
        let loggedUserName = decodeURIComponent(getCookie("MATTEL_WELCOME_MSG")).replace(/\+/g, " ").split(",");
        if($(el).is(":checked")) {
            $(self.element).find("[data-key='firstName']").val(loggedUserName[0] || "").attr("disabled",true);
            $(self.element).find("[data-key='lastName']").val(loggedUserName[1] || "").attr("disabled",true);
            $(self.element).find("#addChildCheckbox,.child-check-label").addClass("hide");
            validate.editFormFields($(self.element).find("#updateAddress"));
        } else {
            $(self.element).find("[data-key='firstName']").removeAttr("disabled",false);
            $(self.element).find("[data-key='lastName']").removeAttr("disabled",false);
            $(self.element).find("#addChildCheckbox,.child-check-label").removeClass("hide");
            if($(self.element).find("#addChildCheckbox").is(":checked")){
                $(self.element).find(".child-check-label").click();
            }
        }
        $(el).focus();
    }

    toggleChildBox(el,evt) {
        $(self.element).find(".defaultCheck-message").addClass("hide");
        if($(el).is(":checked")) {
            if($(self.element).find("#defaultCheck").is(":checked")){
                $(self.element).find(".default-check-label").click();
            }
        }
        $(el).focus();
    }

    inputChange(target) {
        if($(target).attr("id")=="streetName2" && $(target).val().trim() !="") {
            $(target).attr("required",true);
        }else if($(target).attr("id")=="streetName2" && $(target).val().trim() =="") {
            $(target).removeAttr("required").parent().removeClass("has-error");
            $("#streetAddress2Error").html("");
        }
        validate.instantValidation(target);
    };

    addressType(el,evt,val){
        if($(el).is(":checked")) {
            let ele = val ? "Business" : $(el).val();
            if(ele == 'Business') {
                $(self.element).find('.business-name, #businessName').removeClass("hide")
            }  else {
                $(self.element).find('.business-name,#businessName').addClass("hide");
                $(self.element).find('.business-name').removeClass("has-error");
            }
            $(".business-name .help-block").empty();
        }
    }
    addressStandaradization(ele,evt) {
        evt.preventDefault();
        self.beforeSubmit("#updateAddress", function (res, formFields) {
            self.newAdd = $(ele).hasClass("add-add-Btn") ? true : false;

            $("#updateAddress").find("[data-key]").each( i => {
                if(!$("#updateAddress").find("[data-key]").eq(i).hasClass("hide")) {
                     self.getPayload($("#updateAddress").find("[data-key]").eq(i));
                }
            });
            const addStdObj = {
                "addressLine1" : self.payloadObj["addressLine1"],
                "addressLine2" : self.payloadObj["addressLine2"],
                "city" : self.payloadObj["city"],
                "state" : self.payloadObj["state"],
                "zipCode" : self.payloadObj["zipCode"],
                "country" : self.payloadObj["country"],
                "showAdd" : true
            }
            addStdObj["firstName"] = self.payloadObj["firstName"];
            addStdObj["lastName"] = self.payloadObj["lastName"];

            let ajaxOption = apiConfigInst.getApiConfig("addressBook").addStandardization;
            ajaxOption.data = JSON.stringify(addStdObj);
            request.ajaxCall(ajaxOption)
            .then(data => {
            	setCommonProp("form-submit", $(".add-add-Btn").val());
                handleBarTemplateInst.loadTemplate("#addStdTmpl","#user-input", addStdObj, "replace");
                data["firstName"] = self.payloadObj["firstName"];
                data["lastName"] = self.payloadObj["lastName"];
                data["showAdd"] = data.errors ? false : true;
                handleBarTemplateInst.loadTemplate("#addStdTmpl","#usps-input", data, "replace");
                if(data["showAdd"]) {
                    $.each(addStdObj, (k,v)=> {
                        if(data[k]!=v && v){
                            $("#user-input").find(`[data-key=${k}]`).addClass("highlight");
                        }
                     });
                }
                $("#addressStdModal").modal("show");
            })
            .catch(error => {
               console.log(error);
            })

         })
    }
    submitForm(ele, evt) {
        if(!$(ele).hasClass("correct-address"))  {
            if($(ele).hasClass("use-usps-address")) {
                let $modifiedEle = $("#usps-input").find("[data-key]");
                $modifiedEle.each( i => {
                    self.payloadObj[$modifiedEle.eq(i).attr("data-key")] = $modifiedEle.eq(i).text();
                });
            }
            self.payloadObj.addressLine[0] = self.payloadObj.addressLine1;
            self.payloadObj.addressLine[1] = self.payloadObj.addressLine2;
            self.payloadObj.addressLine[2] = "-1";
            self.payloadObj.addressType = "SB";
            self.payloadObj.primary = self.payloadObj.defaultAddress ? 1 : 0;
            self.payloadObj.addressField2 = self.payloadObj.addressField2 || "KeepAddress";
            let ajaxOption;
            if(self.newAdd){
                ajaxOption = apiConfigInst.getApiConfig("addressBook").add;
            } else {
                ajaxOption = apiConfigInst.getApiConfig("addressBook").update;
            }
            ajaxOption["data"] = JSON.stringify(self.payloadObj);
            validate.showLoading(".address-info-body");
            request.ajaxCall(ajaxOption)
            .then(data => {
                exceptionHandler("success","Data updated successfully!");
                self.collapseFormState();
                self.render();
                setCommonProp("form-submit", $(".add-add-Btn").val());
            })
            .catch(error => {
                console.log(error);
                checkForAddressBookError($(".add-add-Btn").val());
                validate.hideLoading(".address-info-body");
            });
            window.history.replaceState(null, null, window.location.pathname);
        }
        $("#addressStdModal").modal("hide");
        evt.preventDefault();
    }

    beforeSubmit(target, cb) {
        let formElement = $(target);
        validate.checkAllFields(formElement,  (output, formFields) => {
            if (!output) {
                checkForAddressBookError($(".add-add-Btn").val());
                return;
            }
            cb(output, target)
        })
    }

    getPayload(ele){
        let type = $(ele).attr("type");
        let key = $(ele).attr("data-key");

        if(type=="checkbox") {
            if(key == "billingCodeType") {
                self.payloadObj[key] = $(ele).is(":checked") ? $(ele).val() : "";
            } else {
                self.payloadObj[key] = $(ele).is(":checked");
            }
        } else {
            self.payloadObj[key] = $(ele).val();
        }
    }

    removeAddress(ele,evt){
        const obj = {
            "nickName" : $(ele).attr("data-nickname") || ""
        }
        $(self.element).find("#confirmModal").modal("hide");
        const ajaxOption = apiConfigInst.getApiConfig("addressBook").delete;
        ajaxOption["data"] = JSON.stringify(obj);
        validate.showLoading(".address-info-body");
        request.ajaxCall(ajaxOption)
        .then(data => {
            exceptionHandler("success","Data updated successfully!");
            self.render();
            setCommonProp("form-submit", $(".add-add-Btn").val());
        })
        .catch(error => {
            console.log(error);
            checkForAddressBookError($(".add-add-Btn").val());
            validate.hideLoading(".address-info-body");
        })
        evt.preventDefault();
    }

    showConfirmModal(el) {
        $(self.element).find("#confirmOk").attr("data-nickname", $(el).attr("data-nickname"));
        $(self.element).find("#confirmModal").modal("show");
    }
    hideConfirmModal() {
        $(self.element).find("#confirmModal").modal("hide");
    }
}

let self;
let eventBindingInst = new eventBinding();
let apiConfigInst = new apiConfig();
let request = new ajaxRequest();
let handleBarTemplateInst = new handleBarTemplate();
let handleBarsHelperInst = new handleBarsHelper();
let validate = new fieldValidation();
let addressInfoInstance = new addressInfo();
let getCountry = new countryListFetch();
addressInfoInstance.init();
