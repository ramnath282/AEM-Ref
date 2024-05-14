/**
 * email-pref.js
 * Version 1.1 - Remove the PLAYAEM dependencies and make it global
 */
(function(global) {
    var emailPreference = {
        el: '.email-preferences-component',
        api_Url: $("#formApi-url").val(),
        api_key: $("#formApi-key").val(),
        apipref_Url: $("#prefApi-url").val(),
        apipref_key: $("#prefApi-key").val(),
        changeEmailPrefRedirectUrl: $("#redirect_brands").val(),
        unsubscribeRedirectUrl: $("#redirect_unsubscribe").val(),
        useremaiID:$('#useremaiID').val(),
        RequestedBy: $('#requestedBy').val() || 'AEM',
        checkedField: 'Y',
        checkedStatus: true,

        apiConfig: function(name) {
            var obj = {
				//To get master list prefs
                "consumerPreferences": {
                    //"url": this.api_Url + "qa/preferences",
                    "url": this.apipref_Url,
					"body": "",
                    "type": "get",
                    "headers": {
                        "api_key": this.apipref_key,
                        "contentType": "application/json",
                    },
                },
				//pass emailaddress to fetch user preferences
                "userpreferences": {
                    "url": this.api_Url,
                    //"url": "https://api.sdn.mattel.com/preprod/consumerpreferences",
					"body": "",
                    "type": "get",
                    "headers": {
                        "EmailAddress": this.useremaiID,
						"api_key": this.api_key,
						//"api_key": "5km3zm8mrnbfkmnawd2fjjqd",
                        "contentType": 'application/json',
                    },
                },
				//Submit the preference
				"consumerinfoAPI": {
                    "url": this.api_Url,
					//"url": "https://api.sdn.mattel.com/preprod/consumerpreferences",
                    "type": "POST",
					"body": "",
					"headers": {
						"api_key": this.api_key,
						//"api_key": "5km3zm8mrnbfkmnawd2fjjqd",
						"Content-Type":"application/json",
					},
                },
				
			}
            return obj[name];
        },
        bindingEventsConfig: function() {
            var events = {
                "click input[type='radio']": "radioBtnChange",
                "click #selectAllBrands": "selectAll",
                "click .mattlebrands": "mattlebrandSelect",
                "click .accordion-title input": "brandListSelect",
                "click #update-btn": "updateList"
            }
            return events;
        },
        ajaxCall: function(obj, cb) {
            $.ajax({
                url: obj.url,
                data: obj.body || '',
                type: obj.type,
                headers: obj.headers,
                beforeSend: function(xhr) {
                    if (obj.beforeSend) {
                        xhr.setRequestHeader('Authorization', obj.beforeSend);
                    }
                },
                success: function(data) {
                    if (typeof cb == "function") cb(data);
                },
                error: function(xhr, textStatus, errorThrown) {
                    if (typeof cb == "function") cb(false, xhr.responseJSON || errorThrown);
                }
            });
        },

        radioBtnChange: function(ele, evt) {
            if ($(ele).attr('id') == 'changeEmailPref') {
                $('#changeEmailPref').parents("label").removeClass("opaque-area");
                $('#changeEmailPrefOpt').removeClass("opaque-area");
            } else {
                $('#changeEmailPref').parents("label").addClass("opaque-area");
                $('#changeEmailPrefOpt').addClass("opaque-area");
            }
        },
        selectAll: function(ele, evt) {
            if (ele.checked) {
                // Iterate each checkbox
                $('.mattlebrands:visible').each(function() {
                    this.checked = true;
                });
                $('.accordion-title input').prop("checked",true);
            } else {
                $('.mattlebrands:visible').each(function() {
                    this.checked = false;
                });
                $('.accordion-title input').prop("checked",false);
            }
        },
		
		mattlebrandSelect: function(ele, evt) {
            if($('.mattlebrands:visible').length == $('.mattlebrands:visible:checked').length) {
                $("#selectAllBrands").prop("checked",true);
			} else {
                $("#selectAllBrands").prop("checked",false);
            }

            if($('.mattlebrands:checked').length > 0) {
                $('.accordion-title input').prop("checked",true);
            } else {
                $('.accordion-title input').prop("checked",false);
            }
        },
        brandListSelect: function(ele, evt) {
            if (ele.checked) {
                // Iterate each checkbox
                $('.mattlebrands:visible').each(function() {
                    this.checked = true;
                });
                $("#selectAllBrands").prop("checked",true);
            } else {
                $('.mattlebrands:visible').each(function() {
                    this.checked = false;
                });
                $("#selectAllBrands").prop("checked",false);
            }
        },
        loadData: function() {
            self.ajaxCall(self.apiConfig("consumerPreferences"), function(res, err) {
                var preferenceIds = _.map(res, 'PreferenceID');
                $(".mattlebrands").each(function(k, val) {
                    var dataId = parseInt($(val).attr('data-id'));
                    if (preferenceIds.indexOf(dataId) == -1) {
                        $(val).closest(".checkbox").addClass('hide');
                    }
                });

                $(".primary-preference").each(function(k, val) {
                    var dataId = parseInt($(val).attr('data-id'));
                    if (preferenceIds.indexOf(dataId) == -1) {
                        $(val).closest(".checkbox").addClass('hide');
                    }
                });
                self.getValueChecked();
            });
            self.ajaxCall(self.apiConfig("userpreferences"), function(res, err) {
                $(res.consumerPreferences.PreferenceList).each(function(indx, obj) {
                    if (obj.PreferenceOptIn == "Y") {
                        $("[data-id='" + obj.PreferenceID + "']").prop("checked", true);
                    } else if (obj.PreferenceOptIn == "N") {
                        $("[data-id='" + obj.PreferenceID + "']").prop("checked", false);
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
                $(".accordion-content").hide();
                $(".accordion .arrow").removeClass("arrowUp").addClass("arrowDown");         
            }
        },
        getValueChecked: function() {
            $('.mattlebrands').each(function(i, el) {
                var inputField = $(el);
                if (inputField.attr('data-OptIn') === 'Y') {
                    inputField.prop('checked', true);
                }

            });
        },
        updateList: function(ele) {
            var $ele = $(ele);
            var PreID = [];
            var $InputStatus = $ele.parents('.email-preferences-content-block').find("#inputfield .checkbox:not(.hide) .mattlebrands");
            var isUnsubscribeOn = $(".unsubscribe").is(":checked");

            var $EmailPrefInputStatus = $ele.parents('.email-preferences-content-block').find(".emailpref-checkbox .primary-preference:not(.hide)");

            $EmailPrefInputStatus.each(function() {
                PreID.push({
                    "PreferenceID": $(this).attr('data-id'),
                    "PreferenceOptIn": $(this).prop('checked') == true && !isUnsubscribeOn ? "Y" : "N",
                })
            });

            $InputStatus.each(function() {
                PreID.push({
                    "PreferenceID": $(this).attr('data-id'),
                    "PreferenceOptIn": $(this).prop('checked') == true && !isUnsubscribeOn ? "Y" : "N",
                })
            });


            
            var apiParams = {
                "EmailAddress": self.useremaiID,
                "RequestedBy": self.RequestedBy,
                "consumerPreferences": {
                    "PreferenceList": PreID
                }
            }
            var ajaxSettings = self.apiConfig("consumerinfoAPI");
            ajaxSettings.body = JSON.stringify(apiParams);
            self.ajaxCall(ajaxSettings, function(res, err) {
                if(res) {
                    if($(".unsubscribe").is(":checked")){
                        location.href = emailPreference.unsubscribeRedirectUrl;
                    }else if($("#changeEmailPref").is(":checked")) {
                        location.href = emailPreference.changeEmailPrefRedirectUrl;
                    }
                }
            });
            
        },
        render: function() {
            window.global.eventBindingInst.bindLooping(this.bindingEventsConfig(), self);
            self.loadData();
        },
        init: function() {
            if (!$(this.el).length || window.global.emailPreference) return;
            self = this;
            self.render();
        }
    }
    var self;
    emailPreference.init();
    window.global.emailPreference = window.global.emailPreference || emailPreference;
    
}(window));
