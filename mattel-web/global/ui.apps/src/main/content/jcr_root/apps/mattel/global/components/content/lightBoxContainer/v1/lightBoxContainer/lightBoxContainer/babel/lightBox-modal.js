(() => {
  const config = {
    el: ".modal-component",
    enableOnload: $(".modal-component.enable-modal-onpageload").length ? true : false
  };
  class ModalComponent {
    constructor() {
      self = this;
      self.init();
    }
    init() {      
      self.initEvents();      
      self.removeDefaultModalAction();
      if(config.enableOnload && cookieCheckModalSlideDisplay(config.el)) {
        $(config.el).modal("show");
      }
    }
    removeDefaultModalAction(ele, evt){
      let $el = $(config.el).find(".modal .close");
      $el.each(function(){
        $(this).attr("data-dismiss", "");
      })
    }
    initEvents() {
      window.global.eventBindingInst.bindLooping(
        {
          [`click ${config.el} .close`]: "hideModal"
        },
        self
      );
      $(config.el).on('shown.bs.modal', function () {
        $('body').addClass("modal-component-open");
      });
      $(config.el).on('hidden.bs.modal', function () {
        if(!$(config.el).is(':visible')) {          
          $('body').removeClass("modal-component-open");
        }        
      });
    }
    hideModal(ele, evt){
      var m = $(ele).parents(".modal");
	    m.eq(0).modal("hide");
    }
  }
 

  let self;
  $(document).ready(function(){
    const modalInit = new ModalComponent();
  });
  const cookieCheckModalSlideDisplay = window.global.cookieCheckModalSlideDisplay;
})();