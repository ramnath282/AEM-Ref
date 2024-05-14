import eventBinding from '../shared/eventBinding';
import constant from '../shared/constant';

class contactNav {
    constructor() {
        self = this;
        this.element = ".ecomm-wrapper .aside-section";
    }
    init() {
        if(this.element.length && constant.isMediumScreen) {
            eventBindingInst.bindLooping({
            "click #myAccountNav nav h3.submenu": "dropMenu",
            "click .nav-header button": "btnCollapse"
            },self);
        }
        $(window).on("resize load", e => {
                window.innerWidth < 1024 ? self.navToogle(true) :  self.navToogle(false);
            });
    }
    navToogle (flag) {
        flag ? $(self.element).find("#myAccountNav").addClass("collapse") : $(self.element).find("#myAccountNav").removeAttr("style");       
    }
    btnCollapse(el){
        var ele = $(el).attr('aria-expanded');
        ele == 'true' ? $(el).children('.arrow-icon').css({'transform' : 'rotate('+ 180 +'deg)'}) : $(el).children('.arrow-icon').css({'transform' : 'rotate('+ 360 +'deg)'});
    }
    dropMenu(el){
            $('.submenu').removeClass('active');
            if ($('.dropmenu').is(':visible')) {
                $(".dropmenu").slideUp();
                $(el).children('.arrow-icon').css({'transform' : 'rotate('+ 360 +'deg)'});
            }
	        if ($(el).next(".dropmenu").is(':visible')) {
                $(el).next(".dropmenu").slideUp();
                $(el).attr('aria-expanded',false);
                $(el).children('.arrow-icon').css({'transform' : 'rotate('+ 360 +'deg)'});
	        } 
	        else {
                $(el).next(".dropmenu").slideDown();
                $(el).addClass('active');
                $(el).attr('aria-expanded',true);
                $('.submenu').children('.arrow-icon').css({'transform' : 'rotate('+ 360 +'deg)'});
                $(el).children('.arrow-icon').css({'transform' : 'rotate('+ 180 +'deg)'});
            }
    }
}

let self,
    eventBindingInst = new eventBinding(),
    contactNavInstance = new contactNav();
contactNavInstance.init();

