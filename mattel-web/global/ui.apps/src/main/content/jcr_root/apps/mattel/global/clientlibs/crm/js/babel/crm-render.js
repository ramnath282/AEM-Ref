//moved from initialize-js
const CRMRender = function () {
    self = this;
    this.init();
};
CRMRender.prototype = {
    render() {
        var bodyDesktopImg = new Image();
        var bodyMobileImg = new Image();
        if(typeof $('body').attr('data-desktopimage') !== 'undefined'){
            bodyDesktopImg.src=$('body').attr('data-desktopimage');
        }
        if(typeof $('body').attr('data-mobileimage') !== 'undefined'){
             bodyMobileImg.src=$('body').attr('data-mobileimage');
        }
        var styleElem = document.head.appendChild(document.createElement("style"));
        styleElem.innerHTML = '.bg-img:before {background-image: url('+$('body').attr('data-desktopimage')+')} @media screen and (max-width:767px) { .bg-img:before {background-image: url('+$('body').attr('data-mobileimage')+')}}';
        if($('body').attr('data-desktopimage') || $('body').attr('data-mobileimage')) {
            $('body').addClass("bg-img");
        }

    },
    resize() {
        var _self = this;
        $(window).on("resize load ", function() {
               if(window.innerWidth <= 767) {
                  $("footer").css("margin-top",0);
                }else {
                  _self.setFooter();
                }
        });


    },
    setFooter(){
        var rootHeight = $(".root").outerHeight();
        var windowHeight = $(window).outerHeight();
        var marginTop = parseInt(windowHeight) - parseInt(rootHeight);
        if(marginTop > 0) {
            $(".footer").css("margin-top",marginTop);
        }

    },
    init() {
        this.render();
        this.resize();
    }
};
let self;
const evtBinding = window.global.eventBindingInst;
const gemCRMImageBlockInit = new CRMRender();