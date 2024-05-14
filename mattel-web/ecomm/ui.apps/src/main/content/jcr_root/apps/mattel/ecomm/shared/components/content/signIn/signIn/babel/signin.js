import ajaxRequest from '../shared/ajaxbinding';
import apiConfig from '../shared/apiConfig';
import fieldValidation from '../shared/inputvalidation';
import {setCookie, getCookie, deleteCookie} from '../shared/browserCookie';
import eventBinding from '../shared/eventBinding';
import {
    exceptionHandler
} from '../shared/flickerMessage';

export class loginFormAction {
    constructor() {
        self = this;
        evtBinding.bindLooping(this.bindingEventsConfig(), self);
        validate.editFormFields(".signin-form");
        self.checkAutoCompleteField();
        self.dataTimeout = $("#sessionTimeoutModal").attr("data-timeout");
        if(getCookie("MATTEL_WELCOME_MSG").trim()!=""){
            $('.signin-header').addClass('hide');
            $('.signout').removeClass('hide');
            self.startTimers();
        }
    }
    startTimers() {
        this.sessionTimer = setTimeout (()=>{ $("#sessionTimeoutModal").modal("show") }, self.dataTimeout);
    }
    checkAutoCompleteField(){
        if(autoFieldCheck>10) return;
        const $formFields = $(".signin-form .form-input");
        const $field =  $("#sign-email");
        if($field.val() || $field.is(":-webkit-autofill")){
            $formFields.addClass('not-empty');
        } else{
            autoFieldCheck++;
            setTimeout(function(){
                self.checkAutoCompleteField();
            },100);
        }
        const urlParam = urlName => {
            urlName = urlName.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
            let regexS = "[\\?&]"+urlName+"=([^&#]*)";
            let regex = new RegExp( regexS );
            let results = regex.exec( window.location.href);
            if( results )
                return results[1];
            else
                return results;
        } 
        if(urlParam("successmessage")) {
            exceptionHandler("success",decodeURI(urlParam("successmessage")));
        }
    }
    bindingEventsConfig() {
        let eventsArr = {
            "submit .signin-form": "submitSignInForm",
            "keyup .signin-form .input-field.has-error input": "inputChange",
            "change .signin-form .input-field input,.signin-form .input-field select": "inputChange",
            "mousemove .basepage-ecomm": "resetTimers",
            "keypress .basepage-ecomm": "resetTimers"
        }
        return eventsArr;
    }    
    resetTimers() {        
        window.clearTimeout(self.sessionTimer);
        self.startTimers();
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
            formElement.addClass('request-pending');
            cb(output, target);
        })
    }
    clearUserCookies(){
        let cookies = document.cookie.split(";");
        let i;
        for (i = 0; i < cookies.length; i++){
            if(cookies[i].indexOf('WC_AUTHENTICATION_')!=-1 || cookies[i].indexOf('MATTEL_CUSTOMER_SEGMENT')!=-1){
                deleteCookie(cookies[i]);
            }
        }
    }
    submitSignInForm(ele, evt) {
        evt.preventDefault();
        self.beforeSubmit(ele, function (res, formFields) {
            const formValue = {
                email: formFields.emailId.value.toLowerCase(),
                password: formFields.password.value
            }
            const payload = config('loginPayload').apply(formValue);

            request(payload).then(data => {
                let visitorStatusCookie = getCookie("MATTEL_VISITOR_STATUS");
                visitorStatusCookie ? setCookie("MATTEL_VISITOR_STATUS",encodeURIComponent("returning_visitor")) : setCookie("MATTEL_VISITOR_STATUS",encodeURIComponent("new_visitor"));

                $(ele).removeClass('request-pending');
                const errorMessage = data.errors && data.errors[0] && data.errors[0].errorMessage;
                if(!errorMessage){
                    window.location.href = formFields.action;
                }
            }).catch(error => {
                $(ele).removeClass('request-pending');
            })
        })
    }
}

let self,
    autoFieldCheck = 0;
const request = new ajaxRequest().ajaxCall;
const config = new apiConfig().getApiConfig;
const validate = new fieldValidation();
const evtBinding = new eventBinding();
let signIn = new loginFormAction();