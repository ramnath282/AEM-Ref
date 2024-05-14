var path = window.location.pathname;
var path_val = path.split('/').join(':').replace(".html", "").substring(1);
var page_name = path_val.replace(".html", "");
var page_type = $("#pageType").val();
var site_type = "commerce";
var pageTemplate = $('meta[name=template]').attr("content");
var cleanUrl = window.location.pathname.replace(".html", "");
var store_id = $('#storeId').val();
var store_name = $('#storeName').val();
var language = $("html").attr("lang") ? $("html").attr("lang") : "";
var site_region = "";
var product_name = "";
var product_sku = "";
var bundle_type = "";
var PDPProductName = $('.product-info-wrapper h1').text();
var pdpProductType = $('.product-info-wrapper.parent').attr('data-producttype');
var urlArray = path.split("/");
var page = urlArray.pop();
var giftTrunkText = $("#largeTrunk .trunk-heading").text();
var starterSetText = $("#smallTrunk .trunk-heading").text();
var first_click = 0;
var signup_location = 0;
var selected_children ="";
var ELB_last_accessed_field="";
var site_subsection = "";
var quickViewFlag = false;
var quickViewProdCategory = "";
var quickViewProdCharacter = "";
var productListingPage = "";

if (pageTemplate == "doll-hospital-template") {
	page_name = $('#pageNameDH').val();
	page_type = $('#pageTypeDH').val();
	site_subsection = page_name.substring(page_name.lastIndexOf(":")+1,page_name.length);
}

if (page.length == 0 && urlArray.length > 1) {
    page = urlArray[urlArray.length - 1];
}
if ($('#siteKey').attr('value') == "ag_en") {
    page_name = "ag:us:" + page_name;
}
var pageNameGT = document.getElementById("pageNameGT");
if (pageNameGT && pageNameGT.value) {
	page_name = $('#pageNameGT').val();
}
var pageTypeGT = document.getElementById("pageTypeGT");
if (pageTypeGT && pageTypeGT.value) {
	page_type = $('#pageTypeGT').val();
}
var pageTypeVariable = $('#pageTypeGT').val();
if(typeof(pageTypeVariable) != 'undefined'){
	if( pageTypeVariable.indexOf("homepage") != -1 || pageTypeVariable.indexOf("coppa") != -1 ){
	        site_type = "";
	}
}
var referrerUrl = document.referrer;
if(referrerUrl.indexOf("americangirl.com") != -1) {
    productListingPage = document.referrer;
}

var setCookieValue = function (cname, cvalue, exdays) {
    var d = new Date();
    exdays = exdays ? exdays : 90
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    if (cname == "MATTEL_VISITOR_STATUS") {
        if (getCookieValue("MATTEL_VISITOR_STATUS")) {
            expires = getCookieValue("MATTEL_VISITOR_STATUS_EXPIRY");
        } else {
            expires = d.toUTCString();
            document.cookie = "MATTEL_VISITOR_STATUS_EXPIRY" + "=" + expires + ";" + expires + ";path=/";
        }
    }
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function deleteCookieValue(name) {
	document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
	}

var getCookieValue = function (cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            var customer_type_length = c.length - name.length;
            if (customer_type_length > 2) {
                return c.substring(name.length, c.length);
            } else return null;
        }
    }
    return "";
}

var utag_data = utag_data || {};
var cust_email_optin_status = function () {
    if (sessionStorage.getItem("custemail")) {
        utag_data.customer_email_hash = typeof sha256 == "function" && sha256(JSON.parse(sessionStorage.getItem("custemail")))
    }
    if (sessionStorage.getItem("optin_status")) {
        utag_data.optin_status = sessionStorage.getItem("optin_status")
    }
    if (getCookieValue('LIGHT_BOX_SUBMITTED') == 'true') {
        utag_data.optin_status = getCookieValue('LIGHT_BOX_SUBMITTED');
    }
    if (getCookieValue('LIGHT_BOX_LOCATION') == 'true') {
        utag_data.email_capture_location = getCookieValue('LIGHT_BOX_LOCATION');
    }
    if (getCookieValue('ELB_SUBMIT_SUCCESS') == 'true') {
        utag_data.optin_status = "true";
        utag_data.email_capture_location = "lightbox";
    }
}
/*functions for ELB starts*/

/*Function for collecting user entered child info on successful form submission*/
var listChildren = function () {
	var str="";
	$(".child-container .child-info-form").each(function (i, item) {
		var child_info="";
		var count=i+1;
		child_info="child"+count+":"+$(item).find("input[name='childOption-"+count+"']:checked").siblings("label").text()+",child"+count+"relationship:"+$(item).find("input[name='guardianOption-"+count+"']:checked").siblings("label").text();
		str!="" ? str = str+","+child_info : str=child_info;
	});
	return str;
};

/*Function for collecting set of all validation errors*/
var listErrors = function () {
	var error_string="";
	$(".email-lightboxpopup-comp .has-error .form-message").each(function (i, item) {
		var error_msg = $(this).text();
		error_string!="" ? error_string = error_string+"|"+error_msg : error_string=error_msg;
	});
	return error_string;
};

/*Function for data layer population on appearance of ELB*/
var emailLightBoxPrompt = function () {
	first_click = 0;
	utag_data.event_action_type = "prompt";
	utag_data.form_stage = "start";
	utag_data.location_name = "light box";
};

/*Function for common variable population for ELB without child*/
var newsletterVars = function () {
	utag_data.event_action = "lightbox newsletter signup";
	utag_data.form_type = "newsletter_signup";
};

/*Function for common variable population for ELB with child*/
var emailVars = function () {
	utag_data.event_action = "light box email signup";
	utag_data.form_type = "email_signup";
};

/*Common function for add/remove child in ELB with child*/
var addRemoveChild = function (action_name) {
	utag_data.event_action = "light box email signup";
	utag_data.form_type = "email_signup";
	utag_data.event_action_type = "click";
	utag_data.location_name = "light box";
	utag_data.form_stage = "child "+action_name;
	utag_data.event_detail = "child "+action_name;
};

/*Function for data population when ELB is abandoned*/
var emailLightBoxAbandonment = function () {
	if($('#emailLightBoxModal').length && $('#emailLightBoxModal').is(":visible")){
		if(signup_location=="onload"){
			newsletterVars();
		}
		else if(signup_location=="hamburger" || signup_location=="footer"){
			emailVars();
		}
        utag_data.event_action_type = "form abandonment";
        utag_data.form_stage = "form abandonment";
        utag_data.form_abandon_type = "browser close";
		utag_data.last_accessed_field = ELB_last_accessed_field;
        utag.link(utag_data);
	}
}
/*functions for ELB Ends*/

// Function for identifying platform
var device = function () {
    if (navigator.userAgent.match(/Tablet|iPad/i)) {
        return "Tablet";
    } else if (navigator.userAgent.match(/Mobile|Windows Phone|Lumia|Android|webOS|iPhone|iPod|Blackberry|PlayBook|BB10|Opera Mini|\bCrMo\/|Opera Mobi/i)) {
        return "Mobile";
    } else {
        return "Desktop"
    }
};
var site_name = function () {
    if (window.location.href.indexOf('/shop') >= 0 ) {
        return "shop"
    } else if (window.location.href.indexOf('/discover') >= 0 ) {
        return "discover"
    } else if (window.location.href.indexOf('/retail') >= 0) {
        return "retail"
    } else if (window.location.href.indexOf('/explore') >= 0 ) {
        return "explore"
    } else if (window.location.href.indexOf('/doll-hospital') >= 0 ) {
        return "doll hospital"
    } else {
        return "";
    }
};
var visitor_status = function () {
    utag_data.visitor_status = getCookieValue('MATTEL_VISITOR_STATUS');
};
var loyalty_details = function () {
    var loyaltydata = JSON.parse(sessionStorage.getItem("loyalty"));
    if (loyaltydata != null) {
        utag_data.loyalty_level = loyaltydata.loyaltySegment
        utag_data.loyalty_id = loyaltydata.loyaltyNumber
        utag_data.loyalty_points = loyaltydata.loyaltyPoints
        utag_data.loyalty_dollars_to_next_tier = loyaltydata.loyaltyPointsToNextTier
        utag_data.loyalty_tier_expiration_date = loyaltydata.tierExpirationDate
    }
};
var customer_type = function () {
    utag_data.customer_type = window.global.getUserCookie().customerSegment;
}

var contactpreferencesAnalytics = function (data) {
    var obj = jQuery.parseJSON(data);
    var analyticsData = (obj.CONT_PREF_GL == "") ? 'email preferences:no' : 'email preferences:yes';
    sessionStorage.setItem("optin_status", analyticsData);
    if (obj.CONT_PREF_CAT.length > 0) {
        analyticsData = analyticsData + (',' + obj.CONT_PREF_CAT).toString() + ',';
    } else {
        analyticsData = analyticsData + ",no-message preferences,";
    }
    var contPrefCat = obj.CONT_PREF_LOY;
    analyticsData = analyticsData + (contPrefCat.length > 0 ? contPrefCat.toString() + ',' : 'NO-REWARDS,');
    var contPrefCtl = (obj.CONT_PREF_CTL).toString();
    if ((obj.CONT_PREF_CTL).length > 1) {
        analyticsData = analyticsData + 'receive catalogue:yes,information_sharing:yes';
    } else if ((obj.CONT_PREF_CTL).length == 1) {
        var strToAdd = (contPrefCtl == 'SHARED~ALL') ? 'receive catalogue:no,information_sharing:yes' : 'receive catalogue:yes,information_sharing:no';
        analyticsData = analyticsData + strToAdd;
    } else {
        analyticsData = analyticsData + "receive catalogue:no,information_sharing:no";
    }
    utag_data.event_detail = "Email Preferences|Message Preferences|AG Rewards|Catalogue by mail|information sharing"
    utag_data.event_detail_sub = analyticsData.toLowerCase().split('~').join('_')
    utag_data.event_action = "click event"
    utag_data.event_action_type = "click"
    utag_data.location_name = $("h1").text().toLowerCase()
    utag_data.optin_status = sessionStorage.getItem("optin_status")
    utag.link(utag_data);
};
var productInterestAnalytics = function (data) {
    if (data != undefined) {
        var productInterestAnalyticsData = (data.PROD_INTER_PI.length > 0 ? (data.PROD_INTER_PI).toString() : "no-product interests") + ",";
        productInterestAnalyticsData = productInterestAnalyticsData + (data.selectCountry.split(" ")[0] == "" ? "no-store interests" : data.selectCountry.split(" ")[0]) + ",";
        productInterestAnalyticsData = productInterestAnalyticsData + (data.PROD_INTER_LO.length > 0 ? (data.PROD_INTER_LO).toString() : "no-activities");
        utag_data.event_detail = "account summary interests|product interests|store interests|store activities"
        utag_data.event_detail_sub = "account summary update," + productInterestAnalyticsData.toLowerCase()
        utag_data.location_name = $("h1").text().toLowerCase()
        utag_data.event_action = "click event"
        utag_data.event_action_type = "click"
        console.log(utag_data);
        utag.link(utag_data);
    }
};
var custAuthInfo = function () {
    var cookieList = document.cookie.split(';')
    var cList = [];
    var customer_id_str = "";
    for (var i = 0; i < cookieList.length; i++) {
        if (cookieList[i].indexOf("WC_AUTHENTICATION") != -1) {
            cList.push(cookieList[i])
        }
    }
    if (cList.length > 0) {
        customer_id_str = cList[0].split('=')[0].split('WC_AUTHENTICATION_')[1]
    }

    if (getCookieValue('MATTEL_WELCOME_MSG') != "") {
        utag_data.customer_id = customer_id_str
        utag_data.wcsrefid = customer_id_str
        utag_data.authentication_status = "true"
        utag_data.logged_in_status = "registered"
    } else {
        if (customer_id_str == -1002) {
            utag_data.authentication_status = "false"
            utag_data.logged_in_status = "anonymous"
        } else {
            utag_data.authentication_status = "false"
            utag_data.logged_in_status = "guest"
        }
    }
    return;
};
var checkForError = function () {
    var errorList = "";
    var errorSelect = $('.doll-registration-form').find('.has-error');
    if (errorSelect.length > 0) {
        errorSelect.find('select').each(function () {
            errorList = errorList + $(this).attr("name") + "|";
        });
        utag_data.event_name = 'form failure'
        utag_data.event_type = 'click'
        utag_data.event_detail = $(".doll-reg").val().toLowerCase() //'doll registration'
        utag_data.event_detail_sub = 'pre form failure'
        utag_data.error_name = 'validation error'
        utag_data.error_field = errorList.slice(0, -1)
        utag_data.location_name = $("h1").text().toLowerCase()
        utag.link(utag_data)
    }
};
var checkForAddressBookError = function (detail) {
    var addErrorList = "";
    var errorSelect = $('#updateAddress').find('.has-error');
    if (errorSelect.length > 0) {
        errorSelect.find('input').each(function () {
            addErrorList = addErrorList + $(this).attr("id") + "|";
        });
        utag_data.event_name = 'form failure'
        utag_data.event_type = 'click'
        utag_data.event_detail = detail.toLowerCase()
        utag_data.event_detail_sub = 'pre form failure'
        utag_data.error_name = 'validation error'
        utag_data.error_field = addErrorList.slice(0, -1)
        utag_data.location_name = $("h1").text().toLowerCase()
        utag.link(utag_data)
    }
};
 utag_data = {
    "page_name": page_name,
    "page_type": page_type,
    "clean_url": cleanUrl,
    "company_division": "american girl " + site_name().toLowerCase(),
    "category_id": "",
    "email_capture_location": getCookieValue('LIGHT_BOX_LOCATION') ? getCookieValue('LIGHT_BOX_LOCATION') : '',
    "platform": device().toLowerCase(),
    "referring_url": document.referrer,
    "requested_url": window.location.href,
    "site_section": site_name(),
    "site_subsection": site_subsection,
    "site_language": $("html").attr("lang"),
    "site_currency": "usd",
    "site_region": "us",
    "site_type": site_type,
    "store_id": store_id,
    "authentication_status": "",
    "carousel_detail": "",
    "carousel_detail_sub": "",
    "carousel_click": "",
    "customer_id": "",
    "customer_country": "",
    "customer_email_hash": "",
    "customer_type": "",
    "promo_drawer_detail": "",
    "promo_drawer_detail_sub": "",
    "last_accessed_field": "",
    "logged_in_status": "",
    "loyalty_level": "",
    "loyalty_id": "",
    "loyalty_points": "",
    "location_name": "",
    "loyalty_dollars_to_next_tier": "",
    "loyalty_tier_expiration_date": "",
    "modal_section": "",
    "navigation_click": "",
    "optin_status": "false",
    "visitor_status": "",
    "wcsrefid": "",
    "cart_total_items": "",
    "cart_total_value": "",
    "component_name": "",
    "doll_add_on": "",
    "doll_add_on_name": "",
    "event_action": "",
    "event_action_type": "",
    "event_detail": "",
    "event_detail_sub": "",
    "event_name": "",
    "event_type": "",
    "error_name": "",
    "error_field": "",
    "jumbotron_detail": "",
    "jumbotron_detail_sub": "",
    "jumbotron_image_carousel": "",
    "product_add_on": "",
    "product_add_on_name": "",
    "product_name": "",
    "product_sku": "",
    "product_position": "",
    "page_template_id": pageTemplate,
    "product_category": "",
    "product_subcategory": "",
    "personalization_strategy": "",
    "product_status": "",
    "product_selection_position": "",
    "promo_campaign_id": "",
    "facet_filter": "",
    "tool_type": "",
    "trunk_range": "",
    "trunk_size": "",
    "include_for": "",
    "hearing_aid": "",
    "doll_name_detail": "",
    "doll_finding_method": "",
    "gt_doll_details": "",
    "gt_bundle_details": "",
    "gt_letter_details": "",
    "gt_envelope_details": "",
    "gt_trunk_details": "",
    "gt_add_on": "",
    "gt_hnh_insert": "",
    "gt_doll_image_url": "",
	"dh_dollname": "",
	"dh_dolltype": "",
	"dh_eyecolor": "",
	"dh_haircolor": "",
	"dh_doll_treatment": "",
	"dh_doll_addon": "",
	"dh_doll_list": "",
	"product_quantity": "",
    "product_price": "",
	"form_type":"",
    "form_abandon_type":"",
    "error_code":"",
    "error_message":"",
    "product_listing_page":productListingPage
}



var initialUtagData = $.extend({}, utag_data);
var clearPreviousData = function () {
    utag_data = $.extend({}, initialUtagData);
    callOnLoadMethods();
}
var commonClickEventFn = function () {
    utag_data.event_action = "click event";
    utag_data.event_action_type = "click";
    utag.link(utag_data);
}
var setUtagDataCommon = function () {
    clearPreviousData();
    if($('.pdpproduct .product-info-wrapper').length != 0 || $('.giftcard .product-info-wrapper').length != 0){
		product_name = PDPProductName;
		product_sku = $('.product-info-wrapper').attr('data-partnumber');
		if(pdpProductType == "BundleBean"){
			getProductSkuDetails();
		}
		bundle_type = $('.product-info-wrapper').attr('data-producttype');
    }else{
        product_name = $("#quickViewModal .product-name").text();
        product_sku = $("#quickViewModal #addToBagBtn").attr('data-parentpartnumber');
        bundle_type = $("#quickViewModal #addToBagBtn").attr('data-producttype');
	    utag_data.product_category = $("#quickViewModal .product-info-wrapper").attr('data-agcategory') || "";
    }
    var age = $('.age-specification').text();
    if ($('.age-specification').text().indexOf(':') != -1) {
        age = $('.age-specification').text().split(':')[1].trim();
    }
    if ($('.product-info-wrapper .associations-addons').length) {
        utag_data.product_add_on = "no";
        var selected_productaddon_array = [];
        var selected_product;
        $.each($(".product-info-wrapper .associations-addons"), function () {
            if ( $('.custom-checkbox').attr('aria-checked') == 'true' ){
                selected_product = $(this).find('label').text();
                selected_productaddon_array.push(selected_product);
                utag_data.product_add_on = "yes";
            }
            else{
                selected_product = $(this).find('label').text();
                selected_productaddon_array.push(selected_product);
            }
        });
        var product_addonname = selected_productaddon_array.toString();
        utag_data.product_add_on_name = product_addonname;

    } else {
        utag_data.product_add_on = "not applicable";
        utag_data.product_add_on_name = "not applicable";
    }
    utag_data.affirm_eligibility = ($('.product-container .bag__affirm').text().length && $('.product-container [class="bag__affirm hide"]').length == 0) ? "N" : "Y";
    utag_data.affirm_message = utag_data.affirm_eligibility == "N" ? $('.product-container .bag__affirm').text().trim() : "not-available";
    utag_data.product_name = product_name || "";
	utag_data.product_sku = product_sku || "";
    setProductUtagDetails(age);
    utag_data.bundle_type = bundle_type || "";
    utag_data.doll_add_on = $("#addons_available_field").val() ? "Yes" : "NO";
    utag_data.doll_add_on_name = $("#selected_addons_field").val() || "not applicable";

}

//This function is written to get the child SKU details in case BundleBean product
var getProductSkuDetails = function () {
	var productSkus = [];
	var $bundleEle = $(".pdpproduct .bundle-check-item input");
	var $addOnEle = $(".pdpproduct .addon-box input");

	$bundleEle.each(function (index) {
		if ($(this).is(":checked") && !$(".bundle-info").eq(index).find(".innerCont").length) {
			productSkus.push($(this).attr("data-partnumber"));
		} else if($(this).is(":checked") && $(".bundle-info").eq(index).find(".innerCont").length){
			var $swatchEle = $(".bundle-info").eq(index).find(".text-swatches [data-partnoswatch]");
			$swatchEle.each(function (swatchIndex) {
				if ($(this).hasClass("active")) {
					productSkus.push($swatchEle.eq(swatchIndex).attr("data-partnoswatch"));
				}
			});
		}
	});

	$addOnEle.each(function () {
		if ($(this).is(":checked")) {
			productSkus.push($(this).attr("data-partnumber"));
		}
	});
	utag_data.product_partnumbers = productSkus;
}

var setAccordionUtagDetails = function (ele) {
    var video_name = $(ele).find('source').attr('src').split('/').pop();
    if (video_name.indexOf('.') != -1) {
        video_name = $(ele).find('source').attr('src').split('/').pop().split('.')[0];
    }
    utag_data.event_action = "video clicks";
    utag_data.event_action_type = "click";
    utag_data.event_detail = video_name;
    utag_data.video_id = "html5-" + video_name;
    utag_data.video_length = ele.duration;
    utag_data.video_platform = "html5";
    utag.link(utag_data);
}
var setCommonPropForLink = function (subDetail) {
    utag_data.event_action = "click event"
    utag_data.event_action_type = "click"
    utag_data.event_detail = "account - " + $("h1").text().toLowerCase()
    utag_data.event_detail_sub = subDetail.toLowerCase()
    utag_data.location_name = $("h1").text().toLowerCase()
    utag.link(utag_data);
}
var setCommonProp = function (subDetail, detail) {
    utag_data.event_name = "form tracking"
    utag_data.event_type = "click"
    utag_data.event_detail = detail
    utag_data.event_detail_sub = subDetail.toLowerCase() //"form-submit"
    utag_data.location_name = $("h1").text().toLowerCase()
    console.log(utag_data);
    utag.link(utag_data);
}
var setLinkCommonProp = function (detail, subDetail) {
    utag_data.event_action = "click event"
    utag_data.event_action_type = "click"
    utag_data.event_detail = $("h1").text().toLowerCase() + " - " + detail.toLowerCase()
    utag_data.event_detail_sub = subDetail.toLowerCase()
    utag_data.location_name = $("h1").text().toLowerCase()
    //console.log(utag_data);
    utag.link(utag_data);
}
var setSearchCount = function (count) {
    utag_data.search_results_count = count;
    utag_data.search_status = (count > 0 ? "successful" : "unsuccessful");
}
var setSearchResultsData = function () {
	utag_data.search_status = (($('.product-grid-no-search-result-txt').hasClass('hide')) ? "successful" : "unsuccessful");
	utag_data.search_results_count = $('.productGrid .product-grid-count').text();
	utag_data.search_term = decodeURI(getParamValueFromURL("searchTerm") || '').trim();
    utag_data.search_category = document.cookie.indexOf("SEARCH_ANALYTICS_COOKIE")>=0 ? "typeahead search":"user entered search";
    utag_data.search_type = decodeURI(getParamValueFromURL("searchCategory") || '').trim();
    utag_data.product_category = "";
    utag_data.product_subcategory = "";
    utag_data.page_type = "search results page";
    utag_data.site_type = "commerce";
}

var setPDPData = function () {
    getConsumerId()
    utag_data.socialshare_icons_loaded = $(".socialShareForProducts .at-resp-share-element").length ? "true" : "false";
    var partNumber = $('.product-info-wrapper').attr('data-partnumber');
    utag_data.product_category = $('.PDP_Product_category').attr('data-category') || "not_applicable";
    utag_data.product_character = $('.PDP_Product_character').attr('data-character') || "not_applicable";
    utag_data.product_name = PDPProductName;
    utag_data.product_age = $('.age-specification span').text();
    utag_data.product_sku = partNumber;
    utag_data.product_brand = "AG"
    utag_data.product_bundle = $('.product-info-wrapper').attr('data-producttype');
    if ($('.product-info-wrapper').attr('data-producttype') != "BundleBean") {
        utag_data.product_discount = "";
        utag_data.product_list_price = $('.product-price .current_price').text();
        utag_data.product_original_price = $('.product-price .current_price').text();
        utag_data.product_unit_price = $('.product-price .current_price').text();
    }
    utag_data.product_id = partNumber;
    utag_data.product_image_url = $("#scenesevenUrls").attr("data-scenesevenServerurl") + "/" + partNumber;
    utag_data.product_reviews_count = $('.read-reviews span').text();
    utag_data.product_url = $(location).attr("href");
    utag_data.product_pricing_indicator = "USD";
    utag_data.product_availability = $('.inventory-status-message').text();
    utag_data.likelihood_to_recommend = "";
    utag_data.product_availability_identifier = "";
    utag_data.product_inventory_level = "";
    utag_data.product_line = "";
    utag_data.product_promo_code = "";
    utag_data.product_subbrand = "";
    utag_data.product_variant = "";
    utag_data.product_position = "";
    utag_data.site_type = "commerce";
    utag_data.product_average_rating = ""
}

var bag_type_value = function () {
    var cart_total = $('#minishopcart_total').text();
    if (cart_total > 1) {
        utag_data.bag_type = "multiple sku";
    } else if (cart_total == 1) {
        utag_data.bag_type = "single sku";
    } else {
        utag_data.bag_type = "not applicable";
    }

};
var discover_add_on = function () {
	var addon_present = 0;
	if ($('.product-container .addon-option').length ) {
			var selected_productaddon_array = [];
        	var selected_product;
        	$.each($('.product-container .addon-option'), function () {
			if(!$(this).hasClass('hide')){
				addon_present = 1;
					if ( $('.custom-checkbox').attr('aria-checked') == 'true' ){
						utag_data.product_add_on = "yes";
					}
					selected_product = $(this).find('label').text();
					selected_productaddon_array.push(selected_product);
				}
        	});
			var product_addonname = selected_productaddon_array.toString();
            utag_data.product_add_on_name = product_addonname;
			if(addon_present==0){
				utag_data.product_add_on = "not applicable";
				utag_data.product_add_on_name = "not applicable";
			}
			else if(!(utag_data.product_add_on == "yes")){
			utag_data.product_add_on = "no";
			}

        } else {
        	utag_data.product_add_on = "not applicable";
        	utag_data.product_add_on_name = "not applicable";
        }
};

var selected_add_on = function () {
    utag_data.doll_add_on_name = $('.addon-info-single span').text().trim();

    if ($('.add-to-bag-content input').length) {
        var selected_addOn_array = [];
        $.each($(".add-to-bag-content input[type='checkbox']:checked"), function () {
            var selected_checkbox = $(this).closest('.addon-option').find('.addon-info span').text();
            selected_addOn_array.push(selected_checkbox);
        });
        var selected_radio = $('input[type="radio"]:checked').closest('.addon-option-wrapper').find('.addon-title span').text();
        if (selected_radio != "") selected_addOn_array.push(selected_radio);
        utag_data.doll_add_on_name = selected_addOn_array.join(", ") || "not applicable";
	}
};

var updateDHViewEvent = function () {

   		    utag_data.page_type="doll hospital"
			utag_data.category_id=""
			utag_data.company_division="american girl doll hospital"
			utag_data.email_capture_location=getCookieValue('LIGHT_BOX_LOCATION') ? getCookieValue('LIGHT_BOX_LOCATION') : ''
			utag_data.paplatform=device().toLowerCase()
            utag_data.referring_url=document.referrer
			utag_data.site_section="doll hospital"
			utag_data.site_language="en"
            utag_data.site_currency="usd"
			utag_data.site_region="us"
			utag_data.site_type="commerce"
			utag_data.store_id="10651"
            utag_data.page_template_id=pageTemplate
            custAuthInfo();
           	visitor_status();
            cust_email_optin_status();
           	customer_type();
            loyalty_details();

}

var productFindingMethod = function () {
    var Referring_URL = document.referrer;
    var Current_URL = window.location.href;
    if (Referring_URL != undefined) {
        utag_data.product_finding_method = "Page navigation";
        if (Referring_URL.indexOf('SearchDisplay') >= 0) {
            utag_data.product_finding_method = "Onsite search";
        }
	}
	if(Current_URL.indexOf('?icid=') >= 0){
		utag_data.product_finding_method = "Internal promotion banner";
	}
	else if(Current_URL.indexOf('?cid=') >= 0){
		utag_data.product_finding_method = "External promotion";
	}
	else if(Current_URL.indexOf('rp_reflink=') >= 0){
		utag_data.product_finding_method = "Recommendation";
	}
	else if(Current_URL.indexOf('SearchDisplay') >= 0){
		utag_data.product_finding_method = "Onsite search";
    }
};
var setProductCategoryPLP = function () {
    var breadcrumb_length = $('.breadcrumb-nav li').length;
    if (breadcrumb_length == 2) {
        utag_data.product_category = $('.breadcrumb-nav li')[1].innerText;
        utag_data.product_subcategory = "";
    }
    if (breadcrumb_length > 2) {
        utag_data.product_category = $('.breadcrumb-nav li')[1].innerText;
        utag_data.product_subcategory = $('.breadcrumb-nav li')[2].innerText;
    }

    if ($('.productGrid .grid-lists').attr('data-snp-param') != undefined) {
        var snp_param = $('.productGrid .grid-lists').attr('data-snp-param');
        utag_data.product_character = "";
        if (snp_param.indexOf('character=') >= 0 && snp_param.indexOf(';')) {
            var snp_param_array = snp_param.split(';');
            for (var i = 0; i < snp_param_array.length; i++) {
                if (snp_param_array[i].indexOf('character=') >= 0) {
                    utag_data.product_character = snp_param_array[i].split('=')[1];
                }
            }
		}
		else if(snp_param.indexOf('character=') >= 0){
            utag_data.product_character = snp_param.split('=')[1];
        }
    }
};

var callOnLoadMethods = function () {
    loyalty_details();
    custAuthInfo();
    cust_email_optin_status();
    customer_type();

	if (pageTemplate == "ag-search-results-page") {
		setSearchResultsData();
	}
	if(document.cookie.indexOf("SEARCH_ANALYTICS_COOKIE")>=0){
		deleteCookieValue("SEARCH_ANALYTICS_COOKIE");
	}
	if (pageTemplate == "ecomm-template") {
        utag_data.site_type = site_type;
    }

	if (pageTemplate == "ecomm-plp-page") {
		var cat = document.querySelector('.grid-lists').dataset.snpParam ? document.querySelector('.grid-lists').dataset.snpParam.toLowerCase() : "";
		if (cat.indexOf('category=') != -1) {
			utag_data.category_id = (cat.substring(cat.indexOf("=") + 1, cat.length));
		} else {
			utag_data.category_id = cat
		}
		utag_data.site_type = site_type;
		setProductCategoryPLP();
	} else if (pageTemplate == "ecomm-pdp-page") {
		setPDPData();
	}

    var pageTypeVariable = utag_data.page_type || '';
    if(pageTypeVariable.indexOf("homepage") != -1){
            utag_data.site_type = "";
            utag_data.site_section = "homepage";
    }
    if(pageTypeVariable.indexOf("coppa") != -1){
            utag_data.site_type = "";
    }

 }

var gridScroll = function (params) {
    var config = {
        event_action: 'products scroll tracking',
        event_action_type: 'scroll',
        event_detail: params.categoryName, //'Product Category',
        event_detail_sub: "plp" + params.columnVal + ":" + params.viewedCnt + ":" + params.totalCnt,
        location_name: 'products scroll',
    };
    utag.link(config);
};

$(document).ready(function () {

    postReadyCalls();
    setPromoCodeList();
    $("body").on("click", ".quick-view", function () {
        if($(this).parents(".personalization_recommendation_products").length>0 && pageTemplate=="ecomm-plp-page"){
            localStorage.setItem("PRODUCT_BUY_OPTION", "personalization:recs");
        }
        else if($(this).parents(".personalization_recommendation_products").length>0 && pageTemplate=="ecomm-template"){
            localStorage.setItem("PRODUCT_BUY_OPTION", "personalization:justforyou:recs");
        }
        else if($(this).parents(".aem-recommend-item").length>0  && pageTemplate=="ecomm-pdp-page"){
            localStorage.setItem("PRODUCT_BUY_OPTION", "pdp:targetrecs");
        }
        else {
            localStorage.removeItem("PRODUCT_BUY_OPTION");
        }
    });
    // callOnLoadMethods();
    $("body").on("click", "a", function () {
        clearPreviousData();
        if ($(this).attr("data-track-left-nav-account")) {
            utag_data.event_detail = "account navigation";
            utag_data.location_name = "left navigation";
            utag_data.event_detail_sub = $(this).attr("data-track-left-nav-account").toLowerCase();
            commonClickEventFn();
        } else if ($(this).attr("data-track-left-nav-order")) {
            utag_data.event_detail = "account navigation";
            utag_data.location_name = "left navigation";
            commonClickEventFn();
            utag_data.event_detail_sub = $(this).attr("data-track-left-nav-order").toLowerCase();
        }else if ($(this).attr("data-track-order-history")){
            utag_data.event_detail = "account - orders - order details";
            utag_data.location_name = "order history";
            commonClickEventFn();
            utag_data.event_detail_sub = $(this).attr("data-track-order-history").toLowerCase();
        } else if ($(this).hasClass('create-certificate')) {
            utag_data.event_detail = "account - doll certificate";
            utag_data.event_detail_sub = "create certificate";
            utag_data.location_name = $("h1").text().toLowerCase();
            commonClickEventFn();
        } else if ($(this).attr("data-track-recent-order-history")) {
            var isOrderSumm;
            if ($(".my-order-summary").find("h2").text()) {
                isOrderSumm = " - " + $(".my-order-summary").find("h2").text();
            } else {
                isOrderSumm = " - orders - order details";
            }
            utag_data.event_detail = $("h1").text().toLowerCase() + isOrderSumm.toLowerCase();
            utag_data.event_detail_sub = $(this).attr("data-track-recent-order-history").toLowerCase();
            utag_data.location_name = "my account summary";
            commonClickEventFn();
        } else if ($(this).hasClass('view-recent-order')) {
            utag_data.event_detail = $("h1").text().toLowerCase() + " - " + $(".my-order-summary").find("h2").text().toLowerCase();
            utag_data.event_detail_sub = $(this).text();
            utag_data.location_name = "my account summary";
            commonClickEventFn();
        } else if ($(this).parent().hasClass('add-child-birth')) {
            utag_data.event_detail = "account - " + $("h1").text() + "-" + $(".add-child-birth").parent().find("h2").text().trim();
            utag_data.event_detail_sub = this.text.toLowerCase();
            utag_data.location_name = $("h1").text().toLowerCase();
            commonClickEventFn();
        } else if ($(this).hasClass('edit-default-address') || $(this).hasClass('edit-default-shippingmethod')) {
            if ($(this).hasClass('edit-default-address')) {
                setLinkCommonProp($(this).find('span').text(), $(".edit-default-address").text().replace($(".edit-default-address").find("span").text(), ""));
            } else if ($(this).hasClass('edit-default-shippingmethod')) {
                setLinkCommonProp($(this).find('span').text(), $(".edit-default-shippingmethod").text().replace($(".edit-default-shippingmethod").find("span").text(), ""));
            }
        } else if ($(this).hasClass('add-default-address') || $(this).hasClass('add-default-shippingmethod') || $(this).hasClass('add-default-payment')) {
            if ($(this).hasClass('add-default-address')) {
                setLinkCommonProp($(this).find('span').text(), $(".add-default-address").text().replace($(".add-default-address").find("span").text(), ""));
            } else if ($(this).hasClass('add-default-shippingmethod')) {
                setLinkCommonProp($(this).find('span').text(), $(".add-default-shippingmethod").text().replace($(".add-default-shippingmethod").find("span").text(), ""));
            } else if ($(this).hasClass('add-default-payment')) {
                setLinkCommonProp($(this).find('span').text(), $(".add-default-payment").text().replace($(".add-default-payment").find("span").text(), ""));
            }
        } else if ($(this).hasClass('edit-default-payment')) {
            if ($(this).hasClass('link-separator')) {
                utag_data.event_detail = "account - " + $("h1").text().toLowerCase();
                utag_data.event_detail_sub = "edit";
                utag_data.location_name = $("h1").text().toLowerCase();
                commonClickEventFn();
            } else {
                setLinkCommonProp($(this).find('span').text(), "edit");
            }
        } else if ($(this).hasClass("delete-cards default")) {
            utag_data.event_detail = "account - " + $("h1").text().toLowerCase();
            utag_data.event_detail_sub = "default card - " + this.text.toLowerCase();
            utag_data.location_name = $("h1").text().toLowerCase();
            commonClickEventFn();
        } else if ($(this).hasClass('agrewards-addnow')) {
            utag_data.event_detail = $("h1").text().toLowerCase() + " - " + $(this).find('span').text().toLowerCase();
            utag_data.event_detail_sub = $(".agrewards-addnow").text().replace($(".agrewards-addnow").children().text(), "");
            utag_data.location_name = $("h1").text().toLowerCase();
            commonClickEventFn();
        } else if ($(this).attr("data-edit")) {
            // edit personal info
            utag_data.event_detail = "account - " + $("h1").text().toLowerCase() + " - " + $(this).parent().find("h2").text().split(":")[0].toLowerCase();
            utag_data.event_detail_sub = this.text.split(" ")[0];
            utag_data.location_name = $("h1").text().toLowerCase();
            commonClickEventFn();
        } else if ($(this).attr("data-track-product-grid")) {
            utag_data.event_action = "plp - sorting";
            utag_data.event_action_type = "click";
            utag_data.event_detail = $(this).attr("data-track-product-grid");
            utag_data.event_detail_sub = $(this).text().trim().toLowerCase();
            utag_data.location_name = "right nav";
            utag.link(utag_data);
        }
    });
    $("footer").on("click", "a", function () {
            clearPreviousData();
            utag_data.event_action = "Footer Clicks"
            utag_data.event_action_type = "click"
            utag_data.event_detail = "Footer Links"
            utag_data.event_detail_sub = $(this).text();
            utag.link(utag_data);
     });
    $(".agrewards-addnow a").click(function () {
        clearPreviousData();
        utag_data.event_action = "click event"
        utag_data.event_action_type = "click"
        utag_data.event_detail = "account summary - add receipts and more"
        utag_data.event_detail_sub = "add now";
        utag_data.location_name = $("h1").text().toLowerCase()
        utag.link(utag_data);
    });
    /*Functions for PDP Analytics starts*/
    $(document).on("click", ".s7thumbcell", function () {
        clearPreviousData();
        var ImageVideoName = "";
        var ImageVideoIndex = "";
        if ($(this).children('div').children('div').attr('type') == 'image') {
            if ($(this).children('div').attr('style').indexOf('url')) {
                ImageVideoName = $(this).children('div').attr('style').split('url')[1].split('/').pop().split('?')[0] || "";
            }
            ImageVideoIndex = $(this).attr('aria-posinset') || "";
        }
        utag_data.event_action = "product image viewer carousel click";
            utag_data.event_action_type = "click";
            utag_data.event_detail = PDPProductName || "";
            utag_data.event_detail_sub = "p" + ImageVideoIndex + ":" + ImageVideoName;
            utag_data.location_name = "product image viewer carousel"
        utag.link(utag_data);
    });
    $(document).on("click", ".s7button", function () {
        clearPreviousData();
        utag_data.event_action = "product image viewer carousel navigation arrow click";
            utag_data.event_action_type = "click";
            utag_data.event_detail = PDPProductName || "";
            utag_data.event_detail_sub = ($(this).hasClass('s7scrollleftbutton')) ? 'left navigation' : 'right navigation';
            utag_data.location_name = "product image viewer carousel";
        utag.link(utag_data);
    });
    $(document).on("click", ".product-container .dropdown-menu li a", function () {
        clearPreviousData();
        var product_type_bundle = $('.product-info-wrapper').attr('data-producttype');
        var product_name = PDPProductName;
        if (product_type_bundle == 'BundleBean') {
            product_name = $(this).closest('.product-info-wrapper').find('.bundle-info h3').text();
        }
        utag_data.event_action = "check store availability";
            utag_data.event_action_type = "click";
            utag_data.event_detail = product_name;
            utag_data.event_detail_sub = $(this).text() || "";
            utag_data.location_name = "product details section";
        utag.link(utag_data);
    });
    $(document).on("click", ".at-share-btn-elements a", function () {
        clearPreviousData();
        utag_data.event_action = "social share click";
            utag_data.event_action_type = "click";
            utag_data.event_detail = PDPProductName || "";
            utag_data.event_detail_sub = $(this).find('title').text() || "";
            utag_data.location_name = "product details section";
        utag.link(utag_data);
    });
    $(".review-status a").click(function () {
        clearPreviousData();
        utag_data.event_action = ($(this).hasClass('read-reviews')) ? 'read reviews' : 'write reviews';
            utag_data.event_action_type = "click";
            utag_data.event_detail = PDPProductName || "";
            utag_data.event_detail_sub = $('.product-info-wrapper').attr('data-partnumber') || "";
            utag_data.location_name = "product details section";
        utag.link(utag_data);
    });

    $(".size-chart").click(function () {
        clearPreviousData();
        utag_data.event_action = "size chart click";
            utag_data.event_action_type = "click";
            utag_data.event_detail = PDPProductName || "";
            utag_data.event_detail_sub = "";
            utag_data.location_name = "product grid section";
        utag.link(utag_data);
    });

    $(".accordion-section").click(function () {
        clearPreviousData();
        utag_data.event_action = ($(this).hasClass('active')) ? 'product accordion collapse' : 'product accordion expand';
            utag_data.event_action_type = "click";
            utag_data.event_detail = PDPProductName || "";
            utag_data.event_detail_sub = $(this).find('.accordion-title h3').text() || "";
            utag_data.location_name = "product accordion section";
        utag.link(utag_data);
    });

    $(document).on("click", ".modal-content .addon-button-container button", function () {
        clearPreviousData();
        utag_data.event_action = "doll added to bag";
            utag_data.event_action_type = "click";
            utag_data.event_detail = product_name || "";
            utag_data.event_detail_sub = product_sku || "";
            utag_data.location_name = "doll add on section"
        if ($(this).hasClass('close-product-addOns')) {
            utag_data.doll_add_on = "no thanks";
                utag_data.doll_add_on_name = "not applicable";
		}
		else {
            utag_data.doll_add_on = "yes";
            selected_add_on();
        }
        utag.link(utag_data);
    });

    $(document).on("click", ".minicart_analytics_service", function () {
        setUtagDataCommon();
        utag_data.event_action = "mini cart -confirmation modal";
        utag_data.event_action_type = "prompt";
        bag_type_value();
        productFindingMethod();
        if(quickViewFlag){
            utag_data.product_category = quickViewProdCategory;
            utag_data.product_character = quickViewProdCharacter;
            quickViewFlag = false;
        }
        utag.link(utag_data);
    });

    $(document).on("click", ".shoppingBagAnalytics", function () {
        setUtagDataCommon();
        utag_data.event_action = "mini cart shopping bag";
        utag_data.event_action_type = "click";
        bag_type_value();
        productFindingMethod();
        utag.link(utag_data);
    });

	$(document).on("click", "#popularSearch li", function () {
		if(document.cookie.indexOf('SEARCH_ANALYTICS_COOKIE')<0){
			setCookieValue("SEARCH_ANALYTICS_COOKIE","PRODUCT");
		}
	});

    $(document).on("click", "#recommended-products .aem-recommend-item a", function() {
        var personalize = $(this).parents('.personalization_recommendation_products').attr('class');
        var parent_variable = $(this).closest('.owl-item');
        var product_number = 0;
        $('.owl-stage .active').each(function() {
            $(this).attr('data-product-number', product_number);
            product_number++;
        });
        if (!($(this).hasClass('quick-view'))) {
            clearPreviousData();
            if (personalize) {
                utag_data.product_name = $(this).parent().find('a div.item-desc').text();
                utag_data.event_action = "product info"
                utag_data.event_action_type = "click"
                utag_data.event_detail = 'personalization_recommendation_products';
                utag_data.personalization_strategy = $('.personalization_recommendation_products').find('h2').text();
                utag_data.product_status = '';
                utag_data.product_position = $(this).parent().attr('data-slick-index');
                utag_data.product_subcategory = '';
                utag_data.event_detail_sub = $(this).parent().find('.product-price').attr('data-actual-price');
                utag_data.location_name = "personalization_product_carousel";
            } else {
                utag_data.event_action = "recommended products click"
                utag_data.event_action_type = "click";
                utag_data.event_detail = parent_variable.find('.item-desc').text().trim();
                utag_data.event_detail_sub = parent_variable.find('.sale-price').text().trim();
                utag_data.location_name = "recommended products section";
                utag_data.product_position = parent_variable.attr('data-product-number');
            }
            utag.link(utag_data);
        }
    });

    $(document).on("click", ".pdpproduct .product-container .btn-add-to-bag, .giftCard-container .btn-add-to-bag", function () {
        clearPreviousData();
		if(!($('.childErrMsg').is(':visible') || $('.logo-giftCard-container .form-message').is(':visible'))){
			var current_variable = $(this);
			product_sku = $('.product-info-wrapper').attr('data-partnumber');
			if (current_variable.closest('.product-container').length) {
				product_name = PDPProductName;
				utag_data.event_detail = product_name || "";
					utag_data.affirm_eligibility = ($('.product-container .bag__affirm').text().length && $('.product-container [class="bag__affirm hide"]').length == 0) ? "N" : "Y";
					utag_data.affirm_message = utag_data.affirm_eligibility == "N" ? $('.product-container .bag__affirm').text().trim() : "not-available";
			}
			if (current_variable.closest('.giftCard-container').length) {
				product_name = $('.logo-giftCard-container h1').text();
				utag_data.event_detail = product_name || ""
			}
			if(pdpProductType == "BundleBean"){
				getProductSkuDetails();
			}
				utag_data.event_action = "add to bag";
				utag_data.event_action_type = "click";
				utag_data.event_detail_sub = product_sku || "";
				utag_data.location_name = "product details section";
				utag_data.product_age = $('.age-specification span').text() || "not applicable";
				utag_data.product_price = $('.product-price .offer_price').text() || "";
				utag_data.product_average_rating = $('.rating-section span').text() || "not applicable";
				utag_data.products_total = $('#minishopcart_total').text() || "";
				utag_data.product_availability = $('.inventory-status-message').text() || "Available";
				utag_data.product_reviews_count = $('.review-status a span').text() || "not applicable";
				utag_data.product_variant = ($('.product-container .swatch-available').length) ? 'variant available' : 'not applicable';
				utag_data.product_list_price = $('.product-price .offer_price').text() || "not applicable";
				utag_data.product_original_price = $('.product-price .offer_price').text() || "not applicable";
				utag_data.product_unit_price = $('.product-price .current_price').text() || "not applicable";
				utag_data.product_discount = $('.price-saved .discount_price').text() || "not applicable";
				utag_data.product_color = $('.product-specification .product-value').text() || "not applicable";
				utag_data.product_size = $('.size-selection-preference .active a span').text() || "not appicable";
				utag_data.bundle_type = $('.product-info-wrapper').attr('data-producttype') || "";
			bag_type_value();
			productFindingMethod();
			discover_add_on();
			utag.link(utag_data);
		}
    });

    $(document).on("click", ".bag__affirm a", function () {
        clearPreviousData();
        utag_data.event_action = "affirm tracking";
            utag_data.event_action_type = "click";
            utag_data.event_detail = PDPProductName || "";
            utag_data.event_detail_sub = $(this).text();
            utag_data.location_name = "product details section";
            utag_data.affirm_message = $('.product-container .bag__affirm').text().trim() || "not-available";
        utag.link(utag_data);
    });

    $(document).on("click", ".categoryBanner .plp-banner-sec-expand a", function () {
    	var current_variable= $(this);
      if (!current_variable.closest('.ecomm-modal').length && !current_variable.hasClass('arrow-btn')) {
      clearPreviousData();
      utag_data.event_action = "category banner";
            utag_data.event_action_type = "click";
            utag_data.event_detail = "";
            utag_data.event_detail_sub = current_variable.text().trim();
            utag_data.location_name = "banner content";
            utag.link(utag_data);
   		}
    });

    $(document).on("click", ".productGrid .grid-lists li", function () {
		clearPreviousData();
		var current_variable = $(this);
		utag_data.event_action_type = "click";
		utag_data.product_position = current_variable.index();
		utag_data.event_detail = current_variable.find('.grid-title').text();
		utag_data.event_detail_sub = current_variable.find('.product-price span').eq(0).text().trim();
		if (current_variable.hasClass("espot-grid")) {
			utag_data.event_action = "in-line promo";
			utag_data.event_detail = current_variable.find("img").attr("alt").toLowerCase();
			utag_data.event_detail_sub = current_variable.find("img").attr("src").toLowerCase();
			utag_data.location_name = "product grid";
			utag.link(utag_data);
			return;
		}
		else if(current_variable.closest(".ecomm-search-results-page").length){
			utag_data.event_action = "search results click through";
			utag_data.location_name = "search results page";
			utag.link(utag_data);
		}
		utag_data.event_action = "product info";
		utag_data.location_name = "product grid";
		utag.link(utag_data);
	});

    $(document).on("click", ".ecomm-search-results-page .grid-container .grid-articles li .article-title,.ecomm-search-results-page .grid-container .grid-articles li .article-read-more a,			.ecomm-search-results-page .grid-container .grid-articles li .product-image", function () {
        clearPreviousData();
        var parent_variable = $(this).closest('li');
        utag_data.event_action = "search results click through";
            utag_data.event_action_type = "click";
            utag_data.event_detail = parent_variable.find('.article-title').text();
            utag_data.event_detail_sub = parent_variable.find('.article-read-more a').attr('href');
            utag_data.location_name = "search results page";
            utag_data.product_position = parent_variable.index();
        utag.link(utag_data);
    });

    $(document).on("click", ".ecomm-search-results-page .social-icons-container .social-icons li", function () {
        clearPreviousData();
        var current_variable = $(this);
        utag_data.event_action = "social share click";
            utag_data.event_action_type = "click";
            utag_data.event_detail = current_variable.parents('.article-info').find('.article-title').text().trim();
            utag_data.event_detail_sub = current_variable.children('a').attr('data-link-attr');
            utag_data.location_name = "search results page";
        utag.link(utag_data);
    });

    $(document).on("click", ".ecomm-search-results-page .productGrid #see-more", function () {
        clearPreviousData();
        utag_data.event_action = "search results page - see more click";
            utag_data.event_action_type = "click";
            utag_data.event_detail = "see more";
            utag_data.event_detail_sub = "see more";
            utag_data.location_name = "search results page";
        utag.link(utag_data);
    })

    $(".accordion-video-container").bind('play', function () {
        clearPreviousData();
        utag_data.event_detail_sub = "play";
        setAccordionUtagDetails(this);
    });

    $(".accordion-video-container").bind('pause', function () {
        clearPreviousData();
        utag_data.event_detail_sub = "pause";
        setAccordionUtagDetails(this);
    });
    $(document).on("click", ".modal-body .product-container .btn-add-to-bag", function () {
        clearPreviousData();
        var current_variable = $(this);
        product_name = $('.product-container h2').text().trim();
        product_sku = current_variable.attr('data-parentpartnumber');

        var age = $('.age-specification').text();
        if ($('.age-specification').text().indexOf(':') != -1) {
            age = $('.age-specification').text().split(':')[1].trim();
        }
        if($(this).parents("#quickViewModal").length) {
            quickViewFlag = true;
            quickViewProdCategory = $("#quickViewModal .product-info-wrapper").attr('data-agcategory') || "";
            quickViewProdCharacter = $("#quickViewModal .product-info-wrapper").attr('data-product-character') || "";
            utag_data.product_category = $("#quickViewModal .product-info-wrapper").attr('data-agcategory') || "";
            utag_data.product_character = $("#quickViewModal .product-info-wrapper").attr('data-product-character') || "";
        }
        if(!!localStorage.getItem("PRODUCT_BUY_OPTION") && $(this).parents("#quickViewModal").length>0){
            utag_data.product_buying_method=localStorage.getItem("PRODUCT_BUY_OPTION") ? localStorage.getItem("PRODUCT_BUY_OPTION") : '';
            localStorage.removeItem("PRODUCT_BUY_OPTION");
        }
        utag_data.affirm_eligibility = ($('.product-container .bag__affirm').text().length && $('.product-container [class="bag__affirm hide"]').length == 0) ? "N" : "Y";
        utag_data.affirm_message = utag_data.affirm_eligibility == "N" ?  $('.product-container .bag__affirm').text().trim() : "not-available";
        utag_data.event_action = "add to bag";
        utag_data.event_action_type = "click";
        utag_data.event_detail = product_name || "";
        utag_data.event_detail_sub = product_sku || "";
        setProductUtagDetails(age);
        utag_data.bundle_type = current_variable.attr('data-producttype') || "";
        bag_type_value();
        productFindingMethod();
        discover_add_on();
        utag.link(utag_data);
    });

	/*Function for PDP Analytics Ends*/

	 /*Function for Gift Trunk PDP Analytic - Start*/
     $("body").on('click',".banner-btn .btn-oval",function () {
     	  clearPreviousData();
          utag_data.tool_type = "ag gift trunk - doll selection"
          utag_data.tool_stage = "step1-doll pre-selection"
          var doll_name = localStorage.getItem('gt-product-doll-name');
          var doll_age = getCookieValue("gt-product-age");
          utag_data.doll_name_detail = "DOLL:"+doll_name+",AGE:"+doll_age;
          utag.link(utag_data);
                              });

    /*Function for Gift Trunk PDP Analytic - End*/

	/*Functions for Gift Trunk Quiz - Start*/

	 $(".return_profinity_success").click(function () {

	         var dataBinderName = $(this).closest('.gt-quiz-input-holder').attr('data-binder-name');

	         if(dataBinderName == "gt-quiz-display-recipient"){
	        	clearPreviousData();
	            utag_data.event_action = "ag gift trunk - quiz steps"
	            utag_data.tool_type = "ag gift trunk - quiz"
	            utag_data.tool_stage = "continue-1"
	            utag_data.event_detail = "to:"
	            utag_data.event_detail_sub = "continue-1"
	            // utag_data.referring_url = "https://www.americangirl.com/";
	            utag.link(utag_data);
	         }else if(dataBinderName == "gt-quiz-display-giver"){
	        	clearPreviousData();
	            utag_data.event_action = "ag gift trunk - quiz steps"
	            utag_data.tool_type = "ag gift trunk - quiz"
	            utag_data.tool_stage = "continue-2"
	            utag_data.event_detail = "from:"
	            utag_data.event_detail_sub = "continue-2"
	            // utag_data.referring_url = "https://www.americangirl.com/";
	            utag.link(utag_data);
	         }
         });

	var occasionScreen = $("form").find("[data-binder-name='gt-quiz-display-occassion']")[0];
	var occasionScreenButton = $(occasionScreen).next();
	$(occasionScreenButton).click(function () {
		clearPreviousData();
		utag_data.event_action = "ag gift trunk - quiz steps"
		utag_data.tool_type = "ag gift trunk - quiz"
		utag_data.tool_stage = "continue-3"
		utag_data.event_detail = "what's the occasion?"
		var favorite = [];
		$.each($("input[name='quizRdoOccassion']:checked"), function () {
			var label=$(this).next('label').text().trim();
            favorite.push(label);
		});
		utag_data.event_detail_sub = favorite.join(",")
		// utag_data.referring_url = "https://www.americangirl.com/";
		utag.link(utag_data);

	});

	var displayAttributeScreen = $("form").find("[data-binder-name='gt-quiz-display-attr']")[0];
	var displayAttributeScreenButton = $(displayAttributeScreen).next();
	$(displayAttributeScreenButton).click(function () {
		clearPreviousData();
		utag_data.event_action = "ag gift trunk - quiz steps"
		utag_data.tool_type = "ag gift trunk - quiz"
		utag_data.tool_stage = "continue-4"
		utag_data.event_detail = "what is user like?"
		var favorite = [];
		$.each($("input[name='quizChkAttributes']:checked"), function () {
			var label=$(this).next('label').text().trim();
            favorite.push(label);
		});
		utag_data.event_detail_sub = favorite.join(",")
		// utag_data.referring_url = "https://www.americangirl.com/";
		utag.link(utag_data);
	});

	var displayAspireScreen = $("form").find("[data-binder-name='gt-quiz-display-aspire']")[0];
	var displayAspireScreenButton = $(displayAspireScreen).next();
	$(displayAspireScreenButton).click(function () {
		clearPreviousData();
		utag_data.event_action = "ag gift trunk - quiz steps"
		utag_data.tool_type = "ag gift trunk - quiz"
		utag_data.tool_stage = "continue-5"
		utag_data.event_detail = "what do you wish for user?"
		var favorite = [];
		$.each($("input[name='quizChkAspiration']:checked"), function () {
			var label=$(this).next('label').text().trim();
            favorite.push(label);
		});
		utag_data.event_detail_sub = favorite.join(",")
		// utag_data.referring_url = "https://www.americangirl.com/";
		utag.link(utag_data);
	});

	var yesContinue = $('#quizSummary').find('.gt-btn');
	$(yesContinue).click(function () {
		clearPreviousData();
		utag_data.event_action = "ag gift trunk - quiz steps"
		utag_data.tool_type = "ag gift trunk - quiz"
		utag_data.tool_stage = "final-complete"
		utag_data.event_detail = "let's review your reponses"
		utag_data.event_detail_sub = "is this correct?:yes,continue"
		// utag_data.referring_url = "https://www.americangirl.com/";
		utag.link(utag_data);
	});

	var NoStartOver = $('#quizSummary').find('.gt-link');
	$(NoStartOver).click(function () {
		clearPreviousData();
		utag_data.event_action = "ag gift trunk - quiz steps"
		utag_data.tool_type = "ag gift trunk - quiz"
		utag_data.tool_stage = "re-start"
		utag_data.event_detail = "let's review your reponses"
		utag_data.event_detail_sub = "is this correct?:no,start over"
		// utag_data.referring_url = "https://www.americangirl.com/";
		utag.link(utag_data);
	});

	$("body").on('click',".gt-quiz-block .gt-btn.online-purchase",function () {
        clearPreviousData();
	        utag_data.event_action = "ag gift trunk - quiz steps"
	        utag_data.tool_type = "ag gift trunk - quiz"
	        utag_data.tool_stage = "start"
	        utag_data.event_detail = "ready to customize a gift experience to remember?"
	        utag_data.event_detail_sub = "start now"
	       // utag_data.referring_url = "https://www.americangirl.com/";
	        utag.link(utag_data);
     });

	var count=0;
	var flag = 0;

	$(".gt-quiz-input-holder input").keyup(function(){
	         count = count +1 ;
	    if($(this).val().length == 0){
	      count =0;
	    }
	        var dataBinderName = $(this).closest('.gt-quiz-input-holder').attr('data-binder-name');
	        var maxLength = $(this).attr('maxlength');
	        var error_msg = $(this).parent().children('small').eq(0).text();
	        var error_msg1 = $(this).parent().children('small').eq(2).text();
	        var quizRegex = new RegExp("^[A-Za-z + & ' -]+$");
	    if(dataBinderName == "gt-quiz-display-recipient" && $(this).val() != "" && count<=maxLength){


	      if($(this).val().length < maxLength  && count == 1){
	                if(!quizRegex.test($(this).val().trim())) {
	                utag_data.event_action ="ag gift trunk - quiz steps"
	                utag_data.tool_type ="ag gift trunk - quiz"
	                utag_data.tool_stage = "error message"
	                utag_data.event_detail ="to:"
	                utag_data.event_detail_sub = error_msg
	               // utag_data.referring_url = "https://www.americangirl.com/";
	                utag.link(utag_data);
	              }
	        }
	        else if(count>=maxLength){
	                utag_data.event_action ="ag gift trunk - quiz steps"
	                utag_data.tool_type ="ag gift trunk - quiz"
	                utag_data.tool_stage = "error message"
	                utag_data.event_detail ="to:"
	                if(!quizRegex.test($(this).val().trim())){
	                    utag_data.event_detail_sub = error_msg + " " + error_msg1
	                }
	              else{
	                    utag_data.event_detail_sub = error_msg1
	                            }
	                // utag_data.referring_url = "https://www.americangirl.com/";
	                utag.link(utag_data);
	        }


	    }else if(dataBinderName == "gt-quiz-display-giver" && $(this).val() != "" && count<=maxLength){
	        if(flag==0){
			count=1;
	        flag=1;
	        }

	         if($(this).val().length < maxLength && count == 1){
	                if(!quizRegex.test($(this).val().trim())) {
	                utag_data.event_action ="ag gift trunk - quiz steps"
	                utag_data.tool_type ="ag gift trunk - quiz"
	                utag_data.tool_stage = "error message"
	                utag_data.event_detail ="from:"
	                utag_data.event_detail_sub = error_msg
	              // utag_data.referring_url = "https://www.americangirl.com/";
	                utag.link(utag_data);
	              }
	        }
	        else if(count>=maxLength){
	                utag_data.event_action ="ag gift trunk - quiz steps"
	                utag_data.tool_type ="ag gift trunk - quiz"
	                utag_data.tool_stage = "error message"
	                utag_data.event_detail ="from:"
	                if(!quizRegex.test($(this).val().trim())){
	                    utag_data.event_detail_sub = error_msg + " " + error_msg1
	                   }
	               else{
	                    utag_data.event_detail_sub = error_msg1
	                   }
	                // utag_data.referring_url = "https://www.americangirl.com/";
	                utag.link(utag_data);
	        }

	       }

	    });


	  $(".return_profinity_error").click(function () {
	      var dataBinderName = $(this).closest('.gt-quiz-input-holder').attr('data-binder-name');
	     var profinityError = $(".gt-quiz-input-holder input").parent().children('small').eq(1).text();
	 if(dataBinderName == "gt-quiz-display-recipient"){
	    utag_data.event_action ="ag gift trunk - quiz steps"
	    utag_data.tool_type ="ag gift trunk - quiz"
	    utag_data.tool_stage = "error message"
	    utag_data.event_detail ="to:"
	    utag_data.event_detail_sub = profinityError
	   // utag_data.referring_url = "https://www.americangirl.com/";
	    utag.link(utag_data);
	 }else if(dataBinderName == "gt-quiz-display-giver"){
	    utag_data.event_action ="ag gift trunk - quiz steps"
	    utag_data.tool_type ="ag gift trunk - quiz"
	    utag_data.tool_stage = "error message"
	    utag_data.event_detail ="from:"
	    utag_data.event_detail_sub = profinityError
	   // utag_data.referring_url = "https://www.americangirl.com/";
	    utag.link(utag_data);
	 }
	});

	/*Functions for Gift Trunk Quiz - End*/
	  /*Functions for Gift Trunk Letter - Start*/

      $("body").on('click',".gt-bundle-selection-wrapper .ltr-btn-wrapper #openModal",function () {
                 clearPreviousData();
                 utag_data.event_action = "ag gift trunk - letter"
                 utag_data.tool_type = "ag gift trunk - letter"
                 utag_data.tool_stage = "edit letter"
                 utag_data.event_detail = "your letter to"
                 utag_data.event_detail_sub = "edit letter"
                // utag_data.referring_url = "https://www.americangirl.com/";
                 utag.link(utag_data);
       });

      $("body").on('click',".gt-bundle-selection-wrapper .ltr-btn-wrapper #submitLetter",function () {
                 clearPreviousData();
                 utag_data.event_action = "ag gift trunk - letter"
                 utag_data.tool_type = "ag gift trunk - letter"
                 utag_data.tool_stage = "accept letter-2"
                 utag_data.event_detail = "your letter to"
                 utag_data.event_detail_sub = "accept letter"
                 // utag_data.referring_url = "https://www.americangirl.com/";
                 utag.link(utag_data);
        });

      $("body").on('click',".gt-bundle-selection-wrapper .modal-footer #acceptBtn",function () {
                 clearPreviousData();
                 utag_data.event_action = "ag gift trunk - letter steps"
                 utag_data.tool_type = "ag gift trunk - letter"
                 utag_data.tool_stage = "accept letter-1"
                 utag_data.event_detail = "edit letter - pop up"
                 utag_data.event_detail_sub = "accept letter"
                // utag_data.referring_url = "https://www.americangirl.com/";
                 utag.link(utag_data);
        });

 /*Functions for Gift Trunk Letter - End*/
    /*Functions for doll recommendation and truly me - Start*/
         $(document).on("click", "#dollrecommendList .product-tile", function () {

           clearPreviousData();
           var current_variable = $(this);
            utag_data.event_action = "ag gift trunk - doll selection steps"
            utag_data.tool_type = "ag gift trunk - doll selection"
            utag_data.tool_stage = "step1-doll selection"
            utag_data.event_detail = "DOLL:"+current_variable.find('.product-title').text().trim()+",AGE:"+current_variable.find('.product-age-range').text().trim();
            utag_data.event_detail_sub = current_variable.find('.gt-btn').text().split(' ')[0];
           // utag_data.referring_url = "https://www.americangirl.com/";
            utag.link(utag_data);
          });

        $(".gtDollSelection .view-all-products").click(function () {
           clearPreviousData();
           utag_data.event_action = "ag gift trunk - doll selection steps"
           utag_data.tool_type = "ag gift trunk - doll selection"
           utag_data.tool_stage = "step1-doll selection"
           utag_data.event_detail = "actions performed"
           utag_data.event_detail_sub = "view all"
           // utag_data.referring_url = "https://www.americangirl.com/";
           utag.link(utag_data);
          });


        $(".gtDollSelection .show-less-products").click(function () {
           clearPreviousData();
           utag_data.event_action = "ag gift trunk - doll selection steps"
           utag_data.tool_type = "ag gift trunk - doll selection"
           utag_data.tool_stage = "step1-doll selection"
           utag_data.event_detail = "actions performed"
           utag_data.event_detail_sub = "show recommendations"
           // utag_data.referring_url = "https://www.americangirl.com/";
           utag.link(utag_data);
        });

        $(document).on("click", "#trulyMeDollList .product-card", function () {

            clearPreviousData();
            var current_variable = $(this);
            utag_data.event_action = "ag gift trunk - doll selection steps"
            utag_data.tool_type = "ag gift trunk - doll selection"
            utag_data.tool_stage = "step1-doll options"
            var dollname = current_variable.find('.product-title').text();
            var eyes = current_variable.find('.product-eye-color').text();
            var hair = current_variable.find('.product-hair-type').text();
            utag_data.event_detail = "DOLL:"+dollname+",EYES:"+eyes+",HAIR:"+hair;
            utag_data.event_detail_sub = "select"
            // utag_data.referring_url = "https://www.americangirl.com/";
            utag.link(utag_data);
          });

    /*functions for doll recommendation and truly me - ENDS */

   /*Functions for Bundle Selection - Start*/

        $("body").on('click',"#smallTrunk",function () {
           clearPreviousData();
           var currentVar =$("#smallTrunk").find('.trunk-heading').text();
           var dollName = localStorage.getItem('gt-product-doll-name');
           var dollAge = getCookieValue("gt-product-age");
           var dollEyeColor = getCookieValue("gt-product-eye-color");
           var dollHairColor = getCookieValue("gt-product- hair-type");
          utag_data.event_action = "ag gift trunk - doll selection steps"
          utag_data.tool_type = "ag gift trunk - doll selection"
          utag_data.tool_stage = "step2 - trunk selection"
          if(dollAge != undefined && dollAge != ""){
           utag_data.event_detail = "DOLL:"+dollName+",AGE:"+dollAge;
          }else{
          utag_data.event_detail = "DOLL:"+dollName+",EYE:"+dollEyeColor+",HAIR:"+dollHairColor;
          }
          utag_data.event_detail_sub = currentVar;
          utag_data.trunk_range = $(this).find('.trunk-pricerange').text();
          // utag_data.referring_url = "https://www.americangirl.com/";
          utag.link(utag_data);
         });


        $("body").on('click',"#largeTrunk",function () {
        clearPreviousData();
        var dollName = localStorage.getItem('gt-product-doll-name');
        var dollAge = getCookieValue("gt-product-age");
        var dollEyeColor = getCookieValue("gt-product-eye-color");
        var dollHairColor = getCookieValue("gt-product- hair-type");
        var currentVar =$("#largeTrunk").find('.trunk-heading').text();
        utag_data.event_action = "ag gift trunk - doll selection steps"
        utag_data.tool_type = "ag gift trunk - doll selection"
        utag_data.tool_stage = "step2 - trunk selection"
        if(dollAge != undefined && dollAge != ""){
        utag_data.event_detail = "DOLL:"+dollName+",AGE:"+dollAge;
        }else{
        utag_data.event_detail = "DOLL:"+dollName+",EYE:"+dollEyeColor+",HAIR:"+dollHairColor;
        }
        utag_data.event_detail_sub = currentVar;
        utag_data.trunk_range = $(this).find('.trunk-large-pricerange').text();
        // utag_data.referring_url = "https://www.americangirl.com/";
        utag.link(utag_data);
        });

        $("#categoryBundle1 #categoryBundleList1").click(function () {
        clearPreviousData();
        var dollName = localStorage.getItem('gt-product-doll-name');
        var dollAge = getCookieValue("gt-product-age");
        var dollEyeColor = getCookieValue("gt-product-eye-color");
        var dollHairColor = getCookieValue("gt-product- hair-type");
        utag_data.event_action = "ag gift trunk - doll selection steps"
        utag_data.tool_type = "ag gift trunk - doll selection"
        utag_data.tool_stage = "step3 - play pack selection"
        if(dollAge != undefined && dollAge != ""){
        utag_data.event_detail = "DOLL:"+dollName+",AGE:"+dollAge;
        }else{
        utag_data.event_detail = "DOLL:"+dollName+",EYE:"+dollEyeColor+",HAIR:"+dollHairColor;
        }
        utag_data.event_detail_sub = "play pack 1:"+$(this).find('.choice').parents(".option").find(".selected .name").text();
        var currentVar =$('.trunk .offerSize .name').text();
        utag_data.trunk_size = currentVar;
            // utag_data.referring_url = "https://www.americangirl.com/";
            utag.link(utag_data);
        });

        $("#categoryBundle2 #categoryBundleList2").click(function () {
            clearPreviousData();
            var dollName = localStorage.getItem('gt-product-doll-name');
            var dollAge = getCookieValue("gt-product-age");
            var dollEyeColor = getCookieValue("gt-product-eye-color");
            var dollHairColor = getCookieValue("gt-product- hair-type");
            utag_data.event_action = "ag gift trunk - doll selection steps"
            utag_data.tool_type = "ag gift trunk - doll selection"
            utag_data.tool_stage = "step3 - play pack selection"
            if(dollAge != undefined && dollAge != ""){
            utag_data.event_detail = "DOLL:"+dollName+",AGE:"+dollAge;
            }else{
            utag_data.event_detail = "DOLL:"+dollName+",EYE:"+dollEyeColor+",HAIR:"+dollHairColor;
            }
            utag_data.event_detail_sub = "play pack 2:"+$(this).find('.choice').parents(".option").find(".selected .name").text();
            var currentVar = $('.trunk .offerSize .name').text();
            utag_data.trunk_size = currentVar;
          //  utag_data.referring_url = "https://www.americangirl.com/";
            utag.link(utag_data);
        });

       $(".trunk .view-all-products").click(function () {
	       clearPreviousData();
	       utag_data.event_action = "ag gift trunk - doll selection steps"
	       utag_data.tool_type = "ag gift trunk - doll selection"
	       utag_data.tool_stage = "step3 - play pack selection"
	       utag_data.event_detail = "actions performed"
	       utag_data.event_detail_sub = "view all"
	      // utag_data.referring_url = "https://www.americangirl.com/";
	       utag.link(utag_data);
     });

       $(".trunk .show-less-products").click(function () {
	       clearPreviousData();
	       utag_data.event_action = "ag gift trunk - doll selection steps"
	       utag_data.tool_type = "ag gift trunk - doll selection"
	       utag_data.tool_stage = "step3 - play pack selection"
	       utag_data.event_detail = "actions performed"
	       utag_data.event_detail_sub = "show recommendations"
	      // utag_data.referring_url = "https://www.americangirl.com/";
	       utag.link(utag_data);
     });


       $("body").on('click',"#earPiercing .choice",function () {
	        clearPreviousData();
	        var dollName = localStorage.getItem('gt-product-doll-name');
	        var dollAge = getCookieValue("gt-product-age");
	        var dollEyeColor = getCookieValue("gt-product-eye-color");
	        var dollHairColor = getCookieValue("gt-product- hair-type");
	        var data_choice_value = $(this).attr('data-choice');
            var data_value = $(this).parents('.option-details').find('h2').text().trim();
            var data_value_yes = data_value+": Yes";
            var data_value_no = data_value+": No";
	        if(data_choice_value == data_value_yes){
	        utag_data.event_action = "ag gift trunk - doll selection steps"
	        utag_data.tool_type = "ag gift trunk - doll selection"
	        utag_data.tool_stage = "step4 - ear piercing selection"
	           if(dollAge != undefined && dollAge != ""){
	             utag_data.event_detail = "DOLL:"+dollName+",AGE:"+dollAge;
	           }else{
	             utag_data.event_detail = "DOLL:"+dollName+",EYE:"+dollEyeColor+",HAIR:"+dollHairColor;
	            }
	        utag_data.event_detail_sub = data_choice_value;
	        utag_data.include_for = $(this).filter("[data-choice='" +data_value_yes+"']").find('.trunk-heading').text();
	       // utag_data.referring_url = "https://www.americangirl.com/";
	        utag.link(utag_data);
	        }else if(data_choice_value == data_value_no){
	        utag_data.event_action = "ag gift trunk - doll selection steps"
	        utag_data.tool_type = "ag gift trunk - doll selection"
	        utag_data.tool_stage = "step4 - ear piercing selection"
	        utag_data.event_detail = "actions performed";
	        utag_data.event_detail_sub = $(this).filter("[data-choice='" +data_value_no+"']").find('.trunk-heading').text();
	       // utag_data.referring_url = "https://www.americangirl.com/";
	        utag.link(utag_data);
           }
       });

       $("body").on('click',"#hearingAid .choice",function () {
	        clearPreviousData();
	        var dollName = localStorage.getItem('gt-product-doll-name');
	        var dollAge = getCookieValue("gt-product-age");
	        var dollEyeColor = getCookieValue("gt-product-eye-color");
	        var dollHairColor = getCookieValue("gt-product- hair-type");
	        var data_choice_value = $(this).attr('data-choice');
			var data_value = $(this).parents('.option-details').find('h2').text().trim();
            var data_value_yes = data_value+": Yes";
            var data_value_no = data_value+": No";
	        if(data_choice_value == data_value_yes){
	        utag_data.event_action = "ag gift trunk - doll selection steps"
	        utag_data.tool_type = "ag gift trunk - doll selection"
	        utag_data.tool_stage = "step4 - hearing aid selection"
	        if(dollAge != undefined && dollAge != ""){
	        utag_data.event_detail = "DOLL:"+dollName+",AGE:"+dollAge;
	        }else{
	        utag_data.event_detail = "DOLL:"+dollName+",EYE:"+dollEyeColor+",HAIR:"+dollHairColor;
	        }
	        utag_data.event_detail_sub = data_choice_value
	        var input_radio = $("input[name='hearingAidOption']:checked").next();
	        var label_radio = input_radio.text().trim();
	        utag_data.hearing_aid = label_radio;
	        utag_data.include_for =  $(this).filter("[data-choice='" +data_value_yes+"']").find('.trunk-heading').text();
	       // utag_data.referring_url = "https://www.americangirl.com/";
	        utag.link(utag_data);
	         }else if(data_choice_value == data_value_no){
	        utag_data.event_action = "ag gift trunk - doll selection steps"
	        utag_data.tool_type = "ag gift trunk - doll selection"
	        utag_data.tool_stage = "step4 - hearing aid selection"
	        utag_data.event_detail = "actions performed";
	        utag_data.event_detail_sub = $(this).filter("[data-choice='" +data_value_no+"']").find('.trunk-heading').text();
	      //  utag_data.referring_url = "https://www.americangirl.com/";
	        utag.link(utag_data);
      }
  });

        $("body").on('click',".trunk .selected .change",function () {
            clearPreviousData();
            utag_data.event_action = "ag gift trunk - doll selection steps"
            utag_data.tool_type = "ag gift trunk - doll selection"
            utag_data.tool_stage = "step5 - doll selection summary"
            utag_data.event_detail = $(this).siblings('.name').text().trim();
            utag_data.event_detail_sub = "change"
            utag.link(utag_data);

         });

   /*Functions for Bundle Selection - End*/
        /*Function for GT Accordion -Start*/

        $("body").on("click", ".gt-accordion-block .extras-toggle .extras-open", function () {
            clearPreviousData();
            utag_data.event_action = "gift trunk container accordion"
            utag_data.event_action_type = "click"
            utag_data.event_detail = "accordion open"
            var accordianButton = $(this).text().trim();
            var accordianTitle = $(this).parents().siblings('.name').text().trim();
            utag_data.event_detail_sub = accordianButton+":"+accordianTitle;
            utag_data.location_name="gift trunk container"
            utag.link(utag_data);

           });

         $("body").on("click", ".contact-us .see-details", function () {
            clearPreviousData();
            utag_data.event_action = "gift trunk container accordion"
            utag_data.event_action_type = "click"
            utag_data.event_detail = "accordion open"
            var accordianButton = $(this).text().trim();
            var accordianTitle = $(this).siblings('.contact-heading').text().trim();
            utag_data.event_detail_sub = accordianButton+":"+accordianTitle;
            utag_data.location_name="gift trunk container"
            utag.link(utag_data);

           });

         $("body").on("click", ".gt-accordion-block .extras-toggle .extras-close", function () {
             clearPreviousData();
             utag_data.event_action = "gift trunk container accordion"
             utag_data.event_action_type = "click"
             utag_data.event_detail = "accordion close"
             var accordianButton = $(this).text().trim();
             var accordianTitle = $(this).parents().siblings('.name').text().trim();
             utag_data.event_detail_sub = accordianButton+":"+accordianTitle;
             utag_data.location_name="gift trunk container"
             utag.link(utag_data);

            });

         $("body").on("click", ".include-extrasContainer .modal .modal-content .close", function () {
             clearPreviousData();
              utag_data.event_action = "gift trunk container accordion";
             utag_data.event_action_type = "click";
             utag_data.event_detail = "accordion close";
             utag_data.event_detail_sub = "see details :our guarantee";
             utag_data.location_name="gift trunk container";
             utag.link(utag_data);

            });
          /*Function for GT Accordion -end*/

         /*Function for Add to Bag- Start*/
         $("body").on('click',".text-right #addToBagbutton",function () {
             utag_data.product_listing_page = productListingPage;
             var trunkValue = $(this).parents('.gtSummaryPage').find('.trunk .offerSize .selected .name').text();
              var pdpValue = getCookieValue("pdp");
              var bundle_1_sku_id, partNumberLetter,bundle_1_name,letterDetails,partNumberEnvelope,earPiercingText,earSkuId,hearingAidText,hearingAidSkuId,EnvelopeDetails="";
             if(trunkValue == starterSetText){
                clearPreviousData();
                utag_data.event_action = "gift trunk add to bag"
                utag_data.event_action_type = "click"
                utag_data.event_detail_sub = $(this).parents().find('.selected-product-name .name').attr('id');
                utag_data.event_detail = localStorage.getItem('gt-product-doll-name');
                utag_data.location_name ="gift trunk doll selection summary"
                utag_data.gt_doll_details = localStorage.getItem('gt-product-doll-name')+":"+$(this).parents().find('.selected-product-name .name').attr('id');
                 bundle_1_name = $(this).parents('.gtSummaryPage').find('#categoryBundle1 .selected .name').text();
                 bundle_1_sku_id = $(this).parents('.gtSummaryPage').find('#categoryBundle1').attr('data-sku-id');
                utag_data.gt_bundle_details = bundle_1_name +":"+bundle_1_sku_id;
                 letterDetails =$.parseJSON(localStorage.getItem('gtLetterDetails'));
                 partNumberLetter = letterDetails["partNumber"];
                utag_data.gt_letter_details = "letter:"+partNumberLetter;
                 EnvelopeDetails =$.parseJSON(localStorage.getItem('gtNonDisplayableItems'));
                 partNumberEnvelope="";
                $(EnvelopeDetails).each(function (index, item) {
                  partNumberEnvelope = item.partNumber;
                });
                utag_data.gt_envelope_details = "envelope:"+ partNumberEnvelope;
                utag_data.gt_trunk_details = "starter box:"+$(this).parents('.gtSummaryPage').find('.offerSize').attr('data-sku-id');
                 earPiercingText = $(this).parents('.gtSummaryPage').find('#earPiercing .selected .name').text().trim();
                 earSkuId = $(this).parents('.gtSummaryPage').find('#earPiercing').attr('data-sku-id');
                 hearingAidText = $(this).parents('.gtSummaryPage').find('#hearingAid .selected .name').text().trim();
                 hearingAidSkuId = $(this).parents('.gtSummaryPage').find('#hearingAid').attr('data-sku-id');
                utag_data.gt_add_on = earPiercingText+":"+earSkuId+" or "+hearingAidText+":"+hearingAidSkuId;
                utag_data.gt_doll_image_url = getCookieValue("gt-product-doll-image");
                 if(pdpValue == "true"){
                 utag_data.doll_finding_method ="content banner doll PDP page"
                 }else{
                 utag_data.doll_finding_method ="doll pre-selection"
                 }
                utag.link(utag_data);

             }else if(trunkValue == giftTrunkText){
                clearPreviousData();
                utag_data.event_action = "gift trunk add to bag"
                utag_data.event_action_type = "click"
                utag_data.event_detail_sub = $(this).parents().find('.selected-product-name .name').attr('id');
                utag_data.event_detail = localStorage.getItem('gt-product-doll-name');
                utag_data.location_name ="gift trunk doll selection summary"
                utag_data.gt_doll_details = localStorage.getItem('gt-product-doll-name')+":"+$(this).parents().find('.selected-product-name .name').attr('id');
                bundle_1_name = $(this).parents('.gtSummaryPage').find('#categoryBundle1 .selected .name').text();
                 bundle_1_sku_id = $(this).parents('.gtSummaryPage').find('#categoryBundle1').attr('data-sku-id');
                var bundle_2_name = $(this).parents('.gtSummaryPage').find('#categoryBundle2 .selected .name').text();
                var bundle_2_sku_id = $(this).parents('.gtSummaryPage').find('#categoryBundle2').attr('data-sku-id');
                utag_data.gt_bundle_details = bundle_1_name +":"+bundle_1_sku_id+"|"+bundle_2_name+":"+bundle_2_sku_id;
                 letterDetails =$.parseJSON(localStorage.getItem('gtLetterDetails'));
                partNumberLetter = letterDetails["partNumber"];
                utag_data.gt_letter_details = "letter:"+partNumberLetter;
                 EnvelopeDetails =$.parseJSON(localStorage.getItem('gtNonDisplayableItems'));
                 partNumberEnvelope ="";
                $(EnvelopeDetails).each(function (index, item) {
                   partNumberEnvelope = item.partNumber;
                 });
                utag_data.gt_envelope_details = "envelope:"+ partNumberEnvelope;
                utag_data.gt_trunk_details = "gift trunk box:"+$(this).parents('.gtSummaryPage').find('.offerSize').attr('data-sku-id');
                 earPiercingText = $(this).parents('.gtSummaryPage').find('#earPiercing .selected .name').text().trim();
                 earSkuId = $(this).parents('.gtSummaryPage').find('#earPiercing').attr('data-sku-id');
                 hearingAidText = $(this).parents('.gtSummaryPage').find('#hearingAid .selected .name').text().trim();
                 hearingAidSkuId = $(this).parents('.gtSummaryPage').find('#hearingAid').attr('data-sku-id');
                utag_data.gt_add_on = earPiercingText+":"+earSkuId+" or "+hearingAidText+":"+hearingAidSkuId;
                utag_data.gt_hnh_insert = $(this).parents('.gtSummaryPage').find('.offerSize').attr('data-sku-id');
                utag_data.gt_doll_image_url = getCookieValue("gt-product-doll-image");

                 if(pdpValue == "true"){
                 utag_data.doll_finding_method ="content banner doll PDP page"
                 }else{
                 utag_data.doll_finding_method ="doll pre-selection"
                 }
                utag.link(utag_data);


             }

             });
      /*Function for Add to Bag- End*/
      /*Function for Retail flow- Start*/
         $("body").on("click", ".retail .gt-btn", function () {
             var associated_code = localStorage.getItem("associateCode");
               if(typeof(associated_code) != "undefined" && associated_code !== "") {
                if($(this).parents('.retail').find('.associate-code input').val() != associated_code){
		               var errorMessage = $(this).parents('.retail').find('.associate-code small').text();
		               clearPreviousData();
		               utag_data.event_action ="retail_store_fulfillment_process"
		               utag_data.event_action_type ="pre form failure"
		               utag_data.form_type="retail_store_fulfillment_form"
		               utag_data.form_stage="pre form failure"
		               utag_data.error_name = errorMessage;
		               utag_data.error_field ="Associate Code"
		               utag.link(utag_data);
                 }else if(getCookieValue('gt_response_value') != "fail"){
                       clearPreviousData();
                       utag_data.event_action ="retail_store_fulfillment_process"
                       utag_data.event_action_type ="click"
                       utag_data.form_type="retail_store_fulfillment_form"
                       utag_data.form_stage="final-complete"
                       utag_data.location_name ="ready to purchase"
                       utag.link(utag_data);
                 }else if(getCookieValue('gt_response_value') == "fail"){
                       utag_data.event_action = "retail_store_fulfillment_process";
                       utag_data.event_action_type = "click";
                       utag_data.form_type = "retail_store_fulfillment_form";
                       utag_data.form_stage = "post form failure";
                       utag_data.error_code = $('#return_error_status').val();
                       utag_data.error_message = $('#return_error_status_text').val();
                       utag.link(utag_data);
                   }
               }
      	});

        var returnFormClickGT = true;
        var returnFormLastClickGT = '';
        $('.retail .name-input, .retail .code-input').click(function () {
        	returnFormLastClickGT = $(this).attr("placeholder").toLowerCase();
    		if(returnFormClickGT == true){
        		returnFormFieldGT();
        		returnFormClickGT = false;
        	}

        });

    	function returnFormFieldGT(){
        	utag_data.event_action = "retail_store_fulfillment_process";
            utag_data.event_action_type = "click";
            utag_data.form_type = "retail_store_fulfillment_form";
            utag_data.form_stage = "initiation";
            utag_data.location_name = "ready to purchase";
            utag.link(utag_data);
       }

        $(document).ready(function () {
               var redirectionUrl = function(){
                   window.history.pushState({page: 1}, "", "");
                   window.onpopstate = function(event) {
                       if(event){
                           utag_data.event_action ="retail_store_fulfillment_process"
                           utag_data.event_action_type ="form abandonment"
                           utag_data.form_type="retail_store_fulfillment_form"
                           utag_data.form_stage="form abandonment"
                           utag_data.form_abandon_type = "form close"
    					   utag_data.last_accessed_field = returnFormLastClickGT;
                           utag.link(utag_data);
                       }
                   }
               }
            if($('.gt-quiz-block .retail').length){
                redirectionUrl();
              //  this.onPageRefresh();
            }
     });
   /*Function for Retail flow- End*/

    /*Function for Doll Hospital Start*/

      $("body").on('click',".dhbasicInformationCollection .active .dh-next-btn",function () {
       if(($(this).parents('section').attr("data-page"))=="1"){
			clearPreviousData();
            utag_data.tool_type = "dh"
	        utag_data.tool_stage = "start"
	        utag_data.event_detail = "welcome to the doll hospital?"
            visitor_status();
           	utag.link(utag_data);
			clearPreviousData();
			utag_data.page_name="ag:us:doll hospital:doll name"
            utag_data.clean_url=cleanUrl+"#page1"
			utag_data.requested_url=cleanUrl+"#page1"
            utag_data.site_subsection="doll name"
			updateDHViewEvent();
			utag.view(utag_data);
		}


	   if(($(this).parents('section').attr("data-page"))=="2"){
            clearPreviousData();
        	utag_data.event_action="doll name"
			utag_data.tool_type = "dh"
	        utag_data.tool_stage = "step1"
	        utag_data.event_detail = $('.dhbasicInformationCollection .active  h2').text()
			utag_data.dh_dollname= $('.dhbasicInformationCollection .dh-basicinfo-input-holder .doll-name').val()
            utag_data.page_name="ag:us:doll hospital:doll name"
            utag_data.referring_url=document.referrer
			utag_data.requested_url=cleanUrl+"#page1"
			utag_data.clean_url=cleanUrl+"#page1"
            utag_data.site_subsection="doll name"
           	updateDHViewEvent();
            utag.link(utag_data);

			clearPreviousData();
            utag_data.page_name="ag:us:doll hospital:doll type"
            utag_data.clean_url=cleanUrl+"#page2"
			utag_data.requested_url=cleanUrl+"#page2"
            utag_data.site_subsection="doll type"
			updateDHViewEvent();
            utag.view(utag_data);
       }

	   if(($(this).parents('section').attr("data-page"))=="3"){
            clearPreviousData();
        	utag_data.event_action="doll type"
			utag_data.tool_type = "dh"
	        utag_data.tool_stage = "step2"
	        utag_data.event_detail = $('.dhbasicInformationCollection .active h2').text()
            var selectedDollData =$(".dh-dollType-option-group").find('input[type="radio"]').filter(":checked");
			utag_data.dh_dolltype= $(selectedDollData).next('label').find('.card-title').text()
            utag_data.page_name="ag:us:doll hospital:doll type"
            utag_data.referring_url=document.referrer
			utag_data.requested_url=cleanUrl+"#page2"
			utag_data.clean_url=cleanUrl+"#page2"
            utag_data.site_subsection="doll type"
           	updateDHViewEvent();
            utag.link(utag_data);

			clearPreviousData();
            utag_data.page_name="ag:us:doll hospital:eye color"
            utag_data.clean_url=cleanUrl+"#page3"
			utag_data.requested_url=cleanUrl+"#page3"
            utag_data.site_subsection="eye color"
			updateDHViewEvent();
			utag.view(utag_data);
       }

       if(($(this).parents('section').attr("data-page"))=="4"){
            clearPreviousData();
        	utag_data.event_action="eye color"
			utag_data.tool_type = "dh"
	        utag_data.tool_stage = "step3"
	        utag_data.event_detail = $('.dhbasicInformationCollection .active h2').text()
            utag_data.dh_eyecolor= $(".dh-eye-option-group").find('input[type="radio"]').filter(":checked").val()
			var selectedDollData =$(".dh-dollType-option-group").find('input[type="radio"]').filter(":checked");
			utag_data.dh_dolltype= $(selectedDollData).next('label').find('.card-title').text()
            utag_data.page_name="ag:us:doll hospital:eye color"
            utag_data.referring_url=document.referrer
			utag_data.requested_url=cleanUrl+"#page3"
			utag_data.clean_url=cleanUrl+"#page3"
            utag_data.site_subsection="eye color"
           	updateDHViewEvent();
			utag.link(utag_data);

			clearPreviousData();
            utag_data.page_name="ag:us:doll hospital:hair color"
            utag_data.clean_url=cleanUrl+"#page4"
			utag_data.requested_url=cleanUrl+"#page4"
            utag_data.site_subsection="hair color"
			updateDHViewEvent();
			utag.view(utag_data);
       }

	   if(($(this).parents('section').attr("data-page"))=="5"){
            clearPreviousData();
        	utag_data.event_action="hair color"
			utag_data.tool_type = "dh"
	        utag_data.tool_stage = "step4"
	        utag_data.event_detail = $('.dhbasicInformationCollection .active h2').text()
            utag_data.dh_haircolor= $(".dh-hair-option-group").find('input[type="radio"]').filter(":checked").val()
			var selectedDollData =$(".dh-dollType-option-group").find('input[type="radio"]').filter(":checked");
			utag_data.dh_dolltype= $(selectedDollData).next('label').find('.card-title').text()
            utag_data.page_name="ag:us:doll hospital:hair color"
            utag_data.referring_url=document.referrer
			utag_data.requested_url=cleanUrl+"#page4"
			utag_data.clean_url=cleanUrl+"#page4"
            utag_data.site_subsection="hair color"
           	updateDHViewEvent();
			utag.link(utag_data);
		}
     });

	$("body").on('click',".dh-treatment-block .dh-btn-span .dh-next-btn",function () {
        clearPreviousData();
		var current_variable = $(this);
        var DHformdata= JSON.parse(sessionStorage.getItem('DHFormData'));
		utag_data.event_action = "treatment"
	    utag_data.tool_type = "dh"
	    utag_data.tool_stage = "step5"
		utag_data.event_detail = $('.dhTreatments h2').text()
		utag_data.dh_doll_treatment = _.unescape(DHformdata['treatment']['skuName'])
		utag_data.dh_dolltype = DHformdata['quizData']['page2']['inputTitle']
        visitor_status();
		utag.link(utag_data);
        });

	$("body").on('click',".dh-sub-treatment-block .dh-btn-span .dh-next-btn",function () {
        clearPreviousData();
		var DHformdata= JSON.parse(sessionStorage.getItem('DHFormData'));
		utag_data.event_action = "subtreatment"
	    utag_data.tool_type = "dh"
	    utag_data.tool_stage = "step5a"
		utag_data.event_detail = $('.dh-sub-treatment-block .page-title-section h2').text()
        var dh_get_values =$(".dh-sub-treatment-block form input").filter(":checked").next('label');
		var dh_selectedValues=[];
        _.each(dh_get_values, (item, index) => {
        dh_selectedValues[index] = item.innerText;})
        utag_data.dh_doll_treatment = dh_selectedValues.join('&');
        utag_data.dh_dolltype = DHformdata['quizData']['page2']['inputTitle']
        visitor_status();
		utag.link(utag_data);
        });

	$("body").on('click',".dh-specialExtra-block .dh-btn-span .dh-next-btn",function () {
        clearPreviousData();
		var DHformdata= JSON.parse(sessionStorage.getItem('DHFormData'));
		utag_data.event_action = "add on"
	    utag_data.tool_type = "dh"
	    utag_data.tool_stage = "step6"
		utag_data.event_detail = $('.dh-specialExtra-block .active h2').text()
        var dh_get_values =$(".dh-specialExtra-block form input").filter(":checked").next('label');
		var dh_selectedValues=[];
        _.each(dh_get_values, (item, index) => {
        dh_selectedValues[index] = item.innerText;})
        if (dh_get_values.length > 0 ) {
		utag_data.dh_doll_addon = dh_selectedValues.join('&');
        sessionStorage.setItem("specialExtrasValue", dh_selectedValues.join('&'));
        } else {
		utag_data.dh_doll_addon = "not selected"
		sessionStorage.setItem("specialExtrasValue", "not selected");
        }
		utag_data.dh_dolltype = DHformdata['quizData']['page2']['inputTitle']
        visitor_status();
		utag.link(utag_data);
        });

	$("body").on('click',".review-add-to-bag .dh-btn-span .dh-next-btn",function () {
        clearPreviousData();
		var DHformdata= JSON.parse(sessionStorage.getItem('DHFormData'));
		utag_data.event_action = "dh add to bag"
	    utag_data.tool_type = "dh"
	    utag_data.tool_stage = "step7"
		utag_data.event_detail = $('.dh-review-block  h2').text()
        utag_data.dh_dolltype = DHformdata['quizData']['page2']['inputTitle']
        var dh_get_values = DHformdata['subTreatmentData']
		var dh_subtreatment_selectedValues=[];
        var sub_id = 0;
		_.each(dh_get_values, (item, index) => {

        dh_subtreatment_selectedValues[sub_id] = item.skuName;
               sub_id++;
         })
		dh_subtreatment_selectedValues = dh_subtreatment_selectedValues.join('&')
        if (DHformdata['subTreatmentData']['isSkipped']) {
            dh_subtreatment_selectedValues = "not applicable"
        }
		utag_data.dh_doll_list="dh" + "|" + DHformdata['dollName'] + "|" + DHformdata['quizData']['page2']['inputTitle'] + "|" + DHformdata['quizData']['page3']['inputTitle']     +"|" +       DHformdata['quizData']['page4']['inputTitle']   + "|" +    _.unescape(DHformdata['treatment']['skuName'])  + "|" +	dh_subtreatment_selectedValues + "|" + sessionStorage.getItem("specialExtrasValue")
		utag_data.product_sku= DHformdata['treatment']['skuId']
		utag_data.product_quantity= "1"
		utag_data.product_price= $('.dhSummaryPage .review-add-to-bag .total-price .doll-price').text()
		utag_data.product_unit_price= $('.dhSummaryPage .review-add-to-bag .total-price .doll-price').text()
        setCookieValue("product_sku",DHformdata['treatment']['skuId'].toLowerCase());
        setCookieValue("product_quantity",1);
		setCookieValue("product_price",$('.dhSummaryPage .review-add-to-bag .total-price .doll-price').text());
        setCookieValue("doll_name",$('.dhSummaryPage .dollName').text().toLowerCase());
        setCookieValue("doll_type",$('.dhSummaryPage .dollType').text().toLowerCase());
        setCookieValue("eye_color",$('.dhSummaryPage .dollEyeColor').text().toLowerCase());
        setCookieValue("hair_color",$('.dhSummaryPage .dollHairColor').text().toLowerCase());
        setCookieValue("treatment",$('.dhSummaryPage .treatment-type').text().toLowerCase());
        setCookieValue("sub_treatment",dh_subtreatment_selectedValues.toLowerCase());
        setCookieValue("add_on",sessionStorage.getItem("specialExtrasValue").toLowerCase());
		visitor_status();
		utag.link(utag_data);
        });

	/*Function for Doll Hospital End*/


	$("body").on("click", "input .add-new-address", function () {
        clearPreviousData();
        setCommonPropForLink(this.value);
    });

    $("body").on("click", "input .data-val", function () {
        clearPreviousData();
        setCommonPropForLink(this.value);
    });
	$("#searchOrder").change(function () {
		clearPreviousData();
		utag_data.event_action = "click event"
		utag_data.event_action_type = "click"
		utag_data.event_detail = "account - orders - display orders from"
		utag_data.event_detail_sub = $("#searchOrder option:selected").html()
		utag_data.location_name = "order history"
		utag.link(utag_data)
	});
	$("#shippingMethodContainer").change(function () {
		clearPreviousData();
		utag_data.event_action = "click event"
		utag_data.event_action_type = "click"
		utag_data.event_detail = "account - " + $("h1").text().toLowerCase() + " - " + $(".defaultShipLabel").text().toLowerCase()
		utag_data.event_detail_sub = $("#shippingMethodContainer input[type='radio']:checked").val().toLowerCase()
		utag_data.location_name = $("h1").text().toLowerCase()
		console.log(utag_data);
		utag.link(utag_data);
	});
	$(".plp-banner-sec-expand .arrow-btn").click(function () {
		var expandText = ""
		if (!$(this).hasClass('open')) {
			expandText = "banner expand";
		} else {
			expandText = "banner collapse";
		}
		clearPreviousData();
		utag_data.event_action = "category banner"
		utag_data.event_action_type = "click"

        if ($(".plp-banner-extend").attr("data-analytics-text") != undefined) {
            utag_data.event_detail = $(".plp-banner-extend").attr("data-analytics-text")
        }

        utag_data.event_detail_sub = expandText
        console.log("=" + utag_data);
        utag_data.location_name = "banner content"

        utag.link(utag_data)
    });

    $("body").on("click", "#product-filter-section h2 a", function () {
        clearPreviousData();
        utag_data.event_action = "category filter"
        utag_data.event_action_type = "click"
        utag_data.event_detail = $(this).text().trim().toLowerCase()
        utag_data.location_name = "left nav"
        if ($(this).hasClass('collapsed')) {
            utag_data.event_detail_sub = "expand"
        } else {
            utag_data.event_detail_sub = "collapse"
        }

        utag.link(utag_data)
    });

    $("body").on("click", "#categoryNav a", function () {
        clearPreviousData();
        utag_data.event_action = "category filter"
        utag_data.event_action_type = "click"
        utag_data.event_detail_sub = $(this).text()
        utag_data.location_name = "left nav"
        if ($(this).hasClass("see-less link") || $(this).hasClass("see-options link")) {
            utag_data.event_detail = $(this).text().trim().toLowerCase()
        } else {
            utag_data.event_detail = $(this).parents(".dropmenu").find("li").eq(0).text()
        }
        utag.link(utag_data)
    });

    $("body").on("click", ".filter-grid input", function () {
        clearPreviousData();
        var final_string_checkbox = "";
        $('.filter-grid input[type="checkbox"]:checked').each(function () {
            var input_value = $(this).siblings('label').text().trim();
            if (input_value == "") {
                input_value = $(this).siblings('label').find('img').attr('alt');
            }
            var heading_value = $(this).closest('.checkbox-cont').find('h2').text().trim();
            var current_string = heading_value + ":" + input_value;
            if (final_string_checkbox == "") {
                final_string_checkbox = current_string;
		}
		else{
                final_string_checkbox = final_string_checkbox + "," + current_string;
            }
        });

        utag_data.event_action = "category filter";
        utag_data.event_action_type = "click";
        utag_data.event_detail = utag_data.product_category;
        utag_data.event_detail_sub = utag_data.product_subcategory;
        utag_data.facet_filter = final_string_checkbox.toLowerCase();
        utag_data.location_name = "left nav";
        utag.link(utag_data)
    });



    $("#see-more").click(function () {
        clearPreviousData();
        utag_data.event_action = "products scroll tracking"
        utag_data.event_action_type = "click"
        utag_data.event_detail = "girl clothing:dresses for girls"
        utag_data.event_detail_sub = $("#see-more").text().trim().toLowerCase()
        utag_data.location_name = "products scroll"

        utag.link(utag_data);
    });

    /* Global Navigation functions STARTS here */

    $(document).on('click', '.main-nav-container .brand-logo', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation header click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global header - " + $(this).find("img").attr("alt").toLowerCase();
        utag_data.event_detail_sub = $(this).find("img").attr("alt").toLowerCase();
        utag_data.location_name = "global navigation header";

        utag.link(utag_data);
    });

    $(document).on('click', '.inline-nav a', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation header click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global header -" + $(this).find('span').text().toLowerCase();
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation header";

        utag.link(utag_data);
    });

    $(document).on('click', '.search-container button', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation header click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global header -search icon expand";
        utag_data.event_detail_sub = "search icon expanded";
        utag_data.location_name = "global navigation header";

        utag.link(utag_data);
    });

    $(document).on('click', '.pull-right .shop-link', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation header click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global header - " + $(this).find('span').text().toLowerCase();
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation header";

        utag.link(utag_data);
    });

    $(document).on('click', '.pull-right .shopping-bag', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation header click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global header - " + $(this).find("img").attr("alt").toLowerCase().trim();
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation header";

        utag.link(utag_data);
    });

    $(document).on('click', '.pull-right .aghamburger', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation header click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global header - global menu";
        utag_data.event_detail_sub = "global menu";
        utag_data.location_name = "global navigation header";

        utag.link(utag_data);
    });

    $(document).on('click', '.header-spark-menu .ham-homelogo', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu";
        utag_data.event_detail_sub = "home icon";
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.header-spark-menu #spark-menu-close-button', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu";
        utag_data.event_detail_sub = "close";
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.category-menu .desktop-menu a', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu - " + $(this).find('span').text().toLowerCase();
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.account-menu a', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu - " + $(this).find('span').text().toLowerCase();
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.category-menu .main-menu', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu - " + $(this).find('span').text().toLowerCase();
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.category-menu .sub-menu', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu - " + $(this).data('parent-value');
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.category-menu .sub-sub-menu', function () {
        clearPreviousData();
        let parentVal=$(this).data('parent-value');
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu - " + ((parentVal !="" && parentVal !=undefined) ? parentVal : $(this).find('span').text().toLowerCase());
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.backButtons a', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu - " + $(this).find('span').text().toLowerCase();
        utag_data.event_detail_sub = $(this).find('span').text().toLowerCase();
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.social-icons a', function () {
        clearPreviousData();
        utag_data.event_action = "global navigation menu click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = "global menu";
        utag_data.event_detail_sub = $(this).attr("title").toLowerCase();
        utag_data.location_name = "global navigation menu";

        utag.link(utag_data);
    });

    $(document).on('click', '.user-info-in-header .activated-user a, .user-info-in-header .not-activated-user a', function () {
    	clearPreviousData();
    	var userName = $(this).parent().find(".hidden-xs > span:first").text().trim() || $(this).parent().find(".hidden-xs").text().trim() || "";
    	utag_data.event_action = "global navigation menu click";
    	utag_data.event_action_type = "click";
    	utag_data.event_detail = "global menu - " + userName;
    	utag_data.event_detail_sub = userName;
    	utag_data.location_name = "global navigation menu";
    	utag.link(utag_data);
    });

    /* Global Navigation functions ENDS here */

    /* Return Form Start*/

    var returnFormClick = true;
    var returnFormLastClick = '';
    $('#firstName, #lastName, #defaultState, #zipCode').click(function () {
        returnFormLastClick = $(this).attr("name").toLowerCase();
        if (returnFormClick == true) {
            setTimeout(function () {
                clearPreviousData();
                returnFormField();
                returnFormClick = false;
 			}, 600);

        }
    });
    function returnFormField() {

        utag_data.event_action = "return_form_shipping_label";
        utag_data.event_action_type = "click";
        utag_data.form_type = "return_form_shipping_label";
        utag_data.form_stage = "initiation";
        utag_data.location_name = "returns form page";

        utag.link(utag_data);

    }

    $('.return_analytics_success').click(function () {
        clearPreviousData();
        utag_data.event_action = "return_form_shipping_label";
        utag_data.event_action_type = "click";
        utag_data.form_type = "return_form_shipping_label";
        utag_data.form_stage = "final-complete";
        utag_data.location_name = "returns page";
        utag.link(utag_data);
    });

    $(".return_analytics_service_failure").click(function () {
        clearPreviousData();
        utag_data.event_action = "return_form_shipping_label";
        utag_data.event_action_type = "post form failure";
        utag_data.form_type = "return_form_shipping_label";
        utag_data.form_stage = "post form failure";
        utag_data.error_code = $('#return_error_code').val();
        utag_data.error_message = $('#return_error_message').val();

        utag.link(utag_data);
    });

    $(".return_analytics_validation_failure").click(function () {
        clearPreviousData();
        utag_data.event_action = "return_form_shipping_label";
        utag_data.event_action_type = "pre form failure";
        utag_data.form_type = "return_form_shipping_label";
        utag_data.form_stage = "pre form failure";
        utag_data.error_name = "Return form field validation";
        utag_data.error_field = $('#return_error_field').val();

        utag.link(utag_data);
    });

    window.onbeforeunload = function () {

    	emailLightBoxAbandonment();

    var $formElem = $("#returnForm");
    if($formElem.length){
        if($formElem.find("#zipCode").val() !="" || $formElem.find("#firstName").val() !="" || $formElem.find("#lastName").val() !=""){
           var abandon_type = "";
           if (performance.navigation.type == 1) {
               console.info("This page is reloaded");
               abandon_type = "page reload"
           } else {
               console.info("This page is not reloaded");
               abandon_type = "browser close";
           }
           utag_data.event_action = "return_form_shipping_label";
           utag_data.event_action_type = "form abandonment";
           utag_data.form_type = "return_form_shipping_label";
           utag_data.form_stage = "form abandonment";
           utag_data.form_abandon_type = abandon_type;
           utag_data.last_accessed_field = returnFormLastClick;

           utag.link(utag_data);
        }
    }



    };

    /* Return Form End*/
    /* ELB on click events STARTS here*/
    $(document).on("click", "#emailLightboxForm .email_field", function () {
    	if(first_click == 0){
    		first_click = 1;
            clearPreviousData();
    		if(signup_location=="onload"){
    			newsletterVars();
    		}
    		else if(signup_location=="hamburger" || signup_location=="footer"){
    			emailVars();
    		}
            utag_data.event_action_type = "click";
            utag_data.form_stage = "initiation";
            utag_data.location_name = "light box";
            utag.link(utag_data);
    	}
    });

    $(document).on('click', '.account-menu .popupEmailSignUp , .email-section .signup-model-popup', function () {
    	if($('.email-lightboxpopup-comp .modal').is(":visible")){
    		clearPreviousData();
    		emailLightBoxPrompt();
    		var current_variable= $(this);
    		signup_location = current_variable.closest('.account-menu').length>0 ? "hamburger" : "footer";
    		emailVars();
    		utag.link(utag_data);
    	}
    });

    $(document).on('click', '.cmEcomEmailSubscription .modal-header button', function () {
    	clearPreviousData();
    	if(signup_location=="onload"){
    		newsletterVars();
    	}
    	else if(signup_location=="hamburger" || signup_location=="footer"){
    		emailVars();
    	}
    	utag_data.event_action_type="click";
    	utag_data.form_stage="light box close";
    	utag_data.location_name="light box";
    	utag.link(utag_data);
    });

    $(document).on('click', '.cmEcomEmailSubscription .addChildCont', function () {
    	clearPreviousData();
    	addRemoveChild("addition");
    	utag_data.event_detail_sub = "add a child";
    	utag.link(utag_data);
    });

    $(document).on('click', '.cmEcomEmailSubscription .remove-child', function () {
    	clearPreviousData();
    	addRemoveChild("remove");
    	utag_data.event_detail_sub = "remove";
    	utag.link(utag_data);
    });

    $(document).on('click', '#emailLightboxForm .email-signup-account', function () {
    	var parent_form=$(this).closest('form');

    	if(parent_form.find('.has-error , .dateErrCls').length > 0){
    		clearPreviousData();
	     	if(signup_location=="onload"){
	     		newsletterVars();
	    	}
	    	else if(signup_location=="hamburger" || signup_location=="footer"){
	    		emailVars();
	    	}
		        utag_data.event_action_type = "pre form failure";
		        utag_data.form_stage = "pre form failure";
		        utag_data.error_name = "validation error";
		        utag_data.error_field = listErrors();
		        utag.link(utag_data);
    	}
    	else{
    		selected_children=listChildren();
    	}

    });


    $('#myThankyou').on('show.bs.modal', function () {
    	clearPreviousData();
    	if(signup_location=="onload"){
    		newsletterVars();
    	}
    	else if(signup_location=="hamburger" || signup_location=="footer"){
    		emailVars();
    		utag_data.add_child = selected_children;
    	}
    	var status_code=$(".email-lightboxpopup-comp[data-is-modal='true']").find("#elbModalDetails").attr("data-status-code");
    	if(status_code=="200" || status_code=="201"){
    		setCookieValue("ELB_SUBMIT_SUCCESS","true");
            utag_data.event_action_type = "click";
            utag_data.form_stage = "final-complete";
            utag_data.location_name = "light box";
    		utag_data.customer_email_hash = typeof sha256 == "function" && sha256($("#emailId").val());
    		utag_data.source_id = $('#sourceId').attr('data-'+signup_location+'version-source-id');
    		utag_data.subscription_ids = $('#subscriptionId').data('primarysubscription-id')+","+$('#subscriptionId').data('nosubscription-id');
    		utag.link(utag_data);
    	}
    	else if(status_code!="1004"){
    		utag_data.event_action_type="post form failure";
    		utag_data.form_stage = "post form failure";
    		utag_data.error_code = status_code;
    		utag_data.error_message = $(this).find('.display-error-msg').text();
    		utag.link(utag_data);
    	}
    });

    $(document).on('click', '#emailLightboxForm input, #emailLightboxForm select', function () {
    	ELB_last_accessed_field = $(this).data("key");
    });


    /* ELB on click events ENDS here*/


    /* Coppa site entry Module STARTS here */
    $(".modal-body .shop-section a").click(function () {
        clearPreviousData();
        utag_data.event_action = "coppa site entry module click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = $(this).closest("div").find("h2").text().toLowerCase();
        utag_data.event_detail_sub = "shop now";
        utag_data.modal_section = "shop now";
        utag_data.location_name = "coppa site entry module";

        utag.link(utag_data);
    });

    $(".modal-body .play-section a").click(function () {
        clearPreviousData();
        utag_data.event_action = "coppa site entry module click";
        utag_data.event_action_type = "click";
        utag_data.event_detail = $(this).closest("div").find("h2").text().toLowerCase();
        utag_data.event_detail_sub = "play now";
        utag_data.modal_section = "play now";
        utag_data.location_name = "coppa site entry module";

        utag.link(utag_data);
    });
    /* Coppa site entry Module ENDS here */



});

function quickview_event(ele) {
    var quickViewParent = $(ele).parent();
    clearPreviousData();
    var personalize = quickViewParent.parents('.personalization_recommendation_products').attr('class');
    if (personalize) {
        utag_data.product_name = quickViewParent.find('a div.item-desc').text();
        utag_data.event_action = "quick view"
        utag_data.event_action_type = "click"
        utag_data.event_detail = 'personalization_recommendation_products';
        utag_data.personalization_strategy = $('.personalization_recommendation_products').find('h2').text();
        utag_data.product_status = '';
        utag_data.product_position = quickViewParent.attr('data-slick-index');
        utag_data.product_subcategory = '';
        utag_data.event_detail_sub = quickViewParent.find('.product-price').attr('data-actual-price');
        utag_data.location_name = "personalization_product_carousel";
    } else {
        utag_data.event_action = "quick view"
        utag_data.event_action_type = "click"
        utag_data.event_detail = quickViewParent.find(".grid-title").text();
        utag_data.event_detail_sub = quickViewParent.attr('data-partno');
        utag_data.location_name = "product grid"
        if (quickViewParent.find(".inventory-status strong").text() == 'Backordered') {
            utag_data.backorder_product = "yes"
        } else {
            utag_data.backorder_product = "no"
        }
    }
    utag.link(utag_data);
}

function getURLParameter(url, name) {
    return (RegExp(name + '=' + '(.+?)(&|$)').exec(url) || [, null])[1];
}
// carousel dots
$(document).on('click', '.flexible-carousel-component .slick-dots li', function (evt) {
    var $parentEle = $(evt.target).closest(".flexible-carousel-component");
    if (!$parentEle.length) {
        console.log("Error in AnalyticsJS : flexible-carousel-component element not found");
        return;
    }
    clearPreviousData();
    var datasets = $parentEle.data("sliderProperties");
    utag_data.event_name = datasets.centerMode ? "center mode carousel" : (datasets.slideToShow == 1 ? "single image carousel" : "small grid carousel");
    utag_data.event_type = "click";
    utag_data.carousel_detail = $parentEle.find(".section-title:first h2").text().toLowerCase() || "not applicable";
    utag_data.carousel_detail_sub = $parentEle.find(datasets.centerMode ? ".slick-slide.slick-center:not(.slick-cloned)" : ".slick-slide.slick-active:not(.slick-cloned)").find(".content h3").text().toLowerCase() || "not applicable";
    utag_data.carousel_click = "p" + ($(this).index() + 1);
    utag_data.navigation_click = "dots";
    utag_data.location_name = "carousel section";
    utag_data.component_name = "carousel";
    if ($(this).parents(".jumbotron-banner-component").length) {
        utag_data.component_name = "jumbotron"
    }
    utag.link(utag_data);
})
// carousel arrows
$(document).on('click', '.flexible-carousel-component .slick-arrow', function (evt) {
    var $parentEle = $(evt.target).closest(".flexible-carousel-component");
    if (!$parentEle.length) {
        console.log("Error in AnalyticsJS : flexible-carousel-component element not found");
        return;
    }
    clearPreviousData();
    var datasets = $parentEle.data("sliderProperties");
    utag_data.event_name = datasets.centerMode ? "center mode carousel" : (datasets.slideToShow == 1 ? "single image carousel" : "small grid carousel");
    utag_data.event_type = "click";
    utag_data.carousel_detail = $parentEle.find(".section-title:first h2").text().toLowerCase() || "not applicable";
    utag_data.carousel_detail_sub = $parentEle.find(datasets.centerMode ? ".slick-slide.slick-center:not(.slick-cloned)" : ".slick-slide.slick-active:not(.slick-cloned)").find(".content h3").text().toLowerCase() || "not applicable";
    utag_data.carousel_click = "not applicable";
    utag_data.navigation_click = $(this).hasClass("slick-prev") ? "left" : "right";
    utag_data.location_name = "carousel section";
    utag_data.component_name = "carousel";
    if ($(this).parents(".jumbotron-banner-component").length) {
        utag_data.component_name = "jumbotron"
    }
    utag.link(utag_data);
})
// Content Group CTA
$(document).on('click', '.flexible-cta-component .description-content a', function (evt) {
    var $parentEle = $(evt.target).closest(".flexible-cta-component");
    if (!$parentEle.length) {
        console.log("Error in AnalyticsJS : flexible-cta-component element not found");
        return;
    }
    if ($(this).parents(".jumbotron-banner-component").length) {
        return;
    }
    clearPreviousData();
    utag_data.event_name = "content group";
    utag_data.event_type = "click";
    utag_data.event_detail = $parentEle.find(".title").text().toLowerCase().trim(); //|| $parentEle.find(".title h2").text().toLowerCase();
    utag_data.event_detail_sub = $(evt.target).text().toLowerCase();
    utag_data.location_name = "content group section";
    utag_data.user_type = getUserCookie().userType;
    utag.link(utag_data);
})

//function to get user type
function getUserCookie(){
    var UserName = window.global.browserCookie.getCookie("MATTEL_WELCOME_MSG");
    var isUserLoggedIn = UserName.trim() != "";
    if(isUserLoggedIn){
        var decodedUserName = decodeURIComponent(UserName).replace(","," ").split(" ")[0];
        var customerSegmentCookie = window.global.getUserCookie().customerSegment;
        if(customerSegmentCookie == "SILVER" || customerSegmentCookie == "GOLD" || customerSegmentCookie == "BERRY") return {userType:"agr-user", userName: decodedUserName, authenticationId: document.cookie.split(';').filter(function (c) {return c.trim().indexOf('WC_AUTHENTICATION_') !=-1;}) };
        else return {userType:"non-agr-user", userName: decodedUserName};
    }
    return {userType:"guest-user"};
}

// Cards Component cta
$(document).on('click', '.cardsContainer .flexible-grid-component>.cta-button a', function (evt) {
    var $parentEle = $(evt.target).closest(".flexible-grid-component");
    if (!$parentEle.length) {
        console.log("Error in AnalyticsJS : cardsContainer element not found");
        return;
    }
    if ($(this).parents(".jumbotron-banner-component").length) {
        return;
    }
    clearPreviousData();
    utag_data.event_action = "multiple feature cards";
    utag_data.event_action_type = "click";
    utag_data.event_detail = $parentEle.find(".section-title:first h2").text().toLowerCase() || $parentEle.find(".section-title:first h3").text().toLowerCase() || "not applicable"; //Title of the featured card
    utag_data.event_detail_sub = $(this).text().toLowerCase(); //Button click value
    utag_data.location_name = "cards component";

    utag.link(utag_data);
})

$(document).on('click', '.featureCardComponent .flexible-grid-component .content a', function (evt) {
    var $parentEle = $(evt.target).closest(".flexible-grid-component");
    if (!$parentEle.length) {
        console.log("Error in AnalyticsJS : flexible-carousel-component element not found");
        return;
    }
    clearPreviousData();
    utag_data.event_action = $parentEle.hasClass("two-image-grid") ? "small featured card" : "large featured card";
    utag_data.event_action_type = "click";
    utag_data.event_detail = $(this).closest(".content").find("h3").text().toLowerCase(); //Title of the featured card
    utag_data.event_detail_sub = $(this).text().toLowerCase(); //Button click value
    utag_data.location_name = "feature card section";
    if ($(this).parents(".jumbotron-banner-component").length) {
        utag_data.component_name = "jumbotron"
    }
    if ($(this).parents(".cardsContainer").length) {
            utag_data.component_name = "multiple feature cards"
            if(utag_data.event_detail == ""){
                utag_data.event_detail = $(this).closest(".cardsContainer").find(".title").children().text();
            }
    }
    if ($(this).parents(".flexible-carousel-component").length) {
         $parentEle = $(evt.target).closest(".flexible-carousel-component");
        var datasets = $parentEle.data("sliderProperties");
        var $activeSlideElem = $parentEle.find(datasets.centerMode ? ".slick-slide.slick-center:not(.slick-cloned)" : ".slick-slide.slick-active:not(.slick-cloned)");
            utag_data.component_name = "carousel";
            utag_data.carousel_detail = $parentEle.find(".section-title:first h2").text().toLowerCase() || "not applicable";
            utag_data.carousel_detail_sub = $activeSlideElem.find(".content h3").text().toLowerCase() || "not applicable";
            utag_data.carousel_click = "p" + ($activeSlideElem.data("slickIndex") + 1) + ":" + $(this).text().toLowerCase();
            utag_data.navigation_click = "not applicable";
        }
    utag.link(utag_data);
})
// on click of any promotion banner
$(document).on('click', '.no-promo-drawer .promo-column-wrapper div>a', function (evt) {
    var link = $(this).attr('href');
    var icidvalue = getURLParameter(link, 'icid');
    if (icidvalue == null) {
        icidvalue = "not applicable";
    }
    clearPreviousData();
    utag_data.event_action = "global promo banner";
    utag_data.event_action_type = "click";
    utag_data.promo_campaign_id = icidvalue;
    utag_data.banner_detail = $(this).text().toLowerCase();
    utag_data.location_name = "header promo";

    utag.link(utag_data);
})
var isPromoTextClicked = false;
// On click of collpase/expand
// called from promo-header.js
var promoHeaderAccordion = function (elem) {
    if (isPromoTextClicked) {
        isPromoTextClicked = false;
        return;
    }
    var isExpanded = $("#plus-click").attr('aria-expanded');
    var elemId = "#"+$(elem).attr("id");
    var elementTar = elemId + " .featurecard";
    var $promoContainer = $(elementTar);
    //var $promoContainer = $(".promo-header-dropdown .featurecard");
    var promoCount = $promoContainer.length;
    var $anchorEle = $(elemId).siblings().find('.enable-text-carousel div>a');
    var str = "";
    /*_.each($promoContainer.find('h3'), function (item) {
         str += str == "" ? $(item).text() : "," + $(item).text()
     })*/
     _.each($anchorEle, function (item) {
         str += str == "" ? $(item).text() : "," + $(item).text()
     })
    clearPreviousData();
    utag_data.event_action = "global promo drawer";
    utag_data.event_action_type = "click"; //Capture the value from icid querystring param
    utag_data.promo_drawer_detail = promoCount + " promo " + (isExpanded == "true" ? "collapse" : "expand");
    utag_data.promo_drawer_detail_sub = str;
    utag_data.location_name = "promo drawer section";
    //console.log("accordion click");
    utag.link(utag_data);
};

// on click of actual promotions if applicable
$(document).on('click', '.promo-header-dropdown .flexible-grid-component a', function (evt) {
    var $anchorEle = $(".promo-header-dropdown .flexible-grid-component a");
    var link = $(this).attr('href');
    var icidvalue = getURLParameter(link, 'icid');
    if (icidvalue == null) {
        icidvalue = "not applicable";
    }
	var url=$(this).find("img").attr('src');
	var arr=url.split('/');
	var myoutput=arr[arr.length - 1];

    var str = "";
	_.each($anchorEle, function (item) {
		var url=$(item).find("img").attr('src');
		var arr=url.split('/');
		var myoutput=arr[arr.length - 1];
        str += str == "" ? myoutput : "," + myoutput;
    })
    clearPreviousData();
    utag_data.event_action = "global promo drawer";
    utag_data.event_action_type = "click";
    utag_data.promo_campaign_id = icidvalue;
    utag_data.promo_drawer_detail = $(this).parent().siblings(".content").find("h3").text().toLowerCase() || myoutput || "not applicable";
    utag_data.promo_drawer_detail_sub = $(this).parent().siblings(".content").find("p").text().toLowerCase() || str || "not applicable";
    utag_data.location_name = "promo drawer section";

    utag.link(utag_data);
})

var getParamValueFromURL = function getParamValueFromURL(attrName) {
    var urlParam = function urlParam(urlName) {
      urlName = urlName.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
      var regexS = "[\\?&]" + urlName + "=([^&#]*)";
      var regex = new RegExp(regexS);
      var results = regex.exec(window.location.search);
      if (results) return results[1];else return results;
    };

    return urlParam(attrName);
};

var trackingPDPSearchPage = function () {
    clearPreviousData();
    var searchCookie = getCookieValue('SEARCH_KEYWORD_ACTION');
    var searchActionName = searchCookie ? JSON.parse(searchCookie) : ''
    var pageUrl = document.location.protocol + "//" + document.location.hostname + document.location.pathname;
    var mtachUrlwithCookie = searchActionName.actionName == "visual_search" ? searchActionName.searchVal == pageUrl : 0;
    utag_data.visual_search = mtachUrlwithCookie ? 'yes' : '';
    utag_data.visual_search_product_name = mtachUrlwithCookie ? $(".product-name").text() : '';

}
$(document).on('click', '.footer .signUpButton', function (evt) {
    clearPreviousData();
    setCookieValue("EMAIL_CAPTURE_LOCATIONS", "footer");
});


var scrollDirection = null;
function scrollJumbotronDirection() {
    var previousScroll = 0;
    $(window).scroll(function(){
       var currentScroll = $(this).scrollTop();
       if (currentScroll > previousScroll){
          scrollDirection = 'bottom';
       } else {
          scrollDirection = 'up'
       }
       utag_data.carousel_click = scrollDirection;
       previousScroll = currentScroll;
    });
}
$(document).on('click', '#anchor-indicators .scroll-to-component', function (evt) {
    clearPreviousData();
    scrollJumbotronDirection();
    utag_data.component_name = "jumbotron";
    utag_data.event_name = "jumbotron-carousel";
    utag_data.event_type = "click";
    utag_data.jumbotron_detail = $(this).find('span').text().toLowerCase();//Title of the banner only in English
    utag_data.jumbotron_image_carousel = "dot:" + ($(this).closest("li").index() + 1);//"dot: position" or "arrow next jumbotron"
    utag_data.location_name = "jumbotron section"

    setTimeout(function(){
        utag.link(utag_data);}, 200);
});
// tracking :: jump next arrow :: jumbotron
$(document).on('click', '.jumbotron-banner-component .jump-next-container', function (evt) {
    clearPreviousData();
    var mediaId = $(this).parents(".jumbotron-banner-component").attr('id');
    var jumbotronTitle = $("#anchor-indicators .scroll-to-component[href='#".concat(mediaId, "']")).find('span').text().toLowerCase();
    var personalize = $(this).parent().parent().find('div#personalization_banner').attr('id');
    if (personalize) {
        utag_data.event_action = "jumbotron-carousel";
        utag_data.event_detail = "personalization_banner";
        utag_data.event_action_type = "click";
        utag_data.jumbotron_detail = jumbotronTitle; //Title of the banner only in English
        utag_data.jumbotron_image_carousel = "arrow next jumbotron";
        utag.link(utag_data);
    } else {
        utag_data.component_name = "jumbotron";
        utag_data.event_name = "jumbotron-carousel";
        utag_data.event_type = "click";
        utag_data.jumbotron_detail = jumbotronTitle; //Title of the banner only in English
        utag_data.jumbotron_image_carousel = "arrow next jumbotron"; //"dot: position" or "arrow next jumbotron"
        utag_data.carousel_click = "bottom"; //left / right / top / buttom
        utag_data.location_name = "jumbotron section"
        utag.link(utag_data);
    }
});
// tracking :: button click :: jumbotron
$(document).on('click', '.jumbotron-banner-component .cta-button .theme-btn', function (evt) {
    clearPreviousData();
    var mediaId = $(this).parents(".jumbotron-banner-component").attr('id');
    var jumbotronTitle = $("#anchor-indicators .scroll-to-component[href='#".concat(mediaId, "']")).find('span').text().toLowerCase();
    var personalize = $(this).parent().parent().parent().parent().parent().parent().find('div#personalization_banner').attr('id');
    if (personalize) {
        utag_data.event_action = "jumbotron-button";
        utag_data.event_detail = "personalization_banner";
        utag_data.event_action_type = "click";
        utag_data.jumbotron_detail = jumbotronTitle;
        utag_data.jumbotron_detail_sub = $(this).text().toLowerCase();
        utag.link(utag_data);
    } else {
        utag_data.component_name = "jumbotron";
        utag_data.event_name = "jumbotron-button";
        utag_data.event_type = "click";
        utag_data.jumbotron_detail = jumbotronTitle;
        utag_data.jumbotron_detail_sub = $(this).text().toLowerCase();
        utag_data.location_name = "jumbotron section";
        utag.link(utag_data);
    }
});

// tracking :: anchor link :: jumbotron
$(document).on('click', '.jumbotron-banner-component .cta-link-component .external-link', function(evt) {
    clearPreviousData();
    var mediaId = $(this).parents(".jumbotron-banner-component").attr('id');
    var jumbotronTitle = $("#anchor-indicators .scroll-to-component[href='#".concat(mediaId, "']")).find('span').text();
    var personalize = $(this).parent().parent().find('div#personalization_link').attr('id');
    if (personalize) {
        utag_data.event_action = "jumbotron-button";
        utag_data.event_detail = "personalization_banner";
        utag_data.jumbotron_detail = jumbotronTitle;
        utag_data.jumbotron_detail_sub = $(this).text().toLowerCase();
        utag.link(utag_data);
    } else {
        var isButtonLink = $(this).parents(".cta-circle-button").length || $(this).parents(".cta-square-button").length || $(this).parents(".cta-right-arrow").length;
        utag_data.component_name = "jumbotron";
        utag_data.event_name = isButtonLink ? "jumbotron-button" : "jumbotron-link";
        utag_data.event_type = "click";
        utag_data.jumbotron_detail = jumbotronTitle;
        utag_data.jumbotron_detail_sub = $(this).text().toLowerCase();
        utag_data.location_name = "jumbotron section";
        utag.link(utag_data);
    }
});

var utagConditionLoop = 0;
function isUTAGLoaded(cb) {
    if (typeof utag == "undefined") {
        utagConditionLoop++;
        if (utagConditionLoop > 20) {
            cb(false);
            return;
        }
        setTimeout(function () {isUTAGLoaded(cb);}, 500);
    } else {
        console.log("utag js loading time :"+(utagConditionLoop * 500)/1000+"s");
        cb(true)
    }
}
function postReadyCalls() {
    // define onload fns here.
	if($('.email-lightboxpopup-comp .modal').is(":visible")){//should be put under document.ready
		emailLightBoxPrompt();
		signup_location = "onload";
		newsletterVars();
	}
    if ($('body').hasClass('ecomm-pdp-page')) {
        trackingPDPSearchPage();
    }
    callOnLoadMethods();
	setVisitorStatus();
	 getConsumerId();
}
var getConsumerId = function () {
    var consumerData = window.global.localStorage.get("CUSTOMER_DATA");
    if (consumerData) {
        var consumerID = consumerData.consumerID;
        if (consumerData) {
            utag_data.consumer_ID = consumerID;
        }

    }
}

var setVisitorStatus = function () {
	var visitorStatusCookie = getCookieValue("MATTEL_VISITOR_STATUS");
		visitorStatusCookie ? setCookieValue("MATTEL_VISITOR_STATUS", encodeURIComponent("returning_visitor")) : setCookieValue("MATTEL_VISITOR_STATUS", encodeURIComponent("new_visitor"));
	visitor_status();
}
//tooltip in loyalty rewards drawer component tracking start
$("body").on("mouseenter", ".reward-tooltip", function() {
if(device().toLowerCase() =="desktop"){
 var $this = $(this);
 trackTooltip($this,"hover");
}
});

$("body").on("click", ".reward-tooltip", function() {
if(device().toLowerCase()=="mobile" || device().toLowerCase()=="tablet"){
    var $this = $(this);
    trackTooltip($this,"click");
}
});

function trackTooltip(ele,evtType){
    clearPreviousData();
    utag_data.event_detail = "header_toolTip";
    utag_data.event_action = "header_toolTip";
    utag_data.event_action_type = evtType;
    utag_data.rewardStatus=$(".loyaltyRewardsDrawerComponent .pencil-drawer .userProfileComponent #loyalityComponentContainer .enhanced-container").attr("data-active-tier");
    utag_data.rewardMembershipId=$(".usr-name-section .usr-membership").text();
    utag_data.rewardListAvailable=$(".rewards-total").text() ? $(".rewards-total").text().trim() : "";
    utag_data.rewardPoints = "";
    utag_data.rewardApplied=$(ele).parents(".usr-points").find(".promo-description .default-pts").text();
    utag.link(utag_data);
}
//tooltip in loyalty rewards drawer component tracking end

/* Show and Hide Rewards click in the loyalty rewards drawer - start */
$("body").on("click", ".loyaltyRewardsDrawerComponent .loyality-bar .rewards-btn span", function(e) {
    clearPreviousData();
    var list=[];
    	$(".redeemed-on .promo-description .default-pts").each(function(){
    	var offerName = $(this).attr("data-title") || "";
    		list.push(offerName);
    	});
	utag_data.event_action = $(this).attr("class")=="expand-btn" ? "header_agRewards_hideRewards" : "header_agRewards_showRewards";
    utag_data.event_action_type = "click";
	utag_data.event_detail = $(this).attr("class")=="expand-btn" ? "header_agRewards_hideRewards" : "header_agRewards_showRewards";
	utag_data.rewardMembershipId = $(".usr-name-section .usr-membership").text();
	utag_data.rewardListAvailable=$(".rewards-total").text() ? $(".rewards-total").text().trim() : "";
	utag_data.rewardStatus=$(".loyaltyRewardsDrawerComponent .pencil-drawer .userProfileComponent #loyalityComponentContainer .enhanced-container").attr("data-active-tier");
	utag_data.rewardApplied=list.toString();
    utag_data.rewardPoints = "";
	utag.link(utag_data);
	e.stopPropagation();
});
/* Show and Hide Rewards click in the loyalty rewards drawer - end */

/* Loyalty Carousel component analytics - start */
$("body").on("click", ".promoPencilContainer .slick-prev, .promoPencilContainer .slick-next, #loyalityComponentContainer .slick-prev, #loyalityComponentContainer .slick-next", function() {
    loyaltyCarouselClickEvent("arrow");
});

$("body").on("click", ".slick-dots li", function() {
	loyaltyCarouselClickEvent("dots");
});

$("body").on("click", ".personalization_recommendation_products div .arrow-image-next img, .personalization_recommendation_products div .arrow-image-prev img", function() {
    loyaltyCarouselClickEvent("plp_arrow");
});


function loyaltyCarouselClickEvent(itmClicked) {
    var list = [];
    $(".redeemed-on .promo-description .default-pts").each(function() {
        var offerName = $(this).attr("data-title") || "";
        list.push(offerName);
    });

    if (itmClicked == 'plp_arrow') {
        utag_data.event_action = "carousel_slider_toggle"
        utag_data.event_action_type = "click"
        utag_data.event_detail = 'personalization_recommendation_products';
        utag_data.personalization_strategy = $('.personalization_recommendation_products').find('h2').text();
        utag_data.location_name = "personalization_product_carousel";
        utag.link(utag_data);
    } else {
        utag_data.event_action = itmClicked == "dots" ? "header_toggle" : "header_sliderArrow";
        utag_data.event_action_type = "click";
        utag_data.event_detail = itmClicked == "dots" ? "header_toggle" : "header_sliderArrow";
        utag_data.rewardStatus = $(".loyaltyRewardsDrawerComponent .pencil-drawer .userProfileComponent #loyalityComponentContainer .enhanced-container").attr("data-active-tier");
        utag_data.rewardMembershipId = $(".usr-name-section .usr-membership").text();
        utag_data.rewardListAvailable = $(".rewards-total").text() ? $(".rewards-total").text().trim() : "";
        utag_data.rewardApplied = list.toString();
        utag_data.rewardPoints = "";
        utag.link(utag_data);
    }
}

/* Loyalty Carousel component analytics - end */

//Global Header Redeem Drawer - Remove Rewards - Click Event
function removePromoCodeClick(ele,errorMsg) {
	clearPreviousData();
	var fullDate = new Date()
	var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
	var currentDate = twoDigitMonth + "/" + fullDate.getDate() + "/" + fullDate.getFullYear();
	var dateField = $(ele).parents().siblings(".right-col .sub-label").text() || "";
	var expDate = dateField.substring(4,dateField.length);
	var redeem_code_id = $(ele).attr("data-promo-code") ? $(ele).attr("data-promo-code").trim() : "";
	var productDiscount = $(ele).attr("data-award-amt") ? $(ele).attr("data-award-amt").trim() : "";
	var rewardRemoved = $(ele).parents(".usr-points").find(".promo-description").text().trim() || "";
	utag_data.event_detail = "header_agRewards_remove";
	utag_data.event_action = "header_agRewards_remove";
    utag_data.event_action_type = "click";
	utag_data.rewardStatus=$(".loyaltyRewardsDrawerComponent .pencil-drawer .userProfileComponent #loyalityComponentContainer .enhanced-container").attr("data-active-tier");
	utag_data.rewardMembershipId=$(".usr-name-section .usr-membership").text();
	utag_data.rewardRemoved = rewardRemoved;
	utag_data.rewardPoints = "";
	utag_data.rewardListAvailable=$(".rewards-total").text() ? $(".rewards-total").text().trim() : "";
	utag_data.rewardExpirationInDays = expirationInDays(currentDate,expDate);
	utag_data.promo_code = redeem_code_id;
	utag_data.product_discount = productDiscount;
	utag_data.rewardError=errorMsg == "success" ? "" : errorMsg;
	utag.link(utag_data);
}

//Global Header Redeem Drawer - Redeem - Click Event
function trackRedeemClick(ele,errorMsg) {
	clearPreviousData();
	var fullDate = new Date()
	var twoDigitMonth = ((fullDate.getMonth().length+1) === 1)? (fullDate.getMonth()+1) : '0' + (fullDate.getMonth()+1);
	var currentDate = twoDigitMonth + "/" + fullDate.getDate() + "/" + fullDate.getFullYear();
	var dateField = $(ele).parents().siblings(".right-col .sub-label").text() || "";
	var expDate = dateField.substring(4,dateField.length);
	var redeem_code_id = $(ele).attr("data-promo-code") ? $(ele).attr("data-promo-code").trim() : "";
	var productDiscount = $(ele).attr("data-award-amt") ? $(ele).attr("data-award-amt").trim() : "";
	var rewardApplied = $(ele).parents(".usr-points").find(".promo-description").text().trim() || "";
	utag_data.event_detail = "header_agRewards_redeem";
	utag_data.event_action = "header_agRewards_redeem";
    utag_data.event_action_type = "click";
	utag_data.rewardStatus=$(".loyaltyRewardsDrawerComponent .pencil-drawer .userProfileComponent #loyalityComponentContainer .enhanced-container").attr("data-active-tier");
	utag_data.rewardMembershipId=$(".usr-name-section .usr-membership").text();
	utag_data.rewardApplied = errorMsg == "success" ? rewardApplied : "Rewards not applied";
	utag_data.rewardPoints = "";
	utag_data.rewardListAvailable=$(".rewards-total").text() ? $(".rewards-total").text().trim() : "";
	utag_data.rewardExpirationInDays = expirationInDays(currentDate,expDate);
	utag_data.promo_code = redeem_code_id;
	utag_data.product_discount = productDiscount;
	utag_data.rewardError=errorMsg == "success" ? "" : errorMsg;
	utag.link(utag_data);
}

function expirationInDays (currentDate,expDate){
	var currentDateArray =currentDate.split('/'); //splits the date string by '/' and stores in a array.
	var expDateArray =expDate.split('/');
	var currentDateMonth = currentDateArray[0];
	var currentDateDay = currentDateArray[1];
	var currentDateYear = currentDateArray[2];
	var expDateMonth = expDateArray[0];
	var expDateDay = expDateArray[1];
	var expDateYear = expDateArray[2];
	var dateTemp1=new Date(currentDateYear, (parseInt(currentDateMonth)-1),currentDateDay);
	var dateTemp2=new Date(expDateYear, (parseInt(expDateMonth)-1),expDateDay);
	var expirationInDays= Math.ceil(((dateTemp2.getTime()-dateTemp1.getTime())/(1000*60*60*24)));
	return expirationInDays;
}
function setProductUtagDetails(age){
    var review_text = $('.read-reviews').text();
    utag_data.location_name = "product details section";
    utag_data.product_age = age || "not applicable";
    utag_data.product_price = $('.product-price .current_price').text() || "";
    utag_data.product_average_rating = $('.product-container .rating-section span').text() || "not applicable";
    utag_data.products_total = $('#minishopcart_total').text() || "";
    utag_data.product_availability = $('.product-container .inventory-status-message').text() || "Available";
    utag_data.product_reviews_count = review_text.match(/\d+/) ? review_text.match(/\d+/)[0] : review_text;
    utag_data.product_variant = ($('.color_and_size').length) ? 'variant available' : 'not applicable';
    utag_data.product_list_price = $('.product-price .offer_price').text() || $('.product-price .current_price').text();
    utag_data.product_original_price = $('.product-price .offer_price').text() || $('.product-price .current_price').text();
    utag_data.product_unit_price = $('.product-price .current_price').text() || "not applicable";
    utag_data.product_discount = $('.price-saved .discount_price').text() || "not applicable";
    utag_data.product_color = $('.modal-body .active .img-swatch').attr('alt') || "not applicable";
    utag_data.product_size = $('.color_swatch_selected .swatch_text').text() || "not applicable";
}
function setPromoCodeList(){
	if(window.localStorage.getItem("USR_LOYALITY_DATA")){
    var consumerData = JSON.parse(window.localStorage.getItem("USR_LOYALITY_DATA"));
    var redeemedPromoCodeList = [];
        if(consumerData.consumerPromotions){
            var promotionsList = consumerData.consumerPromotions;
            for(var i=0; i<promotionsList.length; i++){
                if(promotionsList[i].RedeemedStatus == "REDEEMED"){
                    redeemedPromoCodeList.push(promotionsList[i].RewardsPromoCode)
                }
            }
            if(redeemedPromoCodeList.length){
                utag_data.promo_code = redeemedPromoCodeList.toString().toLowerCase();
            }
        }
	}
}