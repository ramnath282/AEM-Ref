CRMAEM.MRSForm.apiConfig = function(name) {
    var obj = {
        "getToken": {
            "url": CRMAEM.MRSForm.apiBaseUrl + "token/clienttoken",
            "body": { clientId: CRMAEM.MRSForm.clientId, clientSecret: CRMAEM.MRSForm.secretId },
            "type": "POST",
            "contentType": 'application/x-www-form-urlencoded'
        },
        "acquistionAPI": {
            //"url" : "https://api.sdn.mattel.com/qa/consumerinfo",
            "url": $("#signApi-url").val(),
            "body": "",
            "type": "POST",
            //"contentType": 'application/json',
            "headers": {
                "Content-Type": "application/json",
                //"api_key": 'wsa7rhee9etmy42ryg3xnjay',
                "api_key": $("#signApi-key").val(),
            },
            "beforeSend": 'Bearer ' + CRMAEM.MRSForm.clientTokenKey
        },
        "getAPIErrorMessage": {
            "url": "/bin/getErrorMessages." + CRMAEM.MRSForm.pageLocale + ".json",
            "body": "",
            "type": "get",
            "contentType": 'application/json'
        },
        "getGeoDetection": {
            "url": "//geodetection.svc.mattelcloud.com/api/geoipcode",
            "type": "get"
        }
    }
    return obj[name];
};
CRMAEM.MRSForm.validateForm = function(elem, cb) {
    var self = this;
    var $formEle = $(elem.form);
    var apiParams = {};
    var isAPIError = $formEle.find('.has-api-error').length;
    var isFormError;
    var childrensDetailsArr;

    $("input[id^='mom']").val("MOTHER");
    $("input[id^='dad']").val("FATHER");
    $("input[id^='grandparent']").val("GRANDPARENT");
    $("input[id^='other']").val("FRIEND");
    $("input[id^='other']").val("FRIEND / FAMILY MEMBER");
    var stickyHeaderHeight, errorElementHeight;
    if (isAPIError) {
        stickyHeaderHeight = $('.scroll-sticky').height();
        errorElementHeight = $formEle.find('.has-api-error').last().height() / 2;
        $('html, body').animate({ scrollTop: ($formEle.find('.has-api-error').last().offset().top - stickyHeaderHeight - errorElementHeight) }, 'slow');
        //$('html, body').animate({scrollTop: ($formEle.find('.has-api-error').last().offset().top)}, 'slow');
        $formEle.find('.has-api-error').first().find('input').focus();
        cb(false);
        return;
    }
    _.each($formEle.find(".input_field, .checkbox input[type='checkbox']"), function(inputEle) {
        self.validateInput(inputEle);
    });
    // prevent form sumbition if has error\
    isFormError = $formEle.find('.has-error').length;
    if (isFormError) {
        stickyHeaderHeight = $('.scroll-sticky').height();
        errorElementHeight = $formEle.find('.has-error').first().height() / 2;
        $('html, body').animate({ scrollTop: ($formEle.find('.has-error').first().offset().top - stickyHeaderHeight - errorElementHeight) }, 'slow');
        //$('html, body').animate({scrollTop: ($formEle.find('.has-error').first().offset().top)}, 'slow');
        $formEle.find('.has-error').first().find('input').focus();
        var errorName = "",
            errorField = "",
            $errorFieldElement,
            $errorNameElememt;

        $formEle.find('.has-error').filter(function() {
            $errorNameElememt = $(this).find(".form-error").text();
            $errorFieldElement = $(this).find(".input_field").attr("id") || $(this).find("input[type='checkbox']").attr("id");
            errorName = errorName == "" ? $errorNameElememt : errorName + '|' + $errorNameElememt;
            errorField = errorField == "" ? $errorFieldElement : errorField + '|' + $errorFieldElement;
        })
        self.clientErrorForTracking(errorName, errorField, $('#emailId').val());
        cb(false);
        return;
    }
    // check email subscription
    var subscriptionId = $("#agreeTerms").prop('checked') == true ? [$("#agreeTerms").data("subscriptionId")] : [];
    if ($("#agreeEmailNotifcation").prop('checked') == true) {
        subscriptionId = _.union(subscriptionId, JSON.parse("[" + ($("#agreeEmailNotifcation").data("subscriptionId") || '') + "]"));
    }
    apiParams = generatepayload($formEle);

    // apiParams = {
    // 	"SourceID":this.sourceId,
    // 	"RequestedBy":"AEM",
    // 	"consumerDetails":{
    // 		"EmailAddress":$('#emailId').val(),
    // 		"MTTLAffiliationInd":"Y",
    // 		"ActiveInd":"Y",
    // 		"GlobalOptInInd":$("#agreeTerms").is(':checked')?"Y":"N",
    // 		"consumerName":{
    // 			"ConsumerTitle":"",
    // 			"ConsumerFirstName":$('#parentFirstName').val(),
    // 			"ConsumerLastName":""
    // 		}
    // 	}
    // };
    localStorage.setItem("params", JSON.stringify(apiParams));
    localStorage.setItem("subscription", subscriptionId);
    // childrensDetailsArr = self.getChildDetailsParamsV2();
    // if(childrensDetailsArr.length){
    //     apiParams.consumerDetails.consumerChild = {};
    //     apiParams.consumerDetails.consumerChild.ChildList = childrensDetailsArr;
    // 	localStorage.setItem("myArray", JSON.stringify(childrensDetailsArr));
    // }

    localStorage.setItem("params", JSON.stringify(apiParams));
    localStorage.setItem("subscription", subscriptionId);

    if ($formEle[0].checkValidity()) {
        cb(apiParams)
    } else {
        self.hideLoading();
    }
};

function generatepayload(formEle) {
    var childrensDetailsArr = CRMAEM.MRSForm.getChildDetailsParamsV2();
    var preferncesList = getSelectedSubscription(formEle);
    var stateVal = $('#stateField [aria-labelledby="stateDropdown"]').filter(':visible').val() || '';
    var countryCodeVal = $("#countryDropdown").val();
    var obj = {
        "SourceID": CRMAEM.MRSForm.sourceId,
        "RequestedBy": "AEM",
        "consumerDetails": {
            "EmailAddress": $('#emailId').val(),
            "MTTLAffiliationInd": "Y",
            "ActiveInd": "Y",
            "consumerName": {
                "ConsumerTitle": "",
                "ConsumerFirstName": $('#parentFirstName').val(),
                "ConsumerLastName": ""
            },
            consumerPhone: {
                PhoneNumber: $('#contactNumber').val() || ''
            },
            consumerAddress: {
                AddressList: countryCodeVal ? [
                  {
                    AddressLine1: $("#streetText").val() || '',
                    AddressLine2: $("#streetText1").val() || '',
                    City: $("#cityField").val() || '',
                    ZipCode: $("#postalCode").val() || '',
                    State: stateVal!="0" ? stateVal : '',
                    Country: countryCodeVal
                  }
                ] : []
            },
        }
    };
    if (childrensDetailsArr.length) {
        obj.consumerDetails.consumerChild = {};
        obj.consumerDetails.consumerChild.ChildList = childrensDetailsArr;
        localStorage.setItem("myArray", JSON.stringify(childrensDetailsArr));
    }
    if (preferncesList.length) {
        obj.consumerDetails.consumerPreferences = {};
        obj.consumerDetails.consumerPreferences.PreferenceList = preferncesList;
    }
    return obj;
}

function getLocaleName() {
    return ($("#pageLocale").val() || 'us').toLowerCase();
}

function getSelectedSubscription(formEle) {
    var localeName = getLocaleName(),
        isDoubleOptinEnabled = localeName == "de",
        $checkboxElems = $(formEle).find(".checkbox.terms"),
        subArr = [],
        $checkboxElem,
        subscriptionId,
        isContainMultipleIds;
    _.each($checkboxElems, function(item) {
        $checkboxElem = $(item).find('input[type="checkbox"]');
        subscriptionId = $checkboxElem.data('subscriptionId');
        isContainMultipleIds = subscriptionId && subscriptionId.toString().split(',');
        if ($checkboxElem.is(':checked')) {
            for (var i = 0; i < isContainMultipleIds.length; i++) {
                if (isDoubleOptinEnabled) {
                    subArr.push({
                        "PreferenceID": isContainMultipleIds[i],
                        "PreferenceOptIn": "N",
                        "DoubleOptInStatusCD": "V"
                    });
                } else {
                    subArr.push({
                        "PreferenceID": isContainMultipleIds[i],
                        "PreferenceOptIn": "Y"
                    });
                }
            }
        }
    });
    return subArr;
}
CRMAEM.MRSForm.checkMaxChildren = function() {
    if ($(this.el).find("#child-details-wrapper .child-details-fieldset").length == this.maxChildren + 1) {
        $(this.el).find("#addChild").addClass("hide");
        $(this.el).find("#addChild").closest(".add-child-option").css("border-bottom", "none");
    } else {
        $(this.el).find("#addChild").removeClass("hide");
        $(this.el).find("#addChild").closest(".add-child-option").css("border-bottom", "2px solid #E5E0DC");
    }
};

CRMAEM.MRSForm.getChildDetailsParamsV2 = function() {
    var childrenArray = [];
    _.each($('#child-details-wrapper fieldset:not(.sub-fieldset)'), function(item) {
        var childrenSubObj = {};
        _.each($(item).find(".child-data"), function(subItem) {
            var isoDate;
            var datearray;
            var date;
            if (!_.isEmpty($(subItem).find("input[type='radio']:checked").val()) || !_.isEmpty($(subItem).find("input[type='number']").val()) || !_.isEmpty($(subItem).find("input[type='text']").val())) {
                if ($(subItem).hasClass("child-gender")) {
                    childrenSubObj.ChildGender = $(subItem).find("input[type='radio']:checked").val() || '';
                } else if ($(subItem).hasClass("child-relation")) {
                    childrenSubObj.Relationship = $(subItem).find("input[type='radio']:checked").val() || '';
                } else if ($(subItem).hasClass("child-dob")) {
                    if ($(subItem).find("input").val() != '') {
                        datearray = $(subItem).find('.mobi-picker').data("date-format").toLowerCase() == "dd/mm/yyyy" ? $(subItem).find("input").val().split("/") : false;
                        date = new Date(datearray && datearray[1] + '/' + datearray[0] + '/' + datearray[2] || $(subItem).find("input").val());
                        isoDate = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString();
                        childrenSubObj.ChildDOB = isoDate.split('T')[0];
                    } else {
                        childrenSubObj.ChildDOB = '';
                    }
                } else if ($(subItem).hasClass("child-first-name")) {
                    childrenSubObj.ChildFirstName = $(subItem).find("input[type='text']").val() || '';
                }
            }

        });
        !_.isEmpty(childrenSubObj) && childrenArray.push(childrenSubObj)
    });
    return childrenArray;
};

CRMAEM.MRSForm.customBindEventV2 = function() {
    var mobileVer = $(window).width() <= 400;

    mobileVer && $('#crm-form').on("click", ".edit-child-option", function(event) {
        $('html, body').animate({
            scrollTop: $('.add-child-option').offset().top - 250
        }, 1000);
    });
};

CRMAEM.MRSForm.ajaxCall = function(obj, cb) {
    $.ajax({
        url: obj.url,
        data: obj.body || '',
        type: obj.type,
        contentType: obj.contentType || "application/json",
        beforeSend: function(xhr) {
            if (obj.beforeSend) {
                xhr.setRequestHeader('Authorization', obj.beforeSend);
            }
            if (obj.headers) {
                xhr.setRequestHeader('api_key', obj.headers.api_key);
            }
        },
        success: function(data) { if (typeof cb == "function") cb(data); },
        error: function(xhr, textStatus, errorThrown) { if (typeof cb == "function") cb(false, xhr.responseJSON || errorThrown); }
    });
};
CRMAEM.MRSForm.displayAPIErrors = function(errCode, errMsg, isFirstErr, parentElem) {
    var apierrorcopy = this.apiMessages && this.apiMessages[errCode];
    if (errCode == "1004") {
        self.existEmail = $("#emailId").val();
        this.errorAPIToggle($("#emailId"), true, apierrorcopy);
        if (isFirstErr) $("#emailId").focus();
    }
};

CRMAEM.MRSForm.customBindEventV2();
