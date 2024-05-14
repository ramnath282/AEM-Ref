import eventBinding from '../shared/eventBinding';
import apiConfig  from '../shared/apiConfig';
import ajaxRequest  from '../shared/ajaxbinding';

export class agMiniCarousel {
	constructor() {
		self = this;
		evtBinding.bindLooping(this.bindingEventsConfig(), this);
	}
	bindingEventsConfig() {
		let eventsArr = { 
			//'click #pdpThumbUl li.video-thumbnail-viewer' : 'videoInit',
		};
		return eventsArr;
	}
	
	/** Helps to initialize thumbail generation method and attach slick initiation  **/
	init() {
		this.loadMiniCarouselData();
		carouselHolder.slick(carouselSetting);
	}

	/** Consumes API response and assigns to handlebar template **/
	loadMiniCarouselData() {
		let partNumbers = window.global.browserCookie.getCookie("products_in_cart");
		/* Preventing the service call if the cookie is not set */
		if(partNumbers && partNumbers != "undefined" && partNumbers != "null"){
			let ajaxOptions = apiConfigInst.getApiConfig("cartProductMiniCarousel").apply();
			ajaxRequestInst.ajaxCall(ajaxOptions)
			.done(data => {
				/* showing the Cart after successful service-call */
				$('.ecomm .recently_viewed-aemcomp').css('display', 'block');
				let resultSet = data.resultsets[0].results;
				$('.heading_text p span').html(partNumbers.split('|').length);
				window.global.handleBarTemplateInst.loadTemplate("#agMiniCarouselTmpl", "#agMiniCarouselUl", resultSet, '');
				carouselHolder.slick('unslick').slick(carouselSetting);
			}).fail(err => { })
		}
	}
}

let carouselSetting = {
	slidesToShow: 3,
	slidesToScroll: 1,
	mobileFirst: true,
  arrows: true,
	infinite : false,
	dots: false,
	autoplay: false,
	focusOnSelect: true,
	prevArrow: $('.mini-carousel-slider-prev'),
	nextArrow: $('.mini-carousel-slider-next')
},
carouselHolder = $('#agMiniCarouselUl'),
partNumbers = null;

let evtBinding = new eventBinding();
let ajaxRequestInst = new ajaxRequest();
let apiConfigInst = new apiConfig();
let agMiniCarouselInst = new agMiniCarousel();
agMiniCarouselInst.init();