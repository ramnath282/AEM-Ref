const GemCRM = function () {
    self = this;
    this.el = '.gem-wrapper #crm-form';
    this.countryCode = document.getElementById("formCountryCode").value;
    this.sourceId = document.getElementById("formSourceId").value;
    this.locale = document.getElementById("formLocale").value;
    this.pageLocale = document.getElementById("pageLocale").value;
    this.noSubscriptionId = [370];
    this.apiBaseUrl = `${document.getElementById("formApiBaseUrl").value}api/`;
    this.tokenKeyName = 'mrs_token';
    this.belowChildAge = 10;
    this.cookieExpireDay = 1;
    this.cookieExpireDate = new Date().setDate(new Date().getDate() + 1);
    this.existEmail = '';
    this.init();
};

GemCRM.prototype = {
    apiConfig(objName) {
        const obj = {
            "getAPIErrorMessage": {
                "url": `/bin/getErrorMessages.${this.pageLocale}.json`,
                "body": "",
                "type": "get",
                "contentType": 'application/json'
            }
        };
        return obj[objName];
    },
    bindingEventsConfig() {
        const events = {
            "keyup .gem-wrapper #crm-form .input_field": "emailInputFocus",
            "mouseover .gem-wrapper #crm-form .input_field": "emailInputHover",
            "click .gem-wrapper #crm-form #crm-form-submit": "emailFormSubmit",
            "click .gem-wrapper .email_field": "emailPopoverShow",
            "keyup .gem-wrapper .email_field": "emailPopoverShow",
            "click .gem-wrapper .popover-close": "emailSetCloseFlag",
            "click .gem-wrapper": "emailPopoverHide",
            "click .gem-wrapper .oops-popup .close-btn": "emailErrorPopup"
        };
        return events;
    },
    ajaxCall(emailObj, emailCb) {
        $.ajax({
            url: emailObj.url,
            data: emailObj.body || '',
            type: emailObj.type,
            contentType: emailObj.contentType || "application/json",
            beforeSend(xhr) {
                if (emailObj.beforeSend) {
                    xhr.setRequestHeader('Authorization', emailObj.beforeSend);
                }
            },
            success(data) {
                if (typeof emailCb == "function") emailCb(data);
            },
            error(xhr, textStatus, errorThrown) {
                if (typeof emailCb == "function") emailCb(false, xhr.responseJSON || errorThrown);
            }
        });
    },
    emailPopoverShow(ele, evt) {
        $('body').click();
        evt.stopPropagation();
        if (!self.bubbleCloseFlag) {
            $('.popover_window').show();
        }
    },
    emailPopoverHide(ele, evt) {
        $('.popover_window').hide();
    },
    emailErrorPopup(ele, evt) {
        evt.preventDefault();
        $('#errorpopup').modal('hide');
    },
    emailSetCloseFlag(ele, evt) {
        self.bubbleCloseFlag = true;
    },
    emailFormSubmit(elem, evt) {
        const $parentElem = $(elem).parents(".form-action-btn");
        self.showLoading();
        self.validateForm(elem, params => {
            evt.preventDefault();
            if (!window.global.CMFormSubmit) {
                console.log("CM Error: CommonDependency code not found.");
                return;
            }
            if (!params) {
                console.log("field validation error..")
                setTimeout(() => {
                    self.hideLoading()
                }, 500);
                return false;
            }
            window.global.CMFormSubmit(params, (res, data) => {
                self.hideLoading();
                if (res !== "success") {
                    $parentElem.addClass("api-fails");
                } else if(res === "success") {
                    self.apiSuccessForTracking(params);
                    $parentElem.removeClass("api-fails");
                    $(self.el).addClass("form-submitted");
                    $('.form-component').hide();
                    $('.thank-msg').show();
                    $('.thank-msg h2').attr("tabindex", -1).focus();
                    typeof _satellite != "undefined" && _satellite.track("thankYou");
                }
            });
        });
    },
    apiErrorForTracking(errorCode, errorMessage, email) {
        typeof apiGemFormError == "function" && apiGemFormError({
            error_code: errorCode,
            error_message: errorMessage
        }, typeof sha256 == "function" && sha256(email));
    },
    apiSuccessForTracking(params) {
        typeof apiSuccessGemForm == "function" && apiSuccessGemForm({
            customer_email_hash: typeof sha256 == "function" && sha256(params.consumerDetails.EmailAddress),
            subscription_ids: $("#agreeTerms").attr('data-subscription-id')
        })
    },
    displayAPIErrors(errCode, errMsg, isFirstErr, parentElem) {
        const emailApierrorcopy = this.apiMessages && this.apiMessages[errCode];
        switch (errCode) {
            case "1004":
            case "1009":
                this.errorAPIToggle($(".child-dob"), true, emailApierrorcopy);
                if (isFirstErr) $(".child-dob input").focus();
                break;

            case "1010":
            case "1017":
            case "1018":
            case "1019":
            case "1020":
            case "1021":
                $(parentElem).attr('data-content', emailApierrorcopy).addClass("api-fails");
                break;

            case "1022": 
            case "1029":
                self.existEmail = $("#emailId").val();
                this.errorAPIToggle($("#emailId"), true, emailApierrorcopy);
                if (isFirstErr) $("#emailId").focus();
                break;
        }
    },
    clientErrorForTracking(errorName, errorField, email) {
        typeof clientGemFormValidationError == "function" && clientGemFormValidationError({
            error_name: errorName,
            error_field: errorField
        }, typeof sha256 == "function" && sha256(email));
    },
    emailInputFocus(ele, evt) {
        const checkCode = evt.keyCode || evt.which;
        if (checkCode != '9') { // not tab key
            self.validateInput(ele);
        }
    },
    emailInputHover(ele, evt) {
        const formError = $(ele).parent().find(".form-error");
        let emailTitle = $(ele).attr("placeholder");
        const apiError = $(ele).parent().find(".api-error");
        if ($(ele).val() != "") {
            emailTitle = "";
        }
        if (formError.is(":visible")) {
            emailTitle = $(formError).text();
        }
        if (apiError.is(":visible")) {
            emailTitle = $(apiError).text();
        }
        $(ele).attr("title", emailTitle);
    },
    getDeviceType() {
        let checkMobile = false; //initiate as false
        // device detection
        if (/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent) || /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0, 4))) isMobile = true;
        return checkMobile == true ? 'Mobile' : 'Desktop';
    },
    isEmail(email) {
        const regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    },
    addRequiredAttr(inputEle) {
        $(inputEle).attr('required', true);
        $(inputEle).parents('.form-group').addClass('required');
    },
    removeRequiredAttr(inputEle) {
        $(inputEle).attr('required', false);
        $(inputEle).parents('.form-group').removeClass('required');
    },
    showLoading(ele) {
        $(".loading-wrapper").show();
    },
    hideLoading(ele) {
        $(".loading-wrapper").hide();
    },
    errorToggle(elm, isError) {
        const $inputParentElem = elm.parents('.form-group'),
            hasReqAttribute = $(elm).attr('required'),
            errorClassName = "has-error";
        if (isError) {
            $inputParentElem.addClass(errorClassName);
            const inputFieldID = $inputParentElem.find(".form-error").attr("id");
            $inputParentElem.find("input").attr("aria-labelledby", inputFieldID);
            if (elm.val() == "" && typeof hasReqAttribute !== typeof undefined && hasReqAttribute !== false) {
                $inputParentElem.addClass('has-required-error');
            }
        } else {
            $inputParentElem.find("input").removeAttr("aria-labelledby");
            $inputParentElem.removeClass(errorClassName).removeClass('has-api-error has-required-error');
        }
    },
    errorAPIToggle(elm, isError, msgCopy) {
        const $inputParentElemEmail = elm.parents('.form-group'),
            errorClassName = "has-api-error";
        if (isError) {
            if (msgCopy) $inputParentElemEmail.find('.api-error').html(msgCopy);
            $inputParentElemEmail.addClass(errorClassName);
        } else {
            $inputParentElemEmail.removeClass(errorClassName);
        }
    },
    clearElmError(elm) {
        elm.parents('.form-group').removeClass('has-error');
    },
    validateInput(elm) {
        const $elm = $(elm),
            inputType = $elm.attr('type'),
            checkinputVal = $.trim($elm.val()),
            ret = true;
        if (elm.hasAttribute('required') && inputType != "email") {
            let error = false;
            if ($elm.is("#child_dob") && $elm.parents(".has-error").length) {
                error = true;
                this.errorToggle($elm, error);
                return;
            }
            if (checkinputVal == '') {
                error = true;
                this.errorToggle($elm, error);
                return;
            }    
            this.errorToggle($elm, error);
        }
        switch (inputType) {
            case "email":
                this.errorToggle($elm, !this.isEmail(checkinputVal));
                if (self.existEmail) {
                    if (self.existEmail == checkinputVal) {
                        this.errorAPIToggle($elm, true);
                    } else {
                        this.errorAPIToggle($elm, false);
                    }
                }
                break;
            case "text":
                if ($elm.is("#postalCode")) {
                    this.errorToggle($elm, false);
                }
                else if (checkinputVal != '') {
                    if ($elm.is("#child_dob") && $elm.parents(".has-api-error").length) {
                        this.errorAPIToggle($elm, false);
                    } else if (!elm.hasAttribute('required') && !$elm.is("#child_dob")) {
                        this.errorToggle($elm, false);
                    }
                } 
                break;

            default:
                this.clearElmError($elm);
        }
        return ret;
    },
    validateForm(elem, cb) {
        const $formEleEmail = $(elem.form);
        let apiParams = {};
        const isEmailAPIError = $formEleEmail.find('.has-api-error').length;
        let isEmailFormError;

        if (isEmailAPIError) {
            $('html, body').animate({
                scrollTop: ($formEleEmail.find('.has-api-error').last().offset().top)
            }, 'slow');
            $formEleEmail.find('.has-api-error').first().find('input').focus();
            cb(false);
            return;
        }

        _.each($formEleEmail.find(".input_field"), inputEle => {
            self.validateInput(inputEle);
        });
        // prevent form sumbition if has error\
        isEmailFormError = $formEleEmail.find('.has-error').length;
        if (isEmailFormError) {
            $('html, body').animate({
                scrollTop: ($formEleEmail.find('.has-error').first().offset().top)
            }, 'slow');
            $formEleEmail.find('.has-error').first().find('input').focus();
            let errorName = "",
            $errorFieldElement,
            $errorNameElememt,
            errorField = "";
                

            $formEleEmail.find('.has-error').filter(function () {
                $errorNameElememt = $(this).find(".form-error").text();
                errorName = errorName == "" ? $errorNameElememt : `${errorName}|${$errorNameElememt}`;
                $errorFieldElement = $(this).find(".input_field").attr("id");   
                errorField = errorField == "" ? $errorFieldElement : `${errorField}|${$errorFieldElement}`;
            })
            self.clientErrorForTracking(errorName, errorField, $('#emailId').val());
            cb(false);
            return;
        }
        const stateVal = $('#stateField [aria-labelledby="stateDropdown"]').filter(':visible').val() || '';
        const countryCodeVal = $("#countryDropdown").val();
        apiParams = {
            SourceID: this.sourceId || '',
            consumerDetails: {
                EmailAddress: $formEleEmail.find("#emailId").val(),
                MTTLAffiliationInd: "Y",
                ActiveInd: "Y",
                consumerName: {
                    ConsumerTitle: "",
                    ConsumerFirstName: $('#parentFirstName').val() || '',
                    ConsumerLastName: $('#parentLastName').val() || ''
                },
                consumerPhone: {
                    PhoneNumber: $('#contactNumber').val() || ''
                },
                consumerChild: {
                    ChildList: [{
                        ChildGender: '',
                        Relationship: '',
                        ChildDOB: self.dateFormat("#child_dob")
                    }]
                },
                consumerPreferences: self.getPreferences($formEleEmail),
                consumerAddress: {
                    AddressList: countryCodeVal ? [{
                        AddressLine1: $("#streetText").val() || '',
                        AddressLine2: $("#streetText1").val() || '',
                        City: $("#cityField").val() || '',
                        ZipCode: $("#postalCode").val() || '',
                        State: stateVal != "0" ? stateVal : '',
                        Country: countryCodeVal
                    }] : []
                },
                customAttributes: {
                    "AttributeList": [{
                        CustomFieldName: "comments",
                        CustomFieldValue: $('#comment').val() || '',
                        SourceID: this.sourceId || ''
                    }]
                }

            }
        }
        if ($formEleEmail[0].checkValidity()) {
            cb(apiParams)
        } else {
            self.hideLoading();
        }
    },
    dateFormat(ele) {
        const dob = $(ele).val();
        let dobFormatReq = "";
        const dobFormat = $(ele).attr('placeholder');
        let tempDob = '';
        if (dob != "") {
            tempDob = dob.split("/");
        }
        if (dobFormat == "DD/MM/YYYY") {
            dobFormatReq = `${tempDob[2]}-${tempDob[1]}-${tempDob[0]}`;
        } else if (dobFormat == "MM/DD/YYYY") {
            dobFormatReq = `${tempDob[2]}-${tempDob[0]}-${tempDob[1]}`;
        }
        return dobFormatReq;
    },
    getPreferences(ele) {
        const emailLocaleName = (this.pageLocale || "us").toLowerCase();
        const isDoubleOptionEnabledEmail = (emailLocaleName == "de");
        const $emailCheckboxElems = $(ele).find(".checkbox.terms");
        const arr = [];
        let $emailCheckboxElem;
        let emailSubscriptionId;
        let isContainMultipleIdsEmail;

        _.each($emailCheckboxElems, item => {
            $emailCheckboxElem = $(item).find('[type="checkbox"]');
            emailSubscriptionId = $emailCheckboxElem.data("emailSubscriptionId") || $("#nosubscriptionId").val();
            isContainMultipleIdsEmail =
            emailSubscriptionId && emailSubscriptionId.toString().split(",");
            if ($emailCheckboxElem.is(":checked")) {
                for (let i = 0; i < isContainMultipleIdsEmail.length; i++) {
                    if (isContainMultipleIdsEmail[i]) {
                        if (isDoubleOptionEnabledEmail) {
                            arr.push({
                                PreferenceID: isContainMultipleIdsEmail[i],
                                PreferenceOptIn: "N",
                                DoubleOptInStatusCD: "V"
                            });
                        } else {
                            arr.push({
                                PreferenceID: isContainMultipleIdsEmail[i],
                                PreferenceOptIn: "Y"
                            });
                        }
                    }
                }
            }
        });
        return {
            PreferenceList: arr
        };
    },
    bindDatePicker(hideFutureDate, triggerChange) {
        const $el = hideFutureDate != undefined ? $(".date-picker") : $(".date-picker:not('.active')");
        const todayDate = new Date().getFullYear();
        if (!$el.length) return false;
        if (hideFutureDate != undefined) {
            $el.datepicker('destroy');
        }
        const dateBinded = $el.datepicker({
            //endDate: hideFutureDate ? false : '+0d',
            endDate: new Date(new Date().setFullYear(todayDate + 1)),
            startDate: new Date(new Date().setFullYear(todayDate - 6)),
            container: $el,
            //startDate: new Date(startYear+'-01-01'), //set start date
            autoclose: true,
            forceParse: false
        }).addClass('active');
        if (triggerChange) dateBinded.trigger("change");
        return dateBinded;
    },
    loadAPIerrormessage() {
        const emailAjaxSettings = self.apiConfig("getAPIErrorMessage");
        self.ajaxCall(emailAjaxSettings, (res, err) => {
            if (!res) {
                console.log(`Email API error message data fails..error is ${err}`);
                return;
            }
            self.apiMessages = res;
        });
    },
    loadAxChanges() {
        const $emailTargetTextAddedinHref = $('[data-href-target]');
        if (!$emailTargetTextAddedinHref.length || !$emailTargetTextAddedinHref.find("a[target='_blank']").length) return;
        $emailTargetTextAddedinHref.find("a[target='_blank']").append(`<span class='sr-only'>${$$emailTargetTextAddedinHref.data('hrefTarget')}</span>`);
    },
    checkGemFormHasDataTracking() {
        const $form = $(this.el);
        const $inputField = $form.find("input[type='text'], input[type='email']").filter(function () {
            return $(this).val() != "" && $(this)
        });
        const obj = {};
        if ($inputField.length) {
            obj.type = "browser close";
            obj.last_accessed_field = $inputField[$inputField.length - 1].getAttribute("id") || $inputField[$inputField.length - 1].getAttribute("name");
            typeof checkGemFormHasData == "function" && checkGemFormHasData(obj);
            // fn(obj);
            return true;
        } else {
            return false;
        }
    },
    dobValidation(ele, curElem) {
        const $dobEle = $(ele.currentTarget).find('input'),
            options = $(curElem).data('datepicker').o,
            endDate = options.endDate,
            datearray = options.format.toLowerCase() == "dd/mm/yyyy" ? $dobEle.val().split("/") : false,
            newdate = datearray && `${datearray[1]}/${datearray[0]}/${datearray[2]}` || $dobEle.val(),
            val = newdate;
        // if(!isNaN(val) && val.length<4) return;
        if (val != "" && (String(new Date(val)) == "Invalid Date") || (new Date(endDate) != "Invalid Date" && (+new Date(val) > +new Date(endDate)))) {
            self.errorToggle($dobEle, true);
            $(curElem).datepicker('hide');
        } else {
            self.errorToggle($dobEle, false);
        }
    },
    render() {
        const datebinding = this.bindDatePicker(true);
        datebinding && datebinding
            .keyup(function (ele) {
                self.dobValidation(ele, this)
            }).change(function (ele) {
                self.dobValidation(ele, this)
            });
        this.loadAPIerrormessage();
        this.loadAxChanges();
    },
    init() {
        if (!$(this.el).length) return;
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.render();
        if ($(".gem-wrapper").length) {
            setTimeout(() => {
                $("iframe").remove();
            }, 5000);
        }
    }
};

let self;
const evtBinding = window.global.eventBindingInst;
const gemCRMInit = new GemCRM();

window.addEventListener("beforeunload", e => {
    if (!$(gemCRMInit.el).hasClass("form-submitted") && gemCRMInit.checkGemFormHasDataTracking()) {
        const confirmationMessage = "\o/";

        (e || window.event).returnValue = confirmationMessage;
        return confirmationMessage;
    }
});