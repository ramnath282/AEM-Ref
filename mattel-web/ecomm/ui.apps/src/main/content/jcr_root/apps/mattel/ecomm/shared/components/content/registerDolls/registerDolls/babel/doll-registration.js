import ajaxRequest from '../shared/ajaxbinding';
import apiConfig from '../shared/apiConfig';
import fieldValidation from '../shared/inputvalidation';
import eventBinding from '../shared/eventBinding';
import {
    handleBarTemplate
} from '../shared/templateSetter';
import {
    dateFormat
} from '../shared/dateFormat';
import {
    exceptionHandler
} from '../shared/flickerMessage';

export class dollRegister {
    constructor() {
        self = this;
        this.tableEle = "#registered-dolls-data";
        this.element = "#dollRegistration";
        self.startYear = $("#registration-started-year").val() || 1989;
        self.loadYearDropdown();
        self.getRegisteredData();
        validate.editFormFields(".doll-registration-form");
        evtBinding.bindLooping(this.bindingEventsConfig(), self);
    }
    bindingEventsConfig() {
        let eventsArr = {
            "submit .doll-registration-form": "submitForm",
            "keyup .input-field .has-error input": "inputChange",
            "change .input-field input,.input-field select": "inputChange",
            "click #registered-dolls-data .create-certificate": "createCertificate",
            "click #registered-dolls-data .remove-doll": "removeDoll",
        }
        return eventsArr;
    }
    errorMessage(err, loadingEle) {
        loadingEle && $(loadingEle).removeClass('request-pending');
        $(self.tableEle).removeClass('request-pending');
    }
    inputChange(ele) {
        validate.instantValidation(ele);
    }
    loadYearDropdown() {
        const yearList = formatDate.getYearList(self.startYear).reverse();
        let newEle='';
        for (const value of yearList) {
            newEle+=`<option value="${value}">${value}</option>`
        }
        $("#yearField").append(newEle);
    }
    beforeSubmit(target, cb) {
        let formElement = $(target);
        validate.checkAllFields(formElement, function (output, formFields) {
            if (!output) {
                checkForError();
                return;
            }
            cb(output, target)
        })
    }
    getRegisteredData(templateParam) {
        const payload = config('dollRegistration')['get'];
        $(self.tableEle).addClass('request-pending');
        request(payload).then(data => {
            for (let [value] of data.dolls.entries()) {
                value.purchasedDate = value.purchasedDate ? formatDate.formatToNewDate(new Date(value.purchasedDate)) : '';
            }
            handleBarTemplateInst.loadTemplate("#registeredDataTemp", self.tableEle, data.dolls, templateParam);
            $(self.tableEle).removeClass('request-pending');
            if(templateParam) exceptionHandler('success', "Data Updated Successfully..");
        }).catch(error => {
            self.errorMessage(error);
        });
    }
    removeDoll(ele,evt) {
        evt.preventDefault();
        const $ele = $(ele);
        $(self.tableEle).addClass('request-pending');
        const productId = $ele.data('productId');
        const payload = config('dollRegistration')['delete'].apply({productId:productId});
        request(payload).then(data => {
            const $parentEle = $ele.closest('tr');
            $parentEle.remove();
            $(self.tableEle).removeClass('request-pending');
            exceptionHandler('success', "Data Removed Successfully..");
        }).catch(error => {
            self.errorMessage(error)
        });
    }
    addDoll(form) {
        const payload = config('dollRegistration')['add'].apply(form);
        $(self.tableEle).addClass('request-pending');
        request(payload).then(data => {
        	setCommonProp("form-submit", $(".doll-reg").val().toLowerCase());
        	self.getRegisteredData('replace');
			$(form).trigger("reset");
        }).catch(error => {
        	checkForError();
            self.errorMessage(error);
        });
    }
    submitForm(ele, evt) {
        evt.preventDefault();
        self.beforeSubmit(ele, function (res, formFields) {
            self.addDoll(formFields);
        })
    }
}

let self;
const request = new ajaxRequest().ajaxCall;
const config = new apiConfig().getApiConfig;
const validate = new fieldValidation();
const evtBinding = new eventBinding();
const formatDate = new dateFormat();
const handleBarTemplateInst = new handleBarTemplate();
const dollRegistration = new dollRegister();