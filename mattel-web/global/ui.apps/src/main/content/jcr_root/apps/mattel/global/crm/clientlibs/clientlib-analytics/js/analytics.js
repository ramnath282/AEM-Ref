var path = window.location.pathname;
var page = path.split("/").pop();
var page_name = page.replace(".html", "");
var language=$("html").attr("lang");
var pageTitle = $(document).find("title").text();
var jsonAnalytics={};
var analyticData = $(".data").attr("data-analytics-property");
var cleanUrl=window.location.pathname.replace(".html","");
var countryCode = $("body").attr("data-countrycode") ? $("body").attr("data-countrycode") : '';
if (analyticData!= null) {
jsonAnalytics = JSON.parse(analyticData);
}
jsonAnalytics["page_name"]=page_name;
jsonAnalytics["meta.site_language"]=language;
jsonAnalytics["page_title"]=pageTitle;
jsonAnalytics["clean_url"]=cleanUrl;
jsonAnalytics["countryCode"]=countryCode;
var device = function() {
    if (navigator.userAgent.match(/Tablet|iPad/i)) {
        return "Tablet";
    } else if (navigator.userAgent.match(/Mobile|Windows Phone|Lumia|Android|webOS|iPhone|iPod|Blackberry|PlayBook|BB10|Opera Mini|\bCrMo\/|Opera Mobi/i)) {
        return "Mobile";
    } else {
        return "Desktop"
    }
};
var browser = function() {
    // Return cached result if avalible, else get result then cache it.
    if (browser.prototype._cachedResult)
        return browser.prototype._cachedResult;

    // Opera 8.0+
    var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;

    // Firefox 1.0+
    var isFirefox = typeof InstallTrigger !== 'undefined';

    // Safari 3.0+ "[object HTMLElementConstructor]"
    var isSafari = /constructor/i.test(window.HTMLElement) || (function(p) { return p.toString() === "[object SafariRemoteNotification]"; })(!window['safari'] || safari.pushNotification);

    // Internet Explorer 6-11
    var isIE = document.documentMode;

    // Edge 20+
    var isEdge = !isIE && !!window.StyleMedia;

    // Chrome 1+
    var isChrome = !!window.chrome && !!window.chrome.webstore;

    return browser.prototype._cachedResult =
        isOpera ? 'Opera' :
        isFirefox ? 'Firefox' :
        isSafari ? 'Safari' :
        isChrome ? 'Chrome' :
        isIE ? 'IE' :
        isEdge ? 'Edge' :
        "Don't know";
};

var digitalData={
    "pageInfo":{
        "page_name":"",
        "page_title": "",
        "page_type": "",
        "site_language":"",
        "site_country":"",
        "device":"",
        "company_division":"",
        "clean_ur":"",
        "url":"",
        "referring_url":"",
        "browser_type":"",
        "source_id":""


    },

               "eventInfo": {
               "event_name":"",
               "event_type":"click",
               "item_clicked":"",
               "item_subcategory":"",
               "location_name":""
               },
               "videoInfo" :{
               "video_id":"",
               "action":"",
               "time_watched":""
               },
               "errorInfo":{
               "error_name":"",
               "error_detail":"",
               "error_field":""
               },
               "formInfo":{
               "customer_email_hash":"",
               "optin_status":"",
               "subscription_ids":"",
               "terms_and_conditions":"",
               "gender":"",
               "postal_code":"",
               "relationship":"",
               "rich_text":""
               },
               "abandonmentInfo":{
               "type":"",
               "last_accessed_field":""
               }

}


document.addEventListener('DOMContentLoaded', function () {
     setTimeout(function(){
         onPageLoad(jsonAnalytics);}
         ,2000);
}, false);

function onPageLoad(jsonAnalytics){
var sourceid = null;
    if (document.getElementById("formSourceId") != null) {
                                sourceid = document.getElementById("formSourceId").value;
    } else {
        sourceid = jsonAnalytics["source_id"];
    }
digitalData.pageInfo={
                             page_name:jsonAnalytics["page_name"],
                             page_title:jsonAnalytics["page_title"],
                             page_type:jsonAnalytics["page_type"],
                             site_language:jsonAnalytics["meta.site_language"],
                             site_country:jsonAnalytics["countryCode"],
                             device:device(),
                             company_division:jsonAnalytics["company_division"],
                             clean_url:jsonAnalytics["clean_url"],
                             url:window.location.href,
                             referring_url:document.referrer,
                             browser_type:browser(),
                            source_id:sourceid
                         };
_satellite.track("pageLoad");
}

function sendToAnalytics(params, evtName){
  digitalData.eventInfo= params;
  _satellite.track(evtName+"Clicks");
}
var checkFormHasData = function(res){
digitalData.eventInfo={
     event_name: "Form-Abandonment",
     event_type: "click",
     item_clicked: "crm-form",

   };
   digitalData.abandonmentInfo=res;

  _satellite.track("formSubmit");
}
var apiSuccessForm = function(res){
    clearPreviousData();
digitalData.eventInfo={
     event_name: "Form-Submit",
     event_type: "click",
     item_clicked: "crm-form",
};
  digitalData.formInfo = res;
  _satellite.track("formSubmit");
}
var apiFormError = function(res,email){
    clearPreviousData();
digitalData.eventInfo={
       event_name: "Form-Failure",
       event_type: "click",
       item_clicked: "crm-form",
   }
  digitalData.formInfo ={
       email: email,
   };
   digitalData.Post_submit_errorInfo = res;

  _satellite.track("formSubmit");
}
var clientFormValidationError = function(res,email){
    clearPreviousData();
digitalData.eventInfo={
       event_name: "Form-Failure",
       event_type: "click",
       item_clicked: "crm-form",
   };
   digitalData.formInfo ={
       email: email,
   };
  digitalData.pre_submit_errorInfo = res

  _satellite.track("formSubmit");
}

function clearPreviousData(){
    if(digitalData.pre_submit_errorInfo != undefined){
        delete digitalData.pre_submit_errorInfo;
    }

    if (digitalData.Post_submit_errorInfo != undefined) {
        delete digitalData.Post_submit_errorInfo;
    }
}

var populateRetailersData = function(retailerName,buttonClickedInsidePopUp){
digitalData.eventInfo={
            event_name: "Retailer section",
            event_type: "click",
            item_category: retailerName, 
            item_subcategory: buttonClickedInsidePopUp,
   };
  _satellite.track("event");
}

// GEM Check form has data 
var checkGemFormHasData = function(res){
digitalData.eventInfo={
     event_name: "Form-Abandonment",
     event_type: "click",
     item_clicked: "email-subscription-form",

   };
   digitalData.abandonmentInfo=res;

  _satellite.track("formSubmit");
}
//GEM Api success form
var apiSuccessGemForm = function(res){
    clearPreviousData();
digitalData.eventInfo={
     event_name: "Form-Submit",
     event_type: "click",
     item_clicked: "email-subscription-form",
};
  digitalData.formInfo = res;
  _satellite.track("formSubmit");
}
// GEM Form Error
var apiGemFormError = function(res,email){
    clearPreviousData();
digitalData.eventInfo={
       event_name: "Form-Failure",
       event_type: "click",
       item_clicked: "email-subscription-form",
   }
  digitalData.formInfo ={
       email: email,
   };
   digitalData.Post_submit_errorInfo = res;

  _satellite.track("formSubmit");
}
//GEM Client form validation error
var clientGemFormValidationError = function(res,email){
    clearPreviousData();
digitalData.eventInfo={
       event_name: "Form-Failure",
       event_type: "click",
       item_clicked: "email-subscription-form",
   };
   digitalData.formInfo ={
       email: email,
   };
  digitalData.pre_submit_errorInfo = res

  _satellite.track("formSubmit");
}

var populateCountryDropDownData = function(countryName){
digitalData.eventInfo={
            event_name: "Country Dropdown Section",
            event_type: "click",
            item_clicked: countryName, 
            item_subcategory: "",
   };
  _satellite.track("event");
}