import apiConfig from '../shared/apiConfig';
import fieldValidation from '../shared/inputvalidation';
import ajaxRequest from '../shared/ajaxbinding';

class agSignIn {
	constructor() {
		self = this;
		this.element = ".signout";
		$('#sessionTimeoutModal.timeout-modal').on('hidden.bs.modal', function () {
			if (window.location.href.indexOf('?') > -1 && window.location.href.indexOf('mode=auth') == -1 ) {
				var uri = window.location.href.toString();
				if (uri.indexOf("?") > -1) {
					var clean_uri = uri.substring(0, uri.indexOf("?"));
					window.location = clean_uri;
					window.history.replaceState({}, document.title, clean_uri);
				}
			}
		})
		self.url = window.location.href;
	}
	init() {
		if (this.element.length) {
			window.global.eventBindingInst.bindLooping({
				"submit .signin-form": "submitSignInForm",
				"keyup .signin-form .input-field.has-error input": "inputChange",
				"change .signin-form .input-field input,.signin-form .input-field select": "inputChange"
			}, self);

			if (self.url.indexOf('mode=auth') != -1 || self.url.indexOf('authenticated=true') != -1) {
				if(!self.IsloginUser()){
					$('#sessionTimeoutModal').modal('show');
				}
			}
		}
	}
	IsloginUser() {
		const {userName} = window.global.getUserCookie();
        if (userName) {
            return true;
        }
        return false;
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
			const $btnEle = $(ele).find('input[type="submit"]');
			const formValue = {
				"email": formFields.emailId.value.toLowerCase(),
				"password": formFields.password.value
			}
			const payload = apiConfigInst.getApiConfig('loginPayload').apply(formValue);
			$btnEle.attr('disabled',true);
			request.ajaxCall(payload)
				.then(data => {
					$(ele).removeClass('request-pending');
					const errorMessage = data.errors && data.errors[0] && data.errors[0].errorMessage;
					const {shopifyCustomerId,identityAccessToken,multipassURL} = data;
					if (!errorMessage && shopifyCustomerId && identityAccessToken) {
						window.localStorage.setItem("SCID",shopifyCustomerId);
						window.localStorage.setItem("IAT",identityAccessToken);
						window.location.href = multipassURL;
					} else{
						$btnEle.attr('disabled',false);
						console.log(`%c Login Service Failed:IAT or SCID values may not available`, "background: red; color:white; font-weight:bold");
					}

				}).catch(error => {
					$(ele).removeClass('request-pending');
					$btnEle.attr('disabled',false);
				})
		})
	}
}

let self,
	apiConfigInst = new apiConfig(),
	validate = new fieldValidation(),
	request = new ajaxRequest();
$(document).ready(() => {
	let agSignInInstance = new agSignIn();
	agSignInInstance.init();
});