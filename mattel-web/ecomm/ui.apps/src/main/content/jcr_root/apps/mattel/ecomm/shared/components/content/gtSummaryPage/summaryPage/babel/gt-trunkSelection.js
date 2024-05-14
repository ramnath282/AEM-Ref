/*import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import {setCookie, getCookie} from '../shared/browserCookie';
import { handleBarTemplate } from '../shared/templateSetter';
import { handleBarsHelper } from "../shared/handleBarsHelper";
import { gtSummaryPageShared } from "../shared/gtSummaryPageShared";
class trunkSelection {
	constructor() {
			self = this;
			this.element = ".gt-container>.row div.gt-bundle-selection-wrapper div.trunk div.offerSize";           
			this.bindingHelperFn();			
			evtBinding.bindLooping(this.bindingEventsConfig(), this);
	}
    init() {
    	
    }
    loadTrunkJsonData()
    {

    	this.smallTrunkData= self.getSmallTrunkDetails();
    	this.largeTrunkData=self.getLargeTrunkDetails();
    	self.renderProductList();
    	console.log(this.smallTrunkData);
    	console.log(this.largeTrunkData);
    }
    bindingHelperFn() {
    	 handleBarsHelperInst.checkIFConditions("ifEquals");
    	 handleBarsHelperInst.checkIFConditions("ifNotEquals");
         handleBarsHelperInst.callRegisterHelper("getMMDDYYYY");
         handleBarsHelperInst.checkIFConditions("checkIndexExist");
    }
	bindingEventsConfig() {
		let eventsArr = {
			'click .gt-container > .row div.gt-bundle-selection-wrapper div.trunk div.offerSize #smallTrunk': 'smallTrunkChange',
			'click .gt-container > .row div.gt-bundle-selection-wrapper div.trunk div.offerSize #largeTrunk': 'largeTrunkChange',
		};
		return eventsArr;
	}
    renderProductList(){
    	let hearingAidtemplate=self.executeHandleBar("#hearingAidTemplate",self.largeTrunkData.gtHearingAidDetails);
		let earPiercingTemplate=self.executeHandleBar("#earPiercingTemplate",self.largeTrunkData.gtEarPiercingDetails);
		// append letter
		$(".trunk").append(hearingAidtemplate.templateData);
		$(".trunk").append(earPiercingTemplate.templateData);
    	if(typeof self.largeTrunkData != "undefined" && self.largeTrunkData != null && self.largeTrunkData != "")
    	{	self.sortHandleBarTemplate(self.largeTrunkData);       		
    	}
    	else if(typeof self.smallTrunkData != "undefined" && self.smallTrunkData != null && self.smallTrunkData != "")
    	{
    		self.sortHandleBarTemplate(self.smallTrunkData);       		
    	}
    	else
    	{
    		return false;
    	}
    }
	
    smallTrunkChange()
    {
    	$("#categoryBundle2").nextAll().remove();
		if(parseInt(self.smallTrunkData.attributes.descripitiveAttributes.PlaypackCount)==1)
		{
			if($("#categoryBundle1"))
					$("#categoryBundle1").show();
			if($("#categoryBundle2"))
			{
				$("#categoryBundle2").hide();
			}
			
		}
		
		self.sortHandleBarTemplate(self.smallTrunkData);
		self.productOfferprice(self.smallTrunkData);
    }

    
    largeTrunkChange()
    {
    	$( "#categoryBundle2" ).nextAll().remove();
    	if(parseInt(self.largeTrunkData.attributes.descripitiveAttributes.PlaypackCount)==2)
		{
			if($("#categoryBundle1"))
					$("#categoryBundle1").show();
			if($("#categoryBundle2"))
				$("#categoryBundle2").show();
			
		}
    	self.sortHandleBarTemplate(self.largeTrunkData);    	
    	self.productOfferprice(self.largeTrunkData);		
    }
    sortHandleBarTemplate(data)
    {

		var sortOrder=self.sortObject(data.kitSequence);
		sortOrder.forEach(function(item,index){
			if(data.gtHearingAidDetails != null)
			{
				let hearingAidtemplate=self.executeHandleBar("#hearingAidTemplate",data.gtHearingAidDetails);
				if(item.key == hearingAidtemplate.skuId)
				{
					$(".trunk").append(hearingAidtemplate.templateData);
				}
			}
			if(data.gtEarPiercingDetails != null)
			{
				let earPiercingTemplate=self.executeHandleBar("#earPiercingTemplate",data.gtEarPiercingDetails);
				if(item.key == earPiercingTemplate.skuId)
				{				
					$(".trunk").append(earPiercingTemplate.templateData);					
				}
			}
		});
		$(".gt-container > .row div.gt-bundle-selection-wrapper div.option-details .choice").bind( "click", function(e) {
			gtSummaryPageSharedInst.optionSelection(e.target);
          });
		
    }
    getSmallTrunkDetails()
    {
    	let smallTrunkData="";
    	let smallTrunkJson=$("#smallTrunkData").html();
    	if(typeof smallTrunkJson != "undefined" && smallTrunkJson != null && smallTrunkJson != "")
    		{
    			 smallTrunkData= JSON.parse(smallTrunkJson);
    			 
    		}
    	return smallTrunkData;
    }
    getLargeTrunkDetails()
    {
    	let largeTrunkData="";
    	let largeTrunkJson=$("#largeTrunkData").html();
    	if(typeof largeTrunkJson != "undefined" && largeTrunkJson != null && largeTrunkJson != "")
		{		
    		 largeTrunkData= JSON.parse(largeTrunkJson);    		
		}
    	return largeTrunkData;
    };
    

    executeHandleBar(template,data)
    {
    	var templateObj={};    
    	if(typeof data != "undefined" && data != null)
   		{	
    		   		
   			var source = $(template).html(); 
   			var templatesource = Handlebars.compile(source);
   			var templateData =templatesource(data);		
   			templateObj["skuId"]=data.partNumber;
   			templateObj["templateData"]=templateData;
   		}
   		return templateObj;
   }

   sortObject(obj)
   {
        var arr = [];
    	var sortedObj={};
        var prop;
        for (prop in obj) {
            if (obj.hasOwnProperty(prop)) {
                arr.push({
                    'key': prop,
                    'value': obj[prop]
                });
            }
        }
        arr.sort(function(a, b) {
            return a.value - b.value;
        });
    	
        return arr; // returns array
    }
  productOfferprice(data) { 
	let gtHearingAidDetailsSKU=data.gtHearingAidDetails.partNumber;
	let gtEarPiercingDetailsSKU=data.gtEarPiercingDetails.partNumber; 
   
   let ajaxOptions = jQuery.extend({},apiConfigInst.getApiConfig("skuCompPrice").get),
   partNumberArr = [];
   partNumberArr.push(gtHearingAidDetailsSKU);
   partNumberArr.push(gtEarPiercingDetailsSKU);
   
   let partNumbers = partNumberArr.toString();
   ajaxOptions.data = JSON.stringify({ partNumbers: partNumbers });

   request.ajaxCall(ajaxOptions).then(data => {	     
	      self.populatePricingValue(data);
	    });
  }
  
  populatePricingValue(data)
  {
	  let priceList=data.priceDetails.product_priceList;
	  for(var i = 0; i < priceList.length; i++) {		   
		   let partnumber=priceList[i].partNumber;
		   let price=priceList[i].unitPrice[0].price.value;
		   let productElement=$("#gt-bundle-selection-wrapper").find('[data-sku-id='+partnumber+']');
		   if(typeof productElement != 'undefined' && productElement != null)
			   {
			   		if(price > 0)
			   		{
			   			console.log("into pricing");
			   			console.log($(productElement).find('gt-price'));
			   			console.log(price);
			   			let priceString='$'+price;
			   			$(productElement).find('.gt-price').text(priceString);
			   		}
			   		else
			   		{
			   			$(productElement).find('.gt-price').text('free');
			   		}
			   }
		}
  }
}



let self;
const request = new ajaxRequest();
const evtBinding = new eventBinding();
const apiConfigInst = new apiConfig();
const handleBarsHelperInst = new handleBarsHelper();
const handleBarTemplateInst = new handleBarTemplate();
const apiData = new apiConfig().getApiConfig;
const gtSummaryPageSharedInst=new gtSummaryPageShared();
const trunkSelectionInst = new trunkSelection();

$( document ).ready(function() {
	trunkSelectionInst.loadTrunkJsonData();
});*/