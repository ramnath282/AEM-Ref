import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import {getCookie} from '../shared/browserCookie';
import { handleBarTemplate } from '../shared/templateSetter';

class myAccountSummary {
    constructor() {
        self = this;
        this.element = ".default-account-smmary";
    }
    init() {        
        if(this.element.length) {
            eventBindingInst.bindLooping({
                "click .ag-reward-popup": "rewardPopup"
            },self);
            $(document).on("hidden.bs.modal", function () {
                self.closeRewardsModal();
            });
            this.render();
        }     
    }
    rewardPopup(el,evt){
        evt.preventDefault();
        $(self.element).find("#rewardModal").modal("show");        
    }
    closeRewardsModal(){
        $(self.element).find("a.ag-reward-popup").focus();
    }
    render() {    
    	let loggedUserName = decodeURIComponent(getCookie("MATTEL_WELCOME_MSG")).split(",");
        $(".username span").append(" "+ loggedUserName[0].indexOf("+")!=-1 ? loggedUserName[0].split("+").join(" ") : loggedUserName[0]);
        ajaxRequestInst.ajaxCall(apiConfigInst.getApiConfig("defaultAccSummary"))
            .then(data => { 
                const summaryData = data;   
                const loyaltyStatusFlag = summaryData.agRewards.loyaltyDetails;
                sessionStorage.setItem("loyalty", JSON.stringify(loyaltyStatusFlag));
                if(loyaltyStatusFlag && loyaltyStatusFlag.loyaltyStatus && loyaltyStatusFlag.loyaltyStatus.toLowerCase() == "active") {
                    handleBarTemplateInst.loadTemplate("#rewardDetailsTemp","#rewardDetails",summaryData.agRewards ,"replace");                    
                }else {
                    $(".banner-outer").removeClass("hide");
                } 
                let shipBillAdd = summaryData.userInfo.addresses.filter(d => d.defaultAddress).filter(d => {
                    if(d.addressType =="ShippingAndBilling") {
                        d.addressLine.pop();
                        if(d.addressLine[1].trim() == "") {
                            d.addressLine.pop();
                        }
                    }
                    return d.addressType =="ShippingAndBilling";
                });     
                if(!shipBillAdd.length) {
                    shipBillAdd = summaryData.userInfo.addresses.filter(d => d.defaultAddress).filter(d => {
                        d.addressLine.pop();
                        if(d.addressLine[1].trim() == "") {
                            d.addressLine.pop();
                        }
                        return d.addressType =="Shipping";
                    });
                }   
                
                handleBarTemplateInst.loadTemplate("#addressDetailsTmpl","#defaultAdd",shipBillAdd.length ?  shipBillAdd[0] : {},"replace");
                handleBarTemplateInst.loadTemplate("#shippingDetailsTmpl","#defaultShip",summaryData.userInfo ,"replace");
                let defaultCard;
                $.each(summaryData.defaultPayment.Cards, (key,val) => {
                    if(val.defaultFlag == "1"){
                        defaultCard = true;
                        if(val.address.addressLine) {
                            val.address.addressLine.pop();
                        }  
                        let cardnum = val.maskAccount;
                        val.cardEndDigits = cardnum.substr(cardnum.length - 4);
                        handleBarTemplateInst.loadTemplate("#paymentDetailsTmpl","#defaultPayment",val,"replace");
                    } 
                });
                if(!defaultCard) {
                    handleBarTemplateInst.loadTemplate("#paymentDetailsTmpl","#defaultPayment",{},"replace");
                }
                
             })
            .catch(error => { 
                console.log(error) 
            });
    }
}

let self;
let eventBindingInst = new eventBinding();
let apiConfigInst = new apiConfig();
let ajaxRequestInst = new ajaxRequest();
let handleBarTemplateInst = new handleBarTemplate();
let myAccountSummaryInstance = new myAccountSummary();
myAccountSummaryInstance.init();

