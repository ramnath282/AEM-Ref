/**
* AEM.js
* Version 1.0
*/
(function(global) {
    // "use strict";

    var version = 1.0,
        //isLoaded = false,
        glob = typeof global === 'undefined' ? window : global,
        doc = glob.document,
        SVG_NS = 'http://www.w3.org/2000/svg',
        userAgent = (glob.navigator && glob.navigator.userAgent) || '',
        /*svg = (
            doc &&
            doc.createElementNS &&
            !!doc.createElementNS(SVG_NS, 'svg').createSVGRect
        ),
        isMS = /(edge|msie|trident)/i.test(userAgent) && !glob.opera, */
        isFirefox = userAgent.indexOf('Firefox') !== -1,
        isChrome = userAgent.indexOf('Chrome') !== -1,
        AGAEM = {
            version: version,
            isDependencyLoaded: typeof _ == "function" && typeof $ == "function" ? true : false,
            hasTouch: doc && doc.documentElement.ontouchstart !== undefined,
            isWebKit: userAgent.indexOf('AppleWebKit') !== -1,
            isFirefox: isFirefox,
            isChrome: isChrome,
            isSafari: !isChrome && userAgent.indexOf('Safari') !== -1,
            isTouchDevice: /(Mobile|Android|Windows Phone)/.test(userAgent),
            SVG_NS: SVG_NS,
            onlyDesktop : window.innerWidth < 1024,
            isMobile : window.innerWidth <= 767,
            win: typeof global === 'undefined' ? window : global,
            scrollTop : '100',
            root : ".root",
            /*
            @params
                param1 - bind Element
                param2 - cb function
            */
            bindEvents: function (eventName,el, callBack) {
                if (typeof callBack === 'function') {
                    $(document).on(eventName, el, function (evt) {
                        callBack(this, evt);
                    });
                } else {
                    console.log("Error:CB function not found for this element :" + el);
                }
            },
            bindingEventsConfig: function() {
                var events = {
                    "click .open-interstitial": "openInterstitial",
                    "click .recipe-card .print-me" : "printCard"
                }
                return events;
            },
            bindLooping: function (name,evtParentObj) {
                // Regular expression used to split event strings.
                var eventSplitter = /(\S+)\s(.*)/;
                var i = 0, names, splitKeys;
                for (names = _.keys(name); i < names.length; i++) {
                    splitKeys = names[i].match(eventSplitter).slice(1);
                    if(!_.isEmpty(splitKeys) && !_.isEmpty(name[names[i]]) && typeof evtParentObj[name[names[i]]] == "function"){
                        this.bindEvents(splitKeys[0], splitKeys[1], evtParentObj[name[names[i]]]);
                    } else{
                        console.log("Event Binding failed for " +splitKeys);
                    }
                }
            },
            requestAPICall: function (obj, cb) {
                /*var self = this;*/
                return $.ajax({
                    type: obj.type,
                    url: obj.url,
                    contentType: 'application/json',
                    data: obj.body ? JSON.stringify(obj.body) : '',
                    success: function (response) {
                        if(typeof cb == "function") {
                            cb(response)
                        }
                    },
                    error: function (errrLog) {
                        if (typeof cb == 'function') {
                            cb(false);
                            console.log(obj.methodName + "API Error!", "error");
                        }
                    }
                });
            },
            scrollToTop : function($el){
               $el.click(function(ele, evt) { 
                $('html,body').animate({scrollTop:0},'slow');
                    $(".brand-logo").focus();
                    return false;
             });
            },
            showScrollButton : function($el){
               /*var self = this;*/
                $(window).scroll(function(){
                    var isPlp = $("body").hasClass("ecomm-plp-page") || $("body").hasClass("ecomm-search-results-page") ? true : false;
                   // console.log($(this).scrollTop() , $(window).height())
                    if ( $(this).scrollTop() >= $(window).height() ) {
                        $el.addClass('active');
                        if(!isPlp){
                            $(".back-to-top").fadeIn();
                        }
                    }
                    else{
                        $el.removeClass('active');
                        if(!isPlp){
                            $(".back-to-top").fadeOut();
                        }
                    }
                });
            },
            openInterstitial: function(ele,evt){
                var $ele = $(ele),
                    $modalElem = $(".interstial-modal"),
                    title= $ele.data("warning"),
                    des = $ele.data("age"),
                    href = $ele.attr("href");
                    
                if(!$modalElem.length || !title || !des || !href){
                    console.log("Modal element not here..")
                    return;
                }
                $modalElem.find(".modal-title").html(title);
                $modalElem.find(".modal-disclaimer").html(des);
                $modalElem.find(".keep-going").attr('href',href);
                $modalElem.modal('show');
                evt.preventDefault();
            },
            pageLoadActions: function(){
                $(this.root).attr({"role" : "main", "id" : "page-content"});
                if($(".article-page").length>0) {
                    $(this.root).addClass("container").css({"margin-left": "auto", "margin-right": "auto"});
                    $(".article-page .container-fluid").each(function(){
                        var position = $(this).css("position");
                        if(position !="absolute" && position !="fixed") {
                            $(this).addClass("full-width-container");
                        }
                    });
                }
                var $scrollTop = $("#return-to-top");
                if($scrollTop.length) {
                    this.showScrollButton($scrollTop);
                    this.scrollToTop($scrollTop);
                }
            },
            setObjectStorage : function(setName,obj){
                if(this.getObjectStorage(setName)!=null) global.sessionStorage.removeItem(setName);
                sessionStorage.setItem(setName, JSON.stringify(obj));
            },
            getObjectStorage : function(name){
                return JSON.parse(sessionStorage.getItem(name));
            },
            compareStorageDate : function(obj, storageName){
                if(obj==null || obj['timestamp']==undefined){
                    window.sessionStorage.removeItem(storageName);
                    return false;
                }
                var expiresIn = obj.timestamp,
                    now = Date.now(),
                    objList = false;
                 if(expiresIn<now) {// will Expire in 1Day
                    window.sessionStorage.removeItem(storageName);
                    console.log(storageName +" data Expired");
                 } else{
                    objList = obj['obj']
                 }
                return objList;
            },
            storageExpiryDate : function(){
                return Date.now()+(24*60*60)*1000; //1day
            },
            printCard: function(ele,evt){
                $(".recipe-card").addClass("print-hide");   
                var index = $(".print-me").index(ele);
                $(".recipe-card").eq(index).removeClass("print-hide");                
                evt.preventDefault();                
                $("body").addClass("recipe-card-page");                
                window.print();
            },
            init: function() {
                this.bindLooping(this.bindingEventsConfig(), this);
            }

        }
    global = global || window;    
    global.AGAEM = AGAEM;
    AGAEM.init();
    document.addEventListener('DOMContentLoaded', function () {
        AGAEM.pageLoadActions();
        if($('.hc-main-container').length || $(".basepage-ecomm .parallax .rating-section").length) {
            setTimeout(function(){
                AOS.init();
            },1500);
        } else {
            $(".parallax-banner [data-aos],.pdp-col-container [data-aos], .columnControl [data-aos], .imageAndText [data-aos]").removeAttr("data-aos");
        }
    }, false);
}(window));

