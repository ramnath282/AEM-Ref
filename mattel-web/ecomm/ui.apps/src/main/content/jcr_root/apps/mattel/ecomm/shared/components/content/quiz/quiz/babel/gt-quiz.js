import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import {setCookie, getCookie} from '../shared/browserCookie';
import constant from '../shared/constant';
import { exceptionHandler } from '../shared/flickerMessage';
class gtQuiz {
	constructor() {
			self = this;
			this.element = ".gt-container>.row div.gt-quiz-block section";
			this.displayPath = ".gt-container>.row div.gt-quiz-block section .";
			this.checkedCount = 0;
			this.skuItems = [];
			this.trunkSKU="";
			evtBinding.bindLooping(this.bindingEventsConfig(), this);
	}
	bindingEventsConfig() {
		let eventsArr = {
			'keyup .gt-container > .row div.gt-quiz-block section .gt-quiz-input-holder input[type="text"]': 'quizInputFocus',
			'keypress .gt-container > .row div.gt-quiz-block section .gt-quiz-input-holder input[type="text"]': 'continueClick',
			'change .gt-container > .row div.gt-quiz-block section .gt-quiz-option-group input[type="radio"]': 'selectionRdoOption',
			'change .gt-container > .row div.gt-quiz-block section .gt-quiz-option-group input[type="checkbox"]': 'selectionChkOption',
			'submit .gt-container > .row div.gt-quiz-block section form': 'moveNext',
			'click .gt-container > .row button.retail-online': 'storeRetailOnlineValues',
			'click .gt-container > .row button.retail-instore': 'retailInStoreValues',
			'click .gt-container > .row button.online-purchase': 'normalQuizFlow',
            'click .gt-container > .row a.gt-btn.summary-page' : 'summaryPageClick'
		};
		return eventsArr;
	}
	 init() {

    }
	 normalQuizFlow(ele) {
		 const $ele = $(ele);
		 let href = $ele.attr("href");

		 window.location.href = href;
		 localStorage.setItem("RetailSelected","Online");
		 localStorage.setItem("storeSelected","");
		 localStorage.setItem("email","");
		 localStorage.setItem("associateCode","");
	 }

	 summaryPageClick(ele){
        localStorage.setItem("letterEdited","N");
     }

	 storeRetailOnlineValues(ele) {
		 let i, parameter, params, query;
		 let  uri = window.location.search;
	      if (uri.indexOf("?") != -1) {
	      query = uri.slice(1);
	      params = query.split("&");
	      i = 0;
	      while (i < params.length) {
	        parameter = params[i].split("=");
	        if(parameter[0] == "associateCode") {
	        localStorage.setItem(parameter[0],parameter[1].replace(/%20/g, " "));
	        }
	        i++;
	      }
	      }
		 const $ele = $(ele);
		 let href = $ele.attr("href");
		 window.location.href = href;
		 localStorage.setItem("RetailSelected","Online");
		 let storename = localStorage.getItem("storeSelected");
		 let email = localStorage.getItem("email");

		if(typeof(storename) != "undefined" && storename != "") {
			localStorage.setItem("storeSelected","");
		}
		if(typeof(email) != "undefined" && email != "") {
			localStorage.setItem("email","");
		}


	 }

	 retailInStoreValues(ele) {
		 const $ele = $(ele);
		 let href = $ele.attr("href");

		 window.location.href = href;
		 localStorage.setItem("RetailSelected","Instore");
		 let i, parameter, params, query;

	      let  uri = window.location.search;

	      if (uri.indexOf("?") != -1) {
	      query = uri.slice(1);
	      params = query.split("&");
	      i = 0;
	      while (i < params.length) {
	        parameter = params[i].split("=");
	        if(parameter[0] == "storeSelected" || parameter[0] == "email" || parameter[0] == "associateCode") {
	        localStorage.setItem(parameter[0],parameter[1].replace(/%20/g, " "));
	        }
	        i++;
	      }
	      }
	      else {
	    	  	let storename = localStorage.getItem("storeSelected");
	 		 	let email = localStorage.getItem("email");
	 		 	let code = localStorage.getItem("associateCode");
	    	  if(typeof(storename) != "undefined" && storename != "") {
	    		  localStorage.setItem("storeSelected","");
				}
				if(typeof(email) != "undefined" && email != "") {
					localStorage.setItem("email","");
				}
				if(typeof(code) != "undefined" && code != "") {
					localStorage.setItem("associateCode","");
				}
	      }
	 }

	selectionRdoOption(ele) {
		const $ele = $(ele);
		if($ele.parent().data('mandate') == true) {
			if ($ele.is(':checked')) {

				$ele.parent().next().removeClass('disabled');

			}
		}
	}
	selectionChkOption(ele) {
		const $ele = $(ele);
		if($ele.parent().data('mandate') == true) {
			($ele.is(':checked'))?self.checkedCount++ : self.checkedCount--;
			if (self.checkedCount <= $ele.parent().data('max')) {
				if (self.checkedCount >= $ele.parent().data('min')) {
					$ele.parent().next().removeClass('disabled');
				}
				else {
					$ele.parent().next().addClass('disabled');
				}
			}
			else{
				// $ele.next().addClass('disabled');
				self.checkedCount--;
				$(ele).prop("checked", false);
				// $ele.next().addClass('disabled');
			}
		}
	}
	continueClick(ele,evt) {

        if(evt.keyCode == 13){
            evt.preventDefault();
            return false;
        }
    }

	quizInputFocus(ele,evt) {
		const $ele = $(ele);

		let maxLength = $(ele).attr("maxlength");
		$ele.parent().children("small").eq(1).css("display","none");
		$ele.parent().children("small").eq(2).css("display","none");
		if($ele.parents('.gt-quiz-block').hasClass('retail')){
			var maxLen = $('.code-input').data('max-length');
			if(($('.name-input').val().length != 0) && ($('.code-input').val().length == maxLen)){
				$ele.parents('.gt-quiz-block').find('.gt-btn').removeClass('disabled');
			}else{
				$ele.parents('.gt-quiz-block').find('.gt-btn').addClass('disabled');
			}
		}
		if($ele.parent('.gt-quiz-input-holder').hasClass('associate-code')){
			$ele.parent().children("small").eq(0).css("display","none");
		}
		function letterCapitalize(str) {
				return str[0] ? str[0].toUpperCase()+str.substring(1) : '';
                // return str.split(" ").map(item=>item.substring(0,1).toUpperCase()+item.substring(1)).join(" ")
		}
		if($ele.data('mandate') == true) {

			if($ele.val().length!==0 && $ele.val().length <= maxLength && $ele.val().trim().length != 0) {

					var thisText = $ele.val();
					var trimText = thisText.replace(/^\s/, '');
					var capitalText = letterCapitalize(trimText);
					$ele.val(capitalText);
					let quizRegex = new RegExp("^[A-Za-z + & ' -]+$");

				if(!quizRegex.test($ele.val())) {
					$ele.parent().children('small').eq(0).css('display','block');

					$ele.parent().next().addClass('disabled');
					return false;
				}
				else{
					$ele.parent().children('small').eq(0).css('display','none');
					$ele.parent().children('small').eq(1).css('display','none');
					$ele.parent().next().removeClass('disabled');
				}
					//$ele.parent().children("small").eq(2).css("display","none");
			} else{
				$ele.parent().next().addClass('disabled');
				$ele.parent().children('small').eq(0).css('display','none');
				$ele.parent().children('small').eq(1).css('display','none');
			}

			if($ele.val().length!==0 && $ele.val().length >= maxLength && $ele.val().trim().length != 0){
				$ele.parent().children("small").eq(2).css("display","block");
				$ele.val($ele.val().substr(0, maxLength));
				evt.preventDefault();
				//return false;
			}
			else{
				$ele.parent().children("small").eq(2).css("display","none");
			}
		}
	}
	moveNext(ele, evt) {
		evt.preventDefault();
		const $ele = $(ele);
		const $eleBtn = $ele.find('button');
		let isValid=true;

		if($eleBtn.prev().data('control-type')=="input") {
			if($ele.parents('.retail').find('.associate-code').data('binder-name') == "gt-quiz-associate_code"){

				let associateCookie = localStorage.getItem("associateCode");

				if(typeof(associateCookie) != "undefined" && associateCookie !== "") {
				if($ele.parents('.retail').find('.associate-code input').val() != associateCookie){

					$eleBtn.prev().children('small').eq(0).css('display','block');
					return false;
				}

				else{
					$('#retailPurchaseButton').addClass("disabled");
					let storename = localStorage.getItem("storeSelected");
					let recipient = getCookie('gt-quiz-display-recipient');
					let sender = getCookie('gt-quiz-display-giver');
					let occasion = getCookie('gt-quiz-display-occassion');
					let letter = localStorage.getItem("letterContent");
					letter = letter.replace(/\n\n/gi, '&lt;br&gt;\n');
					let custName= $('#retailCustName').val() ;
					let emailMessage = "&lt;br&gt;\n &lt;p&gt;Customer Name = "+custName+"&lt;/p&gt;\n"+
						" &lt;p&gt;Recipient = "+recipient+"&lt;/p&gt;\n"+
						" &lt;p&gt;Sender = "+sender+"&lt;/p&gt;\n"+
						" &lt;p&gt;Occasion = "+occasion+"&lt;/p&gt;\n"+
						" &lt;p&gt;Envelope Text = "+recipient+"&lt;/p&gt;\n"+
						" &lt;p&gt;&lt;u&gt;LetterContent = &lt;/u&gt;&lt;br&gt;"+letter+"&lt;/p&gt;";
					let email = localStorage.getItem('email');
					let successLandingPage = $('#purchaseScreen').attr('data-successLandingPage');
					let serviceDetails = $('#tnsDetails').text();
					if (typeof serviceDetails != "undefined" && serviceDetails != null && serviceDetails != "") {
						let tnsServiceDetails = JSON.parse($('#tnsDetails').text());
						let ajaxOption = apiConfigInst.getApiConfig("gtTNSEmail");
						let today = new Date();
						let dd = self.formattingDateInput(today.getDate());
                        let mm = self.formattingDateInput(today.getMonth() + 1);
						let yyyy = today.getFullYear();
						let seconds = self.formattingDateInput(today.getSeconds());
                        let minutes = self.formattingDateInput(today.getMinutes());
                        let hour = self.formattingDateInput(today.getHours());
						let todayDate = yyyy + '-' + mm + '-' + dd;
						let timeStamp = yyyy + '-' + mm + '-' + dd + 'T' + hour +":"+ minutes +":"+ seconds;
						self.trunkSelected();
						self.dollSelected();
						self.bundleAddition();
						self.addOnAddition();
						self.gtNonDisplayableItems();
						self.gtLetterDetails();
						let emailSubject = mm +'-'+dd+'-'+yyyy+'-'+hour+minutes+seconds+'_'+self.trunkSKU+'_'+custName;
						let correlationId = mm+dd+yyyy+hour+minutes+seconds;
						ajaxOption.data = {
								"createNoticeEventReqInput":
								{
								 "createNoticeEventReq": {
									"noticeName": tnsServiceDetails.noticeName[0],
									"organizationId": tnsServiceDetails.organizationId[0],
									"originatingSystemCode": tnsServiceDetails.originatingSystemCode[0],
									"createDate": todayDate, //"2019-08-14",
									"eventNoticeData": {
									   "emailDetails": {
										  "senderName": tnsServiceDetails.senderName[0],
										  "recipientDetails": {
							                     "recipientDetail": {
							                        "contactEmailID": email
							                     }
							                  },
										  "emailSubject": emailSubject,
										  "emailMessage": emailMessage
									   },
									   "items": {
									   "item": self.skuItems
									   }
									}
								 },
								 "payloadHeader": {
									"source": tnsServiceDetails.source[0],
									"target": tnsServiceDetails.target[0],
									"correlationID": correlationId,
									"timeStamp": timeStamp,
									"environment": tnsServiceDetails.environment[0],
									"hostName": tnsServiceDetails.hostName[0],
									"user": tnsServiceDetails.user[0]
								 }
								}
							}

						ajaxOption.data = JSON.stringify(ajaxOption.data);
						request.ajaxCall(ajaxOption).then(data => {
					   		   try {
					   			   const responseEmail = typeof data == "string" ? JSON.parse(data) : data;
					   			   if(responseEmail.createNoticeEventReqOutput.CommonResponseMessage.status == 'Success') {
					   			   window.location.href = successLandingPage+".html?storeSelected="+storename+"&email="+email+"&associateCode="+associateCookie;
					   			      }
					   		   } catch (e) {
					   			console.log("TNS Email Catch");
					   			$('#retailPurchaseButton').removeClass("disabled")
					            exceptionHandler("error", "Failed to connect to TNS");
					                  }

					   	   })
					   	    .fail(function(err){
					   	    	console.log("Send Email Failed"+err);
					   	    	$('#retailPurchaseButton').removeClass("disabled")
					   	    	exceptionHandler("error", "Failed to trigger Email");
						})
				            .catch(error => {
								console.log("TNS Email Catch");
								$('#retailPurchaseButton').removeClass("disabled")
					            exceptionHandler("error", "Failed to connect to TNS");
				                  });
						}
					else {
						console.log("TNS Email Config Service failed");
						$('#retailPurchaseButton').removeClass("disabled")
			            exceptionHandler("error", "Failed to retrieve TNS config service Details");
					}
					}
				}
			}

			if($eleBtn.prev().children('input').data('mandate')== true && !$eleBtn.hasClass('disabled')) {
					$eleBtn.parents('.gt-quiz-block').find('.gt-btn').addClass('disabled');
					let ajaxOption = apiConfigInst.getApiConfig("profanityCheck").startCheck;
					ajaxOption.data = JSON.stringify({"giftMsgText": $eleBtn.prev().children('input').val(),"validateType": "giftMsg"});
					request.ajaxCall(ajaxOption).then(data => {
						if(data.hasOwnProperty("errorCode") || data.isNameValid) {
							$eleBtn.parents('.gt-quiz-block').find('.gt-btn').removeClass('disabled');
							self.handleProfanityMessage($ele,$eleBtn);
							$eleBtn.prev().children('.return_profinity_success').trigger('click');
						}
						else {
							$eleBtn.prev().children('small').eq(1).css('display','block');
							$eleBtn.prev().children('.return_profinity_error').trigger('click');
							$eleBtn.parents('.gt-quiz-block').find('.gt-btn').addClass('disabled');
					     	return false;
						}
					})
					.fail(function(err){
						console.log("WCS Failed"+err);
						self.handleProfanityMessage($ele,$eleBtn);
					})
					.catch(error => {
						console.log("Into Profanity catch");
						self.handleProfanityMessage($ele,$eleBtn);
					});

			} else {
					if($eleBtn.prev().children('input').data('mandate')== true && $eleBtn.hasClass('disabled'))
					{
						if($eleBtn.prev().children('input').val() === "")
							{
								return false;
							}
					}
				$eleBtn.prev().children('small').each((idx,elt)=>{
				        if($(elt).css('display') == 'block') {
				        	isValid=false;
				        }
				  })
				if(isValid)
					{
						$(self.displayPath+$eleBtn.prev().data('binder-name')).text($eleBtn.prev('div').children('input:text').val());
						setCookie($eleBtn.prev().data('binder-name'),$eleBtn.prev('div').children('input:text').val());
						self.proceedToNext($ele);
					}
				else
					{
						return false;
					}
			}
		}
		else if($eleBtn.prev().data('control-type')=="radio") {
			$(self.displayPath+$eleBtn.prev().data('binder-name')).text($eleBtn.prev().children("input[type=radio]:checked+label").text());
			setCookie($eleBtn.prev().data('binder-name'),$eleBtn.prev().children("input[type=radio]:checked+label").text());
			setCookie("gt-quiz-display-occassion-value",$eleBtn.prev().children("input[type=radio]:checked").val());
			if(!$eleBtn.hasClass('disabled')){
				self.proceedToNext($ele);
			}
		}
		else if($eleBtn.prev().data('control-type')=="checkbox") {
			let checkBoxArray = [];
			let checkBoxArrayVal = [];
			let cbName = $ele.find($('input[type="checkbox"]:checked+label:not(".disabled")'));
			let cbvName = $ele.find($('input[type="checkbox"]:checked:not(".disabled")'));
			$.each(cbName, (idx,obx) => {
				checkBoxArray.push($(obx).text().toLowerCase());
			});
			$.each(cbvName, (idx1,obx1) => {
				checkBoxArrayVal.push($(obx1).val());
			});
			if(checkBoxArray.length<=2) {
				$(self.displayPath+$eleBtn.prev().data('binder-name')).text(checkBoxArray.join(" and "));
				setCookie($eleBtn.prev().data('binder-name'),checkBoxArray.join(" and "));
			} else {
				$(self.displayPath+$eleBtn.prev().data('binder-name')).html((checkBoxArray.slice(0, (checkBoxArray.length-1)).join(", ")) + ', <small>and</small> ' + checkBoxArray[checkBoxArray.length-1]);
				setCookie($eleBtn.prev().data('binder-name'),(checkBoxArray.slice(0, (checkBoxArray.length-1)).join(", ")) + ', <small>and</small> ' + checkBoxArray[checkBoxArray.length-1]);
			}
			checkBoxArray = [];
			if(checkBoxArrayVal.length<=2) {
				setCookie($eleBtn.prev().data('binder-val'),checkBoxArrayVal.join(","));
			} else {
				setCookie($eleBtn.prev().data('binder-val'),(checkBoxArrayVal.slice(0, (checkBoxArrayVal.length-1)).join(",")) + ',' + checkBoxArrayVal[checkBoxArrayVal.length-1]);
			}
			checkBoxArrayVal = [];
			if(!$eleBtn.hasClass('disabled')){
				self.proceedToNext($ele);
			}
		}

	}
	handleProfanityMessage($ele,$eleBtn)
	{
		$(self.displayPath+$eleBtn.prev().data('binder-name')).text($eleBtn.prev('div').children('input:text').val());
		setCookie($eleBtn.prev().data('binder-name'),$eleBtn.prev('div').children('input:text').val());
		self.proceedToNext($ele);
	}
	proceedToNext(elem) {
		$(self.element).removeClass('active');
		elem.parents('.gt-quiz-block').next().children().addClass('active');
		self.checkedCount = 0;
		$("html, body").animate({ scrollTop: 0 }, "slow");
	}

	formattingDateInput(value) {
        if(value<10) {
              value = '0'+value;
        }
        return value;
    }

	trunkSelected() {
		let offerSize = localStorage.getItem("offerSize");
		if (typeof offerSize != "undefined" && offerSize != null && offerSize != "") {
			let sku, itemDescription, itemName;
			let parsedJSON = JSON.parse(offerSize);
	   		let partNumberJSON = parsedJSON.skuId;
	   		if (typeof partNumberJSON != "undefined" && partNumberJSON != null && partNumberJSON != "") {
	   			self.trunkSKU = partNumberJSON;
	   			sku = partNumberJSON;
	   		}
	   		if (typeof parsedJSON.title != "undefined" && parsedJSON.title != null && parsedJSON.title != "") {
	   			itemName = parsedJSON.title;
	   		}
	   		if (typeof parsedJSON.description != "undefined" && parsedJSON.description != null && parsedJSON.description != "") {
	   			itemDescription = "Trunk Description";
   			}
	   		let item = {
					"sku": sku,
					 "itemDescription": itemDescription,
					 "itemName": itemName
			};
   			self.skuItems.push(item);
		}
	}

	dollSelected() {
		let dollSku = getCookie('gt-product-doll-id');
		let dollDesc = localStorage.getItem('gt-product-doll-name');
		let sku, itemName;
			if (typeof dollSku != "undefined" && dollSku != null && dollSku != "") {
	   			sku = dollSku;
	   		}
	   		if (typeof dollDesc != "undefined" && dollDesc != null && dollDesc != "") {
	   			itemName = dollDesc;
	   		}
	   		let item = {
					"sku": sku,
					"itemDescription": "DollDescription",
					"itemName": itemName
			};
   			self.skuItems.push(item);

	}

	gtNonDisplayableItems() {
		let sku, itemName;
	    let nonDisplayable = localStorage.getItem("gtNonDisplayableItems");
	   if (typeof nonDisplayable != "undefined" && nonDisplayable != null && nonDisplayable != "") {
	   		let parsedJSON = JSON.parse(nonDisplayable);
	   		for (var i=0; i < parsedJSON.length; i++) {


				if (typeof parsedJSON[i].partNumber != "undefined" && parsedJSON[i].partNumber != null && parsedJSON[i].partNumber != "") {
		   				sku = parsedJSON[i].partNumber;
		   			}
		   			if (typeof parsedJSON[i].name != "undefined" && parsedJSON[i].name != null && parsedJSON[i].name != "") {
		   				itemName = parsedJSON[i].name;
		   			}
		   			let item = {
							"sku": sku,
							 "itemDescription": "description",
							 "itemName": itemName
					};
		   			self.skuItems.push(item);

	   	}
	   }
    }

    gtLetterDetails() {
	   let letter = localStorage.getItem("gtLetterDetails");
	   if (typeof letter != "undefined" && letter != null && letter != "") {
	   		let parsedJSON = JSON.parse(letter);
	   		let sku, itemName;
			if (typeof parsedJSON.partNumber != "undefined" && parsedJSON.partNumber != null && parsedJSON.partNumber != "") {
	   			sku = parsedJSON.partNumber;
	   		}
	   		if (typeof parsedJSON.name != "undefined" && parsedJSON.name != null && parsedJSON.name != "") {
	   			itemName = parsedJSON.name;
	   		}
	   		let item = {
					"sku": sku,
					"itemDescription": "LetterDescription",
					"itemName": itemName
			};
   			self.skuItems.push(item);

	   	}
    }

	bundleAddition() {
		  let keys = Object.keys(localStorage);
		   for(var i=0; i<keys.length; i++) {
		   		if(keys[i].startsWith("bundlelist")) {
		   			let sku, itemDescription, itemName;
		   			let bundlelist = localStorage.getItem(keys[i]);
		   			if (typeof bundlelist != "undefined" && bundlelist != null && bundlelist != "") {
		   			let parsedJSON = JSON.parse(bundlelist);

		   			if (typeof parsedJSON.skuId != "undefined" && parsedJSON.skuId != null && parsedJSON.skuId != "") {
		   				sku = parsedJSON.skuId.toString();
		   			}
		   			if (typeof parsedJSON.title != "undefined" && parsedJSON.title != null && parsedJSON.title != "") {
		   				itemName = parsedJSON.title;
		   			}
		   			if (typeof parsedJSON.description != "undefined" && parsedJSON.description != null && parsedJSON.description != "") {
		   				itemDescription = parsedJSON.description;
		   			}
		   			let item = {
							"sku": sku,
							 "itemDescription": itemDescription,
							 "itemName": itemName
					};
		   			self.skuItems.push(item);
		   			}

		   		}
		   	}
	   }

	   addOnAddition() {
		   let keys = Object.keys(localStorage);
		   for(var i=0; i<keys.length; i++) {
		   		if(keys[i].startsWith("addon")) {
		   			let sku, itemDescription, itemName;
		   			let addon = localStorage.getItem(keys[i]);
		   			if (typeof addon != "undefined" && addon != null && addon != "") {
		   			let parsedJSON = JSON.parse(addon);
		   			if (typeof parsedJSON.skuId != "undefined" && parsedJSON.skuId != null && parsedJSON.skuId != "") {
		   				sku = parsedJSON.skuId.toString();
		   			}
		   			if (typeof parsedJSON.title != "undefined" && parsedJSON.title != null && parsedJSON.title != "") {
		   				itemName = parsedJSON.title;
		   			}
		   			if (typeof parsedJSON.description != "undefined" && parsedJSON.description != null && parsedJSON.description != "") {
		   				itemDescription = "Add-On Description";
		   			}
		   			let item = {
							"sku": sku,
							 "itemDescription": itemDescription,
							 "itemName": itemName
					};
		   			self.skuItems.push(item);
		   			}
		   		}
		   	}
	   }
}

let self;
const evtBinding = new eventBinding();
const gtQuizInstance = new gtQuiz();
const apiConfigInst = new apiConfig();
const request = new ajaxRequest();
gtQuizInstance.init();

$(window).bind("pageshow",function(){
    $('form').each(function() { this.reset() });
});

$(document).ready(function () {
    $(".gt-container div.gt-quiz-block").on("focus", ":input", function (event) {
        $(window).one("scroll", function () {
            event.target.blur();
        });
    })
});
