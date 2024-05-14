/**
   * crm-form.js
   * Version 1.0
   */
  (function (global, CRMAEM, cookie) {
    var form = {
        el: '#crm-form',
        // clientId : document.getElementById("formClientId").value,
        // secretId : document.getElementById("formClientSecret").value,
        countryCode : document.getElementById("formCountryCode").value,
        sourceId : document.getElementById("formSourceId").value,
        locale : document.getElementById("formLocale").value,
        pageLocale : document.getElementById("pageLocale").value,
        noSubscriptionId : document.getElementById("nosubscriptionId").value,
        apiBaseUrl : document.getElementById("formApiBaseUrl").value+"api/",
        tokenKeyName : 'mrs_token',
        belowChildAge : 10,
        cookieExpireDay : 1,
        cookieExpireDate : new Date().setDate(new Date().getDate() + 1),
        existEmail:'',
        childShowFlag: false,
        childDOBCount:0,
        childDetailClone: "",
        dobRequired: false,
        maxChildren:0,
        apiConfig : function(name){
            var obj = {
                "getAPIErrorMessage" : {
                  "url" : "/apierror.json",
                    "body" : "",
                    "type": "get",
                    "contentType": 'application/json'
                },
                "getGeoDetection" : {
                    "url" : "//geodetection.svc.mattelcloud.com/api/geoipcode",
                    "type": "get"
                }
            }
            return obj[name];
        },
        ajaxCall : function(obj,cb){
            $.ajax({
                url: obj.url,
                data: obj.body || '',
                type: obj.type,
                contentType: obj.contentType || "application/json",
                 beforeSend: function(xhr) {
                    if(obj.beforeSend){
                        xhr.setRequestHeader('Authorization', obj.beforeSend);
                    }
                },
                success: function(data) {
                if(data.status == 'SUCCESS') {
                  $('.thank-msg').show();
                  $('.form-component').hide();

                 }
                if (typeof cb == "function") cb(data);

                    },
                error: function(xhr, textStatus, errorThrown) {if (typeof cb == "function") cb(false,xhr.responseJSON || errorThrown);}
            });
        },
        bindingEventsConfig: function () {
            var events = {
                "keyup #crm-form .input_field": "inputFocus",
                "mouseover #crm-form .input_field": "inputHover",
                "change #crm-form input[type='radio']": "radioFocus",
                "click #crm-form #crm-form-submit" : "formSubmit",
                "click #crm-form #crm-form-reset" : "formReset",
                "change #shortCountryCode" : "updateCountryCodeValue",
                "change #countryDropdown" : "changeStateDropdown",
                "click #crm-form #addChild" : "addChild"
            }
            return events;
        },
        addChild : function(ele,evt){
            var self = CRMAEM.MRSForm;
            
            if(!self.childShowFlag) {
              var chidDetails = $(self.el).find("#child-details-wrapper .child-details-fieldset").eq(0);
                self.childDetailClone = chidDetails.clone();
                chidDetails.removeClass("hide");
                if(self.dobRequired) {
                  chidDetails.find(".child-dob").parents(".form-group").addClass("required");
                  chidDetails.find(".child-dob input").attr("required",true);
                }                  
                self.childShowFlag = true;
                self.childDOBCount +=1;
            } else {
               $(self.el).find(".add-child-option").before("<fieldset class='child-details-fieldset'>"+self.childDetailClone.html()+"</fieldset>");
                var  childParent = $(self.el).find("#child-details-wrapper .child-details-fieldset").eq($(self.el).find("#child-details-wrapper .child-details-fieldset").length-1);
                self.changeFieldId(childParent);
                self.childDOBCount +=1;
            }
            self.checkMaxChildren();
            evt.preventDefault();
        },
        checkMaxChildren: function(){
          if($(this.el).find("#child-details-wrapper .child-details-fieldset").length == this.maxChildren){
              $(this.el).find("#addChild").addClass("hide");
          } else {
              $(this.el).find("#addChild").removeClass("hide");
          }
        },
        changeFieldId : function(el){
          var self = CRMAEM.MRSForm;
          el.find(".child-data").each(function(){                
              if($(this).hasClass("child-gender") || $(this).hasClass("child-relation")){                    
                  $(this).find(".item-list").each(function(){
                      var nameProp = $(this).find("input").attr("name")+self.childDOBCount;
                      var idProp = $(this).find("input").attr("id")+self.childDOBCount;
                      var forProp = $(this).find("label").attr("for")+self.childDOBCount;
                      $(this).find("input").attr({name: nameProp,id: idProp});
                      $(this).find("label").attr("for",forProp);
                  });
              }else {
                  var nameProp = $(this).find("input").attr("name")+self.childDOBCount;
                  var idProp = $(this).find("input").attr("id")+self.childDOBCount;
                  var forProp = $(this).prev("label").attr("for")+self.childDOBCount;
                  if(self.dobRequired) {
                      $(this).parents(".form-group").addClass("required");
                      $(this).find("input").attr("required",true);
                  }
                  $(this).find("input").attr({name: nameProp,id: idProp});
                  $(this).prev().attr("for",forProp);
              }
          });
          var datebinding = this.bindDatePicker(true);
            datebinding && datebinding
                .keyup(function(ele){
                    self.dobValidation(ele,this)
                }).change(function(ele){
                    self.dobValidation(ele,this)
                });
          
          
        },
        updateCountryCodeValue : function(ele){
            var $parentElem = $(ele).closest(".relative-dropdown-container"),
                $targetElem = $parentElem.find("#relative-country-code");

            $targetElem.text($(ele).find(":selected").data('code'));
        },
        formSubmit : function(elem,evt){
            var self = CRMAEM.MRSForm,
                $parentElem = $(elem).parents(".form-action-btn");
            self.showLoading();
            self.validateForm(elem, function(params) {
                evt.preventDefault();
                if(!params){
                    console.log("field validation error..")
                    setTimeout(function(){self.hideLoading()},500);
                    return false;
                }
                if(!window.global.CMFormSubmit){
                    console.log("CM Error: CommonDependency code not found.");
                    return;
                }
                window.global.CMFormSubmit(params,function(res, data){
                    self.hideLoading();
                    if(res === "success"){
                        $('.thank-msg').show();
                        $('.form-component').hide();
                        $parentElem.removeClass("api-fails");
                        self.apiSuccessForTracking(params);
                        $(self.el).addClass("form-submitted");
                        $('.thank-msg').show();
                        $('.form-component').hide();
                        if(elem.hasAttribute("data-action"))
                            window.location = $(elem).data("action");
                    } else{
                        console.log("CM API Fails. " + data);
                        $parentElem.addClass("api-fails");
                    }
                });
            });
        },
        apiSuccessForTracking : function(params){
            typeof apiSuccessForm == "function" && apiSuccessForm ({
                customer_email_hash: typeof sha256 == "function" && sha256(params.consumerDetails.EmailAddress),
                customer_id: '',
                source_id: $("#formSourceId").val(),
                subscription_ids: '117'


            })
        },
        apiErrorForTracking : function(errorCode,errorMessage,email){
            typeof apiFormError == "function" && apiFormError ({
                error_code: errorCode,
                error_message : errorMessage
            },typeof sha256 == "function" && sha256(email));
        },
        clientErrorForTracking : function(errorName,errorField,email){
            typeof clientFormValidationError == "function" && clientFormValidationError ({
                error_name: errorName,
                error_field : errorField
            },typeof sha256 == "function" && sha256(email));
        },
        formReset : function(ele,evt){
            var $removeAddedChild = $("#child-details-wrapper fieldset:not(.sub-fieldset):not(:first-child)");
            $("#child-details-wrapper fieldset:not(.sub-fieldset)").removeClass("child-data-saved");
            $removeAddedChild.length && $removeAddedChild.remove();
        },
        displayAPIErrors : function(errCode,errMsg, isFirstErr,parentElem){
            var apierrorcopy = this.apiMessages && this.apiMessages[errCode];
            switch(errCode){
                case "1022":
                case "1009":
                    this.errorAPIToggle($(".child-dob"),true, apierrorcopy);
                    if(isFirstErr) $(".child-dob input").focus();
                    break;
                
                case "1010":
                case "1017":
                case "1018":
                case "1019":
                case "1020":
                case "1021":
                    $(parentElem).attr('data-content',apierrorcopy).addClass("api-fails");
                    break;

                case "1004":
                case "1029":
                    self.existEmail = $("#emailId").val();
                    this.errorAPIToggle($("#emailId"),true, apierrorcopy);
                    if(isFirstErr) $("#emailId").focus();
                break;
            }
        },
        inputFocus : function(ele,evt){
          var code = evt.keyCode || evt.which;
          if (code != '9') { // not tab key
              CRMAEM.MRSForm.validateInput(ele);
          }
        },
        inputHover : function(ele,evt){
          var title= $(ele).attr("placeholder");  
          var formError = $(ele).parent().find(".form-error");
          var apiError = $(ele).parent().find(".api-error");
          if($(ele).val()!="") {
              title ="";
          }
          if(formError.is(":visible")) {
              title = $(formError).text();
          }
          if(apiError.is(":visible")) {
              title = $(apiError).text();
          }
          $(ele).attr("title", title);
        },
        radioFocus: function(ele,evt){
            var code = evt.keyCode || evt.which;
            if (code != '9') { // not tab key
                CRMAEM.MRSForm.validateInput(ele);
            }
        },
        addRequiredAttr: function(inputEle){
            $(inputEle).attr('required',true);
            $(inputEle).parents('.form-group').addClass('required');
        },
        removeRequiredAttr: function(inputEle){
            $(inputEle).attr('required',false);
            $(inputEle).parents('.form-group').removeClass('required');
        },
        isEmail : function(email) {
            var regex = /^\b[A-Z0-9._%-](?!.*?\.\.).*?[A-Z]+@[A-Z]+[A-Z0-9-]+\.[A-Z]{2,4}\b$/i;
            return regex.test(email);
        },
        isPhoneNumber : function(num) {
            var regex = /([0-9]{10})|(\([0-9]{3}\)\s+[0-9]{3}\-[0-9]{4})/;
            return regex.test(num);
        },
        isValidDate : function(dateString,format) {
            var m = dateString.match(/[0-9]{2}[/][0-9]{2}[/][0-9]{4}$/);
            return (!m) ? true : false;
        },
        isvalidZipCode : function(val){
            var regex = /^[A-Za-z0-9 ]*$/;
            return regex.test(val);
        },
        showLoading : function(ele){
            var $elem = ele == undefined ? $(this.el).parents(".form-component") : ele.parents('.form-group'),
                loadingClassName = "data-loading";
            $elem.addClass(loadingClassName);
        },
        hideLoading : function(ele){
            var $elem = ele == undefined ? $(this.el).parents(".form-component") : ele.parents('.form-group'),
                loadingClassName = "data-loading";
            $elem.removeClass(loadingClassName);
        },
        errorToggle : function(elm,isError) {
            var $inputParentElem = elm.parents('.form-group'),
                hasReqAttribute = $(elm).attr('required'),
                errorClassName = "has-error";
            if(isError){
                $inputParentElem.addClass(errorClassName);
                if (elm.val()=="" && typeof hasReqAttribute !== typeof undefined && hasReqAttribute !== false) {
                    $inputParentElem.addClass('has-required-error');
                }
            } else{
                $inputParentElem.removeClass(errorClassName).removeClass('has-api-error has-required-error');
            }
        },
        errorAPIToggle : function(elm,isError,msgCopy) {
            var $inputParentElem = elm.parents('.form-group'),
                errorClassName = "has-api-error";
            if(isError){
                if(msgCopy) $inputParentElem.find('.api-error').html(msgCopy);
                $inputParentElem.addClass(errorClassName);
            } else{
                $inputParentElem.removeClass(errorClassName);
            }
        },
        clearElmError : function(elm) {
            elm.parents('.form-group').removeClass('has-error');
        },
        validateInput : function(elm){
            var $elm = $(elm),                
                inputType = $elm.attr('type'),
                inputVal = $.trim($elm.val()),
                ret = true;
            if (elm.hasAttribute('required') && inputType!="email") {
                var error = false;
                if(inputVal == ''){
                    error = true;
                    this.errorToggle($elm, error);
                    return;
                }
                if($elm.is(".child-dob input") && $elm.parents(".has-error").length){
                    error = true;
                    this.errorToggle($elm, error);
                    return;
                }
                this.errorToggle($elm, error);
            } 
            switch (inputType){
                case "email":
                    this.errorToggle($elm,!this.isEmail(inputVal));
                    if(self.existEmail){
                      if(self.existEmail == inputVal){
                          this.errorAPIToggle($elm, true);
                      } else{
                          this.errorAPIToggle($elm, false);
                      }
                    } 
                    break;

                case "tel":
                    this.errorToggle($elm,inputVal!="" && !this.isPhoneNumber(inputVal));
                    break;

                case "text":
                    if(inputVal!= ''){
                        if($elm.is("#postalCode") && !this.isvalidZipCode(inputVal)){
                            this.errorToggle($elm, true);
                        } else if($elm.is(".child-dob input") && $elm.parents(".has-api-error").length){
                             this.errorAPIToggle($elm, false);
                        } 
                        else if(!elm.hasAttribute('required') && !$elm.is(".child-dob input")){
                            this.errorToggle($elm, false);
                        }
                    } else if($elm.is("#postalCode")){
                        this.errorToggle($elm, false);
                    }
                    break;
                
                default:
                    this.clearElmError($elm);
            }  
            return ret;
        },
        validateRadio : function(ele){
            var $elm = $(ele);
            this.errorToggle($elm,false);
            return true;
        },
        validateChildFields: function(){
            var self = CRMAEM.MRSForm;
            var $childGender = $(".child-gender input");
            var $childRelation = $(".child-relation input");
            var $childDOB = $(".child-dob input");
            if($childDOB.val()!="" && !$childGender.is(":checked") && !$childRelation.is(":checked")){
                self.errorToggle($childGender, true);
                console.log("gender or relationship to be filled..")
            } else if(($childGender.is(":checked") || $childRelation.is(":checked")) && $childDOB.val()==""){
                console.log("DOB to be filled..")
                self.errorToggle($childDOB, true);
            }
        },
        validateForm : function(elem,cb){
            var self = this;
            var $formEle =$(elem.form);
            var apiParams = {};
            var isAPIError = $formEle.find('.has-api-error').length;
            var isFormError;

            if (isAPIError) {
                $('html, body').animate({scrollTop: ($formEle.find('.has-api-error').last().offset().top)}, 'slow');
                $formEle.find('.has-api-error').first().find('input').focus();
                cb(false);
                return;
            }

            _.each($formEle.find(".input_field"),function(inputEle){
                self.validateInput(inputEle);
            });
            // prevent form sumbition if has error\
            isFormError = $formEle.find('.has-error').length;
            if (isFormError) {
                $('html, body').animate({scrollTop: ($formEle.find('.has-error').first().offset().top)}, 'slow');
                $formEle.find('.has-error').first().find('input').focus();
                var errorName = "",
                    errorField = "",
                    $errorFieldElement,
                    $errorNameElememt;

                $formEle.find('.has-error').filter(function(){
                    $errorNameElememt = $(this).find(".form-error").text();
                    $errorFieldElement = $(this).find(".input_field").attr("id");
                    errorName= errorName=="" ? $errorNameElememt  : errorName+'|'+$errorNameElememt;
                    errorField= errorField=="" ?  $errorFieldElement : errorField+'|'+$errorFieldElement;
                })
                self.clientErrorForTracking(errorName, errorField, $('#emailId').val());
                cb(false);
                return;
            }
            var stateVal = $('#stateField [aria-labelledby="stateDropdown"]').filter(':visible').val() || '';
            var countryCodeVal = $("#countryDropdown").val();
            apiParams = {
                SourceID: this.sourceId || '',
                consumerDetails : {
                    EmailAddress: $formEle.find("#emailId").val(),
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
                    consumerChild: self.getChildDetailsParams(),
                    consumerPreferences: self.getPreferences($formEle),
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
                    customAttributes: {"AttributeList": [{
                        CustomFieldName: "comments",
                        CustomFieldValue: $('#comment').val() || '',
                        SourceID: this.sourceId || ''
                      }]}
                  }
            }
            if($formEle[0].checkValidity()){
                cb(apiParams)
            } else{
                self.hideLoading();
            }
        },
        getChildDetailsParams : function(){
            var childrenArray = [];
            _.each($('#child-details-wrapper fieldset:not(.sub-fieldset)'),function(item){
                var childrenSubObj = {};
                _.each($(item).find(".child-data"), function(subItem){
                    var isoDate;
                    var datearray;
                    var date;
                    if(!_.isEmpty($(subItem).find("input[type='radio']:checked").val()) || !_.isEmpty($(subItem).find("input[type='number']").val()) || !_.isEmpty($(subItem).find("input[type='text']").val())){
                        if($(subItem).hasClass("child-gender")){
                            childrenSubObj.ChildGender = $(subItem).find("input[type='radio']:checked").val() || '';
                        } else if($(subItem).hasClass("child-relation")){
                            childrenSubObj.Relationship = $(subItem).find("input[type='radio']:checked").val() || '';
                        } else if($(subItem).hasClass("child-dob")){
                            if($(subItem).find("input").val()!=''){
                                  datearray = $(subItem).data("date-format").toLowerCase()=="dd/mm/yyyy" ? $(subItem).find("input").val().split("/") : false;
                                  date = new Date(datearray &&  datearray[1] + '/' + datearray[0] + '/' + datearray[2] || $(subItem).find("input").val());
                                  isoDate = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString();
                                childrenSubObj.ChildDOB = isoDate.split('T')[0];
                            } else{
                                childrenSubObj.ChildDOB = '';
                            }
                        } else if($(subItem).hasClass("child-first-name")){
                            childrenSubObj.ChildFirstName = $(subItem).find("input[type='text']").val() || '';
                        }
                    }
                    
                })
                !_.isEmpty(childrenSubObj) && childrenArray.push(childrenSubObj)
            });
            return {
                ChildList: this.changeRelationVal(childrenArray)
            };
        },
        changeRelationVal : function(arr) {
            for(var i=0; i<arr.length; i++) {
              switch(arr[i].Relationship) {
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
                  arr[i].Relationship = "FRIEND / FAMILY MEMBER";
              }
            }
            return arr;
        },
        getPreferences : function(ele){
            var localeName = (this.pageLocale || "us").toLowerCase(),
                isDoubleOptionEnabled = (localeName == "de"),
                $checkboxElems = $(ele).find(".checkbox.terms"),
                arr = [],
                $checkboxElem,
                subscriptionId,
                isContainMultipleIds;
            
            _.each($checkboxElems, function(item) {
                $checkboxElem = $(item).find('[type="checkbox"]');
                subscriptionId = $checkboxElem.data("subscriptionId") || $("#nosubscriptionId").val();
                isContainMultipleIds =
                    subscriptionId && subscriptionId.toString().split(",");
                if ($checkboxElem.is(":checked")) {
                    for (var i = 0; i < isContainMultipleIds.length; i++) {
                    if (isDoubleOptionEnabled) {
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
            return {
                PreferenceList: arr
            };
        },
        bindDatePicker : function(hideFutureDate,triggerChange){
            var $el = hideFutureDate!=undefined ? $(".date-picker") : $(".date-picker:not('.active')"),
                startYear = (new Date()).getFullYear() - this.belowChildAge || 14;

            if(!$el.length) return false;
            if(hideFutureDate!=undefined){
                $el.datepicker('destroy');
            }
            var dateBinded = $el.datepicker({
                startDate: new Date(startYear+'-01-01'), //set start date
                autoclose: true,
                forceParse: false
            }).addClass('active');
            if(triggerChange) dateBinded.trigger("change");
            return dateBinded;
        },
        changeStateDropdown : function(ele){
            var $el = $("#stateField"),
                selectedVal = $(ele).val();
            $el.attr('data-country-selected',selectedVal);
            if($("#"+selectedVal+"-states").length){
                $("#"+selectedVal+"-states option").prop('selected', function() {
                    return this.defaultSelected;
                });
            } else{
                $("#others-states").val('');
            }

        },
        loadStateDropdown : function(){
            this.changeStateDropdown($("#countryDropdown"))
        },
        loadAPIerrormessage : function(){
            var self = this;
            var ajaxSettings = self.apiConfig("getAPIErrorMessage");
            self.ajaxCall(ajaxSettings,function(res,err){
                if(!res){
                    console.log("API error message data fails..error is "+ err);
                    return;
                }
                self.apiMessages = res;
            });
        },
        loadAxChanges : function(){
            var $targetTextAddedinHref = $('[data-href-target]');
            if(!$targetTextAddedinHref.length || !$targetTextAddedinHref.find("a[target='_blank']").length) return;
            $targetTextAddedinHref.find("a[target='_blank']").append("<span class='sr-only'>"+$targetTextAddedinHref.data('hrefTarget')+"</span>");
        },
        checkFormHasDataTracking : function(){
            var $form = $(this.el);
            var $inputField = $form.find("input[type='text'], input[type='email']").filter(function() { return $(this).val()!="" && $(this) });
            var obj = {};
            if($inputField.length){
                obj.type = "browser close";
                obj.last_accessed_field =  $inputField[$inputField.length-1].getAttribute("id") || $inputField[$inputField.length-1].getAttribute("name");
                typeof checkFormHasData == "function" && checkFormHasData (obj);
                // fn(obj);
                return true;
            } else{
                return false;
            }
        },
        getCountryCode : function(){
            var self = this;
            var cookieName = "country-code";
            var isValInCookie = cookie.get(cookieName);
            if(isValInCookie){
                CRMAEM.geoCountryCode = isValInCookie;
                this.preCheckedUS(this.geoCountryCode);
            } else{
                var ajaxSettings = this.apiConfig("getGeoDetection");
                var obj = {};
                this.ajaxCall(ajaxSettings,function(res,err){
                    if(!res){
                        console.log("Geo Detection API fails..error is " + err)
                        return;
                    }
                    CRMAEM.geoCountryCode = JSON.parse(res).CountryCode;
                    obj.expires=self.cookieExpireDate;
                    cookie.prepend(cookieName, self.geoCountryCode,obj)
                    self.preCheckedUS(self.geoCountryCode);
                })
            }

        },
        preCheckedUS : function(countryCode){
            var $checkboxElem = $("#agreeEmailNotifcation");
            if(countryCode=="US")
                $checkboxElem.attr("checked",true);
        },
        customBindEvent : function(){
            var self = this;
            $('#parentFirstName, #parentLastName').on("keypress", function(key) {
                if (key.charCode != 13 && (key.charCode < 97 || key.charCode > 122) && (key.charCode < 65 || key.charCode > 90) && (key.charCode != 45) && (key.charCode != 32) && (key.charCode != 0)  ) return false;
            });
            $('#contactNumber').on("keypress", function(event) {
                var key = window.event ? event.keyCode : event.which;
                var key_codes = [48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 0, 8, 13];

                if (!($.inArray(key, key_codes) >= 0)) {
                   event.preventDefault();
                 }
            });
              $('#crm-form').on("click",".edit-child-option", function(event) {
                  var index = $('#crm-form .edit-child-option').index(this);
                  $("#child-details-wrapper .child-details-fieldset").eq(index).remove();
                  self.checkMaxChildren();
                  event.preventDefault();
              });
        },
        dobValidation : function(ele,curElem){
            var self = this,
                $dobEle = $(ele.currentTarget).find('input'),
                options = $(curElem).data('datepicker').o,
                endDate = options.endDate,
                datearray = options.format.toLowerCase()=="dd/mm/yyyy" ? $dobEle.val().split("/") : false,
                newdate = datearray &&  datearray[1] + '/' + datearray[0] + '/' + datearray[2] || $dobEle.val(),
                val = newdate,
                isValidDate = self.isValidDate(val);
            // if(!isNaN(val) && val.length<4) return;
            if((val != "" && ((String(new Date(val))=="Invalid Date") || (new Date(endDate) != "Invalid Date" && (+new Date(val) > +new Date(endDate))))) || ( val != "" && isValidDate)){
                self.errorToggle($dobEle, true);
                $(curElem).datepicker('hide');
            }else{
                self.errorToggle($dobEle, false);
            }
        },
        render: function(){
            var self  = this;
            this.loadStateDropdown();
            var datebinding = this.bindDatePicker(true);
            datebinding && datebinding
                .keyup(function(ele){
                    self.dobValidation(ele,this)
                }).change(function(ele){
                    self.dobValidation(ele,this)
                });
            this.loadAPIerrormessage();
            this.loadAxChanges();
            this.getCountryCode();
            if($(this.el).find(".child-details-fieldset").length) {
              if($(this.el).find(".child-details-fieldset .child-dob").parents(".form-group").hasClass("required")) {
                  $(this.el).find(".child-details-fieldset .child-dob").parents(".form-group").removeClass("required");
                  $(this.el).find(".child-details-fieldset .child-dob input").removeAttr("required");
                  this.dobRequired = true;
              }
            }
            this.maxChildren = parseInt($(this.el).find("#child-details-wrapper").attr("data-max-children"));              
        },
        init: function () {
            if (!CRMAEM.isDependencyLoaded || !$(this.el).length || CRMAEM.form) return;
            CRMAEM.bindLooping(this.bindingEventsConfig(), this);
            this.customBindEvent();
            this.render();
        }
    }
    CRMAEM.MRSForm = form;
    form.init();
    document.addEventListener('DOMContentLoaded', function () {
        if (!CRMAEM.isDependencyLoaded) {
            form.init();
        }
    }, false);
    window.addEventListener("beforeunload", function (e) {
      if(!$(CRMAEM.MRSForm.el).hasClass("form-submitted") && CRMAEM.MRSForm.checkFormHasDataTracking()){
          var confirmationMessage = "\o/";

          (e || window.event).returnValue = confirmationMessage; 
          return confirmationMessage;  
      }

    });

}(window, CRMAEM, CRMAEM.cookie));
