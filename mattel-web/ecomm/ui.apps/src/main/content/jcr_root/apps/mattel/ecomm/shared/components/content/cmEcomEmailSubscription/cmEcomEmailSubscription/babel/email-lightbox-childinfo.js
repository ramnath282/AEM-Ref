import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import fieldValidation from '../shared/inputvalidation';
import { getCookie, setCookie } from '../shared/browserCookie';
import { excludeSupportObj } from "../shared/excludeSupportiveFunction";

const cookieConfig = {
    cookieCount: "email-lightboxpopup-extension-value",
    cookieExcludekeyword: "email-lightboxpopup-excludekeyword",
    excludepage: "email-lightboxpopup-excludepage",
    coppaElbDelayCookie: "coppa-elb-delay",
    iscoppaTimer: true
};

class emailLightboxpopup {
    constructor() {
        self = this;
        this.el = ".email-lightboxpopup-extension-comp";
        self.payloadObj = {};
        self.formCount = 0;
    }
    init() {
        $(document).ready(function() {
            eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
            self.render();
            if (!$(".entry-modal-container").is(":visible")) {
                cookieConfig.iscoppaTimer = false;
                self.initELBModal();
            }
        });
    }
    bindingEventsConfig() {
        let eventsArr = {
            "change .email-lightboxpopup-extension-comp .dob-select": "adjustDays",
            "keyup .email-lightboxpopup-extension-comp .has-error input": "inputChange",
            "change .email-lightboxpopup-extension-comp .input-field input, .email-lightboxpopup-extension-comp .input-field select": "inputChange",
            "click #emailLightboxForm .email-signup-account": "submitForm",
            "click .email-lightboxpopup-extension-comp .remove-child": "removeChildElement",
            "click .email-lightboxpopup-extension-comp .addChildCont ": "addMore",
            "click .email-lightboxpopup-extension-comp #emailLightboxForm input, .email-lightboxpopup-extension-comp #emailLightboxForm select": "validateInputFields",
            "keyup .email-lightboxpopup-extension-comp #emailLightboxForm input": "validateInputFields",
            "click .email-lightboxpopup-extension-comp  #emailLightboxForm input, .email-lightboxpopup-extension-comp  #emailLightboxForm select ": "resetFieldsValidation",
            "click .signup-model-popup": "showSignupLightBox",
            "click .popupEmailSignUp": "showSignupLightBox",
            "blur .email-lightboxpopup-extension-comp  #emailLightboxForm select": "removeFocusArrow",
            "hidden.bs.modal .email-lightboxpopup-extension-comp  #emailLightBoxModal": "closeModal",
            "click .email-lightboxpopup-extension-comp #closeInfoDiv": "closeFormSubmitMsg",
            "click .shop-section .theme-btn": "initELBModal"
        };
        return eventsArr;
    }
    render() {
        const monthNames = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];
        let currentYear = new Date().getFullYear();
        let yearList = "<option value=''></option>";
        for (let y = 0; y < 50; y++) {
            yearList += "<option value=" + currentYear + ">" + currentYear + "</option>";
            currentYear--;
        }
        $(".email-lightboxpopup-comp:not(.email-lightbox-form)").find("#year").html("").append(yearList);
        $(".email-lightbox-form").find("#year").html("").append(yearList);

        let monthList = "<option value=''></option>";
        for (let m = 0; m < monthNames.length; m++) {
            let strnum = (m < 10 ? "0" : "") + (m + 1);
            monthList += "<option value=" + strnum + ">" + monthNames[m] + "</option>";
        }
        $(".email-lightboxpopup-comp:not(.email-lightbox-form)").find("#month").html("").append(monthList);
        $(".email-lightbox-form").find("#month").html("").append(monthList);

        let dayList = "<option value=''></option>";
        for (let i = 1; i <= 31; i++) {
            let strnum = (i < 10 ? "0" : "") + i;
            dayList += "<option value=" + strnum + ">" + i + "</option>";
        }
        $(".email-lightboxpopup-comp:not(.email-lightbox-form)").find("#day").html("").append(dayList);
        $(".email-lightbox-form").find("#day").html("").append(dayList);
    }
    validateInputFields(ele, event) {
        let StatusOne = true;
        let StatusTwo = true;
        let StatusThree = true;
        $(".email-lightboxpopup-extension-comp #emailLightboxForm").find('input[type="email"], input[type="text"], input[type="radio"], select').each(function(i, item) {
            if (item.tagName == "INPUT" && item.type == "radio") {
                if (!$('[data-attr="childOption"]:checked').length || !$('[data-attr="relationship"]:checked').length) {
                    $(".email-signup-account").removeClass("signup-btnColor ")
                    StatusOne = false;
                    return false;
                }
            } else if (item.tagName == "INPUT") {
                if (!$(item).val()) {
                    $(".email-signup-account").removeClass("signup-btnColor ")
                    StatusTwo = false;
                    return false;
                }
            } else {
                if (!$(item).val()) {
                    $(".email-signup-account").removeClass("signup-btnColor ")
                    StatusThree = false;
                    return false;
                }
            }
            if (StatusOne && StatusTwo && StatusThree) {
                $(".email-signup-account").addClass("signup-btnColor ")
            }
        });
    }
    showSignupLightBox(ele) {
        let emailModalCookie = getCookie(cookieConfig.cookieCount).trim() == "" ? 0 : getCookie(cookieConfig.cookieCount).trim();
        emailModalCookie++;
        self.resetEmailForm();
        if ($(ele).hasClass("signup-model-popup")) {
            $('html, body').animate({
                'scrollTop': $(".global-footer").position().top
            });
        }
        $("#emailLightboxForm").find(".child-info-heading h2, .addChildCont, .child-info-text, .child-info-heading .privacy-statement").removeClass("hide");
        $(".email-lightboxpopup-extension-comp:not(.email-lightbox-form) #emailLightBoxModal").modal("show");
        $(".header-spark-menu").removeClass('active');
        setCookie(cookieConfig.cookieCount, parseInt(emailModalCookie), 30);
        if ($(ele).hasClass("popupEmailSignUp")) {
            $(".email-lightboxpopup-extension-comp:not(.email-lightbox-form)").attr("data-modal-type", "hamburger-modal");
        } else {
            $(".email-lightboxpopup-extension-comp:not(.email-lightbox-form)").attr("data-modal-type", "footer-modal");
        }
    }
    resetEmailForm() {
        //reset for email popup
        self.formCount = 0;
        $(".email-lightboxpopup-extension-comp #emailLightboxForm").find(".has-error").find(".help-block.form-message").html('');
        $(".email-lightboxpopup-extension-comp #emailLightboxForm").find(".has-error").removeClass("has-error");
        $(".email-lightboxpopup-extension-comp #emailLightboxForm").find("input, select").val("");
        $(".email-lightboxpopup-extension-comp #emailLightboxForm").find("input").removeClass("not-empty");
        $(".email-lightboxpopup-extension-comp #emailLightboxForm").find(".result_cont").html("");
        $(".email-lightboxpopup-extension-comp #emailLightboxForm").find(".email-signup-account").removeAttr('id');

        //reset for thank you popup
        $(".thankyou-modal .display_error_title, .thankyou-modal .display_error_msg").hide();
        $(".thankyou-modal .display_success_title, .thankyou-modal .display_success_msg").hide();
    }
    inputChange(target) {
        validate.instantValidation(target);
    }
    resetFieldsValidation(ele) {
        $(ele).closest(".has-error").find(".help-block.form-message").html('');
        $(ele).closest(".has-error").removeClass("has-error");
        if (ele.type == "radio") {
            $(ele).closest(".input-field.child-data").find(".custom-error-message").html('');
        } else if (ele.type == "select-one") {
            $(ele).closest("ul").find(".dateErrCls").removeClass("dateErrCls");
            $(ele).closest("ul").find(".dateInvalidCont").hide();
        }

        if ($(ele).focus()) {
            $(ele).parent().addClass("focus-arrow");
        }
    }
    removeFocusArrow(ele, evt) {
        $(ele).parent().removeClass("focus-arrow");
    }
    changeMonth(ele) {
        let year = $(ele).find(".yearCont").val();
        let month = $(ele).find(".monthCont").val();
        let day = $(ele).find(".dayCont").val();
        year = year ? year : new Date().getFullYear();
        month = month ? month : new Date().getMonth();
        day = day ? day : new Date().getDate();
        year = parseInt(year, 10);
        month = parseInt(month, 10);
        day = parseInt(day, 10);
        var date = new Date(year, month - 1, day);
        let dateStatus = date.getFullYear() == year && date.getMonth() == (month - 1) && date.getDate() == day;
        if (!dateStatus) {
            validate.errorToggle($(ele).find(".select-picker-wrapper"), null, "ERROR_InvalidDate1");
        }
    }
    beforeSubmit(target, cb) {
        let formElement = $(target);

        validate.checkAllFields(formElement, function(output, formFields) {
            if (!output) {
                return;
            }
            cb(output, target);
        });
    }
    submitForm(ele, evt) {
        evt.preventDefault();
        let form = $(ele).closest("form");
        for (let i = 0; i < $(form).find(".child-info-form").length; i++) {
            let element = $(form).find(".child-info-form")[i];
            self.changeMonth(element);
        }

        self.beforeSubmit(form, function(res, formFields) {
            let len = $(form).find('.has-error , .dateErrCls').length;
            if (len == 0) {
                $(form).find(".email-signup-account").addClass("signup-btnColor")
                self.submitEmailPopup(ele, evt);
            }
        });
    }
    removeChildElement(ele, evt) {
        let childWrapperObj = $(ele).parents("#child-details-wrapper");
        $(ele).closest(".child-info-form").remove();
        self.formCount--;
        childWrapperObj.find(".child-info-form").each((k, v) => {
            let index = k + 1;
            let formInputs = $(v)[0].querySelectorAll('select,input:not([type="hidden"]):not(.hide)');
            for (let i = 0; i < formInputs.length; i++) {
                formInputs[i].id = formInputs[i].id.split("-")[0] + "-" + index
                formInputs[i].name = formInputs[i].name.split("-")[0] + "-" + index
            }
            let lable = $(v)[0].querySelectorAll('label');
            for (let i = 0; i < lable.length; i++) {
                lable[i].htmlFor = lable[i].htmlFor.split("-")[0] + "-" + index
            }
        });
        evt.preventDefault();
    }
    addMore(ele, evt) {
        let form = $(ele).closest("form");
        $(".email-signup-account").removeClass("signup-btnColor");
        self.beforeSubmit(form, function(res, formFields) {
            let lastIndex = $(ele).parents(".form-component").find(".result_cont .child-info-form:last").index();
            self.formCount = lastIndex + 2;
            let currentEle = $(ele).parents(".form-component").find(".email_form_cont .child-info-form").clone();
            let formInputs = $(currentEle)[0].querySelectorAll('select,input:not([type="hidden"]):not(.hide)');
            for (let i = 0; i < formInputs.length; i++) {
                formInputs[i].id = formInputs[i].id + "-" + self.formCount
                formInputs[i].name = formInputs[i].name + "-" + self.formCount
            }
            $(currentEle).find('input[type="text"]').val('');
            let lable = $(currentEle)[0].querySelectorAll('label');
            for (let i = 0; i < lable.length; i++) {
                lable[i].htmlFor = lable[i].htmlFor + "-" + self.formCount
            }
            $(ele).parents(".form-component").find(".result_cont").append($(currentEle));
        });
        evt.preventDefault();
    }
    adjustDays(ele, evt) {
        let currentCont = $(ele).closest('.child-info-form');
        let day = currentCont.find(".dayCont").val() ? currentCont.find(".dayCont").val() : "00";
        let month = currentCont.find(".monthCont").val() ? currentCont.find(".monthCont").val() : "00";
        let year = currentCont.find(".yearCont").val() ? currentCont.find(".yearCont").val() : "0000";
        currentCont.find(".mbsc-comp").val(day + "/" + month + "/" + year);
    }
    closeModal(ele, evt) {
        $(".outer-wrapper").removeClass("email_light_box_overlay");
        $(".modal-backdrop.in").hide();
        $("body").removeClass("hamburger-on");
    }
    initELBModal() {
        let modalPopup = $(".email-lightboxpopup-comp[data-is-modal='true']");
        let formModal = $(".email-lightboxpopup-extension-comp[data-is-modal='false']");
        let emailModalShowVal = modalPopup.length > 0 ? modalPopup.find("#elbModalDetails").attr("data-modal-value").trim() : 0;
        let emailModalCookie = getCookie(cookieConfig.cookieCount).trim() == "" ? 0 : getCookie(cookieConfig.cookieCount).trim();

        let isExcludeKeyword = excludeSupportObj.checkExcludeKeywordAsParameter(modalPopup);
        let isExcludepage = false;
        if (excludeSupportObj.checkExcludePages(modalPopup) || excludeSupportObj.checkExcludeKeywordAsPageName(modalPopup)) {
            isExcludepage = true;
        }
        let isCoppaElbDelay = cookieConfig.iscoppaTimer ? $('#emailLightBoxModal').attr('data-emailsignupdelay') || 500 : 0;

        formModal.find("#emailLightBoxModal").modal("show");
        formModal.find("#emailLightBoxModal").css("overflow", "auto");
        let promoDrawerHeight = 0;
        $(".email-lightbox-form").find(".modal-content").css("margin-top", (parseInt(promoDrawerHeight) + 50));
        if (isExcludepage == false && isExcludeKeyword == false) {
            if (parseInt(emailModalCookie) < parseInt(emailModalShowVal)) {
                if (modalPopup.length > 0) {

                    setTimeout(() => {
                        $(".email-lightboxpopup-extension-comp[data-is-modal='true'] #emailLightBoxModal").modal("show");
                        $(".email-lightboxpopup-extension-comp[data-is-modal='true'] #emailLightboxForm").find(".child-info-heading h2, .addChildCont, .child-info-text, .child-info-heading .privacy-statement").addClass("hide");
                        $(".email-lightboxpopup-extension-comp:not(.email-lightbox-form)").attr("data-modal-type", "onload-modal");
                        emailModalCookie++;
                        setCookie(cookieConfig.cookieCount, parseInt(emailModalCookie), 30);
                    }, isCoppaElbDelay);
                }
            }
        }
        setTimeout(() => {
            if (modalPopup.find("#emailLightBoxModal:visible").length == 0 && formModal.find("#emailLightBoxModal:visible").length == 1) {
                formModal.parents("body").find(".modal-backdrop.in").hide();
                formModal.parents("body").removeClass("modal-open");
            }
        }, 200);
    }
    getParameterByName(name) {
        let url = window.location.href;
        name = name.replace(/[\[\]]/g, '\\$&');
        let regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
            results = regex.exec(url);
        if (!results) return null;
        if (!results[2]) return '';
        return decodeURIComponent(results[2].replace(/\+/g, ' '));
    }
    checkExcludeKeywordAsParameter() {
        // let urlParams = new URLSearchParams(window.location.search),
        let excludeKeywords = $(".email-lightboxpopup-comp[data-is-modal='true']").find("#excludekeywords").val();
        let excludeKeyArr = excludeKeywords != "" && excludeKeywords != undefined ? excludeKeywords.split(",") : [];
        let flag = false;

        if (excludeKeyArr.length > 0) {
            for (let i = 0; i < excludeKeyArr.length; i++) {
                if (self.getParameterByName(excludeKeyArr[i]) != null) {
                    flag = true;
                }
            }
        }
        return flag;
    }
    checkExcludeKeywordAsPageName() {
        let currentPage = location.pathname.split('/').slice(-1)[0],
            excludeKeywords = $(".email-lightboxpopup-comp[data-is-modal='true']").find("#excludekeywords").val();
        let excludeKeyArr = excludeKeywords != "" && excludeKeywords != undefined ? excludeKeywords.split(",") : [];
        let flag = false;
        if (currentPage != "" && excludeKeyArr.length > 0) {
            for (let i = 0; i < excludeKeyArr.length; i++) {
                if (currentPage.indexOf(excludeKeyArr[i]) > -1) {
                    flag = true;
                }
            }
        }
        return flag;
    }
    checkExcludePages() {
        let urlPath = window.location.pathname,
            excludePages = $(".email-lightboxpopup-comp[data-is-modal='true']").find("#excludePages").val();
        let excludePageArr = excludePages != "" && excludePages != undefined ? excludePages.split(",") : [];
        let flag = false;

        if (urlPath != "" && excludePageArr.length > 0) {
            for (let i = 0; i < excludePageArr.length; i++) {
                if (urlPath.indexOf(excludePageArr[i]) > -1) {
                    flag = true;
                }
            }
        }
        return flag;
    }
    getInputValues(ele, obj) {
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
                obj[key] = $(ele).is(":checked");
                break;
            case "radio":
                if ($(ele).is(":checked")) {
                    obj[key] = $(ele).val();
                }
                break;
            default:
                if ($(ele).hasClass("dob-select")) {
                    let dateVal = $(ele).val();
                    dateStr = dateStr == "" ? dateVal : dateStr + "/" + dateVal;
                    obj[key] = dateStr;
                } else {
                    obj[key] = $(ele).val();
                }
        }
        return obj;
    }
    getChildInfoPayload(inputNames) {
        let obj = {};
        inputNames.each(index => {
            obj = self.getInputValues(inputNames.eq(index), obj);
        });
        dateStr = "";
        return obj;
    }
    submitEmailPopup(ele, evt) {
        let currElement = $(ele).closest(".email-lightboxpopup-extension-comp");
        let emailLightboxData = {},
            ConsumerDetails = {},
            ConsumerName = {},
            ConsumerPhone = {},
            ConsumerChild = {},
            ConsumerAddress = {},
            CustomAttributes = {};
        let modalType = currElement.attr("data-modal-type").trim();
        let emailAddress = currElement.find("#emailId").val().trim();
        let sourceId = "";
        if (modalType == "form") {
            sourceId = currElement.find("#sourceId").attr("data-default-source-id").trim();
        } else if (modalType == "footer-modal") {
            sourceId = currElement.find("#sourceId").attr("data-footerversion-source-id").trim();
        } else if (modalType == "hamburger-modal") {
            sourceId = currElement.find("#sourceId").attr("data-hamburgerversion-source-id").trim();
        } else {
            sourceId = currElement.find("#sourceId").attr("data-onloadversion-source-id").trim();
        }

        emailLightboxData.SourceID = sourceId;


        //ConsumerDetails.ConsumerBirthDate = "";
        ConsumerName.ConsumerFirstName = "";
        ConsumerName.ConsumerLastName = "";
        ConsumerName.ConsumerTitle = "";
        ConsumerPhone.PhoneNumber = "";


        let childArr = [],
            childObj = {};
        currElement.find(".child-info-form:visible").each((k, v) => {
            let element = $(v).find('[data-key]');
            childObj = self.getChildInfoPayload(element);
            let childDob = childObj.ChildDOB;
            childDob = childDob.split("/")[2] + "-" + childDob.split("/")[0] + "-" + childDob.split("/")[1];
            childObj.ChildDOB = childDob;
            childArr.push(childObj);
        });
        console.log(childArr);


        let countryCode = currElement.find("#emaillightboxLocale").attr("data-country-locale").trim();
        let addressListArr = [],
            addressListObj = {};
        addressListObj.AddressFirstName = "";
        addressListObj.AddressLastName = "";
        addressListObj.AddressLine1 = "";
        addressListObj.AddressLine2 = "";
        addressListObj.City = "";
        addressListObj.Country = "";
        addressListObj.State = "";
        addressListObj.ZipCode = "";
        addressListArr.push(addressListObj);
        ConsumerAddress.AddressList = [];


        let attributeListArr = [],
            attributeListObj = {};
        attributeListObj.CustomFieldName = "";
        attributeListObj.CustomFieldValue = "";
        attributeListArr.push(attributeListObj);
        CustomAttributes.AttributeList = attributeListArr;

        //**********Setting the Data********************
        emailLightboxData.consumerDetails = ConsumerDetails;
        ConsumerDetails.consumerAddress = (ConsumerAddress.length > 0) ? ConsumerAddress : void(0);
        ConsumerDetails.consumerPhone = (ConsumerPhone.length > 0) ? ConsumerPhone : void(0);
        ConsumerDetails.customAttributes = (CustomAttributes.length > 0) ? CustomAttributes : void(0);
        ConsumerDetails.EmailAddress = (emailAddress.length > 0) ? emailAddress : void(0);
        ConsumerDetails.consumerName = (ConsumerName.length > 0) ? ConsumerName : void(0);
        ConsumerChild.ChildList = (childArr.length > 0) ? childArr : void(0);
        ConsumerDetails.consumerChild = (childArr.length > 0) ? ConsumerChild : void(0);
		
		ConsumerDetails.AGAffiliationInd = "Y";
        ConsumerDetails.GlobalOptInInd = "Y";

        $(self.el).addClass("request-pending");
        let $thankYouModal = modalType == "form" ? $(".email-lightboxpopup-comp[data-is-modal='false'] #formSubmitMsg") : $("#myThankyou"),
            $successDiv = $thankYouModal.find(".display-success-title, .display-success-msg"),
            $errorDiv = $thankYouModal.find(".display-error-title, .display-error-msg");
        let emailModalCookie = $(".email-lightboxpopup-comp[data-is-modal='true']").find("#elbModalDetails").attr("data-modal-value");
        // let option = apiConfigInst.getApiConfig("submitEmailLightbox");
        // option.data = JSON.stringify(emailLightboxData);
        if (!window.global.CMFormSubmit) {
            console.log("CM Error: CommonDependency code not found.");
            return;
        }
        window.global.CMFormSubmit(emailLightboxData, (res, data) => {
            if (res === "success") {
                $(self.el).removeClass("request-pending");
                if (modalType != "form") {
                    $(".email-lightboxpopup-extension-comp:not(.email-lightbox-form) #emailLightBoxModal").modal("hide");
                    $thankYouModal.modal("show");
                }
                let statusCode = data.Status.statusCode,
                    message = data.Status.message;
                $(".email-lightboxpopup-comp[data-is-modal='true']").find("#elbModalDetails").attr("data-status-code", statusCode);
                if (statusCode == 200 || statusCode == 201) {
                    setCookie(cookieConfig.cookieCount, parseInt(emailModalCookie), 30);
                    if (modalType == "form") {
                        currElement.find(".modal-content").addClass("hide");
                        currElement.find("#formSubmitMsg").removeClass("hide");
                    }
                    $successDiv.removeClass("hide");
                    $errorDiv.addClass("hide");
                } else if (statusCode == 1004) {
                    setCookie(cookieConfig.cookieCount, parseInt(emailModalCookie), 30);
                    if (modalType == "form") {
                        currElement.find(".modal-content").addClass("hide");
                        currElement.find("#formSubmitMsg").removeClass("hide");
                    }
                    $successDiv.addClass("hide");
                    $errorDiv.removeClass("hide");
                    $thankYouModal.find(".display-error-msg").html(message);
                } else {
                    if (modalType == "form") {
                        currElement.find(".modal-content").addClass("hide");
                        currElement.find("#formSubmitMsg").removeClass("hide");
                    }
                    $successDiv.addClass("hide");
                    $errorDiv.removeClass("hide");
                }
            } else {
                if (modalType == "form") {
                    currElement.find(".modal-content").addClass("hide");
                    currElement.find("#formSubmitMsg").removeClass("hide");
                }
                $successDiv.addClass("hide");
                $errorDiv.removeClass("hide");
                $(self.el).removeClass("request-pending");
            }
        });
        // request.ajaxCall(option) 
        //     .then(data => {

        //     })
        //     .catch(error => {
        //         console.log(error);

        //     });

    }
    closeFormSubmitMsg(ele, evt) {
        let currElement = $(ele).closest(".email-lightboxpopup-extension-comp");
        currElement.find(".modal-content").removeClass("hide");
        currElement.find("#formSubmitMsg").addClass("hide");
        currElement.find("#child-details-wrapper").empty();
        currElement.find("#emailId").removeClass("not-empty");
        currElement.find("#emailId").val("").focus();
    }
}

let self;
let dateStr = "";
const request = new ajaxRequest();
const apiConfigInst = new apiConfig();
const validate = new fieldValidation();
let eventBindingInst = new eventBinding();
let emailLightboxpopupInstance = new emailLightboxpopup();
emailLightboxpopupInstance.init();