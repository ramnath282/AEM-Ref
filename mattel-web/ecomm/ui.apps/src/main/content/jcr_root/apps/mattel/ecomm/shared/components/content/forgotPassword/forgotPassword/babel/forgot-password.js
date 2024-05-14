import ajaxRequest from '../shared/ajaxbinding';
import apiConfig from '../shared/apiConfig';
import fieldValidation from '../shared/inputvalidation';
import eventBinding from '../shared/eventBinding';
import {setCookie} from '../shared/browserCookie';

export class forgotPassword {
    constructor() {
        self = this;
        evtBinding.bindLooping(this.bindingEventsConfig(), self);
    }
    bindingEventsConfig() {
        let eventsArr = {
            "submit .forgot-password-form": "submitForm",
            "submit .create-new-password-form": "submitNewPasswordForm",
            "keyup .input-field.has-error input": "inputChange",
            "change .input-field input,.input-field select": "inputChange",
        }
        return eventsArr;
    }
    inputChange(ele) {
        validate.instantValidation(ele);
    }
    beforeSubmit(target, cb) {
        let formElement = $(target);
        validate.checkAllFields(formElement, function (output, formFields) {
            if (!output) {
                return;
            }
            cb(output, target)
        })
    }
    apiMessage(form, err) {
        let $ele = $(form).find('.api-error-message');
        if (err) {
            $ele.html(err).show();
            return;
        }
        $ele.hide();
    }
    submitForm(ele, evt) {
        evt.preventDefault();
        self.beforeSubmit(ele, function (res, formFields) {
            const payload = config('forgotPassword').apply(formFields);
            request(payload).then(data => {
                setCookie("MATTEL_USERID", data.userId);
                if (data.emailValidationProcessStatus){
                window.location.href="//"+window.location.host+$(".forgot-password-form").attr("data-success");
                }
            }).catch(error => {
                const {
                    errorMessage
                } = error.responseJSON.errors[0];
                console.log(errorMessage);
            })
        })
    }
    submitNewPasswordForm(ele, evt){
        evt.preventDefault();
        self.beforeSubmit(ele, function (res, formFields) {
            const payload = config('createNewPassword').apply(formFields);
            request(payload).then(data => {
                setCookie("MATTEL_USERID","",0);
                if (data.passwordChangeStatus) {
                    let erroMsg = JSON.parse(sessionStorage.getItem("errorList"));
                    window.location.href="//"+window.location.host+$(".create-new-password-form").attr("data-success")+"?successmessage="+erroMsg.FORGOT_PASSWORD_SUCCESS;
                }
            }).catch(error => {
                const {
                    errorMessage
                } = error.responseJSON.errors[0];
                console.log(errorMessage);
            })
        });
    }
}

let self;
const request = new ajaxRequest().ajaxCall;
const config = new apiConfig().getApiConfig;
const validate = new fieldValidation();
const evtBinding = new eventBinding();
let signIn = new forgotPassword();