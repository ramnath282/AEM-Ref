/**
 * AEM.js
 * Version 1.0
 */
(function (global) {
    // "use strict";

    var version = 1.0,
        isLoaded = false,
        glob = typeof global === 'undefined' ? window : global,
        doc = glob.document,
        SVG_NS = 'http://www.w3.org/2000/svg',
        userAgent = (glob.navigator && glob.navigator.userAgent) || '',
        svg = (
            doc &&
            doc.createElementNS &&
            !!doc.createElementNS(SVG_NS, 'svg').createSVGRect
        ),
        isMS = /(edge|msie|trident)/i.test(userAgent) && !glob.opera,
        isFirefox = userAgent.indexOf('Firefox') !== -1,
        isChrome = userAgent.indexOf('Chrome') !== -1,
        playerV4 = "https://player.ooyala.com/static/v4/stable/"
        var playerVersion = document.getElementById("oo-player-version") != null ? document.getElementById("oo-player-version").value : '4.32.8',
        PLAYAEM = {
            version: version,
            isDependencyLoaded: typeof _ == "function" && typeof $ == "function" ? true : false,
            hasTouch: doc && doc.documentElement.ontouchstart !== undefined,
            isWebKit: userAgent.indexOf('AppleWebKit') !== -1,
            isFirefox: isFirefox,
            isChrome: isChrome,
            isSafari: !isChrome && userAgent.indexOf('Safari') !== -1,
            isTouchDevicePlay: /(Mobile|Android|Windows Phone)/.test(userAgent),
            SVG_NS: SVG_NS,
            onlyDesktop: window.innerWidth <= 1024,
            isMobile: window.innerWidth <= 767,
            win: typeof global === 'undefined' ? window : global,
            fadeAnimValue: 500,
            // this is a ooyala player config object.
           getOoyalaConfig: function(){
                return {
                    'pcode': document.getElementById("oo-player-pcode") != null ? document.getElementById("oo-player-pcode").value : [],
                    "playerBrandingId": document.getElementById("oo-player-id") != null ? document.getElementById("oo-player-id").children[0].value : [],
                    'autoplay': false,
                    'width': '100%',
                    'height': '100%',
                    "iosPlayMode": 'inline',
                    "skin": {
                        "config": playerV4 + playerVersion + "/skin-plugin/skin.json",
                        "inline": {
                            "startScreen": {
                                "showDescription": false,
                                "showTitle": false,
                                "playIconStyle": {
                                    "color": "white",
                                    "opacity": 1
                                }
                            },
                            "shareScreen": false,
                            "pauseScreen": {
                                "showTitle": false,
                                "showDescription": false
                            },
                            "closedCaptionOptions": {
                                "enabled": false
                            }
                        }
                    }
                }
            },
            bindingEventsConfig: function () {

                var events = {
                    "click [data-tracking-id]": "getTrackingValues",
                }

                return events;
            },
            /*
            @params
                param1 - bind Element
                param2 - cb function
            */
            bindEvents: function (eventName, el, callBack) {
                if (typeof callBack === 'function') {
                    $(document).on(eventName, el, function (evt) {
                        callBack(this, evt);
                    });
                } else {
                    console.log("Error:CB function not found for this element :" + el);
                }
            },
            bindLooping: function (name, evtParentObj) {
                // Regular expression used to split event strings.
                var eventSplitter = /(\S+)\s(.*)/;
                var i = 0,
                    names, splitKeys;
                for (names = _.keys(name); i < names.length; i++) {
                    splitKeys = names[i].match(eventSplitter).slice(1);
                    if (!_.isEmpty(splitKeys) && !_.isEmpty(name[names[i]]) && typeof evtParentObj[name[names[i]]] == "function") {
                        this.bindEvents(splitKeys[0], splitKeys[1], evtParentObj[name[names[i]]]);
                    } else {
                        console.log("Event Binding failed for " + splitKeys);
                    }
                }
            },
            requestAPICall: function (obj, cb) {
                return $.ajax({
                    type: obj.type,
                    url: obj.url,
                    contentType: 'application/json',
                    data: obj.body ? JSON.stringify(obj.body || '') : '',
                    success: function (response) {
                        if (typeof cb == "function") {
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

            getTrackingValues: function (elem, evt, trackingData, isVideoCategory) {
                var trackingVal;
                if (trackingData != undefined)
                    trackingVal = trackingData;

                else
                    trackingVal = $(elem).data("trackingId");

                if (_.isEmpty(trackingVal)) {
                    console.log("Warn: Tracking value should not be empty.. ");
                    return;
                }
                var valArr = trackingVal.split('|');
                var obj = {
                    event_name: valArr[0] || '', // action name
                    event_type: 'click', //action event type
                    item_clicked: valArr[1] || '', //category
                    item_subcategory: valArr[2] || '', //sub category element
                    location_name: valArr[3] || '',
                    video_discovery: valArr[4] || ''
                }
                var camelCaseName = camelize(valArr[0]);
                var evtName = camelCaseName.replace(/-/g, "");

                typeof sendToAnalytics == "function" && sendToAnalytics(obj, (evtName && evtName.toLowerCase() == "click" && elem.tagName == "A") ? "button" : evtName, isVideoCategory);

            },
            scrollToTop: function ($el) {
                $el.click(function (ele, evt) {
                    $('html,body').animate({
                        scrollTop: 0
                    }, 'slow');
                    $(".navbar-brand").focus();
                    //return false;
                });
            },
            showScrollButton: function ($el) {
                var self = this,
                    footerHeight = $('footer').outerHeight() + 10,
                    lastScrollTop = 0,
                    windowHeight = $(window).height(),
                    documentHeight = $(document).height(),
                    offset = windowHeight / 2,
                    opaqueTimer;

                $(window).scroll(function () {
                    if ($(this).scrollTop() >= offset) {
                        $el.fadeIn(this.fadeAnimValue);
                    } else {
                        $el.fadeOut(this.fadeAnimValue);
                    }
                    var st = $(this).scrollTop();
                    if (st > lastScrollTop) {
                        if (($(this).scrollTop() >= (documentHeight - windowHeight - 10)) || ($(this).scrollTop() <= (documentHeight - offset))) {
                            $el.css({
                                WebkitTransition: 'opacity 1s ease-in-out',
                                MozTransition: 'opacity 1s ease-in-out',
                                MsTransition: 'opacity 1s ease-in-out',
                                OTransition: 'opacity 1s ease-in-out',
                                transition: 'opacity 1s ease-in-out'
                            })


                                 

                        }
                    }
                    lastScrollTop = st;
                });

            },
            setObjectStorage: function (setName, obj) {
                if (this.getObjectStorage(setName) != null) global.sessionStorage.removeItem(setName);
                sessionStorage.setItem(setName, JSON.stringify(obj));
            },
            getObjectStorage: function (name) {
                return JSON.parse(sessionStorage.getItem(name));
            },
            compareStorageDate: function (obj, storageName) {
                if (obj == null || obj['timestamp'] == undefined || obj['locale'] == null || obj['locale'] != $("#siteCountry").val()) {
                    window.sessionStorage.removeItem(storageName);
                    return false;
                }
                var expiresIn = obj.timestamp,
                    now = Date.now(),
                    objList = false;
                if (expiresIn < now) { // will Expire in 1Day
                    window.sessionStorage.removeItem(storageName);
                    console.log(storageName + " data Expired");
                } else {
                    objList = obj['obj']
                }
                return objList;
            },
            storageExpiryDate: function () {
                return Date.now() + (24 * 60 * 60) * 1000; //1day
            },
            fullWidthTextBlock: function () {
                function isEmpty(el) {
                    return !$.trim(el.html())
                }
                if (isEmpty($('.page-error-component .image-block'))) {
                    $('.page-error-component .text-block').addClass('full-width'); //will Change to width 100%
                }
            },
            imgHoverOver: function () {
                $(".image-container[data-hover!=undefined]").mouseover(function () {
                    $(this).attr('src', $(this).data("hover"));
                    $(this).attr('alt', $(this).data("hoveralt"));
                }).mouseout(function () {
                    $(this).attr('src', $(this).data("src"));
                });
            },
            maintainHeight: function (elem) {
                var self = this,
                    $heightSyncElem = elem ? $(elem) : $('[data-maintain-height]'),
                    maxHeight;
                if (!$heightSyncElem.length) return;
                _.each($heightSyncElem, function (item) {
                    if ($(item).is(":visible")) {
                        if (typeof $.fn.imagesLoaded == "function") {
                            $(item).find('img').imagesLoaded(function () { // image ready
                                maxHeight = self.getMaxHeight(item);
                                $(item).find($(item).data('maintain-height')).css('height', maxHeight + 'px');
                            });
                        } else {
                            maxHeight = self.getMaxHeight(item)
                            $(item).find($(item).data('maintain-height')).css('height', maxHeight + 'px');
                        }
                    }
                });
            },
            getMaxHeight: function (item) {
                var max = -1,
                    height;
                _.each($(item).find($(item).data('maintainHeight')), function (el) {
                    height = $(el).height();
                    max = height > max ? height : max;
                });
                return max;
            },
            isTouchDevice: function () {
                try {
                    document.createEvent("TouchEvent");
                    return true;
                } catch (e) {
                    return false;
                }
            },
            pageLoadActions: function () {
                $(".inner-wrapper").attr({
                    "role": "main",
                    "id": "page-content"
                });
                var $scrollTop = $(".play-backtotop");
                if ($scrollTop.length) {
                    this.showScrollButton($scrollTop);
                    this.scrollToTop($scrollTop);
                }
                this.fullWidthTextBlock();
                this.imgHoverOver();
            },
            init: function () {
                if (!this.isDependencyLoaded) return;
                this.bindLooping(this.bindingEventsConfig(), this);
                this.ooParams = this.getOoyalaConfig();
                this.maintainHeight();
                if (this.isTouchDevice()) {
                    $('body').addClass("has-touch");
                }
            }
        }
    window.PLAYAEM = PLAYAEM;
    PLAYAEM.init();
    document.addEventListener('DOMContentLoaded', function () {
        PLAYAEM.pageLoadActions();
    }, false);

}(window));
