import fieldValidation from "../shared/inputvalidation";

const apiStatusConfig = () => {
    return {
        url: `/bin/getErrorMessages.${$("#crmConfig").data('pageLocale') || "en-us"}.json`,
        body: "",
        type: "get",
        contentType: "application/json"
    };
};
const CRMCONFIG = form => {
    return new payload(form);
};

const payload = function(form) {
    formNamesArr = getUniqueFormNames(form);
    this.formVal = form;
    return {
        SourceID: $("#crmConfig").data('sourceId') || configError("Source Id"),
        consumerDetails: {
            EmailAddress: this.email(),
            MTTLAffiliationInd: "Y",
            ActiveInd: "Y",
            consumerName: this.name(),
            consumerPhone: this.phone(),
            consumerChild: this.child(),
            consumerPreferences: this.preferences(),
            consumerAddress: this.address(),
            customAttributes: this.customAttributes()
        },
        EmailTriggerDetails: this.emailConfig()
    };
};

payload.prototype = {
    email() {
        return this.returnFormValue("emailId");
    },
    name() {
        const firstName = this.returnFormValue("parentFirstName"),
            lastName = this.returnFormValue("parentLastName");
        return firstName == null && lastName == null ? null : {
            ConsumerFirstName: firstName || '',
            ConsumerLastName: lastName || ''
        };
    },
    phone() {
        const phoneNumber = this.returnFormValue("PhoneNumber");
        return phoneNumber == null ? null : {
            PhoneNumber: phoneNumber
        };
    },
    child() {
        let $childElem = $(this.formVal).find(".child-information");
        let arr = [];
        _.each($childElem, item => {
            if (this.returnFormValue($(item).find(".date-picker input").attr("name")) != null) {
                arr.push(Object.fromEntries(Object.entries({
                    ChildDOB: this.formatDate(this.returnFormValue($(item).find(".date-picker input").attr("name"))),
                    ChildGender: this.returnFormValue(
                        $(item)
                        .find(".gender input")
                        .attr("name")
                    ),
                    Relationship: this.returnFormValue(
                        $(item)
                        .find(".relationship input")
                        .attr("name")
                    )
                }).filter(([_, v]) => v != null)));
            }
        });
        if (_.isEmpty(arr[0])) {
            return null;
        }
        const childObj = this.changeRelationVal(arr);
        return {
            ChildList: childObj
        };
    },
    changeRelationVal(arr) {
        for (let i = 0; i < arr.length; i++) {
            const gender = $(this.formVal).find(".child-information .gender").eq(i).find("input:checked");
            const relation = $(this.formVal).find(".child-information .relationship").eq(i).find("input:checked");
            if (gender && gender.length) arr[i].ChildGender  =  gender.val();
            if (relation && relation.length) arr[i].Relationship  =  relation.val();
            switch (arr[i].Relationship) {
                case "grandParent":
                    arr[i].Relationship = "GRANDPARENT";
                    break;
                case "mom":
                    arr[i].Relationship = "MOTHER";
                    break;
                case "dad":
                    arr[i].Relationship = "FATHER";
                    break;
                default:
                    if (arr[i].Relationship != undefined) arr[i].Relationship = "FRIEND / FAMILY MEMBER";
            }
        }
        return arr;
    },
    preferences() {

        let localeName = ($("#crmConfig").data('pageLocale') || "us").toLowerCase(),
            isDoubleOptinEnabled = (localeName == "de"),
            $checkboxElems = $(this.formVal).find(".preferences-checkbox"),
            arr = [],
            $checkboxElem,
            subscriptionId,
            isContainMultipleIds;

        _.each($checkboxElems, function(item) {
            $checkboxElem = $(item);
            subscriptionId = $checkboxElem.data("subscriptionId") || $("#crmConfig").data('nosubscriptionId');
            isContainMultipleIds =
                subscriptionId && subscriptionId.toString().split(",");
            if ($checkboxElem.is(":checked")) {

                formNamesArr = _.without(formNamesArr, item.name);
                for (let i = 0; i < isContainMultipleIds.length; i++) {
                    if (isDoubleOptinEnabled) {
                        arr.push({
                            PreferenceID: isContainMultipleIds[i],
                            PreferenceOptIn: "N",
                            DoubleOptInStatusCD: "V"
                        });
                    } else {
                        arr.push({
                            PreferenceID: isContainMultipleIds[i],
                            PreferenceOptIn: "Y"
                        });
                    }
                }
            }
        });
        return _.isEmpty(arr) ? null : {
            PreferenceList: arr
        };
    },
    address() {
        let countryCode = this.returnFormValue("country");
        const addressObj = [Object.fromEntries(Object.entries({
                AddressLine1: this.returnFormValue("address"),
                AddressLine2: this.returnFormValue("apartment"),
                City: this.returnFormValue("city"),
                ZipCode: this.returnFormValue("zipCode"),
                State: this.returnFormValue("state"),
                Country: countryCode
            }).filter(([_, v]) => v != null))
            ];
        return _.isEmpty(addressObj[0]) ? null : { AddressList : addressObj };
    },

    customAttributes() {
        const arr = [];
        const formVal = this.formVal;

        _.each(formNamesArr, function(item) {
            if (formVal[item].value) {
                arr.push({
                    CustomFieldName: item,
                    CustomFieldValue: formVal[item].value,
                    SourceID: $("#crmConfig").data('sourceId') || configError("Source Id")
                });
            }
        });
        return _.isEmpty(arr) ? null : { "AttributeList": arr };
    },
    emailConfig() {
        let $childDOBLength = $(this.formVal).find(".date-input-picker").length,
            $getChildDOB = $(this.formVal).find(".date-input-picker input").val();

        if ($childDOBLength === 1 && $getChildDOB != '' && $getChildDOB != null) {
            var $setChildDateFormat = this.formatDate($getChildDOB);
        }
        var triggerList = [{
                Name: "parentFirstName",
                Value: $("#parentFirstName").val() || ''
            },
            {
                Name: "parentLastName",
                Value: $("#parentLastName").val() || ''
            },
            {
                Name: "emailId",
                Value: $("#emailId").val() || ''
            },
            {
                Name: "address",
                Value: $("#address").val() || ''
            },
            {
                Name: "apartment",
                Value: $("#apartment").val() || ''
            },
            {
                Name: "city",
                Value: $("#city").val() || ''
            },
            {
                Name: "state",
                Value: $("#state").val() || ''
            },
            {
                Name: "zipCode",
                Value: $("#zipCode").val() || ''
            },
            {
                Name: "country",
                Value: $("#country").val() || ''
            },
            {
                Name: "PhoneNumber",
                Value: $("#PhoneNumber").val() || ''
            },
            {
                Name: "comments",
                Value: $("#comments").val() || ''
            },
            {
                Name: "birthDate0",
                Value: $setChildDateFormat || ""
            }
        ].filter(val => val.Value != "")
        return {
            TemplateType: $("#crmConfig").data('emailTemplate') || configError("Email Template Type"),
            TriggerList: triggerList
        };
    },
    returnFormValue(inputName) {

        formNamesArr = _.without(formNamesArr, inputName);
        return this.formVal[inputName] ?
            (this.formVal[inputName].value || null) :
            configError(inputName);
    },
    formatDate: function($ChildDOBFormatVal) {
        let $childDOBVal = $(this.formVal).find(".date-input-picker input");
        var $ChildDOBFormat;
        
        if ($childDOBVal.data("dateFormat") == "dd-mm-yyyy") {
            $ChildDOBFormat = $ChildDOBFormatVal.split("-").reverse().join("-");
        } else if($childDOBVal.data("dateFormat") == "yyyy-mm-dd"){
            $ChildDOBFormat =  $ChildDOBFormatVal;
        }
        else {
            var date = new Date($ChildDOBFormatVal);
            $ChildDOBFormat = date.getFullYear() + '-' + ("0" + (date.getMonth() + 1)).slice(-2) + '-' + ("0" + date.getDate()).slice(-2);
        }
        return $ChildDOBFormat;
    }
};

const getUniqueFormNames = form => {
    let arrs = [];

    _.each(
        $(form).find(
            'input[type="text"],input[type="email"],input[type="number"],input[type="checkbox"],input[type="radio"],select,textarea'
        ),
        item => {
            arrs.push(item.name);
        }
    );

    return _.unique(arrs);
};

const configError = settingName => {
    console.log("%c" + settingName + " value not found.", "color:red");
    return null;
};
let formNamesArr;

(() => {
    const config = {
        el: ".adaptive-form-container",
        parentEl: ".flexible-adaptive-container",
        isTouchDevice: "ontouchstart" in window,
        belowChildAge: 10,
        apiStatusCodeStorage: "APIStatusCodes",
        slideInScrollPos: 100
    };
    class CRMFORM {
        constructor() {
            self = this;
            self.init();
            $(`${config.el} .half-width-input`).each(function(index) {
                if (index % 2 === 0) {
                    $(this).addClass("odd-half-input");
                }
            })
        }
        init() {
            self.initEvents();
            self.loadAPIerrormessage();
            cloneInputElem = $.trim(
                $(`${config.el} .elem-repeat`).length ? $(`${config.el} .elem-repeat:eq(0)`)[0].outerHTML : "");
            self.bindDatePicker();
        }
        initEvents() {

            window.global.eventBindingInst.bindLooping({
                    [`click ${config.el} [type="submit"]`]: "formSubmit",
                    [`keyup ${config.el} .input-field.has-error input, ${config.el} .input-field.has-api-error input,${config.el} .input-field.has-error textarea`]: "inputChange",
                    [`change ${config.el} .input-field input,${config.el} .input-field select`]: "inputChange",
                    [`click ${config.el} .add-sub-form-group>a`]: "cloneInputGroup",
                    [`click ${config.el} .remove-sub-form-group>a`]: "removeInputGroup"
                },
                self
            );
            $(".adaptive-form").on("keypress", "#parentFirstName", function(key) {
                let regex = /^[a-zA-Z' ]$/;
                return regex.test(key.originalEvent.key);
            })
        }

        bindDatePicker() {
            const datebinding = self.initDatePicker(true);
            datebinding &&
                datebinding.change(function(ele) {
                    self.dobValidation(ele, this);
                });
        }

        bindScrollEvent(ele) {
            let scrollTop;
            const $ele = $(ele);
            let scrollAcheived = false;
            window.onscroll = function() {
                scrollTop = $(window).scrollTop();
                if (scrollTop > config.slideInScrollPos && !scrollAcheived) {
                    scrollAcheived = true;
                    $ele.addClass("active");
                }
            };
        }

        loadAPIerrormessage() {
            const checkStorage = window.global.getStorage(config.apiStatusCodeStorage);
            if (checkStorage) {
                self.apiStatusCodes = checkStorage;
                return;
            }
            request(apiStatusConfig())
                .then(data => {
                    if (!data) {
                        console.log("API error message data fails..error is " + data);
                        return;
                    }
                    self.apiStatusCodes = data;
                    window.global.setStorage(config.apiStatusCodeStorage, data);
                })

        }


        beforeSubmit(formElement, cb) {

            validate.checkAllFields(formElement, (output, formFields) => {
                output ? $(".form-level-error").addClass("hide") : $(".form-level-error").removeClass("hide");
                if (!output) {
                    self.getErrorNamesAndIds(formElement);
                    return;
                }
                $(formElement).addClass("request-pending");
                cb(output, formElement);
            });
        }
        inputChange(ele) {
            let validationForOptionalFields = true; // validation for non mandatory fields
            validate.instantValidation(ele, validationForOptionalFields);
            if (self.existEmail && ele.name == "emailId") {
                self.errorAPIToggle(
                    $(ele),
                    self.existEmail == ele.value ? true : false
                );
            }
        }
        formSubmit(ele, evt) {
            evt.preventDefault();
            const $formEle = $(ele.form);
            self.existEmail = "";
            $formEle.addClass("request-pending");

            self.beforeSubmit(ele.form, (res, formFields) => {
                self.toggleLoadingClass($formEle, true);
                const payload = CRMCONFIG(ele.form);
                console.log(payload);
                payload.consumerDetails = Object.fromEntries(Object.entries(payload.consumerDetails).filter(([_, v]) => v != null));
                let params = payload;
                if (!window.global.CMFormSubmit) {
                    console.log("CM Error: CommonDependency code not found.");
                    return;
                }
                window.global.CMFormSubmit(params, (res, data) => {
                    if (res === "success") {
                        let { statusCode } = data.Status || data;
                        if (statusCode) {
                            $(".adaptive-form").addClass("form-submitted");
                            if (statusCode == "201" || statusCode == "200") {
                                self.apiSuccessForTracking(ele, params);
                                self.successCB(ele.form, data);
                            } else {
                                self.checkAPIErrors(statusCode);
                                self.apiErrorForTracking(statusCode, data.Status.message, $('#emailId').val())
                            }
                        }
                        self.toggleLoadingClass($formEle, false);
                        $(".adaptive-form-container .api-error-msg").addClass("hide");
                    } else {
                        if (data.responseJSON) {
                            $(".adaptive-form-container .api-error-msg").removeClass("hide");
                        }
                        self.toggleLoadingClass($formEle, false);
                    }
                });
            });
        }

        successCB(formEle, res) {
            const datasets = $(formEle)[0].dataset;
            const successType = (datasets.successLocation || "").toLowerCase();
            const successAction = datasets.successAction;
            if (successType == "inline") {
                self.formReset(formEle);
                if ($(formEle).parents(".slidein").length == 0) {
                    $(formEle).hide();
                }
                $(".generic-api-message").removeClass("hide");
            } else if (successType == "redirect") {
                window.location.href = successAction;
                return false;
            }
        }
        formReset(formEle) {
            $(formEle).trigger("reset");
        }
        checkAPIErrors(errCode, isFirstErr, parentElem) {
            const apierrorcopy = self.apiStatusCodes && self.apiStatusCodes[errCode];
            let $curEle;
            switch (errCode) {
                case "1022":
                case "1009":
                    self.errorAPIToggle($(".child-dob"), true, apierrorcopy);
                    if (isFirstErr) $(".child-dob input").focus();
                    break;

                case "1010":
                case "1017":
                case "1018":
                case "1019":
                case "1020":
                case "1021":
                    $(parentElem)
                        .attr("data-content", apierrorcopy)
                        .addClass("api-fails");
                    break;

                case "1004":
                case "1029":
                    $curEle = $(`${config.el} #emailId`);
                    self.existEmail = $curEle.val();
                    self.errorAPIToggle($curEle, true, apierrorcopy);
                    $curEle.focus();
                    break;
            }
        }
        cloneInputGroup(ele, evt) {
            evt.preventDefault();
            const $ele = $(ele);
            const $parentEle = $ele.parents(".form-group");
            const childrenLimit = $parentEle.data("maxChildren") || 5;
            const $childrenInputs = $ele
                .closest(".form-group")
                .find(".elem-repeat:last-child");

            self.beforeSubmit($childrenInputs, (res, formFields) => {
                const regex = /^(.+?)(\d+)$/i;
                self.toggleLoadingClass($childrenInputs, true);
                const $insertItem = $ele.closest(".form-group").find(".elem-repeat");
                if (!$insertItem.length) {
                    return;
                }
                if (childrenLimit == $insertItem.length + 1) {
                    $parentEle.addClass("max-children-reached");
                }
                const cloneIndex = $insertItem.length;
                $childrenInputs.after(cloneInputElem);
                $ele
                    .closest(".form-group")
                    .find(".elem-repeat:last-child")
                    .attr("id", `children-item-${cloneIndex}`)
                    .find("label,input")

                .each((key, item) => {
                    const id = item.id || "";
                    const forAttr = $(item).attr("for");
                    const match = id.match(regex) || [];
                    const labelFor = (forAttr && forAttr.match(regex)) || [];
                    if (match.length == 3) {
                        item.id = match[1] + (match[2] + cloneIndex);
                        item.name = match[1] + cloneIndex;
                    }
                    if (labelFor.length == 3) {
                        $(item).attr("for", labelFor[1] + (labelFor[2] + cloneIndex));
                    }
                });
                self.bindDatePicker();
                setTimeout(() => {
                    self.toggleLoadingClass($childrenInputs, false);
                }, 500);
            });
        }
        toggleLoadingClass(ele, bool) {
            if (bool) {
                $(ele).addClass("form-loader");
            } else {
                $(ele).removeClass("form-loader");
            }
        }

        dobValidation(ele, curElem) {
            let self = this,
                $dobEle = $(ele.currentTarget);
            self.errorToggle($dobEle, $dobEle.val() == "" ? true : false);
        }
        errorToggle(elm, isError) {
            let $inputParentElem = elm.parents(".form-group"),
                hasReqAttribute = $(elm).attr("required"),
                errorClassName = "has-error";
            if (isError) {
                $inputParentElem.addClass(errorClassName);
                if (
                    elm.val() == "" &&
                    typeof hasReqAttribute !== typeof undefined &&
                    hasReqAttribute !== "false"
                ) {
                    $inputParentElem.addClass("has-required-error");
                }
            } else {
                $inputParentElem
                    .removeClass(errorClassName)
                    .removeClass("has-api-error has-required-error");
            }
        }
        errorAPIToggle(elm, isError, msgCopy) {
            let $inputParentElem = elm.closest(".input-field"),
                errorClassName = "has-api-error";
            if (isError) {
                if (msgCopy)
                    $inputParentElem.find(".application-message").html(msgCopy);
                $inputParentElem.addClass(errorClassName);
            } else {
                $inputParentElem.removeClass(errorClassName);
            }
        }
        initDatePicker(hideFutureDate, triggerChange) {
            let $el = $(`${config.el} .mobi-picker:not('.active')`),
                startYear = new Date().getFullYear() - config.belowChildAge || 14;

            if (!$el.length) return false;
            if (hideFutureDate != undefined) {

                $el.mobiscroll("destroy");
            }
            let dateBinded = $el

                .mobiscroll()
                .calendar({
                    theme: "ios",
                    lang: $("body").attr("data-countrycode") ?
                        $("body")
                        .attr("data-countrycode")
                        .toLowerCase() : "en",
                    dateFormat: $el.data("dateFormat") || "yy/mm/dd",
                    min: new Date(startYear + "-01-01"),
                    max: hideFutureDate ? false : new Date(),
                    responsive: {
                        xsmall: {
                            controls: ["date"]
                        },
                        small: {
                            controls: ["date"]
                        },
                        medium: {
                            controls: ["date"]
                        },
                        large: {
                            controls: ["calendar"],
                            touchUi: false
                        }
                    }
                })
                .addClass("active");
            if (triggerChange) dateBinded.trigger("change");
            return dateBinded;
        }
        removeInputGroup(ele, evt) {
            evt.preventDefault();
            const $ele = $(ele).closest(".elem-repeat");
            const $parentEle = $ele.parents(".form-group");
            self.toggleLoadingClass($ele, true);
            self.removeChildClick(ele, evt);
            setTimeout(() => {
                self.toggleLoadingClass($ele, false);
                $ele.remove();
                if ($parentEle.hasClass("max-children-reached")) {
                    $parentEle.removeClass("max-children-reached");
                }
            }, 500);
        }

        getErrorNamesAndIds(formElement) {
            let $errorNameElememt, $errorFieldElement, errorName = "",
                errorField = "";
            $(formElement).find('.has-error').each((key, item) => {
                $errorNameElememt = $(item).find(".form-message").text();
                $errorFieldElement = $(item).find("input, select, textarea").attr("id");
                errorName = errorName == "" ? $errorNameElememt : errorName + '|' + $errorNameElememt;
                errorField = errorField == "" ? $errorFieldElement : errorField + '|' + $errorFieldElement;
            });
            self.globalClientErrorForTracking(errorName, errorField, $('#emailId').val());
        }

        globalClientErrorForTracking(errorName, errorField, email) {
            typeof globalClientFormValidationError == "function" && globalClientFormValidationError({
                error_name: errorName,
                error_field: errorField
            }, typeof sha256 == "function" && sha256(email));
        }

        apiSuccessForTracking(ele, params) {
            var childGender = '';
            var childRel = '';
            var listOfSubscriptionIds = [];
            var i = 1;
            if (typeof params.consumerDetails.consumerChild !== typeof undefined && params.consumerDetails.consumerChild.ChildList) {
                $.each(params.consumerDetails.consumerChild.ChildList, function(key, value) {
                    if (childGender == '') {
                        childGender = "child1:" + value.ChildGender;
                        childRel = "child1:" + value.Relationship;
                    } else {

                        childGender = childGender + "| child" + i + ":" + value.ChildGender;
                        childRel = childRel + "| child" + i + ":" + value.Relationship;
                    }
                    i = i + 1;
                })
            }
            if (params.consumerDetails.consumerPreferences) {
                $.each(params.consumerDetails.consumerPreferences.PreferenceList, function(key, value) {
                    listOfSubscriptionIds.push(value.PreferenceID);
                });
            }
            typeof globalApiSuccessForm == "function" && globalApiSuccessForm({
                customer_email_hash: typeof sha256 == "function" && sha256(params.Email ? params.Email : params.consumerDetails.EmailAddress),
                optin_status: $("#agreeEmailNotifcation").prop('checked') || '',
                terms_and_conditions: $("#preferences-1").prop('checked') || '',
                child_gender: childGender,
                relationship: childRel,
                postal_code: (typeof params.consumerDetails.consumerAddress !== typeof undefined && params.consumerDetails.consumerAddress.AddressList && params.consumerDetails.consumerAddress.AddressList.length) ? params.consumerDetails.consumerAddress.AddressList[0].ZipCode : '',
                rich_text: $("#comment").val() != "" ? true : false,
                current_ele: $(ele),
                subscription_ids: listOfSubscriptionIds
                    // radio_button_selection: "OPTION SELECTED ON RADIO BUTTON”,
            })
        }
        apiErrorForTracking(errorCode, errorMessage, email) {
            typeof globalApiFormError == "function" && globalApiFormError({
                error_code: errorCode,
                error_message: errorMessage
            }, typeof sha256 == "function" && sha256(email));
        }

        checkFormHasDataTracking() {
            var $form = $(config.el);
            var $inputField = $form.find("input[type='text'],input[type='email'],input[type='radio'],input[type='checkbox'],select,textarea").filter(function() { return $(this).val() != "" && $(this) });
            var obj = {};
            if ($inputField.length) {
                obj.type = "browser close";
                obj.last_accessed_field = $inputField[$inputField.length - 1].getAttribute("id") || $inputField[$inputField.length - 1].getAttribute("name");
                typeof checkFormHasData == "function" && checkFormHasData(obj);
                return true;
            } else {
                return false;
            }
        }

        removeChildClick(ele, evt) {
            var obj = {};
            obj.event_action = "newsletter-signup-remove-child";
            obj.event_action_type = "click";
            obj.form_stage = "child remove";
            obj.form_type = "subscription";
            obj.event_detail = "child remove";
            obj.event_detail_sub = "remove a child";
            obj.sign_up_method = window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '';
            obj.location_name = "newsletter-signup-remove-child";
            obj.current_ele = $(ele);
            typeof globalTrackRemoveChild == "function" && globalTrackRemoveChild(obj);
            var index = $('#crm-form .edit-child-option').index(this);
            $("#child-details-wrapper .child-details-fieldset").eq(index).remove();
            evt.preventDefault();
        }
    }
    window.addEventListener("beforeunload", function(e) {
        if (!$(".adaptive-form").hasClass("form-submitted") && self.checkFormHasDataTracking()) {
            var confirmationMessage = "\o/";
            if (!$(".wcmmode").length && $(".wcmmode").val() == 'preview') {
                (e || window.event).returnValue = confirmationMessage;
                return confirmationMessage;
            }
        }
    });
    const APIError = val => {
        console.log(`%c${val}`, "color:red");
        return undefined;
    };
    $(function() {
        $('[data-toggle="popover"]').popover();
    });

    let self;
    let cloneInputElem;
    const request = window.global.ajaxRequest.ajaxCall;
    const validate = new fieldValidation();

    const formInit = new CRMFORM();
})();