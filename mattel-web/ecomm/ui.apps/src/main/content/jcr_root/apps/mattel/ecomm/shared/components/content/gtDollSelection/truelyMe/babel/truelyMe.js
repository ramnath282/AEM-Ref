import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import { getCookie} from '../shared/browserCookie';
import { handleBarTemplate } from '../shared/templateSetter';
import { handleBarsHelper } from "../shared/handleBarsHelper";
import Constants from "../shared/constant";

class truelyMeProducts {
  constructor() {
    self = this;
    evtBinding.bindLooping(this.bindingEventsConfig(), this);
    this.eleme = ".gt-container>.row div.gt-product-trulyme-wrapper";
    this.priceObjName = self.getActivePriceName();
	this.tmImageParams=$('#gtTMImageParams').val() || '&wid=150&hei=198&fit=hfit,0&crop=550,000,600,800&layer=comp&fmt=jpeg&qlt=85,0&resMode=sharp2';
    this.bindingHelperFn();  
  }
  
  init(){
	  this.getTruelyMeResponse();
  }
  renderData(){
	  $(self.eleme).find('.gt-link').removeClass('hide');
  }
 bindingEventsConfig() {
    let eventsArr = {      
      "click .getTruelyMeProducts": "getTruelyMeResponse"
    };
    return eventsArr;
  }
 
 bindingHelperFn() {
	 handleBarsHelperInst.checkIFConditions("ifEquals");
	 handleBarsHelperInst.checkIFConditions("ifNotEquals");
     handleBarsHelperInst.callRegisterHelper("getMMDDYYYY");
} 
 
  getTruelyMeResponse() {
	  $(self.eleme).find('span.gt-quiz-display-recipient').html(getCookie('gt-quiz-display-recipient'));
	  let storeSelected = localStorage.getItem("storeSelected");
	  let availableResult = [];
	    var categoryParam = document.getElementById("gt-trulyme-wrapper").getAttribute("data-snp-param");
	    const payload = apiData("products")["getSAndP"].apply({
	      queryString: categoryParam
	    });
	    request.ajaxCall(payload)
	      .then(data => {
	        try {	        
	          const response = typeof data == "string" ? JSON.parse(data) : data;	
	          let products = response.resultsets[0];
	        	    $.each(products.results, (k, v) => {
								v.layerCompParams='?'+self.tmImageParams;
	        		      		let imageLink = v.imageLink,
	        		      			scene7Url = "";
	        		      		if(imageLink){
	        		      			scene7Url = imageLink.substring(0, imageLink.lastIndexOf("/") + 1);
	        		      			v.scene7Url = scene7Url;
	        		      		}
	        		      		let targetPricing=self.priceObjName;
	        		      		if(v.availability == "Backorderable") {
	         		      			let dateStr=v.backorder_date; 
	         		      			let a=dateStr.split(" ");
	         		      			let d=a[0].split("-");
	         		      			let t=a[1].split(":");
	         		      			let date = new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
	         		      			v.backorder_date = date;
	         		      		}
	        		      		$.each(v.prices, (index, value) => {
	                            	let priceObject=value;
	                            	if(targetPricing in priceObject )
	                            	{
	                            		v.targetPricing=priceObject[targetPricing][0].price;
	                            	}
	                            	
	                            });
	        		      		if(v.availability != "noLongerAvailable" && v.availability != "Unavailable") {
	         		      			availableResult.push(v);
	         		      		}
	        		      	}); 
	        	    if(typeof(storeSelected) != "undefined" && storeSelected !== "" && storeSelected != null) {
	                	 self.checkAvailability(products, storeSelected);
	                 }
	                 else {
	                	 handleBarTemplateInst.loadTemplate('#trulyMe-template','#trulyMeDollList', availableResult , 'append');
						 self.renderData();
	                 }
	        } catch (e) {
	          exceptionHandler("error", "Failed to load S&P products");
	        }
	      })
	      .catch(error => {	        
	        exceptionHandler("error", "S&P service failed.please try again..");
	      });
	  }
  
  getActivePriceName() {
      let loyalityPriceName = getCookie(
        Constants.miniCartCookieName || "MATTEL_CUSTOMER_SEGMENT"
      );
      let employeePrice = getCookie(
        Constants.EmployeePriceCookieName || "EmployeeSegment"
      );
      if (employeePrice == true || employeePrice == "true") {
        if (loyalityPriceName) {
          return "employee_loyalty_price";
        }
        return "employee_price";
      } else if (
        loyalityPriceName == "SILVER" ||
        loyalityPriceName == "GOLD" ||
        loyalityPriceName == "BERRY"
      ) {
        return "loyalty_price";
      } else {
        return "sale_price";
      }
    }
  
  checkAvailability(products, storeSelectedValue) {
	  let catVal = $("#catalogId").val();
	  	let partNumberArr = [];
		$.each(products.results, (k, v) => {
			partNumberArr.push(v.PartNumber.toString());
		}); 
		let errorMessage;
		 let ajaxOptions = jQuery.extend({},apiConfigInst.getApiConfig("storeAvailability").update);
		ajaxOptions.data = JSON.stringify({ catalogId: catVal ? catVal : "",
			storeSelected: storeSelectedValue, 
			productsList: partNumberArr});
		request.ajaxCall(ajaxOptions).then(data => {
			   try {  
			      const responseATP = typeof data == "string" ? JSON.parse(data) : data; 
				  let availableResult = [];
			      $.each(products.results, (k, v) => {
						for(var i=0;i<responseATP.itemAvailabilityDetailsList.length;i++) {
							let itemCode = responseATP.itemAvailabilityDetailsList[i].itemCode;
							let availabilityStatus = responseATP.itemAvailabilityDetailsList[i].availabilityStatus;
							let partNumber = v.PartNumber;
							if(partNumber === itemCode && availabilityStatus != "T" && availabilityStatus != "S") {
								if(availabilityStatus == "A" || availabilityStatus == "N" || availabilityStatus == "M") {
									v.availability = "Available";
									availableResult.push(v);
								}
								else {
									v.availability = "Unavailable";
								}
								
							}
							else {
								if(partNumber === itemCode) {
								v.availability = "Unavailable";
								}
							}
						}
					}); 
			      handleBarTemplateInst.loadTemplate('#trulyMe-template','#trulyMeDollList', availableResult , 'append');
			   } catch (e) {
	                 errorMessage = e.message;
	                 exceptionHandler("error", "Failed to load ATP products");
	                 console.log(`ATP JSON Format error: ${errorMessage}`);            
	               }
			    }); 
	}
}

let self;
const request = new ajaxRequest();
const evtBinding = new eventBinding();
const apiConfigInst = new apiConfig();
const apiData = new apiConfig().getApiConfig;
const handleBarTemplateInst = new handleBarTemplate();
const handleBarsHelperInst = new handleBarsHelper();
const truelyMeProductsInst = new truelyMeProducts();
truelyMeProductsInst.init(); 
