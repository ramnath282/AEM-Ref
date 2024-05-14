import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import { handleBarTemplate } from '../shared/templateSetter';

class orderDetails {
    constructor() {
        self = this;
        self.element = ".my-order-details";
    }
    init() {
        if(this.element.length) {
            self.render();
        }
    }    
    render() {
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
        let idNum = urlParam("orderId");
        if (idNum) {
            let ajaxOption = apiConfigInst.getApiConfig("orderDetails");
            ajaxOption.data = {'orderId':idNum}
            ajaxRequestInst.ajaxCall(ajaxOption)
            .then(data => {
                $.each(data.orderDetails.OrderItems, (key, val) => {
                val['listPrice'] = parseFloat(val['listPrice']).toFixed(2);
                let itemPrice = (val['listPrice'])*(val['quantity']);
                    val['totalPrice'] = parseFloat(itemPrice).toFixed(2);
                });
                data.orderDetails.orderInfo.orderSubTotal=parseFloat(data.orderDetails.orderInfo.orderSubTotal).toFixed(2);
                data.orderDetails.orderInfo.orderTax=parseFloat(data.orderDetails.orderInfo.orderTax).toFixed(2);
                data.orderDetails.orderInfo.orderShipping=parseFloat(data.orderDetails.orderInfo.orderShipping).toFixed(2);
                data.orderDetails.orderInfo.orderTotal=parseFloat(data.orderDetails.orderInfo.orderTotal).toFixed(2);
                this.data=data;            
                handleBarTemplateInst.loadTemplate("#orderInfo", "#viewOrderInfo", data.orderDetails, "replace");                  
            })
            .catch(error => { console.log(error) })
        }
    }
}

let self;
let eventBindingInst = new eventBinding();
let apiConfigInst = new apiConfig();
let ajaxRequestInst = new ajaxRequest();
let handleBarTemplateInst = new handleBarTemplate();
let orderDetailsInstance = new orderDetails();
orderDetailsInstance.init();

