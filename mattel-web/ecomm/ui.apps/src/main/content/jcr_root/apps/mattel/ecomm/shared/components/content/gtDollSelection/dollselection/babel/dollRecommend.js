import eventBinding from '../shared/eventBinding';
import  apiConfig  from '../shared/apiConfig';
import  ajaxRequest  from '../shared/ajaxbinding';
import {setCookie} from '../shared/browserCookie';

class gtProduct {
	constructor() {
			self = this;
			this.element = ".gt-container>.row div.gt-product-recommend-wrapper";
            this.numberOfRecordsToShow= parseInt($('#gt-product-recommend-wrapper').attr('data_noOfProducts'));
			evtBinding.bindLooping(this.bindingEventsConfig(), this);
	}
    init() {
		 if(this.element.length){
        	this.renderProductList();
		 }
    }
	bindingEventsConfig() {
		let eventsArr = {
			'click .gt-container > .row div.gt-product-recommend-wrapper .view-all-products': 'productViewAll',
			'click .gt-container > .row div.gt-product-recommend-wrapper .show-less-products': 'productViewAll',
			'click .gt-container > .row div.gt-product-recommend-wrapper .product-card-wrapper': 'moveBundleSelection',
			'click .gt-container > .row div.gt-product-trulyme-wrapper .product-card-wrapper': 'moveBundleSelection'
		};
		return eventsArr;
	}
    
	productViewAll(ele) {
	
		$(self.element).find(".show-less-products").toggleClass("hide");
        $(self.element).find(".view-all-products").toggleClass("hide");
        $(self.element).find(".product-tile:gt("+(self.numberOfRecordsToShow - 1)+")").toggleClass("hide");
	}
	moveBundleSelection(ele){
		const $ele = $(ele);
	
		setCookie($ele.find('span.product-id').data('binder-name'),$ele.find('span.product-id').text());
		setCookie($ele.find('span.product-pricing').data('binder-name'),$ele.find('span.product-pricing').text());
		localStorage.setItem($ele.find('span.product-safetyMsg').data('binder-name'),$ele.find('span.product-safetyMsg').text());
        if($ele.find('span.backorder-date').text() != "" && $ele.find('span.backorder-date').text() != null){
            setCookie('gt-product-doll-backorder-date',$ele.find('span.backorder-date').text());
        }else{
            setCookie('gt-product-doll-backorder-date',"");
        }
        if($ele.find('span.product-TrunkPlaypacks').text() == "Character") {
        	setCookie($ele.find('span.product-character').data('binder-name'),$ele.find('span.product-character').text());
        }
        else {
        	setCookie($ele.find('span.product-character').data('binder-name'),"");
        }
		localStorage.setItem($ele.find('h3.product-title').data('binder-name'),$ele.find('h3.product-title').text());
		setCookie($ele.find('span.product-small-price').data('binder-name'),$ele.find('span.product-small-price').text());
		setCookie($ele.find('span.product-large-price').data('binder-name'),$ele.find('span.product-large-price').text());
		setCookie($ele.find('.product-image').data('binder-name'),$ele.find('.product-image img').attr('src')); 
		setCookie($ele.find('p.product-age-range').data('binder-name'),$ele.find('p.product-age-range').text());
	}
}
let self;
const evtBinding = new eventBinding();
const gtProductInstance = new gtProduct();
const apiConfigInst = new apiConfig();
const request = new ajaxRequest();
