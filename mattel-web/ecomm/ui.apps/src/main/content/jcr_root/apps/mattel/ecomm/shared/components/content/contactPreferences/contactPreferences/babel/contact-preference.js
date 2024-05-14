import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import fieldValidation from '../shared/inputvalidation';
import { handleBarTemplate } from '../shared/templateSetter';
import { exceptionHandler } from '../shared/flickerMessage';

class preferenceInfo {
    constructor() {
        self = this;
        self.element = ".contact-preference-body";
    }
    init() {
        if(this.element.length) {
            self.render();
            eventBindingInst.bindLooping({
            "click .contact-preference-body .update-preferences-btn" : "updateContactPref",
            "change .contact-preference-body input[name='emailPreference']": "changePreferences",
            "change .contact-preference-body  #messagePreferences  input": "changeMsgPreferences"            
            },this);
        }
    }    

    getPayLoad() {
        const obj = {
            "storeId": $("#storeId").val(),
            "catalogId": $("#catalogId").val(),
            "partyId" : self.data.partyId || "",
            "hash": self.data.hash || "",
            "URL": "EmailPreferencesView" ,
            "sourceName": "WebMyAccount",
            "loyaltyEmail": self.data.loyaltyEmail || "",
            "loyaltyRequest": self.data.loyaltyRequest || "",
            "langId": $("#langId").val(),            
            "CONT_PREF_GL" : $("input[name='emailPreference']:checked").val() == "yes" ? "GLOBAL_PROMOTIONAL~EMAIL" : "",
            "CONT_PREF_CTL" : [],
            "email" : self.data.email || ""
        };
        obj["CONT_PREF_CAT"] = self.getSubmitObj($(self.element).find("#messagePreferences input"));
        obj["CONT_PREF_LOY"] = self.getSubmitObj($(self.element).find("#agrewardsPreferences input"));
        if(self.getSubmitObj($(self.element).find("#catalougueMail input"))[0]) {
            obj["CONT_PREF_CTL"].push(self.getSubmitObj($(self.element).find("#catalougueMail input"))[0]);
        } 
        if(self.getSubmitObj($(self.element).find("#infoSharing input"))[0]) {
            obj["CONT_PREF_CTL"].push(self.getSubmitObj($(self.element).find("#infoSharing input"))[0]);
        }

        return JSON.stringify(obj);
    }
    updateContactPref(ele,evt){
        validateInst.showLoading("#contactPreferences form");
        let ajaxConfig = apiConfigInst.getApiConfig("contactPreference").update;        
        ajaxConfig.data = self.getPayLoad();
        contactpreferencesAnalytics(ajaxConfig.data);
        ajaxRequestInst.ajaxCall(ajaxConfig)
        .then(data => {
            validateInst.hideLoading("#contactPreferences form");
            exceptionHandler('success', "Successfully updated!");
        })
        .catch(error => { 
            console.log(error);            
            validateInst.hideLoading("#contactPreferences form");
        })
        evt.preventDefault();
    }

    getSubmitObj(ele){
        let arr = [];
        ele.each( key => {
            let el = ele.eq(key);
            if(el.is(":checked")) {
                arr.push(el.attr("id"));
            }
        });
        return arr;
    }
    render() {
        validateInst.showLoading("#contactPreferences form");
        ajaxRequestInst.ajaxCall(apiConfigInst.getApiConfig("contactPreference").get)
        .then(data => {
            self.data = data;
            
            $(self.element).find(".contact-email").html(data.email.split("|")[1]);
            data.userContactPrefGL !="" ? $('input:radio[name=emailPreference][value="yes"]').attr("checked",true) : $('input:radio[name=emailPreference][value="no"]').attr("checked",true);            
            handleBarTemplateInst.loadTemplate("#preferencesTmpl","#messagePreferences",this.dataList(data.contactPrefSEQMap.CONT_PREF_CAT), "replace");  
            if(self.data.loyaltyStatus && self.data.loyaltyStatus.toLowerCase() == "active") {                
                handleBarTemplateInst.loadTemplate("#loyaltyPreferencesTmpl","#agrewardsPreferences", this.dataList(data.contactPrefSEQMap.CONT_PREF_LOY), "replace");   
            } else {
                $(".banner-outer").removeClass("hide");
            }
            handleBarTemplateInst.loadTemplate("#sharePreferencesTmpl","#catalougueMail",this.dataList(data.contactPrefSEQMap.CONT_PREF_CTL[0]),"replace");
            handleBarTemplateInst.loadTemplate("#sharePreferencesTmpl","#infoSharing",this.dataList(data.contactPrefSEQMap.CONT_PREF_CTL[1]),"replace");
            validateInst.hideLoading("#contactPreferences form");
            
        })
        .catch(error => { 
            console.log(error);
            validateInst.hideLoading("#contactPreferences form");
        })
    }
    dataList(data) { 
        let items = [];
        let dataList=[];
        if(Array.isArray(data)) {
            dataList = data
        } else {
            dataList.push(data)
        }
        if (dataList.length) {
            $.each(dataList, (k1,v1) => {
                let obj = {};
                let arr = v1.value.split(":");
                obj.id = arr[0];
                obj.val = arr[1];
                obj.selected = v1.selected;
                items.push(obj);
            });
            return {"item":items};
        }   
    }
    
    changePreferences(ele,evt) {
       if($(ele).val()=="no") {
            self.msgPrefEl="";           
       }
       $.each(self.data.contactPrefSEQMap.CONT_PREF_CAT, (k,v) => {
            v.value.split(":")[0] == self.msgPrefEl ? v.selected = true : v.selected = false;
        }) 
       handleBarTemplateInst.loadTemplate("#preferencesTmpl","#messagePreferences",self.dataList(self.data.contactPrefSEQMap.CONT_PREF_CAT), "replace");  
    }

    changeMsgPreferences(ele,evt) {        
        if($("input[value='no']").is(":checked") && $(ele).is(":checked")) {
            self.msgPrefEl = $(ele).attr("id");
           $('input:radio[name=emailPreference][value="yes"]').click();
        }
    }
}



let self;
const eventBindingInst = new eventBinding();
const apiConfigInst = new apiConfig();
const ajaxRequestInst = new ajaxRequest();
const validateInst = new fieldValidation();
const handleBarTemplateInst = new handleBarTemplate();
const userPreference = new preferenceInfo();
userPreference.init();