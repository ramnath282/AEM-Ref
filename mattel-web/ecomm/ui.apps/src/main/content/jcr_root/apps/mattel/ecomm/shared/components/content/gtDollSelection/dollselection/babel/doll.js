import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import { exceptionHandler } from '../shared/flickerMessage';
import { getCookie} from '../shared/browserCookie';
import { handleBarTemplate } from '../shared/templateSetter';
import { handleBarsHelper } from '../shared/handleBarsHelper';
import Constants from "../shared/constant";

class dollProducts {
  constructor() {
    self = this;   
    this.bindingHelperFn();
    this.dialogQuery= $('#gt-product-recommend-wrapper').attr('data_categoryTypeRecommend');
	this.element = ".gt-container>.row div.gt-product-recommend-wrapper";
    this.numberOfRecordsToShow= parseInt($('#gt-product-recommend-wrapper').attr('data_noOfProducts'));
	this.nonTMImageParams=$('#gtDollImageParams').val() || '&wid=800&fmt=png&qlt=85,0&resMode=sharp2&op_usm=0.9,1.0,8,0';
    this.priceObjName = self.getActivePriceName();
  }
  
  init(){
	  
         this.dollResponse();
  }

  renderProductList(){
		$(self.element).find('span.gt-quiz-display-recipient').html(getCookie($(self.element).find('span').attr('class')));
      if($(self.element).find(".product-tile").length > self.numberOfRecordsToShow){
          $(self.element).find(".show-less-products").addClass("hide");
          $(self.element).find(".view-all-products").removeClass("hide");
          $(self.element).find(".product-tile:gt("+(self.numberOfRecordsToShow - 1)+")").addClass("hide");
      } else if($(self.element).find(".product-tile").length <= self.numberOfRecordsToShow) {
		  $(self.element).find(".show-less-products").addClass("hide");
		  $(self.element).find(".view-all-products").addClass("hide");
	  }
	  else{
          $(self.element).find(".view-all-products").addClass("hide");
          $(self.element).find(".show-less-products").removeClass("hide");
          $(self.element).find(".product-tile:gt("+(self.numberOfRecordsToShow - 1)+")").removeClass("hide");
      }
  }
  
  bindingHelperFn() {
	    handleBarsHelperInst.checkIFConditions("greaterThan");
	    handleBarsHelperInst.checkIFConditions("lessThan");
	    handleBarsHelperInst.checkIFConditions("ifEquals");
   	 	handleBarsHelperInst.checkIFConditions("ifNotEquals");
        handleBarsHelperInst.callRegisterHelper("getMMDDYYYY");
	  }
  getQueryString()
	{
		let queryString = this.dialogQuery;
		let gtQuizParam=self.getCookiesValuesForSnp();
		queryString=queryString+gtQuizParam;
		return queryString;
	}
	getCookiesValuesForSnp(){
		var cookieParamString="";		
		let quizAttrValue=getCookie('gt-quiz-display-attr-value');
		let quizAspireValue=getCookie('gt-quiz-display-aspire-value');
				
		if(typeof(quizAttrValue) != "undefined" && quizAttrValue !== "" && quizAttrValue != null)
		{
			cookieParamString=cookieParamString+'&TrunkTrait=';
			var quizAttrArr=quizAttrValue.split(',');
			
			for (let i = 0; i < quizAttrArr.length; i++) { 
				if(i < quizAttrArr.length-1) {
				cookieParamString=cookieParamString+quizAttrArr[i]+'+';
				}
				else {
				cookieParamString=cookieParamString+quizAttrArr[i];
				}
			}
		}
		if(typeof(quizAspireValue) != "undefined" && quizAspireValue !== "" && quizAspireValue != null)
		{
			cookieParamString=cookieParamString+'&TrunkAspiration=';
			var quizAspireArr=quizAspireValue.split(',');
			
			for (let i = 0; i < quizAspireArr.length; i++) {
				if(i < quizAspireArr.length-1) {
					cookieParamString=cookieParamString+quizAspireArr[i]+'+';
				}
				else {
					cookieParamString=cookieParamString+quizAspireArr[i];
				}
				}
		}
		
		return cookieParamString;
	}
  
  dollResponse() {
	  let availableResult1 = [];
	   let framedQueryString = self.getQueryString();
	   let storeSelected = localStorage.getItem("storeSelected");
           let errorMessage;          
           const payload = apiData("products")["getSAndP"].apply({
             queryString: framedQueryString
           });
           request.ajaxCall(payload)
             .then(data => {
               try {         
                 const response = typeof data == "string" ? JSON.parse(data) : data;          
                 let products = response.resultsets[0]; 
                 $.each(products.results, (k, v) => {
					 v.layerCompParams='?'+self.nonTMImageParams;
 		      		let imageLink = v.imageLink,
 		      			scene7Url = "";
 		      		if(imageLink){
 		      			scene7Url = imageLink.substring(0, imageLink.lastIndexOf("/") + 1);
 		      			v.scene7Url = scene7Url;

 		      		}
 		      		if(v.availability == "Backorderable") {
 		      			let dateStr=v.backorder_date; 
 		      			let a=dateStr.split(" ");
 		      			let d=a[0].split("-");
 		      			let t=a[1].split(":");
 		      			let date = new Date(d[0],(d[1]-1),d[2],t[0],t[1],t[2]);
 		      			v.backorder_date = date;
 		      		}
 		      		let targetPricing=self.priceObjName;
 		      		$.each(v.prices, (index, value) => {
                    	let priceObject=value;
                    	if(targetPricing in priceObject )
                    	{
                    		v.targetPricing=priceObject[targetPricing][0].price;
                    	}
                    	
                    });
 		      		if(v.availability != "noLongerAvailable" && v.availability != "Unavailable") {
 		      			availableResult1.push(v);
 		      		}
 		      	}); 
                 if(typeof(storeSelected) != "undefined" && storeSelected !== "" && storeSelected != null) {
                	 self.checkAvailability(products, storeSelected);
                 }
                 else {
                 
                 handleBarTemplateInst.loadTemplate('#dollrecommend-template1','#dollrecommendList', availableResult1 , 'append');
				 $(self.element).find(".product-truly-me").removeClass("hide");
				 handleBarTemplateInst.loadTemplate('#dollrecommend-template2','#dollrecommendList-remaining', availableResult1 , 'append'); 				 
				 self.renderProductList();
                 }
               } catch (e) {
                 errorMessage = e.message;
                 exceptionHandler("error", "Failed to load S&P products");
                 console.log(`S&P JSON Format error: ${errorMessage}`);            
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
	  let errorMessage;
	  let ajaxOptions = jQuery.extend({},apiConfigInst.getApiConfig("storeAvailability").update);
		 ajaxOptions.data = { 
				"catalogId": catVal ? catVal : "",
				"storeSelected": storeSelectedValue ? storeSelectedValue : "",
				"productsList":[]
		}; 
		$.each(products.results, (k, v) => {
			ajaxOptions.data.productsList.push(v.PartNumber.toString());

		}); 		
		
		ajaxOptions.data = JSON.stringify(ajaxOptions.data);
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
			      
			      handleBarTemplateInst.loadTemplate('#dollrecommend-template1','#dollrecommendList', availableResult , 'append');
					 $(self.element).find(".product-truly-me").removeClass("hide");
					 handleBarTemplateInst.loadTemplate('#dollrecommend-template2','#dollrecommendList-remaining', availableResult , 'append'); 				 
					 self.renderProductList();
			   } catch (e) {
	                 errorMessage = e.message;
	                 exceptionHandler("error", "Failed to load ATP products");
	                 console.log(`ATP JSON Format error: ${errorMessage}`);            
	               }
			    });   
	} 
}
let self,
    request = new ajaxRequest(),
    evtBinding = new eventBinding(),
    apiConfigInst = new apiConfig(),
    apiData = new apiConfig().getApiConfig,
    handleBarTemplateInst = new handleBarTemplate(),
    handleBarsHelperInst = new handleBarsHelper(),
    dollProductsInst = new dollProducts();
	dollProductsInst.init(); 
