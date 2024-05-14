var noValue = 'no-value'
var path = $('#currentPagePathForAnalytics').val();
var businessSite = $(document).find("#businessSiteName").val();
var siteCountry = $(document).find("#siteCountry").val();
var page_name = $('#pageNamesForAnalytics').val();
var page_id_temp = page_name.split(':');
var page_type = $('#pageTypesForAnalytics').val();
var subbrand_name = $('#subBrandNameForAnalytics').val();
var language = $("html").attr("lang");
var pageTitle = $(document).find("title").text();
var pageTemplate = $('meta[name=template]').attr("content");
var jsonAnalytics = {};
var analyticData = $(".data").attr("data-analytics-property");
var cleanUrl = window.location.pathname.replace(".html", "");
var templateType = $('#templateType').val();
var pageLoadIdentifier = 'pageLoad';
var previous_value = '';
var bvLoaded = false;
var bverror = false;
var ref_url = '';
var device = '';
var formInitCount = 0;
var lastAccessField = '';
var breadcrumbValues = function() {
    var findBreadCrumb = $(".breadcrumb").children();
    var tempValues = '';
    if (findBreadCrumb.length != 0) {
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
jsonAnalytics["subbrand_name"] = subbrand_name;
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
var age = function() {
    var checkAge = $('.age-badges .badge-txt');
    if (checkAge.length != 0) {
        return checkAge[0].innerText.split(':')[1].trim().toLowerCase();
    }
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
        Business_site: businessSite || 'no business-site',
        page_name: jsonAnalytics["page_name"],
        page_title: jsonAnalytics["page_title"],
        subbrand_name: jsonAnalytics["subbrand_name"] || 'no subbrand-name',
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
        company_division: $('#rescueParentName').val() ||
            'no company-division',
        browser_type: browser(),
        product_pricing_indicator: 'USD',
        site_type: 'Shop',
        store_id: '',
        site_currency: 'USD',
        site_region: 'us',

    };
     if (templateType.indexOf("article-details-page") != -1) {        
        var searchCategory = window._satellite.cookie.get("searchType") ? window._satellite.cookie.get("searchType") : ''
                                var searchKeyword = window._satellite.cookie.get("searchTerm") ? window._satellite.cookie.get("searchTerm") : '';         
           var searchStatus = '';
            var searchType = '';
         if(searchCategory != ''){
             searchStatus = 'successful';
             searchType = 'articles';
        }
         pageLoadIdentifier = 'articleDetailpageLoad';
                 var categoryName = $('#primaryTag').val();
                 var articleName = jsonAnalytics["page_title"].substring(0, jsonAnalytics["page_title"].indexOf("|")).trim();
                 digitalData.pageInfo['customer_email_hash'] = 'fw42hwfjcvy231uefgogd82'
         digitalData.pageInfo['page_type'] = jsonAnalytics["page_type"];
         digitalData.pageInfo['logged_in_status'] = 'anonymous'
         digitalData.pageInfo['store_id'] = ''
         digitalData.pageInfo['article_category'] = categoryName;
         digitalData.pageInfo['article_name'] = articleName;
         window._satellite.cookie.set("acat", categoryName, 0);
         digitalData.pageInfo['search_keyword'] = searchKeyword
         digitalData.pageInfo['search_status'] = searchStatus
         digitalData.pageInfo['search_category'] = searchCategory
         digitalData.pageInfo['search_type'] = searchType
    }
    if (templateType.indexOf("category-landing-page") != -1) {
   	 pageLoadIdentifier = 'articleCatergorypageLoad';
   	 var categoryName = $('#primaryTag').val();
   	 var articleName = jsonAnalytics["page_title"].substring(0, jsonAnalytics["page_title"].indexOf("|")).trim();
   	digitalData.pageInfo['page_type'] = jsonAnalytics["page_type"];
   	 digitalData.pageInfo['customer_email_hash'] = 'fw42hwfjcvy231uefgogd82'
        digitalData.pageInfo['logged_in_status'] = 'anonymous'
        digitalData.pageInfo['store_id'] = ''
        digitalData.pageInfo['article_category'] = categoryName;
        digitalData.pageInfo['article_name'] = articleName;
        window._satellite.cookie.set("acat", categoryName, 0);

   }
    if (templateType.indexOf("landing-page") != -1) {
   	 pageLoadIdentifier = 'articlepageLoad';
   	digitalData.pageInfo['page_type'] = jsonAnalytics["page_type"];
   	  digitalData.pageInfo['customer_email_hash'] = 'fw42hwfjcvy231uefgogd82'
        digitalData.pageInfo['logged_in_status'] = 'anonymous'
        digitalData.pageInfo['store_id'] = ''

   }
    if (templateType.indexOf("fp-productdetail-page") != -1) {
        pageLoadIdentifier = 'pageLoadPDP';
        var searchCategory = window._satellite.cookie.get("searchType") ? window._satellite.cookie.get("searchType") : '';
        var searchCategoryForPDP = '';
        var internalSearchTerm = window._satellite.cookie.get("searchTerm") ? window._satellite.cookie.get("searchTerm") : '';
        var searchType = '';
        var searchStatus = '';
        var searchKeyword = '';
        if (searchCategory != '' && (searchCategory != 'related products' || searchCategory != 'popular products')) {
            searchCategoryForPDP = searchCategory;
            searchType = 'products';
            searchStatus = 'successful';
        }
        webCollageAnalytics();
        digitalData.pageInfo['customer_country'] = 'US'
            digitalData.pageInfo['customer_email_hash'] = ''
            digitalData.pageInfo['category_id'] = ''
            digitalData.pageInfo['grs_1'] = $("#grs").val().split('|')[0] || ''
            digitalData.pageInfo['grs_2'] = $("#grs").val().split('|')[1] || ''
            digitalData.pageInfo['grs_3'] = $("#grs").val().split('|')[2] || ''
            digitalData.pageInfo['grs_4'] = $("#grs").val().split('|')[3] || ''
            digitalData.pageInfo['grs_5'] = $("#grs").val().split('|')[4] || ''
            digitalData.pageInfo['grs_6'] = $("#grs").val().split('|')[5] || ''
            digitalData.pageInfo['grs_7'] = $("#grs").val().split('|')[6] || ''
            digitalData.pageInfo['grs_concat'] = $("#grs").val()
            digitalData.pageInfo['logged_in_status'] = 'anonymous'
            digitalData.pageInfo['optin_status'] = 'false'
            digitalData.pageInfo['product_context'] == $("#category").val() || ''
            digitalData.pageInfo['product_category'] = $("#category").val().split(/[|]+/).join(',') || ''
            digitalData.pageInfo['product_average_rating'] = $("#averageRating").val() || ''
            digitalData.pageInfo['product_brand'] = $("#productBrand").val() || ''
            digitalData.pageInfo['product_bundle'] = ''
            digitalData.pageInfo['product_discount'] = '0.00'
            digitalData.pageInfo['product_reviews_count'] = $('.productNav-sticky .header-section .star-rating-container span.rating-count').length > 0 ? $('.productNav-sticky .header-section .star-rating-container span.rating-count')[0].innerText.replace(/\D/g, '') : ''
            digitalData.pageInfo['product_subcategory'] = $("#subCategory").val().split(/[|]+/).join(',') || ''
            digitalData.pageInfo['product_unit_price'] = $('.fpProductinfo-module > .product-info-block > .product-price').text().trim() || ''
            digitalData.pageInfo['product_list_price'] = $('.fpProductinfo-module > .product-info-block > .product-price').text().trim() || ''
            digitalData.pageInfo['product_type'] = ''
            digitalData.pageInfo['registration_id'] = ''
            digitalData.pageInfo['visitor_status'] = ''
            digitalData.pageInfo['bread_crumb'] = breadcrumbValues()
            digitalData.pageInfo['breadcrumb_level1'] = breadcrumbSplit[0] || breadcrumbValues()
            digitalData.pageInfo['breadcrumb_level2'] = breadcrumbSplit[1] || noValue
            digitalData.pageInfo['breadcrumb_level3'] = breadcrumbSplit[2] || noValue
            digitalData.pageInfo['product_age_group'] = age()
            digitalData.pageInfo['product_id'] = $('#productSKUId').val() || ''
            digitalData.pageInfo['product_name'] = $('.product-info-block h1')[0] ? $('.product-info-block h1')[0].innerText.toLowerCase() : ''
            digitalData.pageInfo['product_sku'] = $('#s7sku').val() || ''
            digitalData.pageInfo['product_line'] = ''
            digitalData.pageInfo['page_type'] = 'Product Index'
            digitalData.pageInfo['product_badge'] = $('.product-badges')[0].innerText || ''
            digitalData.pageInfo['product_url'] = $('#productURL').val() || ''
            digitalData.pageInfo['product_image_url'] = $('.product-image-block img')[0] ? $('.product-image-block img')[0].src : ''
            digitalData.pageInfo['page_name'] = getPagename() + ':' + digitalData.pageInfo['site_country'].toUpperCase() + getPdpCleanurl()
            digitalData.pageInfo['page_template_id'] = jsonAnalytics["page_template_name"]
            digitalData.pageInfo['page_id'] = page_id_temp[0] + ':' + page_id_temp[1] + ':ProductDisplay'
            digitalData.pageInfo['site_section'] = breadcrumbSplit[1] || ''
            digitalData.pageInfo['site_subsection'] = breadcrumbSplit[2] || ''
            digitalData.pageInfo['product_finding_method'] = window._satellite.cookie.get("pfm") ? window._satellite.cookie.get("pfm") : ''
            digitalData.pageInfo['product_impression_name'] = $('.product-info-block h1')[0] ? $('.product-info-block h1')[0].innerText.toLowerCase() : ''
            digitalData.pageInfo['product_impression_sku'] = $('#s7sku').val() || ''
            digitalData.pageInfo['cross_sell_product'] = window._satellite.cookie.get("crossSell") ? window._satellite.cookie.get("crossSell") : ''
            digitalData.pageInfo['product_impression_price'] = $('.fpProductinfo-module > .product-info-block > .product-price').text().trim() || ''
            digitalData.pageInfo['search_keyword'] = searchKeyword
            digitalData.pageInfo['search_status'] = searchStatus
            digitalData.pageInfo['search_category'] = searchCategoryForPDP
            digitalData.pageInfo['search_type'] = searchType
            digitalData.pageInfo['internal_search_term'] = internalSearchTerm || ''
        window._satellite.cookie.remove("searchType");
        window._satellite.cookie.remove("searchTerm");
    } else if (templateType.includes("fp-productlisting-page") && !(window.location.href.includes('search-results'))) {
        window._satellite.cookie.remove("crossSell");
        pageLoadIdentifier = 'pageLoadPLP';
        getImpressionVariable();
        var cat = "";
        if (breadcrumbSplit.length > 2) {
            cat = breadcrumbValues().split('|')[breadcrumbValues().split('|').length - 2] || ''
                digitalData.pageInfo['product_subcategory'] = breadcrumbValues().split('|')[breadcrumbValues().split('|').length - 1] || ''
        } else {
            cat = breadcrumbValues().split('|')[breadcrumbValues().split('|').length - 1] || ''
                digitalData.pageInfo['product_subcategory'] = ''
        }
        digitalData.pageInfo['product_category'] = window._satellite.cookie.get("pcat") ? window._satellite.cookie.get("pcat") : cat
            digitalData.pageInfo['bread_crumb'] = breadcrumbValues()
            digitalData.pageInfo['breadcrumb_level1'] = breadcrumbSplit[0] || breadcrumbValues()
            digitalData.pageInfo['breadcrumb_level2'] = breadcrumbSplit[1] || noValue
            digitalData.pageInfo['breadcrumb_level3'] = breadcrumbSplit[2] || noValue
            digitalData.pageInfo['customer_country'] = 'US'
            digitalData.pageInfo['customer_email_hash'] = ''
            digitalData.pageInfo['category_id'] = ''
            digitalData.pageInfo['logged_in_status'] = 'anonymous'
            digitalData.pageInfo['optin_status'] = 'false'
            digitalData.pageInfo['page_name'] = getPagename() + ':' + digitalData.pageInfo['site_country'].toUpperCase() + ':Shop' + getCleanurl()
            digitalData.pageInfo['page_id'] = jsonAnalytics["page_name"]
            digitalData.pageInfo['page_type'] = 'Category Index'
            digitalData.pageInfo['page_template_id'] = jsonAnalytics["page_template_name"]
            digitalData.pageInfo['registration_id'] = ''
            digitalData.pageInfo['visitor_status'] = ''
            digitalData.pageInfo['site_section'] = breadcrumbSplit[0] || ''
            digitalData.pageInfo['site_subsection'] = breadcrumbSplit[1] || ''
            digitalData.pageInfo['total_products_count'] = $('.slide-header .total-itemcnt-inner').text() || ''
        window._satellite.cookie.remove("pcat");
    } else if (templateType.includes("fp-contentpage") || templateType.includes("fp-homepage-page")) {
        window._satellite.cookie.remove("crossSell");
        pageLoadIdentifier = 'pageLoad';
        digitalData.pageInfo['customer_country'] = 'US'
            digitalData.pageInfo['customer_email_hash'] = ''
            digitalData.pageInfo['category_id'] = ''
            digitalData.pageInfo['logged_in_status'] = 'anonymous'
            digitalData.pageInfo['optin_status'] = 'false'
            digitalData.pageInfo['page_name'] = getPagename() + ':' + digitalData.pageInfo['site_country'].toUpperCase() + ':Shop:' + digitalData.pageInfo['site_section']
            digitalData.pageInfo['page_id'] = jsonAnalytics["page_name"]
            digitalData.pageInfo['page_type'] = 'Home'
            digitalData.pageInfo['page_template_id'] = jsonAnalytics["page_template_name"]
            digitalData.pageInfo['registration_id'] = ''
            digitalData.pageInfo['visitor_status'] = ''

        if (templateType.includes("fp-homepage-page")) {
            digitalData.pageInfo['page_type'] = 'Home'
                digitalData.pageInfo['site_section'] = 'home'
                digitalData.pageInfo['site_subsection'] = 'home'
        } else {
            let pageName = jsonAnalytics["page_name"];
            let siteSection = pageName.substring(pageName.lastIndexOf(':') + 1, pageName.length);
            digitalData.pageInfo['page_type'] = 'Content'
                digitalData.pageInfo['site_section'] = siteSection
                digitalData.pageInfo['site_subsection'] = ''
        }
    } else if (templateType.includes("fp-productlisting-page") && window.location.href.includes('search-results')) {
    	digitalData.pageInfo['article_category'] = window._satellite.cookie.get("acat") ? window._satellite.cookie.get("acat") : 'no-value'
        window._satellite.cookie.remove("crossSell");
        pageLoadIdentifier = 'pageLoadSearch';
        var key = $('.bc-title.searchResultTitle span').text().trim().split("“")[1].split("”")[0];
        digitalData.pageInfo['page_type'] = 'Product Search Results'
            digitalData.pageInfo['page_name'] = getPagename() + ':' + digitalData.pageInfo['site_country'].toUpperCase() + ':' + digitalData.pageInfo['clean_url'].substring(digitalData.pageInfo['clean_url'].lastIndexOf("/") + 1)
            digitalData.pageInfo['search_keyword'] = key
            digitalData.pageInfo['search_results_count'] = $(".search-result").hasClass("hide") ? 0 : $('.slide-header .total-itemcnt-inner').text().trim()
            digitalData.pageInfo['search_status'] = $(".search-result").hasClass("hide") ? 'unsuccessful' : 'successful'
            digitalData.pageInfo['search_category'] = window._satellite.cookie.get("searchType") ? window._satellite.cookie.get("searchType") : ''
            digitalData.pageInfo['search_type'] = 'products'
        window._satellite.cookie.remove("searchType");
        window._satellite.cookie.remove("searchTerm");
        window._satellite.cookie.remove("acat");
    } else if (templateType.includes("fp-cm-emailsubscription-page")) {
        digitalData.pageInfo['product_pricing_indicator'] = ''
            digitalData.pageInfo['page_type'] = 'Email Sign Up'
    } else if (templateType.includes("fp-contentpage") && $('#currentPagePathForAnalytics').val().includes("error")) {
        window._satellite.cookie.remove("crossSell");
        pageLoadIdentifier = 'pageLoad';
        var errorCode = '';
        var errorMsg = '';
        if ($('#currentPagePathForAnalytics').val().includes("500")) {
            errorCode = "500";
            errorMsg = "Internal Server Error";
        } else if ($('#currentPagePathForAnalytics').val().includes("404")) {
            errorCode = "404";
            errorMsg = "Page not available";
        } else {
            errorMsg = "no error";
        }
        digitalData.pageInfo['error_code'] = errorCode
            digitalData.pageInfo['error_message'] = errorMsg
    }
    _satellite.track(pageLoadIdentifier);
    $("meta[name='platform']").attr('content', digitalData.pageInfo['platform']);
    localStorage.removeItem('categoryName');
}

$(document).ready(function() {
    $(".selectpicker").on('shown.bs.select', function(e) {
        previous_value = $(this).find(":selected").text() || $('.filter-option-inner-inner').text();
    });
    $('.data-tracking-sign-up').click(function() {
        clearPreviousData();
        var valuesTrackApp = $(this).attr("data-tracking-sign-up-id").split("|");
        window._satellite.cookie.set("signupmethod", valuesTrackApp[4], 0);
        digitalData.eventInfo = {
            event_action: valuesTrackApp[0],
            event_action_type: "Click",
            form_type: "subscription",
            form_stage: "newsletter-sign-up cta click",
            sign_up_method: valuesTrackApp[4],
            location_name: valuesTrackApp[3] || '',
            component_type:"crm",
            component_name:"subscription",
            component_step:"start",
            container_type:valuesTrackApp[4],
        };
        _satellite.track("formStart");
    });

    $("#crm-form input").click(function() {
        if (formInitCount == 0 && !($(this).attr("id") == "crm-form-submit")) {
            digitalData.eventInfo = {
                event_action: "newsletter-signup-initiation",
                event_action_type: "click",
                form_type: "subscription",
                form_stage: "initiation",
                sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
                location_name: "newsletter-signup-form-field"
            };
            _satellite.track("formInitiation");
        }
        formInitCount++;
    });

    $(document).on("click", "input[type='text'],input[type='email'],input[type='radio'],input[type='checkbox'],select,textarea", function() {
        lastAccessField = $(this).attr("name");
    });

    $("body").on("click", "a", function() {
        var index;
        var position;
        var valuesTrackApp;
        if ($(this).hasClass("at-icon-wrapper")) {
            clearPreviousData();
            digitalData.eventInfo = {
                event_name: "share-click",
                event_type: "click",
                item_clicked: $(this).find('title').text(),
                item_subcategory: "",
                location_name: "share"
            };
            _satellite.track("event");
        }else if ($(this).hasClass("track-product-grid-image") && $(this).parents().hasClass("productGrid") && !window.location.href.includes("search-results")) {
            clearPreviousData();
            var price = $(this).parent().find(".product-info > .product-price > span").text() || '';
            var doller_index = price.lastIndexOf('$');
            var product_price = price.substring(doller_index + 1);
            index = $(this).parent().index() + 1;
            position = '1:' + index + ':' + $('.slide-header .total-itemcnt-inner').text();
            digitalData.eventInfo = {
                event_name: " product info ",
                event_type: "click",
                item_clicked: $(this).parent().find(".product-info > .grid-title-wrapper > a").text().trim() || '',
                item_subcategory: product_price.trim() || '',
                product_position: position,
                location_name: "product grid",
                product_sku: $(this).parent().attr("data-partno") || '',
                number_of_products_remaining: $('.slide-header .total-itemcnt-inner').text() > 0 ? $('.slide-header .total-itemcnt-inner').text() - index : 0
            };
            _satellite.track("PlpProductClick");
        }
       else if ($(this).hasClass("track-product-grid-image") && $(this).parents().hasClass("productGrid") && window.location.href.includes("search-results")) {
            clearPreviousData();
            index = $(this).parent().index() + 1;
            position = '1:' + index;
            digitalData.eventInfo = {
                event_name: "search click through",
                event_type: "click",
                item_clicked: $(this).parent().find(".product-info > .grid-title-wrapper > a").text().trim() || '',
                item_subcategory: $(this).attr("href"),
                location_name: position,
                product_sku: $(this).parent().attr("data-partno") || '',
                number_of_products_remaining: $('.slide-header .total-itemcnt-inner').text() > 0 ? $('.slide-header .total-itemcnt-inner').text() - index : 0
            };
            _satellite.track("event");
        }
        else if ($(this).hasClass("track-product-grid-image") && $(this).parents().hasClass("relatedProducts") && window.location.href.includes("search-results")) {
            clearPreviousData();

            var item_subcateg=$(this).parent().find(".product-info > .grid-title-wrapper > a").text().trim() || '';
            var article_categ=$(this).parent().find(".product-info > .grid-category-wrapper").text().trim() || '';
            var relatedTitle=$("#related-title").text().trim();
            relatedSearchClick(item_subcateg, article_categ, relatedTitle);
        }
        else if ($(this).hasClass("track-product-grid-title") &&  $(this).parents().hasClass("relatedProducts") && window.location.href.includes("search-results")) {
            clearPreviousData();
            var item_subcateg= $(this).text().trim() || ''
            var article_categ= $(this).closest('.product-info').find('.grid-category-wrapper').text().trim();
            var relatedTitle=$("#related-title").text().trim();
            relatedSearchClick(item_subcateg, article_categ, relatedTitle);
        }
        else if ($(this).hasClass("track-product-grid-desc") && $(this).parents().hasClass("relatedProducts") && window.location.href.includes("search-results")) {
            clearPreviousData();
            var item_subcateg= $(this).closest('.product-info').find('.grid-title-wrapper').text().trim();
            var article_categ= $(this).closest('.product-info').find('.grid-category-wrapper').text().trim();
            var relatedTitle=$("#related-title").text().trim();
            relatedSearchClick(item_subcateg, article_categ, relatedTitle);
        }
       else if ($(this).parent().hasClass("product-read-more") && window.location.href.includes("search-results")) {
            clearPreviousData();
            var item_subcateg= $(this).closest('.product-info').find('.grid-title-wrapper').text().trim();
            var article_categ= $(this).closest('.product-info').find('.grid-category-wrapper').text().trim();
            var relatedTitle=$("#related-title").text().trim();
            relatedSearchClick(item_subcateg, article_categ, relatedTitle);
        }
        else if ($(this).hasClass("track-view-all-article") && $(this).parents().hasClass("relatedProducts") && window.location.href.includes("search-results")) {
            clearPreviousData();
            digitalData.eventInfo = {
                event_name: "link click",
                event_type: "click",
                item_clicked: $("#related-title").text().trim(),
                item_subcategory: $(this).text().trim(),
                location_name: "link",
                container_type: "CTA",
                component_name: $(this).text().trim()
            };
            _satellite.track("event");
        }
        else if ($(this).hasClass("track-product-grid-title") && $(this).parents().hasClass("productGrid") && !window.location.href.includes("search-results")) {
             clearPreviousData();
             var priceOfProduct = $(this).parent().parent().find(".product-price > span").text() || '';
             var prod_doller_index = priceOfProduct.lastIndexOf('$');
             var product_final_price = priceOfProduct.substring(prod_doller_index + 1);
             index = $(this).parents().eq(2).index() + 1;
             position = '1:' + index + ':' + $('.slide-header .total-itemcnt-inner').text();
             digitalData.eventInfo = {
                 event_name: " product info ",
                 event_type: "click",
                 item_clicked: $(this).attr('data-title').trim() || '',
                 item_subcategory: product_final_price.trim() || '',
                 product_position: position.trim() || '',
                 location_name: "product grid",
                 product_sku: $(this).closest("li").attr("data-partno") || '',
                 number_of_products_remaining: $('.slide-header .total-itemcnt-inner').text() > 0 ? $('.slide-header .total-itemcnt-inner').text() - index : 0
             };
             _satellite.track("PlpProductClick");
         } else if ($(this).hasClass("track-product-grid-title") && $(this).parents().hasClass("productGrid") && window.location.href.includes("search-results")) {
            clearPreviousData();
            index = $(this).parent().index() + 1;
            position = '1:' + index;
            digitalData.eventInfo = {
                event_name: "search click through",
                event_type: "click",
                item_clicked: $(this).text().trim() || '',
                item_subcategory: $(this).attr("href"),
                location_name: position,
                product_sku: $(this).closest("li").attr("data-partno") || '',
                number_of_products_remaining: $('.slide-header .total-itemcnt-inner').text() > 0 ? $('.slide-header .total-itemcnt-inner').text() - index : 0
            };
            _satellite.track("event");
        } else if ($(this).attr("data-track-product-grid")) {
            clearPreviousData();
            digitalData.eventInfo = {
                event_name: "plp - sorting",
                event_type: "click",
                item_clicked: $(this).attr("data-track-product-grid"),
                item_subcategory: $(this).text().trim().toLowerCase(),
                location_name: "right nav"
            };
            _satellite.track("event");
        } else if ($(this).attr("data-tracking-accordion")) {
            clearPreviousData();
            valuesTrackApp = $(this).attr("data-tracking-accordion").split("|");
            var accordionPosition = ''
            if ($(this).hasClass('arrowUp')) {
                accordionPosition = 'collapse'
            } else if ($(this).hasClass('arrowDown')) {
                accordionPosition = 'expand'
            }
            digitalData.eventInfo = {
                event_name: valuesTrackApp[0] || 'Accordion Section',
                event_type: "Click",
                item_clicked: valuesTrackApp[1] || '',
                item_subcategory: accordionPosition || '',
                location_name: valuesTrackApp[1] || ''
            };
            _satellite.track("event");
        } else if ($(this).attr("data-track-recommended")) {
            clearPreviousData();
            var itemClicked = $(this).closest(".recommended-products").find(".headerPart span").text();
            var subCategory = $(this).attr("data-track-recommended").split("|")[0];
            var skuId = $(this).attr("data-track-recommended").split("|")[1];
            var name = $('.product-info-block h1')[0] ? $('.product-info-block h1')[0].innerText.toLowerCase() : '';
            var productInfo = skuId.trim().substring(0, 5) + "|" + $(this).attr("data-track-recommended").split("|")[2];
            window._satellite.cookie.set("crossSell", name, 0);
            window._satellite.cookie.set("pfm", "Recommendation", 0);
            digitalData.eventInfo = {
                event_name: 'recommended products click',
                event_type: 'click',
                item_clicked: itemClicked.trim() || '',
                item_subcategory: subCategory.trim() || '',
                product_info: productInfo.trim() || '',
                location_name: 'recommended products'
            };
            _satellite.track("recommendedProductsFP");
        } else if ($(this).attr('id') == "addChild") {
            clearPreviousData();
            digitalData.eventInfo = {
                event_action: "newsletter-signup-add-child",
                event_action_type: "click",
                form_type: "subscription",
                form_stage: "child addition",
                event_detail: "child addition",
                event_detail_sub: 'add a child',
                sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
                location_name: "newsletter-signup-add-child"
            };
            _satellite.track("formCTA");

        } else if ($(this).closest(".recall-component").length > 0 && !($(this).attr('data-tracking-id'))) {
            clearPreviousData();
            digitalData.eventInfo = {
                event_name: "recall-module",
                event_type: "click",
                item_clicked: "recall click",
                item_subcategory: $(this).text().trim() || '',
                location_name: "header section"
            };
            _satellite.track("event");
        } else if ($(this).attr("data-track-brandListing")) {
            clearPreviousData();
            var currentPagePath = window.location.pathname;
            var currentPageName = "";
            var currentLinkArray = currentPagePath.split("/");
            var currentLinkName = currentLinkArray[currentLinkArray.length - 1];
            if (currentPagePath.endsWith(".html")) {
                currentPageName = currentLinkName.substr(0, currentLinkName.length - 5).split("-").join(" ");
            } else {
                currentPageName = currentLinkName.split("-").join(" ");
            }
            var pageName;
            if (currentPageName == "brands") {
                pageName = $(this).attr("data-track-brandListing").split('|')[1] || ''
                var itemsub = "";
                var linkPath = $(this).attr('href');
                var linkArray = linkPath.split("/");
                var linkName = linkArray[linkArray.length - 1];
                if (linkPath.endsWith(".html")) {
                    itemsub = linkName.substr(0, linkName.length - 5).split("-").join(" ");
                } else {
                    itemsub = linkName.split("-").join(" ");
                }
                digitalData.eventInfo = {
                    event_name: "brand listing module click",
                    event_type: "click",
                    item_clicked: pageName.trim() || '',
                    item_subcategory: itemsub,
                    location_name: "brand listing module"
                };
            } else {
                pageName = $(this).attr("data-track-brandListing").split('|')[0] || '';
                var locationName = $(this).attr("data-track-brandListing").split('|')[1] || '';
                digitalData.eventInfo = {
                    event_name: "Text Image Section",
                    event_type: "click",
                    item_clicked: locationName.trim() || '',
                    item_subcategory: pageName.trim() || '',
                    location_name: locationName.trim() || ''
                };
            }
            _satellite.track("event");
        } else if ($(this).attr("data-analytics-tracking-id")) {
            clearPreviousData();
            valuesTrackApp = $(this).attr("data-analytics-tracking-id").split("|");
            var prodCategory;
            if (valuesTrackApp[2]) {
                prodCategory = valuesTrackApp[1] + ":" + valuesTrackApp[2] + ":" + valuesTrackApp[3];
            } else {
                prodCategory = valuesTrackApp[1] + ":" + valuesTrackApp[3];
            }
            window._satellite.cookie.set("pcat", prodCategory, 0);
        } else if ($(this).attr("data-track-replacement")) {
            clearPreviousData();
            valuesTrackApp = $(this).attr("data-track-replacement").split("|");
            digitalData.eventInfo = {
                event_name: valuesTrackApp[0],
                event_type: "Click",
                item_clicked: valuesTrackApp[1] || '',
                item_subcategory: valuesTrackApp[2] || '',
                location_name: "Product Features:Product support info",
                product_sku: $('#s7sku').val() || '',
                product_name: $('.product-info-block h1')[0] ? $('.product-info-block h1')[0].innerText.toLowerCase() : ''
            };
            _satellite.track("ReplacementParts");
        }
        else if ($(this).attr("data-track-targetrecommended")) {
             var trackingValues = $(this).attr("data-track-targetrecommended").split("|");
             var sku_id = trackingValues[1].trim();
             digitalData.eventInfo = {
                 event_name: "recommended products click",
                 event_type: "click",
                 item_clicked: "recommended products",
                 item_subcategory: trackingValues[0].trim() || '',
                 article_name: $(".articleComponent .article-text .main-title h3").text().trim() || '',
                 location_name: "recommended products",
                 container_type: "",
                 component_name: "recommended products",
                 product_info: sku_id.substring(5, 0) + "|" + trackingValues[2].trim()
             };
             _satellite.track("recommendedProductsFP");
         }

    });

    $("body").on("click", "button", function() {
        if ($(this).parent().attr("id") == "see-more" && $(this).closest(".productGrid").length > 0) {
            clearPreviousData();
            digitalData.eventInfo = {
                event_name: " link click",
                event_type: "click",
                item_clicked: "product list",
                item_subcategory: $(this).text(),
                location_name: "link"
            };
            _satellite.track("event");
        }
    });
    $("body").on("click", ".more-article .articlecomponent-info", function() {
        clearPreviousData();
        digitalData.eventInfo = {
            event_name: "recommended articles click",
            event_type: "click",
            item_clicked: "More Articles",
            item_subcategory: $(this).find('.articlecomponent-headline').text().trim() || '',
            article_category: $(this).find('.articlecomponent-category').text().trim() || '',
            location_name: "More Articles",
            container_type: "",
            component_name: "More Articles",
        };
        _satellite.track("recommendedArticles");

    });
    $("body").on("click", ".targetRecommendation .target-article-carousel", function() {
        clearPreviousData();
        digitalData.eventInfo = {
            event_name: "recommended articles click",
            event_type: "click",
            item_clicked: $(this).parents().siblings('.target-carousal-title').text().trim() || '',
            article_category: $(this).find('.target-carousal-component-category').text().trim() || '',
            item_subcategory: $(this).find('.target-carousal-component-headline').text().trim() || '',
            location_name: $(this).parents().siblings('.target-carousal-title').text().trim() || '',
            container_type: "",
            component_name: $(this).parents().siblings('.target-carousal-title').text().trim() || '',
        };
        _satellite.track("recommendedArticles");

    });
    $("body").on("click", ".data-track-gf", function() {
        clearPreviousData();
        digitalData.eventInfo = {
            event_name: " gift finder click",
            event_type: "click",
            item_clicked: $(this).text(),
            gf_age: $("#age_select_box option:selected").text(),
            gf_price: $("#price_select_box option:selected").text(),
            location_name: "gift finder"
        };
        _satellite.track("event");
    });
    $("body").on("click", ".products-list-item .list-item .custom-checkbox", function() {
        clearPreviousData();
        if ($(this).attr('data-action') != undefined) {
            var breadcrumbSplitStr = breadcrumbValues().split('|');
            var str = decodeURIComponent($(this).attr('data-action'));
            var propNames = str.substring(str.indexOf(";q1") + 1, str.indexOf(";x1"))
            var valsNames = str.substring(str.indexOf(";x1") + 1, str.length)
            var varValArr = propNames.split(";");
            var varPropArr = valsNames.split(";");
            var product = str.substring(str.indexOf("product"), str.indexOf(";q1")).replace("=", ":");
            var finalprops = "";
            for (var counter = 0; counter < varValArr.length; counter++) {
                if (!varPropArr[counter].includes("LegalAge")) {
                    var cat = varPropArr[counter].split('=')[1].replace(/\+/g, " ") + ':';
                    var catVal = varValArr[counter].split('=')[1].split("|");
                    for (var i = 0; i < catVal.length; i++) {
                        finalprops = finalprops + cat + catVal[i].replace(/\+/g, " ") + ',';
                    }
                } else {
                    finalprops = finalprops + varPropArr[counter].split('=')[1] + ":" + varValArr[counter].split('=')[1] + ",";
                }
            }
            var finalString = product.split('?')[0] + finalprops.slice(0, -1);
            digitalData.eventInfo = {
                event_name: "category filter",
                event_type: "click",
                item_clicked: breadcrumbSplitStr[breadcrumbSplitStr.length - 2] || '',
                item_subcategory: breadcrumbSplitStr[breadcrumbSplitStr.length - 1] || '',
                facet_filter: finalString.toLowerCase(),
                location_name: "left nav"
            };
            _satellite.track("PlpCategoryFilter");
        }
    });
    $("body").on("click", "#product-filter-section h2 a", function() {
        clearPreviousData();
        if ($(this).hasClass('collapsed')) {
            digitalData.eventInfo = {
                event_name: "category filter",
                event_type: "click",
                item_clicked: $(this).text().trim().toLowerCase(),
                item_subcategory: "expand",
                location_name: "left nav",
                container_type:"category filter",
                component_name:"plpFacetsLeftNav"
            };
        } else {
            digitalData.eventInfo = {
                event_name: "category filter",
                event_type: "click",
                item_clicked: $(this).text().trim().toLowerCase(),
                item_subcategory: "collapse",
                location_name: "left nav",
                container_type :"category filter",
                component_name :"plpFacetsLeftNav"
            };
        }
        _satellite.track("event");
    });
    $(document).on('click', '#popular-article li a', function() {
        var searchInput = $('#searchInput_field').val();
        searchInput ? window._satellite.cookie.set("searchType", "related articles", 0) : window._satellite.cookie.set("searchType", "popular articles", 0);
        window._satellite.cookie.set("searchTerm", searchInput, 0);
    });
    $('.mobile-container .recall-link').click(function() {
        $(this).attr('data-tracking-id', 'recall-link-click|recall alert|' + $(this).text() + '|recall link');
    });
    var scrollToBazarvoice = false;

    function analyticsParams(param) {
        if (param == 'bazarVoice') {
            if ($('#BVRRContainer').html() != '' && !bvLoaded) {
                digitalData.eventInfo = {
                    event_name: "bazarvoice content view",
                    event_type: "scroll",
                    item_clicked: "bazarvoice",
                    item_subcategory: "bazarvoice",
                    location_name: "bazarvoice content"
                };
                bvLoaded = true;
            } else {
                if (!bverror) {
                    digitalData.eventInfo = {
                        event_name: "bazarvoice content error",
                        event_type: "scroll",
                        item_clicked: "bazarvoice error",
                        item_subcategory: "ERROR MESSAGE",
                        location_name: "bazarvoice content"
                    };
                    bverror = true;
                }
            }
            if (scrollToBazarvoice != true) {
                _satellite.track("event");
            }
            scrollToBazarvoice = true;
        }
    }

    function bazarVoiceAnalytics() {
        if ($('#BVRRContainer').length > 0) {
            var top_of_element = $('#BVRRContainer').offset().top;
            var bottom_of_element = $('#BVRRContainer').offset().top + $("#BVRRContainer").outerHeight();
            var bottom_of_screen = $(window).scrollTop() + $(window).innerHeight();
            var top_of_screen = $(window).scrollTop();
            if ((bottom_of_screen > top_of_element) && (top_of_screen < bottom_of_element)) {
                analyticsParams('bazarVoice');
            }
        }
    }
    $(document).on('click', '#popular-product li a', function() {
        var searchInput = $('#searchInput_field').val();
        searchInput ? window._satellite.cookie.set("searchType", "related products", 0) : window._satellite.cookie.set("searchType", "popular products", 0);
        window._satellite.cookie.set("searchTerm", searchInput, 0);
    });
    $(window).on('scroll', function() {
        bazarVoiceAnalytics();
    });
    productFindingMethod();
});

function webCollageAnalytics() {
    if ($('.webCollage').length > 0 && ($('.webCollage').height() <= 0) || $('.webCollage').length == 0) {
        digitalData.eventInfo = {
            event_name: "webcollage content error",
            event_type: "webcollage error",
            item_clicked: "webcollage error",
            item_subcategory: "webcollage content is not available",
            location_name: "webcollage content"
        };
    }
    _satellite.track("event");
}

function productFindingMethod() {
    var Referring_URL = document.referrer;
    if (Referring_URL == "no-value") {
        window._satellite.cookie.set("pfm", "Page-navigation", 0);
    } else if (Referring_URL.includes('search-results')) {
        window._satellite.cookie.set("pfm", "Onsite-search", 0);
    } else if (Referring_URL.includes('icid=')) {
        window._satellite.cookie.set("pfm", "Internal-promotion-banner", 0);
    } else if (Referring_URL.includes('cid=')) {
        window._satellite.cookie.set("pfm", "External-promotion", 0);
    } else if (Referring_URL.includes('rp_reflink=')) {
        window._satellite.cookie.set("pfm", "Recommendation", 0);
    }
    var pfm = window._satellite.cookie.get("pfm") ? window._satellite.cookie.get("pfm") : '';
    if (pfm != "Onsite-search" && pfm != "Internal-promotion-banner" && pfm != "External-promotion" && pfm != "Recommendation") {
        window._satellite.cookie.set("pfm", "Page-navigation", 0);
    }
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

function sendNavToAnalytics(params, evtName) {
    digitalData.eventInfo = params;
    _satellite.track("NavigationLinksFP");
}

function camelize(str) {
    return str.replace(/(?:^\w|[A-Z]|\b\w|\s+)/g, function(match, index) {
        if (+match === 0)
            return ""; // or if (/\s+/.test(match)) for white spaces
        return index == 0 ? match.toLowerCase() : match.toUpperCase();
    });
}


var globalApiSuccessForm = function(res) {
    clearPreviousData();
    var add_Child = "";
    var sourceid = $("#crmConfig").attr("data-source-id") || '';
    var subscriptionid = $("#crmConfig").attr("data-subscription-id") || '';
    $(".child-information").each(function(index) {
        var field = $(this);
        var childGender = '';
        var childNo = index + 1;
        var childDob = field.find(".date-input-picker input").val() || '';
        var gender = field.find('.gender input:checked').val() || '';
        if (gender == 'M') {
            childGender = 'boy';
        } else if (gender == 'F') {
            childGender = 'girl';
        } else if (gender == 'U') {
            childGender = 'unknown';
        }
        var childRelation = field.find('.relationship input:checked').val() || '';
        add_Child = add_Child + "child" + childNo + ":" + childGender + ", child" + childNo + "-" + "relationship:" + childRelation + ", child" + childNo + "-" + "DOB:" + childDob + ", ";
    });
    var signupmethod = window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '';
    var action = signupmethod == '' ?'' : signupmethod+'-';
    digitalData.eventInfo = {
        event_action: "newsletter-signup-" + action + "complete",
        event_action_type: "click",
        form_type: "subscription",
        form_stage: "form-submission",
        customer_email_hash: res.customer_email_hash,
        add_child: add_Child.substring(0, add_Child.length - 2),
        source_id: sourceid,
        sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
        subscription_id: subscriptionid,
        component_name:"subscription",
        component_type:"crm",
        component_step:"complete",
        container_type:getContainerType(res.current_ele),
        location_name: "newsletter-signup-form-submission"
    };
    _satellite.track("formSubmit");
    _satellite.track("thankYou");
}

var apiSuccessForm = function(res) {
    clearPreviousData();
    var add_Child = "";
    var sourceid = $("#formSourceId").val() || '';
    var subscriptionid = $("#nosubscriptionId").val() || '';
    $(".child-details-fieldset").each(function(index) {
        var field = $(this);
        var childGender = '';
        var childNo = index + 1;
        var childDob = field.find(".child-dob input").val() || '';
        var gender = field.find(".child-gender input:checked").val() || '';
        if (gender == 'M') {
            childGender = 'boy';
        } else if (gender == 'F') {
            childGender = 'girl';
        } else if (gender == 'U') {
            childGender = 'unknown';
        }
        var childRelation = field.find(".child-relation input:checked").val() || '';
        add_Child = add_Child + "child" + childNo + ":" + childGender + ", child" + childNo + "-" + "relationship:" + childRelation + ", child" + childNo + "-" + "DOB:" + childDob + ", ";
    });
    var signupmethod = window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '';

    digitalData.eventInfo = {
        event_action: "newsletter-signup-" + signupmethod + "-complete",
        event_action_type: "click",
        form_type: "subscription",
        form_stage: "form-submission",
        customer_email_hash: res.customer_email_hash,
        add_child: add_Child.substring(0, add_Child.length - 2),
        source_id: sourceid,
        sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
        subscription_id: subscriptionid,
        location_name: "newsletter-signup-form-submission"
    };
    _satellite.track("formSubmit");
    _satellite.track("thankYou");
}

var trackRemoveChild = function(obj) {
    clearPreviousData();
    digitalData.eventInfo = {
        event_action: obj.event_action,
        event_action_type: obj.event_action_type,
        form_stage: obj.form_stage,
        form_type: obj.form_type,
        event_detail: obj.event_detail,
        event_detail_sub: obj.event_detail_sub,
        sign_up_method: obj.sign_up_method,
        location_name: obj.location_name
    };
    _satellite.track("formCTA");
}

var globalTrackRemoveChild = function(obj) {
    clearPreviousData();
    digitalData.eventInfo = {
        event_action: obj.event_action,
        event_action_type: obj.event_action_type,
        form_stage: obj.form_stage,
        form_type: obj.form_type,
        event_detail: obj.event_detail,
        event_detail_sub: obj.event_detail_sub,
        sign_up_method: obj.sign_up_method,
        component_type:"crm",
        component_name:"subscription",
        component_step:"remove child",
        container_type:getContainerType(obj.current_ele),
        location_name: obj.location_name
    };
    _satellite.track("formCTA");
}

var checkFormHasData = function(res) {
    digitalData.eventInfo = {
        event_action: "newsletter-signup-form-abandonment",
        event_action_type: "form abandonment",
        form_stage: "form abandonment",
        form_type: "subscription",
        sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
        form_abandonment_type: res.type,
        last_access_field: lastAccessField
    };
    _satellite.track("formSubmit");
}
var apiFormError = function(res, email) {
    clearPreviousData();
    digitalData.eventInfo = {
        event_action: "newsletter-signup-post-form-failure",
        event_action_type: "post-form-failure",
        form_stage: "post-form-failure",
        form_type: "subscription",
        sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
        customer_email_hash: email,
        error_message: res.error_message,
        error_code: res.error_code
    };
    _satellite.track("formSubmit");
}

var relatedSearchClick = function(item_subcateg, article_categ, relatedTitle) {
    clearPreviousData();
    digitalData.eventInfo = {
            event_name: "recommended articles click",
            event_type: "click",
            item_clicked: relatedTitle,
            item_subcategory: item_subcateg || '',
            location_name: relatedTitle,
            container_type: "",
            component_name: relatedTitle,
            article_category: article_categ || ''
        };
        _satellite.track("event");
}
var clientFormValidationError = function(res, email) {
    clearPreviousData();
    digitalData.eventInfo = {
        event_action: "newsletter-signup-pre-form-failure",
        event_action_type: "pre-form-failure",
        form_stage: "pre-form-failure",
        form_type: "subscription",
        sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
        customer_email_hash: email,
        error_name: res.error_name,
        error_field: res.error_field
    };
    _satellite.track("formSubmit");
}
 var emailPreferenceFormClick=true;
  var emailPreferenceFormLastClick = '';
  $('.email-preferences-content-block .subscription-opt .radio .unsubscribe , #changeEmailPref').click(function () {
    emailPreferenceFormLastClick = $(this).parent().find('.checkval').text().trim();
      if(emailPreferenceFormClick==true) {
          emailPreferenceFormField();
          emailPreferenceFormClick=false;
      }
  });

  function emailPreferenceFormField() {
      clearPreviousData();
      digitalData.eventInfo= {
          event_action: "email-preferences-initiation",
          event_action_type: "click",
          form_type: "update your email preferences",
          form_stage: "initiation",
          location_name: "email-preferences-form-field",
          component_type: "crm",
          component_name: "email preferences",
          component_step: "page",
          container_type: "initiation"
      };
      _satellite.track("formInitiation");
  }

  $("body").on("click", '.email-preferences-content-block .btn-primary', function () {
      clearPreviousData();
      var pref = [];
      $.each($(".mattelBrandsList  .check-active"), function () {
      	var label=$(this).find('.checkval').text().trim();
         pref.push(label);
      });

      digitalData.eventInfo= {
          event_action: "email-preferences-complete",
          event_action_type: "click",
          form_type: "update your email preferences",
          form_stage: "final-complete",
          location_name: "email-preferences-form-submission",
          form_options: $("input[name='radio']:checked").parent().find('.checkval').text().trim(),
          preferences:  pref.join(","),
          component_type: "crm",
          component_name: "email preferences",
          container_type: "page",
          component_step: "complete"
      };
      _satellite.track("formSubmit");
  });

  window.onbeforeunload = function () {

     var $formElem = $(".email-preferences-component");
     if($formElem.length){
         if($formElem.find($("input[name='radio']:checked"))){
            var abandon_type = "";
            if (performance.navigation.type == 1) {
                 abandon_type = "page reload"
            } else {
                 abandon_type = "browser close";
            }
          digitalData.eventInfo= {
          event_action: "email preferences-abandonment",
          event_action_type: "form abandonment",
          form_type: "update your email preferences",
          form_stage: "form abandonment",
          form_abandon_type: abandon_type,
 		 last_accessed_field: emailPreferenceFormLastClick
      };
      _satellite.track("formSubmit");
         }
     }
 };

var populateCountryDropDownData = function(countryName) {
    digitalData.eventInfo = {
        event_name: "Country Dropdown Section",
        event_type: "click",
        item_clicked: previous_value || $('.filter-option-inner-inner').text(),
        item_subcategory: countryName
    };
    _satellite.track("event");
}

function getPagename() {
    var brand = '';
    if (digitalData.pageInfo['Business_site'] == "fisher-price") {
        brand = "FP";
    } else {
        brand = digitalData.pageInfo['Business_site'];
    }
    return brand;
}

function getCleanurl() {
    var data = digitalData.pageInfo['clean_url'];
    var arr = data.split('/shop');
    return arr[1].split('/').join(':');

}

function getPdpCleanurl() {
    var data = digitalData.pageInfo['product_url'];
    var siteLanguage = digitalData.pageInfo['site_language'];
    var arr = data.split(siteLanguage.toLowerCase());
    var name = arr[1].split('/').join(':');
    return name.substr(0, name.length - 6).split("-").join(" ");
}

function getImpressionVariable() {
    var sku_id = "";
    var pName = "";
    var pPrice = "";
    var pPosition = "";
    var count = 1;
    $('#product-grid-container li').each(function(i) {
        var sku = $(this).attr('data-partno'); // This is your rel value
        var name = $(this).find(".track-product-grid-title").text().trim()
        var price = $(this).find(".product-price > span").text().trim();
        var doller_index = price.lastIndexOf('$');
        var final_price = "$" + price.substring(doller_index + 1);
        var index = $(this).index() + 1;
        var position = '1:' + index + ':' + $('.slide-header .total-itemcnt-inner').text();
        if (count < 2) {
            pPrice = pPrice + final_price;
            pName = pName + name;
            sku_id = sku_id + sku;
            pPosition = pPosition + position;
        } else {
            pName = pName + "|" + name;
            pPrice = pPrice + "|" + final_price;
            sku_id = sku_id + "|" + sku;
            pPosition = pPosition + "|" + position;
        }
        count++;
    });
    digitalData.pageInfo['product_impression_name'] = pName || ''
        digitalData.pageInfo['product_impression_sku'] = sku_id || ''
        digitalData.pageInfo['product_impression_price'] = pPrice || ''
        digitalData.pageInfo['product_impression_position'] = pPosition || ''
}

function clearPreviousData() {
    if (digitalData.pre_submit_errorInfo != undefined) {
        delete digitalData.pre_submit_errorInfo;
    }
    if (digitalData.formInfo != undefined) {
        delete digitalData.formInfo;
    }

    if (digitalData.Post_submit_errorInfo != undefined) {
        delete digitalData.Post_submit_errorInfo;
    }
}
function getContainerType ($this) {
    var containerType = "page";
    if (($this).parents(".modal-component").length > 0){
        containerType = "modal";
    }
    else if (($this).parents(".slidein").length > 0){
         containerType = "slider";
     }
     return containerType;
}


$('.add-sub-form-group').click(function() {
    clearPreviousData();
        digitalData.eventInfo = {
            event_action: "newsletter-signup-add-child",
            event_action_type: "click",
            form_type: "subscription",
            form_stage: "child addition",
            event_detail: "child addition",
            event_detail_sub: 'add a child',
            sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
            component_type:"crm",
            component_name:"subscription",
            component_step:"add child",
            container_type:getContainerType($(this)),
            location_name: "newsletter-signup-add-child"
        };
    _satellite.track("formCTA");
});

$(".adaptive-form input[type='text'],.adaptive-form input[type='email'],.adaptive-form input[type='radio'],.adaptive-form input[type='checkbox'],.adaptive-form select,.adaptive-form textarea").click(function() {
    if (formInitCount == 0 && !($(this).attr("id") == "crm-form-submit")) {
        var $this=$(this);
        digitalData.eventInfo = {
            event_action: "newsletter-signup-initiation",
            event_action_type: "click",
            form_type: "subscription",
            form_stage: "initiation",
            sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
            component_type:"crm",
            component_name:"subscription",
            component_step:"initiation",
            container_type:getContainerType($this),
            location_name: "newsletter-signup-form-field"
        };
        _satellite.track("formInitiation");
    }
    formInitCount++;
});
var globalApiFormError = function(res, email) {
    clearPreviousData();
    digitalData.eventInfo = {
        event_action: "newsletter-signup-post-form-failure",
        event_action_type: "post-form-failure",
        form_stage: "post-form-failure",
        form_type: "subscription",
        sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
        error_message: res.error_message,
        error_code: res.error_code
    };
    _satellite.track("formSubmit");
}
var globalClientFormValidationError = function(res, email) {
    clearPreviousData();
    digitalData.eventInfo = {
        event_action: "newsletter-signup-pre-form-failure",
        event_action_type: "pre-form-failure",
        form_stage: "pre-form-failure",
        form_type: "subscription",
        sign_up_method: window._satellite.cookie.get("signupmethod") ? window._satellite.cookie.get("signupmethod") : '',
        error_name: res.error_name,
        error_field: res.error_field
    };
    _satellite.track("formSubmit");
}