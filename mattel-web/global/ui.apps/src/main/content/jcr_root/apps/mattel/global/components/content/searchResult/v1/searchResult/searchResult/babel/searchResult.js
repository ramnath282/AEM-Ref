const config = {
  sAndPLink: $("#snpEP").val(),
  productInPage: parseInt($("#articleLoadCount").val()),
  page: 1,
  pageLimit: 4,
  elementVar: '.search-outer-wrapper',
  loader : $(".loader-outer"),
  ViewAllButton: $(".cta-button"),
  isOngoingCall: false,
  searchText: "",
  pageupper: 1,
  pagelower: 0,
  totalFeed: 0,
  searchResultType: $("#searchResultType").val(),
  webImage: $('#webResultImg').val(),
  documentImage: $('#docResultImg').val()
}

class newsInfo {
  constructor () {
    self = this;
    this.element = config.elementVar;
    window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
    self.payloadObj = {};
    handleBarsHelperInst.checkIFConditions('ifEquals'); 
  }

  getApiConfig(name) {
        var listingComponentInfo = {
            "newsSearchLandingData":  {
                "type": 'get',
                "accept": 'application/json',
                "crossDomain": true,
                "dataType": 'json',
                "url": config.sAndPLink + '?search=' + config.searchResultType + '&q=' + config.searchText + '&count=' + config.productInPage + '&page=' + config.page,
				  },

        }
        return listingComponentInfo[name];
    }

  init () {
    if (this.element.length) {
      this.render(1)
    }
    window.global.eventBindingInst.bindLooping({
    }, self)

    $(document).ready( function() {
      window.addEventListener('scroll', function (e) {
        if (config.page >= config.pageLimit) {
          self.handleElementInViewChecker();
        }
      });
    });

  }

  bindingEventsConfig() {
      let eventsArr = {
        "click .search-outer-wrapper .view-all-btn": "viewAllSubmit",
      };
      return eventsArr;
  }
  render (autoload) {
    if (config.isOngoingCall) return;
    if(config.pageupper === config.totalFeed) return;
    if(window.location.search.indexOf('searchTerm') != -1){
      config.searchText = self.getParameterByName('searchTerm');
    }
    if(config.searchText != ''){
      const apiconfig = self.getApiConfig('newsSearchLandingData');
      config.isOngoingCall = true;
      if (config.currentPage >= config.pageLimit) {
          config.ViewAllButton.css({'display': 'none'});
        }
      $.ajax(apiconfig)
        .done(response => {
          let searchValue = [];
          const responseData = response.resultsets[0].results;
          if(responseData.length){
            if(response.general.noresults === "true"){
              searchValue["searchResult"] = true;
              $(".show-noSearchResultsItems").attr('hidden',false);
              $(".search-outer-wrapper .search-result").attr('hidden',true);
            }else{
              searchValue["searchResult"] = false;
              $(".search-outer-wrapper .cta-button").attr('hidden',false);
            }

            searchValue["searchText"] = config.searchText;
            config.pageupper = parseInt(response.resultcount.pageupper);
            config.pagelower = parseInt(response.resultcount.pagelower);
            config.totalFeed = parseInt(response.resultcount.total);
            searchValue["total"] = config.totalFeed;
            if(config.totalFeed === config.pageupper ){
              config.ViewAllButton.css({'display': 'none'});
            }
          }
          window.global.handleBarTemplateInst.loadTemplate('#newsSearchTemplate', '#searchGridInfo', responseData, '');
          window.global.handleBarTemplateInst.loadTemplate('#newsHeaderTemplate', '#newsHeaderGrid', searchValue, 'replace');
          config.isOngoingCall = false;
          if (config.page >= config.pageLimit) {
            config.ViewAllButton.css({'display': 'block','visibility': 'hidden'});
            config.page++;
          }

          const items = $('#searchGridInfo li').length;
          const showImage = (items - config.productInPage) - 1;
            
         if(config.productInPage >= items ) {
            self.dynamicImage($('#searchGridInfo li:lt(' + config.productInPage + ')'));
           } else {
            self.dynamicImage($('#searchGridInfo li:gt(' + showImage + ')'));
           }

          self.checkForLink();
          config.loader.hide();
          if(!autoload){
            $('#searchGridInfo li:nth-child('+config.pagelower+') a.read-more').focus();
          }
        })
        .fail(err => {
          console.log(err);
          config.isOngoingCall = false;
          config.loader.hide();
        })
    }else{
      config.ViewAllButton.css({'display': 'none'});
    }
  }

  getParameterByName(name, url) {
      if (!url) url = window.location.href;
      name = name.replace(/[\[\]]/g, '\\$&');
      var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
          results = regex.exec(url);
      if (!results) return null;
      if (!results[2]) return '';
      return decodeURIComponent(results[2].replace(/\+/g, ' '));
  }

  checkForLink () {
     var href= $(".search-result ul li");
     $.each(href,function(){
      var hrefLink = $(this).find("a").attr("href");
      if(!hrefLink.match("/news")) {
        $(this).find("a").attr("target", "blank");
      }
    });
  }

  dynamicImage(ele){
    let $ele = $(ele);
    const $documentIcon =  $ele.find('.document-icon');
    const $webpageIcon = $ele.find('.webpage-icon')
          $documentIcon.empty();$webpageIcon.empty();
          $webpageIcon.append(`<img class="dynamicImg_web" src='${config.webImage}'>`);
          $documentIcon.append(`<img class="dynamicImg_doc" src='${config.documentImage}'>`);
}
  viewAllSubmit(ele, evt) {
    evt.preventDefault();
    config.page++;
    config.loader.show();
    self.render(0);
  }

  isElementOnScreen(elem) {
    if( elem.length == 0 ) {
      return;
    }
    const $window = $(window);
    const viewport_top = $window.scrollTop();
    const viewport_height = $window.height();
    const viewport_bottom = viewport_top + viewport_height;
    const $elem = $(elem);
    const top = $elem.offset().top;
    const height = $elem.height();
    const bottom = top + height;

    return (top >= viewport_top && top < viewport_bottom) ||
      (bottom > viewport_top && bottom <= viewport_bottom) ||
      (height > viewport_height && top <= viewport_top && bottom >= viewport_bottom);
  }

  handleElementInViewChecker() {
    if (config.ViewAllButton && config.ViewAllButton.length) {
      if (self.isElementOnScreen(config.ViewAllButton)) {
        if(!(config.totalFeed === config.pageupper)){
          config.loader.show();
          self.render(0);
        }
      }
    }
  }


}
let self;
const handleBarsHelperInst = window.global.handleBarsHelperInst;
window.global.newsInfoInstance = window.global.newsInfoInstance || new newsInfo();
window.global.newsInfoInstance.init();
