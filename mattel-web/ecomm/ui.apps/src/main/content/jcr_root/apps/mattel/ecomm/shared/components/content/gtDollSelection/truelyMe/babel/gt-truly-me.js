import eventBinding from '../shared/eventBinding';
import apiConfig from '../shared/apiConfig';
import ajaxRequest from '../shared/ajaxbinding';
import { setCookie } from '../shared/browserCookie';

class gtTrulyMe {
                constructor() {
                                                self = this;
                                                this.element = ".gt-container>.row div.gt-product-trulyme-wrapper";
                                                evtBinding.bindLooping(this.bindingEventsConfig(), this);
                }
                bindingEventsConfig() {
                                let eventsArr = {
                                                'click .gt-container > .row div.gt-product-trulyme-wrapper .product-card': 'productAlignment',
                                                'click .gt-container > .row div.gt-product-trulyme-wrapper .product-card-wrapper': 'moveBundleSelection'
                                };
                                return eventsArr;
                }
                productAlignment(ele) {
                             
                                let href = $(self.element).find('.product-card').attr("href");
                             $(self.element).find('.product-card').removeClass('active');
                                $(ele).addClass('active');
                             window.location.href = href; 
       
                }
                moveBundleSelection(ele){
                                const $ele = $(ele);
                                
								let normalImage = $ele.find('.product-image img').attr('src');
								var imageArr = normalImage.split('?');
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
                                setCookie($ele.find('.product-image').data('binder-name'),imageArr[0]); 
								setCookie($ele.find('span.product-eye-color').data('binder-name'),$ele.find('span.product-eye-color').text());
								setCookie($ele.find('span.product-hair-type').data('binder-name'),$ele.find('span.product-hair-type').text());

    }
}
let self;
const evtBinding = new eventBinding();
const gtTrulyMeInstance = new gtTrulyMe();
const apiConfigInst = new apiConfig();
const request = new ajaxRequest();
