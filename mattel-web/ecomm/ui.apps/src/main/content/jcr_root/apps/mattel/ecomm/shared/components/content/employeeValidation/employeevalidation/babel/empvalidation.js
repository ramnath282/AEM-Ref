import ajaxRequest from '../shared/ajaxbinding';
import apiConfig from '../shared/apiConfig';
import fieldValidation from '../shared/inputvalidation';
import eventBinding from '../shared/eventBinding';
import constant from '../shared/constant';

export class employeeFormAction {
    constructor() {
        self = this;
        evtBinding.bindLooping(this.bindingEventsConfig(), self);
    }
    bindingEventsConfig() {
        let eventsArr = {
            "submit .employee-signin-form": "submitSignInForm",
            "keyup .employee-signin-form .input-field.has-error input": "inputChange",
            "change .employee-signin-form .input-field input,.employee-signin-form .input-field select": "inputChange",
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
            formElement.addClass('request-pending');
            cb(output, target);
        })
    }
    submitSignInForm(ele, evt) {
        evt.preventDefault();
        self.beforeSubmit(ele, function (res, formFields) {
           
            const isAnonymousUser = document.cookie.indexOf(constant.unknownUserCookieName || "WC_AUTHENTICATION_-1002") != -1;
            const noUser = document.cookie.indexOf(constant.guestCookieName || "WC_AUTHENTICATION_") == -1;
            if (isAnonymousUser || noUser) {
                request(config("getGuestIdentity"))
                    .then(data => {
                    	self.preparePayload(formFields.employeeId.value, data.userId, ele, formFields)
                    })
                    .catch(error => {
                        console.log('error', `Guest API's failed..`);
                    });
                return;
            } else{
            	let readcookie = self.getUserId();
            	if(readcookie != undefined){
            		self.preparePayload(formFields.employeeId.value, readcookie, ele, formFields);
            	}
            }
        })
    }
    
    getUserId(){
    	return document.cookie.split(';').filter(function(c) {
    	    return c.trim().indexOf('WC_AUTHENTICATION_') === 0;
    	}).map(function(c) {
    	    return c.trim().split('=')[0].split('_')[2];
    	});
    }
    
    preparePayload(employeeId, userId, ele, formFields){
    	const formValue = {
                employeeId: employeeId,
                userId: userId
           }
        const payload = config('employeeValidation').apply(formValue);
    	self.callEmployeeAPI(payload, ele, formFields.action);
    }
    
    callEmployeeAPI(options, ele, redirectUrl) {
        request(options).then(data => {
            $(ele).removeClass('request-pending');
            window.location.href = redirectUrl;
        }).catch(error => {
            $(ele).removeClass('request-pending');
        })
    }

}

let self;
const request = new ajaxRequest().ajaxCall;
const config = new apiConfig().getApiConfig;
const validate = new fieldValidation();
const evtBinding = new eventBinding();
let signIn = new employeeFormAction();