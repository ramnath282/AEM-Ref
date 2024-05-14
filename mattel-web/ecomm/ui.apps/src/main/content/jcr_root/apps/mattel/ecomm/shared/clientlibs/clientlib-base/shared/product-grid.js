import { getDeviceName } from './constant';
import eventBinding from './eventBinding';
import { renderVideoPlayer } from './product-espot-videos';
import { handleBarTemplate } from './templateSetter';
import { truncateInit } from './textTruncate';
import { setDynamicId } from './_videoPlayer';
import { setCookie } from '../shared/browserCookie';

export const datasets = document.querySelector('.grid-lists').dataset;
let fpPage = $("#isFPPage").val() || "false";
const config = {
	el: '.grid-lists',
	deviceName: getDeviceName(),
	desktopTextLine: 2,
	tabletTextLine: 2,
	mobileTextLine: 3,
	defaultLoad: parseInt(datasets.initialLoadcount || 7),
	defaultLoadCnt: 0,
	nextLoad: parseInt(datasets.productLoadcount || 4),
	pageNo: 0,
	lazyScroll: true,
	detailUrlPrefix: location.protocol + '//' + location.hostname, //window.location.href.split('?')[0],
	isSearchModeON: $("#isSearchMode").val() || false,
	isFPPage: fpPage,
	showRatingsReviewsPLP : $('#ratingsReviewsPLPToggle').val() || "true",
	windowUrl: window.location.href,
	lazyLoadEnabled: {
		desktop: fpPage ? false: true,
		tablet: fpPage ? false: true,
		mobile: false
	}
};

export const clearStoredData = () => {
	self.gridResponse = '';
	config.pageNo = 0;
};
export const relatedGridTemplate = (res, schemaObj) => {
	renderTemplate(
		'#productGridTemp',
		'#relatedGridLists',
		res,
		'replace',
		undefined,
		schemaObj
	);
	$(".related-grid-lists").removeClass('hide');
	checkTruncateText("#relatedGridLists");
};
export const renderGridTemplate = (generalResponse, res, isFilterClick, browserResizing, priceObjName, schemaObj, seeMoreConfig = {}) => {
	setCookie("search_res_count","",0);
	let { seeMoreAction } = seeMoreConfig;
	let { nextIndex } = seeMoreConfig;
	isFilterClick = seeMoreAction ? false : isFilterClick;
	config.defaultLoadCnt = getDefaultScrollCnt(isFilterClick);
	(isFilterClick || seeMoreAction) && clearStoredData();
	let gridContainer = '#product-grid-container';
	self.gridResponse = browserResizing ? res : self.gridResponse || res || {};
	self.generalResponse = generalResponse == undefined ? self.generalResponse : generalResponse;
	self.schemaData = schemaObj == undefined ? self.schemaData : schemaObj;
	const slicedObj = sliceObject(self.gridResponse, browserResizing, isFilterClick, seeMoreAction) || {};
	renderTemplate(
		'#productGridTemp',
		gridContainer,
		slicedObj.obj,
		(isFilterClick || browserResizing) && 'replace',
		priceObjName,
		self.schemaData
	);
	handleBarTemplateInst.loadTemplate('#seeMoreTemp', '#see-more', slicedObj.obj, 'replace');
	$('.searchResultTitle').addClass('visibleDiv');
	$('.product-title-bar').removeClass('hide');
	$('.plp-no-search, .product-grid-search-result, .product-grid-heading, .product-grid-text-container').addClass('hide');
	$('.product-grid-no-search-result-txt, .product-grid-no-results-heading').addClass('hide');
	if(config.isFPPage == 'true') {
		$('.slider-h2').addClass('showCount');
		$('.details-list-item.grid-sort').addClass('show-sort');
		$('.plpFacetsLeftNav').find('.filter-grid').addClass('show-sort');	
	}
	if(config.isSearchModeON == 'true') {
        let $searchNotFound = $('.bc-title').find('.nosearchResult');
        let $searchFound = $('.bc-title').find('.search-result');
		let $colMdHtml = $('.col-md-9').find('.details-list-item.grid-sort');
		let $hideCount = $('.category-aside-section').find('.slide-header');
		let isFPGiftFinderSearch = window.location.search.indexOf('giftAge');
		$('.product-title-bar').addClass('hide');
		if(self.generalResponse != undefined){
			if((self.generalResponse.query == '' && isFPGiftFinderSearch ==-1) || self.generalResponse.noresults == 'true'){
				$colMdHtml.addClass('hide');
				$colMdHtml.removeClass('visible-md visible-lg');
				$searchNotFound.removeClass('hide');
				
               if(config.isFPPage == 'true') {
				setCookie("search_res_count","0");
				
                $hideCount.addClass('hide');
                $searchFound.addClass('hide');
	       $('.searchResultTitle').addClass('visibleDiv');
               }
				$('.plp-no-search, .product-grid-no-search-result-txt, .product-grid-no-results-heading, .total-filtered-cnt').removeClass('hide');
				$(".total-filtered-cnt span.total-itemcnt-inner").html("0");

				if(config.isFPPage != 'true') {
					typeof setSearchCount == "function" && setSearchCount('0');
				}
			}else{
				$colMdHtml.addClass('visible-md visible-lg');
				$('.searchResultTitle').addClass('visibleDiv');
				$('.details-list-item.grid-sort').addClass('show-sort');
				$('.plpFacetsLeftNav').find('.filter-grid').addClass('show-sort');
				$('.product-grid-search-result, .product-grid-heading, .product-grid-text-container, .details-list-item.grid-sort').removeClass('hide');
				if(config.isFPPage != 'true') {
					typeof setSearchCount == "function" && setSearchCount(self.generalResponse.total);
				}
				    if(config.isFPPage == 'true') {
                                     $('.searchResultTitle').addClass('visibleDiv');
                                     }
			}
		}
		
		if (window.location.search.indexOf('directQuery') > -1) {
			 $searchFound.addClass('hide');
			 
			 if (generalResponse && generalResponse.total > 0) {
				 $('.searchResultTitle').removeClass('visibleDiv');
				 $(".total-filtered-cnt span.total-itemcnt-inner").html(generalResponse.total);
				 $(".slide-header").removeClass('hide');
				 $(".product-grid-no-results-heading").hide();
				 $(".show-sort").removeClass('hide');
			 }
		}
		handleBarTemplateInst.loadTemplate('#productGridTxtTemp', '.product-grid-text-container', self.generalResponse, 'replace');
	}
	checkImageLoaded(gridContainer);
	checkTruncateText();
	if(config.isFPPage == 'true') {
		productBadges(); 
		productCurrencyMapList();
    }
	contentLoading = false;
	(slicedObj.focusIndex || seeMoreAction) &&
		$(config.el)
			.find(`>li:eq(${slicedObj.focusIndex || nextIndex}) a:first`)
			.focus();
};
const renderTemplate = (templateId, container, data, action, priceObjName, schemaObj) => {
	let $previewList = $(config.el).find('.preview-list');
	let source = $(templateId).html();
	let template = Handlebars.compile(source);
	let url= config.detailUrlPrefix;
    let concatUrl = '/shop/p/';
	if(config.isFPPage == 'true') {
    	concatUrl = '/content/fisher-price/us/en-us/home/product/productfinder/product-';
    }
	$.each(data, (k, v) => {
		let imageLink = v.imageLink,
			scene7Url = "";
		if(imageLink != undefined && imageLink != ""){
			scene7Url = imageLink.substring(0, imageLink.lastIndexOf("/") + 1);
			v.scene7Url = scene7Url;
		}
	});
	let output = template({
		data,
		priceName: priceObjName || 'sale_price',
		isAuthorMode: parentConfig.isAuthorModeON,
		urlPrefix: url.substring(url.length - 1) == '/' ? url.substring(0, url.lastIndexOf("/")) : url,
		concatUrl: concatUrl,
		isFPPage: config.isFPPage,
		schema: schemaObj,
    	 showRatingsReviewsPLP : config.showRatingsReviewsPLP
	});
	$previewList.remove();
	action === 'replace' ? $(container).html(output) : $(container).append(output);
};
const getDefaultScrollCnt = isFilterClick => {
	return !config.lazyScroll || (onScrollLoadCnt > config.defaultLoad && !isFilterClick) || !onScrollLoadCnt
		? config.defaultLoad
		: onScrollLoadCnt;
};
export const loadPreviewList = cnt => {
	let template = Handlebars.compile($('#previewListTemp').html());
	let output = template(cnt);
	$(config.el).append(output);
};
export const lazyLoadGrid = (res, totalPageCnt, curPageNo, productResponse) => {
	let $el = $(config.el);
	let $seeMoreElem = $('#see-more');
	let $listEl = $el.find('li:not(.preview-list):not(.espot-grid)');
	if($listEl.length > 1){
		$el.find('li.zero-result').addClass('hide');
	}else{
		$el.find('li.zero-result').removeClass('hide');
	}
	if (_.isEmpty(res)) return;
	if($listEl.length < parseInt(datasets.initialLoadcount) ){
		$seeMoreElem.addClass('hide');
	} else if (parseInt(totalPageCnt) > curPageNo) {
		$seeMoreElem.removeClass('hide');
	} else{
		$seeMoreElem.addClass('hide');
	}
	if ($listEl.length >= productResponse.length) return;
	if (window.innerHeight > $el[0].getBoundingClientRect().bottom - 50) {
		if (dataLoaded) {
			$seeMoreElem.addClass('hide');
			return;
		}
		if ($listEl.length < config.defaultLoad) {
			if (contentLoading) return;
			contentLoading = true;
			let deviceColCnt = parseInt(parentConfig[`${config.deviceName}Col`]);
			if($listEl.length % deviceColCnt != 0){
				deviceColCnt = deviceColCnt + ($listEl.length % deviceColCnt);
			}
			loadPreviewList(deviceColCnt);
			setTimeout(() => {
				renderGridTemplate();
				lazyLoadGrid(res, totalPageCnt, curPageNo, productResponse);
			}, 1000);
		}
	}
};
const onLoadScrollCnt = landingPageConfig => {
	parentConfig = landingPageConfig;
	let $el = $(config.el);
	let $listEl = $el.find('>li:not(.preview-list)');
	let liHeight = $listEl.length ? $($listEl[0]).height() : 393;
	let deviceCol = parentConfig[`${config.deviceName}Col`];
	let val =
		Math.ceil(($(window).scrollTop() + $el.offset().top - liHeight + $(window).height()) / (liHeight - 30)) *
		(deviceCol || 4);
	return val >= deviceCol * 2 ? val : deviceCol * 2;
};
const sliceObject = (res, browserResizing, isFilterClick, isTriggeredFromSeeMore) => {
	if (!res.length) return;
	let listCount = $(config.el).find('>li:not(.preview-list):not(.espot-grid)').length;
	if (browserResizing) {
		return { obj: res.slice(0, listCount).map(result => result) };
	}
	if (config.defaultLoad <= listCount || isTriggeredFromSeeMore || (!config.lazyLoadEnabled[config.deviceName])) {
		return { obj: res };
	}
	let getActualRange = fromAndToCount(res, isFilterClick);
	let startCnt = getActualRange.start;
	let endCnt = getActualRange.end;

	return { obj: res.slice(startCnt, endCnt).map(result => result), focusIndex: startCnt };
};
const fromAndToCount = (res, isFilterClick) => {
	let listCount = $(config.el).find('>li:not(.preview-list)').length;
	let fromCnt = isFilterClick ? 0 : listCount || 0;
	let colVal = parseInt(parentConfig[`${config.deviceName}Col`] || 4);

	if(!fromCnt){
		let remLiCount = res.length % colVal;
		let newColVal = colVal * 2;
		if(res.length > newColVal && res.length < colVal * 3 && remLiCount != 0){
			config.defaultLoadCnt = newColVal + remLiCount;
		}
	} else{
		config.defaultLoadCnt = listCount;
	}
	let isStillScrollingActive = fromCnt && config.defaultLoad - config.defaultLoadCnt;
	let nextScrollCnt = isStillScrollingActive
		? isStillScrollingActive % colVal == 0
			? colVal
			: isStillScrollingActive % colVal
		: 0;
	
	return {
		start: fromCnt,
		end:
			config.defaultLoad == -1 || (config.nextLoad < 1 && config.pageNo)
				? res.length
				: isStillScrollingActive > 0
				? fromCnt + nextScrollCnt
				: fromCnt + (fromCnt ? config.nextLoad : config.defaultLoadCnt),
	};
};
const checkImageLoaded = elem => {
	let $closestEle,
		$imageElem = $(elem).find('.product-image:not(.image-loaded)');
	if (!$imageElem.length) {
		setPromoGridHeight($(config.el).find('>li:not(.espot-grid):first'));
		return;
	}
	$imageElem
		.imagesLoaded()
		.progress((instance, image) => {
			$closestEle = $(image.img).closest('.product-image');
			$closestEle.addClass('image-loaded');
			if (!image.isLoaded) {
				$closestEle.addClass('broken-image');
			}
		})
		.done((instance, image) => {
			setPromoGridHeight($(config.el).find('>li:not(.espot-grid):not(.preview-list):first'));
		});
};

const checkTruncateText = (ele) => {
	const el = ele || config.el;
	const $ele = $($(el).attr('data-truncate')).not('.active');
	if (!$ele) {
		return;
	}
	_.each($ele, (item, indx) => {
		$(item).addClass('active');
		truncateInit(item, config[`${config.deviceName}TextLine`]);
	});
};

const productBadges = () => {
   if($('.product-badge').length >=1) {
     var currentPagePath = $('#currentPagePathForAnalytics').val(),
        getProducts = "/bin/getProductBadge";
            var obj = {
                    "async": false,
                    "type": "get",
                    "params": "",
                    "url": window.location.protocol + "//" + window.location.host + getProducts + "?currentPath=" + currentPagePath +"&plp=plppage"
                }

                PLAYAEM.requestAPICall(obj, function(response) {
                    if (!response) {
                        console.log("Err : Upcoming Events API failed..");
						return false;
                    } else {
                       
						if(response.productBageList !=''){
				for(var i=0;i<response.productBageList.length;i++) {
					for(var j=0;j<$('.product-badge').length;j++) {
                        var badgeDisplayValue = response.productBageList[i].badgeDisplayValue,
							 badgeColour = response.productBageList[i].badgeColour,
							 badgeTitle = response.productBageList[i].badge,
							 badgeIcon = response.productBageList[i].badgeIcon,
							 textColour = response.productBageList[i].textColour;
						if(badgeTitle == $(".product-badge:eq("+j+")").html()) {

							 if(badgeDisplayValue !='') {
								$(".product-badge:eq("+j+")").text(badgeDisplayValue);
								$(".product-badge:eq("+j+")").css({"color":textColour,"background-color":badgeColour,"border":"1px solid "+badgeColour});
								$(".product-badge:eq("+j+")").prepend("<img src='"+badgeIcon+"' alt='"+badgeTitle+"' />");
								}
							
							}
						}
					}
				}

                    }

                });
   }
};

const productCurrencyMapList = () => {
   if($('.price-currency').length >=1) {
     var getCurrencyMapList = "/bin/getCurrencyMapList";
            var obj = {
                    "async": false,
                    "type": "get",
                    "params": "",
                    "url": window.location.protocol + "//" + window.location.host + getCurrencyMapList
                }

                PLAYAEM.requestAPICall(obj, function(response) {
                    if (!response) {
                        console.log("Err : Upcoming Events API failed..");
						return false;
                    } else {
                       
						if(response.currencyMap !=''){
						for(var i=0;i<response.currencyMap.length;i++) {
							for(var j=0;j<$('.price-currency').length;j++) {
								var currencyType = response.currencyMap[i].currencyType,
									currencySymbol = response.currencyMap[i].currencySymbol;
									if(currencyType == $(".price-currency:eq("+j+")").attr('data-currency')) {
										$(".price-currency:eq("+j+")").html(currencySymbol);
									}
								}
							}
						}
					}
				});
   }
}

const setPromoGridHeight = (elem, resizeBool) => {
	const $ele = $(config.el).find('.espot-grid');
	const $promoGridEle = resizeBool ? $ele : $ele.not('.loaded');
	if (!$promoGridEle.length) {
		return;
	}
	if (typeof $.fn.imagesLoaded == 'function') {
		$promoGridEle.find('img').imagesLoaded(() => {
			// $promoGridEle.height(Math.ceil(elem.innerHeight() || 0));
			$promoGridEle.height(elem[0].getBoundingClientRect().height);
			$promoGridEle.addClass('loaded');
			setDynamicId($promoGridEle.find('.aem-video-player'));
			!resizeBool && playerScroll();
		});
	} 
};
const playerScroll = () => {
	let didScroll = 0;
	renderVideoPlayer(config.el);
	$(window)
		.scroll(() => {
			if (didScroll == 0) {
				renderVideoPlayer(config.el);
				didScroll = 1;
				setTimeout(() => {
					didScroll = 0;
				}, 250);
			}
		})
		.resize(() => {
			checkTruncateText();
		});
};

export class gridActions {
	constructor(config) {
		onScrollLoadCnt = onLoadScrollCnt(config);
		evtBinding.bindLooping(this.bindingEventsConfig(), this);
		this.promoGridResize();
	}
	bindingEventsConfig() {
		let eventsArr = {
			'click .grid-lists .quick-view': 'showQuickViewModal',
            'click .grid-lists li.scroll-loaded': 'setBadgeToSession',
			'click .shopNowFocus': 'productListFocus'
		};
		return eventsArr;
	}
    setBadgeToSession(el, evt) {
    	if(config.isFPPage == 'true') {    	
			let badgeValue  = $(el).find("span.product-badge").text().trim();
        	sessionStorage.setItem("productBadge", badgeValue);
    	}
    }
	productListFocus(el, evt){
    let newsletterHeight = 0;
		if(config.isFPPage == 'true') {
        if( $('.newsletter-sign-up').css('display') == 'block'){ newsletterHeight = $('.newsletter-sign-up').height()}
        const scrollAnimationSet = ($("header").height() + newsletterHeight);   
			let mobileView = window.matchMedia("(max-width: 767px)"),
				height;
			if(mobileView.matches){
				height = $('.filter-grid').offset().top - $("header").height();
			}else{
				height = $('#product-grid-container').offset().top - scrollAnimationSet;
			}
			jQuery(window).scrollTop(height);
		}
	}
	promoGridResize() {
		$(window).resize(
			_.debounce(() => {
				setPromoGridHeight($(config.el).find('>li:not(.espot-grid):not(.preview-list):first'), true);
			}));
	}
	showQuickViewModal(ele, evt) {}
}
let contentLoading = false;
let dataLoaded = false;
let onScrollLoadCnt;
let parentConfig = {};
const handleBarTemplateInst = new handleBarTemplate();
const evtBinding = new eventBinding();
