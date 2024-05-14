import { getCookie } from './browserCookie';

export default class fpApiConfig {
	constructor() {
		self = this;
		this.searchapiUrl = $('#snpAccountUrl').val();

		this.config = {

			headerNavigation:{
	         navigationUrl:function() {
            let localPath = ''
			let path = '/bin/getNavigation',
				currentPath = $('#headerNodePath').val(),
				deviceType = '';
			if (window.innerWidth > 1199) {
				deviceType = 'desktop';
			} else {
				deviceType = 'mobile';
			}
				 return {
					"type": "get",
				    "url": window.location.protocol + "//" + window.location.host + path + "?currentPath=" + currentPath + "&deviceType=" + deviceType,
					// "url": '//' + location.host + '/dist/mockjson/fp_header.json',
				 	"contentType": 'application/json',	
				 }

			 }
		     	
			},
			  giftFinderParams: function(birthQuery,ageQuery) {
				let url = $('#snpAccountUrl').val() || '//stage-sp1004f984.guided.ss-omtrdc.net/';
				return {
					"url":'https:'+url+'?i=1;q1='+birthQuery+';q2='+ageQuery+';x1=marketingAge;x2=pricerange',
					"body": "",
					"type": "POST",
					"contentType": 'application/json',	

				}
			},
 
			relatedProduct: function(DisMaxItem,globalSearch) {
				let searchapiUrl = $('#snpAccountUrl').val() || '//stage-sp1004f984.guided.ss-omtrdc.net/';
				return {
					"url":'https:' + searchapiUrl + '?do=related&i=1&count=' + DisMaxItem + '&q=' + globalSearch +'*',
					"body": "",
					"type": "POST",
					"contentType": 'application/json',	
				}
			},

			getRedirectPage: function(globalSearch){
				let searchapiUrl = $('#snpAccountUrl').val() || '//stage-sp1004f984.guided.ss-omtrdc.net/';
				return {
				"url": 'https:' + searchapiUrl + '?q=' + globalSearch,
				"body": "",
				"type": "POST",
				"contentType": 'application/json',
				}
			},
 
	    	popularProduct: function(count){
				let searchapiUrl = $('#snpAccountUrl').val() || '//stage-sp1004f984.guided.ss-omtrdc.net/';
				return {
				"url": 'https:'+searchapiUrl+'?do=popular&i=1&count='+count,
				"body": "",
				"type": "POST",
				"contentType": "text/plain",
     			}
			},

			autoSearchResult: function(DisMaxItem){
				let autoCompleteURL = $("#aCompleteapiUrl").val();
				return {
				     "url": 'https:' + autoCompleteURL + '?max_results=' + DisMaxItem + '&query=',
					 "body": "",
					 "type": "POST",
					 "contentType": 'application/json'
				 }
			 },

		};
	}

	getApiConfig(reqConfig) {
		return self.config[reqConfig];
	}
	getParamValueFromURL(attrName) {
		const urlParam = urlName => {
			urlName = urlName.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
			let regexS = "[\\?&]" + urlName + "=([^&#]*)";
			let regex = new RegExp(regexS);
			let results = regex.exec(window.location.search);
			if (results) return results[1];
			else return results;
		};
		return urlParam(attrName);
	}
}
let self;
