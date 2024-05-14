const config = {
  ViewAllButton: $(".articlecomponent-container .btn-cta-item"),
  loader: $(".loader-outer"),
  elementVar: '.articlecomponent-outer-wrapper',
  sAndPLink: $("#snpAccountUrl").val(),
  defaultImage: $("#defaultImage").val(),
  pageLimit: 4,
  defaultPageLimit: 1,
  defaultProduct: parseInt($("#DefaultLoad").val()),
  productLimit: parseInt($("#ProductLimit").val()),
  totalCount: 0,
  page_lower: 0,
  page_upper: 1,
  currentPage: 0,
  isOngoingCall: false,
  detailUrlPrefix: location.protocol + '//' + location.hostname,
  totalVisiblPerFold: 0,
  sortByDate: $("#sortByDate").val(),
  x1: $("#x1").val(),
  q1: $("#q1").val(),
}

class articlecomponentInfo {
  constructor() {
    this.element = config.elementVar;
    self = this;
    window.global.eventBindingInst.bindLooping(self.bindingEventsConfig(), self);
    self.payloadObj = {};
  }

  getApiConfig(name) {

    let url;
    var date_sorting = "";
    if (config.sortByDate != "") {
      date_sorting = '&sort=' + config.sortByDate;
    }
    if (config.q1 != "") {
      url = config.sAndPLink + '?do=article' + date_sorting + '&count=' + config.defaultProduct + '&page=' + config.defaultPageLimit + '&x1=' + config.x1 + '&q1=' + encodeURIComponent(config.q1);
    } else {
      url = config.sAndPLink + '?do=article' + date_sorting + '&count=' + config.defaultProduct + '&page=' + config.defaultPageLimit;
    }
    const articleConfig = {
      "articleComponentData": {
        "type": 'get',
        "accept": 'application/json',
        "crossDomain": true,
        "dataType": 'jsonp',
        "url": url
      },

    }
    return articleConfig[name];
  }

  init() {
    if (this.element.length) {
      config.loader.show();
      this.render(1)
    }
    //window.global.eventBindingInst.bindLooping({}, self)

    $(document).ready(function () {
      window.addEventListener('scroll', function (e) {
        if (config.currentPage >= config.pageLimit) {
          self.handleElementInViewChecker();
        }
      });
    });

  }

  bindingEventsConfig() {
    let eventsArr = {
      "click .articlecomponent-container .btn-cta-item": "viewAllSubmit",
    };
    return eventsArr;
  }

  render(autoload) {
    //const classRef = self;
    if (config.isOngoingCall) return;
    if (config.page_upper === config.totalCount) return;
    const apiconfig = self.getApiConfig('articleComponentData');
    config.isOngoingCall = true;
    if (config.currentPage >= config.pageLimit) {
      config.ViewAllButton.css({ 'display': 'none' });
    }
    $.ajax(apiconfig)
      .done(response => {
        let responseData;
        config.totalCount = parseInt(response.resultcount.total);
        config.page_lower = parseInt(response.resultcount.pagelower);
        config.page_upper = parseInt(response.resultcount.pageupper);
        config.totalVisiblPerFold = (config.page_upper - config.page_lower) + 1;
        responseData = response.resultsets[0].results;

        if ((config.totalCount === config.page_upper)) {
          responseData["dataEnd"] = true;
        } else {
          responseData["dataEnd"] = false;
        }

        if (response.general.query !== "") {
          responseData.general = response.general.query;
        }
		
		responseData = responseData.filter((_, index) => index < config.totalVisiblPerFold).map(results => {
          return _.extend({}, results,
            {
              imageLink: self.mediaLinkCheck(results.imageLink),
            }
          );
        });

        window.global.handleBarTemplateInst.loadTemplate('#articlecomponentTemplate', '#articlecomponentInfo', responseData, '');
        config.isOngoingCall = false;
        config.currentPage++;
        if (config.currentPage >= config.pageLimit) {
          config.ViewAllButton.css({ 'display': 'block', 'visibility': 'hidden' });
          config.defaultPageLimit++;
        } else if (config.page_upper < config.defaultProduct) {
          config.ViewAllButton.css({ 'display': 'none' });
        }

        if (config.page_upper === config.totalCount) {
          config.ViewAllButton.css({ 'display': 'none' });
        }

        config.loader.hide();
        if (!autoload) {
          $('#articlecomponentInfo li:nth-child(' + config.page_lower + ') a.total-content').focus();
        }
      })
      .fail(err => {
        config.isOngoingCall = false;
        console.log(err);
        config.loader.hide();
      })
  }

  isElementOnScreen(elem) {
    if (elem.length == 0) {
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
  
  mediaLinkCheck(imgUrl) {
    let updatedImgUrl;
    if (imgUrl && imgUrl.length) {
      updatedImgUrl = imgUrl;
    } else {
      updatedImgUrl = config.defaultImage;
    }
    return updatedImgUrl;
  }

  handleElementInViewChecker() {
    if (config.ViewAllButton && config.ViewAllButton.length) {
      if (self.isElementOnScreen(config.ViewAllButton)) {
        if (config.totalCount !== config.page_upper) {
          config.loader.show();
          self.render(0);
        }
      }
    }
  }

  viewAllSubmit(ele, evt) {
    evt.preventDefault();
    config.defaultPageLimit++;
    config.defaultProduct = config.productLimit;
    config.loader.show();
    self.render(0);
  }

}

window.global.articlecomponentInstance = window.global.articlecomponentInstance || new articlecomponentInfo();
window.global.articlecomponentInstance.init();