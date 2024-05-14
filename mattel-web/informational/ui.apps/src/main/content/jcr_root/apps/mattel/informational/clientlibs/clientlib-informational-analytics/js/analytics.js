var path = $('#currentPagePathForAnalytics').val();
var businessSite = $(document).find("#businessSiteName").val();
var siteCountry = $(document).find("#siteCountry").val();
var page_name = $('#pageNamesForAnalytics').val();
var page_id_temp = page_name.split(':');
var page_type = $('#pageTypesForAnalytics').val();
var language = $("html").attr("lang");
var pageTitle = $(document).find("title").text();
var pageTemplate = $('meta[name=template]').attr("content");
var jsonAnalytics = {};
var analyticData = $(".data").attr("data-analytics-property");
var cleanUrl = window.location.pathname.replace(".html", "");
var templateType = $('#templateType').val();
var pageLoadIdentifier = 'pageLoad';
var ref_url = '';
var device = '';
var breadcrumbValues = function() {
    var findBreadCrumb = $(".breadcrumb").children();
    var tempValues = '';
    if (findBreadCrumb.length !== 0) {
        findBreadCrumb.each(function(index) {
            tempValues = tempValues + $(this).text().trim();
            if (index < findBreadCrumb.length - 1) {
                tempValues = tempValues + '|';
            }
        })
    }
    return tempValues.toLowerCase();
};
var breadcrumbSplit = breadcrumbValues().split('|');
if (analyticData != null) {
    jsonAnalytics = JSON.parse(analyticData);
}
jsonAnalytics["page_name"] = page_name;
jsonAnalytics["page_type"] = page_type;
jsonAnalytics["meta.site_language"] = language;
jsonAnalytics["page_title"] = pageTitle;
jsonAnalytics["clean_url"] = cleanUrl;
jsonAnalytics["page_template_name"] = pageTemplate;


var browser = function() {
    // Return cached result if avalible, else get result then cache it.
    if (browser.prototype._cachedResult)
        return browser.prototype._cachedResult;

    // Opera 8.0+
    var isOpera = (!!window.opr && !!opr.addons) || !!window.opera ||
        navigator.userAgent.includes(' OPR/');

    // Firefox 1.0+
    var isFirefox = typeof InstallTrigger !== 'undefined';

    // Safari 3.0+ "[object HTMLElementConstructor]"
    var isSafari = /constructor/i.test(window.HTMLElement) || (function(p) {
        return p.toString() === "[object SafariRemoteNotification]";
    })(!window['safari'] || (typeof safari !== 'undefined' && safari.pushNotification));

    // Internet Explorer 6-11
    var isIE = !!document.documentMode;

    // Edge 20+
    var isEdge = !isIE && !!window.StyleMedia;

    // Chrome 1+
    var isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);

    return browser.prototype._cachedResult = isOpera ? 'Opera' :
        isFirefox ? 'Firefox' : isSafari ? 'Safari' : isChrome ? 'Chrome' :
        isIE ? 'IE' : isEdge ? 'Edge' : "Don't know";
};

var digitalData = {
    "pageInfo": {
        "Business_site": "",
        "page_name": "",
        "page_title": "",
        "page_template_name": "",
        "site_language": "",
        "site_country": "",
        "platform": "",
        "page_type": "",
        "device": "",
        "company_division": "",
        "clean_ur": "",
        "referring_url": "",
        "browser_type": "",
        "requested_url": "",
        "clean_url": "",
        'product_pricing_indicator': "",
        'site currency': "",
        'site_type': '',
        'store_id': '',
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
        "item_subheader": "",
        "item_category1": "",
        "item_category2": "",
        "item_category3": ""
    }

}

document.addEventListener('DOMContentLoaded', function() {
    setTimeout(function() {
        onPageLoad(jsonAnalytics);
    }, 2000);
}, false);

$(document).on("click", ".at-icon-wrapper", function() {
	digitalData.eventInfo = {
		event_name: "share-click",
		event_type: "click",
		item_clicked: $(this).find('title').text(),
		item_subcategory: "",
		location_name: "share"
	};
	_satellite.track("socialshare");
});

function onPageLoad(jsonAnalytics) {
    var referrerUrl = document.referrer;
    var currentHost = window.location.host;
    if (referrerUrl.includes(currentHost)) {
        ref_url = '';
    } else {
        ref_url = referrerUrl;
    }

    if (navigator.userAgent.match(/Tablet|iPad/i)) {
        device = "Tablet";
    } else if (navigator.userAgent
        .match(/Mobile|Windows Phone|Lumia|Android|webOS|iPhone|iPod|Blackberry|PlayBook|BB10|Opera Mini|\bCrMo\/|Opera Mobi/i)) {
        device = "Mobile";
    }


    digitalData.pageInfo = {
        Business_site: businessSite ? businessSite.split("-").join(" ") : 'no business-site',
        page_name: jsonAnalytics["page_name"],
        page_title: jsonAnalytics["page_title"],
        page_type: jsonAnalytics["page_type"],
        page_template_name: jsonAnalytics["page_template_name"],
        platform: device || 'desktop',
        site_language: jsonAnalytics["meta.site_language"],
        site_country: siteCountry,
        device: device || 'desktop',
        clean_url: jsonAnalytics["clean_url"],
        url: window.location.href,
        referring_url: ref_url,
        site_section: $('#siteSection').val() || 'no site-section',
        site_subsection:$('#siteSubSection').val().split("-").join(" ") || 'no site-sub-section',
        browser_type: browser(),


    };
    if (templateType.includes("corporate-homepage") || templateType.includes("corporate-contentpage") || templateType.includes("corporate-news-article-page")) {
        pageLoadIdentifier = 'pageLoad';
        digitalData.pageInfo['customer_country'] = 'US'
            digitalData.pageInfo['page_name'] = 'CORP:' + digitalData.pageInfo['site_country'].toUpperCase() + ':'
            + digitalData.pageInfo['site_section'].split("-").join(" ") + getSiteSubSectoion()
            digitalData.pageInfo['page_id'] = jsonAnalytics["page_name"]
            digitalData.pageInfo['page_type'] = getPageType()
            digitalData.pageInfo['page_template_id'] = jsonAnalytics["page_template_name"]
    }
    if(window.location.href.includes('search-results')) {
        var docResultCount = $(".search-result .dynamicImg_doc").length;
        var webResultCount = $(".search-result .dynamicImg_web").length
        var searchType = "";
        if(docResultCount > 0 && webResultCount > 0){
            searchType = "news,web"
        }
        else if(docResultCount > 0 && webResultCount <= 0){
            searchType = "news"
        }
        else if(docResultCount <= 0 && webResultCount > 0){
            searchType = "web"
        }
        pageLoadIdentifier = 'pageLoadSearch';
        var urlParams = new URLSearchParams(window.location.search);
        var searchKey = urlParams.has('searchTerm') ? urlParams.get('searchTerm') : '';
        var searchStatus=$(".total-search").text().trim() ? "successful" : "unsuccessful";
        var searchResultCount=searchStatus == "successful" ?  $(".individual-search").length : "0";
        digitalData.pageInfo['search_keyword'] = searchKey
        digitalData.pageInfo['search_results_count'] = searchResultCount
        digitalData.pageInfo['search_status'] =searchStatus
        digitalData.pageInfo['search_category'] = window._satellite.cookie.get("searchType") ? window._satellite.cookie.get("searchType") : ''
        digitalData.pageInfo['search_type'] = searchType
    }
    _satellite.track(pageLoadIdentifier);
    $("meta[name='platform']").attr('content', digitalData.pageInfo['platform']);
}

function getPageType() {
    var pageType='';
    if(templateType.includes("corporate-homepage")) {
        pageType='Home';
    }
    else if (templateType.includes("corporate-contentpage") || templateType.includes("corporate-news-article-page")){
        pageType='Content';
    }
    else {
        pageType='no-value';
    }
    return pageType;
}

function getSiteSubSectoion() {
    var subSection=$('#siteSubSection').val();
    if (subSection){
    return ':'+subSection.split("-").join(" ")
    }
    else {
        return '';
    }
}