/**
 * email-pref.js
 * Version 2.0
 */

const config = {
    el: '.email-preferences-component',
    apipref_Url: $("#prefApi-url").val(), // https://api.sdn.mattel.com/qa/preferences
    apipref_key: $("#prefApi-key").val(),
    changeEmailPrefRedirectUrl: $("#redirect_brands").val(),
    unsubscribeRedirectUrl: $("#redirect_unsubscribe").val(),
    useremail_encrypted: $('#formApi-useremaiID').val(),
    RequestedBy: $('#requestedBy').val(),
    checkedField: 'Y',
    randomText:  Math.random().toString(36).slice(2),
    checkedStatus: true,

    apiConfig(name) {
        var obj = {
            //To get master list prefs
            "consumerPreferences": {
                "url": config.apipref_Url,
                "body": "",
                "type": "get",
                "cache": "false",
                "headers": {
                    "api_key": config.apipref_key,
                    "contentType": "application/json",
                },
            },
            //pass emailaddress to fetch user preferences
            "userpreferences": {
                "url": `//${window.location.host}/bin/consumerinfohandler.consumerPreferenceService.json?q=${config.randomText}`,
                "body": "",
                "type": "get",
                "cache": "false",
                "headers": {
                    "EmailAddress": config.useremail_encrypted,
                    "contentType": 'application/json',
                },
            },
            //Submit the preference
            "consumerinfoAPI": {
                "url": `//${window.location.host}/bin/consumerinfohandler.consumerPreferenceService.json`,
                "type": "POST",
                "body": "",
                "headers": {
                    "Content-Type":"application/json",
                },
            },

        }
        return obj[name];
    }
}
class emailPref {
    constructor() {
        self = this;
        // self.init();
    }
    init() {
        window.global.eventBindingInst.bindLooping({
            "click input[type='radio']": "radioBtnChange",
            "click .mattlebrands": "mattlebrandSelect",
            "click #update-btn": "updateList"
        }, self);
        this.render();
    }

    render() {
        this.loadData();
    }

    loadData() {
        this.ajaxCall(config.apiConfig("consumerPreferences"), function(res, err) { //https://api.sdn.mattel.com/qa/preferences
        });
        this.ajaxCall(config.apiConfig("userpreferences"), function(res, err) { //https://api.sdn.mattel.com/dev/consumerpreferences
            $(res.consumerPreferences.PreferenceList).each(function(indx, obj) {
                if (obj.PreferenceOptIn == "Y") {
                    $("[data-id='" + obj.PreferenceID + "']").prop("checked", true);
                    $("[data-id='" + obj.PreferenceID + "']").closest("li").addClass("check-active");
                } else if (obj.PreferenceOptIn == "N") {
                    $("[data-id='" + obj.PreferenceID + "']").prop("checked", false);
                    $("[data-id='" + obj.PreferenceID + "']").closest("li").removeClass("check-active");
                }
            });
            if($('.mattlebrands:checked').length > 0) {
                $('.accordion-title input').prop("checked",true);
            }
            if($('.mattlebrands:checked').length == $('.mattlebrands:visible').length){
                $("#selectAllBrands").prop("checked","true");
            }
        });

        // To open the change email-pref radio btn section
        if($("#changeEmailPref").is(":checked")) {
            $('#changeEmailPrefOpt').show();
        }
    }

    ajaxCall(config, cb) {
        $.ajax({
            url: config.url,
            data: config.body || '',
            type: config.type,
            headers: config.headers,
            beforeSend: function(xhr) {
                if (config.beforeSend) {
                    xhr.setRequestHeader('Authorization', config.beforeSend);
                }
            },
            success: function(data) {
                if (typeof cb == "function") cb(data);
            },
            error: function(xhr, textStatus, errorThrown) {
                if (typeof cb == "function") cb(false, xhr.responseJSON || errorThrown);
            }
        });
    }

    radioBtnChange(ele) {
        if ($(ele).attr('id') == 'changeEmailPref') {
            $('#changeEmailPref').parents("label").removeClass("opaque-area");
            $('#changeEmailPrefOpt').removeClass("opaque-area");
        } else {
            $('#changeEmailPref').parents("label").addClass("opaque-area");
            $('#changeEmailPrefOpt').addClass("opaque-area");
        }
    }

    mattlebrandSelect(ele) {
        var $ele = $(ele);
        if ($ele.prop('checked') == true) {
            $ele.closest('li').addClass('check-active');
        } else {
            $ele.closest('li').removeClass('check-active');
        }
    }

    updateList(ele) {
        var el = $(ele);
        var formself = config;
        var PreID = [];
        var $InputStatus = el.parents('.email-preferences-content-block').find("#inputfield .checkbox:not(.hide) .mattlebrands");
        var isUnsubscribeOn = $(".unsubscribe").is(":checked");
        var $EmailPrefInputStatus = el.parents('.email-preferences-content-block').find(".emailpref-checkbox .primary-preference:not(.hide)");
        $EmailPrefInputStatus.each(function () {
            PreID.push({
                "PreferenceID": $(this).attr('data-id'),
                "PreferenceOptIn": $(this).prop('checked') == true && !isUnsubscribeOn ? "Y" : "N",
            })
        });
        $InputStatus.each(function () {
            PreID.push({
                "PreferenceID": $(this).attr('data-id'),
                "PreferenceOptIn": $(this).prop('checked') == true && !isUnsubscribeOn ? "Y" : "N",
            })
        });
        var apiParams = {
            "EmailAddress": config.useremail_encrypted,
            "RequestedBy": formself.RequestedBy,
            "consumerPreferences": {
                "PreferenceList": PreID
            }
        }
        var ajaxSettings = config.apiConfig("consumerinfoAPI");
        ajaxSettings.body = JSON.stringify(apiParams);
        self.ajaxCall(ajaxSettings, function (res, err) {
            if (res) {
                if ($(".unsubscribe").is(":checked")) {
                    location.href = config.unsubscribeRedirectUrl;
                } else if ($("#changeEmailPref").is(":checked")) {
                    location.href = config.changeEmailPrefRedirectUrl;
                }
            }
        });
    }
}

let self;
window.global.emailPreferences = window.global.emailPreferences || new emailPref();
window.global.emailPreferences.init();
