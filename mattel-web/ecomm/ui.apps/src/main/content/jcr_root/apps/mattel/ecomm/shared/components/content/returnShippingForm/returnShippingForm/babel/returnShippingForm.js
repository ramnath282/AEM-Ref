import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import fieldValidation from '../shared/inputvalidation';
import { handleBarsHelper } from '../shared/handleBarsHelper';
import { handleBarTemplate } from '../shared/templateSetter';
import { countryListFetch } from '../shared/countryListFetch';

const config = {
    endPointUrl : $("#endPointUrl").val(),
    shipperaccount : $("#shipperaccount").val(),
    postmenapikey : $('#postmen-api-key').val(),
};

class returnsInfo {
    constructor() {
         self = this;
         this.element = ".return-form-body";
         self.payloadObj = {};         
    }
    
    init() {
        if(this.element.length) {
            this.render();
        }
        eventBindingInst.bindLooping({
            "keyup .return-form .has-error input": "inputChange",
             "change .return-form input": "inputChange",
             "click .return-form select": "selectChange",
             "focusout .return-form select": "selectChange",
             "change .return-form select": "optionChange",
             "click .return-form .form-submission": "formSubmit",
             //"click .return-form": "submitForm"  
        },self);
    }

    render() { 
        this.countryList = getCountry.fetchCountryList();
        const defaultCountry = "US"  
        let stateList = self.fetchStateList(defaultCountry);
        stateList.unshift({code: "",displayName: ""});
        handleBarTemplateInst.loadTemplate('#state', '#defaultState', stateList, 'replace');
        $(".return-form select > option:first").attr("hidden",true);
        $('.return-form select option').filter(function() {
			return !this.value || $.trim(this.value).length == 0 || $.trim(this.text).length == 0;
		}).attr('disabled', true);
    }

    fetchStateList(countryVal){
        let states = [];
        $.each(self.countryList.countries, (key, value) => {
            if(countryVal == value.code){
                states = value.states;
            }
        });
        return states;
    }

    inputChange(target) {       
        validate.instantValidation(target);
    }
    selectChange(ele,evt){
        $(ele).toggleClass("active");
    }
    optionChange(ele,evt){
        if($(ele).val() != ""){
            $(ele).addClass("not-empty");
    	}
		validate.instantValidation(ele);
    }
    formSubmit(ele,evt) {
        evt.preventDefault();

        self.beforeSubmit("#returnForm", function (res, formFields) {
            const formFieldValue =  $(formFields).serializeArray();
            let formatedFieldArray = {};
            for (var i = 0; i < formFieldValue.length; i++){
                formatedFieldArray[formFieldValue[i]['name']] = formFieldValue[i]['value'];
            }

            self.newAdd = $(ele).hasClass("add-add-Btn") ? true : false;
            const addStdObj = {
                "firstName" : formatedFieldArray.firstName,
				"lastName" :formatedFieldArray.lastName,
                "state" : formatedFieldArray.stateProvince,
                "zipCode" : formatedFieldArray.zipCode,
				"isGT" : $("#isGT").val()
            };
			
            let ajaxOption = apiConfigInst.getApiConfig("fedex");
            ajaxOption.data = JSON.stringify(addStdObj);
            request.ajaxCall(ajaxOption).then(response => {
               if(response){
                    $('.return_analytics_success').trigger('click');
                    /*const redirectUrl = response.data.files.label.url;
                    window.open(redirectUrl);*/
                    var newBlob = new Blob([response], {type: "application/pdf"})

                    // IE doesn't allow using a blob object directly as link href
                    // instead it is necessary to use msSaveOrOpenBlob
                    if (window.navigator && window.navigator.msSaveOrOpenBlob) {
                      window.navigator.msSaveOrOpenBlob(newBlob);
                      return;
                    }

                    // For other browsers: 
                    // Create a link pointing to the ObjectURL containing the blob.
                    const data = window.URL.createObjectURL(newBlob);
                   // var link = document.createElement('a');
                    //link.href = data;
                    //link.download="file.pdf";
                    //link.click();
                    setTimeout(function(){
                      // For Firefox it is necessary to delay revoking the ObjectURL
                    	window.open(data, "_blank");
                    }, 100);
                }else{
                     const return_error_code = $("#return_error_code");
                     const return_error_message = $("#return_error_message");
                     return_error_code.val(response.meta.code);
                     return_error_message.val(response.meta.message);

                     $('.return_analytics_service_failure').trigger('click');
                }
            })        
            .catch(error => {
                // Added for Analytics
                const return_error_code = $("#return_error_code");
                const return_error_message = $("#return_error_message");
                return_error_code.val(error.meta.code);
                return_error_message.val(error.meta.message);

                $('.return_analytics_service_failure').trigger('click');
            })
         });
    }

    beforeSubmit(target,cb) {
        let formElement = $(target);
        const return_error_field = $("#return_error_field");
        validate.checkAllFields(formElement,  (output, formFields) => {
            //Added for Analytics
            if(!output){
                let blanFields = []
                for (var i = 0; i < formFields.length; i++){
                    if(formFields[i]['value'] === ''){
                        blanFields.push(formFields[i]['name'] + " field empty |");
                    }
                }
                let blanFieldsString = blanFields.toString();
                blanFieldsString = blanFieldsString.replace(/\,/g," ");
                blanFieldsString = blanFieldsString.substring(0, blanFieldsString.length - 1);
                return_error_field.val(blanFieldsString);
            }
            
            if (!output) {
                $('.return_analytics_validation_failure').trigger('click');
                return;
            }
            cb(output, target)
        })
    }
}

let self;
let eventBindingInst = new eventBinding();
let apiConfigInst = new apiConfig();
let request = new ajaxRequest();
let handleBarTemplateInst = new handleBarTemplate();
let handleBarsHelperInst = new handleBarsHelper();
let validate = new fieldValidation();
let returnsInfoInstance = new returnsInfo();
let getCountry = new countryListFetch();
returnsInfoInstance.init();




