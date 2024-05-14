var pageId = '';
var szProductId = '';
var szEventId = '';
var szEventCategoryId = '';
var szEventAction = '';
var szEventDetail = '';
var szEventSubDetail = '';
var szEventActionType = '';
var szEventType = '';
var szEventPoint = '';
var szEVentConvId = '';

var siteSection = '';
var siteType = '';

var CustomerId = '';
var ReviewDate = '';
var ReviewId = '';

// Added for Core Metrics - Starts
var szManualLink = '';
var szManualLinkName = '';
var gameName = '';
var searchTerm = '';
var searchText = '';
var refinementId = '';
var region = '';
var currency = '';

var szBrandName = '';
var tealiumTrack = '';
var isTrackable = false;
var SKU = '';

$(document).ready(function() {
    if (window.location.href.toLowerCase().indexOf('/parenting-articles/') >= 0) {
	$(document).on("click", "a", function() {
	    var clsname = $(this).attr("class");
	    var parentclsname = $(this).closest('div').attr("class");
            if ((clsname != undefined && clsname.indexOf("at-recommendedTitle") == 0) || (parentclsname != undefined && parentclsname.indexOf("at4-recommended-item-img") == 0)) {
	        szEventCategoryId = "Int. Promo";
	        szEventId = "Clicking on Recirculated Article : article recirculated content";
	        szEventDetail = $(this).attr("title");
			if (localInfo == "/en_US")
			{
			utag.link({ event_action : "click", event_category: szEventCategoryId, event_label: szEventId + ':' + szEventDetail, event_name: "link_click" });	
			}
			else
			{
			utag.link({ eve_act: "click", eve_cat: szEventCategoryId, eve_lab: szEventId + ':' + szEventDetail, eve_des: "link_click" });
			}
	    }
		else
		{
			FireTealiumTrack($(this));
		}
	});
    } else if (window.location.href.toLowerCase().indexOf('/brands/') >= 0 && window.location.href.toLowerCase().indexOf('/index.html') >= 0) {
	$(document).on("click", "a", function() {
	    var clsname = $(this).attr("class");
            if ((clsname != undefined && clsname.indexOf("ps_WtbButtonStyle") >= 0)) {
	        szEventCategoryId = "Int. Promo";
	        szEventId = "Brand Landing Page carousel - Where To Buy";
	        szEventDetail = $(this).closest('div').attr("data-skuid");
	        if (localInfo == "/en_US")
			{
			utag.link({ event_action : "click", event_category: szEventCategoryId, event_label: szEventId + ':' + szEventDetail, event_name: "link_click" });	
			}
			else
			{
			utag.link({ eve_act: "click", eve_cat: szEventCategoryId, eve_lab: szEventId + ':' + szEventDetail+ '- WTB button click', eve_des: "link_click", language_attr7: utag_data.language_attr7, site_country_attr3: utag_data.site_country_attr3, site_section_attr8: utag_data.site_section_attr8, page_id: utag_data.page_id, page_type_attr10: utag_data.page_type_attr10, page_subtype_attr11: utag_data.page_subtype_attr11, page_name_attr9: utag_data.page_name_attr9, product_age_range: utag_data.product_age_range, product_id: utag_data.product_id, product_name: utag_data.product_name, product_base_price_attr22: utag_data.product_base_price_attr22, brand_attr20: utag_data.brand_attr20 });
			}
	    }
            } else if (clsname != undefined && (clsname.indexOf("ps_SellerNameStyle") >= 0 || clsname.indexOf("ps_BuyButtonStyle")) >= 0) {
	        szEventCategoryId = "Ext. Promo";
			szEventId = "Price Spider - Retailer";
			if($(this).attr("title"))
			{
	        szEventDetail = $(this).attr("title");
			}else
			{
			szEventDetail = $(this).children("img").attr("title");
			}
	        utag.link({ eve_act: "click", eve_cat: szEventCategoryId, eve_lab: szEventId + ':' + szEventDetail + '-' + SKU + '- WTB retailer click', eve_des: "WTB retailer click", language_attr7: utag_data.language_attr7, site_country_attr3: utag_data.site_country_attr3, site_section_attr8: utag_data.site_section_attr8, page_id: utag_data.page_id, page_type_attr10: utag_data.page_type_attr10, page_subtype_attr11: utag_data.page_subtype_attr11, page_name_attr9: utag_data.page_name_attr9, product_age_range: utag_data.product_age_range, product_id: utag_data.product_id, product_name: utag_data.product_name, product_base_price_attr22: utag_data.product_base_price_attr22, brand_attr20: utag_data.brand_attr20 });
	    }		
		else
		{
			FireTealiumTrack($(this));
		}		
	});
    } else if (window.location.href.toLowerCase().indexOf('/products/') >= 0) {
	$(document).on("click", "a", function() {
	    var clsname = $(this).attr("class");
		var customid = $(this).closest('div').attr("data-campaignid");
            if ((clsname != undefined && clsname.indexOf("ps_WtbButtonStyle") >= 0 && customid == "FP_Product_Page_Carousel")) {
			szEventCategoryId = "Int. Promo";
	        szEventId = "Product Detail Page Carousel - Where To Buy";
			SKU = $(this).closest('div').attr("skuid");
	        szEventDetail = $(this).closest('div').attr("skuid");
	        utag.link({ eve_act: "click", eve_cat: szEventCategoryId, eve_lab: szEventId + ':' + szEventDetail, eve_des: "link_click", language_attr7: utag_data.language_attr7, site_country_attr3: utag_data.site_country_attr3, site_section_attr8: utag_data.site_section_attr8, page_id: utag_data.page_id, page_type_attr10: utag_data.page_type_attr10, page_subtype_attr11: utag_data.page_subtype_attr11, page_name_attr9: utag_data.page_name_attr9, product_age_range: utag_data.product_age_range, product_id: utag_data.product_id, product_name: utag_data.product_name, product_base_price_attr22: utag_data.product_base_price_attr22, brand_attr20: utag_data.brand_attr20 });
	    }
	    else if ((clsname != undefined && clsname.indexOf("ps_WtbButtonStyle") > 0 && customid != "FP_Product_Page_Carousel"))
	    {
	        szEventCategoryId = "Int. Promo";
	        szEventId = "Product Detail Page - Where To Buy";
			SKU = $(this).closest('div').attr("skuid");
	        szEventDetail = $(this).closest('div').attr("skuid");
	        utag.link({ eve_act: "click", eve_cat: szEventCategoryId, eve_lab: szEventId + ':' + szEventDetail + '- WTB button click', eve_des: "WTB button click", language_attr7: utag_data.language_attr7, site_country_attr3: utag_data.site_country_attr3, site_section_attr8: utag_data.site_section_attr8, page_id: utag_data.page_id, page_type_attr10: utag_data.page_type_attr10, page_subtype_attr11: utag_data.page_subtype_attr11, page_name_attr9: utag_data.page_name_attr9, product_age_range: utag_data.product_age_range, product_id: utag_data.product_id, product_name: utag_data.product_name, product_base_price_attr22: utag_data.product_base_price_attr22, brand_attr20: utag_data.brand_attr20 });
	    }		
		else if (clsname != undefined && (clsname.indexOf("ps_SellerNameStyle") > 0 || clsname.indexOf("ps_BuyButtonStyle")) > 0) 
		{
	        szEventCategoryId = "Ext. Promo";
	        szEventId = "Price Spider - Retailer";
			if($(this).attr("title"))
			{
	        szEventDetail = $(this).attr("title");
			}else
			{
			szEventDetail = $(this).children("img").attr("title");
			}
	        utag.link({ eve_act: "click", eve_cat: szEventCategoryId, eve_lab: szEventId + ':' + szEventDetail + '-' + SKU + '- WTB retailer click', eve_des: "WTB retailer click", language_attr7: utag_data.language_attr7, site_country_attr3: utag_data.site_country_attr3, site_section_attr8: utag_data.site_section_attr8, page_id: utag_data.page_id, page_type_attr10: utag_data.page_type_attr10, page_subtype_attr11: utag_data.page_subtype_attr11, page_name_attr9: utag_data.page_name_attr9, product_age_range: utag_data.product_age_range, product_id: utag_data.product_id, product_name: utag_data.product_name, product_base_price_attr22: utag_data.product_base_price_attr22, brand_attr20: utag_data.brand_attr20 });
	    }
		else
		{
			FireTealiumTrack($(this));
		}		
	});
	}
	else
	{
	$(document).on("click", "a", function() {	
	FireTealiumTrack($(this));
	});
	}
	
	$(".tr_tr .catalogListItem a").click(function () {
	    utag_data.dc_event = "click";
	});

	$(".tr_tr #email-signUp-tile a").click(function () {
	    utag_data.dc_event = "click";
	});	
});

function FireTealiumTrack(ctrl) {

    GetAllPropertyValues(ctrl);
    if (isTrackable) {
		
		if (localInfo == "/en_US")
		{
		utag.link({ event_action : "click", event_category: szEventCategoryId, event_label: szEventId + ':' + szEventDetail, event_name: "link_click" });	
		}
		else
		{
        utag.link({ eve_act : "click", eve_cat: szEventCategoryId, eve_lab: szEventId + ':' + szEventDetail, eve_des: "link_click" });
		}
    }
}

function GetAllPropertyValues(ctrl) {
    if (ctrl != undefined) {
        var trackVal = ctrl.attr('tealiumtrack');
        if (trackVal != undefined) {
            isTrackable = true;
            var arrTrack = trackVal.split('|');
            if (arrTrack != undefined) {
                if (arrTrack.length > 0) {
                    for (var i = 0; i <= arrTrack.length; i++) {
                        if (i == 0)
                            szEventCategoryId = arrTrack[0];
                        if (i == 1)
                            szEventId = arrTrack[1];
                        if (i == 2)
                            szEventDetail = arrTrack[2];
                    }
                }
            }
        }
        
    }
}






    
    
        

    




