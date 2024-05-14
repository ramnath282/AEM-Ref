var path = $('#currentPagePathForAnalytics').val() || '';
var page = path.split("/").pop();
var businessSite =$(document).find("#businessSiteName").val();
var siteCountry =$(document).find("#siteCountry").val();
var page_name = $('#pageNamesForAnalytics').val();
var page_type = $('#pageTypesForAnalytics').val();
var subbrand_name = $('#subBrandNameForAnalytics').val();
var language = $("html").attr("lang");
var pageTitle = $(document).find("title").text();
var pageTemplate = $('meta[name=template]').attr("content");
var jsonAnalytics = {};
var analyticData = $(".data").attr("data-analytics-property");
var cleanUrl = window.location.pathname.replace(".html", "");
if (analyticData != null) {
    jsonAnalytics = JSON.parse(analyticData);
}
jsonAnalytics["subbrand_name"] = subbrand_name;
jsonAnalytics["page_name"] = page_name;
jsonAnalytics["page_type"] = page_type;
jsonAnalytics["meta.site_language"] = language;
jsonAnalytics["page_title"] = pageTitle;
jsonAnalytics["clean_url"] = cleanUrl;
jsonAnalytics["page_template_name"] = pageTemplate;

var device = function() {

    if (navigator.userAgent.match(/Tablet|iPad/i)) {
        return "Tablet";
    } else if (navigator.userAgent
        .match(/Mobile|Windows Phone|Lumia|Android|webOS|iPhone|iPod|Blackberry|PlayBook|BB10|Opera Mini|\bCrMo\/|Opera Mobi/i)) {
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
    var isSafari = /constructor/i.test(window.HTMLElement) || (function(p) {
        return p.toString() === "[object SafariRemoteNotification]";
    })(!window['safari'] || safari.pushNotification);

    // Internet Explorer 6-11
    var isIE = !!document.documentMode;

    // Edge 20+
    var isEdge = !isIE && !!window.StyleMedia;

    // Chrome 1+
    var isChrome = !!window.chrome && !!window.chrome.webstore;

    // Blink engine detection
    var isBlink = (isChrome || isOpera) && !!window.CSS;

    return browser.prototype._cachedResult = isOpera ? 'Opera' : isFirefox ? 'Firefox' : isSafari ? 'Safari' : isChrome ? 'Chrome' : isIE ? 'IE' : isEdge ? 'Edge' : "Don't know";
};

var digitalData = {
    "pageInfo": {
        "Business_site": "",
        "page_name": "",
        "page_title": "",
        "subbrand_name": "",
        "page_template_name": "",
        "site_language": "",
        "site_country": "",
        "platform": "",
        "page_type": "",
        "device": "",
        "article_category":"",
        "company_division": "",
        "clean_ur": "",
        "referring_url": "",
        "browser_type": "",
        "requested_url": "",
        "clean_url": ""
    },

    "eventInfo": {
        "event_name": "",
        "event_type": "",
        "item_clicked": "",
        "character_name": "",
        "character_category": "",
        "characterInfo": "",
        "page_attribution": "",
        "arrow_type": "",
        "present_at": "",
        "footer_item_clicked": "",
        "site_country": "",
        "item_header": "",
        "item_subheader": ""
    }

}

document.addEventListener('DOMContentLoaded', function() {
    setTimeout(function() {
        onPageLoad();
    }, 2000);
}, false);

function onPageLoad() {
    digitalData.pageInfo = {
        Business_site: businessSite || 'no business-site',
        page_name: jsonAnalytics["page_name"],
        page_title: jsonAnalytics["page_title"],
        subbrand_name: jsonAnalytics["subbrand_name"] || 'no subbrand-name',
        page_type: jsonAnalytics["page_type"],
        page_template_name: jsonAnalytics["page_template_name"],
        platform: device(),
        site_language: jsonAnalytics["meta.site_language"],
        site_country: siteCountry,
        device: device(),
        article_category:jsonAnalytics["page_type"],
        clean_url: jsonAnalytics["clean_url"],
        url: window.location.href,
        referring_url: document.referrer,
        site_section : $('#siteSelection').val() || 'no site-section',
        company_division:$('#rescueParentName').val() || 'no company-division',
        browser_type: browser()

    };
    if (pageTemplate == 'characterpage-template' && $('.char-details-description h1').text()) {

        digitalData.characterInfo = {
        	character_name:$('#tracCharTitle').val(),
            character_category: localStorage.getItem("categoryName"),
            page_attribution: document.referrer
        };
    }
    if (pageTemplate == 'videopage-template') {
        digitalData.videoInfo = {
            category_url: window.location.href,
            clean_url: window.location.pathname
        };
    }
    if (pageTemplate == 'productdetailpage-template') {
        digitalData.productInfo = {
            product_name: $('#alwaysEnglishPC').val(),
            product_category: $('#categoryID').val(),
            product_sku: $('#productSkuid').val(),
            product_age: $('#productAges').val(),
            product_id: $('#productId').val(),
            product_image_position: "Position1", 
            page_attribution: document.referrer
        };
    }

    if (pageTemplate == 'gamepage-template' && $('.title-block h2').text()) {

        digitalData.gameInfo = {
            game_name: $('.title-block h2').text(),
            game_category: localStorage.getItem("categoryName"),
            page_attribution: document.referrer
        };
    }
    _satellite.track("pageLoad");
    localStorage.removeItem('categoryName');

}

function sendToAnalytics(params, evtName, isVideoCategory) {
    digitalData.eventInfo = params;
    delete digitalData.videoInfo;
    if (isVideoCategory) {
        digitalData.videoInfo = {
            category_url: window.location.href,
            clean_url: window.location.pathname
        }
    }
    _satellite.track("event");
}



function camelize(str) {
    return str.replace(/(?:^\w|[A-Z]|\b\w|\s+)/g, function(match, index) {
        if (+match === 0) return ""; // or if (/\s+/.test(match)) for white spaces
        return index == 0 ? match.toLowerCase() : match.toUpperCase();
    });
}

var apiSuccessForm = function(res){
    clearPreviousData();
digitalData.eventInfo={
     event_name: "Email Signup",
     event_type: "click",
     item_clicked: $('#form-title').val(),
     item_subcategory: $('#form-signup').val()
};
  digitalData.formInfo = res;
  _satellite.track("formSubmit");
     _satellite.track("thankYou");

}
var checkFormHasData = function(res){
digitalData.eventInfo={
     event_name: "Form-Abandonment",
     event_type: "click",
     item_clicked: $('#form-title').val(),

   };
   digitalData.abandonmentInfo=res;

  _satellite.track("formSubmit");
}
var apiFormError = function(res,email){
    clearPreviousData();
digitalData.eventInfo={
       event_name: "Email Signup failure",
       event_type: "click",
       item_clicked: $('#form-title').val(),
       item_subcategory: $('#form-signup').val()
   }
  digitalData.formInfo ={
       customer_email_hash: email,
   };
   digitalData.Post_submit_errorInfo = res;

  _satellite.track("formSubmit");
}
var clientFormValidationError = function(res,email){
    clearPreviousData();
digitalData.eventInfo={
       event_name: "Email Signup Failure",
       event_type: "click",
       item_clicked: $('#form-title').val(),
       item_subcategory: $('#form-signup').val()
   };
   digitalData.formInfo ={
       customer_email_hash: email,
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