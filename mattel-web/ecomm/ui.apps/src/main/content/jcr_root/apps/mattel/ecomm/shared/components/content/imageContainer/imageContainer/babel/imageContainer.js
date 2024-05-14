
import eventBinding from '../shared/eventBinding';

const config = {
    menuHeight: $('.scroll-sticky').height(),
    tabAction: $(window).width() < 991,
    listShow: $('.facet-list li').length,
    listCount: 4,
}

class imageFacet {
    constructor() {
        self = this;
        this.element = ".facetContainer";
    }


    init() {
        eventBindingInst.bindLooping({
            "click .facetContainer .facetBtn": "imageFacetToggle",
        }, self);

        self.showButtonAction()
    }

    imageFacetToggle(ele) {
        const moretext = $(ele).attr('data-text-collapse');
        const lesstext = $(ele).attr('data-text-expand');

        $(ele).toggleClass('active');
        if ($(ele).hasClass("less")) {
            $(ele).removeClass("less");
            $(ele).html(moretext);
            $(ele).attr('aria-expanded', 'false');
            $('html, body').animate({
                scrollTop: ($(ele).parents('.facetContainer').offset().top) - ((config.menuHeight || $('.scroll-sticky').height()) + 20)
            }, 500);
        } else {
            $(ele).addClass("less");
            $(ele).html(lesstext);
            $(ele).attr('aria-expanded', 'true');
            $(ele).prepend('<span class="sr-only"> content updated</span>');
        }
        $(ele).parents('.facetContainer').toggleClass('facet-tablist');
    }

    showButtonAction() {
        if (config.listShow > config.listCount) {
            $('.facetBtn').addClass("show-btn")
        }
    }
};

let self,
    eventBindingInst = new eventBinding(),
    imageFacetList = new imageFacet();
    imageFacetList.init();
