
(() => {
  const config = {
    el: ".slidein",
    slideInScrollPos: 100
  };
  class slideInmodal {
    constructor() {
      self = this;
      self.init();
    }
    init() {
      self.initEvents();
      self.compStyle();
    }
    initEvents() {
      // @ts-ignore
      window.global.eventBindingInst.bindLooping(
        {
          [`click ${config.el} .close button`]: "hideFormComp"
        },
        self
      );
    }
    compStyle() {
      $(config.el).each(function(){
        self.bindScrollEvent(this);
      });     
    }    
    bindScrollEvent(ele) {
      let scrollTop;
      const $ele = $(ele);
      let scrollAcheived = false;
      $( window ).scroll(function() {
        scrollTop = $(window).scrollTop();
        console.log(scrollTop > config.slideInScrollPos && !scrollAcheived);
        if (scrollTop > config.slideInScrollPos && !scrollAcheived && cookieCheckModalSlideDisplay(config.el)) {
          scrollAcheived = true;
          $ele.addClass("active");
        }
      });
    }

    // @ts-ignore
    hideFormComp(ele, evt) {        
      $(config.el).animate(
          {
            opacity: 0
          },
          500,
          function() {
            $(this).hide(); // applies display: none; to the element .panel
          }
        );
    }    
  }
 

  let self;
  $(document).ready(function(){
          const slideInmodalInit = new slideInmodal();
      });
  const cookieCheckModalSlideDisplay = window.global.cookieCheckModalSlideDisplay;
    
})();