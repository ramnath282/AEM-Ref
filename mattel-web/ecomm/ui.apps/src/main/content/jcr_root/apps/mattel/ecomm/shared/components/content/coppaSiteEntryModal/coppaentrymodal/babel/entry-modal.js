import eventBinding from "../shared/eventBinding";
import { setCookie, getCookie } from "../shared/browserCookie";
import { excludeSupportObj } from "../shared/excludeSupportiveFunction";

const cookieConfig = {
  adultCookieName: "confirmed_adult_user"
};
class entryModal {
  constructor() {
    self = this;
    self.el = ".entry-modal-container";
    self.initModal();
    if($("#pageEditMode").length){
      self.authorMode();
    }
  }
  bindingEventsConfig() {
    let eventsArr = {
      "click .shop-section .theme-btn": "navigateToShopPage"
    };
    return eventsArr;
  }
  navigateToShopPage(ele, evt) {
    let cookieExpiry = $(self.el).attr("data-cookieExpiry");
    let shopbtnURL = ele.getAttribute("href");
    setCookie(cookieConfig.adultCookieName, true, cookieExpiry);
    if(shopbtnURL == "#" || shopbtnURL == "javascript:void(0);" || shopbtnURL == ""){
      evt.preventDefault();
      $(self.el).modal("hide");
    }
  }
  initModal() {
    let container = $(self.el);
    let isExcludeKeyword = false, isExcludepage = false;
    let cookieExpiry = $(self.el).attr("data-cookieExpiry");
    if(excludeSupportObj.checkExcludeKeywordAsParameter(container)){
      isExcludeKeyword = true;
      setCookie(cookieConfig.adultCookieName, true, cookieExpiry);
    }
    if(excludeSupportObj.checkExcludePages(container) || excludeSupportObj.checkExcludeKeywordAsPageName(container)){
      isExcludepage = true;
      setCookie(cookieConfig.adultCookieName, true, cookieExpiry);
    }
    let excludeCookieVal = isExcludeKeyword;
    if(isExcludepage == false && excludeCookieVal == false){
      let isAdultUser = getCookie(cookieConfig.adultCookieName) || false;
      if (!isAdultUser && $(self.el).length) {
        $(self.el).modal("show");
        evtBinding.bindLooping(self.bindingEventsConfig(), self);
        return;
      }
    }
  }
  authorMode(){
    var authorPage = $("#pageEditMode").val();
    if(authorPage == "author"){
      $(".coppaSiteEntryModal").hide();
    }
  }
}

let self;
const evtBinding = new eventBinding();
let entryModalInit = new entryModal();