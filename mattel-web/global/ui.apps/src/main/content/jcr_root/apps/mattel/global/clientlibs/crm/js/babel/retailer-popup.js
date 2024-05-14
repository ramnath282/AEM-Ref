const GemCRMRetailerPopup = function () {
    self = this;
    this.el = '.gem-retalier-logo';
    this.lastClickedLogo = "";
    this.init();
};
GemCRMRetailerPopup.prototype = {
    bindingEventsConfig() {
        const events = {
            "click .gem-retalier-logo ul li a": "showPopup",
            "click .cancel_btn": "analyticsCancelClick",
            "click .close": "analyticsCloseClick",
            "click .retailer-yes-btn": "hidePopup"
        };
        return events;
    },
    showPopup(ele, eve) {
        if ($('.retailer-yes-btn').attr('data-url')) {
            $('.retailer-yes-btn').attr('data-url', $(ele).attr('href'));
        } else {
            $('.retailer-yes-btn').attr('href', $(ele).attr('href'));
        }

        this.lastClickedLogo = $(ele).find("img").attr("analyticsKeyword");
        populateRetailersData(this.lastClickedLogo, "");
        eve.preventDefault();

    },

    analyticsCancelClick(ele, eve) {
        populateRetailersData(this.lastClickedLogo, "Cancel");
    },
    analyticsCloseClick(ele, eve) {
        populateRetailersData(this.lastClickedLogo, "Close");
    },
    hidePopup(ele, eve) {
        $(ele).parents('.modal').modal('hide');
        populateRetailersData(this.lastClickedLogo, "Yes");
    },
    init() {
        if (!$(this.el).length) return;
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
    }
};
let self;
const evtBinding = window.global.eventBindingInst;
const gemCRMRetailerPopupInit = new GemCRMRetailerPopup();