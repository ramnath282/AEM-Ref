var cleanUrl = window.location.pathname.replace(".html", "");
var path = window.location.pathname;
var urlArray=path.split("/");
var page = urlArray.pop();
if (page.length==0 && urlArray.length >1){

	page=urlArray[urlArray.length-1];
}
var page_name = page.replace(".html", "");
var pageTitle = $(document).find("title").text();
var selectLocationClickFlag = false;
var language = $("html").attr("lang")?$("html").attr("lang"):"";
var componentName="";
var articleTitle = "";
var socialShareClicked = "";

//Function for identifying platform

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
//Function for identifying site name
var siteName = function() {
    if(window.location.href.indexOf('/retail') >= 0 ) {
        return "Retail"
    }
    else if(window.location.href.indexOf('/explore') >= 0) {
        return "Explore"
    }
    else if(window.location.href.indexOf('/discover') >= 0 ) {
        return "Discover"
    }
    else {
        return "";
    }
};

var utag_data = {
    "breadcrumb": "",
    "category_id": cleanUrl.substring(1, cleanUrl.length).replace(/\//g,':').toUpperCase(),
    "clean_url": cleanUrl,
    "company_division": "American Girl "+siteName(),
    "currency": "",
    "customer_id": "",
    "domain": window.location.hostname,
    "hash": "",
    "pathname": window.location.pathname,
    "query_string": location.search,
    "referrer": document.referrer,
    "title": pageTitle,
    "url": window.location.href,
    "ip_address": "",
    "site_language": $("html").attr("lang"),
    "page_id": "AG:"+language.toUpperCase()+cleanUrl.replace(/\//g,':').toUpperCase(),
    "page_name": siteName().toUpperCase()==page_name.toUpperCase()?"AG:US:"+siteName().toUpperCase():"AG:US:"+siteName().toUpperCase()+':'+page_name.toUpperCase(),
    "page_subtype": "",
    "page_type": siteName().toUpperCase(),
    "platform_attr5": device(),
    "referring_url": document.referrer,
    "requested_url": window.location.href,
    "site_section": siteName().toUpperCase(),
    "event_action": "",
    "event_action_type": "",
    "event_detail": "",
    "event_detail_sub": ""
}

var initialUtagData =  $.extend({}, utag_data);
var clearPreviousData = function (){
    utag_data = $.extend({}, initialUtagData);
}

$(document).ready(
    function() {
        //clearPreviousData();

        $('body').on("click", ".store-address a", function() {
            clearPreviousData();
            utag_data.event_action_type = "click"
            utag_data.event_action = "Click Event"
            utag_data.event_detail = pageTitle + "-direction"
            utag_data.event_detail_sub = "Get Direction"
            utag.link(utag_data);
        });

        /*
        Code for populating close of popup modal.
        */
        $('#selectLocationModal').on('hidden.bs.modal', function() {
            clearPreviousData();
            utag_data.event_action = "Store Selection"
            utag_data.event_action_type = "click"
            utag_data.event_detail = "Select a Location- close"
            utag_data.event_detail_sub = "Close"
            utag.link(utag_data);
        });


        $("body").on("click", "a", function() {
                // select-location
                if ($(this).hasClass('select-location')) {
                    clearPreviousData();
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    var currentPagePath = $(this).attr("href")
                    var currentpage = currentPagePath.split("/").pop();
                    var currentPageName = currentpage.replace(".html", "");
                    utag_data.event_detail = "Select a location";
                    utag_data.event_detail_sub = currentPageName;
                }

                else if($(this).parent().hasClass('back-to-top')){
                    clearPreviousData();
                    utag_data.event_action = "Back To Top"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = "Back to Top"
                    utag_data.event_detail_sub = "Back to Top"
                }

                else if($(this).parent().hasClass('dropdown-content')){
                    clearPreviousData();
                    utag_data.event_action_type = "click"
                    utag_data.event_action = "Store Selection"
                    utag_data.event_detail = $(this).text();
                    utag.link(utag_data);
                }

                else if($(this).parent().hasClass('footer-link')){
                    clearPreviousData();
                    utag_data.event_action = "Footer Clicks"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = "Footer Links"
                    utag_data.event_detail_sub = $(this).text();
                    utag.link(utag_data);
                }

                //EXPLORE FOOTER TRACKING START
                else if ($(this).hasClass('contact-us')) {
                    clearPreviousData();
                    utag_data.event_action = "Click Event";
                        utag_data.event_action_type = "click";
                            utag_data.event_detail = "Footer - Contact Us";
                                utag_data.event_detail_sub = $(this).find('span:last-child').text().trim();
                }

                else if ($(this).hasClass('read-more-guarantee-text-track')){
                    clearPreviousData();
                    utag_data.event_action = "Click Event";
                        utag_data.event_action_type = "click";
                            utag_data.event_detail = "Footer - " + $(this).parent().find('h2').text();
                                utag_data.event_detail_sub = $(this).text().trim();
                }

                else if ($(this).hasClass('track-sign-up')){
                    clearPreviousData();
                    utag_data.event_action = "Click Event";
                        utag_data.event_action_type = "click";
                            utag_data.event_detail = "Footer – Email sign up";
                                utag_data.event_detail_sub = $(this).text().trim();
                }

                /* Commented due to multiple times Analytics event triggered , by default footer click event is tracked
                else if ($(this).hasClass('track-link')){
                    clearPreviousData();
                    utag_data.event_action = "Click Event";
                        utag_data.event_action_type = "click";
                            utag_data.event_detail = "Footer - " + $(this).attr("data-track-title");
                                utag_data.event_detail_sub = $(this).text().trim();
                }*/

                //track app in global footer
                else if ($(this).hasClass('track-app')){
                    clearPreviousData();
                    var valuesTrackApp = $(this).attr("data-track-title").split("|");
                    utag_data.event_action = "Click Event";
                        utag_data.event_action_type = "click";
                            utag_data.event_detail = valuesTrackApp[1] + " - " + valuesTrackApp[0];
                                utag_data.event_detail_sub = valuesTrackApp[2];
                }

                //track telephone in global footer
                else if ($(this).hasClass('track-telephone')){
                    clearPreviousData();
                    var valuesTrackTelephone = $(this).attr("data-tracking-telephone").split("|");
                    utag_data.event_action = "Click Event";
                    utag_data.event_action_type = "click";
                    utag_data.event_detail = valuesTrackTelephone[1] + " - " + valuesTrackTelephone[2];
                    utag_data.event_detail_sub = valuesTrackTelephone[0]
                }

                //tracking social share
                else if ($(this).hasClass('track-social-share')){
                    clearPreviousData();
                    var valuesTrackSocial = $(this).attr("data-tracking-social").split("|");
                    clearPreviousData();
                    utag_data.event_action = "Click Event";
                    utag_data.event_action_type = "click";
                    if(valuesTrackSocial.length>1){
                        utag_data.event_detail = valuesTrackSocial[0];
                            utag_data.event_detail_sub = valuesTrackSocial[1];
                    }
                    else{
                        componentName=$(this).attr("data-componentname") ? $(this).attr("data-componentname") : "";
                        articleTitle = $(this).attr("data-title") ? $(this).attr("data-title"):"";
                        socialShareClicked = valuesTrackSocial[0];
                        utag_data.event_detail = $(this).attr("data-title") ? componentName +" - " + $(this).attr("data-title") : "Share Block";
                            utag_data.event_detail_sub = valuesTrackSocial[0];
                    }
                }

                else if ($(this).hasClass('track-read-more')){
                    clearPreviousData();
                    utag_data.event_action = "Click Event";
                        utag_data.event_action_type = "click";
                            utag_data.event_detail = "Footer - " + $(this).text().trim();
                                utag_data.event_detail_sub = $(this).text().trim();
                }

                //Fake-instagram-banner
                else if ($(this).hasClass('track-instagram-banner')){
                    clearPreviousData();
                    utag_data.event_action = "Click Event";
                        utag_data.event_action_type = "click";
                            utag_data.event_detail = "Instagram banner - Follow us on Instagram";
                                utag_data.event_detail_sub = "Follow us on Instagram";
                }

                //Explore Hero Banner
                else if ($(this).hasClass('track-explore-hero-banner')){
                    clearPreviousData();
                    var valuesExploreHero = $(this).attr("data-tracking-banner").split("|");
                    utag_data.event_action = "Click Event";
                        utag_data.event_action_type = "click";
                            utag_data.event_detail = valuesExploreHero[0] + " - " + valuesExploreHero[1]
                                utag_data.event_detail_sub = $(this).text().trim();
                }

                else if ($(this).hasClass('track-nav')){
                    clearPreviousData();
                    utag_data.event_action = "Click Event";
                    utag_data.event_action_type = "click";
                    utag_data.event_detail = $(this).attr("data-track-nav") + " - " + $(this).text();
                    utag_data.event_detail_sub = $(this).text().trim();
                }

                //EXPLORE FOOTER TRACKING END
                else if ($(this).attr("data-tracking-id")) {
                    clearPreviousData()
                    var valuesTrackingId = $(this).attr("data-tracking-id")
                    .split("|");
                    utag_data.event_action = valuesTrackingId[0];
                    utag_data.event_action_type = valuesTrackingId[1];
                    //utag_data.event_detail = values[2];
                    if (valuesTrackingId[3] == undefined && valuesTrackingId[1] !='brandsbanner' ) {
                        utag_data.event_detail_sub = $(this).html()
                        .split("<span")[0].trim();
                    } else if(valuesTrackingId[1] =='brandsbanner'){
                        utag_data.event_detail_sub = valuesTrackingId[2];
                    }
                    else {
                        utag_data.event_detail_sub = valuesTrackingId[3];
                    }

                    if ($(this).hasClass('accordion')) {
                        var subsection = "";
                        if ($(this).parent() != undefined) {
                            subsection = $(this).parent().siblings('.tile-content').find('.tile-name').text()
                        }
                        var parents = $(this).parentsUntil(".cardContainer");
                        var containerObj = "";
                        if (parents && parents.length == 7) {
                            containerObj = parents[5];
                        } else if (parents && parents.length == 8) {
                            containerObj = parents[6];
                        }
                        var parentSection = $(containerObj).find('h2').text();
                        utag_data.event_detail = parentSection + ' - ' + subsection;
                    }

                    else if (!$(this).hasClass('accordion') && $(this).hasClass('action-text')) {
                        var subsectionAccordion = "";
                        if ($(this).parent() != undefined) {
                            var par = $(this).parent()
                            subsectionAccordion = par.parent().siblings('.tile-content').find('.tile-name').text()
                        }
                        var parentsCard = $(this).parentsUntil(".cardContainer");
                        var cardContainer = "";
                        if (parentsCard && parentsCard.length == 8) {
                        	cardContainer = parentsCard[6];
                        } else if (parentsCard && parentsCard.length == 9) {
                        	cardContainer = parentsCard[7];
                        }
                        var cardParentSection = $(cardContainer).find('h2').text();
                        utag_data.event_detail = cardParentSection + ' - ' + subsectionAccordion;
                    }

                    else if ($(this).hasClass('directions-btn')) {
                        var directionsPar = $(this).parent()
                        subsection = directionsPar.parent().siblings('.tile-content').find('.tile-name').text()
                        var directionParents = $(this).parentsUntil(".cardContainer");
                        var directionContainerObj = "";
                        if (directionParents && directionParents.length == 8) {
                        	directionContainerObj = directionParents[6];
                        } else if (directionParents && directionParents.length == 9) {
                        	directionContainerObj = directionParents[7];
                        }
                        var directionParentSection = $(directionContainerObj).find('h2').text();
                        utag_data.event_detail = directionParentSection + ' - ' + subsection;
                    }

                    else if ($(this).hasClass('visit-website')) {
                        subsection = $(this).parent().parent().find('.tile-name').text()
                        var visitWebsiteParents = $(this).parentsUntil(".cardContainer");
                        var visitWebsiteContainerObj = "";
                        if (visitWebsiteParents && visitWebsiteParents.length == 8) {
                        	visitWebsiteContainerObj = visitWebsiteParents[6];
                        } else if (visitWebsiteParents && visitWebsiteParents.length == 9) {
                        	visitWebsiteContainerObj = visitWebsiteParents[7];
                        }
                        var visitWebsiteParentSection = $(visitWebsiteContainerObj).find('h2').text();
                        utag_data.event_detail = visitWebsiteParentSection + ' - ' + subsection;
                    }
                }

                //Tracking article filter
                else if ($(this).hasClass('track-article-filter')){
                    clearPreviousData();
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = "Article Filter"
                    utag_data.event_detail_sub = $(this).text().trim()
                }

                //Tracking recipe card
                else if ($(this).attr("data-tracking-recipe-card")) {
                    clearPreviousData();
                    var valuesRecipie = $(this).attr("data-tracking-recipe-card").split("|");
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = valuesRecipie[0]+" - "+valuesRecipie[1]
                    utag_data.event_detail_sub = valuesRecipie[2]
                }
                //Tracking Featured article
                else if ($(this).attr("data-tracking-featured-article")) {
                    clearPreviousData();
                    var valuesFeatureArticles = $(this).attr("data-tracking-featured-article").split("|");
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = valuesFeatureArticles[0]
                    utag_data.event_detail_sub = valuesFeatureArticles[1]
                }
              //Tracking related article
                else if ($(this).attr("data-tracking-related-article")) {
                    clearPreviousData();
                    var valuesRelatedArticles = $(this).attr("data-tracking-related-article").split("|");
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = valuesRelatedArticles[2].replace(/-/g, ' ')+" - "+valuesRelatedArticles[3].replace(/-/g, ' ');
                    utag_data.event_detail_sub = "Read more";
                }
              //Tracking Filtered article
                else if ($(this).attr("data-tracking-filtered-article")) {
                    clearPreviousData();
                    var valuesFilteredArticle = $(this).attr("data-tracking-filtered-article").split("|");
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = valuesFilteredArticle[2]+" - "+valuesFilteredArticle[3];
                    utag_data.event_detail_sub = "Read more";
                }
                else if($(this).hasClass('track-social-media-action')){
                    clearPreviousData();
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click";
                    utag_data.event_detail = componentName+" - "+articleTitle;
                    utag_data.event_detail_sub = socialShareClicked + " - " + $(this).text();
                    componentName="";
                    socialShareClicked="";
                    articleTitle = "";
                }
				else if ($(this).hasClass('nyc-360-video')) {
					clearPreviousData();
					 utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click";
					utag_data.event_detail ="Video - American Girl Look Around - 360"
                    utag_data.event_detail_sub = "Play"

				}
             else if ($(this).hasClass('track-historical-character-link')) {
                 clearPreviousData();
                 var valuesHistoricalCharacters = $(this).attr("data-tracking-historic-character").split("|");
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = valuesHistoricalCharacters[1].trim()+" - "+valuesHistoricalCharacters[2].trim();
                    utag_data.event_detail_sub = valuesHistoricalCharacters[0].trim();
            }
            else if ($(this).hasClass('track-historical-character-image')) {
                clearPreviousData();
                 var valuesHistoricalCharactersImage = $(this).attr("data-tracking-historic-character-image").split("|");
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = valuesHistoricalCharactersImage[0].trim()+' - '+valuesHistoricalCharactersImage[2].trim();
                    utag_data.event_detail_sub = valuesHistoricalCharactersImage[1].trim();
            }
             else if ($(this).hasClass('data-track-carousel-link')) {
                 clearPreviousData();
                 var carouselLink = $(this).attr("data-tracking-carousel-link").split("|");
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = carouselLink[1];
                    utag_data.event_detail_sub = carouselLink[0];
            }
            	else if($(this).hasClass('at-svc-facebook')|| $(this).hasClass('at-svc-twitter') || $(this).hasClass('at-svc-pinterest_share') || $(this).hasClass('bv-write-review-label') || $(this).hasClass('video')){
                   return false;
                }
                       else if ($(this).hasClass('category-link collapsed')){
                                if (!AGAEM.isMobile) {
                                    return false;
                                } else {
                                     clearPreviousData();
                                    utag_data.event_action = "Click Event"
                                    utag_data.event_action_type = "click"
                                    utag_data.event_detail = $(this).text().trim()
                                    utag_data.event_detail_sub = ""
                                }
                            }

                else {
                    //General anchor link tracking
                    clearPreviousData();
                    utag_data.event_action = "Click Event"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = $(this).text().trim()
                    utag_data.event_detail_sub = ""
                }
                utag.link(utag_data);
            });

        $("body").on("click","button", function() {
            if ($(this).hasClass("video-360-close") || $(this).hasClass("store-location-close") ) {
                return;
            }
            clearPreviousData()
            if ($(this).attr("data-tracking-id")) {
                clearPreviousData();
                var valuesDataTracking = $(this).attr("data-tracking-id")
                .split("|");
                utag_data.event_action = valuesDataTracking[0];
                utag_data.event_action_type = valuesDataTracking[1];
                utag_data.event_detail = valuesDataTracking[2];
                if (valuesDataTracking[3] == undefined) {
                    utag_data.event_detail_sub = $(this).html()
                    .split("<span")[0].trim();
                } else {
                    utag_data.event_detail_sub = valuesDataTracking[3];
                }
                if ($(this).hasClass('reservation-btn')) {
                    var subsectionReservation = $(this).parent().find('.tile-name').text()
                    var parents = $(this).parentsUntil(".cardContainer");
                    var containerObj = "";
                    if (parents && parents.length == 7) {
                        containerObj = parents[5];
                    } else if (parents && parents.length == 8) {
                        containerObj = parents[6];
                    }
                    var parentSection = $(containerObj).find('h2').text();
                    utag_data.event_detail = parentSection + ' - ' + subsectionReservation;
                }
                utag.link(utag_data);
            }

            else if($(this).hasClass("slick-prev")){
            clearPreviousData();
            utag_data.event_action = "navigating arrows"
           	utag_data.event_action_type= "click"
            utag_data.event_detail= "Carousel-characters"
            utag_data.event_detail_sub= "left"
            utag.link(utag_data);

            }
            else if($(this).hasClass("slick-next")){
            clearPreviousData();
            utag_data.event_action = "navigating arrows"
           	utag_data.event_action_type= "click"
            utag_data.event_detail= "Carousel-characters"
            utag_data.event_detail_sub= "right"
            utag.link(utag_data);

            }
            else if ($(this).parent().parent().hasClass("slick-dots")){
            clearPreviousData();
            utag_data.event_action = "navigating dots"
            utag_data.event_action_type= "click"
			utag_data.event_detail= "Carousel-characters"
			utag_data.event_detail_sub= $(this).text();
            utag.link(utag_data);
            }

            else if ($(this).attr("data-tracking-events")) {
                clearPreviousData();
                var valuesTrackEvents = $(this).attr("data-tracking-events")
                .split("|");
                utag_data.event_action = valuesTrackEvents[0].replace(/-/g, ' ');
                utag_data.event_action_type = valuesTrackEvents[1];
                utag_data.event_detail = valuesTrackEvents[2].replace(/-/g, ' ');
                if (valuesTrackEvents[3] == undefined) {
                    utag_data.event_detail_sub = $(this).html()
                    .split("<span")[0].trim();
                } else {
                    utag_data.event_detail_sub = valuesTrackEvents[3].replace(/-/g, ' ');
                }
                utag_data.event_detail = valuesTrackEvents[2].replace(/-/g, ' ') +" - "+ valuesTrackEvents[4].replace(/-/g, ' ');
                utag.link(utag_data);
            }

            else if($(this).hasClass('analytics-track-store-locator')){
            clearPreviousData();
            selectLocationClickFlag = true;
            utag_data.event_action_type = "click"
            utag_data.event_action = "Store Selection"
            utag_data.event_detail = "Select a location"
            utag.link(utag_data);
            }
            else if ($(this).attr("data-tracking-filter-showmore")){
                clearPreviousData();
                utag_data.event_action_type = "click"
               utag_data.event_action = "Click Event"
               utag_data.event_detail = $(this).attr("data-tracking-filter-showmore");
               utag_data.event_detail_sub = $(this).text();
                utag.link(utag_data);
            }

        });
    });

	//Video-360 Close functionality
	$('#VRModal').on('hidden.bs.modal', function() {
			  clearPreviousData();
                    utag_data.event_action = "Video Clicks"
                    utag_data.event_action_type = "click"
                    utag_data.event_detail = "Video - American Girl Look Around - 360"
                    utag_data.event_detail_sub = "Video Close"
                    utag.link(utag_data);

	});
	//Search Functonality Starts
    $( "#SimpleSearchForm_SearchTerm" ).focus(function() {

     $("body").on("click",".rfk_list li a", function () {
    populateSearchData();
    	utag_data.search_keywords= $(this).text();
         utag_data.search_category=$(this).parent().parent().parent().find("span:first").text();
    	utag.link(utag_data);

    });
         $("body").on("click",".rfk_products .rfk_product a", function () {
       populateSearchData();
    	utag_data.search_keywords= $(this).text();
             utag_data.search_category="Product"
    	utag.link(utag_data);

    });
        $("input").on("keydown",function search(e) {
        if(e.keyCode == 13) {
            populateSearchData();
    	utag_data.search_keywords= $(this).val();
            utag_data.search_category=""
    	utag.link(utag_data);
        }
    });
           $("body").on("click",".fa-search", function () {
       populateSearchData();
    	utag_data.search_keywords= document.getElementById('SimpleSearchForm_SearchTerm').value;
               utag_data.search_category=""
    	utag.link(utag_data);

    });

    });
    function populateSearchData() {
                  var sessionCount = 0;
         var getSessionCount = 0;
        clearPreviousData();
    	 if (sessionStorage.getItem("sessionCount") === null){
             sessionStorage.setItem("sessionCount", sessionCount+1);
         } else {
    		getSessionCount = sessionStorage.getItem("sessionCount");
    		getSessionCount = (parseInt(getSessionCount))+1;
             sessionStorage.removeItem('sessionCount');
             sessionStorage.setItem("sessionCount", getSessionCount);
         }
    	utag_data.event_action = "Search Section"
        utag_data.event_action_type = "click"
        utag_data.event_detail = "navigation:search_submit"
          utag_data.search_depth= sessionStorage.getItem("sessionCount");
    }

    //Search Functonality Ends

//Post a review bazar-voice
var postAreview = function(res) {
    clearPreviousData();
    utag_data.event_action = "Review Section"
    utag_data.event_action_type = "click"
    utag_data.event_detail = res.Id+" - Post review"
    utag_data.event_detail_sub = "Post review"
    utag.link(utag_data);
};

// Shopper page form submit
var shopperFormSubmit = function(res) {
    clearPreviousData();
    utag_data.event_name = "Personal Shopping"
    utag_data.event_type = "click"
    utag_data.event_detail = "Personal Shopping Contact Form"
    utag_data.event_detail_sub = "Submit"
    utag_data.customer_email_hash = res.email
    utag_data.location = res.location
    utag_data.anticipated_visit = res.anticipated_visit
    utag_data.contact_method = res.contact_method
    utag_data.first_visit = res.first_visit
    utag_data.terms_and_condition = res.terms_and_condition
    utag.link(utag_data);
};

var shopperFormFailure = function (res) {
    clearPreviousData();
    utag_data.event_name = "Form Failure"
    utag_data.event_type = "click"
    utag_data.event_detail = "Personal Shopping Contact Form"
    utag_data.item_clicked = "Personal Shopper Request Form"
    utag_data.error_name = ""
    utag_data.form_errors = res.error_field
    utag.link(utag_data);
};

var checkFormHasData = function(res){
    clearPreviousData();
    utag_data.event_name = "Form-Abandonment"
    utag_data.event_type = "click"
    utag_data.event_item_clicked = "Personal Shopper Request Form"
    utag_data.event_last_accessed_field = res.last_accessed_field
    utag_data.close_type = res.type
    utag.link(utag_data);
};

var carouselArrowNavigation = function(clickType,navArrow,dotPosition){
    clearPreviousData();
    if(clickType == "Arrow"){
    utag_data.event_name = "navigating arrows"
    utag_data.event_type = "click"
    utag_data.event_detail = "Carousel"
    utag_data.event_detail_sub = navArrow
    }else if(clickType == "Dots"){
    utag_data.event_name = "navigating dots"
    utag_data.event_type = "click"
    utag_data.event_detail = "Carousel"
    utag_data.event_detail_sub = dotPosition
    }

    utag.link(utag_data);
}; 
