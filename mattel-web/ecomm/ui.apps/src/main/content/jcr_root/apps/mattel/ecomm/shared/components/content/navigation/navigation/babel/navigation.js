import eventBinding from "../shared/eventBinding";

class header {
  constructor() {
    self = this;
    evtBinding.bindLooping(self.bindingEventsConfig(), self);
    self.stickyHeaderOnScroll();
    self.init();
  }
  bindingEventsConfig() {
    let eventsArr = {
      "click .main-header .aghamburger": 'toggleNavDropdown',
      "click #spark-menu-close-button": 'toggleNavDropdown',
      "click .main-menu": 'toggleSubNav',
      "click .sub-menu": 'toggleSubSubNav',
      "click .account-sub-menu": 'toggleAccountMenu',
      "click .back-to-menu": 'backToMainHam',
      "click body": 'hideOverlay',
      "keydown .ham-homelogo": 'focusSocialIcon',
      "keydown .social-icons li:last-child a": 'focusHamHome',
      "click .main-menu.active-menu": "checkOnMainMenu",
      "click .sub-menu.active-sub-menu": "checkOnSubMenu",
      "click .account-menu .not-activated-user": "SignUpModelPopUpFunction",
      "click .user-info-in-header .not-activated-user": "SignUpModelPopUpFunction",
    };
    return eventsArr;
  }
  
  checkOnMainMenu(el, evt){
	  let url = $(el).attr('data-key-url');
	  if (url != "#" &&  !($(el).hasClass('sub-menu-active-parent'))){
		  window.location = url;
	  }else{
		  $(el).removeClass('sub-menu-active-parent');
	  }
  }
  
  checkOnSubMenu(el, evt){
	  let url = $(el).attr('data-key-url');
	  if (url != "#"){
		  window.location = url;
	  }
  }
  
  
  
  hideOverlay(ele, evt) {
    const $hambergurEle = $(".main-header .aghamburger");
    const $targetEle = $(".header-spark-menu");
    self.getheaderHeight();
    if (!$(evt.target).is(".aghamburger") && !$(".header-spark-menu").find(evt.target).length && $targetEle.hasClass('active')) {
      $targetEle.removeClass('active');
      $('body').removeClass("hamburger-on");
      $hambergurEle.removeClass('active');
    }
    return;
  }

  resizeScrollSearch() {
    $(window).scroll(function () {
      self.getheaderHeight();
    });
    $(window).resize(function () {
      self.getheaderHeight();
    });
  }
  openShopCategoryOnLoad(){
    let currentUrl = window.location.href;
    if(currentUrl.indexOf("/shop") != -1){
      self.toggleSubNav("a[data-rootshoppage='true']");
    }
  }
  getheaderHeight() {
    const targetEle = $(".header-spark-menu");
    let marginHeight = 0;
    if (!$('.non-sticky-header').length) {
      const headerWrapperHeight = $("#consent_blackbar");
      let headerHeight = headerWrapperHeight.height();
      targetEle.css('top', headerHeight + 'px');
      if ($('.header-sticky-active').length) {
        marginHeight = 0;
        targetEle.css('margin-top', marginHeight + 'px');
      } else {
        targetEle.css('margin-top', marginHeight + 'px');
      }

    }
  }


  toggleNavDropdown(ele, evt) {
    const className = "active";
    const $targetEle = $(".header-spark-menu");
    const $bodyTag = $("body");
    const ham_home_icon = $(".ham-homelogo");
    const hamburger_icon = $(".aghamburger");
    const hamburger_close_icon = $("#spark-menu-close-button");

    if ($targetEle.hasClass(className)) {
      $targetEle.removeClass(className);
      $(ele).removeClass(className);
      $(hamburger_icon).focus();
      $bodyTag.removeClass("hamburger-on");
      return;
    }
    $targetEle.addClass(className);
    $targetEle.find('.close-navigation a:first-child').focus();
    $(ele).addClass(className);
    $(ham_home_icon).focus();
    $bodyTag.addClass("hamburger-on");

    const backToMainMenu = $("a.back-to-menu");
    const back_to_menu = "back-to-menu";
    const backToSubMenu = $("a.main-menu.active-menu.sub-menu-active-parent");
    const active_parent_class = "sub-menu-active-parent";
    const backButton_Ele = $(".backButtons");
    
    if (backToSubMenu.hasClass(active_parent_class)) {
      const targetLink = backToSubMenu.next().children('li:not(.hide)').children('a.active-sub-menu');
      self.toggleSubSubNav(targetLink, evt, true);
    }
    if (backToMainMenu.hasClass(back_to_menu)) {
      const targetLink = backToMainMenu.parent().nextAll(".category-menu").children('li:not(.hide)').children('a.active-menu');
      self.toggleSubNav(targetLink, evt, true);
      backButton_Ele.addClass('hide');
    }
    self.openShopCategoryOnLoad();

    self.loggedUser();
	}
  backToMainHam(ele, evt) {
    const back_to_menu = "back-to-menu";
    const Element = $(ele);
    console.log(Element);
    if (Element.hasClass(back_to_menu)) {
      const targetLink = Element.parent().nextAll(".category-menu").children('li:not(.hide)').children('a.active-menu');
      self.toggleSubNav(targetLink, evt, true);
    }
  }
  toggleSubNav(ele, evt, flag) {
    if($(ele).not('.sub-menu-active-parent').hasClass("active-menu") && $(ele).attr('data-key-url')!="#" && !flag){ return; }
    const Sub_Menu_Active_ClassName = "sub-active";
    const Menu_Right_Zero = "right-0";
    const Menu_Deactive_ClassName = "hide";
    const Active_Menu_ClassName = "active-menu";
    const active_parent_class = "sub-menu-active-parent";

    const backButton_Ele = $(".backButtons");
    const Element = $(ele);
    const $submenu_target_Ele = Element.next();
    const $main_Menu_target_Next_Ele = Element.parent().nextAll();
    const $main_Menu_target_Prev_Ele = Element.parent().prevAll();
    const $main_Nav_target_Ele = Element.parents().eq(1).prevUntil('.backButtons');

    if (Element.hasClass(active_parent_class)) {
      const targetLink = Element.next().children('li:not(.hide)').children('a.active-sub-menu');
      self.toggleSubSubNav(targetLink, evt, true);
    } else if ($submenu_target_Ele.hasClass(Sub_Menu_Active_ClassName)) {
      $submenu_target_Ele.removeClass(Sub_Menu_Active_ClassName);
      $submenu_target_Ele.removeClass(Menu_Right_Zero);
      $main_Menu_target_Next_Ele.removeClass(Menu_Deactive_ClassName);
      $main_Menu_target_Prev_Ele.removeClass(Menu_Deactive_ClassName);
      $main_Nav_target_Ele.removeClass(Menu_Deactive_ClassName);
      backButton_Ele.addClass(Menu_Deactive_ClassName);
      Element.removeClass(Active_Menu_ClassName);
      return;
    } else {
      $submenu_target_Ele.addClass(Sub_Menu_Active_ClassName);
      $main_Menu_target_Next_Ele.addClass(Menu_Deactive_ClassName);
      $main_Menu_target_Prev_Ele.addClass(Menu_Deactive_ClassName);
      $main_Nav_target_Ele.addClass(Menu_Deactive_ClassName);
      backButton_Ele.removeClass(Menu_Deactive_ClassName);
      Element.addClass(Active_Menu_ClassName);
      setTimeout(() => $submenu_target_Ele.addClass(Menu_Right_Zero), 100);
    }
  }
  toggleSubSubNav(ele, evt, flag) {
    if($(ele).hasClass("active-sub-menu") && $(ele).attr('data-key-url')!="#" && !flag){
      return;}
    const Sub_Sub_Menu_Active_ClassName = "sub-sub-active";
    const Menu_Right_Zero = "right-0";
    const Menu_Deactive_ClassName = "hide";
    const Active_SubMenu_ClassName = "active-sub-menu";
    const Active_parentSubmenu = "sub-menu-active-parent";
    const backButton_Ele = $(".backButtons");

    const Element = $(ele);
    const sub_sub_menu_target_Ele = Element.next();
    const target_all_submenu_except_activeOne_Ele = Element.parent().siblings().not(Element.parent());
    const target_active_subMenu_Ele = Element.parents().eq(1).prev();

    if (sub_sub_menu_target_Ele.hasClass(Sub_Sub_Menu_Active_ClassName)) {
      sub_sub_menu_target_Ele.removeClass(Sub_Sub_Menu_Active_ClassName);
      sub_sub_menu_target_Ele.removeClass(Menu_Right_Zero);
      target_all_submenu_except_activeOne_Ele.removeClass(Menu_Deactive_ClassName);
      Element.removeClass(Active_SubMenu_ClassName);
      backButton_Ele.removeClass(Menu_Deactive_ClassName);
      return;
    } else {
      sub_sub_menu_target_Ele.addClass(Sub_Sub_Menu_Active_ClassName);
      target_all_submenu_except_activeOne_Ele.addClass(Menu_Deactive_ClassName);
      target_active_subMenu_Ele.addClass(Active_parentSubmenu);
      backButton_Ele.addClass(Menu_Deactive_ClassName);
      setTimeout(() => sub_sub_menu_target_Ele.addClass(Menu_Right_Zero), 100);
      Element.addClass(Active_SubMenu_ClassName);
    }
  }

  toggleAccountMenu(ele, evt) {
    const Menu_Deactive_ClassName = "hide";
    const backbutton_Deactive_ClassName = "bbhide";
    const account_Menu_Active_ClassName = "account-menu-active";
    const Sub_Menu_Active_ClassName = "sub-active";
    const Menu_Right_Zero = "right-0";
    const Element = $(ele);
    const backButton_Ele = $(".backButtons");
    const target_all_submenu_except_activeOne_Ele = Element.parent().siblings().not(Element.parent());
    const main_Nav_target_Ele = Element.parents().eq(1).prevUntil('.backButtons');
    const submenu_target_Ele = Element.next();

    if (Element.hasClass(account_Menu_Active_ClassName)) {
      Element.removeClass(account_Menu_Active_ClassName);
      target_all_submenu_except_activeOne_Ele.removeClass(Menu_Deactive_ClassName);
      main_Nav_target_Ele.removeClass(Menu_Deactive_ClassName);
      backButton_Ele.removeClass(backbutton_Deactive_ClassName);
      submenu_target_Ele.removeClass(Sub_Menu_Active_ClassName);
      submenu_target_Ele.removeClass(Menu_Right_Zero);
      return;
    } else {
      Element.addClass(account_Menu_Active_ClassName);
      target_all_submenu_except_activeOne_Ele.addClass(Menu_Deactive_ClassName);
      main_Nav_target_Ele.addClass(Menu_Deactive_ClassName);
      backButton_Ele.addClass(backbutton_Deactive_ClassName);
      submenu_target_Ele.addClass(Sub_Menu_Active_ClassName);
      setTimeout(() => submenu_target_Ele.addClass(Menu_Right_Zero), 100);
    }
  }
  
  focusSocialIcon(ele, evt) {
    if (evt.shiftKey && evt.keyCode === 9) {
      $('.social-icons li:last-child a').focus();
    }
  }
  focusHamHome(ele, evt) {
    if (evt.shiftKey === false && evt.keyCode === 9) {
      $('.ham-homelogo').focus();
    }
  }

  SignUpModelPopUpFunction(ele){
    if(!$(ele).hasClass("user-inactive")  && !$(ele).hasClass("disable-signin-modal") && $(".not-activated-user").find(".popupAgRewardSignUp")){
        $("body").removeClass("hamburger-on");
        $("#sessionTimeoutModal").modal("show");
        let sessionEmail = $.trim($("#session-email").val()),
          sessionPassword = $.trim($("#session-password").val());
        if(sessionEmail != ""){
          $("#session-email").addClass("not-empty");
        }
        if(sessionPassword != ""){
          $("#session-password").addClass("not-empty");
        }
        $(".header-spark-menu").removeClass("active");
    }
    else {
      let currentURL = window.location.href,
      redirectURL = $(e).find(".popupAgRewardSignUp").attr('href')+'&redirect_url='+currentURL;
      $(e).find(".popupAgRewardSignUp").attr('href',redirectURL);
    }
  }
  loggedUser() {
    const not_activeted_user = $(".not-activated-user");
    const activeted_user = $(".activated-user");
    const active_user_name_div = $(".activated-user .username");
    const active_user_type_div = $(".activated-user .usertype");
    const active_class = "active";
    let active_user_name = "";
    // for (i = 0; i < cookies.length; i++) {
    const {userName,customerSegment} = window.global.getUserCookie();
    if (userName) {
      not_activeted_user.addClass(active_class);
      activeted_user.removeClass(active_class);
      active_user_name = userName;
      active_user_name = active_user_name.split(" ");
      active_user_name = active_user_name[0];
      active_user_name = active_user_name.split("%2C");
      active_user_name = active_user_name[0];
      active_user_name_div.replaceWith(userName);
    }
    if (customerSegment) {
      active_user_type_div.replaceWith(customerSegment);
    } else {
      $(".activated-user .tier-name").hide();
    }
    // }
  }
  stickyHeaderOnScroll() {
    if (!$('.navbar-fixed-top').length) {
      $('body').addClass('non-sticky-header');
      return;
    }
    var stickyOffset = $('.navbar-fixed-top').offset().top;
    $(".header-promo-container:eq(0)").length && $(".header-promo-container:eq(0)").text().trim().length && $('body').addClass('promo-banner-on');
    // let $topPromoBanner = $(".promoBannerDrawer +.navigation").length && $(".promoBannerDrawer:eq(0) .promo-text-container");
    let openState = false;
    $(window).scroll(function () {
      if(!$(".filter-btn-slider.hide").length) {
        var sticky = $('body'),
          scroll = $(window).scrollTop();

        if (scroll >= stickyOffset+(openState ? $(".promo-header-dropdown:eq(0)").height() : 0 )) {
          sticky.addClass('header-sticky-active');
          if ($(".promo-header-dropdown:eq(0)").hasClass('in') && !openState) {
            $(".promo-header-dropdown:eq(0)").collapse('hide');
          } 
          // $topPromoBanner && !openState && $topPromoBanner.css("opacity", "0");
        } else {
          sticky.removeClass('header-sticky-active');
          // $topPromoBanner && !openState && $topPromoBanner.css("opacity", "1");
        }
      }
    });
  }
    
    init() {
        // self.renderUserInfo();
        self.resizeScrollSearch();
    }
}
let self;
const evtBinding = new eventBinding();
const headerComp = new header();