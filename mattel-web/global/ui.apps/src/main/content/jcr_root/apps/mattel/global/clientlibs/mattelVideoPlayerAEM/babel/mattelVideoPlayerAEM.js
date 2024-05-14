/*
comp list:
    1. video gallery (play, reusability, global)
    2. video Detail
    3. image/video and text 
*/

let autoplayQueueYTVideos= [];
class videoProps {
    constructor() {
        this.el = config.el;
        this.ytVideoRegex =/^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#&?]*).*/;
        this.ytIframeCbCheck = 0;
        this.deviceName = getDeviceName();
        this.bindModalEvent();
        self = this;
    }
    createIDsforPlayers(elem) {
        const $videoEle = $(elem);
        if (!$videoEle.length) {
            console.log("Warn: featured video player not exists..");
            return;
        }
        _.each($videoEle, (item) => {
            playerProps.playerIndx++;
            $(item).attr({
                'id': `aem-video-player-${playerProps.playerIndx}`,
                'data-video-index': playerProps.playerIndx
            })
        });
    }
    compareStorageDate(obj, storageName) {
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
    }
    getProperPlayerEvents(player) {
        if (typeof player.paused == "function") {
            return {
                isPaused: player.paused(),
                isFullscreen: player.isFullscreen(),
                isPlaying: this.isVideoPlaying(player),
            }
        }
        player = player[0];
        return {
            isPaused: player.paused,
            isFullscreen: false,
            isPlaying: this.isVideoPlaying(player),
        }
    }
    playerScrollAction(elem, isElemVisible) {
        const $ele = $(elem);
        const dataAttr = $ele[0].dataset;
        const videoIndex = parseInt(dataAttr.videoIndex || 0);
        let curPlayer = playerProps.players[videoIndex];
        if (curPlayer == undefined) return;
        const propEvents = this.getProperPlayerEvents(curPlayer);
        if (typeof curPlayer.pause != 'function') {
            curPlayer = curPlayer[0];
        }
        if (isElemVisible && playerProps.actionProps[videoIndex] && playerProps.actionProps[videoIndex].paused && !playerProps.actionProps[videoIndex].forcePaused) {
            if (propEvents.isPaused && !playerProps.isTouchDevice) {
                curPlayer.play();
                playerProps.actionProps[videoIndex] = {
                    paused: false,
                    playing: true
                };
            }
        } else if (isElemVisible && ((dataAttr.autoplay == "true" || dataAttr.autoplay == true) && !playerProps.actionProps[videoIndex]) && !playerProps.isTouchDevice) {
            curPlayer.play();
            playerProps.actionProps[videoIndex] = {
                paused: false,
                playing: true
            };
        } else if (isElemVisible == false && propEvents.isPlaying) {
            setTimeout(() => {
                if (!propEvents.isFullscreen) {
                    curPlayer.pause();

                    setTimeout(() => {
                        playerProps.actionProps[videoIndex] = {
                            paused: true,
                            playing: false,
                            forcePaused: false
                        };
                    }, 100)
                }
            }, 1000)
        }
    }
    bindScrollEvent() {
        let timer,
            scrollPos,
            elem
        $(window).on("scroll", () => {
            if (timer) clearTimeout(timer);

            timer = setTimeout(() => {
                if (!playerProps.isIOSDevice) {
                    playerProps.players.forEach((player, index) => {
                        elem = $(`#aem-video-player-${index}`)
                        if(elem.data("loop") == true) return;
                        scrollPos = this.isScrolledIntoView(elem);
                        if (scrollPos > 0.2) {
                            if (playerProps.scrollingActive[index] != true) {
                                this.playerScrollAction(elem, true);
                                playerProps.scrollingActive[index] = true;
                            }
                        } else if (playerProps.scrollingActive[index] == true) {
                            this.playerScrollAction(elem, false);
                            playerProps.scrollingActive[index] = false;
                        }
                    });
                }
            }, 50);
        });
    }
    playerInitORUpdate(elem, cb) {
        const $ele = $(elem);
        const dataAttr = $ele[0].dataset;
        dataAttr.elemId = $ele.attr("id");
        const videoIndex = parseInt(dataAttr.videoIndex || 0);
        const isMobile = self.deviceName == "mobile" || self.deviceName == "mobilePortrait";
        if(isMobile){
            const checkVideoIDForMobile = dataAttr.xsVideoId;
            if(checkVideoIDForMobile) dataAttr.videoId = checkVideoIDForMobile;
        }
        if (playerProps.players[videoIndex]) {
            window.global.updateDeluxePlayer(playerProps.players[videoIndex], dataAttr.videoId);
            $('html, body').animate({
                scrollTop: elem.offset().top - ($("header").height() || 0) - 100 //#DIV_ID is an example. Use the id of your destination on the page
            }, 500);
            return;
        }
        window.global.createDeluxePlayer(dataAttr.elemId, dataAttr.videoId, (player) => {
            if ($("#sub-" + dataAttr.elemId).length) {
                $("#sub-" + dataAttr.elemId).css({
                    "width": '100%',
                    "padding-top": '56.25%',
                    "height": "auto"
                });
            } else {
                $("#" + dataAttr.elemId).find('video').css({
                    "width": '100%'
                });
            }
            const events = this.getProperPlayerEvents(player);
            player.on('loadstart', () => {
                if ((dataAttr.autoplay == "true" || dataAttr.autoplay == true) && events.isPaused && this.isScrolledIntoView($("#" + dataAttr.elemId)) > 0 && !playerProps.isIOSDevice && !playerProps.isTouchDevice) {
                    typeof player.play == "function" ? player.play() : player[0].play();
                }
            });
            player.isAutoPlay = dataAttr.autoplay == "true" || dataAttr.autoplay == true;
            typeof cb === "function" && cb(player);
            !playerProps.players.length && this.bindScrollEvent();
            playerProps.players[videoIndex] = player;
            this.playerActionBinding(player);
            if (self.deviceName != "mobilePortrait" && self.deviceName != "mobile") {
                var elem = "div#video-gallery-player-component.reusability-video-component.loaded"
                if ($(elem).parents('.youtube').length >= 1) {
                    $(elem).parents('.youtube').find('.reusability-videos-gallery-playlist').height($(elem).find(".video-player").height());
                }
            }
        });
    }
    playerActionBinding(player, index) {
        player.on("playing", event => {
            let videoIndex = parseInt($("[id^=aem-video-player]").filter(`[data-video-id=${event.target.parentElement.dataset.videoId}]`).data("videoIndex") || 0);
            playerProps.actionProps[videoIndex] = {
                playing: true,
                paused: false
            };
        });
        player.on("pause", event => {
            let videoIndex = parseInt($("[id^=aem-video-player]").filter(`[data-video-id=${event.target.parentElement.dataset.videoId}]`).data("videoIndex") || 0);
            playerProps.actionProps[videoIndex] = {
                playing: false,
                paused: true,
                forcePaused: true
            };
        });
    }
    readableDuration(seconds) {
        let sec = Math.floor(seconds),
            min = Math.floor(sec / 60);
        min = min >= 10 ? min : '0' + min;
        sec = Math.floor(sec % 60);
        sec = sec >= 10 ? sec : '0' + sec;
        return min + ':' + sec;
    }
    getTrackingValuesForUtag(params) {
        let { event_type, item_clicked, item_subcategory, videoId, event_name, platformName, videoDuration } = params;
        if (_.isEmpty(params)) {
            console.log("Warn: Tracking value should not be empty.. ");
            return;
        }
        Object.assign(utag_data, {
            "event_action": event_name,
            "event_action_type": event_type,
            "event_detail": item_clicked,
            "event_detail_sub": item_subcategory,
            "video_id": `${platformName}-${videoId}`,
            "video_length": videoDuration,
            "video_platform": platformName
        });
        utag.link(utag_data);
    }
    getTrackingValues(elem, evt, trackingData, isVideoCategory, playerAttrs) {
        let trackingVal;
        trackingVal = trackingData != undefined ? trackingData : $(elem).data("trackingId");
        if (_.isEmpty(trackingVal)) {
            console.log("Warn: Tracking value should not be empty.. ");
            return;
        }
        let valArr = trackingVal.split('|'),
            platformName, videoId, videoDuration;
        if (playerAttrs) {
            platformName = playerAttrs.html5Player ? "Deluxe" : "Brightcove";
            videoId = playerAttrs.el_.parentElement.dataset.videoId;
            videoDuration = self.readableDuration(playerAttrs.duration());
        }
        let obj = {
            event_name: valArr[0] || '', // action name
            event_type: 'click', //action event type
            item_clicked: valArr[1] || videoId || '', //category
            item_subcategory: valArr[2] || '', //sub category element
            location_name: valArr[3] || '',
            video_discovery: valArr[4] || ''
        }
        if (typeof utag_data == "object" && typeof utag == "object" && typeof utag.link == "function") {
            Object.assign(obj, { videoId, platformName, videoDuration });
            self.getTrackingValuesForUtag(obj);
            return;
        }
        let camelCaseName = typeof camelize == 'function' ? camelize(valArr[0]) : valArr[0];
        let evtName = camelCaseName.replace(/-/g, "");

        typeof sendToAnalytics == "function" && sendToAnalytics(obj, (evtName && evtName.toLowerCase() == "click" && elem.tagName == "A") ? "button" : evtName, isVideoCategory);
    }
    isVideoPlaying(video) {
        if (!video) return false;
        if (typeof video.currentTime != "function") {
            if (typeof video[0] != "undefined") video = video[0];
            return !!(video.currentTime > 0 && !video.paused && !video.ended && video.readyState > 2);
        }
        return !!(video.currentTime() > 0 && !video.paused() && !video.ended() && video.readyState() > 2);
    }
    isScrolledIntoView(elem) {
        var elementTop = $(elem).offset().top;
        var elementBottom = elementTop + $(elem).outerHeight();
        var viewportTop = $(window).scrollTop() - 200;
        var viewportBottom = viewportTop + $(window).height();

        return elementBottom > viewportTop && elementTop < viewportBottom;
    }
    heightSync(elem) {
        let max = -1;
        const $heightElem = $(elem).find(".tile-content");
        $(elem).find('img').imagesLoaded(() => { // image ready
            _.each($heightElem, el => {
                const height = $(el).innerHeight();
                max = height > max ? height : max;
            });

            $heightElem.css('height', `${max}px`);
        });
        return;
    }
    updateTitleAndDescription(elem, player, obj) {
        $(elem).find("#video-player-title").html(obj.title);
        $(elem).find("#video-player-desc").html(obj.description);
        $(player).attr('aria-label', `${obj.title} Video`);
    }
    addClassOnlyActive(elem) {
        const $parentUL = elem.parents("ul");
        $parentUL.find('li').removeClass("active");
        elem.addClass("active").removeClass("play-disable");
    }
    stopVideoPlayer(ele){
        let isYTPlayer = $(ele).find("#youtube_player"),
            isAEMPlayer = $(ele).find('[id^=aem-video-player-] video');
        if(isYTPlayer.length && youtubeVideoPlayerInit){
            youtubeVideoPlayerInit.ytPlayer.stopVideo()
        } else if(isAEMPlayer.length){
            isAEMPlayer[0].pause();
            isAEMPlayer[0].currentTime = 0;
        }
    }
    bindModalEvent(){
        const ele = '.lightBoxContainer .modal,.lightBoxContainer .slidein';
        if(!$(ele).length || $(ele).hasClass("evt-binded")) return;
        $(ele).addClass("evt-binded");
        if($(ele).hasClass("modal")){
            $(document).on('hidden.bs.modal',ele, function () {
                self.stopVideoPlayer(this);
            });
        } else{
            $(document).on('click','.lightBoxContainer .slidein', function () {
                self.stopVideoPlayer(this);
            });
        }
    }
    loadYTAPI(){
        if(this.YTAPILoaded || (typeof YT == "object" && typeof YT.Player == "function")) return;
        bindingYTEvents();
        const tag = document.createElement('script'),
            firstScriptTag = document.getElementsByTagName('script')[0];
        tag.src = "https://www.youtube.com/iframe_api";
        firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);
        this.YTAPILoaded=true;
    }
    YTPlayerCommands(ele){
        autoplayQueueYTVideos.push($(ele));
    }
    getVideoElement(ele,videoName, videoSrc, props){
        let str='';
        switch (videoName) {
            case "deluxe":
                str = `<div id="main-player-container" data-single-player="true" data-video-id="${videoSrc}" data-loop="${props.loop}" data-autoplay="${props.autoplay}" data-controls="${props.controls}" data-mute ="${props.mute}"></div>`
                break;
            case "youtube":
                self.loadYTAPI();
                let onlyVideoId = videoSrc.match(self.ytVideoRegex);
                if(onlyVideoId){
                    onlyVideoId = onlyVideoId[7];
                }
                str = `<iframe id="youtube_player" src="${videoSrc}${(videoSrc.indexOf("?") > -1 ? "&" : "?")}enablejsapi=1${props.mute == "true" ? '&mute=1' : ''}${props.loop == "true" ? '&loop=1' : ''}${props.controls == "false" ? '&controls=0' : ''}${props.autoplay == "true" ? '&autoplay=1' : ''}&rel=0&playlist=${onlyVideoId}" allowfullscreen data-mute="${props.mute}" data-controls="${props.controls}" data-autoplay="${props.autoplay}" data-loop="${props.loop}" allow="${props.autoplay == "true" ? 'autoplay' : ''}"></iframe>`
                break;
            case "scene7":
                str = `<video class="vr-iframe embed-responsive-item html5video" ${props.loop == "true" ? 'loop':''} ${props.autoplay == "true" ? 'autoplay':''} ${props.controls == "false" ? '' : 'controls'} ${props.mute == "true" ? 'muted' : ''}><source  data-src="" src="${videoSrc}"></video>`
                break;
            default:
                break;
        }
        ele.append(str);
    }
    isDifferentScenarioForMobile(ele, desktopVideoName) {
        const mobileVideoId = $(ele).data("xsVideoId");
        if($(ele).closest(".video-player-off").length){
            return {status:"video-off", statusCode: 0};
        } else if(mobileVideoId){
            const $parentEle = $(ele).parent();
            const mobileVideoName = mobileVideoId.indexOf("youtube") != -1 ? "youtube": (mobileVideoId.indexOf("scene7") != -1 ? "scene7" : "deluxe");
            if(desktopVideoName != mobileVideoName){
                self.getVideoElement($parentEle,mobileVideoName, mobileVideoId, $(ele)[0].dataset || {});
                if(mobileVideoName == "deluxe"){
                    if (!singleVideoPlayerInit) singleVideoPlayerInit = new singleVideoPlayer();
                    singleVideoPlayerInit.init( $parentEle.find('[data-single-player="true"]'));
                }
                $(ele).remove();
                mobileVideoId.indexOf("youtube") != -1 && $(ele)[0].dataset.autoplay == "true"  && self.YTPlayerCommands($parentEle.find("iframe"));
                return {status:"differentVideoPlayer: initiated", statusCode: 0};
            }
            return {status:"SameVideoPlayer: different Video Id", statusCode: 1};
        }
        return {status:"above featured not enabled for mobile", statusCode: -1};
    }
}

class videoGallery extends videoProps {
    constructor() {
        super();
    }
    bindingEventsConfig() {
        if(videoGalleryInit.evtBinding) return;
        videoGalleryInit.evtBinding = true;
        const events = {
            "click #player-gallery-thumbnail-datas li": "thumbnailAction",
            "click #play-list-datas li": "thumbnailAction",
            "click #video-gallery-player-component .slide-btn": "videoSlide",
            "click .reusability-play-tiles-gallery .show-more a": "reasuexpandDetail"
        };
        return events;
    }
    reasuexpandDetail(ele, evt) {
        ele.parentElement.parentElement.parentElement.classList.add('openList');
        var targetParent = ele.parentElement.parentElement.classList;
        var trackingVal;
        var expandText = $(ele).data('text-expand'),
            collapseText = $(ele).data('text-collapse'),
            maxLiCount = $('.reusability-gallery-tile ul li').length,
            componentVal = $(ele).attr('data-component-id'),
            $parentEle= $(ele).parents('.openList').find('.reusability-gallery-tile'),
            $focusElem,
            visibleCnt;
            if(!$parentEle.data('load')){
                $parentEle.attr('data-load',$parentEle.find('li:visible').length);
            }
            visibleCnt= $parentEle.data('load') || 4;
        if ($(targetParent).has('openList')) {
            if (!$(ele).hasClass('active')) {
                $focusElem =  $(ele).parents('.openList').find('.reusability-gallery-tile li:nth-child('+(visibleCnt+1)+')');
                $(ele).addClass('active');
                $(ele).text(expandText).attr('aria-expanded', 'true');
                $(ele).parents('.openList').find('.reusability-gallery-tile li:lt(' + maxLiCount + ')').slideDown(100); //.css('display','block');
                if($focusElem.find('a').length){
                    $focusElem.find('a').attr('tabindex', '-1').focus();
                } else{
                    $focusElem.attr('tabindex', '-1').focus();
                }
                $(ele).parents('.openList').find('.reusability-gallery-tile li:nth-child('+(visibleCnt+1)+') a').removeAttr('tabindex');
                $('.openList .reusability-gallery-tile li:nth-child('+visibleCnt+'n+'+visibleCnt+')').next().css('clear', 'both');
                $('.openList .view-all').show();
                trackingVal = componentVal + '|Show More';

            } else {
                $focusElem =  $(ele).parents('.openList').find('.reusability-gallery-tile li:nth-child(1)');
                $(ele).closest('.tile-action').removeClass('active');
                $(ele).text(collapseText).attr('aria-expanded', 'false');
                $(ele).removeClass('active');
                $(ele).parents('.openList').find('.reusability-gallery-tile li:gt('+(visibleCnt-1)+')').slideUp(100); //.css('display','none');
                if($focusElem.find('a').length){
                    $focusElem.find('a').attr('tabindex', '-1').focus();
                } else{
                    $focusElem.attr('tabindex', '-1').focus();
                }
                $(ele).parents('.openList').find('.reusability-gallery-tile li:nth-child('+(visibleCnt+1)+') a').removeAttr('tabindex');
                $('.openList .view-all').hide();
                trackingVal = componentVal + '|Show Less';
            }
            ele.parentElement.parentElement.classList.remove('openList');
            evt.stopImmediatePropagation();
        }
    }
    checkPlayerAvailability(ele) {
        const isPlayerExist = ele.closest('[data-player-available="true"]');
        if (!isPlayerExist.length) {
            const $hrefElem = $(ele).find("a:first");
            $hrefElem.attr("href", $hrefElem.data('detailLink'));
            return false;
        }
        return true;
    }
    videoSlide(ele, evt) {
        evt.preventDefault();
        const $gridList = $(ele).parents("#video-gallery-player-component").find("#player-gallery-thumbnail-datas li");
        const $activeItem = $gridList.filter(".active");
        let currentIndex = $activeItem.data('index');
        const actionName = $(ele).data("action");
        if (actionName == "prev") {
            if (currentIndex == 0) {
                currentIndex = $gridList.length;
            }
            currentIndex = currentIndex - 1;
        } else if (actionName == "next") {
            currentIndex = currentIndex + 1;
            if (currentIndex > $gridList.length - 1) {
                currentIndex = 0;
            }
        }
        $gridList.filter(`[data-index="${currentIndex}"]`)[0].click();

    }
    thumbnailAction(ele, evt) {
        const $ele = $(ele);
        if (!videoGalleryInit.checkPlayerAvailability($ele)) return;
        evt && evt.preventDefault();
        if ($ele.hasClass("active")) {
            return false;
        }
        self.addClassOnlyActive($ele);
        const $playerParnt = $ele.parents("#video-gallery-player-component");
        const $playerEle = $playerParnt.find("[id^=aem-video-player]");
        $playerEle.attr("data-video-id", $ele.data('videoId'));
        self.playerInitORUpdate($playerEle, (player) => {
            videoGalleryInit.playerEventBindings(player, $playerParnt);
        });
        self.updateTitleAndDescription($playerParnt, $playerEle, {
            title: $(ele).find(".tile-content").text(),
            description: $(ele).find(".tile-desc").text(),
        })
    }
    autoplayNextVideo(parEle) {
        $(parEle).find(".slide-btn.cd-next")[0].click();
    }
    playerEventBindings(player, parEle) {
        player.on("playing", event => {
            const videoIndex = player.html5Player ? player.el_.parentElement.dataset.videoIndex : 1;
            const props = playerProps.actionProps[videoIndex] || {};
            self.getTrackingValues('', '', `video Gallery section|${player.mediainfo ? player.mediainfo.name : (player.title || ($("#video-player-title").html()).split("|").join("-") )}|${player.isAutoPlay ? (props.forcePaused ? '' : 'auto-') : ''}play|video Player|Thumbnail`, undefined, player);
        });
        player.on("pause", event => {
            self.getTrackingValues('', '', `video Gallery section|${player.mediainfo ? player.mediainfo.name : (player.title || ($("#video-player-title").html()).split("|").join("-") )}|pause|video Player|Thumbnail`, undefined, player);
        });
        player.on("ended", (event) => {
            if (player.isAutoPlay) {
                videoGalleryInit.autoplayNextVideo(parEle);
            }
        });
    }
    resizeBind(){
        $(window).on("load resize",function(e){
            setTimeout(function(){
                if($('.youtube').length >=1){
                    if($(window).width() < 767){
                        $('.youtube .reusability-videos-gallery-playlist').css("height","auto");
                    }else{
                        $('.youtube .reusability-videos-gallery-playlist').height($('.youtube .featured-promo').height());
                    }
                }
            },800);
        });
    }
    render(elem) {
        const $gridList = $(elem).find("#videos-gallery");
        if ($(elem).find(".video-player").length && $gridList.length) {
            this.createIDsforPlayers($(elem).find(".video-player"));
            _.each($gridList, function(item) {
                $(item).find("li:eq(0)")[0].click();
            });
        }
        this.heightSync($gridList.find("li"));
        $('.cd-prev').attr('aria-label', 'Previous');
        $('.cd-next').attr('aria-label', 'Next');
        if (self.deviceName != "mobilePortrait" && self.deviceName != "mobile") {
            setTimeout(function() {
                if ($(elem).find('.video-rightscroll').length >= 1) {
                    $(elem).find('.video-rightscroll .videos-gallery-playlist').height($(elem).find(".video-player").height());
                }
            }, 800);
        }
    }
    init(elem) {
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.render(elem);
        this.resizeBind();
    }
}

class singleVideoPlayer extends videoProps {
    constructor() {
        super();
    }
    playerEventBindings(player) {
        player.on("playing", event => {
            const videoIndex = player.html5Player ? player.el_.parentElement.dataset.videoIndex : 1;
            const props = playerProps.actionProps[videoIndex] || {};
            self.getTrackingValues('', '', `Video Text Section|${player.mediainfo ? player.mediainfo.name : (player.title || '') }|${player.isAutoPlay ? (props.forcePaused ? '' : 'auto-') : ''}play|video Player|`, undefined, player);
        });
        player.on("pause", event => {
            self.getTrackingValues('', '', `Video Text Section|${player.mediainfo ? player.mediainfo.name : (player.title || '') }|pause|video Player|`, undefined, player);
        });
    }
    init(ele) {
        if (!$(ele).length) return;
        if ((self.deviceName == "mobile" || self.deviceName == "mobilePortrait" || self.deviceName == "tablet" || self.deviceName == "tabletPortrait")) {
            const $closestEle = $(ele).closest(".jumbotron-banner-component");
            const {statusCode,status} = self.isDifferentScenarioForMobile(ele,"deluxe");
            if(status!="video-off" && $closestEle.length) $closestEle.removeClass("jumbotron-banner-component").removeAttr("id");
            if (statusCode == 0) return;
        }
        this.createIDsforPlayers(ele);
        this.playerInitORUpdate(ele, (player) => {
            this.playerEventBindings(player);
        });
    }
}
class youtubeVideoPlayer extends videoProps {
    constructor() {
        super();
        this.ytPlayer;
    }
    init(ele) {
        let videoSrc = $(ele).attr("data-src");
        if (!$(ele).length || !videoSrc) return;
        const mobileVideoId = $(ele).data("xsVideoId");
        const $parentEle = $(ele).parent();
        const desktopVideoName = videoSrc.indexOf("youtube") != -1 ? "youtube": (videoSrc.indexOf("scene7") != -1 ? "scene7" : "deluxe");
        if ((self.deviceName == "mobile" || self.deviceName == "mobilePortrait" || self.deviceName == "tablet" || self.deviceName == "tabletPortrait")) {
            const $closestEle = $(ele).closest(".jumbotron-banner-component");
            const {status,statusCode} = self.isDifferentScenarioForMobile(ele,desktopVideoName);
            if(status!="video-off" && $closestEle.length) $closestEle.removeClass("jumbotron-banner-component").removeAttr("id");
            if (statusCode == 0) return;
            videoSrc = mobileVideoId || videoSrc;
        }
        if(videoSrc.indexOf("youtube") != -1 || videoSrc.indexOf("scene7") != -1){
            self.getVideoElement($parentEle,desktopVideoName, videoSrc, $(ele)[0].dataset || {});
            $(ele).remove();
        }
        videoSrc.indexOf("youtube") != -1 && $(ele)[0].dataset.autoplay == "true" && self.YTPlayerCommands($parentEle.find("iframe"));
    }
}
let player={},
    ytRunningId=0;
window.bindingYTEvents = () => {
    window.onYouTubeIframeAPIReady = (arg) => {
        if(arg) return "commonJS";
        if(typeof YT != "undefined" && autoplayQueueYTVideos.length){
            let runningId;
            autoplayQueueYTVideos.forEach(iframeEle=> {
                ytRunningId++;
                runningId = `youtube_player-${ytRunningId}`;
                iframeEle.attr('id', runningId)
                player[runningId] = new YT.Player(runningId, {
                    events: {'onReady': onPlayerReady}
                });
            });
            autoplayQueueYTVideos=[];
        }
    };
    window.onPlayerReady = (event) => {
        // check if video is playing
        if(event.target.getPlayerState() != 1 && $(event.target.getIframe()).data("autoplay") == true){
            event.target.playVideo();
        }
    }
}
class videoDetail extends videoProps {
    constructor() {
        super();
        
    }
    bindingEventsConfig() {
        const events = {
            "click .nav-categorylist li": "filterAction",
            "click .play-list li": "thumbnailAction",
            "click #play-list-datas li": "thumbnailAction",
            "click .video-player-container .slide-btn": "videoSlide",
            "change #enable-autoplay": "playlistAutoplay",
            "click .show-more-btn": "triggerScrollManually",
        };
        return events;
    }
    apiConfig(apiKey) {
        const path = "/bin/getVideoLandingGrid.json";
        const currentPath = $('#currentPath').val();
        const obj = {
            "videoCategoryDatas": {
                //"url": "//" + window.location.host + "/videos.json",
                // "url": `//${window.location.host}${path}?currentPath=${currentPath}`,
                "url": `//${window.location.host}${path}?currentPath=${currentPath}`,
                "type": "get",
                "params": ""
            }
        };
        return obj[apiKey];
    }
    templateConfig(configName) {
        let configObj;
        switch (configName) {
            case 'filterNames':
                configObj = {
                    "templateId": "#filter-items-template",
                    "targetId": "#filter-category-items"
                };
                break;
            case 'videoGallery':
                configObj = {
                    "templateId": "#player-thumbnail-template",
                    "targetId": "#player-thumbnail-datas"
                };
                break;
            case 'relatedVideoGallery':
                configObj = {
                    "templateId": "#player-thumbnail-template",
                    "targetId": "#play-list-datas"
                };
                break;
        }
        return configObj;
    }
    playlistAutoplay(ele) {
        const $parentEle = $(ele).closest(".autoplay-checkbox");
        $parentEle.removeClass("checked-true checked-false");
        $parentEle.addClass(`checked-${$(ele).is(':checked')}`);
    }
    pageReset() {
        this.page = 0;
    }
    isDataNotFound(obj) {
        if (obj.length == 0) {
            $(self.gridEl).addClass('no-result').removeClass('success data-loading next-data-loading');
            $(".show-more-btn").addClass("hidden");
            return false;
        }
        return true;
    }
    addNameToClearFilter(filterName) {
        const $wrapper = $(".clear-filter-wrapper"),
            $elem = $wrapper.find("#filtered-name");
        if (!$elem.length) {
            console.log("clear filter element not found..");
            return;
        }
        $elem.html(filterName);
        $wrapper.removeClass("hidden");
    }
    clearFilter(ele) {
        if (self.isFirstLoad) return;
        $(self.gridEl).html('').addClass("data-loading").removeClass("success");
        $(".nav-categorylist li").removeClass("active");
        self.filteredDatas = "";
        self.pageReset();
        self.APICallback();
    }
    filterAction(ele, evt) {
        const filterName = $(ele).data("filter");
        const activeClass = "active";
        $('.category-drpdown-display').toggleClass('open');
        const trackingId = $(ele).attr("data-analytics-id");
        const $parentEle = $(".nav-carousel-slides");
        if (_.isEmpty(self.gridAPIDatas)) {
            console.log("Grid API datas not found..");
            return;
        }
        //trigger next if filter is partially visible
        const activeThumbnail = $(`#player-thumbnail-datas li.active`);
        if (activeThumbnail.length) {
            self.deepLinkVideo = activeThumbnail.data('videoId');
        }
        const owlParent = $(ele).closest(".owl-item.active");
        if (owlParent.length) {
            if ($parentEle.find(".owl-item.active").index(owlParent) == $parentEle.find(".owl-item.active").length - 1) {
                $parentEle.find(".owl-next")[0].click();
            }
        }
        // seo
        const seoName = $(ele).data("seoName");
        const seoDesc = $(ele).data("seoDesc");
        const seoKeywords = $(ele).data("seoKeywords");
        $('title').text(seoName);
        $('meta[name=description]').attr('content', seoDesc);
        $('meta[name=keywords]').attr('content', seoKeywords);

        // url change
        const categoryName = $(ele).find('a').attr('title');
        const url = location.href;
        let findCategory = url.split('/');
        const urlVal = findCategory[findCategory.length - 2];
        if (urlVal == 'category' || urlVal == 'video') {
            findCategory = findCategory.slice(0, findCategory.length - 2);

            const orignUrl = findCategory.join('/')+location.search;
            history.pushState(null, null, orignUrl);
        }

        const seoUrl = `${location.href.split("?")[0]}/category/${categoryName}${location.search}`;
        history.pushState(null, null, seoUrl);

        //dropdown append
        $('.category-drpdown-display').empty().append(`${$(ele).find('a').text()}<span></span>`);
        //seo link url change
        const linkData = [];
        $('link').each(function() {
            if (this.hreflang != '')
                linkData.push(this.hreflang);
        });

        _.each(linkData, item => {
            const seoLink = $(`link[hreflang=${item}]`).attr('href');
            let findCategory = seoLink.split('/');
            const urlVal = findCategory[findCategory.length - 2];
            let orignUrl;
            if (urlVal == 'category' || urlVal == 'video') {
                findCategory = findCategory.slice(0, findCategory.length - 2);
                orignUrl = findCategory.join('/');
            } else {
                orignUrl = $(`link[hreflang=${item}]`).attr('href');
            }
            $(`link[hreflang=${item}]`).attr('href', `${orignUrl}/category/${categoryName}`);
        });

        $(".nav-categorylist li a").removeClass(activeClass);
        $(".nav-categorylist li").removeClass(activeClass);
        $('#videos-gallery h2').text($(ele).find('a').text());
        $(ele).addClass(activeClass);
        $(ele).find('a').addClass(activeClass);
        if (self.deviceName == "mobile" || self.deviceName == "mobilePortrait") {
            $('.nav-categorylist').hide();
        }
        if (trackingId) {
            self.getTrackingValues(ele, evt, trackingId, true);
        }
        if (filterName && filterName.indexOf("all") > -1 || filterName.indexOf("All") > -1) {
            self.clearFilter();
            return;
        }

        self.filteredDatas = self.filterObjects(self.gridAPIDatas, filterName);
        $(self.gridEl).html('').addClass("data-loading").removeClass("success");
        if (!self.isDataNotFound(self.filteredDatas)) {
            console.log(`No Datas found when passing this filter name :${filterName}`);
            return;
        }
        self.ajaxCollection(self.filteredDatas, self.filterClicked);
        self.getCollDatas(self.filteredDatas, self.filterClicked);
    }
    autoplayCheck() {
        const playButton = $('.video-component');
        _.each(playButton, item => {
            const activeToggle = $(item).find('.featured-promo').attr('data-autoplay');
            if (activeToggle == "true") {
                $(item).find('.play-list li.active').addClass("play-active");
            } else {
                $(item).find('.play-list li.active').addClass("play-disable");
            }

        });

    }
    playNowButtonPolly(el) {
        el.parents('.video-component').find('.play-list li.active').removeClass('play-disable');
    }
    filterObjects(obj, uniqueId) {
        const arr = [];
        let i;
        const filterKeyName = $(self.gridEl).data("filterKeyName") || "videoTags";
        for (i = 0; i < obj.length; i++) {
            if (obj[i][filterKeyName] && obj[i][filterKeyName].indexOf(uniqueId) > -1) {
                arr.push(obj[i]);
            }
        }
        return arr;
    }
    thumbnailAction(ele, evt, deeplinkObj) {
        evt && evt.preventDefault();
        if ($(ele).parent().hasClass('play-list') == true) {
            playerProps.eventClicked = 'Thumbnail';
        } else if ($(ele).parent().attr('id') == 'play-list-datas') {
            playerProps.eventClicked = 'Playlist';
        } else {
            playerProps.eventClicked = 'AutoPlay';
        }
        if (ele && ($(ele).hasClass("active") && $("#play-list-datas li").length)) {
            return false;
        }
        const filteredObj = deeplinkObj || {};
        const index = filteredObj.videoId ? filteredObj.index : $(ele).data("index");
        const obj = self.filterRightSideData(index);
        let trackingId = $(ele).attr("data-analytics-id");
        const pageTitle = filteredObj.seotitle || $(ele).data("seoTitle");
        self.playlistData = obj;
        self.resetPlaylistLoad();
        self.loadRightSideSection(obj);
        self.thumbnailActionFnCall(ele, filteredObj);
        setTimeout(() => {
            if (!trackingId) {
                trackingId = `Navigating Arrows|Video Player Section-${self.alwaysEnglish}|${self.slideArrow}|Video Player`
            }
            self.getTrackingValues(ele, evt, trackingId);
        }, 2600);
        document.title = pageTitle || document.title;
    }
    thumbnailActionFnCall(ele, deeplinkObj) {
        const filteredObj = deeplinkObj || {};
        const videoId = filteredObj.videoId || $(ele).data("videoId");
        const seoName = filteredObj.videoName || $(ele).data("seoName");
        const seoDesc = filteredObj.metaDesc || $(ele).data("seoDesc");
        const seoKeywords = filteredObj.metaKeywords || $(ele).data("seoKeywords");
        const videoTitle = filteredObj.videoTitle || $(ele).find(".tile-content").text();
        const videoDesc = filteredObj.videoDesc || $(ele).find(".tile-desc").text();
        const videoName = filteredObj.videoName || $(ele).find(".tile-video-name").val();
        // seo

        $('title').text(seoName);
        $('meta[name=description]').attr('content', seoDesc);
        $('meta[name=keywords]').attr('content', seoKeywords);


        // url change
        if (!self.isFirstLoad) {
            const url = location.href;
            let findCategory = url.split('/');
            const urlVal = findCategory[findCategory.length - 2];
            if (urlVal == 'category' || urlVal == 'video') {
                findCategory = findCategory.slice(0, findCategory.length - 2);
                const orignUrl = findCategory.join('/')+location.search;
                history.pushState(null, null, orignUrl);
            }
            const seoUrl = `${location.href.split("?")[0]}/video/${videoName}${location.search}`;
            history.pushState(null, null, seoUrl);
        } else {
            self.isFirstLoad = false;
        }

        //seo link url change

        const seoLinkData = [];
        $('link').each(function() {
            if (this.hreflang != '')
            seoLinkData.push(this.hreflang);
        });

        _.each(seoLinkData, item => {
            const checkSeoLink = $(`link[hreflang=${item}]`).attr('href');
            let findSEOCategory = checkSeoLink.split('/');
            const urlVal = findSEOCategory[findSEOCategory.length - 2];
            let orignUrl;
            if (urlVal == 'category' || urlVal == 'video') {
                findSEOCategory = findSEOCategory.slice(0, findSEOCategory.length - 2);
                orignUrl = findSEOCategory.join('/');
            } else {
                orignUrl = $(`link[hreflang=${item}]`).attr('href');
            }
            $(`link[hreflang=${item}]`).attr('href', `${orignUrl}/video/${seoName}`);
        });

        $("#player-thumbnail-datas li, #play-list-datas li").removeClass("active");
        if (ele) {
            $(`#player-thumbnail-datas li[data-video-id=${videoId}], #play-list-datas li[data-video-id=${videoId}]`).addClass("active");
        }
        self.thumbnailActiveClass(videoId);
        self.updateVideoPlayer(videoId, videoTitle, videoDesc, ele);
    }
    thumbnailActiveClass(videoId) {
        const id = videoId || this.deepLinkVideo;
        if (_.isEmpty(id)) return;
        const $activeElem = $(`#player-thumbnail-datas li[data-video-id=${id}]`);
        if ($activeElem.length) {
            $(`#player-thumbnail-datas li[data-video-id=${id}], #play-list-datas li[data-video-id=${id}]`).addClass("active");
            this.deepLinkVideo = '';
        } else {
            this.deepLinkVideo = id;
        }

        this.autoplayCheck();
    }
    thumbnailClassonFilterClick() {
        const urlVal = self.deeplinking();
        const seoName = urlVal[1] != "" && urlVal[1].replace(/%20/g, " ");
        const isVideoPlayerId = ($(self.gridEl).find("li.active").data("videoId") || "").toString().replace("ref:", "");
        if (!isVideoPlayerId || !seoName) return;
        $("#player-thumbnail-datas li, #play-list-datas li").removeClass("active");
        if (isVideoPlayerId != '') {
            $(`#player-thumbnail-datas li[data-video-id=${isVideoPlayerId}], #play-list-datas li[data-video-id=${isVideoPlayerId}]`).addClass("active");
        } else if (seoName != '') {
            $(`#player-thumbnail-datas li[data-seo-name=${seoName}], #play-list-datas li[data-seo-name=${seoName}]`).addClass("active")
        }
    }
    updateVideoPlayer(videoId, videoTitle, videoDesc, ele) {
        let $activeGrid;
        const $elem = $(".featured-promo.video-player");
        if ($elem.attr('data-video-id') == videoId) {
            return;
        }
        $elem.attr("data-video-id", videoId);
        console.log("Initializing Video: "+videoId);
        // self.addClassOnlyActive(ele ? $(ele) : $(self.gridEl).find("li:first"));
        self.playerInitORUpdate($elem, (player) => {
            self.playerEventBindings(player);
            $activeGrid = $(self.gridEl).find(`li[data-video-id="${videoId}"]`);
            $activeGrid.length && self.addClassOnlyActive($activeGrid);
        });
        self.updateTitleAndDescription($(".videoDetail"), $elem, {
            title: videoTitle,
            description: videoDesc,
        });
        self.verticalListHeight();
    }
    playerEventBindings(player) {
        player.on("playing", event => {
            const videoIndex = player.html5Player ? player.el_.parentElement.dataset.videoIndex : 1;
            const props = playerProps.actionProps[videoIndex] || {};
            self.getTrackingValues('', '', `video player section|${player.mediainfo ? player.mediainfo.name : (player.title || ($("#video-player-title").html()).split("|").join("-") )}|${player.isAutoPlay ? (props.forcePaused ? '' : 'auto-') : ''}play|video Player|${playerProps.eventClicked == undefined ? 'Thumbnail' : playerProps.eventClicked }`, undefined, player);
        });
        player.on("pause", event => {
            self.getTrackingValues('', '', `video player section|${player.mediainfo ? player.mediainfo.name : (player.title || ($("#video-player-title").html()).split("|").join("-") )}|pause|video Player|${playerProps.eventClicked == undefined ? 'Thumbnail' : playerProps.eventClicked }`, undefined, player);
        });
        player.on("ended", event => {
            if ($("#enable-autoplay").is(":checked")) {
                self.enableAutoplay();
                typeof self.verticalListHeight == "function" && self.verticalListHeight();
            }
        });
    }
    videoPlayonLoad(callBack) {
        const playerContainer = document.getElementById("main-player-container"),
            isAutoplay = $(playerContainer).data('autoplay');
        if (isAutoplay && !self.isVideoPlaying(self.player) && self.isScrolledIntoView(playerContainer) > 0) {
            callBack(true);
        } else {
            callBack(false);
        }
    }
    enableAutoplay() {
        const obj = $(".nav-categorylist li.active:not([data-filter='All'])").length ? self.filteredDatas : self.gridAPIDatas;
        let objIndex;

        let //targetIndex,
            targetObj;

        let indx;
        obj.filter(item => {
            if (item.videoId == ($(self.gridEl).find("li.active").data("videoId") || "").toString().replace("ref:", "")) {
                indx = item.index;
            }
        });
        objIndex = _.findIndex(obj, {
            index: indx
        });
        targetObj = obj.length != (objIndex + 1) ? obj[objIndex + 1] : obj[0];
        playerProps.playerEventTracking = true;
        self.thumbnailAction(undefined, undefined, targetObj);
    }
    videoSlide(ele, evt) {
        evt.preventDefault();
        const actionName = $(ele).data("action");
        const obj = $(".nav-categorylist li.active:not([data-filter='All'])").length ? self.filteredDatas : self.gridAPIDatas;
        let objIndex;
        let targetObj;

        let //targetIndex,
            indx;

        if (!actionName) return;
        obj.filter(item => {
            if (item.videoId == ($(".featured-promo.video-player").attr("data-video-id") || $(self.gridEl).find("li.active").data("videoId")).toString().replace("ref:", "")) {
                indx = item.index;
            }
        })
        objIndex = _.findIndex(obj, {
            index: indx
        });
        if (actionName == "prev") {
            if (objIndex == 0) {
                objIndex = obj.length;
            }
            targetObj = obj.length != (objIndex - 1) ? obj[objIndex - 1] : obj[0];
            self.slideArrow = "Left";
            self.alwaysEnglish = targetObj.alwaysEnglish;
            // if (!targetObj && objIndex == 0) {
            //     targetObj = obj[obj.length - 1];
            // }
        } else if (actionName == "next") {
            self.slideArrow = "Right";
            if (objIndex >= obj.length - 1) {
                objIndex = -1;
            }
            targetObj = obj.length != (objIndex + 1) ? obj[objIndex + 1] : obj[0];
            self.alwaysEnglish = targetObj.alwaysEnglish;
        }
        self.thumbnailAction(undefined, undefined, targetObj);
    }
    triggerScrollManually() {
        self.loadMore(document.querySelector(self.gridEl), true);
    }
    showmoreBtn() {
        if ($('.play-tiles-gallery').hasClass('lazy-load')) {
            $('.show-more-btn').addClass('hide');
        } else {
            $('.show-more-btn').removeClass('hide');
        }
    }
    filterRightSideData(indx) {
        let arr = [];
        const arr1 = [];
        const arr2 = [];
        const obj = $(".nav-categorylist li.active:not([data-filter='All'])").length ? self.filteredDatas : self.gridAPIDatas;

        _.each(obj, item => {
            if (item.index > indx) {
                arr1.push(item);
            } else {
                arr2.push(item);
            }
        });
        arr = arr.concat(arr1, _.sortBy(arr2, 'index'));
        // arr.push(obj[i]);

        return arr;
    }
    loadRightSideSection(obj) {
        const isPlaylist = true,
            config = self.templateConfig("relatedVideoGallery");

        const getPlaylistdata = self.playListLazyLoad(obj || self.playlistData);
        self.templateInit(config, getPlaylistdata, (elem, collection) => {
            if (!collection) {
                console.log("right side template not rendered..");
                return;
            }
            if (self.playlistPage == 1) {
                $(elem).html(collection);
                if (obj.length == 1) {
                    $(".slide-btn").hide();
                } else {
                    $(".slide-btn").show();
                }
                $(".video-play-list-area").scrollTop(0);
            } else {
                $(elem).append(collection);
            }
            self.thumbnailClassonFilterClick();
            setTimeout(() => {
                self.playlistLoading = false;
            }, 500);
            $(".title-block h2").html(self.rightSideFilterTitle)
        }, isPlaylist);
    }
    playlistCount() {
        return this.playlistData.length;
    }
    playListLazyLoad(obj) {
        let start;
        let end;
        const ret = [];
        if (self.playlistLoaded) return;
        if (this.playlistPage == 0) {
            start = 0;
            end = this.playlistlimit - 1;
        } else {
            start = (self.playlistlimit) + ((self.playlistPage - 1) * self.nextPlaylistLoad);
            end = start + self.nextPlaylistLoad - 1;
        }
        if (end >= self.playlistCount() - 1) {
            end = self.playlistCount() - 1;
        }
        if (start >= self.count()) { // start has extended past the length... find not loaded
            self.playlistLoaded = true;
        }
        for (let i = start, m; i < end + 1; i++) {
            m = obj[i];
            if (!_.isUndefined(m)) {
                ret.push(m);
            }
        }
        this.playlistPage++;
        return ret;
    }
    resetPlaylistLoad() {
        this.playlistPage = 0;
        this.playlistlimit = this.initial || 5;
        this.nextPlaylistLoad = this.initial || 5;
        this.playlistLoaded = false;
    }
    count() {
        return this.models.length;
    }
    getCollDatas(obj, bool) {
        let start;
        let end;
        const ret = [];
        const $elem = $(self.gridEl);
        const $showMoreBtn = $(".show-more-btn");
        if (typeof obj == "undefined") {
            console.log("Fn: getcollDatas, object is undefined..");
            return;
        }
        if (self.initial_count === 'all') {
            _.each(obj, m => {
                ret.push(m);
            });
        } else {
            if (self.page === 0) {
                start = 0;
                end = self.initial_count - 1;
            } else {
                if (bool) {
                    self.filPage = start = (obj.length < self.initial_count) ? self.filPage + obj.length : self.initial_count + 1;
                } else {
                    start = (self.initial_count) + ((self.page - 1) * self.paged_count);
                }
                end = start + self.paged_count - 1;
            }
            if (end >= self.count() - 1) {
                end = self.count() - 1;
            }
            if (start >= self.count()) { // start has extended past the length... find not loaded
                self.dataLoaded = true;
            } else {
                if (bool && self.page == 0) {
                    self.filPage = start = 0;
                }
                for (let i = start, m; i < end + 1; i++) {
                    m = obj[i];
                    if (!_.isUndefined(m)) {
                        ret.push(m);
                    }
                }
                self.dataLoaded = false;
            }
        }
        if (start + ret.length >= self.count()) {
            $elem.addClass("success");
            $showMoreBtn.addClass("hidden");
            console.log("Data Loaded Sucessfully..")
        }
        if (self.dataLoaded) {
            return;
        }
        self.page++;
        if ($elem.find("li").length) {
            $elem.addClass("next-data-loading");
        }
        self.templateBind(ret, self.page == 1 ? self.filterClicked : undefined, undefined, start + ret.length >= self.count());
    }
    heightSync(elem) {
        if (!$("#player-thumbnail-datas li").length) return;
        if (!elem) {
            const element = $("#player-thumbnail-datas");
            $(element).find(".tile-content").css('height', 'auto');
            $(element).find(".gallery-image").css({
                'height': 'auto',
                'line-height': 1
            });
        }
        let max = -1;
        let imgMax = max;
        const $heightElem = $(elem).find(".tile-content");
        let $imgElem;
        let imgHght;
        let height;

        $(elem).find('img').imagesLoaded(() => { // image ready
            _.each($heightElem, el => {
                $imgElem = $(el).closest("li").find(".gallery-image");
                imgHght = $imgElem.length ? $imgElem.innerHeight() : 0;
                height = $(el).innerHeight();
                max = height > max ? height : max;
                imgMax = imgHght > imgMax ? imgHght : imgMax;
            });
            $heightElem.css('height', `${max}px`);
            imgMax && $(elem).find(".gallery-image").css({
                'height': `${imgMax}px`,
                'line-height': `${imgMax}px`
            });
        });
        return;
    }
    templateInit(config, obj, cb, isPlaylist) {
        const elem = $(config.targetId);
        const templateId = $(config.templateId).length ? _.template($(config.templateId).html().trim()) : 0;
        let templateCollection;

        if (!templateId) {
            console.log(`${config.templateId} id not exists.`);
            return;
        }
        templateCollection = templateId({
            'items': obj,
            'isPlaylist': isPlaylist || false
        });
        typeof cb == "function" && cb(elem, templateCollection);
    }
    templateBind(obj, filterClick, dataEmptyMsg, dataLoaded) {
        const config = self.templateConfig("videoGallery");
        let $focusElem;
        self.templateInit(config, obj, (elem, templateCollection) => {
            setTimeout(() => {
                (filterClick ? elem.html(dataEmptyMsg || templateCollection) : elem.append(templateCollection));
                $focusElem = $(elem).find(`.list[data-index=${$(templateCollection).first("li").data("index")}] a:first`)
                self.page > 1 && $focusElem.length && $focusElem.focus();
                $(elem).imagesLoaded(() => {
                    if (this.deviceName != "mobile" && this.deviceName != "mobilePortrait") {
                        self.heightSync(elem);
                    }
                    self.templateLoadedCallback(elem, filterClick, dataLoaded);
                    self.thumbnailActiveClass();
                    self.thumbnailClassonFilterClick();
                });
            }, 500);

        });
    }
    templateLoadedCallback(elem, filterClick, dataLoaded) {
        $(elem).removeClass("data-loading no-result next-data-loading");
        self.loading = false;
        // if (!dataLoaded) $(".show-more").removeClass("hidden");
        const urlVal = self.deeplinking();
        if (!self.apiLoaded) {
            self.apiLoaded = true;
            if (urlVal[0] == 'category' || (urlVal[0] == 'video' && urlVal[1] == "") || urlVal[0] == "home" || (urlVal[0] != "home" && urlVal[1] == "videos" || urlVal[1] == "videos.html")) {
                $(".play-list li:first")[0].click();
            }
        }

    }
    deeplinking() {
        const url = location.href.split('?')[0],
            findCategory = url.split('/'),
            lastSlash = findCategory[findCategory.length - 2],
            lastBeforeSlash = findCategory[findCategory.length - 1];
        return [lastSlash, lastBeforeSlash];
    }
    ajaxCollection(obj, filterClick) {
        const el = this.gridEl;
        this.initial = $(el).data('initialLoad');
        this.initial_count = this.initial_count || this.initial || 0;
        this.paged_count = 0;
        this.curt_count_obj = {};
        this.page = !filterClick ? this.page || 0 : 0;
        this.paged_count = $(el).data('nextLoad') || this.initial;
        this.models = obj;
        this.filPage = this.filPage || 0;
        this.remItem = this.remItem || 0;
    }
    loadMore(curVal, isManualTrigger) {
        const obj = $(".nav-categorylist li.active:not([data-filter='All'])").length ? self.filteredDatas : self.gridAPIDatas || '';
        if (!self.loading && obj.length && (window.innerHeight > curVal.getBoundingClientRect().bottom) || isManualTrigger) {
            self.loading = true;
            self.getCollDatas(obj);
        }
    }
    props(obj) {
        const object = _.map(obj, (element, indx) => {
            return _.extend({}, element, {
                index: indx
            });
        });
        return object;
    }
    waitForObjectLoad(callBack) {
        window.setTimeout(function() {
            if (typeof PLAYAEM == "object") {
                callBack(true);
            } else {
                self.pluginCheckCnt++;
                if (self.pluginCheckCnt > 20) {
                    console.log("%c PLAYAEM Initialize JS not loaded/failed.", "color:red");
                    callBack(false);
                    return;
                }
                self.waitForObjectLoad(callBack);
            }
        }, 500);
    }
    renderFilterNames(res) {
        if (res.length != 0) {
            let matchedCategoryEle;
            if ($('.category-drpdown-display').text() == '') {
                $('.category-drpdown-display').text(res[0].categoryTitle);
                $('.video-category-filter #filter-category-items li:first-child').addClass('active');
            }
            const config = self.templateConfig("filterNames");
            let $sliderEle;
            self.templateInit(config, res, (elem, collection) => {
                if (!collection) {
                    console.log("Filter Names template not rendered..");
                    return;
                }
                $sliderEle = $(elem).parents(".cda-banner");
                (this.deviceName != "mobile" && this.deviceName != "mobilePortrait") && $sliderEle.removeClass("enable-carousel");
                $(elem).html(collection);
                // call slider..
                self.waitForObjectLoad((res) => {
                    if (res && !_.isEmpty(PLAYAEM.charfilter) && (this.deviceName != "mobile" && this.deviceName != "mobilePortrait")) {
                        PLAYAEM.charfilter.applyNonGridCarousel($sliderEle.find(".nav-carousel-slides").addClass("owl-carousel"), $sliderEle[0].dataset);
                        if ($(".nav-carousel-slides").data('owl.carousel')) {
                            setTimeout(() => {
                                $(".nav-carousel-slides").data('owl.carousel').refresh();
                            }, 500);
                        }

                    }
                });
            });

            /* url trigger */
            const urlVal = self.deeplinking();
            if (urlVal[0] == 'category') {
                matchedCategoryEle = $(`.nav-carousel-slides li a[title="${urlVal[1] && urlVal[1].replace(/%20/g, " ")}"]`);
                if(matchedCategoryEle.length){
                    $(`.nav-carousel-slides li a[title="${urlVal[1] && urlVal[1].replace(/%20/g, " ")}"]`)[0].click();
                } else {
                    console.log(`Data is not available for under this category name : ${urlVal[1] && urlVal[1].replace(/%20/g, " ")}`);
                }
            } else {
                $(".nav-categorylist .owl-item:first-child li, .nav-categorylist:not(.owl-carousel)>li:first-child ").addClass('active');
                $(".nav-categorylist .owl-item:first-child li a, .nav-categorylist:not(.owl-carousel)>li:first-child a").addClass('active');
                $('#videos-gallery h2').html(self.filterAPIDatas.length ? res[0].categoryTitle : self.filterNoCategoryTile);
            }
        } else {
            $('.video-category-filter').css('display', 'none');
            $('#videos-gallery h2').css('pdding-top', '20px');
            if (self.filterNoCategoryTile) {
                $('#videos-gallery h2').html(self.filterNoCategoryTile);
            }

        }
    }
    verticalListHeight() {
        // $(window).on("load",function(e){
        const vPh = $('.video-player').outerHeight();
        const vDh = $('.video-player-label').outerHeight();

        const vOh = $('.video-play-option').outerHeight();
        const vTh = $('.video-vertical-playlist .title-block').outerHeight();
        const vLcp = parseInt($('.video-vertical-playlist-container').css('padding-top'));

        const videoListHeight = (vPh + vDh) - (vOh + vTh + vLcp);

        $('.video-play-list-area').css({
            'height': `${videoListHeight}px`
        });
        // });
    }
    loadFirstVideo(obj) {
        const firstVideo = obj[0] || obj;
        if (!firstVideo) {
            console.log("First video fn fails..");
            return;
        }
        const videoId = firstVideo.videoId;
        const videoTitle = firstVideo.videoTitle;
        const videoDesc = firstVideo.videoDesc;
        if (obj.length > 1 || (self.gridAPIDatas[0].videoName == firstVideo.videoName)) {
            self.updateVideoPlayer(videoId, videoTitle, videoDesc);
        }
        self.verticalListHeight();
    }
    storageExpiryDate() {
        return Date.now() + (24 * 60 * 60) * 1000; //1day
    }
    APICallback(res) {
        const urlVal = self.deeplinking();
        let i;
        let item;
        self.isFirstLoad = res ? true : false;
        self.filterAPIDatas = self.filterAPIDatas || res.categories || '';
        self.gridAPIDatas = self.props(self.gridAPIDatas || res.videos);
        self.initial = parseInt(self.initial || res.lazyLoadLimit || 5);
        self.videoAutoPlay = self.videoAutoPlay || res.autoPlay || '';
        self.rightSideFilterTitle = self.rightSideFilterTitle || (res && res.relVideosTitle) || '';
        self.filterNoCategoryTile = self.filterNoCategoryTile || (res && res.sectionAltTitle) || '';
        if (res) self.renderFilterNames(self.filterAPIDatas);

        if (window.innerWidth >= 992) {
            if (self.videoAutoPlay == "true" && res) {
                $('#enable-autoplay')[0].click();
                $('#enable-autoplay').closest(".autoplay-checkbox").addClass("checked-true");
            }
        }
        if (urlVal[0] == 'category' && urlVal[1] != "" && self.filteredDatas) {
            self.ajaxCollection(self.filteredDatas);
            self.getCollDatas(self.filteredDatas);
        } else {
            self.ajaxCollection(self.gridAPIDatas);
            self.getCollDatas(self.gridAPIDatas);
        }
        if (self.isFirstLoad && urlVal[0].toLowerCase() == "video") {
            for (i = 0; i < self.gridAPIDatas.length; i++) {
                item = self.gridAPIDatas[i];
                if (item.videoName.trim() == urlVal[1].replace(/%20/g, " ")) {
                    self.loadFirstVideo(item);
                    self.thumbnailAction(undefined, undefined, item);
                    let $activeGrid = $(self.gridEl).find(`li[data-video-id="${item.videoId}"]`);
                    self.addClassOnlyActive($activeGrid.length ? $activeGrid : $(self.gridEl).find("li:first"));
                    $("#filter-category-items li:first a").addClass("active");
                    break;
                }
            }
        }
    }
    APIFails() {
        const $gridEle = $(this.gridEl),
            failsMsg = $gridEle.data("fails");
        if (!failsMsg) {
            console.log("API data fails message not found..");
            return;
        }
        $gridEle.removeClass("data-loading").addClass("data-fails");
    }
    APICall() {
        const urlVal = self.deeplinking();
        const storageName = "videoAPIDatas";
        const config = self.apiConfig('videoCategoryDatas');
        const localeName = $("#siteCountry").val() || '';
        const sessionStorage = getStorage(storageName) != null ? this.compareStorageDate(getStorage(storageName), storageName) : 0;
        let mappedres = "";
        if (sessionStorage && sessionStorage != false && getStorage(storageName).url == config.url) {
            if (urlVal[0].toLowerCase() != "video" && urlVal[0] != "category") self.loadFirstVideo(sessionStorage.videos);
            self.APICallback(sessionStorage);
            return;
        }
        request(config)
            .then(res => {
                if (!res) {
                    console.log("Video datas API call fails..")
                    self.APIFails();
                    return;
                }
                mappedres = res;
                if (urlVal[0].toLowerCase() != "video" && urlVal[0] != "category") self.loadFirstVideo(mappedres.videos);
                self.APICallback(mappedres);
                setStorage(storageName, {
                    'obj': mappedres,
                    'timestamp': self.storageExpiryDate(),
                    'locale': localeName,
                    'url': config.url
                });
            }).catch(error => {
                console.log(`API Call failed. Please try again..`);
            });
    }
    analyticsTrack() {
        const $autoPlayEle = $('#enable-autoplay');
        const autoplayVal = $autoPlayEle.attr('data-autoplay-id');
        let trackingVal;
        $autoPlayEle.on('change', (ele, evt) => {
            setTimeout(function() {
                if ($(this).is(":checked")) {
                    trackingVal = `${autoplayVal}|ON|autoPlay`;
                } else {
                    trackingVal = `${autoplayVal}|OFF|autoPlay`;
                }
                self.getTrackingValues(ele, evt, trackingVal);
            }, 2500);
        })
    }
    resizeBind() {
        window.onresize = event => {
            this.deviceName = getDeviceName();
            self.deviceName = this.deviceName;
            if (this.deviceName != "mobile" && this.deviceName != "mobilePortrait") {
                setTimeout(() => {
                    self.heightSync();
                }, 200);
            }
            self.verticalListHeight();
        };
    }
    scrollLoadMore() {
        let isLazyLoad = $('.play-tiles-gallery').hasClass('lazy-load');
        const parentContainer = document.querySelector(self.gridEl);
        // lazy load event triggers in page ready
        $(window).scroll(() => {
            if (isLazyLoad) {
                if (parentContainer) {
                    self.loadMore(parentContainer);
                }
            } else {
                return false;
            }
        });
        $('#video-play-list .video-play-list-area').scroll(function(el, ev) {
            const $this = $(this);
            // var curVal = document.querySelector("#video-play-list .video-play-list-area");
            if (!self.playlistLoading && !self.playlistLoaded && $this.height() / 2 <= $this.scrollTop()) {
                self.loadRightSideSection();
                self.playlistLoading = true;
            }
        });
    }
    init() {
        evtBinding.bindLooping(this.bindingEventsConfig(), this);
        this.filterClicked = true;
        this.dataLoaded = false;
        this.loading = false
        this.apiLoaded = false;
        this.playlistLoading = false;
        this.alwaysEnglish = "";
        this.pluginCheckCnt = 0;
        this.gridEl = "#player-thumbnail-datas";
        this.createIDsforPlayers("#main-player-container");
        this.APICall();
        this.analyticsTrack();
        this.resizeBind();
        this.showmoreBtn();
        $('.cd-prev').attr('aria-label', 'Previous');
        $('.cd-next').attr('aria-label', 'Next');
        this.scrollLoadMore();
    }
}

let ths,cnt=0;
class videoPlayInModal extends videoProps {
    constructor(){
        super();
        ths = this;
        this.ele = "#video-player-component";
        if(!$(this.ele).length) return;
        this.bindingEventsConfig();
    }
    bindingEventsConfig(){
        evtBinding.bindLooping({
            [`click ${this.ele} .video-poster-image img`]: "playVideo"
        },this);
        $(document).on('hide.bs.modal','#VideoPlayInModal', function () {
            const $player = $("#VideoPlayInModal:first").find('.aem-video-player');
            if (!$player.is(':empty')) { $player.empty();}
        });
    }
    playVideo(elem) {
        let ele = elem.closest(".video-poster-image"),
            dataset = ele.dataset;
        if ((self.deviceName == "mobile" || self.deviceName == "mobilePortrait") && dataset.xsVideoId) {
            dataset.videoId = dataset.xsVideoId;
            dataset.videoType = (dataset.videoId.indexOf("youtube") != -1 || dataset.videoId.indexOf("scene7") != -1) ? "youtube" : "deluxe";
        }
		if(dataset.action == "inline"){
			ths.playInline(ele, dataset);
		} else if(dataset.action == "modal"){
            !ths.bindEventModal && ths.bindingModal();
			ths.playModal(dataset);
		}
	}
	playInline(elem, dataset){
		const {videoType, videoId, mute, loop, controls, autoplay} = dataset,
			videoElem = elem.previousElementSibling == undefined ? elem.nextElementSibling : elem.previousElementSibling;
		if(videoType == "deluxe"){
			$(videoElem).find("video")[0].play();
		} else if (videoElem.tagName == "IFRAME"){
            self.loadYTAPI();
            let onlyVideoId = videoId.match(self.ytVideoRegex);
            if(onlyVideoId){
                onlyVideoId = onlyVideoId[7];
            }
            let updatedId = `${videoId}${(videoId.indexOf("?") > -1 ? "&" : "?")}enablejsapi=1${mute == "true" ? '&mute=1' : ''}${loop == "true" ? '&loop=1' : ''}${controls == "false" ? '&controls=0' : ''}&autoplay=1&rel=0&playlist=${onlyVideoId}`;
			videoElem.src=  updatedId;
            self.YTPlayerCommands(videoElem);
            $(videoElem).attr("data-autoplay", true);
            onYouTubeIframeAPIReady();
		} else if (videoElem.tagName == "VIDEO"){
			videoElem.play();
		}
		elem.style.display = "none";
	}
	playModal(dataset){
		const {videoType, videoId, mute, loop, controls, autoplay} = dataset,
			$modalEle = $("#VideoPlayInModal:first"),
			$videoElem = $modalEle.find('.aem-video-player'),
            videoIndex = videoId.replace ( /[^\d.]/g, '' );
		if(!$videoElem .length) return;
		$videoElem.attr({'data-video-type': videoType,'data-video-id': videoId, "id": `aem-video-modal-${videoIndex+cnt++}`, "data-auto-play": true, "data-video-index": videoIndex, "data-mute": mute,
        "data-loop":loop, "data-controls":controls});
		if (!$videoElem.is(':empty')) { $videoElem.empty();}
		if(videoType == "deluxe"){
            var initDeluxePlayer = window.global.initDeluxePlayer;
            if (initDeluxePlayer && typeof videojs == "undefined") initDeluxePlayer(undefined, true);
			delete playerProps.players[videoIndex];
            ths.playerInitORUpdate($videoElem[0], (player) => {
                player.play();
                console.log(player);
            });
			// checkVideoType($modalEle);
		} else if(videoType == "youtube"){
            if(videoId.indexOf("scene7") != -1){
                self.getVideoElement($videoElem,"scene7", videoId, dataset);
            } else {
                self.loadYTAPI();
                let onlyVideoId = videoId.match(self.ytVideoRegex);
                if(onlyVideoId){
                    onlyVideoId = onlyVideoId[7];
                }
                let updatedId = `${videoId}${(videoId.indexOf("?") > -1 ? "&" : "?")}enablejsapi=1${mute == "true" ? '&mute=1' : ''}${loop == "true" ? '&loop=1' : ''}${controls == "false" ? '&controls=0' : ''}&autoplay=1&rel=0&playlist=${onlyVideoId}`;
                $videoElem.append(`<iframe width="100%" height="100%" src="${updatedId}" frameborder="0" data-autoplay="true" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen allow='autoplay'></iframe>`);
                autoplay == "true" && self.YTPlayerCommands($videoElem.find("iframe"));
                onYouTubeIframeAPIReady();
            }
		}
		$modalEle.modal("show");
	}
    bindingModal(){
        ths.bindEventModal = true;
        let $wrapperEle = $(".outer-wrapper");
        $('body').on('hidden.bs.modal', '#VideoPlayInModal', function(evt) {
            $(evt.currentTarget).closest(".jumbotronContainerComponent").removeClass("video-modal-on");
    
        }).on('show.bs.modal', '#VideoPlayInModal', function(evt) {
            $(evt.currentTarget).closest(".jumbotronContainerComponent").addClass("video-modal-on");
         });
    }
}

const request = window.global.ajaxRequest.ajaxCall;
const evtBinding = window.global.eventBindingInst;
const getDeviceName = window.global.deviceName;
const getStorage = window.global.getStorage;
const setStorage = window.global.setStorage;

let singleVideoPlayerInit;
let videoGalleryInit;
let videoDetailInit;
let youtubeVideoPlayerInit;
const config = {
    el: '.video-component,.reusability-video-component, .image-text-container,[data-single-player="true"],iframe',
    compAction: (elementId, ele) => {
        switch (elementId) {
            case "video-gallery-player-component":
                if (!videoGalleryInit) videoGalleryInit = new videoGallery();
                videoGalleryInit.init(ele);
                break;
            case "video-player-component":
                if (!videoDetailInit) videoDetailInit = new videoDetail();
                videoDetailInit.init(ele);
                break;
            case "main-player-container":
                if (!singleVideoPlayerInit) singleVideoPlayerInit = new singleVideoPlayer();
                singleVideoPlayerInit.init(ele);
                break;
            case "youtube_player":
                if (!youtubeVideoPlayerInit) youtubeVideoPlayerInit = new youtubeVideoPlayer();
                youtubeVideoPlayerInit.init(ele);
                break;
            case "image-text-video-gallery-player-component":
                if (!singleVideoPlayerInit) singleVideoPlayerInit = new singleVideoPlayer();
                singleVideoPlayerInit.init($(ele).find(".single-video-player"));
                break;
            default:
                console.log(`this video comp is not captured in the video player JS file. Element id is ${elementId}`);
                break;
        }
    },
    playerProps: {
        isIOSDevice: /iPad|iPhone|iPod/.test(navigator.userAgent) && !window.MSStream,
        isTouchDevice: /(Mobile|Android|Windows Phone)/.test(window.navigator && window.navigator.userAgent),
        players: [],
        playerIndx: 0,
        actionProps: [],
        scrollingActive: [],
    },
    init: (obj) => {
        const self = obj;
        const $el = $(self.el).filter(":not(.loaded)");
        if (!$el.length) return;
        if(!window.global.initDeluxePlayer){
            console.log(`%c DeluxePlayerDependencyNotFound => This is dependency with commonDependency.JS file. Please check the JS order once.`, "background: red; color:white");
            return;
        } 
        window.global.initDeluxePlayer();
        _.each($el, (item, indx) => {
            $(item).addClass("loaded");
            self.compAction($(item).attr("id"), $(item));
        });
    }
}
let self;
const { playerProps } = config;
config.init.call(undefined, config);
if(!$(".videoDetail").length){
    const videoModal = new videoPlayInModal();
}
document.addEventListener('DOMContentLoaded', () => {
    // post load action
    config.init.call(undefined, config);
    // videoModal.init();
}, false);