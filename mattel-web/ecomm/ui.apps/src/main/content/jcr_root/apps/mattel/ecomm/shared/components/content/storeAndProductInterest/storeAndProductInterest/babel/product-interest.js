import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import fieldValidation from '../shared/inputvalidation';
import { handleBarTemplate } from '../shared/templateSetter';
import { exceptionHandler } from '../shared/flickerMessage';


class productInterest {
    constructor() {
        self = this;
        this.element = ".product-interest";
    }
    init() {
        if(this.element.length) {
            eventBindingInst.bindLooping({
                "click .product-interest .update-interest-btn": "updateProductInterest"
            },this);
            this.render();
        }        
    }
    
    updateProductInterest(ele,evt){
        validateInst.showLoading(".product-interest");
        let obj = {};
        obj["PROD_INTER_PI"] = self.getSubmitobj($(self.element).find("#productInterestList input"));
        obj["PROD_INTER_LO"] = self.getSubmitobj($(self.element).find("#locationInterestList input"));
        obj["selectCountry"] = $(self.element).find("#eventInterestList").val();
        const dataObj = apiConfigInst.getApiConfig("productInterest").update;
        productInterestAnalytics(obj);
        dataObj.data = JSON.stringify(obj);
        ajaxRequestInst.ajaxCall(dataObj)
            .then(data => {                 
                self.render();
                validateInst.hideLoading(".product-interest");  
                exceptionHandler('success', "Successfully updated!");            
             })
            .catch(error => { 
                console.log(error);            
                validateInst.hideLoading(".product-interest");
             })
        evt.preventDefault(); 
    }

    getSubmitobj(ele){
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
        ajaxRequestInst.ajaxCall(apiConfigInst.getApiConfig("productInterest").get)
            .then(data => { 
                handleBarTemplateInst.loadTemplate("#productInterestListTmpl","#productInterestList",this.dataList(data.productInterestList),"replace");
                handleBarTemplateInst.loadTemplate("#productInterestListTmpl","#locationInterestList",this.dataList(data.locationInterestList),"replace");
                handleBarTemplateInst.loadTemplate("#eventInterestListTmpl","#eventInterestList",this.dataList(data.eventInterestList,true),"replace");
                //$(self.element).find('select').selectpicker();
             })
            .catch(error => { console.log(error) })
    }

    dataList(data, reverse) {       
        let products = [];
        if (data.length) {
            $.each(data, (k1,v1) => {
                let obj = {};
                let arr = v1.value.split(":")
                obj.id = reverse ? arr[1] : arr[0];
                obj.val = reverse ? arr[0] : arr[1];
                obj.selected = v1.selected;
                products.push(obj);
            });
            return {"item":products};
        }        
    }
}

let self;
const eventBindingInst = new eventBinding();
const apiConfigInst = new apiConfig();
const ajaxRequestInst = new ajaxRequest();
const handleBarTemplateInst = new handleBarTemplate();
const validateInst = new fieldValidation();
const productInterestInstance = new productInterest();
productInterestInstance.init();

