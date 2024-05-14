import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import  { handleBarTemplate }  from '../shared/templateSetter';
import fieldValidation from '../shared/inputvalidation';
import {setCookie, getCookie} from '../shared/browserCookie';
import { handleBarsHelper } from '../shared/handleBarsHelper';
import {
    exceptionHandler
} from '../shared/flickerMessage';

class personalInfo {
    constructor() {
        self = this;
        this.element = ".personal-info-body";
    }
    init() {
        if(this.element.length) {
            this.render();
        }
        eventBindingInst.bindLooping({
                "click .personal-info-body .edit-link": "renderEditTemplate",
                "keyup .personal-info-body .has-error input": "inputChange",
                "change .personal-info-body input, .personal-info-body .has-error select": "inputChange",
                "click .personal-info-body .update-info": "formSubmit",
                "click .personal-info-body .remove-template": "removeEditTemplate",
                "click .personal-info-body .edit-child-info": "childInfoEdit",
                "click .personal-info-body .add-child-birth": "toggleChildInfoEntry",
                "click .personal-info-body .cancel-child-info": "removeChildInfoEdit",
                "click .personal-info-body .delete-info": "deleteInfo"
        },self);
    }
    render() {
        validate.showLoading(".user-info-readMode");
        request.ajaxCall(apiConfigInst.getApiConfig("personalInfo").get)
            .then(data => {
                this.personalData = data;
                sessionStorage.setItem("custemail",JSON.stringify(this.personalData.personalInfo.email1));
                if(this.personalData.childInfo != null && this.personalData.childInfo.children != null && this.personalData.childInfo.children.length > 0){
                    $.each(this.personalData.childInfo.children, (key,val)=> {
                        if(val.gender == "O"){
                            val.genderTxt = "Expecting(unknown)";
                        }else if(val.gender == "M"){
                            val.genderTxt = "Boy";
                        } else if(val.gender == "F") {
                        val.genderTxt = "Girl";
                        }
                    });
                }
                this.renderPersonalInfoDetails(data);
                this.renderChildInfoDetails(data);
                this.logonId = data.personalInfo && data.personalInfo.logonId;
                validate.hideLoading(".user-info-readMode");
             })
            .catch(error => { 
                console.log(error)
                validate.hideLoading(".user-info-readMode");
             })
    }
    errorMessage(err, loadingEle) {
        loadingEle && $(loadingEle).removeClass('request-pending');
        $(self.element).removeClass('request-pending');
    }
    resetPersonalInfo() {
        $(self.element).find(".remove-template").trigger("click");
        $(self.element).find(".add-child-birth").removeClass("hide");
        $(self.element).find(".child-information-wrapper").addClass("hide");
        $(self.element).find(".child-information-wrapper").empty();
    }
    renderPersonalInfoDetails(data){
        handleBarTemplateInst.loadTemplate("#personalInfoDetailsTemp", "#personalInfoDetails", data, "replace");        
    }
    renderChildInfoDetails(data){
        handleBarTemplateInst.loadTemplate("#childInfoListTemp", "#childInfoList", data.childInfo, "replace");
    }
    renderChildInfoForm(){
        handleBarTemplateInst.loadTemplate("#childInfoTemp", ".child-information-wrapper", {}, "replace");
    }
    renderEditTemplate(el, evt){
        $(self.element).find(".user-info-readMode").addClass("hide");
        $(self.element).find(".user-info-editMode").removeClass("hide");
        let editTemp = $(el).attr("data-edit");
        handleBarTemplateInst.loadTemplate("#"+editTemp, ".user-info-editModeDiv", self.personalData, "replace");        
        validate.editFormFields($(".user-info-editModeDiv").closest('form'));
        $(self.element).find(".user-info-editModeDiv input:first").focus();
        $(self.element).find(".user-info-editModeDiv input[type='submit']").attr("data-edit", editTemp);
        evt.preventDefault();
    }
    removeEditTemplate(el,evt){
        let focusElem = $(el).attr("data-edit");
        $(self.element).find(".user-info-editMode").addClass("hide");
        $(self.element).find(".user-info-editMode div").empty();
        $(self.element).find(".user-info-readMode").removeClass("hide");
        $(self.element).find(".user-info-readMode a[data-edit="+focusElem+"]").focus();
        evt.preventDefault();
    }    
    childInfoEdit(el, evt){
        let index = $(self.element).find(".edit-child-info").index(el);
        let context = self.personalData.childInfo.children[index];
        context.index = index;        
        let dob = context.dateOfBirth;
        let childValue = context.gender;
        let guardianValue = context.relationship;
        let dateArr = dob.split("/");
        handleBarsHelperInst.callRegisterHelper("monthSelector", [1,2,3,4,5,6,7,8,9,10,11,12], dateArr[0]);
        handleBarsHelperInst.callRegisterHelper("daySelector", [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31], dateArr[1]);
        handleBarsHelperInst.callRegisterHelper("yearSelector", [1989,1990,1991,1992,1993,1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019], dateArr[2]);
        $(el).parent().addClass("hide");        
        handleBarTemplateInst.loadTemplate("#childInfoTemp",  $(el).parent().next(".child-info-edit"), context, "replace");
        $(self.element).find("input[name='childOption"+index+"'][value='"+childValue+"']").attr("checked", "checked");
        $(self.element).find("input[name='guardianOption"+index+"'][value='"+guardianValue+"']").attr("checked", "checked");
        validate.editFormFields($(el).parent().next(".child-info-edit"));
        $(self.element).find(".child-info-edit").eq(index).find("input:first").focus();
        evt.preventDefault();
    }
    removeChildInfoEdit(el){
        if(!$(el).parent().parent().hasClass("child-information-wrapper")){
            let focusIndex = $(el).attr("data-update");
            $(el).parents(".child-info-edit").prev(".child-info-details").removeClass("hide");
            $(el).parent().empty();
            $(self.element).find(".child-info-details a[data-update='editChildInfo"+focusIndex+"']").focus();
        }
    }
    toggleChildInfoEntry(el, evt){
        if($(el).hasClass("cancel-child-info")){
            if($(el).parent().parent().hasClass("child-information-wrapper")){
                $(el).parent().parent().next(".add-child-birth").removeClass("hide");
                $(el).parent().parent().addClass("hide");
                $(self.element).find(".child-information-wrapper").empty();
                $(self.element).find("div.add-child-birth a").focus();
            }            
        }else{
            self.renderChildInfoForm();
            $(el).prev(".child-information-wrapper").removeClass("hide");            
            $(el).prev(".child-information-wrapper").find("input:first").focus();
            $(el).addClass("hide");            
        }
        evt.preventDefault();
    }
    getInputValues(ele,obj) {
        let type = $(ele).attr("type");
        let key = $(ele).attr("data-key");
        switch (type) {
            case "text":
                obj[key] = $(ele).val();
                break; 
            case "number":
                obj[key] = $(ele).val();
                break;
            case "checkbox":
                obj[key] = $(ele).is(":checked");
                break;
            case "radio":
                if($(ele).is(":checked")) {
                    obj[key] = $(ele).val(); 
                }
                break;
            default:
                if($(ele).hasClass("dob-select")) {
                    let dateVal = $(ele).val();
                    dateStr = dateStr == "" ? dateVal : dateStr+"/"+dateVal;
                    obj[key] = dateStr;
                }else{
                    obj[key] = $(ele).val();
                }                
        }
        return obj;
    }
    deleteInfo(el, evt) {
        let nickName = $(el).attr("data-delete");
        let index = $(el).attr("data-index");
        const option = apiConfigInst.getApiConfig("childInfo").delete;
        evt.preventDefault();
        self.confirmDialog(a => {
            option.data = JSON.stringify({"nickName": nickName});
            request.ajaxCall(option)
            .then(data => {
                
                if(index != ""){
                    exceptionHandler('success', 'Child information deleted successfully');
                    $(el).parents(".details-list-item").remove();
                    if($(el).parents(".details-list-item").prev(".details-list-item").length > 0){
                        $(el).parents(".details-list-item").prev(".details-list-item").find(".delete-info").focus();
                    }else{
                        $(el).parents(".details-list-item").next(".details-list-item").find(".delete-info").focus();
                    }
                }                
            })
            .catch(error => {
                console.log(error);                
                self.errorMessage(error);
            });
            
        });
    }
    confirmDialog(onConfirm) {
        const fClose = () => {
            modal.modal("hide");
        };
        let modal = $(self.element).find("#confirmModal");
        modal.modal("show");
        $(self.element).find("#confirmOk").unbind().one('click', onConfirm).one('click', fClose);
        $(self.element).find("#confirmCancel").unbind().one("click", fClose);
    }
    getAPIPayload(inputNames, childIndex, addressId, formElement) {
        let obj = {};
        inputNames.each( index => {
            obj = self.getInputValues(inputNames.eq(index), obj);
        });
        
        if(obj['relationship'] != undefined){
            obj['addressType'] =  "child";
        }else{
            if(formElement == ".update-email"){
                obj['logonId'] = self.logonId.split("|")[0]+ "|" + obj.email1;
            }else{
                obj['logonId'] = self.logonId;
            }
        }
        if(childIndex != ""){
            obj['addressId'] = addressId;            
        }
        dateStr = "";
        return  obj;
    }
    inputChange(target) {
        validate.instantValidation(target);
    };
    formSubmit(target, evt) {
        evt.preventDefault();
        let formElement = target.dataset.formname || target.form;        
        let focusElem = target.dataset.edit != undefined ? target.dataset.edit : "";
        let focusIndex = target.dataset.update != undefined ? target.dataset.update : "";
        let option = "";
        validate.checkAllFields(formElement, (output) => {
            if (!output) {
                return;
            }
            let inputNames = $(formElement).find("[data-key]");
            let childIndex = $(target).attr("data-update"),
                addressId = $(target).attr("data-addressid");
            const payload = self.getAPIPayload(inputNames, childIndex, addressId, formElement);
            if(formElement != ".update-pass"){
                option = childIndex != "" && childIndex != undefined ? apiConfigInst.getApiConfig("childInfo").update : (formElement == ".update-childInfo" ? apiConfigInst.getApiConfig("childInfo").submit : apiConfigInst.getApiConfig("personalInfo").update);
            }else{
                option = apiConfigInst.getApiConfig("personalInfo").updatePassword;
            }
            if(formElement == ".update-email"){
                payload.email1 = payload.email1.toLowerCase();
            }
            if(formElement == ".update-phone"){
                let isZeroStart= true;
                payload.phone1 = payload.phone1.split("").filter(v => {
                    if(isZeroStart && v!=0) {
                        isZeroStart = false;
                    }
                    if(!isZeroStart){
                        return !isNaN(v)
                    }else {
                        return false;
                    }                    
                }).join("");
            }
            validate.showLoading(formElement);
            option.data = JSON.stringify(payload);
            
            request.ajaxCall(option)
            .then(data => {
                if(formElement == ".update-name") {
                    let inputVal = $('#firstName').val().trim()+" "+$('#lastName').val().trim();
                    let pageCookie = getCookie("MATTEL_WELCOME_MSG");
                    if(pageCookie) {
                        setCookie("MATTEL_WELCOME_MSG",encodeURIComponent(inputVal),0)
                    }
                    setCookie("MATTEL_WELCOME_MSG",encodeURIComponent(inputVal));                    
                }               
                validate.hideLoading(formElement);                          
                self.render();
                self.resetPersonalInfo();
                const errorMessage = data.errors != null ? data.errors[0].errorMessage : "";
                const errorKey = data.errors != null ? data.errors[0].errorKey : "";
                if(errorKey == "_ERR_LOGONID_ALREDY_EXIST"){
                    if(formElement == ".update-email"){
                        $(self.element).find("#duplicateEmailModal").modal("show");
                        $(self.element).find("#duplicateEmailModal .modal-body p").html(errorMessage);
                    }
                }else{
                    exceptionHandler('success', 'Information Updated Successfully');
                }
                setTimeout(a => {
                    if(formElement == ".update-childInfo"+childIndex){
                        $(self.element).find(".child-info-details a[data-update='editChildInfo"+focusIndex+"']").focus();                        
                    }else{
                        $(self.element).find(".user-info-readMode a[data-edit="+focusElem+"]").focus();
                    }
                }, 500);
                
            })
            .catch(error => {
                console.log(error);
                validate.hideLoading(formElement);              
                self.errorMessage(error);
            })
        });
    };
}

let self;
let dateStr = "";
let eventBindingInst = new eventBinding();
let perInfoInstance = new personalInfo();
let apiConfigInst = new apiConfig();
let request = new ajaxRequest();
let handleBarTemplateInst = new handleBarTemplate();
let validate = new fieldValidation();
let handleBarsHelperInst = new handleBarsHelper();
perInfoInstance.init();