import apiConfig from './apiConfig';
import {
    setStorage,
    getStorage
} from './sessionStorage';
import Constants from './constant';
import {
    exceptionHandler
} from './flickerMessage';

export default class ajaxRequest {
    constructor() {
        self = this;
        const {userName} = window.global.getUserCookie();
        if (userName) {
            $('.signin-header').addClass('hide');
            $('.signout').removeClass('hide');
        }
        if(window.global.errorAPIStatus == "loading"){
            setTimeout(function(){
                self.apiErrorDatas = getStorage(Constants.errorStorageName) || self.getErrorAPI();
            },1000)
        } else {
            this.getErrorAPI();
        }
    }
    getErrorAPI() {
        let self = this;
        window.ecomm = window.ecomm || {};
        const checkAPI = getStorage(Constants.errorStorageName);

        if(window.ecomm.isErrorListAvailable && checkAPI) {
           self.apiErrorDatas = checkAPI;
           return
        } else {
            window.ecomm.isErrorListAvailable = true;
            if(window.ecomm.errorListCallCount == "undefined"){
            	window.ecomm.errorListCallCount = 0;
            }
        }

        if (checkAPI) {
            self.apiErrorDatas = checkAPI;
        } else {
            window.global.errorAPIStatus = "loading";
            self.ajaxCall(config.getApiConfig("errorCodes"))
                .then(data => {
                    window.global.errorAPIStatus = "success";
                    self.apiErrorDatas = typeof data == "string" ? JSON.parse(data) : data;
                    setStorage(Constants.errorStorageName, self.apiErrorDatas);
                })
                .catch(error => {
                    window.global.errorAPIStatus = "error";
                    self.apiErrorDatas = false;
                    window.ecomm.isErrorListAvailable = false;
                    if(window.ecomm.errorListCallCount < 3){ 
                    	self.getErrorAPI();
                    	window.ecomm.errorListCallCount = window.ecomm.errorListCallCount + 1;
                    }
                    exceptionHandler('error', 'Error Message API failed..');
                })
        }
    }
    getApiConfig(reqConfig) {
        return self.config[reqConfig];
    }
    errorMessageException(errorKey, errorCode) {
        if((errorKey == "_ERR_LOGONID_ALREDY_EXIST" && errorCode) || errorKey == "_ERR_ADDRESS_NOT_FOUND" || self.apiErrorDatas["DYN." + errorKey]){
            return false;
        }
        return true;
    }
    showErrorMessage(obj) {
        const isError = obj.errors,
            errorKey = isError && isError[0] && isError[0].errorKey,
            errorMessage = self.apiErrorDatas && self.apiErrorDatas[errorKey] || `${errorKey} not configured`,
            serviceErrorMessage = isError && isError[0] && isError[0].errorMessage,
            errorCode = isError && isError[0] && isError[0].errorCode;
        if (errorKey && errorMessage && self.errorMessageException(errorKey, errorCode)) {
            exceptionHandler('error', errorMessage);
            return true;
        } else if (errorKey && self.apiErrorDatas["DYN." + errorKey] && serviceErrorMessage) {
            exceptionHandler('error', serviceErrorMessage);
            return true;
        }
        return false;
    }
    showErrorMessageForFailures(obj) {
        const isError = obj.errors,
            errorKey = isError && isError[0] && isError[0].errorKey,
            errorMessage = self.apiErrorDatas && self.apiErrorDatas[errorKey],
            serviceErrorMessage = isError && isError[0] && isError[0].errorMessage,
            errorCode = isError && isError[0] && isError[0].errorCode;
        if (errorKey && errorMessage && self.errorMessageException(errorKey, errorCode)) {
            exceptionHandler('error', errorMessage);
            return true;
        } else if (errorKey && self.apiErrorDatas["DYN." + errorKey] && serviceErrorMessage) {
            exceptionHandler('error', serviceErrorMessage);
            return true;
        }
		
		console.error(errorMessage || serviceErrorMessage || 'An error has occurred');
        return false;
    }
    ajaxCall(config, resolve, reject) {
        // self.resetTimers();
        return $.ajax(config).then(function (res) {
            if(self.showErrorMessage(res)){
                return $.Deferred().reject(res);
            } else{
                return $.Deferred().resolve(res);
            }
            
        }).fail(function(err){
        	if (err.responseJSON && err.responseJSON.errors) {
        		self.showErrorMessageForFailures(err.responseJSON);
        	} else if (err.status == 500) {
        		console.error(err);
        		exceptionHandler('error', (self.apiErrorDatas["_ERR_GENERIC"] || "A generic error has occurred."));
        	}
        });        
    }
    ajaxCallWithoutErrorHandling(config, resolve, reject) {
        // self.resetTimers();
        return $.ajax(config).then(function (res,text,req) {
            return $.Deferred().resolve(res,req);
        }).fail(function(err){
            const errorJSON = err.responseJSON;
            if (errorJSON && errorJSON.errors && errorJSON.errors.length) {
                const errorKey = errorJSON.errors[0].errorKey,
                errorMessage = self.apiErrorDatas && self.apiErrorDatas[errorKey],
                errorCode = errorJSON.errors[0].errorCode;
                if (errorKey && errorMessage && self.errorMessageException(errorKey, errorCode)) {
                    return true;
                }
            }
            return $.Deferred().reject(err);
        });
    }
}
let self;
const config = new apiConfig();