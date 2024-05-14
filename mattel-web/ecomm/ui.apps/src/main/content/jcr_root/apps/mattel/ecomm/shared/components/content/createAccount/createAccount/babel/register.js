import ajaxRequest from '../shared/ajaxbinding';
import apiConfig from '../shared/apiConfig';
import fieldValidation from '../shared/inputvalidation';
import eventBinding from '../shared/eventBinding';
import { exceptionHandler } from '../shared/flickerMessage';
import { setStorage, getStorage } from '../shared/sessionStorage';
import Constants from '../shared/constant';
import {deleteCookie} from '../shared/browserCookie';

export class registerFormAction {
	constructor() {
		self = this;
		evtBinding.bindLooping(this.bindingEventsConfig(), self);
		self.getCountryCode();
		$('#confirmRegisterModal').on('hidden.bs.modal', function () {
			window.location = $(".register-form").attr("data-redirect");
		});
		self.renderYear();
		self.rendertwentyYear();
	}
	renderYear() {
        for (let i = new Date().getFullYear(); i >= new Date().getFullYear()-100; i--)
            {
                $('#childBirthYear').append($('<option />').val(i).html(i));
            }
	}
	rendertwentyYear() {
        for (let i = new Date().getFullYear(); i >= new Date().getFullYear()-20; i--)
            {
                $('#childBirthYear1').append($('<option />').val(i).html(i));
            }
    }
	getCountryCode() {
		const checkAPIData = getStorage(Constants.geoAPIStorageName);
		if (checkAPIData) {
			self.countryCode = checkAPIData;
			if(self.countryCode.CountryCode == 'US'){
				$('input[name="termConditions"],input[name="emailOptin"]').attr('checked',true);
			}
		} else {
			return request(config('getGeoDetection'))
				.then(data => {
					self.countryCode = typeof data == 'string' ? JSON.parse(data) : data; // JSON-string from `response.json()` call
					setStorage(Constants.geoAPIStorageName, self.countryCode);
					if(self.countryCode.CountryCode == 'US'){
						$('input[name="termConditions"],input[name="emailOptin"]').attr('checked',true);
					}
				})
				.catch(error => {
					self.countryCode = false;
				});
		}
	}
	bindingEventsConfig() {
		let eventsArr = {
			'submit .register-form': 'submitRegisterForm',
			'click .find-rewards': 'checkMembershipLinked',
			'click .collapse-reward-form':'cancelRewardReg',
			'submit .find-rewards-form': 'rewardsFormSubmit',
			'keyup .register .input-field.has-error input': 'inputChange',
			'change .register .input-field input,.register .input-field select': 'inputChange',
			'change #enroll-rewards': 'isRewardsEligible',
			'click .reward-toggle-actions a': 'rewardToggleAction',
			'click #confirmRegisterModal #confirmRegOk': 'closeSorryModel'
		};
		return eventsArr;
	}
	rewardToggleAction(ele) {
		let $targetEle = $('.expand-form');
		let $rewardsForm = $('.find-rewards-form');
		if ($(ele).attr('aria-expanded') == 'true') {
			$targetEle.hide();
			if ($targetEle.attr('aria-expanded') == 'true') {
				$targetEle[0].click();
				self.clearForm('.register-form');
			}
			return;
		}
		self.toggleErrorForm($rewardsForm[0], false);
		$targetEle.show();
	}
	inputChange(ele) {
		validate.instantValidation(ele);
	}
	clearForm(form) {
		$(form)
			.find('.input-field')
			.removeClass('has-error');
	}
	beforeSubmit(target, cb) {
		let formElement = $(target);
		validate.checkOnlyVisibleFields(formElement, function(output, formFields) {
			if (!output) {
				return;
			}
			self.clearUserCookies();
			formElement.addClass('request-pending');
			cb(output, target);
		});
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
	isRewardsEligible(ele) {
		if ($(ele).is(':checked') && self.countryCode.CountryCode != 'US') {
			const $rewardModal = $('#rewards-modal');
			$rewardModal.length && $rewardModal.modal();
		}
	}
	getAge(dateString) {
		var today = new Date();
		var birthDate = new Date(dateString);
		var age = today.getFullYear() - birthDate.getFullYear();
		var m = today.getMonth() - birthDate.getMonth();
		if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
			age--;
		}
		return age;
	}
	closeSorryModel() {
		$("#confirmRegisterModal").modal("hide");
	}
	submitRegisterForm(ele, evt) {
		evt.preventDefault();
		self.beforeSubmit(ele, function(res, formFields) {
			const payload = config('registerPayload').apply(formFields);
			let dataVerify = JSON.parse(payload.data);
			if(self.getAge(`${dataVerify.birth_year}/${dataVerify.birth_month}/${dataVerify.birth_date}`) >= 18) {	
				request(payload)
					.then(data => {
						exceptionHandler('success', 'Account created successfully!');
						$(ele).removeClass('request-pending');
						const errorMessage = data.errors && data.errors[0] && data.errors[0].errorMessage;
						if (!errorMessage) {
							window.location.href = formFields.action;
						}
					})
					.catch(error => {
						$(ele).removeClass('request-pending');
					});
			} else {
				$(ele).removeClass('request-pending');
				$("#confirmRegisterModal").modal("show");				
			}	
		});
	}
	rewardsFormSubmit(ele, evt) {
		evt.preventDefault();
		self.beforeSubmit(ele, function(res, formFields) {
			const membershipRes = self.memberShipRes || {};
			const rewardsData = {
				userType: membershipRes.userType || '',
				membershipAccountFound: membershipRes.membershipAccountFound || '',
				lastName: membershipRes.lastName || '',
				firstName: membershipRes.firstName || '',
				phone1: membershipRes.phone || '',
				usersId: membershipRes.usersId || ''
			};
			const payload = config('registerMembershipId').call(this, formFields, rewardsData);
			let dataVerify = JSON.parse(payload.data);
			if(self.getAge(`${dataVerify.birth_year}/${dataVerify.birth_month}/${dataVerify.birth_date}`) >= 18) {	
			request(payload)
				.then(data => {					
					$(formFields).removeClass('request-pending');
					exceptionHandler('success', 'Account created successfully!');
					const errorMessage = data.errors && data.errors[0] && data.errors[0].errorMessage;
                    if (!errorMessage) {
                    window.location.href = formFields.action;
                    }
				})
				.catch(error => {
					$(formFields).removeClass('request-pending');
				});
			} else {
				$(ele).removeClass('request-pending');
				$("#confirmRegisterModal").modal("show");	
			}
		});
	}
	toggleErrorForm(formFields, checkError) {
		if (checkError) {
			formFields.member_id.disabled = true;
			$('#email-id-value').html(formFields.emailId.value);
			$(formFields).addClass('has-error');
			$('.find-rewards').addClass('hide');
		} else {
			formFields.member_id.disabled = false;
			$('#email-id-value').html('');
			$(formFields).removeClass('has-error');
			$('.find-rewards').removeClass('hide');
			$(formFields)
				.find('input')
				.removeClass('not-empty');
			$(formFields).trigger('reset');
		}
	}
	cancelRewardReg(evt){
		$('.signup-with-membership, .reward-success-message, .reward-success-message, .no-membership-msg').addClass('hide');
	}
	checkMembershipLinked(ele, evt) {
		evt.preventDefault();
		let form = ele.form;
		self.beforeSubmit(form, function(res, formFields) {
            const payload = config('checkMembershipId').apply(formFields);
            const $errMsg1 = $(".membership-linked-msg");
            const $errMsg2 = $(".no-membership-msg");
			const $succMsg = $('.reward-success-message');
			request(payload)
				.then(data => {
					$('.find-rewards').addClass('hide');
					$(form).removeClass('request-pending');
					if (data.membershipAccountFound == false) {
                        self.toggleErrorForm(formFields, true);
                        $errMsg1.addClass('hide');
                        $errMsg2.removeClass('hide');
					} else if (data.userType == 'R') {
                        self.toggleErrorForm(formFields, true);
                        $errMsg1.removeClass('hide');
                        $errMsg2.addClass('hide');
					} else {
						self.memberShipRes = data;
						$(formFields).addClass('has-success');
						formFields.member_id.disabled = true;
						$succMsg.removeClass('hide');
						$('.find-rewards').addClass('hide');
						$('.signup-with-membership').removeClass('hide');
					}
				})
				.catch(error => {
					$(form).removeClass('request-pending');
				});
		});
	}
}

let self;
const request = new ajaxRequest().ajaxCall;
const config = new apiConfig().getApiConfig;
const validate = new fieldValidation();
const evtBinding = new eventBinding();
let signIn = new registerFormAction();
