import ajaxRequest from '../shared/ajaxbinding';
import eventBinding from '../shared/eventBinding';
import { handleBarsHelper } from "../shared/handleBarsHelper";
import { affixScroll } from '../shared/product-slider';

export class pdpImageViewer {
	constructor() {
		self = this;
		this.touch = "ontouchstart" in window || window.DocumentTouch && document instanceof DocumentTouch;
		evtBinding.bindLooping(this.bindingEventsConfig(), this);
		partNo = $(".product-info-wrapper:first").attr("data-partnumber");
		this.config = {
			pdpProdGallery: {
				//url: `//${window.location.host}/mockjson/pdp-viewer-response.json`,
				url: `https://s7d9.scene7.com/is/image/Mattel/`+partNo+`_Viewer?req=set,json`,
				type: 'get',
				accept: 'application/json',
				jsonpCallback: 's7jsonResponse',
				crossDomain: true,
				dataType: 'jsonp'
			}
		};
		
	}
	bindingEventsConfig() {
		let eventsArr = { 
			'click .pdp-img-viewer-holder #pdpThumbUl li': 'thumbSlideAction',
			'click .product-wrapper .size-selection-preference .color_category .innerCont' : 'productSwatchSelection',
			'click #pdpThumbUl li' : 'currentThumbSelection'
		};
		return eventsArr;
	}
	
	/** Helps to initialize thumbail generation method and attach slick initiation  **/
	init() {
		this.loadThumbData();
	}

	/** Helps the functionality of zooming selected large product image **/
	zoomInit(el, evt) {
		if(slickCurrState == 0) {
			largeHolder.slick('slickSetOption', slickFreezeState[1], true);
			$('li:not(.video-poster-viewer)', largeHolder).zoom({on:'mouseover'});
			slickCurrState = 1;
		}
		else {
			largeHolder.slick('slickSetOption', slickFreezeState[0], true);
			$('li:not(.video-poster-viewer)', largeHolder).trigger('zoom.destroy');
			slickCurrState = 0;
		}
		largeHolder.slick('refresh');
	}

	/** Product Color Swatch Selection **/
	productSwatchSelection(el, evt) {
		evt.preventDefault();
		let swatchSrc = $('img',el).attr('src') || '';
		swatchSrc = swatchSrc.substring(swatchSrc.lastIndexOf('_')+1, swatchSrc.length);
		$('li',thumbHolder).each(function(liIdx, liRef){
			let thumbImgSrc = $(liRef).data('img-name') || '';
			thumbImgSrc = thumbImgSrc.substring(thumbImgSrc.lastIndexOf('_')+1, thumbImgSrc.length);
			if(thumbImgSrc == swatchSrc) {
				$('li',thumbHolder).removeClass('slick-current');
				$(liRef).addClass('slick-current').trigger('click');
			}
		});
	}

	/** Determine Current Selection and Assignments **/
	currentThumbSelection(el, evt) {
		let currentThumbSrc = 'https://s7d9.scene7.com/is/image/'+($(el).data('img-name'));
		$(".social_pinterest_image").attr("src", currentThumbSrc);
		if($(".product-wrapper .size-selection-preference .color_category .innerCont.active").length > 0){
			$('.gt-image:first').html(currentThumbSrc);
		}
	}
	updateVideoHeight(){
		const $ele = $(".video-poster-viewer");
		if(!$ele.length) return;
		let ulHeight;
		_.each($ele,(ele)=>{
			$(ele).css('height','auto');
			ulHeight = $(ele).closest("ul").height();
			if(ulHeight > 10) $(ele).css('height',ulHeight);
		});
	}
	/** Initializes deluxe video functionality **/
	videoInit(elem) {
		const $ele = elem.find(".video-viewer-player");
		const dataAttr = $ele[0].dataset;
		dataAttr.elemId = $ele.attr("id");
		let {videoId, videoIndex} = dataAttr;
		videoIndex = parseInt(videoIndex || 0);
		self.updateVideoHeight();
		if (viewerPlayers[videoIndex] || dataAttr.videoInitiated) { 
			return; 
		}
		dataAttr.videoInitiated = true;
		const {createDeluxePlayer} = window.global;
		if(!createDeluxePlayer) return;
		createDeluxePlayer(dataAttr.elemId, videoId, (player) => {
			if ($("#sub-" + dataAttr.elemId).length) {
				player.tech_ && player.tech_.clearTracks("text");
        		player.loadTech_(player.options_.techOrder[0], null);
				$("#sub-" + dataAttr.elemId).css({
					"width": '100%',
					"padding-top": '56.25%',
					"height": "auto"
				});
				player.on('loadstart', (evt) => {
					console.log(evt);
				});
			} else {
				$("#" + dataAttr.elemId).find('video').css({ "width": '100%' });
			}
			viewerPlayers[videoIndex] = player;
		});
	}

	/** Forms API response and sends back for consumption **/
	getScene7ApiConfig(reqConfig) {
		return self.config[reqConfig];
	}
	getPosterImageUrl(videoId) {
		let idInString = videoId.toString();
        return (idInString.length >= 32 || idInString.startsWith("6")) ? "poster" : videoId;
	}
	getDeluxeIdResponse(){
		let videoIdAttr = $(".product-info-wrapper").data('video');
		if(videoIdAttr == "" || !videoIdAttr)  return;
		const videoIds = videoIdAttr.toString().split(',');
		const videoDomain = "https://video.mattel.com/assets/images";
		const arr= [];
		for (const id of videoIds){
			arr.push({
				// "xs" : `${videoDomain}/${id}/thumbnail.jpg`,
				"xs" : `${videoDomain}/${id}/${self.getPosterImageUrl(id)}.jpg`,
				"lg" : `${videoDomain}/${id}/${self.getPosterImageUrl(id)}.jpg`,
				'id' : id,
				'isVideo': true
			})
		}
		return arr;
	}
	thumbSlideAction(nextSlide,evt){
		if(evt && evt.type == "click"){
			nextSlide = nextSlide.dataset.slickIndex;
		}
		let $ele = largeHolder.find(`[data-slick-index=${nextSlide}]`);
		if(!$ele.length) return;
		if(viewerPlayers.length){
			for (const player in viewerPlayers){
				if(!viewerPlayers[player].paused()){
					viewerPlayers[player].pause();
				}
			}
		}
		$ele.hasClass('video-poster-viewer') && self.videoInit($ele);
	}
	initZoom(ele) {
		$('li:not(.video-poster-viewer)', ele).zoom({
			on: self.touch ? "click" : "mouseover"
		});
	}
	/** Consumes API response and assigns to handlebar template **/
	loadThumbData() {
		let ajaxOption = this.getScene7ApiConfig("pdpProdGallery");
		ajaxRequestInst.ajaxCall(ajaxOption)
			.then(data => { 
				let resultSet = data.set.item,
						videoSet = self.getDeluxeIdResponse();
				if(resultSet instanceof Array == false){
					let arr=[];
					arr.push(resultSet);
					resultSet = arr;
				}
				if(!_.isEmpty(videoSet)) {
					const {initDeluxePlayer} = window.global;
					if(!initDeluxePlayer){
						console.log(`%c DeluxePlayerDependencyNotFound => This is dependency with commonDependency.JS file. Please check the JS order once.`, "background: red; color:white");
					} else{
						initDeluxePlayer(undefined, true);
					}
					resultSet = resultSet.concat(videoSet);
				}
				handleBarsHelperInst.checkIFConditions("ifEquals");
				thumbHolder.slick(thumbSetting);
				largeHolder.slick(largeSetting);
				if(resultSet.length > 1) {
					window.global.handleBarTemplateInst.loadTemplate("#pdpThumbTmpl", "#pdpThumbUl", resultSet, ''); 
					if(sm.matches) {
						smallThumbTotalSize = resultSet.length*22;
						$('.pdp-img-viewer-holder aside').width(smallThumbTotalSize);
					}
					this.normalizeThumbContWidth();
					$(".socialShareForProducts").css("margin-left", "75px"); 
				}
				else {
					 $('.pdp-img-viewer-holder aside').hide();
					 $('.pdp-img-viewer-holder section').width(100+'%');
				}
				window.global.handleBarTemplateInst.loadTemplate("#pdpLargeTmpl", "#pdpLargeUl", resultSet, '');
				thumbHolder.slick('unslick').slick(thumbSetting);
				thumbHolder.on('beforeChange',function(event, slick, currentSlide, nextSlide) {
					if (currentSlide !== nextSlide) {
						self.thumbSlideAction(nextSlide);
					}
				});
				largeHolder.slick('unslick').slick(largeSetting);
				// On swipe event
				largeHolder.on('swipe', function(event, slick, direction){
					self.thumbSlideAction(slick.currentSlide);
					if(self.touch){
						let $prevSlide = $(slick.$slides[slick.currentSlide - (direction == "left" ? 1 : -1)]).find(".zoomImg");
						if($prevSlide.length){
							if($prevSlide.css("opacity") == "1"){
								$('li:not(.video-poster-viewer)', largeHolder).trigger('zoom.destroy'); // remove zoom
								self.initZoom(largeHolder);
							}
						}
					}
				});
				self.initZoom(largeHolder);
				affixScrollInst.setPdpColHeight(true);
			})
			.catch(error => { 
				console.log(error) 
			})
	}

	normalizeThumbContWidth() {
		$(window).resize(function(){
			if(sm.matches) {
				$('.pdp-img-viewer-holder aside').width(smallThumbTotalSize);
			} else {
				$('.pdp-img-viewer-holder aside').removeAttr('style');
			}
		});
		$(window).resize(
			_.debounce(() => {
				self.updateVideoHeight();
            }, 500)
        );
	}
}

let thumbSetting = {
	dots: false,
	infinite: false,
	speed: 500,
	mobileFirst: true,
	slidesToScroll: 1,
	centerMode: false,
	autoplay: false,
	focusOnSelect: true,
	adaptiveHeight: false,
	slidesToShow: 10,
	arrows: false,
	variableWidth: true,
	vertical: false,
	verticalSwiping: false,
	asNavFor: $('.pdp-img-viewer-holder section ul'),
	responsive: [{
		breakpoint: 767,
		settings: {
			slidesToShow: 5,
			arrows: true,
			prevArrow: $('.pdp-thumb-prev'),
      		nextArrow: $('.pdp-thumb-next')
		}
	},
	{
		breakpoint: 991,
		settings: {
			slidesToShow: 3,
			variableWidth: false,
			vertical: true,
			verticalSwiping: true,
			arrows: true,
			prevArrow: $('.pdp-thumb-prev'),
      		nextArrow: $('.pdp-thumb-next')
		}
	},
	{
		breakpoint: 1025,
		settings: {
			slidesToShow: 5,
			variableWidth: false,
			vertical: true,
			verticalSwiping: true,
			arrows: true,
			prevArrow: $('.pdp-thumb-prev'),
      		nextArrow: $('.pdp-thumb-next')
		}
	}],
},
largeSetting = {
	dots: false,
	infinite: false,
	speed: 500,
	slidesToScroll: 1,
	autoplay: false,
	focusOnSelect: true,
	touchThreshold: 3,
	slidesToShow: 1,
	arrows: false,
	fade: true,
	mobileFirst: true,
	asNavFor: $('.pdp-img-viewer-holder aside ul'),
	responsive: [{
		breakpoint: 1025,
		settings: {
			// speed: 500,
			fade: false
		}
	}]
},
thumbHolder = $('.pdp-img-viewer-holder aside ul'),
largeHolder = $('.pdp-img-viewer-holder section ul'),
slickCurrState = 0,
slickFreezeState = [{draggable:true},{draggable:false}],
partNo = null,
sm = window.matchMedia("(max-width: 760px)"),
smallThumbTotalSize = 0;

let playerIndex = 0,viewerPlayers=[];
let ajaxRequestInst = new ajaxRequest();
let evtBinding = new eventBinding();
let handleBarsHelperInst = new handleBarsHelper();
let affixScrollInst = new affixScroll();
$(document).ready(function(){
	let pdpImageViewerInst = new pdpImageViewer();
	pdpImageViewerInst.init();
});
