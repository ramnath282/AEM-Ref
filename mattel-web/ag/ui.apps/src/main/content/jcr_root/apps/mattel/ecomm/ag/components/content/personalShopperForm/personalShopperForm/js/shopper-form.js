/**
 * shopper-form.js
 * Version 1.0
 */
(function (global, AGAEM) {
    var self,
        shopperForm = {
            el: '#shopper-request-form',
            lastEveTrack: '',
            bindingEventsConfig: function () {
                var events = {};
                events["keyup " + this.el + " .form-control"] = "inputFocus";
                events["click " + this.el + " select"] = "trackEvent";
                events["click " + this.el + " input[type='radio']"] = "trackEvent";
                events["click " + this.el + " #submit-form"] = "formSubmit";
                return events;
            },
            trackEvent: function (ele, evt) {
                var field = ele.getAttribute("name") || ele.getAttribute("id");
                self.lastEveTrack = field;
            },
            inputFocus: function (ele, evt) {
                var code = evt.keyCode || evt.which;
                if (code != '9') { // not tab key
                    self.validateInput(ele);
                }
                self.trackEvent(ele);
            },
            errorToggle: function (elm, isError) {
                var $inputParentElem = elm.parents('.form-group'),
                    errorClassName = "has-error";
                if (isError) {
                    $inputParentElem.addClass(errorClassName);
                } else {
                    $inputParentElem.removeClass(errorClassName).removeClass('has-api-error');
                }
            },
            clearElmError: function (elm) {
                elm.parents('.form-group').removeClass('has-error');
            },
            isEmail: function (email) {
                var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                return regex.test(email);
            },
            showLoading: function (ele) {
                var $elem = ele == undefined ? $(this.el).parents(".form-component") : ele.parents('.form-group'),
                    loadingClassName = "data-loading";
                $elem.addClass(loadingClassName);
            },
            hideLoading: function (ele) {
                var $elem = ele == undefined ? $(this.el).parents(".form-component") : ele.parents('.form-group'),
                    loadingClassName = "data-loading";
                $elem.removeClass(loadingClassName);
            },
            validateInput: function (elm) {
                var $elm = $(elm),
                    inputType = $elm.attr('type'),
                    inputVal = $.trim($elm.val()),
                    ret = true;
                if (elm.hasAttribute('required') && inputType != "email") {
                    var error = false;
                    if (inputVal == '') {
                        error = true;
                        this.errorToggle($elm, error);
                        return;
                    }
                    this.errorToggle($elm, error);
                }
                if (inputType == "email") {
                    this.errorToggle($elm, !this.isEmail(inputVal));
                } else {
                    this.clearElmError($elm);
                }
                // switch (inputType){
                //     case "email":
                //         this.errorToggle($elm,!this.isEmail(inputVal));
                //         break;

                //     default:
                //         this.clearElmError($elm);
                // }  
                return ret;
            },
            formSubmit: function (elem, evt) {
                self.showLoading();
                self.validateForm(elem, function (params) {
                    // evt.preventDefault();
                    if (!params) {
                        console.log("field validation error..")
                        setTimeout(function () {
                            self.hideLoading()
                        }, 500);

                        return;
                    }
                    //do ajax call
                    self.apiSubmitForTracking(params);
                });
            },

            // Function for Form Submit
            apiSubmitForTracking: function () {
                typeof shopperFormSubmit == "function" && shopperFormSubmit({
                    email: typeof sha256 == "function" && sha256($("#email").val()),
                    location: $("#store").val(),
                    anticipated_visit: $("#month").val() + '|' + $("#day").val() + '|' + $("#year").val(),
                    contact_method: $('.radio input[name="contactGroup"]:checked').val(),
                    first_visit: $('.radio input[name="visitGroup"]:checked').val(),
                    terms_and_condition: $('input[name="termsConditions"]').is(':Checked') ? 'yes' : 'no',
                    item_clicked: "Personal Shopper"
                });
            },

            // Function for Form failure
            apiFormFailureTracking: function (params) {
                typeof shopperFormSubmit == "function" && shopperFormFailure({
                    email: typeof sha256 == "function" && sha256($("#email").val()),
                    error_name: "",
                    error_field: params
                });
            },

            // Function for Form abandonment
            checkFormHasDataTracking: function () {
                var self = this;
                var obj = {};
                if (self.lastEveTrack != '') {
                    obj.type = "browser close";
                    obj.last_accessed_field = self.lastEveTrack;
                    typeof checkFormHasData == "function" && checkFormHasData(obj);
                    return true;
                } else {
                    return false;
                }
            },

            validateForm: function (elem, cb) {
                var $formEle = $(elem.form);
                var apiParams = {};
                var isFormError;
                var stickyHeaderHeight;

                _.each($formEle.find(".form-control"), function (inputEle) {
                    self.validateInput(inputEle);
                });
                isFormError = $formEle.find('.has-error').length;
                if (isFormError || !($('input[name="termsConditions"]').is(':Checked'))) {
                    var error_field = "";
                    var emailValue = $('#email');
                    $formEle.find('.has-error').each(function () {
                        if ($(this).find('.form-control').attr('name')) {
                            var field = $(this).find('.form-control').attr('name');
                            if (field == 'email' && emailValue.val()) {
                                error_field = error_field + "Email id not in proper format " + field + " | "
                            } else {
                                error_field = error_field + "empty " + field + " | "
                            }
                        }
                    });
                    var tnc = $('input[name="termsConditions"]').is(':Checked');
                    if (!tnc) {
                        error_field = error_field + "empty terms and conditions | "
                    }
                    error_field = error_field.substring(0, error_field.length - 2)
                    stickyHeaderHeight = $(".sticky-header").height() || 0;
                    if ($formEle.find('.has-error').length) {
                        $('html, body').animate({
                            scrollTop: ($formEle.find('.has-error').first().offset().top) - stickyHeaderHeight
                        }, 0);
                        $formEle.find('.has-error').first().find('input').focus();
                    }
                    self.apiFormFailureTracking(error_field)
                    cb(false);
                    return;
                }
                apiParams = {};

                if ($formEle[0].checkValidity()) {
                    cb(apiParams)
                } else {
                    self.hideLoading();
                }
            },
            init: function () {
                if (!AGAEM.isDependencyLoaded || !$(this.el).length) return;
                self = this;
                AGAEM.bindLooping(this.bindingEventsConfig(), this);
            }
        }
    shopperForm.init();
    document.addEventListener('DOMContentLoaded', function () {
        if (!AGAEM.isDependencyLoaded) {
            shopperForm.init();
        }
    }, false);

    window.addEventListener("beforeunload", function (e) {
        if (!$(this.el).hasClass("form-submitted") && shopperForm.checkFormHasDataTracking()) {
            var confirmationMessage = "\o/";
            (e || window.event).returnValue = confirmationMessage;
            return confirmationMessage;
        }
    });
}(window, AGAEM));