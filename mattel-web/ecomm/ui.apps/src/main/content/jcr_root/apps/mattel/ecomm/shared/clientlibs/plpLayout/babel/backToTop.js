import eventBinding from '../shared/eventBinding';
export class backToTop {
	constructor() {
        self = this;
        this.el = ".back-to-top";
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.elementScroll();
	}
	bindingEventsConfig() {
		let eventsArr = {
			'click #return-to-top': 'scrollToTop',
		};
		return eventsArr;
	}
	scrollToTop(ele, evt) {
		const topVal = document.documentElement.scrollTop || document.body.scrollTop;
        if (topVal > 0) {
        window.requestAnimationFrame(self.scrollToTop);
        window.scrollTo(0, topVal - topVal / 8);
        }
    }
    elementScroll () {
        var $elem = $(this.el);
        if (!$elem.length) return;
        $(window).scroll(function () {
            if($(window).width()>=768){
                $(this).scrollTop() > 350 ? $elem.fadeIn() : $elem.fadeOut();   
            } 
            else{
                $(this).scrollTop() > 350 ? $elem.show() : $elem.hide();  
            }          
        });
    }
}
let self;
const evtBinding = new eventBinding();
const backToTopInit = new backToTop();