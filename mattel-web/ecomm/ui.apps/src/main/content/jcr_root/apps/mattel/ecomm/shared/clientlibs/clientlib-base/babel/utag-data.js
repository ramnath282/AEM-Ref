let cleanUrl = window.location.pathname.replace(".html", "");
let path = window.location.pathname;
let urlArray = path.split("/");
let page = urlArray.pop();
if (page.length == 0 && urlArray.length > 1) {
	page = urlArray[urlArray.length - 1];
}
let page_name = page.replace(".html", "");
let language = $("html").attr("lang") ? $("html").attr("lang") : "";
let utag_data;
let site_region = "";
let page_type = $("#pageType").val();
let store_id = $("#storeId").val();
// Function for identifying platform

let device = function() {
	if (navigator.userAgent.match(/Tablet|iPad/i)) {
		return "Tablet";
	} else if (navigator.userAgent
			.match(/Mobile|Windows Phone|Lumia|Android|webOS|iPhone|iPod|Blackberry|PlayBook|BB10|Opera Mini|\bCrMo\/|Opera Mobi/i)) {
		return "Mobile";
	} else {
		return "Desktop"
	}
};
let site_name = function() {
	if (window.location.href.includes('/shop.html')
			|| window.location.href.includes('/shop/')) {
		return "shop"
	} else {
		return "";
	}
};
let visitor_status = function() {
	let vStatus = $.cookie('returning_visitor');
	if (vStatus != null) {
		return "returning_visitor";
	} else {
		return "new_visitor";
	}
};
let utag_common_data = {
	"page_name" : "ag:" + site_region + ":" + site_type + ":" + page_name,
	"page_type" : page_type,
	"clean_url" : cleanUrl,
	"company_division" : "american girl shop",
	"category_id" : "",
	"email_capture_location" : "account",
	"platform" : device(),
	"referring_url" : document.referrer,
	"requested_url" : window.location.href,
	"site_section" : site_name,
	"site_language" : $("html").attr("lang"),
	"site_currency" : "usd",
	"site_region" : "us",
	"site_type" : site_name,
	"store_id" : store_id,
};

function additionalDatas() {
	let myaccount_data = {
		"authentication_status" : "true",
		"customer_id" : "8237572",
		"customer_country" : "the country selected in default address in address book.",
		"customer_email_hash" : "customer email in hashed format",
		"customer_type" : "new",
		"logged_in_status" : "Registered",
		"loyalty_level" : "silver/berry/platinum",
		"loyalty_id" : "membership number from ag rewards section",
		"loyalty_points" : "points from ag rewards section",
		"loyalty_dollars_to_next_tier" : "dollars to next tier from ag rewards section",
		"loyalty_tier_expiration_date" : "tier expiration date from ag rewards section",
		"optin_status" : "true",
		"visitor_status" : visitor_status,
		"wcsrefid" : "55474747",
		"cart_total_items" : "2",
		"cart_total_value" : "350",
	};
	let signin_data = {
		"visitor_status" : visitor_status,
	};
	if (window.location.pathname.includes("/my-account")) {
		utag_data = Object.assign(utag_common_data, myaccount_data);
	} else if (window.location.pathname.includes("/signin")) {
		utag_data = Object.assign(utag_common_data, signin_data);
	} else {
		utag_data = utag_common_data;
	}
}
let clickevent_data = {
	"event_action" : "",
	"event_action_type" : "",
	"event_detail" : "",
	"event_detail_sub" : "",
	"location_name" : "",
}
let formtracking_data = {
	"event_name" : "",
	"event_type" : "",
	"event_detail" : "",
	"event_detail_sub" : "",

}
let initialUtagData = $.extend({}, utag_data);
let clearPreviousData = function() {
	utag_data = $.extend({}, initialUtagData);
}

$(document).ready(function() {
	$('body').on("click", "a", function() {
		if ($(this).parents("dropmenu")) {
			clearPreviousData();
			clickevent_data.event_action = "click event"
			clickevent_data.event_action_type = "click"
			clickevent_data.event_detail = "account navigation"
			let currentPagePath = $(this).attr("href")
			let currentpage = currentPagePath.split("/").pop();
			let currentPageName = currentpage.replace(".html", "");
			clickevent_data.event_detail_sub = currentPageName
		}
	})
});
