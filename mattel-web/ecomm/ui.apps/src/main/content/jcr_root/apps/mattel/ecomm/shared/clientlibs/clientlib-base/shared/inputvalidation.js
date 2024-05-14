import ajaxRequest from './ajaxbinding';
import {
    dateFormat
} from './dateFormat';

export default class fieldValidation {
    constructor() {
        this.regex = {
            'email': /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/,
            'phoneNumber': /^[0-9+() -]{10,16}$/,
            'name': /^[a-zA-Z0-9'\- ]*$/,
            'pwdRule1': new RegExp(/^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)[A-Za-z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]{8,}$/),
            'pwdRule2': new RegExp(/([A-Za-z])\1{2}/),
            'creditCard': /^[0-9]{15,16}$/,
            'city' : /^[a-zA-Z-\s]+$/,
            'streetAddress' :/^[a-zA-Z0-9#,. -]*$/
        }
        this.checkEmptyField = function (field) {
            let val = $(field).val();
            if (val != null && val != "") {
                $(field).addClass("not-empty");
                return false;
            }
            $(field).removeClass("not-empty");
            return true;
        }
        this.checkPattern = function (val, pattern) {
            return val.test(pattern);
        }
    }  
    
    isEmail(val) {
        let {
            email
        } = this.regex;
        return email.test(val);
    };
    verifyEmail(field) {
        let email = field.form.emailId.value.trim();
        let confirmEmail = field.form.verifyEmail.value.trim();
        if (email != confirmEmail) {
            return false;
        }
        return true;
    }
    validateCharacterCount(pwd) {
        let counts = {};
        let ch, index, len, count;
        for (index = 0,
            len = pwd.length; index < len; ++index) {
            ch = pwd.charAt(index);
            count = counts[ch];
            counts[ch] = count ? count + 1 : 1;
        }
        for (ch in counts) {
            if (counts[ch] >= 4) {
                return true;
            }
        }
        return false;
    }
    isValidName(val) {
        let {
            name
        } = this.regex;
        return name.test(val);
    }
    isValidCity(val) {
        let {
            city
        } = this.regex;
        return city.test(val);
    }
    isValidStreet(val) {
        let {
            streetAddress
        } = this.regex;
        return streetAddress.test(val);
    }
    isPassword(val) {
        let {
            pwdRule1,
            pwdRule2
        } = this.regex;
        let validPwd = pwdRule1.test(val);
        let validPwd1 = pwdRule2.test(val);
        if (!validPwd || validPwd1 || this.validateCharacterCount(val)) {
            return false;
        }
        return true;
    };
    verifyPassword(field) {
        let password = field.form.password.value;
        let confirmPassword = field.value;
        if (password != confirmPassword) {
            return false;
        }
        return true;
    }
    isPhoneNumber(num) {
        let isZeroStart= true;
        let phone = num.split("").filter(v => {
            if(isZeroStart && v!=0) {
                isZeroStart = false;
            }
            if(!isZeroStart){
                return !isNaN(v)
            }else {
                return false;
            }                    
        }).join("");
        let {
            phoneNumber
        } = this.regex;
        let isValidPhone = phoneNumber.test(phone);
        if(isValidPhone){
            let numArr = phone.split("").filter(v => !isNaN(v) && v!=" ");
            let repeatNum = true;
            $.each(numArr, d => {
                if(numArr[0] != numArr[d]) {
                    repeatNum = false;
                }
            });
            isValidPhone = !repeatNum;
        }
        return isValidPhone;
    };
    verifyPhoneNumber(field) {
        let phone = $(field.form).find('.phone-field').val();
        let verifyPhone = $(field.form).find('.verify-phone').val();
        if (phone != verifyPhone) {
            return false;
        }
        return true;
    };
    isvalidZipCode(val) {
        let defaultCountry = $("#defaultCountry").val() || "US"
        let zipCode = this.getZipRegex(defaultCountry);
        if(defaultCountry=="CA") {
            return zipCode.test(val.toUpperCase());
        }
        return zipCode.test(val);
    };
    isCardNumberValid(val) {
        let {
            creditCard
        } = this.regex;
        return creditCard.test(val);
    };
    isTextLengthValid(val) {
        if(val.length > 30){
            return false;
        }
        return true;
    };
    isSenderNameValid(val) {
        if(val.length > 29){
            return false;
        }
        return true;
    }
    isValidGiftCardAmount(val) {
        let amountVal = $.trim(val);
        if(amountVal < 5 || amountVal > 1000){
            return false;
        }
        return true;
    };
    getCardType(number) {
        // visa
        var re = new RegExp("^4[0-9]{12}(?:[0-9]{3})?$");
        if (number.match(re) != null)
            return "Visa";

        // Mastercard 
        // Updated for Mastercard 2017 BINs expansion
        re = new RegExp("^5[1-5][0-9]{14}$");
        if (number.match(re) != null)
            return "Mastercard";

        // AMEX
        re = new RegExp("^3[47][0-9]{13}$");
        if (number.match(re) != null)
            return "AMEX";

        // Discover
        re = new RegExp("^(6011|622(12[6-9]|1[3-9][0-9]|[2-8][0-9]{2}|9[0-1][0-9]|92[0-5]|64[4-9])|65)");
        if (number.match(re) != null)
            return "Discover";

        // Diners
        re = new RegExp("^36");
        if (number.match(re) != null)
            return "Diners";

        // Diners - Carte Blanche
        re = new RegExp("3(?:0[0-5]|[68][0-9])[0-9]{11}");
        if (number.match(re) != null)
            return "Diners - Carte Blanche";

        // JCB
        re = new RegExp("(?:2131|1800|35[0-9]{3})[0-9]{11}");
        if (number.match(re) != null)
            return "JCB";

        // Visa Electron
        re = new RegExp("^(4026|417500|4508|4844|491(3|7))");
        if (number.match(re) != null)
            return "VisaElectron";

        return "";
    };
    showLoading(ele) {
        $(ele).addClass("request-pending");
    };
    hideLoading(ele) {
        $(ele).removeClass("request-pending");
    };
    errorToggle(ele, isDisplayErr, errorCode) {
        let $elm = errorCode == "ERROR_InvalidDate1" ? $(ele) : $(ele).closest('.input-field'),
            $errEle = errorCode == "ERROR_InvalidDate1" ? $elm.find('>.form-message') : $elm.find('.form-message');
        if (!request.apiErrorDatas) {
            request.apiErrorDatas = JSON.parse(sessionStorage.getItem("errorList")) || 0;
            if(request.apiErrorData){
                console.log("info: error code api regenerating..");
            } else{
            	console.log("Warn: error code json not found/loaded..");
            }
        }
        if ((isDisplayErr || isDisplayErr == null) && errorCode) {
            $elm.addClass('has-error');
            if ((($errEle.html() != "" && ele[0].tagName == "SELECT") && errorCode == 'ERROR_FIELDREQUIRED') || errorCode == "CUSTOM") {
                console.log(ele);
            } else {
                if(errorCode == 'ERROR_GIFT_CARD_AMT_Invalid'){
                    let gCAmtValidationMsg = $.trim($('#amountValidationMsg').val());
                    $errEle.html(gCAmtValidationMsg);
                }else{
                    $errEle.html(request.apiErrorDatas && (request.apiErrorDatas[errorCode] || ''));
                }
            }
        } else {
            $elm.removeClass('has-error');
            $errEle.html('');
        }

    };
    shouldBeValidated(field) {
        let $elm = $(field),
            $requiredElem = $elm.attr('required') || $elm.attr('aria-required'),
            isCustomMessage = $elm.siblings(".custom-message").length,
            inputVal = $.trim($elm.val()),
            $elmType = $elm.attr("type");
        if ($elmType == "radio") {
            let isChecked = $elm.prop("checked");
            let name = $elm.attr("name");
            $("input[name='" + name + "']").removeClass("not-empty");
            if (isChecked == true) {
                $(field).addClass("not-empty");
            } else {
                $(field).removeClass("not-empty");
            }
        } else if (inputVal != "" && $elmType != "radio") {
            $(field).addClass("not-empty");
        } else {
            $(field).removeClass("not-empty");
        }

        if (typeof $requiredElem !== typeof undefined && $requiredElem !== false) {
            if ($requiredElem == "false" && field.value == "") return;
            if (isCustomMessage && inputVal == '') {
                this.errorToggle($elm, null, "CUSTOM")
            } else {
                this.getErrorMessage(field);
            }
            return false;
        }
        return true;
    }
    getErrorMessage(field) {
        let $elm = $(field);
        let isVerifyPWDEmail = $(field.form).hasClass("signin-form");
        let inputVal = $.trim($elm.val());
        let isChecked, isCBChecked;
        let errorName = '';
        if(!$("#emailLightBoxModal").is(":visible")){
            switch (field.name || field.dataset.attr) {
                case 'firstName':
                    errorName = inputVal == "" ? 'ERROR_FirstNameEmpty' : (!this.isValidName(inputVal) ? 'ERROR_INVALIDNAMEFORMAT' : '');
                    break;
                case 'lastName':
                    errorName = inputVal == "" ? 'ERROR_LastNameEmpty' : (!this.isValidName(inputVal) ? 'ERROR_INVALIDNAMEFORMAT' : '');
                    break;
                case "emailId":
                case "email":
                    errorName = inputVal == "" ? 'ERROR_INVALIDEMAIL' : (!this.isEmail(inputVal) ? 'ERROR_INVALIDEMAILFORMAT' : '');
                    break;
                case "verifyEmail":
                case "emailVerify":
                    errorName = inputVal == "" ? 'ERROR_VerifyLogonIdEmpty' : (!this.verifyEmail(field) ? 'LOGONID_DO_NOT_MATCH' : '');
                    break;
                case "currPassword":
                case "password":
                    errorName = inputVal == "" ? 'ERROR_PasswordEmpty' : (!this.isPassword(inputVal) && !isVerifyPWDEmail ? 'ERROR_PasswordPolicy' : '');
                    break;
                case "verifyPassword":
                    errorName = inputVal == "" ? 'ERROR_VerifyPasswordEmpty' : (!this.verifyPassword(field) ? 'PWDREENTER_DO_NOT_MATCH' : '');
                    break;
                case "PhoneNumber":
                case "Phone":
                    errorName = inputVal == "" ? 'ERROR_PhonenumberEmpty' : (!this.isPhoneNumber(inputVal) ? 'ERROR_INVALIDPHONE' : '');
                    break;
                case "childBirthMonth":
                case "birthMonth":
                    errorName = inputVal == "" ? 'ERROR_MONTHEmpty' : '';
                    break;
                case "childBirthDay":
                case "birthDate":
                    errorName = inputVal == "" ? 'ERROR_DATEEmpty' : '';
                    break;
                case "childBirthYear":
                case "birthYear":
                    errorName = inputVal == "" ? 'ERROR_YearEmpty' : '';
                    break;
                case "currentPassword":
                    errorName = inputVal == "" ? 'ERROR_CurrentPasswordEmpty' : (!this.isPassword(inputVal) && !isVerifyPWDEmail ? 'ERROR_PasswordPolicy' : '');
                    break;
                case "verifyPhone":
                    errorName = inputVal == "" ? 'ERROR_VerifyPhoneEmpty' : (!this.verifyPhoneNumber(field) ? 'ERROR_VerifyPhoneNotMatch' : '');
                    break;
                case "addressOption":
                    isChecked = $("input[name='addressOption']").is(":checked");
                    errorName = isChecked == false ? 'ERROR_FIELDREQUIRED' : '';
                    break;
                case "childOption":
                    isChecked = $("input[name='childOption']").is(":checked");
                    errorName = isChecked == false ? 'ERROR_GENDER_FIELDREQUIRED' : '';
                    break;
                case "guardianOption":
                    isChecked = $("input[name='guardianOption']").is(":checked");
                    errorName = isChecked == false ? 'ERROR_RELATIONSHIP_FIELDREQUIRED' : '';
                    break;
                case "cardNumber":
                    errorName = inputVal == "" ? 'ERROR_CardnumberEmpty' : (!this.isCardNumberValid(inputVal) ? 'ERROR_INVALIDCARD' : '');
                    break;
                case "childName": 
                    errorName = inputVal == "" ? 'ERROR_CHILDNAME_FIELDREQUIRED' : '';
                    break;
                case "zipCode":
                    errorName = inputVal == "" ? 'ERROR_ZipCodeEmpty' : (!this.isvalidZipCode(inputVal) ? 'ERROR_ZipCodeInvalid' : '');
                    break;
                case 'cityName':
                    errorName = inputVal == "" ? 'ERROR_CityEmpty' : (!this.isValidCity(inputVal) ? 'ERROR_InvalidCity' : '');
                    break;
                case 'streetAddress':
                    errorName = inputVal == "" ? 'ERROR_AddressEmpty' : (!this.isValidStreet(inputVal) ? 'ERROR_InvalidStreet' : '');
                    break;
                case 'billingAddress':
                    errorName = inputVal == "" ? 'ERROR_EMPTY_BILLING_ADDRESS' : '';
                    break;
                case 'cardName':
                    errorName = inputVal == "" ? 'ERROR_EMPTY_CARD_NAME' : '';
                    break;
                case 'cardMonth':
                    errorName = inputVal == "" ? 'ERROR_EMPTY_CARD_MONTH' : '';
                    break;
                case 'cardYear':
                    errorName = inputVal == "" ? 'ERROR_EMPTY_CARD_YEAR' : '';
                    break;
                case 'streetAddress1':
                    errorName = inputVal == "" ? 'ERROR_AddressEmpty' : '';
                    break;
                case 'stateProvince':
                    errorName = inputVal == "" ? 'ERROR_StateEmpty' : '';
                    break;
                case 'countryRegion':
                    errorName = inputVal == "" ? 'ERROR_CountryEmpty' : '';
                    break;
                case 'recipientName':
                    errorName = inputVal == "" ? 'ERROR_RecipientEmpty' : (!this.isTextLengthValid(inputVal) ? 'ERROR_RecipientTooLong' : '');
                    break;
                case 'senderName':
                    errorName = inputVal == "" ? 'ERROR_SenderEmpty' : (!this.isSenderNameValid(inputVal) ? 'ERROR_SenderTooLong' : '');
                    break;
                case 'messageLine1':
                case 'messageLine2':
                case 'messageLine3':
                case 'messageSelect':
                    errorName = inputVal == "" ? 'ERROR_MessageEmpty' : (!this.isTextLengthValid(inputVal) ? 'ERROR_MessageTooLong' : '');
                    break;
                case 'giftCardamount':
                    errorName = inputVal == "" ? 'ERROR_AMOUNTREQUIRED' : (!this.isValidGiftCardAmount(inputVal) ? 'ERROR_GIFT_CARD_AMT_Invalid' : '');
                    break;
                default:
                    errorName = inputVal == "" ? 'ERROR_FIELDREQUIRED' : '';
                    break;
            }
            this.errorToggle($elm, null, errorName);
            return errorName;
        }else{
            switch (field.dataset.attr) {
                case "childOption":
                    $(".child-gender:visible").each((k, v) => {
                        let index = k+1;
                        let $elm = $("input[name='childOption-"+index+"']").parents("ul");
                        isCBChecked = $("input[name='childOption-"+index+"']").is(":checked");
                        let errorName = isCBChecked == false ? 'ERROR_GENDER_FIELDREQUIRED' : '';
                        this.errorToggle($elm, null, errorName);
                        return errorName;
                    });
                    break;
                case "guardianOption":
                case "relationship":  
                    $(".child-relation:visible").each((k, v) => {
                        let index = k+1;
                        let $elm = $("input[name='guardianOption-"+index+"']").parents("ul");
                        isCBChecked = $("input[name='guardianOption-"+index+"']").is(":checked");
                        let errorName = isCBChecked == false ? 'ERROR_RELATIONSHIP_FIELDREQUIRED' : '';
                        this.errorToggle($elm, null, errorName);
                        return errorName;
                    });
                    break;
                case "childName":
                    $(".form_Child_name:visible").each((k, v) => {
                        let index = k+1;
                        let $elm = $("input[name='childName-"+index+"']");
                        let inputVal = $.trim($("input[name='childName-"+index+"']").val());
                        let errorName = inputVal == "" ? 'ERROR_CHILDNAME_FIELDREQUIRED' : '';
                        this.errorToggle($elm, null, errorName);
                        return errorName;
                    });
                    break;
                case "childBirthMonth":
                    $(".monthCont:visible").each((k, v) => {
                        let index = k+1;
                        let $elm = $("select[name='childBirthMonth-"+index+"']");
                        let inputVal = $.trim($("select[name='childBirthMonth-"+index+"']").val());
                        let errorName = inputVal == "" ? 'ERROR_MONTHEmpty' : '';
                        this.errorToggle($elm, null, errorName);
                        return errorName;
                    });
                    break;
                case "childBirthDay":
                    $(".dayCont:visible").each((k, v) => {
                        let index = k+1;
                        let $elm = $("select[name='childBirthDay-"+index+"']");
                        let inputVal = $.trim($("select[name='childBirthDay-"+index+"']").val());
                        let errorName = inputVal == "" ? 'ERROR_DATEEmpty' : '';
                        this.errorToggle($elm, null, errorName);
                        return errorName;
                    });
                    break;
                case "childBirthYear":
                    $(".yearCont:visible").each((k, v) => {
                        let index = k+1;
                        let $elm = $("select[name='childBirthYear-"+index+"']");
                        let inputVal = $.trim($("select[name='childBirthYear-"+index+"']").val());
                        let errorName = inputVal == "" ? 'ERROR_YearEmpty' : '';
                        this.errorToggle($elm, null, errorName);
                        return errorName;
                    });
                    break;
                case "emailId":
                case "email":
                    errorName = inputVal == "" ? 'ERROR_INVALIDEMAIL' : (!this.isEmail(inputVal) ? 'ERROR_INVALIDEMAILFORMAT' : '');
                    this.errorToggle($elm, null, errorName);
                    return errorName;
                    break;
            }
        }
    }
    instantValidation(field) {
        if (this.shouldBeValidated(field)) {
            let invalid =
                (field.getAttribute("aria-required") && !field.value) || field.value;
            if (!invalid && field.getAttribute("aria-invalid")) {
                field.removeAttribute("aria-invalid");
            } else if (invalid && !field.getAttribute("aria-invalid")) {
                field.setAttribute("aria-invalid", "true");
            }
        }
        if(field.nodeName == "SELECT"){
            this.checkDOB(field);
        } 
    }
    editFormFields(form) {
        let $formEle = $(form);
        let formInputs = $formEle[0].querySelectorAll('select,input:not([type="hidden"]):not(.hide)');
        for (let i = 0; i < formInputs.length; i++) {
            this.checkEmptyField(formInputs[i]);
        }
    }
    checkDOB(field){
        const $form = $(field).closest('fieldset');
        if(!$form.length) return;
        const Month = $form.find("[name=childBirthMonth]").val();
        const Day = $form.find("[name=childBirthDay]").val();
        const Year = $form.find("[name=childBirthYear]").val();
        if(!Month || !Day || !Year) return;
        const dobVal = `${Day}/${Month}/${Year}`;
        this.errorToggle($form, !dateChecker.isValidDate(dobVal) ? null : false,  "ERROR_InvalidDate1");        
    }
    checkAllFields(form, cb) {
        let isFormError;
        let $formEle = $(form);
        let formInputs = $formEle[0].querySelectorAll('select,input:not([type="hidden"]):not(.hide):not(:disabled)');
        
        for (let i = 0; i < formInputs.length; i++) {
            this.instantValidation(formInputs[i]);
        }
        isFormError = $formEle.find('.has-error').length;
        if (isFormError) {
            $formEle.find('.has-error').first().find('input').focus();
            cb(false, formInputs);
            return;
        }
        cb(true);
    }
    checkOnlyVisibleFields(form, cb) {
        let isFormError;
        let $formEle = $(form);
        let formInputs = $formEle.find('select:visible,input:visible:not([type="hidden"]):not(.hide)');
        for (let i = 0; i < formInputs.length; i++) {
            this.instantValidation(formInputs[i]);
        }
        isFormError = $formEle.find('.has-error').length;
        if (isFormError) {
            $formEle.find('.has-error').first().find('input').focus();
            cb(false, formInputs);
            return;
        }
        cb(true);
    }
    getZipRegex(country) {
        let zipCodeRegexList = { 
            "AL": { "regex": "^\\d{4}$" },
			"DZ": { "regex": "^\\d{5}$" },
			"AI": { "regex": "^2640$" },
			"AS": { "regex": "^(96799)(?:[ \\-](\\d{4}))?$" },
			"AD": { "regex": "^AD[1-7]0\\d$" },
			"AF": { "regex": "^\\d{4}$" },
			"AQ": { "regex": "" },
			"AG": { "regex": "" },
			"AM": { "regex": "^(37)?\\d{4}$" },
			"AR": { "regex": "^((?:[A-HJ-NP-Z])?\\d{4})([A-Z]{3})?$" },
			"AW": { "regex": "" },
			"AU": { "regex": "^\\d{4}$" },
			"AT": { "regex": "^\\d{4}$" },
			"BS": { "regex": "" },
			"AZ": { "regex": "^\\d{4}$" },
			"BH": { "regex": "^(?:\\d|1[0-2])\\d{2}$" },
			"BD": { "regex": "^\\d{4}$" },
			"BB": { "regex": "^BB\\d{5}$" },
			"BY": { "regex": "^\\d{6}$" },
			"BZ": { "regex": "" },
			"BJ": { "regex": "" },
			"BE": { "regex": "^\\d{4}$" },
			"BM": { "regex": "^[A-Z]{2} ?[A-Z0-9]{2}$" },
			"BT": { "regex": "^\\d{5}$" },
			"BO": { "regex": "" },
			"BQ": { "regex": "" },
			"BA": { "regex": "^\\d{5}$" },
			"BW": { "regex": "" },
			"BV": { "regex": "" },
			"BR": { "regex": "^\\d{5}-?\\d{3}$" },
			"IO": { "regex": "^BBND 1ZZ$" },
			"BF": { "regex": "" },
			"BG": { "regex": "^\\d{4}$" },
			"BN": { "regex": "^[A-Z]{2} ?\\d{4}$" },
			"BI": { "regex": "" },
			"KH": { "regex": "^\\d{5}$" },
			"CA": { "regex": "^[ABCEGHJKLMNPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z] ?\\d[ABCEGHJ-NPRSTV-Z]\\d$" },
			"CV": { "regex": "^\\d{4}$" },
			"CM": { "regex": "" },
			"CF": { "regex": "" },
			"TD": { "regex": "" },
			"KY": { "regex": "^KY\\d-\\d{4}$" },
			"CL": { "regex": "^\\d{7}$" },
			"CN": { "regex": "^\\d{6}$" },
			"CX": { "regex": "^6798$" },
			"CO": { "regex": "^\\d{6}$" },
			"CC": { "regex": "^6799$" },
			"KM": { "regex": "" },
			"CG": { "regex": "" },
			"CD": { "regex": "" },
			"HR": { "regex": "^\\d{5}$" },
			"CK": { "regex": "" },
			"CR": { "regex": "^\\d{4,5}|\\d{3}-\\d{4}$" },
			"CW": { "regex": "" },
			"CY": { "regex": "^\\d{4}$" },
			"CZ": { "regex": "^\\d{3} ?\\d{2}$" },
			"DK": { "regex": "^\\d{4}$" },
			"DM": { "regex": "" },
			"DJ": { "regex": "" },
			"DO": { "regex": "^\\d{5}$" },
			"TL": { "regex": "" },
			"EG": { "regex": "^\\d{5}$" },
			"EC": { "regex": "^[A-Z]\\d{4}[A-Z]|(?:[A-Z]{2})?\\d{6}$" },
			"SV": { "regex": "^CP [1-3][1-7][0-2]\\d$" },
			"GQ": { "regex": "" },
			"ER": { "regex": "" },
			"EE": { "regex": "^\\d{5}$" },
			"ET": { "regex": "^\\d{4}$" },
			"FK": { "regex": "^FIQQ 1ZZ$" },
			"FO": { "regex": "^\\d{3}$" },
			"FI": { "regex": "^\\d{5}$" },
			"FJ": { "regex": "" },
			"FR": { "regex": "^\\d{2} ?\\d{3}$" },
			"GF": { "regex": "^9[78]3\\d{2}$" },
			"GA": { "regex": "" },
			"GM": { "regex": "" },
			"TF": { "regex": "" },
			"GE": { "regex": "^\\d{4}$" },
			"DE": { "regex": "^\\d{5}$" },
			"GH": { "regex": "" },
			"GI": { "regex": "^GX11 1AA$" },
			"GB": { "regex": "^GIR ?0AA|((AB|AL|B|BA|BB|BD|BH|BL|BN|BR|BS|BT|BX|CA|CB|CF|CH|CM|CO|CR|CT|CV|CW|DA|DD|DE|DG|DH|DL|DN|DT|DY|E|EC|EH|EN|EX|FK|FY|G|GL|GY|GU|HA|HD|HG|HP|HR|HS|HU|HX|IG|IM|IP|IV|JE|KA|KT|KW|KY|L|LA|LD|LE|LL|LN|LS|LU|M|ME|MK|ML|N|NE|NG|NN|NP|NR|NW|OL|OX|PA|PE|PH|PL|PO|PR|RG|RH|RM|S|SA|SE|SG|SK|SL|SM|SN|SO|SP|SR|SS|ST|SW|SY|TA|TD|TF|TN|TQ|TR|TS|TW|UB|W|WA|WC|WD|WF|WN|WR|WS|WV|YO|ZE)(\\d[\\dA-Z]? ?\\d[ABD-HJLN-UW-Z]{2}))|BFPO ?\\d{1,4}$" },
			"GR": { "regex": "^\\d{3} ?\\d{2}$" },
			"GL": { "regex": "^39\\d{2}$" },
			"GP": { "regex": "^9[78][01]\\d{2}$" },
			"GD": { "regex": "" },
			"GU": { "regex": "^(969(?:[12]\\d|3[12]))(?:[ \\-](\\d{4}))?$" },
			"GT": { "regex": "^\\d{5}$" },
			"GN": { "regex": "^\\d{3}$" },
			"GG": { "regex": "^GY\\d[\\dA-Z]? ?\\d[ABD-HJLN-UW-Z]{2}$" },
			"GY": { "regex": "" },
			"GW": { "regex": "^\\d{4}$" },
			"HT": { "regex": "^\\d{4}$" },
			"HM": { "regex": "^\\d{4}$" },
			"HN": { "regex": "^\\d{5}$" },
			"HU": { "regex": "^\\d{4}$" },
			"HK": { "regex": "" },
			"IS": { "regex": "^\\d{3}$" },
			"IN": { "regex": "^[1-9][0-9]{5}$" },
			"ID": { "regex": "^\\d{5}$" },
			"IQ": { "regex": "^\\d{5}$" },
			"IE": { "regex": "^[\\dA-Z]{3} ?[\\dA-Z]{4}$" },
			"IT": { "regex": "^\\d{5}$" },
			"IL": { "regex": "^\\d{5}(?:\\d{2})?$" },
			"JM": { "regex": "" },
			"CI": { "regex": "" },
			"JE": { "regex": "^JE\\d[\\dA-Z]? ?\\d[ABD-HJLN-UW-Z]{2}$" },
			"JP": { "regex": "^\\d{3}-?\\d{4}$" },
			"JO": { "regex": "^\\d{5}$" },
			"KZ": { "regex": "^\\d{6}$" },
			"KE": { "regex": "^\\d{5}$" },
			"KI": { "regex": "" },
			"KW": { "regex": "^\\d{5}$" },
			"LA": { "regex": "^\\d{5}$" },
			"LV": { "regex": "^LV-\\d{4}$" },
			"LB": { "regex": "^(?:\\d{4})(?: ?(?:\\d{4}))?$" },
			"KG": { "regex": "^\\d{6}$" },
			"LS": { "regex": "^\\d{3}$" },
			"LY": { "regex": "" },
			"LR": { "regex": "^\\d{4}$" },
			"LI": { "regex": "^(948[5-9])|(949[0-7])$" },
			"LT": { "regex": "^\\d{5}$" },
			"LU": { "regex": "^\\d{4}$" },
			"MO": { "regex": "" },
			"MK": { "regex": "^\\d{4}$" },
			"MW": { "regex": "" },
			"MG": { "regex": "^\\d{3}$" },
			"MY": { "regex": "^\\d{5}$" },
			"MV": { "regex": "^\\d{5}$" },
			"ML": { "regex": "" },
			"MT": { "regex": "^[A-Z]{3} ?\\d{2,4}$" },
			"MH": { "regex": "^(969[67]\\d)(?:[ \\-](\\d{4}))?$" },
			"MQ": { "regex": "^9[78]2\\d{2}$" },
			"MU": { "regex": "^\\d{3}(?:\\d{2}|[A-Z]{2}\\d{3})$" },
			"MR": { "regex": "" },
			"YT": { "regex": "^976\\d{2}$" },
			"MX": { "regex": "^\\d{5}$" },
			"FM": { "regex": "^(9694[1-4])(?:[ \\-](\\d{4}))?$" },
			"MD": { "regex": "^\\d{4}$" },
			"MC": { "regex": "^980\\d{2}$" },
			"MN": { "regex": "^\\d{5}$" },
			"MS": { "regex": "" },
			"MA": { "regex": "^\\d{5}$" },
			"ME": { "regex": "^8\\d{4}$" },
			"MZ": { "regex": "^\\d{4}$" },
			"MM": { "regex": "^\\d{5}$" },
			"NR": { "regex": "" },
			"NA": { "regex": "" },
			"NL": { "regex": "^\\d{4} ?[A-Z]{2}$" },
			"NP": { "regex": "^\\d{5}$" },
			"AN": { "regex": "" },
			"NC": { "regex": "^988\\d{2}$" },
			"NZ": { "regex": "^\\d{4}$" },
			"NE": { "regex": "^\\d{4}$" },
			"NI": { "regex": "^\\d{5}$" },
			"NG": { "regex": "^\\d{6}$" },
			"NU": { "regex": "" },
			"NF": { "regex": "^2899$" },
			"MP": { "regex": "^(9695[012])(?:[ \\-](\\d{4}))?$" },
			"NO": { "regex": "^\\d{4}$" },
			"PW": { "regex": "^(969(?:39|40))(?:[ \\-](\\d{4}))?$" },
			"OM": { "regex": "^(PC )?\\d{3}$" },
			"PS": { "regex": "" },
			"PK": { "regex": "^\\d{5}$" },
			"PG": { "regex": "^\\d{3}$" },
			"PA": { "regex": "" },
			"PE": { "regex": "^(?:LIMA \\d|CALLAO 0?)\\d|[0-2]\\d{4}$" },
			"PY": { "regex": "^\\d{4}$" },
			"PH": { "regex": "^\\d{4}$" },
			"PN": { "regex": "^PCRN 1ZZ$" },
			"PF": { "regex": "^987\\d{2}$" },
			"PL": { "regex": "^\\d{2}-\\d{3}$" },
			"PR": { "regex": "^(00[679]\\d{2})(?:[ \\-](\\d{4}))?$" },
			"RE": { "regex": "^9[78]4\\d{2}$" },
			"PT": { "regex": "^\\d{4}-\\d{3}$" },
			"QA": { "regex": "" },
			"RO": { "regex": "^\\d{6}$" },
			"RU": { "regex": "^\\d{6}$" },
			"SH": { "regex": "^(ASCN|STHL) 1ZZ$" },
			"LC": { "regex": "" },
			"KN": { "regex": "" },
			"PM": { "regex": "^9[78]5\\d{2}$" },
			"GS": { "regex": "^SIQQ 1ZZ$" },
			"ST": { "regex": "" },
			"VC": { "regex": "^VC\\d{4}$" },
			"WS": { "regex": "" },
			"SM": { "regex": "^4789\\d$" },
			"SN": { "regex": "^\\d{5}$" },
			"SA": { "regex": "^\\d{5}$" },
			"SC": { "regex": "" },
			"RS": { "regex": "^\\d{5,6}$" },
			"SL": { "regex": "" },
			"SG": { "regex": "^\\d{6}$" },
			"SK": { "regex": "^\\d{3} ?\\d{2}$" },
			"KR": { "regex": "^\\d{3}(?:\\d{2}|-\\d{3})$" },
			"SB": { "regex": "" },
			"ZA": { "regex": "^\\d{4}$" },
			"SI": { "regex": "^\\d{4}$" },
			"ES": { "regex": "^\\d{5}$" },
			"SR": { "regex": "" },
			"LK": { "regex": "^\\d{5}$" },
			"SJ": { "regex": "^\\d{4}$" },
			"SZ": { "regex": "^[HLMS]\\d{3}$" },
			"SE": { "regex": "^\\d{3} ?\\d{2}$" },
			"CH": { "regex": "^\\d{4}$" },
			"TW": { "regex": "^\\d{3}(\\d{2})?$" },
			"TJ": { "regex": "^\\d{6}$" },
			"TH": { "regex": "^\\d{5}$" },
			"TZ": { "regex": "^\\d{4,5}$" },
			"TG": { "regex": "" },
			"TK": { "regex": "" },
			"TO": { "regex": "" },
			"TM": { "regex": "^\\d{6}$" },
			"TN": { "regex": "^\\d{4}$" },
			"TT": { "regex": "" },
			"TR": { "regex": "^\\d{5}$" },
			"TC": { "regex": "^TKCA 1ZZ$" },
			"TV": { "regex": "" },
			"UG": { "regex": "" },
			"UA": { "regex": "^\\d{5}$" },
			"AE": { "regex": "" },
			"UM": { "regex": "^96898$" },
			"US": { "regex": "^(\\d{5})(?:[ \\-](\\d{4}))?$" },
			"UY": { "regex": "^\\d{5}$" },
			"UZ": { "regex": "^\\d{6}$" },
			"VU": { "regex": "" },
			"VA": { "regex": "^00120$" },
			"VN": { "regex": "^\\d{6}$" },
			"VE": { "regex": "^\\d{4}$" },
			"VG": { "regex": "^VG\\d{4}$" },
			"VI": { "regex": "^(008(?:(?:[0-4]\\d)|(?:5[01])))(?:[ \\-](\\d{4}))?$" },
			"WF": { "regex": "^986\\d{2}$" },
			"EH": { "regex": "^\\d{5}$" },
			"YE": { "regex": "" },
			"ZM": { "regex": "^\\d{5}$" },
			"ZW": { "regex": "^\\d{5}$" }
	    }       
        return new RegExp(zipCodeRegexList[country].regex);
    }
}
const request = new ajaxRequest();
const dateChecker = new dateFormat();