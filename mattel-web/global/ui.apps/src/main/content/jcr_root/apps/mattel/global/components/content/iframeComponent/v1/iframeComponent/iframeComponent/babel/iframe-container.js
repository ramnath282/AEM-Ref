const iframeContainer = (() => {
    const mediaQueryDef = {
        xsm: window.matchMedia("(max-width: 545px)"),
        sm: window.matchMedia("(max-width: 760px)"),
        md: window.matchMedia("(min-width:767px) and (max-width: 992px)")
    };
    const spacing = {
        xsm: 15,
        sm: 15,
        lg: 25
    }
    const getDeviceName = () =>{
        let deviceName;
        let width = window.innerWidth;
        if (width <= 480) {deviceName = "mobilePortrait"} 
        else if (width <= 767) {deviceName = "mobile"} 
        else if (width <= 980) {deviceName = "tabletPortrait"} 
        else if (width <= 1200) {deviceName = "tablet"} 
        else {deviceName = "desktop"}
        return deviceName;
    }
    const headerHeight = () => `${$(".experiencefragment_header").length ? $(".experiencefragment_header").height() : $(".outer-wrapper .page-view").offset().top}`
    let deviceName = getDeviceName(),
        $el, 
        elemLength = 0,
        browserResize= false,
        datasets;

    const applyMediaQueries = () => {
        datasets = $el[0].dataset;
        let dimensions,padding,headerInnerHeight = headerHeight(),isDynamicHeight = !$el.hasClass('with-dynamic-height');
        if (mediaQueryDef.xsm.matches || mediaQueryDef.md.matches) { // mobile portrait
            dimensions = datasets.xsmDimension.split("*");
            padding = isDynamicHeight ? datasets.xsmSpacing : spacing.xsm;
        } else if (mediaQueryDef.sm.matches) { // mobile landscape + tab
            dimensions = datasets.smDimension.split("*");
            padding = isDynamicHeight ? datasets.smSpacing : spacing.sm;
        } else { // desktop
            dimensions = datasets.lgDimension.split("*");
            padding = datasets.lgSpacing || spacing.lg;
        }
        $el.css({
            'width': dimensions[0],
            'height': dimensions[1],
            '--app-height': window.innerHeight - headerInnerHeight,
            'padding': padding
        })
        if(headerInnerHeight >100){ // header height resetting
            setTimeout(() =>{$('.iframe-global-content').css('--app-height', window.innerHeight - headerHeight());},1000)
        }
    }
    const postLoadAction = () => {
        let $iframeEle = $(".iframe-global-content");
        for (let i = elemLength; i < $iframeEle.length; i++) {
            iframeContainer.initialize();
        }
    };
    const iFrameLoadEvent = elem => {
        elem.addClass('iframe-loading');
        elem.find('iframe').on('load', ()=> {
            elem.removeClass('iframe-loading');
        });
    };
    const resize = () => {
        if(deviceName == getDeviceName()){return;}
        browserResize= true;
        deviceName = getDeviceName();
        elemLength = 0;
        postLoadAction();
    };
    const render = () => {
        $el = $(`.iframe-global-content:eq(${elemLength})`);
        if (!$el.length) return;
        applyMediaQueries();
        !browserResize && iFrameLoadEvent($el);
        elemLength++;
    };
    return {
        initialize: render,
        windowResize: resize,
        postLoad: postLoadAction
    };

})();

iframeContainer.initialize();

window.addEventListener("resize", iframeContainer.windowResize);
document.addEventListener("DOMContentLoaded", iframeContainer.postLoad);