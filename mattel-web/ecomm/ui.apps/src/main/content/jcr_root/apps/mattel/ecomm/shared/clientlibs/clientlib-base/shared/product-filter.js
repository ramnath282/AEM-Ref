import { getDeviceName } from './constant';
import eventBinding from './eventBinding';
import { handleBarTemplate } from './templateSetter';

const config = {
  el: "#product-filter-section",
  deviceName: getDeviceName()
};

export const renderFilterTemplate = (res, isFilterActive, activeCatNames) => {
  let $totalCntEle = $(".total-cnt-inner");
  let $globalTotalCnt = $(".slider-h2 .total-itemcnt-inner");
  accordionArr = accordionArr.concat(activeCatNames);
  res.activeFilter = accordionArr;
  res.deviceName = config.deviceName;
  let hiddenFacets = $("#hiddenFacets").val();
  res = hideFacets(res, hiddenFacets);
  handleBarTemplateInst.loadTemplate(
    "#productFiltersTemp",
    config.el,
    res,
    isFilterActive ? "replace" : ""
  );
  if ($('#isFPPage').val() == "true") {
    if ($(window).width() < 768) {
      $("#product-filter-section h2 a").attr("aria-expanded", "true");
    } else {
      $('#product-filter-section .products-list-item.checkbox-cont').each(function (i) {
        if ($("#product-filter-section .products-list-item.checkbox-cont:eq('" + i + "') fieldset").hasClass('in')) {
          $("#product-filter-section h2 a:eq('" + i + "')").attr("aria-expanded", "true");
        }
      });
    }
  }
  renderSortByTemplate(res);
  $totalCntEle.html($(".checkbox-cont fieldset input:checkbox:checked") ? $(".checkbox-cont fieldset input:checkbox:checked").length : 0)
  $globalTotalCnt.html(res.resultcount.total || 0);
  $('.custom-dropdown .inner li a[aria-selected="true"]').attr(
    "aria-current",
    "page"
  );
  showHideFilterLabel(res);
  if (pageLoadAction) {
    filterNavToggle();
    pageLoadAction = false;
  }
};

const hideFacets = (res, hiddenFacets) => {
  if(hiddenFacets){
    let hiddenFacetsArr = hiddenFacets.split("~");
    res.facets = res.facets.filter( obj => {
      return hiddenFacetsArr.indexOf(obj.label) == -1;
    });
  }
  return res;
};

const renderSortByTemplate = res => {
  handleBarTemplateInst.loadTemplate(
    "#productSortTemp",
    ".grid-sort",
    res,
    "replace"
  );
};

const showHideFilterLabel = (res) => {
  let isFilter = 0;
  $.each(res.facets, (k, v) => {
    if(v.label){
      isFilter++;
    }
  });
  if(isFilter > 0){
    $(".filter-grid .filter-header, .filter-grid .expand-collapse-filter").removeClass("hide");
  }else{
    $(".filter-grid .filter-header, .filter-grid .expand-collapse-filter").addClass("hide");
  }
}

const filterNavToggle = () => {
  const $el = $(config.el).parents(".filter-wrapper");
  if (window.innerWidth > 991) {
    $("#product-filter-section .products-list-item:not(:first-child) .collapse.in").collapse('hide');
    if ($(".filter-wrapper").hasClass("expand-filter")) {
      $(".expand-collapse-filter")[0].click();
    }
    $el.removeClass("hide");
    $(".custom-dropdown-toggle").attr("aria-expanded", "false");
    $(".custom-dropdown").attr("aria-expanded", "true");
    $el.find(".custom-dropdown-toggle").attr("role", "button");
    $el.find(".custom-dropdown ul.inner li a").attr("role", "option");
    $el.find(".custom-dropdown ul.inner li").removeAttr("role");
    return;
  } else {
    if(window.isFilterClicked) {
      $el.removeClass("hide");
    } else {
      $el.addClass("hide");
    }    
  }
  
  $(".custom-dropdown-toggle")[0].click();
  $el.find(".custom-dropdown-toggle").removeAttr("aria-expanded");
  $el.find(".custom-dropdown-toggle").attr("role", "link");
  $el.find(".custom-dropdown").removeAttr("aria-expanded");
  $el.find(".custom-dropdown ul.inner li").attr("role", "presentation");
  $el.find(".custom-dropdown ul.inner li a").removeAttr("role");
  if ($('#isFPPage').val() != "true") {
    $el.find(".custom-dropdown ul.inner li a").removeAttr("aria-selected");
  } else {
    $el.find("#product-filter-section h2 a").attr("aria-expanded", "true");
  }
  $el.find(".dropdown-menu .inner li a.hide").attr("aria-current", "page");
};

export const onFilterResizeAction = () => {
  filterNavToggle();
};

class filterActions {
  constructor() {
    self = this;
    self.promoBannerDrawerHeight= $('.promoPencilContainer').height();
    this.filterResize();
    evtBinding.bindLooping(this.bindingEventsConfig(), this);
  }
  bindingEventsConfig() {
    let eventsArr = {
      "click .expand-collapse-filter": "filterCollapseAction",
      "click .main-section, .sliding-div-mask": "filterCollapseAction",
      'click #product-filter-section .value-label [data-toggle="collapse"]':
        "trackAccordionAction",
      "click .hide-overlay": "hideOverlay",
      "click .category-aside-section .apply-filter": "applyFilterOnClick"
    };
    return eventsArr;
  }
  filterCollapseAction(el) {
    let $el = $(config.el).parents(".filter-wrapper");
    if ($el.hasClass("expand-filter")) {   
      window.isFilterClicked = false;
      self.removemargin();     
      $("#header").removeClass("switchTop");
      $(".filter-grid")
        .find(".filter-btn-slider")
        .removeClass("hide");
      $el.addClass("collapse-filter hide").removeClass("expand-filter");
      $(".filter-grid")
        .find("a.expand-collapse-filter")
        .focus();
      $(".filter-grid")
        .find("a.expand-collapse-filter")
        .attr("aria-expanded", "false");
      $('.navbar-fixed-top').removeAttr("style");
      $("body").removeClass("fixed-body-position");
    } else {
      window.isFilterClicked = true;
      if (window.innerWidth < 1024) {
        $("#header").addClass("switchTop");
        self.setmargin();
      }
      $(".filter-grid")
        .find(".filter-btn-slider")
        .addClass("hide");
      $el.removeClass("collapse-filter hide").addClass("expand-filter");
      $el.find(".apply-filter").focus();
      $(".filter-grid")
        .find("a.expand-collapse-filter")
        .attr("aria-expanded", "true");
      let maskHeight = $(".ecomm-wrapper").height();
      let slideNavHeight = $(".sliding-div").height();
      if (slideNavHeight > maskHeight) {
        maskHeight = slideNavHeight;
      }
      $(
        ".category-aside-section .sliding-cont.expand-filter .sliding-div-mask"
      ).css("min-height", maskHeight);
      $("body").addClass("fixed-body-position");
    }
    setTimeout(() => {
      $el.find(".dropdown-menu .inner li a.hide").attr("aria-current", "page");
      $el.find(".custom-dropdown-toggle").removeAttr("aria-expanded");
      if ($('#isFPPage').val() != "true") {
        $el.find(".custom-dropdown ul.inner li a").removeAttr("aria-selected");
      }
      $el.find(".custom-dropdown").removeAttr("aria-expanded");

    }, 200);
  }
  setmargin() {
    const searchDropdown = $(".sliding-div");
    searchDropdown.css("opacity",0);
    const clearwrapper = $(".clear-wrapper");
    if (!$('.non-sticky-header').length) {
      const headerWrapperHeight = $("#header-wrapper, #header");
      let headerHeight = headerWrapperHeight.height();
      searchDropdown.css('top', headerHeight + 'px');
      clearwrapper.css('top', headerHeight + 'px');
      if ($(".promoPencilContainer+.navigation").length) {
        if($('body').hasClass('header-sticky-active')){
          marginHeight = 0;
          searchDropdown.css('margin-top', marginHeight + 'px');
          clearwrapper.css('margin-top', marginHeight + 'px');
          $('.navbar-fixed-top').css('position','fixed');
        }else{         
            marginHeight += self.promoBannerDrawerHeight + $(".page-view").offset().top;
            searchDropdown.css('margin-top', marginHeight + 'px');
            clearwrapper.css('margin-top', marginHeight + 'px');
        }
      }
    }
    setTimeout(function(){
      searchDropdown.css("opacity",1);
    },400);
  }
  removemargin() {
    const searchDropdown = $(".sliding-div");
    const clearwrapper = $(".clear-wrapper");
    marginHeight = 0;
    searchDropdown.css('margin-top', marginHeight + 'px');
    clearwrapper.css('margin-top', marginHeight + 'px');
  }
  trackAccordionAction(ele) {
    let indx = $(ele).data("index");
    if ($(ele).attr("aria-expanded") == "true") {
      accordionArr.push(indx);
    } else {
      accordionArr.pop(indx);
    }
  }
  hideOverlay(ele, evt) {
    $(".sliding-div")
      .addClass("request-pending ")
      .delay(500)
      .queue(function (next) {
        $(this).removeClass("request-pending ");
        $(".sliding-div-mask")[0].click();
        next();
        if ($('#isFPPage').val() == "true") {
          var filtertop = $('.image-text-container .col-md-3').offset().top - $('header').outerHeight();
          $("html, body").animate({ scrollTop: filtertop }, "fast");
        }
      });
  }
  applyFilterOnClick(el, evt) {
    evt.preventDefault();
    // $(".filter-grid")
    //   .find("a.expand-collapse-filter")
    //   .focus();
    // if($('#isFPPage').val()=="true"){
    //   var filtertop = $('.image-text-container').offset().top - $('header').outerHeight();
    //   $("html, body").animate({ scrollTop: filtertop }, "fast");
    // }
  }
  filterResize() {
    if (config.deviceName == "tablet") {
      $(window).on("resize", onFilterResizeAction);
    }
  }
}
let marginHeight = 0;
let self, pageLoadAction = true,
  accordionArr = [0];
const handleBarTemplateInst = new handleBarTemplate();
const evtBinding = new eventBinding();
const filterAction = new filterActions();
