import { setStorage, getStorage } from './sessionStorage';

/*
    This would set/get data's based on type args
    params : (arg1 = object key name, arg2 = object value, arg3 = type (i.e., set or get))
*/
const DHFormSession = (objKeyName, val, type) => {
    const sessionName = "DHFormData";
    let sessionData = getStorage(sessionName);
    if (typeof sessionData != "object" || !sessionData) {
        sessionData = {};
    }
    const predefinedKeyNames = ['quizData', 'treatment', 'subTreatmentData', 'dollName', 'specialExtrasData', 'retailFlowData', 'skippedSubTreatment', 'actionType', 'skippedSpclExtra', 'bckfromSummary'];
    if (predefinedKeyNames.indexOf(objKeyName) == -1) {
        return 'fail';
    }
    if (type == "get") {
        return sessionData[objKeyName] || false;
    } else if (type == "set") {
        sessionData[objKeyName] = val;
        setStorage(sessionName, sessionData);
    } else if (type == "delete") {
        delete sessionData[objKeyName];
        setStorage(sessionName, sessionData);
    }
};
/*
    This will check whether the entered value is restricted or not
    params : (arg1 = input value, arg2 = callback fn)
*/
const crossWordCheck = (inputVal, cb) => {
    let ajaxOption = apiConfig("profanityCheck").startCheck;
    ajaxOption.data = JSON.stringify({
        "giftMsgText": inputVal,
        "validateType": "giftMsg"
    });
    request.ajaxCall(ajaxOption).then(data => {
        if (data.hasOwnProperty("errorCode") || data.isNameValid) {
            cb("pass");
        } else {
            cb("fail");
        }
    }).fail(err => {
        cb("fail", err);
    }).catch(error => {
        cb("fail", error);
    });
};

/*
    This will handle the toggle error class name based on the arguments
    params : (arg1 = input element, arg2 = want to add or remove class name, arg3 = the error class name(i.e., .max-limit-err))
*/
const errorToggle = ($inputEle, isDisplayErr, errorClassName, showNextBtn) => {
    let $errorEle = $inputEle.parent().find('.err-msg');
    let $inputType = $inputEle.attr('type');
    let $nextEle;
    if (!$errorEle.length) {
        $errorEle = $inputEle.closest('form').find('.err-msg');
    }
    $inputType == "textarea" ? $nextEle = $('.review-add-to-bag').find(".dh-next-btn") : $nextEle = $($inputEle[0].form).find(".dh-next-btn");
    $errorEle.removeClass('in');
    if ($inputEle.val() == "" && $inputEle.attr('type') == "text") {
        !showNextBtn && $nextEle.addClass('disabled').attr('aria-disabled',true);
        return;
    } else if (isDisplayErr && errorClassName) {
        if (showNextBtn) {
            $errorEle.filter(`${errorClassName}`).addClass('in').delay(3000).queue(function (next) {
                $(this).removeClass("in");
                next();
            });
        } else {
            $errorEle.filter(`${errorClassName}`).addClass('in');
            $nextEle.addClass('disabled').attr('aria-disabled',true);
        }
        return;
    }
    $nextEle.removeClass('disabled').removeAttr('aria-disabled');
};
/*
    This would change the text to capitalize format
    param : (arg1 = input value)
*/
const textCapitalize = (val) => {
    const trimText = val.replace(/^\s/, '');
    return trimText[0] ? trimText[0].toUpperCase() + trimText.substring(1) : '';
};

/*
    This would validate the input text field based on args
    params : (arg1 = input element, arg2 = it should be an array format.ex: ['capitalize','maxlimit check'..],type = keyup or submit event)
*/
const inputTextValidation = (ele, validationLists, type) => {
    let $inputEle = $(ele);
    let inputVal = $inputEle.val();
    let inputtype = $(ele).attr('type');
    if (validationLists.indexOf('capitalize') != -1) {
        $inputEle.val(textCapitalize(inputVal));
    }
    if (validationLists.indexOf('regexPattern') != -1) {
        let regex = inputtype == 'text' ? new RegExp("^[A-Za-z + & ' -]+$") : new RegExp("^[ A-Za-z_!&'�+.?,-]*$");
        if (!regex.test(inputVal)) {
            errorToggle($inputEle, true, '.format-message');
            return false;
        } else {
            errorToggle($inputEle, false, '');
        }
    }
    if (validationLists.indexOf('checkMaxLength') != -1) {
        let maxLength = $inputEle.attr("maxlength");
        if (inputVal.length >= maxLength && inputVal.trim().length != 0) {
            let showNxtBtn = true; //type
            errorToggle($inputEle, true, '.maxlimit-message', showNxtBtn);
            $inputEle.val(inputVal.substr(0, maxLength));
        } else {
            errorToggle($inputEle, false, '');
        }
    }
};

/*
    This would validate the input radio button
    params : (arg1 = input element)
*/
const inputRadioValidation = (ele) => {
    const $ele = $(ele),
        inputName = $ele[0].name,
        $form = $($ele[0].form);
    if ($form.find(`input[name='${inputName}']`).is(':checked')) {
        errorToggle($ele, false, '');
    } else {
        errorToggle($ele, true, '');
    }
};

/*
    This would validate the input checkbox which covers empty value, max item and min item.
    params : (arg1 = input element)
*/
const inputCheckboxValidation = (ele, maxVal, minVal) => {
    const $ele = $(ele),
        $form = $($ele[0].form),
        $maxVal = maxVal,
        $minVal = minVal,
        checkedCount = $form.find('[type="checkbox"]:checked').length || $form.find('[type="radio"]:checked').length;
    let $nextEle = $form.find(".dh-next-btn");
    if (checkedCount) {
        if (checkedCount > $maxVal) {
            $form.find('.maxlimit-message').addClass('in');
            $nextEle.addClass('disabled').attr('aria-disabled',true);
        } else if (checkedCount < $minVal) {
            $form.find('.maxlimit-message').removeClass('in');
            $nextEle.addClass('disabled').attr('aria-disabled',true);
        } else {
            $form.find('.maxlimit-message').removeClass('in');
            $nextEle.removeClass('disabled').removeAttr('aria-disabled');
        }
    } else if (minVal == 0) {
        $nextEle.removeClass('disabled').removeAttr('aria-disabled');
    } else {
        $nextEle.addClass('disabled').attr('aria-disabled',true);
    }
};

/*
    This would render the doll name which user entered on first screen
    params : (arg1 = form element, arg2 = callback fn(optional))
*/
const bindDollName = (formEle, cb) => {
    let name;
    let targetEle = formEle.find('.dh-doll-name');
    if (!targetEle.length) return;
    let checkDOMData = $(".doll-name").val() || false;
    name = checkDOMData;
    if (!checkDOMData) {
        let sessionData = DHFormSession('dollName', '', 'get');
        if (sessionData) {
            name = sessionData;
        } !name && console.log("%cdoll name not exist in both session/DOM.", 'color:red')
    }
    name && targetEle.html(name);
    typeof cb == "function" && cb(name);
};

/*
    This would add or update the Hash string parameter
    params : (arg1 = key name, arg2 = value)
*/
const updateHashStringParameter = (key, value) => {
    let hashValue = `#${key}${value}`;
    if (window.location.hash) {
        let hashValues = window.location.hash.split('&');
        _.each(hashValues, (item, indx) => {
            if (item.indexOf(key) != -1) {
                hashValues[indx] = indx == 0 ? `#${key}${value}` : `${key}${value}`;
            }
        });
        hashValue = hashValues.length > 1 ? hashValues.join('&') : hashValues[0];
    }
    window.location.hash = hashValue;
};

/*
    This would remove the Hash string parameter
    params : (arg1 = key name, arg2 = value)
*/
const removeHashStringParameter = (deleteKeyVal) => {
    if (window.location.hash) {
        let hashValues = window.location.hash.split('&');
        _.each(hashValues, (item, indx) => {
            if (item.indexOf(deleteKeyVal) != -1) {
                hashValues[indx] = hashValues.splice(indx, 1);
            }
        });
        if (hashValues.length) {
            history.replaceState(null, null, ' ');
        } else {
            window.location.hash = hashValues.join('&');
        }
    }
};

/*
    This would read a page's GET URL variables and return them as an associative array.

*/
const getHashValue = (attrNamePrefix) => {
    if (window.location.hash) {
        let hashValues = window.location.hash.split('&');
        let hashVal;
        _.each(hashValues, (item) => {
            if (item.indexOf(attrNamePrefix) != -1) {
                hashVal = item.replace(`#${attrNamePrefix}`, '');
            }
        });
        return hashVal;
    }
    return false;
};
const helperTextEmpty = () => {
    let ele = $('.helper-text');
    if(ele.length){
        let helpertext = ele.text();
        if(helpertext == '' || helpertext == 'Info'){
            $(ele).addClass('hide');
        }
        $(ele).on('show.bs.popover', function () {
            setTimeout(function(){
                    $('.popover.in').removeAttr('role')
            },500)
        })
    }
};

const apiConfig = window.dh.apiConfig;
const request = window.dh.ajaxRequest;
export {
    crossWordCheck,
    inputTextValidation,
    inputRadioValidation,
    inputCheckboxValidation,
    bindDollName,
    updateHashStringParameter,
    getHashValue,
    removeHashStringParameter,
    DHFormSession,
    helperTextEmpty
};